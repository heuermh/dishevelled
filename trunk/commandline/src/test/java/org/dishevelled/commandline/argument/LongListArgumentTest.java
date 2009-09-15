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
 * Unit test for LongListArgument.
 *
 * @author  Michael Heuer
 * @version $Revision: 1.2 $ $Date: 2006/01/02 23:16:28 $
 */
public class LongListArgumentTest
    extends TestCase
{

    public void testLongListArgument()
    {
        LongListArgument lla = new LongListArgument("l", "long-list", "Long list argument", true);
        assertNotNull("lla not null", lla);
        assertEquals("lla shortName == l", "l", lla.getShortName());
        assertEquals("lla longName == long-list", "long-list", lla.getLongName());
        assertEquals("lla description == Long list argument", "Long list argument", lla.getDescription());
        assertTrue("lla isRequired", lla.isRequired());
        assertFalse("lla wasFound == false", lla.wasFound());
        assertEquals("lla value == null", null, lla.getValue());
    }

    public void testValidArgumentShort()
        throws CommandLineParseException
    {
        Argument<List<Long>> longListArgument = new LongListArgument("l", "long-list", "Long list argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { longListArgument }));
        List<String> values = Arrays.asList(new String[] { "1", "-1", "1,2", "1, 2", " 1,2 " });

        for (String value : values)
        {
            String[] args = new String[] { "-l", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            List<Long> list = longListArgument.getValue();
            assertNotNull("-l list not null", list);
            assertFalse("-l list not empty", list.isEmpty());
        }
    }

    public void testValidArgumentLong()
        throws CommandLineParseException
    {
        Argument<List<Long>> longListArgument = new LongListArgument("l", "long-list", "Long list argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { longListArgument }));
        List<String> values = Arrays.asList(new String[] { "1", "-1", "1,2", "1, 2", " 1,2 " });

        for (String value : values)
        {
            String[] args = new String[] { "--long-list", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            List<Long> list = longListArgument.getValue();
            assertNotNull("--long-list list not null", list);
            assertFalse("--long-list list not empty", list.isEmpty());
        }
    }

    public void testInvalidArgumentLong()
    {
        Argument<List<Long>> longListArgument = new LongListArgument("l", "long-list", "Long list argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { longListArgument }));
        List<String> values = Arrays.asList(new String[] { "not-a-long" });

        for (String value : values)
        {
            try
            {
                String[] args = new String[] { "--long-list", value };
                CommandLine commandLine = new CommandLine(args);
                CommandLineParser.parse(commandLine, arguments);

                fail("--long-list " + value + " expected CommandLineParseException");
            }
            catch (CommandLineParseException e)
            {
                // expected
            }
        }
    }

    public void testInvalidArgumentShort()
    {
        Argument<List<Long>> longListArgument = new LongListArgument("l", "long-list", "Long list argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { longListArgument }));
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

    public void testRequiredArgument()
    {
        try
        {
            Argument<List<Long>> longListArgument = new LongListArgument("l", "long-list", "Long list argument", true);
            ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { longListArgument }));

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
        Argument<List<Long>> longListArgument = new LongListArgument("l", "long-list", "Long list argument", false);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { longListArgument }));

        String[] args = new String[] { "not-an-argument", "not-a-long" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertFalse("longListArgument isRequired == false", longListArgument.isRequired());
        assertFalse("longListArgument wasFound == false", longListArgument.wasFound());
        assertEquals("longListArgument value == null", null, longListArgument.getValue());
    }
}
