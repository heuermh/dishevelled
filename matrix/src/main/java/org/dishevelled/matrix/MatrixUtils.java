/*

    dsh-matrix  long-addressable bit and typed object matrix implementations.
    Copyright (c) 2004-2010 held jointly by the individual authors.

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

import org.dishevelled.functor.BinaryFunction;
import org.dishevelled.functor.BinaryPredicate;
import org.dishevelled.functor.BinaryProcedure;
import org.dishevelled.functor.QuaternaryPredicate;
import org.dishevelled.functor.QuaternaryProcedure;
import org.dishevelled.functor.TertiaryPredicate;
import org.dishevelled.functor.TertiaryProcedure;
import org.dishevelled.functor.UnaryFunction;
import org.dishevelled.functor.UnaryPredicate;
import org.dishevelled.functor.UnaryProcedure;

/**
 * Static utility methods on matrices.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class MatrixUtils
{

    /**
     * Private constructor.
     */
    private MatrixUtils()
    {
        // empty
    }


    /**
     * Return an unmodifiable view of the specified 1D matrix.  Query operations
     * on the returned 1D matrix read through to the specified matrix, and any attempt
     * to modify the returned 1D matrix, whether directly or via its iterator, will result
     * in an <code>UnsupportedOperationException</code>.
     *
     * @param <T> 1D matrix type
     * @param matrix 1D matrix to view, must not be null
     * @return an unmodifiable view of the specified 1D matrix
     */
    public static <T> Matrix1D<T> unmodifiableMatrix(final Matrix1D<T> matrix)
    {
        return new UnmodifiableMatrix1D<T>(matrix);
    }

    /**
     * Return an unmodifiable view of the specified 2D matrix.  Query operations
     * on the returned 2D matrix read through to the specified matrix, and any attempt
     * to modify the returned 2D matrix, whether directly or via its iterator, will result
     * in an <code>UnsupportedOperationException</code>.
     *
     * @param <T> 2D matrix type
     * @param matrix 2D matrix to view, must not be null
     * @return an unmodifiable view of the specified 2D matrix
     */
    public static <T> Matrix2D<T> unmodifiableMatrix(final Matrix2D<T> matrix)
    {
        return new UnmodifiableMatrix2D<T>(matrix);
    }

    /**
     * Return an unmodifiable view of the specified 3D matrix.  Query operations
     * on the returned 3D matrix read through to the specified matrix, and any attempt
     * to modify the returned 3D matrix, whether directly or via its iterator, will result
     * in an <code>UnsupportedOperationException</code>.
     *
     * @param <T> 3D matrix type
     * @param matrix 3D matrix to view, must not be null
     * @return an unmodifiable view of the specified 3D matrix
     */
    public static <T> Matrix3D<T> unmodifiableMatrix(final Matrix3D<T> matrix)
    {
        return new UnmodifiableMatrix3D<T>(matrix);
    }


    /**
     * Unmodifiable 1D matrix view.
     */
    private static class UnmodifiableMatrix1D<E>
        implements Matrix1D<E>
    {
        /** Wrapped 1D matrix. */
        private final Matrix1D<E> matrix;


        /**
         * Create a new unmodifiable 1D matrix view for the
         * specified 1D matrix.
         *
         * @param matrix 1D object matrix to view, must not be null
         */
        UnmodifiableMatrix1D(final Matrix1D<E> matrix)
        {
            if (matrix == null)
            {
                throw new IllegalArgumentException("matrix must not be null");
            }
            this.matrix = matrix;
        }


        /** {@inheritDoc} */
        public E aggregate(final BinaryFunction<E, E, E> aggr,
                           final UnaryFunction<E, E> function)
        {
            return matrix.aggregate(aggr, function);
        }

        /** {@inheritDoc} */
        public E aggregate(final Matrix1D<? extends E> other,
                           final BinaryFunction<E, E, E> aggr,
                           final BinaryFunction<E, E, E> function)
        {
            return matrix.aggregate(other, aggr, function);
        }

        /** {@inheritDoc} */
        public Matrix1D<E> assign(final E e)
        {
            throw new UnsupportedOperationException("assign operation not supported by this 1D matrix");
        }

        /** {@inheritDoc} */
        public Matrix1D<E> assign(final Matrix1D<? extends E> other)
        {
            throw new UnsupportedOperationException("assign operation not supported by this 1D matrix");
        }

        /** {@inheritDoc} */
        public Matrix1D<E> assign(final Matrix1D<? extends E> other,
                                        final BinaryFunction<E, E, E> function)
        {
            throw new UnsupportedOperationException("assign operation not supported by this 1D matrix");
        }

        /** {@inheritDoc} */
        public Matrix1D<E> assign(final UnaryFunction<E, E> function)
        {
            throw new UnsupportedOperationException("assign operation not supported by this 1D matrix");
        }

        /** {@inheritDoc} */
        public long cardinality()
        {
            return matrix.cardinality();
        }

        /** {@inheritDoc} */
        public void clear()
        {
            throw new UnsupportedOperationException("clear operation not supported by this 1D matrix");
        }

        /** {@inheritDoc} */
        public void forEach(final BinaryPredicate<Long, ? super E> predicate,
                            final BinaryProcedure<Long, ? super E> procedure)
        {
            matrix.forEach(predicate, procedure);
        }

        /** {@inheritDoc} */
        public void forEach(final BinaryProcedure<Long, ? super E> procedure)
        {
            matrix.forEach(procedure);
        }

        /** {@inheritDoc} */
        public void forEach(final UnaryPredicate<? super E> predicate,
                            final UnaryProcedure<? super E> procedure)
        {
            matrix.forEach(predicate, procedure);
        }

        /** {@inheritDoc} */
        public void forEach(final UnaryProcedure<? super E> procedure)
        {
            matrix.forEach(procedure);
        }

        /** {@inheritDoc} */
        public void forEachNonNull(final UnaryProcedure<? super E> procedure)
        {
            matrix.forEach(procedure);
        }

        /** {@inheritDoc} */
        public E get(final long index)
        {
            return matrix.get(index);
        }

        /** {@inheritDoc} */
        public E getQuick(final long index)
        {
            return matrix.getQuick(index);
        }

        /** {@inheritDoc} */
        public boolean isEmpty()
        {
            return matrix.isEmpty();
        }

        /** {@inheritDoc} */
        public Iterator<E> iterator()
        {
            // todo:  wrap iterator and block remove()
            return matrix.iterator();
        }

        /** {@inheritDoc} */
        public void set(final long index, final E e)
        {
            throw new UnsupportedOperationException("set operation not supported by this 1D matrix");
        }

        /** {@inheritDoc} */
        public void setQuick(final long index, final E e)
        {
            throw new UnsupportedOperationException("setQuick operation not supported by this 1D matrix");
        }

        /** {@inheritDoc} */
        public long size()
        {
            return matrix.size();
        }

        /** {@inheritDoc} */
        public Matrix1D<E> viewFlip()
        {
            Matrix1D<E> flip = matrix.viewFlip();
            return MatrixUtils.unmodifiableMatrix(flip);
        }

        /** {@inheritDoc} */
        public Matrix1D<E> viewPart(final long index, final long width)
        {
            Matrix1D<E> part = matrix.viewPart(index, width);
            return MatrixUtils.unmodifiableMatrix(part);
        }

        /** {@inheritDoc} */
        public Matrix1D<E> viewSelection(final long[] indices)
        {
            Matrix1D<E> selection = matrix.viewSelection(indices);
            return MatrixUtils.unmodifiableMatrix(selection);
        }

        /** {@inheritDoc} */
        public Matrix1D<E> viewSelection(final UnaryPredicate<E> predicate)
        {
            Matrix1D<E> selection = matrix.viewSelection(predicate);
            return MatrixUtils.unmodifiableMatrix(selection);
        }

        /** {@inheritDoc} */
        public Matrix1D<E> viewSelection(final BitMatrix1D mask)
        {
            Matrix1D<E> selection = matrix.viewSelection(mask);
            return MatrixUtils.unmodifiableMatrix(selection);
        }

        /** {@inheritDoc} */
        public Matrix1D<E> viewStrides(final long stride)
        {
            Matrix1D<E> strides = matrix.viewStrides(stride);
            return MatrixUtils.unmodifiableMatrix(strides);
        }
    }

    /**
     * Unmodifiable 2D matrix view.
     */
    private static class UnmodifiableMatrix2D<E>
        implements Matrix2D<E>
    {
        /** Wrapped 2D matrix. */
        private final Matrix2D<E> matrix;


        /**
         * Create a new unmodifiable 2D matrix view for the
         * specified 2D matrix.
         *
         * @param matrix 2D matrix to view, must not be null
         */
        UnmodifiableMatrix2D(final Matrix2D<E> matrix)
        {
            if (matrix == null)
            {
                throw new IllegalArgumentException("matrix must not be null");
            }
            this.matrix = matrix;
        }


        /** {@inheritDoc} */
        public E aggregate(final BinaryFunction<E, E, E> aggr,
                           final UnaryFunction<E, E> function)
        {
            return matrix.aggregate(aggr, function);
        }

        /** {@inheritDoc} */
        public E aggregate(final Matrix2D<? extends E> other,
                           final BinaryFunction<E, E, E> aggr,
                           final BinaryFunction<E, E, E> function)
        {
            return matrix.aggregate(other, aggr, function);
        }

        /** {@inheritDoc} */
        public Matrix2D<E> assign(final E e)
        {
            throw new UnsupportedOperationException("assign operation not supported by this 2D matrix");
        }

        /** {@inheritDoc} */
        public Matrix2D<E> assign(final Matrix2D<? extends E> other)
        {
            throw new UnsupportedOperationException("assign operation not supported by this 2D matrix");
        }

        /** {@inheritDoc} */
        public Matrix2D<E> assign(final Matrix2D<? extends E> other,
                                        final BinaryFunction<E, E, E> function)
        {
            throw new UnsupportedOperationException("assign operation not supported by this 2D matrix");
        }

        /** {@inheritDoc} */
        public Matrix2D<E> assign(final UnaryFunction<E, E> function)
        {
            throw new UnsupportedOperationException("assign operation not supported by this 2D matrix");
        }

        /** {@inheritDoc} */
        public long cardinality()
        {
            return matrix.cardinality();
        }

        /** {@inheritDoc} */
        public void clear()
        {
            throw new UnsupportedOperationException("clear operation not supported by this 2D matrix");
        }

        /** {@inheritDoc} */
        public long columns()
        {
            return matrix.columns();
        }

        /** {@inheritDoc} */
        public void forEach(final TertiaryPredicate<Long, Long, ? super E> predicate,
                            final TertiaryProcedure<Long, Long, ? super E> procedure)
        {
            matrix.forEach(predicate, procedure);
        }

        /** {@inheritDoc} */
        public void forEach(final TertiaryProcedure<Long, Long, ? super E> procedure)
        {
            matrix.forEach(procedure);
        }

        /** {@inheritDoc} */
        public void forEachNonNull(final UnaryProcedure<? super E> procedure)
        {
            matrix.forEachNonNull(procedure);
        }

        /** {@inheritDoc} */
        public void forEach(final UnaryPredicate<? super E> predicate,
                            final UnaryProcedure<? super E> procedure)
        {
            matrix.forEach(predicate, procedure);
        }

        /** {@inheritDoc} */
        public void forEach(final UnaryProcedure<? super E> procedure)
        {
            matrix.forEach(procedure);
        }

        /** {@inheritDoc} */
        public E get(final long row, final long column)
        {
            return matrix.get(row, column);
        }

        /** {@inheritDoc} */
        public E getQuick(final long row, final long column)
        {
            return matrix.getQuick(row, column);
        }

        /** {@inheritDoc} */
        public boolean isEmpty()
        {
            return matrix.isEmpty();
        }

        /** {@inheritDoc} */
        public Iterator<E> iterator()
        {
            // todo:  wrap iterator and block remove()
            return matrix.iterator();
        }

        /** {@inheritDoc} */
        public long rows()
        {
            return matrix.rows();
        }

        /** {@inheritDoc} */
        public void set(final long row, final long column, final E e)
        {
            throw new UnsupportedOperationException("set operation not supported by this 2D matrix");
        }

        /** {@inheritDoc} */
        public void setQuick(final long row, final long column, final E e)
        {
            throw new UnsupportedOperationException("setQuick operation not supported by this 2D matrix");
        }

        /** {@inheritDoc} */
        public long size()
        {
            return matrix.size();
        }

        /** {@inheritDoc} */
        public Matrix1D<E> viewColumn(final long column)
        {
            Matrix1D<E> columnView = matrix.viewColumn(column);
            return MatrixUtils.unmodifiableMatrix(columnView);
        }

        /** {@inheritDoc} */
        public Matrix2D<E> viewColumnFlip()
        {
            Matrix2D<E> columnFlip = matrix.viewColumnFlip();
            return MatrixUtils.unmodifiableMatrix(columnFlip);
        }

        /** {@inheritDoc} */
        public Matrix2D<E> viewDice()
        {
            Matrix2D<E> dice = matrix.viewDice();
            return MatrixUtils.unmodifiableMatrix(dice);
        }

        /** {@inheritDoc} */
        public Matrix2D<E> viewPart(final long row,
                                          final long column,
                                          final long height,
                                          final long width)
        {
            Matrix2D<E> part = matrix.viewPart(row, column, height, width);
            return MatrixUtils.unmodifiableMatrix(part);
        }

        /** {@inheritDoc} */
        public Matrix1D<E> viewRow(final long row)
        {
            Matrix1D<E> rowView = matrix.viewRow(row);
            return MatrixUtils.unmodifiableMatrix(rowView);
        }

        /** {@inheritDoc} */
        public Matrix2D<E> viewRowFlip()
        {
            Matrix2D<E> rowFlip = matrix.viewRowFlip();
            return MatrixUtils.unmodifiableMatrix(rowFlip);
        }

        /** {@inheritDoc} */
        public Matrix2D<E> viewSelection(final long[] rowIndices,
                                               final long[] columnIndices)
        {
            Matrix2D<E> selection = matrix.viewSelection(rowIndices, columnIndices);
            return MatrixUtils.unmodifiableMatrix(selection);
        }

        /** {@inheritDoc} */
        public Matrix2D<E> viewSelection(final UnaryPredicate<Matrix1D<E>> predicate)
        {
            Matrix2D<E> selection = matrix.viewSelection(predicate);
            return MatrixUtils.unmodifiableMatrix(selection);
        }

        /** {@inheritDoc} */
        public Matrix2D<E> viewSelection(final BitMatrix2D mask)
        {
            Matrix2D<E> selection = matrix.viewSelection(mask);
            return MatrixUtils.unmodifiableMatrix(selection);
        }

        /** {@inheritDoc} */
        public Matrix2D<E> viewStrides(final long rowStride, final long columnStride)
        {
            Matrix2D<E> strides = matrix.viewStrides(rowStride, columnStride);
            return MatrixUtils.unmodifiableMatrix(strides);
        }
    }

    /**
     * Unmodifiable 3D matrix view.
     */
    private static class UnmodifiableMatrix3D<E>
        implements Matrix3D<E>
    {
        /** Wrapped 3D matrix. */
        private final Matrix3D<E> matrix;


        /**
         * Create a new unmodifiable 3D matrix view for the
         * specified 3D matrix.
         *
         * @param matrix 3D matrix to view, must not be null
         */
        UnmodifiableMatrix3D(final Matrix3D<E> matrix)
        {
            if (matrix == null)
            {
                throw new IllegalArgumentException("matrix must not be null");
            }
            this.matrix = matrix;
        }


        /** {@inheritDoc} */
        public E aggregate(final BinaryFunction<E, E, E> aggr,
                           final UnaryFunction<E, E> function)
        {
            return matrix.aggregate(aggr, function);
        }

        /** {@inheritDoc} */
        public E aggregate(final Matrix3D<? extends E> other,
                           final BinaryFunction<E, E, E> aggr,
                           final BinaryFunction<E, E, E> function)
        {
            return matrix.aggregate(other, aggr, function);
        }

        /** {@inheritDoc} */
        public Matrix3D<E> assign(final E e)
        {
            throw new UnsupportedOperationException("assign operation not supported by this 3D matrix");
        }

        /** {@inheritDoc} */
        public Matrix3D<E> assign(final Matrix3D<? extends E> other)
        {
            throw new UnsupportedOperationException("assign operation not supported by this 3D matrix");
        }

        /** {@inheritDoc} */
        public Matrix3D<E> assign(final Matrix3D<? extends E> other,
                                        final BinaryFunction<E, E, E> function)
        {
            throw new UnsupportedOperationException("assign operation not supported by this 3D matrix");
        }

        /** {@inheritDoc} */
        public Matrix3D<E> assign(final UnaryFunction<E, E> function)
        {
            throw new UnsupportedOperationException("assign operation not supported by this 3D matrix");
        }

        /** {@inheritDoc} */
        public long cardinality()
        {
            return matrix.cardinality();
        }

        /** {@inheritDoc} */
        public void clear()
        {
            throw new UnsupportedOperationException("clear operation not supported by this 3D matrix");
        }

        /** {@inheritDoc} */
        public long columns()
        {
            return matrix.columns();
        }

        /** {@inheritDoc} */
        public void forEach(final QuaternaryPredicate<Long, Long, Long, ? super E> predicate,
                            final QuaternaryProcedure<Long, Long, Long, ? super E> procedure)
        {
            matrix.forEach(predicate, procedure);
        }

        /** {@inheritDoc} */
        public void forEach(final QuaternaryProcedure<Long, Long, Long, ? super E> procedure)
        {
            matrix.forEach(procedure);
        }

        /** {@inheritDoc} */
        public void forEachNonNull(final UnaryProcedure<? super E> procedure)
        {
            matrix.forEachNonNull(procedure);
        }

        /** {@inheritDoc} */
        public void forEach(final UnaryPredicate<? super E> predicate,
                            final UnaryProcedure<? super E> procedure)
        {
            matrix.forEach(predicate, procedure);
        }

        /** {@inheritDoc} */
        public void forEach(final UnaryProcedure<? super E> procedure)
        {
            matrix.forEach(procedure);
        }

        /** {@inheritDoc} */
        public E get(final long slice, final long row, final long column)
        {
            return matrix.get(slice, row, column);
        }

        /** {@inheritDoc} */
        public E getQuick(final long slice, final long row, final long column)
        {
            return matrix.getQuick(slice, row, column);
        }

        /** {@inheritDoc} */
        public boolean isEmpty()
        {
            return matrix.isEmpty();
        }

        /** {@inheritDoc} */
        public Iterator<E> iterator()
        {
            // todo:  wrap iterator and block remove()
            return matrix.iterator();
        }

        /** {@inheritDoc} */
        public long rows()
        {
            return matrix.rows();
        }

        /** {@inheritDoc} */
        public void set(final long slice, final long row, final long column, final E e)
        {
            throw new UnsupportedOperationException("set operation not supported by this 3D matrix");
        }

        /** {@inheritDoc} */
        public void setQuick(final long slice, final long row, final long column, final E e)
        {
            throw new UnsupportedOperationException("setQuick operation not supported by this 3D matrix");
        }

        /** {@inheritDoc} */
        public long size()
        {
            return matrix.size();
        }

        /** {@inheritDoc} */
        public long slices()
        {
            return matrix.slices();
        }

        /** {@inheritDoc} */
        public Matrix2D<E> viewColumn(final long column)
        {
            Matrix2D<E> columnView = matrix.viewColumn(column);
            return MatrixUtils.unmodifiableMatrix(columnView);
        }

        /** {@inheritDoc} */
        public Matrix3D<E> viewColumnFlip()
        {
            Matrix3D<E> columnFlip = matrix.viewColumnFlip();
            return MatrixUtils.unmodifiableMatrix(columnFlip);
        }

        /** {@inheritDoc} */
        public Matrix3D<E> viewDice(final int axis0, final int axis1, final int axis2)
        {
            Matrix3D<E> dice = matrix.viewDice(axis0, axis1, axis2);
            return MatrixUtils.unmodifiableMatrix(dice);
        }

        /** {@inheritDoc} */
        public Matrix3D<E> viewPart(final long slice,
                                          final long row,
                                          final long column,
                                          final long depth,
                                          final long height,
                                          final long width)
        {
            Matrix3D<E> part = matrix.viewPart(slice, row, column, depth, height, width);
            return MatrixUtils.unmodifiableMatrix(part);
        }

        /** {@inheritDoc} */
        public Matrix2D<E> viewRow(final long row)
        {
            Matrix2D<E> rowView = matrix.viewColumn(row);
            return MatrixUtils.unmodifiableMatrix(rowView);
        }

        /** {@inheritDoc} */
        public Matrix3D<E> viewRowFlip()
        {
            Matrix3D<E> rowFlip = matrix.viewRowFlip();
            return MatrixUtils.unmodifiableMatrix(rowFlip);
        }

        /** {@inheritDoc} */
        public Matrix3D<E> viewSelection(final long[] sliceIndices,
                                               final long[] rowIndices,
                                               final long[] columnIndices)
        {
            Matrix3D<E> selection = matrix.viewSelection(sliceIndices, rowIndices, columnIndices);
            return MatrixUtils.unmodifiableMatrix(selection);
        }

        /** {@inheritDoc} */
        public Matrix3D<E> viewSelection(final UnaryPredicate<Matrix2D<E>> predicate)
        {
            Matrix3D<E> selection = matrix.viewSelection(predicate);
            return MatrixUtils.unmodifiableMatrix(selection);
        }

        /** {@inheritDoc} */
        public Matrix3D<E> viewSelection(final BitMatrix3D mask)
        {
            Matrix3D<E> selection = matrix.viewSelection(mask);
            return MatrixUtils.unmodifiableMatrix(selection);
        }

        /** {@inheritDoc} */
        public Matrix2D<E> viewSlice(final long slice)
        {
            Matrix2D<E> sliceView = matrix.viewColumn(slice);
            return MatrixUtils.unmodifiableMatrix(sliceView);
        }

        /** {@inheritDoc} */
        public Matrix3D<E> viewSliceFlip()
        {
            Matrix3D<E> sliceFlip = matrix.viewSliceFlip();
            return MatrixUtils.unmodifiableMatrix(sliceFlip);
        }

        /** {@inheritDoc} */
        public Matrix3D<E> viewStrides(final long sliceStride,
                                             final long rowStride,
                                             final long columnStride)
        {
            Matrix3D<E> strides = matrix.viewStrides(sliceStride, rowStride, columnStride);
            return MatrixUtils.unmodifiableMatrix(strides);
        }
    }
}
