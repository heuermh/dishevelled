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
import java.awt.Paint;
import java.awt.Stroke;

import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.piccolo2d.PNode;

import org.piccolo2d.jdk16.nodes.PArea;
import org.piccolo2d.jdk16.nodes.PPath;

import org.piccolo2d.nodes.PText;

import org.dishevelled.venn.BinaryVennModel;

/**
 * Binary venn diagram node.
 *
 * @param <E> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class BinaryVennNode<E>
    extends AbstractBinaryVennNode<E>
{
    /** Path node for the first set. */
    private final PPath first = new PPath.Double(FIRST_SHAPE, STROKE);

    /** Area node for the first only view. */
    private final PArea firstOnly = new PArea(AREA_STROKE);

    /** Label for the size of the first only view. */
    private final PText firstOnlySize = new PText();

    /** Path node for the second set. */
    private final PPath second = new PPath.Double(SECOND_SHAPE, STROKE);

    /** Area node for the second only view. */
    private final PArea secondOnly = new PArea(AREA_STROKE);

    /** Label for the size of the second only view. */
    private final PText secondOnlySize = new PText();

    /** Area node for the intersection view. */
    private final PArea intersection = new PArea(AREA_STROKE);

    /** Label for the size of the intersection view. */
    private final PText intersectionSize = new PText();

    /** List of nodes. */
    private final List<PNode> nodes = Arrays.asList(new PNode[] { firstOnly, secondOnly, intersection });

    /** Cached area. */
    private Area f;

    /** Cached area. */
    private Area s;

    /** Cached rectangle. */
    private Rectangle2D a = new Rectangle2D.Double();

    /** Cached area. */
    private Rectangle2D b = new Rectangle2D.Double();

    /** Cached point. */
    private Point2D c = new Point2D.Double();

    /** Label gap, <code>8.0d</code>. */
    private static final double LABEL_GAP = 8.0d;

    /** Adjust label gap, <code>10.0d</code>. */
    private static final double ADJUST_LABEL_GAP = 10.0d;

    /** First shape. */
    private static final Ellipse2D FIRST_SHAPE = new Ellipse2D.Double(0.0d, 0.0d, 128.0d, 128.0d);

    /** First paint. */
    private static final Paint FIRST_PAINT = new Color(30, 30, 30, 50);

    /** Second shape. */
    private static final Ellipse2D SECOND_SHAPE = new Ellipse2D.Double(((2.0d * 128.0d) / 3.0d), 0.0d, 128.0d, 128.0d);

    /** Second paint. */
    private static final Paint SECOND_PAINT = new Color(5, 37, 255, 50);

    /** Stroke. */
    private static final Stroke STROKE = new BasicStroke(0.5f);

    /** Stroke paint. */
    private static final Paint STROKE_PAINT = new Color(20, 20, 20);

    /** Area paint. */
    private static final Paint AREA_PAINT = new Color(0, 0, 0, 0);

    /** Area stroke. */
    private static final Stroke AREA_STROKE = null;


    /**
     * Create a new empty binary venn node.
     */
    public BinaryVennNode()
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
    public BinaryVennNode(final String firstLabelText, final Set<? extends E> first,
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
    public BinaryVennNode(final BinaryVennModel<E> model)
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
        first.setPaint(FIRST_PAINT);
        first.setStrokePaint(STROKE_PAINT);
        second.setPaint(SECOND_PAINT);
        second.setStrokePaint(STROKE_PAINT);
        firstOnly.setPaint(AREA_PAINT);
        secondOnly.setPaint(AREA_PAINT);
        intersection.setPaint(AREA_PAINT);

        addChild(first);
        addChild(second);
        addChild(firstOnlySize);
        addChild(secondOnlySize);
        addChild(intersectionSize);
        addChild(firstOnly);
        addChild(secondOnly);
        addChild(intersection);
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
        f = new Area(first.getPathReference());
        s = new Area(second.getPathReference());

        firstOnly.reset();
        firstOnly.add(f);
        firstOnly.subtract(s);

        secondOnly.reset();
        secondOnly.add(s);
        secondOnly.subtract(f);

        intersection.reset();
        intersection.add(f);
        intersection.intersect(s);

        offset(firstOnly.getAreaReference(), firstOnlySize);
        offset(secondOnly.getAreaReference(), secondOnlySize);
        offset(intersection.getAreaReference(), intersectionSize);

        labelLeft(firstOnly.getAreaReference(), getFirstLabel());
        labelRight(secondOnly.getAreaReference(), getSecondLabel());
        adjustLabels(getFirstLabel(), getSecondLabel());
    }

    /**
     * Offset the specified size label to the center of the specified area.
     *
     * @param area area
     * @param size size label
     */
    private void offset(final Area area, final PText size)
    {
        b = size.getFullBoundsReference();
        c = Centers.centerOf(area, c);
        size.setOffset(c.getX() - (b.getWidth() / 2.0d), c.getY() - (b.getHeight() / 2.0d));
    }

    /**
     * Offset the specified label to the top and left of the center of the specified area.
     *
     * @param area area
     * @param label label
     */
    private void labelLeft(final Area area, final PText label)
    {
        a = area.getBounds2D();
        b = label.getFullBoundsReference();
        c = Centers.centerOf(area, c);
        label.setOffset(c.getX() - ((2.0d * b.getWidth()) / 3.0d), a.getY() - b.getHeight() - LABEL_GAP);
    }

    /**
     * Offset the specified label to the top and right of the center of the specified area.
     *
     * @param area area
     * @param label label
     */
    private void labelRight(final Area area, final PText label)
    {
        a = area.getBounds2D();
        b = label.getFullBoundsReference();
        c = Centers.centerOf(area, c);
        label.setOffset(c.getX() - (b.getWidth() / 3.0d), a.getY() - b.getHeight() - LABEL_GAP);
    }

    /**
     * Adjust the horizontal offsets for the specified pair of labels if their bounds overlap.
     *
     * @param leftLabel left label
     * @param rightLabel right label
     */
    private void adjustLabels(final PText leftLabel, final PText rightLabel)
    {
        a = leftLabel.getFullBoundsReference();
        b = rightLabel.getFullBoundsReference();
        Rectangle2D.intersect(a, b, a);
        if (a.getWidth() > 0.0d)
        {
            leftLabel.offset(-1.0 * a.getWidth() / 2.0d - ADJUST_LABEL_GAP, 0.0d);
            rightLabel.offset(a.getWidth() / 2.0d + ADJUST_LABEL_GAP, 0.0d);
        }
    }

    /**
     * Return the path node for the first set.
     *
     * @return the path node for the first set
     */
    public PPath getFirst()
    {
        return first;
    }

    /**
     * Return the path node for the second set.
     *
     * @return the path node for the second set
     */
    public PPath getSecond()
    {
        return second;
    }

    /**
     * Return the area node for the first only view.
     *
     * @return the area node for the first only view
     */
    public PArea getFirstOnly()
    {
        return firstOnly;
    }

    /**
     * Return the area node for the second only view.
     *
     * @return the area node for the second only view
     */
    public PArea getSecondOnly()
    {
        return secondOnly;
    }

    /**
     * Return the area node for the intersection view.
     *
     * @return the area node for the intersection view
     */
    public PArea getIntersection()
    {
        return intersection;
    }

    /** {@inheritDoc} */
    public Iterable<PNode> nodes()
    {
        return nodes;
    }

    /** {@inheritDoc} */
    public PText labelForNode(final PNode node)
    {
        if (firstOnly.equals(node))
        {
            return getFirstOnlyLabel();
        }
        else if (secondOnly.equals(node))
        {
            return getSecondOnlyLabel();
        }
        else if (intersection.equals(node))
        {
            return getIntersectionLabel();
        }
        return null;
    }

    /** {@inheritDoc} */
    public String labelTextForNode(final PNode node)
    {
        if (firstOnly.equals(node))
        {
            return getFirstOnlyLabelText();
        }
        else if (secondOnly.equals(node))
        {
            return getSecondOnlyLabelText();
        }
        else if (intersection.equals(node))
        {
            return getIntersectionLabelText();
        }
        return null;
    }

    /** {@inheritDoc} */
    public Set<E> viewForNode(final PNode node)
    {
        if (firstOnly.equals(node))
        {
            return getModel().firstOnly();
        }
        else if (secondOnly.equals(node))
        {
            return getModel().secondOnly();
        }
        else if (intersection.equals(node))
        {
            return getModel().intersection();
        }
        return null;
    }
}