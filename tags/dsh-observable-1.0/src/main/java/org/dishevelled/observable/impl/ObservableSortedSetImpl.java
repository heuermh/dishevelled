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
package org.dishevelled.observable.impl;

import java.util.Collection;
import java.util.SortedSet;

import org.dishevelled.observable.AbstractObservableSortedSet;

import org.dishevelled.observable.event.SortedSetChangeEvent;
import org.dishevelled.observable.event.SortedSetChangeVetoException;
import org.dishevelled.observable.event.VetoableSortedSetChangeEvent;

/**
 * Observable sorted set decorator that fires empty
 * vetoable sorted set change events in <code>preXxx</code> methods and
 * empty sorted set change events in <code>postXxx</code> methods.
 * Observable sorted set listeners may query the source of the events to determine
 * what may or may not have changed due to the event.
 *
 * @param <E> sorted set element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class ObservableSortedSetImpl<E>
    extends AbstractObservableSortedSet<E>
{
    /** Cached sorted set change event. */
    private final SortedSetChangeEvent<E> changeEvent;

    /** Cached vetoable sorted set change event. */
    private final VetoableSortedSetChangeEvent<E> vetoableChangeEvent;


    /**
     * Create a new observable decorator for the specified
     * sorted set.
     *
     * @param sortedSet sorted set to decorate, must not be null
     */
    public ObservableSortedSetImpl(final SortedSet<E> sortedSet)
    {
        super(sortedSet);
        changeEvent = new SortedSetChangeEvent<E>(this);
        vetoableChangeEvent = new VetoableSortedSetChangeEvent<E>(this);
    }


    /** {@inheritDoc} */
    protected boolean preAdd(final E e)
    {
        try
        {
            fireSortedSetWillChange(vetoableChangeEvent);
            return true;
        }
        catch (SortedSetChangeVetoException ex)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postAdd(final E e)
    {
        fireSortedSetChanged(changeEvent);
    }

    /** {@inheritDoc} */
    protected boolean preAddAll(final Collection<? extends E> coll)
    {
        try
        {
            fireSortedSetWillChange(vetoableChangeEvent);
            return true;
        }
        catch (SortedSetChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postAddAll(final Collection<? extends E> coll)
    {
        fireSortedSetChanged(changeEvent);
    }

    /** {@inheritDoc} */
    protected boolean preClear()
    {
        try
        {
            fireSortedSetWillChange(vetoableChangeEvent);
            return true;
        }
        catch (SortedSetChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postClear()
    {
        fireSortedSetChanged(changeEvent);
    }

    /** {@inheritDoc} */
    protected boolean preRemove(final Object o)
    {
        try
        {
            fireSortedSetWillChange(vetoableChangeEvent);
            return true;
        }
        catch (SortedSetChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postRemove(final Object o)
    {
        fireSortedSetChanged(changeEvent);
    }

    /** {@inheritDoc} */
    protected boolean preRemoveAll(final Collection<?> coll)
    {
        try
        {
            fireSortedSetWillChange(vetoableChangeEvent);
            return true;
        }
        catch (SortedSetChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postRemoveAll(final Collection<?> coll)
    {
        fireSortedSetChanged(changeEvent);
    }

    /** {@inheritDoc} */
    protected boolean preRetainAll(final Collection<?> coll)
    {
        try
        {
            fireSortedSetWillChange(vetoableChangeEvent);
            return true;
        }
        catch (SortedSetChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postRetainAll(final Collection<?> coll)
    {
        fireSortedSetChanged(changeEvent);
    }

    /** {@inheritDoc} */
    protected boolean preIteratorRemove()
    {
        try
        {
            fireSortedSetWillChange(vetoableChangeEvent);
            return true;
        }
        catch (SortedSetChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postIteratorRemove()
    {
        fireSortedSetChanged(changeEvent);
    }
}
