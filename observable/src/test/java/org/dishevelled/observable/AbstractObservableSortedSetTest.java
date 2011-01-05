/*

    dsh-observable  Observable decorators for collection and map interfaces.
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
package org.dishevelled.observable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import junit.framework.TestCase;

import org.dishevelled.observable.event.SortedSetChangeEvent;
import org.dishevelled.observable.event.SortedSetChangeListener;
import org.dishevelled.observable.event.SortedSetChangeVetoException;
import org.dishevelled.observable.event.VetoableSortedSetChangeEvent;
import org.dishevelled.observable.event.VetoableSortedSetChangeListener;

/**
 * Abstract unit test for implementations of ObservableSortedSet.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractObservableSortedSetTest
    extends TestCase
{

    /**
     * Create and return a new instance of ObservableSortedSet to test.
     *
     * @param <T> sorted set element type
     * @return a new instance of ObservableSortedSet to test
     */
    protected abstract <T> ObservableSortedSet<T> createObservableSortedSet();


    public void testCreateObservableSortedSet()
    {
        ObservableSortedSet<String> sortedSet0 = createObservableSortedSet();
        assertNotNull(sortedSet0);

        ObservableSortedSet<String> sortedSet1 = createObservableSortedSet();
        assertNotNull(sortedSet1);

        assertTrue(sortedSet1 != sortedSet0);
    }

    public void testListeners()
    {
        ObservableSortedSet<String> sortedSet = createObservableSortedSet();
        Listener<String> listener = new Listener<String>();
        sortedSet.addSortedSetChangeListener(listener);

        assertEquals(1, sortedSet.getSortedSetChangeListenerCount());
        assertNotNull(sortedSet.getSortedSetChangeListeners());
        assertEquals(1, sortedSet.getSortedSetChangeListeners().length);
        assertEquals(listener, sortedSet.getSortedSetChangeListeners()[0]);

        sortedSet.removeSortedSetChangeListener(listener);
        assertEquals(0, sortedSet.getSortedSetChangeListenerCount());
        assertNotNull(sortedSet.getSortedSetChangeListeners());
        assertEquals(0, sortedSet.getSortedSetChangeListeners().length);

        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        sortedSet.addVetoableSortedSetChangeListener(neverVetoListener);

        assertEquals(1, sortedSet.getVetoableSortedSetChangeListenerCount());
        assertNotNull(sortedSet.getVetoableSortedSetChangeListeners());
        assertEquals(1, sortedSet.getVetoableSortedSetChangeListeners().length);
        assertEquals(neverVetoListener, sortedSet.getVetoableSortedSetChangeListeners()[0]);

        sortedSet.removeVetoableSortedSetChangeListener(neverVetoListener);
        assertEquals(0, sortedSet.getVetoableSortedSetChangeListenerCount());
        assertNotNull(sortedSet.getVetoableSortedSetChangeListeners());
        assertEquals(0, sortedSet.getVetoableSortedSetChangeListeners().length);
    }

    public void testFireSortedSetWillChange()
        throws Exception
    {
        ObservableSortedSet<String> sortedSet = createObservableSortedSet();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        sortedSet.addVetoableSortedSetChangeListener(neverVetoListener);

        if (sortedSet instanceof AbstractObservableSortedSet)
        {
            ((AbstractObservableSortedSet) sortedSet).fireSortedSetWillChange();

            assertNotNull(neverVetoListener.getEvent());
            assertSame(sortedSet, neverVetoListener.getEvent().getObservableSortedSet());
        }
    }

    public void testFireSortedSetChanged()
    {
        ObservableSortedSet<String> sortedSet = createObservableSortedSet();
        Listener<String> listener = new Listener<String>();
        sortedSet.addSortedSetChangeListener(listener);

        if (sortedSet instanceof AbstractObservableSortedSet)
        {
            ((AbstractObservableSortedSet) sortedSet).fireSortedSetChanged();

            assertNotNull(listener.getEvent());
            assertSame(sortedSet, listener.getEvent().getObservableSortedSet());
        }
    }

    public void testAdd()
    {
        ObservableSortedSet<String> sortedSet = createObservableSortedSet();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        sortedSet.addSortedSetChangeListener(listener);
        sortedSet.addVetoableSortedSetChangeListener(neverVetoListener);

        assertTrue(sortedSet.add("foo"));
        assertTrue(sortedSet.contains("foo"));

        assertNotNull(listener.getEvent());
        assertSame(sortedSet, listener.getEvent().getObservableSortedSet()); // assertEquals failed!

        assertNotNull(neverVetoListener.getEvent());
        assertSame(sortedSet, neverVetoListener.getEvent().getObservableSortedSet());
    }

    public void testAddVeto()
    {
        ObservableSortedSet<String> sortedSet = createObservableSortedSet();
        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        sortedSet.addVetoableSortedSetChangeListener(alwaysVetoListener);

        assertFalse(sortedSet.add("foo"));
        assertFalse(sortedSet.contains("foo"));
    }

    public void testAddAll()
    {
        ObservableSortedSet<String> sortedSet = createObservableSortedSet();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        sortedSet.addSortedSetChangeListener(listener);
        sortedSet.addVetoableSortedSetChangeListener(neverVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        assertTrue(sortedSet.addAll(strings));
        for (String s : strings)
        {
            assertTrue(sortedSet.contains(s));
        }

        assertNotNull(listener.getEvent());
        assertSame(sortedSet, listener.getEvent().getObservableSortedSet());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(sortedSet, neverVetoListener.getEvent().getObservableSortedSet());
    }

    public void testAddAllVeto()
    {
        ObservableSortedSet<String> sortedSet = createObservableSortedSet();
        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        sortedSet.addVetoableSortedSetChangeListener(alwaysVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        assertFalse(sortedSet.addAll(strings));
        for (String s : strings)
        {
            assertFalse(sortedSet.contains(s));
        }
    }

    public void testClear()
    {
        ObservableSortedSet<String> sortedSet = createObservableSortedSet();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        sortedSet.addSortedSetChangeListener(listener);
        sortedSet.addVetoableSortedSetChangeListener(neverVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        sortedSet.addAll(strings);
        for (String s : strings)
        {
            assertTrue(sortedSet.contains(s));
        }
        sortedSet.clear();
        for (String s : strings)
        {
            assertFalse(sortedSet.contains(s));
        }

        assertNotNull(listener.getEvent());
        assertSame(sortedSet, listener.getEvent().getObservableSortedSet());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(sortedSet, neverVetoListener.getEvent().getObservableSortedSet());
    }

    public void testClearVeto()
    {
        ObservableSortedSet<String> sortedSet = createObservableSortedSet();
        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        sortedSet.addAll(strings);
        for (String s : strings)
        {
            assertTrue(sortedSet.contains(s));
        }

        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        sortedSet.addVetoableSortedSetChangeListener(alwaysVetoListener);

        sortedSet.clear();
        for (String s : strings)
        {
            assertTrue(sortedSet.contains(s));
        }
    }

    public void testRemove()
    {
        ObservableSortedSet<String> sortedSet = createObservableSortedSet();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        sortedSet.addSortedSetChangeListener(listener);
        sortedSet.addVetoableSortedSetChangeListener(neverVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        sortedSet.addAll(strings);
        for (String s : strings)
        {
            assertTrue(sortedSet.contains(s));
        }
        assertTrue(sortedSet.remove("foo"));
        assertFalse(sortedSet.contains("foo"));

        assertNotNull(listener.getEvent());
        assertSame(sortedSet, listener.getEvent().getObservableSortedSet());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(sortedSet, neverVetoListener.getEvent().getObservableSortedSet());
    }

    public void testRemoveVeto()
    {
        ObservableSortedSet<String> sortedSet = createObservableSortedSet();
        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        sortedSet.addAll(strings);
        for (String s : strings)
        {
            assertTrue(sortedSet.contains(s));
        }

        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        sortedSet.addVetoableSortedSetChangeListener(alwaysVetoListener);

        assertFalse(sortedSet.remove("foo"));
        assertTrue(sortedSet.contains("foo"));
    }

    public void testRemoveAll()
    {
        ObservableSortedSet<String> sortedSet = createObservableSortedSet();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        sortedSet.addSortedSetChangeListener(listener);
        sortedSet.addVetoableSortedSetChangeListener(neverVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        sortedSet.addAll(strings);
        for (String s : strings)
        {
            assertTrue(sortedSet.contains(s));
        }
        assertTrue(sortedSet.removeAll(strings));
        for (String s : strings)
        {
            assertFalse(sortedSet.contains(s));
        }

        assertNotNull(listener.getEvent());
        assertSame(sortedSet, listener.getEvent().getObservableSortedSet());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(sortedSet, neverVetoListener.getEvent().getObservableSortedSet());
    }

    public void testRemoveAllVeto()
    {
        ObservableSortedSet<String> sortedSet = createObservableSortedSet();
        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        sortedSet.addAll(strings);
        for (String s : strings)
        {
            assertTrue(sortedSet.contains(s));
        }

        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        sortedSet.addVetoableSortedSetChangeListener(alwaysVetoListener);

        assertFalse(sortedSet.removeAll(strings));
        for (String s : strings)
        {
            assertTrue(sortedSet.contains(s));
        }
    }

    public void testRetainAll()
    {
        ObservableSortedSet<String> sortedSet = createObservableSortedSet();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        sortedSet.addSortedSetChangeListener(listener);
        sortedSet.addVetoableSortedSetChangeListener(neverVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        sortedSet.addAll(strings);
        for (String s : strings)
        {
            assertTrue(sortedSet.contains(s));
        }
        assertTrue(sortedSet.retainAll(Collections.singleton("foo")));
        assertTrue(sortedSet.contains("foo"));
        assertFalse(sortedSet.contains("bar"));
        assertFalse(sortedSet.contains("baz"));

        assertNotNull(listener.getEvent());
        assertSame(sortedSet, listener.getEvent().getObservableSortedSet());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(sortedSet, neverVetoListener.getEvent().getObservableSortedSet());
    }

    public void testRetainAllVeto()
    {
        ObservableSortedSet<String> sortedSet = createObservableSortedSet();
        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        sortedSet.addAll(strings);
        for (String s : strings)
        {
            assertTrue(sortedSet.contains(s));
        }

        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        sortedSet.addVetoableSortedSetChangeListener(alwaysVetoListener);

        assertFalse(sortedSet.retainAll(Collections.singleton("foo")));
        for (String s : strings)
        {
            assertTrue(sortedSet.contains(s));
        }
    }

    public void testIteratorRemove()
    {
        ObservableSortedSet<String> sortedSet = createObservableSortedSet();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        sortedSet.addSortedSetChangeListener(listener);
        sortedSet.addVetoableSortedSetChangeListener(neverVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        sortedSet.addAll(strings);
        for (String s : strings)
        {
            assertTrue(sortedSet.contains(s));
        }
        Iterator<String> iterator = sortedSet.iterator();
        String s = iterator.next();
        iterator.remove();
        assertFalse(sortedSet.contains(s));
        assertEquals(2, sortedSet.size());

        assertNotNull(listener.getEvent());
        assertSame(sortedSet, listener.getEvent().getObservableSortedSet());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(sortedSet, neverVetoListener.getEvent().getObservableSortedSet());
    }

    public void testIteratorRemoveVeto()
    {
        ObservableSortedSet<String> sortedSet = createObservableSortedSet();
        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        sortedSet.addAll(strings);
        for (String s : strings)
        {
            assertTrue(sortedSet.contains(s));
        }

        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        sortedSet.addVetoableSortedSetChangeListener(alwaysVetoListener);

        Iterator<String> iterator = sortedSet.iterator();
        String ignore = iterator.next();
        iterator.remove();
        for (String s : strings)
        {
            assertTrue(sortedSet.contains(s));
        }
    }

    // TODO:  subSet, subSet iterator, nested subSets, ...


    /**
     * Listener.
     */
    protected class Listener<E>
        implements SortedSetChangeListener<E> {

        /** Last event heard, if any. */
        private SortedSetChangeEvent<E> event;


        /** {@inheritDoc} */
        public void sortedSetChanged(final SortedSetChangeEvent<E> event)
        {
            this.event = event;
        }

        /**
         * Return the last heard event, if any.
         *
         * @return the last heard event, if any
         */
        SortedSetChangeEvent<E> getEvent()
        {
            return event;
        }
    }

    /**
     * Always veto listener.
     */
    protected class AlwaysVetoListener<E>
        implements VetoableSortedSetChangeListener<E>
    {

        /** {@inheritDoc} */
        public void sortedSetWillChange(final VetoableSortedSetChangeEvent<E> event)
            throws SortedSetChangeVetoException
        {
            throw new SortedSetChangeVetoException();
        }
    }

    /**
     * Never veto listener.
     */
    protected class NeverVetoListener<E>
        implements VetoableSortedSetChangeListener<E>
    {
        /** Last event heard, if any. */
        private VetoableSortedSetChangeEvent<E> event;


        /** {@inheritDoc} */
        public void sortedSetWillChange(final VetoableSortedSetChangeEvent<E> event)
            throws SortedSetChangeVetoException
        {
            this.event = event;
        }

        /**
         * Return the last heard event, if any.
         *
         * @return the last heard event, if any
         */
        VetoableSortedSetChangeEvent<E> getEvent()
        {
            return event;
        }
    }
}
