/*

    dsh-layout  Layout managers for lightweight components.
    Copyright (c) 2003-2010 held jointly by the individual authors.

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
package org.dishevelled.layout;

import javax.swing.JPanel;

import junit.framework.TestCase;

/**
 * Unit test for ButtonLayout.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class ButtonLayoutTest
    extends TestCase
{

    public void testConstructor()
    {
        ButtonLayout buttonLayout0 = new ButtonLayout(null);
        assertNotNull(buttonLayout0);
        JPanel panel = new JPanel();
        ButtonLayout buttonLayout1 = new ButtonLayout(panel);
        assertNotNull(buttonLayout1);
    }
}