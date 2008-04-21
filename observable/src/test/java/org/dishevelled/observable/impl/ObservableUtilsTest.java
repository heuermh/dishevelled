/*

    dsh-observable  Observable decorators for collection and map interfaces.
    Copyright (c) 2003-2008 held jointly by the individual authors.

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
package org.dishevelled.observable.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.TreeSet;

import junit.framework.TestCase;

import org.dishevelled.observable.ObservableCollection;
import org.dishevelled.observable.ObservableList;
import org.dishevelled.observable.ObservableMap;
import org.dishevelled.observable.ObservableSet;
import org.dishevelled.observable.ObservableSortedMap;
import org.dishevelled.observable.ObservableSortedSet;
import org.dishevelled.observable.impl.ObservableUtils;

/**
 * Unit test for ObservableUtils.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class ObservableUtilsTest
    extends TestCase
{

    public void testObservableCollection()
    {
        ObservableCollection<String> collection0 = ObservableUtils.observableCollection(new ArrayList<String>());
        assertNotNull(collection0);

        try
        {
            ObservableCollection<String> collection = ObservableUtils.observableCollection(null);
            fail("observableCollection(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testObservableList()
    {
        ObservableList<String> list0 = ObservableUtils.observableList(new ArrayList<String>());
        assertNotNull(list0);

        try
        {
            ObservableList<String> list = ObservableUtils.observableList(null);
            fail("observableList(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testObservableMap()
    {
        ObservableMap<String, Double> map0 = ObservableUtils.observableMap(new HashMap<String, Double>());
        assertNotNull(map0);

        try
        {
            ObservableMap<String, Double> map = ObservableUtils.observableMap(null);
            fail("observableMap(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testObservableSet()
    {
        ObservableSet<String> set0 = ObservableUtils.observableSet(new HashSet<String>());
        assertNotNull(set0);

        try
        {
            ObservableSet<String> set = ObservableUtils.observableSet(null);
            fail("observableSet(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testObservableSortedMap()
    {
        ObservableSortedMap<String, Double> sortedMap0 = ObservableUtils.observableSortedMap(new TreeMap<String, Double>());
        assertNotNull(sortedMap0);

        try
        {
            ObservableSortedMap<String, Double> sortedMap = ObservableUtils.observableSortedMap(null);
            fail("observableSortedMap(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testObservableSortedSet()
    {
        ObservableSortedSet<String> sortedSet0 = ObservableUtils.observableSortedSet(new TreeSet<String>());
        assertNotNull(sortedSet0);

        try
        {
            ObservableSortedSet<String> sortedSet = ObservableUtils.observableSortedSet(null);
            fail("observableSortedSet(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }
}