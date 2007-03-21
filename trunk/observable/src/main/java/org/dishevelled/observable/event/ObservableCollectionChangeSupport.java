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

import org.dishevelled.observable.ObservableCollection;

/**
 * A support class that can be used via delegation to provide
 * <code>CollectionChangeListener</code> and <code>VetoableCollectionChangeListener</code>
 * management.
 *
 * @param <E> collection element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class ObservableCollectionChangeSupport<E>
    implements Serializable
{
    /** Event source. */
    private ObservableCollection<E> source;

    /** Listener list. */
    private final EventListenerList listenerList;


    /**
     * Create a new support class meant to be subclassed.
     */
    protected ObservableCollectionChangeSupport()
    {
        listenerList = new EventListenerList();
    }

    /**
     * Create a new support class that fires collection change and
     * vetoable collection change events with the specified source as the
     * source of the events.
     *
     * @param source the event source
     */
    public ObservableCollectionChangeSupport(final ObservableCollection<E> source)
    {
        this();
        setSource(source);
    }


    /**
     * Set the source of collection change and vetoable collection change events
     * to <code>source</code>.  Subclasses should call this method before
     * any of the <code>fireX</code> methods.
     *
     * @param source the event source
     */
    protected void setSource(final ObservableCollection<E> source)
    {
        this.source = source;
    }

    /**
     * Return the <code>EventListenerList</code> backing this
     * observable collection support class.
     *
     * @return the <code>EventListenerList</code> backing this
     *    observable collection support class.
     */
    protected final EventListenerList getEventListenerList()
    {
        return listenerList;
    }

    /**
     * Add the specified collection change listener.
     *
     * @param l collection change listener to add
     */
    public final void addCollectionChangeListener(final CollectionChangeListener<E> l)
    {
        listenerList.add(CollectionChangeListener.class, l);
    }

    /**
     * Remove the specified collection change listener.
     *
     * @param l collection change listener to remove
     */
    public final void removeCollectionChangeListener(final CollectionChangeListener<E> l)
    {
        listenerList.remove(CollectionChangeListener.class, l);
    }

    /**
     * Add the specified vetoable collection change listener.
     *
     * @param l vetoable collection change listener to add
     */
    public final void addVetoableCollectionChangeListener(final VetoableCollectionChangeListener<E> l)
    {
        listenerList.add(VetoableCollectionChangeListener.class, l);
    }

    /**
     * Remove the specified vetoable collection change listener.
     *
     * @param l vetoable collection change listener to remove
     */
    public final void removeVetoableCollectionChangeListener(final VetoableCollectionChangeListener<E> l)
    {
        listenerList.remove(VetoableCollectionChangeListener.class, l);
    }

    /**
     * Return an array of all <code>CollectionChangeListener</code>s, or
     * an empty array if none are registered.
     *
     * @return an array of all <code>CollectionChangeListener</code>s, or
     *    an empty array if none are registered
     */
    public final CollectionChangeListener<E>[] getCollectionChangeListeners()
    {
        return (CollectionChangeListener<E>[]) listenerList.getListeners(CollectionChangeListener.class);
    }

    /**
     * Return the number of <code>CollectionChangeListener</code>s registered
     * to this observable collection support class.
     *
     * @return the number of <code>CollectionChangeListener</code>s registered
     *    to this observable collection support class
     */
    public final int getCollectionChangeListenerCount()
    {
        return listenerList.getListenerCount(CollectionChangeListener.class);
    }

    /**
     * Return an array of all <code>VetoableCollectionChangeListener</code>s,
     * or an empty array if none are registered.
     *
     * @return an array of all <code>VetoableCollectionChangeListener</code>s,
     *    or an empty array if none are registered
     */
    public final VetoableCollectionChangeListener<E>[] getVetoableCollectionChangeListeners()
    {
        return (VetoableCollectionChangeListener<E>[])
            listenerList.getListeners(VetoableCollectionChangeListener.class);
    }

    /**
     * Return the number of <code>VetoableCollectionChangeListener</code>s
     * registered to this observable collection support class.
     *
     * @return the number of <code>VetoableCollectionChangeListener</code>s
     *    registered to this observable collection support class
     */
    public final int getVetoableCollectionChangeListenerCount()
    {
        return listenerList.getListenerCount(VetoableCollectionChangeListener.class);
    }

    /**
     * Fire a will change event to all registered
     * <code>VetoableCollectionChangeListener</code>s.
     *
     * @throws CollectionChangeVetoException if any of the listeners veto the change
     */
    public void fireCollectionWillChange()
        throws CollectionChangeVetoException
    {
        Object[] listeners = listenerList.getListenerList();
        VetoableCollectionChangeEvent<E> e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == VetoableCollectionChangeListener.class)
            {
                // lazily create the event
                if (e == null)
                {
                    e = new VetoableCollectionChangeEvent<E>(source);
                }
                ((VetoableCollectionChangeListener<E>) listeners[i + 1]).collectionWillChange(e);
            }
        }
    }

    /**
     * Fire the specified will change event to all registered
     * <code>VetoableCollectionChangeListener</code>s.
     *
     * @param e will change event
     * @throws CollectionChangeVetoException if any of the listeners veto the change
     */
    public void fireCollectionWillChange(final VetoableCollectionChangeEvent<E> e)
        throws CollectionChangeVetoException
    {
        Object[] listeners = listenerList.getListenerList();

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == VetoableCollectionChangeListener.class)
            {
                ((VetoableCollectionChangeListener<E>) listeners[i + 1]).collectionWillChange(e);
            }
        }
    }

    /**
     * Fire a change event to all registered <code>CollectionChangeListener</code>s.
     */
    public void fireCollectionChanged()
    {
        Object[] listeners = listenerList.getListenerList();
        CollectionChangeEvent<E> e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == CollectionChangeListener.class)
            {
                // lazily create the event
                if (e == null)
                {
                    e = new CollectionChangeEvent<E>(source);
                }
                ((CollectionChangeListener<E>) listeners[i + 1]).collectionChanged(e);
            }
        }
    }

    /**
     * Fire the specified change event to all registered
     * <code>CollectionChangeListener</code>s.
     *
     * @param e change event
     */
    public void fireCollectionChanged(final CollectionChangeEvent<E> e)
    {
        Object[] listeners = listenerList.getListenerList();

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == CollectionChangeListener.class)
            {
                ((CollectionChangeListener<E>) listeners[i + 1]).collectionChanged(e);
            }
        }
    }
}
