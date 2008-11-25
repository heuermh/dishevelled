/*

    dsh-collect  Collection and map utility classes.
    Copyright (c) 2008 held jointly by the individual authors.

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
package org.dishevelled.collect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Static utility methods for Lists.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class Lists
{

    /**
     * Private no-arg constructor.
     */
    private Lists()
    {
        // empty
    }


    /**
     * Create and return a list containing the specified elements.
     *
     * @param <T> element type
     * @param elements variable number of elements to be added to the
     *    returned list
     * @return a list containing the specified elements
     */
    public static <T> List<T> asList(final T... elements)
    {
        List<T> list = createList(elements.length);
        for (T t : elements)
        {
            list.add(t);
        }
        return list;
    }

    /**
     * Create and return a list containing the elements returned by the
     * specified iterator.
     *
     * @param <T> element type
     * @param iterator iterator, must not be null
     * @return a list containing the elements returned by the specified iterator
     */
    public static <T> List<T> asList(final Iterator<? extends T> iterator)
    {
        if (iterator == null)
        {
            throw new IllegalArgumentException("iterator must not be null");
        }
        List<T> list = createList();
        while (iterator.hasNext())
        {
            list.add(iterator.next());
        }
        return list;
    }

   /**
     * Create and return a list containing the elements returned by the
     * specified iterable.
     *
     * @param <T> element type
     * @param iterable iterable, must not be null
     * @return a list containing the elements returned by the specified iterable
     */
    public static <T> List<T> asList(final Iterable<? extends T> iterable)
    {
        if (iterable == null)
        {
            throw new IllegalArgumentException("iterable must not be null");
        }
        List<T> list = createList();
        for (T t : iterable)
        {
            list.add(t);
        }
        return list;
    }

    /**
     * Create and return a new instance of List.
     *
     * @param <T> element type
     * @return a new instance of List
     */
    public static <T> List<T> createList()
    {
        return new ArrayList<T>();
    }

    /**
     * Create and return a new instance of List containing the elements of
     * the specified collection, in the order they are returned by the collection's
     * iterator.
     *
     * @param <T> element type
     * @param elements elements to be added to the returned list, must not be null
     * @return a new instance of List containing the elements of the specified
     *    collection
     */
    public static <T> List<T> createList(final Collection<? extends T> elements)
    {
        return new ArrayList<T>(elements);
    }

    /**
     * Create and return a new instance of List with the specified initial capacity.
     *
     * @param <T> element type
     * @param initialCapacity initial capacity
     * @return a new instance of List with the specified initial capacity
     */
    public static <T> List<T> createList(final int initialCapacity)
    {
        return new ArrayList<T>(initialCapacity);
    }

    /**
     * Create and return a new instance of LinkedList.
     *
     * @param <T> element type
     * @return a new instance of LinkedList
     */
    public static <T> LinkedList<T> createLinkedList()
    {
        return new LinkedList<T>();
    }

    /**
     * Create and return a new instance of LinkedList containing the elements of
     * the specified collection, in the order they are returned by the collection's
     * iterator.
     *
     * @param <T> element type
     * @param elements elements to be added to the returned list, must not be null
     * @return a new instance of LinkedList containing the elements of the specified
     *    collection
     */
    public static <T> LinkedList<T> createLinkedList(final Collection<? extends T> elements)
    {
        return new LinkedList<T>(elements);
    }

    /**
     * Create and return a new instance of CopyOnWriteArrayList.
     *
     * @param <T> element type
     * @return a new instance of CopyOnWriteArrayList
     */
    public static <T> CopyOnWriteArrayList<T> createCopyOnWriteArrayList()
    {
        return new CopyOnWriteArrayList<T>();
    }

    /**
     * Create and return a new instance of CopyOnWriteArrayList containing the
     * elements of the specified collection, in the order they are returned by the
     * collection's iterator.
     *
     * @param <T> element type
     * @param elements elements to be added to the returned list, must not be null
     * @return a new instance of CopyOnWriteArrayLIst containing the elements
     *    of the specified collection
     */
    public static <T> CopyOnWriteArrayList<T> createCopyOnWriteArrayList(final Collection<? extends T> elements)
    {
        return new CopyOnWriteArrayList<T>(elements);
    }

    /**
     * Create and return a new instance of CopyOnWriteArrayList holding a copy
     * of the specified array of elements.
     *
     * @param <T> element type
     * @param elements array of elements to copy, must not be null
     * @return a new instance of CopyOnWriteArrayList holding a copy of the
     *    specified array of elements
     */
    public static <T> CopyOnWriteArrayList<T> createCopyOnWriteArrayList(final T[] elements)
    {
        return new CopyOnWriteArrayList<T>(elements);
    }

    /**
     * Create and return an immutable list containing only the specified object.
     *
     * @param <T> element type
     * @param element element to be added to the returned list
     * @return an immutable list containing only the specified object
     */
    public static <T> List<T> singletonList(final T element)
    {
        return Collections.singletonList(element);
    }

    /**
     * Create and return an unmodifiable view of the specified list.  Query operations on
     * the returned list "read through" to the specified list, and attempts to modify the
     * returned list, whether direct or via its iterator, result in an
     * <code>UnsupportedOperationException</code>.
     *
     * @param <T> element type
     * @param list list to view, must not be null
     * @return an unmodifiable view of the specified list
     */
    public static <T> List<T> unmodifiableList(final List<? extends T> list)
    {
        return Collections.unmodifiableList(list);
    }
}