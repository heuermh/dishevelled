/*

    dsh-variation-cytoscape3-app  Variation Cytoscape3 app.
    Copyright (c) 2013-2014 held jointly by the individual authors.

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

import static org.dishevelled.variation.cytoscape3.internal.VariationUtils.ensemblGeneIds;
import static org.dishevelled.variation.cytoscape3.internal.VariationUtils.resultStatusMessage;

import ca.odell.glazedlists.util.concurrent.Lock;

import org.apache.commons.lang.StringUtils;
import org.cytoscape.model.CyNode;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import org.dishevelled.variation.Feature;

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


    /**
     * Create a new retrieve features task with the specified model.
     *
     * @param model model, must not be null
     */
    RetrieveFeaturesTask(final VariationModel model)
    {
        checkNotNull(model);
        this.model = model;
    }


    @Override
    public void run(final TaskMonitor taskMonitor)
    {
        taskMonitor.setTitle("Retrieve features");
        taskMonitor.setProgress(0.0d);

        // if merge strategy is retain, skip to end
        // foreach node in model
        //   retrieve existing features from node --> feature mapping
        //   foreach ensembl gene id per node
        //     retrieve feature for ensembl gene id
        //       if merge strategy is replace, remove existing node --> feature mappings and features from features and add new ones
        //       else if merge strategy is merge, remove existing node --> feature mappings and features from features, perform the merge and re-add merged ones
        //       (note that removing feature mappings should also remove downstream variations and consequences, and should update counts in node table)

        final Lock nodesReadLock = model.nodes().getReadWriteLock().readLock();
        final Lock featuresWriteLock = model.features().getReadWriteLock().writeLock();
        nodesReadLock.lock();
        featuresWriteLock.lock();
        try
        {
            for (int i = 0, size = model.nodes().size(); i < size; i++)
            {
                CyNode node = model.nodes().get(i);
                for (String ensemblGeneId : ensemblGeneIds(node, model.getNetwork(), model.getEnsemblGeneIdColumn()))
                {
                    if (StringUtils.isNotBlank(ensemblGeneId))
                    {
                        taskMonitor.setStatusMessage("Retrieving genome feature for Ensembl Gene " + ensemblGeneId + "...");
                        Feature feature = model.getFeatureService().feature(model.getSpecies(), model.getReference(), ensemblGeneId);
                        taskMonitor.setStatusMessage(resultStatusMessage(feature == null ? 0 : 1, "genome feature", "Ensembl Gene", ensemblGeneId));
                        if (feature != null)
                        {
                            model.add(node, feature);
                        }
                    }
                }
                taskMonitor.setProgress(i / (double) size);
            }
        }
        finally
        {
            nodesReadLock.unlock();
            featuresWriteLock.unlock();
        }
        taskMonitor.setProgress(1.0d);
    }
}
