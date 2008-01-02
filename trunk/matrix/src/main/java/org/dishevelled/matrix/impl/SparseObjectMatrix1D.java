/*

    dsh-matrix  long-addressable bit and typed object matrix implementations.
    Copyright (c) 2004-2008 held jointly by the individual authors.

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
package org.dishevelled.matrix.impl;

import java.io.IOException;
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.Map;
import java.util.HashMap;

/**
 * Sparse implementation of ObjectMatrix1D based on
 * a hash map whose keys are <code>Long</code>s.
 *
 * <p>The cardinality of this sparse object matrix is limited
 * by the <code>HashMap</code> underlying this implementation
 * to something less than <code>Integer.MAX_VALUE</code>.  The
 * addressable size, on the other hand, is limited to
 * <code>size &lt; Long.MAX_VALUE</code>.
 *
 * @param <E> type for this sparse 1D matrix
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class SparseObjectMatrix1D<E>
    extends AbstractObjectMatrix1D<E>
    implements Serializable
{
    /** Map of elements keyed by a <code>Long</code> index. */
    private Map<Long, E> elements;

    /** Default load factor. */
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;


    /**
     * Private no-arg constructor, to support serialization.
     */
    private SparseObjectMatrix1D()
    {
        elements = null;
    }

    /**
     * Create a new abstract 1D matrix with the specified size.
     *
     * @param size size, must be <code>&gt;= 0</code>
     * @throws IllegalArgumentException if <code>size</code> is negative
     */
    public SparseObjectMatrix1D(final long size)
    {
        this(size, (int) Math.min(Integer.MAX_VALUE, (size * DEFAULT_LOAD_FACTOR)), DEFAULT_LOAD_FACTOR);
    }

    /**
     * Create a new abstract 1D matrix with the specified size,
     * initial capacity, and load factor.
     *
     * @param size size, must be <code>&gt;= 0</code>
     * @param initialCapacity initial capacity, must be <code>&gt;= 0</code>
     * @param loadFactor load factor, must be <code>&gt; 0</code>
     */
    public SparseObjectMatrix1D(final long size, final int initialCapacity, final float loadFactor)
    {
        super(size);
        elements = new HashMap<Long, E>(initialCapacity, loadFactor);
    }

    /**
     * Create a new instance of SparseObjectMatrix1D with the specified
     * parameters and map of elements.  Used exclusively by the
     * <code>clone()</code> method.
     *
     * @param size size, must be <code>&gt;= 0</code>
     * @param zero index of the first element
     * @param stride number of indices between any two elements
     * @param isView true if this instance is a view
     * @param elements map of elements
     */
    protected SparseObjectMatrix1D(final long size,
                                   final long zero,
                                   final long stride,
                                   final boolean isView,
                                   final Map<Long, E> elements)
    {
        super(size, zero, stride, isView);
        this.elements = elements;
    }


    /** {@inheritDoc} */
    public Object clone()
    {
        return new SparseObjectMatrix1D<E>(size, zero, stride, isView, elements);
    }

    /** {@inheritDoc} */
    public E getQuick(final long index)
    {
        long i = zero + index * stride;
        return elements.get(i);
    }

    /** {@inheritDoc} */
    public void setQuick(final long index, final E e)
    {
        long i = zero + index * stride;
        if (e == null)
        {
            elements.remove(i);
        }
        else
        {
            elements.put(i, e);
        }
    }

    /**
     * Write this 1D object matrix to the specified object output stream.
     *
     * @see java.io.ObjectOutputStream
     * @param out object output stream
     * @throws IOException if an IO error occurs
     */
    private void writeObject(final ObjectOutputStream out)
        throws IOException
    {
        out.writeLong(size);
        out.writeLong(zero);
        out.writeLong(stride);
        out.writeBoolean(isView);
        out.writeObject(elements);
    }

    /**
     * Read this 1D object matrix in from the specified object input stream.
     *
     * @see java.io.ObjectInputStream
     * @param in object input stream
     * @throws IOException if an IO error occurs
     * @throws ClassNotFoundException if a classloading error occurs
     */
    private void readObject(final ObjectInputStream in)
        throws IOException, ClassNotFoundException
    {
        super.size = in.readLong();
        super.zero = in.readLong();
        super.stride = in.readLong();
        super.isView = in.readBoolean();
        this.elements = (Map<Long, E>) in.readObject();
    }

    /** {@inheritDoc} */
    public String toString()
    {
        StringBuffer sb = new StringBuffer(super.toString());
        sb.append("\n   size=");
        sb.append(size);
        sb.append("   zero=");
        sb.append(zero);
        sb.append("   stride=");
        sb.append(stride);
        sb.append("\n   elements=");
        sb.append(elements.toString());
        sb.append("\n");
        return sb.toString();
    }
}
