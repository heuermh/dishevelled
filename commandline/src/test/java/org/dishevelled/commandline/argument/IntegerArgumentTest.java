/*

    dsh-commandline  Command line parser based on typed arguments.
    Copyright (c) 2004-2013 held jointly by the individual authors.

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
package org.dishevelled.commandline.argument;

import java.util.Arrays;

import junit.framework.TestCase;

import org.dishevelled.commandline.Argument;
import org.dishevelled.commandline.ArgumentList;
import org.dishevelled.commandline.CommandLine;
import org.dishevelled.commandline.CommandLineParser;
import org.dishevelled.commandline.CommandLineParseException;

/**
 * Unit test for IntegerArgument.
 *
 * @author  Michael Heuer
 */
public class IntegerArgumentTest
    extends TestCase
{

    public void testIntegerArgument()
    {
        IntegerArgument ia = new IntegerArgument("i", "integer", "Integer argument", true);
        assertNotNull("ia not null", ia);
        assertEquals("ia shortName == i", "i", ia.getShortName());
        assertEquals("ia longName == integer", "integer", ia.getLongName());
        assertEquals("ia description == Integer argument", "Integer argument", ia.getDescription());
        assertTrue("ia isRequired", ia.isRequired());
        assertFalse("ia wasFound == false", ia.wasFound());
        assertEquals("ia value == null", null, ia.getValue());
    }

    public void testValidArgumentShort()
        throws CommandLineParseException
    {
        Argument<Integer> integerArgument = new IntegerArgument("i", "integer", "Integer argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { integerArgument }));

        String[] args = new String[] { "-i", "127" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertEquals("-i == 127", Integer.valueOf("127"), integerArgument.getValue());
    }

    public void testValidArgumentLong()
        throws CommandLineParseException
    {
        Argument<Integer> integerArgument = new IntegerArgument("i", "integer", "Integer argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { integerArgument }));

        String[] args = new String[] { "--integer", "127" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertEquals("--integer == 127", Integer.valueOf("127"), integerArgument.getValue());
    }

    public void testValidNegativeArgumentShort()
        throws CommandLineParseException
    {
        Argument<Integer> integerArgument = new IntegerArgument("i", "integer", "Integer argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { integerArgument }));

        String[] args = new String[] { "-i", "-128" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertEquals("-i == -128", Integer.valueOf("-128"), integerArgument.getValue());
    }

    public void testValidNegativeArgumentLong()
        throws CommandLineParseException
    {
        Argument<Integer> integerArgument = new IntegerArgument("i", "integer", "Integer argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { integerArgument }));

        String[] args = new String[] { "--integer", "-128" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertEquals("--integer == -128", Integer.valueOf("-128"), integerArgument.getValue());
    }

    public void testInvalidArgumentShort()
    {
        try
        {
            Argument<Integer> integerArgument = new IntegerArgument("i", "integer", "Integer argument", true);
            ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { integerArgument }));

            String[] args = new String[] { "-i", "not-a-integer" };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            fail("-i not-a-integer expected CommandLineParseException");
        }
        catch (CommandLineParseException e)
        {
            // expected
        }
    }

    public void testMissingValueShort() throws CommandLineParseException
    {
        Argument<Integer> integerArgument = new IntegerArgument("i", "integer", "Integer argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { integerArgument }));

        String[] args = new String[] { "-i" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);
        Integer value = integerArgument.getValue();
        assertEquals(null, value);
        // note that unboxing a null value with throw NPE
        try
        {
            int intValue = integerArgument.getValue();
            fail("unboxing a null value expected NullPointerException, got " + intValue);
        }
        catch (NullPointerException e)
        {
            // expected
        }
    }

    public void testInvalidArgumentLong()
    {
        try
        {
            Argument<Integer> integerArgument = new IntegerArgument("i", "integer", "Integer argument", true);
            ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { integerArgument }));

            String[] args = new String[] { "--integer", "not-a-integer" };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            fail("--integer not-a-integer expected CommandLineParseException");
        }
        catch (CommandLineParseException e)
        {
            // expected
        }
    }

    public void testTooSmallArgumentShort()
    {
        try
        {
            Argument<Integer> integerArgument = new IntegerArgument("i", "integer", "Integer argument", true);
            ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { integerArgument }));

            String[] args = new String[] { "-i", "-2147483649" };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            fail("-i -2147483649 expected CommandLineParseException");
        }
        catch (CommandLineParseException e)
        {
            // expected
        }
    }

    public void testTooLargeArgumentShort()
    {
        try
        {
            Argument<Integer> integerArgument = new IntegerArgument("i", "integer", "Integer argument", true);
            ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { integerArgument }));

            String[] args = new String[] { "-i", "2147483648" };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            fail("-b 2147483648 expected CommandLineParseException");
        }
        catch (CommandLineParseException e)
        {
            // expected
        }
    }

    public void testTooSmallArgumentLong()
    {
        try
        {
            Argument<Integer> integerArgument = new IntegerArgument("i", "integer", "Integer argument", true);
            ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { integerArgument }));

            String[] args = new String[] { "--integer", "-2147483649" };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            fail("--integer -2147483649 expected CommandLineParseException");
        }
        catch (CommandLineParseException e)
        {
            // expected
        }
    }

    public void testTooLargeArgumentLong()
    {
        try
        {
            Argument<Integer> integerArgument = new IntegerArgument("i", "integer", "Integer argument", true);
            ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { integerArgument }));

            String[] args = new String[] { "--integer", "2147483648" };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            fail("--integer 2147483648 expected CommandLineParseException");
        }
        catch (CommandLineParseException e)
        {
            // expected
        }
    }
}
