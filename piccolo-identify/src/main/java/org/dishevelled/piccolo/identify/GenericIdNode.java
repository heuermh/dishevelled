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

import java.awt.geom.Point2D;

import edu.umd.cs.piccolo.util.PBounds;

import org.dishevelled.iconbundle.IconSize;

/**
 * Generic id node.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class GenericIdNode
    extends AbstractIdNode
{
    /** Default icon text gap. */
    private static final double DEFAULT_ICON_TEXT_GAP = 2.0d;

    /** Icon text gap. */
    private final double iconTextGap = DEFAULT_ICON_TEXT_GAP;


    /**
     * Create a new generic id node.
     */
    public GenericIdNode()
    {
        this(null);
    }

    /**
     * Create a new generic id node with the specified value.
     *
     * @param value value for this id node
     */
    public GenericIdNode(final Object value)
    {
        super(value);
        addChild(getIconBundleImageNode());
        addChild(getNameTextNode());
        resetStateMachine();
    }

    /**
     * Create a new generic id node with the specified value and icon size.
     *
     * @param value value for this id node
     * @param iconSize icon size for this id node, must not be null
     */
    public GenericIdNode(final Object value, final IconSize iconSize)
    {
        super(value);
        setIconSize(iconSize);
        addChild(getIconBundleImageNode());
        addChild(getNameTextNode());
        resetStateMachine();
    }


    /** {@inheritDoc} */
    protected void layoutChildren()
    {
        PBounds iconBounds = getIconBundleImageNode().getBoundsReference();
        Point2D iconCenter = iconBounds.getCenter2D();
        getIconBundleImageNode().setOffset(-iconCenter.getX(), -iconCenter.getY());

        PBounds textBounds = getNameTextNode().getBoundsReference();
        Point2D textCenter = textBounds.getCenter2D();
        getNameTextNode().setOffset(-textCenter.getX(), iconCenter.getY() + iconTextGap);
    }
}