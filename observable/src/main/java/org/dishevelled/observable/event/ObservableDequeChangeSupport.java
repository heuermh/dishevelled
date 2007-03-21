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

import org.dishevelled.observable.ObservableDeque;

/**
 * A support class that can be used via delegation to provide
 * <code>DequeChangeListener</code> and <code>VetoableDequeChangeListener</code>
 * management.
 *
 * @param <E> deque element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class ObservableDequeChangeSupport<E>
    implements Serializable
{
    /** Event source. */
    private ObservableDeque<E> source;

    /** Listener list. */
    private final EventListenerList listenerList;


    /**
     * Create a new support class meant to be subclassed.
     */
    protected ObservableDequeChangeSupport()
    {
        listenerList = new EventListenerList();
    }

    /**
     * Create a new support class that fires deque change and
     * vetoable deque change events with the specified source as the
     * source of the events.
     *
     * @param source the event source
     */
    public ObservableDequeChangeSupport(final ObservableDeque<E> source)
    {
        this();
        setSource(source);
    }


    /**
     * Set the source of deque change and vetoable deque change events
     * to <code>source</code>.  Subclasses should call this method before
     * any of the <code>fireX</code> methods.
     *
     * @param source the event source
     */
    protected void setSource(final ObservableDeque<E> source)
    {
        this.source = source;
    }

    /**
     * Return the <code>EventListenerList</code> backing this
     * observable deque support class.
     *
     * @return the <code>EventListenerList</code> backing this
     *    observable deque support class.
     */
    protected final EventListenerList getEventListenerList()
    {
        return listenerList;
    }

    /**
     * Add the specified deque change listener.
     *
     * @param l deque change listener to add
     */
    public final void addDequeChangeListener(final DequeChangeListener<E> l)
    {
        listenerList.add(DequeChangeListener.class, l);
    }

    /**
     * Remove the specified deque change listener.
     *
     * @param l deque change listener to remove
     */
    public final void removeDequeChangeListener(final DequeChangeListener<E> l)
    {
        listenerList.remove(DequeChangeListener.class, l);
    }

    /**
     * Add the specified vetoable deque change listener.
     *
     * @param l vetoable deque change listener to add
     */
    public final void addVetoableDequeChangeListener(final VetoableDequeChangeListener<E> l)
    {
        listenerList.add(VetoableDequeChangeListener.class, l);
    }

    /**
     * Remove the specified vetoable deque change listener.
     *
     * @param l vetoable deque change listener to remove
     */
    public final void removeVetoableDequeChangeListener(final VetoableDequeChangeListener<E> l)
    {
        listenerList.remove(VetoableDequeChangeListener.class, l);
    }

    /**
     * Return an array of all <code>DequeChangeListener</code>s, or
     * an empty array if none are registered.
     *
     * @return an array of all <code>DequeChangeListener</code>s, or
     *    an empty array if none are registered
     */
    public final DequeChangeListener<E>[] getDequeChangeListeners()
    {
        return (DequeChangeListener<E>[]) listenerList.getListeners(DequeChangeListener.class);
    }

    /**
     * Return the number of <code>DequeChangeListener</code>s registered
     * to this observable deque support class.
     *
     * @return the number of <code>DequeChangeListener</code>s registered
     *    to this observable deque support class
     */
    public final int getDequeChangeListenerCount()
    {
        return listenerList.getListenerCount(DequeChangeListener.class);
    }

    /**
     * Return an array of all <code>VetoableDequeChangeListener</code>s,
     * or an empty array if none are registered.
     *
     * @return an array of all <code>VetoableDequeChangeListener</code>s,
     *    or an empty array if none are registered
     */
    public final VetoableDequeChangeListener<E>[] getVetoableDequeChangeListeners()
    {
        return (VetoableDequeChangeListener<E>[]) listenerList.getListeners(VetoableDequeChangeListener.class);
    }

    /**
     * Return the number of <code>VetoableDequeChangeListener</code>s
     * registered to this observable deque support class.
     *
     * @return the number of <code>VetoableDequeChangeListener</code>s
     *    registered to this observable deque support class
     */
    public final int getVetoableDequeChangeListenerCount()
    {
        return listenerList.getListenerCount(VetoableDequeChangeListener.class);
    }

    /**
     * Fire a will change event to all registered
     * <code>VetoableDequeChangeListener</code>s.
     *
     * @throws DequeChangeVetoException if any of the listeners veto the change
     */
    public void fireDequeWillChange()
        throws DequeChangeVetoException
    {
        Object[] listeners = listenerList.getListenerList();
        VetoableDequeChangeEvent<E> e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == VetoableDequeChangeListener.class)
            {
                // lazily create the event
                if (e == null)
                {
                    e = new VetoableDequeChangeEvent<E>(source);
                }
                ((VetoableDequeChangeListener<E>) listeners[i + 1]).dequeWillChange(e);
            }
        }
    }

    /**
     * Fire the specified will change event to all registered
     * <code>VetoableDequeChangeListener</code>s.
     *
     * @param e will change event
     * @throws DequeChangeVetoException if any of the listeners veto the change
     */
    public void fireDequeWillChange(final VetoableDequeChangeEvent<E> e)
        throws DequeChangeVetoException
    {
        Object[] listeners = listenerList.getListenerList();

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == VetoableDequeChangeListener.class)
            {
                ((VetoableDequeChangeListener<E>) listeners[i + 1]).dequeWillChange(e);
            }
        }
    }

    /**
     * Fire a change event to all registered <code>DequeChangeListener</code>s.
     */
    public void fireDequeChanged()
    {
        Object[] listeners = listenerList.getListenerList();
        DequeChangeEvent<E> e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == DequeChangeListener.class)
            {
                // lazily create the event
                if (e == null)
                {
                    e = new DequeChangeEvent<E>(source);
                }
                ((DequeChangeListener<E>) listeners[i + 1]).dequeChanged(e);
            }
        }
    }

    /**
     * Fire the specified change event to all registered
     * <code>DequeChangeListener</code>s.
     *
     * @param e change event
     */
    public void fireDequeChanged(final DequeChangeEvent<E> e)
    {
        Object[] listeners = listenerList.getListenerList();

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == DequeChangeListener.class)
            {
                ((DequeChangeListener<E>) listeners[i + 1]).dequeChanged(e);
            }
        }
    }
}
