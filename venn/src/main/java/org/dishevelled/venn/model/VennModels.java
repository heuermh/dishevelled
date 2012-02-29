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
package org.dishevelled.venn.model;

import java.util.Set;

import org.dishevelled.venn.BinaryVennModel;
import org.dishevelled.venn.TernaryVennModel;
import org.dishevelled.venn.QuaternaryVennModel;

/**
 * Venn models.
 *
 * @author  Michael Heuer
 */
public final class VennModels
{

    /**
     * Private no-arg constructor.
     */
    private VennModels()
    {
        // empty
    }


    /**
     * Create and return a new binary venn model with the specified sets.
     *
     * @param <E> value type
     * @param first first set, must not be null
     * @param second second set, must not be null
     * @return a new binary venn model with the specified sets
     */
    public static <E> BinaryVennModel<E> createVennModel(final Set<? extends E> first,
                                                         final Set<? extends E> second)
    {
        return new BinaryVennModelImpl<E>(first, second);
    }

    /**
     * Create and return a new ternary venn model with the specified sets.
     *
     * @param <E> value type
     * @param first first set, must not be null
     * @param second second set, must not be null
     * @param third third set, must not be null
     * @return a new ternary venn model with the specified sets
     */
    public static <E> TernaryVennModel<E> createVennModel(final Set<? extends E> first,
                                                          final Set<? extends E> second,
                                                          final Set<? extends E> third)
    {
        return new TernaryVennModelImpl<E>(first, second, third);
    }

    /**
     * Create and return a new quaternary venn model with the specified sets.
     *
     * @param <E> value type
     * @param first first set, must not be null
     * @param second second set, must not be null
     * @param third third set, must not be null
     * @param fourth fourth set, must not be null
     * @return a new quaternary venn model with the specified sets
     */
    public static <E> QuaternaryVennModel<E> createVennModel(final Set<? extends E> first,
                                                             final Set<? extends E> second,
                                                             final Set<? extends E> third,
                                                             final Set<? extends E> fourth)
    {
        return new QuaternaryVennModelImpl<E>(first, second, third, fourth);
    }
}