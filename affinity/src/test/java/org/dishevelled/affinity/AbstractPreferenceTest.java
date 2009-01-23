/*

    dsh-affinity  Clustering by affinity propagation.
    Copyright (c) 2007-2009 held jointly by the individual authors.

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
package org.dishevelled.affinity;

import junit.framework.TestCase;

/**
 * Abstract unit test for implementations of Preference.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractPreferenceTest
    extends TestCase
{

    /**
     * Create and return a new instance of an implementation of Preference to test.
     *
     * @param <T> value type
     * @return a new instance of an implementation of Preference to test
     */
    protected abstract <T> Preference<T> createPreference();

    public void testAbstractPreference()
    {
        Preference<String> preference = createPreference();
        assertNotNull("preference not null");

        try
        {
            preference.preference(null);
            fail("preference(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }
}