/*

    dsh-analysis-examples  Examples for the analysis library.
    Copyright (c) 2012-2013 held jointly by the individual authors.

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
package org.dishevelled.analysis.examples;

import java.io.File;
import java.io.IOException;

import java.util.Set;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.stax.DoubleElementHandler;
import net.sf.stax.LongElementHandler;

import org.apache.commons.math.stat.descriptive.SummaryStatistics;

import org.dishevelled.commandline.ArgumentList;
import org.dishevelled.commandline.CommandLine;
import org.dishevelled.commandline.CommandLineParseException;
import org.dishevelled.commandline.CommandLineParser;
import org.dishevelled.commandline.Switch;
import org.dishevelled.commandline.Usage;

import org.dishevelled.commandline.argument.FileArgument;

import org.dishevelled.graph.Graph;
import org.dishevelled.graph.Node;

import org.dishevelled.graph.impl.GraphUtils;

import org.dishevelled.graph.io.graphml.GraphMLReader;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * Graph summary example.
 *
 * @author  Michael Heuer
 */
public final class GraphSummary
    implements Runnable
{
    /** XML Reader. */
    private final XMLReader xmlReader;

    /** Graph file, if any. */
    private final File graphFile;

    /** Usage string. */
    private static final String USAGE = "java GraphSummary --graph graph";


    /**
     * Create a new graph summary example with the specified graph file.
     *
     * @param graphFile graph file
     */
    public GraphSummary(final File graphFile)
    {
        this.graphFile = graphFile;

        try
        {
            xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        }
        catch (SAXException e)
        {
            throw new RuntimeException(e.getMessage());
        }
        catch (ParserConfigurationException e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }


    @Override
    public void run()
    {
        try
        {
            GraphMLReader<Long, Double> reader = new GraphMLReader<Long, Double>(xmlReader);
            reader.setNodeValueHandler(new LongElementHandler());
            reader.setEdgeValueHandler(new DoubleElementHandler());
            Graph<Long, Double> graph = graphFile == null ? reader.read(System.in) : reader.read(graphFile);

            System.out.println("nodes=" + graph.nodeCount());
            System.out.println("edges=" + graph.edgeCount());

            SummaryStatistics summary = new SummaryStatistics();
            for (Node<Long, Double> node : graph.nodes())
            {
                summary.addValue(node.degree());
            }
            System.out.println("min degree=" + summary.getMin());
            System.out.println("mean degree=" + summary.getMean() + " +/- " + summary.getStandardDeviation());
            System.out.println("max degree=" + summary.getMax());
            summary.clear();

            Set<Set<Node<Long, Double>>> connectedComponents = GraphUtils.connectedComponents(graph);
            System.out.println("connected components=" + connectedComponents.size());
            for (Set<Node<Long, Double>> connectedComponent : connectedComponents)
            {
                summary.addValue(connectedComponent.size());
            }
            System.out.println("min connected component size=" + summary.getMin());
            System.out.println("mean connected component size=" + summary.getMean() + " +/- " + summary.getStandardDeviation());
            System.out.println("max connected component size=" + summary.getMax());
        }
        catch (IOException e)
        {
            e.printStackTrace();
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
            FileArgument graphFile = new FileArgument("g", "graph", "graph file", false);
            arguments = new ArgumentList(help, graphFile);
            commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            if (help.wasFound())
            {
                Usage.usage(USAGE, null, commandLine, arguments, System.out);
            }
            else
            {
                new GraphSummary(graphFile.getValue()).run();
            }
        }
        catch (CommandLineParseException e)
        {
            Usage.usage(USAGE, e, commandLine, arguments, System.err);
        }
    }
}