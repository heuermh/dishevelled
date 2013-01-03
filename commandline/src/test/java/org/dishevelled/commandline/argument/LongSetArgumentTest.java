/*

    dsh-commandline  Command line parser based on typed arguments.
    Copyright (c) 2004-2013 held jointly by the individual authors.

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
 * Unit test for LongSetArgument.
 *
 * @author  Michael Heuer
 * @version $Revision: 1.2 $ $Date: 2006/01/02 23:16:28 $
 */
public class LongSetArgumentTest
    extends TestCase
{

    public void testLongSetArgument()
    {
        LongSetArgument lsa = new LongSetArgument("l", "long-set", "Long set argument", true);
        assertNotNull("lsa not null", lsa);
        assertEquals("lsa shortName == l", "l", lsa.getShortName());
        assertEquals("lsa longName == long-set", "long-set", lsa.getLongName());
        assertEquals("lsa description == Long set argument", "Long set argument", lsa.getDescription());
        assertTrue("lsa isRequired", lsa.isRequired());
        assertFalse("lsa wasFound == false", lsa.wasFound());
        assertEquals("lsa value == null", null, lsa.getValue());
    }

    public void testValidArgumentShort()
        throws CommandLineParseException
    {
        Argument<Set<Long>> longSetArgument = new LongSetArgument("l", "long-set", "Long set argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { longSetArgument }));
        List<String> values = Arrays.asList(new String[] { "1", "-1", "1,2", "1, 2", " 1,2 " });

        for (String value : values)
        {
            String[] args = new String[] { "-l", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            Set<Long> set = longSetArgument.getValue();
            assertNotNull("-l set not null", set);
            assertFalse("-l set not empty", set.isEmpty());
        }
    }

    public void testValidArgumentLong()
        throws CommandLineParseException
    {
        Argument<Set<Long>> longSetArgument = new LongSetArgument("l", "long-set", "Long set argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { longSetArgument }));
        List<String> values = Arrays.asList(new String[] { "1", "-1", "1,2", "1, 2", " 1,2 " });

        for (String value : values)
        {
            String[] args = new String[] { "--long-set", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            Set<Long> set = longSetArgument.getValue();
            assertNotNull("--long-set set not null", set);
            assertFalse("--long-set set not empty", set.isEmpty());
        }
    }

    public void testInvalidArgumentShort()
    {
        Argument<Set<Long>> longSetArgument = new LongSetArgument("l", "long-set", "Long set argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { longSetArgument }));
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
        Argument<Set<Long>> longSetArgument = new LongSetArgument("l", "long-set", "Long set argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { longSetArgument }));
        List<String> values = Arrays.asList(new String[] { "not-a-long" });

        for (String value : values)
        {
            try
            {
                String[] args = new String[] { "--long-set", value };
                CommandLine commandLine = new CommandLine(args);
                CommandLineParser.parse(commandLine, arguments);

                fail("--long-set " + value + " expected CommandLineParseException");
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
            Argument<Set<Long>> longSetArgument = new LongSetArgument("l", "long-set", "Long set argument", true);
            ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { longSetArgument }));

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
        Argument<Set<Long>> longSetArgument = new LongSetArgument("l", "long-set", "Long set argument", false);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { longSetArgument }));

        String[] args = new String[] { "not-an-argument", "not-a-long" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertFalse("longSetArgument isRequired == false", longSetArgument.isRequired());
        assertFalse("longSetArgument wasFound == false", longSetArgument.wasFound());
        assertEquals("longSetArgument value == null", null, longSetArgument.getValue());
    }
}
