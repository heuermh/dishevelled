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

import org.dishevelled.observable.ObservableNavigableSet;

/**
 * A support class that can be used via delegation to provide
 * <code>NavigableSetChangeListener</code> and <code>VetoableNavigableSetChangeListener</code>
 * management.
 *
 * @param <E> navigable set element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class ObservableNavigableSetChangeSupport<E>
    implements Serializable
{
    /** Event source. */
    private ObservableNavigableSet<E> source;

    /** Listener list. */
    private final EventListenerList listenerList;


    /**
     * Create a new support class meant to be subclassed.
     */
    protected ObservableNavigableSetChangeSupport()
    {
        listenerList = new EventListenerList();
    }

    /**
     * Create a new support class that fires navigable set change and
     * vetoable navigable set change events with the specified source as the
     * source of the events.
     *
     * @param source the event source
     */
    public ObservableNavigableSetChangeSupport(final ObservableNavigableSet<E> source)
    {
        this();
        setSource(source);
    }


    /**
     * Set the source of navigable set change and vetoable navigable set change events
     * to <code>source</code>.  Subclasses should call this method before
     * any of the <code>fireX</code> methods.
     *
     * @param source the event source
     */
    protected void setSource(final ObservableNavigableSet<E> source)
    {
        this.source = source;
    }

    /**
     * Return the <code>EventListenerList</code> backing this
     * observable navigable set support class.
     *
     * @return the <code>EventListenerList</code> backing this
     *    observable navigable set support class.
     */
    protected final EventListenerList getEventListenerList()
    {
        return listenerList;
    }

    /**
     * Add the specified navigable set change listener.
     *
     * @param l navigable set change listener to add
     */
    public final void addNavigableSetChangeListener(final NavigableSetChangeListener<E> l)
    {
        listenerList.add(NavigableSetChangeListener.class, l);
    }

    /**
     * Remove the specified navigable set change listener.
     *
     * @param l navigable set change listener to remove
     */
    public final void removeNavigableSetChangeListener(final NavigableSetChangeListener<E> l)
    {
        listenerList.remove(NavigableSetChangeListener.class, l);
    }

    /**
     * Add the specified vetoable navigable set change listener.
     *
     * @param l vetoable navigable set change listener to add
     */
    public final void addVetoableNavigableSetChangeListener(final VetoableNavigableSetChangeListener<E> l)
    {
        listenerList.add(VetoableNavigableSetChangeListener.class, l);
    }

    /**
     * Remove the specified vetoable navigable set change listener.
     *
     * @param l vetoable navigable set change listener to remove
     */
    public final void removeVetoableNavigableSetChangeListener(final VetoableNavigableSetChangeListener<E> l)
    {
        listenerList.remove(VetoableNavigableSetChangeListener.class, l);
    }

    /**
     * Return an array of all <code>NavigableSetChangeListener</code>s, or
     * an empty array if none are registered.
     *
     * @return an array of all <code>NavigableSetChangeListener</code>s, or
     *    an empty array if none are registered
     */
    public final NavigableSetChangeListener<E>[] getNavigableSetChangeListeners()
    {
        return (NavigableSetChangeListener<E>[]) listenerList.getListeners(NavigableSetChangeListener.class);
    }

    /**
     * Return the number of <code>NavigableSetChangeListener</code>s registered
     * to this observable navigable set support class.
     *
     * @return the number of <code>NavigableSetChangeListener</code>s registered
     *    to this observable navigable set support class
     */
    public final int getNavigableSetChangeListenerCount()
    {
        return listenerList.getListenerCount(NavigableSetChangeListener.class);
    }

    /**
     * Return an array of all <code>VetoableNavigableSetChangeListener</code>s,
     * or an empty array if none are registered.
     *
     * @return an array of all <code>VetoableNavigableSetChangeListener</code>s,
     *    or an empty array if none are registered
     */
    public final VetoableNavigableSetChangeListener<E>[] getVetoableNavigableSetChangeListeners()
    {
        return (VetoableNavigableSetChangeListener<E>[])
            listenerList.getListeners(VetoableNavigableSetChangeListener.class);
    }

    /**
     * Return the number of <code>VetoableNavigableSetChangeListener</code>s
     * registered to this observable navigable set support class.
     *
     * @return the number of <code>VetoableNavigableSetChangeListener</code>s
     *    registered to this observable navigable set support class
     */
    public final int getVetoableNavigableSetChangeListenerCount()
    {
        return listenerList.getListenerCount(VetoableNavigableSetChangeListener.class);
    }

    /**
     * Fire a will change event to all registered
     * <code>VetoableNavigableSetChangeListener</code>s.
     *
     * @throws NavigableSetChangeVetoException if any of the listeners veto the change
     */
    public void fireNavigableSetWillChange()
        throws NavigableSetChangeVetoException
    {
        Object[] listeners = listenerList.getListenerList();
        VetoableNavigableSetChangeEvent<E> e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == VetoableNavigableSetChangeListener.class)
            {
                // lazily create the event
                if (e == null)
                {
                    e = new VetoableNavigableSetChangeEvent<E>(source);
                }
                ((VetoableNavigableSetChangeListener<E>) listeners[i + 1]).navigableSetWillChange(e);
            }
        }
    }

    /**
     * Fire the specified will change event to all registered
     * <code>VetoableNavigableSetChangeListener</code>s.
     *
     * @param e will change event
     * @throws NavigableSetChangeVetoException if any of the listeners veto the change
     */
    public void fireNavigableSetWillChange(final VetoableNavigableSetChangeEvent<E> e)
        throws NavigableSetChangeVetoException
    {
        Object[] listeners = listenerList.getListenerList();

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == VetoableNavigableSetChangeListener.class)
            {
                ((VetoableNavigableSetChangeListener<E>) listeners[i + 1]).navigableSetWillChange(e);
            }
        }
    }

    /**
     * Fire a change event to all registered <code>NavigableSetChangeListener</code>s.
     */
    public void fireNavigableSetChanged()
    {
        Object[] listeners = listenerList.getListenerList();
        NavigableSetChangeEvent<E> e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == NavigableSetChangeListener.class)
            {
                // lazily create the event
                if (e == null)
                {
                    e = new NavigableSetChangeEvent<E>(source);
                }
                ((NavigableSetChangeListener<E>) listeners[i + 1]).navigableSetChanged(e);
            }
        }
    }

    /**
     * Fire the specified change event to all registered
     * <code>NavigableSetChangeListener</code>s.
     *
     * @param e change event
     */
    public void fireNavigableSetChanged(final NavigableSetChangeEvent<E> e)
    {
        Object[] listeners = listenerList.getListenerList();

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == NavigableSetChangeListener.class)
            {
                ((NavigableSetChangeListener<E>) listeners[i + 1]).navigableSetChanged(e);
            }
        }
    }
}
