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
    // limit for sequences
    // whether to read paths


    /**
     * Create a new import Graphical Fragment Assembly (GFA) 1.0 task with the specified
     * application manager.
     *
     * @param applicationManager application manager, must not be null
     */
    ImportGfa1Task(final CyApplicationManager applicationManager)
    {
        checkNotNull(applicationManager);
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

        // todo: if network is null, need to create a new one
        final CyNetwork network = applicationManager.getCurrentNetwork();

        final Map<String, CyNode> nodes = new HashMap<String, CyNode>(segmentsByOrientation.size());

        for (Table.Cell<String, Orientation, Segment> c : segmentsByOrientation.cellSet()) {
            String id = c.getRowKey();
            Orientation orientation = c.getColumnKey();
            Segment segment = c.getValue();

            String name = id + (orientation.isForward() ? "+" : "-");
            String sequence = orientation.isForward() ? trim(segment.getSequence(), 100) : reverseComplement(segment.getSequence(), 100);

            CyTable nodeTable = network.getDefaultNodeTable();
            if (!nodes.containsKey(name))
            {
                CyNode node = network.addNode();
                CyRow nodeRow = nodeTable.getRow(node.getSUID());

                setValue(nodeTable, nodeRow, "name", String.class, name);
                setValue(nodeTable, nodeRow, "sequenceLength", Integer.class, segment.getSequence() == null ? null : segment.getSequence().length());
                setValue(nodeTable, nodeRow, "length", Integer.class, segment.getLengthOpt().orElse(null));
                setValue(nodeTable, nodeRow, "readCount", Integer.class, segment.getReadCountOpt().orElse(null));
                setValue(nodeTable, nodeRow, "fragmentCount", Integer.class, segment.getFragmentCountOpt().orElse(null));
                setValue(nodeTable, nodeRow, "kmerCount", Integer.class, segment.getKmerCountOpt().orElse(null));
                setValue(nodeTable, nodeRow, "sequenceChecksum", String.class, segment.containsSequenceChecksum() ? String.valueOf(segment.getSequenceChecksum()) : null);
                setValue(nodeTable, nodeRow, "sequenceUri", String.class, segment.getSequenceUriOpt().orElse(null));
                setValue(nodeTable, nodeRow, "sequence", String.class, sequence);

                nodes.put(name, node);
            }
        }
        segmentsByOrientation.clear();

        taskMonitor.setStatusMessage("Building Cytoscape edges from links ...");

        for (Link link : links)
        {
            String sourceId = link.getSource().getId() + (link.getSource().isForwardOrientation() ? "+" : "-");
            String targetId = link.getTarget().getId() + (link.getTarget().isForwardOrientation() ? "+" : "-");
            CyNode sourceNode = nodes.get(sourceId);
            CyNode targetNode = nodes.get(targetId);
            CyEdge edge = network.addEdge(sourceNode, targetNode, true);
            CyTable edgeTable = network.getDefaultEdgeTable();
            CyRow edgeRow = edgeTable.getRow(edge.getSUID());

            setValue(edgeTable, edgeRow, "id", String.class, link.getIdOpt().orElse(null));
            setValue(edgeTable, edgeRow, "overlap", String.class, link.getOverlapOpt().orElse(null));
            setValue(edgeTable, edgeRow, "readCount", Integer.class, link.getReadCountOpt().orElse(null));
            setValue(edgeTable, edgeRow, "fragmentCount", Integer.class, link.getFragmentCountOpt().orElse(null));
            setValue(edgeTable, edgeRow, "kmerCont", Integer.class, link.getKmerCountOpt().orElse(null));
            setValue(edgeTable, edgeRow, "mappingQuality", Integer.class, link.getMappingQualityOpt().orElse(null));
            setValue(edgeTable, edgeRow, "mismatchCount", Integer.class, link.getMismatchCountOpt().orElse(null));
        }
        nodes.clear();
        links.clear();

        // todo: visual mapping, layout
        // pass paths to AssemblyApp if requested
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

    /**
     * Trim the specified value to the limit, if necessary.
     *
     * @param value value to trim
     * @param limit limit
     * @return the specified value trimmed to the limit, if necessary
     */
    private String trim(final String value, final int limit) {
        if (value == null)
        {
            return "";
        }
        if (value.length() > limit)
        {
            return value.substring(0, limit) + "...";
        }
        return value;
    }

    /**
     * Reverse complement and trim the specified value to the limit, if necessary.
     *
     * @param value value to trim
     * @param limit limit
     * @return the specified value reverse complemented and trimmed to the limit, if necessary
     */
    private String reverseComplement(final String value, final int limit) {
        if (value == null)
        {
            return "";
        }
        int size = Math.min(value.length(), limit);
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
        return sb.toString() + ((value.length() > limit) ? "..." : "");
    }
}
