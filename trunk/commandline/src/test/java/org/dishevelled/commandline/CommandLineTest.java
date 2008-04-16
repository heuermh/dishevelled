/*

    dsh-commandline  Command line parser based on typed arguments.
    Copyright (c) 2004-2008 held jointly by the individual authors.

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
package org.dishevelled.commandline;

import java.util.Collections;
import java.util.NoSuchElementException;

import junit.framework.TestCase;

/**
 * Unit test for CommandLine.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class CommandLineTest
    extends TestCase
{

    public void testConstructor()
    {
        try
        {
            CommandLine cl = new CommandLine(null);
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        CommandLine cl0 = new CommandLine(new String[0]);
        CommandLine cl1 = new CommandLine(Collections.emptyList().toArray(new String[0]));
        CommandLine cl2 = new CommandLine(new String[] { "--foo" });
        CommandLine cl3 = new CommandLine(Collections.singleton("--foo").toArray(new String[0]));
        CommandLine cl4 = new CommandLine(new String[] { "--foo", "foo-value", "--bar", "bar-value" });
    }

    public void testObjectContract()
    {
        CommandLine cl0 = new CommandLine(new String[0]);
        assertEquals("cl0 equals itself", cl0, cl0);
        assertEquals("cl0 hashCode == cl0 hashCode", cl0.hashCode(), cl0.hashCode());
        assertFalse("cl0 does not equal null", cl0.equals(null));
        assertFalse("cl0 does not equal new Object", cl0.equals(new Object()));

        CommandLine cl1 = new CommandLine(new String[] { "--foo" });
        CommandLine cl2 = new CommandLine(new String[] { "--bar" });
        CommandLine cl3 = new CommandLine(new String[] { "--foo", "--bar" });
        CommandLine cl4 = new CommandLine(new String[0]);

        assertFalse("cl0 does not equal cl1", cl0.equals(cl1));
        assertFalse("cl0 does not equal cl1", cl0.equals(cl2));
        assertFalse("cl0 does not equal cl1", cl0.equals(cl3));
        assertFalse("cl0 does not equal cl4", cl0.equals(cl4));
        assertFalse("cl1 does not equal cl0", cl1.equals(cl0));
        assertFalse("cl2 does not equal cl0", cl2.equals(cl0));
        assertFalse("cl3 does not equal cl0", cl3.equals(cl0));
        assertFalse("cl4 does not equal cl0", cl4.equals(cl0));

        CommandLine cl5 = cl0;
        assertEquals("cl0 equals cl5", cl0, cl5);
        assertEquals("cl0 hashCode == cl5 hashCode", cl0.hashCode(), cl5.hashCode());
    }

    public void testListIterator()
    {
        CommandLine cl0 = new CommandLine(new String[0]);
        assertFalse("cl0 hasNext == false", cl0.hasNext());
        assertFalse("cl0 hasPrevious == false", cl0.hasPrevious());
        assertEquals("cl0 nextIndex == 0", 0, cl0.nextIndex());
        assertEquals("cl0 previousIndex == -1", -1, cl0.previousIndex());

        try
        {
            cl0.next();
            fail("next expected NoSuchElementException");
        }
        catch (NoSuchElementException e)
        {
            // expected
        }

        try
        {
            cl0.previous();
            fail("previous expected NoSuchElementException");
        }
        catch (NoSuchElementException e)
        {
            // expected
        }

        try
        {
            cl0.remove();
            fail("remove expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }

        try
        {
            cl0.set("foo");
            fail("set expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }

        try
        {
            cl0.add("foo");
            fail("add expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }

        CommandLine cl1 = new CommandLine(new String[] { "--foo", "--bar" });
        assertTrue("cl1 hasNext == true", cl1.hasNext());
        assertFalse("cl1 hasPrevious == false", cl1.hasPrevious());

        String s0 = cl1.next();
        String s1 = cl1.next();

        assertFalse("cl1 hasNext == false", cl1.hasNext());
        assertTrue("cl1 hasPrevious == true", cl1.hasPrevious());

        String s2 = cl1.previous();
        String s3 = cl1.previous();

        assertTrue("cl1 hasNext == true", cl1.hasNext());
        assertFalse("cl1 hasPrevious == false", cl1.hasPrevious());

        assertEquals("s3 equals s0", s3, s0);
        assertEquals("s2 equals s1", s2, s1);

        try
        {
            cl1.remove();
            fail("remove expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }

        try
        {
            cl1.set("foo");
            fail("set expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }

        try
        {
            cl1.add("foo");
            fail("add expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
    }
}
