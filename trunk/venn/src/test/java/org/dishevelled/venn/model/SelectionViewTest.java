/*

    dsh-venn  Lightweight components for venn diagrams.
    Copyright (c) 2009-2015 held jointly by the individual authors.

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
package org.dishevelled.venn.model;

import java.util.HashSet;

import junit.framework.TestCase;

import org.dishevelled.observable.ObservableSet;

import org.dishevelled.observable.impl.ObservableSetImpl;

/**
 * Unit test for SelectionView.
 *
 * @author  Michael Heuer
 */
public final class SelectionViewTest
    extends TestCase
{

    public void testConstructor()
    {
        ObservableSet<String> union = new ObservableSetImpl<String>(new HashSet<String>());
        ObservableSet<String> view = new ObservableSetImpl<String>(new HashSet<String>());
        try
        {
            new SelectionView(null, null);
            fail("ctr(null, null) expected IllegalArgumentException");
        }
        //catch (IllegalArgumentException e)
        catch (NullPointerException e)
        {
            // expected
        }
        try
        {
            new SelectionView(union, null);
            fail("ctr(, null) expected IllegalArgumentException");
        }
        //catch (IllegalArgumentException e)
        catch (NullPointerException e)
        {
            // expected
        }

        assertNotNull(new SelectionView<String>(null, view));
        assertNotNull(new SelectionView<String>(union, view));
    }

    public void testToString()
    {
        ObservableSet<String> union = new ObservableSetImpl<String>(new HashSet<String>());
        ObservableSet<String> view = new ObservableSetImpl<String>(new HashSet<String>());
        SelectionView<String> selectionView = new SelectionView<String>(union, view);
        assertNotNull(selectionView.toString());
    }
}