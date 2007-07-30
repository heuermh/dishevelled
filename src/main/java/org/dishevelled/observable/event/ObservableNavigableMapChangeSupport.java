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

import org.dishevelled.observable.ObservableNavigableMap;

/**
 * A support class that can be used via delegation to provide
 * <code>NavigableMapChangeListener</code> and <code>VetoableNavigableMapChangeListener</code>
 * management.
 *
 * @param <K> navigable map key type
 * @param <V> navigable map value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class ObservableNavigableMapChangeSupport<K,V>
    implements Serializable
{
    /** Event source. */
    private ObservableNavigableMap<K,V> source;

    /** Listener list. */
    private final EventListenerList listenerList;


    /**
     * Create a new support class meant to be subclassed.
     */
    protected ObservableNavigableMapChangeSupport()
    {
        listenerList = new EventListenerList();
    }

    /**
     * Create a new support class that fires navigable map change and
     * vetoable navigable map change events with the specified source as the
     * source of the events.
     *
     * @param source the event source
     */
    public ObservableNavigableMapChangeSupport(final ObservableNavigableMap<K,V> source)
    {
        this();
        setSource(source);
    }


    /**
     * Set the source of navigable map change and vetoable navigable map change events
     * to <code>source</code>.  Subclasses should call this method before
     * any of the <code>fireX</code> methods.
     *
     * @param source the event source
     */
    protected void setSource(final ObservableNavigableMap<K,V> source)
    {
        this.source = source;
    }

    /**
     * Return the <code>EventListenerList</code> backing this
     * observable navigable map support class.
     *
     * @return the <code>EventListenerList</code> backing this
     *    observable navigable map support class.
     */
    protected final EventListenerList getEventListenerList()
    {
        return listenerList;
    }

    /**
     * Add the specified navigable map change listener.
     *
     * @param l navigable map change listener to add
     */
    public final void addNavigableMapChangeListener(final NavigableMapChangeListener<K,V> l)
    {
        listenerList.add(NavigableMapChangeListener.class, l);
    }

    /**
     * Remove the specified navigable map change listener.
     *
     * @param l navigable map change listener to remove
     */
    public final void removeNavigableMapChangeListener(final NavigableMapChangeListener<K,V> l)
    {
        listenerList.remove(NavigableMapChangeListener.class, l);
    }

    /**
     * Add the specified vetoable navigable map change listener.
     *
     * @param l vetoable navigable map change listener to add
     */
    public final void addVetoableNavigableMapChangeListener(final VetoableNavigableMapChangeListener<K,V> l)
    {
        listenerList.add(VetoableNavigableMapChangeListener.class, l);
    }

    /**
     * Remove the specified vetoable navigable map change listener.
     *
     * @param l vetoable navigable map change listener to remove
     */
    public final void removeVetoableNavigableMapChangeListener(final VetoableNavigableMapChangeListener<K,V> l)
    {
        listenerList.remove(VetoableNavigableMapChangeListener.class, l);
    }

    /**
     * Return an array of all <code>NavigableMapChangeListener</code>s, or
     * an empty array if none are registered.
     *
     * @return an array of all <code>NavigableMapChangeListener</code>s, or
     *    an empty array if none are registered
     */
    public final NavigableMapChangeListener<K,V>[] getNavigableMapChangeListeners()
    {
        return (NavigableMapChangeListener<K,V>[]) listenerList.getListeners(NavigableMapChangeListener.class);
    }

    /**
     * Return the number of <code>NavigableMapChangeListener</code>s registered
     * to this observable navigable map support class.
     *
     * @return the number of <code>NavigableMapChangeListener</code>s registered
     *    to this observable navigable map support class
     */
    public final int getNavigableMapChangeListenerCount()
    {
        return listenerList.getListenerCount(NavigableMapChangeListener.class);
    }

    /**
     * Return an array of all <code>VetoableNavigableMapChangeListener</code>s,
     * or an empty array if none are registered.
     *
     * @return an array of all <code>VetoableNavigableMapChangeListener</code>s,
     *    or an empty array if none are registered
     */
    public final VetoableNavigableMapChangeListener<K,V>[] getVetoableNavigableMapChangeListeners()
    {
        return (VetoableNavigableMapChangeListener<K,V>[])
            listenerList.getListeners(VetoableNavigableMapChangeListener.class);
    }

    /**
     * Return the number of <code>VetoableNavigableMapChangeListener</code>s
     * registered to this observable navigable map support class.
     *
     * @return the number of <code>VetoableNavigableMapChangeListener</code>s
     *    registered to this observable navigable map support class
     */
    public final int getVetoableNavigableMapChangeListenerCount()
    {
        return listenerList.getListenerCount(VetoableNavigableMapChangeListener.class);
    }

    /**
     * Fire a will change event to all registered
     * <code>VetoableNavigableMapChangeListener</code>s.
     *
     * @throws NavigableMapChangeVetoException if any of the listeners veto the change
     */
    public void fireNavigableMapWillChange()
        throws NavigableMapChangeVetoException
    {
        Object[] listeners = listenerList.getListenerList();
        VetoableNavigableMapChangeEvent<K,V> e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == VetoableNavigableMapChangeListener.class)
            {
                // lazily create the event
                if (e == null)
                {
                    e = new VetoableNavigableMapChangeEvent<K,V>(source);
                }
                ((VetoableNavigableMapChangeListener<K,V>) listeners[i + 1]).navigableMapWillChange(e);
            }
        }
    }

    /**
     * Fire the specified will change event to all registered
     * <code>VetoableNavigableMapChangeListener</code>s.
     *
     * @param e will change event
     * @throws NavigableMapChangeVetoException if any of the listeners veto the change
     */
    public void fireNavigableMapWillChange(final VetoableNavigableMapChangeEvent<K,V> e)
        throws NavigableMapChangeVetoException
    {
        Object[] listeners = listenerList.getListenerList();

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == VetoableNavigableMapChangeListener.class)
            {
                ((VetoableNavigableMapChangeListener<K,V>) listeners[i + 1]).navigableMapWillChange(e);
            }
        }
    }

    /**
     * Fire a change event to all registered <code>NavigableMapChangeListener</code>s.
     */
    public void fireNavigableMapChanged()
    {
        Object[] listeners = listenerList.getListenerList();
        NavigableMapChangeEvent<K,V> e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == NavigableMapChangeListener.class)
            {
                // lazily create the event
                if (e == null)
                {
                    e = new NavigableMapChangeEvent<K,V>(source);
                }
                ((NavigableMapChangeListener<K,V>) listeners[i + 1]).navigableMapChanged(e);
            }
        }
    }

    /**
     * Fire the specified change event to all registered
     * <code>NavigableMapChangeListener</code>s.
     *
     * @param e change event
     */
    public void fireNavigableMapChanged(final NavigableMapChangeEvent<K,V> e)
    {
        Object[] listeners = listenerList.getListenerList();

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == NavigableMapChangeListener.class)
            {
                ((NavigableMapChangeListener<K,V>) listeners[i + 1]).navigableMapChanged(e);
            }
        }
    }
}
