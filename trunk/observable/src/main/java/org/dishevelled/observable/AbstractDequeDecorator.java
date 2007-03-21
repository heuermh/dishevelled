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

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

/**
 * Abstract deque that decorates an instance of <code>Deque</code>.
 *
 * @param <E> deque element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
abstract class AbstractDequeDecorator<E>
    implements Deque<E>
{
    /** Deque this decorator decorates. */
    private final Deque<E> deque;


    /**
     * Create a new abstract observable deque that
     * decorates the specified deque.
     *
     * @param deque deque to decorate, must not be null
     */
    protected AbstractDequeDecorator(final Deque<E> deque)
    {
        if (deque == null)
        {
            throw new IllegalArgumentException("deque must not be null");
        }
        this.deque = deque;
    }


    /**
     * Return a reference to the deque this decorator decorates.
     *
     * @return a reference to the deque this decorator decorates
     */
    protected final Deque<E> getDeque()
    {
        return deque;
    }


    /** {@inheritDoc} */
    public void addFirst(final E e)
    {
        deque.addFirst(e);
    }

    /** {@inheritDoc} */
    public void addLast(final E e)
    {
        deque.addLast(e);
    }

    /** {@inheritDoc} */
    public Iterator<E> descendingIterator()
    {
        return deque.descendingIterator();
    }

    /** {@inheritDoc} */
    public E getFirst()
    {
        return deque.getFirst();
    }

    /** {@inheritDoc} */
    public E getLast()
    {
        return deque.getLast();
    }

    /** {@inheritDoc} */
    public boolean offerFirst(final E e)
    {
        return deque.offerFirst(e);
    }

    /** {@inheritDoc} */
    public boolean offerLast(final E e)
    {
        return deque.offerLast(e);
    }

    /** {@inheritDoc} */
    public E peekFirst()
    {
        return deque.peekFirst();
    }

    /** {@inheritDoc} */
    public E peekLast()
    {
        return deque.peekLast();
    }

    /** {@inheritDoc} */
    public E pollFirst()
    {
        return deque.pollFirst();
    }

    /** {@inheritDoc} */
    public E pollLast()
    {
        return deque.pollLast();
    }

    /** {@inheritDoc} */
    public E pop()
    {
        return deque.pop();
    }

    /** {@inheritDoc} */
    public void push(final E e)
    {
        deque.push(e);
    }

    /** {@inheritDoc} */
    public E removeFirst()
    {
        return deque.removeFirst();
    }

    /** {@inheritDoc} */
    public boolean removeFirstOccurrence(final Object o)
    {
        return deque.removeFirstOccurrence(o);
    }

    /** {@inheritDoc} */
    public E removeLast()
    {
        return deque.removeLast();
    }

    /** {@inheritDoc} */
    public boolean removeLastOccurrence(final Object o)
    {
        return deque.removeLastOccurrence(o);
    }

    /** {@inheritDoc} */
    public E element()
    {
        return deque.element();
    }

    /** {@inheritDoc} */
    public boolean offer(final E e)
    {
        return deque.offer(e);
    }

    /** {@inheritDoc} */
    public E peek()
    {
        return deque.peek();
    }

    /** {@inheritDoc} */
    public E poll()
    {
        return deque.poll();
    }

    /** {@inheritDoc} */
    public E remove()
    {
        return deque.remove();
    }

    /** {@inheritDoc} */
    public boolean add(final E e)
    {
        return deque.add(e);
    }

    /** {@inheritDoc} */
    public boolean addAll(final Collection<? extends E> c)
    {
        return deque.addAll(c);
    }

    /** {@inheritDoc} */
    public void clear()
    {
        deque.clear();
    }

    /** {@inheritDoc} */
    public boolean contains(final Object o)
    {
        return deque.contains(o);
    }

    /** {@inheritDoc} */
    public boolean containsAll(final Collection<?> c)
    {
        return deque.containsAll(c);
    }

    /** {@inheritDoc} */
    public boolean equals(final Object o)
    {
        return deque.equals(o);
    }

    /** {@inheritDoc} */
    public int hashCode()
    {
        return deque.hashCode();
    }

    /** {@inheritDoc} */
    public boolean isEmpty()
    {
        return deque.isEmpty();
    }

    /** {@inheritDoc} */
    public Iterator<E> iterator()
    {
        return deque.iterator();
    }

    /** {@inheritDoc} */
    public boolean remove(final Object o)
    {
        return deque.remove(o);
    }

    /** {@inheritDoc} */
    public boolean removeAll(final Collection<?> c)
    {
        return deque.removeAll(c);
    }

    /** {@inheritDoc} */
    public boolean retainAll(final Collection<?> c)
    {
        return deque.retainAll(c);
    }

    /** {@inheritDoc} */
    public int size()
    {
        return deque.size();
    }

    /** {@inheritDoc} */
    public Object[] toArray()
    {
        return deque.toArray();
    }

    /** {@inheritDoc} */
    public <T> T[] toArray(final T[] a)
    {
        return deque.toArray(a);
    }
}
