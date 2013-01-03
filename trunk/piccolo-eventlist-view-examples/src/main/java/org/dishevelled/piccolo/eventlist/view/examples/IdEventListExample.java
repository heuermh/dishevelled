/*

    dsh-piccolo-eventlist-view-examples  Examples for the piccolo-eventlist-view library.
    Copyright (c) 2010-2013 held jointly by the individual authors.

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

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.dishevelled.iconbundle.IconBundle;

import org.dishevelled.iconbundle.tango.TangoProject;

import org.dishevelled.identify.ContextMenuListener;
import org.dishevelled.identify.Identifiable;
import org.dishevelled.piccolo.eventlist.view.IdEventListNode;

import org.piccolo2d.PCanvas;
import org.piccolo2d.PLayer;

import org.piccolo2d.extras.swing.PScrollPane;

import org.piccolo2d.util.PPaintContext;

/**
 * Identifiable event list example.
 *
 * @author  Michael Heuer
 * @version $Version$ $Date$
 */
public final class IdEventListExample
    extends JPanel
    implements Runnable
{

    /**
     * Create a new identifiable event list example.
     */
    public IdEventListExample()
    {
        super();
        PCanvas canvas = new PCanvas();
        canvas.setDefaultRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.setAnimatingRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.setInteractingRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        PLayer layer = canvas.getLayer();

        List<IdentifiableString> strings = new ArrayList<IdentifiableString>();
        for (int i = 0; i < 100; i++)
        {
            strings.add(new IdentifiableString("Value " + i));
        }
        final EventList<IdentifiableString> eventList = GlazedLists.eventList(strings);

        IdEventListNode<IdentifiableString> eventListNode = new IdEventListNode<IdentifiableString>(eventList);
        eventListNode.offset(20.0d, 40.0d);
        layer.addChild(eventListNode);

        // todo:  zoom with mouse?  right click is for context menu, mouse wheel scrolls
        canvas.removeInputEventListener(canvas.getZoomEventHandler());
        canvas.addMouseListener(new ContextMenuListener(eventListNode.getContextMenu()));

        JPanel toolBarPanel = new JPanel();
        toolBarPanel.setLayout(new BoxLayout(toolBarPanel, BoxLayout.X_AXIS));
        toolBarPanel.add(Box.createGlue());
        toolBarPanel.add(Box.createGlue());
        toolBarPanel.add(eventListNode.getToolBar());
        toolBarPanel.addMouseListener(new ContextMenuListener(eventListNode.getToolBarContextMenu()));

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

        setLayout(new BorderLayout());
        add("North", toolBarPanel);
        add("Center", new PScrollPane(canvas));
    }


    /** {@inheritDoc} */
    public void run()
    {
        JFrame f = new JFrame("Identifiable Event List Example");
        f.setContentPane(this);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setBounds(100, 100, 600, 600);
        f.setVisible(true);
    }

    private class IdentifiableString
        implements Identifiable
    {
        private final String value;
        IdentifiableString(final String value) { this.value = value; }
        public String getName() { return value; }
        public String toString() { return getName(); }
        public IconBundle getIconBundle() { return TangoProject.TEXT_X_GENERIC; }
    }

    /**
     * Main.
     *
     * @param args command line arguments, ignored
     */
    public static void main(final String[] args)
    {
        SwingUtilities.invokeLater(new IdEventListExample());
    }
}