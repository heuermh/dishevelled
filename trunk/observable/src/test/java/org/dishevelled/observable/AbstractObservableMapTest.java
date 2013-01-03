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

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import org.dishevelled.observable.event.MapChangeEvent;
import org.dishevelled.observable.event.MapChangeListener;
import org.dishevelled.observable.event.MapChangeVetoException;
import org.dishevelled.observable.event.VetoableMapChangeEvent;
import org.dishevelled.observable.event.VetoableMapChangeListener;

/**
 * Abstract unit test for implementations of ObservableMap.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractObservableMapTest
    extends TestCase
{

    /**
     * Create and return a new instance of ObservableMap to test.
     *
     * @param <K> map key type
     * @param <V> map value type
     * @return a new instance of ObservableMap to test
     */
    protected abstract <K,V> ObservableMap<K,V> createObservableMap();


    public void testCreateObservableMap()
    {
        ObservableMap<String, Double> map0 = createObservableMap();
        assertNotNull(map0);

        ObservableMap<String, Double> map1 = createObservableMap();
        assertNotNull(map1);

        assertTrue(map1 != map0);
    }

    public void testListeners()
    {
        ObservableMap<String, Double> map = createObservableMap();
        Listener<String, Double> listener = new Listener<String, Double>();
        map.addMapChangeListener(listener);

        assertEquals(1, map.getMapChangeListenerCount());
        assertNotNull(map.getMapChangeListeners());
        assertEquals(1, map.getMapChangeListeners().length);
        assertEquals(listener, map.getMapChangeListeners()[0]);

        map.removeMapChangeListener(listener);
        assertEquals(0, map.getMapChangeListenerCount());
        assertNotNull(map.getMapChangeListeners());
        assertEquals(0, map.getMapChangeListeners().length);

        NeverVetoListener<String, Double> neverVetoListener = new NeverVetoListener<String, Double>();
        map.addVetoableMapChangeListener(neverVetoListener);

        assertEquals(1, map.getVetoableMapChangeListenerCount());
        assertNotNull(map.getVetoableMapChangeListeners());
        assertEquals(1, map.getVetoableMapChangeListeners().length);
        assertEquals(neverVetoListener, map.getVetoableMapChangeListeners()[0]);

        map.removeVetoableMapChangeListener(neverVetoListener);
        assertEquals(0, map.getVetoableMapChangeListenerCount());
        assertNotNull(map.getVetoableMapChangeListeners());
        assertEquals(0, map.getVetoableMapChangeListeners().length);
    }

    public void testFireMapWillChange()
        throws Exception
    {
        ObservableMap<String, Double> map = createObservableMap();
        NeverVetoListener<String, Double> neverVetoListener = new NeverVetoListener<String, Double>();
        map.addVetoableMapChangeListener(neverVetoListener);

        if (map instanceof AbstractObservableMap)
        {
            ((AbstractObservableMap) map).fireMapWillChange();

            assertNotNull(neverVetoListener.getEvent());
            assertSame(map, neverVetoListener.getEvent().getObservableMap());
        }
    }

    public void testFireMapChanged()
    {
        ObservableMap<String, Double> map = createObservableMap();
        Listener<String, Double> listener = new Listener<String, Double>();
        map.addMapChangeListener(listener);

        if (map instanceof AbstractObservableMap)
        {
            ((AbstractObservableMap) map).fireMapChanged();

            assertNotNull(listener.getEvent());
            assertSame(map, listener.getEvent().getObservableMap());
        }
    }

    public void testClear()
    {
        ObservableMap<String, Double> map = createObservableMap();
        map.put("foo", Double.valueOf(0.0d));
        map.put("bar", Double.valueOf(1.0d));
        map.put("baz", Double.valueOf(2.0d));
        Listener<String, Double> listener = new Listener<String, Double>();
        NeverVetoListener<String, Double> neverVetoListener = new NeverVetoListener<String, Double>();
        map.addMapChangeListener(listener);
        map.addVetoableMapChangeListener(neverVetoListener);

        assertTrue(map.containsKey("foo"));
        assertTrue(map.containsKey("bar"));
        assertTrue(map.containsKey("baz"));
        assertTrue(map.containsValue(Double.valueOf(0.0d)));
        assertTrue(map.containsValue(Double.valueOf(1.0d)));
        assertTrue(map.containsValue(Double.valueOf(2.0d)));
        assertEquals(3, map.size());

        map.clear();
        assertFalse(map.containsKey("foo"));
        assertFalse(map.containsKey("bar"));
        assertFalse(map.containsKey("baz"));
        assertFalse(map.containsValue(Double.valueOf(0.0d)));
        assertFalse(map.containsValue(Double.valueOf(1.0d)));
        assertFalse(map.containsValue(Double.valueOf(2.0d)));
        assertEquals(0, map.size());

        assertNotNull(listener.getEvent());
        assertSame(map, listener.getEvent().getObservableMap()); // assertEquals failed!

        assertNotNull(neverVetoListener.getEvent());
        assertSame(map, neverVetoListener.getEvent().getObservableMap());
    }

    public void testClearVeto()
    {
        ObservableMap<String, Double> map = createObservableMap();
        map.put("foo", Double.valueOf(0.0d));
        map.put("bar", Double.valueOf(1.0d));
        map.put("baz", Double.valueOf(2.0d));
        assertTrue(map.containsKey("foo"));
        assertTrue(map.containsKey("bar"));
        assertTrue(map.containsKey("baz"));
        assertTrue(map.containsValue(Double.valueOf(0.0d)));
        assertTrue(map.containsValue(Double.valueOf(1.0d)));
        assertTrue(map.containsValue(Double.valueOf(2.0d)));
        assertEquals(3, map.size());

        AlwaysVetoListener<String, Double> alwaysVetoListener = new AlwaysVetoListener<String, Double>();
        map.addVetoableMapChangeListener(alwaysVetoListener);

        map.clear();
        assertTrue(map.containsKey("foo"));
        assertTrue(map.containsKey("bar"));
        assertTrue(map.containsKey("baz"));
        assertTrue(map.containsValue(Double.valueOf(0.0d)));
        assertTrue(map.containsValue(Double.valueOf(1.0d)));
        assertTrue(map.containsValue(Double.valueOf(2.0d)));
        assertEquals(3, map.size());
    }

    public void testPut()
    {
        ObservableMap<String, Double> map = createObservableMap();
        Listener<String, Double> listener = new Listener<String, Double>();
        NeverVetoListener<String, Double> neverVetoListener = new NeverVetoListener<String, Double>();
        map.addMapChangeListener(listener);
        map.addVetoableMapChangeListener(neverVetoListener);

        assertEquals(null, map.put("foo", Double.valueOf(0.0d)));
        assertTrue(map.containsKey("foo"));
        assertTrue(map.containsValue(Double.valueOf(0.0d)));

        assertNotNull(listener.getEvent());
        assertSame(map, listener.getEvent().getObservableMap());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(map, neverVetoListener.getEvent().getObservableMap());
    }

    public void testPutVeto()
    {
        ObservableMap<String, Double> map = createObservableMap();
        assertTrue(map.isEmpty());

        AlwaysVetoListener<String, Double> alwaysVetoListener = new AlwaysVetoListener<String, Double>();
        map.addVetoableMapChangeListener(alwaysVetoListener);

        map.put("foo", Double.valueOf(0.0d));
        assertTrue(map.isEmpty());
    }

    public void testPutAll()
    {
        ObservableMap<String, Double> map = createObservableMap();
        Listener<String, Double> listener = new Listener<String, Double>();
        NeverVetoListener<String, Double> neverVetoListener = new NeverVetoListener<String, Double>();
        map.addMapChangeListener(listener);
        map.addVetoableMapChangeListener(neverVetoListener);

        Map<String, Double> addl = new HashMap<String, Double>();
        addl.put("foo", Double.valueOf(0.0d));
        addl.put("bar", Double.valueOf(1.0d));
        addl.put("baz", Double.valueOf(2.0d));

        map.putAll(addl);
        assertTrue(map.containsKey("foo"));
        assertTrue(map.containsKey("bar"));
        assertTrue(map.containsKey("baz"));
        assertTrue(map.containsValue(Double.valueOf(0.0d)));
        assertTrue(map.containsValue(Double.valueOf(1.0d)));
        assertTrue(map.containsValue(Double.valueOf(2.0d)));
        assertEquals(3, map.size());

        assertNotNull(listener.getEvent());
        assertSame(map, listener.getEvent().getObservableMap());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(map, neverVetoListener.getEvent().getObservableMap());
    }

    public void testPutAllVeto()
    {
        ObservableMap<String, Double> map = createObservableMap();
        assertTrue(map.isEmpty());

        AlwaysVetoListener<String, Double> alwaysVetoListener = new AlwaysVetoListener<String, Double>();
        map.addVetoableMapChangeListener(alwaysVetoListener);

        Map<String, Double> addl = new HashMap<String, Double>();
        addl.put("foo", Double.valueOf(0.0d));
        addl.put("bar", Double.valueOf(1.0d));
        addl.put("baz", Double.valueOf(2.0d));

        map.putAll(addl);
        assertTrue(map.isEmpty());
    }

    public void testRemove()
    {
        ObservableMap<String, Double> map = createObservableMap();
        Listener<String, Double> listener = new Listener<String, Double>();
        NeverVetoListener<String, Double> neverVetoListener = new NeverVetoListener<String, Double>();
        map.addMapChangeListener(listener);
        map.addVetoableMapChangeListener(neverVetoListener);
        map.put("foo", Double.valueOf(0.0d));
        map.put("bar", Double.valueOf(1.0d));
        map.put("baz", Double.valueOf(2.0d));
        assertEquals(3, map.size());

        assertEquals(Double.valueOf(0.0d), map.remove("foo"));
        assertFalse(map.containsKey("foo"));
        assertEquals(2, map.size());

        assertNotNull(listener.getEvent());
        assertSame(map, listener.getEvent().getObservableMap());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(map, neverVetoListener.getEvent().getObservableMap());
    }

    public void testRemoveVeto()
    {
        ObservableMap<String, Double> map = createObservableMap();
        map.put("foo", Double.valueOf(0.0d));
        map.put("bar", Double.valueOf(1.0d));
        map.put("baz", Double.valueOf(2.0d));
        assertEquals(3, map.size());

        AlwaysVetoListener<String, Double> alwaysVetoListener = new AlwaysVetoListener<String, Double>();
        map.addVetoableMapChangeListener(alwaysVetoListener);

        assertEquals(null, map.remove("foo"));
        assertTrue(map.containsKey("foo"));
        assertEquals(3, map.size());
    }

    public void testValuesRemove()
    {
        ObservableMap<String, Double> map = createObservableMap();
        map.put("foo", Double.valueOf(0.0d));
        map.put("bar", Double.valueOf(1.0d));
        map.put("baz", Double.valueOf(2.0d));

        Listener<String, Double> listener = new Listener<String, Double>();
        NeverVetoListener<String, Double> neverVetoListener = new NeverVetoListener<String, Double>();
        map.addMapChangeListener(listener);
        map.addVetoableMapChangeListener(neverVetoListener);

        Collection<Double> values = map.values();
        assertTrue(values.remove(Double.valueOf(2.0d)));
        assertFalse(map.containsValue(Double.valueOf(2.0d)));
        assertEquals(2, map.size());

        assertNotNull(listener.getEvent());
        assertSame(map, listener.getEvent().getObservableMap());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(map, neverVetoListener.getEvent().getObservableMap());
    }

    public void testValuesRemoveVeto()
    {
        ObservableMap<String, Double> map = createObservableMap();
        map.put("foo", Double.valueOf(0.0d));
        map.put("bar", Double.valueOf(1.0d));
        map.put("baz", Double.valueOf(2.0d));

        AlwaysVetoListener<String, Double> alwaysVetoListener = new AlwaysVetoListener<String, Double>();
        map.addVetoableMapChangeListener(alwaysVetoListener);

        Collection<Double> values = map.values();
        assertFalse(values.remove(Double.valueOf(2.0d)));
        assertTrue(map.containsValue(Double.valueOf(2.0d)));
        assertEquals(3, map.size());
    }

    public void testValuesRemoveAll()
    {
        ObservableMap<String, Double> map = createObservableMap();
        map.put("foo", Double.valueOf(0.0d));
        map.put("bar", Double.valueOf(1.0d));
        map.put("baz", Double.valueOf(2.0d));

        Listener<String, Double> listener = new Listener<String, Double>();
        NeverVetoListener<String, Double> neverVetoListener = new NeverVetoListener<String, Double>();
        map.addMapChangeListener(listener);
        map.addVetoableMapChangeListener(neverVetoListener);

        Collection<Double> values = map.values();
        Collection<Double> doubles = Arrays.asList(new Double[] { Double.valueOf(0.0d), Double.valueOf(1.0d) });
        assertTrue(values.removeAll(doubles));
        assertFalse(values.contains(Double.valueOf(0.0d)));
        assertFalse(values.contains(Double.valueOf(1.0d)));
        assertTrue(values.contains(Double.valueOf(2.0d)));
        assertFalse(map.containsKey("foo"));
        assertFalse(map.containsKey("bar"));
        assertTrue(map.containsKey("baz"));

        assertNotNull(listener.getEvent());
        assertSame(map, listener.getEvent().getObservableMap());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(map, neverVetoListener.getEvent().getObservableMap());
    }

    public void testValuesRemoveAllVeto()
    {
        ObservableMap<String, Double> map = createObservableMap();
        map.put("foo", Double.valueOf(0.0d));
        map.put("bar", Double.valueOf(1.0d));
        map.put("baz", Double.valueOf(2.0d));

        AlwaysVetoListener<String, Double> alwaysVetoListener = new AlwaysVetoListener<String, Double>();
        map.addVetoableMapChangeListener(alwaysVetoListener);

        Collection<Double> values = map.values();
        Collection<Double> doubles = Arrays.asList(new Double[] { Double.valueOf(0.0d), Double.valueOf(1.0d) });
        assertFalse(values.removeAll(doubles));
        assertTrue(values.contains(Double.valueOf(0.0d)));
        assertTrue(values.contains(Double.valueOf(1.0d)));
        assertTrue(values.contains(Double.valueOf(2.0d)));
        assertTrue(map.containsKey("foo"));
        assertTrue(map.containsKey("bar"));
        assertTrue(map.containsKey("baz"));
    }

    public void testValuesRetainAll()
    {
        ObservableMap<String, Double> map = createObservableMap();
        map.put("foo", Double.valueOf(0.0d));
        map.put("bar", Double.valueOf(1.0d));
        map.put("baz", Double.valueOf(2.0d));

        Listener<String, Double> listener = new Listener<String, Double>();
        NeverVetoListener<String, Double> neverVetoListener = new NeverVetoListener<String, Double>();
        map.addMapChangeListener(listener);
        map.addVetoableMapChangeListener(neverVetoListener);

        Collection<Double> values = map.values();
        Collection<Double> doubles = Arrays.asList(new Double[] { Double.valueOf(0.0d), Double.valueOf(1.0d) });
        assertTrue(values.retainAll(doubles));
        assertTrue(values.contains(Double.valueOf(0.0d)));
        assertTrue(values.contains(Double.valueOf(1.0d)));
        assertFalse(values.contains(Double.valueOf(2.0d)));
        assertTrue(map.containsKey("foo"));
        assertTrue(map.containsKey("bar"));
        assertFalse(map.containsKey("baz"));

        assertNotNull(listener.getEvent());
        assertSame(map, listener.getEvent().getObservableMap());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(map, neverVetoListener.getEvent().getObservableMap());
    }

    public void testValuesRetainAllVeto()
    {
        ObservableMap<String, Double> map = createObservableMap();
        map.put("foo", Double.valueOf(0.0d));
        map.put("bar", Double.valueOf(1.0d));
        map.put("baz", Double.valueOf(2.0d));

        AlwaysVetoListener<String, Double> alwaysVetoListener = new AlwaysVetoListener<String, Double>();
        map.addVetoableMapChangeListener(alwaysVetoListener);

        Collection<Double> values = map.values();
        Collection<Double> doubles = Arrays.asList(new Double[] { Double.valueOf(0.0d), Double.valueOf(1.0d) });
        assertFalse(values.retainAll(doubles));
        assertTrue(values.contains(Double.valueOf(0.0d)));
        assertTrue(values.contains(Double.valueOf(1.0d)));
        assertTrue(values.contains(Double.valueOf(2.0d)));
        assertTrue(map.containsKey("foo"));
        assertTrue(map.containsKey("bar"));
        assertTrue(map.containsKey("baz"));
    }

    public void testValuesClear()
    {
        ObservableMap<String, Double> map = createObservableMap();
        map.put("foo", Double.valueOf(0.0d));
        map.put("bar", Double.valueOf(1.0d));
        map.put("baz", Double.valueOf(2.0d));

        Listener<String, Double> listener = new Listener<String, Double>();
        NeverVetoListener<String, Double> neverVetoListener = new NeverVetoListener<String, Double>();
        map.addMapChangeListener(listener);
        map.addVetoableMapChangeListener(neverVetoListener);

        Collection<Double> values = map.values();
        values.clear();
        assertTrue(values.isEmpty());
        assertTrue(map.isEmpty());

        assertNotNull(listener.getEvent());
        assertSame(map, listener.getEvent().getObservableMap());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(map, neverVetoListener.getEvent().getObservableMap());
    }

    public void testValuesClearVeto()
    {
        ObservableMap<String, Double> map = createObservableMap();
        map.put("foo", Double.valueOf(0.0d));
        map.put("bar", Double.valueOf(1.0d));
        map.put("baz", Double.valueOf(2.0d));

        AlwaysVetoListener<String, Double> alwaysVetoListener = new AlwaysVetoListener<String, Double>();
        map.addVetoableMapChangeListener(alwaysVetoListener);

        Collection<Double> values = map.values();
        values.clear();
        assertEquals(3, values.size());
        assertEquals(3, map.size());
    }

    public void testValuesIteratorRemove()
    {
        ObservableMap<String, Double> map = createObservableMap();
        map.put("foo", Double.valueOf(0.0d));
        map.put("bar", Double.valueOf(1.0d));
        map.put("baz", Double.valueOf(2.0d));

        Listener<String, Double> listener = new Listener<String, Double>();
        NeverVetoListener<String, Double> neverVetoListener = new NeverVetoListener<String, Double>();
        map.addMapChangeListener(listener);
        map.addVetoableMapChangeListener(neverVetoListener);

        Collection<Double> values = map.values();
        Iterator<Double> iterator = values.iterator();
        Double d = iterator.next();
        iterator.remove();
        assertFalse(values.contains(d));
        assertFalse(map.containsValue(d));
        assertEquals(2, values.size());
        assertEquals(2, map.size());

        assertNotNull(listener.getEvent());
        assertSame(map, listener.getEvent().getObservableMap());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(map, neverVetoListener.getEvent().getObservableMap());
    }

    public void testValuesIteratorRemoveVeto()
    {
        ObservableMap<String, Double> map = createObservableMap();
        map.put("foo", Double.valueOf(0.0d));
        map.put("bar", Double.valueOf(1.0d));
        map.put("baz", Double.valueOf(2.0d));

        AlwaysVetoListener<String, Double> alwaysVetoListener = new AlwaysVetoListener<String, Double>();
        map.addVetoableMapChangeListener(alwaysVetoListener);

        Collection<Double> values = map.values();
        Iterator<Double> iterator = values.iterator();
        Double d = iterator.next();
        iterator.remove();
        assertTrue(values.contains(d));
        assertTrue(map.containsValue(d));
        assertEquals(3, values.size());
        assertEquals(3, map.size());
    }

    public void testEntrySetRemove()
    {
        ObservableMap<String, Double> map = createObservableMap();
        map.put("foo", Double.valueOf(0.0d));
        map.put("bar", Double.valueOf(1.0d));
        map.put("baz", Double.valueOf(2.0d));

        Listener<String, Double> listener = new Listener<String, Double>();
        NeverVetoListener<String, Double> neverVetoListener = new NeverVetoListener<String, Double>();
        map.addMapChangeListener(listener);
        map.addVetoableMapChangeListener(neverVetoListener);

        Set<Map.Entry<String, Double>> entrySet = map.entrySet();
        Map.Entry<String, Double> entry = entrySet.iterator().next();
        String key = entry.getKey();
        Double value = entry.getValue();
        assertTrue(entrySet.remove(entry));
        assertFalse(map.containsKey(key));
        assertFalse(map.containsValue(value));

        assertNotNull(listener.getEvent());
        assertSame(map, listener.getEvent().getObservableMap());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(map, neverVetoListener.getEvent().getObservableMap());
    }

    public void testEntrySetRemoveVeto()
    {
        ObservableMap<String, Double> map = createObservableMap();
        map.put("foo", Double.valueOf(0.0d));
        map.put("bar", Double.valueOf(1.0d));
        map.put("baz", Double.valueOf(2.0d));

        AlwaysVetoListener<String, Double> alwaysVetoListener = new AlwaysVetoListener<String, Double>();
        map.addVetoableMapChangeListener(alwaysVetoListener);

        Set<Map.Entry<String, Double>> entrySet = map.entrySet();
        Map.Entry<String, Double> entry = entrySet.iterator().next();
        String key = entry.getKey();
        Double value = entry.getValue();
        assertFalse(entrySet.remove(entry));
        assertTrue(map.containsKey(key));
        assertTrue(map.containsValue(value));
    }

    public void testEntrySetRemoveAll()
    {
        ObservableMap<String, Double> map = createObservableMap();
        map.put("foo", Double.valueOf(0.0d));
        map.put("bar", Double.valueOf(1.0d));
        map.put("baz", Double.valueOf(2.0d));

        Listener<String, Double> listener = new Listener<String, Double>();
        NeverVetoListener<String, Double> neverVetoListener = new NeverVetoListener<String, Double>();
        map.addMapChangeListener(listener);
        map.addVetoableMapChangeListener(neverVetoListener);

        Set<Map.Entry<String, Double>> entrySet = map.entrySet();
        Set<Map.Entry<String, Double>> entries = new HashSet<Map.Entry<String, Double>>();
        Iterator<Map.Entry<String, Double>> it = entrySet.iterator();
        Map.Entry<String, Double> entry0 = it.next();
        String key0 = entry0.getKey();
        Double value0 = entry0.getValue();
        entries.add(entry0);
        Map.Entry<String, Double> entry1 = it.next();
        String key1 = entry1.getKey();
        Double value1 = entry1.getValue();
        entries.add(entry1);

        assertTrue(entrySet.removeAll(entries));
        assertFalse(map.containsKey(key0));
        assertFalse(map.containsValue(value0));
        assertFalse(map.containsKey(key1));
        assertFalse(map.containsValue(value1));

        assertNotNull(listener.getEvent());
        assertSame(map, listener.getEvent().getObservableMap());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(map, neverVetoListener.getEvent().getObservableMap());
    }

    public void testEntrySetRemoveAllVeto()
    {
        ObservableMap<String, Double> map = createObservableMap();
        map.put("foo", Double.valueOf(0.0d));
        map.put("bar", Double.valueOf(1.0d));
        map.put("baz", Double.valueOf(2.0d));

        AlwaysVetoListener<String, Double> alwaysVetoListener = new AlwaysVetoListener<String, Double>();
        map.addVetoableMapChangeListener(alwaysVetoListener);

        Set<Map.Entry<String, Double>> entrySet = map.entrySet();
        Set<Map.Entry<String, Double>> entries = new HashSet<Map.Entry<String, Double>>();
        Iterator<Map.Entry<String, Double>> it = entrySet.iterator();
        Map.Entry<String, Double> entry0 = it.next();
        String key0 = entry0.getKey();
        Double value0 = entry0.getValue();
        entries.add(entry0);
        Map.Entry<String, Double> entry1 = it.next();
        String key1 = entry1.getKey();
        Double value1 = entry1.getValue();
        entries.add(entry1);

        assertFalse(entrySet.removeAll(entries));
        assertTrue(map.containsKey(key0));
        assertTrue(map.containsValue(value0));
        assertTrue(map.containsKey(key1));
        assertTrue(map.containsValue(value1));
    }

    public void testEntrySetRetainAll()
    {
        ObservableMap<String, Double> map = createObservableMap();
        map.put("foo", Double.valueOf(0.0d));
        map.put("bar", Double.valueOf(1.0d));
        map.put("baz", Double.valueOf(2.0d));

        Listener<String, Double> listener = new Listener<String, Double>();
        NeverVetoListener<String, Double> neverVetoListener = new NeverVetoListener<String, Double>();
        map.addMapChangeListener(listener);
        map.addVetoableMapChangeListener(neverVetoListener);

        Set<Map.Entry<String, Double>> entrySet = map.entrySet();
        Set<Map.Entry<String, Double>> entries = new HashSet<Map.Entry<String, Double>>();
        Iterator<Map.Entry<String, Double>> it = entrySet.iterator();
        Map.Entry<String, Double> entry0 = it.next();
        String key0 = entry0.getKey();
        Double value0 = entry0.getValue();
        entries.add(entry0);
        Map.Entry<String, Double> entry1 = it.next();
        String key1 = entry1.getKey();
        Double value1 = entry1.getValue();
        entries.add(entry1);
        Map.Entry<String, Double> entry2 = it.next();
        String key2 = entry2.getKey();
        Double value2 = entry2.getValue();

        assertTrue(entrySet.retainAll(entries));
        assertTrue(map.containsKey(key0));
        assertTrue(map.containsValue(value0));
        assertTrue(map.containsKey(key1));
        assertTrue(map.containsValue(value1));
        assertFalse(map.containsKey(key2));
        assertFalse(map.containsValue(value2));

        assertNotNull(listener.getEvent());
        assertSame(map, listener.getEvent().getObservableMap());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(map, neverVetoListener.getEvent().getObservableMap());
    }

    public void testEntrySetRetainAllVeto()
    {
        ObservableMap<String, Double> map = createObservableMap();
        map.put("foo", Double.valueOf(0.0d));
        map.put("bar", Double.valueOf(1.0d));
        map.put("baz", Double.valueOf(2.0d));

        AlwaysVetoListener<String, Double> alwaysVetoListener = new AlwaysVetoListener<String, Double>();
        map.addVetoableMapChangeListener(alwaysVetoListener);

        Set<Map.Entry<String, Double>> entrySet = map.entrySet();
        Set<Map.Entry<String, Double>> entries = new HashSet<Map.Entry<String, Double>>();
        Iterator<Map.Entry<String, Double>> it = entrySet.iterator();
        Map.Entry<String, Double> entry0 = it.next();
        String key0 = entry0.getKey();
        Double value0 = entry0.getValue();
        entries.add(entry0);
        Map.Entry<String, Double> entry1 = it.next();
        String key1 = entry1.getKey();
        Double value1 = entry1.getValue();
        entries.add(entry1);
        Map.Entry<String, Double> entry2 = it.next();
        String key2 = entry2.getKey();
        Double value2 = entry2.getValue();

        assertFalse(entrySet.retainAll(entries));
        assertTrue(map.containsKey(key0));
        assertTrue(map.containsValue(value0));
        assertTrue(map.containsKey(key1));
        assertTrue(map.containsValue(value1));
        assertTrue(map.containsKey(key2));
        assertTrue(map.containsValue(value2));
    }

    public void testEntrySetClear()
    {
        ObservableMap<String, Double> map = createObservableMap();
        map.put("foo", Double.valueOf(0.0d));
        map.put("bar", Double.valueOf(1.0d));
        map.put("baz", Double.valueOf(2.0d));

        Listener<String, Double> listener = new Listener<String, Double>();
        NeverVetoListener<String, Double> neverVetoListener = new NeverVetoListener<String, Double>();
        map.addMapChangeListener(listener);
        map.addVetoableMapChangeListener(neverVetoListener);

        Set<Map.Entry<String, Double>> entrySet = map.entrySet();
        entrySet.clear();
        assertTrue(map.isEmpty());

        assertNotNull(listener.getEvent());
        assertSame(map, listener.getEvent().getObservableMap());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(map, neverVetoListener.getEvent().getObservableMap());
    }

    public void testEntrySetClearVeto()
    {
        ObservableMap<String, Double> map = createObservableMap();
        map.put("foo", Double.valueOf(0.0d));
        map.put("bar", Double.valueOf(1.0d));
        map.put("baz", Double.valueOf(2.0d));

        AlwaysVetoListener<String, Double> alwaysVetoListener = new AlwaysVetoListener<String, Double>();
        map.addVetoableMapChangeListener(alwaysVetoListener);

        Set<Map.Entry<String, Double>> entrySet = map.entrySet();
        entrySet.clear();
        assertFalse(map.isEmpty());
        assertEquals(3, map.size());
    }

    public void testEntrySetIteratorRemove()
    {
        ObservableMap<String, Double> map = createObservableMap();
        map.put("foo", Double.valueOf(0.0d));
        map.put("bar", Double.valueOf(1.0d));
        map.put("baz", Double.valueOf(2.0d));

        Listener<String, Double> listener = new Listener<String, Double>();
        NeverVetoListener<String, Double> neverVetoListener = new NeverVetoListener<String, Double>();
        map.addMapChangeListener(listener);
        map.addVetoableMapChangeListener(neverVetoListener);

        Set<Map.Entry<String, Double>> entrySet = map.entrySet();
        Iterator<Map.Entry<String, Double>> it = entrySet.iterator();
        Map.Entry<String, Double> entry = it.next();
        String key = entry.getKey();
        Double value = entry.getValue();

        it.remove();
        assertEquals(2, map.size());
        assertFalse(map.containsKey(key));
        assertFalse(map.containsValue(value));

        assertNotNull(listener.getEvent());
        assertSame(map, listener.getEvent().getObservableMap());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(map, neverVetoListener.getEvent().getObservableMap());
    }

    public void testEntrySetIteratorRemoveVeto()
    {
        ObservableMap<String, Double> map = createObservableMap();
        map.put("foo", Double.valueOf(0.0d));
        map.put("bar", Double.valueOf(1.0d));
        map.put("baz", Double.valueOf(2.0d));

        AlwaysVetoListener<String, Double> alwaysVetoListener = new AlwaysVetoListener<String, Double>();
        map.addVetoableMapChangeListener(alwaysVetoListener);

        Set<Map.Entry<String, Double>> entrySet = map.entrySet();
        Iterator<Map.Entry<String, Double>> it = entrySet.iterator();
        Map.Entry<String, Double> entry = it.next();
        String key = entry.getKey();
        Double value = entry.getValue();

        it.remove();
        assertEquals(3, map.size());
        assertTrue(map.containsKey(key));
        assertTrue(map.containsValue(value));
    }

    public void testEntrySetIteratorSetValue()
    {
        ObservableMap<String, Double> map = createObservableMap();
        map.put("foo", Double.valueOf(0.0d));
        map.put("bar", Double.valueOf(1.0d));
        map.put("baz", Double.valueOf(2.0d));

        Listener<String, Double> listener = new Listener<String, Double>();
        NeverVetoListener<String, Double> neverVetoListener = new NeverVetoListener<String, Double>();
        map.addMapChangeListener(listener);
        map.addVetoableMapChangeListener(neverVetoListener);

        Set<Map.Entry<String, Double>> entrySet = map.entrySet();
        Iterator<Map.Entry<String, Double>> it = entrySet.iterator();
        Map.Entry<String, Double> entry = it.next();
        String key = entry.getKey();
        Double oldValue = entry.getValue();
        Double newValue = Double.valueOf(-1.0d);
        entry.setValue(newValue);

        assertTrue(map.containsKey(key));
        assertFalse(map.containsValue(oldValue));
        assertTrue(map.containsValue(newValue));
        assertEquals(newValue, map.get(key));

        assertNotNull(listener.getEvent());
        assertSame(map, listener.getEvent().getObservableMap());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(map, neverVetoListener.getEvent().getObservableMap());
    }

    public void testEntrySetIteratorSetValueVeto()
    {
        ObservableMap<String, Double> map = createObservableMap();
        map.put("foo", Double.valueOf(0.0d));
        map.put("bar", Double.valueOf(1.0d));
        map.put("baz", Double.valueOf(2.0d));

        AlwaysVetoListener<String, Double> alwaysVetoListener = new AlwaysVetoListener<String, Double>();
        map.addVetoableMapChangeListener(alwaysVetoListener);

        Set<Map.Entry<String, Double>> entrySet = map.entrySet();
        Iterator<Map.Entry<String, Double>> it = entrySet.iterator();
        Map.Entry<String, Double> entry = it.next();
        String key = entry.getKey();
        Double oldValue = entry.getValue();
        Double newValue = Double.valueOf(-1.0d);
        entry.setValue(newValue);

        assertTrue(map.containsKey(key));
        assertTrue(map.containsValue(oldValue));
        assertFalse(map.containsValue(newValue));
        assertEquals(oldValue, map.get(key));
    }

    public void testKeySetRemove()
    {
        ObservableMap<String, Double> map = createObservableMap();
        map.put("foo", Double.valueOf(0.0d));
        map.put("bar", Double.valueOf(1.0d));
        map.put("baz", Double.valueOf(2.0d));

        Listener<String, Double> listener = new Listener<String, Double>();
        NeverVetoListener<String, Double> neverVetoListener = new NeverVetoListener<String, Double>();
        map.addMapChangeListener(listener);
        map.addVetoableMapChangeListener(neverVetoListener);

        Set<String> keySet = map.keySet();
        String key = keySet.iterator().next();
        assertTrue(keySet.remove(key));
        assertFalse(map.containsKey(key));

        assertNotNull(listener.getEvent());
        assertSame(map, listener.getEvent().getObservableMap());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(map, neverVetoListener.getEvent().getObservableMap());
    }

    public void testKeySetRemoveVeto()
    {
        ObservableMap<String, Double> map = createObservableMap();
        map.put("foo", Double.valueOf(0.0d));
        map.put("bar", Double.valueOf(1.0d));
        map.put("baz", Double.valueOf(2.0d));

        AlwaysVetoListener<String, Double> alwaysVetoListener = new AlwaysVetoListener<String, Double>();
        map.addVetoableMapChangeListener(alwaysVetoListener);

        Set<String> keySet = map.keySet();
        String key = keySet.iterator().next();
        assertFalse(keySet.remove(key));
        assertTrue(map.containsKey(key));
    }

    public void testKeySetRemoveAll()
    {
        ObservableMap<String, Double> map = createObservableMap();
        map.put("foo", Double.valueOf(0.0d));
        map.put("bar", Double.valueOf(1.0d));
        map.put("baz", Double.valueOf(2.0d));

        Listener<String, Double> listener = new Listener<String, Double>();
        NeverVetoListener<String, Double> neverVetoListener = new NeverVetoListener<String, Double>();
        map.addMapChangeListener(listener);
        map.addVetoableMapChangeListener(neverVetoListener);

        Set<String> keySet = map.keySet();
        Set<String> keys = new HashSet<String>();
        String key0 = keySet.iterator().next();
        keys.add(key0);
        String key1 = keySet.iterator().next();
        keys.add(key1);

        assertTrue(keySet.removeAll(keys));
        assertFalse(map.isEmpty());
        assertFalse(map.containsKey(key0));
        assertFalse(map.containsKey(key1));

        assertNotNull(listener.getEvent());
        assertSame(map, listener.getEvent().getObservableMap());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(map, neverVetoListener.getEvent().getObservableMap());
    }

    public void testKeySetRemoveAllVeto()
    {
        ObservableMap<String, Double> map = createObservableMap();
        map.put("foo", Double.valueOf(0.0d));
        map.put("bar", Double.valueOf(1.0d));
        map.put("baz", Double.valueOf(2.0d));

        AlwaysVetoListener<String, Double> alwaysVetoListener = new AlwaysVetoListener<String, Double>();
        map.addVetoableMapChangeListener(alwaysVetoListener);

        Set<String> keySet = map.keySet();
        Set<String> keys = new HashSet<String>();
        String key0 = keySet.iterator().next();
        keys.add(key0);
        String key1 = keySet.iterator().next();
        keys.add(key1);

        assertFalse(keySet.removeAll(keys));
        assertFalse(map.isEmpty());
        assertTrue(map.containsKey(key0));
        assertTrue(map.containsKey(key1));
    }

    public void testKeySetRetainAll()
    {
        ObservableMap<String, Double> map = createObservableMap();
        map.put("foo", Double.valueOf(0.0d));
        map.put("bar", Double.valueOf(1.0d));
        map.put("baz", Double.valueOf(2.0d));

        Listener<String, Double> listener = new Listener<String, Double>();
        NeverVetoListener<String, Double> neverVetoListener = new NeverVetoListener<String, Double>();
        map.addMapChangeListener(listener);
        map.addVetoableMapChangeListener(neverVetoListener);

        Set<String> keySet = map.keySet();
        Set<String> keys = new HashSet<String>();
        Iterator<String> it = keySet.iterator();
        String key0 = it.next();
        keys.add(key0);
        String key1 = it.next();
        keys.add(key1);
        String key2 = it.next();

        assertTrue(keySet.retainAll(keys));
        assertFalse(map.isEmpty());
        assertTrue(map.containsKey(key0));
        assertTrue(map.containsKey(key1));
        assertFalse(map.containsKey(key2));

        assertNotNull(listener.getEvent());
        assertSame(map, listener.getEvent().getObservableMap());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(map, neverVetoListener.getEvent().getObservableMap());
    }

    public void testKeySetRetainAllVeto()
    {
        ObservableMap<String, Double> map = createObservableMap();
        map.put("foo", Double.valueOf(0.0d));
        map.put("bar", Double.valueOf(1.0d));
        map.put("baz", Double.valueOf(2.0d));

        AlwaysVetoListener<String, Double> alwaysVetoListener = new AlwaysVetoListener<String, Double>();
        map.addVetoableMapChangeListener(alwaysVetoListener);

        Set<String> keySet = map.keySet();
        Set<String> keys = new HashSet<String>();
        Iterator<String> it = keySet.iterator();
        String key0 = it.next();
        keys.add(key0);
        String key1 = it.next();
        keys.add(key1);
        String key2 = it.next();

        assertFalse(keySet.retainAll(keys));
        assertEquals(3, map.size());
        assertTrue(map.containsKey(key0));
        assertTrue(map.containsKey(key1));
        assertTrue(map.containsKey(key2));
    }

    public void testKeySetClear()
    {
        ObservableMap<String, Double> map = createObservableMap();
        map.put("foo", Double.valueOf(0.0d));
        map.put("bar", Double.valueOf(1.0d));
        map.put("baz", Double.valueOf(2.0d));

        Listener<String, Double> listener = new Listener<String, Double>();
        NeverVetoListener<String, Double> neverVetoListener = new NeverVetoListener<String, Double>();
        map.addMapChangeListener(listener);
        map.addVetoableMapChangeListener(neverVetoListener);

        Set<String> keySet = map.keySet();
        keySet.clear();
        assertTrue(map.isEmpty());

        assertNotNull(listener.getEvent());
        assertSame(map, listener.getEvent().getObservableMap());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(map, neverVetoListener.getEvent().getObservableMap());
    }

    public void testKeySetClearVeto()
    {
        ObservableMap<String, Double> map = createObservableMap();
        map.put("foo", Double.valueOf(0.0d));
        map.put("bar", Double.valueOf(1.0d));
        map.put("baz", Double.valueOf(2.0d));

        AlwaysVetoListener<String, Double> alwaysVetoListener = new AlwaysVetoListener<String, Double>();
        map.addVetoableMapChangeListener(alwaysVetoListener);

        Set<String> keySet = map.keySet();
        keySet.clear();
        assertFalse(map.isEmpty());
        assertEquals(3, map.size());
    }

    public void testKeySetIteratorRemove()
    {
        ObservableMap<String, Double> map = createObservableMap();
        map.put("foo", Double.valueOf(0.0d));
        map.put("bar", Double.valueOf(1.0d));
        map.put("baz", Double.valueOf(2.0d));

        Listener<String, Double> listener = new Listener<String, Double>();
        NeverVetoListener<String, Double> neverVetoListener = new NeverVetoListener<String, Double>();
        map.addMapChangeListener(listener);
        map.addVetoableMapChangeListener(neverVetoListener);

        Set<String> keySet = map.keySet();
        Iterator<String> it = keySet.iterator();
        String key = it.next();

        it.remove();
        assertEquals(2, map.size());
        assertFalse(map.containsKey(key));

        assertNotNull(listener.getEvent());
        assertSame(map, listener.getEvent().getObservableMap());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(map, neverVetoListener.getEvent().getObservableMap());
    }

    public void testKeySetIteratorRemoveVeto()
    {
        ObservableMap<String, Double> map = createObservableMap();
        map.put("foo", Double.valueOf(0.0d));
        map.put("bar", Double.valueOf(1.0d));
        map.put("baz", Double.valueOf(2.0d));

        AlwaysVetoListener<String, Double> alwaysVetoListener = new AlwaysVetoListener<String, Double>();
        map.addVetoableMapChangeListener(alwaysVetoListener);

        Set<String> keySet = map.keySet();
        Iterator<String> it = keySet.iterator();
        String key = it.next();

        it.remove();
        assertEquals(3, map.size());
        assertTrue(map.containsKey(key));
    }

    /**
     * Listener.
     */
    protected class Listener<K, V>
        implements MapChangeListener<K, V> {

        /** Last event heard, if any. */
        private MapChangeEvent<K, V> event;


        /** {@inheritDoc} */
        public void mapChanged(final MapChangeEvent<K, V> event)
        {
            this.event = event;
        }

        /**
         * Return the last heard event, if any.
         *
         * @return the last heard event, if any
         */
        MapChangeEvent<K, V> getEvent()
        {
            return event;
        }
    }

    /**
     * Always veto listener.
     */
    protected class AlwaysVetoListener<K, V>
        implements VetoableMapChangeListener<K, V>
    {

        /** {@inheritDoc} */
        public void mapWillChange(final VetoableMapChangeEvent<K, V> event)
            throws MapChangeVetoException
        {
            throw new MapChangeVetoException();
        }
    }

    /**
     * Never veto listener.
     */
    protected class NeverVetoListener<K, V>
        implements VetoableMapChangeListener<K, V>
    {
        /** Last event heard, if any. */
        private VetoableMapChangeEvent<K, V> event;


        /** {@inheritDoc} */
        public void mapWillChange(final VetoableMapChangeEvent<K, V> event)
            throws MapChangeVetoException
        {
            this.event = event;
        }

        /**
         * Return the last heard event, if any.
         *
         * @return the last heard event, if any
         */
        VetoableMapChangeEvent<K, V> getEvent()
        {
            return event;
        }
    }
}
