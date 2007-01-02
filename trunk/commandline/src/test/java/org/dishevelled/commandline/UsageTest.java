/*

    dsh-commandline  Simple command line parser based on typed arguments.
    Copyright (c) 2004-2007 held jointly by the individual authors.

    This library is free software; you can redistribute it and/or modify it
    under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation; either version 2.1 of the License, or (at
    your option) any later version.

    This library is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; with out even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
    License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this library;  if not, write to the Free Software Foundation,
    Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA.

    > http://www.gnu.org/copyleft/lesser.html
    > http://www.opensource.org/licenses/lgpl-license.php

*/
package org.dishevelled.commandline;

import java.io.ByteArrayOutputStream;

import java.util.Arrays;

import junit.framework.TestCase;

import org.dishevelled.commandline.argument.IntegerArgument;

/**
 * Unit test for Usage.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class UsageTest
    extends TestCase
{

    public void testUsage()
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        String message = "message";
        Throwable cause = new Exception("cause");
        CommandLine emptyCommandLine = new CommandLine(new String[0]);
        CommandLine fullCommandLine = new CommandLine(new String[] { "--foo" });
        ArgumentList emptyArguments = new ArgumentList(Arrays.asList(new Argument[0]));
        Argument<Integer> integerArgument = new IntegerArgument("f", "foo", "Foo", false);
        Argument<Integer> requiredIntegerArgument = new IntegerArgument("b", "bar", "Bar", true);
        ArgumentList fullArguments = new ArgumentList(Arrays.asList(new Argument[] { integerArgument, requiredIntegerArgument }));

        Usage.usage(null, null, null, null, out);
        Usage.usage(message, null, null, null, out);
        Usage.usage(message, cause, null, null, out);
        Usage.usage(message, cause, emptyCommandLine, null, out);
        Usage.usage(message, cause, fullCommandLine, null, out);
        Usage.usage(message, cause, fullCommandLine, emptyArguments, out);
        Usage.usage(message, cause, fullCommandLine, fullArguments, out);

        try
        {
            Usage.usage(message, cause, fullCommandLine, fullArguments, null);
            fail("usage(,,,,out) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }
}