/*

    dsh-observable  Observable decorators for collection and map interfaces.
    Copyright (c) 2003-2008 held jointly by the individual authors.

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
import java.util.Set;

import org.dishevelled.observable.AbstractObservableSet;

import org.dishevelled.observable.event.SetChangeEvent;
import org.dishevelled.observable.event.SetChangeVetoException;
import org.dishevelled.observable.event.VetoableSetChangeEvent;

/**
 * Observable set decorator that simply fires empty
 * vetoable set change events in <code>preXxx</code> methods and
 * empty set change events in <code>postXxx</code> methods.
 * Observable set listeners may query the source of the events to determine
 * what may or may not have changed due to the event.
 *
 * @param <E> set element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class ObservableSetImpl<E>
    extends AbstractObservableSet<E>
{
    /** Cached set change event. */
    private final SetChangeEvent<E> changeEvent;

    /** Cached vetoable set change event. */
    private final VetoableSetChangeEvent<E> vetoableChangeEvent;


    /**
     * Create a new observable decorator for the specified
     * set.
     *
     * @param set set to decorate, must not be null
     */
    public ObservableSetImpl(final Set<E> set)
    {
        super(set);
        changeEvent = new SetChangeEvent<E>(this);
        vetoableChangeEvent = new VetoableSetChangeEvent<E>(this);
    }


    /** {@inheritDoc} */
    protected boolean preAdd(final E e)
    {
        try
        {
            fireSetWillChange(vetoableChangeEvent);
            return true;
        }
        catch (SetChangeVetoException ex)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postAdd(final E e)
    {
        fireSetChanged(changeEvent);
    }

    /** {@inheritDoc} */
    protected boolean preAddAll(final Collection<? extends E> coll)
    {
        try
        {
            fireSetWillChange(vetoableChangeEvent);
            return true;
        }
        catch (SetChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postAddAll(final Collection<? extends E> coll)
    {
        fireSetChanged(changeEvent);
    }

    /** {@inheritDoc} */
    protected boolean preClear()
    {
        try
        {
            fireSetWillChange(vetoableChangeEvent);
            return true;
        }
        catch (SetChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postClear()
    {
        fireSetChanged(changeEvent);
    }

    /** {@inheritDoc} */
    protected boolean preRemove(final Object o)
    {
        try
        {
            fireSetWillChange(vetoableChangeEvent);
            return true;
        }
        catch (SetChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postRemove(final Object o)
    {
        fireSetChanged(changeEvent);
    }

    /** {@inheritDoc} */
    protected boolean preRemoveAll(final Collection<?> coll)
    {
        try
        {
            fireSetWillChange(vetoableChangeEvent);
            return true;
        }
        catch (SetChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postRemoveAll(final Collection<?> coll)
    {
        fireSetChanged(changeEvent);
    }

    /** {@inheritDoc} */
    protected boolean preRetainAll(final Collection<?> coll)
    {
        try
        {
            fireSetWillChange(vetoableChangeEvent);
            return true;
        }
        catch (SetChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postRetainAll(final Collection<?> coll)
    {
        fireSetChanged(changeEvent);
    }

    /** {@inheritDoc} */
    protected boolean preIteratorRemove()
    {
        try
        {
            fireSetWillChange(vetoableChangeEvent);
            return true;
        }
        catch (SetChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postIteratorRemove()
    {
        fireSetChanged(changeEvent);
    }
}
