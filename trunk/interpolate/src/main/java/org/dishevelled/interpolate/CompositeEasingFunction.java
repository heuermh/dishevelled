/*

    dsh-interpolate  Interpolation and easing functions.
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
package org.dishevelled.interpolate;

/**
 * Composite easing function, <code>g(h(value))</code>.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class CompositeEasingFunction
    implements EasingFunction
{
    /** Easing function g, in <code>g(h(value))</code>. */
    private final EasingFunction g;

    /** Easing function h, in <code>g(h(value))</code>. */
    private final EasingFunction h;


    /**
     * Create a new composite easing function <code>g(h(value))</code> with the
     * specified easing functions <code>g</code> and <code>h</code>.
     *
     * @param g easing function g, in <code>g(h(value))</code>, must not be null
     * @param h easing function h, in <code>g(h(value))</code>, must not be null
     */
    public CompositeEasingFunction(final EasingFunction g, final EasingFunction h)
    {
        if (g == null)
        {
            throw new IllegalArgumentException("g must not be null");
        }
        if (h == null)
        {
            throw new IllegalArgumentException("h must not be null");
        }
        this.g = g;
        this.h = h;
    }


    /** {@inheritDoc} */
    public Double evaluate(final Double value)
    {
        return g.evaluate(h.evaluate(value));
    }
}
