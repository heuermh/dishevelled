/*

    dsh-piccolo-eventlist-view  Piccolo2D views for event lists.
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
package org.dishevelled.piccolo.eventlist.view;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.ListSelection;

import ca.odell.glazedlists.event.ListEvent;
import ca.odell.glazedlists.event.ListEventListener;

import java.awt.event.ActionEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;

import org.dishevelled.eventlist.view.EventListView;
import org.dishevelled.eventlist.view.EventListViewSupport;

import org.dishevelled.identify.IdentifiableAction;

import static org.dishevelled.iconbundle.tango.TangoProject.*;

import org.piccolo2d.PNode;

/**
 * Abstract event list node.
 *
 * @param <E> model element type
 * @author  Michael Heuer
 */
public abstract class AbstractEventListNode<E>
    extends PNode
    implements EventListView<E>
{
    /** I18n. */
    private static final ResourceBundle I18N = ResourceBundle.getBundle("dsh-piccolo-eventlist-view", Locale.getDefault());

    /** Event list view support. */
    private final EventListViewSupport<E> eventListViewSupport;

    /** Select all action. */
    private final IdentifiableAction selectAllAction = new IdentifiableAction(I18N.getString("AbstractEventListNode.selectAll"), EDIT_SELECT_ALL)
        {
                @Override
                public void actionPerformed(final ActionEvent event)
                {
                    selectAll();
                }
        };

    /** Clear selection action. */
    private final AbstractAction clearSelectionAction = new AbstractAction(I18N.getString("AbstractEventListNode.clearSelection"))
        {
                @Override
                public void actionPerformed(final ActionEvent event)
                {
                    clearSelection();
                }
        };

    /** Invert selection action. */
    private final AbstractAction invertSelectionAction = new AbstractAction(I18N.getString("AbstractEventListNode.invertSelection"))
        {
                @Override
                public void actionPerformed(final ActionEvent event)
                {
                    invertSelection();
                }
        };

    /** Cut action. */
    private final IdentifiableAction cutAction = new IdentifiableAction(I18N.getString("AbstractEventListNode.cut"), EDIT_CUT)
        {
                @Override
                public void actionPerformed(final ActionEvent event)
                {
                    cut();
                }
        };

    /** Copy action. */
    private final IdentifiableAction copyAction = new IdentifiableAction(I18N.getString("AbstractEventListNode.copy"), EDIT_COPY)
        {
                @Override
                public void actionPerformed(final ActionEvent event)
                {
                    copy();
                }
        };

    /** Paste action. */
    private final IdentifiableAction pasteAction = new IdentifiableAction(I18N.getString("AbstractEventListNode.paste"), EDIT_PASTE)
        {
                @Override
                public void actionPerformed(final ActionEvent event)
                {
                    paste();
                }
        };

    /** Add action. */
    private final IdentifiableAction addAction = new IdentifiableAction(I18N.getString("AbstractEventListNode.add"), LIST_ADD)
        {
                @Override
                public void actionPerformed(final ActionEvent event)
                {
                    add();
                }
        };

    /** Remove action. */
    private final IdentifiableAction removeAction = new IdentifiableAction(I18N.getString("AbstractEventListNode.remove"), LIST_REMOVE)
        {
                @Override
                public void actionPerformed(final ActionEvent event)
                {
                    remove();
                }
        };

    /** Remove all action. */
    private final AbstractAction removeAllAction = new AbstractAction(I18N.getString("AbstractEventListNode.removeAll"))
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
     * Create a new abstract event list node with the specified model.
     *
     * @param model model, must not be null
     */
    protected AbstractEventListNode(final EventList<E> model)
    {
        super();
        eventListViewSupport = new EventListViewSupport<E>(model);
        getModel().addListEventListener(listener);
        getSelectionModel().addSelectionListener(selectionListener);
        updateListActions();
        updateSelectionActions();
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
    }
}
