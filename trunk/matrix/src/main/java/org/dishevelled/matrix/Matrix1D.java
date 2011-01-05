/*

    dsh-matrix  long-addressable bit and typed object matrix implementations.
    Copyright (c) 2004-2011 held jointly by the individual authors.

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
package org.dishevelled.matrix;

import java.util.Iterator;

import org.dishevelled.functor.UnaryFunction;
import org.dishevelled.functor.UnaryPredicate;
import org.dishevelled.functor.UnaryProcedure;
import org.dishevelled.functor.BinaryFunction;
import org.dishevelled.functor.BinaryPredicate;
import org.dishevelled.functor.BinaryProcedure;

/**
 * Typed fixed size matrix of objects in one dimension, indexed
 * by <code>long</code>s.
 *
 * @param <E> type of this 1D matrix
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface Matrix1D<E>
    extends Iterable<E>
{

    /**
     * Return the size of this 1D matrix.
     *
     * @return the size of this 1D matrix
     */
    long size();

    /**
     * Return the cardinality of this 1D matrix, the number
     * of non-null values.
     *
     * @return the cardinality of this 1D matrix
     */
    long cardinality();

    /**
     * Return true if the cardinality of this 1D matrix is zero.
     *
     * @return true if the cardinality of this 1D matrix is zero
     */
    boolean isEmpty();

    /**
     * Clear all the values in this 1D matrix (optional operation).
     *
     * @throws UnsupportedOperationException if the <code>clear</code> operation
     *    is not supported by this 1D matrix
     */
    void clear();

    /**
     * Return the value at the specified index.
     *
     * @param index index, must be <code>&gt;= 0</code> and <code>&lt; size()</code>
     * @return the value at the specified index
     * @throws IndexOutOfBoundsException if <code>index</code> is negative
     *    or if <code>index</code> is greater than or equal to <code>size()</code>
     */
    E get(long index);

    /**
     * Return the value at the specified index without checking bounds.
     *
     * @param index index, should be <code>&gt;= 0</code> and <code>&lt; size()</code>
     *    (unchecked)
     * @return the value at the specified index without checking bounds
     */
    E getQuick(long index);

    /**
     * Set the value at the specified index to <code>e</code> (optional operation).
     *
     * @param index index, must be <code>&gt;= 0</code> and <code>&lt; size()</code>
     * @param e value
     * @throws IndexOutOfBoundsException if <code>index</code> is negative
     *    or if <code>index</code> is greater than or equal to <code>size()</code>
     * @throws UnsupportedOperationException if the <code>set</code> operation
     *    is not supported by this 1D matrix
     */
    void set(long index, E e);

    /**
     * Set the value at the specified index to <code>e</code> without
     * checking bounds (optional operation).
     *
     * @param index index, should be <code>&gt;= 0</code> and <code>&lt; size()</code>
     *    (unchecked)
     * @param e value
     * @throws UnsupportedOperationException if the <code>setQuick</code> operation
     *    is not supported by this 1D matrix
     */
    void setQuick(long index, E e);

    /**
     * Return an iterator over the values in this 1D matrix, including
     * <code>null</code>s.
     *
     * @return an iterator over the values in this 1D matrix, including
     *    <code>null</code>s
     */
    Iterator<E> iterator();

    /**
     * Assign all values in this 1D matrix to <code>e</code> (optional operation).
     *
     * @param e value
     * @return this 1D matrix, for convenience
     * @throws UnsupportedOperationException if this <code>assign</code> operation
     *    is not supported by this 1D matrix
     */
    Matrix1D<E> assign(E e);

    /**
     * Assign the result of the specified function to each value
     * in this 1D matrix (optional operation).
     *
     * @param function function, must not be null
     * @return this 1D matrix, for convenience
     * @throws UnsupportedOperationException if this <code>assign</code> operation
     *    is not supported by this 1D matrix
     */
    Matrix1D<E> assign(UnaryFunction<E, E> function);

    /**
     * Assign all values in this 1D matrix to the values in the
     * specified matrix (optional operation).
     *
     * @param other other 1D matrix, must not be null and must
     *    be the same size as this 1D matrix
     * @return this 1D matrix, for convenience
     * @throws UnsupportedOperationException if this <code>assign</code> operation
     *    is not supported by this 1D matrix
     */
    Matrix1D<E> assign(Matrix1D<? extends E> other);

    /**
     * Assign the result of the specified function of a value from
     * this 1D matrix and the specified matrix to each value in this
     * 1D matrix (optional operation).
     *
     * @param other other 1D matrix, must not be null and must
     *    be the same size as this 1D matrix
     * @param function function, must not be null
     * @return this 1D matrix, for convenience
     * @throws UnsupportedOperationException if this <code>assign</code> operation
     *    is not supported by this 1D matrix
     */
    Matrix1D<E> assign(Matrix1D<? extends E> other,
                             BinaryFunction<E, E, E> function);

    /**
     * Apply a function to each value in this 1D matrix and aggregate
     * the result.
     *
     * @param aggr aggregate function, must not be null
     * @param function function, must not be null
     * @return the aggregate result
     */
    E aggregate(BinaryFunction<E, E, E> aggr, UnaryFunction<E, E> function);

    /**
     * Apply a function to each value in this 1D matrix and the specified
     * matrix and aggregate the result.
     *
     * @param other other 1D matrix, must not be null and must
     *    be the same size as this 1D matrix
     * @param aggr aggregate function, must not be null
     * @param function function, must not be null
     * @return the aggregate result
     */
    E aggregate(Matrix1D<? extends E> other,
                BinaryFunction<E, E, E> aggr,
                BinaryFunction<E, E, E> function);

    /**
     * Return a new 1D matrix <i>flip view</i>.  What used to be index
     * <code>0</code> is now index <code>size() - 1</code>, ..., what used to
     * be index <code>size() - 1</code> is now index <code>0</code>.  The
     * view is backed by this matrix, so changes made to the returned view
     * are reflected in this matrix, and vice-versa.
     *
     * @return a new 1D matrix <i>flip view</i>
     */
    Matrix1D<E> viewFlip();

    /**
     * Return a new 1D matrix <i>sub-range view</i> that contains only those values
     * from index <code>index</code> to <code>index + width - 1</code>.
     * The view is backed by this matrix, so changes made to the returned
     * view are reflected in this matrix, and vice-versa.
     *
     * @param index index, must be <code>&gt;= 0</code> and <code>&lt; size()</code>
     * @param width width
     * @return a new 1D matrix <i>sub-range view</i> that contains only those values
     *    from index <code>index</code> to <code>index + width - 1</code>
     */
    Matrix1D<E> viewPart(long index, long width);

    /**
     * Return a new 1D matrix <i>selection view</i> that contains only those values
     * at the specified indices.  The view is backed by this matrix, so
     * changes made to the returned view are reflected in this matrix,
     * and vice-versa.
     *
     * @param indices indices
     * @return a new 1D matrix <i>selection view</i> that contains only those values
     *    at the specified indices
     */
    Matrix1D<E> viewSelection(long[] indices);

    /**
     * Return a new 1D matrix <i>selection view</i> that contains only those values
     * selected by the specified predicate.  The view is backed by this
     * matrix, so changes made to the returned view are reflected in
     * this matrix, and vice-versa.
     *
     * @param predicate predicate, must not be null
     * @return a new 1D matrix <i>selection view</i> that contains only those values
     *    selected by the specified predicate
     */
    Matrix1D<E> viewSelection(UnaryPredicate<E> predicate);

    /**
     * Return a new 1D matrix <i>selection view</i> that contains only those values
     * at the indices present in the specified bit mask.  The view is backed by this matrix, so
     * changes made to the returned view are reflected in this matrix, and vice-versa.
     *
     * @param mask 1D bit mask, must not be null
     * @return a new 1D matrix <i>selection view</i> that contains only those values
     *    at the indices present in the specified mask
     */
    Matrix1D<E> viewSelection(BitMatrix1D mask);

    /**
     * Return a new 1D matrix <i>stride view</i> which is a sub matrix
     * consisting of every i-th value in this matrix.  The view is backed
     * by this matrix, so changes made to the returned view are reflected in
     * this matrix, and vice-versa.
     *
     * @param stride stride, must be <code>&gt; 0</code>
     * @return a new 1D matrix <i>stride view</i> which is a sub matrix consisting
     *    of every i-th value in this matrix
     * @throws IndexOutOfBoundsException if <code>stride</code> is
     *    negative or zero
     */
    Matrix1D<E> viewStrides(long stride);

    /**
     * Apply the specified procedure to each value in this 1D matrix.
     *
     * <p>For example:
     * <pre>
     * Matrix1D&lt;String&gt; m;
     * m.forEach(new UnaryProcedure&lt;String&gt;()
     *     {
     *         public void run(final String value)
     *         {
     *             System.out.println(value);
     *         }
     *     });
     * </pre></p>
     *
     * @param procedure procedure, must not be null
     */
    void forEach(UnaryProcedure<? super E> procedure);

    /**
     * Apply the specified procedure to each value in this 1D matrix
     * accepted by the specified predicate.
     *
     * <p>For example:
     * <pre>
     * Matrix1D&lt;String&gt; m;
     * m.forEach(new UnaryPredicate&lt;String&gt;()
     *     {
     *         public boolean test(final String value)
     *         {
     *             return (value != null);
     *         }
     *     }, new UnaryProcedure&lt;String&gt;()
     *     {
     *         public void run(final String value)
     *         {
     *             System.out.println(value);
     *         }
     *     });
     * </pre></p>
     *
     * @param predicate predicate, must not be null
     * @param procedure procedure, must not be null
     */
    void forEach(UnaryPredicate<? super E> predicate,
                 UnaryProcedure<? super E> procedure);

    /**
     * Apply the specified procedure to each non-null value in this 1D matrix.
     *
     * <p>For example:
     * <pre>
     * Matrix1D&lt;String&gt; m;
     * m.forEachNonNull(new UnaryProcedure&lt;String&gt;()
     *     {
     *         public void run(final String value)
     *         {
     *             System.out.println(value);
     *         }
     *     });
     * </pre></p>
     *
     * @param procedure procedure, must not be null
     */
    void forEachNonNull(UnaryProcedure<? super E> procedure);

    /**
     * Apply the specified procedures to each index and value in this
     * 1D matrix.
     *
     * <p>For example:
     * <pre>
     * Matrix1D&lt;String&gt; m;
     * m.forEach(new BinaryProcedure&lt;Long, String&gt;()
     *     {
     *         public void run(final Long index, final String value)
     *         {
     *             System.out.print("m[" + index + "]=" + value);
     *         }
     *     });
     * </pre></p>
     *
     * @param procedure procedure, must not be null
     */
    void forEach(BinaryProcedure<Long, ? super E> procedure);

    /**
     * Apply the specified procedures to each index and value in this
     * 1D matrix accepted by the specified predicate.
     *
     * <p>For example:
     * <pre>
     * Matrix1D&lt;String&gt; m;
     * m.forEach(new BinaryPredicate&lt;Long, String&gt;()
     *     {
     *         public boolean test(final Long index, final String value)
     *         {
     *             return (value != null);
     *         }
     *     }, new BinaryProcedure&lt;Long, String&gt;()
     *     {
     *         public void run(final Long index, final String value)
     *         {
     *             System.out.print("m[" + index + "]=" + value);
     *         }
     *     });
     * </pre></p>
     *
     * @param predicate predicate, must not be null
     * @param procedure procedure, must not be null
     */
    void forEach(BinaryPredicate<Long, ? super E> predicate,
                 BinaryProcedure<Long, ? super E> procedure);
}
