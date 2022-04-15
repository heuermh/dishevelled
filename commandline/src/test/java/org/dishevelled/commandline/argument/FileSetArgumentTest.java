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

import java.io.File;

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
 * Unit test for FileSetArgument.
 *
 * @author  Michael Heuer
 */
public class FileSetArgumentTest
    extends TestCase
{

    public void testFileSetArgument()
    {
        FileSetArgument fsa = new FileSetArgument("f", "file-set", "File set argument", true);
        assertNotNull("fsa not null", fsa);
        assertEquals("fsa shortName == f", "f", fsa.getShortName());
        assertEquals("fsa longName == file-set", "file-set", fsa.getLongName());
        assertEquals("fsa description == File set argument", "File set argument", fsa.getDescription());
        assertTrue("fsa isRequired", fsa.isRequired());
        assertFalse("fsa wasFound == false", fsa.wasFound());
        assertEquals("fsa value == null", null, fsa.getValue());
    }

    public void testValidArgumentShort()
        throws CommandLineParseException
    {
        Argument<Set<File>> fileSetArgument = new FileSetArgument("f", "file-set", "File set argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { fileSetArgument }));
        List<String> values = Arrays.asList(new String[] { "foo", "foo,bar", "foo, bar", " foo , bar " });

        for (String value : values)
        {
            String[] args = new String[] { "-f", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            Set<File> set = fileSetArgument.getValue();
            assertNotNull("-f set not null", set);
            assertFalse("-f set not empty", set.isEmpty());
        }
    }

    public void testValidArgumentLong()
        throws CommandLineParseException
    {
        Argument<Set<File>> fileSetArgument = new FileSetArgument("f", "file-set", "File set argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { fileSetArgument }));
        List<String> values = Arrays.asList(new String[] { "foo", "foo,bar", "foo, bar", " foo , bar " });

        for (String value : values)
        {
            String[] args = new String[] { "--file-set", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            Set<File> set = fileSetArgument.getValue();
            assertNotNull("--file-set set not null", set);
            assertFalse("--file-set set not empty", set.isEmpty());
        }
    }

    public void testRequiredArgument()
    {
        try
        {
            Argument<Set<File>> fileSetArgument = new FileSetArgument("f", "file-set", "File set argument", true);
            ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { fileSetArgument }));

            String[] args = new String[] { "not-an-argument", "not-a-file" };
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
        Argument<Set<File>> fileSetArgument = new FileSetArgument("f", "file-set", "File set argument", false);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { fileSetArgument }));

        String[] args = new String[] { "not-an-argument", "not-a-file" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertFalse("fileSetArgument isRequired == false", fileSetArgument.isRequired());
        assertFalse("fileSetArgument wasFound == false", fileSetArgument.wasFound());
        assertEquals("fileSetArgument value == null", null, fileSetArgument.getValue());
    }
}
