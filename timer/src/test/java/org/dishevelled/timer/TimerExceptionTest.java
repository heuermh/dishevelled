/*

    dsh-timer  A simple timer with nanosecond resolution and summary
    statistics on recorded elapsed times.
    Copyright (c) 2004-2006 held jointly by the individual authors.

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
package org.dishevelled.timer;

import junit.framework.TestCase;

/**
 * Unit test for TimerException.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class TimerExceptionTest
    extends TestCase
{

    public void testConstructor()
    {
        TimerException te0 = new TimerException();
        assertNotNull("te0 not null", te0);

        TimerException te1 = new TimerException((String) null);
        assertNotNull("te1 not null", te1);

        TimerException te2 = new TimerException("");
        assertNotNull("te2 not null", te2);

        TimerException te3 = new TimerException("detail message");
        assertNotNull("te3 not null", te3);

        TimerException te4 = new TimerException(null, null);
        assertNotNull("te4 not null", te4);

        TimerException te5 = new TimerException("", null);
        assertNotNull("te5 not null", te5);

        TimerException te6 = new TimerException("detail message", null);
        assertNotNull("te6 not null", te6);

        TimerException te7 = new TimerException(null, new RuntimeException("detail message"));
        assertNotNull("te7 not null", te7);

        TimerException te8 = new TimerException("", new RuntimeException("detail message"));
        assertNotNull("te8 not null", te8);

        TimerException te9 = new TimerException("detail message", new RuntimeException("detail message"));
        assertNotNull("te9 not null", te9);

        TimerException te10 = new TimerException((Throwable) null);
        assertNotNull("te10 not null", te10);

        TimerException te11 = new TimerException(new RuntimeException("detail message"));
        assertNotNull("te11 not null", te11);
    }
}
