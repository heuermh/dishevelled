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
package org.dishevelled.observable.event;

import java.io.Serializable;

import javax.swing.event.EventListenerList;

import org.dishevelled.observable.ObservableQueue;

/**
 * A support class that can be used via delegation to provide
 * <code>QueueChangeListener</code> and <code>VetoableQueueChangeListener</code>
 * management.
 *
 * @param <E> queue element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class ObservableQueueChangeSupport<E>
    implements Serializable
{
    /** Event source. */
    private ObservableQueue<E> source;

    /** Listener list. */
    private final EventListenerList listenerList;


    /**
     * Create a new support class meant to be subclassed.
     */
    protected ObservableQueueChangeSupport()
    {
        listenerList = new EventListenerList();
    }

    /**
     * Create a new support class that fires queue change and
     * vetoable queue change events with the specified source as the
     * source of the events.
     *
     * @param source the event source
     */
    public ObservableQueueChangeSupport(final ObservableQueue<E> source)
    {
        this();
        setSource(source);
    }


    /**
     * Set the source of queue change and vetoable queue change events
     * to <code>source</code>.  Subclasses should call this method before
     * any of the <code>fireX</code> methods.
     *
     * @param source the event source
     */
    protected void setSource(final ObservableQueue<E> source)
    {
        this.source = source;
    }

    /**
     * Return the <code>EventListenerList</code> backing this
     * observable queue support class.
     *
     * @return the <code>EventListenerList</code> backing this
     *    observable queue support class.
     */
    protected final EventListenerList getEventListenerList()
    {
        return listenerList;
    }

    /**
     * Add the specified queue change listener.
     *
     * @param l queue change listener to add
     */
    public final void addQueueChangeListener(final QueueChangeListener<E> l)
    {
        listenerList.add(QueueChangeListener.class, l);
    }

    /**
     * Remove the specified queue change listener.
     *
     * @param l queue change listener to remove
     */
    public final void removeQueueChangeListener(final QueueChangeListener<E> l)
    {
        listenerList.remove(QueueChangeListener.class, l);
    }

    /**
     * Add the specified vetoable queue change listener.
     *
     * @param l vetoable queue change listener to add
     */
    public final void addVetoableQueueChangeListener(final VetoableQueueChangeListener<E> l)
    {
        listenerList.add(VetoableQueueChangeListener.class, l);
    }

    /**
     * Remove the specified vetoable queue change listener.
     *
     * @param l vetoable queue change listener to remove
     */
    public final void removeVetoableQueueChangeListener(final VetoableQueueChangeListener<E> l)
    {
        listenerList.remove(VetoableQueueChangeListener.class, l);
    }

    /**
     * Return an array of all <code>QueueChangeListener</code>s, or
     * an empty array if none are registered.
     *
     * @return an array of all <code>QueueChangeListener</code>s, or
     *    an empty array if none are registered
     */
    public final QueueChangeListener<E>[] getQueueChangeListeners()
    {
        return (QueueChangeListener<E>[]) listenerList.getListeners(QueueChangeListener.class);
    }

    /**
     * Return the number of <code>QueueChangeListener</code>s registered
     * to this observable queue support class.
     *
     * @return the number of <code>QueueChangeListener</code>s registered
     *    to this observable queue support class
     */
    public final int getQueueChangeListenerCount()
    {
        return listenerList.getListenerCount(QueueChangeListener.class);
    }

    /**
     * Return an array of all <code>VetoableQueueChangeListener</code>s,
     * or an empty array if none are registered.
     *
     * @return an array of all <code>VetoableQueueChangeListener</code>s,
     *    or an empty array if none are registered
     */
    public final VetoableQueueChangeListener<E>[] getVetoableQueueChangeListeners()
    {
        return (VetoableQueueChangeListener<E>[]) listenerList.getListeners(VetoableQueueChangeListener.class);
    }

    /**
     * Return the number of <code>VetoableQueueChangeListener</code>s
     * registered to this observable queue support class.
     *
     * @return the number of <code>VetoableQueueChangeListener</code>s
     *    registered to this observable queue support class
     */
    public final int getVetoableQueueChangeListenerCount()
    {
        return listenerList.getListenerCount(VetoableQueueChangeListener.class);
    }

    /**
     * Fire a will change event to all registered
     * <code>VetoableQueueChangeListener</code>s.
     *
     * @throws QueueChangeVetoException if any of the listeners veto the change
     */
    public void fireQueueWillChange()
        throws QueueChangeVetoException
    {
        Object[] listeners = listenerList.getListenerList();
        VetoableQueueChangeEvent<E> e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == VetoableQueueChangeListener.class)
            {
                // lazily create the event
                if (e == null)
                {
                    e = new VetoableQueueChangeEvent<E>(source);
                }
                ((VetoableQueueChangeListener<E>) listeners[i + 1]).queueWillChange(e);
            }
        }
    }

    /**
     * Fire the specified will change event to all registered
     * <code>VetoableQueueChangeListener</code>s.
     *
     * @param e will change event
     * @throws QueueChangeVetoException if any of the listeners veto the change
     */
    public void fireQueueWillChange(final VetoableQueueChangeEvent<E> e)
        throws QueueChangeVetoException
    {
        Object[] listeners = listenerList.getListenerList();

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == VetoableQueueChangeListener.class)
            {
                ((VetoableQueueChangeListener<E>) listeners[i + 1]).queueWillChange(e);
            }
        }
    }

    /**
     * Fire a change event to all registered <code>QueueChangeListener</code>s.
     */
    public void fireQueueChanged()
    {
        Object[] listeners = listenerList.getListenerList();
        QueueChangeEvent<E> e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == QueueChangeListener.class)
            {
                // lazily create the event
                if (e == null)
                {
                    e = new QueueChangeEvent<E>(source);
                }
                ((QueueChangeListener<E>) listeners[i + 1]).queueChanged(e);
            }
        }
    }

    /**
     * Fire the specified change event to all registered
     * <code>QueueChangeListener</code>s.
     *
     * @param e change event
     */
    public void fireQueueChanged(final QueueChangeEvent<E> e)
    {
        Object[] listeners = listenerList.getListenerList();

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == QueueChangeListener.class)
            {
                ((QueueChangeListener<E>) listeners[i + 1]).queueChanged(e);
            }
        }
    }
}
