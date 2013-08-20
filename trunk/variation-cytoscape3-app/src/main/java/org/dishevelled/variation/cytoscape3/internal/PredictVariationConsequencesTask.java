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

import static org.dishevelled.variation.cytoscape3.internal.VariationUtils.addConsequenceCounts;
import static org.dishevelled.variation.cytoscape3.internal.VariationUtils.addCount;
import static org.dishevelled.variation.cytoscape3.internal.VariationUtils.ensemblGeneIds;

import java.util.List;

import org.cytoscape.model.CyNode;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import org.dishevelled.variation.Feature;
import org.dishevelled.variation.FeatureService;
import org.dishevelled.variation.Variation;
import org.dishevelled.variation.VariationConsequence;
import org.dishevelled.variation.VariationConsequencePredictionService;

/**
 * Predict variation consequences task.
 *
 * @author  Michael Heuer
 */
final class PredictVariationConsequencesTask
    extends AbstractTask
{
    /** Variation model. */
    private final VariationModel model;

    /** Feature service. */
    private final FeatureService featureService;

    /** Variation consequence prediction service. */
    private final VariationConsequencePredictionService variationConsequencePredictionService;


    /**
     * Create a new predict variation consequences task.
     *
     * @param model model, must not be null
     * @param featureService feature service, must not be null
     * @param variationConsequencePredictionService variation consequence prediction service, must not be null
     */
    PredictVariationConsequencesTask(final VariationModel model,
                                     final FeatureService featureService,
                                     final VariationConsequencePredictionService variationConsequencePredictionService)
    {
        checkNotNull(model);
        checkNotNull(featureService);
        checkNotNull(variationConsequencePredictionService);

        this.model = model;
        this.featureService = featureService;
        this.variationConsequencePredictionService = variationConsequencePredictionService;
    }


    @Override
    public void run(final TaskMonitor taskMonitor)
    {
        taskMonitor.setTitle("Predict variation consequences");
        taskMonitor.setProgress(0.0d);

        FeatureIndex featureIndex = new FeatureIndex();

        List<CyNode> nodes = model.nodes();
        model.variationConsequences().getReadWriteLock().writeLock().lock();
        try
        {
            for (int i = 0, size = nodes.size(); i < size; i++)
            {
                CyNode node = nodes.get(i);
                for (String ensemblGeneId : ensemblGeneIds(node, model.getNetwork(), model.getEnsemblGeneIdColumn()))
                {
                    if (ensemblGeneId != null)
                    {
                        taskMonitor.setStatusMessage("Retrieving genome feature for Ensembl Gene " + ensemblGeneId + "...");
                        Feature feature = featureService.feature(model.getSpecies(), model.getReference(), ensemblGeneId);
                        if (feature != null)
                        {
                            // todo:  there might be more than one geneId associated with a node
                            featureIndex.add(node, feature);
                        }
                    }
                }
                taskMonitor.setProgress(0.1d * (i / (double) size));
            }
            // todo: need to determine the node <--> feature mapping from model
            featureIndex.buildTrees();

            // allocate 90% of progress to variation filtering and consequence prediction
            for (int i = 0, size = model.variations().size(); i < size; i++)
            {
                Variation variation = model.variations().get(i);
                for (Feature hit : featureIndex.hit(variation))
                {
                    taskMonitor.setStatusMessage("Predicting variation consequences for variation " + variation.getName() + ":" + variation.getStart() + "-" + variation.getEnd() + ":" + variation.getStrand() + " " + variation.getAlternateAlleles() + "...");
                    List<VariationConsequence> variationConsequences = variationConsequencePredictionService.predictConsequences(variation);
                    featureIndex.add(hit, variationConsequences);
                    taskMonitor.setStatusMessage("Predicted " + variationConsequences.size() + " variation consequences for variation " + variation.getName() + ":" + variation.getStart() + "-" + variation.getEnd() + ":" + variation.getStrand() + " " + variation.getAlternateAlleles() + "...");

                    for (VariationConsequence variationConsequence : variationConsequences)
                    {
                        // O(n)
                        if (!model.variationConsequences().contains(variationConsequence))
                        {
                            model.variationConsequences().add(variationConsequence);
                        }
                    }
                }
                taskMonitor.setProgress(0.1d + 0.9d * (i / (double) size));
            }

            // allocate 10% of progress to counts
            for (int i = 0, size = featureIndex.size(); i < size; i++)
            {
                CyNode node = featureIndex.nodeAt(i);
                List<VariationConsequence> variationConsequences = featureIndex.consequencesAt(i);
                // todo:  counts don't consider existing variations or variationConsequences
                addCount(node, model.getNetwork(), "variation_consequence_count", variationConsequences.size());
                addConsequenceCounts(node, model.getNetwork(), variationConsequences);

                taskMonitor.setProgress(0.9d + 0.1d * (i / (double) size));
            }
        }
        finally
        {
            model.variationConsequences().getReadWriteLock().writeLock().unlock();
        }
        featureIndex.clear();
        taskMonitor.setProgress(1.0d);
    }
}