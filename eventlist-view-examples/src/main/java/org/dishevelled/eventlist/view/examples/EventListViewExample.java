/*

    dsh-eventlist-view-examples  Examples for the eventlist-view library.
    Copyright (c) 2010-2011 held jointly by the individual authors.

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
package org.dishevelled.eventlist.view.examples;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.SwingUtilities;

import javax.swing.border.EmptyBorder;

import org.dishevelled.functor.UnaryFunction;

import org.dishevelled.eventlist.view.CountLabel;
import org.dishevelled.eventlist.view.ElementsLabel;
import org.dishevelled.eventlist.view.ElementsList;
import org.dishevelled.eventlist.view.ElementsSummary;
import org.dishevelled.eventlist.view.IdElementsList;
import org.dishevelled.eventlist.view.IdElementsSummary;

import org.dishevelled.layout.LabelFieldPanel;

/**
 * Event list view example.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class EventListViewExample
    extends LabelFieldPanel
    implements Runnable
{

    /**
     * Create a new event list view example.
     */
    public EventListViewExample()
    {
        super();
        setBorder(new EmptyBorder(12, 12, 12, 12));

        List<String> strings = new ArrayList<String>();
        for (int i = 0; i < 100; i++)
        {
            strings.add("Value " + i);
        }
        final EventList<String> eventList = GlazedLists.eventList(strings);
        addField("Count label:", new CountLabel<String>(eventList));
        addField("Elements label:", new ElementsLabel<String>(eventList));
        addLabel("Elements summary:");

        UnaryFunction<String, JComponent> modelToLabel = new UnaryFunction<String, JComponent>()
        {
            // todo:  this really aught to be a list synchronized with the event list
            private final Map<String, JComponent> cache = new HashMap<String, JComponent>();

            /** {@inheritDoc} */
            public JComponent evaluate(final String element)
            {
                if (!cache.containsKey(element))
                {
                    cache.put(element, new JLabel(element));
                }
                return cache.get(element);
            }
        };

        addField(new ElementsSummary<String>(eventList, modelToLabel));
        addSpacing(12);
        addLabel("Identifiable elements summary:");
        addField(new IdElementsSummary<String>(eventList));
        addSpacing(12);
        addLabel("Elements list:");
        addField(new ElementsList<String>(eventList));
        addSpacing(12);
        addLabel("Identifiable elements list:");
        addFinalField(new IdElementsList<String>(eventList));

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
    }


    /** {@inheritDoc} */
    public void run()
    {
        JFrame f = new JFrame("Event list view example");
        f.setContentPane(this);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setBounds(100, 100, 450, 650);
        f.setVisible(true);
    }


    /**
     * Main.
     *
     * @param args command line arguments, ignored
     */
    public static void main(final String[] args)
    {
        SwingUtilities.invokeLater(new EventListViewExample());
    }
}