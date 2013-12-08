/*

    dsh-piccolo-transition  Transitions implemented as Piccolo2D activities.
    Copyright (c) 2007-2013 held jointly by the individual authors.

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
package org.dishevelled.piccolo.transition;

import junit.framework.TestCase;

import org.piccolo2d.PNode;

/**
 * Unit test for BrightenTransition.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class BrightenTransitionTest
    extends TestCase
{

    public void testConstructor()
    {
        PNode node = new PNode();
        BrightenTransition brightenTransition0 = new BrightenTransition(node, 0);
        BrightenTransition brightenTransition1 = new BrightenTransition(node, 1L);
        BrightenTransition brightenTransition2 = new BrightenTransition(node, -1L);
        BrightenTransition brightenTransition3 = new BrightenTransition(node, 500L);
        BrightenTransition brightenTransition4 = new BrightenTransition(node, -500L);
        BrightenTransition brightenTransition5 = new BrightenTransition(node, Long.MAX_VALUE);
        BrightenTransition brightenTransition6 = new BrightenTransition(node, Long.MIN_VALUE);
        BrightenTransition brightenTransition7 = new BrightenTransition(node, 500L, 0.0f);
        BrightenTransition brightenTransition8 = new BrightenTransition(node, 500L, 0.5f);
        BrightenTransition brightenTransition9 = new BrightenTransition(node, 500L, 1.0f);

        try
        {
            BrightenTransition brightenTransition = new BrightenTransition(null, 500L);
            fail("ctr(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            BrightenTransition brightenTransition = new BrightenTransition(node, 500L, -0.1f);
            fail("ctr(,,-0.1f) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            BrightenTransition brightenTransition = new BrightenTransition(node, 500L, 1.1f);
            fail("ctr(,,1.1f) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testSetRelativeTargetValue()
    {
        PNode node = new PNode();
        BrightenTransition brightenTransition = new BrightenTransition(node, 500L);
        brightenTransition.setRelativeTargetValue(0.0f);
        brightenTransition.setRelativeTargetValue(0.5f);
        brightenTransition.setRelativeTargetValue(1.0f);
    }

    public void testDestinationTransparency()
    {
        PNode node = new PNode();
        BrightenTransition brightenTransition0 = new BrightenTransition(node, 1L);
        assertEquals(BrightenTransition.DEFAULT_DESTINATION_TRANSPARENCY, brightenTransition0.getDestinationTransparency(), 0.1f);
        BrightenTransition brightenTransition1 = new BrightenTransition(node, 1L, 0.75f);
        assertEquals(0.75f, brightenTransition1.getDestinationTransparency(), 0.1f);
    }
}