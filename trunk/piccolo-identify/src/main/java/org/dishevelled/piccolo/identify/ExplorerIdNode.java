/*

    dsh-piccolo-identify  Piccolo2D nodes for identifiable beans.
    Copyright (c) 2007-2010 held jointly by the individual authors.

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
import java.awt.geom.Rectangle2D;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.UIManager;

import edu.umd.cs.piccolo.nodes.PPath;

import edu.umd.cs.piccolo.util.PBounds;

import edu.umd.cs.piccolox.nodes.PShadow;

import org.dishevelled.iconbundle.IconSize;
import org.dishevelled.iconbundle.IconState;

/**
 * Explorer-style id node.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class ExplorerIdNode
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
     * Create a new explorer-style id node.
     */
    public ExplorerIdNode()
    {
        this(null);
    }

    /**
     * Create a new explorer-style id node with the specified value.
     *
     * @param value value for this id node
     */
    public ExplorerIdNode(final Object value)
    {
        super(value);
        createNodes();
        resetStateMachine();
    }

    /**
     * Create a new explorer-style id node with the specified value and icon size.
     *
     * @param value value for this id node
     * @param iconSize icon size for this id node, must not be null
     */
    public ExplorerIdNode(final Object value, final IconSize iconSize)
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
        addChild(textSelection);
        addChild(textShadow);
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
     * Active state.
     */
    private void active()
    {
        setTransparency(1.0f);
        setIconState(IconState.SELECTED);
        getNameTextNode().setTextPaint(Color.WHITE);
        textSelection.setPaint(UIManager.getColor("List.selectionBackground"));
        textSelection.setStrokePaint(UIManager.getColor("List.selectionBackground"));
        textShadow.setVisible(false);
        textSelection.setVisible(true);
    }

    /**
     * Reverse active state.
     */
    private void reverseActive()
    {
        active();
    }

    /**
     * Normal state.
     */
    private void normal()
    {
        setTransparency(1.0f);
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
        setTransparency(1.0f);
        setIconState(IconState.NORMAL);
        getNameTextNode().setTextPaint(Color.WHITE);
        textSelection.setVisible(false);
        textShadow.setVisible(true);
    }

    /**
     * Selected state.
     */
    private void selected()
    {
        setTransparency(1.0f);
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

        PBounds textBounds = getNameTextNode().getBoundsReference();
        Point2D textCenter = textBounds.getCenter2D();

        double textSelectionWidthMargin = Math.min(0.2d * textBounds.getWidth(), 6.0d);
        double textSelectionHeightMargin = Math.min(0.1d * textBounds.getHeight(), 0.5d);

        Shape textSelectionRect = new Rectangle2D.Double(0.0d, 0.0d,
                                                         textBounds.getWidth() + 2 * textSelectionWidthMargin, textBounds.getHeight() + 2 * textSelectionHeightMargin);
        textSelection.setPathTo(textSelectionRect);

        PBounds textSelectionBounds = textSelection.getBoundsReference();
        Point2D textSelectionCenter = textSelectionBounds.getCenter2D();

        getIconBundleImageNode().setOffset(-iconCenter.getX(), -iconCenter.getY());
        textSelection.setOffset(-textSelectionCenter.getX(), iconCenter.getY() + iconTextGap);
        getNameTextNode().setOffset(-textCenter.getX(), iconCenter.getY() + iconTextGap + textSelectionHeightMargin);
        textShadow.setOffset(-textCenter.getX() - (2 * BLUR_RADIUS) + TEXT_SHADOW_OFFSET,
                             iconCenter.getY() + iconTextGap + textSelectionHeightMargin - (2 * BLUR_RADIUS) + TEXT_SHADOW_OFFSET);
    }
}