/*

    dsh-eventlist-view  Views for event lists.
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
package org.dishevelled.eventlist.view;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.ListSelection;

import ca.odell.glazedlists.swing.EventListModel;

import java.awt.BorderLayout;

import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import javax.swing.border.EmptyBorder;

import javax.swing.event.EventListenerList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.dishevelled.iconbundle.IconSize;

import org.dishevelled.iconbundle.tango.TangoProject;

import org.dishevelled.identify.ContextMenuButton;
import org.dishevelled.identify.ContextMenuListener;
import org.dishevelled.identify.IdPopupMenu;
import org.dishevelled.identify.IdToolBar;

/**
 * Elements list.
 *
 * @param <E> model element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class ElementsList<E>
    extends AbstractEventListView<E>
{
    /** List. */
    private final JList list;

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
     * Create a new elements view with the specified model.
     *
     * @param model model, must not be null
     */
    public ElementsList(final EventList<E> model)
    {
        super(model);

        list = new JList(new EventListModel<E>(getModel()));
        list.setSelectionModel(new ListSelectionModelAdapter());

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
        list.addMouseListener(new ContextMenuListener(contextMenu));

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
        add("Center", new JScrollPane(list));
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
     * Return the list for this elements list.
     *
     * @return the list for this elements list
     */
    protected final JList getList()
    {
        return list;
    }

    /**
     * Return the label for this elements list.
     *
     * @return the label for this elements list
     */
    protected final JLabel getLabel()
    {
        return label;
    }

    /**
     * Return the tool bar for this elements list.
     *
     * @return the tool bar for this elements list
     */
    protected final IdToolBar getToolBar()
    {
        return toolBar;
    }

    /**
     * Return the context menu for this elements list.
     *
     * @return the context menu for this elements list
     */
    protected final IdPopupMenu getContextMenu()
    {
        return contextMenu;
    }

    /**
     * Return the tool bar context menu for this elements list.
     *
     * @return the tool bar context menu for this elements list
     */
    protected final JPopupMenu getToolBarContextMenu()
    {
        return toolBarContextMenu;
    }

    /**
     * Return the tool bar context menu button for this elements list.
     *
     * @return the tool bar context menu button for this elements list
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
            // will need to dispose this listener at some point
            getSelectionModel().addSelectionListener(new ListSelection.Listener()
                {
                    /** {@inheritDoc} */
                    public void selectionChanged(final int index0, final int index1)
                    {
                        fireSelectionChanged(index0, index1);
                    }
                });
        }


        /** {@inheritDoc} */
        public void setSelectionInterval(final int index0, final int index1)
        {
            getSelectionModel().setSelection(index0, index1);
        }

        /** {@inheritDoc} */
        public void addSelectionInterval(final int index0, final int index1)
        {
            getSelectionModel().select(index0, index1);
        }

        /** {@inheritDoc} */
        public void removeSelectionInterval(final int index0, final int index1)
        {
            getSelectionModel().deselect(index0, index1);
        }

        /** {@inheritDoc} */
        public int getMinSelectionIndex()
        {
            return getSelectionModel().getMinSelectionIndex();
        }

        /** {@inheritDoc} */
        public int getMaxSelectionIndex()
        {
            return getSelectionModel().getMaxSelectionIndex();
        }

        /** {@inheritDoc} */
        public boolean isSelectedIndex(final int index)
        {
            return getSelectionModel().isSelected(index);
        }

        /** {@inheritDoc} */
        public int getAnchorSelectionIndex()
        {
            return getSelectionModel().getAnchorSelectionIndex();
        }

        /** {@inheritDoc} */
        public void setAnchorSelectionIndex(final int index)
        {
            getSelectionModel().setAnchorSelectionIndex(index);
        }

        /** {@inheritDoc} */
        public int getLeadSelectionIndex()
        {
            return getSelectionModel().getLeadSelectionIndex();
        }

        /** {@inheritDoc} */
        public void setLeadSelectionIndex(final int index)
        {
            getSelectionModel().setLeadSelectionIndex(index);
        }

        /** {@inheritDoc} */
        public void clearSelection()
        {
            getSelectionModel().deselectAll();
        }

        /** {@inheritDoc} */
        public boolean isSelectionEmpty()
        {
            return getSelectionModel().getSelected().isEmpty();
        }

        /** {@inheritDoc} */
        public void insertIndexInterval(final int index, final int length, final boolean before)
        {
            // empty
        }

        /** {@inheritDoc} */
        public void removeIndexInterval(final int index0, final int index1)
        {
            // empty
        }

        /** {@inheritDoc} */
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

        /** {@inheritDoc} */
        public boolean getValueIsAdjusting()
        {
            return valueIsAdjusting;
        }

        /** {@inheritDoc} */
        public void setSelectionMode(final int selectionMode)
        {
            getSelectionModel().setSelectionMode(selectionMode);
        }

        /** {@inheritDoc} */
        public int getSelectionMode()
        {
            return getSelectionModel().getSelectionMode();
        }

        /** {@inheritDoc} */
        public void addListSelectionListener(final ListSelectionListener listener)
        {
            listenerList.add(ListSelectionListener.class, listener);
        }

        /** {@inheritDoc} */
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