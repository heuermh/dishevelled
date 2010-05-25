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

import org.dishevelled.venn.QuaternaryVennModel3;

import org.dishevelled.venn.swing.QuaternaryVennLabel3;

/**
 * Venn four input files.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class Venn4
    extends AbstractVennRunnable
{
    /** First input file. */
    private final File first;

    /** Second input file. */
    private final File second;

    /** Third input file. */
    private final File third;

    /** Fourth input file. */
    private final File fourth;

    /** First only output file, if any. */
    private final File firstOnly;

    /** Second only output file, if any. */
    private final File secondOnly;

    /** Third only output file, if any. */
    private final File thirdOnly;

    /** Fourth only output file, if any. */
    private final File fourthOnly;

    /** First second output file, if any. */
    private final File firstSecond;

    /** First third output file, if any. */
    private final File firstThird;

    /** First fourth output file, if any. */
    private final File firstFourth;

    /** Second third output file, if any. */
    private final File secondThird;

    /** Second fourth output file, if any. */
    private final File secondFourth;

    /** Third fourth output file, if any. */
    private final File thirdFourth;

    /** First second third output file, if any. */
    private final File firstSecondThird;

    /** First second fourth output file, if any. */
    private final File firstSecondFourth;

    /** First third fourth output file, if any. */
    private final File firstThirdFourth;

    /** Second third fourth output file, if any. */
    private final File secondThirdFourth;

    /** Intersection output file, if any. */
    private final File intersection;

    /** Union output file, if any. */
    private final File union;

    /** Usage string. */
    private static final String USAGE = "java Venn4 [args] first second third fourth";


    /**
     * Create a new venn 4 with the specified arguments.
     *
     * @param count true to output count(s) only
     * @param header true to ouput header(s)
     * @param first first input file
     * @param second second input file
     * @param third third input file
     * @param fourth fourth input file
     * @param firstOnly first only output file
     * @param secondOnly second only output file
     * @param thirdOnly third only output file
     * @param fourthOnly fourth only output file
     * @param firstSecond first second output file
     * @param firstThird first third output file
     * @param firstFourth first fourth output file
     * @param secondThird second third output file
     * @param secondFourth second fourth output file
     * @param thirdFourth third fourth output
     * @param firstSecondThird first second third output file
     * @param firstSecondFourth first second fourth output file
     * @param firstThirdFourth first third fourth output file
     * @param secondThirdFourth second third fourth output file
     * @param intersection intersection output file
     * @param union union output file
     */
    private Venn4(final boolean count,
                  final boolean header,
                  final File first,
                  final File second,
                  final File third,
                  final File fourth,
                  final File firstOnly,
                  final File secondOnly,
                  final File thirdOnly,
                  final File fourthOnly,
                  final File firstSecond,
                  final File firstThird,
                  final File firstFourth,
                  final File secondThird,
                  final File secondFourth,
                  final File firstSecondThird,
                  final File firstSecondFourth,
                  final File firstThirdFourth,
                  final File secondThirdFourth,
                  final File thirdFourth,
                  final File intersection,
                  final File union)
    {
        super(count, header);
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;

        // default all to stdout if none are specified
        if ((firstOnly == null)
            && (secondOnly == null)
            && (thirdOnly == null)
            && (fourthOnly == null)
            && (firstSecond == null)
            && (firstThird == null)
            && (firstFourth == null)
            && (secondThird == null)
            && (secondFourth == null)
            && (thirdFourth == null)
            && (firstSecondThird == null)
            && (firstSecondFourth == null)
            && (firstThirdFourth == null)
            && (secondThirdFourth == null)
            && (intersection == null)
            && (union == null))
        {
            this.firstOnly = STDOUT;
            this.secondOnly = STDOUT;
            this.thirdOnly = STDOUT;
            this.fourthOnly = STDOUT;
            this.firstSecond = STDOUT;
            this.firstThird = STDOUT;
            this.firstFourth = STDOUT;
            this.secondThird = STDOUT;
            this.secondFourth = STDOUT;
            this.thirdFourth = STDOUT;
            this.firstSecondThird = STDOUT;
            this.firstSecondFourth = STDOUT;
            this.firstThirdFourth = STDOUT;
            this.secondThirdFourth = STDOUT;
            this.intersection = STDOUT;
            this.union = STDOUT;
        }
        else
        {
            this.firstOnly = firstOnly;
            this.secondOnly = secondOnly;
            this.thirdOnly = thirdOnly;
            this.fourthOnly = fourthOnly;
            this.firstSecond = firstSecond;
            this.firstThird = firstThird;
            this.firstFourth = firstFourth;
            this.secondThird = secondThird;
            this.secondFourth = secondFourth;
            this.thirdFourth = thirdFourth;
            this.firstSecondThird = firstSecondThird;
            this.firstSecondFourth = firstSecondFourth;
            this.firstThirdFourth = firstThirdFourth;
            this.secondThirdFourth = secondThirdFourth;
            this.intersection = intersection;
            this.union = union;
        }
    }


    /** {@inheritDoc} */
    public void run()
    {
        QuaternaryVennLabel3<String> label = new QuaternaryVennLabel3<String>(first.getName(), read(first), second.getName(), read(second), third.getName(), read(third), fourth.getName(), read(fourth));
        QuaternaryVennModel3<String> model = label.getModel();

        // write individually to output files first
        write(label.getFirstOnlyLabelText(), model.firstOnly(), firstOnly);
        write(label.getSecondOnlyLabelText(), model.secondOnly(), secondOnly);
        write(label.getThirdOnlyLabelText(), model.thirdOnly(), thirdOnly);
        write(label.getFourthOnlyLabelText(), model.fourthOnly(), fourthOnly);
        write(label.getFirstSecondLabelText(), model.firstSecond(), firstSecond);
        write(label.getFirstThirdLabelText(), model.firstThird(), firstThird);
        write(label.getFirstFourthLabelText(), model.firstFourth(), firstFourth);
        write(label.getSecondThirdLabelText(), model.secondThird(), secondThird);
        write(label.getSecondFourthLabelText(), model.secondFourth(), secondFourth);
        write(label.getThirdFourthLabelText(), model.thirdFourth(), thirdFourth);
        write(label.getFirstSecondThirdLabelText(), model.firstSecondThird(), firstSecondThird);
        write(label.getFirstSecondFourthLabelText(), model.firstSecondFourth(), firstSecondFourth);
        write(label.getFirstThirdFourthLabelText(), model.firstThirdFourth(), firstThirdFourth);
        write(label.getSecondThirdFourthLabelText(), model.secondThirdFourth(), secondThirdFourth);
        write(label.getIntersectionLabelText(), model.intersection(), intersection);
        write(label.getUnionLabelText(), model.union(), union);

        // write collectively to stdout next
        boolean fo = STDOUT.equals(firstOnly);
        boolean so = STDOUT.equals(secondOnly);
        boolean to = STDOUT.equals(thirdOnly);
        boolean ro = STDOUT.equals(fourthOnly);
        boolean fs = STDOUT.equals(firstSecond);
        boolean ft = STDOUT.equals(firstThird);
        boolean fr = STDOUT.equals(firstFourth);
        boolean st = STDOUT.equals(secondThird);
        boolean sr = STDOUT.equals(secondFourth);
        boolean tr = STDOUT.equals(thirdFourth);
        boolean fst = STDOUT.equals(firstSecondThird);
        boolean fsr = STDOUT.equals(firstSecondFourth);
        boolean ftr = STDOUT.equals(firstThirdFourth);
        boolean str = STDOUT.equals(secondThirdFourth);
        boolean i = STDOUT.equals(intersection);
        boolean u = STDOUT.equals(union);

        PrintWriter stdout = null;
        stdout = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        if (header())
        {
            write(fo, label.getFirstOnlyLabelText(), stdout);
            write(so, label.getSecondOnlyLabelText(), stdout);
            write(to, label.getThirdOnlyLabelText(), stdout);
            write(ro, label.getFourthOnlyLabelText(), stdout);
            write(fs, label.getFirstSecondLabelText(), stdout);
            write(ft, label.getFirstThirdLabelText(), stdout);
            write(fr, label.getFirstFourthLabelText(), stdout);
            write(st, label.getSecondThirdLabelText(), stdout);
            write(sr, label.getSecondFourthLabelText(), stdout);
            write(tr, label.getThirdFourthLabelText(), stdout);
            write(fst, label.getFirstSecondThirdLabelText(), stdout);
            write(fsr, label.getFirstSecondFourthLabelText(), stdout);
            write(ftr, label.getFirstThirdFourthLabelText(), stdout);
            write(str, label.getSecondThirdFourthLabelText(), stdout);
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
            write(ro, model.fourthOnly().size(), stdout);
            write(fs, model.firstSecond().size(), stdout);
            write(ft, model.firstThird().size(), stdout);
            write(fr, model.firstFourth().size(), stdout);
            write(st, model.secondThird().size(), stdout);
            write(sr, model.secondFourth().size(), stdout);
            write(tr, model.thirdFourth().size(), stdout);
            write(fst, model.firstSecondThird().size(), stdout);
            write(fsr, model.firstSecondFourth().size(), stdout);
            write(ftr, model.firstThirdFourth().size(), stdout);
            write(str, model.secondThirdFourth().size(), stdout);
            write(i, model.intersection().size(), stdout);
            write(u, model.union().size(), stdout);
            // todo trim extra \t
            stdout.print("\n");
        }
        else
        {
            boolean remaining = fo || so || to || ro || fs || ft || fr || st || sr || tr || fst || fsr || ftr || str || i || u;
            Iterator<String> foit = model.firstOnly().iterator();
            Iterator<String> soit = model.secondOnly().iterator();
            Iterator<String> toit = model.thirdOnly().iterator();
            Iterator<String> roit = model.fourthOnly().iterator();
            Iterator<String> fsit = model.firstSecond().iterator();
            Iterator<String> ftit = model.firstThird().iterator();
            Iterator<String> frit = model.firstFourth().iterator();
            Iterator<String> stit = model.secondThird().iterator();
            Iterator<String> srit = model.secondFourth().iterator();
            Iterator<String> trit = model.thirdFourth().iterator();
            Iterator<String> fstit = model.firstSecondThird().iterator();
            Iterator<String> fsrit = model.firstSecondFourth().iterator();
            Iterator<String> ftrit = model.firstThirdFourth().iterator();
            Iterator<String> strit = model.secondThirdFourth().iterator();
            Iterator<String> iit = model.intersection().iterator();
            Iterator<String> uit = model.union().iterator();
            while (remaining)
            {
                write(fo, foit, stdout);
                write(so, soit, stdout);
                write(to, toit, stdout);
                write(ro, foit, stdout);
                write(fs, fsit, stdout);
                write(ft, ftit, stdout);
                write(fr, frit, stdout);
                write(st, stit, stdout);
                write(sr, srit, stdout);
                write(tr, trit, stdout);
                write(fst, fstit, stdout);
                write(fsr, fsrit, stdout);
                write(ftr, ftrit, stdout);
                write(str, strit, stdout);
                write(i, iit, stdout);
                write(u, uit, stdout);
                remaining = (fo && foit.hasNext())
                    || (so && soit.hasNext())
                    || (to && toit.hasNext())
                    || (ro && roit.hasNext())
                    || (fs && fsit.hasNext())
                    || (ft && ftit.hasNext())
                    || (fr && frit.hasNext())
                    || (st && stit.hasNext())
                    || (sr && srit.hasNext())
                    || (tr && trit.hasNext())
                    || (fst && fstit.hasNext())
                    || (fsr && fsrit.hasNext())
                    || (ftr && ftrit.hasNext())
                    || (str && strit.hasNext())
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
            FileArgument fourthOnly = new FileArgument("r", "fourth-only", "fourth only output file", false);
            FileArgument firstSecond = new FileArgument("j", "first-second", "first second output file", false);
            FileArgument firstThird = new FileArgument("k", "first-third", "first third output file", false);
            FileArgument firstFourth = new FileArgument("l", "first-fourth", "first fourth output file", false);
            FileArgument secondThird = new FileArgument("m", "second-third", "second third output file", false);
            FileArgument secondFourth = new FileArgument("n", "second-fourth", "second fourth output file", false);
            FileArgument thirdFourth = new FileArgument("o", "third-fourth", "third fourth output file", false);
            FileArgument firstSecondThird = new FileArgument("p", "first-second-third", "first second third output file", false);
            FileArgument firstSecondFourth = new FileArgument("q", "first-second-fourth", "first second fourth output file", false);
            FileArgument firstThirdFourth = new FileArgument("v", "first-third-fourth", "first third fourth output file", false);
            FileArgument secondThirdFourth = new FileArgument("w", "second-third-fourth", "second third fourth output file", false);
            FileArgument intersection = new FileArgument("i", "intersection", "intersection output file", false);
            FileArgument union = new FileArgument("u", "union", "union output file", false);

            arguments = new ArgumentList(help, count, header, firstOnly, secondOnly, thirdOnly, fourthOnly,
                                         firstSecond, firstThird, firstFourth, secondThird, secondFourth, thirdFourth,
                                         firstSecondThird, firstSecondFourth, firstThirdFourth, secondThirdFourth, intersection, union);
            commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            if (help.wasFound())
            {
                Usage.usage(USAGE, null, commandLine, arguments, System.out);
            }
            else
            {
                if (args.length < 4)
                {
                    throw new IllegalArgumentException("must have at least two file arguments, first, second, third and fourth input files");
                }
                File first = new File(args[args.length - 4]);
                File second = new File(args[args.length - 3]);
                File third = new File(args[args.length - 2]);
                File fourth = new File(args[args.length - 1]);
                if (first.getName().startsWith("-") || second.getName().startsWith("-") || third.getName().startsWith("-") || fourth.getName().startsWith("-"))
                {
                    throw new IllegalArgumentException("must have at least two file arguments, first, second, third and fourth input files");
                }
                File f = defaultIfFound(firstOnly, first, second, third, fourth, STDOUT);
                File s = defaultIfFound(secondOnly, first, second, third, fourth, STDOUT);
                File t = defaultIfFound(thirdOnly, first, second, third, fourth, STDOUT);
                File r = defaultIfFound(fourthOnly, first, second, third, fourth, STDOUT);
                File j = defaultIfFound(firstSecond, first, second, third, fourth, STDOUT);
                File k = defaultIfFound(firstThird, first, second, third, fourth, STDOUT);
                File l = defaultIfFound(firstFourth, first, second, third, fourth, STDOUT);
                File m = defaultIfFound(secondThird, first, second, third, fourth, STDOUT);
                File n = defaultIfFound(secondFourth, first, second, third, fourth, STDOUT);
                File o = defaultIfFound(thirdFourth, first, second, third, fourth, STDOUT);
                File p = defaultIfFound(firstSecondThird, first, second, third, fourth, STDOUT);
                File q = defaultIfFound(firstSecondFourth, first, second, third, fourth, STDOUT);
                File v = defaultIfFound(firstThirdFourth, first, second, third, fourth, STDOUT);
                File w = defaultIfFound(secondThirdFourth, first, second, third, fourth, STDOUT);
                File i = defaultIfFound(intersection, first, second, third,  fourth, STDOUT);
                File u = defaultIfFound(union, first, second, third, fourth, STDOUT);
                new Venn4(count.wasFound(), header.wasFound(), first, second, third, fourth, f, s, t, r, j, k, l, m, n, o, p, q, v, w, i, u).run();
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