/*

    dsh-color-scheme  Color schemes.
    Copyright (c) 2009-2014 held jointly by the individual authors.

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
package org.dishevelled.color.scheme.factory;

import java.awt.Color;

import java.util.HashMap;
import java.util.Map;

import org.dishevelled.color.scheme.ColorFactory;

/**
 * Caching color factory.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class CachingColorFactory
    implements ColorFactory
{
    /** Map of colors keyed by integer ARGB value. */
    // todo:  could be more efficient with a native int --> object map
    private final Map<Integer, Color> colors;

    /** Default initial capacity, <code>1024</code>. */
    private static final int DEFAULT_INITIAL_CAPACITY = 1024;


    /**
     * Create a new caching color factory.
     */
    public CachingColorFactory()
    {
        colors = new HashMap<Integer, Color>(DEFAULT_INITIAL_CAPACITY);
    }


    /** {@inheritDoc} */
    public Color createColor(final int red, final int green, final int blue, final float alpha)
    {
        int a = Math.min(255, Math.round(alpha * 255));
        int key = (a & 0xFF) << 24 | (red & 0xFF) << 16 | (green & 0xFF) << 8  | (blue & 0xFF) << 0;
        if (!colors.containsKey(key))
        {
            colors.put(key, new Color(red, green, blue, a));
        }
        return colors.get(key);
    }
}
