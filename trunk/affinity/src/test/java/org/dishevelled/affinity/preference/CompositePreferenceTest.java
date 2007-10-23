/*

    dsh-affinity  Clustering by affinity propagation.
    Copyright (c) 2007 held jointly by the individual authors.

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

import java.util.Arrays;
import java.util.Collections;

import org.dishevelled.functor.BinaryFunction;

import org.dishevelled.affinity.AbstractPreferenceTest;
import org.dishevelled.affinity.Preference;

/**
 * Unit test for CompositePreference.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class CompositePreferenceTest
    extends AbstractPreferenceTest
{

    /** {@inheritDoc} */
    protected <T> Preference<T> createPreference()
    {
        Preference<T> child = new UniformPreference<T>();
        BinaryFunction<Double, Double, Double> aggr = new BinaryFunction<Double, Double, Double>()
            {
                /** {@inheritDoc} */
                public Double evaluate(final Double d1, final Double d2)
                {
                    return Double.valueOf(1.0d);
                }
            };
        return new CompositePreference(Collections.singletonList(child), aggr);
    }

    public void testConstructor()
    {
        Preference<String> child0 = new UniformPreference<String>();
        Preference<String> child1 = new UniformPreference<String>();
        Preference<String> child2 = new UniformPreference<String>();
        BinaryFunction<Double, Double, Double> aggr = new BinaryFunction<Double, Double, Double>()
            {
                /** {@inheritDoc} */
                public Double evaluate(final Double d1, final Double d2)
                {
                    return Double.valueOf(1.0d);
                }
            };

        CompositePreference<String> compositePreference0 = new CompositePreference(Collections.singletonList(child0), aggr);
        CompositePreference<String> compositePreference1 = new CompositePreference(Arrays.asList(new Preference[] { child0, child1, child2 }), aggr);

        try
        {
            CompositePreference<String> compositePreference = new CompositePreference(null, aggr);
            fail("ctr(null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            CompositePreference<String> compositePreference = new CompositePreference(Collections.singletonList(child0), null);
            fail("ctr(,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            CompositePreference<String> compositePreference = new CompositePreference(Collections.<Preference<String>>emptyList(), aggr);
            fail("ctr(emptyList(),) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testPreference()
    {
        Preference<String> zero = new UniformPreference<String>(0.0d);
        Preference<String> one = new UniformPreference<String>(1.0d);
        Preference<String> two = new UniformPreference<String>(2.0d);
        BinaryFunction<Double, Double, Double> min = new BinaryFunction<Double, Double, Double>()
            {
                /** {@inheritDoc} */
                public Double evaluate(final Double d1, final Double d2)
                {
                    return Math.min(d1, d2);
                }
            };
        BinaryFunction<Double, Double, Double> max = new BinaryFunction<Double, Double, Double>()
            {
                /** {@inheritDoc} */
                public Double evaluate(final Double d1, final Double d2)
                {
                    return Math.max(d1, d2);
                }
            };

        CompositePreference<String> compositePreference1 = new CompositePreference(Collections.singletonList(zero), min);
        assertEquals(0.0d, compositePreference1.preference("foo"), 0.1d);
        assertEquals(0.0d, compositePreference1.preference("bar"), 0.1d);

        CompositePreference<String> compositePreference2 = new CompositePreference(Collections.singletonList(zero), max);
        assertEquals(0.0d, compositePreference2.preference("foo"), 0.1d);
        assertEquals(0.0d, compositePreference2.preference("bar"), 0.1d);

        CompositePreference<String> compositePreference4 = new CompositePreference(Collections.singletonList(one), min);
        assertEquals(1.0d, compositePreference4.preference("foo"), 0.1d);
        assertEquals(1.0d, compositePreference4.preference("bar"), 0.1d);

        CompositePreference<String> compositePreference5 = new CompositePreference(Collections.singletonList(one), max);
        assertEquals(1.0d, compositePreference5.preference("foo"), 0.1d);
        assertEquals(1.0d, compositePreference5.preference("bar"), 0.1d);

        CompositePreference<String> compositePreference7 = new CompositePreference(Collections.singletonList(two), min);
        assertEquals(2.0d, compositePreference7.preference("foo"), 0.1d);
        assertEquals(2.0d, compositePreference7.preference("bar"), 0.1d);

        CompositePreference<String> compositePreference8 = new CompositePreference(Collections.singletonList(two), max);
        assertEquals(2.0d, compositePreference8.preference("foo"), 0.1d);
        assertEquals(2.0d, compositePreference8.preference("bar"), 0.1d);

        CompositePreference<String> compositePreference10 = new CompositePreference(Arrays.asList(new Preference[] { zero, one, two }), min);
        assertEquals(0.0d, compositePreference10.preference("foo"), 0.1d);
        assertEquals(0.0d, compositePreference10.preference("bar"), 0.1d);

        CompositePreference<String> compositePreference11 = new CompositePreference(Arrays.asList(new Preference[] { zero, one, two }), max);
        assertEquals(2.0d, compositePreference11.preference("foo"), 0.1d);
        assertEquals(2.0d, compositePreference11.preference("bar"), 0.1d);
    }
}