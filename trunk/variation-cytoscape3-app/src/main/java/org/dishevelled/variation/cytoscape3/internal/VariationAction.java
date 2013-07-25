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

import java.awt.event.ActionEvent;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.work.swing.DialogTaskManager;

import org.dishevelled.variation.FeatureService;
import org.dishevelled.variation.VariationConsequenceService;
import org.dishevelled.variation.VariationConsequencePredictionService;
import org.dishevelled.variation.VariationService;

/**
 * Variation action.
 *
 * @author  Michael Heuer
 */
final class VariationAction extends AbstractCyAction
{
    /** Application manager. */
    private final CyApplicationManager applicationManager;

    /** Dialog task manager. */
    private final DialogTaskManager dialogTaskManager;

    /** Feature service. */
    private final FeatureService featureService;

    /** Variation consequence service. */
    private final VariationConsequenceService variationConsequenceService;

    /** Variation consequence prediction service. */
    private final VariationConsequencePredictionService variationConsequencePredictionService;

    /** Variation service. */
    private final VariationService variationService;


    /**
     * Create a new variation action.
     *
     * @param applicationManager application manager, must not be null
     * @param dialogTaskManager dialog task manager, must not be null
     * @param featureService feature service, must not be null
     * @param variationConsequenceService variation consequence service, must not be null
     * @param variationConsequencePredictionService variation consequence prediction service, must not be null
     * @param variationService variation service, must not be null
     */
    VariationAction(final CyApplicationManager applicationManager,
                    final DialogTaskManager dialogTaskManager,
                    final FeatureService featureService,
                    final VariationConsequenceService variationConsequenceService,
                    final VariationConsequencePredictionService variationConsequencePredictionService,
                    final VariationService variationService)
    {
        super("Variation");
        setPreferredMenu("Apps");

        checkNotNull(applicationManager);
        checkNotNull(dialogTaskManager);
        checkNotNull(featureService);
        checkNotNull(variationConsequenceService);
        checkNotNull(variationConsequencePredictionService);
        checkNotNull(variationService);

        this.applicationManager = applicationManager;
        this.dialogTaskManager = dialogTaskManager;
        this.featureService = featureService;
        this.variationConsequenceService = variationConsequenceService;
        this.variationConsequencePredictionService = variationConsequencePredictionService;
        this.variationService = variationService;
    }


    @Override
    public void actionPerformed(final ActionEvent event)
    {
        if (event == null)
        {
            throw new NullPointerException("event must not be null");
        }
        CyNetwork network = applicationManager.getCurrentNetwork();
        AnnotateKnownVariationsTaskFactory annotateKnownVariationsTaskFactory = new AnnotateKnownVariationsTaskFactory("human",
                                                                                                                       "GRCh37",
                                                                                                                       "ensembl",
                                                                                                                       network,
                                                                                                                       featureService,
                                                                                                                       variationService);
        dialogTaskManager.execute(annotateKnownVariationsTaskFactory.createTaskIterator());
    }
}
