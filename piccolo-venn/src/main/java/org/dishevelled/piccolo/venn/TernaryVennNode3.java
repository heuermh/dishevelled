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
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import java.util.Set;

import edu.umd.cs.piccolo.PNode;

import edu.umd.cs.piccolo.nodes.PPath;
import edu.umd.cs.piccolo.nodes.PText;

import org.dishevelled.venn.TernaryVennModel3;

/**
 * Ternary venn diagram node 3.
 *
 * @param <E> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class TernaryVennNode3<E>
    extends AbstractTernaryVennNode<E>
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

    /** Path node for the third set. */
    private final PPath third = new PPath();

    /** Area node for the third only view. */
    private final AreaNode thirdOnly = new AreaNode();

    /** Label for the size of the third only view. */
    private final PText thirdOnlySize = new PText();

    /** Area node for the first second view. */
    private final AreaNode firstSecond = new AreaNode();

    /** Label for the size of the first second view. */
    private final PText firstSecondSize = new PText();

    /** Area node for the first third view. */
    private final AreaNode firstThird = new AreaNode();

    /** Label for the size of the first third view. */
    private final PText firstThirdSize = new PText();

    /** Area node for the second third view. */
    private final AreaNode secondThird = new AreaNode();

    /** Label for the size of the second third view. */
    private final PText secondThirdSize = new PText();

    /** Area node for the intersection view. */
    private final AreaNode intersection = new AreaNode();

    /** Label for the size of the intersection view. */
    private final PText intersectionSize = new PText();

    /** Cached area. */
    private Area f;

    /** Cached area. */
    private Area s;

    /** Cached area. */
    private Area t;

    /** Cached area. */
    private final Area area = new Area();

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


    /**
     * Create a new empty ternary venn node.
     */
    public TernaryVennNode3()
    {
        super();
        initNodes();
        updateContents();
    }

    /**
     * Create a new ternary venn node with the specified sets.
     *
     * @param firstLabelText label text for the first set
     * @param first first set, must not be null
     * @param secondLabelText label text for the second set
     * @param second second set, must not be null
     * @param thirdLabelText label text for the third set
     * @param third third set, must not be null
     */
    public TernaryVennNode3(final String firstLabelText, final Set<? extends E> first,
                             final String secondLabelText, final Set<? extends E> second,
                             final String thirdLabelText, final Set<? extends E> third)
    {
        super(firstLabelText, first, secondLabelText, second, thirdLabelText, third);
        initNodes();
        updateContents();
    }

    /**
     * Create a new ternary venn node with the specified model.
     *
     * @param model model for this ternary venn node, must not be null
     */
    public TernaryVennNode3(final TernaryVennModel3<E> model)
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
        first.setPaint(new Color(30, 30, 30, 50));
        first.setStroke(new BasicStroke(0.5f));
        first.setStrokePaint(new Color(20, 20, 20));
        second.setPathToEllipse(((2.0f * 128.0f) / 3.0f), 0.0f, 128.0f, 128.0f);
        second.setPaint(new Color(5, 37, 255, 50));
        second.setStroke(new BasicStroke(0.5f));
        second.setStrokePaint(new Color(20, 20, 20));
        third.setPathToEllipse(128.0f / 3.0f, (2.0f * 128.0f) / 3.0f, 128.0f, 128.0f);
        third.setPaint(new Color(255, 100, 5, 50));
        third.setStroke(new BasicStroke(0.5f));
        third.setStrokePaint(new Color(20, 20, 20));

        addChild(first);
        addChild(second);
        addChild(third);
        addChild(firstSecond);
        addChild(firstThird);
        addChild(secondThird);
        addChild(intersection);
        addChild(firstOnlySize);
        addChild(secondOnlySize);
        addChild(thirdOnlySize);
        addChild(firstSecondSize);
        addChild(firstThirdSize);
        addChild(secondThirdSize);
        addChild(intersectionSize);
        // todo:  use firstOnlyLabel and secondOnlyLabel as mouseovers?
        addChild(getFirstLabel());
        addChild(getSecondLabel());
        addChild(getThirdLabel());
    }

    /** {@inheritDoc} */
    protected void updateContents()
    {
        firstOnlySize.setText(String.valueOf(getModel().firstOnly().size()));
        secondOnlySize.setText(String.valueOf(getModel().secondOnly().size()));
        thirdOnlySize.setText(String.valueOf(getModel().thirdOnly().size()));
        firstSecondSize.setText(String.valueOf(getModel().firstSecond().size()));
        firstThirdSize.setText(String.valueOf(getModel().firstThird().size()));
        secondThirdSize.setText(String.valueOf(getModel().secondThird().size()));
        intersectionSize.setText(String.valueOf(getModel().intersection().size()));
    }

    /** {@inheritDoc} */
    protected void layoutChildren()
    {
        //System.out.println("layoutChildren");
        f = new Area(first.getPathReference());
        s = new Area(second.getPathReference());
        t = new Area(third.getPathReference());

        area.reset();
        area.add(f);
        area.subtract(s);
        area.subtract(t);
        firstOnly.setArea(area);

        area.reset();
        area.add(s);
        area.subtract(f);
        area.subtract(t);
        secondOnly.setArea(area);

        area.reset();
        area.add(t);
        area.subtract(f);
        area.subtract(s);
        thirdOnly.setArea(area);

        area.reset();
        area.add(f);
        area.intersect(s);
        area.subtract(t);
        firstSecond.setArea(area);

        area.reset();
        area.add(f);
        area.intersect(t);
        area.subtract(s);
        firstThird.setArea(area);

        area.reset();
        area.add(s);
        area.intersect(t);
        area.subtract(f);
        secondThird.setArea(area);

        area.reset();
        area.add(f);
        area.intersect(s);
        area.intersect(t);
        intersection.setArea(area);

        offset(firstOnly.getArea(), firstOnlySize);
        offset(secondOnly.getArea(), secondOnlySize);
        offset(thirdOnly.getArea(), thirdOnlySize);
        offset(firstSecond.getArea(), firstSecondSize);
        offset(firstThird.getArea(), firstThirdSize);
        offset(secondThird.getArea(), secondThirdSize);
        offset(intersection.getArea(), intersectionSize);

        labelLeft(firstOnly.getArea(), getFirstLabel());
        labelRight(secondOnly.getArea(), getSecondLabel());
        labelCenter(thirdOnly.getArea(), getThirdLabel());
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
     * Offset the specified label to the bottom and center of the specified area.
     *
     * @param area area
     * @param label label
     */
    private void labelCenter(final Area area, final PText label)
    {
        a = area.getBounds2D();
        b = label.getFullBoundsReference();
        c = Centers.centerOf(area, c);
        label.setOffset(c.getX() - (b.getWidth() / 2.0d), a.getY() + a.getHeight() + LABEL_GAP);
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

    // todo:  allow getters for nodes, or alternatively getters/setters for paint, stroke, strokePaint
    //    allowing reference to first, second paths would allow clients to change the path/shape and offset


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
            this.area = (Area) area.clone();
        }

        private Area getArea()
        {
            return area;
        }
    }
}