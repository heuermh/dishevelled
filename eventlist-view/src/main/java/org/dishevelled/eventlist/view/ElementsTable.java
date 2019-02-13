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

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.SortedList;

import ca.odell.glazedlists.gui.AbstractTableComparatorChooser;
import ca.odell.glazedlists.gui.TableFormat;

import ca.odell.glazedlists.swing.GlazedListsSwing;
import ca.odell.glazedlists.swing.TableComparatorChooser;

import org.dishevelled.identify.ContextMenuListener;
import org.dishevelled.identify.StripeTableCellRenderer;

/**
 * Elements table.
 *
 * @param <E> model element type
 * @author  Michael Heuer
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
        super(new SortedList<E>(model, null));
        if (tableFormat == null)
        {
            throw new IllegalArgumentException("tableFormat must not be null");
        }
        table = new JTable(GlazedListsSwing.eventTableModelWithThreadProxyList(getModel(), tableFormat));
        TableComparatorChooser.install(table,
                                       (SortedList<E>) getModel(),
                                       AbstractTableComparatorChooser.MULTIPLE_COLUMN_MOUSE);

        StripeTableCellRenderer.install(table);
        table.setSelectionModel(getListSelectionModelAdapter());
        table.addMouseListener(new ContextMenuListener(getContextMenu()));

        setLayout(new BorderLayout());
        add("North", createToolBarPanel());
        add("Center", createTablePanel());
    }

    /**
     * Create a new elements table view with the specified model and table format.
     *
     * @param labelText label text
     * @param model model, must not be null
     * @param tableFormat table format, must not be null
     */
    public ElementsTable(final String labelText, final EventList<E> model, final TableFormat<E> tableFormat)
    {
        this(model, tableFormat);
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
     * Return the table for this elements table.
     *
     * @return the table for this elements table
     */
    protected final JTable getTable()
    {
        return table;
    }

    /**
     * Create and return a new table panel.
     *
     * @return a new table panel
     */
    private JPanel createTablePanel()
    {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout());
        panel.add("Center", new JScrollPane(table));
        return panel;
    }
}
