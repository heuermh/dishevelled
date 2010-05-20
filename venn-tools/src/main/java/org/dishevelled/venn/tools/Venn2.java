/*

    dsh-venn-tools  Command line tools for venn diagrams.
    Copyright (c) 2010 held jointly by the individual authors.

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

import java.io.File;

import org.dishevelled.commandline.Argument;
import org.dishevelled.commandline.ArgumentList;
import org.dishevelled.commandline.CommandLine;
import org.dishevelled.commandline.CommandLineParseException;
import org.dishevelled.commandline.CommandLineParser;
import org.dishevelled.commandline.Switch;
import org.dishevelled.commandline.Usage;

import org.dishevelled.commandline.argument.FileArgument;

/**
 * Venn2.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class Venn2
    implements Runnable
{
    /** True to output count of set elements only. */
    private final boolean count;

    /** First input file. */
    private final File first;

    /** Second input file. */
    private final File second;

    /** First only output file, if any. */
    private final File firstOnly;

    /** Second only output file, if any. */
    private final File secondOnly;

    /** Intersection output file, if any. */
    private final File intersection;

    /** Union output file, if any. */
    private final File union;

    /** Usage string. */
    private static final String USAGE = "java Venn2 [args] first second";

    /** Marker file, write to stdout. */
    private static final File STDOUT = new File(".");


    /**
     * Create a new venn 2 with the specified arguments.
     *
     * @param count true to output count of set elements only
     * @param first first input file
     * @param second second input file
     * @param firstOnly first only output file
     * @param secondOnly second only output file
     * @param intersection intersection output file
     * @param union union output file
     */
    private Venn2(final boolean count,
                  final File first,
                  final File second,
                  final File firstOnly,
                  final File secondOnly,
                  final File intersection,
                  final File union)
    {
        this.count = count;
        this.first = first;
        this.second = second;
        this.firstOnly = firstOnly;
        this.secondOnly = secondOnly;
        this.intersection = intersection;
        this.union = union;
    }


    /** {@inheritDoc} */
    public void run()
    {
        System.out.println("count=" + count);
        System.out.println("first=" + first);
        System.out.println("second=" + second);
        System.out.println("firstOnly=" + firstOnly);
        System.out.println("secondOnly=" + secondOnly);
        System.out.println("intersection=" + intersection);
        System.out.println("union=" + union);
    }


    /**
     * Main.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args)
    {
        CommandLine commandLine = null;
        ArgumentList arguments = null;
        try
        {
            Switch help = new Switch("h", "help", "display help message");
            Switch count = new Switch("c", "count", "output count of set elements only");
            FileArgument firstOnly = new FileArgument("f", "first-only", "first only output file", false);
            FileArgument secondOnly = new FileArgument("s", "second-only", "second only output file", false);
            FileArgument intersection = new FileArgument("i", "intersection", "intersection output file", false);
            FileArgument union = new FileArgument("u", "union", "union output file", false);

            arguments = new ArgumentList(help, count, firstOnly, secondOnly, intersection, union);
            commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            if (help.wasFound())
            {
                Usage.usage(USAGE, null, commandLine, arguments, System.out);
            }
            else
            {
                if (args.length < 2)
                {
                    throw new IllegalArgumentException("must have at least two file arguments, first and second input files");
                }
                File first = new File(args[args.length - 2]);
                File second = new File(args[args.length - 1]);
                File f = defaultIfFound(firstOnly, first, second, STDOUT);
                File s = defaultIfFound(secondOnly, first, second, STDOUT);
                File i = defaultIfFound(intersection, first, second, STDOUT);
                File u = defaultIfFound(union, first, second, STDOUT);
                new Venn2(count.wasFound(), first, second, f, s, i, u).run();
            }
        }
        catch (CommandLineParseException e)
        {
            Usage.usage(USAGE, e, commandLine, arguments, System.err);
        }
        catch (IllegalArgumentException e)
        {
            Usage.usage(USAGE, e, commandLine, arguments, System.err);
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
    private static <T> T defaultIfFound(final Argument<T> argument, final T value0, final T value1, final T defaultValue)
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
}