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

import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import java.util.Set;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

import javax.swing.SwingWorker;

import org.piccolo2d.PNode;

import org.piccolo2d.nodes.PPath;
import org.piccolo2d.nodes.PText;

import org.dishevelled.venn.QuaternaryVennModel3;

/**
 * Quaternary venn diagram node 3.
 *
 * @param <E> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class QuaternaryVennNode3<E>
    extends AbstractQuaternaryVennNode<E>
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

    /** Path node for the fourth set. */
    private final PPath fourth = new PPath();

    /** Area node for the fourth only view. */
    private final AreaNode fourthOnly = new AreaNode();

    /** Label for the size of the fourth only view. */
    private final PText fourthOnlySize = new PText();

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

    /** Area node for the first fourth view. */
    private final AreaNode firstFourth = new AreaNode();

    /** Label for the size of the first fourth view. */
    private final PText firstFourthSize = new PText();

    /** Area node for the second fourth view. */
    private final AreaNode secondFourth = new AreaNode();

    /** Label for the size of the second fourth view. */
    private final PText secondFourthSize = new PText();

    /** Area node for the third fourth view. */
    private final AreaNode thirdFourth = new AreaNode();

    /** Label for the size of the third fourth view. */
    private final PText thirdFourthSize = new PText();

    /** Area node for the first second third view. */
    private final AreaNode firstSecondThird = new AreaNode();

    /** Label for the size of the first second third view. */
    private final PText firstSecondThirdSize = new PText();

    /** Area node for the first second fourth view. */
    private final AreaNode firstSecondFourth = new AreaNode();

    /** Label for the size of the first second fourth view. */
    private final PText firstSecondFourthSize = new PText();

    /** Area node for the first third fourth view. */
    private final AreaNode firstThirdFourth = new AreaNode();

    /** Label for the size of the first third fourth view. */
    private final PText firstThirdFourthSize = new PText();

    /** Area node for the second third fourth view. */
    private final AreaNode secondThirdFourth = new AreaNode();

    /** Label for the size of the second third fourth view. */
    private final PText secondThirdFourthSize = new PText();

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
    private Area r;

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

    /** Thread pool executor service. */
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(2);


    /**
     * Create a new empty quaternary venn node.
     */
    public QuaternaryVennNode3()
    {
        super();
        initNodes();
        updateContents();
    }

    /**
     * Create a new quaternary venn node with the specified sets.
     *
     * @param firstLabelText label text for the first set
     * @param first first set, must not be null
     * @param secondLabelText label text for the second set
     * @param second second set, must not be null
     * @param thirdLabelText label text for the third set
     * @param third third set, must not be null
     * @param fourthLabelText label text for the fourth set
     * @param fourth fourth set, must not be null
     */
    public QuaternaryVennNode3(final String firstLabelText, final Set<? extends E> first,
                               final String secondLabelText, final Set<? extends E> second,
                               final String thirdLabelText, final Set<? extends E> third,
                               final String fourthLabelText, final Set<? extends E> fourth)
    {
        super(firstLabelText, first, secondLabelText, second, thirdLabelText, third, fourthLabelText, fourth);
        initNodes();
        updateContents();
    }

    /**
     * Create a new quaternary venn node with the specified model.
     *
     * @param model model for this quaternary venn node, must not be null
     */
    public QuaternaryVennNode3(final QuaternaryVennModel3<E> model)
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
        Ellipse2D ellipse = new Ellipse2D.Double(0.0d, 0.0d, 376.0d, 234.0d);
        c.setLocation(ellipse.getBounds2D().getCenterX(), ellipse.getBounds2D().getCenterY());

        f = new Area(ellipse);
        f = f.createTransformedArea(AffineTransform.getRotateInstance((2.0d * Math.PI) / 9.0d, c.getX(), c.getY()));
        first.setPathTo(f);
        first.setPaint(new Color(30, 30, 30, 50));
        first.setStroke(new BasicStroke(0.5f));
        first.setStrokePaint(new Color(20, 20, 20));

        s = new Area(ellipse);
        s = s.createTransformedArea(AffineTransform.getRotateInstance((2.0d * Math.PI) / 9.0d, c.getX(), c.getY()));
        s = s.createTransformedArea(AffineTransform.getTranslateInstance(72.0d, -85.0d));
        second.setPathTo(s);
        second.setPaint(new Color(5, 37, 255, 50));
        second.setStroke(new BasicStroke(0.5f));
        second.setStrokePaint(new Color(20, 20, 20));

        t = new Area(ellipse);
        t = t.createTransformedArea(AffineTransform.getRotateInstance((-2.0d * Math.PI) / 9.0d, c.getX(), c.getY()));
        t = t.createTransformedArea(AffineTransform.getTranslateInstance(72.0d, -85.0d));
        third.setPathTo(t);
        third.setPaint(new Color(255, 100, 5, 50));
        third.setStroke(new BasicStroke(0.5f));
        third.setStrokePaint(new Color(20, 20, 20));

        r = new Area(ellipse);
        r = r.createTransformedArea(AffineTransform.getRotateInstance((-2.0d * Math.PI) / 9.0d, c.getX(), c.getY()));
        r = r.createTransformedArea(AffineTransform.getTranslateInstance(144.0d, 0.0d));
        fourth.setPathTo(r);
        fourth.setPaint(new Color(11, 255, 5, 50));
        fourth.setStroke(new BasicStroke(0.5f));
        fourth.setStrokePaint(new Color(20, 20, 20));

        addChild(first);
        addChild(second);
        addChild(third);
        addChild(fourth);
        addChild(firstSecond);
        addChild(firstThird);
        addChild(secondThird);
        addChild(firstFourth);
        addChild(secondFourth);
        addChild(thirdFourth);
        addChild(firstSecondThird);
        addChild(firstSecondFourth);
        addChild(firstThirdFourth);
        addChild(secondThirdFourth);
        addChild(intersection);
        addChild(firstOnlySize);
        addChild(secondOnlySize);
        addChild(thirdOnlySize);
        addChild(fourthOnlySize);
        addChild(firstSecondSize);
        addChild(firstThirdSize);
        addChild(secondThirdSize);
        addChild(firstFourthSize);
        addChild(secondFourthSize);
        addChild(thirdFourthSize);
        addChild(firstSecondThirdSize);
        addChild(firstSecondFourthSize);
        addChild(firstThirdFourthSize);
        addChild(secondThirdFourthSize);
        addChild(intersectionSize);
        // todo:  use firstOnlyLabel and secondOnlyLabel as mouseovers?
        addChild(getFirstLabel());
        addChild(getSecondLabel());
        addChild(getThirdLabel());
        addChild(getFourthLabel());
    }

    /** {@inheritDoc} */
    protected void updateContents()
    {
        firstOnlySize.setText(String.valueOf(getModel().firstOnly().size()));
        secondOnlySize.setText(String.valueOf(getModel().secondOnly().size()));
        thirdOnlySize.setText(String.valueOf(getModel().thirdOnly().size()));
        fourthOnlySize.setText(String.valueOf(getModel().fourthOnly().size()));
        firstSecondSize.setText(String.valueOf(getModel().firstSecond().size()));
        firstThirdSize.setText(String.valueOf(getModel().firstThird().size()));
        secondThirdSize.setText(String.valueOf(getModel().secondThird().size()));
        firstFourthSize.setText(String.valueOf(getModel().firstFourth().size()));
        secondFourthSize.setText(String.valueOf(getModel().secondFourth().size()));
        thirdFourthSize.setText(String.valueOf(getModel().thirdFourth().size()));
        firstSecondThirdSize.setText(String.valueOf(getModel().firstSecondThird().size()));
        firstSecondFourthSize.setText(String.valueOf(getModel().firstSecondFourth().size()));
        firstThirdFourthSize.setText(String.valueOf(getModel().firstThirdFourth().size()));
        secondThirdFourthSize.setText(String.valueOf(getModel().secondThirdFourth().size()));
        intersectionSize.setText(String.valueOf(getModel().intersection().size()));

        layoutNodes();
    }

    /**
     * Run layout separately from <code>layoutChildren</code> to prevent
     * soft infinite loop.
     */
    private void layoutNodes()
    {
        f = new Area(first.getPathReference());
        s = new Area(second.getPathReference());
        t = new Area(third.getPathReference());
        r = new Area(fourth.getPathReference());

        area.reset();
        area.add(f);
        area.subtract(s);
        area.subtract(t);
        area.subtract(r);
        firstOnly.setArea(area);

        area.reset();
        area.add(s);
        area.subtract(f);
        area.subtract(t);
        area.subtract(r);
        secondOnly.setArea(area);

        area.reset();
        area.add(t);
        area.subtract(f);
        area.subtract(s);
        area.subtract(r);
        thirdOnly.setArea(area);

        area.reset();
        area.add(r);
        area.subtract(f);
        area.subtract(s);
        area.subtract(t);
        fourthOnly.setArea(area);

        area.reset();
        area.add(f);
        area.intersect(s);
        area.subtract(t);
        area.subtract(r);
        firstSecond.setArea(area);

        area.reset();
        area.add(f);
        area.intersect(t);
        area.subtract(s);
        area.subtract(r);
        firstThird.setArea(area);

        area.reset();
        area.add(f);
        area.intersect(r);
        area.subtract(s);
        area.subtract(t);
        firstFourth.setArea(area);

        area.reset();
        area.add(s);
        area.intersect(t);
        area.subtract(f);
        area.subtract(r);
        secondThird.setArea(area);

        area.reset();
        area.add(s);
        area.intersect(r);
        area.subtract(f);
        area.subtract(t);
        secondFourth.setArea(area);

        area.reset();
        area.add(t);
        area.intersect(r);
        area.subtract(f);
        area.subtract(s);
        thirdFourth.setArea(area);

        area.reset();
        area.add(f);
        area.intersect(s);
        area.intersect(t);
        area.subtract(r);
        firstSecondThird.setArea(area);

        area.reset();
        area.add(f);
        area.intersect(s);
        area.intersect(r);
        area.subtract(t);
        firstSecondFourth.setArea(area);
        
        area.reset();
        area.add(f);
        area.intersect(t);
        area.intersect(r);
        area.subtract(s);
        firstThirdFourth.setArea(area);

        area.reset();
        area.add(s);
        area.intersect(t);
        area.intersect(r);
        area.subtract(f);
        secondThirdFourth.setArea(area);

        area.reset();
        area.add(f);
        area.intersect(s);
        area.intersect(t);
        area.intersect(r);
        intersection.setArea(area);

        // offset to center now
        offset(firstOnly.getArea(), firstOnlySize);
        offset(secondOnly.getArea(), secondOnlySize);
        offset(thirdOnly.getArea(), thirdOnlySize);
        offset(fourthOnly.getArea(), fourthOnlySize);
        offset(firstSecond.getArea(), firstSecondSize);
        offset(firstThird.getArea(), firstThirdSize);
        offset(secondThird.getArea(), secondThirdSize);
        offset(firstFourth.getArea(), firstFourthSize);
        offset(secondFourth.getArea(), secondFourthSize);
        offset(thirdFourth.getArea(), thirdFourthSize);
        offset(firstSecondThird.getArea(), firstSecondThirdSize);
        offset(firstSecondFourth.getArea(), firstSecondFourthSize);
        offset(firstThirdFourth.getArea(), firstThirdFourthSize);
        offset(secondThirdFourth.getArea(), secondThirdFourthSize);
        offset(intersection.getArea(), intersectionSize);

        // delay offset to centroids
        EXECUTOR_SERVICE.submit(new LayoutWorker(firstOnly.getArea(), firstOnlySize));
        EXECUTOR_SERVICE.submit(new LayoutWorker(secondOnly.getArea(), secondOnlySize));
        EXECUTOR_SERVICE.submit(new LayoutWorker(thirdOnly.getArea(), thirdOnlySize));
        EXECUTOR_SERVICE.submit(new LayoutWorker(fourthOnly.getArea(), fourthOnlySize));
        EXECUTOR_SERVICE.submit(new LayoutWorker(firstSecond.getArea(), firstSecondSize));
        EXECUTOR_SERVICE.submit(new LayoutWorker(firstThird.getArea(), firstThirdSize));
        EXECUTOR_SERVICE.submit(new LayoutWorker(secondThird.getArea(), secondThirdSize));
        EXECUTOR_SERVICE.submit(new LayoutWorker(firstFourth.getArea(), firstFourthSize));
        EXECUTOR_SERVICE.submit(new LayoutWorker(secondFourth.getArea(), secondFourthSize));
        EXECUTOR_SERVICE.submit(new LayoutWorker(thirdFourth.getArea(), thirdFourthSize));
        EXECUTOR_SERVICE.submit(new LayoutWorker(firstSecondThird.getArea(), firstSecondThirdSize));
        EXECUTOR_SERVICE.submit(new LayoutWorker(firstSecondFourth.getArea(), firstSecondFourthSize));
        EXECUTOR_SERVICE.submit(new LayoutWorker(firstThirdFourth.getArea(), firstThirdFourthSize));
        EXECUTOR_SERVICE.submit(new LayoutWorker(secondThirdFourth.getArea(), secondThirdFourthSize));
        EXECUTOR_SERVICE.submit(new LayoutWorker(intersection.getArea(), intersectionSize));

        labelFarLeft(firstOnly.getArea(), secondOnly.getArea(), getFirstLabel());
        labelLeft(secondOnly.getArea(), getSecondLabel());
        labelRight(thirdOnly.getArea(), getThirdLabel());
        labelFarRight(fourthOnly.getArea(), thirdOnly.getArea(), getFourthLabel());
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
     * Offset the specified label to the top and left of the center of the first
     * specified area and to the right of the second specified area.
     *
     * @param area0 first area
     * @param area1 second area
     * @param label label
     */
    private void labelFarLeft(final Area area0, final Area area1, final PText label)
    {
        a = area0.getBounds2D();
        b = label.getFullBoundsReference();
        c = Centers.centerOf(area0, c);
        double y = a.getY() - b.getHeight() - LABEL_GAP;
        a = area1.getBounds2D();
        double left = c.getX() - ((2.0d * b.getWidth()) / 3.0d);
        double farLeft = a.getX() - b.getWidth() - LABEL_GAP;
        label.setOffset(Math.min(left, farLeft), y);
    }

    /**
     * Offset the specified label to the top and right of the center of the first
     * specified area and to the right of the second specified area.
     *
     * @param area0 first area
     * @param area1 second area
     * @param label label
     */
    private void labelFarRight(final Area area0, final Area area1, final PText label)
    {
        a = area0.getBounds2D();
        b = label.getFullBoundsReference();
        c = Centers.centerOf(area0, c);
        double y = a.getY() - b.getHeight() - LABEL_GAP;
        a = area1.getBounds2D();
        double right = c.getX() - ((2.0d * b.getWidth()) / 3.0d);
        double farRight = a.getX() + a.getWidth() + LABEL_GAP;
        label.setOffset(Math.max(right, farRight), y);
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

    /**
     * Layout worker.
     */
    private class LayoutWorker
        extends SwingWorker<Point2D, Object>
    {
        /** Area for this layout worker. */
        private Area area;

        /** Size label for this layout worker. */
        private PText size;


        /**
         * Create a new layout worker for the specified area and size label.
         *
         * @param area area
         * @param size size label
         */
        private LayoutWorker(final Area area, final PText size)
        {
            this.area = area;
            this.size = size;
        }


        /** {@inheritDoc} */
        public Point2D doInBackground()
        {
            return Centers.centroidOf(area);
        }

        /** {@inheritDoc} */
        protected void done()
        {
            try
            {
                Rectangle2D b = size.getFullBoundsReference();
                Point2D c = get();
                size.animateToPositionScaleRotation(c.getX() - (b.getWidth() / 2.0d),
                                                    c.getY() - (b.getHeight() / 2.0d), 1.0d, 0.0d, 2000);
            }
            catch (Exception e)
            {
                // ignore
            }
        }
    }
}