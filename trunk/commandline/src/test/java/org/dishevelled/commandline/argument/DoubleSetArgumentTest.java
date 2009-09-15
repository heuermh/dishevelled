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
 * Unit test for DoubleSetArgument.
 *
 * @author  Michael Heuer
 * @version $Revision: 1.2 $ $Date: 2006/01/02 23:16:28 $
 */
public class DoubleSetArgumentTest
    extends TestCase
{

    public void testDoubleSetArgument()
    {
        DoubleSetArgument dsa = new DoubleSetArgument("d", "double-set", "Double set argument", true);
        assertNotNull("dsa not null", dsa);
        assertEquals("dsa shortName == d", "d", dsa.getShortName());
        assertEquals("dsa longName == double-set", "double-set", dsa.getLongName());
        assertEquals("dsa description == Double set argument", "Double set argument", dsa.getDescription());
        assertTrue("dsa isRequired", dsa.isRequired());
        assertFalse("dsa wasFound == false", dsa.wasFound());
        assertEquals("dsa value == null", null, dsa.getValue());
    }

    public void testValidArgumentShort()
        throws CommandLineParseException
    {
        Argument<Set<Double>> doubleSetArgument = new DoubleSetArgument("d", "double-set", "Double set argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { doubleSetArgument }));
        List<String> values = Arrays.asList(new String[] { "1", "-1", "1.0", "-1.0", "1.0d", "1,2", "1, 2", " 1,2 " });

        for (String value : values)
        {
            String[] args = new String[] { "-d", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            Set<Double> set = doubleSetArgument.getValue();
            assertNotNull("-d set not null", set);
            assertFalse("-d set not empty", set.isEmpty());
        }
    }

    public void testValidArgumentLong()
        throws CommandLineParseException
    {
        Argument<Set<Double>> doubleSetArgument = new DoubleSetArgument("d", "double-set", "Double set argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { doubleSetArgument }));
        List<String> values = Arrays.asList(new String[] { "1", "-1", "1.0", "-1.0", "1.0d", "1,2", "1, 2", " 1,2 " });

        for (String value : values)
        {
            String[] args = new String[] { "--double-set", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            Set<Double> set = doubleSetArgument.getValue();
            assertNotNull("--double-set set not null", set);
            assertFalse("--double-set set not empty", set.isEmpty());
        }
    }

    public void testInvalidArgumentShort()
    {
        Argument<Set<Double>> doubleSetArgument = new DoubleSetArgument("d", "double-set", "Double set argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { doubleSetArgument }));
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
        Argument<Set<Double>> doubleSetArgument = new DoubleSetArgument("d", "double-set", "Double set argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { doubleSetArgument }));
        List<String> values = Arrays.asList(new String[] { "not-a-double" });

        for (String value : values)
        {
            try
            {
                String[] args = new String[] { "--double-set", value };
                CommandLine commandLine = new CommandLine(args);
                CommandLineParser.parse(commandLine, arguments);

                fail("--double-set " + value + " expected CommandLineParseException");
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
            Argument<Set<Double>> doubleSetArgument = new DoubleSetArgument("d", "double-set", "Double set argument", true);
            ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { doubleSetArgument }));

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
        Argument<Set<Double>> doubleSetArgument = new DoubleSetArgument("d", "double-set", "Double set argument", false);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { doubleSetArgument }));

        String[] args = new String[] { "not-an-argument", "not-a-double" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertFalse("doubleSetArgument isRequired == false", doubleSetArgument.isRequired());
        assertFalse("doubleSetArgument wasFound == false", doubleSetArgument.wasFound());
        assertEquals("doubleSetArgument value == null", null, doubleSetArgument.getValue());
    }
}
