/*

    dsh-commandline  Command line parser based on typed arguments.
    Copyright (c) 2004-2022 held jointly by the individual authors.

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

import java.util.Set;
import java.util.List;
import java.util.Arrays;

import junit.framework.TestCase;

import org.dishevelled.commandline.Argument;
import org.dishevelled.commandline.ArgumentList;
import org.dishevelled.commandline.CommandLine;
import org.dishevelled.commandline.CommandLineParser;
import org.dishevelled.commandline.CommandLineParseException;

/**
 * Unit test for IntegerSetArgument.
 *
 * @author  Michael Heuer
 */
public class IntegerSetArgumentTest
    extends TestCase
{

    public void testIntegerSetArgument()
    {
        IntegerSetArgument isa = new IntegerSetArgument("i", "integer-set", "Integer set argument", true);
        assertNotNull("isa not null", isa);
        assertEquals("isa shortName == i", "i", isa.getShortName());
        assertEquals("isa longName == integer-set", "integer-set", isa.getLongName());
        assertEquals("isa description == Integer set argument", "Integer set argument", isa.getDescription());
        assertTrue("isa isRequired", isa.isRequired());
        assertFalse("isa wasFound == false", isa.wasFound());
        assertEquals("isa value == null", null, isa.getValue());
    }

    public void testValidArgumentShort()
        throws CommandLineParseException
    {
        Argument<Set<Integer>> integerSetArgument = new IntegerSetArgument("i", "integer-set", "Integer set argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { integerSetArgument }));
        List<String> values = Arrays.asList(new String[] { "1", "-1", "1, -1", "1,-1", " 1, -1 " });

        for (String value : values)
        {
            String[] args = new String[] { "-i", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            Set<Integer> set = integerSetArgument.getValue();
            assertNotNull("-i set not null", set);
            assertFalse("-i set not empty", set.isEmpty());
        }
    }

    public void testValidArgumentLong()
        throws CommandLineParseException
    {
        Argument<Set<Integer>> integerSetArgument = new IntegerSetArgument("i", "integer-set", "Integer set argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { integerSetArgument }));
        List<String> values = Arrays.asList(new String[] { "1", "-1", "1, -1", "1,-1", " 1, -1 " });

        for (String value : values)
        {
            String[] args = new String[] { "--integer-set", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            Set<Integer> set = integerSetArgument.getValue();
            assertNotNull("--integer-set set not null", set);
            assertFalse("--integer-set set not empty", set.isEmpty());
        }
    }

    public void testInvalidArgumentShort()
    {
        Argument<Set<Integer>> integerSetArgument = new IntegerSetArgument("i", "integer-set", "Integer set argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { integerSetArgument }));
        List<String> values = Arrays.asList(new String[] { "not-a-integer" });

        for (String value : values)
        {
            try
            {
                String[] args = new String[] { "-i", value };
                CommandLine commandLine = new CommandLine(args);
                CommandLineParser.parse(commandLine, arguments);

                fail("-i " + value + " expected CommandLineParseException");
            }
            catch (CommandLineParseException e)
            {
                // expected
            }
        }
    }

    public void testInvalidArgumentLong()
    {
        Argument<Set<Integer>> integerSetArgument = new IntegerSetArgument("i", "integer-set", "Integer set argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { integerSetArgument }));
        List<String> values = Arrays.asList(new String[] { "not-a-integer" });

        for (String value : values)
        {
            try
            {
                String[] args = new String[] { "--integer-set", value };
                CommandLine commandLine = new CommandLine(args);
                CommandLineParser.parse(commandLine, arguments);

                fail("--integer-set " + value + " expected CommandLineParseException");
            }
            catch (CommandLineParseException e)
            {
                // expected
            }
        }
    }

    public void testRequiredArgument()
    {
        try
        {
            Argument<Set<Integer>> integerSetArgument = new IntegerSetArgument("i", "integer-set", "Integer set argument", true);
            ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { integerSetArgument }));

            String[] args = new String[] { "not-an-argument", "not-a-integer" };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            fail("not-an-argument expected CommandLineParseException");
        }
        catch (CommandLineParseException e)
        {
            // expected
        }
    }

    public void testNotRequiredArgument()
        throws CommandLineParseException
    {
        Argument<Set<Integer>> integerSetArgument = new IntegerSetArgument("i", "integer-set", "Integer set argument", false);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { integerSetArgument }));

        String[] args = new String[] { "not-an-argument", "not-a-integer" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertFalse("integerSetArgument isRequired == false", integerSetArgument.isRequired());
        assertFalse("integerSetArgument wasFound == false", integerSetArgument.wasFound());
        assertEquals("integerSetArgument value == null", null, integerSetArgument.getValue());
    }
}
