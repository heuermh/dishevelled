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

import junit.framework.TestCase;

import org.dishevelled.functor.BinaryFunction;
import org.dishevelled.functor.BinaryPredicate;
import org.dishevelled.functor.BinaryProcedure;
import org.dishevelled.functor.QuaternaryPredicate;
import org.dishevelled.functor.QuaternaryProcedure;
import org.dishevelled.functor.TernaryPredicate;
import org.dishevelled.functor.TernaryProcedure;
import org.dishevelled.functor.UnaryFunction;
import org.dishevelled.functor.UnaryPredicate;
import org.dishevelled.functor.UnaryProcedure;

/**
 * Abstract unit test for MatrixUtils.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractMatrixUtilsTest
    extends TestCase
{
    /** Binary function. */
    BinaryFunction<String, String, String> binaryFunction = new BinaryFunction<String, String, String>()
    {
        /** {@inheritDoc} */
        public String evaluate(final String string0, final String string1)
        {
            return "foo";
        }
    };

    /** Binary predicate. */
    BinaryPredicate<Long, String> binaryPredicate = new BinaryPredicate<Long, String>()
    {
        /** {@inheritDoc} */
        public boolean test(final Long index, final String string)
        {
            return true;
        }
    };

    /** Binary procedure. */
    BinaryProcedure<Long, String> binaryProcedure = new BinaryProcedure<Long, String>()
    {
        /** {@inheritDoc} */
        public void run(final Long index, final String string)
        {
            // empty
        }
    };

    /** Quaternary predicate. */
    QuaternaryPredicate<Long, Long, Long, String> quaternaryPredicate = new QuaternaryPredicate<Long, Long, Long, String>()
    {
        /** {@inheritDoc} */
        public boolean test(final Long slice, final Long row, final Long column, final String string)
        {
            return true;
        }
    };

    /** Quaternary procedure. */
    QuaternaryProcedure<Long, Long, Long, String> quaternaryProcedure = new QuaternaryProcedure<Long, Long, Long, String>()
    {
        /** {@inheritDoc} */
        public void run(final Long slice, final Long row, final Long column, final String string)
        {
            // empty
        }
    };

    /** Tertiary predicate. */
    TernaryPredicate<Long, Long, String> ternaryPredicate = new TernaryPredicate<Long, Long, String>()
    {
        /** {@inheritDoc} */
        public boolean test(final Long row, final Long column, final String string)
        {
            return true;
        }
    };

    /** Tertiary procedure. */
    TernaryProcedure<Long, Long, String> ternaryProcedure = new TernaryProcedure<Long, Long, String>()
    {
        /** {@inheritDoc} */
        public void run(final Long row, final Long column, final String string)
        {
            // empty
        }
    };

    /** Unary function. */
    UnaryFunction<String, String> unaryFunction = new UnaryFunction<String, String>()
    {
        /** {@inheritDoc} */
        public String evaluate(final String string)
        {
            return "foo";
        }
    };

    /** Unary predicate. */
    UnaryPredicate<String> unaryPredicate = new UnaryPredicate<String>()
    {
        /** {@inheritDoc} */
        public boolean test(final String string)
        {
            return true;
        }
    };

    /** Unary procedure. */
    UnaryProcedure<String> unaryProcedure = new UnaryProcedure<String>()
    {
        /** {@inheritDoc} */
        public void run(final String string)
        {
            // empty
        }
    };


    /**
     * Create and return a new instance of an implementation of Matrix1D<T> to test.
     *
     * @param <T> 1D object matrix type
     * @return a new instance of an implementation of Matrix1D<T> to test
     */
    protected abstract <T> Matrix1D<T> createMatrix1D();

    /**
     * Create and return a new instance of an implementation of Matrix2D<T> to test.
     *
     * @param <T> 2D object matrix type
     * @return a new instance of an implementation of Matrix2D<T> to test
     */
    protected abstract <T> Matrix2D<T> createMatrix2D();

    /**
     * Create and return a new instance of an implementation of Matrix3D<T> to test.
     *
     * @param <T> 3D object matrix type
     * @return a new instance of an implementation of Matrix3D<T> to test
     */
    protected abstract <T> Matrix3D<T> createMatrix3D();


    public void testUnmodifiableObjectMatrix1D()
    {
        Matrix1D<String> matrix = createMatrix1D();
        Matrix1D<String> other = createMatrix1D();
        Matrix1D<String> unmodifiableView = MatrixUtils.unmodifiableMatrix(matrix);
        assertNotNull("unmodifiableView not null", unmodifiableView);

        assertNotNull("aggregate(BinaryFunction, UnaryFunction) not null",
                      unmodifiableView.aggregate(binaryFunction, unaryFunction));

        assertNotNull("aggregate(Matrix1D, BinaryFunction, BinaryFunction) not null",
                      unmodifiableView.aggregate(other, binaryFunction, binaryFunction));

        assertEquals(matrix.cardinality(), unmodifiableView.cardinality());

        unmodifiableView.forEach(binaryPredicate, binaryProcedure);
        unmodifiableView.forEach(binaryProcedure);
        unmodifiableView.forEach(unaryPredicate, unaryProcedure);
        unmodifiableView.forEach(unaryProcedure);

        assertEquals(matrix.get(0L), unmodifiableView.get(0L));
        assertEquals(matrix.getQuick(0L), unmodifiableView.getQuick(0L));
        assertEquals(matrix.isEmpty(), unmodifiableView.isEmpty());

        assertNotNull("iterator not null", unmodifiableView.iterator());
        // todo:  assert iterator.delete() throws UnsupportedOperationException

        assertEquals(matrix.size(), unmodifiableView.size());

        assertNotNull("viewFlip not null", unmodifiableView.viewFlip());
        assertNotNull("viewPart not null", unmodifiableView.viewPart(0L, 1L));
        //assertNotNull("viewSelection(long[]) not null", unmodifiableView.viewSelection(new long[] { 0L }));
        //assertNotNull("viewSelection(UnaryPredicate) not null", unmodifiableView.viewSelection(unaryPredicate));
        assertNotNull("viewStrides not null", unmodifiableView.viewStrides(1L));
        // todo:  assert views are unmodifiable

        try
        {
            unmodifiableView.assign("foo");
            fail("assign(E) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            unmodifiableView.assign(other);
            fail("assign(Matrix1D) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            unmodifiableView.assign(other, binaryFunction);
            fail("assign(Matrix1D, BinaryFunction) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            unmodifiableView.assign(unaryFunction);
            fail("assign(UnaryFunction) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            unmodifiableView.clear();
            fail("clear() expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            unmodifiableView.set(0L, "foo");
            fail("set(Long, E) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            unmodifiableView.setQuick(0L, "foo");
            fail("setQuick(Long, E) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }

        try
        {
            MatrixUtils.unmodifiableMatrix((Matrix1D<String>) null);
            fail("unmodifiableObjectMatrix(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testUnmodifiableObjectMatrix2D()
    {
        Matrix2D<String> matrix = createMatrix2D();
        Matrix2D<String> other = createMatrix2D();
        Matrix2D<String> unmodifiableView = MatrixUtils.unmodifiableMatrix(matrix);
        assertNotNull("unmodifiableView not null", unmodifiableView);

        assertNotNull("aggregate(BinaryFunction, UnaryFunction) not null",
                      unmodifiableView.aggregate(binaryFunction, unaryFunction));

        assertNotNull("aggregate(Matrix2D, BinaryFunction, BinaryFunction) not null",
                      unmodifiableView.aggregate(other, binaryFunction, binaryFunction));

        assertEquals(matrix.cardinality(), unmodifiableView.cardinality());

        unmodifiableView.forEach(ternaryPredicate, ternaryProcedure);
        unmodifiableView.forEach(ternaryProcedure);
        unmodifiableView.forEach(unaryPredicate, unaryProcedure);
        unmodifiableView.forEach(unaryProcedure);

        assertEquals(matrix.get(0L, 0L), unmodifiableView.get(0L, 0L));
        assertEquals(matrix.getQuick(0L, 0L), unmodifiableView.getQuick(0L, 0L));
        assertEquals(matrix.isEmpty(), unmodifiableView.isEmpty());

        assertNotNull("iterator not null", unmodifiableView.iterator());
        // todo:  assert iterator.delete() throws UnsupportedOperationException

        assertEquals(matrix.size(), unmodifiableView.size());
        assertEquals(matrix.rows(), unmodifiableView.rows());
        assertEquals(matrix.columns(), unmodifiableView.columns());

        assertNotNull("viewColumn not null", unmodifiableView.viewColumn(0L));
        assertNotNull("viewColumnFlip not null", unmodifiableView.viewColumnFlip());
        assertNotNull("viewDice not null", unmodifiableView.viewDice());
        assertNotNull("viewPart not null", unmodifiableView.viewPart(0L, 1L, 2L, 3L));
        assertNotNull("viewRow not null", unmodifiableView.viewRow(0L));
        assertNotNull("viewRowFlip not null", unmodifiableView.viewRowFlip());
        //assertNotNull("viewSelection(long[]) not null", unmodifiableView.viewSelection(new long[] { 0L }));
        //assertNotNull("viewSelection(UnaryPredicate) not null", unmodifiableView.viewSelection(unaryPredicate));
        assertNotNull("viewStrides not null", unmodifiableView.viewStrides(1L, 2L));
        // todo:  assert views are unmodifiable

        try
        {
            unmodifiableView.assign("foo");
            fail("assign(E) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            unmodifiableView.assign(other);
            fail("assign(Matrix2D) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            unmodifiableView.assign(other, binaryFunction);
            fail("assign(Matrix2D, BinaryFunction) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            unmodifiableView.assign(unaryFunction);
            fail("assign(UnaryFunction) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            unmodifiableView.clear();
            fail("clear() expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            unmodifiableView.set(0L, 0L, "foo");
            fail("set(Long, Long, E) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            unmodifiableView.setQuick(0L, 0L, "foo");
            fail("setQuick(Long, Long, E) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }

        try
        {
            MatrixUtils.unmodifiableMatrix((Matrix2D<String>) null);
            fail("unmodifiableObjectMatrix(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testUnmodifiableObjectMatrix3D()
    {
        Matrix3D<String> matrix = createMatrix3D();
        Matrix3D<String> other = createMatrix3D();
        Matrix3D<String> unmodifiableView = MatrixUtils.unmodifiableMatrix(matrix);
        assertNotNull("unmodifiableView not null", unmodifiableView);

        assertNotNull("aggregate(BinaryFunction, UnaryFunction) not null",
                      unmodifiableView.aggregate(binaryFunction, unaryFunction));

        assertNotNull("aggregate(Matrix3D, BinaryFunction, BinaryFunction) not null",
                      unmodifiableView.aggregate(other, binaryFunction, binaryFunction));

        assertEquals(matrix.cardinality(), unmodifiableView.cardinality());

        unmodifiableView.forEach(quaternaryPredicate, quaternaryProcedure);
        unmodifiableView.forEach(quaternaryProcedure);
        unmodifiableView.forEach(unaryPredicate, unaryProcedure);
        unmodifiableView.forEach(unaryProcedure);

        assertEquals(matrix.get(0L, 0L, 0L), unmodifiableView.get(0L, 0L, 0L));
        assertEquals(matrix.getQuick(0L, 0L, 0L), unmodifiableView.getQuick(0L, 0L, 0L));
        assertEquals(matrix.isEmpty(), unmodifiableView.isEmpty());

        assertNotNull("iterator not null", unmodifiableView.iterator());
        // todo:  assert iterator.delete() throws UnsupportedOperationException

        assertEquals(matrix.size(), unmodifiableView.size());
        assertEquals(matrix.rows(), unmodifiableView.rows());
        assertEquals(matrix.columns(), unmodifiableView.columns());

        assertNotNull("viewColumn not null", unmodifiableView.viewColumn(0L));
        assertNotNull("viewColumnFlip not null", unmodifiableView.viewColumnFlip());
        assertNotNull("viewDice not null", unmodifiableView.viewDice(0, 1, 2));
        assertNotNull("viewPart not null", unmodifiableView.viewPart(0L, 1L, 2L, 3L, 4L, 5L));
        assertNotNull("viewRow not null", unmodifiableView.viewRow(0L));
        assertNotNull("viewRowFlip not null", unmodifiableView.viewRowFlip());
        //assertNotNull("viewSelection(long[]) not null", unmodifiableView.viewSelection(new long[] { 0L }));
        //assertNotNull("viewSelection(UnaryPredicate) not null", unmodifiableView.viewSelection(unaryPredicate));
        assertNotNull("viewSlice not null", unmodifiableView.viewSlice(0L));
        assertNotNull("viewSliceFlip not null", unmodifiableView.viewSliceFlip());
        assertNotNull("viewStrides not null", unmodifiableView.viewStrides(1L, 2L, 3L));
        // todo:  assert views are unmodifiable

        try
        {
            unmodifiableView.assign("foo");
            fail("assign(E) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            unmodifiableView.assign(other);
            fail("assign(Matrix3D) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            unmodifiableView.assign(other, binaryFunction);
            fail("assign(Matrix3D, BinaryFunction) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            unmodifiableView.assign(unaryFunction);
            fail("assign(UnaryFunction) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            unmodifiableView.clear();
            fail("clear() expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            unmodifiableView.set(0L, 0L, 0L, "foo");
            fail("set(Long, Long, Long, E) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            unmodifiableView.setQuick(0L, 0L, 0L, "foo");
            fail("setQuick(Long, Long, Long, E) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }

        try
        {
            MatrixUtils.unmodifiableMatrix((Matrix3D<String>) null);
            fail("unmodifiableObjectMatrix(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }
}
