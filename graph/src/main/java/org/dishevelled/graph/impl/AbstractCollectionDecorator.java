/*

    dsh-graph  Directed graph interface and implementation.
    Copyright (c) 2004-2011 held jointly by the individual authors.

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
package org.dishevelled.graph.impl;

import java.util.Collection;
import java.util.Iterator;

/**
 * Abstract collection that decorates an instance of <code>Collection</code>.
 *
 * @param <E> collection element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
abstract class AbstractCollectionDecorator<E>
    implements Collection<E>
{
    /** Collection this decorator decorates. */
    private final Collection<E> collection;


    /**
     * Create a new abstract collection that
     * decorates the specified collection.
     *
     * @param collection collection to decorate, must not be null
     */
    protected AbstractCollectionDecorator(final Collection<E> collection)
    {
        if (collection == null)
        {
            throw new IllegalArgumentException("collection must not be null");
        }
        this.collection = collection;
    }


    /**
     * Return a reference to the collection this decorator decorates.
     *
     * @return a reference to the collection this decorator decorates
     */
    protected final Collection<E> getCollection()
    {
        return collection;
    }

    /** {@inheritDoc} */
    public boolean add(final E e)
    {
        return collection.add(e);
    }

    /** {@inheritDoc} */
    public boolean addAll(final Collection<? extends E> c)
    {
        return collection.addAll(c);
    }

    /** {@inheritDoc} */
    public void clear()
    {
        collection.clear();
    }

    /** {@inheritDoc} */
    public boolean contains(final Object o)
    {
        return collection.contains(o);
    }

    /** {@inheritDoc} */
    public boolean containsAll(final Collection<?> c)
    {
        return collection.containsAll(c);
    }

    /** {@inheritDoc} */
    public boolean equals(final Object o)
    {
        return collection.equals(o);
    }

    /** {@inheritDoc} */
    public int hashCode()
    {
        return collection.hashCode();
    }

    /** {@inheritDoc} */
    public boolean isEmpty()
    {
        return collection.isEmpty();
    }

    /** {@inheritDoc} */
    public Iterator<E> iterator()
    {
        return collection.iterator();
    }

    /** {@inheritDoc} */
    public boolean remove(final Object o)
    {
        return collection.remove(o);
    }

    /** {@inheritDoc} */
    public boolean removeAll(final Collection<?> c)
    {
        return collection.removeAll(c);
    }

    /** {@inheritDoc} */
    public boolean retainAll(final Collection<?> c)
    {
        return collection.retainAll(c);
    }

    /** {@inheritDoc} */
    public int size()
    {
        return collection.size();
    }

    /** {@inheritDoc} */
    public Object[] toArray()
    {
        return collection.toArray();
    }

    /** {@inheritDoc} */
    public <T> T[] toArray(final T[] a)
    {
        return collection.toArray(a);
    }
}
