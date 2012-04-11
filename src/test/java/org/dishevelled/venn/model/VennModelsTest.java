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

import java.util.Collections;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.dishevelled.venn.AbstractBinaryVennModelTest;
import org.dishevelled.venn.AbstractTernaryVennModelTest;
import org.dishevelled.venn.AbstractQuaternaryVennModelTest;
import org.dishevelled.venn.BinaryVennModel;
import org.dishevelled.venn.TernaryVennModel;
import org.dishevelled.venn.QuaternaryVennModel;
import org.dishevelled.venn.VennModel;

import static org.dishevelled.venn.model.VennModels.createVennModel;

/**
 * Unit test for VennModels.
 *
 * @author  Michael Heuer
 */
public final class VennModelsTest extends TestCase
{

    public void testCreateVennModelNullSets()
    {
        try
        {
            VennModels.createVennModel(null);
            fail("createVennModel(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testCreateVennModelEmptySets()
    {
        try
        {
            List<Set<String>> emptySets = Collections.emptyList();
            VennModels.createVennModel(emptySets);
            fail("createVennModel(emptySets) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testCreateVennModelLessThanTwoSets()
    {
        try
        {
            Set<String> set = Sets.newHashSet();
            set.add("foo");
            List<Set<String>> singleSet = Lists.newArrayList();
            singleSet.add(set);
            VennModels.createVennModel(singleSet);
            fail("createVennModel(singleSet) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testCreateVennModel()
    {
        Set<String> set = Sets.newHashSet();
        set.add("foo");
        List<Set<String>> sets = Lists.newArrayList();

        for (int i = 2; i < 6; i++)
        {
            sets.clear();
            for (int j = 0; j < i; j++)
            {
                sets.add(set);
            }
            VennModel<String> vennModel = VennModels.createVennModel(sets);
            assertNotNull(vennModel);
            assertEquals(i, vennModel.size());
        }
    }

    public void testCreateVennModels()
    {
        CreateBinaryVennModelTest createBinaryVennModelTest = new CreateBinaryVennModelTest();
        createBinaryVennModelTest.testCreateBinaryVennModel();

        CreateTernaryVennModelTest createTernaryVennModelTest = new CreateTernaryVennModelTest();
        createTernaryVennModelTest.testCreateTernaryVennModel();

        CreateQuaternaryVennModelTest createQuaternaryVennModelTest = new CreateQuaternaryVennModelTest();
        createQuaternaryVennModelTest.testCreateQuaternaryVennModel();
    }

    /**
     * Test wrapper for binary createVennModel method.
     */
    private static class CreateBinaryVennModelTest extends AbstractBinaryVennModelTest
    {
        @Override
        protected <T> BinaryVennModel<T> createBinaryVennModel(final Set<? extends T> first,
                                                               final Set<? extends T> second)
        {
            return createVennModel(first, second);
        }
    }

    /**
     * Test wrapper for ternary createVennModel method.
     */
    private static class CreateTernaryVennModelTest extends AbstractTernaryVennModelTest
    {
        @Override
        protected <T> TernaryVennModel<T> createTernaryVennModel(final Set<? extends T> first,
                                                                 final Set<? extends T> second,
                                                                 final Set<? extends T> third)
        {
            return createVennModel(first, second, third);
        }
    }

    /**
     * Test wrapper for quaternary createVennModel method.
     */
    private static class CreateQuaternaryVennModelTest extends AbstractQuaternaryVennModelTest
    {
        @Override
        protected <T> QuaternaryVennModel<T> createQuaternaryVennModel(final Set<? extends T> first,
                                                                       final Set<? extends T> second,
                                                                       final Set<? extends T> third,
                                                                       final Set<? extends T> fourth)
        {
            return createVennModel(first, second, third, fourth);
        }
    }
}