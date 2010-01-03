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

import java.io.File;

import java.util.List;
import java.util.Arrays;

import junit.framework.TestCase;

import org.dishevelled.commandline.Argument;
import org.dishevelled.commandline.ArgumentList;
import org.dishevelled.commandline.CommandLine;
import org.dishevelled.commandline.CommandLineParser;
import org.dishevelled.commandline.CommandLineParseException;

/**
 * Unit test for FileArgument.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class FileArgumentTest
    extends TestCase
{

    public void testFileArgument()
    {
        FileArgument fa = new FileArgument("f", "file", "File argument", true);
        assertNotNull("fa not null", fa);
        assertEquals("fa shortName == f", "f", fa.getShortName());
        assertEquals("fa longName == file", "file", fa.getLongName());
        assertEquals("fa description == File argument", "File argument", fa.getDescription());
        assertTrue("fa isRequired", fa.isRequired());
        assertFalse("fa wasFound == false", fa.wasFound());
        assertEquals("fa value == null", null, fa.getValue());
    }

    public void testValidArgumentShort()
        throws CommandLineParseException
    {
        Argument<File> fileArgument = new FileArgument("f", "file-argument", "File argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { fileArgument }));
        List<String> values = Arrays.asList(new String[] { "foo" });

        for (String value : values)
        {
            String[] args = new String[] { "-f", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            assertTrue("fileArgument wasFound == true", fileArgument.wasFound());
            assertNotNull("-d not null", fileArgument.getValue());
        }
    }

    public void testValidArgumentLong()
        throws CommandLineParseException
    {
        Argument<File> fileArgument = new FileArgument("f", "file-argument", "File argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { fileArgument }));
        List<String> values = Arrays.asList(new String[] { "foo" });

        for (String value : values)
        {
            String[] args = new String[] { "--file-argument", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            assertTrue("fileArgument wasFound == true", fileArgument.wasFound());
            assertNotNull("--file-argument " + value + " not null", fileArgument.getValue());
        }
    }

    /*
    public void testInvalidArgumentShort()
    {
        Argument<File> fileArgument = new FileArgument("f", "file-argument", "File argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { fileArgument }));
        List<String> values = Arrays.asList(new String[] { ":" });

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
        Argument<File> fileArgument = new FileArgument("f", "file-argument", "File argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { fileArgument }));
        List<String> values = Arrays.asList(new String[] { ":" });

        for (String value : values)
        {
            try
            {
                String[] args = new String[] { "--file-argument", value };
                CommandLine commandLine = new CommandLine(args);
                CommandLineParser.parse(commandLine, arguments);

                fail("--file-argument " + value + " expected CommandLineParseException");
            }
            catch (CommandLineParseException e)
            {
                // expected
            }
        }
    }
    */

    public void testRequiredArgument()
    {
        try
        {
            Argument<File> fileArgument = new FileArgument("f", "file-argument", "File argument", true);
            ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { fileArgument }));

            String[] args = new String[] { "not-an-argument" };
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
        Argument<File> fileArgument = new FileArgument("f", "file-argument", "File argument", false);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { fileArgument }));

        String[] args = new String[] { "not-an-argument" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertFalse("fileArgument isRequired == false", fileArgument.isRequired());
        assertFalse("fileArgument wasFound == false", fileArgument.wasFound());
        assertEquals("fileArgument value == null", null, fileArgument.getValue());
    }
}
