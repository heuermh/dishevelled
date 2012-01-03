/*

    dsh-cluster-examples  Examples for the cluster library.
    Copyright (c) 2008-2012 held jointly by the individual authors.

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
package org.dishevelled.cluster.examples;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import org.apache.commons.math.stat.descriptive.SummaryStatistics;

import org.dishevelled.cluster.Cluster;
import org.dishevelled.cluster.ClusteringAlgorithm;
import org.dishevelled.cluster.ClusteringAlgorithmAdapter;
import org.dishevelled.cluster.ClusteringAlgorithmEvent;
import org.dishevelled.cluster.ClusteringAlgorithmException;
import org.dishevelled.cluster.ExitStrategy;
import org.dishevelled.cluster.Similarity;

import org.dishevelled.cluster.exit.IterationLimitExitStrategy;

import org.dishevelled.cluster.impl.ConnectedComponents;

import org.dishevelled.commandline.ArgumentList;
import org.dishevelled.commandline.CommandLine;
import org.dishevelled.commandline.CommandLineParseException;
import org.dishevelled.commandline.CommandLineParser;
import org.dishevelled.commandline.Switch;
import org.dishevelled.commandline.Usage;

import org.dishevelled.commandline.argument.DoubleArgument;

/**
 * Connected components clustering algorithm example.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class ConnectedComponentsExample
    implements Runnable
{
    /** Cutoff similarity score. */
    private final double cutoff;

    /** Usage string. */
    private static final String USAGE = "java ConnectedComponentsExample [args]\n\n   Connected components"
        + " clustering algorithm example.\n   Values are read from standard in and clusters are printed to standard out.";


    /**
     * Create a new connected components clustering algorithm example
     * with the specified cutoff similarity score.
     *
     * @param cutoff cutoff similarity score, must be between <code>0.0d</code>
     *    and <code>1.0d</code>
     */
    public ConnectedComponentsExample(double cutoff)
    {
        if ((cutoff < 0.0) || (cutoff > 1.0d))
        {
            throw new IllegalArgumentException("cutoff must be between 0.0d and 1.0d, was " + cutoff);
        }
        this.cutoff = cutoff;
    }


    /** {@inheritDoc} */
    public void run()
    {
        List<String> values = readValues();        
        ClusteringAlgorithm<String> algo = new ConnectedComponents<String>(cutoff);
        Similarity<String> similarity = new LevenshteinDistanceSimilarity();
        SimilarityStats stats = new SimilarityStats();
        algo.addClusteringAlgorithmListener(stats);
        // TODO:  should create a NullExitStrategy or something similar
        ExitStrategy<String> exitStrategy = new IterationLimitExitStrategy<String>(99);
        try
        {
            Set<Cluster<String>> clusters = algo.cluster(values, similarity, exitStrategy);
            int  i = 0;
            for (Cluster<String> cluster : clusters)
            {
                System.out.println("Cluster " + i + " of " + clusters.size() + ":");
                for (String member : cluster.members())
                {
                    System.out.println(member);
                }
                i++;
            }
            System.out.println("Total values: " + values.size());
            System.out.println("Total clusters: " + i);
            System.out.println("Similarity stats: " + stats);
        }
        catch (ClusteringAlgorithmException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
        finally
        {
            algo.removeClusteringAlgorithmListener(stats);
        }
    }

    /**
     * Read values one per line from standard in.
     *
     * @return values read from standard in
     */
    private List<String> readValues()
    {
        List<String> values = new ArrayList<String>(10000);
        BufferedReader reader = null;
        try
        {
            reader = new BufferedReader(new InputStreamReader(System.in));
            while (reader.ready())
            {
                String line = reader.readLine();
                values.add(line);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (reader != null)
                {
                    reader.close();
                }
            }
            catch (Exception e)
            {
                // ignore
            }
        }
        return values;
    }

    /**
     * Similarity measure based on Levenshtein Distance.
     */
    private static class LevenshteinDistanceSimilarity implements Similarity<String>
    {
        /** {@inheritDoc} */
        public double similarity(final String value1, final String value2)
        {
            double distance = (double) StringUtils.getLevenshteinDistance(value1, value2);
            double maxDistance = (double) Math.max(value1.length(), value2.length());
            double similarity = 1.0d - (distance / maxDistance);
            return similarity;
        }
    }

    /**
     * Similarity stats.
     */
    private static class SimilarityStats extends ClusteringAlgorithmAdapter<String>
    {
        /** Summary statistics. */
        final SummaryStatistics stats = new SummaryStatistics();


        /** {@inheritDoc} */
        public void similarityCalculated(final ClusteringAlgorithmEvent<String> event)
        {
            stats.addValue(event.getSimilarity());
        }

        /** {@inheritDoc} */
        public String toString()
        {
            return "min=" + stats.getMin() + " max=" + stats.getMax() + " mean=" + stats.getMean() + " stdev=" + stats.getStandardDeviation();
        }
    }


    /**
     * Main.
     *
     * @param args command line arguments, ignored
     */
    public static void main(final String[] args)
    {
        CommandLine commandLine = null;
        ArgumentList arguments = null;
        try
        {
            Switch help = new Switch("h", "help", "display help message");
            DoubleArgument cutoff = new DoubleArgument("c", "cutoff", "cutoff similarity score, must be between 0.0d and 1.0d", false);

            arguments = new ArgumentList(help, cutoff);
            commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            if (help.wasFound())
            {
                Usage.usage(USAGE, null, commandLine, arguments, System.out);
            }
            else
            {
                Runnable r = new ConnectedComponentsExample(cutoff.wasFound() ? cutoff.getValue() : ConnectedComponents.DEFAULT_CUTOFF);
                r.run();
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