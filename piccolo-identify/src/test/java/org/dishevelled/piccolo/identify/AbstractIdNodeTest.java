/*

    dsh-piccolo-identify  Piccolo2D nodes for identifiable beans.
    Copyright (c) 2007-2011 held jointly by the individual authors.

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
package org.dishevelled.piccolo.identify;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import junit.framework.TestCase;

import org.dishevelled.iconbundle.IconSize;
import org.dishevelled.iconbundle.IconState;
import org.dishevelled.iconbundle.IconTextDirection;

import org.dishevelled.iconbundle.tango.TangoProject;

import org.dishevelled.identify.ExampleIdentifiable;
import org.dishevelled.identify.ExampleWithNameProperty;

/**
 * Abstract unit test for subclasses of AbstractIdNode.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractIdNodeTest
    extends TestCase
{
    /** Example identifiable. */
    protected ExampleIdentifiable exampleIdentifiable;

    /** Example with name property. */
    protected ExampleWithNameProperty exampleWithNameProperty;

    /** Property change listener. */
    protected static final PropertyChangeListener PROPERTY_CHANGE_LISTENER = new PropertyChangeListener()
        {
            /** {@inheritDoc} */
            public void propertyChange(final PropertyChangeEvent event)
            {
                // empty
            }
        };

    /**
     * Create and return a new instance of a subclass of AbstractIdNode
     * to test with the specified value.
     *
     * @param value value for the id node
     * @return a new instance of a subclass of AbstractIdNode
     *   to test with the specified value
     */
    protected abstract AbstractIdNode createIdNode(Object value);

    /** {@inheritDoc} */
    protected void setUp() throws Exception
    {
        exampleIdentifiable = new ExampleIdentifiable("name", TangoProject.TEXT_X_GENERIC);
        exampleWithNameProperty = new ExampleWithNameProperty("name");
    }

    public void testCreateIdNode()
    {
        AbstractIdNode idNode0 = createIdNode(null);
        assertNotNull(idNode0);
        AbstractIdNode idNode1 = createIdNode("foo");
        assertNotNull(idNode1);
        AbstractIdNode idNode2 = createIdNode(new Object());
        assertNotNull(idNode2);
        AbstractIdNode idNode3 = createIdNode(exampleIdentifiable);
        assertNotNull(idNode3);
        AbstractIdNode idNode4 = createIdNode(exampleWithNameProperty);
        assertNotNull(idNode4);
    }

    public void testValue()
    {
        AbstractIdNode idNode0 = createIdNode(null);
        assertEquals(null, idNode0.getValue());
        AbstractIdNode idNode1 = createIdNode("foo");
        assertEquals("foo", idNode1.getValue());
        Object bar = new Object();
        AbstractIdNode idNode2 = createIdNode(bar);
        assertEquals(bar, idNode2.getValue());
        AbstractIdNode idNode3 = createIdNode(exampleIdentifiable);
        assertEquals(exampleIdentifiable, idNode3.getValue());
        AbstractIdNode idNode4 = createIdNode(exampleWithNameProperty);
        assertEquals(exampleWithNameProperty, idNode4.getValue());
    }

    public void testSetValue()
    {
        AbstractIdNode idNode = createIdNode(null);
        assertEquals(null, idNode.getValue());
        idNode.setValue(null);
        assertEquals(null, idNode.getValue());
        idNode.setValue("foo");
        assertEquals("foo", idNode.getValue());
        Object bar = new Object();
        idNode.setValue(bar);
        assertEquals(bar, idNode.getValue());
        idNode.setValue(exampleIdentifiable);
        assertEquals(exampleIdentifiable, idNode.getValue());
        idNode.setValue(exampleWithNameProperty);
        assertEquals(exampleWithNameProperty, idNode.getValue());
    }

    public void testIconSize()
    {
        AbstractIdNode idNode = createIdNode(null);
        assertTrue(idNode.getIconSize() != null);
        assertEquals(AbstractIdNode.DEFAULT_ICON_SIZE, idNode.getIconSize());
        IconSize iconSize = IconSize.DEFAULT_16X16;
        idNode.setIconSize(iconSize);
        assertTrue(idNode.getIconSize() != null);
        assertEquals(iconSize, idNode.getIconSize());

        try
        {
            idNode.setIconSize(null);
            fail("setIconSize(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testIconState()
    {
        AbstractIdNode idNode = createIdNode(null);
        assertTrue(idNode.getIconState() != null);
        assertEquals(AbstractIdNode.DEFAULT_ICON_STATE, idNode.getIconState());
        IconState iconState = IconState.MOUSEOVER;
        idNode.setIconState(iconState);
        assertTrue(idNode.getIconState() != null);
        assertEquals(iconState, idNode.getIconState());

        try
        {
            idNode.setIconState(null);
            fail("setIconState(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testIconTextDirection()
    {
        AbstractIdNode idNode = createIdNode(null);
        assertTrue(idNode.getIconTextDirection() != null);
        assertEquals(AbstractIdNode.DEFAULT_ICON_TEXT_DIRECTION, idNode.getIconTextDirection());
        IconTextDirection iconTextDirection = IconTextDirection.RIGHT_TO_LEFT;
        idNode.setIconTextDirection(iconTextDirection);
        assertTrue(idNode.getIconTextDirection() != null);
        assertEquals(iconTextDirection, idNode.getIconTextDirection());

        try
        {
            idNode.setIconTextDirection(null);
            fail("setIconTextDirection(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testPropertyChangeListeners()
    {
        AbstractIdNode idNode = createIdNode(null);
        idNode.addPropertyChangeListener(null);
        idNode.removePropertyChangeListener(null);
        idNode.addPropertyChangeListener(PROPERTY_CHANGE_LISTENER);
        idNode.removePropertyChangeListener(PROPERTY_CHANGE_LISTENER);
        idNode.addPropertyChangeListener("iconSize", null);
        idNode.removePropertyChangeListener("iconSize", null);
        idNode.addPropertyChangeListener("iconSize", PROPERTY_CHANGE_LISTENER);
        idNode.removePropertyChangeListener("iconSize", PROPERTY_CHANGE_LISTENER);
        idNode.addPropertyChangeListener("iconState", null);
        idNode.removePropertyChangeListener("iconState", null);
        idNode.addPropertyChangeListener("iconState", PROPERTY_CHANGE_LISTENER);
        idNode.removePropertyChangeListener("iconState", PROPERTY_CHANGE_LISTENER);
        idNode.addPropertyChangeListener("iconTextDirection", null);
        idNode.removePropertyChangeListener("iconTextDirection", null);
        idNode.addPropertyChangeListener("iconTextDirection", PROPERTY_CHANGE_LISTENER);
        idNode.removePropertyChangeListener("iconTextDirection", PROPERTY_CHANGE_LISTENER);
    }
}