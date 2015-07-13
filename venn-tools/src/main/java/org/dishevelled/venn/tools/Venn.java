/*

    dsh-venn-tools  Command line tools for venn diagrams.
    Copyright (c) 2010-2015 held jointly by the individual authors.

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
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import org.dishevelled.bitset.MutableBitSet;
import org.dishevelled.bitset.ImmutableBitSet;

import org.dishevelled.commandline.ArgumentList;
import org.dishevelled.commandline.CommandLine;
import org.dishevelled.commandline.CommandLineParseException;
import org.dishevelled.commandline.CommandLineParser;
import org.dishevelled.commandline.Switch;
import org.dishevelled.commandline.Usage;

import org.dishevelled.commandline.argument.FileArgument;

import org.dishevelled.venn.VennModel;

import org.dishevelled.venn.model.VennModels;

/**
 * Venn an arbitrary number of input files.
 *
 * @author  Michael Heuer
 */
public final class Venn
    extends AbstractVennRunnable
{
    /** List of input files. */
    private final List<File> inputFiles;

    /** Usage string. */
    private static final String USAGE = "java Venn [args] [input-files]";


    /**
     * Create a new venn with the specified arguments.
     *
     * @param count true to output count(s) only
     * @param header true to ouput header(s)
     * @param inputFiles list of input files, must not be null, must contain at least
     *    two input files, and must not contain more than 29 input files
     */
    private Venn(final boolean count, final boolean header, final List<File> inputFiles)
    {
        super(count, header);

        if (inputFiles == null)
        {
            throw new IllegalArgumentException("inputFiles must not be null");
        }
        if (inputFiles.size() < 2)
        {
            throw new IllegalArgumentException("inputFiles must contain at least two input files");
        }
        if (inputFiles.size() > 29)
        {
            throw new IllegalArgumentException("inputFiles must contain less than 30 input files");
        }
        this.inputFiles = inputFiles;
    }


    /** {@inheritDoc} */
    public void run()
    {
        int n = inputFiles.size();
        List<String> labels = new ArrayList<String>(n);
        List<Set<String>> sets = new ArrayList<Set<String>>(n);
        for (File inputFile : inputFiles)
        {
            labels.add(inputFile.getName());
            sets.add(read(inputFile));
        }
        VennModel<String> model = VennModels.createVennModel(sets);

        ImmutableMap.Builder<ImmutableBitSet, Set<String>> builder = ImmutableMap.builder();
        Set<Set<Integer>> powerSet = Sets.powerSet(range(n));
        for (Set<Integer> set : powerSet)
        {
            if (!set.isEmpty())
            {
                ImmutableBitSet key = toImmutableBitSet(set);
                builder.put(key, model.exclusiveTo(first(key), additional(key)));
            }
        }
        Map<ImmutableBitSet, Set<String>> views = builder.build();

        PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        if (header())
        {
            for (Map.Entry<ImmutableBitSet, Set<String>> entry : views.entrySet())
            {
                write(true, buildLabel(labels, entry.getKey()), writer);
            }
            // todo trim extra \t
            writer.print("\n");
        }

        if (count())
        {
            for (Map.Entry<ImmutableBitSet, Set<String>> entry : views.entrySet())
            {
                write(true, entry.getValue().size(), writer);
            }
            // todo trim extra \t
            writer.print("\n");
        }
        else
        {
            List<Iterator<String>> iterators = new ArrayList<Iterator<String>>();

            for (Map.Entry<ImmutableBitSet, Set<String>> entry : views.entrySet())
            {
                iterators.add(entry.getValue().iterator());
            }

            boolean done = false;
            while (!done)
            {
                boolean left = false;
                for (Iterator<String> iterator : iterators)
                {
                    if (iterator.hasNext())
                    {
                        writer.write(iterator.next());
                        left = true;
                    }
                    writer.write("\t");
                }
                // todo trim extra \t
                writer.print("\n");
                done = !left;
            }
        }
        try
        {
            writer.close();
        }
        catch (Exception e)
        {
            // ignore
        }
    }

    // copied from VennNode.java

    static String buildLabel(final List<String> labels, final ImmutableBitSet key)
    {
        int first = first(key);
        int[] additional = additional(key);
        StringBuilder sb = new StringBuilder();
        sb.append(labels.get(first));
        if (additional.length > 0) {
            for (int i = 0, size = additional.length - 1; i < size; i++)
            {
                sb.append(", ");
                sb.append(labels.get(additional[i]));
            }
            sb.append(" and ");
            sb.append(labels.get(additional[Math.max(0, additional.length - 1)]));
        }
        sb.append(" only");
        return sb.toString();
    }

    static ImmutableSet<Integer> range(final int n)
    {
        Set<Integer> range = Sets.newHashSet();
        for (int i = 0; i < n; i++)
        {
            range.add(Integer.valueOf(i));
        }
        return ImmutableSet.copyOf(range);
    }

    static int first(final ImmutableBitSet bitSet)
    {
        return (int) bitSet.nextSetBit(0L);
    }

    static int[] additional(final ImmutableBitSet bitSet)
    {
        int[] additional = new int[Math.max(0, (int) bitSet.cardinality() - 1)];
        int index = 0;
        long first = bitSet.nextSetBit(0);
        for (long value = bitSet.nextSetBit(first + 1); value >= 0L; value = bitSet.nextSetBit(value + 1))
        {
            additional[index] = (int) value;
            index++;
        }
        return additional;
    }

    static int first(final Set<Integer> values)
    {
        if (values.isEmpty())
        {
            return -1;
        }
        return values.iterator().next().intValue();
    }

    static int[] additional(final Set<Integer> values)
    {
        int[] additional = new int[Math.max(0, values.size() - 1)];
        int index = -1;
        for (Integer value : values)
        {
            if (index >= 0)
            {
                additional[index] = value.intValue();
            }
            index++;
        }
        return additional;
    }

    static ImmutableBitSet toImmutableBitSet(final Set<Integer> indices)
    {
        if (indices == null)
        {
            throw new IllegalArgumentException("indices must not be null");
        }
        if (indices.isEmpty())
        {
            throw new IllegalArgumentException("indices must not be empty");
        }
        MutableBitSet mutableBitSet = new MutableBitSet(indices.size());
        for (Integer index : indices)
        {
            mutableBitSet.set(index);
        }
        mutableBitSet.trimTrailingZeros();
        return mutableBitSet.immutableCopy();
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

            arguments = new ArgumentList(help, count, header);
            commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            if (help.wasFound())
            {
                Usage.usage(USAGE, null, commandLine, arguments, System.out);
            }
            else
            {
                List<File> inputFiles = new ArrayList<File>(args.length);
                for (int i = 0; i < args.length; i++)
                {
                    // ick.
                    if (!args[i].startsWith("-"))
                    {
                        inputFiles.add(new File(args[i]));
                    }
                }
                new Venn(count.wasFound(), header.wasFound(), inputFiles).run();
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