/*

    dsh-venn  Lightweight components for venn diagrams.
    Copyright (c) 2009-2012 held jointly by the individual authors.

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

import org.dishevelled.venn.AbstractQuaternaryVennModelTest;
import org.dishevelled.venn.QuaternaryVennModel;

import org.dishevelled.venn.model.QuaternaryVennModelImpl;

/**
 * Unit test for QuaternaryVennModelImpl.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class QuaternaryVennModelImplTest
    extends AbstractQuaternaryVennModelTest
{

    /** {@inheritDoc} */
    protected <T> QuaternaryVennModel<T> createQuaternaryVennModel(final Set<? extends T> first,
                                                                   final Set<? extends T> second,
                                                                   final Set<? extends T> third,
                                                                   final Set<? extends T> fourth)
    {
        return new QuaternaryVennModelImpl<T>(first, second, third, fourth);
    }

    public void testConstructor()
    {
        new QuaternaryVennModelImpl<String>();
        new QuaternaryVennModelImpl<String>(FIRST, SECOND, THIRD, FOURTH);

        try
        {
            new QuaternaryVennModelImpl<String>(null, SECOND, THIRD, FOURTH);
            fail("ctr(null,,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            new QuaternaryVennModelImpl<String>(FIRST, null, THIRD, FOURTH);
            fail("ctr(,null,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            new QuaternaryVennModelImpl<String>(FIRST, SECOND, null, FOURTH);
            fail("ctr(,,null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            new QuaternaryVennModelImpl<String>(FIRST, SECOND, THIRD, null);
            fail("ctr(,,,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            new QuaternaryVennModelImpl<String>(null, null, null, null);
            fail("ctr(null, null, null, null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }
}