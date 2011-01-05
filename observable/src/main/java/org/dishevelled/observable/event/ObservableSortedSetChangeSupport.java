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
package org.dishevelled.observable.event;

import java.io.Serializable;

import javax.swing.event.EventListenerList;

import org.dishevelled.observable.ObservableSortedSet;

/**
 * A support class that can be used via delegation to provide
 * <code>SortedSetChangeListener</code> and <code>VetoableSortedSetChangeListener</code>
 * management.
 *
 * @param <E> sorted set element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class ObservableSortedSetChangeSupport<E>
    implements Serializable
{
    /** Event source. */
    private ObservableSortedSet<E> source;

    /** Listener list. */
    private final EventListenerList listenerList;


    /**
     * Create a new support class meant to be subclassed.
     */
    protected ObservableSortedSetChangeSupport()
    {
        listenerList = new EventListenerList();
    }

    /**
     * Create a new support class that fires sorted set change and
     * vetoable sorted set change events with the specified source as the
     * source of the events.
     *
     * @param source the event source
     */
    public ObservableSortedSetChangeSupport(final ObservableSortedSet<E> source)
    {
        this();
        setSource(source);
    }


    /**
     * Set the source of sorted set change and vetoable sorted set change events
     * to <code>source</code>.  Subclasses should call this method before
     * any of the <code>fireX</code> methods.
     *
     * @param source the event source
     */
    protected void setSource(final ObservableSortedSet<E> source)
    {
        this.source = source;
    }

    /**
     * Return the <code>EventListenerList</code> backing this
     * observable sorted set support class.
     *
     * @return the <code>EventListenerList</code> backing this
     *    observable sorted set support class.
     */
    protected final EventListenerList getEventListenerList()
    {
        return listenerList;
    }

    /**
     * Add the specified sorted set change listener.
     *
     * @param l sorted set change listener to add
     */
    public final void addSortedSetChangeListener(final SortedSetChangeListener<E> l)
    {
        listenerList.add(SortedSetChangeListener.class, l);
    }

    /**
     * Remove the specified sorted set change listener.
     *
     * @param l sorted set change listener to remove
     */
    public final void removeSortedSetChangeListener(final SortedSetChangeListener<E> l)
    {
        listenerList.remove(SortedSetChangeListener.class, l);
    }

    /**
     * Add the specified vetoable sorted set change listener.
     *
     * @param l vetoable sorted set change listener to add
     */
    public final void addVetoableSortedSetChangeListener(final VetoableSortedSetChangeListener<E> l)
    {
        listenerList.add(VetoableSortedSetChangeListener.class, l);
    }

    /**
     * Remove the specified vetoable sorted set change listener.
     *
     * @param l vetoable sorted set change listener to remove
     */
    public final void removeVetoableSortedSetChangeListener(final VetoableSortedSetChangeListener<E> l)
    {
        listenerList.remove(VetoableSortedSetChangeListener.class, l);
    }

    /**
     * Return an array of all <code>SortedSetChangeListener</code>s, or
     * an empty array if none are registered.
     *
     * @return an array of all <code>SortedSetChangeListener</code>s, or
     *    an empty array if none are registered
     */
    public final SortedSetChangeListener<E>[] getSortedSetChangeListeners()
    {
        return (SortedSetChangeListener<E>[]) listenerList.getListeners(SortedSetChangeListener.class);
    }

    /**
     * Return the number of <code>SortedSetChangeListener</code>s registered
     * to this observable sorted set support class.
     *
     * @return the number of <code>SortedSetChangeListener</code>s registered
     *    to this observable sorted set support class
     */
    public final int getSortedSetChangeListenerCount()
    {
        return listenerList.getListenerCount(SortedSetChangeListener.class);
    }

    /**
     * Return an array of all <code>VetoableSortedSetChangeListener</code>s,
     * or an empty array if none are registered.
     *
     * @return an array of all <code>VetoableSortedSetChangeListener</code>s,
     *    or an empty array if none are registered
     */
    public final VetoableSortedSetChangeListener<E>[] getVetoableSortedSetChangeListeners()
    {
        return (VetoableSortedSetChangeListener<E>[]) listenerList.getListeners(VetoableSortedSetChangeListener.class);
    }

    /**
     * Return the number of <code>VetoableSortedSetChangeListener</code>s
     * registered to this observable sorted set support class.
     *
     * @return the number of <code>VetoableSortedSetChangeListener</code>s
     *    registered to this observable sorted set support class
     */
    public final int getVetoableSortedSetChangeListenerCount()
    {
        return listenerList.getListenerCount(VetoableSortedSetChangeListener.class);
    }

    /**
     * Fire a will change event to all registered
     * <code>VetoableSortedSetChangeListener</code>s.
     *
     * @throws SortedSetChangeVetoException if any of the listeners veto the change
     */
    public void fireSortedSetWillChange()
        throws SortedSetChangeVetoException
    {
        Object[] listeners = listenerList.getListenerList();
        VetoableSortedSetChangeEvent<E> e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == VetoableSortedSetChangeListener.class)
            {
                // lazily create the event
                if (e == null)
                {
                    e = new VetoableSortedSetChangeEvent<E>(source);
                }
                ((VetoableSortedSetChangeListener<E>) listeners[i + 1]).sortedSetWillChange(e);
            }
        }
    }

    /**
     * Fire the specified will change event to all registered
     * <code>VetoableSortedSetChangeListener</code>s.
     *
     * @param e will change event
     * @throws SortedSetChangeVetoException if any of the listeners veto the change
     */
    public void fireSortedSetWillChange(final VetoableSortedSetChangeEvent<E> e)
        throws SortedSetChangeVetoException
    {
        Object[] listeners = listenerList.getListenerList();

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == VetoableSortedSetChangeListener.class)
            {
                ((VetoableSortedSetChangeListener<E>) listeners[i + 1]).sortedSetWillChange(e);
            }
        }
    }

    /**
     * Fire a change event to all registered <code>SortedSetChangeListener</code>s.
     */
    public void fireSortedSetChanged()
    {
        Object[] listeners = listenerList.getListenerList();
        SortedSetChangeEvent<E> e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == SortedSetChangeListener.class)
            {
                // lazily create the event
                if (e == null)
                {
                    e = new SortedSetChangeEvent<E>(source);
                }
                ((SortedSetChangeListener<E>) listeners[i + 1]).sortedSetChanged(e);
            }
        }
    }

    /**
     * Fire the specified change event to all registered
     * <code>SortedSetChangeListener</code>s.
     *
     * @param e change event
     */
    public void fireSortedSetChanged(final SortedSetChangeEvent<E> e)
    {
        Object[] listeners = listenerList.getListenerList();

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == SortedSetChangeListener.class)
            {
                ((SortedSetChangeListener<E>) listeners[i + 1]).sortedSetChanged(e);
            }
        }
    }
}
