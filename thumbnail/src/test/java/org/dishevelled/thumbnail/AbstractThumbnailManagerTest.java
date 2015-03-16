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
package org.dishevelled.thumbnail;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

/**
 * Abstract unit test for implementations of ThumbnailManager.
 *
 * @author  Michael Heuer
 */
public abstract class AbstractThumbnailManagerTest
{
    /** Thumbnail manager to test. */
    protected ThumbnailManager thumbnailManager;

    /**
     * Create and return a new instance of ThumbnailManager to test.
     *
     * @return a new instance of ThumbnailManager to test
     */
    protected abstract ThumbnailManager createThumbnailManager();

    @Before
    public void setUp()
    {
        thumbnailManager = createThumbnailManager();
    }

    @Test
    public final void testCreateThumbnailManager()
    {
        assertNotNull(thumbnailManager);
    }

    @Test(expected=IllegalArgumentException.class)
    public final void testCreateThumbnailNullUri() throws IOException
    {
        thumbnailManager.createThumbnail(null, 0L);
    }

    @Test(expected=IllegalArgumentException.class)
    public final void testCreateLargeThumbnailNullUri() throws IOException
    {
        thumbnailManager.createLargeThumbnail(null, 0L);
    }
}
