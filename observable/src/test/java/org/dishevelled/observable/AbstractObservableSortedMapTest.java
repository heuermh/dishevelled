/*

    dsh-observable  Observable decorators for collection and map interfaces.
    Copyright (c) 2003-2013 held jointly by the individual authors.

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
package org.dishevelled.observable;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.dishevelled.observable.event.SortedMapChangeEvent;
import org.dishevelled.observable.event.SortedMapChangeListener;
import org.dishevelled.observable.event.SortedMapChangeVetoException;
import org.dishevelled.observable.event.VetoableSortedMapChangeEvent;
import org.dishevelled.observable.event.VetoableSortedMapChangeListener;

/**
 * Abstract unit test for implementations of ObservableSortedMap.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractObservableSortedMapTest
    extends TestCase
{

    /**
     * Create and return a new instance of ObservableSortedMap to test.
     *
     * @param <K> sorted map key type
     * @param <V> sorted map value type
     * @return a new instance of ObservableSortedMap to test
     */
    protected abstract <K,V> ObservableSortedMap<K,V> createObservableSortedMap();


    public void testListeners()
    {
        ObservableSortedMap<String, Double> sortedMap = createObservableSortedMap();
        Listener<String, Double> listener = new Listener<String, Double>();
        sortedMap.addSortedMapChangeListener(listener);

        assertEquals(1, sortedMap.getSortedMapChangeListenerCount());
        assertNotNull(sortedMap.getSortedMapChangeListeners());
        assertEquals(1, sortedMap.getSortedMapChangeListeners().length);
        assertEquals(listener, sortedMap.getSortedMapChangeListeners()[0]);

        sortedMap.removeSortedMapChangeListener(listener);
        assertEquals(0, sortedMap.getSortedMapChangeListenerCount());
        assertNotNull(sortedMap.getSortedMapChangeListeners());
        assertEquals(0, sortedMap.getSortedMapChangeListeners().length);

        NeverVetoListener<String, Double> neverVetoListener = new NeverVetoListener<String, Double>();
        sortedMap.addVetoableSortedMapChangeListener(neverVetoListener);

        assertEquals(1, sortedMap.getVetoableSortedMapChangeListenerCount());
        assertNotNull(sortedMap.getVetoableSortedMapChangeListeners());
        assertEquals(1, sortedMap.getVetoableSortedMapChangeListeners().length);
        assertEquals(neverVetoListener, sortedMap.getVetoableSortedMapChangeListeners()[0]);

        sortedMap.removeVetoableSortedMapChangeListener(neverVetoListener);
        assertEquals(0, sortedMap.getVetoableSortedMapChangeListenerCount());
        assertNotNull(sortedMap.getVetoableSortedMapChangeListeners());
        assertEquals(0, sortedMap.getVetoableSortedMapChangeListeners().length);
    }

    public void testFireSortedMapWillChange()
        throws Exception
    {
        ObservableSortedMap<String, Double> sortedMap = createObservableSortedMap();
        NeverVetoListener<String, Double> neverVetoListener = new NeverVetoListener<String, Double>();
        sortedMap.addVetoableSortedMapChangeListener(neverVetoListener);

        if (sortedMap instanceof AbstractObservableSortedMap)
        {
            ((AbstractObservableSortedMap) sortedMap).fireSortedMapWillChange();

            assertNotNull(neverVetoListener.getEvent());
            assertSame(sortedMap, neverVetoListener.getEvent().getObservableSortedMap());
        }
    }

    public void testFireSortedMapChanged()
    {
        ObservableSortedMap<String, Double> sortedMap = createObservableSortedMap();
        Listener<String, Double> listener = new Listener<String, Double>();
        sortedMap.addSortedMapChangeListener(listener);

        if (sortedMap instanceof AbstractObservableSortedMap)
        {
            ((AbstractObservableSortedMap) sortedMap).fireSortedMapChanged();

            assertNotNull(listener.getEvent());
            assertSame(sortedMap, listener.getEvent().getObservableSortedMap());
        }
    }

    public void testCreateObservableSortedMap()
    {
        ObservableSortedMap<String, Double> sortedMap0 = createObservableSortedMap();
        assertNotNull(sortedMap0);

        ObservableSortedMap<String, Double> sortedMap1 = createObservableSortedMap();
        assertNotNull(sortedMap1);

        assertTrue(sortedMap1 != sortedMap0);
    }

    public void testClear()
    {
        ObservableSortedMap<String, Double> sortedMap = createObservableSortedMap();
        sortedMap.put("foo", Double.valueOf(0.0d));
        sortedMap.put("bar", Double.valueOf(1.0d));
        sortedMap.put("baz", Double.valueOf(2.0d));
        Listener<String, Double> listener = new Listener<String, Double>();
        NeverVetoListener<String, Double> neverVetoListener = new NeverVetoListener<String, Double>();
        sortedMap.addSortedMapChangeListener(listener);
        sortedMap.addVetoableSortedMapChangeListener(neverVetoListener);

        assertTrue(sortedMap.containsKey("foo"));
        assertTrue(sortedMap.containsKey("bar"));
        assertTrue(sortedMap.containsKey("baz"));
        assertTrue(sortedMap.containsValue(Double.valueOf(0.0d)));
        assertTrue(sortedMap.containsValue(Double.valueOf(1.0d)));
        assertTrue(sortedMap.containsValue(Double.valueOf(2.0d)));
        assertEquals(3, sortedMap.size());

        sortedMap.clear();
        assertFalse(sortedMap.containsKey("foo"));
        assertFalse(sortedMap.containsKey("bar"));
        assertFalse(sortedMap.containsKey("baz"));
        assertFalse(sortedMap.containsValue(Double.valueOf(0.0d)));
        assertFalse(sortedMap.containsValue(Double.valueOf(1.0d)));
        assertFalse(sortedMap.containsValue(Double.valueOf(2.0d)));
        assertEquals(0, sortedMap.size());

        assertNotNull(listener.getEvent());
        assertSame(sortedMap, listener.getEvent().getObservableSortedMap()); // assertEquals failed!

        assertNotNull(neverVetoListener.getEvent());
        assertSame(sortedMap, neverVetoListener.getEvent().getObservableSortedMap());
    }

    public void testClearVeto()
    {
        ObservableSortedMap<String, Double> sortedMap = createObservableSortedMap();
        sortedMap.put("foo", Double.valueOf(0.0d));
        sortedMap.put("bar", Double.valueOf(1.0d));
        sortedMap.put("baz", Double.valueOf(2.0d));
        assertTrue(sortedMap.containsKey("foo"));
        assertTrue(sortedMap.containsKey("bar"));
        assertTrue(sortedMap.containsKey("baz"));
        assertTrue(sortedMap.containsValue(Double.valueOf(0.0d)));
        assertTrue(sortedMap.containsValue(Double.valueOf(1.0d)));
        assertTrue(sortedMap.containsValue(Double.valueOf(2.0d)));
        assertEquals(3, sortedMap.size());

        AlwaysVetoListener<String, Double> alwaysVetoListener = new AlwaysVetoListener<String, Double>();
        sortedMap.addVetoableSortedMapChangeListener(alwaysVetoListener);

        sortedMap.clear();
        assertTrue(sortedMap.containsKey("foo"));
        assertTrue(sortedMap.containsKey("bar"));
        assertTrue(sortedMap.containsKey("baz"));
        assertTrue(sortedMap.containsValue(Double.valueOf(0.0d)));
        assertTrue(sortedMap.containsValue(Double.valueOf(1.0d)));
        assertTrue(sortedMap.containsValue(Double.valueOf(2.0d)));
        assertEquals(3, sortedMap.size());
    }

    public void testPut()
    {
        ObservableSortedMap<String, Double> sortedMap = createObservableSortedMap();
        Listener<String, Double> listener = new Listener<String, Double>();
        NeverVetoListener<String, Double> neverVetoListener = new NeverVetoListener<String, Double>();
        sortedMap.addSortedMapChangeListener(listener);
        sortedMap.addVetoableSortedMapChangeListener(neverVetoListener);

        assertEquals(null, sortedMap.put("foo", Double.valueOf(0.0d)));
        assertTrue(sortedMap.containsKey("foo"));
        assertTrue(sortedMap.containsValue(Double.valueOf(0.0d)));

        assertNotNull(listener.getEvent());
        assertSame(sortedMap, listener.getEvent().getObservableSortedMap());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(sortedMap, neverVetoListener.getEvent().getObservableSortedMap());
    }

    public void testPutVeto()
    {
        ObservableSortedMap<String, Double> sortedMap = createObservableSortedMap();
        assertTrue(sortedMap.isEmpty());

        AlwaysVetoListener<String, Double> alwaysVetoListener = new AlwaysVetoListener<String, Double>();
        sortedMap.addVetoableSortedMapChangeListener(alwaysVetoListener);

        sortedMap.put("foo", Double.valueOf(0.0d));
        assertTrue(sortedMap.isEmpty());
    }

    public void testPutAll()
    {
        ObservableSortedMap<String, Double> sortedMap = createObservableSortedMap();
        Listener<String, Double> listener = new Listener<String, Double>();
        NeverVetoListener<String, Double> neverVetoListener = new NeverVetoListener<String, Double>();
        sortedMap.addSortedMapChangeListener(listener);
        sortedMap.addVetoableSortedMapChangeListener(neverVetoListener);

        Map<String, Double> addl = new HashMap<String, Double>();
        addl.put("foo", Double.valueOf(0.0d));
        addl.put("bar", Double.valueOf(1.0d));
        addl.put("baz", Double.valueOf(2.0d));

        sortedMap.putAll(addl);
        assertTrue(sortedMap.containsKey("foo"));
        assertTrue(sortedMap.containsKey("bar"));
        assertTrue(sortedMap.containsKey("baz"));
        assertTrue(sortedMap.containsValue(Double.valueOf(0.0d)));
        assertTrue(sortedMap.containsValue(Double.valueOf(1.0d)));
        assertTrue(sortedMap.containsValue(Double.valueOf(2.0d)));
        assertEquals(3, sortedMap.size());

        assertNotNull(listener.getEvent());
        assertSame(sortedMap, listener.getEvent().getObservableSortedMap());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(sortedMap, neverVetoListener.getEvent().getObservableSortedMap());
    }

    public void testPutAllVeto()
    {
        ObservableSortedMap<String, Double> sortedMap = createObservableSortedMap();
        assertTrue(sortedMap.isEmpty());

        AlwaysVetoListener<String, Double> alwaysVetoListener = new AlwaysVetoListener<String, Double>();
        sortedMap.addVetoableSortedMapChangeListener(alwaysVetoListener);

        Map<String, Double> addl = new HashMap<String, Double>();
        addl.put("foo", Double.valueOf(0.0d));
        addl.put("bar", Double.valueOf(1.0d));
        addl.put("baz", Double.valueOf(2.0d));

        sortedMap.putAll(addl);
        assertTrue(sortedMap.isEmpty());
    }

    public void testRemove()
    {
        ObservableSortedMap<String, Double> sortedMap = createObservableSortedMap();
        Listener<String, Double> listener = new Listener<String, Double>();
        NeverVetoListener<String, Double> neverVetoListener = new NeverVetoListener<String, Double>();
        sortedMap.addSortedMapChangeListener(listener);
        sortedMap.addVetoableSortedMapChangeListener(neverVetoListener);
        sortedMap.put("foo", Double.valueOf(0.0d));
        sortedMap.put("bar", Double.valueOf(1.0d));
        sortedMap.put("baz", Double.valueOf(2.0d));
        assertEquals(3, sortedMap.size());

        assertEquals(Double.valueOf(0.0d), sortedMap.remove("foo"));
        assertFalse(sortedMap.containsKey("foo"));
        assertEquals(2, sortedMap.size());

        assertNotNull(listener.getEvent());
        assertSame(sortedMap, listener.getEvent().getObservableSortedMap());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(sortedMap, neverVetoListener.getEvent().getObservableSortedMap());
    }

    public void testRemoveVeto()
    {
        ObservableSortedMap<String, Double> sortedMap = createObservableSortedMap();
        sortedMap.put("foo", Double.valueOf(0.0d));
        sortedMap.put("bar", Double.valueOf(1.0d));
        sortedMap.put("baz", Double.valueOf(2.0d));
        assertEquals(3, sortedMap.size());

        AlwaysVetoListener<String, Double> alwaysVetoListener = new AlwaysVetoListener<String, Double>();
        sortedMap.addVetoableSortedMapChangeListener(alwaysVetoListener);

        assertEquals(null, sortedMap.remove("foo"));
        assertTrue(sortedMap.containsKey("foo"));
        assertEquals(3, sortedMap.size());
    }

    // TODO:  rest of map views, plus head/tail/subMap, subMap views, nested subMap, nested subMap views, ...


    /**
     * Listener.
     */
    protected class Listener<K, V>
        implements SortedMapChangeListener<K, V> {

        /** Last event heard, if any. */
        private SortedMapChangeEvent<K, V> event;


        /** {@inheritDoc} */
        public void sortedMapChanged(final SortedMapChangeEvent<K, V> event)
        {
            this.event = event;
        }

        /**
         * Return the last heard event, if any.
         *
         * @return the last heard event, if any
         */
        SortedMapChangeEvent<K, V> getEvent()
        {
            return event;
        }
    }

    /**
     * Always veto listener.
     */
    protected class AlwaysVetoListener<K, V>
        implements VetoableSortedMapChangeListener<K, V>
    {

        /** {@inheritDoc} */
        public void sortedMapWillChange(final VetoableSortedMapChangeEvent<K, V> event)
            throws SortedMapChangeVetoException
        {
            throw new SortedMapChangeVetoException();
        }
    }

    /**
     * Never veto listener.
     */
    protected class NeverVetoListener<K, V>
        implements VetoableSortedMapChangeListener<K, V>
    {
        /** Last event heard, if any. */
        private VetoableSortedMapChangeEvent<K, V> event;


        /** {@inheritDoc} */
        public void sortedMapWillChange(final VetoableSortedMapChangeEvent<K, V> event)
            throws SortedMapChangeVetoException
        {
            this.event = event;
        }

        /**
         * Return the last heard event, if any.
         *
         * @return the last heard event, if any
         */
        VetoableSortedMapChangeEvent<K, V> getEvent()
        {
            return event;
        }
    }
}