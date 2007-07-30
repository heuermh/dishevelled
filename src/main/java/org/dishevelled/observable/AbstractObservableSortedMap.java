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

import java.util.SortedMap;

import org.dishevelled.observable.event.ObservableSortedMapChangeSupport;
import org.dishevelled.observable.event.SortedMapChangeEvent;
import org.dishevelled.observable.event.SortedMapChangeListener;
import org.dishevelled.observable.event.SortedMapChangeVetoException;
import org.dishevelled.observable.event.VetoableSortedMapChangeEvent;
import org.dishevelled.observable.event.VetoableSortedMapChangeListener;

/**
 * Abstract implementation of an observable sorted map
 * that decorates an instance of <code>SortedMap</code>.
 *
 * @param <K> sorted map key type
 * @param <V> sorted map value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractObservableSortedMap<K,V>
    extends AbstractSortedMapDecorator<K,V>
    implements ObservableSortedMap<K,V>
{
    /** Observable sorted map change support. */
    private ObservableSortedMapChangeSupport<K,V> support;


    /**
     * Create a new abstract observable sorted map that
     * decorates the specified sorted map.
     *
     * @param sortedMap sorted map to decorate
     */
    protected AbstractObservableSortedMap(final SortedMap<K,V> sortedMap)
    {
        super(sortedMap);
        support = new ObservableSortedMapChangeSupport(this);
    }


    /**
     * Return the <code>ObservableSortedMapChangeSupport</code>
     * class backing this abstract observable sorted map.
     *
     * @return the <code>ObservableSortedMapChangeSupport</code>
     *    class backing this abstract observable sorted map
     */
    protected final ObservableSortedMapChangeSupport<K,V> getObservableSortedMapChangeSupport()
    {
        return support;
    }

    /** {@inheritDoc} */
    public final void addSortedMapChangeListener(final SortedMapChangeListener<K,V> l)
    {
        support.addSortedMapChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void removeSortedMapChangeListener(final SortedMapChangeListener<K,V> l)
    {
        support.removeSortedMapChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void addVetoableSortedMapChangeListener(final VetoableSortedMapChangeListener<K,V> l)
    {
        support.addVetoableSortedMapChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void removeVetoableSortedMapChangeListener(final VetoableSortedMapChangeListener<K,V> l)
    {
        support.removeVetoableSortedMapChangeListener(l);
    }

    /** {@inheritDoc} */
    public final SortedMapChangeListener<K,V>[] getSortedMapChangeListeners()
    {
        return support.getSortedMapChangeListeners();
    }

    /** {@inheritDoc} */
    public final int getSortedMapChangeListenerCount()
    {
        return support.getSortedMapChangeListenerCount();
    }

    /** {@inheritDoc} */
    public final VetoableSortedMapChangeListener<K,V>[] getVetoableSortedMapChangeListeners()
    {
        return support.getVetoableSortedMapChangeListeners();
    }

    /** {@inheritDoc} */
    public final int getVetoableSortedMapChangeListenerCount()
    {
        return support.getVetoableSortedMapChangeListenerCount();
    }

    /** {@inheritDoc} */
    public void fireSortedMapWillChange()
        throws SortedMapChangeVetoException
    {
        support.fireSortedMapWillChange();
    }

    /** {@inheritDoc} */
    public void fireSortedMapWillChange(final VetoableSortedMapChangeEvent<K,V> e)
        throws SortedMapChangeVetoException
    {
        support.fireSortedMapWillChange(e);
    }

    /** {@inheritDoc} */
    public void fireSortedMapChanged()
    {
        support.fireSortedMapChanged();
    }

    /** {@inheritDoc} */
    public void fireSortedMapChanged(final SortedMapChangeEvent<K,V> e)
    {
        support.fireSortedMapChanged(e);
    }


    // TODO:
    // add abstract pre/post methods
    // implement interface methods in terms of pre/post methods

}
