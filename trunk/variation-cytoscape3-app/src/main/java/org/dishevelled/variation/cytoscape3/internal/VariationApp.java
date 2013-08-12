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

import javax.swing.AbstractAction;

import org.cytoscape.work.swing.DialogTaskManager;

/**
 * Variation app.
 */
final class VariationApp
{
    private final VariationModel model;
    private final ConfigView configView;
    private final FeatureView featureView;
    private final VariationView variationView;
    private final VariationConsequenceView variationConsequenceView;
    private final DialogTaskManager dialogTaskManager;

    private final AbstractAction retrieveFeatures = new AbstractAction()
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                retrieveFeatures();
            }
        };

    private final AbstractAction addVariations = new AbstractAction()
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                addVariations();
            }
        };

    private final AbstractAction annotateVariationConsequences = new AbstractAction()
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                annotateVariationConsequences();
            }
        };

    private final AbstractAction predictVariationConsequences = new AbstractAction()
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                predictVariationConsequences();
            }
        };

    VariationApp(final DialogTaskManager dialogTaskManager)
    {
        checkNotNull(dialogTaskManager);

        this.dialogTaskManager = dialogTaskManager;

        model = new VariationModel();
        configView = new ConfigView(model);
        featureView = new FeatureView(model);
        variationView = new VariationView(model);
        variationConsequenceView = new VariationConsequenceView(model);
    }

    VariationModel model()
    {
        return model;
    }

    ConfigView configView()
    {
        return configView;
    }

    FeatureView featureView()
    {
        return featureView;
    }

    VariationView variationView()
    {
        return variationView;
    }

    VariationConsequenceView variationConsequenceView()
    {
        return variationConsequenceView;
    }

    void retrieveFeatures()
    {
        //
        // Task factories should be singletons and should be provided by OSGi
        // Task factories should instantiate all the task's dependencies before calling createTaskIterator
        // Tunable parameters on Task seem better suited for primitives
        //
        RetrieveFeaturesTaskFactory taskFactory = new RetrieveFeaturesTaskFactory(model);
        dialogTaskManager.execute(taskFactory.createTaskIterator());
    }

    void addVariations()
    {
        AddVariationsTaskFactory taskFactory = new AddVariationsTaskFactory(model);
        dialogTaskManager.execute(taskFactory.createTaskIterator());
    }

    void annotateVariationConsequences()
    {
        AnnotateVariationConsequencesTaskFactory taskFactory = new AnnotateVariationConsequencesTaskFactory(model);
        dialogTaskManager.execute(taskFactory.createTaskIterator());
    }

    void predictVariationConsequences()
    {
        PredictVariationConsequencesTaskFactory taskFactory = new PredictVariationConsequencesTaskFactory(model);
        dialogTaskManager.execute(taskFactory.createTaskIterator());
    }
}