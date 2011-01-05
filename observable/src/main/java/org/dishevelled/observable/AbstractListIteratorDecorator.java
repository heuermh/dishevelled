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

import java.util.ListIterator;

/**
 * Abstract iterator that decorates an instance of <code>ListIterator</code>.
 *
 * @param <E> list iterator element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
abstract class AbstractListIteratorDecorator<E>
    implements ListIterator<E>
{
    /** List iterator this decorator decorates. */
    private final ListIterator<E> listIterator;


    /**
     * Create a new abstract list iterator that decorates the specified list iterator.
     *
     * @param listIterator list iterator to decorate, must not be null
     */
    protected AbstractListIteratorDecorator(final ListIterator<E> listIterator)
    {
        if (listIterator == null)
        {
            throw new IllegalArgumentException("listIterator must not be null");
        }
        this.listIterator = listIterator;
    }


    /**
     * Return a reference to the list iterator this decorator decorates.
     *
     * @return a reference to the list iterator this decorator decorates
     */
    protected final ListIterator<E> getListIterator()
    {
        return listIterator;
    }

    /** {@inheritDoc} */
    public void add(final E e)
    {
        listIterator.add(e);
    }

    /** {@inheritDoc} */
    public boolean hasNext()
    {
        return listIterator.hasNext();
    }

    /** {@inheritDoc} */
    public boolean hasPrevious()
    {
        return listIterator.hasPrevious();
    }

    /** {@inheritDoc} */
    public E next()
    {
        return listIterator.next();
    }

    /** {@inheritDoc} */
    public int nextIndex()
    {
        return listIterator.nextIndex();
    }

    /** {@inheritDoc} */
    public E previous()
    {
        return listIterator.previous();
    }

    /** {@inheritDoc} */
    public int previousIndex()
    {
        return listIterator.previousIndex();
    }

    /** {@inheritDoc} */
    public void remove()
    {
        listIterator.remove();
    }

    /** {@inheritDoc} */
    public void set(final E e)
    {
        listIterator.set(e);
    }
}
