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
package org.dishevelled.observable.impl;

import java.util.HashMap;

import org.dishevelled.observable.AbstractObservableMapTest;
import org.dishevelled.observable.ObservableMap;

/**
 * Unit test for ObservableMapImpl.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class ObservableMapImplTest
    extends AbstractObservableMapTest
{

    /** {@inheritDoc} */
    protected <K, V> ObservableMap<K, V> createObservableMap()
    {
        return new ObservableMapImpl<K, V>(new HashMap<K, V>());
    }
}