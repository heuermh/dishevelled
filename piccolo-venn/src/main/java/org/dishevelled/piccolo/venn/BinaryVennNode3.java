/*

    dsh-piccolo-venn  Piccolo2D venn diagram nodes and supporting classes.
    Copyright (c) 2009-2010 held jointly by the individual authors.

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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Shape;

import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import java.util.Set;

import edu.umd.cs.piccolo.PNode;

import edu.umd.cs.piccolo.nodes.PPath;
import edu.umd.cs.piccolo.nodes.PText;

import org.dishevelled.venn.BinaryVennModel3;

/**
 * Binary venn diagram node 3.
 *
 * @param <E> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class BinaryVennNode3<E>
    extends AbstractBinaryVennNode<E>
{
    /** Path node for the first set. */
    private final PPath first = new PPath();

    /** Area node for the first only view. */
    private final AreaNode firstOnly = new AreaNode();

    /** Label for the size of the first only view. */
    private final PText firstOnlySize = new PText();

    /** Path node for the second set. */
    private final PPath second = new PPath();

    /** Area node for the second only view. */
    private final AreaNode secondOnly = new AreaNode();

    /** Label for the size of the second only view. */
    private final PText secondOnlySize = new PText();

    /** Area node for the intersection view. */
    private final AreaNode intersection = new AreaNode();

    /** Label for the size of the intersection view. */
    private final PText intersectionSize = new PText();


    /**
     * Create a new empty binary venn node.
     */
    public BinaryVennNode3()
    {
        super();
        initNodes();
        updateContents();
    }

    /**
     * Create a new binary venn node with the specified sets.
     *
     * @param firstLabelText label text for the first set
     * @param first first set, must not be null
     * @param secondLabelText label text for the second set
     * @param second second set, must not be null
     */
    public BinaryVennNode3(final String firstLabelText, final Set<? extends E> first,
        final String secondLabelText, final Set<? extends E> second)
    {
        super(firstLabelText, first, secondLabelText, second);
        initNodes();
        updateContents();
    }

    /**
     * Create a new binary venn node with the specified model.
     *
     * @param model model for this binary venn node, must not be null
     */
    public BinaryVennNode3(final BinaryVennModel3<E> model)
    {
        super(model);
        initNodes();
        updateContents();
    }


    /**
     * Initialize nodes.
     */
    private void initNodes()
    {
        first.setPathToEllipse(0.0f, 0.0f, 128.0f, 128.0f);
        first.setPaint(new Color(80, 80, 80, 80));
        first.setStroke(new BasicStroke(1.0f));
        first.setStrokePaint(new Color(80, 80, 80, 128));
        second.setPathToEllipse(((2.0f * 128.0f) / 3.0f), 0.0f, 128.0f, 128.0f);
        second.setPaint(new Color(80, 80, 80, 80));
        second.setStroke(new BasicStroke(1.0f));
        second.setStrokePaint(new Color(80, 80, 80, 128));

        // or do tihs in layoutChildren()?
        Shape firstShape = first.getPathReference();
        Shape secondShape = second.getPathReference();
        Area firstArea = new Area(firstShape);
        Area secondArea = new Area(secondShape);
        firstArea.subtract(new Area(secondShape));
        firstOnly.setArea(firstArea);
        secondArea.subtract(new Area(firstShape));
        secondOnly.setArea(secondArea);
        Area intersectionArea = new Area(firstShape);
        intersectionArea.intersect(new Area(secondShape));
        intersection.setArea(intersectionArea);

        addChild(first);
        addChild(second);
        addChild(intersection);
        addChild(firstOnlySize);
        addChild(secondOnlySize);
        addChild(intersectionSize);
        addChild(getFirstLabel());
        addChild(getSecondLabel());
    }

    /** {@inheritDoc} */
    protected void updateContents()
    {
        firstOnlySize.setText(String.valueOf(getModel().firstOnly().size()));
        secondOnlySize.setText(String.valueOf(getModel().secondOnly().size()));
        intersectionSize.setText(String.valueOf(getModel().intersection().size()));
    }

    /** {@inheritDoc} */
    protected void layoutChildren()
    {
        Rectangle2D firstBounds = firstOnly.getArea().getBounds2D();
        Rectangle2D secondBounds = secondOnly.getArea().getBounds2D();
        Rectangle2D intersectionBounds = intersection.getArea().getBounds2D();

        Rectangle2D firstLabelBounds = getFirstLabel().getFullBoundsReference();
        Rectangle2D secondLabelBounds = getSecondLabel().getFullBoundsReference();
        Rectangle2D firstOnlySizeBounds = firstOnlySize.getFullBoundsReference();
        Rectangle2D secondOnlySizeBounds = secondOnlySize.getFullBoundsReference();
        Rectangle2D intersectionSizeBounds = intersectionSize.getFullBoundsReference();

        getFirstLabel().setOffset(firstBounds.getX() + (firstBounds.getWidth() / 2.0d) - (firstLabelBounds.getWidth() / 2.0d),
                                  -1.0d * firstLabelBounds.getHeight() - 4.0d);
        getSecondLabel().setOffset(secondBounds.getX() + (secondBounds.getWidth() / 2.0d) - (secondLabelBounds.getWidth() / 2.0d),
                                   -1.0d * secondLabelBounds.getHeight() - 4.0d);
        firstOnlySize.setOffset(firstBounds.getCenterX() - (firstOnlySizeBounds.getWidth() / 2.0d),
                                firstBounds.getCenterY() - (firstOnlySizeBounds.getHeight() / 2.0d));
        secondOnlySize.setOffset(secondBounds.getCenterX() - (secondOnlySizeBounds.getWidth() / 2.0d),
                                 secondBounds.getCenterY() - (secondOnlySizeBounds.getHeight() / 2.0d));
        intersectionSize.setOffset(intersectionBounds.getCenterX() - (intersectionSizeBounds.getWidth() / 2.0d),
                                   intersectionBounds.getCenterY() - (intersectionSizeBounds.getHeight() / 2.0d));
    }

    /**
     * Area node.
     */
    private class AreaNode
        extends PNode
    {
        /** Area for this area node. */
        private Area area;

        // todo:  implement this to allow for mouse-over, picking, etc.
        private void setArea(final Area area)
        {
            this.area = area;
        }

        private Area getArea()
        {
            return area;
        }
    }
}