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

import static org.dishevelled.variation.cytoscape3.internal.VariationUtils.ensemblGeneId;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import org.dishevelled.variation.Feature;
import org.dishevelled.variation.FeatureService;

/**
 * Retrieve features task.
 *
 * @author  Michael Heuer
 */
final class RetrieveFeaturesTask
    extends AbstractTask
{
    /** Variation model. */
    private final VariationModel model;

    /** Feature service. */
    private final FeatureService featureService;


    /**
     * Create a new retrieve features task.
     *
     * @param model model, must not be null
     * @param network network, must not be null
     * @param featureService feature service, must not be null
     */
    RetrieveFeaturesTask(final VariationModel model,
                         final FeatureService featureService)
    {
        checkNotNull(model);
        checkNotNull(featureService);
        this.model = model;
        this.featureService = featureService;
    }


    @Override
    public void run(final TaskMonitor taskMonitor)
    {
        taskMonitor.setTitle("Retrieve features");
        taskMonitor.setProgress(0.0d);

        List<CyNode> nodes = model.nodes();
        model.features().getReadWriteLock().writeLock().lock();
        try
        {
            for (int i = 0, size = nodes.size(); i < size; i++)
            {
                CyNode node = nodes.get(i);
                String ensemblGeneId = ensemblGeneId(node, model.getNetwork(), model.getEnsemblGeneIdColumn());
                if (ensemblGeneId != null)
                {
                    taskMonitor.setStatusMessage("Retrieving genome feature for Ensembl Gene " + ensemblGeneId + "...");
                    Feature feature = featureService.feature(model.getSpecies(), model.getReference(), ensemblGeneId);
                    if (feature != null)
                    {
                        // O(n)
                        if (!model.features().contains(feature))
                        {
                            model.features().add(feature);
                        }
                    }
                }
                taskMonitor.setProgress(i/(double) size);
            }
        }
        finally
        {
            model.features().getReadWriteLock().writeLock().unlock();
        }
        taskMonitor.setProgress(1.0d);
    }
}