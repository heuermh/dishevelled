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
 * Unit test for NautilusIdNode.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class NautilusIdNodeTest
    extends AbstractIdNodeTest
{

    /** {@inheritDoc} */
    protected AbstractIdNode createIdNode(final Object value)
    {
        return new NautilusIdNode(value);
    }

    public void testConstructor()
    {
        NautilusIdNode idNode0 = new NautilusIdNode();
        assertNotNull(idNode0);

        NautilusIdNode idNode1 = new NautilusIdNode(null);
        assertNotNull(idNode1);

        Object value = new Object();
        NautilusIdNode idNode2 = new NautilusIdNode(value);
        assertNotNull(idNode2);

        NautilusIdNode idNode3 = new NautilusIdNode(null, IconSize.DEFAULT_16X16);
        assertNotNull(idNode3);

        NautilusIdNode idNode4 = new NautilusIdNode(value, IconSize.DEFAULT_16X16);
        assertNotNull(idNode4);

        try
        {
            NautilusIdNode idNode = new NautilusIdNode(value, null);
            fail("ctr(, null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }
}