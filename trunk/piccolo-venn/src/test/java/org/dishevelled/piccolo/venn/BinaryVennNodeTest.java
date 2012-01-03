/*

    dsh-piccolo-venn  Piccolo2D venn diagram nodes and supporting classes.
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
package org.dishevelled.piccolo.venn;

import java.util.HashSet;
import java.util.Set;

import org.dishevelled.venn.BinaryVennModel;

import org.dishevelled.venn.model.BinaryVennModelImpl;

import junit.framework.TestCase;

/**
 * Unit test for BinaryVennNode.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class BinaryVennNodeTest
    extends TestCase
{

    public void testNoArgConstructor()
    {
        assertNotNull(new BinaryVennNode());
    }

    public void testModelConstructor()
    {
        BinaryVennModel<String> model = new BinaryVennModelImpl<String>();
        assertNotNull(new BinaryVennNode<String>(model));

        try
        {
            new BinaryVennNode(null);
            fail("ctr(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testSetsConstructor()
    {
        Set<String> first = new HashSet<String>();
        Set<String> second = new HashSet<String>();

        assertNotNull(new BinaryVennNode<String>(null, first, null, second));
        assertNotNull(new BinaryVennNode<String>("", first, "", second));
        assertNotNull(new BinaryVennNode<String>("first label", first, "second label", second));

        try
        {
            new BinaryVennNode<String>("first label", null, "second label", second);
            fail("ctr(,null,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            new BinaryVennNode<String>("first label", first, "second label", null);
            fail("ctr(,,,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }
}