/*

    dsh-matrix-io  Matrix readers and writers.
    Copyright (c) 2008-2010 held jointly by the individual authors.

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
package org.dishevelled.matrix.io.impl;

import org.dishevelled.matrix.Matrix2D;

import org.dishevelled.matrix.impl.SparseMatrix2D;

/**
 * Abstract sparse tab-delimited text reader for matrices of objects in two dimensions.
 *
 * @param <E> 2D matrix element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractSparseTextMatrix2DReader<E>
    extends AbstractTextMatrix2DReader<E>
{

    /** {@inheritDoc} */
    protected final Matrix2D<E> createMatrix2D(final long rows, final long columns, final int cardinality)
    {
        return new SparseMatrix2D<E>(rows, columns, cardinality, 0.75f);
    }
}
