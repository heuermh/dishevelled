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

import ca.odell.glazedlists.swing.EventListModel;

import java.awt.BorderLayout;

import java.util.List;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import javax.swing.event.EventListenerList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.dishevelled.identify.ContextMenuListener;

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


    /**
     * Create a new elements view with the specified model.
     *
     * @param model model, must not be null
     */
    public ElementsList(final EventList<E> model)
    {
        super(model);

        list = new JList(new EventListModel<E>(getModel()));
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