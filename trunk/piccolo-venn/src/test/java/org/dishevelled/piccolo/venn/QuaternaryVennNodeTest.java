/*

    dsh-piccolo-venn  Piccolo2D venn diagram nodes and supporting classes.
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
package org.dishevelled.piccolo.venn;

import java.util.HashSet;
import java.util.Set;

import org.dishevelled.venn.QuaternaryVennModel;

import org.dishevelled.venn.model.QuaternaryVennModelImpl;

import junit.framework.TestCase;

/**
 * Unit test for QuaternaryVennNode.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class QuaternaryVennNodeTest
    extends TestCase
{

    public void testNoArgConstructor()
    {
        assertNotNull(new QuaternaryVennNode());
    }

    public void testModelConstructor()
    {
        QuaternaryVennModel<String> model = new QuaternaryVennModelImpl<String>();
        assertNotNull(new QuaternaryVennNode<String>(model));

        try
        {
            new QuaternaryVennNode(null);
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
        Set<String> third = new HashSet<String>();
        Set<String> fourth = new HashSet<String>();

        assertNotNull(new QuaternaryVennNode<String>(null, first, null, second, null, third, null, fourth));
        assertNotNull(new QuaternaryVennNode<String>("", first, "", second, "", third, "", fourth));
        assertNotNull(new QuaternaryVennNode<String>("first label", first, "second label", second, "third label", third, "fourth label", fourth));

        try
        {
            new QuaternaryVennNode<String>("first label", null, "second label", second, "third label", third, "fourth label", fourth);
            fail("ctr(,null,,,,,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            new QuaternaryVennNode<String>("first label", first, "second label", null, "third label", third, "fourth label", fourth);
            fail("ctr(,,,null,,,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            new QuaternaryVennNode<String>("first label", first, "second label", second, "third label", null, "fourth label", fourth);
            fail("ctr(,,,,,null,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            new QuaternaryVennNode<String>("first label", first, "second label", second, "third label", third, "fourth label", null);
            fail("ctr(,,,,,,,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }
}