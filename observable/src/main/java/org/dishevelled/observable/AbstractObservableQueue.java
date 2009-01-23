/*

    dsh-observable  Observable decorators for collection and map interfaces.
    Copyright (c) 2003-2009 held jointly by the individual authors.

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
import java.util.Queue;

import org.dishevelled.observable.event.ObservableQueueChangeSupport;
import org.dishevelled.observable.event.QueueChangeEvent;
import org.dishevelled.observable.event.QueueChangeListener;
import org.dishevelled.observable.event.QueueChangeVetoException;
import org.dishevelled.observable.event.VetoableQueueChangeEvent;
import org.dishevelled.observable.event.VetoableQueueChangeListener;

/**
 * Abstract implementation of an observable queue
 * that decorates an instance of <code>Queue</code>.
 *
 * @param <E> queue element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractObservableQueue<E>
    extends AbstractQueueDecorator<E>
    implements ObservableQueue<E>
{
    /** Observable queue change support. */
    private ObservableQueueChangeSupport<E> support;


    /**
     * Create a new abstract observable queue that
     * decorates the specified queue.
     *
     * @param queue queue to decorate
     */
    protected AbstractObservableQueue(final Queue<E> queue)
    {
        super(queue);
        support = new ObservableQueueChangeSupport(this);
    }


    /**
     * Return the <code>ObservableQueueChangeSupport</code>
     * class backing this abstract observable queue.
     *
     * @return the <code>ObservableQueueChangeSupport</code>
     *    class backing this abstract observable queue
     */
    protected final ObservableQueueChangeSupport<E> getObservableQueueChangeSupport()
    {
        return support;
    }

    /** {@inheritDoc} */
    public final void addQueueChangeListener(final QueueChangeListener<E> l)
    {
        support.addQueueChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void removeQueueChangeListener(final QueueChangeListener<E> l)
    {
        support.removeQueueChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void addVetoableQueueChangeListener(final VetoableQueueChangeListener<E> l)
    {
        support.addVetoableQueueChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void removeVetoableQueueChangeListener(final VetoableQueueChangeListener<E> l)
    {
        support.removeVetoableQueueChangeListener(l);
    }

    /** {@inheritDoc} */
    public final QueueChangeListener<E>[] getQueueChangeListeners()
    {
        return support.getQueueChangeListeners();
    }

    /** {@inheritDoc} */
    public final int getQueueChangeListenerCount()
    {
        return support.getQueueChangeListenerCount();
    }

    /** {@inheritDoc} */
    public final VetoableQueueChangeListener<E>[] getVetoableQueueChangeListeners()
    {
        return support.getVetoableQueueChangeListeners();
    }

    /** {@inheritDoc} */
    public final int getVetoableQueueChangeListenerCount()
    {
        return support.getVetoableQueueChangeListenerCount();
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
        support.fireQueueWillChange();
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
        support.fireQueueWillChange(e);
    }

    /**
     * Fire a change event to all registered <code>QueueChangeListener</code>s.
     */
    public void fireQueueChanged()
    {
        support.fireQueueChanged();
    }

    /**
     * Fire the specified change event to all registered
     * <code>QueueChangeListener</code>s.
     *
     * @param e change event
     */
    public void fireQueueChanged(final QueueChangeEvent<E> e)
    {
        support.fireQueueChanged(e);
    }

    /**
     * Notify subclasses the <code>add</code> method is about to
     * be called on the wrapped queue with the specified parameter.
     * Return <code>true</code> to proceed with the change.
     *
     * @param e <code>add</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preAdd(E e);

    /**
     * Notify subclasses the <code>add</code> method has just been
     * called on the wrapped queue with the specified parameter.
     *
     * @param e <code>add</code> method parameter
     */
    protected abstract void postAdd(E e);

    /**
     * Notify subclasses the <code>addAll</code> method is about to
     * be called on the wrapped queue with the specified parameter.
     * Return <code>true</code> to proceed with the change.
     *
     * @param coll <code>addAll</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preAddAll(Collection<? extends E> coll);

    /**
     * Notify subclasses the <code>addAll</code> method has just been
     * called on the wrapped queue with the specified parameter.
     *
     * @param coll <code>addAll</code> method parameter
     */
    protected abstract void postAddAll(Collection<? extends E> coll);

    /**
     * Notify subclasses the <code>clear</code> method is about to
     * be called on the wrapped queue.
     * Return <code>true</code> to proceed with the change.
     *
     * @return true to proceed with the change
     */
    protected abstract boolean preClear();

    /**
     * Notify subclasses the <code>clear</code> method has just been
     * called on the wrapped queue.
     */
    protected abstract void postClear();

    /**
     * Notify subclasses the <code>remove</code> method is about to
     * be called on the wrapped queue with the specified parameter.
     * Return <code>true</code> to proceed with the change.
     *
     * @param o <code>remove</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preRemove(Object o);

    /**
     * Notify subclasses the <code>remove</code> method has just been
     * called on the wrapped queue with the specified parameter.
     *
     * @param o <code>remove</code> method parameter
     */
    protected abstract void postRemove(Object o);

    /**
     * Notify subclasses the <code>removeAll</code> method is about to
     * be called on the wrapped queue with the specified parameter.
     * Return <code>true</code> to proceed with the change.
     *
     * @param coll <code>removeAll</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preRemoveAll(Collection<?> coll);

    /**
     * Notify subclasses the <code>removeAll</code> method has just been
     * called on the wrapped queue with the specified parameter.
     *
     * @param coll <code>removeAll</code> method parameter
     */
    protected abstract void postRemoveAll(Collection<?> coll);

    /**
     * Notify subclasses the <code>retainAll</code> method is about to
     * be called on the wrapped queue with the specified parameter.
     * Return <code>true</code> to proceed with the change.
     *
     * @param coll <code>retainAll</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preRetainAll(Collection<?> coll);

    /**
     * Notify subclasses the <code>retainAll</code> method has just been
     * called on the wrapped queue with the specified parameter.
     *
     * @param coll <code>retainAll</code> method parameter
     */
    protected abstract void postRetainAll(Collection<?> coll);

    /**
     * Notify subclasses the <code>remove</code> method is about to
     * be called on the wrapped queue's iterator.
     * Return <code>true</code> to proceed with the change.
     *
     * @return true to proceed with the change
     */
    protected abstract boolean preIteratorRemove();

    /**
     * Notify subclasses the <code>remove</code> method has just been
     * called on the wrapped queue's iterator.
     */
    protected abstract void postIteratorRemove();

    /**
     * Notify subclasses the <code>offer</code> method is about to
     * be called on the wrapped queue with the specified parameter.
     * Return <code>true</code> to proceed with the change.
     *
     * @param e <code>offer</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preOffer(E e);

    /**
     * Notify subclasses the <code>offer</code> method has just been
     * called on the wrapped queue with the specified parameter.
     *
     * @param e <code>offer</code> method parameter
     */
    protected abstract void postOffer(E e);

    /**
     * Notify subclasses the <code>poll</code> method is about to
     * be called on the wrapped queue.
     * Return <code>true</code> to proceed with the change.
     *
     * @return true to proceed with the change
     */
    protected abstract boolean prePoll();

    /**
     * Notify subclasses the <code>poll</code> method has just been
     * called on the wrapped queue.
     */
    protected abstract void postPoll();


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
        return new ObservableQueueIterator(super.iterator());
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

    /** {@inheritDoc} */
    public boolean offer(final E e)
    {
        boolean result = false;
        if (preOffer(e))
        {
            result = super.offer(e);
            postOffer(e);
        }
        return result;
    }

    /** {@inheritDoc} */
    public E poll()
    {
        E result = null;
        if (prePoll())
        {
            result = super.poll();
            postPoll();
        }
        return result;
    }


    /**
     * Observable queue iterator.
     */
    private class ObservableQueueIterator
        extends AbstractIteratorDecorator<E>
    {

        /**
         * Create a new observable queue iterator that decorates
         * the specified iterator.
         *
         * @param iterator iterator to decorate
         */
        protected ObservableQueueIterator(final Iterator<E> iterator)
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
