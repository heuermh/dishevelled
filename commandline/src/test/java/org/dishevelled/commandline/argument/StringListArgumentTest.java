/*

    dsh-commandline  Command line parser based on typed arguments.
    Copyright (c) 2004-2007 held jointly by the individual authors.

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

import java.util.List;
import java.util.Arrays;

import junit.framework.TestCase;

import org.dishevelled.commandline.Argument;
import org.dishevelled.commandline.ArgumentList;
import org.dishevelled.commandline.CommandLine;
import org.dishevelled.commandline.CommandLineParser;
import org.dishevelled.commandline.CommandLineParseException;

/**
 * Unit test for StringListArgument.
 *
 * @author  Michael Heuer
 * @version $Revision: 1.2 $ $Date: 2006/01/02 23:16:28 $
 */
public class StringListArgumentTest
    extends TestCase
{

    public void testStringListArgument()
    {
        StringListArgument sla = new StringListArgument("s", "string-list", "String list argument", true);
        assertNotNull("sla not null", sla);
        assertEquals("sla shortName == s", "s", sla.getShortName());
        assertEquals("sla longName == string-list", "string-list", sla.getLongName());
        assertEquals("sla description == String list argument", "String list argument", sla.getDescription());
        assertTrue("sla isRequired", sla.isRequired());
        assertFalse("sla wasFound == false", sla.wasFound());
        assertEquals("sla value == null", null, sla.getValue());
    }

    public void testObjectContract()
    {
        StringListArgument a0 = new StringListArgument("s", "string-list", "String list argument", true);
        StringListArgument a1 = new StringListArgument("s", "string-list", "String list argument", true);

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
        Argument<List<String>> stringListArgument = new StringListArgument("s", "string-list", "String list argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument[] { stringListArgument }));
        List<String> values = Arrays.asList(new String[] { "foo,bar,baz" });

        for (String value : values)
        {
            String[] args = new String[] { "-s", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            List<String> list = stringListArgument.getValue();
            assertNotNull("-s list not null", list);
            assertFalse("-s list not empty", list.isEmpty());
        }
    }

    public void testValidArgumentLong()
        throws CommandLineParseException
    {
        Argument<List<String>> stringListArgument = new StringListArgument("s", "string-list", "String list argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument[] { stringListArgument }));
        List<String> values = Arrays.asList(new String[] { "foo,bar,baz" });

        for (String value : values)
        {
            String[] args = new String[] { "--string-list", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            List<String> list = stringListArgument.getValue();
            assertNotNull("--string-list list not null", list);
            assertFalse("--string-list list not empty", list.isEmpty());
        }
    }

    public void testRequiredArgument()
    {
        try
        {
            Argument<List<String>> stringListArgument = new StringListArgument("s", "string-list", "String list argument", true);
            ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument[] { stringListArgument }));

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
        Argument<List<String>> stringListArgument = new StringListArgument("s", "string-list", "String list argument", false);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument[] { stringListArgument }));

        String[] args = new String[] { "not-an-argument", "" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertFalse("stringListArgument isRequired == false", stringListArgument.isRequired());
        assertFalse("stringListArgument wasFound == false", stringListArgument.wasFound());
        assertEquals("stringListArgument value == null", null, stringListArgument.getValue());
    }
}
