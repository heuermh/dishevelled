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

import static org.dishevelled.variation.cytoscape3.internal.VariationUtils.addCount;
import static org.dishevelled.variation.cytoscape3.internal.VariationUtils.ensemblGeneId;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import org.dishevelled.variation.Feature;
import org.dishevelled.variation.FeatureService;
import org.dishevelled.variation.Variation;
import org.dishevelled.variation.VariationService;

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

    /** Feature service. */
    private final FeatureService featureService;

    /** Variation service. */
    private final VariationService variationService;


    /**
     * Create a new add variations task.
     *
     * @param model model, must not be null
     * @param featureService feature service, must not be null
     * @param variationService variation service, must not be null
     */
    AddVariationsTask(final VariationModel model,
                      final FeatureService featureService,
                      final VariationService variationService)
    {
        checkNotNull(model);
        checkNotNull(featureService);
        checkNotNull(variationService);

        this.model = model;
        this.featureService = featureService;
        this.variationService = variationService;
    }


    @Override
    public void run(final TaskMonitor taskMonitor)
    {
        taskMonitor.setTitle("Add variations");
        taskMonitor.setProgress(0.0d);

        List<CyNode> nodes = model.getNetwork().getNodeList();
        for (int i = 0, size = nodes.size(); i < size; i++)
        {
            CyNode node = nodes.get(i);
            String ensemblGeneId = ensemblGeneId(node, model.getNetwork(), model.getEnsemblGeneIdColumn());
            if (StringUtils.isNotBlank(ensemblGeneId))
            {
                taskMonitor.setStatusMessage("Retrieving genome feature for Ensembl Gene " + ensemblGeneId + "...");
                Feature feature = featureService.feature(model.getSpecies(), model.getReference(), ensemblGeneId);
                if (feature != null)
                {
                    taskMonitor.setStatusMessage("Retrieving variations associated with Ensembl Gene " + ensemblGeneId + " in the region " + feature.getName() + ":" + feature.getStart() + "-" + feature.getEnd() + ":" + feature.getStrand() + "...");
                    List<Variation> variations = variationService.variations(feature);
                    addCount(node, model.getNetwork(), "variation_count", variations.size());
                    taskMonitor.setStatusMessage("Found " + variations.size() + " variations associated with Ensembl Gene " + ensemblGeneId);
                }
            }
            taskMonitor.setProgress(i / (double) size);
        }
        taskMonitor.setProgress(1.0d);
    }

    /*

      Ensembl REST feature endpoint doesn't provide proper variation feature type , all feature_type are 'variant'

    private void addFeatureCounts(final CyNode node, final CyNetwork network, final List<Variation> variations)
    {
        Domain sf = sequenceFeatures();
        Map<String, Concept> indexByName = indexByName(sf);

        Authority so = sf.getAuthority();
        Assignable assignableNode = new AssignableNode(node);
        Set<Evidence> evidence = ImmutableSet.of(new Evidence("IEA", 1.0d, 1.0d));
        for (Variation variation : variations)
        {
            so.createAssignment(indexByName.get(variation.getFeatureType()), assignableNode, evidence);
        }

        CyTable table = network.getDefaultNodeTable();
        CyRow row = table.getRow(node.getSUID());
        for (Map.Entry<Concept, Integer> entry : countAssignments(domain))
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
    */
}