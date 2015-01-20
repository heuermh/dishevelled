/*

    dsh-bitset  High performance bit set implementations.
    Copyright (c) 2011-2015 held jointly by the individual authors.

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

/**
 * High performance bit set implementations.
 *
 * <p>
 * The bit set implementations in this package were derived from
 * from <code>org.apache.lucene.util.OpenBitSet</code> from the Apache
 * Lucene project (<a href="http://lucene.apache.org">http://lucene.apache.org</a>).
 * </p>
 *
 * <p>
 * An abstract class was defined and separate immutable, mutable, and unsafe implementations
 * were created.  Various other changes include removal of duplicate methods that
 * were using integers as indices, addition of return values for logical operations, and
 * explicit copy methods for converting from one implementation to another one.
 * </p>
 */
package org.dishevelled.bitset;