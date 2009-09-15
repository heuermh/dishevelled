/*

    dsh-commandline  Command line parser based on typed arguments.
    Copyright (c) 2004-2009 held jointly by the individual authors.

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
 * Unit test for DoubleArgument.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class DoubleArgumentTest
    extends TestCase
{

    public void testDoubleArgument()
    {
        DoubleArgument da = new DoubleArgument("d", "double", "Double argument", true);
        assertNotNull("da not null", da);
        assertEquals("da shortName == d", "d", da.getShortName());
        assertEquals("da longName == double", "double", da.getLongName());
        assertEquals("da description == Double argument", "Double argument", da.getDescription());
        assertTrue("da isRequired", da.isRequired());
        assertFalse("da wasFound == false", da.wasFound());
        assertEquals("da value == null", null, da.getValue());
    }

    public void testValidArgumentShort()
        throws CommandLineParseException
    {
        Argument<Double> doubleArgument = new DoubleArgument("d", "double-argument", "Double argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { doubleArgument }));
        List<String> values = Arrays.asList(new String[] { "1.0d" });

        for (String value : values)
        {
            String[] args = new String[] { "-d", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            assertNotNull("-d not null", doubleArgument.getValue());
        }
    }

    public void testValidArgumentLong()
        throws CommandLineParseException
    {
        Argument<Double> doubleArgument = new DoubleArgument("d", "double-argument", "Double argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { doubleArgument }));
        List<String> values = Arrays.asList(new String[] { "1.0d" });

        for (String value : values)
        {
            String[] args = new String[] { "--double-argument", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            assertNotNull("--double-argument " + value + " not null", doubleArgument.getValue());
        }
    }

    public void testInvalidArgumentShort()
    {
        Argument<Double> doubleArgument = new DoubleArgument("d", "double-argument", "Double argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { doubleArgument }));
        List<String> values = Arrays.asList(new String[] { "not-a-double" });

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
        Argument<Double> doubleArgument = new DoubleArgument("d", "double-argument", "Double argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { doubleArgument }));
        List<String> values = Arrays.asList(new String[] { "not-a-double" });

        for (String value : values)
        {
            try
            {
                String[] args = new String[] { "--double-argument", value };
                CommandLine commandLine = new CommandLine(args);
                CommandLineParser.parse(commandLine, arguments);

                fail("--double-argument " + value + " expected CommandLineParseException");
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
            Argument<Double> doubleArgument = new DoubleArgument("d", "double-argument", "Double argument", true);
            ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { doubleArgument }));

            String[] args = new String[] { "not-an-argument", "not-a-double" };
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
        Argument<Double> doubleArgument = new DoubleArgument("d", "double-argument", "Double argument", false);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { doubleArgument }));

        String[] args = new String[] { "not-an-argument", "not-a-double" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertFalse("doubleArgument isRequired == false", doubleArgument.isRequired());
        assertFalse("doubleArgument wasFound == false", doubleArgument.wasFound());
        assertEquals("doubleArgument value == null", null, doubleArgument.getValue());
    }
}
