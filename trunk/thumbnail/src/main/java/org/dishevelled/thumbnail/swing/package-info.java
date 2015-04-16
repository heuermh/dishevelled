/*

    dsh-thumbnail  Implementation of the freedesktop.org thumbnail specification.
    Copyright (c) 2013-2015 held jointly by the individual authors.

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

/**
 * Lightweight components for thumbnails.
 *
 * <p>
 * All the components in this package use {@link ThumbnailCache} for performance.  The same
 * thumbnail cache should be used for more than one component. For example,
 * <pre>
 * ThumbnailManager manager = new XdgThumbnailManager();
 * ThumbnailCache cache = new ThumbnailCache(manager);
 *
 * JList&lt;URI&gt; list = new JList&lt;URI&gt;();
 * list.setCellRenderer(new ThumbnailListCellRenderer(cache));
 * JTable table = new JTable();
 * table.setDefaultCellRenderer(URI.class, new ThumbnailTableCellRenderer(cache));
 *
 * // repaint components on reload of thumbnail images
 * cache.add(list);
 * cache.add(table);
 * </pre>
 * </p>
 */
package org.dishevelled.thumbnail.swing;
