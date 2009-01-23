/*

    dsh-observable  Observable decorators for collection and map interfaces.
    Copyright (c) 2003-2009 held jointly by the individual authors.

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
 * Observable decorator implementations for collection and map interfaces.
 *
 * <h4>Summary</h4>
 *
 * <p>
 * The decorators in this package fire empty vetoable change events in
 * <code>preXxx</code> methods and empty change events in <code>postXxx</code> methods.
 * Listeners may query the source of the events to determine what may or may
 * not have changed due to the event.
 * </p>
 */
package org.dishevelled.observable.impl;