/*

    dsh-observable  Observable decorators for collection and map interfaces.
    Copyright (c) 2003-2013 held jointly by the individual authors.

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
package org.dishevelled.observable.event;

import java.util.EventObject;

import org.dishevelled.observable.ObservableQueue;

/**
 * An event object representing a vetoable change made to
 * an observable queue.
 *
 * @param <E> queue element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class VetoableQueueChangeEvent<E>
    extends EventObject
{

    /**
     * Create a new vetoable queue change event with the
     * specified observable queue as the event source.
     *
     * @param source source of the event
     */
    public VetoableQueueChangeEvent(final ObservableQueue<E> source)
    {
        super(source);
    }


    /**
     * Return the source of this vetoable queue change event as an
     * <code>ObservableQueue</code>.
     *
     * @return the source of this vetoable queue change event as an
     *    <code>ObservableQueue</code>
     */
    public final ObservableQueue<E> getObservableQueue()
    {
        return (ObservableQueue<E>) super.getSource();
    }
}
