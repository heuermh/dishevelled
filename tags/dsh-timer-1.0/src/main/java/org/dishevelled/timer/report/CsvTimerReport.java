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

import java.io.IOException;

import java.text.NumberFormat;

import java.util.Map;

import org.dishevelled.timer.Timer;

/**
 * Timer report in comma-separated value (CSV) format.
 */
public final class CsvTimerReport
    extends AbstractTimerReport
{
    private final NumberFormat numberFormat;

    public CsvTimerReport(final NumberFormat numberFormat)
    {
        if (numberFormat == null)
        {
            throw new IllegalArgumentException("numberFormat must not be null");
        }
        this.numberFormat = numberFormat;
    }

    /** {@inheritDoc} */
    public <T extends Appendable> T append(final Map<? extends Runnable, Timer> timers, final T appendable)
        throws IOException
    {
        if (timers == null)
        {
            throw new IllegalArgumentException("timers must not be null");
        }
        if (appendable == null)
        {
            throw new IllegalArgumentException("appendable must not be null");
        }
        append(appendable, "Runnable");
        append(appendable, "Size");
        append(appendable, "Min (ns)");
        append(appendable, "Mean (ns)");
        append(appendable, "Max (ns)");
        append(appendable, "Standard Deviation (ns)");
        appendLast(appendable, "Sum (ns)");

        for (Map.Entry<? extends Runnable, Timer> entry : timers.entrySet())
        {
            append(appendable, String.valueOf(entry.getKey()));
            append(appendable, numberFormat.format(entry.getValue().size()));
            append(appendable, numberFormat.format(entry.getValue().min()));
            append(appendable, numberFormat.format(entry.getValue().mean()));
            append(appendable, numberFormat.format(entry.getValue().max()));
            append(appendable, numberFormat.format(entry.getValue().standardDeviation()));
            appendLast(appendable, numberFormat.format(entry.getValue().sum()));
        }
        return appendable;
    }

    private static <T extends Appendable> void append(final T appendable, final String value) throws IOException
    {
        appendable.append("\"");
        appendable.append(value);
        appendable.append("\",");
    }

    private static <T extends Appendable> void appendLast(final T appendable, final String value) throws IOException
    {
        appendable.append("\"");
        appendable.append(value);
        appendable.append("\"\n");
    }
}