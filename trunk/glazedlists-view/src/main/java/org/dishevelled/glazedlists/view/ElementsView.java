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

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
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

    /** Separator text between elements. */
    private String separatorText = DEFAULT_SEPARATOR_TEXT;

    /** Separator icon between elements. */
    private Icon separatorIcon = DEFAULT_SEPARATOR_ICON;

    /** More elements indicator text. */
    private String indicatorText = DEFAULT_INDICATOR_TEXT;

    /** More elements indicator icon. */
    private Icon indicatorIcon = DEFAULT_INDICATOR_ICON;

    /** Default number of elements to display, <code>3</code>. */
    public static final int DEFAULT_ELEMENTS_TO_DISPLAY = 3;

    /** Default separator text between elements, <code>, </code>. */
    public static final String DEFAULT_SEPARATOR_TEXT = ", ";

    /** Default separator icon between elements, <code>null</code>. */
    public static final Icon DEFAULT_SEPARATOR_ICON = null;

    /** Default more elements indicator text, <code>...</code>. */
    public static final String DEFAULT_INDICATOR_TEXT = "...";

    /** Default more elements indicator icon, <code>null</code>. */
    public static final Icon DEFAULT_INDICATOR_ICON = null;

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


    /**
     * Create a new elements view with the specified model.
     *
     * @param model model, must not be null
     */
    protected ElementsView(final EventList<E> model)
    {
        super(model);
        getModel().addListEventListener(listener);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    }

    /**
     * Create a new elements view with the specified model and model to view mapping.
     *
     * @param model model, must not be null
     * @param modelToView model to view mapping, must not be null
     */
    public ElementsView(final EventList<E> model, final UnaryFunction<E, ? extends JComponent> modelToView)
    {
        this(model);
        getModel().addListEventListener(listener);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setModelToView(modelToView);
    }


    /**
     * Set the model to view mapping for this elements view to <code>modelToView</code>.
     *
     * @param modelToView model to view mapping, must not be null
     */
    protected final void setModelToView(final UnaryFunction<E, ? extends JComponent> modelToView)
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

    public void setSeparatorText(final String separatorText)
    {
        String oldSeparatorText = this.separatorText;
        this.separatorText = separatorText;
        firePropertyChange("separatorText", oldSeparatorText, this.separatorText);
        updateComponents();
    }

    public void setSeparatorIcon(final Icon separatorIcon)
    {
        Icon oldSeparatorIcon = this.separatorIcon;
        this.separatorIcon = separatorIcon;
        firePropertyChange("separatorIcon", oldSeparatorIcon, this.separatorIcon);
        updateComponents();
    }

    public void setIndicatorText(final String indicatorText)
    {
        String oldIndicatorText = this.indicatorText;
        this.indicatorText = indicatorText;
        firePropertyChange("indicatorText", oldIndicatorText, this.indicatorText);
        updateComponents();
    }

    public void setIndicatorIcon(final Icon indicatorIcon)
    {
        Icon oldIndicatorIcon = this.indicatorIcon;
        this.indicatorIcon = indicatorIcon;
        firePropertyChange("indicatorIcon", oldIndicatorIcon, this.indicatorIcon);
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
                if (separatorText != null || separatorIcon != null)
                {
                    add(new JLabel(separatorText, separatorIcon, JLabel.CENTER));
                }
                add(modelToView.evaluate(getModel().get(i)));
            }
            if ((elementsToDisplay < getModel().size()) && ((indicatorText != null) || (indicatorIcon != null)))
            {
                add(new JLabel(indicatorText, indicatorIcon, JLabel.CENTER));
            }
        }
        add(Box.createGlue());
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