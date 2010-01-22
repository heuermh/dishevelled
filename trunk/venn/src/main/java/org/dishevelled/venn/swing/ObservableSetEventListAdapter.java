/*

    dsh-venn  Lightweight components for venn diagrams.
    Copyright (c) 2009-2010 held jointly by the individual authors.

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
package org.dishevelled.venn.swing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;

import ca.odell.glazedlists.event.ListEventListener;
import ca.odell.glazedlists.event.ListEventPublisher;

import ca.odell.glazedlists.util.concurrent.ReadWriteLock;

import org.dishevelled.observable.ObservableSet;

import org.dishevelled.observable.event.SetChangeEvent;
import org.dishevelled.observable.event.SetChangeListener;

/**
 * Adapts an ObservableSet to an EventList.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
class ObservableSetEventListAdapter<E>
    implements EventList<E>
{
    /** Event list. */
    private final EventList<E> eventList;

    /** Observable set. */
    private final ObservableSet<E> observableSet;

    /** Update event list. */
    private final SetChangeListener<E> updateEventList = new SetChangeListener<E>()
    {
        /** {@inheritDoc} */
        public void setChanged(final SetChangeEvent<E> event)
        {
            updateEventList();
        }
    };


    /**
     * Create a new ObservableSet to EventList adapter for the specified observable set.
     *
     * @param observableSet observable set, must not be null
     */
    ObservableSetEventListAdapter(final ObservableSet<E> observableSet)
    {
        if (observableSet == null)
        {
            throw new IllegalArgumentException("set must not be null");
        }
        this.observableSet = observableSet;
        eventList = GlazedLists.eventList(this.observableSet);
        this.observableSet.addSetChangeListener(updateEventList);
    }


    /**
     * Update event list.
     */
    private void updateEventList()
    {
        if (observableSet.size() > eventList.size())
        {
            addToEventList();
        }
        else if (observableSet.size() < eventList.size())
        {
            removeFromEventList();
        }
        else
        {
            checkContents();
        }
    }

    /**
     * Add elements to event list as necessary.
     */
    private void addToEventList()
    {
        for (E e : observableSet)
        {
            if (!eventList.contains(e))
            {
                eventList.add(e);
            }
        }
    }

    /**
     * Remove elements from event list as necessary.
     */
    private void removeFromEventList()
    {
        List<E> toRemove = new ArrayList<E>();
        for (E e : eventList)
        {
            if (!observableSet.contains(e))
            {
                toRemove.add(e);
            }
        }
        eventList.removeAll(toRemove);
    }

    /**
     * Check contents of both the observable set and event list.
     */
    private void checkContents()
    {
        // todo  how often would this happen in a set?
    }

    /** {@inheritDoc} */
    public void addListEventListener(final ListEventListener<? super E> listener)
    {
        eventList.addListEventListener(listener);
    }


    /** {@inheritDoc} */
    public void dispose()
    {
        eventList.dispose();
        observableSet.removeSetChangeListener(updateEventList);
    }


    /** {@inheritDoc} */
    public ListEventPublisher getPublisher()
    {
        return eventList.getPublisher();
    }


    /** {@inheritDoc} */
    public ReadWriteLock getReadWriteLock()
    {
        return eventList.getReadWriteLock();
    }


    /** {@inheritDoc} */
    public void removeListEventListener(final ListEventListener<? super E> listener)
    {
        eventList.removeListEventListener(listener);
    }


    /** {@inheritDoc} */
    public boolean add(final E o)
    {
        throw new UnsupportedOperationException();
    }


    /** {@inheritDoc} */
    public void add(final int index, final E element)
    {
        throw new UnsupportedOperationException();
    }


    /** {@inheritDoc} */
    public boolean addAll(final Collection<? extends E> c)
    {
        throw new UnsupportedOperationException();
    }


    /** {@inheritDoc} */
    public boolean addAll(final int index, final Collection<? extends E> c)
    {
        throw new UnsupportedOperationException();
    }


    /** {@inheritDoc} */
    public void clear()
    {
        throw new UnsupportedOperationException();
    }


    /** {@inheritDoc} */
    public boolean contains(final Object o)
    {
        return eventList.contains(o);
    }


    /** {@inheritDoc} */
    public boolean containsAll(final Collection<?> c)
    {
        return eventList.containsAll(c);
    }


    /** {@inheritDoc} */
    public E get(int index)
    {
        return eventList.get(index);
    }


    /** {@inheritDoc} */
    public int indexOf(final Object o)
    {
        return eventList.indexOf(o);
    }


    /** {@inheritDoc} */
    public boolean isEmpty()
    {
        return eventList.isEmpty();
    }


    /** {@inheritDoc} */
    public Iterator<E> iterator()
    {
        return eventList.iterator();
    }


    /** {@inheritDoc} */
    public int lastIndexOf(final Object o)
    {
        return eventList.lastIndexOf(o);
    }


    /** {@inheritDoc} */
    public ListIterator<E> listIterator()
    {
        return eventList.listIterator();
    }


    /** {@inheritDoc} */
    public ListIterator<E> listIterator(final int index)
    {
        return eventList.listIterator(index);
    }

    /** {@inheritDoc} */
    public boolean remove(final Object o)
    {
        throw new UnsupportedOperationException();
    }

    /** {@inheritDoc} */
    public E remove(final int index)
    {
        throw new UnsupportedOperationException();
    }

    /** {@inheritDoc} */
    public boolean removeAll(final Collection<?> c)
    {
        throw new UnsupportedOperationException();
    }

    /** {@inheritDoc} */
    public boolean retainAll(final Collection<?> c)
    {
        throw new UnsupportedOperationException();
    }

    /** {@inheritDoc} */
    public E set(final int index, final E element)
    {
        throw new UnsupportedOperationException();
    }

    /** {@inheritDoc} */
    public int size()
    {
        return eventList.size();
    }

    /** {@inheritDoc} */
    public List<E> subList(final int fromIndex, final int toIndex)
    {
        return eventList.subList(fromIndex, toIndex);
    }

    /** {@inheritDoc} */
    public Object[] toArray()
    {
        return eventList.toArray();
    }

    /** {@inheritDoc} */
    public <T> T[] toArray(final T[] a)
    {
        return eventList.toArray(a);
    }
}
