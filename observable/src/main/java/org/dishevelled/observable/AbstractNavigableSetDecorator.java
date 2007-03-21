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
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.SortedSet;

/**
 * Abstract set that decorates an instance of <code>NavigableSet</code>.
 *
 * @param <E> navigable set element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
abstract class AbstractNavigableSetDecorator<E>
    implements NavigableSet<E>
{
    /** Navigable set this decorator decorates. */
    private final NavigableSet<E> navigableSet;


    /**
     * Create a new abstract observable navigable set that
     * decorates the specified navigable set.
     *
     * @param navigableSet navigable set to decorate, must not be null
     */
    protected AbstractNavigableSetDecorator(final NavigableSet<E> navigableSet)
    {
        if (navigableSet == null)
        {
            throw new IllegalArgumentException("navigableSet must not be null");
        }
        this.navigableSet = navigableSet;
    }


    /**
     * Return a reference to the navigable set this decorator decorates.
     *
     * @return a reference to the navigable set this decorator decorates
     */
    protected final NavigableSet<E> getNavigableSet()
    {
        return navigableSet;
    }

    /** {@inheritDoc} */
    public E ceiling(final E e)
    {
        return navigableSet.ceiling(e);
    }

    /** {@inheritDoc} */
    public Iterator<E> descendingIterator()
    {
        return navigableSet.descendingIterator();
    }

    /** {@inheritDoc} */
    public NavigableSet<E> descendingSet()
    {
        return navigableSet.descendingSet();
    }

    /** {@inheritDoc} */
    public E floor(final E e)
    {
        return navigableSet.floor(e);
    }

    /** {@inheritDoc} */
    public NavigableSet<E> headSet(final E toElement, final boolean inclusive)
    {
        return navigableSet.headSet(toElement, inclusive);
    }

    /** {@inheritDoc} */
    public E higher(final E e)
    {
        return navigableSet.higher(e);
    }

    /** {@inheritDoc} */
    public E lower(final E e)
    {
        return navigableSet.lower(e);
    }

    /** {@inheritDoc} */
    public E pollFirst()
    {
        return navigableSet.pollFirst();
    }

    /** {@inheritDoc} */
    public E pollLast()
    {
        return navigableSet.pollLast();
    }

    /** {@inheritDoc} */
    public NavigableSet<E> subSet(final E fromElement, final boolean fromInclusive,
                                  final E toElement, final boolean toInclusive)
    {
        return navigableSet.subSet(fromElement, fromInclusive,
                                   toElement, toInclusive);
    }

    /** {@inheritDoc} */
    public NavigableSet<E> tailSet(final E fromElement, final boolean inclusive)
    {
        return navigableSet.tailSet(fromElement, inclusive);
    }

    /** {@inheritDoc} */
    public Comparator<? super E> comparator()
    {
        return navigableSet.comparator();
    }

    /** {@inheritDoc} */
    public E first()
    {
        return navigableSet.first();
    }

    /** {@inheritDoc} */
    public SortedSet<E> headSet(final E toElement)
    {
        return navigableSet.headSet(toElement);
    }

    /** {@inheritDoc} */
    public E last()
    {
        return navigableSet.last();
    }

    /** {@inheritDoc} */
    public SortedSet<E> subSet(final E fromElement, final E toElement)
    {
        return navigableSet.subSet(fromElement, toElement);
    }

    /** {@inheritDoc} */
    public SortedSet<E> tailSet(final E fromElement)
    {
        return navigableSet.tailSet(fromElement);
    }

    /** {@inheritDoc} */
    public boolean add(final E e)
    {
        return navigableSet.add(e);
    }

    /** {@inheritDoc} */
    public boolean addAll(final Collection<? extends E> c)
    {
        return navigableSet.addAll(c);
    }

    /** {@inheritDoc} */
    public void clear()
    {
        navigableSet.clear();
    }

    /** {@inheritDoc} */
    public boolean contains(final Object o)
    {
        return navigableSet.contains(o);
    }

    /** {@inheritDoc} */
    public boolean containsAll(final Collection<?> c)
    {
        return navigableSet.containsAll(c);
    }

    /** {@inheritDoc} */
    public boolean equals(final Object o)
    {
        return navigableSet.equals(o);
    }

    /** {@inheritDoc} */
    public int hashCode()
    {
        return navigableSet.hashCode();
    }

    /** {@inheritDoc} */
    public boolean isEmpty()
    {
        return navigableSet.isEmpty();
    }

    /** {@inheritDoc} */
    public Iterator<E> iterator()
    {
        return navigableSet.iterator();
    }

    /** {@inheritDoc} */
    public boolean remove(final Object o)
    {
        return navigableSet.remove(o);
    }

    /** {@inheritDoc} */
    public boolean removeAll(final Collection<?> c)
    {
        return navigableSet.removeAll(c);
    }

    /** {@inheritDoc} */
    public boolean retainAll(final Collection<?> c)
    {
        return navigableSet.retainAll(c);
    }

    /** {@inheritDoc} */
    public int size()
    {
        return navigableSet.size();
    }

    /** {@inheritDoc} */
    public Object[] toArray()
    {
        return navigableSet.toArray();
    }

    /** {@inheritDoc} */
    public <T> T[] toArray(final T[] a)
    {
        return navigableSet.toArray(a);
    }
}
