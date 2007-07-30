/*

    dsh-observable  Observable decorators for collection and map interfaces.
    Copyright (c) 2003-2007 held jointly by the individual authors.

    This library is free software; you can redistribute it and/or modify it
    under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation; either version 2.1 of the License, or (at
    your option) any later version.

    This library is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; with out even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
    License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this library;  if not, write to the Free Software Foundation,
    Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA.

    > http://www.gnu.org/copyleft/lesser.html
    > http://www.opensource.org/licenses/lgpl-license.php

*/
package org.dishevelled.observable.simple;

import java.util.Collection;
import java.util.List;

import org.dishevelled.observable.AbstractObservableList;

import org.dishevelled.observable.event.ListChangeEvent;
import org.dishevelled.observable.event.ListChangeVetoException;
import org.dishevelled.observable.event.VetoableListChangeEvent;

/**
 * Observable list decorator that simply fires empty
 * vetoable list change events in <code>preXxx</code> methods and
 * empty list change events in <code>postXxx</code> methods.
 * Observable list listeners may query the source of the events to determine
 * what may or may not have changed due to the event.
 *
 * @param <E> list element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class SimpleObservableList<E>
    extends AbstractObservableList<E>
{
    /** Cached list change event. */
    private final ListChangeEvent<E> changeEvent;

    /** Cached vetoable list change event. */
    private final VetoableListChangeEvent<E> vetoableChangeEvent;


    /**
     * Create a new observable decorator for the specified
     * list.
     *
     * @param list list to decorate, must not be null
     */
    public SimpleObservableList(final List<E> list)
    {
        super(list);
        changeEvent = new ListChangeEvent<E>(this);
        vetoableChangeEvent = new VetoableListChangeEvent<E>(this);
    }


    /** {@inheritDoc} */
    protected boolean preAdd(final E e)
    {
        try
        {
            fireListWillChange(vetoableChangeEvent);
            return true;
        }
        catch (ListChangeVetoException ex)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postAdd(final E e)
    {
        fireListChanged(changeEvent);
    }

    /** {@inheritDoc} */
    protected boolean preAddAtIndex(final int index, final E e)
    {
        try
        {
            fireListWillChange(vetoableChangeEvent);
            return true;
        }
        catch (ListChangeVetoException ex)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postAddAtIndex(final int index, final E e)
    {
        fireListChanged(changeEvent);
    }

    /** {@inheritDoc} */
    protected boolean preAddAll(final Collection<? extends E> coll)
    {
        try
        {
            fireListWillChange(vetoableChangeEvent);
            return true;
        }
        catch (ListChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postAddAll(final Collection<? extends E> coll)
    {
        fireListChanged(changeEvent);
    }

    /** {@inheritDoc} */
    protected boolean preAddAllAtIndex(final int index, final Collection<? extends E> coll)
    {
        try
        {
            fireListWillChange(vetoableChangeEvent);
            return true;
        }
        catch (ListChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postAddAllAtIndex(final int index, final Collection<? extends E> coll)
    {
        fireListChanged(changeEvent);
    }

    /** {@inheritDoc} */
    protected boolean preSet(final int index, final E e)
    {
        try
        {
            fireListWillChange(vetoableChangeEvent);
            return true;
        }
        catch (ListChangeVetoException ex)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postSet(final int index, final E e)
    {
        fireListChanged(changeEvent);
    }

    /** {@inheritDoc} */
    protected boolean preClear()
    {
        try
        {
            fireListWillChange(vetoableChangeEvent);
            return true;
        }
        catch (ListChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postClear()
    {
        fireListChanged(changeEvent);
    }

    /** {@inheritDoc} */
    protected boolean preRemoveIndex(final int index)
    {
        try
        {
            fireListWillChange(vetoableChangeEvent);
            return true;
        }
        catch (ListChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postRemoveIndex(final int index)
    {
        fireListChanged(changeEvent);
    }

    /** {@inheritDoc} */
    protected boolean preRemove(final Object o)
    {
        try
        {
            fireListWillChange(vetoableChangeEvent);
            return true;
        }
        catch (ListChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postRemove(final Object o)
    {
        fireListChanged(changeEvent);
    }

    /** {@inheritDoc} */
    protected boolean preRemoveAll(final Collection<?> coll)
    {
        try
        {
            fireListWillChange(vetoableChangeEvent);
            return true;
        }
        catch (ListChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postRemoveAll(final Collection<?> coll)
    {
        fireListChanged(changeEvent);
    }

    /** {@inheritDoc} */
    protected boolean preRetainAll(final Collection<?> coll)
    {
        try
        {
            fireListWillChange(vetoableChangeEvent);
            return true;
        }
        catch (ListChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postRetainAll(final Collection coll)
    {
        fireListChanged(changeEvent);
    }

    /** {@inheritDoc} */
    protected boolean preIteratorRemove()
    {
        try
        {
            fireListWillChange(vetoableChangeEvent);
            return true;
        }
        catch (ListChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postIteratorRemove()
    {
        fireListChanged(changeEvent);
    }

    /** {@inheritDoc} */
    protected boolean preListIteratorRemove()
    {
        try
        {
            fireListWillChange(vetoableChangeEvent);
            return true;
        }
        catch (ListChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postListIteratorRemove()
    {
        fireListChanged(changeEvent);
    }

    /** {@inheritDoc} */
    protected boolean preListIteratorSet()
    {
        try
        {
            fireListWillChange(vetoableChangeEvent);
            return true;
        }
        catch (ListChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postListIteratorSet()
    {
        fireListChanged(changeEvent);
    }
}
