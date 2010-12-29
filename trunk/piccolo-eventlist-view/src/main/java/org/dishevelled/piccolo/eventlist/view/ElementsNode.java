/*

    dsh-piccolo-eventlist-view  Piccolo2D views for event lists.
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
package org.dishevelled.piccolo.eventlist.view;

import ca.odell.glazedlists.EventList;

import ca.odell.glazedlists.event.ListEvent;
import ca.odell.glazedlists.event.ListEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPopupMenu;

import javax.swing.border.EmptyBorder;

import org.dishevelled.functor.UnaryFunction;

import org.dishevelled.iconbundle.IconSize;

import org.dishevelled.iconbundle.tango.TangoProject;

import org.dishevelled.identify.ContextMenuListener;
import org.dishevelled.identify.IdPopupMenu;
import org.dishevelled.identify.IdToolBar;

import org.piccolo2d.PNode;

/**
 * Elements node.
 *
 * @param <E> model element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class ElementsNode<E>
    extends AbstractEventListNode<E>
{
    /** Tool bar. */
    private final IdToolBar toolBar;

    /** Context menu. */
    private final IdPopupMenu contextMenu;

    /** Tool bar context menu. */
    private final JPopupMenu toolBarContextMenu;

    /** Model to view mapping. */
    // todo:  use an element factory and synced lists?
    private UnaryFunction<E, ? extends PNode> modelToView;

    /** Listener. */
    private final ListEventListener<E> listener = new ListEventListener<E>()
        {
            /** @{inheritDoc} */
            public void listChanged(final ListEvent<E> event)
            {
                updateNodes();
            }
        };


    /**
     * Create a new elements node with the specified model.
     *
     * @param model model, must not be null
     */
    protected ElementsNode(final EventList<E> model)
    {
        super(model);
        getModel().addListEventListener(listener);

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
        //addMouseListener(new ContextMenuListener(contextMenu));

        toolBar = new IdToolBar();
        toolBar.setBorder(new EmptyBorder(2, 2, 2, 2));
        toolBar.add(getAddAction());
        toolBar.add(getRemoveAction());
        toolBar.add(contextMenu);

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
    }

    /**
     * Create a new elements node with the specified model and model to view mapping.
     *
     * @param model model, must not be null
     * @param modelToView model to view mapping, must not be null
     */
    public ElementsNode(final EventList<E> model, final UnaryFunction<E, ? extends PNode> modelToView)
    {
        this(model);
        getModel().addListEventListener(listener);
        setModelToView(modelToView);
    }


    /**
     * Set the model to view mapping for this elements node to <code>modelToView</code>.
     *
     * @param modelToView model to view mapping, must not be null
     */
    protected final void setModelToView(final UnaryFunction<E, ? extends PNode> modelToView)
    {
        if (modelToView == null)
        {
            throw new IllegalArgumentException("modelToView must not be null");
        }
        this.modelToView = modelToView;
        updateNodes();
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
     * Return the tool bar for this elements node.
     *
     * @return the tool bar for this elements node
     */
    public final IdToolBar getToolBar()
    {
        return toolBar;
    }

    /**
     * Return the context menu for this elements node.
     *
     * @return the context menu for this elements node
     */
    protected final IdPopupMenu getContextMenu()
    {
        return contextMenu;
    }

    /**
     * Return the tool bar context menu for this elements node.
     *
     * @return the tool bar context menu for this elements node
     */
    public final JPopupMenu getToolBarContextMenu()
    {
        return toolBarContextMenu;
    }

    /** {@inheritDoc} */
    protected void layoutChildren()
    {
        double y = 0;
        double padding = 2.0d;
        for (int i = 0, size = getChildrenCount(); i < size; i++)
        {
            PNode child = getChild(i);
            child.setOffset(child.getFullBoundsReference().getWidth() / 2.0d, y);
            y += child.getFullBoundsReference().getHeight();
            y += padding;
        }
    }

    /**
     * Update nodes.
     */
    private void updateNodes()
    {
        // todo:  fine grained updates
        removeAllChildren();
        if (!isEmpty())
        {
            if (modelToView == null)
            {
                throw new IllegalStateException("subclasses of AbstractEventListNode must call setModelToView method"
                                                + " after super(EventList<E>) constructor");
            }
            List<PNode> children = new ArrayList<PNode>(getModel().size());
            for (E element : getModel())
            {
                children.add(modelToView.evaluate(element));
            }
            addChildren(children);
        }
    }

    /**
     * Add listeners.
     */
    private void addListeners()
    {
        getModel().addListEventListener(listener);
    }

    /**
     * Remove listeners.
     */
    private void removeListeners()
    {
        getModel().removeListEventListener(listener);
    }

    /** {@inheritDoc} */
    public void dispose()
    {
        super.dispose();
        removeListeners();
    }
}