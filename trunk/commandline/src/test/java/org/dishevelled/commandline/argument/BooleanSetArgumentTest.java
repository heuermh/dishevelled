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
 * Unit test for BooleanSetArgument.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class BooleanSetArgumentTest
    extends TestCase
{

    public void testBooleanSetArgument()
    {
        BooleanSetArgument bsa = new BooleanSetArgument("b", "boolean-set", "Boolean set argument", true);
        assertNotNull("bsa not null", bsa);
        assertEquals("bsa shortName == b", "b", bsa.getShortName());
        assertEquals("bsa longName == boolean-set", "boolean-set", bsa.getLongName());
        assertEquals("bsa description == Boolean set argument", "Boolean set argument", bsa.getDescription());
        assertTrue("bsa isRequired", bsa.isRequired());
        assertFalse("bsa wasFound == false", bsa.wasFound());
        assertEquals("bsa value == null", null, bsa.getValue());
    }

    public void testValidArgumentShort()
        throws CommandLineParseException
    {
        Argument<Set<Boolean>> booleanSetArgument = new BooleanSetArgument("b", "boolean-set", "Boolean set argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { booleanSetArgument }));
        List<String> values = Arrays.asList(new String[] { "true", "TRUE", "TRue", "t", "T", "1",
                                                           "false", "FALSE", "FALse", "f", "F", "0",
                                                           "true,true", "true,false", "false,true", "false,false",
                                                           "true, true", "true, false", "false, true", "false, false",
                                                           "true,", "false,", ",true", ",false", "true,,false",
                                                           "true,TRUE,TRue,t,T,1", "false,FALSE,FALse,f,F,0",
                                                           "true,TRUE,TRue,t,T,1,false,FALSE,FALse,f,F,0" });

        for (String value : values)
        {
            String[] args = new String[] { "-b", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            Set<Boolean> set = booleanSetArgument.getValue();
            assertNotNull("-b set not null", set);
            assertFalse("-b set not empty", set.isEmpty());
            assertTrue("-b set contains Boolean.TRUE or Boolean.FALSE", set.contains(Boolean.TRUE) || set.contains(Boolean.FALSE));
        }
    }

    public void testValidArgumentLong()
        throws CommandLineParseException
    {
        Argument<Set<Boolean>> booleanSetArgument = new BooleanSetArgument("b", "boolean-set", "Boolean set argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { booleanSetArgument }));
        List<String> values = Arrays.asList(new String[] { "true", "TRUE", "TRue", "t", "T", "1",
                                                           "false", "FALSE", "FALse", "f", "F", "0",
                                                           "true,true", "true,false", "false,true", "false,false",
                                                           "true, true", "true, false", "false, true", "false, false",
                                                           "true,", "false,", ",true", ",false", "true,,false",
                                                           "true,TRUE,TRue,t,T,1", "false,FALSE,FALse,f,F,0",
                                                           "true,TRUE,TRue,t,T,1,false,FALSE,FALse,f,F,0" });

        for (String value : values)
        {
            String[] args = new String[] { "--boolean-set", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            Set<Boolean> set = booleanSetArgument.getValue();
            assertNotNull("--boolean-set set not null", set);
            assertFalse("--boolean-set set not empty", set.isEmpty());
            assertTrue("--boolean-set set contains Boolean.TRUE or Boolean.FALSE", set.contains(Boolean.TRUE) || set.contains(Boolean.FALSE));
        }
    }

    public void testInvalidArgumentShort()
    {
        Argument<Set<Boolean>> booleanSetArgument = new BooleanSetArgument("b", "boolean-set", "Boolean set argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { booleanSetArgument }));
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

    public void testInvalidArgumentLong()
    {
        Argument<Set<Boolean>> booleanSetArgument = new BooleanSetArgument("b", "boolean-set", "Boolean set argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { booleanSetArgument }));
        List<String> values = Arrays.asList(new String[] { "not-a-boolean", "9", "!true", "false!" });

        for (String value : values)
        {
            try
            {
                String[] args = new String[] { "--boolean-set", value };
                CommandLine commandLine = new CommandLine(args);
                CommandLineParser.parse(commandLine, arguments);

                fail("--boolean-set " + value + " expected CommandLineParseException");
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
            Argument<Set<Boolean>> booleanSetArgument = new BooleanSetArgument("b", "boolean-set", "Boolean set argument", true);
            ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { booleanSetArgument }));

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
        Argument<Set<Boolean>> booleanSetArgument = new BooleanSetArgument("b", "boolean-set", "Boolean set argument", false);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { booleanSetArgument }));

        String[] args = new String[] { "not-an-argument", "not-a-boolean" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertFalse("booleanSetArgument isRequired == false", booleanSetArgument.isRequired());
        assertFalse("booleanSetArgument wasFound == false", booleanSetArgument.wasFound());
        assertEquals("booleanSetArgument value == null", null, booleanSetArgument.getValue());
    }
}
