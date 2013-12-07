/*

    dsh-piccolo-identify  Piccolo2D nodes for identifiable beans.
    Copyright (c) 2007-2013 held jointly by the individual authors.

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
package org.dishevelled.piccolo.identify;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;

import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import org.piccolo2d.nodes.PPath;

import org.piccolo2d.util.PBounds;

import org.dishevelled.iconbundle.IconSize;
import org.dishevelled.iconbundle.IconState;

/**
 * Finder-style id node.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class FinderIdNode
    extends AbstractIdNode
{
    /** Icon selection node. */
    private PPath iconSelection;

    /** Text selection node. */
    private PPath textSelection;

    /** Text selection shadow node. */
    private PPath textSelectionShadow;

    /** Text selection paint. */
    private static final Paint TEXT_SELECTION_PAINT = new Color(0, 0, 0, 100);

    /** Text selection selected paint. */
    private static final Paint TEXT_SELECTION_SELECTED_PAINT = new Color(56, 117, 215);

    /** Icon selection stroke. */
    private static final Stroke ICON_SELECTION_STROKE = new BasicStroke(2.0f);

    /** Icon selection stroke paint. */
    private static final Paint ICON_SELECTION_STROKE_PAINT = new Color(130, 130, 130);


    /**
     * Create a new finder-style id node.
     */
    public FinderIdNode()
    {
        this(null);
    }

    /**
     * Create a new finder-style id node with the specified value.
     *
     * @param value value for this id node
     */
    public FinderIdNode(final Object value)
    {
        super(value);
        createNodes();
        resetStateMachine();
    }

    /**
     * Create a new finder-style id node with the specified value and icon size.
     *
     * @param value value for this id node
     * @param iconSize icon size for this id node, must not be null
     */
    public FinderIdNode(final Object value, final IconSize iconSize)
    {
        super(value);
        setIconSize(iconSize);
        createNodes();
        resetStateMachine();
    }


    /**
     * Create nodes.
     */
    private void createNodes()
    {
        iconSelection = new PPath.Double();
        iconSelection.setPaint(TEXT_SELECTION_PAINT);
        iconSelection.setStroke(ICON_SELECTION_STROKE);
        iconSelection.setStrokePaint(ICON_SELECTION_STROKE_PAINT);

        textSelection = new PPath.Double();
        textSelection.setPaint(TEXT_SELECTION_PAINT);
        textSelection.setStroke(null);

        textSelectionShadow = new PPath.Double();
        textSelectionShadow.setPaint(TEXT_SELECTION_PAINT);
        textSelectionShadow.setStroke(null);

        addChild(iconSelection);
        addChild(textSelectionShadow);
        addChild(textSelection);
        addChild(getIconBundleImageNode());
        addChild(getNameTextNode());
    }

    /**
     * Normal state.
     */
    private void normal()
    {
        setTransparency(1.0f);
        getNameTextNode().setTextPaint(Color.BLACK);
        iconSelection.setVisible(false);
        textSelection.setPaint(TEXT_SELECTION_PAINT);
        textSelectionShadow.setVisible(false);
        setIconState(IconState.NORMAL);
    }

    /**
     * Reverse normal state.
     */
    private void reverseNormal()
    {
        setTransparency(1.0f);
        getNameTextNode().setTextPaint(Color.WHITE);
        iconSelection.setVisible(false);
        textSelection.setPaint(TEXT_SELECTION_PAINT);
        textSelectionShadow.setVisible(false);
        setIconState(IconState.NORMAL);
    }

    /**
     * Selected state.
     */
    private void selected()
    {
        setTransparency(1.0f);
        getNameTextNode().setTextPaint(Color.WHITE);
        iconSelection.setVisible(true);
        textSelection.setPaint(TEXT_SELECTION_SELECTED_PAINT);
        textSelectionShadow.setVisible(true);
        setIconState(IconState.NORMAL);
    }

    /**
     * Selected mouseover state.
     */
    private void selectedMouseover()
    {
        selected();
    }

    /**
     * Reverse selected state.
     */
    private void reverseSelected()
    {
        selected();
    }

    /**
     * Reverse selected mouseover state.
     */
    private void reverseSelectedMouseover()
    {
        selected();
    }

    /**
     * Dragging state.
     */
    private void dragging()
    {
        setTransparency(0.66f);
    }

    /**
     * Reverse dragging state.
     */
    private void reverseDragging()
    {
        dragging();
    }

    @Override
    protected void layoutChildren()
    {
        PBounds iconBounds = getIconBundleImageNode().getBoundsReference();
        Point2D iconCenter = iconBounds.getCenter2D();

        double iconSelectionMargin = Math.max(0.1d * iconBounds.getHeight(), 0.1d * iconBounds.getWidth());
        iconSelectionMargin = Math.min(iconSelectionMargin, 2.0d);

        Shape iconSelectionRect = new RoundRectangle2D.Double(0.0d, 0.0d,
                                                              iconBounds.getWidth() + 2.0d * iconSelectionMargin, iconBounds.getHeight() + 2.0d * iconSelectionMargin,
                                                              2.0d, 2.0d);

        iconSelection.reset();
        iconSelection.append(iconSelectionRect, false);

        PBounds iconSelectionBounds = iconSelection.getBoundsReference();
        Point2D iconSelectionCenter = iconSelectionBounds.getCenter2D();

        // unfortunately, the height of the text node is larger than the Mac l&f desires it to be
        //PBounds textBounds = getNameTextNode().getBoundsReference();
        // TODO:  this method doesn't support multi-line text nodes
        FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);
        TextLayout textLayout = new TextLayout(getNameTextNode().getText(), getNameTextNode().getFont(), frc);
        Rectangle2D textBounds = textLayout.getBounds();
        Point2D textCenter = new Point2D.Double(textBounds.getCenterX(), textBounds.getCenterY());
        PBounds textNodeBounds = getNameTextNode().getBoundsReference();
        Point2D textNodeCenter = textNodeBounds.getCenter2D();
        double textHeightAdjustment = (textNodeBounds.getHeight() - textBounds.getHeight()) * 0.66d;

        double textSelectionWidthMargin = 8.0d;
        double textSelectionHeightTopMargin = 2.0d;
        double textSelectionHeightBottomMargin = 1.0d;

        Shape textSelectionRect = new RoundRectangle2D.Double(0.0d, 0.0d,
                                                              textBounds.getWidth() + 2.0d * textSelectionWidthMargin, textBounds.getHeight() + textSelectionHeightTopMargin + textSelectionHeightBottomMargin,
                                                              12.0d, 12.0d);

        textSelection.reset();
        textSelection.append(textSelectionRect, false);
        textSelectionShadow.reset();
        textSelectionShadow.append(textSelectionRect, false);
        PBounds textSelectionBounds = textSelection.getBoundsReference();
        Point2D textSelectionCenter = textSelectionBounds.getCenter2D();

        getIconBundleImageNode().setOffset(-iconCenter.getX(), -iconCenter.getY());
        iconSelection.setOffset(-iconSelectionCenter.getX(), -iconSelectionCenter.getY());
        getNameTextNode().setOffset(-textNodeCenter.getX(), iconCenter.getY() + textSelectionHeightTopMargin + textSelectionCenter.getY() - textHeightAdjustment);
        textSelection.setOffset(-textSelectionCenter.getX(), iconCenter.getY() + textSelectionCenter.getY());
        textSelectionShadow.setOffset(-textSelectionCenter.getX(), iconCenter.getY() + textSelectionCenter.getY() + 1.0d);
    }
}