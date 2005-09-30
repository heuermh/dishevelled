/*

    dsh-commandline  Simple command line parser based on typed arguments.
    Copyright (c) 2004-2005 held jointly by the individual authors.

    This library is free software; you can redistribute it and/or modify it
    under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation; either version 2.1 of the License, or (at
    your option) any later version.

    This library is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; with out even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
    License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this library;  if not, write to the Free Software Foundation,
    Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA.

    > http://www.gnu.org/copyleft/lesser.html
    > http://www.opensource.org/licenses/lgpl-license.php

*/
package org.dishevelled.commandline.argument;

import java.net.URL;

import java.util.List;
import java.util.Arrays;

import junit.framework.TestCase;

import org.dishevelled.commandline.Argument;
import org.dishevelled.commandline.CommandLine;
import org.dishevelled.commandline.CommandLineParser;
import org.dishevelled.commandline.CommandLineParseException;

/**
 * Unit test for URLArgument.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class URLArgumentTest
    extends TestCase
{

    public void testURLArgument()
    {
        URLArgument ua = new URLArgument("u", "URL", "URL argument", true);
        assertNotNull("ua not null", ua);
        assertEquals("ua shortName == u", "u", ua.getShortName());
        assertEquals("ua longName == URL", "URL", ua.getLongName());
        assertEquals("ua description == URL argument", "URL argument", ua.getDescription());
        assertTrue("ua isRequired", ua.isRequired());
        assertFalse("ua wasFound == false", ua.wasFound());
        assertEquals("ua value == null", null, ua.getValue());
    }

    public void testValidArgumentShort()
        throws CommandLineParseException
    {
        Argument<URL> URLArgument = new URLArgument("u", "URL-argument", "URL argument", true);
        List<Argument<?>> arguments = Arrays.asList(new Argument<?>[] { URLArgument });
        List<String> values = Arrays.asList(new String[] { "http://localhost" });

        for (String value : values)
        {
            String[] args = new String[] { "-u", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            assertNotNull("-u " + value + " not null", URLArgument.getValue());
        }
    }

    public void testValidArgumentLong()
        throws CommandLineParseException
    {
        Argument<URL> URLArgument = new URLArgument("u", "URL-argument", "URL argument", true);
        List<Argument<?>> arguments = Arrays.asList(new Argument<?>[] { URLArgument });
        List<String> values = Arrays.asList(new String[] { "http://localhost" });

        for (String value : values)
        {
            String[] args = new String[] { "--URL-argument", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            assertNotNull("--URL-argument " + value + " not null", URLArgument.getValue());
        }
    }

    public void testInvalidArgumentShort()
    {
        Argument<URL> URLArgument = new URLArgument("u", "URL-argument", "URL argument", true);
        List<Argument<?>> arguments = Arrays.asList(new Argument<?>[] { URLArgument });
        List<String> values = Arrays.asList(new String[] { "not-a-URL" });

        for (String value : values)
        {
            try
            {
                String[] args = new String[] { "-u", value };
                CommandLine commandLine = new CommandLine(args);
                CommandLineParser.parse(commandLine, arguments);

                fail("-u " + value + " expected CommandLineParseException");
            }
            catch (CommandLineParseException e)
            {
                // expected
            }
        }
    }

    public void testInvalidArgumentLong()
    {
        Argument<URL> URLArgument = new URLArgument("u", "URL-argument", "URL argument", true);
        List<Argument<?>> arguments = Arrays.asList(new Argument<?>[] { URLArgument });
        List<String> values = Arrays.asList(new String[] { "not-a-URL" });

        for (String value : values)
        {
            try
            {
                String[] args = new String[] { "--URL-argument", value };
                CommandLine commandLine = new CommandLine(args);
                CommandLineParser.parse(commandLine, arguments);

                fail("--URL-argument " + value + " expected CommandLineParseException");
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
            Argument<URL> URLArgument = new URLArgument("u", "URL-argument", "URL argument", true);
            List<Argument<?>> arguments = Arrays.asList(new Argument<?>[] { URLArgument });

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
        Argument<URL> URLArgument = new URLArgument("u", "URL-argument", "URL argument", false);
        List<Argument<?>> arguments = Arrays.asList(new Argument<?>[] { URLArgument });

        String[] args = new String[] { "not-an-argument" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertFalse("URLArgument isRequired == false", URLArgument.isRequired());
        assertFalse("URLArgument wasFound == false", URLArgument.wasFound());
        assertEquals("URLArgument value == null", null, URLArgument.getValue());
    }
}