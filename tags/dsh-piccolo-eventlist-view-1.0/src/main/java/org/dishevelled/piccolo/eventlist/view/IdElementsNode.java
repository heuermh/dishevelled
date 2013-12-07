/*

    dsh-piccolo-eventlist-view  Piccolo2D views for event lists.
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
package org.dishevelled.piccolo.eventlist.view;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.ListSelection;

import org.dishevelled.functor.UnaryFunction;

import org.dishevelled.iconbundle.IconSize;

import org.dishevelled.iconbundle.tango.TangoProject;

import org.dishevelled.piccolo.identify.NautilusIdNode;

import org.piccolo2d.PNode;

import org.piccolo2d.event.PBasicInputEventHandler;
import org.piccolo2d.event.PInputEvent;

/**
 * Identifiable elements node.
 *
 * @param <E> model element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class IdElementsNode<E>
    extends ElementsNode<E>
{
    /** Icon size. */
    private IconSize iconSize = DEFAULT_ICON_SIZE;

    /** Default icon size. */
    public static final IconSize DEFAULT_ICON_SIZE = TangoProject.LARGE;

    /** Model to view mapping. */
    private final UnaryFunction<E, NautilusIdNode> modelToView = new UnaryFunction<E, NautilusIdNode>()
        {
            @Override
            public NautilusIdNode evaluate(final E element)
            {
                final NautilusIdNode idNode = new NautilusIdNode(element, iconSize);
                idNode.removeInputEventListener(idNode.getDragHandler());
                // todo:  will need to dispose this listener at some point
                idNode.addInputEventListener(new PBasicInputEventHandler()
                    {
                        @Override
                        public void mousePressed(final PInputEvent event) {
                            if (event.isLeftMouseButton()) {
                                int indexInParent = indexOfChild(idNode);
                                if (getSelectionModel().isSelected(indexInParent))
                                {
                                    if (!(event.isShiftDown()))
                                    {
                                        getSelectionModel().deselect(indexInParent);
                                    }
                                }
                                else
                                {
                                    if (!(event.isShiftDown()) && !(event.isControlDown()))
                                    {
                                        getSelectionModel().deselectAll();
                                    }
                                    getSelectionModel().select(indexInParent);
                                }
                            }
                        }
                    });
                return idNode;
            }
        };

    /** Selection listener. */
    private final ListSelection.Listener selectionListener = new ListSelection.Listener()
        {
            @Override
            public void selectionChanged(final int changeStart, final int changeEnd)
            {
                for (int i = changeStart; i < (changeEnd + 1); i++)
                {
                    PNode child = getChild(i);
                    if (child instanceof NautilusIdNode)
                    {
                        NautilusIdNode idNode = (NautilusIdNode) child;
                        if (getSelectionModel().isSelected(i))
                        {
                            idNode.select();
                        }
                        else
                        {
                            idNode.deselect();
                        }
                    }
                }
            }
        };


    /**
     * Create a new identifiable elements summary with the specified model.
     *
     * @param model model, must not be null
     */
    public IdElementsNode(final EventList<E> model)
    {
        super(model);
        setModelToView(modelToView);
        getSelectionModel().addSelectionListener(selectionListener);
    }


    // todo:  allow other id node impls
    /**
     * Return the icon size for this identifiable elements node.
     *
     * @return the icon size for this identifiable elements node
     */
    public IconSize getIconSize()
    {
        return iconSize;
    }

    /**
     * Set the icon size for this identifiable elements node to <code>iconSize</code>.  Defaults
     * to {@link #DEFAULT_ICON_SIZE}.
     *
     * <p>This is a bound property.</p>
     *
     * @param iconSize icon size for this identifiable elements node, must not be null
     */
    public void setIconSize(final IconSize iconSize)
    {
        IconSize oldIconSize = this.iconSize;
        this.iconSize = iconSize;
        updateIconSize();
        firePropertyChange(-1, "iconSize", oldIconSize, this.iconSize);
    }

    /**
     * Update icon size.
     */
    private void updateIconSize()
    {
        for (Object o : getChildrenReference())
        {
            if (o instanceof NautilusIdNode)
            {
                NautilusIdNode idNode = (NautilusIdNode) o;
                idNode.setIconSize(iconSize);
            }
        }
    }

    /**
     * Remove listeners.
     */
    private void removeListeners()
    {
        getSelectionModel().removeSelectionListener(selectionListener);
    }

    @Override
    public void dispose()
    {
        super.dispose();
        removeListeners();
    }
}