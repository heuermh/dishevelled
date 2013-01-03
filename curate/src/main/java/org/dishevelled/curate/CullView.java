/*

    dsh-curate  Interfaces for curating collections quickly.
    Copyright (c) 2007-2013 held jointly by the individual authors.

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
package org.dishevelled.curate;

import java.util.Collection;

/**
 * Cull view.
 *
 * @param <E> input and result collections element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface CullView<E>
{

    /**
     * Set the input collection for this cull view to <code>input</code>.
     *
     * @param input input collection for this cull view, must not be null
     */
    void setInput(Collection<E> input);

    /**
     * Return an unmodifiable collection of the input collection elements that
     * should remain.  The collection may be empty but will not be null.  The union
     * of this collection and <code>getRemoved()</code> should have the same
     * elements as the input collection.
     *
     * @return an unmodifiable collection of the input collection elements that
     *    should remain
     */
    Collection<E> getRemaining();

    /**
     * Return an unmodifiable collection of the input collection elements that
     * should be removed.  The collection may be empty but will not be null.  The
     * union of this collection and <code>getRemaining()</code> should have
     * the same elements as the input collection.
     *
     * @return an unmodifiable collection of the input collection elements that
     *    should be removed
     */
    Collection<E> getRemoved();

    // some notification to clients of the progress...
    // either
    //   Future<Result<E>> cull();
    // or
    //   addCullListener(...) { void cancelled(CullEvent event); void completed(CullEvent event); }
}