/*

    dsh-iconbundle  Bundles of variants for icon images.
    Copyright (c) 2003-2007 held jointly by the individual authors.

    This library is free software; you can redistribute it and/or modify it
    under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation; either version 2.1 of the License, or (at
    your option) any later version.

    This library is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; with out even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
    License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this library;  if not, write to the Free Software Foundation,
    Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA.

    > http://www.gnu.org/copyleft/lesser.html
    > http://www.opensource.org/licenses/lgpl-license.php

*/
package org.dishevelled.iconbundle.impl;

import java.awt.Image;
import java.awt.Component;

import java.util.Map;
import java.util.HashMap;

import org.dishevelled.iconbundle.IconSize;
import org.dishevelled.iconbundle.IconState;
import org.dishevelled.iconbundle.IconBundle;
import org.dishevelled.iconbundle.IconTextDirection;

/**
 * Wrapper around an existing implementation of IconBundle
 * that caches images.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class CachingIconBundle
    implements IconBundle
{
    /** Wrapped icon bundle. */
    private final IconBundle iconBundle;

    /** Image cache, keyed by { direction, state, size }. */
    private final Map cache;
    //private final Map<IconBundleKey, Image> cache;


    /**
     * Create a new caching icon bundle which wraps the
     * specified icon bundle.
     *
     * @param iconBundle icon bundle to wrap, must not be null
     */
    public CachingIconBundle(final IconBundle iconBundle)
    {
        if (iconBundle == null)
        {
            throw new IllegalArgumentException("iconBundle must not be null");
        }

        this.iconBundle = iconBundle;
        this.cache = new HashMap();
        //this.cache = new HashMap<IconBundleKey, Image>();
    }


    /**
     * Invalidate the cache of images.
     */
    public void invalidateCache()
    {
        cache.clear();
    }

    /** {@inheritDoc} */
    public Image getImage(final Component component,
                          final IconTextDirection direction,
                          final IconState state,
                          final IconSize size)
    {
        IconBundleKey key = new IconBundleKey(direction, state, size);
        if (cache.containsKey(key))
        {
            // return cache.get(key);
            return (Image) cache.get(key);
        }
        else
        {
            Image image = iconBundle.getImage(component, direction, state, size);
            cache.put(key, image);
            return image;
        }
    }
}
