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

import ca.odell.glazedlists.event.ListEvent;
import ca.odell.glazedlists.event.ListEventListener;

import java.awt.FlowLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;

import org.dishevelled.functor.UnaryFunction;

/**
 * Elements view.
 *
 * @param <E> model element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class ElementsView<E>
    extends AbstractEventListView<E>
{
    /** Number of elements to display. */
    private int elementsToDisplay = DEFAULT_ELEMENTS_TO_DISPLAY;

    /** Separator between elements. */
    private JComponent separator = DEFAULT_SEPARATOR;

    /** More elements indicator. */
    private JComponent indicator = DEFAULT_INDICATOR;

    /** Default number of elements to display, <code>3</code>. */
    public static final int DEFAULT_ELEMENTS_TO_DISPLAY = 3;

    /** Default separator between elements, a label <code>, </code>. */
    public static final JComponent DEFAULT_SEPARATOR = new JLabel(", ");

    /** Default more elements indicator, a label <code>...</code>. */
    public static final JComponent DEFAULT_INDICATOR = new JLabel("...");

    /** Model to view mapping. */
    private UnaryFunction<E, ? extends JComponent> modelToView;

    /** Listener. */
    private final ListEventListener<E> listener = new ListEventListener<E>()
        {
            /** @{inheritDoc} */
            public void listChanged(final ListEvent<E> event)
            {
                updateComponents();
            }
        };

    protected ElementsView(final EventList<E> model)
    {
        super(model);
    }

    /**
     * Create a new elements view with the specified model.
     *
     * @param model model, must not be null
     * @param modelToView model to view mapping, must not be null
     */
    public ElementsView(final EventList<E> model, final UnaryFunction<E, ? extends JComponent> modelToView)
    {
        this(model);
        setModelToView(modelToView);
    }


    protected void setModelToView(final UnaryFunction<E, ? extends JComponent> modelToView)
    {
        if (modelToView == null)
        {
            throw new IllegalArgumentException("modelToView must not be null");
        }
        this.modelToView = modelToView;
        updateComponents();
    }

    public void setElementsToDisplay(final int elementsToDisplay)
    {
        if (elementsToDisplay < 0)
        {
            throw new IllegalArgumentException("elementsToDisplay must be at least zero");
        }
        int oldElementsToDisplay = this.elementsToDisplay;
        this.elementsToDisplay = elementsToDisplay;
        firePropertyChange("elementsToDisplay", oldElementsToDisplay, this.elementsToDisplay);
        updateComponents();
    }

    public void setSeparator(final JComponent separator)
    {
        JComponent oldSeparator = this.separator;
        this.separator = separator;
        firePropertyChange("separator", oldSeparator, this.separator);
        updateComponents();
    }

    public void setIndicator(final JComponent indicator)
    {
        JComponent oldIndicator = this.indicator;
        this.indicator = indicator;
        firePropertyChange("indicator", oldIndicator, this.indicator);
        updateComponents();
    }


    /** {@inheritDoc} */
    protected void add()
    {
        // empty
    }

    /**
     * Update components.
     */
    private void updateComponents()
    {
        removeAll();
        if (!isEmpty())
        {
            add(modelToView.evaluate(getModel().get(0)));
            for (int i = 1, size = Math.min(elementsToDisplay, getModel().size()); i < size; i++)
            {
                if (separator != null)
                {
                    add(separator);
                }
                add(modelToView.evaluate(getModel().get(i)));
            }
            if ((elementsToDisplay < getModel().size()) && (indicator != null))
            {
                add(indicator);
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

    /**
     * Release the resources consumed by this elements label so that it may eventually be garbage collected.
     */
    public void dispose()
    {
        removeListeners();
    }
}