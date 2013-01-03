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
import java.util.Collections;
import java.util.Iterator;

import junit.framework.TestCase;

import org.dishevelled.observable.event.SetChangeEvent;
import org.dishevelled.observable.event.SetChangeListener;
import org.dishevelled.observable.event.SetChangeVetoException;
import org.dishevelled.observable.event.VetoableSetChangeEvent;
import org.dishevelled.observable.event.VetoableSetChangeListener;

/**
 * Abstract unit test for implementations of ObservableSet.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractObservableSetTest
    extends TestCase
{

    /**
     * Create and return a new instance of ObservableSet to test.
     *
     * @param <T> set element type
     * @return a new instance of ObservableSet to test
     */
    protected abstract <T> ObservableSet<T> createObservableSet();


    public void testCreateObservableSet()
    {
        ObservableSet<String> set0 = createObservableSet();
        assertNotNull(set0);

        ObservableSet<String> set1 = createObservableSet();
        assertNotNull(set1);

        assertTrue(set1 != set0);
    }

    public void testListeners()
    {
        ObservableSet<String> set = createObservableSet();
        Listener<String> listener = new Listener<String>();
        set.addSetChangeListener(listener);

        assertEquals(1, set.getSetChangeListenerCount());
        assertNotNull(set.getSetChangeListeners());
        assertEquals(1, set.getSetChangeListeners().length);
        assertEquals(listener, set.getSetChangeListeners()[0]);

        set.removeSetChangeListener(listener);
        assertEquals(0, set.getSetChangeListenerCount());
        assertNotNull(set.getSetChangeListeners());
        assertEquals(0, set.getSetChangeListeners().length);

        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        set.addVetoableSetChangeListener(neverVetoListener);

        assertEquals(1, set.getVetoableSetChangeListenerCount());
        assertNotNull(set.getVetoableSetChangeListeners());
        assertEquals(1, set.getVetoableSetChangeListeners().length);
        assertEquals(neverVetoListener, set.getVetoableSetChangeListeners()[0]);

        set.removeVetoableSetChangeListener(neverVetoListener);
        assertEquals(0, set.getVetoableSetChangeListenerCount());
        assertNotNull(set.getVetoableSetChangeListeners());
        assertEquals(0, set.getVetoableSetChangeListeners().length);
    }

    public void testFireSetWillChange()
        throws Exception
    {
        ObservableSet<String> set = createObservableSet();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        set.addVetoableSetChangeListener(neverVetoListener);

        if (set instanceof AbstractObservableSet)
        {
            ((AbstractObservableSet) set).fireSetWillChange();

            assertNotNull(neverVetoListener.getEvent());
            assertSame(set, neverVetoListener.getEvent().getObservableSet());
        }
    }

    public void testFireSetChanged()
    {
        ObservableSet<String> set = createObservableSet();
        Listener<String> listener = new Listener<String>();
        set.addSetChangeListener(listener);

        if (set instanceof AbstractObservableSet)
        {
            ((AbstractObservableSet) set).fireSetChanged();

            assertNotNull(listener.getEvent());
            assertSame(set, listener.getEvent().getObservableSet());
        }
    }

    public void testAdd()
    {
        ObservableSet<String> set = createObservableSet();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        set.addSetChangeListener(listener);
        set.addVetoableSetChangeListener(neverVetoListener);

        assertTrue(set.add("foo"));
        assertTrue(set.contains("foo"));

        assertNotNull(listener.getEvent());
        assertSame(set, listener.getEvent().getObservableSet()); // assertEquals failed!

        assertNotNull(neverVetoListener.getEvent());
        assertSame(set, neverVetoListener.getEvent().getObservableSet());
    }

    public void testAddVeto()
    {
        ObservableSet<String> set = createObservableSet();
        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        set.addVetoableSetChangeListener(alwaysVetoListener);

        assertFalse(set.add("foo"));
        assertFalse(set.contains("foo"));
    }

    public void testAddAll()
    {
        ObservableSet<String> set = createObservableSet();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        set.addSetChangeListener(listener);
        set.addVetoableSetChangeListener(neverVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        assertTrue(set.addAll(strings));
        for (String s : strings)
        {
            assertTrue(set.contains(s));
        }

        assertNotNull(listener.getEvent());
        assertSame(set, listener.getEvent().getObservableSet());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(set, neverVetoListener.getEvent().getObservableSet());
    }

    public void testAddAllVeto()
    {
        ObservableSet<String> set = createObservableSet();
        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        set.addVetoableSetChangeListener(alwaysVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        assertFalse(set.addAll(strings));
        for (String s : strings)
        {
            assertFalse(set.contains(s));
        }
    }

    public void testClear()
    {
        ObservableSet<String> set = createObservableSet();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        set.addSetChangeListener(listener);
        set.addVetoableSetChangeListener(neverVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        set.addAll(strings);
        for (String s : strings)
        {
            assertTrue(set.contains(s));
        }
        set.clear();
        for (String s : strings)
        {
            assertFalse(set.contains(s));
        }

        assertNotNull(listener.getEvent());
        assertSame(set, listener.getEvent().getObservableSet());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(set, neverVetoListener.getEvent().getObservableSet());
    }

    public void testClearVeto()
    {
        ObservableSet<String> set = createObservableSet();
        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        set.addAll(strings);
        for (String s : strings)
        {
            assertTrue(set.contains(s));
        }

        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        set.addVetoableSetChangeListener(alwaysVetoListener);

        set.clear();
        for (String s : strings)
        {
            assertTrue(set.contains(s));
        }
    }

    public void testRemove()
    {
        ObservableSet<String> set = createObservableSet();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        set.addSetChangeListener(listener);
        set.addVetoableSetChangeListener(neverVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        set.addAll(strings);
        for (String s : strings)
        {
            assertTrue(set.contains(s));
        }
        assertTrue(set.remove("foo"));
        assertFalse(set.contains("foo"));

        assertNotNull(listener.getEvent());
        assertSame(set, listener.getEvent().getObservableSet());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(set, neverVetoListener.getEvent().getObservableSet());
    }

    public void testRemoveVeto()
    {
        ObservableSet<String> set = createObservableSet();
        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        set.addAll(strings);
        for (String s : strings)
        {
            assertTrue(set.contains(s));
        }

        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        set.addVetoableSetChangeListener(alwaysVetoListener);

        assertFalse(set.remove("foo"));
        assertTrue(set.contains("foo"));
    }

    public void testRemoveAll()
    {
        ObservableSet<String> set = createObservableSet();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        set.addSetChangeListener(listener);
        set.addVetoableSetChangeListener(neverVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        set.addAll(strings);
        for (String s : strings)
        {
            assertTrue(set.contains(s));
        }
        assertTrue(set.removeAll(strings));
        for (String s : strings)
        {
            assertFalse(set.contains(s));
        }

        assertNotNull(listener.getEvent());
        assertSame(set, listener.getEvent().getObservableSet());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(set, neverVetoListener.getEvent().getObservableSet());
    }

    public void testRemoveAllVeto()
    {
        ObservableSet<String> set = createObservableSet();
        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        set.addAll(strings);
        for (String s : strings)
        {
            assertTrue(set.contains(s));
        }

        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        set.addVetoableSetChangeListener(alwaysVetoListener);

        assertFalse(set.removeAll(strings));
        for (String s : strings)
        {
            assertTrue(set.contains(s));
        }
    }

    public void testRetainAll()
    {
        ObservableSet<String> set = createObservableSet();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        set.addSetChangeListener(listener);
        set.addVetoableSetChangeListener(neverVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        set.addAll(strings);
        for (String s : strings)
        {
            assertTrue(set.contains(s));
        }
        assertTrue(set.retainAll(Collections.singleton("foo")));
        assertTrue(set.contains("foo"));
        assertFalse(set.contains("bar"));
        assertFalse(set.contains("baz"));

        assertNotNull(listener.getEvent());
        assertSame(set, listener.getEvent().getObservableSet());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(set, neverVetoListener.getEvent().getObservableSet());
    }

    public void testRetainAllVeto()
    {
        ObservableSet<String> set = createObservableSet();
        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        set.addAll(strings);
        for (String s : strings)
        {
            assertTrue(set.contains(s));
        }

        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        set.addVetoableSetChangeListener(alwaysVetoListener);

        assertFalse(set.retainAll(Collections.singleton("foo")));
        for (String s : strings)
        {
            assertTrue(set.contains(s));
        }
    }

    public void testIteratorRemove()
    {
        ObservableSet<String> set = createObservableSet();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        set.addSetChangeListener(listener);
        set.addVetoableSetChangeListener(neverVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        set.addAll(strings);
        for (String s : strings)
        {
            assertTrue(set.contains(s));
        }
        Iterator<String> iterator = set.iterator();
        String s = iterator.next();
        iterator.remove();
        assertFalse(set.contains(s));
        assertEquals(2, set.size());

        assertNotNull(listener.getEvent());
        assertSame(set, listener.getEvent().getObservableSet());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(set, neverVetoListener.getEvent().getObservableSet());
    }

    public void testIteratorRemoveVeto()
    {
        ObservableSet<String> set = createObservableSet();
        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        set.addAll(strings);
        for (String s : strings)
        {
            assertTrue(set.contains(s));
        }

        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        set.addVetoableSetChangeListener(alwaysVetoListener);

        Iterator<String> iterator = set.iterator();
        String ignore = iterator.next();
        iterator.remove();
        for (String s : strings)
        {
            assertTrue(set.contains(s));
        }
    }

    /**
     * Listener.
     */
    protected class Listener<E>
        implements SetChangeListener<E> {

        /** Last event heard, if any. */
        private SetChangeEvent<E> event;


        /** {@inheritDoc} */
        public void setChanged(final SetChangeEvent<E> event)
        {
            this.event = event;
        }

        /**
         * Return the last heard event, if any.
         *
         * @return the last heard event, if any
         */
        SetChangeEvent<E> getEvent()
        {
            return event;
        }
    }

    /**
     * Always veto listener.
     */
    protected class AlwaysVetoListener<E>
        implements VetoableSetChangeListener<E>
    {

        /** {@inheritDoc} */
        public void setWillChange(final VetoableSetChangeEvent<E> event)
            throws SetChangeVetoException
        {
            throw new SetChangeVetoException();
        }
    }

    /**
     * Never veto listener.
     */
    protected class NeverVetoListener<E>
        implements VetoableSetChangeListener<E>
    {
        /** Last event heard, if any. */
        private VetoableSetChangeEvent<E> event;


        /** {@inheritDoc} */
        public void setWillChange(final VetoableSetChangeEvent<E> event)
            throws SetChangeVetoException
        {
            this.event = event;
        }

        /**
         * Return the last heard event, if any.
         *
         * @return the last heard event, if any
         */
        VetoableSetChangeEvent<E> getEvent()
        {
            return event;
        }
    }
}