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

import ca.odell.glazedlists.event.ListEvent;
import ca.odell.glazedlists.event.ListEventListener;

import edu.umd.cs.piccolo.PNode;

import edu.umd.cs.piccolo.nodes.PPath;
import edu.umd.cs.piccolo.nodes.PText;

import org.dishevelled.venn.QuaternaryVennModel;

import org.dishevelled.venn.model.QuaternaryVennModelImpl;

/**
 * Quaternary venn diagram node.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class QuaternaryVennNode<E>
    extends PNode
{
    /** Quaternary venn model. */
    private QuaternaryVennModel<E> model;

    /** Label text for the first list view. */
    private String firstLabelText = DEFAULT_FIRST_LABEL_TEXT;

    /** Label text for the second list view. */
    private String secondLabelText = DEFAULT_SECOND_LABEL_TEXT;

    /** Label text for the third list view. */
    private String thirdLabelText = DEFAULT_THIRD_LABEL_TEXT;

    /** Label text for the fourth list view. */
    private String fourthLabelText = DEFAULT_FOURTH_LABEL_TEXT;

    /** Label for the first list view. */
    private final PText firstLabel;

    /** Path node for the first list view. */
    private final PPath first;

    /** Label for the first list view's size. */
    private final PText firstSize;

    /** Label for the second list view. */
    private final PText secondLabel;

    /** Path node for the second list view. */
    private final PPath second;

    /** Label for the second list view's size. */
    private final PText secondSize;

    /** Label for the third list view. */
    private final PText thirdLabel;

    /** Path node for the third list view. */
    private final PPath third;

    /** Label for the third list view's size. */
    private final PText thirdSize;

    /** Label for the fourth list view. */
    private final PText fourthLabel;

    /** Path node for the fourth list view. */
    private final PPath fourth;

    /** Label for the fourth list view's size. */
    private final PText fourthSize;

    /*
    Areas/labels:

    1 - 2 - 3 - 4  first
    2 - 1 - 3 - 4 second
    3 - 1 - 2 - 4 third
    4 - 1 - 2 - 3 fourth

    (1 n 2) - 3 - 4 firstSecond
    (1 n 3) - 2 - 4 firstThird
    (1 n 4) - 2 - 3 firstFourth
    (2 n 3) - 1 - 4 secondThird
    (2 n 4) - 1 - 3 secondFourth
    (3 n 4) - 1 - 2 thirdFourth

    (1 n 2 n 3) - 4 firstSecondThird
    (1 n 2 n 4) - 3 firstSecondFourth
    (1 n 3 n 4) - 2 firstThirdFourth
    (2 n 3 n 4) - 1secondThirdFourth

    1 n 2 n 3 n 4 intersection
     */

    /** Area node for the intersection of the first and second sets. */
    private final AreaNode firstSecond;

    /** Label for the intersection of the first and second sets. */
    private final PText firstSecondSize;

    /** Area node for the intersection of the first and third sets. */
    private final AreaNode firstThird;

    /** Label for the intersection of the first and third sets. */
    private final PText firstThirdSize;

    /** Area node for the intersection of the first and fourth sets. */
    private final AreaNode firstFourth;

    /** Label for the intersection of the first and fourth sets. */
    private final PText firstFourthSize;

    /** Area node for the intersection of the second and third sets. */
    private final AreaNode secondThird;

    /** Label for the intersection of the second and third sets. */
    private final PText secondThirdSize;

    /** Area node for the intersection of the second and fourth sets. */
    private final AreaNode secondFourth;

    /** Label for the intersection of the second and fourth sets. */
    private final PText secondFourthSize;

    /** Area node for the intersection of the third and fourth sets. */
    private final AreaNode thirdFourth;

    /** Label for the intersection of the third and fourth sets. */
    private final PText thirdFourthSize;

    /** Area node for the intersection of the first, second, and third sets. */
    private final AreaNode firstSecondThird;

    /** Label for the intersection of the first, second, and third sets. */
    private final PText firstSecondThirdSize;

    /** Area node for the intersection of the first, second, and fourth sets. */
    private final AreaNode firstSecondFourth;

    /** Label for the intersection of the first, second, and fourth sets. */
    private final PText firstSecondFourthSize;

    /** Area node for the intersection of the first, third, and fourth sets. */
    private final AreaNode firstThirdFourth;

    /** Label for the intersection of the first, third, and fourth sets. */
    private final PText firstThirdFourthSize;

    /** Area node for the intersection of the second, third, and fourth sets. */
    private final AreaNode secondThirdFourth;

    /** Label for the intersection of the second, third, and fourth sets. */
    private final PText secondThirdFourthSize;

    /** Area node for the intersection. */
    private final AreaNode intersection;

    /** Label for the intersection's size. */
    private final PText intersectionSize;

    /** True if labels should display sizes. */
    private static final boolean SHOW_SIZES = true;

    /** Default label text for the first list view, <code>"First set"</code>. */
    public static final String DEFAULT_FIRST_LABEL_TEXT = "First set";

    /** Default label text for the second list view, <code>"Second set"</code>. */
    public static final String DEFAULT_SECOND_LABEL_TEXT = "Second set";

    /** Default label text for the third list view, <code>"Third set"</code>. */
    public static final String DEFAULT_THIRD_LABEL_TEXT = "Third set";

    /** Default label text for the third list view, <code>"Fourth set"</code>. */
    public static final String DEFAULT_FOURTH_LABEL_TEXT = "Fourth set";


    /**
     * Create a new quaternary venn node with the specified sets.
     *
     * @param firstLabelText label text for the first list view
     * @param first first set, must not be null
     * @param secondLabelText label text for the second list view
     * @param second second set, must not be null
     * @param thirdLabelText label text for the third list view
     * @param third third set, must not be null
     * @param fourthLabelText label text for the fourth list view
     * @param fourth fourth set, must not be null
     */
    public QuaternaryVennNode(final String firstLabelText, final Set<? extends E> first,
                              final String secondLabelText, final Set<? extends E> second,
                              final String thirdLabelText, final Set<? extends E> third,
                              final String fourthLabelText, final Set<? extends E> fourth)
    {
        this(new QuaternaryVennModelImpl<E>(first, second, third, fourth));

        this.firstLabelText = firstLabelText;
        this.secondLabelText = secondLabelText;
        this.thirdLabelText = thirdLabelText;
        this.fourthLabelText = fourthLabelText;
        updateLabels();
    }

    /**
     * Create a new quaternary venn node with the specified model.
     *
     * @param model model for this quaternary venn node, must not be null
     */
    public QuaternaryVennNode(final QuaternaryVennModel<E> model)
    {
        super();
        if (model == null)
        {
            throw new IllegalArgumentException("model must not be null");
        }
        this.model = model;

        // todo:  match reference
        //   using colors from wikipedia image, choose others or attribute correctly
        // todo:  doesn't appear that translate figures into the bounds calc in layout
        Ellipse2D ellipse = new Ellipse2D.Double(0.0d, 0.0d, 376.0d, 234.0d);
        Point2D center = new Point2D.Double(ellipse.getBounds2D().getCenterX(), ellipse.getBounds2D().getCenterY());
        Area firstArea = new Area(ellipse);
        firstArea = firstArea.createTransformedArea(AffineTransform.getRotateInstance((2.0d * Math.PI) / 9.0d, center.getX(), center.getY()));
        first = new PPath();
        first.setPathTo(firstArea);

        first.setPaint(new Color(30, 30, 30, 50));
        first.setStroke(new BasicStroke(0.5f));
        first.setStrokePaint(new Color(20, 20, 20));
        firstLabel = new PText();
        firstSize = new PText();

        Area secondArea = new Area(ellipse);
        secondArea = secondArea.createTransformedArea(AffineTransform.getRotateInstance((2.0d * Math.PI) / 9.0d, center.getX(), center.getY()));
        secondArea = secondArea.createTransformedArea(AffineTransform.getTranslateInstance(72.0d, -85.0d));
        second = new PPath();
        second.setPathTo(secondArea);

        second.setPaint(new Color(5, 37, 255, 50));
        second.setStroke(new BasicStroke(0.5f));
        second.setStrokePaint(new Color(20, 20, 20));
        secondLabel = new PText();
        secondSize = new PText();

        Area thirdArea = new Area(ellipse);
        thirdArea = thirdArea.createTransformedArea(AffineTransform.getRotateInstance((-2.0d * Math.PI) / 9.0d, center.getX(), center.getY()));
        thirdArea = thirdArea.createTransformedArea(AffineTransform.getTranslateInstance(72.0d, -85.0d));
        third = new PPath();
        third.setPathTo(thirdArea);

        third.setPaint(new Color(255, 100, 5, 50));
        third.setStroke(new BasicStroke(0.5f));
        third.setStrokePaint(new Color(20, 20, 20));
        thirdLabel = new PText();
        thirdSize = new PText();

        Area fourthArea = new Area(ellipse);
        fourthArea = fourthArea.createTransformedArea(AffineTransform.getRotateInstance((-2.0d * Math.PI) / 9.0d, center.getX(), center.getY()));
        fourthArea = fourthArea.createTransformedArea(AffineTransform.getTranslateInstance(144.0d, 0.0d));
        fourth = new PPath();
        fourth.setPathTo(fourthArea);

        fourth.setPaint(new Color(11, 255, 5, 50));
        fourth.setStroke(new BasicStroke(0.5f));
        fourth.setStrokePaint(new Color(20, 20, 20));
        fourthLabel = new PText();
        fourthSize = new PText();

        intersection = new AreaNode();
        intersectionSize = new PText();

        firstSecond = new AreaNode();
        firstSecondSize = new PText();
        firstThird = new AreaNode();
        firstThirdSize = new PText();
        firstFourth = new AreaNode();
        firstFourthSize = new PText();
        secondThird = new AreaNode();
        secondThirdSize = new PText();
        secondFourth = new AreaNode();
        secondFourthSize = new PText();
        thirdFourth = new AreaNode();
        thirdFourthSize = new PText();

        firstSecondThird = new AreaNode();
        firstSecondThirdSize = new PText();
        firstSecondFourth = new AreaNode();
        firstSecondFourthSize = new PText();
        firstThirdFourth = new AreaNode();
        firstThirdFourthSize = new PText();
        secondThirdFourth = new AreaNode();
        secondThirdFourthSize = new PText();

        addChild(fourth);
        addChild(third);
        addChild(second);
        addChild(first);
        addChild(firstSecond);
        addChild(firstThird);
        addChild(firstFourth);
        addChild(secondThird);
        addChild(secondFourth);
        addChild(thirdFourth);
        addChild(firstSecondThird);
        addChild(firstSecondFourth);
        addChild(firstThirdFourth);
        addChild(secondThirdFourth);
        addChild(intersection);
        addChild(fourthSize);
        addChild(thirdSize);
        addChild(secondSize);
        addChild(firstSize);
        addChild(firstSecondSize);
        addChild(firstThirdSize);
        addChild(firstFourthSize);
        addChild(secondThirdSize);
        addChild(secondFourthSize);
        addChild(thirdFourthSize);
        addChild(firstSecondThirdSize);
        addChild(firstSecondFourthSize);
        addChild(firstThirdFourthSize);
        addChild(secondThirdFourthSize);
        addChild(intersectionSize);
        addChild(fourthLabel);
        addChild(thirdLabel);
        addChild(secondLabel);
        addChild(firstLabel);

        installListeners();
        updateLabels();
        layoutNodes();
    }


    /**
     * Install listeners.
     */
    private void installListeners()
    {
        model.first().addListEventListener(new ListEventListener<E>()
            {
                /** {@inheritDoc} */
                public void listChanged(final ListEvent<E> event)
                {
                    updateLabels();
                }
            });

        model.second().addListEventListener(new ListEventListener<E>()
            {
                /** {@inheritDoc} */
                public void listChanged(final ListEvent<E> event)
                {
                    updateLabels();
                }
            });

        model.third().addListEventListener(new ListEventListener<E>()
            {
                /** {@inheritDoc} */
                public void listChanged(final ListEvent<E> event)
                {
                    updateLabels();
                }
            });

        model.fourth().addListEventListener(new ListEventListener<E>()
            {
                /** {@inheritDoc} */
                public void listChanged(final ListEvent<E> event)
                {
                    updateLabels();
                }
            });
    }

    /**
     * Update labels.
     */
    private void updateLabels()
    {
        int f = model.first().size();
        int s = model.second().size();
        int t = model.third().size();
        int r = model.fourth().size();
        int i = model.intersection().size();

        firstLabel.setText(buildLabel(firstLabelText, f));
        secondLabel.setText(buildLabel(secondLabelText, s));
        thirdLabel.setText(buildLabel(thirdLabelText, t));
        fourthLabel.setText(buildLabel(fourthLabelText, r));
        intersectionSize.setText(String.valueOf(i));

        int fst = model.intersect(model.first(), model.second(), model.third()).size() - i;
        int fsr = model.intersect(model.first(), model.second(), model.fourth()).size() - i;
        int ftr = model.intersect(model.first(), model.third(), model.fourth()).size() - i;
        int str = model.intersect(model.second(), model.third(), model.fourth()).size() - i;

        firstSecondThirdSize.setText(String.valueOf(fst));
        firstSecondFourthSize.setText(String.valueOf(fsr));
        firstThirdFourthSize.setText(String.valueOf(ftr));
        secondThirdFourthSize.setText(String.valueOf(str));

        int fs = model.intersect(model.first(), model.second()).size() - fst - fsr - i;
        int ft = model.intersect(model.first(), model.third()).size() - fst - ftr - i;
        int fr = model.intersect(model.first(), model.fourth()).size() - fsr - ftr - i;
        int st = model.intersect(model.second(), model.third()).size() - fst - str - i;
        int sr = model.intersect(model.second(), model.fourth()).size() - fsr - str - i;
        int tr = model.intersect(model.third(), model.fourth()).size() - ftr - str - i;

        firstSecondSize.setText(String.valueOf(fs));
        firstThirdSize.setText(String.valueOf(ft));
        firstFourthSize.setText(String.valueOf(fr));
        secondThirdSize.setText(String.valueOf(st));
        secondFourthSize.setText(String.valueOf(sr));
        thirdFourthSize.setText(String.valueOf(tr));

        firstSize.setText(String.valueOf(f - fs - ft - fr - fst - fsr - ftr - i));
        secondSize.setText(String.valueOf(s - st - sr - fst - fsr - str - i));
        thirdSize.setText(String.valueOf(t - ft - st - tr - fst - ftr - str - i));
        fourthSize.setText(String.valueOf(r - fr - sr - fsr - ftr - str - i));
    }

    /**
     * Layout nodes.
     */
    // todo:  need to make some of this layout dynamic
    private void layoutNodes()
    {
        Area f = new Area(first.getPathReference());
        Area s = new Area(second.getPathReference());
        Area t = new Area(third.getPathReference());
        Area r = new Area(fourth.getPathReference());
        Area fs = new Area();
        Area ft = new Area();
        Area fr = new Area();
        Area st = new Area();
        Area sr = new Area();
        Area tr = new Area();
        Area fst = new Area();
        Area fsr = new Area();
        Area ftr = new Area();
        Area str = new Area();
        Area i = new Area();

        fs.add(f);
        fs.intersect(s);
        fs.subtract(t);
        fs.subtract(r);

        ft.add(f);
        ft.intersect(t);
        ft.subtract(s);
        ft.subtract(r);

        fr.add(f);
        fr.intersect(r);
        fr.subtract(s);
        fr.subtract(t);

        st.add(s);
        st.intersect(t);
        st.subtract(f);
        st.subtract(r);

        sr.add(s);
        sr.intersect(r);
        sr.subtract(f);
        sr.subtract(t);

        tr.add(t);
        tr.intersect(r);
        tr.subtract(f);
        tr.subtract(s);

        fst.add(f);
        fst.intersect(s);
        fst.intersect(t);
        fst.subtract(r);

        fsr.add(f);
        fsr.intersect(s);
        fsr.intersect(r);
        fsr.subtract(t);
        
        ftr.add(f);
        ftr.intersect(t);
        ftr.intersect(r);
        ftr.subtract(s);

        str.add(s);
        str.intersect(t);
        str.intersect(r);
        str.subtract(f);

        i.add(f);
        i.intersect(s);
        i.intersect(t);
        i.intersect(r);

        Area _f = new Area(f);
        _f.subtract(s);
        _f.subtract(t);
        _f.subtract(r);

        Area _s = new Area(s);
        _s.subtract(f);
        _s.subtract(t);
        _s.subtract(r);

        Area _t = new Area(t);
        _t.subtract(f);
        _t.subtract(s);
        _t.subtract(r);

        Area _r = new Area(r);
        _r.subtract(f);
        _r.subtract(s);
        _r.subtract(t);

        f = _f;
        s = _s;
        t = _t;
        r = _r;

        firstSecond.setArea(fs);
        firstThird.setArea(ft);
        firstFourth.setArea(fr);
        secondThird.setArea(st);
        secondFourth.setArea(sr);
        thirdFourth.setArea(tr);
        firstSecondThird.setArea(fst);
        firstSecondFourth.setArea(fsr);
        firstThirdFourth.setArea(ftr);
        secondThirdFourth.setArea(str);
        intersection.setArea(i);

        offset(f, firstSize);
        offset(s, secondSize);
        offset(t, thirdSize);
        offset(r, fourthSize);
        offset(fs, firstSecondSize);
        offset(ft, firstThirdSize);
        offset(fr, firstFourthSize);
        offset(st, secondThirdSize);
        offset(sr, secondFourthSize);
        offset(tr, thirdFourthSize);
        offset(fst, firstSecondThirdSize);
        offset(fsr, firstSecondFourthSize);
        offset(ftr, firstThirdFourthSize);
        offset(str, secondThirdFourthSize);
        offset(i, intersectionSize);

        labelLeft(f, firstLabel);
        labelLeft(s, secondLabel);
        labelRight(t, thirdLabel);
        labelRight(r, fourthLabel);
    }

    private Rectangle2D a = new Rectangle2D.Double();
    private Rectangle2D b = new Rectangle2D.Double();
    private Point2D c = new Point2D.Double();

    private void offset(final Area area, final PText size)
    {
        b = size.getFullBoundsReference();
        c = Centers.centroidOf(area, c);
        size.setOffset(c.getX() - (b.getWidth() / 2.0d), c.getY() - (b.getHeight() / 2.0d));
    }

    private void labelLeft(final Area area, final PText label)
    {
        a = area.getBounds2D();
        b = label.getFullBoundsReference();
        c = Centers.centroidOf(area, c);
        label.setOffset(c.getX() - ((2.0d * b.getWidth()) / 3.0d) - 28.0d, a.getY() - b.getHeight() - 8.0d);
    }

    private void labelRight(final Area area, final PText label)
    {
        a = area.getBounds2D();
        b = label.getFullBoundsReference();
        c = Centers.centroidOf(area, c);
        label.setOffset(c.getX() - (b.getWidth() / 3.0d) + 28.0d, a.getY() - b.getHeight() - 8.0d);
    }

    /**
     * Build and return label text.
     *
     * @param labelText label text
     * @param size size
     * @return label text
     */
    private String buildLabel(final String labelText, final int size)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(labelText);
        if (SHOW_SIZES)
        {
            sb.append(" (");
            sb.append(size);
            sb.append(")");
        }
        sb.append(":");
        return sb.toString();
    }

    /**
     * Return the label text for the first list view.  Defaults to {@link #DEFAULT_FIRST_LABEL_TEXT}.
     *
     * @return the label text for the first list view
     */
    public String getFirstLabelText()
    {
        return firstLabelText;
    }

    /**
     * Set the label text for the first list view to <code>firstLabelText</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param firstLabelText label text for the first list view
     */
    public void setFirstLabelText(final String firstLabelText)
    {
        String oldFirstLabelText = this.firstLabelText;
        this.firstLabelText = firstLabelText;
        firstLabel.setText(buildLabel(this.firstLabelText, model.first().size()));
        firePropertyChange(-1, "firstLabelText", this.firstLabelText, oldFirstLabelText);
    }

    /**
     * Return the label text for the second list view.  Defaults to {@link #DEFAULT_SECOND_LABEL_TEXT}.
     *
     * @return the label text for the second list view
     */
    public String getSecondLabelText()
    {
        return secondLabelText;
    }

    /**
     * Set the label text for the second list view to <code>secondLabelText</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param secondLabelText label text for the second list view
     */
    public void setSecondLabelText(final String secondLabelText)
    {
        String oldSecondLabelText = this.secondLabelText;
        this.secondLabelText = secondLabelText;
        secondLabel.setText(buildLabel(this.secondLabelText, model.second().size()));
        firePropertyChange(-1, "secondLabelText", this.secondLabelText, oldSecondLabelText);
    }

    /**
     * Return the label text for the third list view.  Defaults to {@link #DEFAULT_THIRD_LABEL_TEXT}.
     *
     * @return the label text for the third list view
     */
    public String getThirdLabelText()
    {
        return thirdLabelText;
    }

    /**
     * Set the label text for the third list view to <code>thirdLabelText</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param thirdLabelText label text for the third list view
     */
    public void setThirdLabelText(final String thirdLabelText)
    {
        String oldThirdLabelText = this.thirdLabelText;
        this.thirdLabelText = thirdLabelText;
        thirdLabel.setText(buildLabel(this.thirdLabelText, model.third().size()));
        firePropertyChange(-1, "thirdLabelText", this.thirdLabelText, oldThirdLabelText);
    }

    /**
     * Return the label text for the fourth list view.  Defaults to {@link #DEFAULT_FOURTH_LABEL_TEXT}.
     *
     * @return the label text for the fourth list view
     */
    public String getFourthLabelText()
    {
        return fourthLabelText;
    }

    /**
     * Set the label text for the fourth list view to <code>fourthLabelText</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param fourthLabelText label text for the fourth list view
     */
    public void setFourthLabelText(final String fourthLabelText)
    {
        String oldFourthLabelText = this.fourthLabelText;
        this.fourthLabelText = fourthLabelText;
        fourthLabel.setText(buildLabel(this.fourthLabelText, model.fourth().size()));
        firePropertyChange(-1, "fourthLabelText", this.fourthLabelText, oldFourthLabelText);
    }

    public QuaternaryVennModel<E> getModel()
    {
        return model;
    }

    /**
     * Area node.
     */
    private class AreaNode
        extends PNode
    {
        //private PPath path = null;

        // todo:  implement this to allow for mouse-over, picking, etc.
        private void setArea(final Area area)
        {
            //Rectangle2D rect = area.getBounds2D();
            //path = PPath.createRectangle((float) rect.getX(), (float) rect.getY(), (float) rect.getWidth(), (float) rect.getHeight());
            //path.setPaint(new Color(80, 80, 80, 20));
            //path.setStrokePaint(new Color(80, 80, 80, 80));
            //addChild(path);
        }
    }
}