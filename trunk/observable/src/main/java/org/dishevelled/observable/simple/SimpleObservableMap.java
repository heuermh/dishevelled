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

import java.util.Map;

import org.dishevelled.observable.AbstractObservableMap;

import org.dishevelled.observable.event.MapChangeEvent;
import org.dishevelled.observable.event.MapChangeVetoException;
import org.dishevelled.observable.event.VetoableMapChangeEvent;

/**
 * Observable map decorator that simply fires empty
 * vetoable map change events in <code>preXxx</code> methods and
 * empty map change events in <code>postXxx</code> methods.
 * Observable map listeners may query the source of the events to determine
 * what may or may not have changed due to the event.
 *
 * @param <K> map key type
 * @param <V> map value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class SimpleObservableMap<K,V>
    extends AbstractObservableMap<K,V>
{
    /** Cached map change event. */
    private final MapChangeEvent<K,V> changeEvent;

    /** Cached vetoable map change event. */
    private final VetoableMapChangeEvent<K,V> vetoableChangeEvent;


    /**
     * Create a new observable decorator for the specified
     * map.
     *
     * @param map map to decorate
     */
    public SimpleObservableMap(final Map<K,V> map)
    {
        super(map);
        changeEvent = new MapChangeEvent<K,V>(this);
        vetoableChangeEvent = new VetoableMapChangeEvent<K,V>(this);
    }


    /** {@inheritDoc} */
    protected boolean preClear()
    {
        try
        {
            fireMapWillChange(vetoableChangeEvent);
            return true;
        }
        catch (MapChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postClear()
    {
        fireMapChanged(changeEvent);
    }

    /** {@inheritDoc} */
    protected boolean prePut(final K key, final V value)
    {
        try
        {
            fireMapWillChange(vetoableChangeEvent);
            return true;
        }
        catch (MapChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postPut(final K key, final V value)
    {
        fireMapChanged(changeEvent);
    }

    /** {@inheritDoc} */
    protected boolean prePutAll(final Map<? extends K,? extends V> map)
    {
        try
        {
            fireMapWillChange(vetoableChangeEvent);
            return true;
        }
        catch (MapChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postPutAll(final Map<? extends K,? extends V> map)
    {
        fireMapChanged(changeEvent);
    }

    /** {@inheritDoc} */
    protected boolean preRemove(final Object key)
    {
        try
        {
            fireMapWillChange(vetoableChangeEvent);
            return true;
        }
        catch (MapChangeVetoException e)
        {
            return false;
        }
    }

    /** {@inheritDoc} */
    protected void postRemove(final Object key)
    {
        fireMapChanged(changeEvent);
    }
}
