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

import static org.dishevelled.variation.gemini.GeminiUtils.identifier;
import static org.dishevelled.variation.gemini.GeminiUtils.isValidIdentifier;
import static org.dishevelled.variation.gemini.GeminiUtils.variantId;
import static org.dishevelled.variation.gemini.GeminiUtils.URN_BASE;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for GeminiUtils.
 */
public final class GeminiUtilsTest
{

    @Test(expected=NullPointerException.class)
    public void testVariantIdNullIdentifier()
    {
        variantId(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testVariantIdInvalidFormat()
    {
        variantId("invalid format");
    }

    @Test
    public void testVariantId()
    {
        assertEquals(1234, variantId(URN_BASE + "databaseName:1234"));
    }

    @Test(expected=NullPointerException.class)
    public void testIdentifierNullDatabaseName()
    {
        identifier(1234, null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testIdentifierEmptyDatabaseName()
    {
        identifier(1234, "");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testIdentifierInvalidDatabaseName()
    {
        identifier(1234, "database:name");
    }

    @Test
    public void testIdentifier()
    {
        assertEquals(URN_BASE + "databaseName:1234", identifier(1234, "databaseName"));
    }

    @Test
    public void testIsValidIdentifierNullIdentifier()
    {
        assertFalse(isValidIdentifier(null));
    }

    @Test
    public void testIsValidIdentifierEmptyIdentifier()
    {
        assertFalse(isValidIdentifier(""));
    }

    @Test
    public void testIsValidIdentifierInvalidIdentifier()
    {
        assertFalse(isValidIdentifier("rs1234"));
    }

    @Test
    public void testIsValidIdentifier()
    {
        assertTrue(isValidIdentifier(URN_BASE + "databaseName:1234"));
    }
}