/*

    dsh-glazedlists-view  Views for event lists.
    Copyright (c) 2010 held jointly by the individual authors.

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
package org.dishevelled.glazedlists.view;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.ListSelection;

import ca.odell.glazedlists.event.ListEvent;
import ca.odell.glazedlists.event.ListEventListener;

import java.awt.event.ActionEvent;

import java.util.Collection;

import javax.swing.AbstractAction;
import javax.swing.JPanel;

import org.dishevelled.identify.IdentifiableAction;

import org.dishevelled.iconbundle.tango.TangoProject;

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

    /** Select all action. */
    private final IdentifiableAction selectAllAction = new IdentifiableAction("Select all", TangoProject.EDIT_SELECT_ALL)
        {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent event)
                {
                    selectAll();
                }
        };

    /** Clear selection action. */
    private final AbstractAction clearSelectionAction = new AbstractAction("Clear selection")
        {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent event)
                {
                    clearSelection();
                }
        };

    /** Invert selection action. */
    private final AbstractAction invertSelectionAction = new AbstractAction("Invert selection")
        {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent event)
                {
                    invertSelection();
                }
        };

    /** Cut action. */
    private final IdentifiableAction cutAction = new IdentifiableAction("Cut", TangoProject.EDIT_CUT)
        {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent event)
                {
                    cut();
                }
        };

    /** Copy action. */
    private final IdentifiableAction copyAction = new IdentifiableAction("Copy", TangoProject.EDIT_COPY)
        {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent event)
                {
                    copy();
                }
        };

    /** Paste action. */
    private final IdentifiableAction pasteAction = new IdentifiableAction("Paste", TangoProject.EDIT_PASTE)
        {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent event)
                {
                    paste();
                }
        };

    /** Add action. */
    private final IdentifiableAction addAction = new IdentifiableAction("Add", TangoProject.LIST_ADD)
        {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent event)
                {
                    add();
                }
        };

    /** Remove action. */
    private final IdentifiableAction removeAction = new IdentifiableAction("Remove", TangoProject.LIST_REMOVE)
        {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent event)
                {
                    remove();
                }
        };

    /** Remove all action. */
    private final AbstractAction removeAllAction = new AbstractAction("Remove all")
        {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent event)
                {
                    clear();
                }
        };

    /** Listener. */
    private ListEventListener<E> listener = new ListEventListener<E>()
        {
            /** {@inheritDoc} */
            public void listChanged(final ListEvent<E> event)
            {
                updateListActions();
            }
        };

    /** Selection listener. */
    private ListSelection.Listener selectionListener = new ListSelection.Listener()
        {
            /** {@inheritDoc} */
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
        eventListViewSupport = new EventListViewSupport<E>(model);
        getModel().addListEventListener(listener);
        getSelectionModel().addSelectionListener(selectionListener);
        updateListActions();
        updateSelectionActions();
    }


    private void updateListActions()
    {
        selectAllAction.setEnabled(!isEmpty());
        invertSelectionAction.setEnabled(!isEmpty());
        removeAllAction.setEnabled(!isEmpty());
    }

    private void updateSelectionActions()
    {
        copyAction.setEnabled(!isSelectionEmpty());
        cutAction.setEnabled(!isSelectionEmpty());
        removeAction.setEnabled(!isSelectionEmpty());
        clearSelectionAction.setEnabled(!isSelectionEmpty());
    }

    public final boolean isEmpty()
    {
        return getModel().isEmpty();
    }

    public final boolean isSelectionEmpty()
    {
        return getSelectionModel().getSelected().isEmpty();
    }

    public final void selectAll()
    {
        getSelectionModel().selectAll();
    }

    public final void clearSelection()
    {
        getSelectionModel().deselectAll();
    }

    public final void invertSelection()
    {
        getSelectionModel().invertSelection();
    }

    public final void cut()
    {
    }

    public final void copy()
    {
    }

    public final void paste()
    {
    }

    protected abstract void add();

    public final void remove()
    {
        getSelectionModel().getSelected().clear();
    }

    public final void clear() // removeAll
    {
        getModel().clear();
    }

    /*

      conflict with JPanel methods

    public final void add(final E e)
    {
        getModel().add(e);
    }

    public final void addAll(final Collection<? extends E> e)
    {
        getModel().addAll(e);
    }

    public final void removeAll()
    {
        getModel().clear();
    }
    */

    protected final IdentifiableAction getSelectAllAction()
    {
        return selectAllAction;
    }
    protected final AbstractAction getClearSelectionAction()
    {
        return clearSelectionAction;
    }
    protected final AbstractAction getInvertSelectionAction()
    {
        return invertSelectionAction;
    }
    protected final IdentifiableAction getCutAction()
    {
        return cutAction;
    }
    protected final IdentifiableAction getCopyAction()
    {
        return copyAction;
    }
    protected final IdentifiableAction getPasteAction()
    {
        return pasteAction;
    }
    protected final IdentifiableAction getAddAction()
    {
        return addAction;
    }
    protected final IdentifiableAction getRemoveAction()
    {
        return removeAction;
    }
    protected final AbstractAction getRemoveAllAction()
    {
        return removeAllAction;
    }

    /** {@inheritDoc} */
    public final EventList<E> getModel()
    {
        return eventListViewSupport.getModel();
    }

    /** {@inheritDoc} */
    public final ListSelection<E> getSelectionModel()
    {
        return eventListViewSupport.getSelectionModel();
    }

    public void dispose()
    {
        getModel().removeListEventListener(listener);
        getSelectionModel().removeSelectionListener(selectionListener);
    }
}
