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
 * Unit test for BooleanListArgument.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class BooleanListArgumentTest
    extends TestCase
{

    public void testBooleanListArgument()
    {
        BooleanListArgument bla = new BooleanListArgument("b", "boolean-list", "Boolean list argument", true);
        assertNotNull("bla not null", bla);
        assertEquals("bla shortName == b", "b", bla.getShortName());
        assertEquals("bla longName == boolean-list", "boolean-list", bla.getLongName());
        assertEquals("bla description == Boolean list argument", "Boolean list argument", bla.getDescription());
        assertTrue("bla isRequired", bla.isRequired());
        assertFalse("bla wasFound == false", bla.wasFound());
        assertEquals("bla value == null", null, bla.getValue());
    }

    public void testValidArgumentShort()
        throws CommandLineParseException
    {
        Argument<List<Boolean>> booleanListArgument = new BooleanListArgument("b", "boolean-list", "Boolean list argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { booleanListArgument }));
        List<String> values = Arrays.asList(new String[] { "true", "TRUE", "TRue", "t", "T", "1",
                                                           "false", "FALSE", "FALse", "f", "F", "0",
                                                           "true,true", "true,false", "false,true", "false,false",
                                                           "true, true", "true, false", "false, true", "false, false",
                                                           "true,TRUE,TRue,t,T,1", "false,FALSE,FALse,f,F,0",
                                                           "true,TRUE,TRue,t,T,1,false,FALSE,FALse,f,F,0" });

        for (String value : values)
        {
            String[] args = new String[] { "-b", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            List<Boolean> list = booleanListArgument.getValue();
            assertNotNull("-b list not null", list);
            assertFalse("-b list not empty", list.isEmpty());
            assertTrue("-b list contains Boolean.TRUE or Boolean.FALSE", list.contains(Boolean.TRUE) || list.contains(Boolean.FALSE));
        }
    }

    public void testValidArgumentLong()
        throws CommandLineParseException
    {
        Argument<List<Boolean>> booleanListArgument = new BooleanListArgument("b", "boolean-list", "Boolean list argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { booleanListArgument }));
        List<String> values = Arrays.asList(new String[] { "true", "TRUE", "TRue", "t", "T", "1",
                                                           "false", "FALSE", "FALse", "f", "F", "0",
                                                           "true,true", "true,false", "false,true", "false,false",
                                                           "true, true", "true, false", "false, true", "false, false",
                                                           "true,TRUE,TRue,t,T,1", "false,FALSE,FALse,f,F,0",
                                                           "true,TRUE,TRue,t,T,1,false,FALSE,FALse,f,F,0" });

        for (String value : values)
        {
            String[] args = new String[] { "--boolean-list", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            List<Boolean> list = booleanListArgument.getValue();
            assertNotNull("--boolean-list list not null", list);
            assertFalse("--boolean-list list not empty", list.isEmpty());
            assertTrue("--boolean-list list contains Boolean.TRUE or Boolean.FALSE", list.contains(Boolean.TRUE) || list.contains(Boolean.FALSE));
        }
    }

    public void testInvalidArgumentLong()
    {
        Argument<List<Boolean>> booleanListArgument = new BooleanListArgument("b", "boolean-list", "Boolean list argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { booleanListArgument }));
        List<String> values = Arrays.asList(new String[] { "not-a-boolean", "9", "!true", "false!" });

        for (String value : values)
        {
            try
            {
                String[] args = new String[] { "--boolean-list", value };
                CommandLine commandLine = new CommandLine(args);
                CommandLineParser.parse(commandLine, arguments);

                fail("--boolean-list " + value + " expected CommandLineParseException");
            }
            catch (CommandLineParseException e)
            {
                // expected
            }
        }
    }

    public void testInvalidArgumentShort()
    {
        Argument<List<Boolean>> booleanListArgument = new BooleanListArgument("b", "boolean-list", "Boolean list argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { booleanListArgument }));
        List<String> values = Arrays.asList(new String[] { "not-a-boolean", "9", "!true", "false!" });

        for (String value : values)
        {
            try
            {
                String[] args = new String[] { "-b", value };
                CommandLine commandLine = new CommandLine(args);
                CommandLineParser.parse(commandLine, arguments);

                fail("-b " + value + " expected CommandLineParseException");
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
            Argument<List<Boolean>> booleanListArgument = new BooleanListArgument("b", "boolean-list", "Boolean list argument", true);
            ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { booleanListArgument }));

            String[] args = new String[] { "not-an-argument", "not-a-boolean" };
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
        Argument<List<Boolean>> booleanListArgument = new BooleanListArgument("b", "boolean-list", "Boolean list argument", false);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { booleanListArgument }));

        String[] args = new String[] { "not-an-argument", "not-a-boolean" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertFalse("booleanListArgument isRequired == false", booleanListArgument.isRequired());
        assertFalse("booleanListArgument wasFound == false", booleanListArgument.wasFound());
        assertEquals("booleanListArgument value == null", null, booleanListArgument.getValue());
    }
}
