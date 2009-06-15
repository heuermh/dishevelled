/*

    dsh-interpolate  Interpolation and easing functions.
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
package org.dishevelled.interpolate;

import java.util.Random;

/**
 * Random threshold interpolation function.  Generates random values (in red)
 * between <code>0.0d</code> and the value returned by a decorated easing function (in blue).
 * <p><img src="../../../../images/random-threshold.png" alt="random threshold graph" /></p>
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class RandomThreshold
    extends AbstractEasingFunction
{
    /** Source of randomness. */
    private final Random random;

    /** Easing function to delegate to. */
    private final AbstractEasingFunction delegate;


    /**
     * Create a new random threshold interpolation function that delegates to the specified easing function.
     *
     * @param random source of randomness, must not be null
     * @param delegate easing function to delegate to, must not be null
     */
    public RandomThreshold(final Random random, final AbstractEasingFunction delegate)
    {
        if (random == null)
        {
            throw new IllegalArgumentException("random must not be null");
        }
        if (delegate == null)
        {
            throw new IllegalArgumentException("delegate must not be null");
        }
        this.random = random;
        this.delegate = delegate;
    }


    /** {@inheritDoc} */
    public String getName()
    {
        return delegate.getName() + ", decorated by random threshold";
    }

    /** {@inheritDoc} */
    public String getDescription()
    {
        return delegate.getDescription() + ", decorated by random threshold";
    }

    /** {@inheritDoc} */
    public final Double evaluate(final Double value)
    {
        double v = delegate.evaluate(value);
        return Math.max(0.0d, random.nextDouble() * v);
    }
}
