/*

    dsh-piccolo-venn  Piccolo2D venn diagram nodes and supporting classes.
    Copyright (c) 2009 held jointly by the individual authors.

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

import ca.odell.glazedlists.event.ListEvent;
import ca.odell.glazedlists.event.ListEventListener;

import edu.umd.cs.piccolo.PNode;

import edu.umd.cs.piccolo.nodes.PPath;
import edu.umd.cs.piccolo.nodes.PText;

import org.dishevelled.venn.BinaryVennModel;

import org.dishevelled.venn.model.BinaryVennModelImpl;

/**
 * Binary venn diagram node.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class BinaryVennNode<E>
    extends PNode
{
    /** Binary venn model. */
    private BinaryVennModel<E> model;

    /** Label text for the first list view. */
    private String firstLabelText = DEFAULT_FIRST_LABEL_TEXT;

    /** Label text for the second list view. */
    private String secondLabelText = DEFAULT_SECOND_LABEL_TEXT;

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


    /**
     * Create a new binary venn node with the specified sets.
     *
     * @param firstLabelText label text for the first list view
     * @param first first set, must not be null
     * @param secondLabelText label text for the second list view
     * @param second second set, must not be null
     */
    public BinaryVennNode(final String firstLabelText, final Set<? extends E> first,
            final String secondLabelText, final Set<? extends E> second)
    {
        this(new BinaryVennModelImpl<E>(first, second));
        // todo:  not using firstLabelText, secondLabelText
    }

    /**
     * Create a new binary venn node with the specified model.
     *
     * @param model model for this binary venn node, must not be null
     */
    public BinaryVennNode(final BinaryVennModel<E> model)
    {
        super();
        if (model == null)
        {
            throw new IllegalArgumentException("model must not be null");
        }
        this.model = model;

        firstLabel = new PText();
        first = PPath.createEllipse(0.0f, 0.0f, 128.0f, 128.0f);
        first.setPaint(new Color(80, 80, 80, 80));
        first.setStroke(new BasicStroke(1.0f));
        first.setStrokePaint(new Color(80, 80, 80, 128));
        firstSize = new PText();
        secondLabel = new PText();
        second = PPath.createEllipse(((2.0f * 128.0f) / 3.0f), 0.0f, 128.0f, 128.0f);
        second.setPaint(new Color(80, 80, 80, 80));
        second.setStroke(new BasicStroke(1.0f));
        second.setStrokePaint(new Color(80, 80, 80, 128));
        secondSize = new PText();
        intersection = new AreaNode();
        intersectionSize = new PText();

        addChild(second);
        addChild(first);
        addChild(intersection);
        addChild(secondSize);
        addChild(firstSize);
        addChild(intersectionSize);
        addChild(secondLabel);
        addChild(firstLabel);

        installListeners();
        initLabels();
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
                    firstLabel.setText(buildLabel(firstLabelText, model.first().size()));
                    firstSize.setText(String.valueOf(model.first().size() - model.intersection().size()));
                }
            });

        model.second().addListEventListener(new ListEventListener<E>()
            {
                /** {@inheritDoc} */
                public void listChanged(final ListEvent<E> event)
                {
                    secondLabel.setText(buildLabel(secondLabelText, model.second().size()));
                    secondSize.setText(String.valueOf(model.second().size() - model.intersection().size()));
                }
            });

        model.intersection().addListEventListener(new ListEventListener<E>()
            {
                /** {@inheritDoc} */
                public void listChanged(final ListEvent<E> event)
                {
                    intersectionSize.setText(String.valueOf(model.intersection().size()));
                }
            });
    }

    /**
     * Initialize labels.
     */
    private void initLabels()
    {
        firstLabel.setText(buildLabel(firstLabelText, model.first().size()));
        firstSize.setText(String.valueOf(model.first().size() - model.intersection().size()));
        secondLabel.setText(buildLabel(secondLabelText, model.second().size()));
        secondSize.setText(String.valueOf(model.second().size() - model.intersection().size()));
        intersectionSize.setText(String.valueOf(model.intersection().size()));
    }

    /**
     * Layout nodes.
     */
    // todo:  need to make some of this layout dynamic
    private void layoutNodes()
    {
        Shape firstShape = first.getPathReference();
        Shape secondShape = second.getPathReference();
        Area firstArea = new Area(firstShape);
        Area secondArea = new Area(secondShape);
        firstArea.subtract(new Area(secondShape));
        secondArea.subtract(new Area(firstShape));
        Area intersectionArea = new Area(firstShape);
        intersectionArea.intersect(new Area(secondShape));
        intersection.setArea(intersectionArea);

        Rectangle2D firstBounds = firstArea.getBounds2D();
        Rectangle2D secondBounds = secondArea.getBounds2D();
        Rectangle2D intersectionBounds = intersectionArea.getBounds2D();

        Rectangle2D firstLabelBounds = firstLabel.getFullBoundsReference();
        Rectangle2D secondLabelBounds = secondLabel.getFullBoundsReference();
        Rectangle2D firstSizeBounds = firstSize.getFullBoundsReference();
        Rectangle2D secondSizeBounds = secondSize.getFullBoundsReference();
        Rectangle2D intersectionSizeBounds = intersectionSize.getFullBoundsReference();

        firstLabel.setOffset(firstBounds.getX() + (firstBounds.getWidth() / 2.0d) - (firstLabelBounds.getWidth() / 2.0d),
                             -1.0d * firstLabelBounds.getHeight() - 4.0d);
        secondLabel.setOffset(secondBounds.getX() + (secondBounds.getWidth() / 2.0d) - (secondLabelBounds.getWidth() / 2.0d),
                              -1.0d * secondLabelBounds.getHeight() - 4.0d);
        firstSize.setOffset(firstBounds.getCenterX() - (firstSizeBounds.getWidth() / 2.0d),
                            firstBounds.getCenterY() - (firstSizeBounds.getHeight() / 2.0d));
        secondSize.setOffset(secondBounds.getCenterX() - (secondSizeBounds.getWidth() / 2.0d),
                            secondBounds.getCenterY() - (secondSizeBounds.getHeight() / 2.0d));
        intersectionSize.setOffset(intersectionBounds.getCenterX() - (intersectionSizeBounds.getWidth() / 2.0d),
                                   intersectionBounds.getCenterY() - (intersectionSizeBounds.getHeight() / 2.0d));
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
     * Area node.
     */
    private class AreaNode
        extends PNode
    {

        // todo:  implement this to allow for mouse-over, picking, etc.
        private void setArea(final Area area)
        {
            // empty
        }
    }
}