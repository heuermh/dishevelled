/*

    dsh-observable  Observable decorators for collection and map interfaces.
    Copyright (c) 2003-2007 held jointly by the individual authors.

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

import java.util.Iterator;

/**
 * Abstract iterator that decorates an instance of <code>Iterator</code>.
 *
 * @param <E> iterator element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
abstract class AbstractIteratorDecorator<E>
    implements Iterator<E>
{
    /** Iterator this decorator decorates. */
    private final Iterator<E> iterator;


    /**
     * Create a new abstract iterator that decorates the specified iterator.
     *
     * @param iterator iterator to decorate, must not be null
     */
    protected AbstractIteratorDecorator(final Iterator<E> iterator)
    {
        if (iterator == null)
        {
            throw new IllegalArgumentException("iterator must not be null");
        }
        this.iterator = iterator;
    }


    /**
\     * Return a reference to the iterator this decorator decorates.
     *
     * @return a reference to the iterator this decorator decorates
     */
    protected final Iterator<E> getIterator()
    {
        return iterator;
    }

    /** {@inheritDoc} */
    public boolean hasNext()
    {
        return iterator.hasNext();
    }

    /** {@inheritDoc} */
    public E next()
    {
        return iterator.next();
    }

    /** {@inheritDoc} */
    public void remove()
    {
        iterator.remove();
    }
}
