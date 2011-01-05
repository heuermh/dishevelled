/*

    dsh-matrix-io  Matrix readers and writers.
    Copyright (c) 2008-2011 held jointly by the individual authors.

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

import java.io.InputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * Static utility methods for matrix IO.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
final class MatrixIOUtils
{

    /**
     * Private no-arg constructor.
     */
    private MatrixIOUtils()
    {
        // empty
    }


    /**
     * Return the specified value as a CharSequence.
     *
     * @param value value
     * @return the specified value as a CharSequence
     */
    static CharSequence toCharSequence(final Object value)
    {
        return (value instanceof CharSequence) ? (CharSequence) value : String.valueOf(value);
    }

    /**
     * Close the specified input stream quietly.
     *
     * @param inputStream input stream to close
     */
    static void closeQuietly(final InputStream inputStream)
    {
        if (inputStream != null)
        {
            try
            {
                inputStream.close();
            }
            catch (IOException e)
            {
                // ignore
            }
        }
    }

    /**
     * Close the specified reader quietly.
     *
     * @param reader reader to close
     */
    static void closeQuietly(final Reader reader)
    {
        if (reader != null)
        {
            try
            {
                reader.close();
            }
            catch (IOException e)
            {
                // ignore
            }
        }
    }

    /**
     * Close the specified writer quietly.
     *
     * @param writer writer to close
     */
    static void closeQuietly(final Writer writer)
    {
        if (writer != null)
        {
            try
            {
                writer.close();
            }
            catch (IOException e)
            {
                // ignore
            }
        }
    }
}