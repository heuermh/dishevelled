/*

    dsh-matrix  long-addressable bit and typed object matrix implementations.
    Copyright (c) 2004-2007 held jointly by the individual authors.

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
package org.dishevelled.matrix.impl;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.dishevelled.matrix.ObjectMatrix1D;

import org.dishevelled.functor.UnaryFunction;
import org.dishevelled.functor.UnaryProcedure;
import org.dishevelled.functor.UnaryPredicate;
import org.dishevelled.functor.BinaryFunction;
import org.dishevelled.functor.BinaryProcedure;
import org.dishevelled.functor.BinaryPredicate;

/**
 * Abstract implementation of ObjectMatrix1D.
 *
 * @param <E> type of this abstract 1D matrix
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractObjectMatrix1D<E>
    implements ObjectMatrix1D<E>
{
    /** Size. */
    protected long size;

    /** Index of the first element. */
    protected long zero;

    /** Number of indices between any two elements. */
    protected long stride;

    /** True if this instance is a view. */
    protected boolean isView;


    /**
     * Protected no-arg constructor, to support serialization.
     */
    protected AbstractObjectMatrix1D()
    {
        // empty
    }

    /**
     * Create a new abstract 1D matrix with the specified size.
     *
     * @param size size, must be <code>&gt;= 0</code>
     * @throws IllegalArgumentException if <code>size</code> is negative
     */
    protected AbstractObjectMatrix1D(final long size)
    {
        if (size < 0)
        {
            throw new IllegalArgumentException(size + " < 0");
        }

        this.size = size;
        this.zero = 0L;
        this.stride = 1L;
        this.isView = false;
    }

    /**
     * Create a new abstract 1D matrix with the specified parameters.
     *
     * @param size size, must be <code>&gt;= 0</code>
     * @param zero index of the first element
     * @param stride number of indices between any two elements
     * @param isView true if this instance is a view
     */
    protected AbstractObjectMatrix1D(final long size,
                                     final long zero,
                                     final long stride,
                                     final boolean isView)
    {
        if (size < 0)
        {
            throw new IllegalArgumentException(size + " < 0");
        }

        this.size = size;
        this.zero = zero;
        this.stride = stride;
        this.isView = isView;
    }


    /** {@inheritDoc} */
    public long size()
    {
        return size;
    }

    /** {@inheritDoc} */
    public long cardinality()
    {
        long cardinality = 0;
        for (E e : this)
        {
            if (e != null)
            {
                cardinality++;
            }
        }
        return cardinality;
    }

    /** {@inheritDoc} */
    public boolean isEmpty()
    {
        return (0 == cardinality());
    }

    /** {@inheritDoc} */
    public void clear()
    {
        forEach(new BinaryProcedure<Long, E>()
            {
                public void run(final Long index, final E e)
                {
                    if (e != null)
                    {
                        set(index, null);
                    }
                }
            });
    }

    /** {@inheritDoc} */
    public Iterator<E> iterator()
    {
        return new ObjectMatrix1DIterator();
    }

    /** {@inheritDoc} */
    public E get(final long index)
    {
        if (index < 0)
        {
            throw new IndexOutOfBoundsException(index + " < 0");
        }
        if (index >= size)
        {
            throw new IndexOutOfBoundsException(index + " >= " + size);
        }

        return getQuick(index);
    }

    /** {@inheritDoc} */
    public void set(final long index, final E e)
    {
        if (index < 0)
        {
            throw new IndexOutOfBoundsException(index + " < 0");
        }
        if (index >= size)
        {
            throw new IndexOutOfBoundsException(index + " > " + size);
        }

        setQuick(index, e);
    }

    /** {@inheritDoc} */
    public void setQuick(final long index, final E e)
    {
        throw new UnsupportedOperationException();
    }

    /** {@inheritDoc} */
    public ObjectMatrix1D<E> assign(final E e)
    {
        forEach(new BinaryProcedure<Long, E>()
            {
                public void run(final Long index, final E ignore)
                {
                    setQuick(index, e);
                }
            });

        return this;
    }

    /** {@inheritDoc} */
    public ObjectMatrix1D<E> assign(final UnaryFunction<E, E> function)
    {
        if (function == null)
        {
            throw new IllegalArgumentException("function must not be null");
        }

        forEach(new BinaryProcedure<Long, E>()
            {
                public void run(final Long index, final E e)
                {
                    setQuick(index, function.evaluate(e));
                }
            });

        return this;
    }

    /** {@inheritDoc} */
    public ObjectMatrix1D<E> assign(final ObjectMatrix1D<? extends E> other)
    {
        if (other == null)
        {
            throw new IllegalArgumentException("other must not be null");
        }
        if (!(size == other.size()))
        {
            throw new IllegalArgumentException("this and other must be the same size");
        }

        forEach(new BinaryProcedure<Long, E>()
            {
                public void run(final Long index, final E ignore)
                {
                    E e = other.getQuick(index);
                    setQuick(index, e);
                }
            });

        return this;
    }

    /** {@inheritDoc} */
    public ObjectMatrix1D<E> assign(final ObjectMatrix1D<? extends E> other,
                                    final BinaryFunction<E, E, E> function)
    {
        if (other == null)
        {
            throw new IllegalArgumentException("other must not be null");
        }
        if (size != other.size())
        {
            throw new IllegalArgumentException("this and other must be the same size");
        }
        if (function == null)
        {
            throw new IllegalArgumentException("function must not be null");
        }

        forEach(new BinaryProcedure<Long, E>()
            {
                public void run(final Long index, final E e)
                {
                    E result = function.evaluate(e, other.getQuick(index));
                    setQuick(index, result);
                }
            });

        return this;
    }

    /** {@inheritDoc} */
    public E aggregate(final BinaryFunction<E, E, E> aggr, final UnaryFunction<E, E> function)
    {
        if (aggr == null)
        {
            throw new IllegalArgumentException("aggr must not be null");
        }
        if (function == null)
        {
            throw new IllegalArgumentException("function must not be null");
        }

        if (size == 0)
        {
            return null;
        }

        long last = (size - 1L);
        E a = function.evaluate(getQuick(last));
        for (long index = last; --index >= 0;)
        {
            a = aggr.evaluate(a, function.evaluate(getQuick(index)));
        }
        return a;
    }

    /** {@inheritDoc} */
    public E aggregate(final ObjectMatrix1D<? extends E> other,
                       final BinaryFunction<E, E, E> aggr,
                       final BinaryFunction<E, E, E> function)
    {
        if (other == null)
        {
            throw new IllegalArgumentException("other must not be null");
        }
        if (!(size == other.size()))
        {
            throw new IllegalArgumentException("this and other must be the same size");
        }
        if (aggr == null)
        {
            throw new IllegalArgumentException("aggr must not be null");
        }
        if (function == null)
        {
            throw new IllegalArgumentException("function must not be null");
        }

        if (size == 0)
        {
            return null;
        }

        long last = (size - 1L);
        E a = function.evaluate(getQuick(last), other.getQuick(last));
        for (long index = last; --index >= 0;)
        {
            a = aggr.evaluate(a, function.evaluate(getQuick(index), other.getQuick(index)));
        }
        return a;
    }

    /**
     * Create a new view.
     *
     * @return a new view
     */
    protected AbstractObjectMatrix1D<E> view()
    {
        try
        {
            AbstractObjectMatrix1D<E> m = (AbstractObjectMatrix1D<E>) clone();
            return m;
        }
        catch (CloneNotSupportedException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Self-modifying version of <code>viewFlip()</code>.
     *
     * @return modified version of <code>this</code>
     */
    protected AbstractObjectMatrix1D<E> vFlip()
    {
        if (size > 0)
        {
            zero += (size - 1) * stride;
            stride = -stride;
            isView = true;
        }

        return this;
    }

    /** {@inheritDoc} */
    public ObjectMatrix1D<E> viewFlip()
    {
        return view().vFlip();
    }

    /**
     * Self-modifying version of <code>viewPart(long, long)</code>.
     *
     * @param index index
     * @param width width
     * @return modified version of <code>this</code>
     */
    protected AbstractObjectMatrix1D<E> vPart(final long index, final long width)
    {
        if (size > 0)
        {
            zero += (stride * index);
            size = width;
            isView = true;
        }

        return this;
    }

    /** {@inheritDoc} */
    public ObjectMatrix1D<E> viewPart(final long index, final long width)
    {
        return view().vPart(index, width);
    }

    /** {@inheritDoc} */
    public ObjectMatrix1D<E> viewSelection(final long[] indices)
    {
        return null;
    }

    /** {@inheritDoc} */
    public ObjectMatrix1D<E> viewSelection(final UnaryPredicate<E> predicate)
    {
        return null;
    }

    /**
     * Self-modifying version of <code>viewStrides(long)</code>.
     *
     * @param stride stride
     * @return modified version of <code>this</code>
     */
    protected AbstractObjectMatrix1D<E> vStrides(final long stride)
    {
        this.stride *= stride;

        if (size != 0)
        {
            size = ((size - 1) / stride) + 1L;
        }
        isView = true;

        return this;
    }

    /** {@inheritDoc} */
    public ObjectMatrix1D<E> viewStrides(final long stride)
    {
        return view().vStrides(stride);
    }

    /** {@inheritDoc} */
    public void forEach(final UnaryProcedure<E> procedure)
    {
        if (procedure == null)
        {
            throw new IllegalArgumentException("procedure must not be null");
        }

        for (long index = 0; index < size; index++)
        {
            E e = getQuick(index);
            procedure.run(e);
        }
    }

    /** {@inheritDoc} */
    public void forEach(final UnaryPredicate<E> predicate,
                        final UnaryProcedure<E> procedure)
    {
        if (predicate == null)
        {
            throw new IllegalArgumentException("predicate must not be null");
        }
        if (procedure == null)
        {
            throw new IllegalArgumentException("procedure must not be null");
        }

        for (long index = 0; index < size; index++)
        {
            E e = getQuick(index);
            if (predicate.test(e))
            {
                procedure.run(e);
            }
        }
    }

    /** {@inheritDoc} */
    public void forEach(final BinaryProcedure<Long, E> procedure)
    {
        if (procedure == null)
        {
            throw new IllegalArgumentException("procedure must not be null");
        }

        for (long index = 0; index < size; index++)
        {
            E e = getQuick(index);
            procedure.run(index, e);
        }
    }

    /** {@inheritDoc} */
    public void forEach(final BinaryPredicate<Long, E> predicate,
                        final BinaryProcedure<Long, E> procedure)
    {
        if (predicate == null)
        {
            throw new IllegalArgumentException("predicate must not be null");
        }
        if (procedure == null)
        {
            throw new IllegalArgumentException("procedure must not be null");
        }

        for (long index = 0; index < size; index++)
        {
            E e = getQuick(index);
            if (predicate.test(index, e))
            {
                procedure.run(index, e);
            }
        }
    }

    /** {@inheritDoc} */
    public String toString()
    {
        final StringBuffer sb = new StringBuffer();
        sb.append(size);
        sb.append(" matrix\n");

        forEach(new BinaryProcedure<Long, E>()
                {
                    public void run(final Long index, final E e)
                    {
                        sb.append((e == null) ? "null" : e.toString());
                        if (index != (size() - 1))
                        {
                            sb.append(" ");
                        }
                    }
                });

        return sb.toString();
    }

    /**
     * ObjectMatrix1D iterator.
     */
    private class ObjectMatrix1DIterator
        implements Iterator<E>
    {
        /** Index. */
        private long index = 0L;


        /** {@inheritDoc} */
        public boolean hasNext()
        {
            return (index < size);
        }

        /** {@inheritDoc} */
        public E next()
        {
            if (index < size)
            {
                E e = getQuick(index);
                index++;
                return e;
            }
            else
            {
                throw new NoSuchElementException("index=" + index);
            }
        }

        /** {@inheritDoc} */
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }
}
