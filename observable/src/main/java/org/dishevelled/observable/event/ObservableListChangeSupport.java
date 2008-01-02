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

import org.dishevelled.observable.ObservableList;

/**
 * A support class that can be used via delegation to provide
 * <code>ListChangeListener</code> and <code>VetoableListChangeListener</code>
 * management.
 *
 * @param <E> list element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class ObservableListChangeSupport<E>
    implements Serializable
{
    /** Event source. */
    private ObservableList<E> source;

    /** Listener list. */
    private final EventListenerList listenerList;


    /**
     * Create a new support class meant to be subclassed.
     */
    protected ObservableListChangeSupport()
    {
        listenerList = new EventListenerList();
    }

    /**
     * Create a new support class that fires list change and
     * vetoable list change events with the specified source as the
     * source of the events.
     *
     * @param source the event source
     */
    public ObservableListChangeSupport(final ObservableList<E> source)
    {
        this();
        setSource(source);
    }


    /**
     * Set the source of list change and vetoable list change events
     * to <code>source</code>.  Subclasses should call this method before
     * any of the <code>fireX</code> methods.
     *
     * @param source the event source
     */
    protected void setSource(final ObservableList<E> source)
    {
        this.source = source;
    }

    /**
     * Return the <code>EventListenerList</code> backing this
     * observable list support class.
     *
     * @return the <code>EventListenerList</code> backing this
     *    observable list support class.
     */
    protected final EventListenerList getEventListenerList()
    {
        return listenerList;
    }

    /**
     * Add the specified list change listener.
     *
     * @param l list change listener to add
     */
    public final void addListChangeListener(final ListChangeListener<E> l)
    {
        listenerList.add(ListChangeListener.class, l);
    }

    /**
     * Remove the specified list change listener.
     *
     * @param l list change listener to remove
     */
    public final void removeListChangeListener(final ListChangeListener<E> l)
    {
        listenerList.remove(ListChangeListener.class, l);
    }

    /**
     * Add the specified vetoable list change listener.
     *
     * @param l vetoable list change listener to add
     */
    public final void addVetoableListChangeListener(final VetoableListChangeListener<E> l)
    {
        listenerList.add(VetoableListChangeListener.class, l);
    }

    /**
     * Remove the specified vetoable list change listener.
     *
     * @param l vetoable list change listener to remove
     */
    public final void removeVetoableListChangeListener(final VetoableListChangeListener<E> l)
    {
        listenerList.remove(VetoableListChangeListener.class, l);
    }

    /**
     * Return an array of all <code>ListChangeListener</code>s, or
     * an empty array if none are registered.
     *
     * @return an array of all <code>ListChangeListener</code>s, or
     *    an empty array if none are registered
     */
    public final ListChangeListener<E>[] getListChangeListeners()
    {
        return (ListChangeListener<E>[]) listenerList.getListeners(ListChangeListener.class);
    }

    /**
     * Return the number of <code>ListChangeListener</code>s registered
     * to this observable list support class.
     *
     * @return the number of <code>ListChangeListener</code>s registered
     *    to this observable list support class
     */
    public final int getListChangeListenerCount()
    {
        return listenerList.getListenerCount(ListChangeListener.class);
    }

    /**
     * Return an array of all <code>VetoableListChangeListener</code>s,
     * or an empty array if none are registered.
     *
     * @return an array of all <code>VetoableListChangeListener</code>s,
     *    or an empty array if none are registered
     */
    public final VetoableListChangeListener<E>[] getVetoableListChangeListeners()
    {
        return (VetoableListChangeListener<E>[]) listenerList.getListeners(VetoableListChangeListener.class);
    }

    /**
     * Return the number of <code>VetoableListChangeListener</code>s
     * registered to this observable list support class.
     *
     * @return the number of <code>VetoableListChangeListener</code>s
     *    registered to this observable list support class
     */
    public final int getVetoableListChangeListenerCount()
    {
        return listenerList.getListenerCount(VetoableListChangeListener.class);
    }

    /**
     * Fire a will change event to all registered
     * <code>VetoableListChangeListener</code>s.
     *
     * @throws ListChangeVetoException if any of the listeners veto the change
     */
    public void fireListWillChange()
        throws ListChangeVetoException
    {
        Object[] listeners = listenerList.getListenerList();
        VetoableListChangeEvent<E> e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == VetoableListChangeListener.class)
            {
                // lazily create the event
                if (e == null)
                {
                    e = new VetoableListChangeEvent<E>(source);
                }
                ((VetoableListChangeListener<E>) listeners[i + 1]).listWillChange(e);
            }
        }
    }

    /**
     * Fire the specified will change event to all registered
     * <code>VetoableListChangeListener</code>s.
     *
     * @param e will change event
     * @throws ListChangeVetoException if any of the listeners veto the change
     */
    public void fireListWillChange(final VetoableListChangeEvent<E> e)
        throws ListChangeVetoException
    {
        Object[] listeners = listenerList.getListenerList();

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == VetoableListChangeListener.class)
            {
                ((VetoableListChangeListener<E>) listeners[i + 1]).listWillChange(e);
            }
        }
    }

    /**
     * Fire a change event to all registered <code>ListChangeListener</code>s.
     */
    public void fireListChanged()
    {
        Object[] listeners = listenerList.getListenerList();
        ListChangeEvent<E> e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == ListChangeListener.class)
            {
                // lazily create the event
                if (e == null)
                {
                    e = new ListChangeEvent<E>(source);
                }
                ((ListChangeListener<E>) listeners[i + 1]).listChanged(e);
            }
        }
    }

    /**
     * Fire the specified change event to all registered
     * <code>ListChangeListener</code>s.
     *
     * @param e change event
     */
    public void fireListChanged(final ListChangeEvent<E> e)
    {
        Object[] listeners = listenerList.getListenerList();

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == ListChangeListener.class)
            {
                ((ListChangeListener<E>) listeners[i + 1]).listChanged(e);
            }
        }
    }
}
