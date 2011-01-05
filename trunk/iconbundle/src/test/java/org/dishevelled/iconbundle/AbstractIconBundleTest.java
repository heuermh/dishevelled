/*

    dsh-iconbundle  Bundles of variants for icon images.
    Copyright (c) 2003-2011 held jointly by the individual authors.

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
package org.dishevelled.iconbundle;

import java.awt.Image;
import java.awt.Component;

import javax.swing.JPanel;

import java.util.List;
import java.util.Random;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collections;

import junit.framework.TestCase;

/**
 * Abstract unit test for implementations of IconBundle.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractIconBundleTest
    extends TestCase
{
    /** Pseudo-random number generator. */
    private Random random = new Random();


    /**
     * Create a new icon bundle suitable for test.
     *
     * @return a new icon bundle suitable for test
     */
    protected abstract IconBundle createIconBundle();


    public void testGetImage()
    {
        IconBundle ib = createIconBundle();
        assertNotNull("ib not null", ib);

        Component c = new JPanel();

        try
        {
            Image image = ib.getImage(c, null, IconState.NORMAL, IconSize.DEFAULT_16X16);
            fail("getImage(,null,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            Image image = ib.getImage(c, IconTextDirection.LEFT_TO_RIGHT, null, IconSize.DEFAULT_16X16);
            fail("getImage(,,null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            Image image = ib.getImage(c, IconTextDirection.LEFT_TO_RIGHT, IconState.NORMAL, null);
            fail("getImage(,,,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        // do things once in order for good measure
        doTestAllVariants(ib, c, IconTextDirection.VALUES, IconState.VALUES, IconSize.VALUES);
    }

    public void testShuffle()
    {
        for (int i = 0; i < 10; i++)
        {
            IconBundle iconBundle = createIconBundle();
            Component c = new JPanel();

            List directions = new ArrayList(IconTextDirection.VALUES);
            List states = new ArrayList(IconState.VALUES);
            List sizes = new ArrayList(IconSize.VALUES);

            Collections.shuffle(directions);
            Collections.shuffle(states);
            Collections.shuffle(sizes);

            doTestAllVariants(iconBundle, c, directions, states, sizes);
        }
    }


    /**
     * Test the call to <code>getImage</code> over all variants on
     * the specified icon bundle.
     *
     * @param iconBundle icon bundle to test
     * @param component component hint, if any
     * @param directions list of icon text directions
     * @param states list of icon states
     * @param sizes list of icon sizes
     */
    protected void doTestAllVariants(final IconBundle iconBundle,
                                     final Component component,
                                     final List directions,
                                     final List states,
                                     final List sizes)
    {
        for (Iterator i = directions.iterator(); i.hasNext(); )
        {
            IconTextDirection direction = (IconTextDirection) i.next();
            for (Iterator j = states.iterator(); j.hasNext(); )
            {
                IconState state = (IconState) j.next();
                for (Iterator k = sizes.iterator(); k.hasNext(); )
                {
                    IconSize size = (IconSize) k.next();

                    // randomize order of non-null, null hint calls
                    if (random.nextDouble() > 0.5d)
                    {
                        doTestGetImage(iconBundle, component, direction, state, size);
                        doTestGetImage(iconBundle, null, direction, state, size);
                    }
                    else
                    {
                        doTestGetImage(iconBundle, null, direction, state, size);
                        doTestGetImage(iconBundle, component, direction, state, size);
                    }
                }
            }
        }
    }

    /**
     * Test the call to <code>getImage</code> with the specified parameters on the
     * specified icon bundle.
     *
     * @param iconBundle icon bundle to test
     * @param component component hint, if any
     * @param direction icon text direction
     * @param state icon state
     * @param size icon size
     */
    protected void doTestGetImage(final IconBundle iconBundle,
                                  final Component component,
                                  final IconTextDirection direction,
                                  final IconState state,
                                  final IconSize size)
    {
        Image image = iconBundle.getImage(component, direction, state, size);
        assertNotNull("image not null", image);
    }
}