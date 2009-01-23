/*

    dsh-observable  Observable decorators for collection and map interfaces.
    Copyright (c) 2003-2009 held jointly by the individual authors.

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

import org.dishevelled.observable.ObservableSortedMap;

/**
 * A support class that can be used via delegation to provide
 * <code>SortedMapChangeListener</code> and <code>VetoableSortedMapChangeListener</code>
 * management.
 *
 * @param <K> sorted map key type
 * @param <V> sorted map value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class ObservableSortedMapChangeSupport<K, V>
    implements Serializable
{
    /** Event source. */
    private ObservableSortedMap<K, V> source;

    /** Listener list. */
    private final EventListenerList listenerList;


    /**
     * Create a new support class meant to be subclassed.
     */
    protected ObservableSortedMapChangeSupport()
    {
        listenerList = new EventListenerList();
    }

    /**
     * Create a new support class that fires sorted map change and
     * vetoable sorted map change events with the specified source as the
     * source of the events.
     *
     * @param source the event source
     */
    public ObservableSortedMapChangeSupport(final ObservableSortedMap<K, V> source)
    {
        this();
        setSource(source);
    }


    /**
     * Set the source of sorted map change and vetoable sorted map change events
     * to <code>source</code>.  Subclasses should call this method before
     * any of the <code>fireX</code> methods.
     *
     * @param source the event source
     */
    protected void setSource(final ObservableSortedMap<K, V> source)
    {
        this.source = source;
    }

    /**
     * Return the <code>EventListenerList</code> backing this
     * observable sorted map support class.
     *
     * @return the <code>EventListenerList</code> backing this
     *    observable sorted map support class.
     */
    protected final EventListenerList getEventListenerList()
    {
        return listenerList;
    }

    /**
     * Add the specified sorted map change listener.
     *
     * @param l sorted map change listener to add
     */
    public final void addSortedMapChangeListener(final SortedMapChangeListener<K, V> l)
    {
        listenerList.add(SortedMapChangeListener.class, l);
    }

    /**
     * Remove the specified sorted map change listener.
     *
     * @param l sorted map change listener to remove
     */
    public final void removeSortedMapChangeListener(final SortedMapChangeListener<K, V> l)
    {
        listenerList.remove(SortedMapChangeListener.class, l);
    }

    /**
     * Add the specified vetoable sorted map change listener.
     *
     * @param l vetoable sorted map change listener to add
     */
    public final void addVetoableSortedMapChangeListener(final VetoableSortedMapChangeListener<K, V> l)
    {
        listenerList.add(VetoableSortedMapChangeListener.class, l);
    }

    /**
     * Remove the specified vetoable sorted map change listener.
     *
     * @param l vetoable sorted map change listener to remove
     */
    public final void removeVetoableSortedMapChangeListener(final VetoableSortedMapChangeListener<K, V> l)
    {
        listenerList.remove(VetoableSortedMapChangeListener.class, l);
    }

    /**
     * Return an array of all <code>SortedMapChangeListener</code>s, or
     * an empty array if none are registered.
     *
     * @return an array of all <code>SortedMapChangeListener</code>s, or
     *    an empty array if none are registered
     */
    public final SortedMapChangeListener<K, V>[] getSortedMapChangeListeners()
    {
        return (SortedMapChangeListener<K, V>[]) listenerList.getListeners(SortedMapChangeListener.class);
    }

    /**
     * Return the number of <code>SortedMapChangeListener</code>s registered
     * to this observable sorted map support class.
     *
     * @return the number of <code>SortedMapChangeListener</code>s registered
     *    to this observable sorted map support class
     */
    public final int getSortedMapChangeListenerCount()
    {
        return listenerList.getListenerCount(SortedMapChangeListener.class);
    }

    /**
     * Return an array of all <code>VetoableSortedMapChangeListener</code>s,
     * or an empty array if none are registered.
     *
     * @return an array of all <code>VetoableSortedMapChangeListener</code>s,
     *    or an empty array if none are registered
     */
    public final VetoableSortedMapChangeListener<K, V>[] getVetoableSortedMapChangeListeners()
    {
        return (VetoableSortedMapChangeListener<K, V>[])
            listenerList.getListeners(VetoableSortedMapChangeListener.class);
    }

    /**
     * Return the number of <code>VetoableSortedMapChangeListener</code>s
     * registered to this observable sorted map support class.
     *
     * @return the number of <code>VetoableSortedMapChangeListener</code>s
     *    registered to this observable sorted map support class
     */
    public final int getVetoableSortedMapChangeListenerCount()
    {
        return listenerList.getListenerCount(VetoableSortedMapChangeListener.class);
    }

    /**
     * Fire a will change event to all registered
     * <code>VetoableSortedMapChangeListener</code>s.
     *
     * @throws SortedMapChangeVetoException if any of the listeners veto the change
     */
    public void fireSortedMapWillChange()
        throws SortedMapChangeVetoException
    {
        Object[] listeners = listenerList.getListenerList();
        VetoableSortedMapChangeEvent<K, V> e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == VetoableSortedMapChangeListener.class)
            {
                // lazily create the event
                if (e == null)
                {
                    e = new VetoableSortedMapChangeEvent<K, V>(source);
                }
                ((VetoableSortedMapChangeListener<K, V>) listeners[i + 1]).sortedMapWillChange(e);
            }
        }
    }

    /**
     * Fire the specified will change event to all registered
     * <code>VetoableSortedMapChangeListener</code>s.
     *
     * @param e will change event
     * @throws SortedMapChangeVetoException if any of the listeners veto the change
     */
    public void fireSortedMapWillChange(final VetoableSortedMapChangeEvent<K, V> e)
        throws SortedMapChangeVetoException
    {
        Object[] listeners = listenerList.getListenerList();

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == VetoableSortedMapChangeListener.class)
            {
                ((VetoableSortedMapChangeListener<K, V>) listeners[i + 1]).sortedMapWillChange(e);
            }
        }
    }

    /**
     * Fire a change event to all registered <code>SortedMapChangeListener</code>s.
     */
    public void fireSortedMapChanged()
    {
        Object[] listeners = listenerList.getListenerList();
        SortedMapChangeEvent<K, V> e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == SortedMapChangeListener.class)
            {
                // lazily create the event
                if (e == null)
                {
                    e = new SortedMapChangeEvent<K, V>(source);
                }
                ((SortedMapChangeListener<K, V>) listeners[i + 1]).sortedMapChanged(e);
            }
        }
    }

    /**
     * Fire the specified change event to all registered
     * <code>SortedMapChangeListener</code>s.
     *
     * @param e change event
     */
    public void fireSortedMapChanged(final SortedMapChangeEvent<K, V> e)
    {
        Object[] listeners = listenerList.getListenerList();

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == SortedMapChangeListener.class)
            {
                ((SortedMapChangeListener<K, V>) listeners[i + 1]).sortedMapChanged(e);
            }
        }
    }
}
