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
 * Unit test for FloatSetArgument.
 *
 * @author  Michael Heuer
 * @version $Revision: 1.2 $ $Date: 2006/01/02 23:16:28 $
 */
public class FloatSetArgumentTest
    extends TestCase
{

    public void testFloatSetArgument()
    {
        FloatSetArgument fsa = new FloatSetArgument("f", "float-set", "Float set argument", true);
        assertNotNull("fsa not null", fsa);
        assertEquals("fsa shortName == f", "f", fsa.getShortName());
        assertEquals("fsa longName == float-set", "float-set", fsa.getLongName());
        assertEquals("fsa description == Float set argument", "Float set argument", fsa.getDescription());
        assertTrue("fsa isRequired", fsa.isRequired());
        assertFalse("fsa wasFound == false", fsa.wasFound());
        assertEquals("fsa value == null", null, fsa.getValue());
    }

    public void testValidArgumentShort()
        throws CommandLineParseException
    {
        Argument<Set<Float>> floatSetArgument = new FloatSetArgument("f", "float-set", "Float set argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument[] { floatSetArgument }));
        List<String> values = Arrays.asList(new String[] { "1", "-1", "1.0", "-1.0", "1.0f", "1,2", "1, 2", " 1,2 " });

        for (String value : values)
        {
            String[] args = new String[] { "-f", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            Set<Float> set = floatSetArgument.getValue();
            assertNotNull("-f set not null", set);
            assertFalse("-f set not empty", set.isEmpty());
        }
    }

    public void testValidArgumentLong()
        throws CommandLineParseException
    {
        Argument<Set<Float>> floatSetArgument = new FloatSetArgument("f", "float-set", "Float set argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument[] { floatSetArgument }));
        List<String> values = Arrays.asList(new String[] { "1", "-1", "1.0", "-1.0", "1.0f", "1,2", "1, 2", " 1,2 " });

        for (String value : values)
        {
            String[] args = new String[] { "--float-set", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            Set<Float> set = floatSetArgument.getValue();
            assertNotNull("--float-set set not null", set);
            assertFalse("--float-set set not empty", set.isEmpty());
        }
    }

    public void testInvalidArgumentShort()
    {
        Argument<Set<Float>> floatSetArgument = new FloatSetArgument("f", "float-set", "Float set argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument[] { floatSetArgument }));
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
        Argument<Set<Float>> floatSetArgument = new FloatSetArgument("f", "float-set", "Float set argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument[] { floatSetArgument }));
        List<String> values = Arrays.asList(new String[] { "not-a-float" });

        for (String value : values)
        {
            try
            {
                String[] args = new String[] { "--float-set", value };
                CommandLine commandLine = new CommandLine(args);
                CommandLineParser.parse(commandLine, arguments);

                fail("--float-set " + value + " expected CommandLineParseException");
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
            Argument<Set<Float>> floatSetArgument = new FloatSetArgument("f", "float-set", "Float set argument", true);
            ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument[] { floatSetArgument }));

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
        Argument<Set<Float>> floatSetArgument = new FloatSetArgument("f", "float-set", "Float set argument", false);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument[] { floatSetArgument }));

        String[] args = new String[] { "not-an-argument", "not-a-float" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertFalse("floatSetArgument isRequired == false", floatSetArgument.isRequired());
        assertFalse("floatSetArgument wasFound == false", floatSetArgument.wasFound());
        assertEquals("floatSetArgument value == null", null, floatSetArgument.getValue());
    }
}