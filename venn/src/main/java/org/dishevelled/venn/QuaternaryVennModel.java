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
 * Quaternary venn diagram model.
 *
 * @param <E> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface QuaternaryVennModel<E>
{

    /**
     * Return a mutable observable list view of the first set for
     * this quaternary venn model.  Changes made to this list view are
     * reflected in the other views provided by this venn model.
     * Changes made to the original set are not.
     *
     * @return an observable list view of the first set for
     *    this quaternary venn model
     */
    EventList<E> first();

    /**
     * Return a mutable observable list view of the second set for
     * this quaternary venn model.  Changes made to this list view are
     * reflected in the other views provided by this venn model.
     * Changes made to the original set are not.
     *
     * @return an observable list view of the second set for
     *    this quaternary venn model
     */
    EventList<E> second();

    /**
     * Return a mutable observable list view of the third set for
     * this quaternary venn model.  Changes made to this list view are
     * reflected in the other views provided by this venn model.
     * Changes made to the original set are not.
     *
     * @return an observable list view of the third set for
     *    this quaternary venn model
     */
    EventList<E> third();

    /**
     * Return a mutable observable list view of the fourth set for
     * this quaternary venn model.  Changes made to this list view are
     * reflected in the other views provided by this venn model.
     * Changes made to the original set are not.
     *
     * @return an observable list view of the fourth set for
     *    this quaternary venn model
     */
    EventList<E> fourth();

    /**
     * Return an immutable observable list view of the intersection of
     * the first, second, third, and fourth sets for this quaternary venn model.
     *
     * @return an immutable observable list view of the intersection of
     *    the first, second, third, and fourth sets for this quaternary venn model
     */
    EventList<E> intersection();

    /**
     * Return an immutable observable list view of the intersection of
     * the two specified sets from this quaternary venn model.
     *
     * @param a first set to intersect, must be one of {@link #first()},
     *    {@link #second()}, {@link #third()}, or {@link #fourth()}
     * @param b second set to intersect, must be one of {@link #first()},
     *    {@link #second()}, {@link #third()}, or {@link #fourth()}
     * @return an immutable observable list view of the intersection of
     *    the the two specified sets from this quaternary venn model
     */
    EventList<E> intersect(EventList<E> a, EventList<E> b);

    /**
     * Return an immutable observable list view of the intersection of
     * the three specified sets from this quaternary venn model.
     *
     * @param a first set to intersect, must be one of {@link #first()},
     *    {@link #second()}, {@link #third()}, or {@link #fourth()}
     * @param b second set to intersect, must be one of {@link #first()},
     *    {@link #second()}, {@link #third()}, or {@link #fourth()}
     * @param c third set to intersect, must be one of {@link #first()},
     *    {@link #second()}, {@link #third()}, or {@link #fourth()}
     * @return an immutable observable list view of the intersection of
     *    the the three specified sets from this quaternary venn model
     */
    EventList<E> intersect(EventList<E> a, EventList<E> b, EventList<E> c);

    /**
     * Return an immutable observable list view of the union of
     * the first, second, third, and fourth sets for this quaternary venn model.
     *
     * @return an immutable observable list view of the union of
     *    the first, second, third, and fourth sets for this quaternary venn model
     */
    EventList<E> union();

    /**
     * Return an immutable observable list view of the union of
     * the two specified sets from this quaternary venn model.
     *
     * @param a first set to union, must be one of {@link #first()},
     *    {@link #second()}, {@link #third()}, or {@link #fourth()}
     * @param b second set to union, must be one of {@link #first()},
     *    {@link #second()}, {@link #third()}, or {@link #fourth()}
     * @return an immutable observable list view of the union of
     *    the two specified sets from this quaternary venn model
     */
    EventList<E> union(EventList<E> a, EventList<E> b);

    /**
     * Return an immutable observable list view of the union of
     * the three specified sets from this quaternary venn model.
     *
     * @param a first set to union, must be one of {@link #first()},
     *    {@link #second()}, {@link #third()}, or {@link #fourth()}
     * @param b second set to union, must be one of {@link #first()},
     *    {@link #second()}, {@link #third()}, or {@link #fourth()}
     * @param c third set to union, must be one of {@link #first()},
     *    {@link #second()}, {@link #third()}, or {@link #fourth()}
     * @return an immutable observable list view of the union of
     *    the three specified sets from this quaternary venn model
     */
    EventList<E> union(EventList<E> a, EventList<E> b, EventList<E> c);
}