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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.dishevelled.commandline.Argument;
import org.dishevelled.commandline.ArgumentList;
import org.dishevelled.commandline.CommandLine;
import org.dishevelled.commandline.CommandLineParseException;
import org.dishevelled.commandline.CommandLineParser;
import org.dishevelled.commandline.Switch;
import org.dishevelled.commandline.Usage;

import org.dishevelled.commandline.argument.FileArgument;

import org.dishevelled.venn.TernaryVennModel3;

import org.dishevelled.venn.swing.TernaryVennLabel3;

/**
 * Venn3.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class Venn3
    implements Runnable
{
    /** True to output count(s) only. */
    private final boolean count;

    /** True to output header(s). */
    private final boolean header;

    /** First input file. */
    private final File first;

    /** Second input file. */
    private final File second;

    /** Third input file. */
    private final File third;

    /** First only output file, if any. */
    private final File firstOnly;

    /** Second only output file, if any. */
    private final File secondOnly;

    /** Third only output file, if any. */
    private final File thirdOnly;

    /** First second output file, if any. */
    private final File firstSecond;

    /** Second third output file, if any. */
    private final File secondThird;

    /** Intersection output file, if any. */
    private final File intersection;

    /** Union output file, if any. */
    private final File union;

    /** Usage string. */
    private static final String USAGE = "java Venn3 [args] first second third";

    /** Marker file, write to stdout. */
    private static final File STDOUT = new File(".");


    /**
     * Create a new venn 3 with the specified arguments.
     *
     * @param count true to output count(s) only
     * @param header true to ouput header(s)
     * @param first first input file
     * @param second second input file
     * @param third third input file
     * @param firstOnly first only output file
     * @param secondOnly second only output file
     * @param thirdOnly third only output file
     * @param firstSecond first second output file
     * @param secondThird second third output file
     * @param intersection intersection output file
     * @param union union output file
     */
    private Venn3(final boolean count,
                  final boolean header,
                  final File first,
                  final File second,
                  final File third,
                  final File firstOnly,
                  final File secondOnly,
                  final File thirdOnly,
                  final File firstSecond,
                  final File secondThird,
                  final File intersection,
                  final File union)
    {
        this.count = count;
        this.header = header;
        this.first = first;
        this.second = second;
        this.third = third;
        this.firstOnly = firstOnly;
        this.secondOnly = secondOnly;
        this.thirdOnly = thirdOnly;
        this.firstSecond = firstSecond;
        this.secondThird = secondThird;
        this.intersection = intersection;
        this.union = union;
    }


    /** {@inheritDoc} */
    public void run()
    {
        TernaryVennLabel3<String> label = new TernaryVennLabel3<String>(first.getName(), read(first), second.getName(), read(second), third.getName(), read(third));
        TernaryVennModel3<String> model = label.getModel();

        // write individually to output files first
        write(label.getFirstOnlyLabelText(), model.firstOnly(), firstOnly);
        write(label.getSecondOnlyLabelText(), model.secondOnly(), secondOnly);
        write(label.getThirdOnlyLabelText(), model.thirdOnly(), thirdOnly);
        write(label.getFirstSecondLabelText(), model.firstSecond(), firstSecond);
        write(label.getSecondThirdLabelText(), model.secondThird(), secondThird);
        write(label.getIntersectionLabelText(), model.intersection(), intersection);
        write(label.getUnionLabelText(), model.union(), union);

        // write collectively to stdout next
        boolean writeFirstOnly = (firstOnly != null);
        boolean writeSecondOnly = (secondOnly != null);
        boolean writeThirdOnly = (thirdOnly != null);
        boolean writeFirstSecond = (firstSecond != null);
        boolean writeSecondThird = (secondThird != null);
        boolean writeIntersection = (intersection != null);
        boolean writeUnion = (intersection != null);

        PrintWriter stdout = null;
        stdout = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        if (header)
        {
            if (writeFirstOnly)
            {
                stdout.print(label.getFirstOnlyLabelText() + "\t");
            }
            if (writeSecondOnly)
            {
                stdout.print(label.getSecondOnlyLabelText() + "\t");
            }
            if (writeThirdOnly)
            {
                stdout.print(label.getThirdOnlyLabelText() + "\t");
            }
            if (writeFirstSecond)
            {
                stdout.print(label.getFirstSecondLabelText() + "\t");
            }
            if (writeSecondThird)
            {
                stdout.print(label.getSecondThirdLabelText() + "\t");
            }
            if (writeIntersection)
            {
                stdout.print(label.getIntersectionLabelText() + "\t");
            }
            if (writeUnion)
            {
                stdout.print(label.getUnionLabelText() + "\t");
            }
            // todo trim extra \t
            stdout.print("\n");
        }

        if (count)
        {
            if (writeFirstOnly)
            {
                stdout.print(model.firstOnly().size() + "\t");
            }
            if (writeSecondOnly)
            {
                stdout.print(model.secondOnly().size() + "\t");
            }
            if (writeThirdOnly)
            {
                stdout.print(model.thirdOnly().size() + "\t");
            }
            if (writeFirstSecond)
            {
                stdout.print(model.firstSecond().size() + "\t");
            }
            if (writeSecondThird)
            {
                stdout.print(model.secondThird().size() + "\t");
            }
            if (writeIntersection)
            {
                stdout.print(model.intersection().size() + "\t");
            }
            if (writeUnion)
            {
                stdout.print(model.union().size() + "\t");
            }
            // todo trim extra \t
            stdout.print("\n");
        }
        else
        {
            boolean remaining = writeFirstOnly || writeSecondOnly || writeThirdOnly || writeFirstSecond || writeSecondThird || writeIntersection || writeUnion;
            Iterator<String> fo = model.firstOnly().iterator();
            Iterator<String> so = model.secondOnly().iterator();
            Iterator<String> to = model.thirdOnly().iterator();
            Iterator<String> fs = model.firstSecond().iterator();
            Iterator<String> st = model.secondThird().iterator();
            Iterator<String> i = model.intersection().iterator();
            Iterator<String> u = model.union().iterator();

            while (remaining)
            {
                if (writeFirstOnly && fo.hasNext())
                {
                    stdout.print(fo.next() + "\t");
                }
                if (writeSecondOnly && so.hasNext())
                {
                    stdout.print(so.next() + "\t");
                }
                if (writeThirdOnly && to.hasNext())
                {
                    stdout.print(to.next() + "\t");
                }
                if (writeFirstSecond && fs.hasNext())
                {
                    stdout.print(fs.next() + "\t");
                }
                if (writeSecondThird && st.hasNext())
                {
                    stdout.print(st.next() + "\t");
                }
                if (writeIntersection && i.hasNext())
                {
                    stdout.print(i.next() + "\t");
                }
                if (writeUnion && u.hasNext())
                {
                    stdout.print(u.next() + "\t");
                }
                remaining = fo.hasNext() || so.hasNext() || to.hasNext() || fs.hasNext() || st.hasNext() || i.hasNext() || u.hasNext();

                // todo trim extra \t
                stdout.print("\n");
            }
        }
        try
        {
            stdout.close();
        }
        catch (Exception e)
        {
            // ignore
        }
    }

    /**
     * Read a set of strings from the specified input file.
     *
     * @param input input file
     * @return a set of strings
     */
    private static Set<String> read(final File input)
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
    private void write(final String headerText, final Set<String> view, final File file)
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
            Switch count = new Switch("c", "count", "output count(s) only");
            Switch header = new Switch("e", "header", "output header(s)");
            FileArgument firstOnly = new FileArgument("f", "first-only", "first only output file", false);
            FileArgument secondOnly = new FileArgument("s", "second-only", "second only output file", false);
            FileArgument thirdOnly = new FileArgument("t", "third-only", "third only output file", false);
            FileArgument firstSecond = new FileArgument("j", "first-second", "first second output file", false);
            FileArgument secondThird = new FileArgument("k", "second-third", "second third output file", false);
            FileArgument intersection = new FileArgument("i", "intersection", "intersection output file", false);
            FileArgument union = new FileArgument("u", "union", "union output file", false);

            arguments = new ArgumentList(help, count, header, firstOnly, secondOnly, thirdOnly, firstSecond, secondThird, intersection, union);
            commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            if (help.wasFound())
            {
                Usage.usage(USAGE, null, commandLine, arguments, System.out);
            }
            else
            {
                if (args.length < 3)
                {
                    throw new IllegalArgumentException("must have at least two file arguments, first, second, and third input files");
                }
                File first = new File(args[args.length - 3]);
                File second = new File(args[args.length - 2]);
                File third = new File(args[args.length - 1]);
                if (first.getName().startsWith("-") || second.getName().startsWith("-") || third.getName().startsWith("-"))
                {
                    throw new IllegalArgumentException("must have at least two file arguments, first, second, and third input files");
                }
                File f = defaultIfFound(firstOnly, first, second, third, STDOUT);
                File s = defaultIfFound(secondOnly, first, second, third, STDOUT);
                File t = defaultIfFound(thirdOnly, first, second, third, STDOUT);
                File j = defaultIfFound(firstSecond, first, second, third, STDOUT);
                File k = defaultIfFound(secondThird, first, second, third, STDOUT);
                File i = defaultIfFound(intersection, first, second, third,  STDOUT);
                File u = defaultIfFound(union, first, second, third, STDOUT);
                new Venn3(count.wasFound(), header.wasFound(), first, second, third, f, s, t, j, k, i, u).run();
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
    private static <T> T defaultIfFound(final Argument<T> argument, final T value0, final T value1, final T value2, final T defaultValue)
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
}