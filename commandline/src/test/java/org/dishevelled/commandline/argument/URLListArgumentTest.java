/*

    dsh-commandline  Command line parser based on typed arguments.
    Copyright (c) 2004-2011 held jointly by the individual authors.

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

import java.net.URL;

import java.util.List;
import java.util.Arrays;

import junit.framework.TestCase;

import org.dishevelled.commandline.Argument;
import org.dishevelled.commandline.ArgumentList;
import org.dishevelled.commandline.CommandLine;
import org.dishevelled.commandline.CommandLineParser;
import org.dishevelled.commandline.CommandLineParseException;

/**
 * Unit test for URLListArgument.
 *
 * @author  Michael Heuer
 * @version $Revision: 1.2 $ $Date: 2006/01/02 23:16:28 $
 */
public class URLListArgumentTest
    extends TestCase
{

    public void testURLListArgument()
    {
        URLListArgument ula = new URLListArgument("u", "url-list", "URL list argument", true);
        assertNotNull("ula not null", ula);
        assertEquals("ula shortName == u", "u", ula.getShortName());
        assertEquals("ula longName == url-list", "url-list", ula.getLongName());
        assertEquals("ula description == URL list argument", "URL list argument", ula.getDescription());
        assertTrue("ula isRequired", ula.isRequired());
        assertFalse("ula wasFound == false", ula.wasFound());
        assertEquals("ula value == null", null, ula.getValue());
    }

    public void testObjectContract()
    {
        URLListArgument a0 = new URLListArgument("u", "url-list", "URL list argument", true);
        URLListArgument a1 = new URLListArgument("u", "url-list", "URL list argument", true);

        assertEquals("a0 equals itself", a0, a0);
        assertEquals("a1 equals itself", a1, a1);
        assertEquals("a0 hashCode == a0 hashCode", a0.hashCode(), a0.hashCode());
        assertEquals("a1 hashCode == a1 hashCode", a1.hashCode(), a1.hashCode());
        assertFalse("a0 not equals a1", a0.equals(a1));
        assertFalse("a1 not equals a0", a1.equals(a0));
    }

    public void testValidArgumentShort()
        throws CommandLineParseException
    {
        Argument<List<URL>> urlListArgument = new URLListArgument("u", "url-list", "URL list argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { urlListArgument }));
        List<String> values = Arrays.asList(new String[] { "http://localhost", "http://localhost, http://127.0.0.1",
                                                           "http://localhost, http://127.0.0.1", " http://localhost , http://127.0.0.1 " });

        for (String value : values)
        {
            String[] args = new String[] { "-u", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            List<URL> list = urlListArgument.getValue();
            assertNotNull("-u list not null", list);
            assertFalse("-u list not empty", list.isEmpty());
        }
    }

    public void testValidArgumentLong()
        throws CommandLineParseException
    {
        Argument<List<URL>> urlListArgument = new URLListArgument("u", "url-list", "URL list argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { urlListArgument }));
        List<String> values = Arrays.asList(new String[] { "http://localhost", "http://localhost, http://127.0.0.1",
                                                           "http://localhost, http://127.0.0.1", " http://localhost , http://127.0.0.1 " });

        for (String value : values)
        {
            String[] args = new String[] { "--url-list", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            List<URL> list = urlListArgument.getValue();
            assertNotNull("--url-list list not null", list);
            assertFalse("--url-list list not empty", list.isEmpty());
        }
    }

    public void testInvalidArgumentLong()
    {
        Argument<List<URL>> urlListArgument = new URLListArgument("u", "url-list", "URL list argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { urlListArgument }));
        List<String> values = Arrays.asList(new String[] { "not-a-url" });

        for (String value : values)
        {
            try
            {
                String[] args = new String[] { "--url-list", value };
                CommandLine commandLine = new CommandLine(args);
                CommandLineParser.parse(commandLine, arguments);

                fail("--url-list " + value + " expected CommandLineParseException");
            }
            catch (CommandLineParseException e)
            {
                // expected
            }
        }
    }

    public void testInvalidArgumentShort()
    {
        Argument<List<URL>> urlListArgument = new URLListArgument("u", "url-list", "URL list argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { urlListArgument }));
        List<String> values = Arrays.asList(new String[] { "not-a-url" });

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

    public void testRequiredArgument()
    {
        try
        {
            Argument<List<URL>> urlListArgument = new URLListArgument("u", "url-list", "URL list argument", true);
            ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { urlListArgument }));

            String[] args = new String[] { "not-an-argument", "not-a-url" };
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
        Argument<List<URL>> urlListArgument = new URLListArgument("u", "url-list", "URL list argument", false);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { urlListArgument }));

        String[] args = new String[] { "not-an-argument", "not-a-url" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertFalse("urlListArgument isRequired == false", urlListArgument.isRequired());
        assertFalse("urlListArgument wasFound == false", urlListArgument.wasFound());
        assertEquals("urlListArgument value == null", null, urlListArgument.getValue());
    }
}
