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

import ca.odell.glazedlists.gui.TableFormat;

/**
 * Unit test for ElementsTable.
 */
public final class ElementsTableTest
{
    private EventList<String> elements;
    private TableFormat<String> tableFormat;
    private ElementsTable<String> elementsTable;

    @Before
    public void setUp()
    {
        elements = GlazedLists.eventListOf("foo", "bar", "baz");
        tableFormat = new TableFormat<String>()
            {
                @Override
                public int getColumnCount()
                {
                    return 1;
                }

                @Override
                public String getColumnName(final int column)
                {
                    return "Name";
                }

                @Override
                public Object getColumnValue(final String baseObject, final int column)
                {
                    return baseObject;
                }
            };
        elementsTable = new ElementsTable<String>(elements, tableFormat);
    }

    @Test
    public void testConstructor()
    {
        assertNotNull(elementsTable);
    }

    //@Test(expected=IllegalArgumentException.class)  thrown by SortedList ctr
    @Test(expected=NullPointerException.class)
    public void testConstructorNullModel()
    {
        new ElementsTable<String>(null, tableFormat);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConstructorNullTableFormat()
    {
        new ElementsTable<String>(elements, null);
    }

    @Test
    public void testConstructorLabelText()
    {
        assertNotNull(new ElementsTable<String>("labelText", elements, tableFormat));
    }

    @Test
    public void testConstructorNullLabelText()
    {
        assertNotNull(new ElementsTable<String>(null, elements, tableFormat));
    }

    //@Test(expected=IllegalArgumentException.class)  thrown by SortedList ctr
    @Test(expected=NullPointerException.class)
    public void testConstructorLabelTextNullModel()
    {
        new ElementsTable<String>("labelText", null, tableFormat);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConstructorLabelTextNullTableFormat()
    {
        new ElementsTable<String>("labelText", elements, null);
    }
}