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
import java.util.Set;

import org.dishevelled.observable.event.ObservableSetChangeSupport;
import org.dishevelled.observable.event.SetChangeEvent;
import org.dishevelled.observable.event.SetChangeListener;
import org.dishevelled.observable.event.SetChangeVetoException;
import org.dishevelled.observable.event.VetoableSetChangeEvent;
import org.dishevelled.observable.event.VetoableSetChangeListener;

/**
 * Abstract implementation of an observable set
 * that decorates an instance of <code>Set</code>.
 *
 * @param <E> set element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractObservableSet<E>
    extends AbstractSetDecorator<E>
    implements ObservableSet<E>
{
    /** Observable set change support. */
    private final ObservableSetChangeSupport<E> support;


    /**
     * Create a new abstract observable set that
     * decorates the specified set.
     *
     * @param set set to decorate
     */
    protected AbstractObservableSet(final Set<E> set)
    {
        super(set);
        support = new ObservableSetChangeSupport<E>(this);
    }


    /**
     * Return the <code>ObservableSetChangeSupport</code>
     * class backing this abstract observable set.
     *
     * @return the <code>ObservableSetChangeSupport</code>
     *    class backing this abstract observable set
     */
    protected final ObservableSetChangeSupport<E> getObservableSetChangeSupport()
    {
        return support;
    }

    /** {@inheritDoc} */
    public final void addSetChangeListener(final SetChangeListener<E> l)
    {
        support.addSetChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void removeSetChangeListener(final SetChangeListener<E> l)
    {
        support.removeSetChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void addVetoableSetChangeListener(final VetoableSetChangeListener<E> l)
    {
        support.addVetoableSetChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void removeVetoableSetChangeListener(final VetoableSetChangeListener<E> l)
    {
        support.removeVetoableSetChangeListener(l);
    }

    /** {@inheritDoc} */
    public final SetChangeListener<E>[] getSetChangeListeners()
    {
        return support.getSetChangeListeners();
    }

    /** {@inheritDoc} */
    public final int getSetChangeListenerCount()
    {
        return support.getSetChangeListenerCount();
    }

    /** {@inheritDoc} */
    public final VetoableSetChangeListener<E>[] getVetoableSetChangeListeners()
    {
        return support.getVetoableSetChangeListeners();
    }

    /** {@inheritDoc} */
    public final int getVetoableSetChangeListenerCount()
    {
        return support.getVetoableSetChangeListenerCount();
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
        support.fireSetWillChange();
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
        support.fireSetWillChange(e);
    }

    /**
     * Fire a change event to all registered <code>SetChangeListener</code>s.
     */
    public void fireSetChanged()
    {
        support.fireSetChanged();
    }

    /**
     * Fire the specified change event to all registered
     * <code>SetChangeListener</code>s.
     *
     * @param e change event
     */
    public void fireSetChanged(final SetChangeEvent<E> e)
    {
        support.fireSetChanged(e);
    }

    /**
     * Notify subclasses the <code>add</code> method is about to
     * be called on the wrapped set with the specified parameter.
     * Return <code>true</code> to proceed with the change.
     *
     * @param e <code>add</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preAdd(E e);

    /**
     * Notify subclasses the <code>add</code> method has just been
     * called on the wrapped set with the specified parameter.
     *
     * @param e <code>add</code> method parameter
     */
    protected abstract void postAdd(E e);

    /**
     * Notify subclasses the <code>addAll</code> method is about to
     * be called on the wrapped set with the specified parameter.
     * Return <code>true</code> to proceed with the change.
     *
     * @param coll <code>addAll</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preAddAll(Collection<? extends E> coll);

    /**
     * Notify subclasses the <code>addAll</code> method has just been
     * called on the wrapped set with the specified parameter.
     *
     * @param coll <code>addAll</code> method parameter
     */
    protected abstract void postAddAll(Collection<? extends E> coll);

    /**
     * Notify subclasses the <code>clear</code> method is about to
     * be called on the wrapped set.
     * Return <code>true</code> to proceed with the change.
     *
     * @return true to proceed with the change
     */
    protected abstract boolean preClear();

    /**
     * Notify subclasses the <code>clear</code> method has just been
     * called on the wrapped set.
     */
    protected abstract void postClear();

    /**
     * Notify subclasses the <code>remove</code> method is about to
     * be called on the wrapped set with the specified parameter.
     * Return <code>true</code> to proceed with the change.
     *
     * @param o <code>remove</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preRemove(Object o);

    /**
     * Notify subclasses the <code>remove</code> method has just been
     * called on the wrapped set with the specified parameter.
     *
     * @param o <code>remove</code> method parameter
     */
    protected abstract void postRemove(Object o);

    /**
     * Notify subclasses the <code>removeAll</code> method is about to
     * be called on the wrapped set with the specified parameter.
     * Return <code>true</code> to proceed with the change.
     *
     * @param coll <code>removeAll</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preRemoveAll(Collection<?> coll);

    /**
     * Notify subclasses the <code>removeAll</code> method has just been
     * called on the wrapped set with the specified parameter.
     *
     * @param coll <code>removeAll</code> method parameter
     */
    protected abstract void postRemoveAll(Collection<?> coll);

    /**
     * Notify subclasses the <code>retainAll</code> method is about to
     * be called on the wrapped set with the specified parameter.
     * Return <code>true</code> to proceed with the change.
     *
     * @param coll <code>retainAll</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preRetainAll(Collection<?> coll);

    /**
     * Notify subclasses the <code>retainAll</code> method has just been
     * called on the wrapped set with the specified parameter.
     *
     * @param coll <code>retainAll</code> method parameter
     */
    protected abstract void postRetainAll(Collection<?> coll);

    /**
     * Notify subclasses the <code>remove</code> method is about to
     * be called on the wrapped set's iterator.
     * Return <code>true</code> to proceed with the change.
     *
     * @return true to proceed with the change
     */
    protected abstract boolean preIteratorRemove();

    /**
     * Notify subclasses the <code>remove</code> method has just been
     * called on the wrapped set's iterator.
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
        return new ObservableSetIterator(super.iterator());
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
     * Observable set iterator.
     */
    private class ObservableSetIterator
        extends AbstractIteratorDecorator<E>
    {

        /**
         * Create a new observable set iterator that decorates
         * the specified iterator.
         *
         * @param iterator iterator to decorate
         */
        protected ObservableSetIterator(final Iterator<E> iterator)
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
