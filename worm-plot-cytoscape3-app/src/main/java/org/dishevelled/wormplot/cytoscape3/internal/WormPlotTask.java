/*

    dsh-worm-plot-cytoscape3-app  Worm plot Cytoscape 3 app.
    Copyright (c) 2014-2017 held jointly by the individual authors.

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
package org.dishevelled.wormplot.cytoscape3.internal;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.HashMap;
import java.util.Map;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.ImmutableList;

import org.biojava.nbio.core.exceptions.CompoundNotFoundException;

import org.biojava.nbio.core.sequence.AccessionID;
import org.biojava.nbio.core.sequence.DNASequence;

import org.biojava.nbio.core.sequence.io.FastaReaderHelper;
import org.biojava.nbio.core.sequence.io.FastaWriterHelper;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Worm plot task.
 *
 * @author  Michael Heuer
 */
@SuppressWarnings("deprecation")
final class WormPlotTask
    extends AbstractTask
{
    /** Worm plot model. */
    private final WormPlotModel model;

    /** Split FASTA description line pattern. */
    private static final Pattern NAME = Pattern.compile("^(.+):([0-9]+)-([0-9]+):([0-9]+)$");

    /** Logger. */
    private static final Logger logger = LoggerFactory.getLogger(WormPlotTask.class);


    /**
     * Create a new worm plot task.
     *
     * @param model model, must not be null
     */
    WormPlotTask(final WormPlotModel model)
    {
        checkNotNull(model);
        this.model = model;
    }


    @Override
    public void run(final TaskMonitor taskMonitor) throws Exception
    {
        taskMonitor.setProgress(0.0d);
        taskMonitor.setTitle("Generating worm plot...");
        try
        {
            taskMonitor.setStatusMessage("Splitting sequence...");
            File splitFasta = createSplitFasta(model.getSequenceFile(), model.getLength(), model.getOverlap());
            taskMonitor.setProgress(0.2d);

            taskMonitor.setStatusMessage("Calculating self-similarity via blastn...");
            File allVsAllBlastResult = allVsAllBlast(splitFasta);
            taskMonitor.setProgress(0.6d);

            taskMonitor.setStatusMessage("Importing network...");
            importNetwork(allVsAllBlastResult, model.getNetwork());
            taskMonitor.setProgress(1.0d);
        }
        catch (IOException e)
        {
            logger.error("could not generate worm plot", e);
            throw new WormPlotException("Unable to generate worm plot due to I/O error: " + e.getMessage(), e);
        }
    }


    /**
     * Create and return the split fasta file from the specified sequence file.
     *
     * @param sequenceFile sequence file
     * @param length length
     * @param overlap overlap
     * @return the split fasta file from the sequence file
     * @throws IOException if an I/O error occurs
     */
    private static File createSplitFasta(final File sequenceFile, final int length, final int overlap)
        throws IOException
    {
        File splitFasta = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try
        {
            if (sequenceFile == null)
            {
                throw new IOException("must provide a sequence file");
            }
            splitFasta = File.createTempFile("wormPlot", ".fa");
            inputStream = new BufferedInputStream(new FileInputStream(sequenceFile));
            outputStream = new BufferedOutputStream(new FileOutputStream(splitFasta));

            if (logger.isInfoEnabled())
            {
                logger.info("reading from sequenceFile {}", sequenceFile);
                logger.info("writing to splitFasta length {} overlap {} {}", length, overlap, splitFasta);
            }

            int sequencesRead = 0;
            int subsequencesWritten = 0;
            for (DNASequence sequence : FastaReaderHelper.readFastaDNASequence(inputStream).values())
            {
                sequencesRead++;

                if (logger.isTraceEnabled())
                {
                    logger.trace("read sequence {}", sequence);
                }

                int index = 0;
                for (int start = 1; start < sequence.getLength(); start += (length - overlap))
                {
                    int end = Math.min(sequence.getLength(), start + length);
                    String[] tokens = sequence.getAccession().getID().split("\\s+");
                    String subsequenceName = tokens[0] + ":" + start + "-" + end + ":" + index;
                    DNASequence subsequence = new DNASequence(sequence.getSubSequence(start, end).getSequenceAsString());
                    subsequence.setAccession(new AccessionID(subsequenceName));

                    if (logger.isTraceEnabled())
                    {
                        logger.trace("writing subsequence {}", subsequence);
                    }
                    FastaWriterHelper.writeNucleotideSequence(outputStream, ImmutableList.of(subsequence));
                    outputStream.flush();
                    index++;
                    subsequencesWritten++;
                }
            }
            if (logger.isInfoEnabled())
            {
                logger.info("read {} sequences from sequenceFile {}", sequencesRead, sequenceFile);
                logger.info("wrote {} subsequences to splitFasta {}", subsequencesWritten, splitFasta);
            }
        }
        catch (CompoundNotFoundException e)
        {
            throw new IOException(e.getMessage());
        }
        catch (Exception e)
        {
            throw new IOException(e.getMessage());
        }
        finally
        {
            try
            {
                inputStream.close();
            }
            catch (Exception e)
            {
                // ignore
            }
            try
            {
                outputStream.close();
            }
            catch (Exception e)
            {
                // ignore
            }
        }
        return splitFasta;
    }

    /**
     * Create and return the all vs all blast file from the specified split fasta file.
     *
     * @param splitFasta split fasta file
     * @return the all vs all blast file from the split fasta file
     * @throws IOException if an I/O error occurs
     */
    private static File allVsAllBlast(final File splitFasta) throws IOException
    {
        if (logger.isInfoEnabled())
        {
            logger.info("reading from splitFasta {}", splitFasta);
            logger.info("writing to blastdb {}", splitFasta);
        }

        // create blast db
        ProcessBuilder makeBlastDb = new ProcessBuilder("makeblastdb",
                                                        "-in", splitFasta.getPath(),
                                                        "-dbtype", "nucl");
        Process makeBlastDbProcess = makeBlastDb.start();
        try
        {
            makeBlastDbProcess.waitFor();
        }
        catch (InterruptedException e)
        {
            // ignore
        }

        if (logger.isInfoEnabled())
        {
            logger.info("wrote to blastdb {}", splitFasta);
        }

        // all vs all blast
        File blastResult = File.createTempFile("wormPlot", ".txt");

        if (logger.isInfoEnabled())
        {
            logger.info("writing to blastResult {}", blastResult);
        }

        ProcessBuilder blastn = new ProcessBuilder("blastn", "-db", splitFasta.getPath(),
                                                   "-query", splitFasta.getPath(),
                                                   "-outfmt", "6",
                                                   "-out", blastResult.getPath());
        Process blastnProcess = blastn.start();
        try
        {
            blastnProcess.waitFor();
        }
        catch (InterruptedException e)
        {
            // ignore
        }

        if (logger.isInfoEnabled())
        {
            logger.info("wrote to blastResult {}", blastResult);
        }

        return blastResult;
    }

    /**
     * Import the specified edge file into the specified network.
     *
     * @param edgeFile edge file to import
     * @param network network
     * @throws IOException if an I/O error occurs
     */
    private static void importNetwork(final File edgeFile, final CyNetwork network)
        throws IOException
    {
        BufferedReader reader = null;
        Map<String, CyNode> nodes = new HashMap<String, CyNode>(10000);
        try
        {
            reader = new BufferedReader(new FileReader(edgeFile));

            if (logger.isInfoEnabled())
            {
                logger.info("reading from edgeFile {}", edgeFile);
            }

            int lineNumber = 0;
            while (reader.ready())
            {
                String line = reader.readLine();
                if (line == null)
                {
                    break;
                }
                String[] tokens = line.split("\t");
                if (tokens.length == 12)
                {
                    String source = tokens[0].trim();
                    String target = tokens[1].trim();
                    double percentIdentity = Double.parseDouble(tokens[2].trim());
                    long alignmentLength = Long.parseLong(tokens[3].trim());
                    int mismatches = Integer.parseInt(tokens[4].trim());
                    int gapOpens = Integer.parseInt(tokens[5].trim());
                    long sourceStart = Long.parseLong(tokens[6].trim());
                    long sourceEnd = Long.parseLong(tokens[7].trim());
                    long targetStart = Long.parseLong(tokens[8].trim());
                    long targetEnd = Long.parseLong(tokens[9].trim());
                    double evalue = Double.parseDouble(tokens[10].trim());
                    double bitScore = Double.parseDouble(tokens[11].trim());

                    CyTable nodeTable = network.getDefaultNodeTable();
                    if (!nodes.containsKey(source))
                    {
                        CyNode node = network.addNode();
                        CyRow nodeRow = nodeTable.getRow(node.getSUID());
                        setValue(nodeTable, nodeRow, "name", String.class, source);

                        Matcher m = NAME.matcher(source);
                        if (m.matches())
                        {
                            String parent = m.group(1);
                            long start = Long.parseLong(m.group(2));
                            long end = Long.parseLong(m.group(3));
                            long length = end - start;
                            int index = Integer.parseInt(m.group(4));
                            setValue(nodeTable, nodeRow, "parent", String.class, parent);
                            setValue(nodeTable, nodeRow, "start", Long.class, start);
                            setValue(nodeTable, nodeRow, "end", Long.class, end);
                            setValue(nodeTable, nodeRow, "length", Long.class, length);
                            setValue(nodeTable, nodeRow, "index", Integer.class, index);
                        }
                        nodes.put(source, node);
                    }
                    if (!nodes.containsKey(target))
                    {
                        CyNode node = network.addNode();
                        CyRow nodeRow = nodeTable.getRow(node.getSUID());
                        setValue(nodeTable, nodeRow, "name", String.class, target);

                        Matcher m = NAME.matcher(target);
                        if (m.matches())
                        {
                            String parent = m.group(1);
                            long start = Long.parseLong(m.group(2));
                            long end = Long.parseLong(m.group(3));
                            long length = end - start;
                            int index = Integer.parseInt(m.group(4));
                            setValue(nodeTable, nodeRow, "parent", String.class, parent);
                            setValue(nodeTable, nodeRow, "start", Long.class, start);
                            setValue(nodeTable, nodeRow, "end", Long.class, end);
                            setValue(nodeTable, nodeRow, "length", Long.class, length);
                            setValue(nodeTable, nodeRow, "index", Integer.class, index);
                        }
                        nodes.put(target, node);
                    }

                    CyNode sourceNode = nodes.get(source);
                    CyNode targetNode = nodes.get(target);
                    CyEdge edge = network.addEdge(sourceNode, targetNode, true);
                    CyTable edgeTable = network.getDefaultEdgeTable();
                    CyRow edgeRow = edgeTable.getRow(edge.getSUID());

                    setValue(edgeTable, edgeRow, "percentIdentity", Double.class, percentIdentity);
                    setValue(edgeTable, edgeRow, "alignmentLength", Long.class, alignmentLength);
                    setValue(edgeTable, edgeRow, "mismatches", Integer.class, mismatches);
                    setValue(edgeTable, edgeRow, "gapOpens", Integer.class, gapOpens);
                    setValue(edgeTable, edgeRow, "sourceStart", Long.class, sourceStart);
                    setValue(edgeTable, edgeRow, "sourceEnd", Long.class, sourceEnd);
                    setValue(edgeTable, edgeRow, "targetStart", Long.class, targetStart);
                    setValue(edgeTable, edgeRow, "targetEnd", Long.class, targetEnd);
                    setValue(edgeTable, edgeRow, "evalue", Double.class, evalue);
                    setValue(edgeTable, edgeRow, "bitScore", Double.class, bitScore);
                }
                else
                {
                    if (logger.isWarnEnabled())
                    {
                        logger.warn("ill formatted line {}, length {}", lineNumber, tokens.length);
                    }
                }
                lineNumber++;
            }
        }
        finally
        {
            try
            {
                reader.close();
            }
            catch (Exception e)
            {
                // empty
            }
        }
    }

    /**
     * Set the specified value.
     *
     * @param <T> value type
     * @param table table
     * @param row row
     * @param columnName column name
     * @param columnClass column class
     * @param value value
     */
    private static <T> void setValue(final CyTable table,
                                     final CyRow row,
                                     final String columnName,
                                     final Class<?> columnClass,
                                     final T value)
    {
        if (table.getColumn(columnName) == null)
        {
            table.createColumn(columnName, columnClass, false);
        }
        row.set(columnName, value);
    }
}
