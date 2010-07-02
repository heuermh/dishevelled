/*

    dsh-matrix-io-nonblocking  Non-blocking sparse matrix readers.
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
package org.dishevelled.matrix.io.impl.nonblocking;

import org.dishevelled.matrix.Matrix2D;

import org.dishevelled.matrix.impl.nonblocking.NonBlockingSparseMatrix2D;

import org.dishevelled.matrix.io.impl.AbstractMatrixMarketReader;

// todo:  maybe nonblocking should move to matrix.impl.nonblocking

/**
 * Matrix Market format reader for non-blocking sparse matrices of doubles in two dimensions.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class NonBlockingSparseMatrixMarketReader
    extends AbstractMatrixMarketReader
{

    /** {@inheritDoc} */
    protected Matrix2D<Double> createMatrix2D(final long rows, final long columns, final int cardinality)
    {
        return new NonBlockingSparseMatrix2D<Double>(rows, columns, cardinality);
    }
}