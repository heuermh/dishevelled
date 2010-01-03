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

import java.util.List;
import java.util.Arrays;

import junit.framework.TestCase;

import org.dishevelled.commandline.Argument;
import org.dishevelled.commandline.ArgumentList;
import org.dishevelled.commandline.CommandLine;
import org.dishevelled.commandline.CommandLineParser;
import org.dishevelled.commandline.CommandLineParseException;

/**
 * Unit test for CharacterArgument.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class CharacterArgumentTest
    extends TestCase
{

    public void testCharacterArgument()
    {
        CharacterArgument ca = new CharacterArgument("c", "character", "Character argument", true);
        assertNotNull("ca not null", ca);
        assertEquals("ca shortName == c", "c", ca.getShortName());
        assertEquals("ca longName == character", "character", ca.getLongName());
        assertEquals("ca description == Character argument", "Character argument", ca.getDescription());
        assertTrue("ca isRequired", ca.isRequired());
        assertFalse("ca wasFound == false", ca.wasFound());
        assertEquals("ca value == null", null, ca.getValue());
    }

    public void testValidArgumentShort()
        throws CommandLineParseException
    {
        Argument<Character> characterArgument = new CharacterArgument("c", "character-argument", "Character argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { characterArgument }));
        List<String> values = Arrays.asList(new String[] { "a", "b", "c", "1" });

        for (String value : values)
        {
            String[] args = new String[] { "-c", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            assertNotNull("-c not null", characterArgument.getValue());
        }
    }

    public void testValidArgumentLong()
        throws CommandLineParseException
    {
        Argument<Character> characterArgument = new CharacterArgument("c", "character-argument", "Character argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { characterArgument }));
        List<String> values = Arrays.asList(new String[] { "a", "b", "c", "1" });

        for (String value : values)
        {
            String[] args = new String[] { "--character-argument", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            assertNotNull("-c not null", characterArgument.getValue());
        }
    }

    public void testInvalidArgumentShort()
    {
        Argument<Character> characterArgument = new CharacterArgument("c", "character-argument", "Character argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { characterArgument }));
        List<String> values = Arrays.asList(new String[] { "not-a-character", " b", "c " });

        for (String value : values)
        {
            try
            {
                String[] args = new String[] { "-c", value };
                CommandLine commandLine = new CommandLine(args);
                CommandLineParser.parse(commandLine, arguments);

                fail("-c not-a-character expected CommandLineParseException");
            }
            catch (CommandLineParseException e)
            {
                // expected
            }
        }
    }

    public void testInvalidArgumentLong()
    {
        Argument<Character> characterArgument = new CharacterArgument("c", "character-argument", "Character argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { characterArgument }));
        List<String> values = Arrays.asList(new String[] { "not-a-character", " b", "c " });

        for (String value : values)
        {
            try
            {
                String[] args = new String[] { "--character-argument", value };
                CommandLine commandLine = new CommandLine(args);
                CommandLineParser.parse(commandLine, arguments);

                fail("--character-argument not-a-character expected CommandLineParseException");
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
            Argument<Character> characterArgument = new CharacterArgument("c", "character-argument", "Character argument", true);
            ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { characterArgument }));

            String[] args = new String[] { "not-an-argument", "not-a-character" };
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
        Argument<Character> characterArgument = new CharacterArgument("c", "character-argument", "Character argument", false);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { characterArgument }));

        String[] args = new String[] { "not-an-argument", "not-a-character" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertFalse("characterArgument isRequired == false", characterArgument.isRequired());
        assertFalse("characterArgument wasFound == false", characterArgument.wasFound());
        assertEquals("characterArgument value == null", null, characterArgument.getValue());
    }
}
