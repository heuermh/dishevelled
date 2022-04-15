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

import java.nio.file.Path;

import java.util.List;
import java.util.Arrays;

import junit.framework.TestCase;

import org.dishevelled.commandline.Argument;
import org.dishevelled.commandline.ArgumentList;
import org.dishevelled.commandline.CommandLine;
import org.dishevelled.commandline.CommandLineParser;
import org.dishevelled.commandline.CommandLineParseException;

/**
 * Unit test for PathListArgument.
 *
 * @author  Michael Heuer
 */
public class PathListArgumentTest
    extends TestCase
{

    public void testPathListArgument()
    {
        PathListArgument fla = new PathListArgument("f", "path-list", "Path list argument", true);
        assertNotNull("fla not null", fla);
        assertEquals("fla shortName == f", "f", fla.getShortName());
        assertEquals("fla longName == path-list", "path-list", fla.getLongName());
        assertEquals("fla description == Path list argument", "Path list argument", fla.getDescription());
        assertTrue("fla isRequired", fla.isRequired());
        assertFalse("fla wasFound == false", fla.wasFound());
        assertEquals("fla value == null", null, fla.getValue());
    }

    public void testValidArgumentShort()
        throws CommandLineParseException
    {
        Argument<List<Path>> pathListArgument = new PathListArgument("f", "path-list", "Path list argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { pathListArgument }));
        List<String> values = Arrays.asList(new String[] { "foo", "foo,bar", "foo, bar", " foo , bar " });

        for (String value : values)
        {
            String[] args = new String[] { "-f", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            List<Path> list = pathListArgument.getValue();
            assertNotNull("-f list not null", list);
            assertFalse("-f list not empty", list.isEmpty());
        }
    }

    public void testValidArgumentLong()
        throws CommandLineParseException
    {
        Argument<List<Path>> pathListArgument = new PathListArgument("f", "path-list", "Path list argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { pathListArgument }));
        List<String> values = Arrays.asList(new String[] { "foo", "foo,bar", "foo, bar", " foo , bar " });

        for (String value : values)
        {
            String[] args = new String[] { "--path-list", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            List<Path> list = pathListArgument.getValue();
            assertNotNull("--path-list list not null", list);
            assertFalse("--path-list list not empty", list.isEmpty());
        }
    }

    public void testRequiredArgument()
    {
        try
        {
            Argument<List<Path>> pathListArgument = new PathListArgument("f", "path-list", "Path list argument", true);
            ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { pathListArgument }));

            String[] args = new String[] { "not-an-argument", "not-a-path" };
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
        Argument<List<Path>> pathListArgument = new PathListArgument("f", "path-list", "Path list argument", false);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { pathListArgument }));

        String[] args = new String[] { "not-an-argument", "not-a-path" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertFalse("pathListArgument isRequired == false", pathListArgument.isRequired());
        assertFalse("pathListArgument wasFound == false", pathListArgument.wasFound());
        assertEquals("pathListArgument value == null", null, pathListArgument.getValue());
    }
}
