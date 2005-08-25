/*

    dsh-weighted  Weighted map interface and implementation.
    Copyright (c) 2005 held jointly by the individual authors.

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
package org.dishevelled.weighted;

import java.util.Map;

/**
 * A map of elements to weights with sampling and ranking functionality.
 *
 * <pre>
 * WeightedMap&lt;String&gt; m = new HashWeightedMap&lt;String&gt;();
 * m.put(&quot;foo&quot;, 100.0d);
 * m.put(&quot;bar&quot;, 500.0d);
 * m.put(&quot;baz&quot;, 1000.0d);
 *
 * assert(m.get(&quot;foo&quot;) == 100.0d);
 * assert(m.weight(&quot;foo&quot;) == 100.0d);
 * assert(m.totalWeight() == 1600.0d);
 * assert(m.normalizedWeight(&quot;foo&quot;) == 0.0625d);
 * assert(m.rank(&quot;baz&quot;) == 1);
 * assert(m.rank(&quot;bar&quot;) == 2);
 * assert(m.rank(&quot;foo&quot;) == 3);
 *
 * List&lt;String&gt; list = new ArrayList&lt;String&gt;(100);
 * for (int i = 0; i &lt; 100; i++) {
 *     list.add(m.sample());
 * }
 *
 * assert(cardinality in list of &quot;foo&quot; approximately equal to 6.25)
 * assert(cardinality in list of &quot;bar&quot; approximately equal to 31.25)
 * assert(cardinality in list of &quot;baz&quot; approximately equal to 62.5)
 * </pre>
 *
 * @author  Michael Heuer
 * @author  Mark Schreiber
 * @version $Revision$ $Date$
 */
public interface WeightedMap<E>
    extends Map<E, Double>
{

    /**
     * Randomly sample an element from this weighted map according
     * to its normalized weight.
     *
     * @see #totalWeight
     * @see #normalizedWeight
     * @return a random element from this weighted map according to
     *    its normalized weight, or <code>null</code> if this weighted
     *    map is empty or if the total weight is zero
     */
    E sample();

    /**
     * Return the weight for the specified element in
     * this weighted map.  Returns the same value as <code>get(E e)</code>.
     *
     * @param e element
     * @return the weight for the specified element in this weighted
     *    map, or <code>null</code> if this weighted map is empty
     */
    Double weight(E e);

    /**
     * Return the normalized weight for the specified
     * element in this weighted map.
     *
     * @param e element
     * @return the normalized weight for the specified element in this
     *    weighted map, or <code>null</code> if this weighted map is empty
     */
    Double normalizedWeight(E e);

    /**
     * Return the sum of the weights in this weighted map.
     *
     * @return the sum of the weights in this weighted map
     */
    Double totalWeight();

    /**
     * Return an integer rank for the specified element in this
     * weighted map based on its weight.
     *
     * @param e element
     * @return an integer rank for the specified element in this
     *    weighted map based on its weight, or <code>-1</code> if this
     *    weighted map is empty or if <code>e</code> is not an element
     *    in this weighted map
     */
    int rank(E e);

    /**
     * Return the maximum rank in this weighted map.
     *
     * @return the maximum integer rank in this weighted map
     *    or <code>-1</code> if this weighted map is empty
     */
    int maximumRank();
}
