/*

    dsh-matrix-io  Matrix readers and writers.
    Copyright (c) 2008-2013 held jointly by the individual authors.

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

import java.io.IOException;

import org.dishevelled.functor.BinaryPredicate;
import org.dishevelled.functor.BinaryProcedure;

import org.dishevelled.matrix.Matrix1D;

/**
 * Tab-delimited text writer for matrices of objects in one dimension.
 * The first line is the size of the matrix as <code>size\tcardinality</code>
 * and each additional line is formatted as <code>index\tvalue</code>.
 *
 * @param <E> 1D matrix element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class TextMatrix1DWriter<E>
    extends AbstractMatrix1DWriter<E>
{

    /** {@inheritDoc} */
    public <T extends Appendable> T append(final Matrix1D<? extends E> matrix, final T appendable)
        throws IOException
    {
        if (matrix == null)
        {
            throw new IllegalArgumentException("matrix must not be null");
        }
        if (appendable == null)
        {
            throw new IllegalArgumentException("appendable must not be null");
        }
        // first line is size\tcardinality
        appendable.append(String.valueOf(matrix.size()));
        appendable.append('\t');
        appendable.append(String.valueOf(matrix.cardinality()));
        appendable.append('\n');
        matrix.forEach(new BinaryPredicate<Long, E>()
        {
            /** {@inheritDoc} */
            public boolean test(final Long index, final E value)
            {
                return (value != null);
            }
        }, new BinaryProcedure<Long, E>()
        {
            /** {@inheritDoc} */
            public void run(final Long index, final E value)
            {
                try
                {
                    appendable.append(String.valueOf(index));
                    appendable.append('\t');
                    appendable.append(MatrixIOUtils.toCharSequence(value));
                    appendable.append('\n');
                }
                catch (IOException e)
                {
                    // todo: rethrow outside of inner class
                }
            }
        });
        return appendable;
    }
}