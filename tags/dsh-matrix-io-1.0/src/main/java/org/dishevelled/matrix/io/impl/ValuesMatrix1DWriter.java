/*

    dsh-matrix-io  Matrix readers and writers.
    Copyright (c) 2008-2012 held jointly by the individual authors.

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

import java.util.Iterator;

import org.dishevelled.matrix.Matrix1D;

/**
 * Values writer for matrices of objects in one dimension.
 *
 * @param <E> 1D matrix element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class ValuesMatrix1DWriter<E>
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
        appendable.append("[");
        Iterator<? extends E> iterator = matrix.iterator();
        if (iterator.hasNext())
        {
            // append one element
            appendable.append(MatrixIOUtils.toCharSequence(iterator.next()));

            // append rest of elements
            while (iterator.hasNext())
            {
                appendable.append(",");
                appendable.append(MatrixIOUtils.toCharSequence(iterator.next()));
            }
        }
        appendable.append("]");
        return appendable;
    }
}