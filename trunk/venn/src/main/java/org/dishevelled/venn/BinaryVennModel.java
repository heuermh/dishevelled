/*

    dsh-venn  Lightweight components for venn diagrams.
    Copyright (c) 2009 held jointly by the individual authors.

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
package org.dishevelled.venn;

import ca.odell.glazedlists.EventList;

/**
 * Binary venn diagram model.
 *
 * @param <E> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface BinaryVennModel<E>
{

    /**
     * Return a mutable observable list view of the first set for
     * this binary venn model.  Changes made to this list view are
     * reflected in the other views provided by this venn model.
     * Changes made to the original set are not.
     *
     * @return an observable list view of the first set for
     *    this binary venn model
     */
    EventList<E> list0();

    /**
     * Return a mutable observable list view of the second set for
     * this binary venn model.  Changes made to this list view are
     * reflected in the other views provided by this venn model.
     * Changes made to the original set are not.
     *
     * @return an observable list view of the second set for
     *    this binary venn model
     */
    EventList<E> list1();

    /**
     * Return an immutable observable list view of the intersection of
     * the first and second sets for this binary venn model.
     *
     * @return an immutable observable list view of the intersection of
     *    the first and second sets for this binary venn model
     */
    EventList<E> intersection();

    /**
     * Return an immutable observable list view of the union of
     * the first and second sets for this binary venn model.
     *
     * @return an immutable observable list view of the union of
     *    the first and second sets for this binary venn model
     */
    EventList<E> union();
}