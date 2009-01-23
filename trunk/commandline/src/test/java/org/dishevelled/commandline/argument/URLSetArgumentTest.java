/*

    dsh-commandline  Command line parser based on typed arguments.
    Copyright (c) 2004-2009 held jointly by the individual authors.

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
 * Unit test for URLSetArgument.
 *
 * @author  Michael Heuer
 * @version $Revision: 1.2 $ $Date: 2006/01/02 23:16:28 $
 */
public class URLSetArgumentTest
    extends TestCase
{

    public void testURLSetArgument()
    {
        URLSetArgument usa = new URLSetArgument("u", "url-set", "URL set argument", true);
        assertNotNull("usa not null", usa);
        assertEquals("usa shortName == u", "u", usa.getShortName());
        assertEquals("usa longName == url-set", "url-set", usa.getLongName());
        assertEquals("usa description == URL set argument", "URL set argument", usa.getDescription());
        assertTrue("usa isRequired", usa.isRequired());
        assertFalse("usa wasFound == false", usa.wasFound());
        assertEquals("usa value == null", null, usa.getValue());
    }

    public void testObjectContract()
    {
        URLSetArgument a0 = new URLSetArgument("u", "url-set", "URL set argument", true);
        URLSetArgument a1 = new URLSetArgument("u", "url-set", "URL set argument", true);

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
        Argument<Set<URL>> urlSetArgument = new URLSetArgument("u", "url-set", "URL set argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument[] { urlSetArgument }));
        List<String> values = Arrays.asList(new String[] { "http://localhost", "http://localhost, http://127.0.0.1",
                                                           "http://localhost, http://127.0.0.1", " http://localhost , http://127.0.0.1 " });

        for (String value : values)
        {
            String[] args = new String[] { "-u", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            Set<URL> set = urlSetArgument.getValue();
            assertNotNull("-u set not null", set);
            assertFalse("-u set not empty", set.isEmpty());
        }
    }

    public void testValidArgumentLong()
        throws CommandLineParseException
    {
        Argument<Set<URL>> urlSetArgument = new URLSetArgument("u", "url-set", "URL set argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument[] { urlSetArgument }));
        List<String> values = Arrays.asList(new String[] { "http://localhost", "http://localhost, http://127.0.0.1",
                                                           "http://localhost, http://127.0.0.1", " http://localhost , http://127.0.0.1 " });

        for (String value : values)
        {
            String[] args = new String[] { "--url-set", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            Set<URL> set = urlSetArgument.getValue();
            assertNotNull("--url-set set not null", set);
            assertFalse("--url-set set not empty", set.isEmpty());
        }
    }

    public void testInvalidArgumentShort()
    {
        Argument<Set<URL>> urlSetArgument = new URLSetArgument("u", "url-set", "URL set argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument[] { urlSetArgument }));
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

    public void testInvalidArgumentLong()
    {
        Argument<Set<URL>> urlSetArgument = new URLSetArgument("u", "url-set", "URL set argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument[] { urlSetArgument }));
        List<String> values = Arrays.asList(new String[] { "not-a-url" });

        for (String value : values)
        {
            try
            {
                String[] args = new String[] { "--url-set", value };
                CommandLine commandLine = new CommandLine(args);
                CommandLineParser.parse(commandLine, arguments);

                fail("--url-set " + value + " expected CommandLineParseException");
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
            Argument<Set<URL>> urlSetArgument = new URLSetArgument("u", "url-set", "URL set argument", true);
            ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument[] { urlSetArgument }));

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
        Argument<Set<URL>> urlSetArgument = new URLSetArgument("u", "url-set", "URL set argument", false);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument[] { urlSetArgument }));

        String[] args = new String[] { "not-an-argument", "not-a-url" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertFalse("urlSetArgument isRequired == false", urlSetArgument.isRequired());
        assertFalse("urlSetArgument wasFound == false", urlSetArgument.wasFound());
        assertEquals("urlSetArgument value == null", null, urlSetArgument.getValue());
    }
}
