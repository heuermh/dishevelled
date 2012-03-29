/*

    dsh-timer  Timer with nanosecond resolution and summary statistics
    on recorded elapsed times.
    Copyright (c) 2004-2012 held jointly by the individual authors.

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
package org.dishevelled.timer.report;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import java.util.Map;

import org.dishevelled.timer.Timer;
import org.dishevelled.timer.TimerReport;

/**
 * Abstract implementation of TimerReport.
 */
abstract class AbstractTimerReport
    implements TimerReport
{

    /** {@inheritDoc} */
    public final void write(final Map<? extends Runnable, Timer> timers, final File file) throws IOException
    {
        if (timers == null)
        {
            throw new IllegalArgumentException("timers must not be null");
        }
        if (file == null)
        {
            throw new IllegalArgumentException("file must not be null");
        }
        Writer writer = null;
        try
        {
            writer = new BufferedWriter(new FileWriter(file));
            append(timers, writer);
        }
        finally
        {
            closeQuietly(writer);
        }
    }

    /** {@inheritDoc} */
    public final void write(final Map<? extends Runnable, Timer> timers, final OutputStream outputStream) throws IOException
    {
        if (timers == null)
        {
            throw new IllegalArgumentException("timers must not be null");
        }
        if (outputStream == null)
        {
            throw new IllegalArgumentException("outputStream must not be null");
        }
        Writer writer = new BufferedWriter(new OutputStreamWriter(outputStream));
        append(timers, writer);
        writer.flush();
    }

    private static void closeQuietly(final Writer writer)
    {
        try
        {
            writer.close();
        }
        catch (Exception e)
        {
            // ignore
        }
    }
}