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

import org.cytoscape.model.CyNetwork;

import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

import org.dishevelled.variation.FeatureService;
import org.dishevelled.variation.VariationService;
import org.dishevelled.variation.VariationConsequenceService;

/**
 * Annotate known variation consequences task factory.
 *
 * @author  Michael Heuer
 */
final class AnnotateKnownVariationConsequencesTaskFactory
    extends AbstractTaskFactory
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

    /** Variation consequence service. */
    private final VariationConsequenceService variationConsequenceService;


    /**
     * Create a new annotate known variations task factory.
     *
     * @param species species, must not be null
     * @param reference reference, must not be null
     * @param ensemblGeneIdColumn ensembl gene id column, must not be null
     * @param featureService feature service, must not be null
     * @param variationService variation service, must not be null
     * @param variationConsequenceService variation consequence service, must not be null
     */
    AnnotateKnownVariationConsequencesTaskFactory(final String species,
                                                  final String reference,
                                                  final String ensemblGeneIdColumn,
                                                  final CyNetwork network,
                                                  final FeatureService featureService,
                                                  final VariationService variationService,
                                                  final VariationConsequenceService variationConsequenceService)
    {
        checkNotNull(species);
        checkNotNull(reference);
        checkNotNull(ensemblGeneIdColumn);
        checkNotNull(network);
        checkNotNull(featureService);
        checkNotNull(variationService);
        checkNotNull(variationConsequenceService);

        this.species = species;
        this.reference = reference;
        this.ensemblGeneIdColumn = ensemblGeneIdColumn;
        this.network = network;
        this.featureService = featureService;
        this.variationService = variationService;
        this.variationConsequenceService = variationConsequenceService;
    }

    @Override
    public TaskIterator createTaskIterator()
    {
        AnnotateKnownVariationConsequencesTask annotateKnownVariationConsequencesTask = new AnnotateKnownVariationConsequencesTask(species,
                                                                                                                                   reference,
                                                                                                                                   ensemblGeneIdColumn,
                                                                                                                                   network,
                                                                                                                                   featureService,
                                                                                                                                   variationService,
                                                                                                                                   variationConsequenceService);
        return new TaskIterator(annotateKnownVariationConsequencesTask);
    }
}