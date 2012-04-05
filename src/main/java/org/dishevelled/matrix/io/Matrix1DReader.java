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
package org.dishevelled.matrix.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import org.dishevelled.matrix.Matrix1D;

/**
 * Reader for matrices of objects in one dimension.
 *
 * @param <E> 1D matrix element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface Matrix1DReader<E>
{

    /**
     * Read a 1D matrix from the specified file.
     *
     * @param file file to read from, must not be null
     * @return a 1D matrix read from the specified file
     * @throws IOException if an IO error occurs
     */
    Matrix1D<E> read(File file) throws IOException;

    /**
     * Read a 1D matrix from the specified URL.
     *
     * @param url URL to read from, must not be null
     * @return a 1D matrix read from the specified URL
     * @throws IOException if an IO error occurs
     */
    Matrix1D<E> read(URL url) throws IOException;

    /**
     * Read a 1D matrix from the specified input stream.
     *
     * @param inputStream input stream to read from, must not be null
     * @return a 1D matrix read from the specified input stream
     * @throws IOException if an IO error occurs
     */
    Matrix1D<E> read(InputStream inputStream) throws IOException;
}