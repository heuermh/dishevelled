/*

    dsh-piccolo-identify  Piccolo nodes for identifiable beans.
    Copyright (c) 2007-2008 held jointly by the individual authors.

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
import java.awt.geom.RoundRectangle2D;

import javax.swing.UIManager;

import edu.umd.cs.piccolo.nodes.PPath;

import edu.umd.cs.piccolo.util.PBounds;

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
    /** Default icon text gap. */
    private static final double DEFAULT_ICON_TEXT_GAP = 4.0d;

    /** Icon text gap. */
    private final double iconTextGap = DEFAULT_ICON_TEXT_GAP;

    /** Text selection node. */
    private PPath textSelection;


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
    }


    /**
     * Create nodes.
     */
    private void createNodes()
    {
        textSelection = new PPath();

        addChild(getIconBundleImageNode());
        addChild(textSelection);
        addChild(getNameTextNode());

        normal();
    }

    // TODO:  state machine
    public void normal()
    {
        setIconState(IconState.NORMAL);
        getNameTextNode().setTextPaint(Color.BLACK);
        textSelection.setVisible(false);
    }

    public void mouseover()
    {
        setIconState(IconState.MOUSEOVER);
        getNameTextNode().setTextPaint(Color.BLACK);
        textSelection.setVisible(false);
    }

    public void selected()
    {
        setIconState(IconState.SELECTED);
        getNameTextNode().setTextPaint(Color.WHITE);
        textSelection.setPaint(UIManager.getColor("List.selectionBackground"));
        textSelection.setStrokePaint(UIManager.getColor("List.selectionBackground"));
        textSelection.setVisible(true);
    }

    /** {@inheritDoc} */
    protected void layoutChildren()
    {
        PBounds iconBounds = getIconBundleImageNode().getBoundsReference();
        Point2D iconCenter = iconBounds.getCenter2D();
        getIconBundleImageNode().setOffset(-iconCenter.getX(), -iconCenter.getY());

        PBounds textBounds = getNameTextNode().getBoundsReference();
        Point2D textCenter = textBounds.getCenter2D();

        double textSelectionWidthMargin = 2.0d;
        double textSelectionHeightMargin = 2.0d;

        Shape textSelectionRect = new RoundRectangle2D.Double(0.0d, 0.0d,
                                                              textBounds.getWidth() + 2.0d * textSelectionWidthMargin, textBounds.getHeight() + 2.0d * textSelectionHeightMargin,
                                                              4.0d, 4.0d);
        textSelection.setPathTo(textSelectionRect);
        PBounds textSelectionBounds = textSelection.getBoundsReference();
        Point2D textSelectionCenter = textSelectionBounds.getCenter2D();
        textSelection.setOffset(-textSelectionCenter.getX(), iconCenter.getY() + iconTextGap - textSelectionHeightMargin);
        getNameTextNode().setOffset(-textCenter.getX(), iconCenter.getY() + iconTextGap);
    }
}