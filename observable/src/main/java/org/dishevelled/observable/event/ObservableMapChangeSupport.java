/*

    dsh-observable  Observable decorators for collection and map interfaces.
    Copyright (c) 2003-2007 held jointly by the individual authors.

    This library is free software; you can redistribute it and/or modify it
    under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation; either version 2.1 of the License, or (at
    your option) any later version.

    This library is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; with out even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
    License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this library;  if not, write to the Free Software Foundation,
    Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA.

    > http://www.gnu.org/copyleft/lesser.html
    > http://www.opensource.org/licenses/lgpl-license.php

*/
package org.dishevelled.observable.event;

import java.io.Serializable;

import javax.swing.event.EventListenerList;

import org.dishevelled.observable.ObservableMap;

/**
 * A support class that can be used via delegation to provide
 * <code>MapChangeListener</code> and <code>VetoableMapChangeListener</code>
 * management.
 *
 * @param <K> map key type
 * @param <V> map value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class ObservableMapChangeSupport<K,V>
    implements Serializable
{
    /** Event source. */
    private ObservableMap<K,V> source;

    /** Listener list. */
    private final EventListenerList listenerList;


    /**
     * Create a new support class meant to be subclassed.
     */
    protected ObservableMapChangeSupport()
    {
        listenerList = new EventListenerList();
    }

    /**
     * Create a new support class that fires map change and
     * vetoable map change events with the specified source as the
     * source of the events.
     *
     * @param source the event source
     */
    public ObservableMapChangeSupport(final ObservableMap<K,V> source)
    {
        this();
        setSource(source);
    }


    /**
     * Set the source of map change and vetoable map change events
     * to <code>source</code>.  Subclasses should call this method before
     * any of the <code>fireX</code> methods.
     *
     * @param source the event source
     */
    protected void setSource(final ObservableMap<K,V> source)
    {
        this.source = source;
    }

    /**
     * Return the <code>EventListenerList</code> backing this
     * observable map support class.
     *
     * @return the <code>EventListenerList</code> backing this
     *    observable map support class.
     */
    protected final EventListenerList getEventListenerList()
    {
        return listenerList;
    }

    /**
     * Add the specified map change listener.
     *
     * @param l map change listener to add
     */
    public final void addMapChangeListener(final MapChangeListener<K,V> l)
    {
        listenerList.add(MapChangeListener.class, l);
    }

    /**
     * Remove the specified map change listener.
     *
     * @param l map change listener to remove
     */
    public final void removeMapChangeListener(final MapChangeListener<K,V> l)
    {
        listenerList.remove(MapChangeListener.class, l);
    }

    /**
     * Add the specified vetoable map change listener.
     *
     * @param l vetoable map change listener to add
     */
    public final void addVetoableMapChangeListener(final VetoableMapChangeListener<K,V> l)
    {
        listenerList.add(VetoableMapChangeListener.class, l);
    }

    /**
     * Remove the specified vetoable map change listener.
     *
     * @param l vetoable map change listener to remove
     */
    public final void removeVetoableMapChangeListener(final VetoableMapChangeListener<K,V> l)
    {
        listenerList.remove(VetoableMapChangeListener.class, l);
    }

    /**
     * Return an array of all <code>MapChangeListener</code>s, or
     * an empty array if none are registered.
     *
     * @return an array of all <code>MapChangeListener</code>s, or
     *    an empty array if none are registered
     */
    public final MapChangeListener<K,V>[] getMapChangeListeners()
    {
        return (MapChangeListener<K,V>[]) listenerList.getListeners(MapChangeListener.class);
    }

    /**
     * Return the number of <code>MapChangeListener</code>s registered
     * to this observable map support class.
     *
     * @return the number of <code>MapChangeListener</code>s registered
     *    to this observable map support class
     */
    public final int getMapChangeListenerCount()
    {
        return listenerList.getListenerCount(MapChangeListener.class);
    }

    /**
     * Return an array of all <code>VetoableMapChangeListener</code>s,
     * or an empty array if none are registered.
     *
     * @return an array of all <code>VetoableMapChangeListener</code>s,
     *    or an empty array if none are registered
     */
    public final VetoableMapChangeListener<K,V>[] getVetoableMapChangeListeners()
    {
        return (VetoableMapChangeListener<K,V>[]) listenerList.getListeners(VetoableMapChangeListener.class);
    }

    /**
     * Return the number of <code>VetoableMapChangeListener</code>s
     * registered to this observable map support class.
     *
     * @return the number of <code>VetoableMapChangeListener</code>s
     *    registered to this observable map support class
     */
    public final int getVetoableMapChangeListenerCount()
    {
        return listenerList.getListenerCount(VetoableMapChangeListener.class);
    }

    /**
     * Fire a will change event to all registered
     * <code>VetoableMapChangeListener</code>s.
     *
     * @throws MapChangeVetoException if any of the listeners veto the change
     */
    public void fireMapWillChange()
        throws MapChangeVetoException
    {
        Object[] listeners = listenerList.getListenerList();
        VetoableMapChangeEvent<K,V> e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == VetoableMapChangeListener.class)
            {
                // lazily create the event
                if (e == null)
                {
                    e = new VetoableMapChangeEvent<K,V>(source);
                }
                ((VetoableMapChangeListener<K,V>) listeners[i + 1]).mapWillChange(e);
            }
        }
    }

    /**
     * Fire the specified will change event to all registered
     * <code>VetoableMapChangeListener</code>s.
     *
     * @param e will change event
     * @throws MapChangeVetoException if any of the listeners veto the change
     */
    public void fireMapWillChange(final VetoableMapChangeEvent<K,V> e)
        throws MapChangeVetoException
    {
        Object[] listeners = listenerList.getListenerList();

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == VetoableMapChangeListener.class)
            {
                ((VetoableMapChangeListener<K,V>) listeners[i + 1]).mapWillChange(e);
            }
        }
    }

    /**
     * Fire a change event to all registered <code>MapChangeListener</code>s.
     */
    public void fireMapChanged()
    {
        Object[] listeners = listenerList.getListenerList();
        MapChangeEvent<K,V> e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == MapChangeListener.class)
            {
                // lazily create the event
                if (e == null)
                {
                    e = new MapChangeEvent<K,V>(source);
                }
                ((MapChangeListener<K,V>) listeners[i + 1]).mapChanged(e);
            }
        }
    }

    /**
     * Fire the specified change event to all registered
     * <code>MapChangeListener</code>s.
     *
     * @param e change event
     */
    public void fireMapChanged(final MapChangeEvent<K,V> e)
    {
        Object[] listeners = listenerList.getListenerList();

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == MapChangeListener.class)
            {
                ((MapChangeListener<K,V>) listeners[i + 1]).mapChanged(e);
            }
        }
    }
}
