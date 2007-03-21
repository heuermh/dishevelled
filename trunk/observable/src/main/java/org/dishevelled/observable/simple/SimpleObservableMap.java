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

import java.util.Map;

import org.dishevelled.observable.AbstractObservableMap;

/**
 * Observable map decorator that simply fires empty
 * vetoable map change events in <code>preXxx</code> methods and
 * empty map change events in <code>postXxx</code> methods.
 * Observable map listeners may query the source of the events to determine
 * what may or may not have changed due to the event.
 *
 * @param <K> map key type
 * @param <V> map value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class SimpleObservableMap<K,V>
    extends AbstractObservableMap<K,V>
{

    /**
     * Create a new observable decorator for the specified
     * map.
     *
     * @param map map to decorate
     */
    public SimpleObservableMap(final Map<K,V> map)
    {
        super(map);
    }


    // TODO:
    // implement pre/post methods


}
