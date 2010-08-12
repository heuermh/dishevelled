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

import org.piccolo2d.nodes.PArea;
import org.piccolo2d.nodes.PPath;
import org.piccolo2d.nodes.PText;

import org.dishevelled.venn.QuaternaryVennModel;

/**
 * Quaternary venn diagram node.
 *
 * @param <E> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class QuaternaryVennNode<E>
    extends AbstractQuaternaryVennNode<E>
{
    /** Path node for the first set. */
    private final PPath first = new PPath.Double(STROKE);

    /** Area node for the first only view. */
    private final PArea firstOnly = new PArea(AREA_STROKE);

    /** Label for the size of the first only view. */
    private final PText firstOnlySize = new PText();

    /** Path node for the second set. */
    private final PPath second = new PPath.Double(STROKE);

    /** Area node for the second only view. */
    private final PArea secondOnly = new PArea(AREA_STROKE);

    /** Label for the size of the second only view. */
    private final PText secondOnlySize = new PText();

    /** Path node for the third set. */
    private final PPath third = new PPath.Double(STROKE);

    /** Area node for the third only view. */
    private final PArea thirdOnly = new PArea(AREA_STROKE);

    /** Label for the size of the third only view. */
    private final PText thirdOnlySize = new PText();

    /** Path node for the fourth set. */
    private final PPath fourth = new PPath.Double(STROKE);

    /** Area node for the fourth only view. */
    private final PArea fourthOnly = new PArea(AREA_STROKE);

    /** Label for the size of the fourth only view. */
    private final PText fourthOnlySize = new PText();

    /** Area node for the first second view. */
    private final PArea firstSecond = new PArea(AREA_STROKE);

    /** Label for the size of the first second view. */
    private final PText firstSecondSize = new PText();

    /** Area node for the first third view. */
    private final PArea firstThird = new PArea(AREA_STROKE);

    /** Label for the size of the first third view. */
    private final PText firstThirdSize = new PText();

    /** Area node for the second third view. */
    private final PArea secondThird = new PArea(AREA_STROKE);

    /** Label for the size of the second third view. */
    private final PText secondThirdSize = new PText();

    /** Area node for the first fourth view. */
    private final PArea firstFourth = new PArea(AREA_STROKE);

    /** Label for the size of the first fourth view. */
    private final PText firstFourthSize = new PText();

    /** Area node for the second fourth view. */
    private final PArea secondFourth = new PArea(AREA_STROKE);

    /** Label for the size of the second fourth view. */
    private final PText secondFourthSize = new PText();

    /** Area node for the third fourth view. */
    private final PArea thirdFourth = new PArea(AREA_STROKE);

    /** Label for the size of the third fourth view. */
    private final PText thirdFourthSize = new PText();

    /** Area node for the first second third view. */
    private final PArea firstSecondThird = new PArea(AREA_STROKE);

    /** Label for the size of the first second third view. */
    private final PText firstSecondThirdSize = new PText();

    /** Area node for the first second fourth view. */
    private final PArea firstSecondFourth = new PArea(AREA_STROKE);

    /** Label for the size of the first second fourth view. */
    private final PText firstSecondFourthSize = new PText();

    /** Area node for the first third fourth view. */
    private final PArea firstThirdFourth = new PArea(AREA_STROKE);

    /** Label for the size of the first third fourth view. */
    private final PText firstThirdFourthSize = new PText();

    /** Area node for the second third fourth view. */
    private final PArea secondThirdFourth = new PArea(AREA_STROKE);

    /** Label for the size of the second third fourth view. */
    private final PText secondThirdFourthSize = new PText();

    /** Area node for the intersection view. */
    private final PArea intersection = new PArea(AREA_STROKE);

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

    /** Animation length, in milliseconds, <code>2000L</code>. */
    private static final long MS = 2000L;

    /** First paint. */
    private static final Paint FIRST_PAINT = new Color(30, 30, 30, 50);

    /** Second paint. */
    private static final Paint SECOND_PAINT = new Color(5, 37, 255, 50);

    /** Third paint. */
    private static final Paint THIRD_PAINT = new Color(255, 100, 5, 50);

    /** Third paint. */
    private static final Paint FOURTH_PAINT = new Color(11, 255, 5, 50);

    /** Stroke. */
    private static final Stroke STROKE = new BasicStroke(0.5f);

    /** Stroke paint. */
    private static final Paint STROKE_PAINT = new Color(20, 20, 20);

    /** Area paint. */
    private static final Paint AREA_PAINT = new Color(0, 0, 0, 0);

    /** Area stroke. */
    private static final Stroke AREA_STROKE = null;


    /**
     * Create a new empty quaternary venn node.
     */
    public QuaternaryVennNode()
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
    public QuaternaryVennNode(final String firstLabelText, final Set<? extends E> first,
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
    public QuaternaryVennNode(final QuaternaryVennModel<E> model)
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
        /*

          Venn construction based on

          http://commons.wikimedia.org/wiki/File:Venn%27s_four_ellipse_construction.svg
          by author RupertMillard http://commons.wikimedia.org/wiki/User:RupertMillard

          licensed under the Creative Commons Attribution-Share Alike 3.0 Unported license.
          http://creativecommons.org/licenses/by-sa/3.0/deed.en

          The following lines of code, to line 305, are also under the same license.

        */

        Ellipse2D ellipse = new Ellipse2D.Double(0.0d, 0.0d, 376.0d, 234.0d);
        c.setLocation(ellipse.getBounds2D().getCenterX(), ellipse.getBounds2D().getCenterY());

        f = new Area(ellipse);
        f = f.createTransformedArea(AffineTransform.getRotateInstance((2.0d * Math.PI) / 9.0d, c.getX(), c.getY()));
        first.append(f, false);
        first.setPaint(FIRST_PAINT);
        first.setStrokePaint(STROKE_PAINT);

        s = new Area(ellipse);
        s = s.createTransformedArea(AffineTransform.getRotateInstance((2.0d * Math.PI) / 9.0d, c.getX(), c.getY()));
        s = s.createTransformedArea(AffineTransform.getTranslateInstance(72.0d, -85.0d));
        second.append(s, false);
        second.setPaint(SECOND_PAINT);
        second.setStrokePaint(STROKE_PAINT);

        t = new Area(ellipse);
        t = t.createTransformedArea(AffineTransform.getRotateInstance((-2.0d * Math.PI) / 9.0d, c.getX(), c.getY()));
        t = t.createTransformedArea(AffineTransform.getTranslateInstance(72.0d, -85.0d));
        third.append(t, false);
        third.setPaint(THIRD_PAINT);
        third.setStrokePaint(STROKE_PAINT);

        r = new Area(ellipse);
        r = r.createTransformedArea(AffineTransform.getRotateInstance((-2.0d * Math.PI) / 9.0d, c.getX(), c.getY()));
        r = r.createTransformedArea(AffineTransform.getTranslateInstance(144.0d, 0.0d));
        fourth.append(r, false);
        fourth.setPaint(FOURTH_PAINT);
        fourth.setStrokePaint(STROKE_PAINT);

        firstOnly.setPaint(AREA_PAINT);
        secondOnly.setPaint(AREA_PAINT);
        thirdOnly.setPaint(AREA_PAINT);
        fourthOnly.setPaint(AREA_PAINT);
        firstSecond.setPaint(AREA_PAINT);
        firstThird.setPaint(AREA_PAINT);
        secondThird.setPaint(AREA_PAINT);
        firstFourth.setPaint(AREA_PAINT);
        secondFourth.setPaint(AREA_PAINT);
        thirdFourth.setPaint(AREA_PAINT);
        firstSecondThird.setPaint(AREA_PAINT);
        firstSecondFourth.setPaint(AREA_PAINT);
        firstThirdFourth.setPaint(AREA_PAINT);
        secondThirdFourth.setPaint(AREA_PAINT);
        intersection.setPaint(AREA_PAINT);

        addChild(first);
        addChild(second);
        addChild(third);
        addChild(fourth);
        addChild(firstOnly);
        addChild(secondOnly);
        addChild(thirdOnly);
        addChild(fourthOnly);
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

        firstOnly.reset();
        firstOnly.add(f);
        firstOnly.subtract(s);
        firstOnly.subtract(t);
        firstOnly.subtract(r);

        secondOnly.reset();
        secondOnly.add(s);
        secondOnly.subtract(f);
        secondOnly.subtract(t);
        secondOnly.subtract(r);

        thirdOnly.reset();
        thirdOnly.add(t);
        thirdOnly.subtract(f);
        thirdOnly.subtract(s);
        thirdOnly.subtract(r);
        
        fourthOnly.reset();
        fourthOnly.add(r);
        fourthOnly.subtract(f);
        fourthOnly.subtract(s);
        fourthOnly.subtract(t);
        
        firstSecond.reset();
        firstSecond.add(f);
        firstSecond.intersect(s);
        firstSecond.subtract(t);
        firstSecond.subtract(r);

        firstThird.reset();
        firstThird.add(f);
        firstThird.intersect(t);
        firstThird.subtract(s);
        firstThird.subtract(r);

        firstFourth.reset();
        firstFourth.add(f);
        firstFourth.intersect(r);
        firstFourth.subtract(s);
        firstFourth.subtract(t);

        secondThird.reset();
        secondThird.add(s);
        secondThird.intersect(t);
        secondThird.subtract(f);
        secondThird.subtract(r);

        secondFourth.reset();
        secondFourth.add(s);
        secondFourth.intersect(r);
        secondFourth.subtract(f);
        secondFourth.subtract(t);

        thirdFourth.reset();
        thirdFourth.add(t);
        thirdFourth.intersect(r);
        thirdFourth.subtract(f);
        thirdFourth.subtract(s);

        firstSecondThird.reset();
        firstSecondThird.add(f);
        firstSecondThird.intersect(s);
        firstSecondThird.intersect(t);
        firstSecondThird.subtract(r);

        firstSecondFourth.reset();
        firstSecondFourth.add(f);
        firstSecondFourth.intersect(s);
        firstSecondFourth.intersect(r);
        firstSecondFourth.subtract(t);

        firstThirdFourth.reset();
        firstThirdFourth.add(f);
        firstThirdFourth.intersect(t);
        firstThirdFourth.intersect(r);
        firstThirdFourth.subtract(s);

        secondThirdFourth.reset();
        secondThirdFourth.add(s);
        secondThirdFourth.intersect(t);
        secondThirdFourth.intersect(r);
        secondThirdFourth.subtract(f);

        intersection.reset();
        intersection.add(f);
        intersection.intersect(s);
        intersection.intersect(t);
        intersection.intersect(r);

        // offset to center now
        offset(firstOnly.getAreaReference(), firstOnlySize);
        offset(secondOnly.getAreaReference(), secondOnlySize);
        offset(thirdOnly.getAreaReference(), thirdOnlySize);
        offset(fourthOnly.getAreaReference(), fourthOnlySize);
        offset(firstSecond.getAreaReference(), firstSecondSize);
        offset(firstThird.getAreaReference(), firstThirdSize);
        offset(secondThird.getAreaReference(), secondThirdSize);
        offset(firstFourth.getAreaReference(), firstFourthSize);
        offset(secondFourth.getAreaReference(), secondFourthSize);
        offset(thirdFourth.getAreaReference(), thirdFourthSize);
        offset(firstSecondThird.getAreaReference(), firstSecondThirdSize);
        offset(firstSecondFourth.getAreaReference(), firstSecondFourthSize);
        offset(firstThirdFourth.getAreaReference(), firstThirdFourthSize);
        offset(secondThirdFourth.getAreaReference(), secondThirdFourthSize);
        offset(intersection.getAreaReference(), intersectionSize);

        // delay offset to centroids
        EXECUTOR_SERVICE.submit(new LayoutWorker(firstOnly.getAreaReference(), firstOnlySize));
        EXECUTOR_SERVICE.submit(new LayoutWorker(secondOnly.getAreaReference(), secondOnlySize));
        EXECUTOR_SERVICE.submit(new LayoutWorker(thirdOnly.getAreaReference(), thirdOnlySize));
        EXECUTOR_SERVICE.submit(new LayoutWorker(fourthOnly.getAreaReference(), fourthOnlySize));
        EXECUTOR_SERVICE.submit(new LayoutWorker(firstSecond.getAreaReference(), firstSecondSize));
        EXECUTOR_SERVICE.submit(new LayoutWorker(firstThird.getAreaReference(), firstThirdSize));
        EXECUTOR_SERVICE.submit(new LayoutWorker(secondThird.getAreaReference(), secondThirdSize));
        EXECUTOR_SERVICE.submit(new LayoutWorker(firstFourth.getAreaReference(), firstFourthSize));
        EXECUTOR_SERVICE.submit(new LayoutWorker(secondFourth.getAreaReference(), secondFourthSize));
        EXECUTOR_SERVICE.submit(new LayoutWorker(thirdFourth.getAreaReference(), thirdFourthSize));
        EXECUTOR_SERVICE.submit(new LayoutWorker(firstSecondThird.getAreaReference(), firstSecondThirdSize));
        EXECUTOR_SERVICE.submit(new LayoutWorker(firstSecondFourth.getAreaReference(), firstSecondFourthSize));
        EXECUTOR_SERVICE.submit(new LayoutWorker(firstThirdFourth.getAreaReference(), firstThirdFourthSize));
        EXECUTOR_SERVICE.submit(new LayoutWorker(secondThirdFourth.getAreaReference(), secondThirdFourthSize));
        EXECUTOR_SERVICE.submit(new LayoutWorker(intersection.getAreaReference(), intersectionSize));

        labelFarLeft(firstOnly.getAreaReference(), secondOnly.getAreaReference(), getFirstLabel());
        labelLeft(secondOnly.getAreaReference(), getSecondLabel());
        labelRight(thirdOnly.getAreaReference(), getThirdLabel());
        labelFarRight(fourthOnly.getAreaReference(), thirdOnly.getAreaReference(), getFourthLabel());
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
     * Return the path node for the third set.
     *
     * @return the path node for the third set
     */
    public PPath getThird()
    {
        return third;
    }

    /**
     * Return the path node for the fourth set.
     *
     * @return the path node for the fourth set
     */
    public PPath getFourth()
    {
        return fourth;
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
     * Return the area node for the third only view.
     *
     * @return the area node for the third only view
     */
    public PArea getThirdOnly()
    {
        return thirdOnly;
    }

    /**
     * Return the area node for the fourth only view.
     *
     * @return the area node for the fourth only view
     */
    public PArea getFourthOnly()
    {
        return fourthOnly;
    }

    /**
     * Return the area node for the first second view.
     *
     * @return the area node for the first second view
     */
    public PArea getFirstSecond()
    {
        return firstSecond;
    }

    /**
     * Return the area node for the first third view.
     *
     * @return the area node for the first third view
     */
    public PArea getFirstThird()
    {
        return firstThird;
    }

    /**
     * Return the area node for the second third view.
     *
     * @return the area node for the second third view
     */
    public PArea getSecondThird()
    {
        return secondThird;
    }

    /**
     * Return the area node for the first fourth view.
     *
     * @return the area node for the first fourth view
     */
    public PArea getFirstFourth()
    {
        return firstFourth;
    }

    /**
     * Return the area node for the second fourth view.
     *
     * @return the area node for the second fourth view
     */
    public PArea getSecondFourth()
    {
        return secondFourth;
    }

    /**
     * Return the area node for the third fourth view.
     *
     * @return the area node for the third fourth view
     */
    public PArea getThirdFourth()
    {
        return thirdFourth;
    }

    /**
     * Return the area node for the first second third view.
     *
     * @return the area node for the first second third view
     */
    public PArea getFirstSecondThird()
    {
        return firstSecondThird;
    }

    /**
     * Return the area node for the first second fourth view.
     *
     * @return the area node for the first second fourth view
     */
    public PArea getFirstSecondFourth()
    {
        return firstSecondFourth;
    }

    /**
     * Return the area node for the first third fourth view.
     *
     * @return the area node for the first third fourth view
     */
    public PArea getFirstThirdFourth()
    {
        return firstThirdFourth;
    }

    /**
     * Return the area node for the second third fourth view.
     *
     * @return the area node for the second third fourth view
     */
    public PArea getSecondThirdFourth()
    {
        return secondThirdFourth;
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

    /**
     * Layout worker.
     */
    private final class LayoutWorker
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
                Rectangle2D bounds = size.getFullBoundsReference();
                Point2D centroid = get();
                size.animateToPositionScaleRotation(centroid.getX() - (bounds.getWidth() / 2.0d),
                                                    centroid.getY() - (bounds.getHeight() / 2.0d), 1.0d, 0.0d, MS);
            }
            catch (Exception e)
            {
                // ignore
            }
        }
    }
}