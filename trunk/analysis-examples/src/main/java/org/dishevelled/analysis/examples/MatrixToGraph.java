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

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.dishevelled.analysis.AnalysisUtils;

import org.dishevelled.commandline.ArgumentList;
import org.dishevelled.commandline.CommandLine;
import org.dishevelled.commandline.CommandLineParseException;
import org.dishevelled.commandline.CommandLineParser;
import org.dishevelled.commandline.Switch;
import org.dishevelled.commandline.Usage;

import org.dishevelled.commandline.argument.DoubleArgument;
import org.dishevelled.commandline.argument.FileArgument;

import org.dishevelled.functor.UnaryPredicate;

import org.dishevelled.graph.Graph;

import org.dishevelled.graph.io.graphml.GraphMLWriter;
import org.dishevelled.graph.io.graphml.GraphMLWriter.ValueHandler;

import org.dishevelled.matrix.Matrix2D;

import org.dishevelled.matrix.io.Matrix2DReader;

import org.dishevelled.matrix.io.impl.sparse.SparseMatrixMarketReader;

/**
 * Matrix to graph example.
 *
 * @author  Michael Heuer
 */
public final class MatrixToGraph
    implements Runnable
{
    /** Cutoff score. */
    private final double cutoff;

    /** Matrix file, if any. */
    private final File matrixFile;

    /** Graph file, if any. */
    private final File graphFile;

    /** Cutoff predicate. */
    private final UnaryPredicate<Double> cutoffPredicate = new UnaryPredicate<Double>()
    {
        @Override
        public boolean test(final Double score)
        {
            return (score >= cutoff);
        }
    };

    /** Default cutoff score, <code>0.5d</code>. */
    static final double DEFAULT_CUTOFF = 0.5d;

    /** Usage string. */
    private static final String USAGE = "java MatrixToGraph --cutoff 0.24 --matrix matrix --graph graph";

    /** Double value handler. */
    private static final ValueHandler<Double> DOUBLE_VALUE_HANDLER = new ValueHandler<Double>()
    {
        @Override
        public void write(final Double value, final XMLStreamWriter writer) throws IOException, XMLStreamException
        {
            writer.writeCharacters(value.toString());
        }
    };

    /** Long value handler. */
    private static final ValueHandler<Long> LONG_VALUE_HANDLER = new ValueHandler<Long>()
    {
        @Override
        public void write(final Long value, final XMLStreamWriter writer) throws IOException, XMLStreamException
        {
            writer.writeCharacters(value.toString());
        }
    };


    /**
     * Create a new matrix to graph example with the specified cutoff and matrix and graph files.
     *
     * @param cutoff cutoff score
     * @param matrixFile matrix file
     * @param graphFile graph file
     */
    public MatrixToGraph(final double cutoff, final File matrixFile, final File graphFile)
    {
        this.cutoff = cutoff;
        this.matrixFile = matrixFile;
        this.graphFile = graphFile;
    }


    @Override
    public void run()
    {
        try
        {
            Matrix2DReader<Double> reader = new SparseMatrixMarketReader();
            Matrix2D<Double> matrix = matrixFile == null ? reader.read(System.in) : reader.read(matrixFile);
            Graph<Long, Double> graph = AnalysisUtils.toGraph(matrix, cutoffPredicate);
            GraphMLWriter<Long, Double> graphWriter = new GraphMLWriter<Long, Double>();
            graphWriter.setNodeValueHandler(LONG_VALUE_HANDLER);
            graphWriter.setEdgeValueHandler(DOUBLE_VALUE_HANDLER);

            if (graphFile == null)
            {
                graphWriter.write(graph, System.out);
            }
            else
            {
                graphWriter.write(graph, graphFile);
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
            DoubleArgument cutoff = new DoubleArgument("c", "cutoff", "cutoff score, default 0.5", false);
            FileArgument matrixFile = new FileArgument("m", "matrix", "matrix file", false);
            FileArgument graphFile = new FileArgument("g", "graph", "graph file", false);
            arguments = new ArgumentList(help, cutoff, matrixFile, graphFile);
            commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            if (help.wasFound())
            {
                Usage.usage(USAGE, null, commandLine, arguments, System.out);
            }
            else
            {
                new MatrixToGraph(cutoff.wasFound() ? cutoff.getValue() : DEFAULT_CUTOFF, matrixFile.getValue(), graphFile.getValue()).run();
            }
        }
        catch (CommandLineParseException e)
        {
            Usage.usage(USAGE, e, commandLine, arguments, System.err);
        }
    }
}