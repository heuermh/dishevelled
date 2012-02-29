/*

    dsh-venn  Lightweight components for venn diagrams.
    Copyright (c) 2009-2012 held jointly by the individual authors.

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
package org.dishevelled.venn.layout;

import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.dishevelled.venn.AbstractBinaryVennLayoutTest;
import org.dishevelled.venn.BinaryVennLayout;

/**
 * Unit test for BinaryVennLayoutImpl.
 *
 * @author  Michael Heuer
 */
public final class BinaryVennLayoutImplTest
    extends AbstractBinaryVennLayoutTest
{

    /** {@inheritDoc} */
    protected BinaryVennLayout createBinaryVennLayout()
    {
        Shape shape = new Rectangle2D.Double(0.0d, 0.0d, 100.0d, 100.0d);
        Point2D center = new Point2D.Double(50.0d, 50.0);
        Rectangle2D boundingRectangle = new Rectangle2D.Double(-10.0d, -10.0d, 110.0d, 110.0d);

        return new BinaryVennLayoutImpl(shape, shape, center, center, center, boundingRectangle);
    }
}