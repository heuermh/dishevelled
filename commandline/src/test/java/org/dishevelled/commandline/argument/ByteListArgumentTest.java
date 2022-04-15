/*

    dsh-commandline  Command line parser based on typed arguments.
    Copyright (c) 2004-2022 held jointly by the individual authors.

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
 * Unit test for ByteListArgument.
 *
 * @author  Michael Heuer
 */
public class ByteListArgumentTest
    extends TestCase
{

    public void testByteListArgument()
    {
        ByteListArgument bla = new ByteListArgument("b", "byte-list", "Byte list argument", true);
        assertNotNull("bla not null", bla);
        assertEquals("bla shortName == b", "b", bla.getShortName());
        assertEquals("bla longName == byte-list", "byte-list", bla.getLongName());
        assertEquals("bla description == Byte list argument", "Byte list argument", bla.getDescription());
        assertTrue("bla isRequired", bla.isRequired());
        assertFalse("bla wasFound == false", bla.wasFound());
        assertEquals("bla value == null", null, bla.getValue());
    }

    public void testValidArgumentShort()
        throws CommandLineParseException
    {
        Argument<List<Byte>> byteListArgument = new ByteListArgument("b", "byte-list", "Byte list argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { byteListArgument }));
        List<String> values = Arrays.asList(new String[] { "127", "-128,-128", "-128, -128", "-1,-1,-1", "-1 , -1, -1 " });

        for (String value : values)
        {
            String[] args = new String[] { "-b", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            List<Byte> list = byteListArgument.getValue();
            assertNotNull("-b list not null", list);
            assertFalse("-b list not empty", list.isEmpty());
        }
    }

    public void testValidArgumentLong()
        throws CommandLineParseException
    {
        Argument<List<Byte>> byteListArgument = new ByteListArgument("b", "byte-list", "Byte list argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { byteListArgument }));
        List<String> values = Arrays.asList(new String[] { "127", "-128,-128", "-128, -128", "-1,-1,-1", "-1 , -1, -1 " });

        for (String value : values)
        {
            String[] args = new String[] { "--byte-list", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            List<Byte> list = byteListArgument.getValue();
            assertNotNull("--byte-list list not null", list);
            assertFalse("--byte-list list not empty", list.isEmpty());
        }
    }

    public void testInvalidArgumentLong()
    {
        Argument<List<Byte>> byteListArgument = new ByteListArgument("b", "byte-list", "Byte list argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { byteListArgument }));
        List<String> values = Arrays.asList(new String[] { "not-a-byte", "128", "-129" });

        for (String value : values)
        {
            try
            {
                String[] args = new String[] { "--byte-list", value };
                CommandLine commandLine = new CommandLine(args);
                CommandLineParser.parse(commandLine, arguments);

                fail("--byte-list " + value + " expected CommandLineParseException");
            }
            catch (CommandLineParseException e)
            {
                // expected
            }
        }
    }

    public void testInvalidArgumentShort()
    {
        Argument<List<Byte>> byteListArgument = new ByteListArgument("b", "byte-list", "Byte list argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { byteListArgument }));
        List<String> values = Arrays.asList(new String[] { "not-a-byte", "128", "-129" });

        for (String value : values)
        {
            try
            {
                String[] args = new String[] { "-b", value };
                CommandLine commandLine = new CommandLine(args);
                CommandLineParser.parse(commandLine, arguments);

                fail("-b " + value + " expected CommandLineParseException");
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
            Argument<List<Byte>> byteListArgument = new ByteListArgument("b", "byte-list", "Byte list argument", true);
            ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { byteListArgument }));

            String[] args = new String[] { "not-an-argument", "not-a-byte" };
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
        Argument<List<Byte>> byteListArgument = new ByteListArgument("b", "byte-list", "Byte list argument", false);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { byteListArgument }));

        String[] args = new String[] { "not-an-argument", "not-a-byte" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertFalse("byteListArgument isRequired == false", byteListArgument.isRequired());
        assertFalse("byteListArgument wasFound == false", byteListArgument.wasFound());
        assertEquals("byteListArgument value == null", null, byteListArgument.getValue());
    }
}
