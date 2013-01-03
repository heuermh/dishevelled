/*

    dsh-commandline  Command line parser based on typed arguments.
    Copyright (c) 2004-2013 held jointly by the individual authors.

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

import junit.framework.TestCase;

/**
 * Unit test for CommandLineParseException.
 *
 * @author  Michael Heuer
 */
public final class CommandLineParseExceptionTest
    extends TestCase
{

    public void testConstructor()
    {
        Throwable cause = new Exception("message");

        CommandLineParseException clpe0 = new CommandLineParseException("message");
        assertNotNull(clpe0);
        CommandLineParseException clpe1 = new CommandLineParseException(cause);
        assertNotNull(clpe1);
        CommandLineParseException clpe2 = new CommandLineParseException("message");
        assertNotNull(clpe2);
        CommandLineParseException clpe3 = new CommandLineParseException("message", cause);
        assertNotNull(clpe3);
    }
}
