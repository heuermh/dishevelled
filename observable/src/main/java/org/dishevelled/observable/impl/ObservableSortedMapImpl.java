/*

    dsh-observable  Observable decorators for collection and map interfaces.
    Copyright (c) 2003-2009 held jointly by the individual authors.

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

import java.util.Map;
import java.util.SortedMap;

import org.dishevelled.observable.AbstractObservableSortedMap;

import org.dishevelled.observable.event.SortedMapChangeEvent;
import org.dishevelled.observable.event.SortedMapChangeVetoException;
import org.dishevelled.observable.event.VetoableSortedMapChangeEvent;

/**
 * Observable sorted map decorator that fires empty
 * vetoable sorted map change events in <code>preXxx</code> methods and
 * empty sorted map change events in <code>postXxx</code> methods.
 * Observable sorted map listeners may query the source of the events to determine
 * what may or may not have changed due to the event.
 *
 * @param <K> sorted map key type
 * @param <V> sorted map value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class ObservableSortedMapImpl<K, V>
    extends AbstractObservableSortedMap<K, V>
{
    /** Cached sorted map change event. */
    private final SortedMapChangeEvent<K, V> changeEvent;

    /** Cached vetoable sorted map change event. */
    private final VetoableSortedMapChangeEvent<K, V> vetoableChangeEvent;


    /**
     * Create a new observable decorator for the specified
     * sorted map.
     *
     * @param sortedMap sorted map to decorate, must not be null
     */
    public ObservableSortedMapImpl(final SortedMap<K, V> sortedMap)
    {
        super(sortedMap);
        changeEvent = new SortedMapChangeEvent<K, V>(this);
        vetoableChangeEvent = new VetoableSortedMapChangeEvent<K, V>(this);
    }


    /** {@inheritDoc} */
    protected boolean preClear()
    {
        try
        {
            fireSortedMapWillChange(vetoableChangeEvent);
            return true;
        }
        catch (SortedMapChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postClear()
    {
        fireSortedMapChanged(changeEvent);
    }

    /** {@inheritDoc} */
    protected boolean prePut(final K key, final V value)
    {
        try
        {
            fireSortedMapWillChange(vetoableChangeEvent);
            return true;
        }
        catch (SortedMapChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postPut(final K key, final V value)
    {
        fireSortedMapChanged(changeEvent);
    }

    /** {@inheritDoc} */
    protected boolean prePutAll(final Map<? extends K, ? extends V> map)
    {
        try
        {
            fireSortedMapWillChange(vetoableChangeEvent);
            return true;
        }
        catch (SortedMapChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postPutAll(final Map<? extends K, ? extends V> map)
    {
        fireSortedMapChanged(changeEvent);
    }

    /** {@inheritDoc} */
    protected boolean preRemove(final Object key)
    {
        try
        {
            fireSortedMapWillChange(vetoableChangeEvent);
            return true;
        }
        catch (SortedMapChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postRemove(final Object key)
    {
        fireSortedMapChanged(changeEvent);
    }
}
