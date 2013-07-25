/*

    dsh-variation-cytoscape3-app  Variation Cytoscape3 app.
    Copyright (c) 2013 held jointly by the individual authors.

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
package org.dishevelled.variation.cytoscape3.internal;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import org.dishevelled.variation.Feature;
import org.dishevelled.variation.FeatureService;
import org.dishevelled.variation.Variation;
import org.dishevelled.variation.VariationService;

/**
 * Annotate known variations task.
 *
 * @author  Michael Heuer
 */
final class AnnotateKnownVariationsTask
    extends AbstractTask
{
    /** Species. */
    private final String species;

    /** Reference. */
    private final String reference;

    /** Ensembl gene id column. */
    private final String ensemblGeneIdColumn;

    /** Network. */
    private final CyNetwork network;

    /** Feature service. */
    private final FeatureService featureService;

    /** Variation service. */
    private final VariationService variationService;


    /**
     * Create a new annotate known variations task.
     *
     * @param species species, must not be null
     * @param reference reference, must not be null
     * @param ensemblGeneIdColumn ensembl gene id column, must not be null
     * @param featureService feature service, must not be null
     * @param variationService variation service, must not be null
     */
    AnnotateKnownVariationsTask(final String species,
                                final String reference,
                                final String ensemblGeneIdColumn,
                                final CyNetwork network,
                                final FeatureService featureService,
                                final VariationService variationService)
    {
        checkNotNull(species);
        checkNotNull(reference);
        checkNotNull(ensemblGeneIdColumn);
        checkNotNull(network);
        checkNotNull(featureService);
        checkNotNull(variationService);

        this.species = species;
        this.reference = reference;
        this.ensemblGeneIdColumn = ensemblGeneIdColumn;
        this.network = network;
        this.featureService = featureService;
        this.variationService = variationService;
    }


    @Override
    public void run(final TaskMonitor taskMonitor)
    {
        taskMonitor.setTitle("Annotate known variations");
        taskMonitor.setProgress(0.0d);

        List<CyNode> nodes = network.getNodeList();
        for (int i = 0, size = nodes.size(); i < size; i++)
        {
            CyNode node = nodes.get(i);
            String ensemblGeneId = ensemblGeneId(node, network);
            if (ensemblGeneId != null)
            {
                taskMonitor.setStatusMessage("Retrieving genome feature for Ensembl Gene " + ensemblGeneId + "...");
                Feature feature = featureService.feature(species, reference, ensemblGeneId);
                if (feature != null)
                {
                    taskMonitor.setStatusMessage("Retrieving variations associated with Ensembl Gene " + ensemblGeneId + " in the region " + feature.getName() + ":" + feature.getStart() + "-" + feature.getEnd() + ":" + feature.getStrand() + "...");
                    List<Variation> variations = variationService.variations(feature);
                    addCount(node, network, variations.size());
                    taskMonitor.setStatusMessage("Found " + variations.size() + " variations associated with Ensembl Gene " + ensemblGeneId);
                    slowDown();
                }
                slowDown();
            }
            taskMonitor.setProgress(i / (double) size);
        }
        taskMonitor.setProgress(1.0d);
    }

    private String ensemblGeneId(final CyNode node, final CyNetwork network)
    {
        CyTable table = network.getDefaultNodeTable();
        CyRow row = table.getRow(node.getSUID());
        return row.get(ensemblGeneIdColumn, String.class);
    }

    private void addCount(final CyNode node, final CyNetwork network, final int count)
    {
        CyTable table = network.getDefaultNodeTable();
        CyRow row = table.getRow(node.getSUID());
        if (table.getColumn("variation_count") == null)
        {
            table.createColumn("variation_count", Integer.class, false);
        }
        row.set("variation_count", count);
    }

    private void slowDown()
    {
        // slow down calls to prevent rate limit throttling
        try
        {
            Thread.sleep(666L);
        }
        catch (InterruptedException e)
        {
            // ignore
        }
    }
}