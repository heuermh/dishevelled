/*

    dsh-cluster-examples  Examples for the cluster library.
    Copyright (c) 2008 held jointly by the individual authors.

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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import org.dishevelled.cluster.Cluster;
import org.dishevelled.cluster.ClusteringAlgorithm;
import org.dishevelled.cluster.ClusteringAlgorithmException;
import org.dishevelled.cluster.ExitStrategy;
import org.dishevelled.cluster.Similarity;

import org.dishevelled.cluster.exitstrategy.IterationLimitExitStrategy;

import org.dishevelled.cluster.impl.ConnectedComponents;

/**
 * Connected components clustering algorithm example.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class ConnectedComponentsExample
    implements Runnable
{

    /** {@inheritDoc} */
    public void run()
    {
        List<String> values = generateStrings();
        ClusteringAlgorithm<String> algo = new ConnectedComponents<String>(0.125d);
        Similarity<String> similarity = new LevenshteinDistanceSimilarity();
        // TODO:  should create a NullExitStrategy or something similar
        ExitStrategy<String> exitStrategy = new IterationLimitExitStrategy(99);
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
        }
        catch (ClusteringAlgorithmException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Generate and return a list of random strings.
     *
     * @return a list of random strings
     */
    private List<String> generateStrings()
    {
        Random random = new Random();
        List<String> strings = new ArrayList<String>();
        for (int i = 0; i < 10000; i ++)
        {
            StringBuffer sb = new StringBuffer(10);
            for (int j = 0; j < 10; j++)
            {
                int ascii = random.nextInt(26) + 97;
                char c = (char) ascii;
                sb.append(c);
            }
            strings.add(sb.toString());
        }
        return strings;
    }

    /**
     * Similarity measure based on Levenshtein Distance.
     */
    private static class LevenshteinDistanceSimilarity implements Similarity<String>
    {
        /** {@inheritDoc} */
        public double similarity(final String value1, final String value2)
        {
            // TODO:  make the spread wider
            return (1.0d / (1.0 + StringUtils.getLevenshteinDistance(value1, value2)));
        }
    }

    /**
     * Main.
     *
     * @param args command line arguments, ignored
     */
    public static void main(final String[] args)
    {
        new ConnectedComponentsExample().run();
    }
}