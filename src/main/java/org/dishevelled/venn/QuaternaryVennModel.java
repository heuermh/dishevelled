/*

    dsh-venn  Lightweight components for venn diagrams.
    Copyright (c) 2009-2013 held jointly by the individual authors.

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

import java.util.Set;

import org.dishevelled.observable.ObservableSet;

/**
 * Quaternary venn diagram model.
 *
 * @param <E> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface QuaternaryVennModel<E> extends VennModel<E>
{

    /**
     * Return a mutable observable view of the first set for
     * this quaternary venn model.  Changes made to this view are
     * reflected in the other views provided by this venn model.
     * Changes made to the original set are not.
     *
     * @return an observable view of the first set for
     *    this quaternary venn model
     */
    ObservableSet<E> first();

    /**
     * Return a mutable observable view of the second set for
     * this quaternary venn model.  Changes made to this view are
     * reflected in the other views provided by this venn model.
     * Changes made to the original set are not.
     *
     * @return an observable view of the second set for
     *    this quaternary venn model
     */
    ObservableSet<E> second();

    /**
     * Return a mutable observable view of the third set for
     * this quaternary venn model.  Changes made to this view are
     * reflected in the other views provided by this venn model.
     * Changes made to the original set are not.
     *
     * @return an observable view of the third set for
     *    this quaternary venn model
     */
    ObservableSet<E> third();

    /**
     * Return a mutable observable view of the fourth set for
     * this quaternary venn model.  Changes made to this view are
     * reflected in the other views provided by this venn model.
     * Changes made to the original set are not.
     *
     * @return an observable view of the fourth set for
     *    this quaternary venn model
     */
    ObservableSet<E> fourth();

    /**
     * Return an immutable view of the elements unique to the first
     * set for this quaternary venn model.  The first only view will reflect changes
     * made to the first, second, third, and fourth views (i.e. {@link #first()},
     * {@link #second()}, {@link #third()}, and {@link #fourth()}).
     *
     * @return an immutable view of the elements unique to the first
     *    set for this quaternary venn model
     */
    Set<E> firstOnly();

    /**
     * Return an immutable view of the elements unique to the second
     * set for this quaternary venn model.  The second only view will reflect changes
     * made to the first, second, third, and fourth views (i.e. {@link #first()},
     * {@link #second()}, {@link #third()}, and {@link #fourth()}).
     *
     * @return an immutable view of the elements unique to the second
     *    set for this quaternary venn model
     */
    Set<E> secondOnly();

    /**
     * Return an immutable view of the elements unique to the third
     * set for this quaternary venn model.  The third only view will reflect changes
     * made to the first, second, third, and fourth views (i.e. {@link #first()},
     * {@link #second()}, {@link #third()}, and {@link #fourth()}).
     *
     * @return an immutable view of the elements unique to the third
     *    set for this quaternary venn model
     */
    Set<E> thirdOnly();

    /**
     * Return an immutable view of the elements unique to the fourth
     * set for this quaternary venn model.  The fourth only view will reflect changes
     * made to the first, second, third, and fourth views (i.e. {@link #first()},
     * {@link #second()}, {@link #third()}, and {@link #fourth()}).
     *
     * @return an immutable view of the elements unique to the fourth
     *    set for this quaternary venn model
     */
    Set<E> fourthOnly();

    /**
     * Return an immutable view of the elements unique to the intersection
     * of the first and second sets for this quaternary venn model.  The first and second
     * view will reflect changes made to the first, second, third, and fourth views
     * (i.e. {@link #first()}, {@link #second()}, {@link #third()}, and {@link #fourth()}).
     *
     * @return an immutable view of the elements unique to the intersection
     *    of the first and second sets for this quaternary venn model
     */
    Set<E> firstSecond();

    /**
     * Return an immutable view of the elements unique to the intersection
     * of the first and third sets for this quaternary venn model.  The first and third
     * view will reflect changes made to the first, second, third, and fourth views
     * (i.e. {@link #first()}, {@link #second()}, {@link #third()}, and {@link #fourth()}).
     *
     * @return an immutable view of the elements unique to the intersection
     *    of the first and third sets for this quaternary venn model
     */
    Set<E> firstThird();

    /**
     * Return an immutable view of the elements unique to the intersection
     * of the first and fourth sets for this quaternary venn model.  The first and fourth
     * view will reflect changes made to the first, second, third, and fourth views
     * (i.e. {@link #first()}, {@link #second()}, {@link #third()}, and {@link #fourth()}).
     *
     * @return an immutable view of the elements unique to the intersection
     *    of the first and fourth sets for this quaternary venn model
     */
    Set<E> firstFourth();

    /**
     * Return an immutable view of the elements unique to the intersection
     * of the second and third sets for this quaternary venn model.  The second and third
     * view will reflect changes made to the first, second, third, and fourth views
     * (i.e. {@link #first()}, {@link #second()}, {@link #third()}, and {@link #fourth()}).
     *
     * @return an immutable view of the elements unique to the intersection
     *    of the second and third sets for this quaternary venn model
     */
    Set<E> secondThird();

    /**
     * Return an immutable view of the elements unique to the intersection
     * of the second and fourth sets for this quaternary venn model.  The second and fourth
     * view will reflect changes made to the first, second, third, and fourth views
     * (i.e. {@link #first()}, {@link #second()}, {@link #third()}, and {@link #fourth()}).
     *
     * @return an immutable view of the elements unique to the intersection
     *    of the second and fourth sets for this quaternary venn model
     */
    Set<E> secondFourth();

    /**
     * Return an immutable view of the elements unique to the intersection
     * of the third and fourth sets for this quaternary venn model.  The third and fourth
     * view will reflect changes made to the first, second, third, and fourth views
     * (i.e. {@link #first()}, {@link #second()}, {@link #third()}, and {@link #fourth()}).
     *
     * @return an immutable view of the elements unique to the intersection
     *    of the third and fourth sets for this quaternary venn model
     */
    Set<E> thirdFourth();

    /**
     * Return an immutable view of the elements unique to the intersection
     * of the first, second, and third sets for this quaternary venn model.  The first, second,
     * and third view will reflect changes made to the first, second, third, and fourth views
     * (i.e. {@link #first()}, {@link #second()}, {@link #third()}, and {@link #fourth()}).
     *
     * @return an immutable view of the elements unique to the intersection
     *    of the first, second, and third sets for this quaternary venn model
     */
    Set<E> firstSecondThird();

    /**
     * Return an immutable view of the elements unique to the intersection
     * of the first, second, and fourth sets for this quaternary venn model.  The first, second,
     * and fourth view will reflect changes made to the first, second, third, and fourth views
     * (i.e. {@link #first()}, {@link #second()}, {@link #third()}, and {@link #fourth()}).
     *
     * @return an immutable view of the elements unique to the intersection
     *    of the first, second, and fourth sets for this quaternary venn model
     */
    Set<E> firstSecondFourth();

    /**
     * Return an immutable view of the elements unique to the intersection
     * of the first, third, and fourth sets for this quaternary venn model.  The first, third,
     * and fourth view will reflect changes made to the first, second, third, and fourth views
     * (i.e. {@link #first()}, {@link #second()}, {@link #third()}, and {@link #fourth()}).
     *
     * @return an immutable view of the elements unique to the intersection
     *    of the first, third, and fourth sets for this quaternary venn model
     */
    Set<E> firstThirdFourth();

    /**
     * Return an immutable view of the elements unique to the intersection
     * of the second, third, and fourth sets for this quaternary venn model.  The second, third,
     * and fourth view will reflect changes made to the first, second, third, and fourth views
     * (i.e. {@link #first()}, {@link #second()}, {@link #third()}, and {@link #fourth()}).
     *
     * @return an immutable view of the elements unique to the intersection
     *    of the second, third, and fourth sets for this quaternary venn model
     */
    Set<E> secondThirdFourth();

    /**
     * Return an immutable view of the intersection of the first, second, third, and fourth
     * sets for this quaternary venn model.  The intersection view will reflect changes
     * made to the first, second, third, and fourth views (i.e. {@link #first()},
     * {@link #second()}, {@link #third()}, and {@link #fourth()}).
     *
     * @return an immutable view of the intersection of the first, second, third, and fourth
     *    sets for this quaternary venn model
     */
    Set<E> intersection();

    /**
     * Return an immutable view of the union of the first, second, third, and fourth
     * sets for this quaternary venn model.  The union view will reflect changes
     * made to the first, second, third, and fourth views (i.e. {@link #first()},
     * {@link #second()}, {@link #third()}, and {@link #fourth()}).
     *
     * @return an immutable view of the union of the first, second, third, and fourth
     *    sets for this quaternary venn model
     */
    Set<E> union();

    /**
     * Return a mutable observable view of the selection for this
     * quaternary venn model.  The selection view will reflect changes
     * made to the first, second, third, and fourth views (i.e. {@link #first()},
     * {@link #second()}, {@link #third()}, and {@link #fourth()}), and may
     * not contain any elements not present in the union ({@link #union()}).
     *
     * @return an observable view of the selection for
     *    this quaternary venn model
     */
    ObservableSet<E> selection();
}