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

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;

import org.cliffc.high_scale_lib.NonBlockingHashSet;

/**
 * Static utility methods for Sets.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class Sets
{

    /**
     * Private no-arg constructor.
     */
    private Sets()
    {
        // empty
    }


    /**
     * Create and return a set containing the unique elements in <code>elements</code>.
     *
     * @param <T> element type
     * @param elements variable number of elements to be added to the
     *    returned set
     * @return a set containing the unique elements in <code>elements</code>
     */
    public static <T> Set<T> asSet(final T... elements)
    {
        Set<T> set = newSet(elements.length);
        for (T t : elements)
        {
            set.add(t);
        }
        return set;
    }

    /**
     * Create and return a set containing the unique elements returned by the
     * specified iterator.
     *
     * @param <T> element type
     * @param iterator iterator, must not be null
     * @return a set containing the unique elements returned by the specified iterator
     */
    public static <T> Set<T> asSet(final Iterator<? extends T> iterator)
    {
        if (iterator == null)
        {
            throw new IllegalArgumentException("iterator must not be null");
        }
        Set<T> set = newSet();
        while (iterator.hasNext())
        {
            set.add(iterator.next());
        }
        return set;
    }

   /**
     * Create and return a set containing the unique elements returned by the
     * specified iterable.
     *
     * @param <T> element type
     * @param iterable iterable, must not be null
     * @return a set containing the unique elements returned by the specified iterable
     */
    public static <T> Set<T> asSet(final Iterable<? extends T> iterable)
    {
        if (iterable == null)
        {
            throw new IllegalArgumentException("iterable must not be null");
        }
        Set<T> set = newSet();
        for (T t : iterable)
        {
            set.add(t);
        }
        return set;
    }

    /**
     * Create and return a sorted set containing the unique elements in <code>elements</code>.
     *
     * @param <T> element type
     * @param elements variable number of elements to be added to the
     *    returned sorted set
     * @return a sorted set containing the unique elements in <code>elements</code>
     */
    public static <T> SortedSet<T> asSortedSet(final T... elements)
    {
        SortedSet<T> sortedSet = newSortedSet();
        for (T t : elements)
        {
            sortedSet.add(t);
        }
        return sortedSet;
    }

    /**
     * Create and return a sorted set containing the unique elements returned by the
     * specified iterator.
     *
     * @param <T> element type
     * @param iterator iterator, must not be null
     * @return a sorted set containing the unique elements returned by the specified iterator
     */
    public static <T> SortedSet<T> asSortedSet(final Iterator<? extends T> iterator)
    {
        if (iterator == null)
        {
            throw new IllegalArgumentException("iterator must not be null");
        }
        SortedSet<T> sortedSet = newSortedSet();
        while (iterator.hasNext())
        {
            sortedSet.add(iterator.next());
        }
        return sortedSet;
    }

   /**
     * Create and return a sorted set containing the unique elements returned by the
     * specified iterable.
     *
     * @param <T> element type
     * @param iterable iterable, must not be null
     * @return a sorted set containing the unique elements returned by the specified iterable
     */
    public static <T> SortedSet<T> asSortedSet(final Iterable<? extends T> iterable)
    {
        if (iterable == null)
        {
            throw new IllegalArgumentException("iterable must not be null");
        }
        SortedSet<T> sortedSet = newSortedSet();
        for (T t : iterable)
        {
            sortedSet.add(t);
        }
        return sortedSet;
    }

    /**
     * Create and return a sorted set containing the unique elements returned by the
     * specified iterator sorted according to the specified comparator.
     *
     * @param <T> element type
     * @param iterator iterator, must not be null
     * @param comparator comparator comparator to be used to sort the returned set
     * @return a sorted set containing the unique elements returned by the specified iterator
     *    sorted according to the specified comparator
     */
    public static <T> SortedSet<T> asSortedSet(final Iterator<? extends T> iterator,
                                               final Comparator<? super T> comparator)
    {
        if (iterator == null)
        {
            throw new IllegalArgumentException("iterator must not be null");
        }
        SortedSet<T> sortedSet = newSortedSet(comparator);
        while (iterator.hasNext())
        {
            sortedSet.add(iterator.next());
        }
        return sortedSet;
    }

   /**
     * Create and return a sorted set containing the unique elements returned by the
     * specified iterable sorted according to the specified comparator.
     *
     * @param <T> element type
     * @param iterable iterable, must not be null
     * @param comparator comparator to be used to sort the returned set
     * @return a sorted set containing the unique elements returned by the specified iterable
     *    sorted according to the specified comparator
     */
    public static <T> SortedSet<T> asSortedSet(final Iterable<? extends T> iterable,
                                               final Comparator<? super T> comparator)
    {
        if (iterable == null)
        {
            throw new IllegalArgumentException("iterable must not be null");
        }
        SortedSet<T> sortedSet = newSortedSet(comparator);
        for (T t : iterable)
        {
            sortedSet.add(t);
        }
        return sortedSet;
    }

    /**
     * Create and return a new instance of Set.
     *
     * @param <T> element type
     * @return a new instance of Set
     */
    public static <T> Set<T> newSet()
    {
        return new HashSet<T>();
    }

    /**
     * Create and return a new instance of Set containing the elements of the
     * specified collection.
     *
     * @param <T> element type
     * @param elements elements to be added to the returned set, must not be null
     * @return a new instance of Set containing the elements of the specified
     *    collection
     */
    public static <T> Set<T> newSet(final Collection<? extends T> elements)
    {
        return new HashSet<T>(elements);
    }

    /**
     * Create and return a new instance of Set with the specified initial capacity.
     *
     * @param <T> element type
     * @param initialCapacity initial capacity
     * @return a new instance of Set with the specified initial capacity
     */
    public static <T> Set<T> newSet(final int initialCapacity)
    {
        return new HashSet<T>(initialCapacity);
    }

    /**
     * Create and return a new instance of Set with the specified initial capacity
     * and load factor.
     *
     * @param <T> element type
     * @param initialCapacity initial capacity
     * @param loadFactor load factor
     * @return a new instance of Set with the specified initial capacity and
     *    load factor
     */
    public static <T> Set<T> newSet(final int initialCapacity, final float loadFactor)
    {
        return new HashSet<T>(initialCapacity, loadFactor);
    }

    /**
     * Create and return a new instance of SortedSet.
     *
     * @param <T> element type
     * @return a new instance of SortedSet
     */
    public static <T> SortedSet<T> newSortedSet()
    {
        return new TreeSet<T>();
    }

    /**
     * Create and return a new instance of SortedSet containing the elements
     * of the specified collection.
     *
     * @param <T> element type
     * @param elements elements to be added to the returned set, must not be null
     * @return a new instance of SortedSet containing the elements of the
     *    specified collection
     */
    public static <T> SortedSet<T> newSortedSet(final Collection<? extends T> elements)
    {
        return new TreeSet<T>(elements);
    }

    /**
     * Create and return a new instance of SortedSet sorted according to the specified
     * comparator.
     *
     * @param <T> element type
     * @param comparator comparator to be used to sort the returned set
     * @return a new instance of SortedSet
     */
    public static <T> SortedSet<T> newSortedSet(final Comparator<? super T> comparator)
    {
        return new TreeSet<T>(comparator);
    }

    /**
     * Create and return a new instance of SortedSet containing the elements
     * of the specified collection sorted according to the specified comparator.
     *
     * @param <T> element type
     * @param elements elements to be added to the returned set, must not be null
     * @param comparator comparator to be used to sort the returned set
     * @return a new instance of SortedSet containing the elements of the
     *    specified collection sorted according to the specified comparator
     */
    public static <T> SortedSet<T> newSortedSet(final Collection<? extends T> elements,
                                                final Comparator<? super T> comparator)
    {
        SortedSet<T> sortedSet = newSortedSet(comparator);
        sortedSet.addAll(elements);
        return sortedSet;
    }

    /**
     * Create and return a new instance of SortedSet containing the same elements
     * in the same order as the specified sorted set.
     *
     * @param <T> element type
     * @param sortedSet sorted set of elements to be added to the returned set, must not be null
     * @return a new instance of SortedSet containing the same elements in the same
     *    order as the specified sorted set
     */
    public static <T> SortedSet<T> newSortedSet(final SortedSet<T> sortedSet)
    {
        return new TreeSet<T>(sortedSet);
    }

    /**
     * Create and return a new instance of NavigableSet.
     *
     * @param <T> element type
     * @return a new instance of NavigableSet
     */
    public static <T> NavigableSet<T> newNavigableSet()
    {
        return new TreeSet<T>();
    }

    /**
     * Create and return a new instance of NavigableSet containing the elements
     * of the specified collection.
     *
     * @param <T> element type
     * @param elements elements to be added to the returned set, must not be null
     * @return a new instance of NavigableSet containing the elements of the
     *    specified collection
     */
    public static <T> NavigableSet<T> newNavigableSet(final Collection<? extends T> elements)
    {
        return new TreeSet<T>(elements);
    }

    /**
     * Create and return a new instance of NavigableSet sorted according to the specified
     * comparator.
     *
     * @param <T> element type
     * @param comparator comparator to be used to sort the returned set
     * @return a new instance of NavigableSet sorted according to the specified comparator
     */
    public static <T> NavigableSet<T> newNavigableSet(final Comparator<? super T> comparator)
    {
        return new TreeSet<T>(comparator);
    }

    /**
     * Create and return a new instance of NavigableSet containing the elements
     * of the specified collection sorted according to the specified comparator.
     *
     * @param <T> element type
     * @param elements elements to be added to the returned set, must not be null
     * @param comparator comparator to be used to sort the returned set
     * @return a new instance of NavigableSet containing the elements of the
     *    specified collection sorted according to the specified comparator
     */
    public static <T> NavigableSet<T> newNavigableSet(final Collection<? extends T> elements,
                                                      final Comparator<? super T> comparator)
    {
        NavigableSet<T> navigableSet = newNavigableSet(comparator);
        navigableSet.addAll(elements);
        return navigableSet;
    }

    /**
     * Create and return a new instance of NavigableSet containing the same elements
     * in the same order as the specified sorted set.
     *
     * @param <T> element type
     * @param sortedSet sorted set of elements to be added to the returned set, must not be null
     * @return a new instance of NavigableSet containing the same elements in the same
     *    order as the specified sorted set
     */
    public static <T> NavigableSet<T> newNavigableSet(final SortedSet<T> sortedSet)
    {
        return new TreeSet<T>(sortedSet);
    }

    /**
     * Create and return a new instance of ConcurrentSkipListSet.
     *
     * @param <T> element type
     * @return a new instance of ConcurrentSkipListSet
     */
    public static <T> ConcurrentSkipListSet<T> newConcurrentSkipListSet()
    {
        return new ConcurrentSkipListSet<T>();
    }

    /**
     * Create and return a new instance of ConcurrentSkipListSet containing the elements
     * of the specified collection.
     *
     * @param <T> element type
     * @param elements elements to be added to the returned set, must not be null
     * @return a new instance of ConcurrentSkipListSet containing the elements of the
     *    specified collection
     */
    public static <T> ConcurrentSkipListSet<T> newConcurrentSkipListSet(final Collection<? extends T> elements)
    {
        return new ConcurrentSkipListSet<T>(elements);
    }

    /**
     * Create and return a new instance of ConcurrentSkipListSet sorted according to the specified
     * comparator.
     *
     * @param <T> element type
     * @param comparator comparator to be used to sort the returned set
     * @return a new instance of ConcurrentSkipListSet
     */
    public static <T> ConcurrentSkipListSet<T> newConcurrentSkipListSet(final Comparator<? super T> comparator)
    {
        return new ConcurrentSkipListSet<T>(comparator);
    }

    /**
     * Create and return a new instance of ConcurrentSkipListSet containing the elements
     * of the specified collection sorted according to the specified comparator.
     *
     * @param <T> element type
     * @param elements elements to be added to the returned set, must not be null
     * @param comparator comparator to be used to sort the returned set
     * @return a new instance of ConcurrentSkipListSet containing the elements of the
     *    specified collection sorted according to the specified comparator
     */
    public static <T> ConcurrentSkipListSet<T> newConcurrentSkipListSet(final Collection<? extends T> elements,
                                                                        final Comparator<? super T> comparator)
    {
        ConcurrentSkipListSet<T> set = new ConcurrentSkipListSet(comparator);
        set.addAll(elements);
        return set;
    }

    /**
     * Create and return a new instance of ConcurrentSkipListSet containing the same elements
     * in the same order as the specified sorted set.
     *
     * @param <T> element type
     * @param sortedSet sorted set of elements to be added to the returned set, must not be null
     * @return a new instance of ConcurrentSkipListSet containing the same elements in the same
     *    order as the specified sorted set
     */
    public static <T> ConcurrentSkipListSet<T> newConcurrentSkipListSet(final SortedSet<T> sortedSet)
    {
        return new ConcurrentSkipListSet<T>(sortedSet);
    }

    /**
     * Create and return a new instance of CopyOnWriteArraySet.
     *
     * @param <T> element type
     * @return a new instance of CopyOnWriteArraySet
     */
    public static <T> CopyOnWriteArraySet<T> newCopyOnWriteArraySet()
    {
        return new CopyOnWriteArraySet<T>();
    }

    /**
     * Create and return a new instance of CopyOnWriteArraySet containing the elements
     * of the specified collection.
     *
     * @param <T> element type
     * @param elements elements to be added to the returned set, must not be null
     * @return a new instance of CopyOnWriteArraySet containing the elements of the
     *    specified collection
     */
    public static <T> CopyOnWriteArraySet<T> newCopyOnWriteArraySet(final Collection<? extends T> elements)
    {
        return new CopyOnWriteArraySet<T>(elements);
    }

    /**
     * Create and return a new non-blocking implementation of Set.
     *
     * @param <T> element type
     * @return a new non-blocking implementation of Set
     */
    public static <T> Set<T> newNonBlockingSet()
    {
        return new NonBlockingHashSet();
    }

    /**
     * Create and return a new non-blocking implementation of Set containing the elements
     * of the specified collection.
     *
     * @param <T> element type
     * @param elements elements to be added to the returned set, must not be null
     * @return a new non-blocking implementation of Set containing the elements of the
     *    specified collection
     */
    public static <T> Set<T> newNonBlockingSet(final Collection<? extends T> elements)
    {
        Set<T> set = newNonBlockingSet();
        set.addAll(elements);
        return set;
    }

    /**
     * Create and return an immutable set containing only the specified object.
     *
     * @param <T> element type
     * @param element element to be added to the returned set
     * @return an immutable set containing only the specified object
     */
    public static <T> Set<T> singleton(final T element)
    {
        return Collections.singleton(element);
    }

    /**
     * Create and return an immutable set containing only the specified object.
     * Alias to <code>static Set&lt;T&gt; singleton(T)</code> method.
     *
     * @see #singleton
     * @param <T> element type
     * @param element element to be added to the returned set
     * @return an immutable set containing only the specified object
     */
    public static <T> Set<T> singletonSet(final T element)
    {
        return singleton(element);
    }

    /**
     * Create and return an unmodifiable view of the specified set.  Query operations on the
     * returned set "read through" to the specified set, and attempts to modify the returned set,
     * whether direct or via its iterator, result in an <code>UnsupportedOperationException</code>.
     *
     * @param <T> element type
     * @param set set to view, must not be null
     * @return an unmodifiable view of the specified set
     */
    public static <T> Set<T> unmodifiableSet(final Set<? extends T> set)
    {
        return Collections.unmodifiableSet(set);
    }

    /**
     * Create and return an unmodifiable view of the specified sorted set.  Query operations
     * on the returned sorted set "read through" to the specified sorted set.  Attempts to modify
     * the returned sorted set, whether direct, via its iterator, or via its <code>subSet</code>,
     * <code>headSet</code>, or <code>tailSet</code> views, result in an
     * <code>UnsupportedOperationException</code>.
     *
     * @param <T> element type
     * @param sortedSet sorted set to view, must not be null
     * @return an unmodifiable view of the specified sorted set
     */
    public static <T> SortedSet<T> unmodifiableSortedSet(final SortedSet<T> sortedSet)
    {
        return Collections.unmodifiableSortedSet(sortedSet);
    }

}