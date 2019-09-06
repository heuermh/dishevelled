/*

    dsh-venn  Lightweight components for venn diagrams.
    Copyright (c) 2009-2019 held jointly by the individual authors.

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
package org.dishevelled.venn;

import java.util.Set;

/**
 * Venn diagram model.
 *
 * @param <E> value type
 * @author  Michael Heuer
 */
public interface VennModel<E>
{

    /**
     * Return the number of sets in this venn model.
     *
     * @return the number of sets in this venn model
     */
    int size();

    /**
     * Return the set at the specified index in this venn model.
     *
     * @param index index
     * @return the set at the specified index in this venn model
     * @throws IndexOutOfBoundsException if <code>index</code> is out of bounds
     */
    Set<E> get(int index);

    /**
     * Return an immutable set view of the union of the sets in this venn model.
     *
     * @return an immutable set view of the union of the sets in this venn model
     */
    Set<E> union();

    /**
     * Return an immutable set view of the intersection of the sets in this venn model.
     *
     * @return an immutable set view of the intersection of the sets in this venn model
     */
    Set<E> intersection();

    /**
     * Return an immutable set view of the elements exclusive to the sets in this venn model
     * identified by the specified indices.  For example, for <code>n = 3</code>
     * <pre>
     * VennModel&lt;String&gt; vennModel = ...;
     * Set&lt;String&gt; first = vennModel.get(0);
     * Set&lt;String&gt; second = vennModel.get(1);
     * Set&lt;String&gt; third = vennModel.get(2);
     * Set&lt;String&gt; firstOnly = vennModel.exclusiveTo(0);
     * Set&lt;String&gt; firstSecond = vennModel.exclusiveTo(0, 1);
     * Set&lt;String&gt; intersection = vennModel.exclusiveTo(0, 1, 2);
     * </pre>
     * <p>
     * This is equivalent to the difference of the intersection of the sets in this venn model
     * identified by the specified indices and the union of the remainder sets in this venn model.
     * </p>
     *
     * @param index first index
     * @param additional variable number of additional indices, if any
     * @return an immutable set view of the elements exclusive to the sets in this venn model
     *    identified by the specified indices
     * @throws IndexOutOfBoundsException if <code>index</code> or any of <code>additional</code>
     *    are out of bounds, or if too many indices are specified
     */
    Set<E> exclusiveTo(int index, int... additional);
}
