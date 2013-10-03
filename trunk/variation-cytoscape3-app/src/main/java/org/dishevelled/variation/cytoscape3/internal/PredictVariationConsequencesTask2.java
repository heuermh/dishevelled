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
final class PredictVariationConsequencesTask2
    extends AbstractTask
{
    /** Variation model. */
    private final VariationModel model;


    /**
     * Create a new predict variation consequences task with the specified model.
     *
     * @param model model, must not be null
     */
    PredictVariationConsequencesTask2(final VariationModel model)
    {
        checkNotNull(model);
        this.model = model;
    }


    @Override
    public void run(final TaskMonitor taskMonitor)
    {
        taskMonitor.setTitle("Predict variation consequences");
        taskMonitor.setProgress(0.0d);

        List<CyNode> nodes = model.nodes();
        model.variationConsequences().getReadWriteLock().writeLock().lock();
        try
        {
            model.rebuildTrees();

            // allocate 90% of progress to variation filtering and consequence prediction
            for (int i = 0, size = model.variations().size(); i < size; i++)
            {
                Variation variation = model.variations().get(i);
                for (Feature hit : model.hit(variation))
                {
                    taskMonitor.setStatusMessage("Predicting variation consequences for variation " + variation + "...");
                    List<VariationConsequence> variationConsequences = model.getVariationConsequencePredictionService().predictConsequences(variation);
                    taskMonitor.setStatusMessage("Predicted " + variationConsequences.size() + " variation consequences for variation " + variation);

                    model.add(hit, variationConsequences);
                    model.variationConsequences().addAll(variationConsequences);
                }
                taskMonitor.setProgress(0.1d + 0.9d * (i / (double) size));
            }

            // allocate 10% of progress to counts
            for (int i = 0, size = model.nodes().size(); i < size; i++)
            {
                CyNode node = model.nodes().get(i);
                Feature feature = model.featureFor(node);
                List<VariationConsequence> variationConsequences = model.consequencesFor(feature);

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
        taskMonitor.setProgress(1.0d);
    }
}