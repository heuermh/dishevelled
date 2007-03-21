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

import java.util.Queue;

import org.dishevelled.observable.event.ObservableQueueChangeSupport;
import org.dishevelled.observable.event.QueueChangeEvent;
import org.dishevelled.observable.event.QueueChangeListener;
import org.dishevelled.observable.event.QueueChangeVetoException;
import org.dishevelled.observable.event.VetoableQueueChangeEvent;
import org.dishevelled.observable.event.VetoableQueueChangeListener;

/**
 * Abstract implementation of an observable queue
 * that decorates an instance of <code>Queue</code>.
 *
 * @param <E> queue element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractObservableQueue<E>
    extends AbstractQueueDecorator<E>
    implements ObservableQueue<E>
{
    /** Observable queue change support. */
    private ObservableQueueChangeSupport<E> support;


    /**
     * Create a new abstract observable queue that
     * decorates the specified queue.
     *
     * @param queue queue to decorate
     */
    protected AbstractObservableQueue(final Queue<E> queue)
    {
        super(queue);
        support = new ObservableQueueChangeSupport(this);
    }


    /**
     * Return the <code>ObservableQueueChangeSupport</code>
     * class backing this abstract observable queue.
     *
     * @return the <code>ObservableQueueChangeSupport</code>
     *    class backing this abstract observable queue
     */
    protected final ObservableQueueChangeSupport<E> getObservableQueueChangeSupport()
    {
        return support;
    }

    /** {@inheritDoc} */
    public final void addQueueChangeListener(final QueueChangeListener<E> l)
    {
        support.addQueueChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void removeQueueChangeListener(final QueueChangeListener<E> l)
    {
        support.removeQueueChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void addVetoableQueueChangeListener(final VetoableQueueChangeListener<E> l)
    {
        support.addVetoableQueueChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void removeVetoableQueueChangeListener(final VetoableQueueChangeListener<E> l)
    {
        support.removeVetoableQueueChangeListener(l);
    }

    /** {@inheritDoc} */
    public final QueueChangeListener<E>[] getQueueChangeListeners()
    {
        return support.getQueueChangeListeners();
    }

    /** {@inheritDoc} */
    public final int getQueueChangeListenerCount()
    {
        return support.getQueueChangeListenerCount();
    }

    /** {@inheritDoc} */
    public final VetoableQueueChangeListener<E>[] getVetoableQueueChangeListeners()
    {
        return support.getVetoableQueueChangeListeners();
    }

    /** {@inheritDoc} */
    public final int getVetoableQueueChangeListenerCount()
    {
        return support.getVetoableQueueChangeListenerCount();
    }

    /** {@inheritDoc} */
    public void fireQueueWillChange()
        throws QueueChangeVetoException
    {
        support.fireQueueWillChange();
    }

    /** {@inheritDoc} */
    public void fireQueueWillChange(final VetoableQueueChangeEvent<E> e)
        throws QueueChangeVetoException
    {
        support.fireQueueWillChange(e);
    }

    /** {@inheritDoc} */
    public void fireQueueChanged()
    {
        support.fireQueueChanged();
    }

    /** {@inheritDoc} */
    public void fireQueueChanged(final QueueChangeEvent<E> e)
    {
        support.fireQueueChanged(e);
    }


    // TODO:
    // add abstract pre/post methods
    // implement interface methods in terms of pre/post methods

}
