/*

    dsh-piccolo-venn  Piccolo2D venn diagram nodes and supporting classes.
    Copyright (c) 2009-2019 held jointly by the individual authors.

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
package org.dishevelled.piccolo.venn;

import java.util.Set;

import org.piccolo2d.PNode;

import org.piccolo2d.nodes.PText;

/**
 * Abstract venn diagram node.
 *
 * @param <E> value type
 * @author  Michael Heuer
 */
public abstract class AbstractVennNode<E>
    extends PNode
{
    /** True if should display labels. */
    private boolean displayLabels = true;

    /** True if labels should display sizes. */
    private boolean displaySizes = true;

    /** True if should display size labels. */
    private boolean displaySizeLabels = true;

    /** True if should display size labels for empty areas. */
    private boolean displaySizesForEmptyAreas = true;


    /**
     * Create a new abstract venn diagram node.
     */
    protected AbstractVennNode()
    {
        super();
    }


    /**
     * Build and return label text.
     *
     * @param labelText label text
     * @param size size
     * @return label text
     */
    protected final String buildLabel(final String labelText, final int size)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(labelText);
        if (displaySizes)
        {
            sb.append(" (");
            sb.append(size);
            sb.append(")");
        }
        sb.append(":");
        return sb.toString();
    }

    /**
     * Update labels.
     */
    protected abstract void updateLabels();

    /**
     * Return the nodes for this venn diagram node.
     *
     * @return the nodes for this venn diagram node
     */
    public abstract Iterable<PNode> nodes();

    /**
     * Return the labels for this venn diagram node.
     *
     * @return the labels for this venn diagram node
     */
    public abstract Iterable<PText> labels();

    /**
     * Return the size labels for this venn diagram node.
     *
     * @return the size labels for this venn diagram node
     */
    public abstract Iterable<PText> sizeLabels();

    /**
     * Return the label for the specified node, if any.
     *
     * @param node node
     * @return the label for the specified node, or <code>null</code>
     *    if no such label exists
     */
    public abstract PText labelForNode(PNode node);

    /**
     * Return the label text for the specified node, if any.
     *
     * @param node node
     * @return the label text for the specified node, or <code>null</code>
     *    if no such label exists
     */
    public abstract String labelTextForNode(PNode node);

    /**
     * Return the view for the specified node, if any.
     *
     * @param node node
     * @return the view for the specified node, or <code>null</code>
     *    if no such view exists
     */
    public abstract Set<E> viewForNode(PNode node);

    /**
     * Return true if labels should display sizes.  Defaults to <code>true</code>.
     *
     * @return true if labels should display sizes
     */
    public final boolean getDisplaySizes()
    {
        return displaySizes;
    }

    /**
     * Set to true if labels should display sizes.
     *
     * <p>This is a bound property.</p>
     *
     * @param displaySizes true if labels should display sizes
     */
    public final void setDisplaySizes(final boolean displaySizes)
    {
        boolean oldDisplaySizes = this.displaySizes;
        this.displaySizes = displaySizes;
        firePropertyChange(-1, "displaySizes", oldDisplaySizes, this.displaySizes);
        updateLabels();
    }

    /**
     * Return true if this venn node should display set labels.  Defaults to <code>true</code>.
     *
     * @return true if this venn node should display set labels
     */
    public final boolean getDisplayLabels()
    {
        return displayLabels;
    }

    /**
     * Set to true if this venn node should display set labels.
     *
     * <p>This is a bound property.</p>
     *
     * @param displayLabels true if this venn node should display set labels
     */
    public final void setDisplayLabels(final boolean displayLabels)
    {
        boolean oldDisplayLabels = this.displayLabels;
        this.displayLabels = displayLabels;
        firePropertyChange(-1, "displayLabels", oldDisplayLabels, this.displayLabels);
        updateLabels();
    }

    /**
     * Return true if this venn node should display size labels.  Defaults to <code>true</code>.
     *
     * @return true if this venn node should display size labels
     */
    public final boolean getDisplaySizeLabels()
    {
        return displaySizeLabels;
    }

    /**
     * Set to true if this venn node should display size labels.
     *
     * <p>This is a bound property.</p>
     *
     * @param displaySizeLabels true if this venn node should display size labels
     */
    public final void setDisplaySizeLabels(final boolean displaySizeLabels)
    {
        boolean oldDisplaySizeLabels = this.displaySizeLabels;
        this.displaySizeLabels = displaySizeLabels;
        firePropertyChange(-1, "displaySizeLabels", oldDisplaySizeLabels, this.displaySizeLabels);
        updateLabels();
    }

    /**
     * Return true if this venn node should display sizes for empty areas.  Defaults to <code>true</code>.
     *
     * @return true if this venn node should display sizes for empty areas
     */
    public final boolean getDisplaySizesForEmptyAreas()
    {
        return displaySizesForEmptyAreas;
    }

    /**
     * Set to true if this venn node should display sizes for empty areas.
     *
     * <p>This is a bound property.</p>
     *
     * @param displaySizesForEmptyAreas true if this venn node should display sizes for empty areas
     */
    public final void setDisplaySizesForEmptyAreas(final boolean displaySizesForEmptyAreas)
    {
        boolean oldDisplaySizesForEmptyAreas = this.displaySizesForEmptyAreas;
        this.displaySizesForEmptyAreas = displaySizesForEmptyAreas;
        firePropertyChange(-1, "displaySizesForEmptyAreas", oldDisplaySizesForEmptyAreas, this.displaySizesForEmptyAreas);
        updateLabels();
    }
}
