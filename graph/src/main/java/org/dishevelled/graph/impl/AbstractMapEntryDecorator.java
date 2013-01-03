/*

    dsh-graph  Directed graph interface and implementation.
    Copyright (c) 2004-2013 held jointly by the individual authors.

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

import java.util.Map;

/**
 * Abstract map entry decorator.
 *
 * @param <K> map entry key type
 * @param <V> map entry value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
abstract class AbstractMapEntryDecorator<K, V>
    implements Map.Entry<K, V>
{
    /** Map entry this decorator decorates. */
    private Map.Entry<K, V> entry;


    /**
     * Create a new abstract map entyr that decorates the
     * specified map entry.
     *
     * @param entry map entry to decorate, must not be null
     */
    protected AbstractMapEntryDecorator(final Map.Entry<K, V> entry)
    {
        if (entry == null)
        {
            throw new IllegalArgumentException("entry must not be null");
        }
        this.entry = entry;
    }


    /** {@inheritDoc} */
    public K getKey()
    {
        return entry.getKey();
    }

    /** {@inheritDoc} */
    public V getValue()
    {
        return entry.getValue();
    }

    /** {@inheritDoc} */
    public V setValue(final V value)
    {
        return entry.setValue(value);
    }

    /** {@inheritDoc} */
    public int hashCode()
    {
        return entry.hashCode();
    }

    /** {@inheritDoc} */
    public boolean equals(final Object o)
    {
        return entry.equals(o);
    }
}