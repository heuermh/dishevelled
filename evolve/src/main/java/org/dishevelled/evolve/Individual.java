/*

    dsh-evolve  Framework for evolutionary algorithms.
    Copyright (c) 2005-2007 held jointly by the individual authors.

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
package org.dishevelled.evolve;

/**
 * Individual.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface Individual
{

    /**
     * As individuals will be collected into <code>Set</code>s and will
     * be used as keys in <code>WeightedMap</code>s, it is a requirement
     * that each instance of individual be unique.
     *
     * <p>Recommended implementation:
     * <pre>
     * public final boolean equals(final Object o) { return (o == this); }
     * </pre>
     * </p>
     *
     * @param o other object
     * @return true if the specified object is equal to this
     */
    boolean equals(Object o);

    /**
     * As individuals will be collected into <code>Set</code>s and will
     * be used as keys in <code>WeightedMap</code>s, it is recommended
     * that each hash code of an individual be unique.
     *
     * <p>Recommended implementation:
     * <pre>
     * public final int hashCode() { return super.hashCode(); }
     * </pre>
     * (unless a superclass of this other than <code>Object</code> already
     * overrides <code>hashCode()</code>)</p>
     *
     * @return a hash code for this individual
     */
    int hashCode();

    /**
     * Create and return a new shallow copy of this individual.
     *
     * <p>Any or all of the internal state of the new shallow copy
     * may be shared with this individual, but the shallow copy
     * must not be equal to this individual and should have a
     * hash code different from this individual's hash code.</p>
     *
     * @return a new shallow copy of this individual
     */
    Individual shallowCopy();
}
