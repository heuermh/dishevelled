/*

    dsh-thumbnail  Implementation of the freedesktop.org thumbnail specification.
    Copyright (c) 2013-2019 held jointly by the individual authors.

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
package org.dishevelled.thumbnail.swing;

import java.awt.Component;
import java.awt.Image;

import java.net.URI;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

import org.dishevelled.identify.StripeListCellRenderer;

/**
 * List cell renderer that displays the large thumbnail for a given URI.
 *
 * @author  Michael Heuer
 */
public final class LargeThumbnailListCellRenderer extends StripeListCellRenderer
{
    /** Thumbnail cache. */
    private final ThumbnailCache thumbnailCache;

    /** ImageIcon wrapper for thumbnail image. */
    private transient ImageIcon imageIcon;


    /**
     * Create a new large thumbnail list cell renderer with the specified thumbnail cache.
     *
     * @param thumbnailCache thumbnail cache, must not be null
     */
    public LargeThumbnailListCellRenderer(final ThumbnailCache thumbnailCache)
    {
        super();
        if (thumbnailCache == null)
        {
            throw new IllegalArgumentException("thumbnailCache must not be null");
        }
        this.thumbnailCache = thumbnailCache;
    }


    @Override
    public Component getListCellRendererComponent(final JList list,
                                                  final Object value,
                                                  final int index,
                                                  final boolean isSelected,
                                                  final boolean hasFocus)
    {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, hasFocus);

        if (value instanceof URI)
        {
            URI uri = (URI) value;
            Image thumbnail = thumbnailCache.getLargeThumbnail(uri);
            if (imageIcon == null)
            {
                imageIcon = new ImageIcon(thumbnail);
            }
            else
            {
                imageIcon.setImage(thumbnail);
            }
            label.setIcon(imageIcon);
        }
        return label;
    }
}
