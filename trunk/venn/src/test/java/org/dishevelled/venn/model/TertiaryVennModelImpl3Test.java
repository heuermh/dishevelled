/*

    dsh-venn  Lightweight components for venn diagrams.
    Copyright (c) 2009-2010 held jointly by the individual authors.

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

import java.util.Set;

import org.dishevelled.venn.AbstractTertiaryVennModel3Test;
import org.dishevelled.venn.TertiaryVennModel3;

import org.dishevelled.venn.model.TertiaryVennModelImpl3;

/**
 * Unit test for TertiaryVennModelImpl3.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class TertiaryVennModelImpl3Test
    extends AbstractTertiaryVennModel3Test
{

    /** {@inheritDoc} */
    protected <T> TertiaryVennModel3<T> createTertiaryVennModel3(final Set<? extends T> first,
            final Set<? extends T> second,
            final Set<? extends T> third)
    {
        return new TertiaryVennModelImpl3<T>(first, second, third);
    }

    public void testConstructor()
    {
        new TertiaryVennModelImpl3();
        new TertiaryVennModelImpl3(FIRST, SECOND, THIRD);

        try
        {
            new TertiaryVennModelImpl3<String>(null, SECOND, THIRD);
            fail("ctr(null,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            new TertiaryVennModelImpl3<String>(FIRST, null, THIRD);
            fail("ctr(,null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            new TertiaryVennModelImpl3<String>(FIRST, SECOND, null);
            fail("ctr(,,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            new TertiaryVennModelImpl3<String>(null, null, null);
            fail("ctr(null, null, null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }
}