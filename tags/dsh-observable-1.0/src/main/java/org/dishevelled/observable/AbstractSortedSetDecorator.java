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
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;

/**
 * Abstract set that decorates an instance of <code>SortedSet</code>.
 *
 * @param <E> sorted set element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
abstract class AbstractSortedSetDecorator<E>
    implements SortedSet<E>
{
    /** Sorted set this decorator decorates. */
    private final SortedSet<E> sortedSet;


    /**
     * Create a new abstract sorted set that
     * decorates the specified sorted set.
     *
     * @param sortedSet sorted set to decorate, must not be null
     */
    protected AbstractSortedSetDecorator(final SortedSet<E> sortedSet)
    {
        if (sortedSet == null)
        {
            throw new IllegalArgumentException("sortedSet must not be null");
        }
        this.sortedSet = sortedSet;
    }


    /**
     * Return a reference to the sorted set this decorator decorates.
     *
     * @return a reference to the sorted set this decorator decorates
     */
    protected final SortedSet<E> getSortedSet()
    {
        return sortedSet;
    }

    /** {@inheritDoc} */
    public Comparator<? super E> comparator()
    {
        return sortedSet.comparator();
    }

    /** {@inheritDoc} */
    public E first()
    {
        return sortedSet.first();
    }

    /** {@inheritDoc} */
    public SortedSet<E> headSet(final E toElement)
    {
        return sortedSet.headSet(toElement);
    }

    /** {@inheritDoc} */
    public E last()
    {
        return sortedSet.last();
    }

    /** {@inheritDoc} */
    public SortedSet<E> subSet(final E fromElement, final E toElement)
    {
        return sortedSet.subSet(fromElement, toElement);
    }

    /** {@inheritDoc} */
    public SortedSet<E> tailSet(final E fromElement)
    {
        return sortedSet.tailSet(fromElement);
    }

    /** {@inheritDoc} */
    public boolean add(final E e)
    {
        return sortedSet.add(e);
    }

    /** {@inheritDoc} */
    public boolean addAll(final Collection<? extends E> c)
    {
        return sortedSet.addAll(c);
    }

    /** {@inheritDoc} */
    public void clear()
    {
        sortedSet.clear();
    }

    /** {@inheritDoc} */
    public boolean contains(final Object o)
    {
        return sortedSet.contains(o);
    }

    /** {@inheritDoc} */
    public boolean containsAll(final Collection<?> c)
    {
        return sortedSet.containsAll(c);
    }

    /** {@inheritDoc} */
    public boolean equals(final Object o)
    {
        return sortedSet.equals(o);
    }

    /** {@inheritDoc} */
    public int hashCode()
    {
        return sortedSet.hashCode();
    }

    /** {@inheritDoc} */
    public boolean isEmpty()
    {
        return sortedSet.isEmpty();
    }

    /** {@inheritDoc} */
    public Iterator<E> iterator()
    {
        return sortedSet.iterator();
    }

    /** {@inheritDoc} */
    public boolean remove(final Object o)
    {
        return sortedSet.remove(o);
    }

    /** {@inheritDoc} */
    public boolean removeAll(final Collection<?> c)
    {
        return sortedSet.removeAll(c);
    }

    /** {@inheritDoc} */
    public boolean retainAll(final Collection<?> c)
    {
        return sortedSet.retainAll(c);
    }

    /** {@inheritDoc} */
    public int size()
    {
        return sortedSet.size();
    }

    /** {@inheritDoc} */
    public Object[] toArray()
    {
        return sortedSet.toArray();
    }

    /** {@inheritDoc} */
    public <T> T[] toArray(final T[] a)
    {
        return sortedSet.toArray(a);
    }
}
