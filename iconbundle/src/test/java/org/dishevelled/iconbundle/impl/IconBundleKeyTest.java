/*

    dsh-iconbundle  Bundles of variants for icon images.
    Copyright (c) 2003-2019 held jointly by the individual authors.

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
package org.dishevelled.iconbundle.impl;

import java.util.Iterator;

import junit.framework.TestCase;

import org.dishevelled.iconbundle.IconSize;
import org.dishevelled.iconbundle.IconState;
import org.dishevelled.iconbundle.IconTextDirection;

/**
 * Unit test case for IconBundleKey.
 *
 * @author  Michael Heuer
 */
public class IconBundleKeyTest
    extends TestCase
{

    public void testConstructor()
    {
        for (Iterator sizes = IconSize.VALUES.iterator(); sizes.hasNext(); )
        {
            IconSize size = (IconSize) sizes.next();
            for (Iterator states = IconState.VALUES.iterator(); states.hasNext(); )
            {
                IconState state = (IconState) states.next();
                for (Iterator directions = IconTextDirection.VALUES.iterator(); directions.hasNext(); )
                {
                    IconTextDirection direction = (IconTextDirection) directions.next();

                    IconBundleKey key = new IconBundleKey(direction, state, size);
                    assertNotNull("key not null", key);
                    assertNotNull("direction not null", key.getDirection());
                    assertNotNull("state not null", key.getState());
                    assertNotNull("size not null", key.getSize());
                    assertEquals("key direction equals direction", direction, key.getDirection());
                    assertEquals("key state equals state", state, key.getState());
                    assertEquals("key size equals size", size, key.getSize());
                }
            }
        }

        IconSize size = IconSize.DEFAULT_32X32;
        IconState state = IconState.NORMAL;
        IconTextDirection direction = IconTextDirection.LEFT_TO_RIGHT;

        try
        {
            IconBundleKey key = new IconBundleKey(null, state, size);
            fail("ctr(null,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            IconBundleKey key = new IconBundleKey(direction, null, size);
            fail("ctr(,null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            IconBundleKey key = new IconBundleKey(direction, state, null);
            fail("ctr(,,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testIconBundleKey()
    {
        IconBundleKey key0 = new IconBundleKey(IconTextDirection.LEFT_TO_RIGHT,
                                               IconState.NORMAL,
                                               IconSize.DEFAULT_32X32);

        IconBundleKey key1 = new IconBundleKey(IconTextDirection.LEFT_TO_RIGHT,
                                               IconState.NORMAL,
                                               IconSize.DEFAULT_32X32);

        assertNotNull("key0 not null", key0);
        assertNotNull("key1 not null", key1);
        assertEquals("key0 equals key0", key0, key0);
        assertFalse("key0 not equals new Object", key0.equals(new Object()));
        assertFalse("key0 not equals null", key0.equals(null));
        assertEquals("key0 equals key1", key1, key0);
        assertEquals("key1 equals key0", key0, key1);
        assertEquals("key0 hashCode == key1 hashCode", key1.hashCode(), key0.hashCode());
        assertEquals("key1 hashCode == key0 hashCode", key0.hashCode(), key1.hashCode());
        assertNotNull("key0 toString not null", key0.toString());
        assertNotNull("key1 toString not null", key1.toString());
        assertEquals("key0 toString equals key1 toString", key1.toString(), key0.toString());
        assertEquals("key1 toString equals key0 toString", key0.toString(), key1.toString());
        assertEquals("key0 direction equals key1 direction", key1.getDirection(), key0.getDirection());
        assertEquals("key1 direction equals key0 direction", key0.getDirection(), key1.getDirection());
        assertEquals("key0 state equals key1 state", key1.getState(), key0.getState());
        assertEquals("key1 state equals key0 state", key0.getState(), key1.getState());
        assertEquals("key0 size equals key1 size", key1.getSize(), key0.getSize());
        assertEquals("key1 size equals key0 size", key0.getSize(), key1.getSize());

        IconBundleKey key2 = new IconBundleKey(IconTextDirection.RIGHT_TO_LEFT,
                                               IconState.NORMAL,
                                               IconSize.DEFAULT_32X32);

        assertNotNull("key2 not null", key2);
        assertFalse("key2 not equals key0", key2.equals(key0));
        assertFalse("key2 not equals key1", key2.equals(key1));
        assertFalse("key0 not equals key2", key0.equals(key2));
        assertFalse("key1 not equals key2", key1.equals(key2));
        assertFalse("key2 direction not equals key0 direction", key2.getDirection().equals(key0.getDirection()));
        assertFalse("key2 direction not equals key1 direction", key2.getDirection().equals(key1.getDirection()));
        assertFalse("key0 direction not equals key2 direction", key0.getDirection().equals(key2.getDirection()));
        assertFalse("key1 direction not equals key2 direction", key1.getDirection().equals(key2.getDirection()));
        assertFalse("key2 hashCode != key0 hashCode", key2.hashCode() == key0.hashCode());
        assertFalse("key2 hashCode != key1 hashCode", key2.hashCode() == key1.hashCode());
        assertFalse("key2 toString not equals key0 toString", key2.toString().equals(key0.toString()));
        assertFalse("key2 toString not equals key1 toString", key2.toString().equals(key1.toString()));
        assertFalse("key0 toString not equals key2 toString", key0.toString().equals(key2.toString()));
        assertFalse("key1 toString not equals key2 toString", key1.toString().equals(key2.toString()));

        IconBundleKey key3 = new IconBundleKey(IconTextDirection.LEFT_TO_RIGHT,
                                               IconState.ACTIVE,
                                               IconSize.DEFAULT_32X32);

        assertNotNull("key3 not null", key3);
        assertFalse("key3 not equals key0", key3.equals(key0));
        assertFalse("key3 not equals key1", key3.equals(key1));
        assertFalse("key3 not equals key2", key3.equals(key2));
        assertFalse("key0 not equals key3", key0.equals(key3));
        assertFalse("key1 not equals key3", key1.equals(key3));
        assertFalse("key2 not equals key3", key2.equals(key3));
        assertFalse("key3 state not equals key0 state", key3.getState().equals(key0.getState()));
        assertFalse("key3 state not equals key1 state", key3.getState().equals(key1.getState()));
        assertFalse("key0 state not equals key3 state", key0.getState().equals(key3.getState()));
        assertFalse("key1 state not equals key3 state", key1.getState().equals(key3.getState()));
        assertFalse("key3 hashCode != key0 hashCode", key3.hashCode() == key0.hashCode());
        assertFalse("key3 hashCode != key1 hashCode", key3.hashCode() == key1.hashCode());
        assertFalse("key3 hashCode != key2 hashCode", key3.hashCode() == key2.hashCode());
        assertFalse("key3 toString not equals key0 toString", key3.toString().equals(key0.toString()));
        assertFalse("key3 toString not equals key1 toString", key3.toString().equals(key1.toString()));
        assertFalse("key3 toString not equals key2 toString", key3.toString().equals(key2.toString()));
        assertFalse("key0 toString not equals key3 toString", key0.toString().equals(key3.toString()));
        assertFalse("key1 toString not equals key3 toString", key1.toString().equals(key3.toString()));
        assertFalse("key2 toString not equals key3 toString", key2.toString().equals(key3.toString()));

        IconBundleKey key4 = new IconBundleKey(IconTextDirection.LEFT_TO_RIGHT,
                                               IconState.NORMAL,
                                               IconSize.DEFAULT_16X16);

        assertNotNull("key4 not null", key4);
        assertFalse("key4 not equals key0", key4.equals(key0));
        assertFalse("key4 not equals key1", key4.equals(key1));
        assertFalse("key4 not equals key2", key4.equals(key2));
        assertFalse("key4 not equals key3", key4.equals(key3));
        assertFalse("key0 not equals key4", key0.equals(key4));
        assertFalse("key1 not equals key4", key1.equals(key4));
        assertFalse("key2 not equals key4", key2.equals(key4));
        assertFalse("key3 not equals key4", key3.equals(key4));
        assertFalse("key4 size not equals key0 size", key4.getSize().equals(key0.getSize()));
        assertFalse("key4 size not equals key1 size", key4.getSize().equals(key1.getSize()));
        assertFalse("key0 size not equals key4 size", key0.getSize().equals(key4.getSize()));
        assertFalse("key1 size not equals key4 size", key1.getSize().equals(key4.getSize()));
        assertFalse("key4 hashCode != key0 hashCode", key4.hashCode() == key0.hashCode());
        assertFalse("key4 hashCode != key1 hashCode", key4.hashCode() == key1.hashCode());
        assertFalse("key4 hashCode != key2 hashCode", key4.hashCode() == key2.hashCode());
        assertFalse("key4 hashCode != key3 hashCode", key4.hashCode() == key3.hashCode());
        assertFalse("key4 toString not equals key0 toString", key4.toString().equals(key0.toString()));
        assertFalse("key4 toString not equals key1 toString", key4.toString().equals(key1.toString()));
        assertFalse("key4 toString not equals key2 toString", key4.toString().equals(key2.toString()));
        assertFalse("key4 toString not equals key3 toString", key4.toString().equals(key3.toString()));
        assertFalse("key0 toString not equals key4 toString", key0.toString().equals(key4.toString()));
        assertFalse("key1 toString not equals key4 toString", key1.toString().equals(key4.toString()));
        assertFalse("key2 toString not equals key4 toString", key2.toString().equals(key4.toString()));
        assertFalse("key3 toString not equals key4 toString", key3.toString().equals(key4.toString()));
    }
}
