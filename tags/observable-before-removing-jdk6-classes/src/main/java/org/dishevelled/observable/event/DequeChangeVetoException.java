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
package org.dishevelled.observable.event;

import java.io.Serializable;

/**
 * Exception thrown in the event a deque change
 * is vetoed by a <code>VetoableDequeChangeListener</code>.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class DequeChangeVetoException
    extends Exception
    implements Serializable
{

    /**
     * Create a new deque change veto exception.
     */
    public DequeChangeVetoException()
    {
        super();
    }

    /**
     * Create a new deque change veto exception
     * with the specified error message.
     *
     * @param message error message
     */
    public DequeChangeVetoException(final String message)
    {
        super(message);
    }
}