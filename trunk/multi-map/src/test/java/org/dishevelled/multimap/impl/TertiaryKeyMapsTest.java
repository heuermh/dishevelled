/*

    dsh-multi-map  Multi-key map interfaces and implementation.
    Copyright (c) 2007-2010 held jointly by the individual authors.

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
package org.dishevelled.multimap.impl;

import junit.framework.TestCase;

import org.dishevelled.multimap.TertiaryKeyMap;

import static org.dishevelled.multimap.impl.TertiaryKeyMaps.*;

/**
 * Unit test for TertiaryKeyMaps.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class TertiaryKeyMapsTest
    extends TestCase
{

    public void testCreateTertiaryKeyMap()
    {
        TertiaryKeyMap<Float, Object, String, Integer> tertiaryKeyMap = createTertiaryKeyMap();
        assertNotNull(tertiaryKeyMap);
    }

    public void testCreateTertiaryKeyMapInitialCapacity()
    {
        TertiaryKeyMap<Float, Object, String, Integer> tertiaryKeyMap = createTertiaryKeyMap(32);
        assertNotNull(tertiaryKeyMap);
    }

    public void testCreateTertiaryKeyMapInitialCapacityLoadFactorThreshold()
    {
        TertiaryKeyMap<Float, Object, String, Integer> tertiaryKeyMap = createTertiaryKeyMap(32, 0.5f, 16);
        assertNotNull(tertiaryKeyMap);
    }
}