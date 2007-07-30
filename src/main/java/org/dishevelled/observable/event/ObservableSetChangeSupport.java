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

import org.dishevelled.observable.ObservableSet;

/**
 * A support class that can be used via delegation to provide
 * <code>SetChangeListener</code> and <code>VetoableSetChangeListener</code>
 * management.
 *
 * @param <E> set element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class ObservableSetChangeSupport<E>
    implements Serializable
{
    /** Event source. */
    private ObservableSet<E> source;

    /** Listener list. */
    private final EventListenerList listenerList;


    /**
     * Create a new support class meant to be subclassed.
     */
    protected ObservableSetChangeSupport()
    {
        listenerList = new EventListenerList();
    }

    /**
     * Create a new support class that fires set change and
     * vetoable set change events with the specified source as the
     * source of the events.
     *
     * @param source the event source
     */
    public ObservableSetChangeSupport(final ObservableSet<E> source)
    {
        this();
        setSource(source);
    }


    /**
     * Set the source of set change and vetoable set change events
     * to <code>source</code>.  Subclasses should call this method before
     * any of the <code>fireX</code> methods.
     *
     * @param source the event source
     */
    protected void setSource(final ObservableSet<E> source)
    {
        this.source = source;
    }

    /**
     * Return the <code>EventListenerList</code> backing this
     * observable set support class.
     *
     * @return the <code>EventListenerList</code> backing this
     *    observable set support class.
     */
    protected final EventListenerList getEventListenerList()
    {
        return listenerList;
    }

    /**
     * Add the specified set change listener.
     *
     * @param l set change listener to add
     */
    public final void addSetChangeListener(final SetChangeListener<E> l)
    {
        listenerList.add(SetChangeListener.class, l);
    }

    /**
     * Remove the specified set change listener.
     *
     * @param l set change listener to remove
     */
    public final void removeSetChangeListener(final SetChangeListener<E> l)
    {
        listenerList.remove(SetChangeListener.class, l);
    }

    /**
     * Add the specified vetoable set change listener.
     *
     * @param l vetoable set change listener to add
     */
    public final void addVetoableSetChangeListener(final VetoableSetChangeListener<E> l)
    {
        listenerList.add(VetoableSetChangeListener.class, l);
    }

    /**
     * Remove the specified vetoable set change listener.
     *
     * @param l vetoable set change listener to remove
     */
    public final void removeVetoableSetChangeListener(final VetoableSetChangeListener<E> l)
    {
        listenerList.remove(VetoableSetChangeListener.class, l);
    }

    /**
     * Return an array of all <code>SetChangeListener</code>s, or
     * an empty array if none are registered.
     *
     * @return an array of all <code>SetChangeListener</code>s, or
     *    an empty array if none are registered
     */
    public final SetChangeListener<E>[] getSetChangeListeners()
    {
        return (SetChangeListener<E>[]) listenerList.getListeners(SetChangeListener.class);
    }

    /**
     * Return the number of <code>SetChangeListener</code>s registered
     * to this observable set support class.
     *
     * @return the number of <code>SetChangeListener</code>s registered
     *    to this observable set support class
     */
    public final int getSetChangeListenerCount()
    {
        return listenerList.getListenerCount(SetChangeListener.class);
    }

    /**
     * Return an array of all <code>VetoableSetChangeListener</code>s,
     * or an empty array if none are registered.
     *
     * @return an array of all <code>VetoableSetChangeListener</code>s,
     *    or an empty array if none are registered
     */
    public final VetoableSetChangeListener<E>[] getVetoableSetChangeListeners()
    {
        return (VetoableSetChangeListener<E>[]) listenerList.getListeners(VetoableSetChangeListener.class);
    }

    /**
     * Return the number of <code>VetoableSetChangeListener</code>s
     * registered to this observable set support class.
     *
     * @return the number of <code>VetoableSetChangeListener</code>s
     *    registered to this observable set support class
     */
    public final int getVetoableSetChangeListenerCount()
    {
        return listenerList.getListenerCount(VetoableSetChangeListener.class);
    }

    /**
     * Fire a will change event to all registered
     * <code>VetoableSetChangeListener</code>s.
     *
     * @throws SetChangeVetoException if any of the listeners veto the change
     */
    public void fireSetWillChange()
        throws SetChangeVetoException
    {
        Object[] listeners = listenerList.getListenerList();
        VetoableSetChangeEvent<E> e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == VetoableSetChangeListener.class)
            {
                // lazily create the event
                if (e == null)
                {
                    e = new VetoableSetChangeEvent<E>(source);
                }
                ((VetoableSetChangeListener<E>) listeners[i + 1]).setWillChange(e);
            }
        }
    }

    /**
     * Fire the specified will change event to all registered
     * <code>VetoableSetChangeListener</code>s.
     *
     * @param e will change event
     * @throws SetChangeVetoException if any of the listeners veto the change
     */
    public void fireSetWillChange(final VetoableSetChangeEvent<E> e)
        throws SetChangeVetoException
    {
        Object[] listeners = listenerList.getListenerList();

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == VetoableSetChangeListener.class)
            {
                ((VetoableSetChangeListener<E>) listeners[i + 1]).setWillChange(e);
            }
        }
    }

    /**
     * Fire a change event to all registered <code>SetChangeListener</code>s.
     */
    public void fireSetChanged()
    {
        Object[] listeners = listenerList.getListenerList();
        SetChangeEvent<E> e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == SetChangeListener.class)
            {
                // lazily create the event
                if (e == null)
                {
                    e = new SetChangeEvent<E>(source);
                }
                ((SetChangeListener<E>) listeners[i + 1]).setChanged(e);
            }
        }
    }

    /**
     * Fire the specified change event to all registered
     * <code>SetChangeListener</code>s.
     *
     * @param e change event
     */
    public void fireSetChanged(final SetChangeEvent<E> e)
    {
        Object[] listeners = listenerList.getListenerList();

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == SetChangeListener.class)
            {
                ((SetChangeListener<E>) listeners[i + 1]).setChanged(e);
            }
        }
    }
}
