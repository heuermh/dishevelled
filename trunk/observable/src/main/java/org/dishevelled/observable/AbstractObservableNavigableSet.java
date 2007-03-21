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

import java.util.NavigableSet;

import org.dishevelled.observable.event.NavigableSetChangeEvent;
import org.dishevelled.observable.event.NavigableSetChangeListener;
import org.dishevelled.observable.event.NavigableSetChangeVetoException;
import org.dishevelled.observable.event.ObservableNavigableSetChangeSupport;
import org.dishevelled.observable.event.VetoableNavigableSetChangeEvent;
import org.dishevelled.observable.event.VetoableNavigableSetChangeListener;

/**
 * Abstract implementation of an observable navigable set
 * that decorates an instance of <code>NavigableSet</code>.
 *
 * @param <E> navigable set element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractObservableNavigableSet<E>
    extends AbstractNavigableSetDecorator<E>
    implements ObservableNavigableSet<E>
{
    /** Observable navigable set change support. */
    private ObservableNavigableSetChangeSupport<E> support;


    /**
     * Create a new abstract observable navigable set that
     * decorates the specified navigable set.
     *
     * @param navigableSet navigable set to decorate
     */
    protected AbstractObservableNavigableSet(final NavigableSet<E> navigableSet)
    {
        super(navigableSet);
        support = new ObservableNavigableSetChangeSupport(this);
    }


    /**
     * Return the <code>ObservableNavigableSetChangeSupport</code>
     * class backing this abstract observable navigable set.
     *
     * @return the <code>ObservableNavigableSetChangeSupport</code>
     *    class backing this abstract observable navigable set
     */
    protected final ObservableNavigableSetChangeSupport<E> getObservableNavigableSetChangeSupport()
    {
        return support;
    }

    /** {@inheritDoc} */
    public final void addNavigableSetChangeListener(final NavigableSetChangeListener<E> l)
    {
        support.addNavigableSetChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void removeNavigableSetChangeListener(final NavigableSetChangeListener<E> l)
    {
        support.removeNavigableSetChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void addVetoableNavigableSetChangeListener(final VetoableNavigableSetChangeListener<E> l)
    {
        support.addVetoableNavigableSetChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void removeVetoableNavigableSetChangeListener(final VetoableNavigableSetChangeListener<E> l)
    {
        support.removeVetoableNavigableSetChangeListener(l);
    }

    /** {@inheritDoc} */
    public final NavigableSetChangeListener<E>[] getNavigableSetChangeListeners()
    {
        return support.getNavigableSetChangeListeners();
    }

    /** {@inheritDoc} */
    public final int getNavigableSetChangeListenerCount()
    {
        return support.getNavigableSetChangeListenerCount();
    }

    /** {@inheritDoc} */
    public final VetoableNavigableSetChangeListener<E>[] getVetoableNavigableSetChangeListeners()
    {
        return support.getVetoableNavigableSetChangeListeners();
    }

    /** {@inheritDoc} */
    public final int getVetoableNavigableSetChangeListenerCount()
    {
        return support.getVetoableNavigableSetChangeListenerCount();
    }

    /** {@inheritDoc} */
    public void fireNavigableSetWillChange()
        throws NavigableSetChangeVetoException
    {
        support.fireNavigableSetWillChange();
    }

    /** {@inheritDoc} */
    public void fireNavigableSetWillChange(final VetoableNavigableSetChangeEvent<E> e)
        throws NavigableSetChangeVetoException
    {
        support.fireNavigableSetWillChange(e);
    }

    /** {@inheritDoc} */
    public void fireNavigableSetChanged()
    {
        support.fireNavigableSetChanged();
    }

    /** {@inheritDoc} */
    public void fireNavigableSetChanged(final NavigableSetChangeEvent<E> e)
    {
        support.fireNavigableSetChanged(e);
    }


    // TODO:
    // add abstract pre/post methods
    // implement interface methods in terms of pre/post methods

}
