/*

    dsh-commandline  Command line parser based on typed arguments.
    Copyright (c) 2004-2014 held jointly by the individual authors.

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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import junit.framework.TestCase;

import org.dishevelled.commandline.argument.StringArgument;

/**
 * Unit test for ArgumentList.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class ArgumentListTest
    extends TestCase
{

    public void testConstructors()
    {
        ArgumentList arguments0 = new ArgumentList();
        assertNotNull(arguments0);
        ArgumentList arguments1 = new ArgumentList(new Argument[0]);
        assertNotNull(arguments1);
        ArgumentList arguments2 = new ArgumentList(new StringArgument("f", "foo", "foo", true), new StringArgument("b", "bar", "bar", true));
        assertNotNull(arguments2);
        ArgumentList arguments3 = new ArgumentList(Collections.<Argument<?>>emptyList());
        assertNotNull(arguments3);

        try
        {
            new ArgumentList((Argument<?>) null);
            fail("ctr((Argument) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            new ArgumentList((Argument[]) null);
            fail("ctr((Argument[]) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            new ArgumentList((Collection<Argument<?>>) null);
            fail("ctr((Collection<Argument>) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testAdd()
    {
        ArgumentList arguments = new ArgumentList();
        try
        {
            arguments.add(null);
            fail("add(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        Argument<String> stringArgument = new StringArgument("s", "string", "String argument", true);
        Argument<String> conflictingShortName = new StringArgument("s", "conflicting-short-name", "Conflicting short name", true);
        Argument<String> conflictingLongName = new StringArgument("c", "string", "Conflicting long name", true);

        assertTrue(arguments.add(stringArgument));

        try
        {
            arguments.add(conflictingShortName);
            fail("add(conflictingShortName) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            arguments.add(conflictingLongName);
            fail("add(conflictingLongName) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testAddAll()
    {
        ArgumentList arguments = null;

        arguments = new ArgumentList();
        try
        {
            arguments.addAll(null);
            fail("addAll(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        Argument<String> stringArgument = new StringArgument("s", "string", "String argument", true);
        Argument<String> secondStringArgument = new StringArgument("e", "second-string", "Second string argument", true);
        Argument<String> conflictingShortName = new StringArgument("s", "conflicting-short-name", "Conflicting short name", true);
        Argument<String> conflictingLongName = new StringArgument("c", "string", "Conflicting long name", true);

        try
        {
            arguments.addAll(Arrays.asList(new Argument<?>[] { stringArgument, conflictingShortName }));
            fail("addAll(..., conflictingShortName) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        // todo:
        // that last test successfully added stringArgument even though IAE was thrown
        //    check with Collection.addAll doc, is addAll a `transaction'?

        arguments = new ArgumentList();
        try
        {
            arguments.addAll(Arrays.asList(new Argument<?>[] { stringArgument, conflictingLongName }));
            fail("addAll(..., conflictingLongName) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        arguments = new ArgumentList();
        try
        {
            arguments.addAll(Arrays.asList(new Argument<?>[] { stringArgument, stringArgument }));
            fail("addAll(stringArgument, stringArgument) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        arguments = new ArgumentList();
        arguments.addAll(Arrays.asList(new Argument<?>[] { stringArgument, secondStringArgument }));

        boolean foundStringArgument = false;
        boolean foundSecondStringArgument = false;
        for (Argument<?> a : arguments)
        {
            if (a.equals(stringArgument))
            {
                foundStringArgument = true;
            }
            if (a.equals(secondStringArgument))
            {
                foundSecondStringArgument = true;
            }
        }

        assertTrue("found stringArgument", foundStringArgument);
        assertTrue("found secondStringArgument", foundSecondStringArgument);
    }
}
