/*

    dsh-curate  Interfaces for curating collections quickly.
    Copyright (c) 2007-2009 held jointly by the individual authors.

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
package org.dishevelled.curate;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import junit.framework.TestCase;

/**
 * Abstract unit test for implementations of CullView.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractCullViewTest
    extends TestCase
{

    /**
     * Create and return a new instance of an implementation of CullView to test.
     *
     * @param <E> cull view input and result collections element type
     * @return a new instance of an implementation of CullView to test
     */
    protected abstract <E> CullView<E> createCullView();

    public void testCreateCullView()
    {
        CullView<String> cullView = createCullView();
        assertNotNull(cullView);
    }

    public void testSetInput()
    {
        CullView<String> cullView = createCullView();
        Collection<String> emptyInput = Collections.emptySet();
        Collection<String> singletonInput = Collections.singleton("foo");
        Collection<String> input = Arrays.asList(new String[] { "foo", "bar", "baz" });
        cullView.setInput(emptyInput);
        cullView.setInput(singletonInput);
        cullView.setInput(input);

        try
        {
            cullView.setInput(null);
            fail("setInput(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testRemaining()
    {
        CullView<String> cullView = createCullView();
        Collection<String> input = Arrays.asList(new String[] { "foo", "bar", "baz" });
        assertNotNull(cullView.getRemaining());
        cullView.setInput(input);
        assertNotNull(cullView.getRemaining());
    }

    public void testRemainingIsImmutable()
    {
        CullView<String> cullView = createCullView();
        Collection<String> input = Arrays.asList(new String[] { "foo", "bar", "baz" });
        cullView.setInput(input);
        assertNotNull(cullView.getRemaining());
        try
        {
            cullView.getRemaining().clear();
            fail("remaining clear expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            cullView.getRemaining().add("qux");
            fail("remaining add expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            cullView.getRemaining().addAll(input);
            fail("remaining addAll expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            cullView.getRemaining().removeAll(input);
            fail("remaining removeAll expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            cullView.getRemaining().retainAll(input);
            fail("remaining retainAll expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            Iterator<String> iterator = cullView.getRemaining().iterator();
            if (iterator.hasNext())
            {
                iterator.next();
                iterator.remove();
                fail("remaining iterator removed expected UnsupportedOperationException");
            }
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
    }

    public void testRemoved()
    {
        CullView<String> cullView = createCullView();
        Collection<String> input = Arrays.asList(new String[] { "foo", "bar", "baz" });
        assertNotNull(cullView.getRemoved());
        cullView.setInput(input);
        assertNotNull(cullView.getRemoved());
    }

    public void testRemovedIsImmutable()
    {
        CullView<String> cullView = createCullView();
        Collection<String> input = Arrays.asList(new String[] { "foo", "bar", "baz" });
        cullView.setInput(input);
        assertNotNull(cullView.getRemoved());
        try
        {
            cullView.getRemoved().clear();
            fail("removed clear expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            cullView.getRemoved().add("qux");
            fail("removed add expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            cullView.getRemoved().addAll(input);
            fail("removed addAll expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            cullView.getRemoved().removeAll(input);
            fail("removed removeAll expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            cullView.getRemoved().retainAll(input);
            fail("removed retainAll expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            Iterator<String> iterator = cullView.getRemoved().iterator();
            if (iterator.hasNext())
            {
                iterator.next();
                iterator.remove();
                fail("remaining iterator removed expected UnsupportedOperationException");
            }
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
    }
}
