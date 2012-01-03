/*

    dsh-eventlist-view  Views for event lists.
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
package org.dishevelled.eventlist.view;

import java.awt.BorderLayout;

import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.ListSelection;

import ca.odell.glazedlists.gui.TableFormat;

import ca.odell.glazedlists.swing.EventTableModel;
import ca.odell.glazedlists.swing.TableComparatorChooser;

/**
 * Elements table.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class ElementsTable<E>
    extends AbstractEventListView<E>
{
    /** Table. */
    private final JTable table;


    /**
     * Create a new elements table view with the specified model and table format.
     *
     * @param model model, must not be null
     * @param tableFormat table format, must not be null
     */
    public ElementsTable(final EventList<E> model, final TableFormat<E> tableFormat)
    {
        super(model);
        SortedList<E> sortedModel = new SortedList<E>(model, null);
        EventTableModel<E> tableModel = new EventTableModel<E>(sortedModel, tableFormat);
        table = new JTable(tableModel);
        TableComparatorChooser.install(table, sortedModel, TableComparatorChooser.MULTIPLE_COLUMN_MOUSE);

        layoutComponents();
    }


    /**
     * Layout components.
     */
    private void layoutComponents()
    {
        setLayout(new BorderLayout());
        add("Center", new JScrollPane(table));
    }

    /** {@inheritDoc} */
    protected void cut(final List<E> toCut)
    {
        // empty
    }

    /** {@inheritDoc} */
    protected void copy(final List<E> toCopy)
    {
        // empty
    }

    /** {@inheritDoc} */
    public void add()
    {
        // empty
    }

    /** {@inheritDoc} */
    public void paste()
    {
        // empty
    }

    /**
     * Return the table for this elements table.
     *
     * @return the table for this elements table
     */
    protected final JTable getTable()
    {
        return table;
    }
}