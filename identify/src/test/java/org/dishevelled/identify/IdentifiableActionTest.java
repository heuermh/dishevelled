/*

    dsh-identify  Lightweight components for identifiable beans.
    Copyright (c) 2003-2008 held jointly by the individual authors.

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
package org.dishevelled.identify;

import java.awt.event.ActionEvent;

import junit.framework.TestCase;

import org.dishevelled.iconbundle.tango.TangoProject;

/**
 * Unit test for IdentifiableAction.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class IdentifiableActionTest
    extends TestCase
{
    public void testIdentifiableAction()
    {
        IdentifiableAction action = new IdentifiableAction("name", TangoProject.TEXT_X_GENERIC)
            {
                /** @see IdentifiableAction */
                public void actionPerformed(final ActionEvent e)
                {
                    // empty
                }
            };

        assertNotNull("action not null", action);
        assertNotNull("action name not null", action.getName());
        assertNotNull("action iconBundle not null", action.getIconBundle());
        assertEquals("name", action.getName());
        assertEquals(TangoProject.TEXT_X_GENERIC, action.getIconBundle());

        IdentifiableAction nullName = new IdentifiableAction(null, TangoProject.TEXT_X_GENERIC)
            {
                /** @see IdentifiableAction */
                public void actionPerformed(final ActionEvent e)
                {
                    // empty
                }
            };

        assertNotNull("nullName not null", nullName);
        assertNotNull("nullName iconBundle not null", nullName.getIconBundle());
        assertEquals(null, nullName.getName());
        assertEquals(TangoProject.TEXT_X_GENERIC, nullName.getIconBundle());

        try
        {
            IdentifiableAction nullIconBundle = new IdentifiableAction("name", null)
                {
                    /** @see IdentifiableAction */
                    public void actionPerformed(final ActionEvent e)
                    {
                        // empty
                    }
                };

            fail("ctr(,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }
}