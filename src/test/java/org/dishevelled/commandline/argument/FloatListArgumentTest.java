/*

    dsh-commandline  Command line parser based on typed arguments.
    Copyright (c) 2004-2012 held jointly by the individual authors.

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
 * Unit test for FloatListArgument.
 *
 * @author  Michael Heuer
 * @version $Revision: 1.2 $ $Date: 2006/01/02 23:16:28 $
 */
public class FloatListArgumentTest
    extends TestCase
{

    public void testFloatListArgument()
    {
        FloatListArgument fla = new FloatListArgument("f", "float-list", "Float list argument", true);
        assertNotNull("fla not null", fla);
        assertEquals("fla shortName == f", "f", fla.getShortName());
        assertEquals("fla longName == float-list", "float-list", fla.getLongName());
        assertEquals("fla description == Float list argument", "Float list argument", fla.getDescription());
        assertTrue("fla isRequired", fla.isRequired());
        assertFalse("fla wasFound == false", fla.wasFound());
        assertEquals("fla value == null", null, fla.getValue());
    }

    public void testValidArgumentShort()
        throws CommandLineParseException
    {
        Argument<List<Float>> floatListArgument = new FloatListArgument("f", "float-list", "Float list argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { floatListArgument }));
        List<String> values = Arrays.asList(new String[] { "1", "-1", "1.0", "-1.0", "1.0f", "1,2", "1, 2", " 1,2 " });

        for (String value : values)
        {
            String[] args = new String[] { "-f", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            List<Float> list = floatListArgument.getValue();
            assertNotNull("-f list not null", list);
            assertFalse("-f list not empty", list.isEmpty());
        }
    }

    public void testValidArgumentLong()
        throws CommandLineParseException
    {
        Argument<List<Float>> floatListArgument = new FloatListArgument("f", "float-list", "Float list argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { floatListArgument }));
        List<String> values = Arrays.asList(new String[] { "1", "-1", "1.0", "-1.0", "1.0f", "1,2", "1, 2", " 1,2 " });

        for (String value : values)
        {
            String[] args = new String[] { "--float-list", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            List<Float> list = floatListArgument.getValue();
            assertNotNull("--float-list list not null", list);
            assertFalse("--float-list list not empty", list.isEmpty());
        }
    }

    public void testInvalidArgumentLong()
    {
        Argument<List<Float>> floatListArgument = new FloatListArgument("f", "float-list", "Float list argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { floatListArgument }));
        List<String> values = Arrays.asList(new String[] { "not-a-float" });

        for (String value : values)
        {
            try
            {
                String[] args = new String[] { "--float-list", value };
                CommandLine commandLine = new CommandLine(args);
                CommandLineParser.parse(commandLine, arguments);

                fail("--float-list " + value + " expected CommandLineParseException");
            }
            catch (CommandLineParseException e)
            {
                // expected
            }
        }
    }

    public void testInvalidArgumentShort()
    {
        Argument<List<Float>> floatListArgument = new FloatListArgument("f", "float-list", "Float list argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { floatListArgument }));
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

    public void testRequiredArgument()
    {
        try
        {
            Argument<List<Float>> floatListArgument = new FloatListArgument("f", "float-list", "Float list argument", true);
            ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { floatListArgument }));

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
        Argument<List<Float>> floatListArgument = new FloatListArgument("f", "float-list", "Float list argument", false);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { floatListArgument }));

        String[] args = new String[] { "not-an-argument", "not-a-float" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertFalse("floatListArgument isRequired == false", floatListArgument.isRequired());
        assertFalse("floatListArgument wasFound == false", floatListArgument.wasFound());
        assertEquals("floatListArgument value == null", null, floatListArgument.getValue());
    }
}
