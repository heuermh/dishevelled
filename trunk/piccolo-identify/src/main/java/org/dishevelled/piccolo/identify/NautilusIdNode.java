/*

    dsh-piccolo-identify  Piccolo2D nodes for identifiable beans.
    Copyright (c) 2007-2009 held jointly by the individual authors.

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

import java.awt.Color;
import java.awt.Shape;

import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.UIManager;

import edu.umd.cs.piccolo.nodes.PPath;

import edu.umd.cs.piccolo.util.PBounds;

import edu.umd.cs.piccolox.nodes.PShadow;

import org.dishevelled.iconbundle.IconSize;
import org.dishevelled.iconbundle.IconState;

/**
 * Nautilus-style id node.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class NautilusIdNode
    extends AbstractIdNode
{
    /** Text selection node. */
    private PPath textSelection;

    /** Text shadow node. */
    private PShadow textShadow;

    /** Icon text gap. */
    private final double iconTextGap = DEFAULT_ICON_TEXT_GAP;

    /** Text shadow blur radius, <code>4</code>. */
    private static final int BLUR_RADIUS = 4;

    /** Text shadow offset, <code>0.5d</code> */
    private static final double TEXT_SHADOW_OFFSET = 0.5d;

    /** Default icon text gap. */
    private static final double DEFAULT_ICON_TEXT_GAP = 2.0d;


    /**
     * Create a new nautilus-style id node.
     */
    public NautilusIdNode()
    {
        this(null);
    }

    /**
     * Create a new nautilus-style id node with the specified value.
     *
     * @param value value for this id node
     */
    public NautilusIdNode(final Object value)
    {
        super(value);
        createNodes();
        resetStateMachine();
    }

    /**
     * Create a new nautilus-style id node with the specified value and icon size.
     *
     * @param value value for this id node
     * @param iconSize icon size for this id node, must not be null
     */
    public NautilusIdNode(final Object value, final IconSize iconSize)
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
        textSelection = new PPath();
        textShadow = new PShadow(getNameTextNode().toImage(), Color.BLACK, BLUR_RADIUS);
        addChild(textShadow);
        addChild(textSelection);
        addChild(getIconBundleImageNode());
        addChild(getNameTextNode());

        // update text shadow on text node property changes
        PropertyChangeListener listener = new PropertyChangeListener()
            {
                /** {@inheritDoc} */
                public void propertyChange(final PropertyChangeEvent event)
                {
                    updateTextShadow();
                }
            };
        getNameTextNode().addPropertyChangeListener("font", listener);
        getNameTextNode().addPropertyChangeListener("text", listener);
    }

    /**
     * Normal state.
     */
    private void normal()
    {
        setIconState(IconState.NORMAL);
        getNameTextNode().setTextPaint(Color.BLACK);
        textSelection.setVisible(false);
        textShadow.setVisible(false);
    }

    /**
     * Reverse normal state.
     */
    private void reverseNormal()
    {
        setIconState(IconState.NORMAL);
        getNameTextNode().setTextPaint(Color.WHITE);
        textSelection.setVisible(false);
        textShadow.setVisible(true);
    }

    /**
     * Active state.
     */
    private void active()
    {
        setIconState(IconState.SELECTED);
        getNameTextNode().setTextPaint(Color.WHITE);
        textSelection.setPaint(UIManager.getColor("List.selectionBackground"));
        textSelection.setStrokePaint(UIManager.getColor("List.selectionBackground"));
        textSelection.setVisible(true);
        textShadow.setVisible(false);
    }

    /**
     * Reverse active state.
     */
    private void reverseActive()
    {
        active();
    }

    /**
     * Mouseover state.
     */
    private void mouseover()
    {
        setIconState(IconState.MOUSEOVER);
        getNameTextNode().setTextPaint(Color.BLACK);
        textSelection.setVisible(false);
        textShadow.setVisible(false);
    }

    /**
     * Reverse mouseover state.
     */
    private void reverseMouseover()
    {
        setIconState(IconState.MOUSEOVER);
        getNameTextNode().setTextPaint(Color.WHITE);
        textSelection.setVisible(false);
        textShadow.setVisible(true);
    }

    /**
     * Selected state.
     */
    private void selected()
    {
        setIconState(IconState.SELECTED);
        getNameTextNode().setTextPaint(Color.WHITE);
        textSelection.setPaint(UIManager.getColor("List.selectionBackground"));
        textSelection.setStrokePaint(UIManager.getColor("List.selectionBackground"));
        textSelection.setVisible(true);
        textShadow.setVisible(false);
    }

    /**
     * Reverse selected state.
     */
    private void reverseSelected()
    {
        selected();
    }

    /**
     * Update text shadow.
     */
    private void updateTextShadow()
    {
        removeChild(textShadow);
        boolean visible = textShadow.getVisible();
        textShadow = new PShadow(getNameTextNode().toImage(), Color.BLACK, BLUR_RADIUS);
        textShadow.setVisible(visible);
        addChild(0, textShadow);
    }

    /** {@inheritDoc} */
    protected void layoutChildren()
    {
        PBounds iconBounds = getIconBundleImageNode().getBoundsReference();
        Point2D iconCenter = iconBounds.getCenter2D();
        getIconBundleImageNode().setOffset(-iconCenter.getX(), -iconCenter.getY());

        PBounds textBounds = getNameTextNode().getBoundsReference();
        Point2D textCenter = textBounds.getCenter2D();

        double textSelectionWidthMargin = 4.0d;
        double textSelectionHeightMargin = 1.0d;

        Shape textSelectionRect = new RoundRectangle2D.Double(0.0d, 0.0d,
                                                              textBounds.getWidth() + 2.0d * textSelectionWidthMargin, textBounds.getHeight() + 2.0d * textSelectionHeightMargin,
                                                              6.0d, 6.0d);
        textSelection.setPathTo(textSelectionRect);
        PBounds textSelectionBounds = textSelection.getBoundsReference();
        Point2D textSelectionCenter = textSelectionBounds.getCenter2D();
        textSelection.setOffset(-textSelectionCenter.getX(), iconCenter.getY() + iconTextGap - textSelectionHeightMargin);
        getNameTextNode().setOffset(-textCenter.getX(), iconCenter.getY() + iconTextGap);
        textShadow.setOffset(-textCenter.getX() - (2 * BLUR_RADIUS) + TEXT_SHADOW_OFFSET,
                             iconCenter.getY() + iconTextGap + textSelectionHeightMargin - (2 * BLUR_RADIUS) + TEXT_SHADOW_OFFSET);
    }
}