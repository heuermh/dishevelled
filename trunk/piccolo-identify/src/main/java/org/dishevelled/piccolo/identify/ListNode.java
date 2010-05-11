/*

    dsh-piccolo-identify  Piccolo2D nodes for identifiable beans.
    Copyright (c) 2007-2010 held jointly by the individual authors.

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
package org.dishevelled.piccolo.identify;

import java.awt.Color;

import java.util.ArrayList;
import java.util.Iterator;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.ListSelection;

import ca.odell.glazedlists.event.ListEvent;
import ca.odell.glazedlists.event.ListEventListener;

import org.piccolo2d.PNode;

import org.piccolo2d.event.PInputEvent;
import org.piccolo2d.event.PBasicInputEventHandler;

import org.piccolo2d.nodes.PText;

/**
 * List node.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class ListNode<E>
    extends PNode
    implements ListView<E, PNode>
{
    /** Model. */
    private EventList<E> model;

    /** Indexed view &lt;--&gt; model mapping. */
    private IndexedViewToModel<E, PNode> viewToModel;

    /** Indexed model &lt;--&gt; view mapping. */
    private IndexedModelToView<E, PNode> modelToView;

    /** Selection model. */
    private ListSelection<E> selectionModel;

    /** View decorator. */
    private ViewDecorator<PNode> viewDecorator;


    /**
     * Create a new list node with reasonable defaults.
     */
    public ListNode()
    {
        super();
        this.model = GlazedLists.eventList(new ArrayList<E>());
        PTextViews views = new PTextViews();
        this.viewToModel = views;
        this.modelToView = views;
        this.selectionModel = new ListSelection<E>(this.model);
        this.viewDecorator = new PTextViewDecorator();

        this.model.addListEventListener(new ListEventListener<E>()
            {
                /** {@inheritDoc} */
                public void listChanged(final ListEvent<E> event)
                {
                    while (event.next())
                    {
                        if (ListEvent.INSERT == event.getType())
                        {
                            int index = event.getIndex();
                            // todo  should probably distinguish between different event types
                            modelToView.get(index);
                        }
                    }
                }
            });
    }



    /** {@inheritDoc} */
    protected void layoutChildren()
    {
        double y = 0.0d;
        for (Iterator children = getChildrenIterator(); children.hasNext(); )
        {
            PNode child = (PNode) children.next();
            child.setOffset(0.0d, y);
            y += child.getHeight();
            y += 12.0d;
        }
    }

    /** {@inheritDoc} */
    public EventList<E> getModel()
    {
        return model;
    }

    /** {@inheritDoc} */
    public void setModel(final EventList<E> model)
    {
        this.model = model;
        // fire property change
    }

    /** {@inheritDoc} */
    public IndexedViewToModel<E, PNode> getViewToModel()
    {
        return viewToModel;
    }

    /** {@inheritDoc} */
    public void setViewToModel(IndexedViewToModel<E, PNode> viewToModel)
    {
        this.viewToModel = viewToModel;
        // fire property change
    }

    /** {@inheritDoc} */
    public IndexedModelToView<E, PNode> getModelToView()
    {
        return modelToView;
    }

    /** {@inheritDoc} */
    public void setModelToView(IndexedModelToView<E, PNode> modelToView)
    {
        this.modelToView = modelToView;
        // fire property change
    }

    /** {@inheritDoc} */
    public ListSelection<E> getSelectionModel()
    {
        return selectionModel;
    }

    /** {@inheritDoc} */
    public void setSelectionModel(ListSelection<E> selectionModel)
    {
        this.selectionModel = selectionModel;
        // fire property change
    }

    /** {@inheritDoc} */
    public ViewDecorator<PNode> getViewDecorator()
    {
        return viewDecorator;
    }

    /** {@inheritDoc} */
    public void setViewDecorator(ViewDecorator<PNode> viewDecorator)
    {
        this.viewDecorator = viewDecorator;
    }

    /**
     * PText views.
     */
    private final class PTextViews
        implements IndexedModelToView<E, PNode>, IndexedViewToModel<E, PNode>
    {

        /** {@inheritDoc} */
        public int get(final PNode view)
        {
            return indexOfChild(view);
        }

        /** {@inheritDoc} */
        public PNode get(final int index)
        {
            E e = model.get(index);
            if (index < getChildrenCount())
            {
                return getChild(index);
            }
            final PText text = new PText(e.toString());
            text.addInputEventListener(new PBasicInputEventHandler()
                {
                    /** {@inheritDoc} */
                    public void mouseEntered(final PInputEvent event)
                    {
                        viewDecorator.mouseEntered(text);
                    }

                    /** {@inheritDoc} */
                    public void mouseExited(final PInputEvent event)
                    {
                        viewDecorator.mouseExited(text);
                    }
                });

            addChild(index, text);
            return text;
        }
    }

    /**
     * PText view decorator.
     */
    // todo  can this be <PText>?
    private class PTextViewDecorator implements ViewDecorator<PNode>
    {
        /** {@inheritDoc} */
        public void enable(final PNode node)
        {
            // empty
        }

        /** {@inheritDoc} */
        public void disable(final PNode node)
        {
            // empty
        }

        /** {@inheritDoc} */
        public void mousePressed(final PNode node)
        {
            // empty
        }

        /** {@inheritDoc} */
        public void mouseReleased(final PNode node)
        {
            // empty
        }

        /** {@inheritDoc} */
        public void mouseEntered(final PNode node)
        {
            ((PText) node).setTextPaint(Color.RED);
        }

        /** {@inheritDoc} */
        public void mouseExited(final PNode node)
        {
            ((PText) node).setTextPaint(Color.BLACK);
        }

        /** {@inheritDoc} */
        public void startDrag(final PNode node)
        {
            // empty
        }

        /** {@inheritDoc} */
        public void endDrag(final PNode node)
        {
            // empty
        }

        /** {@inheritDoc} */
        public void select(final PNode node)
        {
            // empty
        }

        /** {@inheritDoc} */
        public void deselect(final PNode node)
        {
            // empty
        }

        /** {@inheritDoc} */
        public void gainFocus(final PNode node)
        {
            // empty
        }

        /** {@inheritDoc} */
        public void loseFocus(final PNode node)
        {
            // empty
        }
    }
}