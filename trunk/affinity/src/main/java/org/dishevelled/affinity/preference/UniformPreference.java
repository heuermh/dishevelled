/*

    dsh-affinity  Clustering by affinity propagation.
    Copyright (c) 2007 held jointly by the individual authors.

    This library is free software; you can redistribute it and/or modify it
    under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation; either version 2.1 of the License, or (at
    your option) any later version.

    This library is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; with out even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
    License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this library;  if not, write to the Free Software Foundation,
    Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA.

    > http://www.gnu.org/copyleft/lesser.html
    > http://www.opensource.org/licenses/lgpl-license.php

*/
package org.dishevelled.affinity.preference;

import org.dishevelled.affinity.Preference;

/**
 * Uniform preference function.
 *
 * @param <E> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class UniformPreference<E>
    implements Preference<E>
{
    /** Preference for this uniform preference function. */
    private final double preference;

    /** Default preference. */
    static final double DEFAULT_PREFERENCE = 1.0d;


    /**
     * Create a new uniform preference function with the default preference of <code>1.0d</code>.
     */
    public UniformPreference()
    {
        preference = DEFAULT_PREFERENCE;
    }


    /**
     * Create a new uniform preference function with the specified preference.
     *
     * @param preference preference for this uniform preference function
     */
    public UniformPreference(final double preference)
    {
        this.preference = preference;
    }


    /** {@inheritDoc} */
    public double preference(final E value)
    {
        if (value == null)
        {
            throw new IllegalArgumentException("value must not be null");
        }
        return preference;
    }
}