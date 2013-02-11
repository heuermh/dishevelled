/*

    dsh-thumbnail  Implementation of the freedesktop.org thumbnail specification.
    Copyright (c) 2013 held jointly by the individual authors.

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

import java.io.File;
import java.io.IOException;

import java.net.URI;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Renderer;

import org.dishevelled.thumbnail.ThumbnailManager;

/**
 * Renderer that displays the thumbnail for a given URI.
 *
 * @author  Michael Heuer
 */
public final class ThumbnailRenderer implements Renderer
{
    /** Thumbnail manager. */
    private final ThumbnailManager thumbnailManager;

    /** JLabel delegate. */
    private final JLabel delegate = new JLabel();

    /** ImageIcon wrapper for thumbnail image. */
    private transient ImageIcon imageIcon;


    /**
     * Create a new thumbnail renderer with the specified thumbnail manager.
     *
     * @param thumbnailManager thumbnail manager, must not be null
     */
    public ThumbnailRenderer(final ThumbnailManager thumbnailManager)
    {
        super();
        if (thumbnailManager == null)
        {
            throw new IllegalArgumentException("thumbnailManager must not be null");
        }
        this.thumbnailManager = thumbnailManager;
    }


    @Override
    public Component getComponent()
    {
        return delegate;
    }

    @Override
    public void setValue(final Object value, final boolean selected)
    {
        if (value instanceof URI)
        {
            try
            {
                URI uri = (URI) value;
                long lastModified = 0L;
                try
                {
                    File file = new File(uri);
                    lastModified = file.lastModified();
                }
                catch (IllegalArgumentException e)
                {
                    // ignore
                }
                Image thumbnail = thumbnailManager.createThumbnail(uri, lastModified);
                if (imageIcon == null)
                {
                    imageIcon = new ImageIcon(thumbnail);
                }
                else
                {
                    imageIcon.setImage(thumbnail);
                }
                delegate.setIcon(imageIcon);
            }
            catch (IOException e)
            {
                // ignore
            }
        }
    }
}