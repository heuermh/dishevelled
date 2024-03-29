/*

    dsh-variation-cytoscape3-app  Variation Cytoscape3 app.
    Copyright (c) 2013-2015 held jointly by the individual authors.

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

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.google.common.collect.ImmutableList;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.work.swing.DialogTaskManager;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;

import org.dishevelled.iconbundle.IconBundle;

import org.dishevelled.iconbundle.impl.CachingIconBundle;
import org.dishevelled.iconbundle.impl.PNGIconBundle;

import org.dishevelled.identify.IdentifiableAction;

/**
 * Variation app.
 *
 * @author  Michael Heuer
 */
final class VariationApp
    extends JPanel
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

    /** Visual mapping view. */
    private final VisualMappingView visualMappingView;

    /** Application manager. */
    private final CyApplicationManager applicationManager;

    /** Dialog task manager. */
    private final DialogTaskManager dialogTaskManager;

    /** Tabbed pane. */
    private final JTabbedPane tabbedPane;

    /** Retrieve icon bundle. */
    private final IconBundle retrieveIconBundle = new CachingIconBundle(new PNGIconBundle("/org/dishevelled/variation/cytoscape3/internal/retrieve"));

    /** Add icon bundle. */
    private final IconBundle addIconBundle = new CachingIconBundle(new PNGIconBundle("/org/dishevelled/variation/cytoscape3/internal/add"));

    /** Annotate icon bundle. */
    private final IconBundle annotateIconBundle = new CachingIconBundle(new PNGIconBundle("/org/dishevelled/variation/cytoscape3/internal/annotate"));

    /** Predict icon bundle. */
    private final IconBundle predictIconBundle = new CachingIconBundle(new PNGIconBundle("/org/dishevelled/variation/cytoscape3/internal/predict"));


    /** Retrieve features action. */
    private final class RetrieveFeaturesAction extends IdentifiableAction
    {
        /**
         * Create a new retrieve features action.
         */
        private RetrieveFeaturesAction()
        {
            super("Retrieve features...", retrieveIconBundle);
            putValue(Action.SHORT_DESCRIPTION, "<html>Retrieve genomic features for nodes annotated<br/>with Ensembl gene ids (e.g. ENSG00000157764).</html>");
        }

        @Override
        public void actionPerformed(final ActionEvent event)
        {
            retrieveFeatures();
        }
    };

    /** Retrieve features action. */
    private final RetrieveFeaturesAction retrieveFeatures = new RetrieveFeaturesAction();


    /** Add variations action. */
    private final class AddVariationsAction extends IdentifiableAction
    {
        /**
         * Create a new add variations action.
         */
        private AddVariationsAction()
        {
            super("Add variations...", addIconBundle);
            putValue(Action.SHORT_DESCRIPTION, "<html>Add variations that overlap genomic features<br/>for nodes annotated with Ensembl gene ids.<br/><br/>A new column ‘variation_count’ is added to the<br/>node table with the aggregate count of variations<br/>per node.</html>");
        }

        @Override
        public void actionPerformed(final ActionEvent event)
        {
            addVariations();
        }
    }

    /** Add variations action. */
    private final AddVariationsAction addVariations = new AddVariationsAction();


    /** Annotate variation consequences action. */
    private final class AnnotateVariationConsequencesAction extends IdentifiableAction
    {
        /**
         * Create a new annotate variation consequences action.
         */
        private AnnotateVariationConsequencesAction()
        {
            super("Annotate variation consequences...", annotateIconBundle);
            putValue(Action.SHORT_DESCRIPTION, "<html>Annotate variations with consequences, one for each<br/>combination of alternate allele and consequence term.<br/><br/>A new column ‘variation_consequence_count’ is added to the<br/>node table with the aggregate count of variation consequences<br/>per node.<br/><br/>New columns for each Sequence Ontology (SO) consequence term<br/>(e.g. ‘stop_gained’, ‘stop_lost’) are added to the node table<br/>with the count of matching variation consequences per node.</html>");
        }

        @Override
        public void actionPerformed(final ActionEvent event)
        {
            annotateVariationConsequences();
        }
    };

    /** Annotate variation consequences action. */
    private final AnnotateVariationConsequencesAction annotateVariationConsequences = new AnnotateVariationConsequencesAction();


    /** Predict variation consequences action. */
    private final class PredictVariationConsequencesAction extends IdentifiableAction
    {
        /**
         * Create a new predict variation consequences action.
         */
        private PredictVariationConsequencesAction()
        {
            super("Predict variation consequences...", predictIconBundle);
            putValue(Action.SHORT_DESCRIPTION, "<html>Predict consequences for variations, one for each<br/>combination of alternate allele and consequence term.<br/><br/>A new column ‘variation_consequence_count’ is added to the<br/>node table with the aggregate count of variation consequences<br/>per node.<br/><br/>New columns for each Sequence Ontology (SO) consequence term<br/>(e.g. ‘stop_gained’, ‘stop_lost’) are added to the node table<br/>with the count of matching variation consequences per node.</html>");
        }

        @Override
        public void actionPerformed(final ActionEvent event)
        {
            predictVariationConsequences();
        }
    };

    /** Predict variation consequences action. */
    private final PredictVariationConsequencesAction predictVariationConsequences = new PredictVariationConsequencesAction();


    /**
     * Create a new variation app.
     *
     * @param applicationManager application manager, must not be null
     * @param dialogTaskManager dialog task manager, must not be null
     * @param visualMappingManager visual mapping manager, must not be null
     * @param continuousMappingFactory continuous mapping factory, must not be null
     * @param discreteMappingFactory discrete mapping factory, must not be null
     * @param passthroughMappingFactory passthrough mapping factory, must not be null
     */
    VariationApp(final CyApplicationManager applicationManager,
                 final DialogTaskManager dialogTaskManager,
                 final VisualMappingManager visualMappingManager,
                 final VisualMappingFunctionFactory continuousMappingFactory,
                 final VisualMappingFunctionFactory discreteMappingFactory,
                 final VisualMappingFunctionFactory passthroughMappingFactory)
    {
        checkNotNull(applicationManager);
        checkNotNull(dialogTaskManager);
        checkNotNull(visualMappingManager);
        checkNotNull(continuousMappingFactory);
        checkNotNull(discreteMappingFactory);
        checkNotNull(passthroughMappingFactory);
        this.applicationManager = applicationManager;
        this.dialogTaskManager = dialogTaskManager;

        model = new VariationModel();
        configView = new ConfigView(model);
        featureView = new FeatureView(model);
        variationView = new VariationView(model);
        variationConsequenceView = new VariationConsequenceView(model);
        visualMappingView = new VisualMappingView(model, visualMappingManager, continuousMappingFactory, discreteMappingFactory, passthroughMappingFactory);

        tabbedPane = new JTabbedPane();
        tabbedPane.add("Config", configView);
        tabbedPane.add("Features", featureView);
        tabbedPane.add("Variations", variationView);
        tabbedPane.add("Consequences", variationConsequenceView);
        tabbedPane.add("Visual Mapping", visualMappingView);

        model.setNetwork(applicationManager.getCurrentNetwork());

        layoutComponents();
    }


    /**
     * Layout components.
     */
    private void layoutComponents()
    {
        setLayout(new BorderLayout());
        add("Center", tabbedPane);
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
        RetrieveFeaturesTaskFactory taskFactory = new RetrieveFeaturesTaskFactory(model);
        dialogTaskManager.execute(taskFactory.createTaskIterator());
        tabbedPane.setSelectedComponent(featureView);
    }

    /**
     * Add variations.
     */
    void addVariations()
    {
        AddVariationsTaskFactory taskFactory = new AddVariationsTaskFactory(model);
        dialogTaskManager.execute(taskFactory.createTaskIterator());
        tabbedPane.setSelectedComponent(variationView);
    }

    /**
     * Annotate variation consequences.
     */
    void annotateVariationConsequences()
    {
        AnnotateVariationConsequencesTaskFactory taskFactory = new AnnotateVariationConsequencesTaskFactory(model);
        dialogTaskManager.execute(taskFactory.createTaskIterator());
        tabbedPane.setSelectedComponent(variationConsequenceView);
    }

    /**
     * Predict variation consequences.
     */
    void predictVariationConsequences()
    {
        PredictVariationConsequencesTaskFactory taskFactory = new PredictVariationConsequencesTaskFactory(model);
        dialogTaskManager.execute(taskFactory.createTaskIterator());
        tabbedPane.setSelectedComponent(variationConsequenceView);
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