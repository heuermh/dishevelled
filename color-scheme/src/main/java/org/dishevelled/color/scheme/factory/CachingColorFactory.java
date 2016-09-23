/*

    dsh-color-scheme  Color schemes.
    Copyright (c) 2009-2016 held jointly by the individual authors.

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

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import org.dishevelled.color.scheme.ColorFactory;

/**
 * Caching color factory.
 *
 * @author  Michael Heuer
 */
public final class CachingColorFactory
    implements ColorFactory
{
    /** Cache of colors keyed by integer ARGB value. */
    private final Cache<Integer, Color> colors = CacheBuilder.newBuilder()
        .maximumSize(100000L)
        .build();

    @Override
    public Color createColor(final int red, final int green, final int blue, final float alpha)
    {
        final int a = Math.min(255, Math.round(alpha * 255));
        final int key = (a & 0xFF) << 24 | (red & 0xFF) << 16 | (green & 0xFF) << 8  | (blue & 0xFF) << 0;

        try
        {
            return colors.get(key, new Callable<Color>()
                {
                    @Override
                    public Color call()
                    {
                        return new Color(red, green, blue, a);
                    }
                });
        }
        catch (ExecutionException e)
        {
            // shouldn't happen, no checked exceptions are thrown above
            return Color.WHITE;
        }
    }
}
