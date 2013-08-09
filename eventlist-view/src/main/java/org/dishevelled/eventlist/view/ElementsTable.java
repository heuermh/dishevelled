/*

    dsh-eventlist-view  Views for event lists.
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
package org.dishevelled.eventlist.view;

import java.awt.BorderLayout;

import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import javax.swing.border.EmptyBorder;

import javax.swing.event.EventListenerList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.ListSelection;
import ca.odell.glazedlists.SortedList;

import ca.odell.glazedlists.gui.TableFormat;

import ca.odell.glazedlists.swing.EventTableModel;
import ca.odell.glazedlists.swing.TableComparatorChooser;

import org.dishevelled.iconbundle.IconSize;

import org.dishevelled.iconbundle.tango.TangoProject;

import org.dishevelled.identify.ContextMenuButton;
import org.dishevelled.identify.ContextMenuListener;
import org.dishevelled.identify.IdPopupMenu;
import org.dishevelled.identify.IdToolBar;

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

    /** Label. */
    private final JLabel label;

    /** Tool bar. */
    private final IdToolBar toolBar;

    /** Context menu. */
    private final IdPopupMenu contextMenu;

    /** Tool bar context menu. */
    private final JPopupMenu toolBarContextMenu;

    /** Tool bar context menu button. */
    private final ContextMenuButton contextMenuButton;


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
        table.setSelectionModel(new ListSelectionModelAdapter());
        TableComparatorChooser.install(table, sortedModel, TableComparatorChooser.MULTIPLE_COLUMN_MOUSE);

        label = new JLabel();
        label.setAlignmentY(-1.0f);
        label.setBorder(new EmptyBorder(0, 2, 2, 0));

        contextMenu = new IdPopupMenu();
        contextMenu.add(getCutAction(), TangoProject.EXTRA_SMALL);
        contextMenu.add(getCopyAction(), TangoProject.EXTRA_SMALL);
        contextMenu.add(getPasteAction(), TangoProject.EXTRA_SMALL);
        contextMenu.addSeparator();
        contextMenu.add(getSelectAllAction(), TangoProject.EXTRA_SMALL);
        contextMenu.add(getClearSelectionAction());
        contextMenu.add(getInvertSelectionAction());
        contextMenu.addSeparator();
        contextMenu.add(getAddAction(), TangoProject.EXTRA_SMALL);
        contextMenu.add(getRemoveAction(), TangoProject.EXTRA_SMALL);
        contextMenu.add(getRemoveAllAction());
        table.addMouseListener(new ContextMenuListener(contextMenu));

        toolBar = new IdToolBar();
        toolBar.setBorder(new EmptyBorder(0, 0, 0, 0));
        toolBar.add(getAddAction());
        toolBar.add(getRemoveAction());
        contextMenuButton = toolBar.add(contextMenu);

        toolBarContextMenu = new JPopupMenu();
        for (Object menuItem : toolBar.getDisplayMenuItems())
        {
            toolBarContextMenu.add((JCheckBoxMenuItem) menuItem);
        }
        toolBarContextMenu.addSeparator();
        for (Object iconSize : TangoProject.SIZES)
        {
            toolBarContextMenu.add(toolBar.createIconSizeMenuItem((IconSize) iconSize));
        }
        toolBar.setIconSize(TangoProject.EXTRA_SMALL);
        toolBar.addMouseListener(new ContextMenuListener(toolBarContextMenu));

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
        label.setText(labelText);
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
     * Return the label for this elements table.
     *
     * @return the label for this elements table
     */
    protected final JLabel getLabel()
    {
        return label;
    }

    /**
     * Return the tool bar for this elements table.
     *
     * @return the tool bar for this elements table
     */
    protected final IdToolBar getToolBar()
    {
        return toolBar;
    }

    /**
     * Return the context menu for this elements table.
     *
     * @return the context menu for this elements table
     */
    protected final IdPopupMenu getContextMenu()
    {
        return contextMenu;
    }

    /**
     * Return the tool bar context menu for this elements table.
     *
     * @return the tool bar context menu for this elements table
     */
    protected final JPopupMenu getToolBarContextMenu()
    {
        return toolBarContextMenu;
    }

    /**
     * Return the tool bar context menu button for this elements table.
     *
     * @return the tool bar context menu button for this elements table
     */
    protected final ContextMenuButton getToolBarContextMenuButton()
    {
        return contextMenuButton;
    }

    /**
     * Create and return a new tool bar panel.
     *
     * @return a new tool bar panel
     */
    private JPanel createToolBarPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(label);
        panel.add(Box.createGlue());
        panel.add(Box.createGlue());
        panel.add(toolBar);
        panel.addMouseListener(new ContextMenuListener(toolBarContextMenu));
        return panel;
    }

    /**
     * Create and return a new table panel.
     *
     * @return a new table panel
     */
    private JPanel createTablePanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add("Center", new JScrollPane(table));
        return panel;
    }

    /**
     * List selection model that delegates to {@link #getSelectionModel}.
     *
     * @param <E> model element type
     */
    private final class ListSelectionModelAdapter implements ListSelectionModel
    {
        /** Event listener list. */
        private final EventListenerList listenerList;

        /** True if the selection is undergoing a series of changes. */
        private boolean valueIsAdjusting = false;

        /** Full start index. */
        private int fullIndex0 = -1;

        /** Full end index. */
        private int fullIndex1 = -1;


        /**
         * Create a new list selection model adapter.
         */
        private ListSelectionModelAdapter()
        {
            listenerList = new EventListenerList();
            // todo:  will need to dispose this listener at some point
            getSelectionModel().addSelectionListener(new ListSelection.Listener()
                {
                    @Override
                    public void selectionChanged(final int index0, final int index1)
                    {
                        fireSelectionChanged(index0, index1);
                    }
                });
        }


        @Override
        public void setSelectionInterval(final int index0, final int index1)
        {
            getSelectionModel().setSelection(index0, index1);
        }

        @Override
        public void addSelectionInterval(final int index0, final int index1)
        {
            getSelectionModel().select(index0, index1);
        }

        @Override
        public void removeSelectionInterval(final int index0, final int index1)
        {
            getSelectionModel().deselect(index0, index1);
        }

        @Override
        public int getMinSelectionIndex()
        {
            return getSelectionModel().getMinSelectionIndex();
        }

        @Override
        public int getMaxSelectionIndex()
        {
            return getSelectionModel().getMaxSelectionIndex();
        }

        @Override
        public boolean isSelectedIndex(final int index)
        {
            return getSelectionModel().isSelected(index);
        }

        @Override
        public int getAnchorSelectionIndex()
        {
            return getSelectionModel().getAnchorSelectionIndex();
        }

        @Override
        public void setAnchorSelectionIndex(final int index)
        {
            getSelectionModel().setAnchorSelectionIndex(index);
        }

        @Override
        public int getLeadSelectionIndex()
        {
            return getSelectionModel().getLeadSelectionIndex();
        }

        @Override
        public void setLeadSelectionIndex(final int index)
        {
            getSelectionModel().setLeadSelectionIndex(index);
        }

        @Override
        public void clearSelection()
        {
            getSelectionModel().deselectAll();
        }

        @Override
        public boolean isSelectionEmpty()
        {
            return getSelectionModel().getSelected().isEmpty();
        }

        @Override
        public void insertIndexInterval(final int index, final int length, final boolean before)
        {
            // empty
        }

        @Override
        public void removeIndexInterval(final int index0, final int index1)
        {
            // empty
        }

        @Override
        public void setValueIsAdjusting(final boolean valueIsAdjusting)
        {
            if (!valueIsAdjusting)
            {
                if ((fullIndex0 != -1) && (fullIndex1 != -1))
                {
                    fireSelectionChanged(fullIndex0, fullIndex1);
                    fullIndex0 = -1;
                    fullIndex1 = -1;
                }
            }
            this.valueIsAdjusting = valueIsAdjusting;
        }

        @Override
        public boolean getValueIsAdjusting()
        {
            return valueIsAdjusting;
        }

        @Override
        public void setSelectionMode(final int selectionMode)
        {
            getSelectionModel().setSelectionMode(selectionMode);
        }

        @Override
        public int getSelectionMode()
        {
            return getSelectionModel().getSelectionMode();
        }

        @Override
        public void addListSelectionListener(final ListSelectionListener listener)
        {
            listenerList.add(ListSelectionListener.class, listener);
        }

        @Override
        public void removeListSelectionListener(final ListSelectionListener listener)
        {
            listenerList.remove(ListSelectionListener.class, listener);
        }

        /**
         * Fire a selection changed event to all registered list selection listeners.
         *
         * @param index0 first index
         * @param index1 second index
         */
        private void fireSelectionChanged(final int index0, final int index1)
        {
            if (valueIsAdjusting)
            {
                if ((fullIndex0 == -1) || (index0 < fullIndex0))
                {
                    fullIndex0 = index0;
                }
                if ((fullIndex1 == -1) || (index1 > fullIndex1))
                {
                    fullIndex1 = index1;
                }
            }
            Object[] listeners = listenerList.getListenerList();
            ListSelectionEvent e = null;

            for (int i = listeners.length - 2; i >= 0; i -= 2)
            {
                if (listeners[i] == ListSelectionListener.class)
                {
                    // lazily create the event
                    if (e == null)
                    {
                        e = new ListSelectionEvent(this, index0, index1, valueIsAdjusting);
                    }
                    ((ListSelectionListener) listeners[i + 1]).valueChanged(e);
                }
            }
        }
    }
}