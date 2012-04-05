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
import java.util.Set;
import java.util.Iterator;

/**
 * Abstract set that decorates an instance of <code>Set</code>.
 *
 * @param <E> set element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
abstract class AbstractSetDecorator<E>
    implements Set<E>
{
    /** Set this decorator decorates. */
    private final Set<E> set;


    /**
     * Create a new abstract set that
     * decorates the specified set.
     *
     * @param set set to decorate, must not be null
     */
    protected AbstractSetDecorator(final Set<E> set)
    {
        if (set == null)
        {
            throw new IllegalArgumentException("set must not be null");
        }
        this.set = set;
    }


    /**
     * Return a reference to the set this decorator decorates.
     *
     * @return a reference to the set this decorator decorates
     */
    protected final Set<E> getSet()
    {
        return set;
    }

    /** {@inheritDoc} */
    public boolean add(final E e)
    {
        return set.add(e);
    }

    /** {@inheritDoc} */
    public boolean addAll(final Collection<? extends E> c)
    {
        return set.addAll(c);
    }

    /** {@inheritDoc} */
    public void clear()
    {
        set.clear();
    }

    /** {@inheritDoc} */
    public boolean contains(final Object o)
    {
        return set.contains(o);
    }

    /** {@inheritDoc} */
    public boolean containsAll(final Collection<?> c)
    {
        return set.containsAll(c);
    }

    /** {@inheritDoc} */
    public boolean equals(final Object o)
    {
        return set.equals(o);
    }

    /** {@inheritDoc} */
    public int hashCode()
    {
        return set.hashCode();
    }

    /** {@inheritDoc} */
    public boolean isEmpty()
    {
        return set.isEmpty();
    }

    /** {@inheritDoc} */
    public Iterator<E> iterator()
    {
        return set.iterator();
    }

    /** {@inheritDoc} */
    public boolean remove(final Object o)
    {
        return set.remove(o);
    }

    /** {@inheritDoc} */
    public boolean removeAll(final Collection<?> c)
    {
        return set.removeAll(c);
    }

    /** {@inheritDoc} */
    public boolean retainAll(final Collection<?> c)
    {
        return set.retainAll(c);
    }

    /** {@inheritDoc} */
    public int size()
    {
        return set.size();
    }

    /** {@inheritDoc} */
    public Object[] toArray()
    {
        return set.toArray();
    }

    /** {@inheritDoc} */
    public <T> T[] toArray(final T[] a)
    {
        return set.toArray(a);
    }
}
