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

import java.util.Set;

import com.google.common.collect.Sets;

import junit.framework.TestCase;

import org.dishevelled.venn.VennModel;

/**
 * Unit test for VennModelBuilder.
 *
 * @author  Michael Heuer
 */
public final class VennModelBuilderTest
    extends TestCase
{

    public void testConstructor()
    {
        assertNotNull(new VennModelBuilder<String>());
    }

    public void testWithSetNullSet()
    {
        try
        {
            new VennModelBuilder().withSet(null);
            fail("withSet(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testWithSetsNullSets()
    {
        try
        {
            new VennModelBuilder().withSets(null);
            fail("withSets(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testBuildEmptySets()
    {
        try
        {
            new VennModelBuilder<String>().build();
            fail("build with empty sets expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testBuildLessThanTwoSets()
    {
        try
        {
            VennModelBuilder<String> builder = new VennModelBuilder<String>();
            Set<String> set = Sets.newHashSet();
            set.add("foo");
            builder.withSet(set).build();
            fail("build with less than two sets expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testBuild()
    {
        VennModelBuilder<String> builder = new VennModelBuilder<String>();

        Set<String> set = Sets.newHashSet();
        set.add("foo");

        for (int i = 2; i < 6; i++)
        {
            builder.clear();
            for (int j = 0; j < i; j++)
            {
                builder.withSet(set);
            }
            VennModel<String> vennModel = builder.build();
            assertNotNull(vennModel);
            assertEquals(i, vennModel.size());
        }
    }

    public void testBuildVarargSets()
    {
        VennModelBuilder<String> builder = new VennModelBuilder<String>();

        Set<String> set = Sets.newHashSet();
        set.add("foo");

        builder.withSets(set, set);
        VennModel<String> binaryModel = builder.build();
        assertNotNull(binaryModel);
        assertEquals(2, binaryModel.size());

        builder.clear();
        builder.withSets(set, set, set);
        VennModel<String> ternaryModel = builder.build();
        assertNotNull(ternaryModel);
        assertEquals(3, ternaryModel.size());

        builder.clear();
        builder.withSets(set, set, set, set);
        VennModel<String> quaternaryModel = builder.build();
        assertNotNull(quaternaryModel);
        assertEquals(4, quaternaryModel.size());

        builder.clear();
        builder.withSets(set, set, set, set, set);
        VennModel<String> model = builder.build();
        assertNotNull(model);
        assertEquals(5, model.size());
    }
}
