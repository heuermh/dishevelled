/*

    dsh-eventlist-view  Views for event lists.
    Copyright (c) 2010-2013 held jointly by the individual authors.

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
package org.dishevelled.eventlist.view;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;

/**
 * Unit test for IdElementsList.
 */
public final class IdElementsListTest
{
    private EventList<String> elements;
    private IdElementsList<String> elementsList;

    @Before
    public void setUp()
    {
        elements = GlazedLists.eventListOf("foo", "bar", "baz");
        elementsList = new IdElementsList<String>(elements);
    }

    @Test
    public void testConstructor()
    {
        assertNotNull(elementsList);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConstructorNullModel()
    {
        new IdElementsList<String>(null);
    }

    @Test
    public void testConstructorLabelText()
    {
        assertNotNull(new IdElementsList<String>("labelText", elements));
    }

    @Test
    public void testConstructorNullLabelText()
    {
        assertNotNull(new IdElementsList<String>(null, elements));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConstructorLabelTextNullModel()
    {
        new IdElementsList<String>("labelText", null);
    }
}