/*

    dsh-commandline  Command line parser based on typed arguments.
    Copyright (c) 2004-2008 held jointly by the individual authors.

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
 * Unit test for ByteSetArgument.
 *
 * @author  Michael Heuer
 * @version $Revision: 1.2 $ $Date: 2006/01/02 23:16:28 $
 */
public class ByteSetArgumentTest
    extends TestCase
{

    public void testByteSetArgument()
    {
        ByteSetArgument bsa = new ByteSetArgument("b", "byte-set", "Byte set argument", true);
        assertNotNull("bsa not null", bsa);
        assertEquals("bsa shortName == b", "b", bsa.getShortName());
        assertEquals("bsa longName == byte-set", "byte-set", bsa.getLongName());
        assertEquals("bsa description == Byte set argument", "Byte set argument", bsa.getDescription());
        assertTrue("bsa isRequired", bsa.isRequired());
        assertFalse("bsa wasFound == false", bsa.wasFound());
        assertEquals("bsa value == null", null, bsa.getValue());
    }

    public void testValidArgumentShort()
        throws CommandLineParseException
    {
        Argument<Set<Byte>> byteSetArgument = new ByteSetArgument("b", "byte-set", "Byte set argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument[] { byteSetArgument }));
        List<String> values = Arrays.asList(new String[] { "127", "-128,-128", "-128, -128", "-1,-1,-1", "-1 , -1, -1 " });

        for (String value : values)
        {
            String[] args = new String[] { "-b", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            Set<Byte> set = byteSetArgument.getValue();
            assertNotNull("-b set not null", set);
            assertFalse("-b set not empty", set.isEmpty());
        }
    }

    public void testValidArgumentLong()
        throws CommandLineParseException
    {
        Argument<Set<Byte>> byteSetArgument = new ByteSetArgument("b", "byte-set", "Byte set argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument[] { byteSetArgument }));
        List<String> values = Arrays.asList(new String[] { "127", "-128,-128", "-128, -128", "-1,-1,-1", "-1 , -1, -1 " });

        for (String value : values)
        {
            String[] args = new String[] { "--byte-set", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            Set<Byte> set = byteSetArgument.getValue();
            assertNotNull("--byte-set set not null", set);
            assertFalse("--byte-set set not empty", set.isEmpty());
        }
    }

    public void testInvalidArgumentShort()
    {
        Argument<Set<Byte>> byteSetArgument = new ByteSetArgument("b", "byte-set", "Byte set argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument[] { byteSetArgument }));
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

    public void testInvalidArgumentLong()
    {
        Argument<Set<Byte>> byteSetArgument = new ByteSetArgument("b", "byte-set", "Byte set argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument[] { byteSetArgument }));
        List<String> values = Arrays.asList(new String[] { "not-a-byte", "128", "-129" });

        for (String value : values)
        {
            try
            {
                String[] args = new String[] { "--byte-set", value };
                CommandLine commandLine = new CommandLine(args);
                CommandLineParser.parse(commandLine, arguments);

                fail("--byte-set " + value + " expected CommandLineParseException");
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
            Argument<Set<Byte>> byteSetArgument = new ByteSetArgument("b", "byte-set", "Byte set argument", true);
            ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument[] { byteSetArgument }));

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
        Argument<Set<Byte>> byteSetArgument = new ByteSetArgument("b", "byte-set", "Byte set argument", false);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument[] { byteSetArgument }));

        String[] args = new String[] { "not-an-argument", "not-a-byte" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertFalse("byteSetArgument isRequired == false", byteSetArgument.isRequired());
        assertFalse("byteSetArgument wasFound == false", byteSetArgument.wasFound());
        assertEquals("byteSetArgument value == null", null, byteSetArgument.getValue());
    }
}
