/*

    dsh-assembly-cytoscape3-app  Assembly Cytoscape3 app.
    Copyright (c) 2019 held jointly by the individual authors.

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

import static org.dishevelled.bio.assembly.gfa1.Gfa1Reader.stream;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.HashBasedTable;
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

import org.dishevelled.bio.assembly.gfa.Orientation;
import org.dishevelled.bio.assembly.gfa.Reference;

import org.dishevelled.bio.assembly.gfa1.Gfa1Adapter;
import org.dishevelled.bio.assembly.gfa1.Link;
import org.dishevelled.bio.assembly.gfa1.Path;
import org.dishevelled.bio.assembly.gfa1.Segment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Import Graphical Fragment Assembly (GFA) 1.0 task.
 *
 * @author  Michael Heuer
 */
public final class ImportGfa1Task extends AbstractTask
{
    /** Assembly model. */
    private final AssemblyModel assemblyModel;

    /** Application manager. */
    private final CyApplicationManager applicationManager;

    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /** Input file tunable. */
    @Tunable(description = "Graphical Fragment Assembly (GFA) 1.0 file",
             tooltip = "<html>Input file in Graphical Fragment Assembly (GFA) 1.0 format.<br/>Compressed files (.bgz, .bzip2, .gz) are supported.</html>",
             required = true,
             params = "input=true;fileCategory=unspecified")
    public File inputFile;


    // other tunables:
    // boolean for sequences
    // limit for display sequences
    // whether to read paths


    /**
     * Create a new import Graphical Fragment Assembly (GFA) 1.0 task with the specified
     * application manager.
     *
     * @param assemblyModel assembly model, must not be null
     * @param applicationManager application manager, must not be null
     */
    ImportGfa1Task(final AssemblyModel assemblyModel, final CyApplicationManager applicationManager)
    {
        checkNotNull(assemblyModel);
        checkNotNull(applicationManager);
        this.assemblyModel = assemblyModel;
        this.applicationManager = applicationManager;
    }


    @Override
    public void run(final TaskMonitor taskMonitor) throws Exception
    {
        taskMonitor.setTitle("Import a network in Graphical Fragment Assembly (GFA) 1.0 format");

        final Map<String, Segment> segmentsById = new HashMap<String, Segment>();
        final Table<String, Orientation, Segment> segmentsByOrientation = HashBasedTable.create();

        taskMonitor.setStatusMessage("Reading segments from file ...");

        try (BufferedReader readable = new BufferedReader(new FileReader(inputFile)))
        {
            // stream segments, building cache
            stream(readable, new Gfa1Adapter()
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

        final List<Path> paths = new ArrayList<Path>();
        final List<Link> links = new ArrayList<Link>();

        try (BufferedReader readable = new BufferedReader(new FileReader(inputFile)))
        {
            // stream paths and links, looking for reverse orientation references
            stream(readable, new Gfa1Adapter()
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
                    protected boolean path(final Path path)
                    {
                        for (Reference reference : path.getSegments())
                        {
                            putIfAbsent(reference);
                        }
                        paths.add(path);
                        return true;
                    }

                    @Override
                    protected boolean link(final Link link)
                    {
                        putIfAbsent(link.getSource());
                        putIfAbsent(link.getTarget());
                        links.add(link);
                        return true;
                    }
                });
        }
        segmentsById.clear();

        taskMonitor.setStatusMessage("Building Cytoscape nodes from segments ...");

        final CyNetwork network = applicationManager.getCurrentNetwork();

        final Map<String, CyNode> nodes = new HashMap<String, CyNode>(segmentsByOrientation.size());

        // potential tunables
        int limit = 24;
        boolean loadSequences = true;
        boolean loadPaths = true;

        for (Table.Cell<String, Orientation, Segment> c : segmentsByOrientation.cellSet()) {
            String id = c.getRowKey();
            Orientation orientation = c.getColumnKey();
            Segment segment = c.getValue();

            String name = id + (orientation.isForward() ? "+" : "-");

            if (!nodes.containsKey(name))
            {
                CyNode node = network.addNode();
                CyTable nodeTable = network.getDefaultNodeTable();
                CyRow nodeRow = nodeTable.getRow(node.getSUID());

                Integer length = segment.getLengthOpt().orElse(null);
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
                    String displaySequence = trimFromMiddle(sequence, limit);
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
                if (displayLength != null) {
                    sb.append(" ");
                    sb.append(displayLength);
                    sb.append("bp");
                }
                if (readCount != null) {
                    sb.append(" ");
                    sb.append(readCount);
                    sb.append(" reads");
                }
                if (fragmentCount != null) {
                    sb.append(" ");
                    sb.append(fragmentCount);
                    sb.append(" fragments");
                }
                if (kmerCount != null) {
                    sb.append(" ");
                    sb.append(kmerCount);
                    sb.append(" kmers");
                }
                String displayLabel = sb.toString();

                setValue(nodeTable, nodeRow, "displayLength", Integer.class, displayLength);
                setValue(nodeTable, nodeRow, "displayLabel", String.class, displayLabel);

                nodes.put(name, node);
            }
        }
        segmentsByOrientation.clear();

        taskMonitor.setStatusMessage("Building Cytoscape edges from links ...");

        for (Link link : links)
        {
            String sourceId = link.getSource().getId();
            String sourceOrientation = link.getSource().isForwardOrientation() ? "+" : "-";
            String targetId = link.getTarget().getId();
            String targetOrientation = link.getTarget().isForwardOrientation() ? "+" : "-";
            CyNode sourceNode = nodes.get(sourceId + sourceOrientation);
            CyNode targetNode = nodes.get(targetId + targetOrientation);
            CyEdge edge = network.addEdge(sourceNode, targetNode, true);
            CyTable edgeTable = network.getDefaultEdgeTable();
            CyRow edgeRow = edgeTable.getRow(edge.getSUID());

            setValue(edgeTable, edgeRow, "id", String.class, link.getIdOpt().orElse(null));
            setValue(edgeTable, edgeRow, "sourceId", String.class, sourceId);
            setValue(edgeTable, edgeRow, "sourceOrientation", String.class, sourceOrientation);
            setValue(edgeTable, edgeRow, "targetId", String.class, targetId);
            setValue(edgeTable, edgeRow, "targetOrientation", String.class, targetOrientation);
            setValue(edgeTable, edgeRow, "overlap", String.class, link.getOverlapOpt().orElse(null));
            setValue(edgeTable, edgeRow, "readCount", Integer.class, link.getReadCountOpt().orElse(null));
            setValue(edgeTable, edgeRow, "fragmentCount", Integer.class, link.getFragmentCountOpt().orElse(null));
            setValue(edgeTable, edgeRow, "kmerCount", Integer.class, link.getKmerCountOpt().orElse(null));
            setValue(edgeTable, edgeRow, "mappingQuality", Integer.class, link.getMappingQualityOpt().orElse(null));
            setValue(edgeTable, edgeRow, "mismatchCount", Integer.class, link.getMismatchCountOpt().orElse(null));
        }
        nodes.clear();
        links.clear();

        // pass paths to AssemblyApp if requested
        if (loadPaths)
        {
            assemblyModel.setInputFileName(inputFile.toString());
            assemblyModel.setPaths(paths);
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
