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

import static org.dishevelled.variation.cytoscape3.internal.VariationUtils.addCount;
import static org.dishevelled.variation.cytoscape3.internal.VariationUtils.resultStatusMessage;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import ca.odell.glazedlists.util.concurrent.Lock;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import org.dishevelled.variation.Feature;
import org.dishevelled.variation.Variation;

/**
 * Add variations task.
 *
 * @author  Michael Heuer
 */
final class AddVariationsTask
    extends AbstractTask
{
    /** Variation model. */
    private final VariationModel model;


    /**
     * Create a new add variations task with the specified model.
     *
     * @param model model, must not be null
     */
    AddVariationsTask(final VariationModel model)
    {
        checkNotNull(model);
        this.model = model;
    }


    @Override
    public void run(final TaskMonitor taskMonitor)
    {
        taskMonitor.setTitle("Add variations");
        taskMonitor.setProgress(0.0d);

        final Lock nodesReadLock = model.nodes().getReadWriteLock().readLock();
        final Lock featuresReadLock = model.features().getReadWriteLock().readLock();
        final Lock variationsWriteLock = model.variations().getReadWriteLock().writeLock();
        nodesReadLock.lock();
        featuresReadLock.lock();
        variationsWriteLock.lock();
        try
        {
            for (int i = 0, size = model.features().size(); i < size; i++)
            {
                Feature feature = model.features().get(i);
                taskMonitor.setStatusMessage("Retrieving variations associated with feature " + feature + "...");
                final List<Variation> variations = model.getVariationService().variations(feature);
                taskMonitor.setStatusMessage(resultStatusMessage(variations.size(), "variation", "feature", feature));

                // todo:  merge strategy
                for (Variation variation : variations)
                {
                    if (!model.variations().contains(variation))
                    {
                        model.variations().add(variation);
                    }
                }

                // todo:  count doesn't consider existing variations
                addCount(model.nodeFor(feature), model.getNetwork(), "variation_count", variations.size());
                taskMonitor.setProgress(i / (double) size);
            }
        }
        finally
        {
            nodesReadLock.unlock();
            featuresReadLock.unlock();
            variationsWriteLock.unlock();
        }
        taskMonitor.setProgress(1.0d);
    }
}