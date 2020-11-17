/*

    dsh-assembly-cytoscape3-app  Assembly Cytoscape3 app.
    Copyright (c) 2019-2020 held jointly by the individual authors.

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
package org.dishevelled.assembly.cytoscape3.internal;

import static com.google.common.base.Preconditions.checkNotNull;

import static org.dishevelled.bio.assembly.gfa2.Gfa2Reader.stream;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Table;

import org.cytoscape.application.CyApplicationManager;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.Tunable;

import org.dishevelled.bio.assembly.gfa2.Gfa2Adapter;
import org.dishevelled.bio.assembly.gfa2.Edge;
import org.dishevelled.bio.assembly.gfa2.Gap;
import org.dishevelled.bio.assembly.gfa2.Orientation;
import org.dishevelled.bio.assembly.gfa2.Path;
import org.dishevelled.bio.assembly.gfa2.Reference;
import org.dishevelled.bio.assembly.gfa2.Segment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Import Graphical Fragment Assembly (GFA) 2.0 task.
 *
 * @author  Michael Heuer
 */
public final class ImportGfa2Task extends AbstractTask
{
    /** Assembly model. */
    private final AssemblyModel assemblyModel;

    /** Application manager. */
    private final CyApplicationManager applicationManager;

    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /** Input file tunable. */
    @Tunable(description = "Graphical Fragment Assembly (GFA) 2.0 file",
             tooltip = "<html>Input file in Graphical Fragment Assembly (GFA) 2.0 format.<br/>Compressed files (.bgz, .bzip2, .gz) are supported.</html>",
             required = true,
             params = "input=true;fileCategory=unspecified")
    public File inputFile;

    /** True if sequences should be loaded. */
    @Tunable(description = "Load sequences",
             tooltip = "<html>True if sequences should be loaded into Cytoscape<br/>from GFA 2.0 segment records.</html>",
             required = true)
    public boolean loadSequences = true;

    /** True if paths should be loaded. */
    @Tunable(description = "Load paths",
             tooltip = "<html>True if paths should be loaded into Cytoscape<br/>from GFA 2.0 path records.</html>",
             required = true)
    public boolean loadPaths = true;

    /** Truncate display sequences to the specified limit. */
    @Tunable(description = "Display sequence limit, in base pairs (bp)",
             tooltip = "<html>Truncate sequences to the specified limit<br/>before mapping to node label.</html>",
             required = true)
    public int displaySequenceLimit = 20;


    /**
     * Create a new import Graphical Fragment Assembly (GFA) 2.0 task with the specified
     * application manager.
     *
     * @param assemblyModel assembly model, must not be null
     * @param applicationManager application manager, must not be null
     */
    ImportGfa2Task(final AssemblyModel assemblyModel, final CyApplicationManager applicationManager)
    {
        checkNotNull(assemblyModel);
        checkNotNull(applicationManager);
        this.assemblyModel = assemblyModel;
        this.applicationManager = applicationManager;
    }


    @Override
    public void run(final TaskMonitor taskMonitor) throws Exception
    {
        taskMonitor.setTitle("Import a network in Graphical Fragment Assembly (GFA) 2.0 format");

        final Map<String, Segment> segmentsById = new HashMap<String, Segment>();

        taskMonitor.setStatusMessage("Reading segments from file ...");

        try (BufferedReader readable = new BufferedReader(new FileReader(inputFile)))
        {
            // stream segments, building cache
            stream(readable, new Gfa2Adapter()
                {
                    @Override
                    protected boolean segment(final Segment segment)
                    {
                        segmentsById.put(segment.getId(), segment);
                        return true;
                    }
                });
        }

        taskMonitor.setStatusMessage("Finding reverse orientation references ...");

        final Table<String, Orientation, Segment> segmentsByOrientation = HashBasedTable.create();
        final List<Edge> edges = new ArrayList<Edge>();
        final List<Gap> gaps = new ArrayList<Gap>();
        final List<Path> paths = new ArrayList<Path>();

        try (BufferedReader readable = new BufferedReader(new FileReader(inputFile)))
        {
            // stream edges, gaps, and paths, looking for reverse orientation references
            stream(readable, new Gfa2Adapter()
                {
                    private void putIfAbsent(final Reference reference)
                    {
                        Segment segment = segmentsById.get(reference.getId());

                        if (segment == null)
                        {
                            throw new RuntimeException("could not find segment by id " + reference.getId());
                        }

                        if (!segmentsByOrientation.contains(reference.getId(), reference.getOrientation()))
                        {
                            segmentsByOrientation.put(reference.getId(), reference.getOrientation(), segment);
                        }
                    }

                    @Override
                    public boolean edge(final Edge edge)
                    {
                        putIfAbsent(edge.getSource());
                        putIfAbsent(edge.getTarget());
                        edges.add(edge);
                        return true;
                    }

                    @Override
                    public boolean gap(final Gap gap)
                    {
                        putIfAbsent(gap.getSource());
                        putIfAbsent(gap.getTarget());
                        gaps.add(gap);
                        return true;
                    }

                    @Override
                    public boolean path(final Path path)
                    {
                        for (Reference reference : path.getReferences())
                        {
                            putIfAbsent(reference);
                        }
                        if (loadPaths)
                        {
                            paths.add(path);
                        }
                        return true;
                    }
                });
        }
        logger.info("read {} segments, {} edges, {} gaps, and {} paths from {}",
                    new Object[] { segmentsById.size(), edges.size(), gaps.size(), paths.size(), inputFile });
        segmentsById.clear();

        taskMonitor.setStatusMessage("Building Cytoscape nodes from segments ...");

        final CyNetwork network = applicationManager.getCurrentNetwork();
        final Map<String, CyNode> nodes = new HashMap<String, CyNode>(segmentsByOrientation.size());

        for (Table.Cell<String, Orientation, Segment> c : segmentsByOrientation.cellSet())
        {
            String id = c.getRowKey();
            Orientation orientation = c.getColumnKey();
            Segment segment = c.getValue();

            String name = id + (orientation.isForward() ? "+" : "-");

            if (!nodes.containsKey(name))
            {
                CyNode node = network.addNode();
                CyTable nodeTable = network.getDefaultNodeTable();
                CyRow nodeRow = nodeTable.getRow(node.getSUID());

                Integer length = segment.getLength();
                Integer readCount = segment.getReadCountOpt().orElse(null);
                Integer fragmentCount = segment.getFragmentCountOpt().orElse(null);
                Integer kmerCount = segment.getKmerCountOpt().orElse(null);
                String sequenceChecksum = segment.containsSequenceChecksum() ? String.valueOf(segment.getSequenceChecksum()) : null;
                String sequenceUri = segment.getSequenceUriOpt().orElse(null);

                setValue(nodeTable, nodeRow, "name", String.class, name);
                setValue(nodeTable, nodeRow, "length", Integer.class, length);
                setValue(nodeTable, nodeRow, "readCount", Integer.class, readCount);
                setValue(nodeTable, nodeRow, "fragmentCount", Integer.class, fragmentCount);
                setValue(nodeTable, nodeRow, "kmerCount", Integer.class, kmerCount);
                setValue(nodeTable, nodeRow, "sequenceChecksum", String.class, sequenceChecksum);
                setValue(nodeTable, nodeRow, "sequenceUri", String.class, sequenceUri);

                // default display length to length
                Integer displayLength = length;

                String sequence = orientation.isForward() ? segment.getSequence() : reverseComplement(segment.getSequence());
                if (sequence != null)
                {
                    Integer sequenceLength = sequence.length();
                    String displaySequence = trimFromMiddle(sequence, displaySequenceLimit);
                    Integer displaySequenceLength = displaySequence.length();

                    if (loadSequences)
                    {
                        setValue(nodeTable, nodeRow, "sequence", String.class, sequence);
                    }
                    setValue(nodeTable, nodeRow, "sequenceLength", Integer.class, sequenceLength);
                    setValue(nodeTable, nodeRow, "displaySequence", String.class, displaySequence);
                    setValue(nodeTable, nodeRow, "displaySequenceLength", Integer.class, displaySequenceLength);

                    // override display length with sequence length if necessary
                    if (length == null || length != sequenceLength)
                    {
                        displayLength = sequenceLength;
                    }
                }

                StringBuilder sb = new StringBuilder();
                sb.append(name);
                if (displayLength != null)
                {
                    sb.append("  ");
                    sb.append(displayLength);
                    sb.append(" bp");
                }
                String displayName = sb.toString();

                if (readCount != null)
                {
                    sb.append(" ");
                    sb.append(readCount);
                    sb.append(" reads");
                }
                if (fragmentCount != null)
                {
                    sb.append(" ");
                    sb.append(fragmentCount);
                    sb.append(" fragments");
                }
                if (kmerCount != null)
                {
                    sb.append(" ");
                    sb.append(kmerCount);
                    sb.append(" kmers");
                }
                String displayLabel = sb.toString();

                setValue(nodeTable, nodeRow, "displayName", String.class, displayName);
                setValue(nodeTable, nodeRow, "displayLength", Integer.class, displayLength);
                setValue(nodeTable, nodeRow, "displayLabel", String.class, displayLabel);

                nodes.put(name, node);
            }
        }
        logger.info("converted segments and orientation to " + nodes.size() + " nodes");
        segmentsByOrientation.clear();

        taskMonitor.setStatusMessage("Building Cytoscape edges from edges and gaps ...");

        for (Edge edge : edges)
        {
            String sourceId = edge.getSource().getId();
            String sourceOrientation = edge.getSource().isForwardOrientation() ? "+" : "-";
            String targetId = edge.getTarget().getId();
            String targetOrientation = edge.getTarget().isForwardOrientation() ? "+" : "-";
            CyNode sourceNode = nodes.get(sourceId + sourceOrientation);
            CyNode targetNode = nodes.get(targetId + targetOrientation);
            CyEdge cyEdge = network.addEdge(sourceNode, targetNode, true);
            CyTable edgeTable = network.getDefaultEdgeTable();
            CyRow edgeRow = edgeTable.getRow(cyEdge.getSUID());

            setValue(edgeTable, edgeRow, "id", String.class, edge.getIdOpt().orElse(null));
            setValue(edgeTable, edgeRow, "type", String.class, "edge");
            setValue(edgeTable, edgeRow, "sourceId", String.class, sourceId);
            setValue(edgeTable, edgeRow, "sourceOrientation", String.class, sourceOrientation);
            setValue(edgeTable, edgeRow, "targetId", String.class, targetId);
            setValue(edgeTable, edgeRow, "targetOrientation", String.class, targetOrientation);
            setValue(edgeTable, edgeRow, "sourceStart", String.class, edge.getSourceStart().toString());
            setValue(edgeTable, edgeRow, "sourceEnd", String.class, edge.getSourceEnd().toString());
            setValue(edgeTable, edgeRow, "targetStart", String.class, edge.getTargetStart().toString());
            setValue(edgeTable, edgeRow, "targetEnd", String.class, edge.getTargetEnd().toString());
            setValue(edgeTable, edgeRow, "alignment", String.class, edge.hasAlignment() ? edge.getAlignment().toString() : null);
            setValue(edgeTable, edgeRow, "readCount", Integer.class, edge.getReadCountOpt().orElse(null));
            setValue(edgeTable, edgeRow, "fragmentCount", Integer.class, edge.getFragmentCountOpt().orElse(null));
            setValue(edgeTable, edgeRow, "kmerCount", Integer.class, edge.getKmerCountOpt().orElse(null));
            setValue(edgeTable, edgeRow, "mappingQuality", Integer.class, edge.getMappingQualityOpt().orElse(null));
            setValue(edgeTable, edgeRow, "mismatchCount", Integer.class, edge.getMismatchCountOpt().orElse(null));
        }
        logger.info("converted edges to " + edges.size() + " edges");

        for (Gap gap : gaps)
        {
            String sourceId = gap.getSource().getId();
            String sourceOrientation = gap.getSource().isForwardOrientation() ? "+" : "-";
            String targetId = gap.getTarget().getId();
            String targetOrientation = gap.getTarget().isForwardOrientation() ? "+" : "-";
            CyNode sourceNode = nodes.get(sourceId + sourceOrientation);
            CyNode targetNode = nodes.get(targetId + targetOrientation);
            CyEdge edge = network.addEdge(sourceNode, targetNode, true);
            CyTable edgeTable = network.getDefaultEdgeTable();
            CyRow edgeRow = edgeTable.getRow(edge.getSUID());

            setValue(edgeTable, edgeRow, "id", String.class, gap.getIdOpt().orElse(null));
            setValue(edgeTable, edgeRow, "type", String.class, "gap");
            setValue(edgeTable, edgeRow, "sourceId", String.class, sourceId);
            setValue(edgeTable, edgeRow, "sourceOrientation", String.class, sourceOrientation);
            setValue(edgeTable, edgeRow, "targetId", String.class, targetId);
            setValue(edgeTable, edgeRow, "targetOrientation", String.class, targetOrientation);
            setValue(edgeTable, edgeRow, "distance", Integer.class, gap.getDistance());
            setValue(edgeTable, edgeRow, "variance", Integer.class, gap.getVarianceOpt().orElse(null));
        }
        logger.info("converted gaps to " + gaps.size() + " edges");
        nodes.clear();
        edges.clear();
        gaps.clear();

        // pass paths to AssemblyApp if requested
        if (loadPaths && !paths.isEmpty())
        {
            taskMonitor.setStatusMessage("Loading paths in path view ...");

            assemblyModel.setInputFileName(inputFile.toString());

            // todo: convert to gfa1 paths?
            //   note paths in gfa2 can have references to segments, edges, or other groups
            //assemblyModel.setPaths(paths, traversalsByPathName);
        }
    }


    // todo: move to util class or superclass

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
    static <T> void setValue(final CyTable table,
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

    /**
     * Trim the specified value to the specified limit.
     *
     * @param value value to trim, if any
     * @param limit limit
     * @return the specified value trimmed to the specified limit
     */
    static String trimFromMiddle(final String value, final int limit)
    {
        if (value == null)
        {
            return null;
        }
        if (limit < 3)
        {
            return value.substring(0, limit);
        }
        if (value.length() > limit)
        {
            int head = limit/2;
            int tail = limit/2 - 1;

            return value.substring(0, head) + "\u2026" + value.substring(value.length() - tail);
        }
        return value;
    }

    /**
     * Reverse complement the specified value.
     *
     * @param value value to reverse complement
     * @return the specified value reverse complemented
     */
    static String reverseComplement(final String value)
    {
        if (value == null)
        {
            return null;
        }
        int size = value.length();
        StringBuilder sb = new StringBuilder(size);
        for (int i = 0; i < size; i++)
        {
            char c = value.charAt(i);
            if ('A' == c)
            {
                sb.append('T');
            }
            else if ('a' == c)
            {
                sb.append('t');
            }
            else if ('C' == c)
            {
                sb.append('G');
            }
            else if ('c' == c)
            {
                sb.append('g');
            }
            else if ('G' == c)
            {
                sb.append('C');
            }
            else if ('g' == c)
            {
                sb.append('c');
            }
            else if ('T' == c)
            {
                sb.append('A');
            }
            else if ('t' == c)
            {
                sb.append('a');
            }
            else
            {
                throw new RuntimeException("invalid symbol " + c);
            }
        }
        return sb.toString();
    }
}
