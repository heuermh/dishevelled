/*

    dsh-venn-tools  Command line tools for venn diagrams.
    Copyright (c) 2010-2013 held jointly by the individual authors.

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

import org.dishevelled.venn.BinaryVennModel;

import org.dishevelled.venn.swing.BinaryVennLabel;

/**
 * Venn two input files.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class Venn2
    extends AbstractVennRunnable
{
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


    /**
     * Create a new venn 2 with the specified arguments.
     *
     * @param count true to output count(s) only
     * @param header true to ouput header(s)
     * @param first first input file
     * @param second second input file
     * @param firstOnly first only output file
     * @param secondOnly second only output file
     * @param intersection intersection output file
     * @param union union output file
     */
    private Venn2(final boolean count,
                  final boolean header,
                  final File first,
                  final File second,
                  final File firstOnly,
                  final File secondOnly,
                  final File intersection,
                  final File union)
    {
        super(count, header);
        this.first = first;
        this.second = second;

        // default all to stdout if none are specified
        if ((firstOnly == null)
            && (secondOnly == null)
            && (intersection == null)
            && (union == null))
        {
            this.firstOnly = STDOUT;
            this.secondOnly = STDOUT;
            this.intersection = STDOUT;
            this.union = STDOUT;
        }
        else
        {
            this.firstOnly = firstOnly;
            this.secondOnly = secondOnly;
            this.intersection = intersection;
            this.union = union;
        }
    }


    /** {@inheritDoc} */
    public void run()
    {
        BinaryVennLabel<String> label = new BinaryVennLabel<String>(first.getName(), read(first), second.getName(), read(second));
        BinaryVennModel<String> model = label.getModel();

        // write individually to output files first
        write(label.getFirstOnlyLabelText(), model.firstOnly(), firstOnly);
        write(label.getSecondOnlyLabelText(), model.secondOnly(), secondOnly);
        write(label.getIntersectionLabelText(), model.intersection(), intersection);
        write(label.getUnionLabelText(), model.union(), union);

        // write collectively to stdout next
        boolean fo = STDOUT.equals(firstOnly);
        boolean so = STDOUT.equals(secondOnly);
        boolean i = STDOUT.equals(intersection);
        boolean u = STDOUT.equals(union);

        PrintWriter stdout = null;
        stdout = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        if (header())
        {
            write(fo, label.getFirstOnlyLabelText(), stdout);
            write(so, label.getSecondOnlyLabelText(), stdout);
            write(i, label.getIntersectionLabelText(), stdout);
            write(u, label.getUnionLabelText(), stdout);
            // todo trim extra \t
            stdout.print("\n");
        }

        if (count())
        {
            write(fo, model.firstOnly().size(), stdout);
            write(so, model.secondOnly().size(), stdout);
            write(i, model.intersection().size(), stdout);
            write(u, model.union().size(), stdout);
            // todo trim extra \t
            stdout.print("\n");
        }
        else
        {
            boolean remaining = fo || so || i || u;
            Iterator<String> foit = model.firstOnly().iterator();
            Iterator<String> soit = model.secondOnly().iterator();
            Iterator<String> iit = model.intersection().iterator();
            Iterator<String> uit = model.union().iterator();
            while (remaining)
            {
                write(fo, foit, stdout);
                write(so, soit, stdout);
                write(i, iit, stdout);
                write(u, uit, stdout);
                remaining = (fo && foit.hasNext())
                    || (so && soit.hasNext())
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
            FileArgument intersection = new FileArgument("i", "intersection", "intersection output file", false);
            FileArgument union = new FileArgument("u", "union", "union output file", false);

            arguments = new ArgumentList(help, count, header, firstOnly, secondOnly, intersection, union);
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
                if (first.getName().startsWith("-") || second.getName().startsWith("-"))
                {
                    throw new IllegalArgumentException("must have at least two file arguments, first and second input files");
                }
                File f = defaultIfFound(firstOnly, first, second, STDOUT);
                File s = defaultIfFound(secondOnly, first, second, STDOUT);
                File i = defaultIfFound(intersection, first, second, STDOUT);
                File u = defaultIfFound(union, first, second, STDOUT);
                new Venn2(count.wasFound(), header.wasFound(), first, second, f, s, i, u).run();
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