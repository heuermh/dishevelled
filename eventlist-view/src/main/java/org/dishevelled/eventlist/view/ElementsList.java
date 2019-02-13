/*

    dsh-eventlist-view  Views for event lists.
    Copyright (c) 2010-2019 held jointly by the individual authors.

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
package org.dishevelled.eventlist.view;

import java.awt.BorderLayout;

import java.util.List;

import javax.swing.JList;
import javax.swing.JScrollPane;

import ca.odell.glazedlists.EventList;

import ca.odell.glazedlists.swing.GlazedListsSwing;

import org.dishevelled.identify.ContextMenuListener;
import org.dishevelled.identify.StripeListCellRenderer;

/**
 * Elements list.
 *
 * @param <E> model element type
 * @author  Michael Heuer
 */
public class ElementsList<E>
    extends AbstractEventListView<E>
{
    /** List. */
    private final JList list;


    /**
     * Create a new elements view with the specified model.
     *
     * @param model model, must not be null
     */
    public ElementsList(final EventList<E> model)
    {
        super(model);

        list = new JList(GlazedListsSwing.eventListModelWithThreadProxyList(getModel()));
        list.setCellRenderer(new StripeListCellRenderer());
        list.setSelectionModel(getListSelectionModelAdapter());
        list.addMouseListener(new ContextMenuListener(getContextMenu()));

        setLayout(new BorderLayout());
        add("North", createToolBarPanel());
        add("Center", new JScrollPane(list));
    }

    /**
     * Create a new elements view with the specified label text and model.
     *
     * @param labelText label text
     * @param model model, must not be null
     */
    public ElementsList(final String labelText, final EventList<E> model)
    {
        this(model);
        getLabel().setText(labelText);
    }


    @Override
    protected void cut(final List<E> toCut)
    {
        // empty
    }

    @Override
    protected void copy(final List<E> toCopy)
    {
        // empty
    }

    @Override
    public void add()
    {
        // empty
    }

    @Override
    public void paste()
    {
        // empty
    }

    /**
     * Return the list for this elements list.
     *
     * @return the list for this elements list
     */
    public final JList getList()
    {
        return list;
    }
}
