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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import java.util.Arrays;
import java.util.Map;

import junit.framework.TestCase;

/**
 * Abstract unit test for implementations of TimerReport.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractTimerReportTest
    extends TestCase
{
    /** Runnable. */
    protected static final Runnable RUNNABLE = new Runnable() {
            /** {@inheritDoc} */
            public void run()
            {
                // empty
            }
        };

    /**
     * Create and return a new instance of an implementation of TimerReport to test.
     *
     * @return a new instance of an implementation of TimerReport to test
     */
    protected abstract TimerReport createTimerReport();

    public final void testCreateTimerReport()
    {
        TimerReport timerReport = createTimerReport();
        assertNotNull(timerReport);
    }

    public final void testAppend() throws IOException
    {
        Map<Runnable, Timer> timers = Timer.loop(Arrays.asList(new Runnable[] { RUNNABLE }), 1);
        TimerReport timerReport = createTimerReport();
        StringBuffer appendable = new StringBuffer();
        appendable = timerReport.append(timers, appendable);
        assertNotNull(appendable);

        try
        {
            timerReport.append(null, null);
            fail("append(null, null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            timerReport.append(null, appendable);
            fail("append(null, ) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            timerReport.append(timers, null);
            fail("append(, null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public final void testWriteFile() throws IOException
    {
        Map<Runnable, Timer> timers = Timer.loop(Arrays.asList(new Runnable[] { RUNNABLE }), 1);
        TimerReport timerReport = createTimerReport();
        File file = File.createTempFile("abstractTimerReportTest", null);
        timerReport.write(timers, file);

        try
        {
            timerReport.write(null, file);
            fail("write(, null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            timerReport.write(timers, (File) null);
            fail("write(, (File) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            timerReport.write(null, (File) null);
            fail("write(null, (File) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public final void testWriteOutputStream() throws IOException
    {
        Map<Runnable, Timer> timers = Timer.loop(Arrays.asList(new Runnable[] { RUNNABLE }), 1);
        TimerReport timerReport = createTimerReport();
        OutputStream outputStream = new ByteArrayOutputStream();
        timerReport.write(timers, outputStream);

        try
        {
            timerReport.write(null, outputStream);
            fail("write(, null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            timerReport.write(timers, (OutputStream) null);
            fail("write(, (OutputStream) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        try
        {
            timerReport.write(null, (OutputStream) null);
            fail("write(null, (OutputStream) null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }
}