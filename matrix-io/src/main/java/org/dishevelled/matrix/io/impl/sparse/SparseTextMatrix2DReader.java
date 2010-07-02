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
package org.dishevelled.matrix.io.impl.sparse;

import org.dishevelled.functor.UnaryFunction;

/**
 * Tab-delimited text reader for sparse matrices of objects in two dimensions.
 *
 * @param <E> 2D matrix element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class SparseTextMatrix2DReader<E>
    extends AbstractSparseTextMatrix2DReader<E>
{
    /** Parser function. */
    private final UnaryFunction<String, ? extends E> parser;


    /**
     * Create a new sparse text matrix 2D reader with the specified parser function.
     *
     * @param parser parser function, must not be null
     */
    public SparseTextMatrix2DReader(final UnaryFunction<String, ? extends E> parser)
    {
        if (parser == null)
        {
            throw new IllegalArgumentException("parser must not be null");
        }
        this.parser = parser;
    }


    /** {@inheritDoc} */
    protected E parse(final String value)
    {
        return parser.evaluate(value);
    }
}
