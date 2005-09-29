/*

    dsh-commandline  Simple command line parser based on typed arguments.
    Copyright (c) 2004-2005 held jointly by the individual authors.

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

import junit.framework.TestCase;

/**
 * Unit test for URLArgument.
 *
 * @author  Michael Heuer
 * @version $Revision$ $URL$
 */
public class URLArgumentTest
    extends TestCase
{

    public void testURLArgument()
    {
        URLArgument ua = new URLArgument("u", "URL", "URL argument", true);
        assertNotNull("ua not null", ua);
        assertEquals("ua shortName == u", "u", ua.getShortName());
        assertEquals("ua longName == URL", "URL", ua.getLongName());
        assertEquals("ua description == URL argument", "URL argument", ua.getDescription());
        assertTrue("ua isRequired", ua.isRequired());
        assertFalse("ua wasFound == false", ua.wasFound());
        assertEquals("ua value == null", null, ua.getValue());
    }
}