/*

    dsh-observable  Observable decorators for collection and map interfaces.
    Copyright (c) 2003-2013 held jointly by the individual authors.

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
package org.dishevelled.observable.impl;

import java.util.Collection;
import java.util.Queue;

import org.dishevelled.observable.AbstractObservableQueue;

import org.dishevelled.observable.event.QueueChangeEvent;
import org.dishevelled.observable.event.QueueChangeVetoException;
import org.dishevelled.observable.event.VetoableQueueChangeEvent;

/**
 * Observable queue decorator that fires empty
 * vetoable queue change events in <code>preXxx</code> methods and
 * empty queue change events in <code>postXxx</code> methods.
 * Observable queue listeners may query the source of the events to determine
 * what may or may not have changed due to the event.
 *
 * @param <E> queue element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class ObservableQueueImpl<E>
    extends AbstractObservableQueue<E>
{
    /** Cached queue change event. */
    private final QueueChangeEvent<E> changeEvent;

    /** Cached vetoable queue change event. */
    private final VetoableQueueChangeEvent<E> vetoableChangeEvent;


    /**
     * Create a new observable decorator for the specified
     * queue.
     *
     * @param queue queue to decorate, must not be null
     */
    public ObservableQueueImpl(final Queue<E> queue)
    {
        super(queue);
        changeEvent = new QueueChangeEvent<E>(this);
        vetoableChangeEvent = new VetoableQueueChangeEvent<E>(this);
    }


    /** {@inheritDoc} */
    protected boolean preAdd(final E e)
    {
        try
        {
            fireQueueWillChange(vetoableChangeEvent);
            return true;
        }
        catch (QueueChangeVetoException ex)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postAdd(final E e)
    {
        fireQueueChanged(changeEvent);
    }

    /** {@inheritDoc} */
    protected boolean preAddAll(final Collection<? extends E> coll)
    {
        try
        {
            fireQueueWillChange(vetoableChangeEvent);
            return true;
        }
        catch (QueueChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postAddAll(final Collection<? extends E> coll)
    {
        fireQueueChanged(changeEvent);
    }

    /** {@inheritDoc} */
    protected boolean preClear()
    {
        try
        {
            fireQueueWillChange(vetoableChangeEvent);
            return true;
        }
        catch (QueueChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postClear()
    {
        fireQueueChanged(changeEvent);
    }

    /** {@inheritDoc} */
    protected boolean preRemove(final Object o)
    {
        try
        {
            fireQueueWillChange(vetoableChangeEvent);
            return true;
        }
        catch (QueueChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postRemove(final Object o)
    {
        fireQueueChanged(changeEvent);
    }

    /** {@inheritDoc} */
    protected boolean preRemoveAll(final Collection<?> coll)
    {
        try
        {
            fireQueueWillChange(vetoableChangeEvent);
            return true;
        }
        catch (QueueChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postRemoveAll(final Collection<?> coll)
    {
        fireQueueChanged(changeEvent);
    }

    /** {@inheritDoc} */
    protected boolean preRetainAll(final Collection<?> coll)
    {
        try
        {
            fireQueueWillChange(vetoableChangeEvent);
            return true;
        }
        catch (QueueChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postRetainAll(final Collection<?> coll)
    {
        fireQueueChanged(changeEvent);
    }

    /** {@inheritDoc} */
    protected boolean preIteratorRemove()
    {
        try
        {
            fireQueueWillChange(vetoableChangeEvent);
            return true;
        }
        catch (QueueChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postIteratorRemove()
    {
        fireQueueChanged(changeEvent);
    }

    /** {@inheritDoc} */
    protected boolean preOffer(final E e)
    {
        try
        {
            fireQueueWillChange(vetoableChangeEvent);
            return true;
        }
        catch (QueueChangeVetoException ex)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postOffer(final E e)
    {
        fireQueueChanged(changeEvent);
    }

    /** {@inheritDoc} */
    protected boolean prePoll()
    {
        try
        {
            fireQueueWillChange(vetoableChangeEvent);
            return true;
        }
        catch (QueueChangeVetoException ex)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postPoll()
    {
        fireQueueChanged(changeEvent);
    }
}
