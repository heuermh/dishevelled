/*

    dsh-observable  Observable decorators for collection and map interfaces.
    Copyright (c) 2003-2012 held jointly by the individual authors.

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
package org.dishevelled.observable;

import java.util.Map;
import java.util.SortedMap;

import org.dishevelled.observable.event.ObservableSortedMapChangeSupport;
import org.dishevelled.observable.event.SortedMapChangeEvent;
import org.dishevelled.observable.event.SortedMapChangeListener;
import org.dishevelled.observable.event.SortedMapChangeVetoException;
import org.dishevelled.observable.event.VetoableSortedMapChangeEvent;
import org.dishevelled.observable.event.VetoableSortedMapChangeListener;

/**
 * Abstract implementation of an observable sorted map
 * that decorates an instance of <code>SortedMap</code>.
 *
 * @param <K> sorted map key type
 * @param <V> sorted map value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractObservableSortedMap<K, V>
    extends AbstractSortedMapDecorator<K, V>
    implements ObservableSortedMap<K, V>
{
    /** Observable sorted map change support. */
    private final ObservableSortedMapChangeSupport<K, V> support;


    /**
     * Create a new abstract observable sorted map that
     * decorates the specified sorted map.
     *
     * @param sortedMap sorted map to decorate
     */
    protected AbstractObservableSortedMap(final SortedMap<K, V> sortedMap)
    {
        super(sortedMap);
        support = new ObservableSortedMapChangeSupport<K, V>(this);
    }


    /**
     * Return the <code>ObservableSortedMapChangeSupport</code>
     * class backing this abstract observable sorted map.
     *
     * @return the <code>ObservableSortedMapChangeSupport</code>
     *    class backing this abstract observable sorted map
     */
    protected final ObservableSortedMapChangeSupport<K, V> getObservableSortedMapChangeSupport()
    {
        return support;
    }

    /** {@inheritDoc} */
    public final void addSortedMapChangeListener(final SortedMapChangeListener<K, V> l)
    {
        support.addSortedMapChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void removeSortedMapChangeListener(final SortedMapChangeListener<K, V> l)
    {
        support.removeSortedMapChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void addVetoableSortedMapChangeListener(final VetoableSortedMapChangeListener<K, V> l)
    {
        support.addVetoableSortedMapChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void removeVetoableSortedMapChangeListener(final VetoableSortedMapChangeListener<K, V> l)
    {
        support.removeVetoableSortedMapChangeListener(l);
    }

    /** {@inheritDoc} */
    public final SortedMapChangeListener<K, V>[] getSortedMapChangeListeners()
    {
        return support.getSortedMapChangeListeners();
    }

    /** {@inheritDoc} */
    public final int getSortedMapChangeListenerCount()
    {
        return support.getSortedMapChangeListenerCount();
    }

    /** {@inheritDoc} */
    public final VetoableSortedMapChangeListener<K, V>[] getVetoableSortedMapChangeListeners()
    {
        return support.getVetoableSortedMapChangeListeners();
    }

    /** {@inheritDoc} */
    public final int getVetoableSortedMapChangeListenerCount()
    {
        return support.getVetoableSortedMapChangeListenerCount();
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
        support.fireSortedMapWillChange();
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
        support.fireSortedMapWillChange(e);
    }

    /**
     * Fire a change event to all registered <code>SortedMapChangeListener</code>s.
     */
    public void fireSortedMapChanged()
    {
        support.fireSortedMapChanged();
    }

    /**
     * Fire the specified change event to all registered
     * <code>SortedMapChangeListener</code>s.
     *
     * @param e change event
     */
    public void fireSortedMapChanged(final SortedMapChangeEvent<K, V> e)
    {
        support.fireSortedMapChanged(e);
    }

    /**
     * Notify subclasses the <code>clear</code> method is about to
     * be called on the wrapped sorted map.
     * Return <code>true</code> to proceed with the change.
     *
     * @return true to proceed with the change
     */
    protected abstract boolean preClear();

    /**
     * Notify subclasses the <code>clear</code> method has just been
     * called on the wrapped sorted map.
     */
    protected abstract void postClear();

    /**
     * Notify subclasses the <code>put</code> method is about to
     * be called on the wrapped sorted map with the specified parameters.
     * Return <code>true</code> to proceed with the change.
     *
     * @param key <code>put</code> method key parameter
     * @param value <code>put</code> method value parameter
     * @return true to proceed with the change
     */
    protected abstract boolean prePut(K key, V value);

    /**
     * Notify subclasses the <code>put</code> method has just been
     * called on the wrapped sorted map with the specified parameters.
     *
     * @param key <code>put</code> method key parameter
     * @param value <code>put</code> method value parameter
     */
    protected abstract void postPut(K key, V value);

    /**
     * Notify subclasses the <code>putAll</code> method is about to
     * be called on the wrapped sorted map with the specified parameter.
     * Return <code>true</code> to proceed with the change.
     *
     * @param map <code>putAll</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean prePutAll(Map<? extends K, ? extends V> map);

    /**
     * Notify subclasses the <code>putAll</code> method has just been
     * called on the wrapped sorted map with the specified parameter.
     *
     * @param map <code>putAll</code> method parameter
     */
    protected abstract void postPutAll(Map<? extends K, ? extends V> map);

    /**
     * Notify subclasses the <code>remove</code> method is about to
     * be called on the wrapped sorted map with the specified parameter.
     * Return <code>true</code> to proceed with the change.
     *
     * @param key <code>remove</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preRemove(Object key);

    /**
     * Notify subclasses the <code>remove</code> method has just been
     * called on the wrapped sorted map with the specified parameter.
     *
     * @param key <code>remove</code> method parameter
     */
    protected abstract void postRemove(Object key);

    /** {@inheritDoc} */
    public void clear()
    {
        if (preClear())
        {
            super.clear();
            postClear();
        }
    }

    /** {@inheritDoc} */
    public V put(final K key, final V value)
    {
        V result = null;
        if (prePut(key, value))
        {
            result = super.put(key, value);
            postPut(key, value);
        }
        return result;
    }

    /** {@inheritDoc} */
    public void putAll(final Map<? extends K, ? extends V> map)
    {
        if (prePutAll(map))
        {
            super.putAll(map);
            postPutAll(map);
        }
    }

    /** {@inheritDoc} */
    public V remove(final Object key)
    {
        V result = null;
        if (preRemove(key))
        {
            result = super.remove(key);
            postRemove(key);
        }
        return result;
    }
}
