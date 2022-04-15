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

import java.util.List;
import java.util.Arrays;

import junit.framework.TestCase;

import org.dishevelled.commandline.Argument;
import org.dishevelled.commandline.ArgumentList;
import org.dishevelled.commandline.CommandLine;
import org.dishevelled.commandline.CommandLineParser;
import org.dishevelled.commandline.CommandLineParseException;

/**
 * Unit test for StringArgument.
 *
 * @author  Michael Heuer
 */
public class StringArgumentTest
    extends TestCase
{

    public void testStringArgument()
    {
        StringArgument sa = new StringArgument("s", "string", "String argument", true);
        assertNotNull("sa not null", sa);
        assertEquals("sa shortName == s", "s", sa.getShortName());
        assertEquals("sa longName == string", "string", sa.getLongName());
        assertEquals("sa description == String argument", "String argument", sa.getDescription());
        assertTrue("sa isRequired", sa.isRequired());
        assertFalse("sa wasFound == false", sa.wasFound());
        assertEquals("sa value == null", null, sa.getValue());
        assertEquals("default value", sa.getValue("default value"));
    }

    public void testObjectContract()
    {
        StringArgument a0 = new StringArgument("s", "string", "String argument", true);
        StringArgument a1 = new StringArgument("s", "string", "String argument", true);

        assertEquals("a0 equals itself", a0, a0);
        assertEquals("a1 equals itself", a1, a1);
        assertEquals("a0 hashCode == a0 hashCode", a0.hashCode(), a0.hashCode());
        assertEquals("a1 hashCode == a1 hashCode", a1.hashCode(), a1.hashCode());
        assertFalse("a0 not equals a1", a0.equals(a1));
        assertFalse("a1 not equals a0", a1.equals(a0));
    }

    public void testValidArgumentShort()
        throws CommandLineParseException
    {
        Argument<String> stringArgument = new StringArgument("s", "string-argument", "String argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { stringArgument }));
        List<String> values = Arrays.asList(new String[] { "foo" });

        for (String value : values)
        {
            String[] args = new String[] { "-s", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            assertNotNull("-s " + value + " not null", stringArgument.getValue());
            assertFalse("default value".equals(stringArgument.getValue("default value")));
        }
    }

    public void testValidArgumentLong()
        throws CommandLineParseException
    {
        Argument<String> stringArgument = new StringArgument("s", "string-argument", "String argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { stringArgument }));
        List<String> values = Arrays.asList(new String[] { "foo" });

        for (String value : values)
        {
            String[] args = new String[] { "--string-argument", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            assertNotNull("--string-argument " + value + " not null", stringArgument.getValue());
            assertFalse("default value".equals(stringArgument.getValue("default value")));
        }
    }

    /*
    public void testInvalidArgumentShort()
    {
        Argument<String> stringArgument = new StringArgument("d", "string-argument", "String argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { stringArgument }));
        List<String> values = Arrays.asList(new String[] { "not-a-string" });

        for (String value : values)
        {
            try
            {
                String[] args = new String[] { "-d", value };
                CommandLine commandLine = new CommandLine(args);
                CommandLineParser.parse(commandLine, arguments);

                fail("-d " + value + " expected CommandLineParseException");
            }
            catch (CommandLineParseException e)
            {
                // expected
            }
        }
    }

    public void testInvalidArgumentLong()
    {
        Argument<String> stringArgument = new StringArgument("d", "string-argument", "String argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { stringArgument }));
        List<String> values = Arrays.asList(new String[] { "not-a-string" });

        for (String value : values)
        {
            try
            {
                String[] args = new String[] { "--string-argument", value };
                CommandLine commandLine = new CommandLine(args);
                CommandLineParser.parse(commandLine, arguments);

                fail("--string-argument " + value + " expected CommandLineParseException");
            }
            catch (CommandLineParseException e)
            {
                // expected
            }
        }
    }
    */

    public void testRequiredArgument()
    {
        try
        {
            Argument<String> stringArgument = new StringArgument("s", "string-argument", "String argument", true);
            ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { stringArgument }));

            String[] args = new String[] { "not-an-argument" };
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
        Argument<String> stringArgument = new StringArgument("s", "string-argument", "String argument", false);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { stringArgument }));

        String[] args = new String[] { "not-an-argument" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertFalse("stringArgument isRequired == false", stringArgument.isRequired());
        assertFalse("stringArgument wasFound == false", stringArgument.wasFound());
        assertEquals("stringArgument value == null", null, stringArgument.getValue());
    }
}
