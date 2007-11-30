/*

    dsh-piccolo-identify  Piccolo nodes for identifiable beans.
    Copyright (c) 2007 held jointly by the individual authors.

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

import javax.swing.UIManager;

import edu.umd.cs.piccolo.nodes.PPath;

import edu.umd.cs.piccolo.util.PBounds;

import org.dishevelled.iconbundle.IconSize;
import org.dishevelled.iconbundle.IconState;

/**
 * Windows XP style id node.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class WinXPIdNode
    extends AbstractIdNode
{
    /** Text selection node. */
    private PPath textSelection;


    /**
     * Create a new Windows XP style id node.
     */
    public WinXPIdNode()
    {
        this(null);
    }

    /**
     * Create a new Windows XP style id node with the specified value.
     *
     * @param value value for this id node
     */
    public WinXPIdNode(final Object value)
    {
        super(value);
        createNodes();
    }

    /**
     * Create a new Windows XP style id node with the specified value and icon size.
     *
     * @param value value for this id node
     * @param iconSize icon size for this id node, must not be null
     */
    public WinXPIdNode(final Object value, final IconSize iconSize)
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

        addChild(textSelection);
        addChild(getIconBundleImageNode());
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

    public void selected()
    {
        // TODO:  selected iconbundle state with a dark selection color is not correct
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

        PBounds textBounds = getNameTextNode().getBoundsReference();
        Point2D textCenter = textBounds.getCenter2D();

        double textSelectionWidthMargin = Math.min(0.2d * textBounds.getWidth(), 6.0d);
        double textSelectionHeightMargin = Math.min(0.1d * textBounds.getHeight(), 2.0d);

        // TODO:  looks a little bit too big, might need to implement this similar to Leopard style
        Shape textSelectionRect = new Rectangle2D.Double(0.0d, 0.0d,
                                                         textBounds.getWidth() + 2 * textSelectionWidthMargin, textBounds.getHeight() + 2 * textSelectionHeightMargin);
        textSelection.setPathTo(textSelectionRect);

        PBounds textSelectionBounds = textSelection.getBoundsReference();
        Point2D textSelectionCenter = textSelectionBounds.getCenter2D();

        getIconBundleImageNode().offset(-iconCenter.getX(), -iconCenter.getY());
        getNameTextNode().offset(-textCenter.getX(), iconCenter.getY() + 1.0d + textSelectionHeightMargin + textSelectionCenter.getY());
        textSelection.offset(-textSelectionCenter.getX(), iconCenter.getY() + 1.0d + textSelectionCenter.getY());
    }
}