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

import ca.odell.glazedlists.event.ListEvent;
import ca.odell.glazedlists.event.ListEventListener;

import java.awt.Image;

import java.util.List;

import org.dishevelled.functor.UnaryFunction;

import org.piccolo2d.PNode;

import org.piccolo2d.nodes.PImage;
import org.piccolo2d.nodes.PText;

/**
 * Elements summary node.
 *
 * @param <E> model element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class ElementsSummaryNode<E>
    extends AbstractEventListNode<E>
{
    /** Number of elements to display. */
    private int elementsToDisplay = DEFAULT_ELEMENTS_TO_DISPLAY;

    /** Separator text between elements. */
    private String separatorText = DEFAULT_SEPARATOR_TEXT;

    /** Separator image between elements. */
    private Image separatorImage = DEFAULT_SEPARATOR_IMAGE;

    /** More elements indicator text. */
    private String indicatorText = DEFAULT_INDICATOR_TEXT;

    /** More elements indicator image. */
    private Image indicatorImage = DEFAULT_INDICATOR_IMAGE;

    /** Default number of elements to display, <code>3</code>. */
    public static final int DEFAULT_ELEMENTS_TO_DISPLAY = 3;

    /** Default separator text between elements, <code>, </code>. */
    public static final String DEFAULT_SEPARATOR_TEXT = ", ";

    /** Default separator image between elements, <code>null</code>. */
    public static final Image DEFAULT_SEPARATOR_IMAGE = null;

    /** Default more elements indicator text, <code>...</code>. */
    public static final String DEFAULT_INDICATOR_TEXT = "...";

    /** Default more elements indicator image, <code>null</code>. */
    public static final Image DEFAULT_INDICATOR_IMAGE = null;

    /** Model to view mapping. */
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
     * Create a new elements summary node with the specified model.
     *
     * @param model model, must not be null
     */
    protected ElementsSummaryNode(final EventList<E> model)
    {
        super(model);
        addListeners();
    }

    /**
     * Create a new elements summary node with the specified model and model to view mapping.
     *
     * @param model model, must not be null
     * @param modelToView model to view mapping, must not be null
     */
    public ElementsSummaryNode(final EventList<E> model, final UnaryFunction<E, ? extends PNode> modelToView)
    {
        this(model);
        setModelToView(modelToView);
    }


    /**
     * Set the model to view mapping for this elements summary node to <code>modelToView</code>.
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

    /**
     * Set the number of elements to display to <code>elementsToDisplay</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param elementsToDisplay the number of elements to display, must be at least zero
     */
    public final void setElementsToDisplay(final int elementsToDisplay)
    {
        if (elementsToDisplay < 0)
        {
            throw new IllegalArgumentException("elementsToDisplay must be at least zero");
        }
        int oldElementsToDisplay = this.elementsToDisplay;
        this.elementsToDisplay = elementsToDisplay;
        firePropertyChange(-1, "elementsToDisplay", oldElementsToDisplay, this.elementsToDisplay);
        updateNodes();
    }

    /**
     * Set the separator text between elements to <code>separatorText</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param separatorText separator text between elements
     */
    public final void setSeparatorText(final String separatorText)
    {
        String oldSeparatorText = this.separatorText;
        this.separatorText = separatorText;
        firePropertyChange(-1, "separatorText", oldSeparatorText, this.separatorText);
        updateNodes();
    }

    /**
     * Set the separator image between elements to <code>separatorImage</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param separatorImage separator image between elements
     */
    public final void setSeparatorImage(final Image separatorImage)
    {
        Image oldSeparatorImage = this.separatorImage;
        this.separatorImage = separatorImage;
        firePropertyChange(-1, "separatorImage", oldSeparatorImage, this.separatorImage);
        updateNodes();
    }

    /**
     * Set the more elements indicator text to <code>indicatorText</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param indicatorText more elements indicator text
     */
    public final void setIndicatorText(final String indicatorText)
    {
        String oldIndicatorText = this.indicatorText;
        this.indicatorText = indicatorText;
        firePropertyChange(-1, "indicatorText", oldIndicatorText, this.indicatorText);
        updateNodes();
    }

    /**
     * Set the more elements indicator image to <code>indicatorImage</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param indicatorImage more elements indicator image
     */
    public final void setIndicatorImage(final Image indicatorImage)
    {
        Image oldIndicatorImage = this.indicatorImage;
        this.indicatorImage = indicatorImage;
        firePropertyChange(-1, "indicatorImage", oldIndicatorImage, this.indicatorImage);
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

    /** {@inheritDoc} */
    protected void layoutChildren()
    {
        double x = 0;
        double padding = 2.0d;
        for (int i = 0, size = getChildrenCount(); i < size; i++)
        {
            PNode child = getChild(i);
            child.setOffset(x, child.getFullBoundsReference().getHeight() / 2.0d);
            x += child.getFullBoundsReference().getWidth();
            x += padding;
        }
    }

    /**
     * Update nodes.
     */
    private void updateNodes()
    {
        removeAllChildren();
        if (!isEmpty())
        {
            if (modelToView == null)
            {
                throw new IllegalStateException("subclasses of AbstractEventListNode must call setModelToView method"
                                                + " after super(EventList<E>) constructor");
            }
            addChild(modelToView.evaluate(getModel().get(0)));
            for (int i = 1, size = Math.min(elementsToDisplay, getModel().size()); i < size; i++)
            {
                if (separatorText != null)
                {
                    addChild(new PText(separatorText));
                }
                if (separatorImage != null)
                {
                    addChild(new PImage(separatorImage));
                }
                addChild(modelToView.evaluate(getModel().get(i)));
            }
            if (elementsToDisplay < getModel().size())
            {
                if (indicatorText != null)
                {
                    addChild(new PText(indicatorText));
                }
                if (indicatorImage != null)
                {
                    addChild(new PImage(indicatorImage));
                }
            }
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