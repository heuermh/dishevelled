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

import static org.dishevelled.variation.cytoscape3.internal.SequenceOntology.countAssignments;
import static org.dishevelled.variation.cytoscape3.internal.SequenceOntology.indexByName;
import static org.dishevelled.variation.cytoscape3.internal.SequenceOntology.sequenceVariants;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

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
import org.dishevelled.variation.VariationConsequence;
import org.dishevelled.variation.VariationConsequencePredictionService;

import org.dishevelled.vocabulary.AbstractAssignable;
import org.dishevelled.vocabulary.Assignable;
import org.dishevelled.vocabulary.Authority;
import org.dishevelled.vocabulary.Assignment;
import org.dishevelled.vocabulary.Concept;
import org.dishevelled.vocabulary.Domain;
import org.dishevelled.vocabulary.Evidence;
import org.dishevelled.vocabulary.Relation;

/**
 * Annotate variation consequences task.
 *
 * @author  Michael Heuer
 */
final class AnnotateVariationConsequencesTask
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

    /** Zero or more variations. */
    private final List<Variation> variations;

    /** Feature service. */
    private final FeatureService featureService;

    /** Variation consequence prediction service. */
    private final VariationConsequencePredictionService variationConsequencePredictionService;


    /**
     * Create a new annotate variation consequences task.
     *
     * @param species species, must not be null
     * @param reference reference, must not be null
     * @param ensemblGeneIdColumn ensembl gene id column, must not be null
     * @param featureService feature service, must not be null
     * @param variations zero or more variations, must not be null
     * @param variationConsequencePredictionService variation consequence prediction service, must not be null
     */
    AnnotateVariationConsequencesTask(final String species,
                                      final String reference,
                                      final String ensemblGeneIdColumn,
                                      final CyNetwork network,
                                      final FeatureService featureService,
                                      final List<Variation> variations,
                                      final VariationConsequencePredictionService variationConsequencePredictionService)
    {
        checkNotNull(species);
        checkNotNull(reference);
        checkNotNull(ensemblGeneIdColumn);
        checkNotNull(network);
        checkNotNull(featureService);
        checkNotNull(variations);
        checkNotNull(variationConsequencePredictionService);

        this.species = species;
        this.reference = reference;
        this.ensemblGeneIdColumn = ensemblGeneIdColumn;
        this.network = network;
        this.featureService = featureService;
        this.variations = variations;
        this.variationConsequencePredictionService = variationConsequencePredictionService;
    }


    @Override
    public void run(final TaskMonitor taskMonitor)
    {
        taskMonitor.setTitle("Annotate variation consequences");
        taskMonitor.setProgress(0.0d);

        FeatureIndex featureIndex = new FeatureIndex();

        // allocate 10% of progress to feature fetching
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
                    featureIndex.add(node, feature);
                }
            }
            taskMonitor.setProgress(0.1d * i/(double) size);
        }

        // allocate 90% of progress to variation filtering and consequence prediction
        for (int i = 0, size = variations.size(); i < size; i++)
        {
            Variation variation = variations.get(i);
            Feature hit = featureIndex.hit(variation);
            if (hit != null)
            {
                taskMonitor.setStatusMessage("Predicting variation consequences for variation " + variation.getName() + ":" + variation.getStart() + "-" + variation.getEnd() + ":" + variation.getStrand() + " " + variation.getAlternateAlleles() + "...");
                List<VariationConsequence> variationConsequences = variationConsequencePredictionService.predictConsequences(variation);
                featureIndex.add(hit, variationConsequences);
                taskMonitor.setStatusMessage("Predicted " + variationConsequences.size() + " variation consequences for variation " + variation.getName() + ":" + variation.getStart() + "-" + variation.getEnd() + ":" + variation.getStrand() + " " + variation.getAlternateAlleles() + "...");
            }
            taskMonitor.setProgress(0.1d + 0.9d * i/(double) size);
        }

        // allocate 10% of progress to counts
        for (int i = 0, size = featureIndex.size(); i < size; i++)
        {
            CyNode node = featureIndex.nodeAt(i);
            List<VariationConsequence> variationConsequences = featureIndex.consequencesAt(i);
            addCount(node, network, variationConsequences.size());
            addConsequenceCounts(node, network, variationConsequences);

            taskMonitor.setProgress(0.9d + 0.1d * i/(double) size);
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
        if (table.getColumn("variation_consequence_count") == null)
        {
            table.createColumn("variation_consequence_count", Integer.class, false);
        }
        row.set("variation_consequence_count", count);
    }

    private void addConsequenceCounts(final CyNode node, final CyNetwork network, final List<VariationConsequence> variationConsequences)
    {
        Domain sv = sequenceVariants();
        Map<String, Concept> indexByName = indexByName(sv);

        Authority so = sv.getAuthority();
        Assignable assignableNode = new AssignableNode(node);
        Set<Evidence> evidence = ImmutableSet.of(new Evidence("IEA", 1.0d, 1.0d));
        for (VariationConsequence variationConsequence : variationConsequences)
        {
            so.createAssignment(indexByName.get(variationConsequence.getSequenceOntologyTerm()), assignableNode, evidence);
        }

        CyTable table = network.getDefaultNodeTable();
        CyRow row = table.getRow(node.getSUID());
        for (Map.Entry<Concept, Integer> entry : countAssignments(sv).entrySet())
        {
            Concept concept = entry.getKey();
            Integer count = entry.getValue();

            if (table.getColumn(concept.getName()) == null)
            {
                table.createColumn(concept.getName(), Integer.class, false);
            }
            row.set(concept.getName(), count);
        }
    }

    class FeatureIndex
    {
        void add(CyNode node, Feature feature)
        {
            // empty
        }

        void add(Feature feature, List<VariationConsequence> variationConsequences)
        {
            // empty
        }

        Feature hit(Variation variation)
        {
            return null;
        }

        int size()
        {
            return 0;
        }

        CyNode nodeAt(int index)
        {
            return null;
        }

        List<VariationConsequence> consequencesAt(int index)
        {
            return null;
        }
    }

    private static final class AssignableNode extends AbstractAssignable
    {
        private final CyNode node;

        AssignableNode(final CyNode node)
        {
            super();
            this.node = node;
        }
    }
}