/*

    dsh-weighted  Weighted map interface and implementation.
    Copyright (c) 2005-2013 held jointly by the individual authors.

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
package org.dishevelled.weighted;

import junit.framework.TestCase;

import static org.dishevelled.weighted.WeightedMaps.*;

/**
 * Unit test for WeightedMaps.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class WeightedMapsTest
    extends TestCase
{

    public void testCreateWeightedMap()
    {
        WeightedMap<String> weightedMap0 = createWeightedMap();
        assertNotNull(weightedMap0);

        WeightedMap<String> weightedMap1 = createWeightedMap(15);
        assertNotNull(weightedMap1);

        WeightedMap<String> weightedMap2 = createWeightedMap(15, 0.75f);
        assertNotNull(weightedMap2);

        WeightedMap<String> toCopy = createWeightedMap();
        toCopy.put("foo", 1.0d);
        toCopy.put("bar", 2.0d);

        WeightedMap<String> weightedMap3 = createWeightedMap(toCopy);
        assertNotNull(weightedMap3);

        try
        {
            WeightedMap<String> ignore = createWeightedMap(null);
            fail("createWeightedMap(null) expected NullPointerException");
        }
        catch (NullPointerException e)
        {
            // expected
        }
    }
}