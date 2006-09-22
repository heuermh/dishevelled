/*

    dsh-identify  Lightweight components for identifiable beans.
    Copyright (c) 2003-2006 held jointly by the individual authors.

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
package org.dishevelled.identify;

import java.awt.ComponentOrientation;

import java.awt.event.ActionEvent;

import java.awt.image.BufferedImage;

import javax.swing.Action;

import junit.framework.TestCase;

import org.dishevelled.iconbundle.IconSize;
import org.dishevelled.iconbundle.IconState;
import org.dishevelled.iconbundle.IconTextDirection;

import org.dishevelled.iconbundle.tango.TangoProject;

/**
 * Unit test for IdMenuItem.
 *
 * @author  Michael Heuer
 * @version $Revision: 62 $ $Date: 2006-02-13 17:36:34 -0600 (Mon, 13 Feb 2006) $
 */
public final class IdMenuItemTest
    extends TestCase
{

    public void testConstructor()
    {
        IdentifiableAction action = new IdentifiableAction("name", TangoProject.TEXT_X_GENERIC)
            {
                /** @see IdentifiableAction */
                public void actionPerformed(final ActionEvent e)
                {
                    // empty
                }
            };

        IdMenuItem button0 = new IdMenuItem(action);
        IdMenuItem button1 = new IdMenuItem(action, TangoProject.SMALL);

        try
        {
            IdMenuItem nullAction = new IdMenuItem(null);
            fail("ctr(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            IdMenuItem nullSize = new IdMenuItem(action, null);
            fail("ctr(action, null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }
}