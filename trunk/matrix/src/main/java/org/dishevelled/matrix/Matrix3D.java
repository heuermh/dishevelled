/*

    dsh-matrix  long-addressable bit and typed object matrix implementations.
    Copyright (c) 2004-2013 held jointly by the individual authors.

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
import org.dishevelled.functor.QuaternaryPredicate;
import org.dishevelled.functor.QuaternaryProcedure;

/**
 * Typed fixed size matrix of objects in three dimensions, indexed
 * by <code>long</code>s.
 *
 * @param <E> type of this 3D matrix
 * @author  Michael Heuer
 */
public interface Matrix3D<E>
    extends Iterable<E>
{

    /**
     * Return the size of this 3D matrix.
     *
     * @return the size of this 3D matrix
     */
    long size();

    /**
     * Return the number of rows in this 3D matrix.
     *
     * @return the number of rows in this 3D matrix
     */
    long rows();

    /**
     * Return the number of columns in this 3D matrix.
     *
     * @return the number of columns in this 3D matrix
     */
    long columns();

    /**
     * Return the number of slices in this 3D matrix.
     *
     * @return the number of slices in this 3D matrix
     */
    long slices();

    /**
     * Return the cardinality of this 3D matrix, the number
     * of non-null values.
     *
     * @return the cardinality of this 3D matrix
     */
    long cardinality();

    /**
     * Return true if the cardinality of this 3D matrix is zero.
     *
     * @return true if the cardinality of this 3D matrix is zero
     */
    boolean isEmpty();

    /**
     * Clear all the values in this 3D matrix (optional operation).
     *
     * @throws UnsupportedOperationException if the <code>clear</code> operation
     *    is not supported by this 3D matrix
     */
    void clear();

    /**
     * Return the value at the specified slice, row, and column.
     *
     * @param slice slice index, must be <code>&gt;= 0</code> and <code>&lt; slices()</code>
     * @param row row index, must be <code>&gt;= 0</code> and <code>&lt; rows()</code>
     * @param column column index, must be <code>&gt;= 0</code> and <code>&lt; columns()</code>
     * @return the value at the specified slice, row, and column
     * @throws IndexOutOfBoundsException if any of <code>slice</code>, <code>row</code>, or
     *    <code>column</code> are negative or if any of <code>slice</code>, <code>row</code>,
     *    or <code>column</code> are greater than or equal to <code>slices()</code>,
     *    <code>rows()</code>, or <code>columns()</code>, respectively
     */
    E get(long slice, long row, long column);

    /**
     * Return the value at the specified slice, row, and column without
     * checking bounds.
     *
     * @param slice slice index, should be <code>&gt;= 0</code> and <code>&lt; slices()</code>
     *    (unchecked)
     * @param row row index, should be <code>&gt;= 0</code> and <code>&lt; rows()</code>
     *    (unchecked)
     * @param column column index, should be <code>&gt;= 0</code> and <code>&lt; columns()</code>
     *    (unchecked)
     * @return the value at the specified slice, row, and column without
     *    checking bounds
     */
    E getQuick(long slice, long row, long column);

    /**
     * Set the value at the specified slice, row, and column to <code>e</code>
     * (optional operation).
     *
     * @param slice slice index, must be <code>&gt;= 0</code> and <code>&lt; slices()</code>
     * @param row row index, must be <code>&gt;= 0</code> and <code>&lt; rows()</code>
     * @param column column index, must be <code>&gt;= 0</code> and <code>&lt; columns()</code>
     * @param e value
     * @throws IndexOutOfBoundsException if any of <code>slice</code>, <code>row</code>, or
     *    <code>column</code> are negative or if any of <code>slice</code>, <code>row</code>,
     *    or <code>column</code> are greater than or equal to <code>slices()</code>,
     *    <code>rows()</code>, or <code>columns()</code>, respectively
     * @throws UnsupportedOperationException if the <code>set</code> operation
     *    is not supported by this 3D matrix
     */
    void set(long slice, long row, long column, E e);

    /**
     * Set the value at the specified slice, row, and column to
     * <code>e</code> without checking bounds (optional operation).
     *
     * @param slice slice index, should be <code>&gt;= 0</code> and <code>&lt; slices()</code>
     *    (unchecked)
     * @param row row index, should be <code>&gt;= 0</code> and <code>&lt; rows()</code>
     *    (unchecked)
     * @param column column index, should be <code>&gt;= 0</code> and <code>&lt; columns()</code>
     *    (unchecked)
     * @param e value
     * @throws UnsupportedOperationException if the <code>setQuick</code> operation
     *    is not supported by this 3D matrix
     */
    void setQuick(long slice, long row, long column, E e);

    /**
     * Return an iterator over the values in this 3D matrix, including
     * <code>null</code>s.
     *
     * @return an iterator over the values in this 3D matrix, including
     *    <code>null</code>s
     */
    Iterator<E> iterator();

    /**
     * Assign all values in this 3D matrix to <code>e</code> (optional
     * operation).
     *
     * @param e value
     * @return this 3D matrix, for convenience
     * @throws UnsupportedOperationException if this <code>assign</code> operation
     *    is not supported by this 3D matrix
     */
    Matrix3D<E> assign(E e);

    /**
     * Assign the result of the specified function to each value
     * in this 3D matrix (optional operation).
     *
     * @param function function, must not be null
     * @return this 3D matrix, for convenience
     * @throws UnsupportedOperationException if this <code>assign</code> operation
     *    is not supported by this 3D matrix
     */
    Matrix3D<E> assign(UnaryFunction<E, E> function);

    /**
     * Assign all values in this 3D matrix to the values in the
     * specified matrix (optional operation).
     *
     * @param other other 3D matrix, must not be null and must
     *    have the same dimensions as this 3D matrix
     * @return this 3D matrix, for convenience
     * @throws UnsupportedOperationException if this <code>assign</code> operation
     *    is not supported by this 3D matrix
     */
    Matrix3D<E> assign(Matrix3D<? extends E> other);

    /**
     * Assign the result of the specified function of a value from
     * this 3D matrix and the specified matrix to each value in this
     * 3D matrix (optional operation).
     *
     * @param other other 3D matrix, must not be null and must
     *    have the same dimensions as this 3D matrix
     * @param function function, must not be null
     * @return this 3D matrix, for convenience
     * @throws UnsupportedOperationException if this <code>assign</code> operation
     *    is not supported by this 3D matrix
     */
    Matrix3D<E> assign(Matrix3D<? extends E> other,
                             BinaryFunction<E, E, E> function);

    /**
     * Apply a function to each value in this 3D matrix and aggregate
     * the result.
     *
     * @param aggr aggregate function, must not be null
     * @param function function, must not be null
     * @return the aggregate result
     */
    E aggregate(BinaryFunction<E, E, E> aggr, UnaryFunction<E, E> function);

    /**
     * Apply a function to each value in this 3D matrix and the specified
     * matrix and aggregate the result.
     *
     * @param other other 3D matrix, must not be null and must
     *    have the same dimensions as this 3D matrix
     * @param aggr aggregate function, must not be null
     * @param function function, must not be null
     * @return the aggregate result
     */
    E aggregate(Matrix3D<? extends E> other,
                BinaryFunction<E, E, E> aggr,
                BinaryFunction<E, E, E> function);

    /**
     * Return a new 2D matrix <i>slice view</i> of the specified slice.  The view
     * is backed by this matrix, so changes made to the returned view
     * are reflected in this matrix, and vice-versa.
     *
     * @param slice slice index, must be <code>&gt;= 0</code> and <code>&lt; slices()</code>
     * @return a new 2D matrix <i>slice view</i> of the specified slice
     */
    Matrix2D<E> viewSlice(long slice);

    /**
     * Return a new 2D matrix <i>slice view</i> of the specified row.  The view
     * is backed by this matrix, so changes made to the returned view
     * are reflected in this matrix, and vice-versa.
     *
     * @param row row index, must be <code>&gt;= 0</code> and <code>&lt; rows()</code>
     * @return a new 2D matrix <i>slice view</i> of the specified row
     */
    Matrix2D<E> viewRow(long row);

    /**
     * Return a new 2D matrix <i>slice view</i> of the specified column.  The view
     * is backed by this matrix, so changes made to the returned view
     * are reflected in this matrix, and vice-versa.
     *
     * @param column column index, must be <code>&gt;= 0</code> and <code>&lt; columns()</code>
     * @return a new 2D matrix <i>slice view</i> of the specified column
     */
    Matrix2D<E> viewColumn(long column);

    /**
     * Return a new 3D matrix <i>dice (transposition) view</i>.  The view has
     * dimensions exchanged; what used to be one axis is now another, in all
     * desired permutations.  The view is backed by this matrix, so changes
     * made to the returned view are reflected in this matrix, and vice-versa.
     *
     * @param axis0 the axis that shall become axis 0 (0 for slice, 1 for row, 2 for column)
     * @param axis1 the axis that shall become axis 0 (0 for slice, 1 for row, 2 for column)
     * @param axis2 the axis that shall become axis 0 (0 for slice, 1 for row, 2 for column)
     * @return a new 3D matrix <i>dice (transposition) view</i>
     * @throws IllegalArgumentException if any of the parameters are equal or
     *    if any of the parameters are not 0, 1, or 2.
     */
    Matrix3D<E> viewDice(int axis0, int axis1, int axis2);

    /**
     * Return a new 3D matrix <i>flip view</i> along the slice axis.
     * What used to be slice <code>0</code> is now slice <code>slices() - 1</code>,
     * ..., what used to be slice <code>slices() - 1</code> is now slice <code>0</code>.
     * The view is backed by this matrix, so changes made to the returned
     * view are reflected in this matrix, and vice-versa.
     *
     * @return a new 3D matrix <i>slice view</i> along the row axis
     */
    Matrix3D<E> viewSliceFlip();

    /**
     * Return a new 3D matrix <i>flip view</i> along the row axis.
     * What used to be row <code>0</code> is now row <code>rows() - 1</code>,
     * ..., what used to be row <code>rows() - 1</code> is now row <code>0</code>.
     * The view is backed by this matrix, so changes made to the returned
     * view are reflected in this matrix, and vice-versa.
     *
     * @return a new 3D matrix <i>flip view</i> along the row axis
     */
    Matrix3D<E> viewRowFlip();

    /**
     * Return a new 3D matrix <i>flip view</i> along the column axis.
     * What used to be column <code>0</code> is now column <code>columns() - 1</code>,
     * ..., what used to be column <code>columns() - 1</code> is now column
     * <code>0</code>.  The view is backed by this matrix, so changes made to the
     * returned view are reflected in this matrix, and vice-versa.
     *
     * @return a new 3D matrix <i>flip view</i> along the column axis
     */
    Matrix3D<E> viewColumnFlip();

    /**
     * Return a new 3D matrix <i>sub-range view</i> that contains only those values
     * from <code>(slice, row, column)</code> to
     * <code>(slice + depth - 1, row + height - 1, column + width - 1)</code>.
     * The view is backed by this matrix, so changes made to the returned
     * view are reflected in this matrix, and vice-versa.
     *
     * @param slice slice index, must be <code>&gt;= 0</code> and <code>&lt; slices()</code>
     * @param row row index, must be <code>&gt;= 0</code> and <code>&lt; rows()</code>
     * @param column column index, must be <code>&gt;= 0</code> and <code>&lt; columns()</code>
     * @param depth depth
     * @param height height
     * @param width width
     * @return a new 3D matrix <i>sub-range view</i> that contains only those values
     *    from <code>(slice, row, column)</code> to
     *    <code>(slice + depth - 1, row + height - 1, column + width - 1)</code>.
     */
    Matrix3D<E> viewPart(long slice, long row, long column,
                               long depth, long height, long width);

    /**
     * Return a new 3D matrix <i>selection view</i> that contains only those values
     * at the specified indices.  The view is backed by this matrix, so changes made
     * to the returned view are reflected in this matrix, and vice-versa.
     *
     * @param sliceIndices slice indices
     * @param rowIndices row indices
     * @param columnIndices column indices
     * @return a new 3D matrix <i>selection view</i> that contains only those values at the
     *    specified indices
     */
    Matrix3D<E> viewSelection(long[] sliceIndices, long[] rowIndices, long[] columnIndices);

    /**
     * Return a new 3D matrix <i>selection view</i> that contains only those <b>slices</b>
     * selected by the specified predicate.  The view is backed by this matrix, so changes made to
     * the returned view are reflected in this matrix, and vice-versa.
     *
     * @param predicate predicate, must not be null
     * @return a new 3D matrix <i>selection view</i> that contains only those slices
     *    selected by the specified predicate
     */
    Matrix3D<E> viewSelection(UnaryPredicate<Matrix2D<E>> predicate);

    /**
     * Return a new 3D matrix <i>selection view</i> that contains only those values
     * at the indices present in the specified bit mask.  The view is backed by this matrix, so
     * changes made to the returned view are reflected in this matrix, and vice-versa.
     *
     * @param mask 3D bit mask, must not be null
     * @return a new 3D matrix <i>selection view</i> that contains only those values
     *    at the indices present in the specified mask
     */
    Matrix3D<E> viewSelection(BitMatrix3D mask);

    /**
     * Return a new 3D matrix <i>stride view</i> which is a sub matrix consisting
     * of every <code>sliceStride</code>-th slice, every <code>rowStride</code>-th
     * row, and every <code>columnStride</code>-th column.  The view is backed by
     * this matrix, so changes made to the returned view are reflected in this
     * matrix, and vice-versa.
     *
     * @param sliceStride slice stride, must be <code>&gt; 0</code>
     * @param rowStride row stride, must be <code>&gt; 0</code>
     * @param columnStride column stride, must be <code>&gt; 0</code>
     * @return a new 3D matrix <i>stride view</i> which is a sub matrix consisting
     *    of every <code>sliceStride</code>-th slice, every <code>rowStride</code>-th
     *    row, and every <code>columnStride</code>-th column.
     * @throws IndexOutOfBoundsException if any of <code>sliceStride</code>,
     *    <code>rowStride</code>, or <code>columnStride</code> are negative or zero
     */
    Matrix3D<E> viewStrides(long sliceStride, long rowStride, long columnStride);

    /**
     * Apply the specified procedure to each value in this 3D matrix.
     *
     * <p>For example:
     * <pre>
     * Matrix3D&lt;String&gt; m;
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
     * Apply the specified procedure to each value in this 3D matrix
     * accepted by the specified predicate.
     *
     * <p>For example:
     * <pre>
     * Matrix3D&lt;String&gt; m;
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
     * Apply the specified procedure to each non-null value in this 3D matrix.
     *
     * <p>For example:
     * <pre>
     * Matrix3D&lt;String&gt; m;
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
     * Apply the specified procedure to each slice, row, and column and
     * to each value in this 3D matrix.
     *
     * <p>For example:
     * <pre>
     * Matrix3D&lt;String&gt; m;
     * m.forEach(new QuaternaryProcedure&lt;Long, Long, Long, String&gt;()
     *     {
     *         public void run(final Long slice, final Long row, final Long column, final String value)
     *         {
     *             System.out.print("m[" + slice + ", " + row + ", " + column + "]=" + value);
     *         }
     *     });
     * </pre></p>
     *
     * @param procedure procedure, must not be null
     */
    void forEach(QuaternaryProcedure<Long, Long, Long, ? super E> procedure);

    /**
     * Apply the specified procedure to each slice, row, and column
     * and to each value in this 3D matrix accepted by the specified predicate.
     *
     * <p>For example:
     * <pre>
     * Matrix3D&lt;String&gt; m;
     * m.forEach(new QuaternaryPredicate&lt;Long, Long, Long, String&gt;()
     *     {
     *         public boolean test(final Long slice, final Long row, final Long column, final String value)
     *         {
     *             return (value != null);
     *         }
     *     }, new QuaternaryProcedure&lt;Long, Long, Long, String&gt;()
     *     {
     *         public void run(final Long slice, final Long row, final Long column, final String value)
     *         {
     *             System.out.print("m[" + slice + ", " + row + ", " + column + "]=" + value);
     *         }
     *     });
     * </pre></p>
     *
     * @param predicate predicate, must not be null
     * @param procedure procedure, must not be null
     */
    void forEach(QuaternaryPredicate<Long, Long, Long, ? super E> predicate,
                 QuaternaryProcedure<Long, Long, Long, ? super E> procedure);
}
