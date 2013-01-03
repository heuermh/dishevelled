/*

    dsh-cluster  Framework for clustering algorithms.
    Copyright (c) 2007-2013 held jointly by the individual authors.

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
package org.dishevelled.cluster.impl;

import java.util.Arrays;
import java.util.Collections;

import junit.framework.TestCase;

import org.dishevelled.cluster.Cluster;

/**
 * Unit test for ClusterImpl.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class ClusterImplTest
    extends TestCase
{

    public void testConstructor()
    {
        Cluster<String> cluster0 = new ClusterImpl<String>(Collections.singletonList("foo"));
        assertNotNull(cluster0);
        Cluster<String> cluster1 = new ClusterImpl<String>(Collections.singletonList("foo"), null);
        assertNotNull(cluster1);
        Cluster<String> cluster2 = new ClusterImpl<String>(Collections.singletonList("foo"), "foo");
        assertNotNull(cluster2);

        try
        {
            Cluster<String> cluster = new ClusterImpl<String>(null);
            fail("ctr(null) expected IllegalArgumentException");
        }
        catch(IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            Cluster<String> cluster = new ClusterImpl<String>(Collections.<String>emptyList());
            fail("ctr(emptyList) expected IllegalArgumentException");
        }
        catch(IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            Cluster<String> cluster = new ClusterImpl<String>(null, "foo");
            fail("ctr(null, foo) expected IllegalArgumentException");
        }
        catch(IllegalArgumentException e)
        {
            // expected
        }
       try
        {
            Cluster<String> cluster = new ClusterImpl<String>(Collections.<String>emptyList(), "foo");
            fail("ctr(emptyList, foo) expected IllegalArgumentException");
        }
        catch(IllegalArgumentException e)
        {
            // expected
        }
       try
        {
            Cluster<String> cluster = new ClusterImpl<String>(Collections.singletonList("foo"), "bar");
            fail("ctr(singletonList(foo), bar) expected IllegalArgumentException");
        }
        catch(IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testSize()
    {
        Cluster<String> cluster0 = new ClusterImpl<String>(Collections.singletonList("foo"));
        assertEquals(1, cluster0.size());

        Cluster<String> cluster1 = new ClusterImpl<String>(Arrays.asList(new String[] { "foo", "bar", "baz" }));
        assertEquals(3, cluster1.size());
    }

    public void testIsSingleton()
    {
        Cluster<String> cluster0 = new ClusterImpl<String>(Collections.singletonList("foo"));
        assertTrue(cluster0.isSingleton());

        Cluster<String> cluster1 = new ClusterImpl<String>(Arrays.asList(new String[] { "foo", "bar", "baz" }));
        assertFalse(cluster1.isSingleton());
    }

    public void testExemplar()
    {
        Cluster<String> cluster0 = new ClusterImpl<String>(Collections.singletonList("foo"));
        assertEquals(null, cluster0.exemplar());

        Cluster<String> cluster1 = new ClusterImpl<String>(Collections.singletonList("foo"), "foo");
        assertEquals("foo", cluster1.exemplar());
    }

    public void testMembers()
    {
        Cluster<String> cluster0 = new ClusterImpl<String>(Collections.singletonList("foo"));
        assertNotNull(cluster0);
        assertTrue(cluster0.size() >= 1);
        assertTrue(cluster0.members().contains("foo"));
    }

    public void testMembersIsImmutable()
    {
        // empty
    }

    public void testIterator()
    {
        Cluster<String> cluster0 = new ClusterImpl<String>(Collections.singletonList("foo"));
        assertNotNull(cluster0.iterator());
        assertTrue(cluster0.iterator().hasNext());
        for (String s : cluster0)
        {
            assertEquals("foo", s);
        }
    }

    public void testIteratorIsImmutable()
    {
        // empty
    }
}