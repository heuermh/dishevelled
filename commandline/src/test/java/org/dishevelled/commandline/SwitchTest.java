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
package org.dishevelled.commandline;

import java.util.Arrays;

import junit.framework.TestCase;

/**
 * Unit test for Switch.
 *
 * @author  Michael Heuer
 */
public class SwitchTest
    extends TestCase
{

    public void testSwitch()
    {
        Switch s = new Switch("s", "switch", "Switch");
        assertNotNull("s not null", s);
        assertEquals("s shortName == s", "s", s.getShortName());
        assertEquals("s longName == switch", "switch", s.getLongName());
        assertEquals("s description == Switch", "Switch", s.getDescription());
        assertFalse("s isRequired == false", s.isRequired());
        assertFalse("s wasFound == false", s.wasFound());
        assertEquals("s value == false", Boolean.FALSE, s.getValue());
        assertEquals(Boolean.FALSE, s.getValue(Boolean.FALSE));
        assertEquals(Boolean.TRUE, s.getValue(Boolean.TRUE));
    }

    public void testSwitchShortPresent()
        throws CommandLineParseException
    {
        Switch s = new Switch("s", "switch", "Switch");
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { s }));

        String[] args = new String[] { "-s" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertEquals("-s == true", Boolean.TRUE, s.getValue());
        assertEquals(Boolean.TRUE, s.getValue(Boolean.FALSE));
        assertEquals(Boolean.TRUE, s.getValue(Boolean.TRUE));
    }

    public void testSwitchLongPresent()
        throws CommandLineParseException
    {
        Switch s = new Switch("s", "switch", "Switch");
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { s }));

        String[] args = new String[] { "--switch" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertEquals("--switch == true", Boolean.TRUE, s.getValue());
    }

    public void testSwitchMissing()
        throws CommandLineParseException
    {
        Switch s = new Switch("s", "switch", "Switch");
        ArgumentList arguments = new ArgumentList(Arrays.asList(new Argument<?>[] { s }));

        String[] args = new String[] { "-n", "--not-a-switch" };
        CommandLine commandLine = new CommandLine(args);
        CommandLineParser.parse(commandLine, arguments);

        assertEquals("-n --not-a-switch == false", Boolean.FALSE, s.getValue());
    }

    public void testObjectContract()
    {
        Switch s0 = new Switch("s", "switch", "Switch");
        assertFalse("s0 != null", s0.equals(null));
        assertFalse("s0 != new Object", s0.equals(new Object()));

        Switch s1 = new Switch("t", "switch", "Switch");
        Switch s2 = new Switch("s", "hello, world", "Switch");
        Switch s3 = new Switch("s", "switch", "Hello, world");
        Switch s4 = new Switch("s", "switch", "Switch");

        assertFalse("s0 does not equal s1", s0.equals(s1));
        assertFalse("s1 does not equal s0", s1.equals(s0));
        assertFalse("s0 does not equal s2", s0.equals(s2));
        assertFalse("s2 does not equal s0", s2.equals(s0));
        assertFalse("s0 does not equal s3", s0.equals(s3));
        assertFalse("s3 does not equal s0", s3.equals(s0));
        assertFalse("s0 does not equal s4", s0.equals(s4));
        assertFalse("s4 does not equal s0", s4.equals(s0));
    }
}
