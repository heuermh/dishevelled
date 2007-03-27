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
package org.dishevelled.observable;

import java.util.Map;

import org.dishevelled.observable.event.MapChangeEvent;
import org.dishevelled.observable.event.MapChangeListener;
import org.dishevelled.observable.event.MapChangeVetoException;
import org.dishevelled.observable.event.ObservableMapChangeSupport;
import org.dishevelled.observable.event.VetoableMapChangeEvent;
import org.dishevelled.observable.event.VetoableMapChangeListener;

/**
 * Abstract implementation of an observable map
 * that decorates an instance of <code>Map</code>.
 *
 * @param <K> map key type
 * @param <V> map value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractObservableMap<K,V>
    extends AbstractMapDecorator<K,V>
    implements ObservableMap<K,V>
{
    /** Observable map change support. */
    private ObservableMapChangeSupport<K,V> support;


    /**
     * Create a new abstract observable map that
     * decorates the specified map.
     *
     * @param map map to decorate
     */
    protected AbstractObservableMap(final Map<K,V> map)
    {
        super(map);
        support = new ObservableMapChangeSupport(this);
    }


    /**
     * Return the <code>ObservableMapChangeSupport</code>
     * class backing this abstract observable map.
     *
     * @return the <code>ObservableMapChangeSupport</code>
     *    class backing this abstract observable map
     */
    protected final ObservableMapChangeSupport<K,V> getObservableMapChangeSupport()
    {
        return support;
    }

    /** {@inheritDoc} */
    public final void addMapChangeListener(final MapChangeListener<K,V> l)
    {
        support.addMapChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void removeMapChangeListener(final MapChangeListener<K,V> l)
    {
        support.removeMapChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void addVetoableMapChangeListener(final VetoableMapChangeListener<K,V> l)
    {
        support.addVetoableMapChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void removeVetoableMapChangeListener(final VetoableMapChangeListener<K,V> l)
    {
        support.removeVetoableMapChangeListener(l);
    }

    /** {@inheritDoc} */
    public final MapChangeListener<K,V>[] getMapChangeListeners()
    {
        return support.getMapChangeListeners();
    }

    /** {@inheritDoc} */
    public final int getMapChangeListenerCount()
    {
        return support.getMapChangeListenerCount();
    }

    /** {@inheritDoc} */
    public final VetoableMapChangeListener<K,V>[] getVetoableMapChangeListeners()
    {
        return support.getVetoableMapChangeListeners();
    }

    /** {@inheritDoc} */
    public final int getVetoableMapChangeListenerCount()
    {
        return support.getVetoableMapChangeListenerCount();
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
        support.fireMapWillChange();
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
        support.fireMapWillChange(e);
    }

    /**
     * Fire a change event to all registered <code>MapChangeListener</code>s.
     */
    public void fireMapChanged()
    {
        support.fireMapChanged();
    }

    /**
     * Fire the specified change event to all registered
     * <code>MapChangeListener</code>s.
     *
     * @param e change event
     */
    public void fireMapChanged(final MapChangeEvent<K,V> e)
    {
        support.fireMapChanged(e);
    }

    /**
     * Notify subclasses the <code>clear</code> method is about to
     * be called on the wrapped map.
     * Return <code>true</code> to proceed with the change.
     *
     * @return true to proceed with the change
     */
    protected abstract boolean preClear();

    /**
     * Notify subclasses the <code>clear</code> method has just been
     * called on the wrapped map.
     */
    protected abstract void postClear();

    /**
     * Notify subclasses the <code>put</code> method is about to
     * be called on the wrapped map with the specified parameters.
     * Return <code>true</code> to proceed with the change.
     *
     * @param key <code>put</code> method key parameter
     * @param value <code>put</code> method value parameter
     * @return true to proceed with the change
     */
    protected abstract boolean prePut(K key, V value);

    /**
     * Notify subclasses the <code>put</code> method has just been
     * called on the wrapped map with the specified parameters.
     *
     * @param key <code>put</code> method key parameter
     * @param value <code>put</code> method value parameter
     */
    protected abstract void postPut(K key, V value);

    /**
     * Notify subclasses the <code>putAll</code> method is about to
     * be called on the wrapped map with the specified parameter.
     * Return <code>true</code> to proceed with the change.
     *
     * @param map <code>putAll</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean prePutAll(Map<? extends K,? extends V> map);

    /**
     * Notify subclasses the <code>putAll</code> method has just been
     * called on the wrapped map with the specified parameter.
     *
     * @param map <code>putAll</code> method parameter
     */
    protected abstract void postPutAll(Map<? extends K,? extends V> map);

    /**
     * Notify subclasses the <code>remove</code> method is about to
     * be called on the wrapped map with the specified parameter.
     * Return <code>true</code> to proceed with the change.
     *
     * @param key <code>remove</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preRemove(Object key);

    /**
     * Notify subclasses the <code>remove</code> method has just been
     * called on the wrapped map with the specified parameter.
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
    public void putAll(final Map<? extends K,? extends V> map)
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
