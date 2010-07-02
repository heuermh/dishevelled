/*

    dsh-matrix-nonblocking  Non-blocking matrix implementations.
    Copyright (c) 2010 held jointly by the individual authors.

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
package org.dishevelled.matrix.impl.nonblocking;

import org.dishevelled.matrix.AbstractMatrixUtilsTest;
import org.dishevelled.matrix.Matrix1D;
import org.dishevelled.matrix.Matrix2D;
import org.dishevelled.matrix.Matrix3D;

import static org.dishevelled.matrix.impl.nonblocking.NonBlockingMatrixUtils.*;

/**
 * Unit test for NonBlockingMatrixUtils.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class NonBlockingMatrixUtilsTest
    extends AbstractMatrixUtilsTest
{

    /** {@inheritDoc} */
    protected <T> Matrix1D<T> createMatrix1D()
    {
        return createNonBlockingSparseMatrix1D(100L, 10);
    }

    /** {@inheritDoc} */
    protected <T> Matrix2D<T> createMatrix2D()
    {
        return createNonBlockingSparseMatrix2D(100L, 100L, 10);
    }

    /** {@inheritDoc} */
    protected <T> Matrix3D<T> createMatrix3D()
    {
        return createNonBlockingSparseMatrix3D(100L, 100L, 100L, 10);
    }

    public void testCreateNonBlockingSparseMatrix1D()
    {
        Matrix1D<String> matrix = createNonBlockingSparseMatrix1D(100L, 10);
        assertNotNull(matrix);
        assertEquals(100L, matrix.size());
    }

    public void testCreateNonBlockingSparseMatrix2D()
    {
        Matrix2D<String> matrix = createNonBlockingSparseMatrix2D(100L, 100L, 10);
        assertNotNull(matrix);
        assertEquals(100L * 100L, matrix.size());
    }

    public void testCreateNonBlockingSparseMatrix3D()
    {
        Matrix3D<String> matrix = createNonBlockingSparseMatrix3D(10L, 10L, 10L, 10);
        assertNotNull(matrix);
        assertEquals(10L * 10L * 10L, matrix.size());
    }
}