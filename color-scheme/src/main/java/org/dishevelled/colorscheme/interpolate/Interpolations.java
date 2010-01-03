/*

    dsh-color-scheme  Color schemes.
    Copyright (c) 2009-2010 held jointly by the individual authors.

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
package org.dishevelled.colorscheme.interpolate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dishevelled.colorscheme.Interpolation;

/**
 * Interpolations.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class Interpolations
{
    /** Linear interpolation. */
    public static final Interpolation LINEAR = new LinearInterpolation();

    /** Array of interpolation values. */
    private static final Interpolation[] values = new Interpolation[] { LINEAR };

    /** List of interpolation values. */
    public static final List<Interpolation> VALUES = Arrays.asList(values);

    /** Map of interpolations keyed by name. */
    private static final Map<String, Interpolation> KEYED_BY_NAME = new HashMap<String, Interpolation>();

    static
    {
        KEYED_BY_NAME.put("linear", LINEAR);
    }


    /**
     * Private no-arg constructor.
     */
    private Interpolations()
    {
        // empty
    }


    /**
     * Return the interpolation with the specified name, if any.
     *
     * @param name interpolation name
     * @return the interpolation with the specified name, or <code>null</code>
     *    if no such interpolation exists
     */
    public static Interpolation valueOf(final String name)
    {
        return KEYED_BY_NAME.get(name);
    }
}