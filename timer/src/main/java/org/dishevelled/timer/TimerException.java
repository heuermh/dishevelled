/*

    dsh-timer  Timer with nanosecond resolution and summary statistics
    on recorded elapsed times.
    Copyright (c) 2004-2013 held jointly by the individual authors.

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
package org.dishevelled.timer;

/**
 * Runtime exception thrown in the event that a timer
 * is stopped before it has been started.
 *
 * @see Timer#start
 * @see Timer#stop
 * @author  Michael Heuer
 */
public class TimerException
    extends RuntimeException
{

    /**
     * Create a new timer expection with <code>null</code> as its
     * detail message.
     */
    public TimerException()
    {
        super();
    }

    /**
     * Create a new timer exception with the specified detail message.
     *
     * @param message detail message
     */
    public TimerException(final String message)
    {
        super(message);
    }

    /**
     * Create a new timer exception with the specified detail message and cause.
     *
     * @param message detail message
     * @param cause cause
     */
    public TimerException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

    /**
     * Create a new timer exception with the specified cause.
     *
     * @param cause cause
     */
    public TimerException(final Throwable cause)
    {
        super(cause);
    }
}
