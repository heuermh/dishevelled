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
 * Unit test for PathArgument.
 *
 * @author  Michael Heuer
 */
public class PathArgumentTest
    extends TestCase
{

    public void testPathArgument()
    {
        PathArgument fa = new PathArgument("f", "path", "Path argument", true);
        assertNotNull("fa not null", fa);
        assertEquals("fa shortName == f", "f", fa.getShortName());
        assertEquals("fa longName == path", "path", fa.getLongName());
        assertEquals("fa description == Path argument", "Path argument", fa.getDescription());
        assertTrue("fa isRequired", fa.isRequired());
        assertFalse("fa wasFound == false", fa.wasFound());
        assertEquals("fa value == null", null, fa.getValue());
    }

    public void testValidArgumentShort()
        throws CommandLineParseException
    {
        Argument<Path> pathArgument = new PathArgument("f", "path-argument", "Path argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { pathArgument }));
        List<String> values = Arrays.asList(new String[] { "foo" });

        for (String value : values)
        {
            String[] args = new String[] { "-f", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            assertTrue("pathArgument wasFound == true", pathArgument.wasFound());
            assertNotNull("-d not null", pathArgument.getValue());
        }
    }

    public void testValidArgumentLong()
        throws CommandLineParseException
    {
        Argument<Path> pathArgument = new PathArgument("f", "path-argument", "Path argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { pathArgument }));
        List<String> values = Arrays.asList(new String[] { "foo" });

        for (String value : values)
        {
            String[] args = new String[] { "--path-argument", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            assertTrue("pathArgument wasFound == true", pathArgument.wasFound());
            assertNotNull("--path-argument " + value + " not null", pathArgument.getValue());
        }
    }

    public void testStdinDashLastArgumentShort()
        throws CommandLineParseException
    {
        Argument<Path> pathArgument = new PathArgument("f", "path-argument", "Path argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { pathArgument }));

        String value = "-";
        String[] args = new String[] { "-f", value };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertTrue("pathArgument wasFound == true", pathArgument.wasFound());
        assertNotNull("--path-argument " + value + " not null", pathArgument.getValue());
    }

    public void testStdinDashMultipleArgumentsShort()
        throws CommandLineParseException
    {
        Argument<Path> pathArgument = new PathArgument("f", "path-argument", "Path argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { pathArgument }));

        String value = "-";
        String[] args = new String[] { "-f", value, "-a", "some-other-value" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertTrue("pathArgument wasFound == true", pathArgument.wasFound());
        assertNotNull("--path-argument " + value + " not null", pathArgument.getValue());
    }

    public void testStdinDashLastArgumentLong()
        throws CommandLineParseException
    {
        Argument<Path> pathArgument = new PathArgument("f", "path-argument", "Path argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { pathArgument }));

        String value = "-";
        String[] args = new String[] { "--path-argument", value };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertTrue("pathArgument wasFound == true", pathArgument.wasFound());
        assertNotNull("--path-argument " + value + " not null", pathArgument.getValue());
    }

    public void testStdinDashMultipleArgumentsLong()
        throws CommandLineParseException
    {
        Argument<Path> pathArgument = new PathArgument("f", "path-argument", "Path argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { pathArgument }));

        String value = "-";
        String[] args = new String[] { "--path-argument", value, "--some-other-argument", "some-other-value" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertTrue("pathArgument wasFound == true", pathArgument.wasFound());
        assertNotNull("--path-argument " + value + " not null", pathArgument.getValue());
    }

    /*
    public void testInvalidArgumentShort()
    {
        Argument<Path> pathArgument = new PathArgument("f", "path-argument", "Path argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { pathArgument }));
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
        Argument<Path> pathArgument = new PathArgument("f", "path-argument", "Path argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { pathArgument }));
        List<String> values = Arrays.asList(new String[] { ":" });

        for (String value : values)
        {
            try
            {
                String[] args = new String[] { "--path-argument", value };
                CommandLine commandLine = new CommandLine(args);
                CommandLineParser.parse(commandLine, arguments);

                fail("--path-argument " + value + " expected CommandLineParseException");
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
            Argument<Path> pathArgument = new PathArgument("f", "path-argument", "Path argument", true);
            ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { pathArgument }));

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
        Argument<Path> pathArgument = new PathArgument("f", "path-argument", "Path argument", false);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { pathArgument }));

        String[] args = new String[] { "not-an-argument" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertFalse("pathArgument isRequired == false", pathArgument.isRequired());
        assertFalse("pathArgument wasFound == false", pathArgument.wasFound());
        assertEquals("pathArgument value == null", null, pathArgument.getValue());
    }

    public void testMissingArgumentValueShort()
        throws CommandLineParseException
    {
        Argument<Path> pathArgument = new PathArgument("f", "path-argument", "Path argument", false);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { pathArgument }));

        String[] args = new String[] { "-f" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertTrue(pathArgument.wasFound());
        assertEquals(null, pathArgument.getValue());
    }

    public void testMissingArgumentValueLong()
        throws CommandLineParseException
    {
        Argument<Path> pathArgument = new PathArgument("f", "path-argument", "Path argument", false);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { pathArgument }));

        String[] args = new String[] { "--path-argument" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertTrue(pathArgument.wasFound());
        assertEquals(null, pathArgument.getValue());
    }

    public void testMissingArgumentValueMultipleArgumentsShort()
        throws CommandLineParseException
    {
        Argument<Path> pathArgument = new PathArgument("f", "path-argument", "Path argument", false);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { pathArgument }));

        String[] args = new String[] { "-f", "-s" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertTrue(pathArgument.wasFound());
        assertEquals(null, pathArgument.getValue());
    }

    public void testMissingArgumentValueMultipleArgumentsShortAndLong()
        throws CommandLineParseException
    {
        Argument<Path> pathArgument = new PathArgument("f", "path-argument", "Path argument", false);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { pathArgument }));

        String[] args = new String[] { "-f", "--some-other-argument" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertTrue(pathArgument.wasFound());
        assertEquals(null, pathArgument.getValue());
    }

    public void testMissingArgumentValueMultipleArgumentsLongAndShort()
        throws CommandLineParseException
    {
        Argument<Path> pathArgument = new PathArgument("f", "path-argument", "Path argument", false);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { pathArgument }));

        String[] args = new String[] { "--path-argument", "-s" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertTrue(pathArgument.wasFound());
        assertEquals(null, pathArgument.getValue());
    }

    public void testMissingArgumentValueMultipleArgumentsLong()
        throws CommandLineParseException
    {
        Argument<Path> pathArgument = new PathArgument("f", "path-argument", "Path argument", false);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { pathArgument }));

        String[] args = new String[] { "--path-argument", "--some-other-argument" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertTrue(pathArgument.wasFound());
        assertEquals(null, pathArgument.getValue());
    }

    public void testMissingArgumentValueMultipleArgumentsLaterShort()
        throws CommandLineParseException
    {
        Argument<Path> pathArgument = new PathArgument("f", "path-argument", "Path argument", false);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { pathArgument }));

        String[] args = new String[] { "-f", "-s", "some-other-value" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertTrue(pathArgument.wasFound());
        assertEquals(null, pathArgument.getValue());
    }

    public void testMissingArgumentValueMultipleArgumentsLaterLong()
        throws CommandLineParseException
    {
        Argument<Path> pathArgument = new PathArgument("f", "path-argument", "Path argument", false);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { pathArgument }));

        String[] args = new String[] { "--path-argument", "--some-other-argument", "some-other-value" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertTrue(pathArgument.wasFound());
        assertEquals(null, pathArgument.getValue());
    }

    public void testConvertPathNull() throws Exception
    {
        assertEquals(null, PathArgument.convertPath(null));
    }

    public void testConvertPathEmpty() throws Exception
    {
        assertEquals(null, PathArgument.convertPath(""));
    }

    public void testConvertPathNullOrEmptySchemeAbsolute() throws Exception
    {
        assertNotNull(PathArgument.convertPath("/absolute"));
    }

    public void testConvertPathNullOrEmptySchemeRelative() throws Exception
    {
        assertNotNull(PathArgument.convertPath("relative"));
    }

    public void testConvertPathFileSchemeAbsolute() throws Exception
    {
        assertNotNull(PathArgument.convertPath("file:/absolute"));
    }

    public void testConvertPathUnknownScheme() throws Exception
    {
        try
        {
            PathArgument.convertPath("unknown:/absolute");
        }
        catch (Exception e)
        {
            assertEquals("Provider \"unknown\" not installed", e.getMessage());
        }
    }
}
