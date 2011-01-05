/*

    dsh-venn-tools  Command line tools for venn diagrams.
    Copyright (c) 2010-2011 held jointly by the individual authors.

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
package org.dishevelled.venn.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.dishevelled.commandline.Argument;

/**
 * Abstract venn runnable.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
abstract class AbstractVennRunnable
    implements Runnable
{
    /** True to output count(s) only. */
    private final boolean count;

    /** True to output header(s). */
    private final boolean header;

    /** Marker file, write to stdout. */
    protected static final File STDOUT = new File(".");


    /**
     * Create a new abstract venn runnable with the specified arguments.
     *
     * @param count true to output count(s) only
     * @param header true to ouput header(s)
     */
    protected AbstractVennRunnable(final boolean count, final boolean header)
    {
        this.count = count;
        this.header = header;
    }


    /**
     * Return true to output count(s) only.
     *
     * @return true to output count(s) only
     */
    protected final boolean count()
    {
        return count;
    }

    /**
     * Return true to output header(s).
     *
     * @return true to output header(s)
     */
    protected final boolean header()
    {
        return header;
    }

    /**
     * Write the specified label text to stdout.
     *
     * @param write true to write
     * @param labelText label text to write
     * @param stdout stdout
     */
    protected static final void write(final boolean write, final String labelText, final PrintWriter stdout)
    {
        if (write)
        {
            stdout.print(labelText);
            stdout.print("\t");
        }
    }

    /**
     * Write the specified size to stdout.
     *
     * @param write true to write
     * @param size size to write
     * @param stdout stdout
     */
    protected static final void write(final boolean write, final int size, final PrintWriter stdout)
    {
        if (write)
        {
            stdout.print(size);
            stdout.print("\t");
        }
    }

    /**
     * Write one value from the specified iterator to stdout.
     *
     * @param write true to write
     * @param it iterator to write from
     * @param stdout stdout
     */
    protected static final void write(final boolean write, final Iterator<String> it, final PrintWriter stdout)
    {
        if (write)
        {
            if (it.hasNext())
            {
                stdout.print(it.next());
            }
            stdout.print("\t");
        }
    }

    /**
     * Read a set of strings from the specified input file.
     *
     * @param input input file
     * @return a set of strings
     */
    protected static final Set<String> read(final File input)
    {
        BufferedReader reader = null;
        Set<String> result = new HashSet<String>(Math.max(16, (int) input.length() / 64));
        try
        {
            reader = new BufferedReader(new FileReader(input));
            while (reader.ready())
            {
                result.add(reader.readLine().trim());
            }
        }
        catch (IOException e)
        {
            //throw new IllegalArgumentException("could not read input file " + input, e);  // jdk 1.6+
            throw new IllegalArgumentException("could not read input file " + input + ", " + e.getMessage());
        }
        finally
        {
            try
            {
                reader.close();
            }
            catch (Exception e)
            {
                // ignore
            }
        }
        return result;
    }

    /**
     * Write the specified set view to the specified file if valid.
     *
     * @param headerText header text
     * @param view view
     * @param file file
     */
    protected void write(final String headerText, final Set<String> view, final File file)
    {
        if (file == null || STDOUT.equals(file))
        {
            return;
        }
        PrintWriter writer = null;
        try
        {
            writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            if (header)
            {
                writer.println(headerText);
            }
            if (count)
            {
                writer.println(view.size());
            }
            else
            {
                for (String s : view)
                {
                    writer.println(s);
                }
            }
        }
        catch (IOException e)
        {
            //throw new IllegalArgumentException("could not write to output file " + file, e); // jdk 1.6+
            throw new IllegalArgumentException("could not write to output file " + file + ", " + e.getMessage());
        }
        finally
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

    /**
     * Default to the specified default value if the argument was found and has a null
     * value or if the value matches either of <code>value0</code> or <code>value1</code>.
     *
     * @param <T> argument type
     * @param argument argument
     * @param value0 first value
     * @param value1 second value
     * @param defaultValue default value
     * @return the specified default value if the argument was found and has a null
     *    value or if the value matches either of <code>value0</code> or <code>value1</code>
     */
    protected static final <T> T defaultIfFound(final Argument<T> argument, final T value0, final T value1, final T defaultValue)
    {
        if (argument.wasFound())
        {
            if (argument.getValue() == null)
            {
                return defaultValue;
            }
            else
            {
                if (value0.equals(argument.getValue()) || value1.equals(argument.getValue()))
                {
                    return defaultValue;
                }
                return argument.getValue();
            }
        }
        return null;
    }

    /**
     * Default to the specified default value if the argument was found and has a null
     * value or if the value matches either of <code>value0</code>, <code>value1</code>,
     * or <code>value2</code>.
     *
     * @param <T> argument type
     * @param argument argument
     * @param value0 first value
     * @param value1 second value
     * @param value2 third value
     * @param defaultValue default value
     * @return the specified default value if the argument was found and has a null
     *    value or if the value matches either of <code>value0</code>, <code>value1</code>,
     *    or <code>value2</code>
     */
    protected static final <T> T defaultIfFound(final Argument<T> argument, final T value0, final T value1, final T value2, final T defaultValue)
    {
        if (argument.wasFound())
        {
            if (argument.getValue() == null)
            {
                return defaultValue;
            }
            else
            {
                if (value0.equals(argument.getValue()) || value1.equals(argument.getValue()) || value2.equals(argument.getValue()))
                {
                    return defaultValue;
                }
                return argument.getValue();
            }
        }
        return null;
    }

    /**
     * Default to the specified default value if the argument was found and has a null
     * value or if the value matches either of <code>value0</code>, <code>value1</code>,
     * <code>value2</code>, or <code>value3</code>.
     *
     * @param <T> argument type
     * @param argument argument
     * @param value0 first value
     * @param value1 second value
     * @param value2 third value
     * @param value3 fourth value
     * @param defaultValue default value
     * @return the specified default value if the argument was found and has a null
     *    value or if the value matches either of <code>value0</code>, <code>value1</code>,
     *    <code>value2</code>, or <code>value3</code>
     */
    protected static final <T> T defaultIfFound(final Argument<T> argument, final T value0, final T value1, final T value2, final T value3, final T defaultValue)
    {
        if (argument.wasFound())
        {
            if (argument.getValue() == null)
            {
                return defaultValue;
            }
            else
            {
                if (value0.equals(argument.getValue()) || value1.equals(argument.getValue()) || value2.equals(argument.getValue()) || value3.equals(argument.getValue()))
                {
                    return defaultValue;
                }
                return argument.getValue();
            }
        }
        return null;
    }
}