/*

    dsh-analysis-examples  Examples for the analysis library.
    Copyright (c) 2012 held jointly by the individual authors.

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

import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.dishevelled.analysis.GraphGenerators;

import org.dishevelled.collect.Lists;

import org.dishevelled.commandline.ArgumentList;
import org.dishevelled.commandline.CommandLine;
import org.dishevelled.commandline.CommandLineParseException;
import org.dishevelled.commandline.CommandLineParser;
import org.dishevelled.commandline.Switch;
import org.dishevelled.commandline.Usage;

import org.dishevelled.commandline.argument.FileArgument;
import org.dishevelled.commandline.argument.IntegerArgument;
import org.dishevelled.commandline.argument.StringArgument;

import org.dishevelled.functor.BinaryFunction;

import org.dishevelled.graph.Graph;

import org.dishevelled.graph.io.xgmml.XgmmlGraphWriter;
import org.dishevelled.graph.io.xgmml.XgmmlGraphWriter.ValueHandler;

/**
 * Graph generator.
 *
 * @author  Michael Heuer
 */
public final class GraphGenerator
    implements Runnable
{
    /** Node count. */
    private final int nodeCount;

    /** Edge count. */
    private final int edgeCount;

    /** Connect method. */
    private final String connectMethod;

    /** Graph file, if any. */
    private final File graphFile;

    /** Default node count, <code>100</code>. */
    private static final int DEFAULT_NODE_COUNT = 100;

    /** Default edge count, <code>1000</code>. */
    private static final int DEFAULT_EDGE_COUNT = 1000;

    /** Default connect method, <code>random</code>. */
    private static final String DEFAULT_CONNECT_METHOD = "random";

    /** Usage string. */
    private static final String USAGE = "java GraphGenerator --node-count 100 --edge-count 1000 --connect random --graph graph.graphml";

    /** String value handler. */
    private static final ValueHandler<String> STRING_VALUE_HANDLER = new ValueHandler<String>()
    {
        @Override
        public String getType()
        {
            return "string";
        }

        @Override
        public String getValue(final String value)
        {
            return value;
        }
        /*
          for GraphML

        @Override
        public void write(final String value, final XMLStreamWriter writer) throws IOException, XMLStreamException
        {
            writer.writeCharacters(value);
        }
        */
    };

    /** Append function. */
    private static final BinaryFunction<String, String, String> APPEND = new BinaryFunction<String, String, String>()
    {
        @Override
        public String evaluate(final String value0, final String value1)
        {
            return value0 + " --> " + value1;
        }
    };


    /**
     * Create a new graph summary example with the specified arguments.
     *
     * @param nodeCount node count
     * @param edgeCount edge count
     * @param connectMethod connect method
     * @param graphFile graph file
     */
    public GraphGenerator(final int nodeCount, final int edgeCount, final String connectMethod, final File graphFile)
    {
        // todo: validate
        this.nodeCount = nodeCount;
        this.edgeCount = edgeCount;
        this.connectMethod = connectMethod;
        this.graphFile = graphFile;
    }


    @Override
    public void run()
    {
        try
        {
            List<String> nodeValues = Lists.createList(nodeCount);
            for (int i = 0; i < nodeCount; i++)
            {
                nodeValues.add("node" + i);
            }

            Graph<String, String> graph = null;
            if ("complete".equalsIgnoreCase(connectMethod))
            {
                graph = GraphGenerators.connectCompletely(nodeValues, APPEND);
            }
            else if ("random".equalsIgnoreCase(connectMethod))
            {
                graph = GraphGenerators.connectRandomly(nodeValues, edgeCount, APPEND);
            }
            else if ("preferential".equalsIgnoreCase(connectMethod))
            {
                graph = GraphGenerators.connectPreferentially(nodeValues, edgeCount, APPEND);
            }
            else
            {
                throw new IllegalArgumentException("connectMethod must be one of {connect, random, preferential}");
            }

            if (graph != null)
            {
                XgmmlGraphWriter<String, String> graphWriter = new XgmmlGraphWriter<String, String>();
                graphWriter.addNodeValueHandler("name", STRING_VALUE_HANDLER);
                graphWriter.addEdgeValueHandler("name", STRING_VALUE_HANDLER);
                //GraphMLWriter<String, String> graphWriter = new GraphMLWriter<String, String>();
                //graphWriter.setNodeValueHandler(STRING_VALUE_HANDLER);
                //graphWriter.setEdgeValueHandler(STRING_VALUE_HANDLER);

                if (graphFile == null)
                {
                    graphWriter.write(graph, System.out);
                }
                else
                {
                    graphWriter.write(graph, graphFile);
                }
            }
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
            IntegerArgument nodeCount = new IntegerArgument("n", "node-count", "node count, default " + DEFAULT_NODE_COUNT, false);
            IntegerArgument edgeCount = new IntegerArgument("e", "edge-count", "edge count, default " + DEFAULT_EDGE_COUNT, false);
            StringArgument connectMethod = new StringArgument("c", "connect", "connect method, default " + DEFAULT_CONNECT_METHOD, false);
            FileArgument graphFile = new FileArgument("g", "graph", "graph file", false);
            arguments = new ArgumentList(help, nodeCount, edgeCount, connectMethod, graphFile);
            commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            if (help.wasFound())
            {
                Usage.usage(USAGE, null, commandLine, arguments, System.out);
            }
            else
            {
                int n = nodeCount.wasFound() ? nodeCount.getValue().intValue() : DEFAULT_NODE_COUNT;
                int e = edgeCount.wasFound() ? edgeCount.getValue().intValue() : DEFAULT_EDGE_COUNT;
                String c = connectMethod.wasFound() ? connectMethod.getValue() : DEFAULT_CONNECT_METHOD;
                new GraphGenerator(n, e, c, graphFile.getValue()).run();
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