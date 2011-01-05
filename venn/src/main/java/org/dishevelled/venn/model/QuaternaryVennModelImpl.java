/*

    dsh-venn  Lightweight components for venn diagrams.
    Copyright (c) 2009-2011 held jointly by the individual authors.

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

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Sets;

import org.dishevelled.observable.ObservableSet;

import org.dishevelled.observable.impl.ObservableSetImpl;

import org.dishevelled.venn.QuaternaryVennModel;

/**
 * Quaternary venn model implementation.
 *
 * @param <E> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class QuaternaryVennModelImpl<E>
    implements QuaternaryVennModel<E>
{
    /** First view. */
    private final ObservableSet<E> first;

    /** Second view. */
    private final ObservableSet<E> second;

    /** Third view. */
    private final ObservableSet<E> third;

    /** Fourth view. */
    private final ObservableSet<E> fourth;

    /** First only view. */
    private final Set<E> firstOnly;

    /** Second only view. */
    private final Set<E> secondOnly;

    /** Third only view. */
    private final Set<E> thirdOnly;

    /** Fourth only view. */
    private final Set<E> fourthOnly;

    /** First second view. */
    private final Set<E> firstSecond;

    /** First third view. */
    private final Set<E> firstThird;

    /** First fourth view. */
    private final Set<E> firstFourth;

    /** Second third view. */
    private final Set<E> secondThird;

    /** Second fourth view. */
    private final Set<E> secondFourth;

    /** Third fourth view. */
    private final Set<E> thirdFourth;

    /** First second third view. */
    private final Set<E> firstSecondThird;

    /** First second fourth view. */
    private final Set<E> firstSecondFourth;

    /** First third fourth view. */
    private final Set<E> firstThirdFourth;

    /** Second third fourth view. */
    private final Set<E> secondThirdFourth;

    /** Intersection view. */
    private final Set<E> intersection;

    /** Union view. */
    private final Set<E> union;

    /** Selection view. */
    private final ObservableSet<E> selection;


    /**
     * Create a new empty quaternary venn model.
     */
    public QuaternaryVennModelImpl()
    {
        this(new HashSet<E>(), new HashSet<E>(), new HashSet<E>(), new HashSet<E>());
    }

    /**
     * Create a new quaternary venn model with the specified sets.
     *
     * @param first first set, must not be null
     * @param second second set, must not be null
     * @param third third set, must not be null
     * @param fourth fourth set, must not be null
     */
    public QuaternaryVennModelImpl(final Set<? extends E> first,
                                    final Set<? extends E> second,
                                    final Set<? extends E> third,
                                    final Set<? extends E> fourth)
    {
        if (first == null)
        {
            throw new IllegalArgumentException("first must not be null");
        }
        if (second == null)
        {
            throw new IllegalArgumentException("second must not be null");
        }
        if (third == null)
        {
            throw new IllegalArgumentException("third must not be null");
        }
        if (fourth == null)
        {
            throw new IllegalArgumentException("fourth must not be null");
        }

        // todo  defensive copy?
        this.first = new ObservableSetImpl(first);
        this.second = new ObservableSetImpl(second);
        this.third = new ObservableSetImpl(third);
        this.fourth = new ObservableSetImpl(fourth);

        // alias
        ObservableSet<E> f = this.first;
        ObservableSet<E> s = this.second;
        ObservableSet<E> t = this.third;
        ObservableSet<E> r = this.fourth;
        firstOnly = Sets.difference(Sets.difference(Sets.difference(f, s), t), r); // f - s - t - r
        secondOnly = Sets.difference(Sets.difference(Sets.difference(s, f), t), r); // s - f - t - r
        thirdOnly = Sets.difference(Sets.difference(Sets.difference(t, f), s), r); // t - f - s - r
        fourthOnly = Sets.difference(Sets.difference(Sets.difference(r, f), s), t); // r - f - s - t
        firstSecond = Sets.difference(Sets.difference(Sets.intersection(f, s), t), r); // f n s - t - r
        firstThird = Sets.difference(Sets.difference(Sets.intersection(f, t), s), r); // f n t - s - r
        firstFourth = Sets.difference(Sets.difference(Sets.intersection(f, r), s), t); // f n r - s - t
        secondThird = Sets.difference(Sets.difference(Sets.intersection(s, t), f), r); // s n t - f - r
        secondFourth = Sets.difference(Sets.difference(Sets.intersection(s, r), f), t); // s n r - f - t
        thirdFourth = Sets.difference(Sets.difference(Sets.intersection(t, r), f), s); // t n r - f - s
        firstSecondThird = Sets.difference(Sets.intersection(f, Sets.intersection(s, t)), r); // f u s u t - r
        firstSecondFourth = Sets.difference(Sets.intersection(f, Sets.intersection(s, r)), t); // f u s u r - t
        firstThirdFourth = Sets.difference(Sets.intersection(f, Sets.intersection(t, r)), s); // f u t u r - s
        secondThirdFourth = Sets.difference(Sets.intersection(s, Sets.intersection(t, r)), f); // s u t u r - f
        intersection = Sets.intersection(f, Sets.intersection(s, Sets.intersection(t, r))); // f n s n t n r
        union = Sets.union(f, Sets.union(s, Sets.union(t, r))); // f u s u t u r
        selection = new SelectionView<E>(union, f, s, t, r);
    }


    /** {@inheritDoc} */
    public ObservableSet<E> first()
    {
        return first;
    }

    /** {@inheritDoc} */
    public ObservableSet<E> second()
    {
        return second;
    }

    /** {@inheritDoc} */
    public ObservableSet<E> third()
    {
        return third;
    }

    /** {@inheritDoc} */
    public ObservableSet<E> fourth()
    {
        return fourth;
    }

    /** {@inheritDoc} */
    public Set<E> firstOnly()
    {
        return firstOnly;
    }

    /** {@inheritDoc} */
    public Set<E> secondOnly()
    {
        return secondOnly;
    }

    /** {@inheritDoc} */
    public Set<E> thirdOnly()
    {
        return thirdOnly;
    }

    /** {@inheritDoc} */
    public Set<E> fourthOnly()
    {
        return fourthOnly;
    }

    /** {@inheritDoc} */
    public Set<E> firstSecond()
    {
        return firstSecond;
    }

    /** {@inheritDoc} */
    public Set<E> firstThird()
    {
        return firstThird;
    }

    /** {@inheritDoc} */
    public Set<E> firstFourth()
    {
        return firstFourth;
    }

    /** {@inheritDoc} */
    public Set<E> secondThird()
    {
        return secondThird;
    }

    /** {@inheritDoc} */
    public Set<E> secondFourth()
    {
        return secondFourth;
    }

    /** {@inheritDoc} */
    public Set<E> thirdFourth()
    {
        return thirdFourth;
    }

    /** {@inheritDoc} */
    public Set<E> firstSecondThird()
    {
        return firstSecondThird;
    }

    /** {@inheritDoc} */
    public Set<E> firstSecondFourth()
    {
        return firstSecondFourth;
    }

    /** {@inheritDoc} */
    public Set<E> firstThirdFourth()
    {
        return firstThirdFourth;
    }

    /** {@inheritDoc} */
    public Set<E> secondThirdFourth()
    {
        return secondThirdFourth;
    }

    /** {@inheritDoc} */
    public Set<E> intersection()
    {
        return intersection;
    }

    /** {@inheritDoc} */
    public Set<E> union()
    {
        return union;
    }

    /** {@inheritDoc} */
    public ObservableSet<E> selection()
    {
        return selection;
    }
}