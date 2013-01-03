/*

    dsh-observable  Observable decorators for collection and map interfaces.
    Copyright (c) 2003-2013 held jointly by the individual authors.

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
package org.dishevelled.observable;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.dishevelled.observable.event.ListChangeEvent;
import org.dishevelled.observable.event.ListChangeListener;
import org.dishevelled.observable.event.ListChangeVetoException;
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
    private final ObservableListChangeSupport<E> support;


    /**
     * Create a new abstract observable list that
     * decorates the specified list.
     *
     * @param list list to decorate
     */
    protected AbstractObservableList(final List<E> list)
    {
        super(list);
        support = new ObservableListChangeSupport<E>(this);
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

    /**
     * Fire a will change event to all registered
     * <code>VetoableListChangeListener</code>s.
     *
     * @throws ListChangeVetoException if any of the listeners veto the change
     */
    public void fireListWillChange()
        throws ListChangeVetoException
    {
        support.fireListWillChange();
    }

    /**
     * Fire the specified will change event to all registered
     * <code>VetoableListChangeListener</code>s.
     *
     * @param e will change event
     * @throws ListChangeVetoException if any of the listeners veto the change
     */
    public void fireListWillChange(final VetoableListChangeEvent<E> e)
        throws ListChangeVetoException
    {
        support.fireListWillChange(e);
    }

    /**
     * Fire a change event to all registered <code>ListChangeListener</code>s.
     */
    public void fireListChanged()
    {
        support.fireListChanged();
    }

    /**
     * Fire the specified change event to all registered
     * <code>ListChangeListener</code>s.
     *
     * @param e change event
     */
    public void fireListChanged(final ListChangeEvent<E> e)
    {
        support.fireListChanged(e);
    }

    /**
     * Notify subclasses the <code>add</code> method is about to
     * be called on the wrapped list with the specified parameter.
     * Return <code>true</code> to proceed with the change.
     *
     * @param e <code>add</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preAdd(E e);

    /**
     * Notify subclasses the <code>add</code> method has just been
     * called on the wrapped list with the specified parameter.
     *
     * @param e <code>add</code> method parameter
     */
    protected abstract void postAdd(E e);

    /**
     * Notify subclasses the <code>add(int, E)</code> method is about to
     * be called on the wrapped list with the specified parameters.
     * Return <code>true</code> to proceed with the change.
     *
     * @param index <code>add(int, E)</code> method index parameter
     * @param e <code>add(int, E)</code> method e parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preAddAtIndex(int index, E e);

    /**
     * Notify subclasses the <code>add(int, E)</code> method has just been
     * called on the wrapped list with the specified parameters.
     *
     * @param index <code>add(int, E)</code> method index parameter
     * @param e <code>add(int, E)</code> method e parameter
     */
    protected abstract void postAddAtIndex(int index, E e);

    /**
     * Notify subclasses the <code>addAll</code> method is about to
     * be called on the wrapped list with the specified parameter.
     * Return <code>true</code> to proceed with the change.
     *
     * @param coll <code>addAll</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preAddAll(Collection<? extends E> coll);

    /**
     * Notify subclasses the <code>addAll</code> method has just been
     * called on the wrapped list with the specified parameter.
     *
     * @param coll <code>addAll</code> method parameter
     */
    protected abstract void postAddAll(Collection<? extends E> coll);

    /**
     * Notify subclasses the <code>addAll(int, Collection)</code> method is about to
     * be called on the wrapped list with the specified parameters.
     * Return <code>true</code> to proceed with the change.
     *
     * @param index <code>addAll(int, Collection)</code> method index parameter
     * @param coll <code>addAll(int, Collection)</code> method coll parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preAddAllAtIndex(int index, Collection<? extends E> coll);

    /**
     * Notify subclasses the <code>addAll(int, Collection)</code> method has just been
     * called on the wrapped list with the specified parameters.
     *
     * @param index <code>addAll(int, Collection)</code> method index parameter
     * @param coll <code>addAll(int, Collection)</code> method coll parameter
     */
    protected abstract void postAddAllAtIndex(int index, Collection<? extends E> coll);

    /**
     * Notify subclasses the <code>set</code> method is about to
     * be called on the wrapped list with the specified parameters.
     * Return <code>true</code> to proceed with the change.
     *
     * @param index <code>set</code> method index parameter
     * @param e <code>set</code> method e parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preSet(int index, E e);

    /**
     * Notify subclasses the <code>set</code> method has just been
     * called on the wrapped list with the specified parameters.
     *
     * @param index <code>set</code> method index parameter
     * @param e <code>set</code> method e parameter
     */
    protected abstract void postSet(int index, E e);

    /**
     * Notify subclasses the <code>clear</code> method is about to
     * be called on the wrapped list.
     * Return <code>true</code> to proceed with the change.
     *
     * @return true to proceed with the change
     */
    protected abstract boolean preClear();

    /**
     * Notify subclasses the <code>clear</code> method has just been
     * called on the wrapped list.
     */
    protected abstract void postClear();

    /**
     * Notify subclasses the <code>remove</code> method is about to
     * be called on the wrapped list with the specified parameter.
     * Return <code>true</code> to proceed with the change.
     *
     * @param o <code>remove</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preRemove(Object o);

    /**
     * Notify subclasses the <code>remove</code> method has just been
     * called on the wrapped list with the specified parameter.
     *
     * @param o <code>remove</code> method parameter
     */
    protected abstract void postRemove(Object o);

    /**
     * Notify subclasses the <code>remove(int)</code> method is about to
     * be called on the wrapped list with the specified parameter.
     * Return <code>true</code> to proceed with the change.
     *
     * @param index <code>remove(int)</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preRemoveIndex(int index);

    /**
     * Notify subclasses the <code>remove(int)</code> method has just been
     * called on the wrapped list with the specified parameter.
     *
     * @param index <code>remove(int)</code> method parameter
     */
    protected abstract void postRemoveIndex(int index);

    /**
     * Notify subclasses the <code>removeAll</code> method is about to
     * be called on the wrapped list with the specified parameter.
     * Return <code>true</code> to proceed with the change.
     *
     * @param coll <code>removeAll</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preRemoveAll(Collection<?> coll);

    /**
     * Notify subclasses the <code>removeAll</code> method has just been
     * called on the wrapped list with the specified parameter.
     *
     * @param coll <code>removeAll</code> method parameter
     */
    protected abstract void postRemoveAll(Collection<?> coll);

    /**
     * Notify subclasses the <code>retainAll</code> method is about to
     * be called on the wrapped list with the specified parameter.
     * Return <code>true</code> to proceed with the change.
     *
     * @param coll <code>retainAll</code> method parameter
     * @return true to proceed with the change
     */
    protected abstract boolean preRetainAll(Collection<?> coll);

    /**
     * Notify subclasses the <code>retainAll</code> method has just been
     * called on the wrapped list with the specified parameter.
     *
     * @param coll <code>retainAll</code> method parameter
     */
    protected abstract void postRetainAll(Collection<?> coll);

    /**
     * Notify subclasses the <code>remove</code> method is about to
     * be called on the wrapped list's iterator.
     * Return <code>true</code> to proceed with the change.
     *
     * @return true to proceed with the change
     */
    protected abstract boolean preIteratorRemove();

    /**
     * Notify subclasses the <code>remove</code> method has just been
     * called on the wrapped list's iterator.
     */
    protected abstract void postIteratorRemove();

    /**
     * Notify subclasses the <code>remove</code> method is about to
     * be called on the wrapped list's list iterator.
     * Return <code>true</code> to proceed with the change.
     *
     * @return true to proceed with the change
     */
    protected abstract boolean preListIteratorRemove();

    /**
     * Notify subclasses the <code>remove</code> method has just been
     * called on the wrapped list's list iterator.
     */
    protected abstract void postListIteratorRemove();

    /**
     * Notify subclasses the <code>set</code> method is about to
     * be called on the wrapped list's list iterator.
     * Return <code>true</code> to proceed with the change.
     *
     * @return true to proceed with the change
     */
    protected abstract boolean preListIteratorSet();

    /**
     * Notify subclasses the <code>set</code> method has just been
     * called on the wrapped list's list iterator.
     */
    protected abstract void postListIteratorSet();


    /** {@inheritDoc} */
    public boolean add(final E e)
    {
        boolean result = false;
        if (preAdd(e))
        {
            result = super.add(e);
            postAdd(e);
        }
        return result;
    }

    /** {@inheritDoc} */
    public void add(final int index, final E e)
    {
        if (preAddAtIndex(index, e))
        {
            super.add(index, e);
            postAddAtIndex(index, e);
        }
    }

    /** {@inheritDoc} */
    public boolean addAll(final Collection<? extends E> coll)
    {
        boolean result = false;
        if (preAddAll(coll))
        {
            result = super.addAll(coll);
            postAddAll(coll);
        }
        return result;
    }

    /** {@inheritDoc} */
    public boolean addAll(final int index, final Collection<? extends E> coll)
    {
        boolean result = false;
        if (preAddAllAtIndex(index, coll))
        {
            result = super.addAll(index, coll);
            postAddAllAtIndex(index, coll);
        }
        return result;
    }

    /** {@inheritDoc} */
    public void clear()
    {
        if (preClear())
        {
            super.clear();
            postClear();
        }
    }

    /** {@inheritDoc} */
    public E remove(final int index)
    {
        E result = null;
        if (preRemoveIndex(index))
        {
            result = super.remove(index);
            postRemoveIndex(index);
        }
        return result;
    }

    /** {@inheritDoc} */
    public boolean remove(final Object o)
    {
        boolean result = false;
        if (preRemove(o))
        {
            result = super.remove(o);
            postRemove(o);
        }
        return result;
    }

    /** {@inheritDoc} */
    public boolean removeAll(final Collection<?> coll)
    {
        boolean result = false;
        if (preRemoveAll(coll))
        {
            result = super.removeAll(coll);
            postRemoveAll(coll);
        }
        return result;
    }

    /** {@inheritDoc} */
    public boolean retainAll(final Collection<?> coll)
    {
        boolean result = false;
        if (preRetainAll(coll))
        {
            result = super.retainAll(coll);
            postRetainAll(coll);
        }
        return result;
    }

    /** {@inheritDoc} */
    public E set(final int index, final E e)
    {
        E result = null;
        if (preSet(index, e))
        {
            result = super.set(index, e);
            postSet(index, e);
        }
        return result;
    }

    /** {@inheritDoc} */
    public Iterator<E> iterator()
    {
        return new ObservableListIterator(super.iterator());
    }

    /** {@inheritDoc} */
    public ListIterator<E> listIterator()
    {
        return new ObservableListListIterator(super.listIterator());
    }

    /** {@inheritDoc} */
    public ListIterator<E> listIterator(final int index)
    {
        return new ObservableListListIterator(super.listIterator(index));
    }

    /** {@inheritDoc} */
    public List<E> subList(final int fromIndex, final int toIndex)
    {
        //return new ObservableSubList<E>(super.subList(fromIndex, toIndex));
        return new ObservableSubList(super.subList(fromIndex, toIndex));
    }


    /**
     * Observable subList decorator.
     */
    //protected class ObservableSubList<E>
    protected class ObservableSubList
        extends AbstractListDecorator<E>
    {

        /**
         * Create a new observable subList that decorates
         * the specified sublist.
         *
         * @param subList subList to decorate
         */
        protected ObservableSubList(final List<E> subList)
        {
            super(subList);
        }


        /** {@inheritDoc} */
        public boolean add(final E e)
        {
            boolean result = false;
            if (preAdd(e))
            {
                result = super.add(e);
                postAdd(e);
            }
            return result;
        }

        /** {@inheritDoc} */
        public void add(final int index, final E e)
        {
            if (preAddAtIndex(index, e))
            {
                super.add(index, e);
                postAddAtIndex(index, e);
            }
        }

        /** {@inheritDoc} */
        public boolean addAll(final Collection<? extends E> coll)
        {
            boolean result = false;
            if (preAddAll(coll))
            {
                result = super.addAll(coll);
                postAddAll(coll);
            }
            return result;
        }

        /** {@inheritDoc} */
        public boolean addAll(final int index, final Collection<? extends E> coll)
        {
            boolean result = false;
            if (preAddAllAtIndex(index, coll))
            {
                result = super.addAll(index, coll);
                postAddAllAtIndex(index, coll);
            }
            return result;
        }

        /** {@inheritDoc} */
        public void clear()
        {
            if (preClear())
            {
                super.clear();
                postClear();
            }
        }

        /** {@inheritDoc} */
        public E remove(final int index)
        {
            E result = null;
            if (preRemoveIndex(index))
            {
                result = super.remove(index);
                postRemoveIndex(index);
            }
            return result;
        }

        /** {@inheritDoc} */
        public boolean remove(final Object o)
        {
            boolean result = false;
            if (preRemove(o))
            {
                result = super.remove(o);
                postRemove(o);
            }
            return result;
        }

        /** {@inheritDoc} */
        public boolean removeAll(final Collection<?> coll)
        {
            boolean result = false;
            if (preRemoveAll(coll))
            {
                result = super.removeAll(coll);
                postRemoveAll(coll);
            }
            return result;
        }

        /** {@inheritDoc} */
        public boolean retainAll(final Collection<?> coll)
        {
            boolean result = false;
            if (preRetainAll(coll))
            {
                result = super.retainAll(coll);
                postRetainAll(coll);
            }
            return result;
        }

        /** {@inheritDoc} */
        public E set(final int index, final E e)
        {
            E result = null;
            if (preSet(index, e))
            {
                result = super.set(index, e);
                postSet(index, e);
            }
            return result;
        }

        /** {@inheritDoc} */
        public Iterator<E> iterator()
        {
            return new ObservableListIterator(super.iterator());
        }

        /** {@inheritDoc} */
        public ListIterator<E> listIterator()
        {
            return new ObservableListListIterator(super.listIterator());
        }

        /** {@inheritDoc} */
        public ListIterator<E> listIterator(final int index)
        {
            return new ObservableListListIterator(super.listIterator(index));
        }

        /** {@inheritDoc} */
        public List<E> subList(final int fromIndex, final int toIndex)
        {
            //return new ObservableSubList<E>(super.subList(fromIndex, toIndex));
            return new ObservableSubList(super.subList(fromIndex, toIndex));
        }
    }

    /**
     * Observable list iterator.
     */
    private class ObservableListIterator
        extends AbstractIteratorDecorator<E>
    {

        /**
         * Create a new observable list iterator that decorates
         * the specified iterator.
         *
         * @param iterator iterator to decorate
         */
        protected ObservableListIterator(final Iterator<E> iterator)
        {
            super(iterator);
        }


        /** {@inheritDoc} */
        public void remove()
        {
            if (preIteratorRemove())
            {
                super.remove();
                postIteratorRemove();
            }
        }
    }

    /**
     * Observable list list iterator.
     */
    private class ObservableListListIterator
        extends AbstractListIteratorDecorator<E>
    {

        /**
         * Create a new observable list list iterator that decorates
         * the specified list iterator.
         *
         * @param listIterator list iterator to decorate
         */
        protected ObservableListListIterator(final ListIterator<E> listIterator)
        {
            super(listIterator);
        }


        /** {@inheritDoc} */
        public void remove()
        {
            if (preListIteratorRemove())
            {
                super.remove();
                postListIteratorRemove();
            }
        }

        /** {@inheritDoc} */
        public void set(final E e)
        {
            if (preListIteratorSet())
            {
                super.set(e);
                postListIteratorSet();
            }
        }
    }
}
