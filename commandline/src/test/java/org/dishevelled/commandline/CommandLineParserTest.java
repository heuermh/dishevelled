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
package org.dishevelled.commandline;

import java.util.Collections;

import junit.framework.TestCase;

/**
 * Unit test for CommandLineParser.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class CommandLineParserTest
    extends TestCase
{

    public void testParse()
    {
        CommandLine commandLine = new CommandLine(new String[0]);
        ArgumentList arguments = new ArgumentList(Collections.<Argument<?>>emptyList());

        try
        {
            CommandLineParser.parse(null, arguments);
            fail("parse(null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        catch (CommandLineParseException e)
        {
            fail("caught unexpected CommandLineParseException");
        }

        try
        {
            CommandLineParser.parse(commandLine, null);
            fail("parse(,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        catch (CommandLineParseException e)
        {
            fail("caught unexpected CommandLineParseException");
        }
    }
}
