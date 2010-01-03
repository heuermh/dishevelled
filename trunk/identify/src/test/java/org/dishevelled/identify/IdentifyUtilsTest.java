/*

    dsh-identify  Lightweight components for identifiable beans.
    Copyright (c) 2003-2010 held jointly by the individual authors.

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
package org.dishevelled.identify;

import junit.framework.TestCase;

import org.dishevelled.iconbundle.IconBundle;

import org.dishevelled.iconbundle.tango.TangoProject;

/**
 * Unit test for IdentifyUtils.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class IdentifyUtilsTest
    extends TestCase
{

    public void testIdentifyUtils()
    {
        IdentifyUtils identifyUtils = IdentifyUtils.getInstance();

        assertNotNull(identifyUtils);
        assertEquals(IdentifyUtils.getInstance(), identifyUtils);

        assertNotNull(identifyUtils.getNameStrategy());
        assertNotNull(identifyUtils.getIconBundleStrategy());
        assertNotNull(identifyUtils.getDefaultIconBundle());

        try
        {
            identifyUtils.setNameStrategy(null);
            fail("setNameStrategy(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            identifyUtils.setIconBundleStrategy(null);
            fail("setIconBundleStrategy(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            identifyUtils.setDefaultIconBundle(null);
            fail("setDefaultIconBundle(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testGetNameFor()
    {
        assertEquals("null", IdentifyUtils.getNameFor(null));

        assertEquals("Example identifiable",
                     IdentifyUtils.getNameFor(new ExampleIdentifiable("Example identifiable", TangoProject.TEXT_X_GENERIC)));

        assertEquals("Example with name property",
                     IdentifyUtils.getNameFor(new ExampleWithNameProperty("Example with name property")));

        assertEquals("name", IdentifyUtils.getNameFor("name"));
    }

    public void testCustomNameStrategy()
    {
        IdentifyUtils identifyUtils = IdentifyUtils.getInstance();

        IdentifyUtils.NameStrategy oldNameStrategy = identifyUtils.getNameStrategy();

        IdentifyUtils.NameStrategy nameStrategy = new IdentifyUtils.NameStrategy()
            {
                /** @see IdentifyUtils.NameStrategy */
                public String getNameFor(final Object bean)
                {
                    return (bean == null) ? null : "foo";
                }
            };

        identifyUtils.setNameStrategy(nameStrategy);

        assertEquals(null, IdentifyUtils.getNameFor(null));

        assertEquals("foo",
                     IdentifyUtils.getNameFor(new ExampleIdentifiable("Example identifiable", TangoProject.TEXT_X_GENERIC)));

        assertEquals("foo",
                     IdentifyUtils.getNameFor(new ExampleWithNameProperty("Example with name property")));

        assertEquals("foo", IdentifyUtils.getNameFor("name"));

        identifyUtils.setNameStrategy(oldNameStrategy);
    }

    public void testGetIconBundleFor()
    {
        IdentifyUtils identifyUtils = IdentifyUtils.getInstance();

        assertEquals(null, IdentifyUtils.getIconBundleFor(null));

        assertEquals(TangoProject.TEXT_X_GENERIC,
                     IdentifyUtils.getIconBundleFor(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC)));

        assertEquals(TangoProject.TEXT_X_GENERIC,
                     IdentifyUtils.getIconBundleFor(new ExampleWithNameProperty("name")));

        assertEquals(identifyUtils.getDefaultIconBundle(), IdentifyUtils.getIconBundleFor("name"));
    }

    public void testCustomIconBundleStrategy()
    {
        IdentifyUtils identifyUtils = IdentifyUtils.getInstance();

        IdentifyUtils.IconBundleStrategy iconBundleStrategy = new IdentifyUtils.IconBundleStrategy()
            {
                /** @see IdentifyUtils.IconBundleStrategy */
                public IconBundle getIconBundleFor(final Object bean)
                {
                    // ignore what might be set for bean
                    return (bean == null) ? null : TangoProject.TEXT_HTML;
                }
            };

        IdentifyUtils.IconBundleStrategy oldIconBundleStrategy = identifyUtils.getIconBundleStrategy();
        identifyUtils.setIconBundleStrategy(iconBundleStrategy);

        assertEquals(null, IdentifyUtils.getIconBundleFor(null));

        assertEquals(TangoProject.TEXT_HTML,
                     IdentifyUtils.getIconBundleFor(new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC)));

        assertEquals(TangoProject.TEXT_HTML,
                     IdentifyUtils.getIconBundleFor(new ExampleWithNameProperty("name")));

        assertEquals(TangoProject.TEXT_HTML, IdentifyUtils.getIconBundleFor("name"));

        identifyUtils.setIconBundleStrategy(oldIconBundleStrategy);
    }

    public void testCustomDefaultIconBundle()
    {
        IdentifyUtils identifyUtils = IdentifyUtils.getInstance();
        IconBundle oldDefaultIconBundle = identifyUtils.getDefaultIconBundle();
        identifyUtils.setDefaultIconBundle(TangoProject.TEXT_X_GENERIC);

        assertEquals(TangoProject.TEXT_X_GENERIC, identifyUtils.getDefaultIconBundle());
        assertEquals(TangoProject.TEXT_X_GENERIC, IdentifyUtils.getIconBundleFor("name"));

        identifyUtils.setDefaultIconBundle(oldDefaultIconBundle);
    }
}