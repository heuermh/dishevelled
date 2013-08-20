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

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.ListSelection;

import ca.odell.glazedlists.event.ListEvent;
import ca.odell.glazedlists.event.ListEventListener;

import java.awt.event.ActionEvent;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
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
import org.dishevelled.identify.IdentifiableAction;

import static org.dishevelled.iconbundle.tango.TangoProject.*;

/**
 * Abstract event list view.
 *
 * @param <E> model element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractEventListView<E>
    extends JPanel
    implements EventListView<E>
{
    /** Event list view support. */
    private final EventListViewSupport<E> eventListViewSupport;

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

    /** List selection model adapter. */
    private final ListSelectionModelAdapter listSelectionModelAdapter;

    /** Select all action. */
    private final IdentifiableAction selectAllAction = new IdentifiableAction("Select all", EDIT_SELECT_ALL)
        {
                @Override
                public void actionPerformed(final ActionEvent event)
                {
                    selectAll();
                }
        };

    /** Clear selection action. */
    private final AbstractAction clearSelectionAction = new AbstractAction("Clear selection")
        {
                @Override
                public void actionPerformed(final ActionEvent event)
                {
                    clearSelection();
                }
        };

    /** Invert selection action. */
    private final AbstractAction invertSelectionAction = new AbstractAction("Invert selection")
        {
                @Override
                public void actionPerformed(final ActionEvent event)
                {
                    invertSelection();
                }
        };

    /** Cut action. */
    private final IdentifiableAction cutAction = new IdentifiableAction("Cut", EDIT_CUT)
        {
                @Override
                public void actionPerformed(final ActionEvent event)
                {
                    cut();
                }
        };

    /** Copy action. */
    private final IdentifiableAction copyAction = new IdentifiableAction("Copy", EDIT_COPY)
        {
                @Override
                public void actionPerformed(final ActionEvent event)
                {
                    copy();
                }
        };

    /** Paste action. */
    private final IdentifiableAction pasteAction = new IdentifiableAction("Paste", EDIT_PASTE)
        {
                @Override
                public void actionPerformed(final ActionEvent event)
                {
                    paste();
                }
        };

    /** Add action. */
    private final IdentifiableAction addAction = new IdentifiableAction("Add", LIST_ADD)
        {
                @Override
                public void actionPerformed(final ActionEvent event)
                {
                    add();
                }
        };

    /** Remove action. */
    private final IdentifiableAction removeAction = new IdentifiableAction("Remove", LIST_REMOVE)
        {
                @Override
                public void actionPerformed(final ActionEvent event)
                {
                    remove();
                }
        };

    /** Remove all action. */
    private final AbstractAction removeAllAction = new AbstractAction("Remove all")
        {
                @Override
                public void actionPerformed(final ActionEvent event)
                {
                    removeAll();
                }
        };

    /** Listener. */
    private ListEventListener<E> listener = new ListEventListener<E>()
        {
            @Override
            public void listChanged(final ListEvent<E> event)
            {
                updateListActions();
            }
        };

    /** Selection listener. */
    private ListSelection.Listener selectionListener = new ListSelection.Listener()
        {
            @Override
            public void selectionChanged(final int index0, final int index1)
            {
                updateSelectionActions();
            }
        };


    /**
     * Create a new abstract event list view with the specified model.
     *
     * @param model model, must not be null
     */
    protected AbstractEventListView(final EventList<E> model)
    {
        super();
        setOpaque(false);
        eventListViewSupport = new EventListViewSupport<E>(model);
        getModel().addListEventListener(listener);
        getSelectionModel().addSelectionListener(selectionListener);
        updateListActions();
        updateSelectionActions();

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

        toolBar = new IdToolBar();
        toolBar.setOpaque(false);
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

        listSelectionModelAdapter = new ListSelectionModelAdapter();
    }


    /**
     * Update list actions.
     */
    private void updateListActions()
    {
        selectAllAction.setEnabled(!isEmpty());
        invertSelectionAction.setEnabled(!isEmpty());
        removeAllAction.setEnabled(!isEmpty());
    }

    /**
     * Update selection actions.
     */
    private void updateSelectionActions()
    {
        copyAction.setEnabled(!isSelectionEmpty());
        cutAction.setEnabled(!isSelectionEmpty());
        removeAction.setEnabled(!isSelectionEmpty());
        clearSelectionAction.setEnabled(!isSelectionEmpty());
    }

    /**
     * Return true if the model is empty.
     *
     * @return true if the model is empty.
     */
    public final boolean isEmpty()
    {
        return getModel().isEmpty();
    }

    /**
     * Return true if the selection is empty.
     *
     * @return true if the selection is empty.
     */
    public final boolean isSelectionEmpty()
    {
        return getSelectionModel().getSelected().isEmpty();
    }

    /**
     * Select all.
     */
    public final void selectAll()
    {
        getSelectionModel().selectAll();
    }

    /**
     * Clear selection.
     */
    public final void clearSelection()
    {
        getSelectionModel().deselectAll();
    }

    /**
     * Invert selection.
     */
    public final void invertSelection()
    {
        getSelectionModel().invertSelection();
    }

    /**
     * Cut.
     */
    public final void cut()
    {
        if (!isSelectionEmpty())
        {
            cut(new ArrayList<E>(getSelectionModel().getSelected()));
        }
    }

    /**
     * Copy.
     */
    public final void copy()
    {
        if (!isSelectionEmpty())
        {
            copy(new ArrayList<E>(getSelectionModel().getSelected()));
        }
    }

    /**
     * Cut the specific list of elements to the clipboard.
     *
     * @param toCut list of elements to cut, must not be null
     */
    protected abstract void cut(List<E> toCut);

    /**
     * Copy the specific list of elements to the clipboard.
     *
     * @param toCopy list of elements to copy, must not be null
     */
    protected abstract void copy(List<E> toCopy);

    /**
     * Add.
     */
    public abstract void add();

    /**
     * Paste.
     */
    public abstract void paste();

    /**
     * Remove.
     */
    public final void remove()
    {
        getSelectionModel().getSelected().clear();
    }

    /**
     * Remove all.
     */
    public final void removeAll()
    {
        getModel().clear();
    }

    /**
     * Return the select all action.
     *
     * @return the select all action
     */
    protected final IdentifiableAction getSelectAllAction()
    {
        return selectAllAction;
    }

    /**
     * Return the clear selection action.
     *
     * @return the clear selection action
     */
    protected final AbstractAction getClearSelectionAction()
    {
        return clearSelectionAction;
    }

    /**
     * Return the invert selection action.
     *
     * @return the invert selection action
     */
    protected final AbstractAction getInvertSelectionAction()
    {
        return invertSelectionAction;
    }

    /**
     * Return the cut action.
     *
     * @return the cut action
     */
    protected final IdentifiableAction getCutAction()
    {
        return cutAction;
    }

    /**
     * Return the copy action.
     *
     * @return the copy action
     */
    protected final IdentifiableAction getCopyAction()
    {
        return copyAction;
    }

    /**
     * Return the paste action.
     *
     * @return the paste action
     */
    protected final IdentifiableAction getPasteAction()
    {
        return pasteAction;
    }

    /**
     * Return the add action.
     *
     * @return the add action
     */
    protected final IdentifiableAction getAddAction()
    {
        return addAction;
    }

    /**
     * Return the remove action.
     *
     * @return the remove action
     */
    protected final IdentifiableAction getRemoveAction()
    {
        return removeAction;
    }

    /**
     * Return the remove all action.
     *
     * @return the remove all action
     */
    protected final AbstractAction getRemoveAllAction()
    {
        return removeAllAction;
    }

    /**
     * Return the label.
     *
     * @return the label
     */
    protected final JLabel getLabel()
    {
        return label;
    }

    /**
     * Return the tool bar.
     *
     * @return the tool bar
     */
    protected final IdToolBar getToolBar()
    {
        return toolBar;
    }

    /**
     * Return the context menu.
     *
     * @return the context menu
     */
    protected final IdPopupMenu getContextMenu()
    {
        return contextMenu;
    }

    /**
     * Return the tool bar context menu.
     *
     * @return the tool bar context menu
     */
    protected final JPopupMenu getToolBarContextMenu()
    {
        return toolBarContextMenu;
    }

    /**
     * Return the context menu button.
     *
     * @return the context menu button
     */
    protected final ContextMenuButton getContextMenuButton()
    {
        return contextMenuButton;
    }

    /**
     * Create and return a new tool bar panel.
     *
     * @return a new tool bar panel
     */
    protected final JPanel createToolBarPanel()
    {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(getLabel());
        panel.add(Box.createGlue());
        panel.add(Box.createGlue());
        panel.add(getToolBar());
        panel.addMouseListener(new ContextMenuListener(getToolBarContextMenu()));
        return panel;
    }

    /**
     * Return the list selection model adapter.
     *
     * @return the list selection model adapter
     */
    protected ListSelectionModelAdapter getListSelectionModelAdapter()
    {
        return listSelectionModelAdapter;
    }

    @Override
    public final EventList<E> getModel()
    {
        return eventListViewSupport.getModel();
    }

    @Override
    public final ListSelection<E> getSelectionModel()
    {
        return eventListViewSupport.getSelectionModel();
    }

    /**
     * Release the resources consumed by this abstract event list view
     * so that it may eventually be garbage collected.  Subclasses that override
     * this method should call <code>super.dispose()</code>.
     */
    public void dispose()
    {
        getModel().removeListEventListener(listener);
        getSelectionModel().removeSelectionListener(selectionListener);
        getSelectionModel().removeSelectionListener(listSelectionModelAdapter.getSelectionListener());
    }

    /**
     * List selection model that delegates to {@link #getSelectionModel}.
     *
     * @param <E> model element type
     */
    protected final class ListSelectionModelAdapter implements ListSelectionModel
    {
        /** Event listener list. */
        private final EventListenerList listenerList;

        /** True if the selection is undergoing a series of changes. */
        private boolean valueIsAdjusting = false;

        /** Full start index. */
        private int fullIndex0 = -1;

        /** Full end index. */
        private int fullIndex1 = -1;

        /** List selection listener. */
        private final ListSelection.Listener listSelectionListener = new ListSelection.Listener()
            {
                @Override
                public void selectionChanged(final int index0, final int index1)
                {
                    fireSelectionChanged(index0, index1);
                }
            };


        /**
         * Create a new list selection model adapter.
         */
        private ListSelectionModelAdapter()
        {
            listenerList = new EventListenerList();
            getSelectionModel().addSelectionListener(listSelectionListener);
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

        /**
         * Return the selection listener.
         *
         * @return the selection listener
         */
        private ListSelection.Listener getSelectionListener()
        {
            return listSelectionListener;
        }
    }
}
