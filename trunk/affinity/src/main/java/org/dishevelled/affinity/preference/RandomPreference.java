/*

    dsh-affinity  Clustering by affinity propagation.
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
package org.dishevelled.affinity.preference;

import java.util.Random;

import org.dishevelled.affinity.Preference;

/**
 * Random preference function.
 *
 * @param <E> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class RandomPreference<E>
    implements Preference<E>
{
    /** Source of randomness for this random preference function. */
    private final Random random;


    /**
     * Create a new random preference function with a default source of randomness.
     */
    public RandomPreference()
    {
        random = new Random();
    }


    /**
     * Create a new random preference function with the specified source of randomness.
     *
     * @param random source of randomness for this random preference funtion, must not be null
     */
    public RandomPreference(final Random random)
    {
        if (random == null)
        {
            throw new IllegalArgumentException("random must not be null");
        }
        this.random = random;
    }


    /** {@inheritDoc} */
    public double preference(final E value)
    {
        if (value == null)
        {
            throw new IllegalArgumentException("value must not be null");
        }
        return random.nextDouble();
    }
}