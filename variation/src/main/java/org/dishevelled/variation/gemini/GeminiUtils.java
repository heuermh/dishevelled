/*

    dsh-variation  Variation.
    Copyright (c) 2013-2014 held jointly by the individual authors.

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
package org.dishevelled.variation.gemini;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Static utility methods for GEMINI.
 *
 * @author  Michael Heuer
 */
final class GeminiUtils
{
    /** URN for GEMINI identifiers. */
    static final String URN_BASE = "urn:gemini/";

    /** GEMINI identifier pattern. */
    static final Pattern IDENTIFIER = Pattern.compile("^urn:gemini/[^:]+:([0-9]+)$");


    /**
     * Return the GEMINI variantid for the specified identifier.
     *
     * @param identifier identifier
     * @return the GEMINI variantId for the specified identifier
     */
    static int variantId(final String identifier)
    {
        checkNotNull(identifier);
        Matcher m = IDENTIFIER.matcher(identifier);
        if (m.matches())
        {
            return Integer.parseInt(m.group(1));
        }
        throw new IllegalArgumentException("identifier " + identifier + " not valid format");
    }

    /**
     * Create and return a GEMINI identifier given the specified variant id and database name.
     *
     * @param variantId variant id
     * @param databaseName database name, must not be null, empty, or contain the '<code>:</code>' character
     * @return a GEMINI identifier given the specified variant id and database name
     */
    static String identifier(final int variantId, final String databaseName)
    {
        checkNotNull(databaseName);
        checkArgument(databaseName.length() > 0, "databaseName must not be empty");
        checkArgument(!databaseName.contains(":"), "databaseName must not contain ':' character");

        StringBuilder sb = new StringBuilder();
        sb.append(URN_BASE);
        // URL encode database name?
        sb.append(databaseName);
        sb.append(":");
        sb.append(variantId);
        return sb.toString();
    }

    /**
     * Return true if the specified identifier is a valid GEMINI identifier.
     *
     * @param identifier identifier
     * @return true if the specified identifier is a valid GEMINI identifier
     */
    static boolean isValidIdentifier(final String identifier)
    {
        if (identifier == null)
        {
            return false;
        }
        Matcher m = IDENTIFIER.matcher(identifier);
        return m.matches();
    }
}
