/*

    dsh-eventlist-view-examples  Examples for the eventlist-view library.
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
package org.dishevelled.eventlist.view.examples;

import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.SwingUtilities;

import javax.swing.border.EmptyBorder;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;

import ca.odell.glazedlists.gui.TableFormat;

import org.dishevelled.functor.UnaryFunction;

import org.dishevelled.eventlist.view.CountLabel;
import org.dishevelled.eventlist.view.ElementsLabel;
import org.dishevelled.eventlist.view.ElementsList;
import org.dishevelled.eventlist.view.ElementsTable;
import org.dishevelled.eventlist.view.ElementsSummary;
import org.dishevelled.eventlist.view.IdElementsList;
import org.dishevelled.eventlist.view.IdElementsSummary;

import org.dishevelled.layout.LabelFieldPanel;

/**
 * Event list view example.
 *
 * @author  Michael Heuer
 */
public final class EventListViewExample
    extends LabelFieldPanel
    implements Runnable
{
    /** Values. */
    private final EventList<String> eventList;


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
        eventList = GlazedLists.eventList(strings);

        addField("Count label:", new CountLabel<String>(eventList));
        addField("Elements label:", new ElementsLabel<String>(eventList));
        addLabel("Elements summary:");

        UnaryFunction<String, JComponent> modelToLabel = new UnaryFunction<String, JComponent>()
        {
            // todo:  this really aught to be a list synchronized with the event list
            private final Map<String, JComponent> cache = new HashMap<String, JComponent>();

            @Override
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
        addFinalField(createLowerPanel());

        Timer t = new Timer(5000, new ActionListener()
            {
                @Override
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


    /**
     * Create and return a new lower panel.
     *
     * @return a new lower panel
     */
    private JPanel createLowerPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 3, 12, 12));

        LabelFieldPanel left = new LabelFieldPanel();
        left.addLabel("Elements list:");
        left.addFinalField(new ElementsList<String>("Elements:", eventList));

        LabelFieldPanel center = new LabelFieldPanel();
        center.addLabel("Identifiable elements list:");
        center.addFinalField(new IdElementsList<String>("Identifiable elements:", eventList));

        LabelFieldPanel right = new LabelFieldPanel();
        right.addLabel("Elements table:");
        TableFormat<String> tableFormat = new TableFormat<String>()
            {
                @Override
                public int getColumnCount()
                {
                    return 2;
                }

                @Override
                public String getColumnName(final int column)
                {
                    if (column == 0)
                    {
                        return "Value";
                    }
                    else
                    {
                        return "Length";
                    }
                }

                @Override
                public Object getColumnValue(final String value, final int column)
                {
                    if (column == 0)
                    {
                        return value;
                    }
                    else
                    {
                        return value.length();
                    }
                }
            };
        right.addFinalField(new ElementsTable<String>("Elements:", eventList, tableFormat));

        panel.add(left);
        panel.add(center);
        panel.add(right);
        return panel;
    }


    @Override
    public void run()
    {
        JFrame f = new JFrame("Event list view example");
        f.setContentPane(this);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setBounds(100, 100, 1080, 606);
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