/*

    dsh-piccolo-identify  Piccolo2D nodes for identifiable beans.
    Copyright (c) 2007-2019 held jointly by the individual authors.

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

import org.dishevelled.iconbundle.IconSize;

/**
 * Unit test for FinderIdNode.
 *
 * @author  Michael Heuer
 */
public final class FinderIdNodeTest
    extends AbstractIdNodeTest
{

    @Override
    protected AbstractIdNode createIdNode(final Object value)
    {
        return new FinderIdNode(value);
    }

    public void testConstructor()
    {
        FinderIdNode idNode0 = new FinderIdNode();
        assertNotNull(idNode0);

        FinderIdNode idNode1 = new FinderIdNode(null);
        assertNotNull(idNode1);

        Object value = new Object();
        FinderIdNode idNode2 = new FinderIdNode(value);
        assertNotNull(idNode2);

        FinderIdNode idNode3 = new FinderIdNode(null, IconSize.DEFAULT_16X16);
        assertNotNull(idNode3);

        FinderIdNode idNode4 = new FinderIdNode(value, IconSize.DEFAULT_16X16);
        assertNotNull(idNode4);

        try
        {
            FinderIdNode idNode = new FinderIdNode(value, null);
            fail("ctr(, null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }
}
