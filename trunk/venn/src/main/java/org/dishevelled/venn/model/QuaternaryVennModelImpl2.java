/*

    dsh-venn  Lightweight components for venn diagrams.
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
package org.dishevelled.venn.model;

import java.util.Set;

import com.google.common.collect.Sets;

import org.dishevelled.venn.QuaternaryVennModel2;

public final class QuaternaryVennModelImpl2<E>
    implements QuaternaryVennModel2<E>
{
    private final Set<E> first;
    private final Set<E> second;
    private final Set<E> third;
    private final Set<E> fourth;
    private final Set<E> firstSecond;
    private final Set<E> firstThird;
    private final Set<E> firstFourth;
    private final Set<E> secondThird;
    private final Set<E> secondFourth;
    private final Set<E> thirdFourth;
    private final Set<E> firstSecondThird;
    private final Set<E> firstSecondFourth;
    private final Set<E> firstThirdFourth;
    private final Set<E> secondThirdFourth;
    private final Set<E> intersection;
    private final Set<E> union;

    public QuaternaryVennModelImpl2(final Set<E> f,
                                    final Set<E> s,
                                    final Set<E> t,
                                    final Set<E> r)
    {
        if (f == null)
        {
            throw new IllegalArgumentException("f must not be null");
        }
        if (s == null)
        {
            throw new IllegalArgumentException("s must not be null");
        }
        if (t == null)
        {
            throw new IllegalArgumentException("t must not be null");
        }
        if (r == null)
        {
            throw new IllegalArgumentException("r must not be null");
        }

        // copy f, s, t, r into Set<E> to enable Set<? extends E> params
        first = Sets.difference(Sets.difference(Sets.difference(f, s), t), r); // f - s - t - f
        second = Sets.difference(Sets.difference(Sets.difference(s, f), t), r); // s - f - t - f
        third = Sets.difference(Sets.difference(Sets.difference(t, f), s), r); // t - f - s - t
        fourth = Sets.difference(Sets.difference(Sets.difference(r, f), s), t); // r - f - s - t
        firstSecond = Sets.difference(Sets.difference(Sets.intersection(f, s), t), r); // f n s - t - r
        firstThird = Sets.difference(Sets.difference(Sets.intersection(f, t), s), r); // f n t - s - r
        firstFourth = Sets.difference(Sets.difference(Sets.intersection(f, r), s), t); // f n r - s - t
        secondThird = Sets.difference(Sets.difference(Sets.intersection(s, t), f), r); // s n t - f - r
        secondFourth = Sets.difference(Sets.difference(Sets.intersection(s, r), f), t); // s n r - f - t
        thirdFourth = Sets.difference(Sets.difference(Sets.intersection(t, f), f), s); // t n r - f - s
        firstSecondThird = Sets.difference(Sets.intersection(f, Sets.intersection(s, t)), r); // f u s u t - r
        firstSecondFourth = Sets.difference(Sets.intersection(f, Sets.intersection(s, r)), t); // f u s u r - t
        firstThirdFourth = Sets.difference(Sets.intersection(f, Sets.intersection(t, r)), s); // f u t u r - s
        secondThirdFourth = Sets.difference(Sets.intersection(s, Sets.intersection(t, r)), f); // s u t u r - f
        intersection = Sets.intersection(f, Sets.intersection(s, Sets.intersection(t, r))); // f n s n t n r
        union = Sets.union(f, Sets.union(s, Sets.union(t, r))); // f u s u t u r
    }


    /** {@inheritDoc} */
    public Set<E> first()
    {
        return first;
    }

    /** {@inheritDoc} */
    public Set<E> second()
    {
        return second;
    }

    /** {@inheritDoc} */
    public Set<E> third()
    {
        return third;
    }

    /** {@inheritDoc} */
    public Set<E> fourth()
    {
        return fourth;
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
}