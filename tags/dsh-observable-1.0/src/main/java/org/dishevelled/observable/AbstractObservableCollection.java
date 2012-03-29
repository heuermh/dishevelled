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

import java.util.Iterator;
import java.util.Collection;

import org.dishevelled.observable.event.CollectionChangeEvent;
import org.dishevelled.observable.event.CollectionChangeListener;
import org.dishevelled.observable.event.CollectionChangeVetoException;
import org.dishevelled.observable.event.ObservableCollectionChangeSupport;
import org.dishevelled.observable.event.VetoableCollectionChangeEvent;
import org.dishevelled.observable.event.VetoableCollectionChangeListener;

/**
 * Abstract implementation of an observable collection
 * that decorates an instance of <code>Collection</code>.
 *
 * @param <E> collection element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractObservableCollection<E>
    extends AbstractCollectionDecorator<E>
    implements ObservableCollection<E>
{
    /** Observable collection change support. */
    private final ObservableCollectionChangeSupport<E> support;


    /**
     * Create a new abstract observable collection that
     * decorates the specified collection.
     *
     * @param collection collection to decorate
     */
    protected AbstractObservableCollection(final Collection<E> collection)
    {
        super(collection);
        support = new ObservableCollectionChangeSupport<E>(this);
    }


    /**
     * Return the <code>ObservableCollectionChangeSupport</code>
     * class backing this abstract observable collection.
     *
     * @return the <code>ObservableCollectionChangeSupport</code>
     *    class backing this abstract observable collection
     */
    protected final ObservableCollectionChangeSupport<E> getObservableCollectionChangeSupport()
    {
        return support;
    }

    /** {@inheritDoc} */
    public final void addCollectionChangeListener(final CollectionChangeListener<E> l)
    {
        support.addCollectionChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void removeCollectionChangeListener(final CollectionChangeListener<E> l)
    {
        support.removeCollectionChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void addVetoableCollectionChangeListener(final VetoableCollectionChangeListener<E> l)
    {
        support.addVetoableCollectionChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void removeVetoableCollectionChangeListener(final VetoableCollectionChangeListener<E> l)
    {
        support.removeVetoableCollectionChangeListener(l);
    }

    /** {@inheritDoc} */
    public final CollectionChangeListener<E>[] getCollectionChangeListeners()
    {
        return support.getCollectionChangeListeners();
    }

    /** {@inheritDoc} */
    public final int getCollectionChangeListenerCount()
    {
        return support.getCollectionChangeListenerCount();
    }

    /** {@inheritDoc} */
    public final VetoableCollectionChangeListener<E>[] getVetoableCollectionChangeListeners()
    {
        return support.getVetoableCollectionChangeListeners();
    }

    /** {@inheritDoc} */
    public final int getVetoableCollectionChangeListenerCount()
    {
        return support.getVetoableCollectionChangeListenerCount();
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
        support.fireCollectionWillChange();
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
        support.fireCollectionWillChange(e);
    }

    /**
     * Fire a change event to all registered <code>CollectionChangeListener</code>s.
     */
    public void fireCollectionChanged()
    {
        support.fireCollectionChanged();
    }

    /**
     * Fire the specified change event to all registered
     * <code>CollectionChangeListener</code>s.
     *
     * @param e change event
     */
    public void fireCollectionChanged(final CollectionChangeEvent<E> e)
    {
        support.fireCollectionChanged(e);
    }

    /**
     * Notify subclasses the <code>add</code> method is about to
     * be called on the wrapped collection with the specified parameter.
     * Return <code>true</code> to proceed with the change.
     *
     * @param e <code>add</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preAdd(E e);

    /**
     * Notify subclasses the <code>add</code> method has just been
     * called on the wrapped collection with the specified parameter.
     *
     * @param e <code>add</code> method parameter
     */
    protected abstract void postAdd(E e);

    /**
     * Notify subclasses the <code>addAll</code> method is about to
     * be called on the wrapped collection with the specified parameter.
     * Return <code>true</code> to proceed with the change.
     *
     * @param coll <code>addAll</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preAddAll(Collection<? extends E> coll);

    /**
     * Notify subclasses the <code>addAll</code> method has just been
     * called on the wrapped collection with the specified parameter.
     *
     * @param coll <code>addAll</code> method parameter
     */
    protected abstract void postAddAll(Collection<? extends E> coll);

    /**
     * Notify subclasses the <code>clear</code> method is about to
     * be called on the wrapped collection.
     * Return <code>true</code> to proceed with the change.
     *
     * @return true to proceed with the change
     */
    protected abstract boolean preClear();

    /**
     * Notify subclasses the <code>clear</code> method has just been
     * called on the wrapped collection.
     */
    protected abstract void postClear();

    /**
     * Notify subclasses the <code>remove</code> method is about to
     * be called on the wrapped collection with the specified parameter.
     * Return <code>true</code> to proceed with the change.
     *
     * @param o <code>remove</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preRemove(Object o);

    /**
     * Notify subclasses the <code>remove</code> method has just been
     * called on the wrapped collection with the specified parameter.
     *
     * @param o <code>remove</code> method parameter
     */
    protected abstract void postRemove(Object o);

    /**
     * Notify subclasses the <code>removeAll</code> method is about to
     * be called on the wrapped collection with the specified parameter.
     * Return <code>true</code> to proceed with the change.
     *
     * @param coll <code>removeAll</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preRemoveAll(Collection<?> coll);

    /**
     * Notify subclasses the <code>removeAll</code> method has just been
     * called on the wrapped collection with the specified parameter.
     *
     * @param coll <code>removeAll</code> method parameter
     */
    protected abstract void postRemoveAll(Collection<?> coll);

    /**
     * Notify subclasses the <code>retainAll</code> method is about to
     * be called on the wrapped collection with the specified parameter.
     * Return <code>true</code> to proceed with the change.
     *
     * @param coll <code>retainAll</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preRetainAll(Collection<?> coll);

    /**
     * Notify subclasses the <code>retainAll</code> method has just been
     * called on the wrapped collection with the specified parameter.
     *
     * @param coll <code>retainAll</code> method parameter
     */
    protected abstract void postRetainAll(Collection<?> coll);

    /**
     * Notify subclasses the <code>remove</code> method is about to
     * be called on the wrapped collection's iterator.
     * Return <code>true</code> to proceed with the change.
     *
     * @return true to proceed with the change
     */
    protected abstract boolean preIteratorRemove();

    /**
     * Notify subclasses the <code>remove</code> method has just been
     * called on the wrapped collection's iterator.
     */
    protected abstract void postIteratorRemove();


    /** {@inheritDoc} */
    public boolean add(final E e)
    {
        boolean result = false;
        if (preAdd(e))
        {
            result = super.add(e);
            postAdd(e);
        }
        return result;
    }

    /** {@inheritDoc} */
    public boolean addAll(final Collection<? extends E> coll)
    {
        boolean result = false;
        if (preAddAll(coll))
        {
            result = super.addAll(coll);
            postAddAll(coll);
        }

        return result;
    }

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
    public Iterator<E> iterator()
    {
        return new ObservableCollectionIterator(super.iterator());
    }

    /** {@inheritDoc} */
    public boolean remove(final Object o)
    {
        boolean result = false;
        if (preRemove(o))
        {
            result = super.remove(o);
            postRemove(o);
        }

        return result;
    }

    /** {@inheritDoc} */
    public boolean removeAll(final Collection<?> coll)
    {
        boolean result = false;
        if (preRemoveAll(coll))
        {
            result = super.removeAll(coll);
            postRemoveAll(coll);
        }

        return result;
    }

    /** {@inheritDoc} */
    public boolean retainAll(final Collection<?> coll)
    {
        boolean result = false;
        if (preRetainAll(coll))
        {
            result = super.retainAll(coll);
            postRetainAll(coll);
        }

        return result;
    }


    /**
     * Observable collection iterator.
     */
    private class ObservableCollectionIterator
        extends AbstractIteratorDecorator<E>
    {

        /**
         * Create a new observable collection iterator that decorates
         * the specified iterator.
         *
         * @param iterator iterator to decorate
         */
        protected ObservableCollectionIterator(final Iterator<E> iterator)
        {
            super(iterator);
        }


        /** {@inheritDoc} */
        public void remove()
        {
            if (preIteratorRemove())
            {
                super.remove();
                postIteratorRemove();
            }
        }
    }
}
