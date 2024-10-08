/*

    dsh-multi-map  Multi-key map interfaces and implementation.
    Copyright (c) 2007-2016 held jointly by the individual authors.

    This library is free software; you can redistribute it and/or modify it
    under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation; either version 2.1 of the License, or (at
    your option) any later version.

    This library is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; with out even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
    License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this library;  if not, write to the Free Software Foundation,
    Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA.

    > http://www.gnu.org/copyleft/lesser.html
    > http://www.opensource.org/licenses/lgpl-license.php

*/
package org.dishevelled.multimap.impl;

import org.dishevelled.multimap.AbstractTernaryKeyMapTest;
import org.dishevelled.multimap.TernaryKeyMap;

/**
 * Unit test for HashedTernaryKeyMap.
 *
 * @author  Michael Heuer
 */
public final class HashedTernaryKeyMapTest
    extends AbstractTernaryKeyMapTest
{

    @Override
    protected <K1, K2, K3, V> TernaryKeyMap<K1, K2, K3, V> createTernaryKeyMap()
    {
        return new HashedTernaryKeyMap<K1, K2, K3, V>();
    }
}
