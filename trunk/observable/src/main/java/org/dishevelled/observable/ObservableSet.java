/*

    dsh-observable  Observable decorators for collection and map interfaces.
    Copyright (c) 2003-2010 held jointly by the individual authors.

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
package org.dishevelled.observable;

import java.util.Set;

import org.dishevelled.observable.event.SetChangeListener;
import org.dishevelled.observable.event.VetoableSetChangeListener;

/**
 * Observable extension to the <code>Set</code> interface.
 *
 * @param <E> set element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface ObservableSet<E>
    extends Set<E>
{

    /**
     * Add the specified set change listener.
     *
     * @param l set change listener to add
     */
    void addSetChangeListener(SetChangeListener<E> l);

    /**
     * Remove the specified set change listener.
     *
     * @param l set change listener to remove
     */
    void removeSetChangeListener(SetChangeListener<E> l);

    /**
     * Add the specified vetoable set change listener.
     *
     * @param l vetoable set change listener to add
     */
    void addVetoableSetChangeListener(VetoableSetChangeListener<E> l);

    /**
     * Remove the specified vetoable set change listener.
     *
     * @param l vetoable set change listener to remove
     */
    void removeVetoableSetChangeListener(VetoableSetChangeListener<E> l);

    /**
     * Return an array of all <code>SetChangeListener</code>s, or
     * an empty array if none are registered.
     *
     * @return an array of all <code>SetChangeListener</code>s, or
     *    an empty array if none are registered
     */
    SetChangeListener<E>[] getSetChangeListeners();

    /**
     * Return the number of <code>SetChangeListener</code>s registered
     * to this observable set.
     *
     * @return the number of <code>SetChangeListener</code>s registered
     *    to this observable set
     */
    int getSetChangeListenerCount();

    /**
     * Return an array of all <code>VetoableSetChangeListener</code>s,
     * or an empty array if none are registered.
     *
     * @return an array of all <code>VetoableSetChangeListener</code>s,
     *    or an empty array if none are registered
     */
    VetoableSetChangeListener<E>[] getVetoableSetChangeListeners();

    /**
     * Return the number of <code>VetoableSetChangeListener</code>s
     * registered to this observable set.
     *
     * @return the number of <code>VetoableSetChangeListener</code>s
     *    registered to this observable set
     */
    int getVetoableSetChangeListenerCount();
}
