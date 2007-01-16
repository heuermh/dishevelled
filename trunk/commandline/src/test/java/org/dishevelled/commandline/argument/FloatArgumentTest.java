/*

    dsh-commandline  Command line parser based on typed arguments.
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
 * Unit test for FloatArgument.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class FloatArgumentTest
    extends TestCase
{

    public void testFloatArgument()
    {
        FloatArgument fa = new FloatArgument("f", "float", "Float argument", true);
        assertNotNull("fa not null", fa);
        assertEquals("fa shortName == f", "f", fa.getShortName());
        assertEquals("fa longName == float", "float", fa.getLongName());
        assertEquals("fa description == Float argument", "Float argument", fa.getDescription());
        assertTrue("fa isRequired", fa.isRequired());
        assertFalse("fa wasFound == false", fa.wasFound());
        assertEquals("fa value == null", null, fa.getValue());
    }

    public void testValidArgumentShort()
        throws CommandLineParseException
    {
        Argument<Float> floatArgument = new FloatArgument("f", "float-argument", "Float argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument[] { floatArgument }));
        List<String> values = Arrays.asList(new String[] { "1.0f" });

        for (String value : values)
        {
            String[] args = new String[] { "-f", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            assertTrue("floatArgument wasFound == true", floatArgument.wasFound());
            assertNotNull("-f " + value + " not null", floatArgument.getValue());
        }
    }

    public void testValidArgumentLong()
        throws CommandLineParseException
    {
        Argument<Float> floatArgument = new FloatArgument("f", "float-argument", "Float argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument[] { floatArgument }));
        List<String> values = Arrays.asList(new String[] { "1.0f" });

        for (String value : values)
        {
            String[] args = new String[] { "--float-argument", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            assertTrue("floatArgument wasFound == true", floatArgument.wasFound());
            assertNotNull("--float-argument " + value + " not null", floatArgument.getValue());
        }
    }

    public void testInvalidArgumentShort()
    {
        Argument<Float> floatArgument = new FloatArgument("f", "float-argument", "Float argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument[] { floatArgument }));
        List<String> values = Arrays.asList(new String[] { "not-a-float" });

        for (String value : values)
        {
            try
            {
                String[] args = new String[] { "-f", value };
                CommandLine commandLine = new CommandLine(args);
                CommandLineParser.parse(commandLine, arguments);

                fail("-f " + value + " expected CommandLineParseException");
            }
            catch (CommandLineParseException e)
            {
                // expected
            }
        }
    }

    public void testInvalidArgumentLong()
    {
        Argument<Float> floatArgument = new FloatArgument("f", "float-argument", "Float argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument[] { floatArgument }));
        List<String> values = Arrays.asList(new String[] { "not-a-float" });

        for (String value : values)
        {
            try
            {
                String[] args = new String[] { "--float-argument", value };
                CommandLine commandLine = new CommandLine(args);
                CommandLineParser.parse(commandLine, arguments);

                fail("--float-argument " + value + " expected CommandLineParseException");
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
            Argument<Float> floatArgument = new FloatArgument("f", "float-argument", "Float argument", true);
            ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument[] { floatArgument }));

            String[] args = new String[] { "not-an-argument", "not-a-float" };
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
        Argument<Float> floatArgument = new FloatArgument("f", "float-argument", "Float argument", false);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument[] { floatArgument }));

        String[] args = new String[] { "not-an-argument", "not-a-float" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertFalse("floatArgument isRequired == false", floatArgument.isRequired());
        assertFalse("floatArgument wasFound == false", floatArgument.wasFound());
        assertEquals("floatArgument value == null", null, floatArgument.getValue());
    }
}
