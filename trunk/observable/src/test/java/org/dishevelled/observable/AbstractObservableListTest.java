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
import java.util.List;
import java.util.ListIterator;

import junit.framework.TestCase;

import org.dishevelled.observable.event.ListChangeEvent;
import org.dishevelled.observable.event.ListChangeListener;
import org.dishevelled.observable.event.ListChangeVetoException;
import org.dishevelled.observable.event.VetoableListChangeEvent;
import org.dishevelled.observable.event.VetoableListChangeListener;

/**
 * Abstract unit test for implementations of ObservableList.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractObservableListTest
    extends TestCase
{

    /**
     * Create and return a new instance of ObservableList to test.
     *
     * @param <T> list element type
     * @return a new instance of ObservableList to test
     */
    protected abstract <T> ObservableList<T> createObservableList();


    public void testListeners()
    {
        ObservableList<String> list = createObservableList();
        Listener<String> listener = new Listener<String>();
        list.addListChangeListener(listener);

        assertEquals(1, list.getListChangeListenerCount());
        assertNotNull(list.getListChangeListeners());
        assertEquals(1, list.getListChangeListeners().length);
        assertEquals(listener, list.getListChangeListeners()[0]);

        list.removeListChangeListener(listener);
        assertEquals(0, list.getListChangeListenerCount());
        assertNotNull(list.getListChangeListeners());
        assertEquals(0, list.getListChangeListeners().length);

        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        list.addVetoableListChangeListener(neverVetoListener);

        assertEquals(1, list.getVetoableListChangeListenerCount());
        assertNotNull(list.getVetoableListChangeListeners());
        assertEquals(1, list.getVetoableListChangeListeners().length);
        assertEquals(neverVetoListener, list.getVetoableListChangeListeners()[0]);

        list.removeVetoableListChangeListener(neverVetoListener);
        assertEquals(0, list.getVetoableListChangeListenerCount());
        assertNotNull(list.getVetoableListChangeListeners());
        assertEquals(0, list.getVetoableListChangeListeners().length);
    }

    public void testFireListWillChange()
        throws Exception
    {
        ObservableList<String> list = createObservableList();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        list.addVetoableListChangeListener(neverVetoListener);

        if (list instanceof AbstractObservableList)
        {
            ((AbstractObservableList) list).fireListWillChange();

            assertNotNull(neverVetoListener.getEvent());
            assertSame(list, neverVetoListener.getEvent().getObservableList());
        }
    }

    public void testFireListChanged()
    {
        ObservableList<String> list = createObservableList();
        Listener<String> listener = new Listener<String>();
        list.addListChangeListener(listener);

        if (list instanceof AbstractObservableList)
        {
            ((AbstractObservableList) list).fireListChanged();

            assertNotNull(listener.getEvent());
            assertSame(list, listener.getEvent().getObservableList());
        }
    }

    public void testCreateObservableList()
    {
        ObservableList<String> list0 = createObservableList();
        assertNotNull(list0);

        ObservableList<String> list1 = createObservableList();
        assertNotNull(list1);

        assertTrue(list1 != list0);
    }

    public void testAdd()
    {
        ObservableList<String> list = createObservableList();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        list.addListChangeListener(listener);
        list.addVetoableListChangeListener(neverVetoListener);

        assertTrue(list.add("foo"));
        assertTrue(list.contains("foo"));

        assertNotNull(listener.getEvent());
        assertSame(list, listener.getEvent().getObservableList()); // assertEquals failed!

        assertNotNull(neverVetoListener.getEvent());
        assertSame(list, neverVetoListener.getEvent().getObservableList());
    }

    public void testAddVeto()
    {
        ObservableList<String> list = createObservableList();
        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        list.addVetoableListChangeListener(alwaysVetoListener);

        assertFalse(list.add("foo"));
        assertFalse(list.contains("foo"));
    }

    public void testAddAtIndex()
    {
        ObservableList<String> list = createObservableList();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        list.addListChangeListener(listener);
        list.addVetoableListChangeListener(neverVetoListener);

        list.add(0, "foo");
        assertEquals("foo", list.get(0));
        assertTrue(list.contains("foo"));

        assertNotNull(listener.getEvent());
        assertSame(list, listener.getEvent().getObservableList());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(list, neverVetoListener.getEvent().getObservableList());
    }

    public void testAddAtIndexVeto()
    {
        ObservableList<String> list = createObservableList();
        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        list.addVetoableListChangeListener(alwaysVetoListener);

        list.add(0, "foo");
        assertFalse(list.contains("foo"));
    }

    public void testAddAll()
    {
        ObservableList<String> list = createObservableList();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        list.addListChangeListener(listener);
        list.addVetoableListChangeListener(neverVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        assertTrue(list.addAll(strings));
        for (String s : strings)
        {
            assertTrue(list.contains(s));
        }

        assertNotNull(listener.getEvent());
        assertSame(list, listener.getEvent().getObservableList());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(list, neverVetoListener.getEvent().getObservableList());
    }

    public void testAddAllVeto()
    {
        ObservableList<String> list = createObservableList();
        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        list.addVetoableListChangeListener(alwaysVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        assertFalse(list.addAll(strings));
        for (String s : strings)
        {
            assertFalse(list.contains(s));
        }
    }

    public void testAddAllAtIndex()
    {
        ObservableList<String> list = createObservableList();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        list.addListChangeListener(listener);
        list.addVetoableListChangeListener(neverVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        assertTrue(list.addAll(0, strings));
        for (String s : strings)
        {
            assertTrue(list.contains(s));
        }

        assertNotNull(listener.getEvent());
        assertSame(list, listener.getEvent().getObservableList());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(list, neverVetoListener.getEvent().getObservableList());
    }

    public void testAddAllAtIndexVeto()
    {
        ObservableList<String> list = createObservableList();
        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        list.addVetoableListChangeListener(alwaysVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        assertFalse(list.addAll(0, strings));
        for (String s : strings)
        {
            assertFalse(list.contains(s));
        }
    }

    public void testClear()
    {
        ObservableList<String> list = createObservableList();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        list.addListChangeListener(listener);
        list.addVetoableListChangeListener(neverVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        list.addAll(strings);
        for (String s : strings)
        {
            assertTrue(list.contains(s));
        }
        list.clear();
        for (String s : strings)
        {
            assertFalse(list.contains(s));
        }

        assertNotNull(listener.getEvent());
        assertSame(list, listener.getEvent().getObservableList());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(list, neverVetoListener.getEvent().getObservableList());
    }

    public void testClearVeto()
    {
        ObservableList<String> list = createObservableList();
        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        list.addAll(strings);
        for (String s : strings)
        {
            assertTrue(list.contains(s));
        }

        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        list.addVetoableListChangeListener(alwaysVetoListener);

        list.clear();
        for (String s : strings)
        {
            assertTrue(list.contains(s));
        }
    }

    public void testRemove()
    {
        ObservableList<String> list = createObservableList();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        list.addListChangeListener(listener);
        list.addVetoableListChangeListener(neverVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        list.addAll(strings);
        for (String s : strings)
        {
            assertTrue(list.contains(s));
        }
        assertTrue(list.remove("foo"));
        assertFalse(list.contains("foo"));

        assertNotNull(listener.getEvent());
        assertSame(list, listener.getEvent().getObservableList());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(list, neverVetoListener.getEvent().getObservableList());
    }

    public void testRemoveVeto()
    {
        ObservableList<String> list = createObservableList();
        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        list.addAll(strings);
        for (String s : strings)
        {
            assertTrue(list.contains(s));
        }

        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        list.addVetoableListChangeListener(alwaysVetoListener);

        assertFalse(list.remove("foo"));
        assertTrue(list.contains("foo"));
    }

    public void testRemoveIndex()
    {
        ObservableList<String> list = createObservableList();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        list.addListChangeListener(listener);
        list.addVetoableListChangeListener(neverVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        list.addAll(strings);
        for (String s : strings)
        {
            assertTrue(list.contains(s));
        }
        list.remove(0);
        assertFalse(list.contains("foo"));

        assertNotNull(listener.getEvent());
        assertSame(list, listener.getEvent().getObservableList());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(list, neverVetoListener.getEvent().getObservableList());
    }

    public void testRemoveIndexVeto()
    {
        ObservableList<String> list = createObservableList();
        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        list.addAll(strings);
        for (String s : strings)
        {
            assertTrue(list.contains(s));
        }

        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        list.addVetoableListChangeListener(alwaysVetoListener);

        list.remove(0);
        assertTrue(list.contains("foo"));
        assertEquals(3, list.size());
    }

    public void testRemoveAll()
    {
        ObservableList<String> list = createObservableList();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        list.addListChangeListener(listener);
        list.addVetoableListChangeListener(neverVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        list.addAll(strings);
        for (String s : strings)
        {
            assertTrue(list.contains(s));
        }
        assertTrue(list.removeAll(strings));
        for (String s : strings)
        {
            assertFalse(list.contains(s));
        }

        assertNotNull(listener.getEvent());
        assertSame(list, listener.getEvent().getObservableList());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(list, neverVetoListener.getEvent().getObservableList());
    }

    public void testRemoveAllVeto()
    {
        ObservableList<String> list = createObservableList();
        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        list.addAll(strings);
        for (String s : strings)
        {
            assertTrue(list.contains(s));
        }

        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        list.addVetoableListChangeListener(alwaysVetoListener);

        assertFalse(list.removeAll(strings));
        for (String s : strings)
        {
            assertTrue(list.contains(s));
        }
    }

    public void testRetainAll()
    {
        ObservableList<String> list = createObservableList();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        list.addListChangeListener(listener);
        list.addVetoableListChangeListener(neverVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        list.addAll(strings);
        for (String s : strings)
        {
            assertTrue(list.contains(s));
        }
        assertTrue(list.retainAll(Collections.singleton("foo")));
        assertTrue(list.contains("foo"));
        assertFalse(list.contains("bar"));
        assertFalse(list.contains("baz"));

        assertNotNull(listener.getEvent());
        assertSame(list, listener.getEvent().getObservableList());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(list, neverVetoListener.getEvent().getObservableList());
    }

    public void testRetainAllVeto()
    {
        ObservableList<String> list = createObservableList();
        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        list.addAll(strings);
        for (String s : strings)
        {
            assertTrue(list.contains(s));
        }

        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        list.addVetoableListChangeListener(alwaysVetoListener);

        assertFalse(list.retainAll(Collections.singleton("foo")));
        for (String s : strings)
        {
            assertTrue(list.contains(s));
        }
    }

    public void testIteratorRemove()
    {
        ObservableList<String> list = createObservableList();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        list.addListChangeListener(listener);
        list.addVetoableListChangeListener(neverVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        list.addAll(strings);
        for (String s : strings)
        {
            assertTrue(list.contains(s));
        }
        Iterator<String> iterator = list.iterator();
        String s = iterator.next();
        iterator.remove();
        assertFalse(list.contains(s));
        assertEquals(2, list.size());

        assertNotNull(listener.getEvent());
        assertSame(list, listener.getEvent().getObservableList());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(list, neverVetoListener.getEvent().getObservableList());
    }

    public void testIteratorRemoveVeto()
    {
        ObservableList<String> list = createObservableList();
        List<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        list.addAll(strings);
        for (String s : strings)
        {
            assertTrue(list.contains(s));
        }

        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        list.addVetoableListChangeListener(alwaysVetoListener);

        Iterator<String> iterator = list.iterator();
        String ignore = iterator.next();
        iterator.remove();
        for (String s : strings)
        {
            assertTrue(list.contains(s));
        }
    }

    public void testSet()
    {
        ObservableList<String> list = createObservableList();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        list.addListChangeListener(listener);
        list.addVetoableListChangeListener(neverVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        list.addAll(strings);
        String rv = list.set(0, "qux");
        assertEquals("qux", list.get(0));
        assertEquals("foo", rv);
        assertTrue(list.contains("qux"));
        assertTrue(list.contains("bar"));
        assertTrue(list.contains("baz"));

        assertNotNull(listener.getEvent());
        assertSame(list, listener.getEvent().getObservableList());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(list, neverVetoListener.getEvent().getObservableList());
    }

    public void testSetVeto()
    {
        ObservableList<String> list = createObservableList();
        List<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        list.addAll(strings);
        for (String s : strings)
        {
            assertTrue(list.contains(s));
        }

        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        list.addVetoableListChangeListener(alwaysVetoListener);

        String rv = list.set(0, "qux");
        assertFalse(list.contains("qux"));
        assertTrue(list.contains("foo"));
        assertEquals(null, rv);
        assertTrue(list.contains("bar"));
        assertTrue(list.contains("baz"));
    }

    public void testListIteratorRemove()
    {
        ObservableList<String> list = createObservableList();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        list.addListChangeListener(listener);
        list.addVetoableListChangeListener(neverVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        list.addAll(strings);
        for (String s : strings)
        {
            assertTrue(list.contains(s));
        }
        ListIterator<String> iterator = list.listIterator();
        String s = iterator.next();
        iterator.remove();
        assertFalse(list.contains(s));
        assertEquals(2, list.size());

        assertNotNull(listener.getEvent());
        assertSame(list, listener.getEvent().getObservableList());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(list, neverVetoListener.getEvent().getObservableList());
    }

    public void testListIteratorRemoveVeto()
    {
        ObservableList<String> list = createObservableList();
        List<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        list.addAll(strings);
        for (String s : strings)
        {
            assertTrue(list.contains(s));
        }

        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        list.addVetoableListChangeListener(alwaysVetoListener);

        ListIterator<String> iterator = list.listIterator();
        String ignore = iterator.next();
        iterator.remove();
        for (String s : strings)
        {
            assertTrue(list.contains(s));
        }
    }

    public void testListIteratorSet()
    {
        ObservableList<String> list = createObservableList();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        list.addListChangeListener(listener);
        list.addVetoableListChangeListener(neverVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        list.addAll(strings);
        for (String s : strings)
        {
            assertTrue(list.contains(s));
        }
        ListIterator<String> iterator = list.listIterator();
        String s = iterator.next();
        iterator.set("qux");
        assertFalse(list.contains(s));
        assertTrue(list.contains("qux"));
        assertEquals(3, list.size());

        assertNotNull(listener.getEvent());
        assertSame(list, listener.getEvent().getObservableList());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(list, neverVetoListener.getEvent().getObservableList());
    }

    public void testListIteratorSetVeto()
    {
        ObservableList<String> list = createObservableList();
        List<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        list.addAll(strings);
        for (String s : strings)
        {
            assertTrue(list.contains(s));
        }

        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        list.addVetoableListChangeListener(alwaysVetoListener);

        ListIterator<String> iterator = list.listIterator();
        String s = iterator.next();
        iterator.set("qux");
        assertTrue(list.contains(s));
        assertFalse(list.contains("qux"));
    }

    public void testListIteratorAtIndexRemove()
    {
        ObservableList<String> list = createObservableList();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        list.addListChangeListener(listener);
        list.addVetoableListChangeListener(neverVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        list.addAll(strings);
        for (String s : strings)
        {
            assertTrue(list.contains(s));
        }
        ListIterator<String> iterator = list.listIterator(0);
        String s = iterator.next();
        iterator.remove();
        assertFalse(list.contains(s));
        assertEquals(2, list.size());

        assertNotNull(listener.getEvent());
        assertSame(list, listener.getEvent().getObservableList());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(list, neverVetoListener.getEvent().getObservableList());
    }

    public void testListIteratorAtIndexRemoveVeto()
    {
        ObservableList<String> list = createObservableList();
        List<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        list.addAll(strings);
        for (String s : strings)
        {
            assertTrue(list.contains(s));
        }

        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        list.addVetoableListChangeListener(alwaysVetoListener);

        ListIterator<String> iterator = list.listIterator(0);
        String ignore = iterator.next();
        iterator.remove();
        for (String s : strings)
        {
            assertTrue(list.contains(s));
        }
    }

    public void testListIteratorAtIndexSet()
    {
        ObservableList<String> list = createObservableList();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        list.addListChangeListener(listener);
        list.addVetoableListChangeListener(neverVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        list.addAll(strings);
        for (String s : strings)
        {
            assertTrue(list.contains(s));
        }
        ListIterator<String> iterator = list.listIterator(0);
        String s = iterator.next();
        iterator.set("qux");
        assertFalse(list.contains(s));
        assertTrue(list.contains("qux"));
        assertEquals(3, list.size());

        assertNotNull(listener.getEvent());
        assertSame(list, listener.getEvent().getObservableList());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(list, neverVetoListener.getEvent().getObservableList());
    }

    public void testListIteratorAtIndexSetVeto()
    {
        ObservableList<String> list = createObservableList();
        List<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        list.addAll(strings);
        for (String s : strings)
        {
            assertTrue(list.contains(s));
        }

        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        list.addVetoableListChangeListener(alwaysVetoListener);

        ListIterator<String> iterator = list.listIterator(0);
        String s = iterator.next();
        iterator.set("qux");
        assertTrue(list.contains(s));
        assertFalse(list.contains("qux"));
    }

    public void testSubListAdd()
    {
        ObservableList<String> list = createObservableList();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        list.addListChangeListener(listener);
        list.addVetoableListChangeListener(neverVetoListener);

        List<String> subList = list.subList(0, 0);
        assertTrue(subList.add("foo"));
        assertTrue(subList.contains("foo"));
        assertTrue(list.contains("foo"));

        assertNotNull(listener.getEvent());
        assertSame(list, listener.getEvent().getObservableList()); // assertEquals failed!

        assertNotNull(neverVetoListener.getEvent());
        assertSame(list, neverVetoListener.getEvent().getObservableList());
    }

    public void testSubListAddVeto()
    {
        ObservableList<String> list = createObservableList();
        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        list.addVetoableListChangeListener(alwaysVetoListener);

        List<String> subList = list.subList(0, 0);
        assertFalse(subList.add("foo"));
        assertFalse(subList.contains("foo"));
        assertFalse(list.contains("foo"));
    }

    public void testSubListAddAtIndex()
    {
        ObservableList<String> list = createObservableList();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        list.addListChangeListener(listener);
        list.addVetoableListChangeListener(neverVetoListener);

        List<String> subList = list.subList(0, 0);
        subList.add(0, "foo");
        assertEquals("foo", subList.get(0));
        assertTrue(subList.contains("foo"));
        assertTrue(list.contains("foo"));

        assertNotNull(listener.getEvent());
        assertSame(list, listener.getEvent().getObservableList());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(list, neverVetoListener.getEvent().getObservableList());
    }

    public void testSubListAddAtIndexVeto()
    {
        ObservableList<String> list = createObservableList();
        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        list.addVetoableListChangeListener(alwaysVetoListener);

        List<String> subList = list.subList(0, 0);
        subList.add(0, "foo");
        assertFalse(subList.contains("foo"));
        assertFalse(list.contains("foo"));
    }

    public void testSubListAddAll()
    {
        ObservableList<String> list = createObservableList();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        list.addListChangeListener(listener);
        list.addVetoableListChangeListener(neverVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        List<String> subList = list.subList(0, 0);
        assertTrue(subList.addAll(strings));
        for (String s : strings)
        {
            assertTrue(subList.contains(s));
            assertTrue(list.contains(s));
        }

        assertNotNull(listener.getEvent());
        assertSame(list, listener.getEvent().getObservableList());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(list, neverVetoListener.getEvent().getObservableList());
    }

    public void testSubListAddAllVeto()
    {
        ObservableList<String> list = createObservableList();
        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        list.addVetoableListChangeListener(alwaysVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        List<String> subList = list.subList(0, 0);
        assertFalse(subList.addAll(strings));
        for (String s : strings)
        {
            assertFalse(subList.contains(s));
            assertFalse(list.contains(s));
        }
    }

    public void testSubListAddAllAtIndex()
    {
        ObservableList<String> list = createObservableList();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        list.addListChangeListener(listener);
        list.addVetoableListChangeListener(neverVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        List<String> subList = list.subList(0, 0);
        assertTrue(subList.addAll(0, strings));
        for (String s : strings)
        {
            assertTrue(subList.contains(s));
            assertTrue(list.contains(s));
        }

        assertNotNull(listener.getEvent());
        assertSame(list, listener.getEvent().getObservableList());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(list, neverVetoListener.getEvent().getObservableList());
    }

    public void testSubListAddAllAtIndexVeto()
    {
        ObservableList<String> list = createObservableList();
        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        list.addVetoableListChangeListener(alwaysVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        List<String> subList = list.subList(0, 0);
        assertFalse(subList.addAll(0, strings));
        for (String s : strings)
        {
            assertFalse(subList.contains(s));
            assertFalse(list.contains(s));
        }
    }

    public void testSubListClear()
    {
        ObservableList<String> list = createObservableList();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        list.addListChangeListener(listener);
        list.addVetoableListChangeListener(neverVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        list.addAll(strings);
        List<String> subList = list.subList(0, list.size());
        for (String s : strings)
        {
            assertTrue(subList.contains(s));
            assertTrue(list.contains(s));
        }
        subList.clear();
        for (String s : strings)
        {
            assertFalse(subList.contains(s));
            assertFalse(list.contains(s));
        }

        assertNotNull(listener.getEvent());
        assertSame(list, listener.getEvent().getObservableList());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(list, neverVetoListener.getEvent().getObservableList());
    }

    public void testSubListClearVeto()
    {
        ObservableList<String> list = createObservableList();
        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        list.addAll(strings);
        List<String> subList = list.subList(0, list.size());
        for (String s : strings)
        {
            assertTrue(subList.contains(s));
            assertTrue(list.contains(s));
        }

        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        list.addVetoableListChangeListener(alwaysVetoListener);

        subList.clear();
        for (String s : strings)
        {
            assertTrue(subList.contains(s));
            assertTrue(list.contains(s));
        }
    }

    public void testSubListRemove()
    {
        ObservableList<String> list = createObservableList();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        list.addListChangeListener(listener);
        list.addVetoableListChangeListener(neverVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        list.addAll(strings);
        List<String> subList = list.subList(0, list.size());
        for (String s : strings)
        {
            assertTrue(subList.contains(s));
            assertTrue(list.contains(s));
        }
        assertTrue(subList.remove("foo"));
        assertFalse(subList.contains("foo"));
        assertFalse(list.contains("foo"));

        assertNotNull(listener.getEvent());
        assertSame(list, listener.getEvent().getObservableList());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(list, neverVetoListener.getEvent().getObservableList());
    }

    public void testSubListRemoveVeto()
    {
        ObservableList<String> list = createObservableList();
        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        list.addAll(strings);
        List<String> subList = list.subList(0, list.size());
        for (String s : strings)
        {
            assertTrue(subList.contains(s));
            assertTrue(list.contains(s));
        }

        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        list.addVetoableListChangeListener(alwaysVetoListener);

        assertFalse(subList.remove("foo"));
        assertTrue(subList.contains("foo"));
        assertTrue(list.contains("foo"));
    }

    public void testSubListRemoveIndex()
    {
        ObservableList<String> list = createObservableList();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        list.addListChangeListener(listener);
        list.addVetoableListChangeListener(neverVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        list.addAll(strings);
        List<String> subList = list.subList(0, list.size());
        for (String s : strings)
        {
            assertTrue(subList.contains(s));
            assertTrue(list.contains(s));
        }
        subList.remove(0);
        assertFalse(subList.contains("foo"));
        assertFalse(list.contains("foo"));

        assertNotNull(listener.getEvent());
        assertSame(list, listener.getEvent().getObservableList());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(list, neverVetoListener.getEvent().getObservableList());
    }

    public void testSubListRemoveIndexVeto()
    {
        ObservableList<String> list = createObservableList();
        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        list.addAll(strings);
        List<String> subList = list.subList(0, list.size());
        for (String s : strings)
        {
            assertTrue(subList.contains(s));
            assertTrue(list.contains(s));
        }

        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        list.addVetoableListChangeListener(alwaysVetoListener);

        subList.remove(0);
        assertTrue(subList.contains("foo"));
        assertTrue(list.contains("foo"));
        assertEquals(3, subList.size());
        assertEquals(3, list.size());
    }

    public void testSubListRemoveAll()
    {
        ObservableList<String> list = createObservableList();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        list.addListChangeListener(listener);
        list.addVetoableListChangeListener(neverVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        list.addAll(strings);
        List<String> subList = list.subList(0, list.size());
        for (String s : strings)
        {
            assertTrue(subList.contains(s));
            assertTrue(list.contains(s));
        }
        assertTrue(subList.removeAll(strings));
        for (String s : strings)
        {
            assertFalse(subList.contains(s));
            assertFalse(list.contains(s));
        }

        assertNotNull(listener.getEvent());
        assertSame(list, listener.getEvent().getObservableList());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(list, neverVetoListener.getEvent().getObservableList());
    }

    public void testSubListRemoveAllVeto()
    {
        ObservableList<String> list = createObservableList();
        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        list.addAll(strings);
        List<String> subList = list.subList(0, list.size());
        for (String s : strings)
        {
            assertTrue(subList.contains(s));
            assertTrue(list.contains(s));
        }

        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        list.addVetoableListChangeListener(alwaysVetoListener);

        assertFalse(subList.removeAll(strings));
        for (String s : strings)
        {
            assertTrue(subList.contains(s));
            assertTrue(list.contains(s));
        }
    }

    public void testSubListRetainAll()
    {
        ObservableList<String> list = createObservableList();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        list.addListChangeListener(listener);
        list.addVetoableListChangeListener(neverVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        list.addAll(strings);
        List<String> subList = list.subList(0, list.size());
        for (String s : strings)
        {
            assertTrue(subList.contains(s));
            assertTrue(list.contains(s));
        }
        assertTrue(subList.retainAll(Collections.singleton("foo")));
        assertTrue(subList.contains("foo"));
        assertFalse(subList.contains("bar"));
        assertFalse(subList.contains("baz"));
        assertTrue(list.contains("foo"));
        assertFalse(list.contains("bar"));
        assertFalse(list.contains("baz"));

        assertNotNull(listener.getEvent());
        assertSame(list, listener.getEvent().getObservableList());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(list, neverVetoListener.getEvent().getObservableList());
    }

    public void testSubListRetainAllVeto()
    {
        ObservableList<String> list = createObservableList();
        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        list.addAll(strings);
        List<String> subList = list.subList(0, list.size());
        for (String s : strings)
        {
            assertTrue(subList.contains(s));
            assertTrue(list.contains(s));
        }

        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        list.addVetoableListChangeListener(alwaysVetoListener);

        assertFalse(subList.retainAll(Collections.singleton("foo")));
        for (String s : strings)
        {
            assertTrue(subList.contains(s));
            assertTrue(list.contains(s));
        }
    }

    public void testSubListIteratorRemove()
    {
        ObservableList<String> list = createObservableList();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        list.addListChangeListener(listener);
        list.addVetoableListChangeListener(neverVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        list.addAll(strings);
        List<String> subList = list.subList(0, list.size());
        for (String s : strings)
        {
            assertTrue(subList.contains(s));
            assertTrue(list.contains(s));
        }
        Iterator<String> iterator = subList.iterator();
        String s = iterator.next();
        iterator.remove();
        assertFalse(subList.contains(s));
        assertEquals(2, subList.size());
        assertFalse(list.contains(s));
        assertEquals(2, list.size());

        assertNotNull(listener.getEvent());
        assertSame(list, listener.getEvent().getObservableList());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(list, neverVetoListener.getEvent().getObservableList());
    }

    public void testSubListIteratorRemoveVeto()
    {
        ObservableList<String> list = createObservableList();
        List<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        list.addAll(strings);
        List<String> subList = list.subList(0, list.size());
        for (String s : strings)
        {
            assertTrue(subList.contains(s));
            assertTrue(list.contains(s));
        }

        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        list.addVetoableListChangeListener(alwaysVetoListener);

        Iterator<String> iterator = subList.iterator();
        String ignore = iterator.next();
        iterator.remove();
        for (String s : strings)
        {
            assertTrue(subList.contains(s));
            assertTrue(list.contains(s));
        }
    }

    public void testSubListSet()
    {
        ObservableList<String> list = createObservableList();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        list.addListChangeListener(listener);
        list.addVetoableListChangeListener(neverVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        list.addAll(strings);
        List<String> subList = list.subList(0, list.size());
        String rv = subList.set(0, "qux");
        assertEquals("qux", subList.get(0));
        assertEquals("foo", rv);
        assertTrue(subList.contains("qux"));
        assertTrue(subList.contains("bar"));
        assertTrue(subList.contains("baz"));
        assertTrue(list.contains("qux"));
        assertTrue(list.contains("bar"));
        assertTrue(list.contains("baz"));

        assertNotNull(listener.getEvent());
        assertSame(list, listener.getEvent().getObservableList());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(list, neverVetoListener.getEvent().getObservableList());
    }

    public void testSubListSetVeto()
    {
        ObservableList<String> list = createObservableList();
        List<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        list.addAll(strings);
        List<String> subList = list.subList(0, list.size());
        for (String s : strings)
        {
            assertTrue(subList.contains(s));
            assertTrue(list.contains(s));
        }

        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        list.addVetoableListChangeListener(alwaysVetoListener);

        String rv = subList.set(0, "qux");
        assertFalse(subList.contains("qux"));
        assertTrue(subList.contains("foo"));
        assertEquals(null, rv);
        assertTrue(subList.contains("bar"));
        assertTrue(subList.contains("baz"));
        assertTrue(list.contains("bar"));
        assertTrue(list.contains("baz"));
    }

    public void testSubListListIteratorRemove()
    {
        ObservableList<String> list = createObservableList();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        list.addListChangeListener(listener);
        list.addVetoableListChangeListener(neverVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        list.addAll(strings);
        List<String> subList = list.subList(0, list.size());
        for (String s : strings)
        {
            assertTrue(subList.contains(s));
            assertTrue(list.contains(s));
        }
        ListIterator<String> iterator = subList.listIterator();
        String s = iterator.next();
        iterator.remove();
        assertFalse(subList.contains(s));
        assertEquals(2, subList.size());
        assertFalse(list.contains(s));
        assertEquals(2, list.size());

        assertNotNull(listener.getEvent());
        assertSame(list, listener.getEvent().getObservableList());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(list, neverVetoListener.getEvent().getObservableList());
    }

    public void testSubListListIteratorRemoveVeto()
    {
        ObservableList<String> list = createObservableList();
        List<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        list.addAll(strings);
        List<String> subList = list.subList(0, list.size());
        for (String s : strings)
        {
            assertTrue(subList.contains(s));
            assertTrue(list.contains(s));
        }

        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        list.addVetoableListChangeListener(alwaysVetoListener);

        ListIterator<String> iterator = subList.listIterator();
        String ignore = iterator.next();
        iterator.remove();
        for (String s : strings)
        {
            assertTrue(subList.contains(s));
            assertTrue(list.contains(s));
        }
    }

    public void testSubListListIteratorSet()
    {
        ObservableList<String> list = createObservableList();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        list.addListChangeListener(listener);
        list.addVetoableListChangeListener(neverVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        list.addAll(strings);
        List<String> subList = list.subList(0, list.size());
        for (String s : strings)
        {
            assertTrue(subList.contains(s));
            assertTrue(list.contains(s));
        }
        ListIterator<String> iterator = subList.listIterator();
        String s = iterator.next();
        iterator.set("qux");
        assertFalse(subList.contains(s));
        assertTrue(subList.contains("qux"));
        assertEquals(3, subList.size());
        assertFalse(list.contains(s));
        assertTrue(list.contains("qux"));
        assertEquals(3, list.size());

        assertNotNull(listener.getEvent());
        assertSame(list, listener.getEvent().getObservableList());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(list, neverVetoListener.getEvent().getObservableList());
    }

    public void testSubListListIteratorSetVeto()
    {
        ObservableList<String> list = createObservableList();
        List<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        list.addAll(strings);
        List<String> subList = list.subList(0, list.size());
        for (String s : strings)
        {
            assertTrue(subList.contains(s));
            assertTrue(list.contains(s));
        }

        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        list.addVetoableListChangeListener(alwaysVetoListener);

        ListIterator<String> iterator = subList.listIterator();
        String s = iterator.next();
        iterator.set("qux");
        assertTrue(subList.contains(s));
        assertFalse(subList.contains("qux"));
        assertTrue(list.contains(s));
        assertFalse(list.contains("qux"));
    }

    public void testSubListListIteratorAtIndexRemove()
    {
        ObservableList<String> list = createObservableList();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        list.addListChangeListener(listener);
        list.addVetoableListChangeListener(neverVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        list.addAll(strings);
        List<String> subList = list.subList(0, list.size());
        for (String s : strings)
        {
            assertTrue(subList.contains(s));
            assertTrue(list.contains(s));
        }
        ListIterator<String> iterator = subList.listIterator(0);
        String s = iterator.next();
        iterator.remove();
        assertFalse(subList.contains(s));
        assertEquals(2, subList.size());
        assertFalse(list.contains(s));
        assertEquals(2, list.size());

        assertNotNull(listener.getEvent());
        assertSame(list, listener.getEvent().getObservableList());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(list, neverVetoListener.getEvent().getObservableList());
    }

    public void testSubListListIteratorAtIndexRemoveVeto()
    {
        ObservableList<String> list = createObservableList();
        List<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        list.addAll(strings);
        List<String> subList = list.subList(0, list.size());
        for (String s : strings)
        {
            assertTrue(subList.contains(s));
            assertTrue(list.contains(s));
        }

        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        list.addVetoableListChangeListener(alwaysVetoListener);

        ListIterator<String> iterator = subList.listIterator(0);
        String ignore = iterator.next();
        iterator.remove();
        for (String s : strings)
        {
            assertTrue(subList.contains(s));
            assertTrue(list.contains(s));
        }
    }

    public void testSubListListIteratorAtIndexSet()
    {
        ObservableList<String> list = createObservableList();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        list.addListChangeListener(listener);
        list.addVetoableListChangeListener(neverVetoListener);

        Collection<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        list.addAll(strings);
        List<String> subList = list.subList(0, list.size());
        for (String s : strings)
        {
            assertTrue(subList.contains(s));
            assertTrue(list.contains(s));
        }
        ListIterator<String> iterator = subList.listIterator(0);
        String s = iterator.next();
        iterator.set("qux");
        assertFalse(subList.contains(s));
        assertTrue(subList.contains("qux"));
        assertEquals(3, subList.size());
        assertFalse(list.contains(s));
        assertTrue(list.contains("qux"));
        assertEquals(3, list.size());

        assertNotNull(listener.getEvent());
        assertSame(list, listener.getEvent().getObservableList());

        assertNotNull(neverVetoListener.getEvent());
        assertSame(list, neverVetoListener.getEvent().getObservableList());
    }

    public void testSubListListIteratorAtIndexSetVeto()
    {
        ObservableList<String> list = createObservableList();
        List<String> strings = Arrays.asList(new String[] { "foo", "bar", "baz" });
        list.addAll(strings);
        List<String> subList = list.subList(0, list.size());
        for (String s : strings)
        {
            assertTrue(subList.contains(s));
            assertTrue(list.contains(s));
        }

        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        list.addVetoableListChangeListener(alwaysVetoListener);

        ListIterator<String> iterator = subList.listIterator(0);
        String s = iterator.next();
        iterator.set("qux");
        assertTrue(subList.contains(s));
        assertFalse(subList.contains("qux"));
        assertTrue(list.contains(s));
        assertFalse(list.contains("qux"));
    }

    // TODO:  remaining nested subLists methods, nested subList iterator, listIterator, ...

    public void testNestedSubListAdd()
    {
        ObservableList<String> list = createObservableList();
        Listener<String> listener = new Listener<String>();
        NeverVetoListener<String> neverVetoListener = new NeverVetoListener<String>();
        list.addListChangeListener(listener);
        list.addVetoableListChangeListener(neverVetoListener);

        List<String> subList = list.subList(0, 0);
        List<String> nestedSubList = subList.subList(0, 0);
        assertTrue(nestedSubList.add("foo"));
        assertTrue(nestedSubList.contains("foo"));
        assertTrue(subList.contains("foo"));
        assertTrue(list.contains("foo"));

        assertNotNull(listener.getEvent());
        assertSame(list, listener.getEvent().getObservableList()); // assertEquals failed!

        assertNotNull(neverVetoListener.getEvent());
        assertSame(list, neverVetoListener.getEvent().getObservableList());
    }

    public void testNestedSubListAddVeto()
    {
        ObservableList<String> list = createObservableList();
        AlwaysVetoListener<String> alwaysVetoListener = new AlwaysVetoListener<String>();
        list.addVetoableListChangeListener(alwaysVetoListener);

        List<String> subList = list.subList(0, 0);
        List<String> nestedSubList = subList.subList(0, 0);
        assertFalse(nestedSubList.add("foo"));
        assertFalse(nestedSubList.contains("foo"));
        assertFalse(subList.contains("foo"));
        assertFalse(list.contains("foo"));
    }


    /**
     * Listener.
     */
    protected class Listener<E>
        implements ListChangeListener<E> {

        /** Last event heard, if any. */
        private ListChangeEvent<E> event;


        /** {@inheritDoc} */
        public void listChanged(final ListChangeEvent<E> event)
        {
            this.event = event;
        }

        /**
         * Return the last heard event, if any.
         *
         * @return the last heard event, if any
         */
        ListChangeEvent<E> getEvent()
        {
            return event;
        }
    }

    /**
     * Always veto listener.
     */
    protected class AlwaysVetoListener<E>
        implements VetoableListChangeListener<E>
    {

        /** {@inheritDoc} */
        public void listWillChange(final VetoableListChangeEvent<E> event)
            throws ListChangeVetoException
        {
            throw new ListChangeVetoException();
        }
    }

    /**
     * Never veto listener.
     */
    protected class NeverVetoListener<E>
        implements VetoableListChangeListener<E>
    {
        /** Last event heard, if any. */
        private VetoableListChangeEvent<E> event;


        /** {@inheritDoc} */
        public void listWillChange(final VetoableListChangeEvent<E> event)
            throws ListChangeVetoException
        {
            this.event = event;
        }

        /**
         * Return the last heard event, if any.
         *
         * @return the last heard event, if any
         */
        VetoableListChangeEvent<E> getEvent()
        {
            return event;
        }
    }
}
