/*

    dsh-piccolo-eventlist-view-examples  Examples for the piccolo-eventlist-view library.
    Copyright (c) 2010-2012 held jointly by the individual authors.

    This library is free software; you can redistribute it and/or modify it
    under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation; either version 3 of the License, or (at
    your option) any later version.

    This library is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; with out even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
    License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this library;  if not, write to the Free Software Foundation,
    Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA.

    > http://www.fsf.org/licensing/licenses/lgpl.html
    > http://www.opensource.org/licenses/lgpl-license.php

*/
package org.dishevelled.piccolo.eventlist.view.examples;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;

import ca.odell.glazedlists.swing.EventListModel;

import java.awt.BorderLayout;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import javax.swing.border.EmptyBorder;

import org.dishevelled.functor.UnaryFunction;

import org.dishevelled.layout.LabelFieldPanel;

import org.dishevelled.piccolo.eventlist.view.CountLabelNode;
import org.dishevelled.piccolo.eventlist.view.ElementsLabelNode;
import org.dishevelled.piccolo.eventlist.view.ElementsNode;
import org.dishevelled.piccolo.eventlist.view.ElementsSummaryNode;
import org.dishevelled.piccolo.eventlist.view.EventListNode;

import org.piccolo2d.PCanvas;
import org.piccolo2d.PLayer;

import org.piccolo2d.nodes.PText;

import org.piccolo2d.util.PPaintContext;

/**
 * Event list node example.
 *
 * @author  Michael Heuer
 * @version $Version$ $Date$
 */
public final class EventListNodeExample
    extends JPanel
    implements Runnable
{

    /**
     * Create a new event list node example.
     */
    public EventListNodeExample()
    {
        super();
        PCanvas canvas = new PCanvas();
        canvas.setDefaultRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.setAnimatingRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.setInteractingRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        PLayer layer = canvas.getLayer();

        List<String> strings = new ArrayList<String>();
        for (int i = 0; i < 100; i++)
        {
            strings.add("Value " + i);
        }
        final EventList<String> eventList = GlazedLists.eventList(strings);
        UnaryFunction<String, PText> modelToView = new UnaryFunction<String, PText>()
            {
                /** {@inheritDoc} */
                public PText evaluate(final String element)
                {
                    return new PText(element);
                }
            };

        PText label0 = new PText("Count label node:");
        label0.offset(20.0d, 20.0d);
        layer.addChild(label0);
        CountLabelNode<String> countLabel = new CountLabelNode<String>(eventList);
        countLabel.offset(200.0d, 20.0d);
        layer.addChild(countLabel);

        PText label1 = new PText("Elements label node:");
        label1.offset(20.0d, 80.0d);
        layer.addChild(label1);
        ElementsLabelNode<String> elementsLabel = new ElementsLabelNode<String>(eventList);
        elementsLabel.offset(200.0d, 80.0d);
        layer.addChild(elementsLabel);

        PText label2 = new PText("Elements summary node:");
        label2.offset(20.0d, 140.0d);
        layer.addChild(label2);
        ElementsSummaryNode<String> elementsSummary = new ElementsSummaryNode<String>(eventList, modelToView);
        elementsSummary.offset(200.0d, 140.0d);
        layer.addChild(elementsSummary);

        PText label3 = new PText("Elements node:");
        label3.offset(20.0d, 200.0d);
        layer.addChild(label3);
        ElementsNode<String> elements = new ElementsNode<String>(eventList, modelToView);
        elements.offset(200.0d, 200.0d);
        layer.addChild(elements);

        PText label4 = new PText("Event list node:");
        label4.offset(420.0d, 20.0d);
        layer.addChild(label4);
        EventListNode<String> eventListNode = new EventListNode<String>(eventList, modelToView);
        eventListNode.offset(420.0d, 80.0d);
        layer.addChild(eventListNode);

        Timer t = new Timer(5000, new ActionListener()
            {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent e)
                {
                    if (!eventList.isEmpty())
                    {
                        eventList.remove(0);
                    }
                }
            });
        t.setRepeats(true);
        t.start();

        JList list = new JList();
        list.setModel(new EventListModel<String>(eventList));
        list.setPrototypeCellValue("0123456789012345678");

        LabelFieldPanel left = new LabelFieldPanel();
        left.addLabel("List:");
        left.addFinalField(new JScrollPane(list));
        left.setBorder(new EmptyBorder(20, 20, 20, 20));

        LabelFieldPanel right = new LabelFieldPanel();
        right.addLabel("Canvas:");
        right.addFinalField(new JScrollPane(canvas));
        right.setBorder(new EmptyBorder(20, 0, 20, 20));

        setLayout(new BorderLayout());
        add("West", left);
        add("Center", right);
    }


    /** {@inheritDoc} */
    public void run()
    {
        JFrame f = new JFrame("Event List Node Example");
        f.setContentPane(this);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setBounds(100, 100, 900, 600);
        f.setVisible(true);
    }


    /**
     * Main.
     *
     * @param args command line arguments, ignored
     */
    public static void main(final String[] args)
    {
        SwingUtilities.invokeLater(new EventListNodeExample());
    }
}