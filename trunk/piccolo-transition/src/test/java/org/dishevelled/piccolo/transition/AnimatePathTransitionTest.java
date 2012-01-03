/*

    dsh-piccolo-transition  Transitions implemented as Piccolo2D activities.
    Copyright (c) 2007-2012 held jointly by the individual authors.

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

import java.awt.geom.Path2D;

import junit.framework.TestCase;

import org.piccolo2d.PNode;

/**
 * Unit test for AnimatePathTransition.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class AnimatePathTransitionTest
    extends TestCase
{

    public void testConstructor()
    {
        PNode node = new PNode();
        Path2D path = new Path2D.Double();
        path.moveTo(75.0d, 150.0d);
        path.lineTo(75.0d, 50.0d);
        path.lineTo(75.0d, 150.0d);
        AnimatePathTransition animatePathTransition0 = new AnimatePathTransition(node, path, 0);
        AnimatePathTransition animatePathTransition1 = new AnimatePathTransition(node, path, 1L);
        AnimatePathTransition animatePathTransition2 = new AnimatePathTransition(node, path, -1L);
        AnimatePathTransition animatePathTransition3 = new AnimatePathTransition(node, path, 500L);
        AnimatePathTransition animatePathTransition4 = new AnimatePathTransition(node, path, -500L);
        AnimatePathTransition animatePathTransition5 = new AnimatePathTransition(node, path, Long.MAX_VALUE);
        AnimatePathTransition animatePathTransition6 = new AnimatePathTransition(node, path, Long.MIN_VALUE);

        try
        {
            AnimatePathTransition animatePathTransition = new AnimatePathTransition(null, path, 500L);
            fail("ctr(null,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            AnimatePathTransition animatePathTransition = new AnimatePathTransition(node, null, 500L);
            fail("ctr(,null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testSetRelativeTargetValue()
    {
        PNode node = new PNode();
        Path2D path = new Path2D.Double();
        path.moveTo(75.0d, 150.0d);
        path.lineTo(75.0d, 50.0d);
        path.lineTo(75.0d, 150.0d);
        AnimatePathTransition animatePathTransition = new AnimatePathTransition(node, path, 500L);
        animatePathTransition.setRelativeTargetValue(0.0f);
        animatePathTransition.setRelativeTargetValue(0.5f);
        animatePathTransition.setRelativeTargetValue(1.0f);

        // TODO:
        //   check out-of-bounds values?
    }

    public void testPath()
    {
        PNode node = new PNode();
        Path2D path = new Path2D.Double();
        path.moveTo(75.0d, 150.0d);
        path.lineTo(75.0d, 50.0d);
        path.lineTo(75.0d, 150.0d);
        AnimatePathTransition animatePathTransition = new AnimatePathTransition(node, path, 500L);
        assertEquals(path, animatePathTransition.getPath());

        // TODO:
        //   is it possible to defensively copy a path?
    }
}