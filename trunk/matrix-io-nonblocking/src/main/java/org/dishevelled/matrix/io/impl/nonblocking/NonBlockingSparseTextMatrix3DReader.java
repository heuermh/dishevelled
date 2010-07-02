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

import org.dishevelled.functor.UnaryFunction;

/**
 * Tab-delimited text reader for non-blocking sparse matrices of objects in three dimensions.
 *
 * @param <E> 3D matrix element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class NonBlockingSparseTextMatrix3DReader<E>
    extends AbstractNonBlockingSparseTextMatrix3DReader<E>
{

        /** Parser function. */
        private final UnaryFunction<String, ? extends E> parser;


        /**
         * Create a new non-blocking sparse text matrix 3D reader with the specified parser function.
         *
         * @param parser parser function, must not be null
         */
        public NonBlockingSparseTextMatrix3DReader(final UnaryFunction<String, ? extends E> parser)
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
