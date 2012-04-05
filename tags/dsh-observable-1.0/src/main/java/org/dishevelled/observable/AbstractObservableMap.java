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

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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
public abstract class AbstractObservableMap<K, V>
    extends AbstractMapDecorator<K, V>
    implements ObservableMap<K, V>
{
    /** Observable map change support. */
    private final ObservableMapChangeSupport<K, V> support;


    /**
     * Create a new abstract observable map that
     * decorates the specified map.
     *
     * @param map map to decorate
     */
    protected AbstractObservableMap(final Map<K, V> map)
    {
        super(map);
        support = new ObservableMapChangeSupport<K, V>(this);
    }


    /**
     * Return the <code>ObservableMapChangeSupport</code>
     * class backing this abstract observable map.
     *
     * @return the <code>ObservableMapChangeSupport</code>
     *    class backing this abstract observable map
     */
    protected final ObservableMapChangeSupport<K, V> getObservableMapChangeSupport()
    {
        return support;
    }

    /** {@inheritDoc} */
    public final void addMapChangeListener(final MapChangeListener<K, V> l)
    {
        support.addMapChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void removeMapChangeListener(final MapChangeListener<K, V> l)
    {
        support.removeMapChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void addVetoableMapChangeListener(final VetoableMapChangeListener<K, V> l)
    {
        support.addVetoableMapChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void removeVetoableMapChangeListener(final VetoableMapChangeListener<K, V> l)
    {
        support.removeVetoableMapChangeListener(l);
    }

    /** {@inheritDoc} */
    public final MapChangeListener<K, V>[] getMapChangeListeners()
    {
        return support.getMapChangeListeners();
    }

    /** {@inheritDoc} */
    public final int getMapChangeListenerCount()
    {
        return support.getMapChangeListenerCount();
    }

    /** {@inheritDoc} */
    public final VetoableMapChangeListener<K, V>[] getVetoableMapChangeListeners()
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
    public void fireMapWillChange(final VetoableMapChangeEvent<K, V> e)
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
    public void fireMapChanged(final MapChangeEvent<K, V> e)
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
    protected abstract boolean prePutAll(Map<? extends K, ? extends V> map);

    /**
     * Notify subclasses the <code>putAll</code> method has just been
     * called on the wrapped map with the specified parameter.
     *
     * @param map <code>putAll</code> method parameter
     */
    protected abstract void postPutAll(Map<? extends K, ? extends V> map);

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

    /**
     * Notify subclasses the <code>clear</code> method is about to
     * be called on the values view for the wrapped map.
     * Return <code>true</code> to proceed with the change.
     *
     * @return true to proceed with the change
     */
    protected abstract boolean preValuesClear();

    /**
     * Notify subclasses the <code>clear</code> method has just been
     * called on the values view for the wrapped map with the specified parameter.
     */
    protected abstract void postValuesClear();

    /**
     * Notify subclasses the <code>remove</code> method is about to
     * be called on the values view for the wrapped map.
     * Return <code>true</code> to proceed with the change.
     *
     * @param value <code>remove</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preValuesRemove(Object value);

    /**
     * Notify subclasses the <code>remove</code> method has just been
     * called on the values view for the wrapped map with the specified parameter.
     *
     * @param value <code>remove</code> method parameter
     */
    protected abstract void postValuesRemove(Object value);

    /**
     * Notify subclasses the <code>removeAll</code> method is about to
     * be called on the values view for the wrapped map.
     * Return <code>true</code> to proceed with the change.
     *
     * @param values <code>removeAll</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preValuesRemoveAll(Collection<?> values);

    /**
     * Notify subclasses the <code>removeAll</code> method has just been
     * called on the values view for the wrapped map with the specified parameter.
     *
     * @param values <code>removeAll</code> method parameter
     */
    protected abstract void postValuesRemoveAll(Collection<?> values);

    /**
     * Notify subclasses the <code>retainAll</code> method is about to
     * be called on the values view for the wrapped map.
     * Return <code>true</code> to proceed with the change.
     *
     * @param values <code>retainAll</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preValuesRetainAll(Collection<?> values);

    /**
     * Notify subclasses the <code>clear</code> method has just been
     * called on the values view for the wrapped map with the specified parameter.
     *
     * @param values <code>retainAll</code> method parameter
     */
    protected abstract void postValuesRetainAll(Collection<?> values);

    /**
     * Notify subclasses the <code>remove</code> method is about to
     * be called on the values view iterator for the wrapped map.
     * Return <code>true</code> to proceed with the change.
     *
     * @return true to proceed with the change
     */
    protected abstract boolean preValuesIteratorRemove();

    /**
     * Notify subclasses the <code>remove</code> method has just been
     * called on the values view iterator for the wrapped map.
     */
    protected abstract void postValuesIteratorRemove();

    /**
     * Notify subclasses the <code>clear</code> method is about to
     * be called on the keySet view for the wrapped map.
     * Return <code>true</code> to proceed with the change.
     *
     * @return true to proceed with the change
     */
    protected abstract boolean preKeySetClear();

    /**
     * Notify subclasses the <code>clear</code> method has just been
     * called on the keySet view for the wrapped map with the specified parameter.
     */
    protected abstract void postKeySetClear();

    /**
     * Notify subclasses the <code>remove</code> method is about to
     * be called on the keySet view for the wrapped map.
     * Return <code>true</code> to proceed with the change.
     *
     * @param value <code>remove</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preKeySetRemove(Object value);

    /**
     * Notify subclasses the <code>remove</code> method has just been
     * called on the keySet view for the wrapped map with the specified parameter.
     *
     * @param value <code>remove</code> method parameter
     */
    protected abstract void postKeySetRemove(Object value);

    /**
     * Notify subclasses the <code>removeAll</code> method is about to
     * be called on the keySet view for the wrapped map.
     * Return <code>true</code> to proceed with the change.
     *
     * @param keySet <code>removeAll</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preKeySetRemoveAll(Collection<?> keySet);

    /**
     * Notify subclasses the <code>removeAll</code> method has just been
     * called on the keySet view for the wrapped map with the specified parameter.
     *
     * @param keySet <code>removeAll</code> method parameter
     */
    protected abstract void postKeySetRemoveAll(Collection<?> keySet);

    /**
     * Notify subclasses the <code>retainAll</code> method is about to
     * be called on the keySet view for the wrapped map.
     * Return <code>true</code> to proceed with the change.
     *
     * @param keySet <code>retainAll</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preKeySetRetainAll(Collection<?> keySet);

    /**
     * Notify subclasses the <code>clear</code> method has just been
     * called on the keySet view for the wrapped map with the specified parameter.
     *
     * @param keySet <code>retainAll</code> method parameter
     */
    protected abstract void postKeySetRetainAll(Collection<?> keySet);

    /**
     * Notify subclasses the <code>remove</code> method is about to
     * be called on the keySet view iterator for the wrapped map.
     * Return <code>true</code> to proceed with the change.
     *
     * @return true to proceed with the change
     */
    protected abstract boolean preKeySetIteratorRemove();

    /**
     * Notify subclasses the <code>remove</code> method has just been
     * called on the keySet view iterator for the wrapped map.
     */
    protected abstract void postKeySetIteratorRemove();

    /**
     * Notify subclasses the <code>clear</code> method is about to
     * be called on the entrySet view for the wrapped map.
     * Return <code>true</code> to proceed with the change.
     *
     * @return true to proceed with the change
     */
    protected abstract boolean preEntrySetClear();

    /**
     * Notify subclasses the <code>clear</code> method has just been
     * called on the entrySet view for the wrapped map with the specified parameter.
     */
    protected abstract void postEntrySetClear();

    /**
     * Notify subclasses the <code>remove</code> method is about to
     * be called on the entrySet view for the wrapped map.
     * Return <code>true</code> to proceed with the change.
     *
     * @param value <code>remove</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preEntrySetRemove(Object value);

    /**
     * Notify subclasses the <code>remove</code> method has just been
     * called on the entrySet view for the wrapped map with the specified parameter.
     *
     * @param value <code>remove</code> method parameter
     */
    protected abstract void postEntrySetRemove(Object value);

    /**
     * Notify subclasses the <code>removeAll</code> method is about to
     * be called on the entrySet view for the wrapped map.
     * Return <code>true</code> to proceed with the change.
     *
     * @param entrySet <code>removeAll</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preEntrySetRemoveAll(Collection<?> entrySet);

    /**
     * Notify subclasses the <code>removeAll</code> method has just been
     * called on the entrySet view for the wrapped map with the specified parameter.
     *
     * @param entrySet <code>removeAll</code> method parameter
     */
    protected abstract void postEntrySetRemoveAll(Collection<?> entrySet);

    /**
     * Notify subclasses the <code>retainAll</code> method is about to
     * be called on the entrySet view for the wrapped map.
     * Return <code>true</code> to proceed with the change.
     *
     * @param entrySet <code>retainAll</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preEntrySetRetainAll(Collection<?> entrySet);

    /**
     * Notify subclasses the <code>clear</code> method has just been
     * called on the entrySet view for the wrapped map with the specified parameter.
     *
     * @param entrySet <code>retainAll</code> method parameter
     */
    protected abstract void postEntrySetRetainAll(Collection<?> entrySet);

    /**
     * Notify subclasses the <code>remove</code> method is about to
     * be called on the entrySet view iterator for the wrapped map.
     * Return <code>true</code> to proceed with the change.
     *
     * @return true to proceed with the change
     */
    protected abstract boolean preEntrySetIteratorRemove();

    /**
     * Notify subclasses the <code>remove</code> method has just been
     * called on the entrySet view iterator for the wrapped map.
     */
    protected abstract void postEntrySetIteratorRemove();

    /**
     * Notify subclasses the <code>setValue</code> method is about to
     * be called on a map entry from the entrySet view iterator for the wrapped map.
     * Return <code>true</code> to proceed with the change.
     *
     * @param value <code>setValue</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preMapEntrySetValue(V value);

    /**
     * Notify subclasses the <code>setValue</code> method has just been
     * called on a map entry from the entrySet view iterator for the wrapped map.
     *
     * @param value <code>setValue</code> method parameter
     */
    protected abstract void postMapEntrySetValue(V value);


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

    /** {@inheritDoc} */
    public Set<Map.Entry<K, V>> entrySet()
    {
        return new ObservableEntrySet(super.entrySet());
    }

    /** {@inheritDoc} */
    public Set<K> keySet()
    {
        return new ObservableKeySet(super.keySet());
    }

    /** {@inheritDoc} */
    public Collection<V> values()
    {
        return new ObservableValues(super.values());
    }


    /**
     * Observable values view decorator.
     */
    protected class ObservableValues
        extends AbstractCollectionDecorator<V>
    {

        /**
         * Create a new observable values view that decorates
         * the specified values view.
         *
         * @param values values view to decorate
         */
        protected ObservableValues(final Collection<V> values)
        {
            super(values);
        }


        /** {@inheritDoc} */
        public void clear()
        {
            if (preValuesClear())
            {
                super.clear();
                postValuesClear();
            }
        }

        /** {@inheritDoc} */
        public boolean remove(final Object o)
        {
            boolean result = false;
            if (preValuesRemove(o))
            {
                result = super.remove(o);
                postValuesRemove(o);
            }
            return result;
        }

        /** {@inheritDoc} */
        public boolean removeAll(final Collection<?> coll)
        {
            boolean result = false;
            if (preValuesRemoveAll(coll))
            {
                result = super.removeAll(coll);
                postValuesRemoveAll(coll);
            }
            return result;
        }

        /** {@inheritDoc} */
        public boolean retainAll(final Collection<?> coll)
        {
            boolean result = false;
            if (preValuesRetainAll(coll))
            {
                result = super.retainAll(coll);
                postValuesRetainAll(coll);
            }
            return result;
        }

        /** {@inheritDoc} */
        public Iterator<V> iterator()
        {
            return new ObservableValuesIterator(super.iterator());
        }


        /**
         * Observable values view iterator decorator.
         */
        protected class ObservableValuesIterator
            extends AbstractIteratorDecorator<V>
        {

            /**
             * Create a new observable values view iterator that decorates
             * the specified values view iterator.
             *
             * @param iterator values view iterator to decorate
             */
            protected ObservableValuesIterator(final Iterator<V> iterator)
            {
                super(iterator);
            }


            /** {@inheritDoc} */
            public void remove()
            {
                if (preValuesIteratorRemove())
                {
                    super.remove();
                    postValuesIteratorRemove();
                }
            }
        }
    }

    /**
     * Observable keySet view decorator.
     */
    protected class ObservableKeySet
        extends AbstractSetDecorator<K>
    {

        /**
         * Create a new observable keySet view that decorates
         * the specified keySet view.
         *
         * @param keySet keySet view to decorate
         */
        protected ObservableKeySet(final Set<K> keySet)
        {
            super(keySet);
        }


        /** {@inheritDoc} */
        public void clear()
        {
            if (preKeySetClear())
            {
                super.clear();
                postKeySetClear();
            }
        }

        /** {@inheritDoc} */
        public boolean remove(final Object o)
        {
            boolean result = false;
            if (preKeySetRemove(o))
            {
                result = super.remove(o);
                postKeySetRemove(o);
            }
            return result;
        }

        /** {@inheritDoc} */
        public boolean removeAll(final Collection<?> coll)
        {
            boolean result = false;
            if (preKeySetRemoveAll(coll))
            {
                result = super.removeAll(coll);
                postKeySetRemoveAll(coll);
            }
            return result;
        }

        /** {@inheritDoc} */
        public boolean retainAll(final Collection<?> coll)
        {
            boolean result = false;
            if (preKeySetRetainAll(coll))
            {
                result = super.retainAll(coll);
                postKeySetRetainAll(coll);
            }
            return result;
        }

        /** {@inheritDoc} */
        public Iterator<K> iterator()
        {
            return new ObservableKeySetIterator(super.iterator());
        }


        /**
         * Observable keySet view iterator decorator.
         */
        protected class ObservableKeySetIterator
            extends AbstractIteratorDecorator<K>
        {

            /**
             * Create a new observable keySet view iterator that decorates
             * the specified keySet view iterator.
             *
             * @param iterator keySet view iterator to decorate
             */
            protected ObservableKeySetIterator(final Iterator<K> iterator)
            {
                super(iterator);
            }


            /** {@inheritDoc} */
            public void remove()
            {
                if (preKeySetIteratorRemove())
                {
                    super.remove();
                    postKeySetIteratorRemove();
                }
            }
        }
    }

    /**
     * Observable entrySet view decorator.
     */
    protected class ObservableEntrySet
        extends AbstractSetDecorator<Map.Entry<K, V>>
    {

        /**
         * Create a new observable entrySet view that decorates
         * the specified entrySet view.
         *
         * @param entrySet entrySet view to decorate
         */
        protected ObservableEntrySet(final Set<Map.Entry<K, V>> entrySet)
        {
            super(entrySet);
        }


        /** {@inheritDoc} */
        public void clear()
        {
            if (preEntrySetClear())
            {
                super.clear();
                postEntrySetClear();
            }
        }

        /** {@inheritDoc} */
        public boolean remove(final Object o)
        {
            boolean result = false;
            if (preEntrySetRemove(o))
            {
                result = super.remove(o);
                postEntrySetRemove(o);
            }
            return result;
        }

        /** {@inheritDoc} */
        public boolean removeAll(final Collection<?> coll)
        {
            boolean result = false;
            if (preEntrySetRemoveAll(coll))
            {
                result = super.removeAll(coll);
                postEntrySetRemoveAll(coll);
            }
            return result;
        }

        /** {@inheritDoc} */
        public boolean retainAll(final Collection<?> coll)
        {
            boolean result = false;
            if (preEntrySetRetainAll(coll))
            {
                result = super.retainAll(coll);
                postEntrySetRetainAll(coll);
            }
            return result;
        }

        /** {@inheritDoc} */
        public Iterator<Map.Entry<K, V>> iterator()
        {
            return new ObservableEntrySetIterator(super.iterator());
        }

        /**
         * Observable map entry decorator.
         */
        protected class ObservableMapEntry
            extends AbstractMapEntryDecorator<K, V>
        {

            /**
             * Create a new observable map entry that decorates the
             * specified map entry.
             *
             * @param entry map entry to decorate
             */
            protected ObservableMapEntry(final Map.Entry<K, V> entry)
            {
                super(entry);
            }


            /** {@inheritDoc} */
            public V setValue(final V value)
            {
                V result = null;
                if (preMapEntrySetValue(value))
                {
                    result = super.setValue(value);
                    postMapEntrySetValue(value);
                }
                return result;
            }
        }

        /**
         * Observable entrySet view iterator decorator.
         */
        protected class ObservableEntrySetIterator
            extends AbstractIteratorDecorator<Map.Entry<K, V>>
        {

            /**
             * Create a new observable entrySet view iterator that decorates
             * the specified entrySet view iterator.
             *
             * @param iterator entrySet view iterator to decorate
             */
            protected ObservableEntrySetIterator(final Iterator<Map.Entry<K, V>> iterator)
            {
                super(iterator);
            }


            /** {@inheritDoc} */
            public Map.Entry<K, V> next()
            {
                return new ObservableMapEntry(super.next());
            }

            /** {@inheritDoc} */
            public void remove()
            {
                if (preEntrySetIteratorRemove())
                {
                    super.remove();
                    postEntrySetIteratorRemove();
                }
            }
        }
    }
}
