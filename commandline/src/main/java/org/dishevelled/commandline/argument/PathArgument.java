/*

    dsh-commandline  Command line parser based on typed arguments.
    Copyright (c) 2004-2022 held jointly by the individual authors.

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
package org.dishevelled.commandline.argument;

import java.io.File;

import java.net.URI;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A path argument.
 *
 * @since 1.2
 * @author  Michael Heuer
 */
public final class PathArgument
    extends AbstractArgument<Path>
{

    /**
     * Create a new path argument.
     *
     * @param shortName short argument name
     * @param longName long argument name
     * @param description argument description
     * @param required <code>true</code> if this argument is required
     */
    public PathArgument(final String shortName,
                        final String longName,
                        final String description,
                        final boolean required)
    {
        super(shortName, longName, description, required);
    }


    @Override
    protected Path convert(final String s)
        throws Exception
    {
        return convertPath(s);
    }

    /**
     * Convert the specified string to a path, defaulting to <code>file:</code> scheme if necessary.
     *
     * @param s string to convert, if any
     * @return the specified string converted to a path, defaulting to <code>file:</code> scheme if necessary
     * @throws Exception if an error occurs
     */
    static Path convertPath(final String s) throws Exception
    {
        // return null path if empty string
        if (s == null || "".equals(s))
        {
            return null;
        }
        URI uri = URI.create(s);

        // default to file: if scheme is missing
        String scheme = uri.getScheme();
        if (scheme == null || "".equals(scheme))
        {
            return new File(s).toPath();
        }

        // create path from URI, allowing custom providers, e.g. hdfs, s3, etc.
        return Paths.get(uri);
    }
}
