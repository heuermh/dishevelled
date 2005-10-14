/*

    dsh-iconbundle  Bundles of variants for icon images.
    Copyright (c) 2003-2005 held jointly by the individual authors.

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

import java.io.File;

import java.net.URL;

import org.dishevelled.iconbundle.IconBundle;
import org.dishevelled.iconbundle.AbstractIconBundleTest;

/**
 * Unit test for SVGIconBundle.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class SVGIconBundleTest
    extends AbstractIconBundleTest
{
    /** Relative path to test image. */
    private static final String TEST_IMAGE_PATH = "./src/test/resources/org/dishevelled/iconbundle/impl/test.svg";


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

        return new SVGIconBundle(file);
    }

    public void testConstructor()
        throws Exception
    {
        File file = new File(TEST_IMAGE_PATH);
        IconBundle ib1 = new SVGIconBundle(file);

        URL url = new URL("file:///" + file.getAbsolutePath());
        IconBundle ib2 = new SVGIconBundle(url);

        try
        {
            IconBundle ib = new SVGIconBundle((File) null);
            fail("ctr((File) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            IconBundle ib = new SVGIconBundle((URL) null);
            fail("ctr((URL) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }
}