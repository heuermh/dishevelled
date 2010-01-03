/*

    dsh-commandline  Command line parser based on typed arguments.
    Copyright (c) 2004-2010 held jointly by the individual authors.

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

import java.util.Arrays;

import junit.framework.TestCase;

import org.dishevelled.commandline.Argument;
import org.dishevelled.commandline.ArgumentList;
import org.dishevelled.commandline.CommandLine;
import org.dishevelled.commandline.CommandLineParser;
import org.dishevelled.commandline.CommandLineParseException;

/**
 * Unit test for ByteArgument.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class ByteArgumentTest
    extends TestCase
{

    public void testByteArgument()
    {
        ByteArgument ba = new ByteArgument("b", "byte", "Byte argument", true);
        assertNotNull("ba not null", ba);
        assertEquals("ba shortName == b", "b", ba.getShortName());
        assertEquals("ba longName == byte", "byte", ba.getLongName());
        assertEquals("ba description == Byte argument", "Byte argument", ba.getDescription());
        assertTrue("ba isRequired", ba.isRequired());
        assertFalse("ba wasFound == false", ba.wasFound());
        assertEquals("ba value == null", null, ba.getValue());
    }

    public void testValidArgumentShort()
        throws CommandLineParseException
    {
        Argument<Byte> byteArgument = new ByteArgument("b", "byte", "Byte argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { byteArgument }));

        String[] args = new String[] { "-b", "127" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertEquals("-b == 127", Byte.valueOf("127"), byteArgument.getValue());
    }

    public void testValidArgumentLong()
        throws CommandLineParseException
    {
        Argument<Byte> byteArgument = new ByteArgument("b", "byte", "Byte argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { byteArgument }));

        String[] args = new String[] { "--byte", "127" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertEquals("--byte == 127", Byte.valueOf("127"), byteArgument.getValue());
    }

    public void testValidNegativeArgumentShort()
        throws CommandLineParseException
    {
        Argument<Byte> byteArgument = new ByteArgument("b", "byte", "Byte argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { byteArgument }));

        String[] args = new String[] { "-b", "-128" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertEquals("-b == -128", Byte.valueOf("-128"), byteArgument.getValue());
    }

    public void testValidNegativeArgumentLong()
        throws CommandLineParseException
    {
        Argument<Byte> byteArgument = new ByteArgument("b", "byte", "Byte argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { byteArgument }));

        String[] args = new String[] { "--byte", "-128" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertEquals("--byte == -128", Byte.valueOf("-128"), byteArgument.getValue());
    }

    public void testInvalidArgumentShort()
    {
        try
        {
            Argument<Byte> byteArgument = new ByteArgument("b", "byte", "Byte argument", true);
            ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { byteArgument }));

            String[] args = new String[] { "-b", "not-a-byte" };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            fail("-b not-a-byte expected CommandLineParseException");
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
            Argument<Byte> byteArgument = new ByteArgument("b", "byte", "Byte argument", true);
            ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { byteArgument }));

            String[] args = new String[] { "--byte", "not-a-byte" };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            fail("--byte not-a-byte expected CommandLineParseException");
        }
        catch (CommandLineParseException e)
        {
            // expected
        }
    }

    public void testTooSmallArgumentShort()
    {
        try
        {
            Argument<Byte> byteArgument = new ByteArgument("b", "byte", "Byte argument", true);
            ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { byteArgument }));

            String[] args = new String[] { "-b", "-129" };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            fail("-b -129 expected CommandLineParseException");
        }
        catch (CommandLineParseException e)
        {
            // expected
        }
    }

    public void testTooLargeArgumentShort()
    {
        try
        {
            Argument<Byte> byteArgument = new ByteArgument("b", "byte", "Byte argument", true);
            ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { byteArgument }));

            String[] args = new String[] { "-b", "128" };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            fail("-b 128 expected CommandLineParseException");
        }
        catch (CommandLineParseException e)
        {
            // expected
        }
    }

    public void testTooSmallArgumentLong()
    {
        try
        {
            Argument<Byte> byteArgument = new ByteArgument("b", "byte", "Byte argument", true);
            ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { byteArgument }));

            String[] args = new String[] { "--byte", "-129" };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            fail("--byte -129 expected CommandLineParseException");
        }
        catch (CommandLineParseException e)
        {
            // expected
        }
    }

    public void testTooLargeArgumentLong()
    {
        try
        {
            Argument<Byte> byteArgument = new ByteArgument("b", "byte", "Byte argument", true);
            ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { byteArgument }));

            String[] args = new String[] { "--byte", "128" };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            fail("--byte 128 expected CommandLineParseException");
        }
        catch (CommandLineParseException e)
        {
            // expected
        }
    }
}
