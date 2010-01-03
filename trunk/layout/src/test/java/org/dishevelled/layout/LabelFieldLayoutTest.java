/*

    dsh-layout  Layout managers for lightweight components.
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
package org.dishevelled.layout;

import junit.framework.TestCase;

/**
 * Unit test case for LabelFieldLayout.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class LabelFieldLayoutTest
    extends TestCase
{

    public void testLabelFieldLayout()
    {
        LabelFieldLayout layout0 = new LabelFieldLayout();
        LabelFieldLayout layout1 = new LabelFieldLayout();

        assertNotNull(layout0);
        assertNotNull(layout1);
        assertTrue("layout0 not equals layout1", !(layout0.equals(layout1)));
        assertTrue("layout1 not equals layout0", !(layout1.equals(layout0)));

        try
        {
            new LabelFieldLayout(-1.0f, 1.0f);
            fail("ctr(-1.0f, 1.0f) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            new LabelFieldLayout(1.0f, -1.0f);
            fail("ctr(1.0f, -1.0f) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            new LabelFieldLayout(-1.0f, -1.0f);
            fail("ctr(-1.0f, -1.0f) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            new LabelFieldLayout(99.0f, 1.0f);
            fail("ctr(99.0f, 1.0f) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            new LabelFieldLayout(1.0f, 99.0f);
            fail("ctr(1.0f, 99.0f) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            new LabelFieldLayout(1.0f, 1.0f);
            fail("ctr(1.0f, 1.0f) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        LabelFieldLayout layout8 = new LabelFieldLayout(0.0f, 0.0f);
        assertNotNull("layout8", layout8);

        LabelFieldLayout layout9 = new LabelFieldLayout(0.0f, 1.0f);
        assertNotNull("layout9", layout9);

        LabelFieldLayout layout10 = new LabelFieldLayout(1.0f, 0.0f);
        assertNotNull("layout10", layout10);

        LabelFieldLayout layout11 = new LabelFieldLayout(0.25f, 0.75f);
        assertNotNull("layout11", layout11);

        LabelFieldLayout layout12 = new LabelFieldLayout();
        assertNotNull("layout12.nextLine()", layout12.nextLine());
        assertEquals("layout12.nextLine equals layout12", layout12, layout12.nextLine());

        LabelFieldLayout layout13 = new LabelFieldLayout(0.0f, 0.0f);
        assertTrue("layout13.getLabelPercent() == 0.0f", layout13.getLabelPercent() == 0.0f);
        assertTrue("layout13.getFieldPercent() == 0.0f", layout13.getFieldPercent() == 0.0f);

        layout13.setLabelPercent(0.25f);
        assertTrue("layout13.getLabelPercent() == 0.25f", layout13.getLabelPercent() == 0.25f);

        layout13.setFieldPercent(0.75f);
        assertTrue("layout13.getFieldPercent() == 0.75f", layout13.getFieldPercent() == 0.75f);

        try
        {
            layout13.setLabelPercent(-1.0f);
            fail("setLabelPercent(-1.0f) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            layout13.setLabelPercent(99.0f);
            fail("setLabelPercent(99.0f) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            layout13.setLabelPercent(0.75f);
            fail("setLabelPercent(0.75f) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        layout13.setLabelPercent(0.25f);

        try
        {
            layout13.setFieldPercent(-1.0f);
            fail("setFieldPercent(-1.0f) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            layout13.setFieldPercent(99.0f);
            fail("setFieldPercent(99.0f) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        try
        {
            layout13.setFieldPercent(0.90f);
            fail("setFieldPercent(0.90f) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        assertNotNull("label()", layout13.label());
        assertNotNull("normalLabel()", layout13.normalLabel());
        assertNotNull("labelLabel()", layout13.labelLabel());
        assertNotNull("wideLabel()", layout13.wideLabel());
        assertNotNull("finalWideLabel()", layout13.finalWideLabel());        
        assertNotNull("field()", layout13.field());
        assertNotNull("normalField()", layout13.normalField());
        assertNotNull("labelField()", layout13.labelField());
        assertNotNull("wideField()", layout13.wideField());
        assertNotNull("finalWideField()", layout13.finalWideField());
        assertNotNull("spacing()", layout13.spacing());        
        assertNotNull("finalSpacing()", layout13.finalSpacing());
    }
}
