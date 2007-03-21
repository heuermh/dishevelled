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

import java.util.List;

import org.dishevelled.observable.event.ListChangeEvent;
import org.dishevelled.observable.event.ListChangeListener;
import org.dishevelled.observable.event.ListChangeVetoException;
import org.dishevelled.observable.event.ListChangeEvent;
import org.dishevelled.observable.event.ObservableListChangeSupport;
import org.dishevelled.observable.event.VetoableListChangeEvent;
import org.dishevelled.observable.event.VetoableListChangeListener;

/**
 * Abstract implementation of an observable list
 * that decorates an instance of <code>List</code>.
 *
 * @param <E> list element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractObservableList<E>
    extends AbstractListDecorator<E>
    implements ObservableList<E>
{
    /** Observable list change support. */
    private ObservableListChangeSupport<E> support;


    /**
     * Create a new abstract observable list that
     * decorates the specified list.
     *
     * @param list list to decorate
     */
    protected AbstractObservableList(final List<E> list)
    {
        super(list);
        support = new ObservableListChangeSupport(this);
    }


    /**
     * Return the <code>ObservableListChangeSupport</code>
     * class backing this abstract observable list.
     *
     * @return the <code>ObservableListChangeSupport</code>
     *    class backing this abstract observable list
     */
    protected final ObservableListChangeSupport<E> getObservableListChangeSupport()
    {
        return support;
    }

    /** {@inheritDoc} */
    public final void addListChangeListener(final ListChangeListener<E> l)
    {
        support.addListChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void removeListChangeListener(final ListChangeListener<E> l)
    {
        support.removeListChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void addVetoableListChangeListener(final VetoableListChangeListener<E> l)
    {
        support.addVetoableListChangeListener(l);
    }

    /** {@inheritDoc} */
    public final void removeVetoableListChangeListener(final VetoableListChangeListener<E> l)
    {
        support.removeVetoableListChangeListener(l);
    }

    /** {@inheritDoc} */
    public final ListChangeListener<E>[] getListChangeListeners()
    {
        return support.getListChangeListeners();
    }

    /** {@inheritDoc} */
    public final int getListChangeListenerCount()
    {
        return support.getListChangeListenerCount();
    }

    /** {@inheritDoc} */
    public final VetoableListChangeListener<E>[] getVetoableListChangeListeners()
    {
        return support.getVetoableListChangeListeners();
    }

    /** {@inheritDoc} */
    public final int getVetoableListChangeListenerCount()
    {
        return support.getVetoableListChangeListenerCount();
    }

    /** {@inheritDoc} */
    public void fireListWillChange()
        throws ListChangeVetoException
    {
        support.fireListWillChange();
    }

    /** {@inheritDoc} */
    public void fireListWillChange(final VetoableListChangeEvent<E> e)
        throws ListChangeVetoException
    {
        support.fireListWillChange(e);
    }

    /** {@inheritDoc} */
    public void fireListChanged()
    {
        support.fireListChanged();
    }

    /** {@inheritDoc} */
    public void fireListChanged(final ListChangeEvent<E> e)
    {
        support.fireListChanged(e);
    }


    // TODO:
    // add abstract pre/post methods
    // implement interface methods in terms of pre/post methods

}
