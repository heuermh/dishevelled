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

import java.util.List;

import ca.odell.glazedlists.EventList;

import ca.odell.glazedlists.event.ListEvent;
import ca.odell.glazedlists.event.ListEventListener;

import org.piccolo2d.nodes.PText;

/**
 * Elements label node.
 *
 * @param <E> event list type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class ElementsLabelNode<E>
    extends AbstractEventListNode<E>
{
    /** Elements label. */
    private final PText elements;

    /** Number of elements to display. */
    private int elementsToDisplay = DEFAULT_ELEMENTS_TO_DISPLAY;

    /** Separator between elements. */
    private String separator = DEFAULT_SEPARATOR;

    /** More elements indicator. */
    private String indicator = DEFAULT_INDICATOR;

    /** Default number of elements to display, <code>3</code>. */
    public static final int DEFAULT_ELEMENTS_TO_DISPLAY = 3;

    /** Default separator between elements, <code>, </code>. */
    public static final String DEFAULT_SEPARATOR = ", ";

    /** Default more elements indicator, <code>...</code>. */
    public static final String DEFAULT_INDICATOR = "...";

    /** Listener. */
    private final ListEventListener<E> listener = new ListEventListener<E>()
        {
            /** @{inheritDoc} */
            public void listChanged(final ListEvent<E> event)
            {
                updateLabelText();
            }
        };


    /**
     * Create a new elements label node with the specified model.
     *
     * @param model model, must not be null
     */
    public ElementsLabelNode(final EventList<E> model)
    {
        super(model);
        elements = new PText();
        updateLabelText();
        addListeners();
        addChild(elements);
    }



    public PText getLabel()
    {
        return elements;
    }

    public void setElementsToDisplay(final int elementsToDisplay)
    {
        if (elementsToDisplay < 0)
        {
            throw new IllegalArgumentException("elementsToDisplay must be at least zero");
        }
        int oldElementsToDisplay = this.elementsToDisplay;
        this.elementsToDisplay = elementsToDisplay;
        firePropertyChange(-1, "elementsToDisplay", oldElementsToDisplay, this.elementsToDisplay);
        updateLabelText();
    }

    public void setSeparator(final String separator)
    {
        String oldSeparator = this.separator;
        this.separator = separator;
        firePropertyChange(-1, "separator", oldSeparator, this.separator);
        updateLabelText();
    }

    public void setIndicator(final String indicator)
    {
        String oldIndicator = this.indicator;
        this.indicator = indicator;
        firePropertyChange(-1, "indicator", oldIndicator, this.indicator);
        updateLabelText();
    }

    // fire label text property changes?


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
     * Update label text.
     */
    private void updateLabelText()
    {
        StringBuilder sb = new StringBuilder();
        if (!isEmpty())
        {
            sb.append(getModel().get(0));
            for (int i = 1, size = Math.min(elementsToDisplay, getModel().size()); i < size; i++)
            {
                if (separator != null)
                {
                    sb.append(separator);
                }
                sb.append(getModel().get(i));
            }
            if ((elementsToDisplay < getModel().size()) && (indicator != null))
            {
                sb.append(indicator);
            }
        }
        elements.setText(sb.toString());
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

    /**
     * Release the resources consumed by this elements label node
     * so that it may eventually be garbage collected.
     */
    public void dispose()
    {
        super.dispose();
        removeListeners();
    }
}