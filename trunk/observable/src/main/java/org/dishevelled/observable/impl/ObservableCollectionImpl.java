/*

    dsh-observable  Observable decorators for collection and map interfaces.
    Copyright (c) 2003-2013 held jointly by the individual authors.

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

import org.dishevelled.observable.AbstractObservableCollection;

import org.dishevelled.observable.event.CollectionChangeEvent;
import org.dishevelled.observable.event.CollectionChangeVetoException;
import org.dishevelled.observable.event.VetoableCollectionChangeEvent;

/**
 * Observable collection decorator that fires empty
 * vetoable collection change events in <code>preXxx</code> methods and
 * empty collection change events in <code>postXxx</code> methods.
 * Observable collection listeners may query the source of events to determine
 * what may or may not have changed due to the event.
 *
 * @param <E> collection element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class ObservableCollectionImpl<E>
    extends AbstractObservableCollection<E>
{
    /** Cached collection change event. */
    private final CollectionChangeEvent<E> changeEvent;

    /** Cached vetoable collection change event. */
    private final VetoableCollectionChangeEvent<E> vetoableChangeEvent;


    /**
     * Create a new observable decorator for the specified
     * collection.
     *
     * @param collection collection to decorate, must not be null
     */
    public ObservableCollectionImpl(final Collection<E> collection)
    {
        super(collection);
        changeEvent = new CollectionChangeEvent<E>(this);
        vetoableChangeEvent = new VetoableCollectionChangeEvent<E>(this);
    }


    /** {@inheritDoc} */
    protected boolean preAdd(final E e)
    {
        try
        {
            fireCollectionWillChange(vetoableChangeEvent);
            return true;
        }
        catch (CollectionChangeVetoException ex)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postAdd(final E e)
    {
        fireCollectionChanged(changeEvent);
    }

    /** {@inheritDoc} */
    protected boolean preAddAll(final Collection<? extends E> coll)
    {
        try
        {
            fireCollectionWillChange(vetoableChangeEvent);
            return true;
        }
        catch (CollectionChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postAddAll(final Collection<? extends E> coll)
    {
        fireCollectionChanged(changeEvent);
    }

    /** {@inheritDoc} */
    protected boolean preClear()
    {
        try
        {
            fireCollectionWillChange(vetoableChangeEvent);
            return true;
        }
        catch (CollectionChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postClear()
    {
        fireCollectionChanged(changeEvent);
    }

    /** {@inheritDoc} */
    protected boolean preRemove(final Object o)
    {
        try
        {
            fireCollectionWillChange(vetoableChangeEvent);
            return true;
        }
        catch (CollectionChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postRemove(final Object o)
    {
        fireCollectionChanged(changeEvent);
    }

    /** {@inheritDoc} */
    protected boolean preRemoveAll(final Collection<?> coll)
    {
        try
        {
            fireCollectionWillChange(vetoableChangeEvent);
            return true;
        }
        catch (CollectionChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postRemoveAll(final Collection<?> coll)
    {
        fireCollectionChanged(changeEvent);
    }

    /** {@inheritDoc} */
    protected boolean preRetainAll(final Collection<?> coll)
    {
        try
        {
            fireCollectionWillChange(vetoableChangeEvent);
            return true;
        }
        catch (CollectionChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postRetainAll(final Collection<?> coll)
    {
        fireCollectionChanged(changeEvent);
    }

    /** {@inheritDoc} */
    protected boolean preIteratorRemove()
    {
        try
        {
            fireCollectionWillChange(vetoableChangeEvent);
            return true;
        }
        catch (CollectionChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postIteratorRemove()
    {
        fireCollectionChanged(changeEvent);
    }
}
