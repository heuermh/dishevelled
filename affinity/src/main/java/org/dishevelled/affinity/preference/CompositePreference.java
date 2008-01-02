/*

    dsh-affinity  Clustering by affinity propagation.
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
package org.dishevelled.affinity.preference;

import java.util.ArrayList;
import java.util.List;

import org.dishevelled.functor.BinaryFunction;
//import cern.colt...functions.DoubleDoubleFunction;

import org.dishevelled.affinity.Preference;

/**
 * Composite preference function.
 *
 * @param <E> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class CompositePreference<E>
    implements Preference<E>
{
    /** List of child preference functions. */
    private final List<Preference<? super E>> children;

    /** Aggregate function. */
    private final BinaryFunction<Double, Double, Double> aggregate;
    //private final DoubleDoubleFunction aggregate;


    /**
     * Create a new composite preference function with the specified list
     * of child preference functions and the specified aggregate function.
     *
     * @param children list of child preference functions, must not be
     *    null and must contain at least one preference function
     * @param aggregate aggregate function, must not be null
     */
    public CompositePreference(final List<Preference<? super E>> children,
                               final BinaryFunction<Double, Double, Double> aggregate)
    //                               final DoubleDoubleFunction aggregate)
    {
        if (children == null)
        {
            throw new IllegalArgumentException("children must not be null");
        }
        if (children.isEmpty())
        {
            throw new IllegalArgumentException("children must contain at least one preference function");
        }
        if (aggregate == null)
        {
            throw new IllegalArgumentException("aggregate must not be null");
        }
        this.children = new ArrayList<Preference<? super E>>(children);
        this.aggregate = aggregate;
    }


    /** {@inheritDoc} */
    public double preference(final E value)
    {
        if (value == null)
        {
            throw new IllegalArgumentException("value must not be null");
        }
        int last = children.size() - 1;
        double rv = children.get(last).preference(value);
        for (int i = last; --i >= 0;)
        {
            rv = aggregate.evaluate(rv, children.get(i).preference(value));
        }
        return rv;
    }
}