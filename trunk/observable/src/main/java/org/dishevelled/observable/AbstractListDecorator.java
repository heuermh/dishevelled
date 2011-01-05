/*

    dsh-observable  Observable decorators for collection and map interfaces.
    Copyright (c) 2003-2011 held jointly by the individual authors.

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
import java.util.List;
import java.util.ListIterator;
import java.util.Iterator;

/**
 * Abstract list that decorates an instance of <code>List</code>.
 *
 * @param <E> list element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
abstract class AbstractListDecorator<E>
    implements List<E>//, RandomAccess
{
    /** List this decorator decorates. */
    private final List<E> list;


    /**
     * Create a new abstract list that
     * decorates the specified list.
     *
     * @param list list to decorate, must not be null
     */
    protected AbstractListDecorator(final List<E> list)
    {
        if (list == null)
        {
            throw new IllegalArgumentException("list must not be null");
        }
        this.list = list;
    }


    /**
     * Return a reference to the list this decorator decorates.
     *
     * @return a reference to the list this decorator decorates
     */
    protected final List<E> getList()
    {
        return list;
    }

    /** {@inheritDoc} */
    public void add(final int index, final E e)
    {
        list.add(index, e);
    }

    /** {@inheritDoc} */
    public boolean addAll(final int index, final Collection<? extends E> c)
    {
        return list.addAll(index, c);
    }

    /** {@inheritDoc} */
    public E get(final int index)
    {
        return list.get(index);
    }

    /** {@inheritDoc} */
    public int indexOf(final Object o)
    {
        return list.indexOf(o);
    }

    /** {@inheritDoc} */
    public int lastIndexOf(final Object o)
    {
        return list.lastIndexOf(o);
    }

    /** {@inheritDoc} */
    public ListIterator<E> listIterator()
    {
        return list.listIterator();
    }

    /** {@inheritDoc} */
    public ListIterator<E> listIterator(final int index)
    {
        return list.listIterator(index);
    }

    /** {@inheritDoc} */
    public E remove(final int index)
    {
        return list.remove(index);
    }

    /** {@inheritDoc} */
    public E set(final int index, final E e)
    {
        return list.set(index, e);
    }

    /** {@inheritDoc} */
    public List<E> subList(final int fromIndex, final int toIndex)
    {
        return list.subList(fromIndex, toIndex);
    }

    /** {@inheritDoc} */
    public boolean add(final E e)
    {
        return list.add(e);
    }

    /** {@inheritDoc} */
    public boolean addAll(final Collection<? extends E> c)
    {
        return list.addAll(c);
    }

    /** {@inheritDoc} */
    public void clear()
    {
        list.clear();
    }

    /** {@inheritDoc} */
    public boolean contains(final Object o)
    {
        return list.contains(o);
    }

    /** {@inheritDoc} */
    public boolean containsAll(final Collection<?> c)
    {
        return list.containsAll(c);
    }

    /** {@inheritDoc} */
    public boolean equals(final Object o)
    {
        return list.equals(o);
    }

    /** {@inheritDoc} */
    public int hashCode()
    {
        return list.hashCode();
    }

    /** {@inheritDoc} */
    public boolean isEmpty()
    {
        return list.isEmpty();
    }

    /** {@inheritDoc} */
    public Iterator<E> iterator()
    {
        return list.iterator();
    }

    /** {@inheritDoc} */
    public boolean remove(final Object o)
    {
        return list.remove(o);
    }

    /** {@inheritDoc} */
    public boolean removeAll(final Collection<?> c)
    {
        return list.removeAll(c);
    }

    /** {@inheritDoc} */
    public boolean retainAll(final Collection<?> c)
    {
        return list.retainAll(c);
    }

    /** {@inheritDoc} */
    public int size()
    {
        return list.size();
    }

    /** {@inheritDoc} */
    public Object[] toArray()
    {
        return list.toArray();
    }

    /** {@inheritDoc} */
    public <T> T[] toArray(final T[] a)
    {
        return list.toArray(a);
    }
}
