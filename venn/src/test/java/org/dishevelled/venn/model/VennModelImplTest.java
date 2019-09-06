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
package org.dishevelled.venn.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableList;

import junit.framework.TestCase;

import org.dishevelled.venn.BinaryVennModel;
import org.dishevelled.venn.TernaryVennModel;
import org.dishevelled.venn.QuaternaryVennModel;
import org.dishevelled.venn.VennModel;

/**
 * Unit test for VennModelImpl.
 *
 * @author  Michael Heuer
 */
public final class VennModelImplTest
    extends TestCase
{
    // copied from AbstractQuaternaryVennModelTest
    /** First set. */
    protected static final Set<String> FIRST = new HashSet<String>(Arrays.asList(new String[]
        {
            "f - s - t - r",
            "f n s - t - r",
            "f n t - s - r",
            "f n r - s - t",
            "f n s n t - r",
            "f n s n r - t",
            "f n t n r - s",
            "f n s n t n r",
        }));

    /** Second set. */
    protected static final Set<String> SECOND = new HashSet<String>(Arrays.asList(new String[]
        {
            "s - f - t - r",
            "f n s - t - r",
            "s n t - f - r",
            "s n r - f - t",
            "f n s n t - r",
            "f n s n r - t",
            "s n t n r - f",
            "f n s n t n r",
        }));

    /** Third set. */
    protected static final Set<String> THIRD = new HashSet<String>(Arrays.asList(new String[]
        {
            "t - f - s - r",
            "f n t - s - r",
            "s n t - f - r",
            "t n r - f - s",
            "f n s n t - r",
            "f n t n r - s",
            "s n t n r - f",
            "f n s n t n r",
        }));

    /** Fourth set. */
    protected static final Set<String> FOURTH = new HashSet<String>(Arrays.asList(new String[]
        {
            "r - f - s - t",
            "f n r - s - t",
            "s n r - f - t",
            "t n r - f - s",
            "f n s n r - t",
            "f n t n r - s",
            "s n t n r - f",
            "f n s n t n r"
        }));

    /** Fifth set. */
    protected static final Set<String> FIFTH = new HashSet<String>(Arrays.asList(new String[]
        {
            "h",
            "f n s n t n r"
        }));

    public void testConstructorNullSets()
    {
        try
        {
            new VennModelImpl(null);
            fail("ctr(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void test2NMatchesBinaryVennModelImpl()
    {
        List<Set<String>> sets = ImmutableList.of(FIRST, SECOND);
        VennModel<String> vennModel = new VennModelImpl<String>(sets);
        BinaryVennModel<String> binaryVennModel = new BinaryVennModelImpl<String>(FIRST, SECOND);

        assertEquals(binaryVennModel.size(), vennModel.size());
        assertEquals(binaryVennModel.first(), vennModel.get(0));
        assertEquals(binaryVennModel.second(), vennModel.get(1));
        assertEquals(binaryVennModel.intersection(), vennModel.intersection());
        assertEquals(binaryVennModel.union(), vennModel.union());
        assertEquals(binaryVennModel.firstOnly(), vennModel.exclusiveTo(0));
        assertEquals(binaryVennModel.secondOnly(), vennModel.exclusiveTo(1));
        assertEquals(binaryVennModel.intersection(), vennModel.exclusiveTo(0, 1));
    }

    public void test3NMatchesTernaryVennModelImpl()
    {
        List<Set<String>> sets = ImmutableList.of(FIRST, SECOND, THIRD);
        VennModel<String> vennModel = new VennModelImpl<String>(sets);
        TernaryVennModel<String> ternaryVennModel = new TernaryVennModelImpl<String>(FIRST, SECOND, THIRD);

        assertEquals(ternaryVennModel.size(), vennModel.size());
        assertEquals(ternaryVennModel.first(), vennModel.get(0));
        assertEquals(ternaryVennModel.second(), vennModel.get(1));
        assertEquals(ternaryVennModel.third(), vennModel.get(2));
        assertEquals(ternaryVennModel.intersection(), vennModel.intersection());
        assertEquals(ternaryVennModel.union(), vennModel.union());
        assertEquals(ternaryVennModel.firstOnly(), vennModel.exclusiveTo(0));
        assertEquals(ternaryVennModel.secondOnly(), vennModel.exclusiveTo(1));
        assertEquals(ternaryVennModel.thirdOnly(), vennModel.exclusiveTo(2));
        assertEquals(ternaryVennModel.firstSecond(), vennModel.exclusiveTo(0, 1));
        assertEquals(ternaryVennModel.firstThird(), vennModel.exclusiveTo(0, 2));
        assertEquals(ternaryVennModel.secondThird(), vennModel.exclusiveTo(1, 2));
        assertEquals(ternaryVennModel.intersection(), vennModel.exclusiveTo(0, 1, 2));
    }

    public void test4NMatchesTernaryVennModelImpl()
    {
        List<Set<String>> sets = ImmutableList.of(FIRST, SECOND, THIRD, FOURTH);
        VennModel<String> vennModel = new VennModelImpl<String>(sets);
        QuaternaryVennModel<String> quaternaryVennModel = new QuaternaryVennModelImpl<String>(FIRST, SECOND, THIRD, FOURTH);

        assertEquals(quaternaryVennModel.size(), vennModel.size());
        assertEquals(quaternaryVennModel.first(), vennModel.get(0));
        assertEquals(quaternaryVennModel.second(), vennModel.get(1));
        assertEquals(quaternaryVennModel.third(), vennModel.get(2));
        assertEquals(quaternaryVennModel.fourth(), vennModel.get(3));
        assertEquals(quaternaryVennModel.intersection(), vennModel.intersection());
        assertEquals(quaternaryVennModel.union(), vennModel.union());
        assertEquals(quaternaryVennModel.firstOnly(), vennModel.exclusiveTo(0));
        assertEquals(quaternaryVennModel.secondOnly(), vennModel.exclusiveTo(1));
        assertEquals(quaternaryVennModel.thirdOnly(), vennModel.exclusiveTo(2));
        assertEquals(quaternaryVennModel.firstSecond(), vennModel.exclusiveTo(0, 1));
        assertEquals(quaternaryVennModel.firstThird(), vennModel.exclusiveTo(0, 2));
        assertEquals(quaternaryVennModel.secondThird(), vennModel.exclusiveTo(1, 2));
        // ...
        assertEquals(quaternaryVennModel.intersection(), vennModel.exclusiveTo(0, 1, 2, 3));
    }

    public void test5N()
    {
        List<Set<String>> sets = ImmutableList.of(FIRST, SECOND, THIRD, FOURTH, FIFTH);
        VennModel<String> vennModel = new VennModelImpl<String>(sets);

        assertEquals(5, vennModel.size());
        // ...
        assertTrue(vennModel.exclusiveTo(4).contains("h"));
        assertTrue(vennModel.intersection().contains("f n s n t n r"));
    }
}
