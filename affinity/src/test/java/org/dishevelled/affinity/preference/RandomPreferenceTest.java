/*

    dsh-affinity  Clustering by affinity propagation.
    Copyright (c) 2007-2011 held jointly by the individual authors.

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
package org.dishevelled.affinity.preference;

import java.util.Random;

import org.dishevelled.affinity.Preference;
import org.dishevelled.affinity.AbstractPreferenceTest;

/**
 * Unit test for RandomPreference.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class RandomPreferenceTest
    extends AbstractPreferenceTest
{

    /** {@inheritDoc} */
    protected <T> Preference<T> createPreference()
    {
        return new RandomPreference<T>();
    }

    public void testConstructor()
    {
        Preference preference0 = new RandomPreference();
        Preference preference1 = new RandomPreference(new Random());

        try
        {
            Preference preference = new RandomPreference(null);
            fail("ctr(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testPreference()
    {
        Preference<String> preference = new RandomPreference<String>();
        for (int i = 0; i < 100; i++)
        {
            double value = preference.preference("foo");
            assertTrue(value >= 0.0d);
            assertTrue(value < 1.0d);
        }
    }
}