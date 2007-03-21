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
package org.dishevelled.observable;

import java.util.Map;

import org.dishevelled.observable.event.MapChangeEvent;
import org.dishevelled.observable.event.MapChangeListener;
import org.dishevelled.observable.event.MapChangeVetoException;
import org.dishevelled.observable.event.ObservableMapChangeSupport;
import org.dishevelled.observable.event.VetoableMapChangeEvent;
import org.dishevelled.observable.event.VetoableMapChangeListener;

/**
 * Abstract implementation of an observable map
 * that decorates an instance of <code>Map</code>.
 *
 * @param <K> map key type
 * @param <V> map value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractObservableMap<K,V>
    extends AbstractMapDecorator<K,V>
    implements ObservableMap<K,V>
{
    /** Observable map change support. */
    private ObservableMapChangeSupport<K,V> support;


    /**
     * Create a new abstract observable map that
     * decorates the specified map.
     *
     * @param map map to decorate
     */
    protected AbstractObservableMap(final Map<K,V> map)
    {
        super(map);
        support = new ObservableMapChangeSupport(this);
    }


    /**
     * Return the <code>ObservableMapChangeSupport</code>
     * class backing this abstract observable map.
     *
     * @return the <code>ObservableMapChangeSupport</code>
     *    class backing this abstract observable map
     */
    protected final ObservableMapChangeSupport<K,V> getObservableMapChangeSupport()
    {
        return support;
    }

    /** {@inheritDoc} */
    public final void addMapChangeListener(final MapChangeListener<K,V> l)
    {
        support.addMapChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void removeMapChangeListener(final MapChangeListener<K,V> l)
    {
        support.removeMapChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void addVetoableMapChangeListener(final VetoableMapChangeListener<K,V> l)
    {
        support.addVetoableMapChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void removeVetoableMapChangeListener(final VetoableMapChangeListener<K,V> l)
    {
        support.removeVetoableMapChangeListener(l);
    }

    /** {@inheritDoc} */
    public final MapChangeListener<K,V>[] getMapChangeListeners()
    {
        return support.getMapChangeListeners();
    }

    /** {@inheritDoc} */
    public final int getMapChangeListenerCount()
    {
        return support.getMapChangeListenerCount();
    }

    /** {@inheritDoc} */
    public final VetoableMapChangeListener<K,V>[] getVetoableMapChangeListeners()
    {
        return support.getVetoableMapChangeListeners();
    }

    /** {@inheritDoc} */
    public final int getVetoableMapChangeListenerCount()
    {
        return support.getVetoableMapChangeListenerCount();
    }

    /** {@inheritDoc} */
    public void fireMapWillChange()
        throws MapChangeVetoException
    {
        support.fireMapWillChange();
    }

    /** {@inheritDoc} */
    public void fireMapWillChange(final VetoableMapChangeEvent<K,V> e)
        throws MapChangeVetoException
    {
        support.fireMapWillChange(e);
    }

    /** {@inheritDoc} */
    public void fireMapChanged()
    {
        support.fireMapChanged();
    }

    /** {@inheritDoc} */
    public void fireMapChanged(final MapChangeEvent<K,V> e)
    {
        support.fireMapChanged(e);
    }


    // TODO:
    // add abstract pre/post methods
    // implement interface methods in terms of pre/post methods

}
