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

import java.util.Deque;

import org.dishevelled.observable.event.DequeChangeEvent;
import org.dishevelled.observable.event.DequeChangeListener;
import org.dishevelled.observable.event.DequeChangeVetoException;
import org.dishevelled.observable.event.ObservableDequeChangeSupport;
import org.dishevelled.observable.event.VetoableDequeChangeEvent;
import org.dishevelled.observable.event.VetoableDequeChangeListener;

/**
 * Abstract implementation of an observable deque
 * that decorates an instance of <code>Deque</code>.
 *
 * @param <E> deque element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractObservableDeque<E>
    extends AbstractDequeDecorator<E>
    implements ObservableDeque<E>
{
    /** Observable deque change support. */
    private ObservableDequeChangeSupport<E> support;


    /**
     * Create a new abstract observable deque that
     * decorates the specified deque.
     *
     * @param deque deque to decorate
     */
    protected AbstractObservableDeque(final Deque<E> deque)
    {
        super(deque);
        support = new ObservableDequeChangeSupport(this);
    }


    /**
     * Return the <code>ObservableDequeChangeSupport</code>
     * class backing this abstract observable deque.
     *
     * @return the <code>ObservableDequeChangeSupport</code>
     *    class backing this abstract observable deque
     */
    protected final ObservableDequeChangeSupport<E> getObservableDequeChangeSupport()
    {
        return support;
    }

    /** {@inheritDoc} */
    public final void addDequeChangeListener(final DequeChangeListener<E> l)
    {
        support.addDequeChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void removeDequeChangeListener(final DequeChangeListener<E> l)
    {
        support.removeDequeChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void addVetoableDequeChangeListener(final VetoableDequeChangeListener<E> l)
    {
        support.addVetoableDequeChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void removeVetoableDequeChangeListener(final VetoableDequeChangeListener<E> l)
    {
        support.removeVetoableDequeChangeListener(l);
    }

    /** {@inheritDoc} */
    public final DequeChangeListener<E>[] getDequeChangeListeners()
    {
        return support.getDequeChangeListeners();
    }

    /** {@inheritDoc} */
    public final int getDequeChangeListenerCount()
    {
        return support.getDequeChangeListenerCount();
    }

    /** {@inheritDoc} */
    public final VetoableDequeChangeListener<E>[] getVetoableDequeChangeListeners()
    {
        return support.getVetoableDequeChangeListeners();
    }

    /** {@inheritDoc} */
    public final int getVetoableDequeChangeListenerCount()
    {
        return support.getVetoableDequeChangeListenerCount();
    }

    /** {@inheritDoc} */
    public void fireDequeWillChange()
        throws DequeChangeVetoException
    {
        support.fireDequeWillChange();
    }

    /** {@inheritDoc} */
    public void fireDequeWillChange(final VetoableDequeChangeEvent<E> e)
        throws DequeChangeVetoException
    {
        support.fireDequeWillChange(e);
    }

    /** {@inheritDoc} */
    public void fireDequeChanged()
    {
        support.fireDequeChanged();
    }

    /** {@inheritDoc} */
    public void fireDequeChanged(final DequeChangeEvent<E> e)
    {
        support.fireDequeChanged(e);
    }


    // TODO:
    // add abstract pre/post methods
    // implement interface methods in terms of pre/post methods

}
