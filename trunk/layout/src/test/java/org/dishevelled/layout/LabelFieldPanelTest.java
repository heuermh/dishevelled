/*

    dsh-layout  Layout manager(s) for lightweight components.
    Copyright (c) 2003-2008 held jointly by the individual authors.

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

import javax.swing.JLabel;
import javax.swing.JTextField;

import junit.framework.TestCase;

/**
 * Unit test for LabelFieldPanel.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class LabelFieldPanelTest
    extends TestCase
{

    public void testLabelFieldPanel()
    {
        LabelFieldPanel panel = new LabelFieldPanel();
        assertNotNull("panel not null", panel);
        // todo:  width, percent --> weight
        assertEquals(LabelFieldLayout.DEFAULT_LABEL_WIDTH, panel.getLabelPercent(), 0.1f);
        assertEquals(LabelFieldLayout.DEFAULT_FIELD_WIDTH, panel.getFieldPercent(), 0.1f);

        try
        {
            panel.add(new JLabel());
            fail("add(Component) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            panel.add("foo", new JLabel());
            fail("add(String, Component) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            panel.add(new JLabel(), 0);
            fail("add(Component, int) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            panel.add(new JLabel(), new Object());
            fail("add(Component, Object) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        try
        {
            panel.add(new JLabel(), new Object(), 0);
            fail("add(Component, Object, int) expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }

        try
        {
            panel.setLabelPercent(-0.1f);
            fail("setLabelPercent(-0.1f) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            panel.setLabelPercent(1.1f);
            fail("setLabelPercent(1.1f) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            panel.setFieldPercent(-1.0f);
            fail("setFieldPercent(-1.0f) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            panel.setFieldPercent(1.1f);
            fail("setFieldPercent(1.1f) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        panel.addLabel("Foo");
        panel.addLabel((String) null);
        panel.addLabel(new JLabel("Bar"));
        try
        {
            panel.addLabel((JLabel) null);
            fail("addLabel((JLabel) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        panel.addField(new JTextField(12));
        panel.addField("Baz", new JTextField(12));
        panel.addField((String) null, new JTextField(12));
        panel.addField(new JLabel("Qux"), new JTextField(12));
        panel.addField(new JLabel("Garply"), new JLabel("Waldo"));
        try
        {
            panel.addField((JLabel) null);
            fail("addField((JLabel) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            panel.addField((JLabel) null, new JTextField(12));
            fail("addField((JLabel) null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            panel.addField("Fred", null);
            fail("addField(,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            panel.addField(new JLabel("Plugh"), null);
            fail("addField(,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        panel.addSpacing(0);
        panel.addSpacing(10);
        try
        {
            panel.addSpacing(-1);
            fail("addSpacing(-1) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        panel.addFinalSpacing();
    }

    public void testFinalComponents()
    {
        LabelFieldPanel panel0 = new LabelFieldPanel();
        panel0.addFinalSpacing();
        try
        {
            panel0.addFinalSpacing();
            fail("expected IllegalStateException");
        }
        catch (IllegalStateException e)
        {
            // expected
        }
        try
        {
            panel0.addFinalSpacing(1);
            fail("expected IllegalStateException");
        }
        catch (IllegalStateException e)
        {
            // expected
        }
        try
        {
            panel0.addFinalField(new JTextField(12));
            fail("expected IllegalStateException");
        }
        catch (IllegalStateException e)
        {
            // expected
        }

        LabelFieldPanel panel1 = new LabelFieldPanel();
        panel1.addFinalSpacing(1);
        try
        {
            panel1.addFinalSpacing();
            fail("expected IllegalStateException");
        }
        catch (IllegalStateException e)
        {
            // expected
        }
        try
        {
            panel1.addFinalSpacing(1);
            fail("expected IllegalStateException");
        }
        catch (IllegalStateException e)
        {
            // expected
        }
        try
        {
            panel1.addFinalField(new JTextField(12));
            fail("expected IllegalStateException");
        }
        catch (IllegalStateException e)
        {
            // expected
        }

        LabelFieldPanel panel2 = new LabelFieldPanel();
        panel2.addFinalField(new JTextField(12));
        try
        {
            panel2.addFinalSpacing();
            fail("expected IllegalStateException");
        }
        catch (IllegalStateException e)
        {
            // expected
        }
        try
        {
            panel2.addFinalSpacing(1);
            fail("expected IllegalStateException");
        }
        catch (IllegalStateException e)
        {
            // expected
        }
        try
        {
            panel2.addFinalField(new JTextField(12));
            fail("expected IllegalStateException");
        }
        catch (IllegalStateException e)
        {
            // expected
        }

        LabelFieldPanel panel3 = new LabelFieldPanel();
        try
        {
            panel3.addFinalSpacing(-1);
            fail("addFinalSpacing(-1) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            panel3.addFinalField(null);
            fail("addFinalField(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }
}