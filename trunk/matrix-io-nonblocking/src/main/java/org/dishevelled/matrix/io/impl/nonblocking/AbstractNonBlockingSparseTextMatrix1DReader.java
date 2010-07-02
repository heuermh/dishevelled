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

import org.dishevelled.matrix.Matrix1D;

import org.dishevelled.matrix.impl.nonblocking.NonBlockingSparseMatrix1D;

import org.dishevelled.matrix.io.impl.AbstractTextMatrix1DReader;

/**
 * Abstract tab-delimited text reader for non-blocking sparse matrices of objects in one dimension.
 *
 * @param <E> 1D matrix element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractNonBlockingSparseTextMatrix1DReader<E>
    extends AbstractTextMatrix1DReader<E>
{

    /** {@inheritDoc} */
    protected final Matrix1D<E> createMatrix1D(final long size, final int cardinality)
    {
        return new NonBlockingSparseMatrix1D<E>(size, cardinality);
    }
}
