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
 * Unit test for IdElementsSummary.
 */
public final class IdElementsSummaryTest
{
    private EventList<String> elements;
    private IdElementsSummary<String> elementsSummary;

    @Before
    public void setUp()
    {
        elements = GlazedLists.eventListOf("foo", "bar", "baz");
        elementsSummary = new IdElementsSummary<String>(elements);
    }

    @Test
    public void testConstructor()
    {
        assertNotNull(elementsSummary);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConstructorNullModel()
    {
        new IdElementsSummary<String>(null);
    }
}