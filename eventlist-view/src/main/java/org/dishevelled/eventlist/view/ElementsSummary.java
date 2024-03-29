/*

    dsh-eventlist-view  Views for event lists.
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
package org.dishevelled.eventlist.view;

import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import ca.odell.glazedlists.EventList;

import ca.odell.glazedlists.event.ListEvent;
import ca.odell.glazedlists.event.ListEventListener;

import org.dishevelled.functor.UnaryFunction;

/**
 * Elements summary.
 *
 * @param <E> model element type
 * @author  Michael Heuer
 */
public class ElementsSummary<E>
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
            @Override
            public void listChanged(final ListEvent<E> event)
            {
                updateComponents();
            }
        };


    /**
     * Create a new elements summary with the specified model.
     *
     * @param model model, must not be null
     */
    protected ElementsSummary(final EventList<E> model)
    {
        super(model);
        getModel().addListEventListener(listener);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    }

    /**
     * Create a new elements summary with the specified model and model to view mapping.
     *
     * @param model model, must not be null
     * @param modelToView model to view mapping, must not be null
     */
    public ElementsSummary(final EventList<E> model, final UnaryFunction<E, ? extends JComponent> modelToView)
    {
        this(model);
        getModel().addListEventListener(listener);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setModelToView(modelToView);
    }


    /**
     * Set the model to view mapping for this elements summary to <code>modelToView</code>.
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

    /**
     * Return the number of elements to display.
     *
     * @return the number of elements to display
     */
    public final int getElementsToDisplay()
    {
        return elementsToDisplay;
    }

    /**
     * Set the number of elements to display to <code>elementsToDisplay</code>.  Defaults
     * to {@link #DEFAULT_ELEMENTS_TO_DISPLAY}.
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
        firePropertyChange("elementsToDisplay", oldElementsToDisplay, this.elementsToDisplay);
        updateComponents();
    }

    /**
     * Return the separator text between elements.
     *
     * @return the separator text between elements
     */
    public final String getSeparator()
    {
        return separatorText;
    }

    /**
     * Set the separator text between elements to <code>separatorText</code>.  Defaults to
     * {@link #DEFAULT_SEPARATOR_TEXT}.
     *
     * <p>This is a bound property.</p>
     *
     * @param separatorText separator text between elements
     */
    public final void setSeparatorText(final String separatorText)
    {
        String oldSeparatorText = this.separatorText;
        this.separatorText = separatorText;
        firePropertyChange("separatorText", oldSeparatorText, this.separatorText);
        updateComponents();
    }

    /**
     * Return the separator icon between elements.
     *
     * @return the separator icon between elements
     */
    public final Icon getSeparatorIcon()
    {
        return separatorIcon;
    }

    /**
     * Set the separator icon between elements to <code>separatorIcon</code>.  Defaults to
     * {@link #DEFAULT_SEPARATOR_ICON}.
     *
     * <p>This is a bound property.</p>
     *
     * @param separatorIcon separator icon between elements
     */
    public final void setSeparatorIcon(final Icon separatorIcon)
    {
        Icon oldSeparatorIcon = this.separatorIcon;
        this.separatorIcon = separatorIcon;
        firePropertyChange("separatorIcon", oldSeparatorIcon, this.separatorIcon);
        updateComponents();
    }

    /**
     * Return the more elements indicator text.
     *
     * @return the more elements indicator text
     */
    public final String getIndicatorText()
    {
        return indicatorText;
    }

    /**
     * Set the more elements indicator text to <code>indicatorText</code>.  Defaults to
     * {@link #DEFAULT_INDICATOR_TEXT}.
     *
     * <p>This is a bound property.</p>
     *
     * @param indicatorText more elements indicator text
     */
    public final void setIndicatorText(final String indicatorText)
    {
        String oldIndicatorText = this.indicatorText;
        this.indicatorText = indicatorText;
        firePropertyChange("indicatorText", oldIndicatorText, this.indicatorText);
        updateComponents();
    }

    /**
     * Return the more elements indicator icon.
     *
     * @return the more elements indicator icon
     */
    public final Icon getIndicatorIcon()
    {
        return indicatorIcon;
    }

    /**
     * Set the more elements indicator icon to <code>indicatorIcon</code>.  Defaults to
     * {@link #DEFAULT_INDICATOR_ICON}.
     *
     * <p>This is a bound property.</p>
     *
     * @param indicatorIcon more elements indicator icon
     */
    public final void setIndicatorIcon(final Icon indicatorIcon)
    {
        Icon oldIndicatorIcon = this.indicatorIcon;
        this.indicatorIcon = indicatorIcon;
        firePropertyChange("indicatorIcon", oldIndicatorIcon, this.indicatorIcon);
        updateComponents();
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
     * Update components.
     */
    protected final void updateComponents()
    {
        removeAll();
        if (!isEmpty())
        {
            add(modelToView.evaluate(getModel().get(0)));
            for (int i = 1, size = Math.min(elementsToDisplay, getModel().size()); i < size; i++)
            {
                if (separatorText != null || separatorIcon != null)
                {
                    add(new JLabel(separatorText, separatorIcon, SwingConstants.CENTER));
                }
                add(modelToView.evaluate(getModel().get(i)));
            }
            if ((elementsToDisplay < getModel().size()) && ((indicatorText != null) || (indicatorIcon != null)))
            {
                add(new JLabel(indicatorText, indicatorIcon, SwingConstants.CENTER));
            }
        }
        add(Box.createGlue());
    }

    /**
     * Remove listeners.
     */
    private void removeListeners()
    {
        getModel().removeListEventListener(listener);
    }

    @Override
    public void dispose()
    {
        super.dispose();
        removeListeners();
    }
}
