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
 * Unit test for StringSetArgument.
 *
 * @author  Michael Heuer
 * @version $Revision: 1.2 $ $Date: 2006/01/02 23:16:28 $
 */
public class StringSetArgumentTest
    extends TestCase
{

    public void testStringSetArgument()
    {
        StringSetArgument ssa = new StringSetArgument("s", "string-set", "String set argument", true);
        assertNotNull("ssa not null", ssa);
        assertEquals("ssa shortName == s", "s", ssa.getShortName());
        assertEquals("ssa longName == string-set", "string-set", ssa.getLongName());
        assertEquals("ssa description == String set argument", "String set argument", ssa.getDescription());
        assertTrue("ssa isRequired", ssa.isRequired());
        assertFalse("ssa wasFound == false", ssa.wasFound());
        assertEquals("ssa value == null", null, ssa.getValue());
    }

    public void testObjectContract()
    {
        StringSetArgument a0 = new StringSetArgument("s", "string-set", "String set argument", true);
        StringSetArgument a1 = new StringSetArgument("s", "string-set", "String set argument", true);

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
        Argument<Set<String>> stringSetArgument = new StringSetArgument("s", "string-set", "String set argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { stringSetArgument }));
        List<String> values = Arrays.asList(new String[] { "foo,bar,baz" });

        for (String value : values)
        {
            String[] args = new String[] { "-s", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            Set<String> set = stringSetArgument.getValue();
            assertNotNull("-s set not null", set);
            assertFalse("-s set not empty", set.isEmpty());
        }
    }

    public void testValidArgumentLong()
        throws CommandLineParseException
    {
        Argument<Set<String>> stringSetArgument = new StringSetArgument("s", "string-set", "String set argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { stringSetArgument }));
        List<String> values = Arrays.asList(new String[] { "foo,bar,baz" });

        for (String value : values)
        {
            String[] args = new String[] { "--string-set", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            Set<String> set = stringSetArgument.getValue();
            assertNotNull("--string-set set not null", set);
            assertFalse("--string-set set not empty", set.isEmpty());
        }
    }

    public void testRequiredArgument()
    {
        try
        {
            Argument<Set<String>> stringSetArgument = new StringSetArgument("s", "string-set", "String set argument", true);
            ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { stringSetArgument }));

            String[] args = new String[] { "not-an-argument", "" };
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
        Argument<Set<String>> stringSetArgument = new StringSetArgument("s", "string-set", "String set argument", false);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { stringSetArgument }));

        String[] args = new String[] { "not-an-argument", "" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertFalse("stringSetArgument isRequired == false", stringSetArgument.isRequired());
        assertFalse("stringSetArgument wasFound == false", stringSetArgument.wasFound());
        assertEquals("stringSetArgument value == null", null, stringSetArgument.getValue());
    }
}
