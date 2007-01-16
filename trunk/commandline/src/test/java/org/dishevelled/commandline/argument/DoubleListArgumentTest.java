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
 * Unit test for DoubleListArgument.
 *
 * @author  Michael Heuer
 * @version $Revision: 1.2 $ $Date: 2006/01/02 23:16:28 $
 */
public class DoubleListArgumentTest
    extends TestCase
{

    public void testDoubleListArgument()
    {
        DoubleListArgument dla = new DoubleListArgument("d", "double-list", "Double list argument", true);
        assertNotNull("dla not null", dla);
        assertEquals("dla shortName == d", "d", dla.getShortName());
        assertEquals("dla longName == double-list", "double-list", dla.getLongName());
        assertEquals("dla description == Double list argument", "Double list argument", dla.getDescription());
        assertTrue("dla isRequired", dla.isRequired());
        assertFalse("dla wasFound == false", dla.wasFound());
        assertEquals("dla value == null", null, dla.getValue());
    }

    public void testValidArgumentShort()
        throws CommandLineParseException
    {
        Argument<List<Double>> doubleListArgument = new DoubleListArgument("d", "double-list", "Double list argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument[] { doubleListArgument }));
        List<String> values = Arrays.asList(new String[] { "1", "-1", "1.0", "-1.0", "1.0d", "1,2", "1, 2", " 1,2 " });

        for (String value : values)
        {
            String[] args = new String[] { "-d", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            List<Double> list = doubleListArgument.getValue();
            assertNotNull("-d list not null", list);
            assertFalse("-d list not empty", list.isEmpty());
        }
    }

    public void testValidArgumentLong()
        throws CommandLineParseException
    {
        Argument<List<Double>> doubleListArgument = new DoubleListArgument("d", "double-list", "Double list argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument[] { doubleListArgument }));
        List<String> values = Arrays.asList(new String[] { "1", "-1", "1.0", "-1.0", "1.0d", "1,2", "1, 2", " 1,2 " });

        for (String value : values)
        {
            String[] args = new String[] { "--double-list", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            List<Double> list = doubleListArgument.getValue();
            assertNotNull("--double-list list not null", list);
            assertFalse("--double-list list not empty", list.isEmpty());
        }
    }

    public void testInvalidArgumentLong()
    {
        Argument<List<Double>> doubleListArgument = new DoubleListArgument("d", "double-list", "Double list argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument[] { doubleListArgument }));
        List<String> values = Arrays.asList(new String[] { "not-a-double" });

        for (String value : values)
        {
            try
            {
                String[] args = new String[] { "--double-list", value };
                CommandLine commandLine = new CommandLine(args);
                CommandLineParser.parse(commandLine, arguments);

                fail("--double-list " + value + " expected CommandLineParseException");
            }
            catch (CommandLineParseException e)
            {
                // expected
            }
        }
    }

    public void testInvalidArgumentShort()
    {
        Argument<List<Double>> doubleListArgument = new DoubleListArgument("d", "double-list", "Double list argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument[] { doubleListArgument }));
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

    public void testRequiredArgument()
    {
        try
        {
            Argument<List<Double>> doubleListArgument = new DoubleListArgument("d", "double-list", "Double list argument", true);
            ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument[] { doubleListArgument }));

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
        Argument<List<Double>> doubleListArgument = new DoubleListArgument("d", "double-list", "Double list argument", false);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument[] { doubleListArgument }));

        String[] args = new String[] { "not-an-argument", "not-a-double" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertFalse("doubleListArgument isRequired == false", doubleListArgument.isRequired());
        assertFalse("doubleListArgument wasFound == false", doubleListArgument.wasFound());
        assertEquals("doubleListArgument value == null", null, doubleListArgument.getValue());
    }
}
