/*

    dsh-observable  Observable decorators for collection and map interfaces.
    Copyright (c) 2003-2007 held jointly by the individual authors.

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
package org.dishevelled.observable.simple;

import java.util.SortedSet;

import org.dishevelled.observable.AbstractObservableSortedSet;

import org.dishevelled.observable.event.SortedSetChangeEvent;
import org.dishevelled.observable.event.VetoableSortedSetChangeEvent;

/**
 * Observable sorted set decorator that simply fires empty
 * vetoable sorted set change events in <code>preXxx</code> methods and
 * empty sorted set change events in <code>postXxx</code> methods.
 * Observable sorted set listeners may query the source of the events to determine
 * what may or may not have changed due to the event.
 *
 * @param <E> sorted set element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class SimpleObservableSortedSet<E>
    extends AbstractObservableSortedSet<E>
{
    /** Cached sorted set change event. */
    private final SortedSetChangeEvent<E> changeEvent;

    /** Cached vetoable sorted set change event. */
    private final VetoableSortedSetChangeEvent<E> vetoableChangeEvent;


    /**
     * Create a new observable decorator for the specified
     * sorted set.
     *
     * @param sortedSet sorted set to decorate, must not be null
     */
    public SimpleObservableSortedSet(final SortedSet<E> sortedSet)
    {
        super(sortedSet);
        changeEvent = new SortedSetChangeEvent<E>(this);
        vetoableChangeEvent = new VetoableSortedSetChangeEvent<E>(this);
    }


    // TODO:
    // implement pre/post methods


}