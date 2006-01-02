/*

    dsh-commandline  Simple command line parser based on typed arguments.
    Copyright (c) 2004-2006 held jointly by the individual authors.

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
package org.dishevelled.commandline.argument;

import java.util.List;
import java.util.Arrays;

import junit.framework.TestCase;

import org.dishevelled.commandline.Argument;
import org.dishevelled.commandline.CommandLine;
import org.dishevelled.commandline.CommandLineParser;
import org.dishevelled.commandline.CommandLineParseException;

/**
 * Unit test for LongArgument.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class LongArgumentTest
    extends TestCase
{

    public void testLongArgument()
    {
        LongArgument la = new LongArgument("l", "long", "Long argument", true);
        assertNotNull("la not null", la);
        assertEquals("la shortName == l", "l", la.getShortName());
        assertEquals("la longName == long", "long", la.getLongName());
        assertEquals("la description == Long argument", "Long argument", la.getDescription());
        assertTrue("la isRequired", la.isRequired());
        assertFalse("la wasFound == false", la.wasFound());
        assertEquals("la value == null", null, la.getValue());
    }

    public void testValidArgumentShort()
        throws CommandLineParseException
    {
        Argument<Long> longArgument = new LongArgument("l", "long-argument", "Long argument", true);
        List<Argument<?>> arguments = Arrays.asList(new Argument<?>[] { longArgument });
        List<String> values = Arrays.asList(new String[] { "1" });

        for (String value : values)
        {
            String[] args = new String[] { "-l", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            assertNotNull("-l" + value + " not null", longArgument.getValue());
        }
    }

    public void testValidArgumentLong()
        throws CommandLineParseException
    {
        Argument<Long> longArgument = new LongArgument("l", "long-argument", "Long argument", true);
        List<Argument<?>> arguments = Arrays.asList(new Argument<?>[] { longArgument });
        List<String> values = Arrays.asList(new String[] { "1" });

        for (String value : values)
        {
            String[] args = new String[] { "--long-argument", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            assertNotNull("--long-argument " + value + " not null", longArgument.getValue());
        }
    }

    public void testInvalidArgumentShort()
    {
        Argument<Long> longArgument = new LongArgument("l", "long-argument", "Long argument", true);
        List<Argument<?>> arguments = Arrays.asList(new Argument<?>[] { longArgument });
        List<String> values = Arrays.asList(new String[] { "not-a-long" });

        for (String value : values)
        {
            try
            {
                String[] args = new String[] { "-l", value };
                CommandLine commandLine = new CommandLine(args);
                CommandLineParser.parse(commandLine, arguments);

                fail("-l " + value + " expected CommandLineParseException");
            }
            catch (CommandLineParseException e)
            {
                // expected
            }
        }
    }

    public void testInvalidArgumentLong()
    {
        Argument<Long> longArgument = new LongArgument("l", "long-argument", "Long argument", true);
        List<Argument<?>> arguments = Arrays.asList(new Argument<?>[] { longArgument });
        List<String> values = Arrays.asList(new String[] { "not-a-long" });

        for (String value : values)
        {
            try
            {
                String[] args = new String[] { "--long-argument", value };
                CommandLine commandLine = new CommandLine(args);
                CommandLineParser.parse(commandLine, arguments);

                fail("--long-argument " + value + " expected CommandLineParseException");
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
            Argument<Long> longArgument = new LongArgument("l", "long-argument", "Long argument", true);
            List<Argument<?>> arguments = Arrays.asList(new Argument<?>[] { longArgument });

            String[] args = new String[] { "not-an-argument", "not-a-long" };
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
        Argument<Long> longArgument = new LongArgument("l", "long-argument", "Long argument", false);
        List<Argument<?>> arguments = Arrays.asList(new Argument<?>[] { longArgument });

        String[] args = new String[] { "not-an-argument", "not-a-long" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertFalse("longArgument isRequired == false", longArgument.isRequired());
        assertFalse("longArgument wasFound == false", longArgument.wasFound());
        assertEquals("longArgument value == null", null, longArgument.getValue());
    }
}