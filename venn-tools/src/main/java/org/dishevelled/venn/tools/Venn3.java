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

import java.io.BufferedWriter;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.util.Iterator;

import org.dishevelled.commandline.ArgumentList;
import org.dishevelled.commandline.CommandLine;
import org.dishevelled.commandline.CommandLineParseException;
import org.dishevelled.commandline.CommandLineParser;
import org.dishevelled.commandline.Switch;
import org.dishevelled.commandline.Usage;

import org.dishevelled.commandline.argument.FileArgument;

import org.dishevelled.venn.TernaryVennModel;

import org.dishevelled.venn.swing.TernaryVennLabel;

/**
 * Venn three input files.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class Venn3
    extends AbstractVennRunnable
{
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
        super(count, header);
        this.first = first;
        this.second = second;
        this.third = third;

        // default all to stdout if none are specified
        if ((firstOnly == null)
            && (secondOnly == null)
            && (thirdOnly == null)
            && (firstSecond == null)
            && (secondThird == null)
            && (intersection == null)
            && (union == null))
        {
            this.firstOnly = STDOUT;
            this.secondOnly = STDOUT;
            this.thirdOnly = STDOUT;
            this.firstSecond = STDOUT;
            this.secondThird = STDOUT;
            this.intersection = STDOUT;
            this.union = STDOUT;
        }
        else
        {
            this.firstOnly = firstOnly;
            this.secondOnly = secondOnly;
            this.thirdOnly = thirdOnly;
            this.firstSecond = firstSecond;
            this.secondThird = secondThird;
            this.intersection = intersection;
            this.union = union;
        }
    }


    /** {@inheritDoc} */
    public void run()
    {
        TernaryVennLabel<String> label = new TernaryVennLabel<String>(first.getName(), read(first), second.getName(), read(second), third.getName(), read(third));
        TernaryVennModel<String> model = label.getModel();

        // write individually to output files first
        write(label.getFirstOnlyLabelText(), model.firstOnly(), firstOnly);
        write(label.getSecondOnlyLabelText(), model.secondOnly(), secondOnly);
        write(label.getThirdOnlyLabelText(), model.thirdOnly(), thirdOnly);
        write(label.getFirstSecondLabelText(), model.firstSecond(), firstSecond);
        write(label.getSecondThirdLabelText(), model.secondThird(), secondThird);
        write(label.getIntersectionLabelText(), model.intersection(), intersection);
        write(label.getUnionLabelText(), model.union(), union);

        // write collectively to stdout next
        boolean fo = STDOUT.equals(firstOnly);
        boolean so = STDOUT.equals(secondOnly);
        boolean to = STDOUT.equals(thirdOnly);
        boolean fs = STDOUT.equals(firstSecond);
        boolean st = STDOUT.equals(secondThird);
        boolean i = STDOUT.equals(intersection);
        boolean u = STDOUT.equals(union);

        PrintWriter stdout = null;
        stdout = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        if (header())
        {
            write(fo, label.getFirstOnlyLabelText(), stdout);
            write(so, label.getSecondOnlyLabelText(), stdout);
            write(to, label.getThirdOnlyLabelText(), stdout);
            write(fs, label.getFirstSecondLabelText(), stdout);
            write(st, label.getSecondThirdLabelText(), stdout);
            write(i, label.getIntersectionLabelText(), stdout);
            write(u, label.getUnionLabelText(), stdout);
            // todo trim extra \t
            stdout.print("\n");
        }

        if (count())
        {
            write(fo, model.firstOnly().size(), stdout);
            write(so, model.secondOnly().size(), stdout);
            write(to, model.thirdOnly().size(), stdout);
            write(fs, model.firstSecond().size(), stdout);
            write(st, model.secondThird().size(), stdout);
            write(i, model.intersection().size(), stdout);
            write(u, model.union().size(), stdout);
            // todo trim extra \t
            stdout.print("\n");
        }
        else
        {
            boolean remaining = fo || so || to || fs || st || i || u;
            Iterator<String> foit = model.firstOnly().iterator();
            Iterator<String> soit = model.secondOnly().iterator();
            Iterator<String> toit = model.thirdOnly().iterator();
            Iterator<String> fsit = model.firstSecond().iterator();
            Iterator<String> stit = model.secondThird().iterator();
            Iterator<String> iit = model.intersection().iterator();
            Iterator<String> uit = model.union().iterator();
            while (remaining)
            {
                write(fo, foit, stdout);
                write(so, soit, stdout);
                write(to, toit, stdout);
                write(fs, fsit, stdout);
                write(st, stit, stdout);
                write(i, iit, stdout);
                write(u, uit, stdout);
                remaining = (fo && foit.hasNext())
                    || (so && soit.hasNext())
                    || (to && toit.hasNext())
                    || (fs && fsit.hasNext())
                    || (st && stit.hasNext())
                    || (i && iit.hasNext())
                    || (u && uit.hasNext());

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
}