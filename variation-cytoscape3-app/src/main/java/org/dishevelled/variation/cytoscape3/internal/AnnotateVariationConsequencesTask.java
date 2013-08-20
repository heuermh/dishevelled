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

import static org.dishevelled.variation.cytoscape3.internal.VariationUtils.addConsequenceCounts;
import static org.dishevelled.variation.cytoscape3.internal.VariationUtils.addCount;
import static org.dishevelled.variation.cytoscape3.internal.VariationUtils.ensemblGeneIds;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import org.cytoscape.model.CyNode;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import org.dishevelled.variation.Feature;
import org.dishevelled.variation.FeatureService;
import org.dishevelled.variation.Variation;
import org.dishevelled.variation.VariationService;
import org.dishevelled.variation.VariationConsequence;
import org.dishevelled.variation.VariationConsequenceService;

/**
 * Annotate variation consequences task.
 *
 * @author  Michael Heuer
 */
final class AnnotateVariationConsequencesTask
    extends AbstractTask
{
    /** Variation model. */
    private final VariationModel model;

    /** Feature service. */
    private final FeatureService featureService;

    /** Variation service. */
    private final VariationService variationService;

    /** Variation consequence service. */
    private final VariationConsequenceService variationConsequenceService;


    /**
     * Create a new annotate variation consequences task.
     *
     * @param model model, must not be null
     * @param featureService feature service, must not be null
     * @param variationService variation service, must not be null
     * @param variationConsequenceService variation consequence service, must not be null
     */
    AnnotateVariationConsequencesTask(final VariationModel model,
                                      final FeatureService featureService,
                                      final VariationService variationService,
                                      final VariationConsequenceService variationConsequenceService)
    {
        checkNotNull(model);
        checkNotNull(featureService);
        checkNotNull(variationService);
        checkNotNull(variationConsequenceService);

        this.model = model;
        this.featureService = featureService;
        this.variationService = variationService;
        this.variationConsequenceService = variationConsequenceService;
    }


    @Override
    public void run(final TaskMonitor taskMonitor)
    {
        taskMonitor.setTitle("Annotate variation consequences");
        taskMonitor.setProgress(0.0d);

        List<CyNode> nodes = model.nodes();
        model.variationConsequences().getReadWriteLock().writeLock().lock();
        try
        {
            for (int i = 0, size = nodes.size(); i < size; i++)
            {
                CyNode node = nodes.get(i);
                for (String ensemblGeneId : ensemblGeneIds(node, model.getNetwork(), model.getEnsemblGeneIdColumn()))
                {
                    if (StringUtils.isNotBlank(ensemblGeneId))
                    {
                        taskMonitor.setStatusMessage("Retrieving genome feature for Ensembl Gene " + ensemblGeneId + "...");
                        Feature feature = featureService.feature(model.getSpecies(), model.getReference(), ensemblGeneId);
                        if (feature != null)
                        {
                            taskMonitor.setStatusMessage("Retrieving variations associated with Ensembl Gene " + ensemblGeneId + " in the region " + feature.getName() + ":" + feature.getStart() + "-" + feature.getEnd() + ":" + feature.getStrand() + "...");
                            List<Variation> variations = variationService.variations(feature);
                            taskMonitor.setStatusMessage("Found " + variations.size() + " variations associated with Ensembl Gene " + ensemblGeneId);

                            List<VariationConsequence> allVariationConsequences = new ArrayList<VariationConsequence>(variations.size());
                            for (Variation variation : variations)
                            {
                                taskMonitor.setStatusMessage("Retrieving variation consequences associated with variation " + variation.getIdentifier() + "...");
                                List<VariationConsequence> variationConsequences = variationConsequenceService.consequences(variation);
                                allVariationConsequences.addAll(variationConsequences);
                                taskMonitor.setStatusMessage("Found " + variationConsequences.size() + " variation consequences associated with variation " + variation.getIdentifier());

                                for (VariationConsequence variationConsequence : variationConsequences)
                                {
                                    // O(n)
                                    if (!model.variationConsequences().contains(variationConsequence))
                                    {
                                        model.variationConsequences().add(variationConsequence);
                                    }
                                }
                            }
                            // todo:  counts don't consider existing variations or variationConsequences
                            addCount(node, model.getNetwork(), "variation_consequence_count", allVariationConsequences.size());
                            addConsequenceCounts(node, model.getNetwork(), allVariationConsequences);
                        }
                    }
                }
                taskMonitor.setProgress(i / (double) size);
            }
        }
        finally
        {
            model.variationConsequences().getReadWriteLock().writeLock().unlock();
        }
        taskMonitor.setProgress(1.0d);
    }
}