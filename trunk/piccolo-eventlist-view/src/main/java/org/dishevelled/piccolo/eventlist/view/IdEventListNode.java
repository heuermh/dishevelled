/*

    dsh-piccolo-eventlist-view  Piccolo2D views for event lists.
    Copyright (c) 2010-2012 held jointly by the individual authors.

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

import java.util.List;

import javax.swing.JPopupMenu;

import org.dishevelled.identify.IdPopupMenu;
import org.dishevelled.identify.IdToolBar;

import org.piccolo2d.PNode;

import org.piccolo2d.util.PPaintContext;

/**
 * Identifiable event list node that supports semantic zooming.
 *
 * @param <E> model element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class IdEventListNode<E>
    extends AbstractEventListNode<E>
{
    /** Count label node. */
    private final CountLabelNode<E> countLabel;

    /** Identifiable elements summary node. */
    private final IdElementsSummaryNode<E> elementsSummary;

    /** Identifiable elements node. */
    private final IdElementsNode<E> elements;

    /** Scale threshold for count --&gt; elements summary transition. */
    private double countThreshold = DEFAULT_COUNT_THRESHOLD;

    /** Scale threshold for elements summary --&gt; elements transition. */
    private double summaryThreshold = DEFAULT_SUMMARY_THRESHOLD;

    /** Default count scale threshold, <code>0.7d</code>. */
    public static final double DEFAULT_COUNT_THRESHOLD = 0.7d;

    /** Default summary scale threshold, <code>0.9d</code>. */
    public static final double DEFAULT_SUMMARY_THRESHOLD = 0.9d;


    /**
     * Create a new identifiable event list node with the specified model.
     *
     * @param model model, must not be null
     */
    public IdEventListNode(final EventList<E> model)
    {
        super(model);
        countLabel = new CountLabelNode<E>(model);
        elementsSummary = new IdElementsSummaryNode<E>(model);
        elements = new IdElementsNode<E>(model);
        addChild(countLabel);
    }


    /**
     * Set the count threshold to <code>countThreshold</code>.  If the scale for the
     * camera viewing this identifiable event list node is less than the specified count threshold, this
     * node will be painted as a count label node. Defaults to {@link #DEFAULT_COUNT_THRESHOLD}.
     *
     * <p>This is a bound property.</p>
     *
     * @param countThreshold count threshold
     */
    public final void setCountThreshold(final double countThreshold)
    {
        double oldCountThreshold = this.countThreshold;
        this.countThreshold = countThreshold;
        firePropertyChange(-1, "countThreshold", oldCountThreshold, this.countThreshold);
        repaint();
    }

    /**
     * Set the summary threshold to <code>summaryThreshold</code>.  If the scale for the
     * camera viewing this identifiable event list node is greater than the count threshold and less than the
     * specified summary threshold, this node will be painted as an identifiable elements summary node.
     * Defaults to {@link #DEFAULT_SUMMARY_THRESHOLD}.
     *
     * <p>This is a bound property.</p>
     *
     * @see #setCountThreshold(double)
     * @param summaryThreshold summary threshold
     */
    public final void setSummaryThreshold(final double summaryThreshold)
    {
        double oldSummaryThreshold = this.summaryThreshold;
        this.summaryThreshold = summaryThreshold;
        firePropertyChange(-1, "summaryThreshold", oldSummaryThreshold, this.summaryThreshold);
        repaint();
    }

    // todo:  delegate or remove?
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
     * Add the specified child node if it not already a child of this node.
     *
     * @param child child node to add
     */
    private void addIfMissing(final PNode child)
    {
        // or getChildrenReference().contains()?
        if (indexOfChild(child) == -1)
        {
            addChild(child);
        }
    }

    /**
     * Remove the specified child node if it is a child of this node.
     *
     * @param child child node to remove
     */
    private void removeIfPresent(final PNode child)
    {
        if (indexOfChild(child) > -1)
        {
            removeChild(child);
        }
    }

    /** {@inheritDoc} */
    protected final void paint(final PPaintContext context)
    {
        if (context.getScale() < countThreshold)
        {
            addIfMissing(countLabel);
            removeIfPresent(elementsSummary);
            removeIfPresent(elements);
        }
        else if (context.getScale() < summaryThreshold)
        {
            removeIfPresent(countLabel);
            addIfMissing(elementsSummary);
            removeIfPresent(elements);
        }
        else
        {
            removeIfPresent(countLabel);
            removeIfPresent(elementsSummary);
            addIfMissing(elements);
        }
        super.paint(context);
    }

    /**
     * Return the tool bar for this elements node.
     *
     * @return the tool bar for this elements node
     */
    public final IdToolBar getToolBar()
    {
        return elements.getToolBar();
    }

    /**
     * Return the context menu for this elements node.
     *
     * @return the context menu for this elements node
     */
    public final IdPopupMenu getContextMenu()
    {
        // todo:  add add'l actions?
        return elements.getContextMenu();
    }

    /**
     * Return the tool bar context menu for this elements node.
     *
     * @return the tool bar context menu for this elements node
     */
    public final JPopupMenu getToolBarContextMenu()
    {
        return elements.getToolBarContextMenu();
    }

    /** {@inheritDoc} */
    public void dispose()
    {
        super.dispose();
        countLabel.dispose();
        elementsSummary.dispose();
        elements.dispose();
    }
}
