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

import java.util.SortedSet;

import org.dishevelled.observable.event.ObservableSortedSetChangeSupport;
import org.dishevelled.observable.event.SortedSetChangeEvent;
import org.dishevelled.observable.event.SortedSetChangeListener;
import org.dishevelled.observable.event.SortedSetChangeVetoException;
import org.dishevelled.observable.event.VetoableSortedSetChangeEvent;
import org.dishevelled.observable.event.VetoableSortedSetChangeListener;

/**
 * Abstract implementation of an observable sorted set
 * that decorates an instance of <code>SortedSet</code>.
 *
 * @param <E> sorted set element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractObservableSortedSet<E>
    extends AbstractSortedSetDecorator<E>
    implements ObservableSortedSet<E>
{
    /** Observable sorted set change support. */
    private ObservableSortedSetChangeSupport<E> support;


    /**
     * Create a new abstract observable sorted set that
     * decorates the specified sorted set.
     *
     * @param sortedSet sorted set to decorate
     */
    protected AbstractObservableSortedSet(final SortedSet<E> sortedSet)
    {
        super(sortedSet);
        support = new ObservableSortedSetChangeSupport(this);
    }


    /**
     * Return the <code>ObservableSortedSetChangeSupport</code>
     * class backing this abstract observable sorted set.
     *
     * @return the <code>ObservableSortedSetChangeSupport</code>
     *    class backing this abstract observable sorted set
     */
    protected final ObservableSortedSetChangeSupport<E> getObservableSortedSetChangeSupport()
    {
        return support;
    }

    /** {@inheritDoc} */
    public final void addSortedSetChangeListener(final SortedSetChangeListener<E> l)
    {
        support.addSortedSetChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void removeSortedSetChangeListener(final SortedSetChangeListener<E> l)
    {
        support.removeSortedSetChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void addVetoableSortedSetChangeListener(final VetoableSortedSetChangeListener<E> l)
    {
        support.addVetoableSortedSetChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void removeVetoableSortedSetChangeListener(final VetoableSortedSetChangeListener<E> l)
    {
        support.removeVetoableSortedSetChangeListener(l);
    }

    /** {@inheritDoc} */
    public final SortedSetChangeListener<E>[] getSortedSetChangeListeners()
    {
        return support.getSortedSetChangeListeners();
    }

    /** {@inheritDoc} */
    public final int getSortedSetChangeListenerCount()
    {
        return support.getSortedSetChangeListenerCount();
    }

    /** {@inheritDoc} */
    public final VetoableSortedSetChangeListener<E>[] getVetoableSortedSetChangeListeners()
    {
        return support.getVetoableSortedSetChangeListeners();
    }

    /** {@inheritDoc} */
    public final int getVetoableSortedSetChangeListenerCount()
    {
        return support.getVetoableSortedSetChangeListenerCount();
    }

    /** {@inheritDoc} */
    public void fireSortedSetWillChange()
        throws SortedSetChangeVetoException
    {
        support.fireSortedSetWillChange();
    }

    /** {@inheritDoc} */
    public void fireSortedSetWillChange(final VetoableSortedSetChangeEvent<E> e)
        throws SortedSetChangeVetoException
    {
        support.fireSortedSetWillChange(e);
    }

    /** {@inheritDoc} */
    public void fireSortedSetChanged()
    {
        support.fireSortedSetChanged();
    }

    /** {@inheritDoc} */
    public void fireSortedSetChanged(final SortedSetChangeEvent<E> e)
    {
        support.fireSortedSetChanged(e);
    }


    // TODO:
    // add abstract pre/post methods
    // implement interface methods in terms of pre/post methods

}
