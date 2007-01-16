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

import java.util.Date;
import java.util.List;
import java.util.Arrays;

import junit.framework.TestCase;

import org.dishevelled.commandline.Argument;
import org.dishevelled.commandline.ArgumentList;
import org.dishevelled.commandline.CommandLine;
import org.dishevelled.commandline.CommandLineParser;
import org.dishevelled.commandline.CommandLineParseException;

/**
 * Unit test for DateArgument.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class DateArgumentTest
    extends TestCase
{

    public void testDateArgument()
    {
        DateArgument da = new DateArgument("d", "date", "Date argument", true);
        assertNotNull("da not null", da);
        assertEquals("da shortName == d", "d", da.getShortName());
        assertEquals("da longName == date", "date", da.getLongName());
        assertEquals("da description == Date argument", "Date argument", da.getDescription());
        assertTrue("da isRequired", da.isRequired());
        assertFalse("da wasFound == false", da.wasFound());
        assertEquals("da value == null", null, da.getValue());
    }

    public void testValidArgumentShort()
        throws CommandLineParseException
    {
        Argument<Date> dateArgument = new DateArgument("d", "date-argument", "Date argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument[] { dateArgument }));
        List<String> values = Arrays.asList(new String[] { "2001-07-04" });

        for (String value : values)
        {
            String[] args = new String[] { "-d", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            assertNotNull("-d not null", dateArgument.getValue());
        }
    }

    public void testValidArgumentLong()
        throws CommandLineParseException
    {
        Argument<Date> dateArgument = new DateArgument("d", "date-argument", "Date argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument[] { dateArgument }));
        List<String> values = Arrays.asList(new String[] { "2001-07-04" });

        for (String value : values)
        {
            String[] args = new String[] { "--date-argument", value };
            CommandLine commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            assertNotNull("-d not null", dateArgument.getValue());
        }
    }

    public void testInvalidArgumentShort()
    {
        Argument<Date> dateArgument = new DateArgument("d", "date-argument", "Date argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument[] { dateArgument }));
        List<String> values = Arrays.asList(new String[] { "foo", "bar" });

        for (String value : values)
        {
            try
            {
                String[] args = new String[] { "-d", value };
                CommandLine commandLine = new CommandLine(args);
                CommandLineParser.parse(commandLine, arguments);

                fail("-d expected CommandLineParseException");
            }
            catch (CommandLineParseException e)
            {
                // expected
            }
        }
    }

    public void testInvalidArgumentLong()
    {
        Argument<Date> dateArgument = new DateArgument("d", "date-argument", "Date argument", true);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument[] { dateArgument }));
        List<String> values = Arrays.asList(new String[] { "foo", "bar" });

        for (String value : values)
        {
            try
            {
                String[] args = new String[] { "--date-argument", value };
                CommandLine commandLine = new CommandLine(args);
                CommandLineParser.parse(commandLine, arguments);

                fail("--date-argument expected CommandLineParseException");
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
            Argument<Date> dateArgument = new DateArgument("d", "date-argument", "Date argument", true);
            ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument[] { dateArgument }));

            String[] args = new String[] { "not-an-argument", "not-a-date" };
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
        Argument<Date> dateArgument = new DateArgument("d", "date-argument", "Date argument", false);
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument[] { dateArgument }));

        String[] args = new String[] { "not-an-argument", "not-a-date" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertFalse("dateArgument isRequired == false", dateArgument.isRequired());
        assertFalse("dateArgument wasFound == false", dateArgument.wasFound());
        assertEquals("dateArgument value == null", null, dateArgument.getValue());
    }
}
