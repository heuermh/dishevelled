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

import com.google.common.collect.ImmutableList;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.work.swing.DialogTaskManager;

import org.dishevelled.iconbundle.IconBundle;

import org.dishevelled.iconbundle.impl.CachingIconBundle;
import org.dishevelled.iconbundle.impl.PNGIconBundle;

import org.dishevelled.identify.IdentifiableAction;

import org.dishevelled.variation.FeatureService;
import org.dishevelled.variation.VariationService;
import org.dishevelled.variation.VariationConsequenceService;
import org.dishevelled.variation.VariationConsequencePredictionService;

/**
 * Variation app.
 *
 * @author  Michael Heuer
 */
final class VariationApp
{
    /** Variation model. */
    private final VariationModel model;

    /** Config view. */
    private final ConfigView configView;

    /** Feature view. */
    private final FeatureView featureView;

    /** Variation view. */
    private final VariationView variationView;

    /** Variation consequences view. */
    private final VariationConsequenceView variationConsequenceView;

    /** Application manager. */
    private final CyApplicationManager applicationManager;

    /** Dialog task manager. */
    private final DialogTaskManager dialogTaskManager;

    /** Retrieve icon bundle. */
    private final IconBundle retrieveIconBundle = new CachingIconBundle(new PNGIconBundle("/org/dishevelled/variation/cytoscape3/internal/retrieve"));

    /** Add icon bundle. */
    private final IconBundle addIconBundle = new CachingIconBundle(new PNGIconBundle("/org/dishevelled/variation/cytoscape3/internal/add"));

    /** Annotate icon bundle. */
    private final IconBundle annotateIconBundle = new CachingIconBundle(new PNGIconBundle("/org/dishevelled/variation/cytoscape3/internal/annotate"));

    /** Predict icon bundle. */
    private final IconBundle predictIconBundle = new CachingIconBundle(new PNGIconBundle("/org/dishevelled/variation/cytoscape3/internal/predict"));

    /** Retrieve features action. */
    private final IdentifiableAction retrieveFeatures = new IdentifiableAction("Retrieve features...", retrieveIconBundle)
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                retrieveFeatures();
            }
        };

    /** Add variations action. */
    private final IdentifiableAction addVariations = new IdentifiableAction("Add variations...", addIconBundle)
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                addVariations();
            }
        };

    /** Annotate variation consequences action. */
    private final IdentifiableAction annotateVariationConsequences = new IdentifiableAction("Annotate variation consequences...", annotateIconBundle)
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                annotateVariationConsequences();
            }
        };

    /** Predict variation consequences action. */
    private final IdentifiableAction predictVariationConsequences = new IdentifiableAction("Predict variation consequences...", predictIconBundle)
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                predictVariationConsequences();
            }
        };


    /**
     * Create a new variation app.
     *
     * @param applicationManager application manager, must not be null
     * @param dialogTaskManager dialog task manager
     * @param featureService feature service, must not be null
     * @param variationService variation service, must not be null
     * @param variationConsequenceService variation consequence service, must not be null
     * @param variationConsequencePredictionService variation consequence prediction service, must not be null
     */
    VariationApp(final CyApplicationManager applicationManager,
                 final DialogTaskManager dialogTaskManager,
                 final FeatureService featureService,
                 final VariationService variationService,
                 final VariationConsequenceService variationConsequenceService,
                 final VariationConsequencePredictionService variationConsequencePredictionService)
    {
        checkNotNull(applicationManager);
        checkNotNull(dialogTaskManager);

        this.applicationManager = applicationManager;
        this.dialogTaskManager = dialogTaskManager;

        model = new VariationModel();

        // hmm...
        model.setFeatureService(featureService);
        model.setVariationService(variationService);
        model.setVariationConsequenceService(variationConsequenceService);
        model.setVariationConsequencePredictionService(variationConsequencePredictionService);

        configView = new ConfigView(model);
        featureView = new FeatureView(model);
        variationView = new VariationView(model);
        variationConsequenceView = new VariationConsequenceView(model);
        model.setNetwork(applicationManager.getCurrentNetwork());
    }


    /***
     * Return the variation model.
     *
     * @return the variation model
     */
    VariationModel model()
    {
        return model;
    }

    /**
     * Return the config view.
     *
     * @return the config view
     */
    ConfigView configView()
    {
        return configView;
    }

    /**
     * Return the feature view.
     *
     * @return the feature view
     */
    FeatureView featureView()
    {
        return featureView;
    }

    /**
     * Return the variation view.
     *
     * @return the variation view
     */
    VariationView variationView()
    {
        return variationView;
    }

    /**
     * Return the variation consequences view.
     *
     * @return the variation consequences view
     */
    VariationConsequenceView variationConsequenceView()
    {
        return variationConsequenceView;
    }

    /**
     * Retrieve features.
     */
    void retrieveFeatures()
    {
        //
        // Task factories should be singletons and should be provided by OSGi
        // Task factories should instantiate all the task's dependencies before calling createTaskIterator
        // Tunable parameters on Task seem better suited for primitives
        // Some implementations might need parameters; e.g. VCF file for VCF reader variation service
        //
        RetrieveFeaturesTaskFactory taskFactory = new RetrieveFeaturesTaskFactory(model);
        dialogTaskManager.execute(taskFactory.createTaskIterator());
    }

    /**
     * Add variations.
     */
    void addVariations()
    {
        AddVariationsTaskFactory taskFactory = new AddVariationsTaskFactory(model);
        dialogTaskManager.execute(taskFactory.createTaskIterator());
    }

    /**
     * Annotate variation consequences.
     */
    void annotateVariationConsequences()
    {
        AnnotateVariationConsequencesTaskFactory taskFactory = new AnnotateVariationConsequencesTaskFactory(model);
        dialogTaskManager.execute(taskFactory.createTaskIterator());
    }

    /**
     * Predict variation consequences.
     */
    void predictVariationConsequences()
    {
        PredictVariationConsequencesTaskFactory taskFactory = new PredictVariationConsequencesTaskFactory(model);
        dialogTaskManager.execute(taskFactory.createTaskIterator());
    }

    /**
     * Return the tool bar actions.
     *
     * @return the tool bar actions
     */
    Iterable<IdentifiableAction> getToolBarActions()
    {
        return ImmutableList.of(retrieveFeatures, addVariations, annotateVariationConsequences, predictVariationConsequences);
    }
}