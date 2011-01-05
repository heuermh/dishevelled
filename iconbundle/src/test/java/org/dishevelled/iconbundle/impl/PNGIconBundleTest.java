/*

    dsh-iconbundle  Bundles of variants for icon images.
    Copyright (c) 2003-2011 held jointly by the individual authors.

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
package org.dishevelled.iconbundle.impl;

import java.awt.Component;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;

import java.net.URL;

import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.FileImageInputStream;

import javax.swing.JPanel;

import org.dishevelled.iconbundle.AbstractIconBundleTest;
import org.dishevelled.iconbundle.IconSize;
import org.dishevelled.iconbundle.IconState;
import org.dishevelled.iconbundle.IconBundle;
import org.dishevelled.iconbundle.IconTextDirection;

/**
 * Unit test for PNGIconBundle.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class PNGIconBundleTest
    extends AbstractIconBundleTest
{
    /** Relative path to test image. */
    private static final String TEST_IMAGE_PATH = "./src/test/resources/org/dishevelled/iconbundle/impl/test.png";


    /** @see AbstractIconBundleTest */
    public IconBundle createIconBundle()
    {
        File file = null;
        try
        {
            file = new File(TEST_IMAGE_PATH);
        }
        catch (Exception e)
        {
            // ignore
        }

        return new PNGIconBundle(file);
    }

    public void testConstructor()
        throws Exception
    {
        File file = new File(TEST_IMAGE_PATH);
        IconBundle ib1 = new PNGIconBundle(file);

        FileImageInputStream imageInputStream = new FileImageInputStream(file);
        IconBundle ib2 = new PNGIconBundle(imageInputStream);

        InputStream inputStream = new FileInputStream(file);
        IconBundle ib3 = new PNGIconBundle(inputStream);

        URL url = new URL("file:///" + file.getAbsolutePath());
        IconBundle ib4 = new PNGIconBundle(url);

        try
        {
            IconBundle ib = new PNGIconBundle((File) null);
            fail("ctr((File) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            IconBundle ib = new PNGIconBundle((ImageInputStream) null);
            fail("ctr((ImageInputStream) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            IconBundle ib = new PNGIconBundle((InputStream) null);
            fail("ctr((InputStream) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            IconBundle ib = new PNGIconBundle((URL) null);
            fail("ctr((URL) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            IconBundle ib = new PNGIconBundle(new File("not_a_valid_image_file"));
            fail("ctr((URL) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            IconBundle ib = new PNGIconBundle(new URL("http://localhost/not_a_valid_image_file"));
            fail("ctr((URL) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testBaseImageNameConstructor()
    {
        assertNotNull(new PNGIconBundle("baseImageName"));
    }

    public void testBaseImageNameConstructorNullBaseImageName()
    {
        try
        {
            new PNGIconBundle((String) null);
            fail("ctr((String) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }        
    }

    public void testBaseImageName()
    {
        PNGIconBundle iconBundle = new PNGIconBundle("test");
        Component c = new JPanel();
        assertNotNull(iconBundle.getImage(c, IconTextDirection.LEFT_TO_RIGHT, IconState.NORMAL, IconSize.DEFAULT_16X16));
        assertNotNull(iconBundle.getImage(c, IconTextDirection.LEFT_TO_RIGHT, IconState.NORMAL, IconSize.DEFAULT_24X24));
        assertNotNull(iconBundle.getImage(c, IconTextDirection.LEFT_TO_RIGHT, IconState.NORMAL, IconSize.DEFAULT_32X32));
        assertNull(iconBundle.getImage(c, IconTextDirection.LEFT_TO_RIGHT, IconState.NORMAL, IconSize.DEFAULT_48X48));
        assertNull(iconBundle.getImage(c, IconTextDirection.LEFT_TO_RIGHT, IconState.NORMAL, IconSize.DEFAULT_64X64));
        assertNull(iconBundle.getImage(c, IconTextDirection.LEFT_TO_RIGHT, IconState.NORMAL, IconSize.DEFAULT_96X96));
        assertNull(iconBundle.getImage(c, IconTextDirection.LEFT_TO_RIGHT, IconState.NORMAL, IconSize.DEFAULT_128X128));
    }
}