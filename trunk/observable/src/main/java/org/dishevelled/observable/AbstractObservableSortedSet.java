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
package org.dishevelled.observable;

import java.util.Collection;
import java.util.Iterator;
import java.util.SortedSet;

import org.dishevelled.observable.event.ObservableSortedSetChangeSupport;
import org.dishevelled.observable.event.SortedSetChangeEvent;
import org.dishevelled.observable.event.SortedSetChangeListener;
import org.dishevelled.observable.event.SortedSetChangeVetoException;
import org.dishevelled.observable.event.VetoableSortedSetChangeEvent;
import org.dishevelled.observable.event.VetoableSortedSetChangeListener;

/**
 * Abstract implementation of an observable sorted set
 * that decorates an instance of <code>SortedSet</code>.
 *
 * @param <E> sorted set element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractObservableSortedSet<E>
    extends AbstractSortedSetDecorator<E>
    implements ObservableSortedSet<E>
{
    /** Observable sorted set change support. */
    private final ObservableSortedSetChangeSupport<E> support;


    /**
     * Create a new abstract observable sorted set that
     * decorates the specified sorted set.
     *
     * @param sortedSet sorted set to decorate
     */
    protected AbstractObservableSortedSet(final SortedSet<E> sortedSet)
    {
        super(sortedSet);
        support = new ObservableSortedSetChangeSupport<E>(this);
    }


    /**
     * Return the <code>ObservableSortedSetChangeSupport</code>
     * class backing this abstract observable sorted set.
     *
     * @return the <code>ObservableSortedSetChangeSupport</code>
     *    class backing this abstract observable sorted set
     */
    protected final ObservableSortedSetChangeSupport<E> getObservableSortedSetChangeSupport()
    {
        return support;
    }

    /** {@inheritDoc} */
    public final void addSortedSetChangeListener(final SortedSetChangeListener<E> l)
    {
        support.addSortedSetChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void removeSortedSetChangeListener(final SortedSetChangeListener<E> l)
    {
        support.removeSortedSetChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void addVetoableSortedSetChangeListener(final VetoableSortedSetChangeListener<E> l)
    {
        support.addVetoableSortedSetChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void removeVetoableSortedSetChangeListener(final VetoableSortedSetChangeListener<E> l)
    {
        support.removeVetoableSortedSetChangeListener(l);
    }

    /** {@inheritDoc} */
    public final SortedSetChangeListener<E>[] getSortedSetChangeListeners()
    {
        return support.getSortedSetChangeListeners();
    }

    /** {@inheritDoc} */
    public final int getSortedSetChangeListenerCount()
    {
        return support.getSortedSetChangeListenerCount();
    }

    /** {@inheritDoc} */
    public final VetoableSortedSetChangeListener<E>[] getVetoableSortedSetChangeListeners()
    {
        return support.getVetoableSortedSetChangeListeners();
    }

    /** {@inheritDoc} */
    public final int getVetoableSortedSetChangeListenerCount()
    {
        return support.getVetoableSortedSetChangeListenerCount();
    }

    /**
     * Fire a will change event to all registered
     * <code>VetoableSortedSetChangeListener</code>s.
     *
     * @throws SortedSetChangeVetoException if any of the listeners veto the change
     */
    public void fireSortedSetWillChange()
        throws SortedSetChangeVetoException
    {
        support.fireSortedSetWillChange();
    }

    /**
     * Fire the specified will change event to all registered
     * <code>VetoableSortedSetChangeListener</code>s.
     *
     * @param e will change event
     * @throws SortedSetChangeVetoException if any of the listeners veto the change
     */
    public void fireSortedSetWillChange(final VetoableSortedSetChangeEvent<E> e)
        throws SortedSetChangeVetoException
    {
        support.fireSortedSetWillChange(e);
    }

    /**
     * Fire a change event to all registered <code>SortedSetChangeListener</code>s.
     */
    public void fireSortedSetChanged()
    {
        support.fireSortedSetChanged();
    }

    /**
     * Fire the specified change event to all registered
     * <code>SortedSetChangeListener</code>s.
     *
     * @param e change event
     */
    public void fireSortedSetChanged(final SortedSetChangeEvent<E> e)
    {
        support.fireSortedSetChanged(e);
    }

    /**
     * Notify subclasses the <code>add</code> method is about to
     * be called on the wrapped sorted set with the specified parameter.
     * Return <code>true</code> to proceed with the change.
     *
     * @param e <code>add</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preAdd(E e);

    /**
     * Notify subclasses the <code>add</code> method has just been
     * called on the wrapped sorted set with the specified parameter.
     *
     * @param e <code>add</code> method parameter
     */
    protected abstract void postAdd(E e);

    /**
     * Notify subclasses the <code>addAll</code> method is about to
     * be called on the wrapped sorted set with the specified parameter.
     * Return <code>true</code> to proceed with the change.
     *
     * @param coll <code>addAll</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preAddAll(Collection<? extends E> coll);

    /**
     * Notify subclasses the <code>addAll</code> method has just been
     * called on the wrapped sorted set with the specified parameter.
     *
     * @param coll <code>addAll</code> method parameter
     */
    protected abstract void postAddAll(Collection<? extends E> coll);

    /**
     * Notify subclasses the <code>clear</code> method is about to
     * be called on the wrapped sorted set.
     * Return <code>true</code> to proceed with the change.
     *
     * @return true to proceed with the change
     */
    protected abstract boolean preClear();

    /**
     * Notify subclasses the <code>clear</code> method has just been
     * called on the wrapped sorted set.
     */
    protected abstract void postClear();

    /**
     * Notify subclasses the <code>remove</code> method is about to
     * be called on the wrapped sorted set with the specified parameter.
     * Return <code>true</code> to proceed with the change.
     *
     * @param o <code>remove</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preRemove(Object o);

    /**
     * Notify subclasses the <code>remove</code> method has just been
     * called on the wrapped sorted set with the specified parameter.
     *
     * @param o <code>remove</code> method parameter
     */
    protected abstract void postRemove(Object o);

    /**
     * Notify subclasses the <code>removeAll</code> method is about to
     * be called on the wrapped sorted set with the specified parameter.
     * Return <code>true</code> to proceed with the change.
     *
     * @param coll <code>removeAll</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preRemoveAll(Collection<?> coll);

    /**
     * Notify subclasses the <code>removeAll</code> method has just been
     * called on the wrapped sorted set with the specified parameter.
     *
     * @param coll <code>removeAll</code> method parameter
     */
    protected abstract void postRemoveAll(Collection<?> coll);

    /**
     * Notify subclasses the <code>retainAll</code> method is about to
     * be called on the wrapped sorted set with the specified parameter.
     * Return <code>true</code> to proceed with the change.
     *
     * @param coll <code>retainAll</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preRetainAll(Collection<?> coll);

    /**
     * Notify subclasses the <code>retainAll</code> method has just been
     * called on the wrapped sorted set with the specified parameter.
     *
     * @param coll <code>retainAll</code> method parameter
     */
    protected abstract void postRetainAll(Collection<?> coll);

    /**
     * Notify subclasses the <code>remove</code> method is about to
     * be called on the wrapped sorted set's iterator.
     * Return <code>true</code> to proceed with the change.
     *
     * @return true to proceed with the change
     */
    protected abstract boolean preIteratorRemove();

    /**
     * Notify subclasses the <code>remove</code> method has just been
     * called on the wrapped sorted set's iterator.
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
        return new ObservableSortedSetIterator(super.iterator());
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
     * Observable sorted set iterator.
     */
    private class ObservableSortedSetIterator
        extends AbstractIteratorDecorator<E>
    {

        /**
         * Create a new observable sorted set iterator that decorates
         * the specified iterator.
         *
         * @param iterator iterator to decorate
         */
        protected ObservableSortedSetIterator(final Iterator<E> iterator)
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
