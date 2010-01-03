/*

    dsh-piccolo-identify  Piccolo2D nodes for identifiable beans.
    Copyright (c) 2007-2010 held jointly by the individual authors.

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
 * Unit test for ExplorerIdNode.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class ExplorerIdNodeTest
    extends AbstractIdNodeTest
{

    /** {@inheritDoc} */
    protected AbstractIdNode createIdNode(final Object value)
    {
        return new ExplorerIdNode(value);
    }

    public void testConstructor()
    {
        ExplorerIdNode idNode0 = new ExplorerIdNode();
        assertNotNull(idNode0);

        ExplorerIdNode idNode1 = new ExplorerIdNode(null);
        assertNotNull(idNode1);

        Object value = new Object();
        ExplorerIdNode idNode2 = new ExplorerIdNode(value);
        assertNotNull(idNode2);

        ExplorerIdNode idNode3 = new ExplorerIdNode(null, IconSize.DEFAULT_16X16);
        assertNotNull(idNode3);

        ExplorerIdNode idNode4 = new ExplorerIdNode(value, IconSize.DEFAULT_16X16);
        assertNotNull(idNode4);

        try
        {
            ExplorerIdNode idNode = new ExplorerIdNode(value, null);
            fail("ctr(, null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }
}