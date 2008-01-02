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
package org.dishevelled.observable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import junit.framework.TestCase;

import org.dishevelled.observable.event.CollectionChangeEvent;
import org.dishevelled.observable.event.CollectionChangeListener;
import org.dishevelled.observable.event.CollectionChangeVetoException;
import org.dishevelled.observable.event.VetoableCollectionChangeEvent;
import org.dishevelled.observable.event.VetoableCollectionChangeListener;

/**
 * Abstract unit test for implementations of ObservableCollection.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractObservableCollectionTest
    extends TestCase
{

    /**
     * Create and return a new instance of ObservableCollection to test.
     *
     * @param <T> collection element type
     * @return a new instance of ObservableCollection to test
     */
    protected abstract <T> ObservableCollection<T> createObservableCollection();


    public void testCreateObservableCollection()
    {
        ObservableCollection<String> collection0 = createObservableCollection();
        assertNotNull(collection0);

        ObservableCollection<String> collection1 = createObservableCollection();
        assertNotNull(collection1);

        assertTrue(collection1 != collection0);
    }

    public void testListeners()
    {
        ObservableCollection<String> collection = createObservableCollection();
        Listener<String> listener = new Listener<String>();
        collection.addCollectionChangeListener(listener);

        assertEquals(1, collection.getCollectionChangeListenerCount());
        assertNotNull(collection.getCollectionChangeListeners());
        assertEquals(1, collection.getCollectionChangeListeners().length);
        assertEquals(listener, collection.getCollectionChangeListeners()[0]);

        collection.removeCollectionChangeListener(listener);
        assertEquals(0, collection.getCollectionChangeListenerCount());
        assertNotNull(collection.getCollectionChangeListeners());
        assertEquals(0, collection.getCollectionChangeListeners().length);

        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        collection.addVetoableCollectionChangeListener(neverVetoListener);

        assertEquals(1, collection.getVetoableCollectionChangeListenerCount());
        assertNotNull(collection.getVetoableCollectionChangeListeners());
        assertEquals(1, collection.getVetoableCollectionChangeListeners().length);
        assertEquals(neverVetoListener, collection.getVetoableCollectionChangeListeners()[0]);

        collection.removeVetoableCollectionChangeListener(neverVetoListener);
        assertEquals(0, collection.getVetoableCollectionChangeListenerCount());
        assertNotNull(collection.getVetoableCollectionChangeListeners());
        assertEquals(0, collection.getVetoableCollectionChangeListeners().length);
    }

    public void testFireCollectionWillChange()
        throws Exception
    {
        ObservableCollection<String> collection = createObservableCollection();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        collection.addVetoableCollectionChangeListener(neverVetoListener);

        if (collection instanceof AbstractObservableCollection)
        {
            ((AbstractObservableCollection) collection).fireCollectionWillChange();

            assertNotNull(neverVetoListener.getEvent());
            assertSame(collection, neverVetoListener.getEvent().getObservableCollection());
        }
    }

    public void testFireCollectionChanged()
    {
        ObservableCollection<String> collection = createObservableCollection();
        Listener<String> listener = new Listener<String>();
        collection.addCollectionChangeListener(listener);

        if (collection instanceof AbstractObservableCollection)
        {
            ((AbstractObservableCollection) collection).fireCollectionChanged();

            assertNotNull(listener.getEvent());
            assertSame(collection, listener.getEvent().getObservableCollection());
        }
    }

    public void testAdd()
    {
        ObservableCollection<String> collection = createObservableCollection();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        collection.addCollectionChangeListener(listener);
        collection.addVetoableCollectionChangeListener(neverVetoListener);

        assertTrue(collection.add("foo"));
        assertTrue(collection.contains("foo"));

        assertNotNull(listener.getEvent());
        assertSame(collection, listener.getEvent().getObservableCollection()); // assertEquals failed!

        assertNotNull(neverVetoListener.getEvent());
        assertSame(collection, neverVetoListener.getEvent().getObservableCollection());
    }

    public void testAddVeto()
    {
        ObservableCollection<String> collection = createObservableCollection();
        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        collection.addVetoableCollectionChangeListener(alwaysVetoListener);

        assertFalse(collection.add("foo"));
        assertFalse(collection.contains("foo"));
    }

    public void testAddAll()
    {
        ObservableCollection<String> collection = createObservableCollection();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        collection.addCollectionChangeListener(listener);
        collection.addVetoableCollectionChangeListener(neverVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        assertTrue(collection.addAll(strings));
        for (String s : strings)
        {
            assertTrue(collection.contains(s));
        }

        assertNotNull(listener.getEvent());
        assertSame(collection, listener.getEvent().getObservableCollection());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(collection, neverVetoListener.getEvent().getObservableCollection());
    }

    public void testAddAllVeto()
    {
        ObservableCollection<String> collection = createObservableCollection();
        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        collection.addVetoableCollectionChangeListener(alwaysVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        assertFalse(collection.addAll(strings));
        for (String s : strings)
        {
            assertFalse(collection.contains(s));
        }
    }

    public void testClear()
    {
        ObservableCollection<String> collection = createObservableCollection();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        collection.addCollectionChangeListener(listener);
        collection.addVetoableCollectionChangeListener(neverVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        collection.addAll(strings);
        for (String s : strings)
        {
            assertTrue(collection.contains(s));
        }
        collection.clear();
        for (String s : strings)
        {
            assertFalse(collection.contains(s));
        }

        assertNotNull(listener.getEvent());
        assertSame(collection, listener.getEvent().getObservableCollection());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(collection, neverVetoListener.getEvent().getObservableCollection());
    }

    public void testClearVeto()
    {
        ObservableCollection<String> collection = createObservableCollection();
        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        collection.addAll(strings);
        for (String s : strings)
        {
            assertTrue(collection.contains(s));
        }

        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        collection.addVetoableCollectionChangeListener(alwaysVetoListener);

        collection.clear();
        for (String s : strings)
        {
            assertTrue(collection.contains(s));
        }
    }

    public void testRemove()
    {
        ObservableCollection<String> collection = createObservableCollection();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        collection.addCollectionChangeListener(listener);
        collection.addVetoableCollectionChangeListener(neverVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        collection.addAll(strings);
        for (String s : strings)
        {
            assertTrue(collection.contains(s));
        }
        assertTrue(collection.remove("foo"));
        assertFalse(collection.contains("foo"));

        assertNotNull(listener.getEvent());
        assertSame(collection, listener.getEvent().getObservableCollection());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(collection, neverVetoListener.getEvent().getObservableCollection());
    }

    public void testRemoveVeto()
    {
        ObservableCollection<String> collection = createObservableCollection();
        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        collection.addAll(strings);
        for (String s : strings)
        {
            assertTrue(collection.contains(s));
        }

        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        collection.addVetoableCollectionChangeListener(alwaysVetoListener);

        assertFalse(collection.remove("foo"));
        assertTrue(collection.contains("foo"));
    }

    public void testRemoveAll()
    {
        ObservableCollection<String> collection = createObservableCollection();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        collection.addCollectionChangeListener(listener);
        collection.addVetoableCollectionChangeListener(neverVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        collection.addAll(strings);
        for (String s : strings)
        {
            assertTrue(collection.contains(s));
        }
        assertTrue(collection.removeAll(strings));
        for (String s : strings)
        {
            assertFalse(collection.contains(s));
        }

        assertNotNull(listener.getEvent());
        assertSame(collection, listener.getEvent().getObservableCollection());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(collection, neverVetoListener.getEvent().getObservableCollection());
    }

    public void testRemoveAllVeto()
    {
        ObservableCollection<String> collection = createObservableCollection();
        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        collection.addAll(strings);
        for (String s : strings)
        {
            assertTrue(collection.contains(s));
        }

        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        collection.addVetoableCollectionChangeListener(alwaysVetoListener);

        assertFalse(collection.removeAll(strings));
        for (String s : strings)
        {
            assertTrue(collection.contains(s));
        }
    }

    public void testRetainAll()
    {
        ObservableCollection<String> collection = createObservableCollection();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        collection.addCollectionChangeListener(listener);
        collection.addVetoableCollectionChangeListener(neverVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        collection.addAll(strings);
        for (String s : strings)
        {
            assertTrue(collection.contains(s));
        }
        assertTrue(collection.retainAll(Collections.singleton("foo")));
        assertTrue(collection.contains("foo"));
        assertFalse(collection.contains("bar"));
        assertFalse(collection.contains("baz"));

        assertNotNull(listener.getEvent());
        assertSame(collection, listener.getEvent().getObservableCollection());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(collection, neverVetoListener.getEvent().getObservableCollection());
    }

    public void testRetainAllVeto()
    {
        ObservableCollection<String> collection = createObservableCollection();
        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        collection.addAll(strings);
        for (String s : strings)
        {
            assertTrue(collection.contains(s));
        }

        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        collection.addVetoableCollectionChangeListener(alwaysVetoListener);

        assertFalse(collection.retainAll(Collections.singleton("foo")));
        for (String s : strings)
        {
            assertTrue(collection.contains(s));
        }
    }

    public void testIteratorRemove()
    {
        ObservableCollection<String> collection = createObservableCollection();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        collection.addCollectionChangeListener(listener);
        collection.addVetoableCollectionChangeListener(neverVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        collection.addAll(strings);
        for (String s : strings)
        {
            assertTrue(collection.contains(s));
        }
        Iterator<String> iterator = collection.iterator();
        String s = iterator.next();
        iterator.remove();
        assertFalse(collection.contains(s));
        assertEquals(2, collection.size());

        assertNotNull(listener.getEvent());
        assertSame(collection, listener.getEvent().getObservableCollection());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(collection, neverVetoListener.getEvent().getObservableCollection());
    }

    public void testIteratorRemoveVeto()
    {
        ObservableCollection<String> collection = createObservableCollection();
        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        collection.addAll(strings);
        for (String s : strings)
        {
            assertTrue(collection.contains(s));
        }

        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        collection.addVetoableCollectionChangeListener(alwaysVetoListener);

        Iterator<String> iterator = collection.iterator();
        String ignore = iterator.next();
        iterator.remove();
        for (String s : strings)
        {
            assertTrue(collection.contains(s));
        }
    }

    /**
     * Listener.
     */
    protected class Listener<E>
        implements CollectionChangeListener<E> {

        /** Last event heard, if any. */
        private CollectionChangeEvent<E> event;


        /** {@inheritDoc} */
        public void collectionChanged(final CollectionChangeEvent<E> event)
        {
            this.event = event;
        }

        /**
         * Return the last heard event, if any.
         *
         * @return the last heard event, if any
         */
        CollectionChangeEvent<E> getEvent()
        {
            return event;
        }
    }

    /**
     * Always veto listener.
     */
    protected class AlwaysVetoListener<E>
        implements VetoableCollectionChangeListener<E>
    {

        /** {@inheritDoc} */
        public void collectionWillChange(final VetoableCollectionChangeEvent<E> event)
            throws CollectionChangeVetoException
        {
            throw new CollectionChangeVetoException();
        }
    }

    /**
     * Never veto listener.
     */
    protected class NeverVetoListener<E>
        implements VetoableCollectionChangeListener<E>
    {
        /** Last event heard, if any. */
        private VetoableCollectionChangeEvent<E> event;


        /** {@inheritDoc} */
        public void collectionWillChange(final VetoableCollectionChangeEvent<E> event)
            throws CollectionChangeVetoException
        {
            this.event = event;
        }

        /**
         * Return the last heard event, if any.
         *
         * @return the last heard event, if any
         */
        VetoableCollectionChangeEvent<E> getEvent()
        {
            return event;
        }
    }
}
