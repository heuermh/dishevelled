/*

    dsh-piccolo-venn  Piccolo2D venn diagram nodes and supporting classes.
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
package org.dishevelled.piccolo.venn.layout;

import java.awt.geom.Rectangle2D;

import org.dishevelled.venn.BinaryVennModel;
import org.dishevelled.venn.TernaryVennModel;
import org.dishevelled.venn.QuaternaryVennModel;

import org.dishevelled.piccolo.venn.BinaryVennLayout;
import org.dishevelled.piccolo.venn.TernaryVennLayout;
import org.dishevelled.piccolo.venn.QuaternaryVennLayout;
import org.dishevelled.piccolo.venn.VennLayout;

/**
 * Venn layout.
 *
 * @author  Michael Heuer
 */
public final class VennLayoutImpl implements VennLayout
{

    /** {@inheritDoc} */
    public BinaryVennLayout layout(final BinaryVennModel<?> model,
                                   final Rectangle2D boundingRectangle,
                                   final PerformanceHint performanceHint)
    {
        if (model == null)
        {
            throw new IllegalArgumentException("model must not be null");
        }
        if (boundingRectangle == null)
        {
            throw new IllegalArgumentException("boundingRectangle must not be null");
        }
        if (performanceHint == null)
        {
            throw new IllegalArgumentException("performanceHint must not be null");
        }
        return null;
    }

    /** {@inheritDoc} */
    public TernaryVennLayout layout(final TernaryVennModel<?> model,
                                    final Rectangle2D boundingRectangle,
                                    final PerformanceHint performanceHint)
    {
        if (model == null)
        {
            throw new IllegalArgumentException("model must not be null");
        }
        if (boundingRectangle == null)
        {
            throw new IllegalArgumentException("boundingRectangle must not be null");
        }
        if (performanceHint == null)
        {
            throw new IllegalArgumentException("performanceHint must not be null");
        }
        return null;
    }

    /** {@inheritDoc} */
    public QuaternaryVennLayout layout(final QuaternaryVennModel<?> model,
                                       final Rectangle2D boundingRectangle,
                                       final PerformanceHint performanceHint)
    {
        if (model == null)
        {
            throw new IllegalArgumentException("model must not be null");
        }
        if (boundingRectangle == null)
        {
            throw new IllegalArgumentException("boundingRectangle must not be null");
        }
        if (performanceHint == null)
        {
            throw new IllegalArgumentException("performanceHint must not be null");
        }
        return null;
    }
}