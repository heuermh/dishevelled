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
package org.dishevelled.matrix.impl;

import org.dishevelled.matrix.AbstractMatrixUtilsTest;
import org.dishevelled.matrix.Matrix1D;
import org.dishevelled.matrix.Matrix2D;
import org.dishevelled.matrix.Matrix3D;

import static org.dishevelled.matrix.impl.SparseMatrixUtils.*;

/**
 * Unit test for MatrixUtils with sparse matrix implementations.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class SparseMatrixUtilsTest
    extends AbstractMatrixUtilsTest
{

    /** {@inheritDoc} */
    protected <T> Matrix1D<T> createMatrix1D()
    {
        return createSparseMatrix1D(100L);
    }

    /** {@inheritDoc} */
    protected <T> Matrix2D<T> createMatrix2D()
    {
        return createSparseMatrix2D(100L, 100L);
    }

    /** {@inheritDoc} */
    protected <T> Matrix3D<T> createMatrix3D()
    {
        return createSparseMatrix3D(100L, 100L, 100L);
    }

    public void testCreateSparseMatrix1D()
    {
        Matrix1D<String> matrix0 = createSparseMatrix1D(100L);
        assertNotNull(matrix0);
        assertEquals(100L, matrix0.size());

        Matrix1D<String> matrix1 = createSparseMatrix1D(100L, 10, 0.75f);
        assertNotNull(matrix1);
        assertEquals(100L, matrix1.size());
    }

    public void testCreateSparseMatrix2D()
    {
        Matrix2D<String> matrix0 = createSparseMatrix2D(100L, 100L);
        assertNotNull(matrix0);
        assertEquals(100L * 100L, matrix0.size());

        Matrix2D<String> matrix1 = createSparseMatrix2D(100L, 100L, 10, 0.75f);
        assertNotNull(matrix1);
        assertEquals(100L * 100L, matrix1.size());
    }

    public void testCreateSparseMatrix3D()
    {
        Matrix3D<String> matrix0 = createSparseMatrix3D(100L, 100L, 100L);
        assertNotNull(matrix0);
        assertEquals(100L * 100L * 100L, matrix0.size());

        Matrix3D<String> matrix1 = createSparseMatrix3D(100L, 100L, 100L, 10, 0.75f);
        assertNotNull(matrix1);
        assertEquals(100L * 100L * 100L, matrix1.size());
    }
}
