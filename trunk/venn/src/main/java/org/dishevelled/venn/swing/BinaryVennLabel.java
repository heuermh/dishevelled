/*

    dsh-venn  Lightweight components for venn diagrams.
    Copyright (c) 2009 held jointly by the individual authors.

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
package org.dishevelled.venn.swing;

import java.awt.BorderLayout;

import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import ca.odell.glazedlists.event.ListEvent;
import ca.odell.glazedlists.event.ListEventListener;

import org.dishevelled.layout.LabelFieldPanel;

import org.dishevelled.venn.BinaryVennModel;

/**
 * Binary venn diagram label.
 *
 * @param <E> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class BinaryVennLabel<E>
    extends JPanel
{
    /** Binary venn model. */
    private BinaryVennModel<E> model;

    /** Label for the first list. */
    private final JLabel label0;

    /** Contents of the first list. */
    private final JLabel list0;

    /** Label for the second list. */
    private final JLabel label1;

    /** Contents of the second list. */
    private final JLabel list1;

    /** Label for the intersection. */
    private final JLabel intersectionLabel;

    /** Contents of the intersection. */
    private final JLabel intersection;

    /** Label for the union. */
    private final JLabel unionLabel;

    /** Contents of the union. */
    private final JLabel union;


    /**
     * Create a new binary venn label with the specified model.
     *
     * @param model model for this binary venn label, must not be null
     */
    public BinaryVennLabel(final BinaryVennModel<E> model)
    {
        super();
        setLayout(new BorderLayout());

        if (model == null)
        {
            throw new IllegalArgumentException("model must not be null");
        }
        this.model = model;

        label0 = new JLabel();
        list0 = new JLabel();
        label1 = new JLabel();
        list1 = new JLabel();
        intersectionLabel = new JLabel();
        intersection = new JLabel();
        unionLabel = new JLabel();
        union = new JLabel();

        installListeners();
        initLabels();
        layoutComponents();
    }

    /**
     * Install listeners.
     */
    private void installListeners()
    {
        model.list0().addListEventListener(new ListEventListener<E>()
            {
                /** {@inheritDoc} */
                public void listChanged(final ListEvent<E> event)
                {
                    label0.setText(buildLabel("First set (", event.getSourceList().size(), "):"));
                    list0.setText(buildContent(event.getSourceList()));
                }
            });

        model.list1().addListEventListener(new ListEventListener<E>()
            {
                /** {@inheritDoc} */
                public void listChanged(final ListEvent<E> event)
                {
                    label1.setText(buildLabel("Second set (", event.getSourceList().size(), "):"));
                    list1.setText(buildContent(event.getSourceList()));
                }
            });

        model.intersection().addListEventListener(new ListEventListener<E>()
            {
                /** {@inheritDoc} */
                public void listChanged(final ListEvent<E> event)
                {
                    intersectionLabel.setText(buildLabel("Intersection (", event.getSourceList().size(), "):"));
                    intersection.setText(buildContent(event.getSourceList()));
                }
            });

        model.union().addListEventListener(new ListEventListener<E>()
            {
                /** {@inheritDoc} */
                public void listChanged(final ListEvent<E> event)
                {
                    unionLabel.setText(buildLabel("Union (", event.getSourceList().size(), "):"));
                    union.setText(buildContent(event.getSourceList()));
                }
            });
    }

    /**
     * Initialize labels.
     */
    private void initLabels()
    {
        label0.setText(buildLabel("First set (", model.list0().size(), "):"));
        list0.setText(buildContent(model.list0()));
        label1.setText(buildLabel("Second set (", model.list1().size(), "):"));
        list1.setText(buildContent(model.list1()));
        intersectionLabel.setText(buildLabel("Intersection (", model.intersection().size(), "):"));
        intersection.setText(buildContent(model.intersection()));
        unionLabel.setText(buildLabel("Union (", model.union().size(), "):"));
        union.setText(buildContent(model.union()));
    }

    /**
     * Layout components.
     */
    private void layoutComponents()
    {
        LabelFieldPanel panel = new LabelFieldPanel();
        panel.addField(label0, list0);
        panel.addField(label1, list1);
        panel.addField(intersectionLabel, intersection);
        panel.addField(unionLabel, union);
        panel.addFinalSpacing();
        add("Center", panel);
    }

    /**
     * Build and return label text.
     *
     * @param prefix prefix
     * @param size size
     * @param suffix suffix
     * @return label text
     */
    private static String buildLabel(final String prefix, final int size, final String suffix)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        sb.append(size);
        sb.append(suffix);
        return sb.toString();
    }

    /**
     * Build and return content text.
     *
     * @param list list
     * @return content text
     */
    private static <T> String buildContent(final List<T> list)
    {
        if (list.isEmpty())
        {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(list.get(0).toString());
        for (int i = 1, size = Math.min(5, list.size()); i < size; i++)
        {
            sb.append(", ");
            sb.append(list.get(i).toString());
        }
        if (list.size() > 5)
        {
            sb.append(", ...");
        }
        return sb.toString();
    }
}