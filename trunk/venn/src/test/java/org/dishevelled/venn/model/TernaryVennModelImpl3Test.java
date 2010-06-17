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

import org.dishevelled.venn.AbstractTernaryVennModel3Test;
import org.dishevelled.venn.TernaryVennModel3;

import org.dishevelled.venn.model.TernaryVennModelImpl3;

/**
 * Unit test for TernaryVennModelImpl3.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class TernaryVennModelImpl3Test
    extends AbstractTernaryVennModel3Test
{

    /** {@inheritDoc} */
    protected <T> TernaryVennModel3<T> createTernaryVennModel3(final Set<? extends T> first,
            final Set<? extends T> second,
            final Set<? extends T> third)
    {
        return new TernaryVennModelImpl3<T>(first, second, third);
    }

    public void testConstructor()
    {
        new TernaryVennModelImpl3();
        new TernaryVennModelImpl3<String>(FIRST, SECOND, THIRD);

        try
        {
            new TernaryVennModelImpl3<String>(null, SECOND, THIRD);
            fail("ctr(null,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            new TernaryVennModelImpl3<String>(FIRST, null, THIRD);
            fail("ctr(,null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            new TernaryVennModelImpl3<String>(FIRST, SECOND, null);
            fail("ctr(,,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            new TernaryVennModelImpl3<String>(null, null, null);
            fail("ctr(null, null, null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }
}