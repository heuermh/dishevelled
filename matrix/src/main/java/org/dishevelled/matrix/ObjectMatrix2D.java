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
package org.dishevelled.matrix;

import java.util.Iterator;

import org.dishevelled.functor.UnaryFunction;
import org.dishevelled.functor.UnaryPredicate;
import org.dishevelled.functor.UnaryProcedure;
import org.dishevelled.functor.BinaryFunction;
import org.dishevelled.functor.TertiaryPredicate;
import org.dishevelled.functor.TertiaryProcedure;

/**
 * Typed fixed size matrix of objects in two dimensions, indexed
 * by <code>long</code>s.
 *
 * @param <E> type of this 2D matrix
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface ObjectMatrix2D<E>
    extends Iterable<E>
{

    /**
     * Return the size of this 2D matrix.
     *
     * @return the size of this 2D matrix
     */
    long size();

    /**
     * Return the number of rows in this 2D matrix.
     *
     * @return the number of rows in this 2D matrix
     */
    long rows();

    /**
     * Return the number of columns in this 2D matrix.
     *
     * @return the number of columns in this 2D matrix
     */
    long columns();

    /**
     * Return the cardinality of this 2D matrix, the number
     * of non-null values.
     *
     * @return the cardinality of this 2D matrix
     */
    long cardinality();

    /**
     * Return true if the cardinality of this 2D matrix is zero.
     *
     * @return true if the cardinality of this 2D matrix is zero
     */
    boolean isEmpty();

    /**
     * Clear all the values in this 2D matrix (optional operation).
     *
     * @throws UnsupportedOperationException if the <code>clear</code> operation
     *    is not supported by this 2D matrix
     */
    void clear();

    /**
     * Return the value at the specified row and column.
     *
     * @param row row index, must be <code>&gt;= 0</code> and <code>&lt; rows()</code>
     * @param column column index, must be <code>&gt;= 0</code> and <code>&lt; columns()</code>
     * @return the value at the specified row and column
     * @throws IndexOutOfBoundsException if <code>row</code> or <code>column</code>
     *    is negative or if <code>row</code> or <code>column</code> is greater than
     *    or equal to <code>rows()</code> or <code>columns()</code>, respectively
     */
    E get(long row, long column);

    /**
     * Return the value at the specified row and column without
     * checking bounds.
     *
     * @param row row index, should be <code>&gt;= 0</code> and <code>&lt; rows()</code>
     *    (unchecked)
     * @param column column index, should be <code>&gt;= 0</code> and <code>&lt; columns()</code>
     *    (unchecked)
     * @return the value at the specified row and column without
     *    checking bounds
     */
    E getQuick(long row, long column);

    /**
     * Set the value at the specified row and column to <code>e</code> (optional
     * operation).
     *
     * @param row row index, must be <code>&gt;= 0</code> and <code>&lt; rows()</code>
     * @param column column index, must be <code>&gt;= 0</code> and <code>&lt; columns()</code>
     * @param e value
     * @throws IndexOutOfBoundsException if <code>row</code> or <code>column</code>
     *    is negative or if <code>row</code> or <code>column</code> is greater than
     *    or equal to <code>rows()</code> or <code>columns()</code>, respectively
     * @throws UnsupportedOperationException if the <code>set</code> operation
     *    is not supported by this 2D matrix
     */
    void set(long row, long column, E e);

    /**
     * Set the value at the specified row and column to
     * <code>e</code> without checking bounds (optional operation).
     *
     * @param row row index, should be <code>&gt;= 0</code> and <code>&lt; rows()</code>
     *    (unchecked)
     * @param column column index, should be <code>&gt;= 0</code> and <code>&lt; columns()</code>
     *    (unchecked)
     * @param e value
     * @throws UnsupportedOperationException if the <code>setQuick</code> operation
     *    is not supported by this 2D matrix
     */
    void setQuick(long row, long column, E e);

    /**
     * Return an iterator over the values in this 2D matrix, including
     * <code>null</code>s.
     *
     * @return an iterator over the values in this 2D matrix, including
     *    <code>null</code>s
     */
    Iterator<E> iterator();

    /**
     * Assign all values in this 2D matrix to <code>e</code> (optional operation).
     *
     * @param e value
     * @return this 2D matrix, for convenience
     * @throws UnsupportedOperationException if this <code>assign</code> operation
     *    is not supported by this 2D matrix
     */
    ObjectMatrix2D<E> assign(E e);

    /**
     * Assign the result of the specified function to each value
     * in this 2D matrix (optional operation).
     *
     * @param function function, must not be null
     * @return this 2D matrix, for convenience
     * @throws UnsupportedOperationException if this <code>assign</code> operation
     *    is not supported by this 2D matrix
     */
    ObjectMatrix2D<E> assign(UnaryFunction<E, E> function);

    /**
     * Assign all values in this 2D matrix to the values in the
     * specified matrix (optional operation).
     *
     * @param other other 2D matrix, must not be null and must
     *    have the same dimensions as this 2D matrix
     * @return this 2D matrix, for convenience
     * @throws UnsupportedOperationException if this <code>assign</code> operation
     *    is not supported by this 2D matrix
     */
    ObjectMatrix2D<E> assign(ObjectMatrix2D<? extends E> other);

    /**
     * Assign the result of the specified function of a value from
     * this 2D matrix and the specified matrix to each value in this
     * 2D matrix (optional operation).
     *
     * @param other other 2D matrix, must not be null and must
     *    have the same dimensions as this 2D matrix
     * @param function function, must not be null
     * @return this 2D matrix, for convenience
     * @throws UnsupportedOperationException if this <code>assign</code> operation
     *    is not supported by this 2D matrix
     */
    ObjectMatrix2D<E> assign(ObjectMatrix2D<? extends E> other,
                             BinaryFunction<E, E, E> function);

    /**
     * Apply a function to each value in this 2D matrix and aggregate
     * the result.
     *
     * @param aggr aggregate function, must not be null
     * @param function function, must not be null
     * @return the aggregate result
     */
    E aggregate(BinaryFunction<E, E, E> aggr, UnaryFunction<E, E> function);

    /**
     * Apply a function to each value in this 2D matrix and the specifed
     * matrix and aggregate the result.
     *
     * @param other other 2D matrix, must not be null and must
     *    have the same dimensions as this 2D matrix
     * @param aggr aggregate function, must not be null
     * @param function function, must not be null
     * @return the aggregate result
     */
    E aggregate(ObjectMatrix2D<? extends E> other,
                BinaryFunction<E, E, E> aggr,
                BinaryFunction<E, E, E> function);

    /**
     * Return a new 1D matrix <i>slice view</i> of the specified row.  The view
     * is backed by this matrix, so changes made to the returned view
     * are reflected in this matrix, and vice-versa.
     *
     * @param row row index, must be <code>&gt;= 0</code> and <code>&lt; rows()</code>
     * @return a new 1D matrix <i>slice view</i> of the specified row
     * @throws IndexOutOfBoundsException if <code>row</code> is negative or if
     *    <code>row</code> is greater than or equal to <code>rows()</code>
     */
    ObjectMatrix1D<E> viewRow(long row);

    /**
     * Return a new 1D matrix <i>slice view</i> of the specified column.  The view
     * is backed by this matrix, so changes made to the returned view
     * are reflected in this matrix, and vice-versa.
     *
     * @param column column index, must be <code>&gt;= 0</code> and <code>&lt; columns()</code>
     * @return a new 1D matrix <i>slice view</i> of the specified column
     * @throws IndexOutOfBoundsException if <code>column</code> is negative or if
     *    <code>column</code> is greater than or equal to <code>columns()</code>
     */
    ObjectMatrix1D<E> viewColumn(long column);

    /**
     * Return a new 2D matrix <i>dice (transposition) view</i>.  The view has
     * both dimensions exchanged; what used to be columns become rows, what used
     * to be rows become columns.  In other words:
     * <code>view.get(row, column) == this.get(column, row)</code>.
     * The view is backed by this matrix, so changes made to the returned
     * view are reflected in this matrix, and vice-versa.
     *
     * @return a new 2D matrix <i>dice (transposition) view</i>
     */
    ObjectMatrix2D<E> viewDice();

    /**
     * Return a new 2D matrix <i>flip view</i> along the row axis.
     * What used to be row <code>0</code> is now row <code>rows() - 1</code>,
     * ..., what used to be row <code>rows() - 1</code> is now row <code>0</code>.
     * The view is backed by this matrix, so changes made to the returned
     * view are reflected in this matrix, and vice-versa.
     *
     * @return a new 2D matrix <i>flip view</i> along the row axis
     */
    ObjectMatrix2D<E> viewRowFlip();

    /**
     * Return a new 2D matrix <i>flip view</i> along the column axis.
     * What used to be column <code>0</code> is now column <code>columns() - 1</code>,
     * ..., what used to be column <code>columns() - 1</code> is now column
     * <code>0</code>.  The view is backed by this matrix, so changes made to the
     * returned view are reflected in this matrix, and vice-versa.
     *
     * @return a new 2D matrix <i>flip view</i> along the column axis
     */
    ObjectMatrix2D<E> viewColumnFlip();

    /**
     * Return a new 2D matrix <i>sub-range view</i> that contains only those values
     * from <code>(row, column)</code> to
     * <code>(row + height - 1, column + width - 1)</code>.
     * The view is backed by this matrix, so changes made to the returned
     * view are reflected in this matrix, and vice-versa.
     *
     * @param row row index, must be <code>&gt;= 0</code> and <code>&lt; rows()</code>
     * @param column column index, must be <code>&gt;= 0</code> and <code>&lt; columns()</code>
     * @param height height
     * @param width width
     * @return a new 2D matrix that contains only those values
     *    from <code>(row, column)</code> to
     *    <code>(row + height - 1, column + width - 1)</code>.
     */
    ObjectMatrix2D<E> viewPart(long row, long column, long height, long width);

    /**
     * Return a new 2D matrix <i>selection view</i> that contains only those values at the
     * specified indices.  The view is backed by this matrix, so changes made
     * to the returned view are reflected in this matrix, and vice-versa.
     *
     * @param rowIndices row indices
     * @param columnIndices column indices
     * @return a new 2D matrix <i>selection view</i> that contains only those values at the
     *    specified indices
     */
    ObjectMatrix2D<E> viewSelection(long[] rowIndices, long[] columnIndices);

    /**
     * Return a new 2D matrix <i>selection view</i> that contains only those <b>rows</b>
     * selected by the specified predicate.  The view is backed by this
     * matrix, so changes made to the returned view are reflected in
     * this matrix, and vice-versa.
     *
     * @param predicate predicate, must not be null
     * @return a new 2D matrix view that contains only those rows
     *    selected by the specified predicate
     */
    ObjectMatrix2D<E> viewSelection(UnaryPredicate<ObjectMatrix1D<E>> predicate);

    /**
     * Return a new 2D matrix <i>stride view</i> which is a sub matrix consisting
     * of every <code>rowStride</code>-th row and every <code>columnStride</code>-th
     * column.  The view is backed by this matrix, so changes made to the returned view
     * are reflected in this matrix, and vice-versa.
     *
     * @param rowStride row stride, must be <code>&gt; 0</code>
     * @param columnStride column stride, must be <code>&gt; 0</code>
     * @return a new 2D matrix <i>stride view</i> which is a sub matrix consisting
     *    of every <code>rowStride</code>-th row and every <code>columnStride</code>-th
     *    column
     * @throws IndexOutOfBoundsException if either <code>rowStride</code>
     *    or <code>columnStride</code> are negative or zero
     */
    ObjectMatrix2D<E> viewStrides(long rowStride, long columnStride);

    /**
     * Apply the specified procedure to each value in this 2D matrix.
     *
     * <p>For example:
     * <pre>
     * ObjectMatrix2D&lt;String&gt; m;
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
    void forEach(UnaryProcedure<E> procedure);

    /**
     * Apply the specified procedure to each value in this 2D matrix
     * accepted by the specified predicate.
     *
     * <p>For example:
     * <pre>
     * ObjectMatrix2D&lt;String&gt; m;
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
    void forEach(UnaryPredicate<E> predicate,
                 UnaryProcedure<E> procedure);

    /**
     * Apply the specified procedures to each row and column and
     * to each value in this 2D matrix.
     *
     * <p>For example:
     * <pre>
     * ObjectMatrix2D&lt;String&gt; m;
     * m.forEach(new TertiaryProcedure&lt;Long, Long, String&gt;()
     *     {
     *         public void run(final Long row, final Long column, final String value)
     *         {
     *             System.out.print("m[" + row + ", " + column + "]=" + value);
     *         }
     *     });
     * </pre></p>
     *
     * @param procedure procedure, must not be null
     */
    void forEach(TertiaryProcedure<Long, Long, E> procedure);

    /**
     * Apply the specified procedures to each row and column
     * and to each value in this 2D matrix accepted by the specified predicate.
     *
     * <p>For example:
     * <pre>
     * ObjectMatrix2D&lt;String&gt; m;
     * m.forEach(new TertiaryPredicate&lt;Long, Long, String&gt;()
     *     {
     *         public boolean test(final Long row, final Long column, final String value)
     *         {
     *             return (value != null);
     *         }
     *     }, new TertiaryProcedure&lt;Long, Long, String&gt;()
     *     {
     *         public void run(final Long row, final Long column, final String value)
     *         {
     *             System.out.print("m[" + row + ", " + column + "]=" + value);
     *         }
     *     });
     * </pre></p>
     *
     * @param predicate predicate, must not be null
     * @param procedure procedure, must not be null
     */
    void forEach(TertiaryPredicate<Long, Long, E> predicate,
                 TertiaryProcedure<Long, Long, E> procedure);
}
