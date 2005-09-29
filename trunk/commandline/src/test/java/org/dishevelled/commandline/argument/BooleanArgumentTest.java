/*

    dsh-commandline  Simple command line parser based on typed arguments.
    Copyright (c) 2004-2005 held jointly by the individual authors.

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
 * Unit test for BooleanArgument.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class BooleanArgumentTest
    extends TestCase
{

    public void testBooleanArgument()
    {
        BooleanArgument ba = new BooleanArgument("b", "boolean", "Boolean argument", true);
        assertNotNull("ba not null", ba);
        assertEquals("ba shortName == b", "b", ba.getShortName());
        assertEquals("ba longName == boolean", "boolean", ba.getLongName());
        assertEquals("ba description == Boolean argument", "Boolean argument", ba.getDescription());
        assertTrue("ba isRequired", ba.isRequired());
        assertFalse("ba wasFound == false", ba.wasFound());
        assertEquals("ba value == null", null, ba.getValue());
    }

    public void testValidArgumentTrueShort()
        throws CommandLineParseException
    {
        Argument<Boolean> booleanArgument = new BooleanArgument("b", "boolean", "Boolean argument", true);
        List<Argument<?>> arguments = Arrays.asList(new Argument<?>[] { booleanArgument });
        List<String> values = Arrays.asList(new String[] { "true", "TRUE", "TRue", "t", "T", "1" });

        for (String value : values)
        {
            String[] args = new String[] { "-b", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            assertEquals("-b == true", Boolean.TRUE, booleanArgument.getValue());
        }
    }

    public void testValidArgumentTrueLong()
        throws CommandLineParseException
    {
        Argument<Boolean> booleanArgument = new BooleanArgument("b", "boolean", "Boolean argument", true);
        List<Argument<?>> arguments = Arrays.asList(new Argument<?>[] { booleanArgument });
        List<String> values = Arrays.asList(new String[] { "true", "TRUE", "TRue", "t", "T", "1" });

        for (String value : values)
        {
            String[] args = new String[] { "--boolean", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            assertEquals("--boolean == true", Boolean.TRUE, booleanArgument.getValue());
        }
    }

    public void testValidArgumentFalseShort()
        throws CommandLineParseException
    {
        Argument<Boolean> booleanArgument = new BooleanArgument("b", "boolean", "Boolean argument", true);
        List<Argument<?>> arguments = Arrays.asList(new Argument<?>[] { booleanArgument });
        List<String> values = Arrays.asList(new String[] { "false", "FALSE", "FALse", "f", "F", "0" });

        for (String value : values)
        {
            String[] args = new String[] { "-b", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            assertEquals("-b == false", Boolean.FALSE, booleanArgument.getValue());
        }
    }

    public void testValidArgumentFalseLong()
        throws CommandLineParseException
    {
        Argument<Boolean> booleanArgument = new BooleanArgument("b", "boolean", "Boolean argument", true);
        List<Argument<?>> arguments = Arrays.asList(new Argument<?>[] { booleanArgument });
        List<String> values = Arrays.asList(new String[] { "false", "FALSE", "FALse", "f", "F", "0" });

        for (String value : values)
        {
            String[] args = new String[] { "--boolean", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            assertEquals("--boolean == false", Boolean.FALSE, booleanArgument.getValue());
        }
    }

    public void testInvalidArgumentShort()
    {
        try
        {
            Argument<Boolean> booleanArgument = new BooleanArgument("b", "boolean", "Boolean argument", true);
            List<Argument<?>> arguments = Arrays.asList(new Argument<?>[] { booleanArgument });

            String[] args = new String[] { "-b", "not-a-boolean" };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            fail("-b not-a-boolean expected CommandLineParseException");
        }
        catch (CommandLineParseException e)
        {
            // expected
        }
    }

    public void testInvalidArgumentLong()
    {
        try
        {
            Argument<Boolean> booleanArgument = new BooleanArgument("b", "boolean", "Boolean argument", true);
            List<Argument<?>> arguments = Arrays.asList(new Argument<?>[] { booleanArgument });

            String[] args = new String[] { "--boolean", "not-a-boolean" };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            fail("--boolean not-a-boolean expected CommandLineParseException");
        }
        catch (CommandLineParseException e)
        {
            // expected
        }
    }

    public void testRequiredArgument()
    {
        try
        {
            Argument<Boolean> booleanArgument = new BooleanArgument("b", "boolean", "Boolean argument", true);
            List<Argument<?>> arguments = Arrays.asList(new Argument<?>[] { booleanArgument });

            String[] args = new String[] { "not-an-argument", "not-a-boolean" };
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
        Argument<Boolean> booleanArgument = new BooleanArgument("b", "boolean", "Boolean argument", false);
        List<Argument<?>> arguments = Arrays.asList(new Argument<?>[] { booleanArgument });

        String[] args = new String[] { "not-an-argument", "not-a-boolean" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertFalse("booleanArgument isRequired == false", booleanArgument.isRequired());
        assertFalse("booleanArgument wasFound == false", booleanArgument.wasFound());
        assertEquals("booleanArgument value == null", null, booleanArgument.getValue());
    }
}
