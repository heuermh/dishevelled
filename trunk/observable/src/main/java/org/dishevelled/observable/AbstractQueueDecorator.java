/*

    dsh-observable  Observable decorators for collection and map interfaces.
    Copyright (c) 2003-2010 held jointly by the individual authors.

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
import java.util.Queue;
import java.util.Iterator;

/**
 * Abstract queue that decorates an instance of <code>Queue</code>.
 *
 * @param <E> queue element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
abstract class AbstractQueueDecorator<E>
    implements Queue<E>
{
    /** Queue this decorator decorates. */
    private final Queue<E> queue;


    /**
     * Create a new abstract queue that
     * decorates the specified queue.
     *
     * @param queue queue to decorate, must not be null
     */
    protected AbstractQueueDecorator(final Queue<E> queue)
    {
        if (queue == null)
        {
            throw new IllegalArgumentException("queue must not be null");
        }
        this.queue = queue;
    }


    /**
     * Return a reference to the queue this decorator decorates.
     *
     * @return a reference to the queue this decorator decorates
     */
    protected final Queue<E> getQueue()
    {
        return queue;
    }


    /** {@inheritDoc} */
    public E element()
    {
        return queue.element();
    }

    /** {@inheritDoc} */
    public boolean offer(final E e)
    {
        return queue.offer(e);
    }

    /** {@inheritDoc} */
    public E peek()
    {
        return queue.peek();
    }

    /** {@inheritDoc} */
    public E poll()
    {
        return queue.poll();
    }

    /** {@inheritDoc} */
    public E remove()
    {
        return queue.remove();
    }

    /** {@inheritDoc} */
    public boolean add(final E e)
    {
        return queue.add(e);
    }

    /** {@inheritDoc} */
    public boolean addAll(final Collection<? extends E> c)
    {
        return queue.addAll(c);
    }

    /** {@inheritDoc} */
    public void clear()
    {
        queue.clear();
    }

    /** {@inheritDoc} */
    public boolean contains(final Object o)
    {
        return queue.contains(o);
    }

    /** {@inheritDoc} */
    public boolean containsAll(final Collection<?> c)
    {
        return queue.containsAll(c);
    }

    /** {@inheritDoc} */
    public boolean equals(final Object o)
    {
        return queue.equals(o);
    }

    /** {@inheritDoc} */
    public int hashCode()
    {
        return queue.hashCode();
    }

    /** {@inheritDoc} */
    public boolean isEmpty()
    {
        return queue.isEmpty();
    }

    /** {@inheritDoc} */
    public Iterator<E> iterator()
    {
        return queue.iterator();
    }

    /** {@inheritDoc} */
    public boolean remove(final Object o)
    {
        return queue.remove(o);
    }

    /** {@inheritDoc} */
    public boolean removeAll(final Collection<?> c)
    {
        return queue.removeAll(c);
    }

    /** {@inheritDoc} */
    public boolean retainAll(final Collection<?> c)
    {
        return queue.retainAll(c);
    }

    /** {@inheritDoc} */
    public int size()
    {
        return queue.size();
    }

    /** {@inheritDoc} */
    public Object[] toArray()
    {
        return queue.toArray();
    }

    /** {@inheritDoc} */
    public <T> T[] toArray(final T[] a)
    {
        return queue.toArray(a);
    }
}
