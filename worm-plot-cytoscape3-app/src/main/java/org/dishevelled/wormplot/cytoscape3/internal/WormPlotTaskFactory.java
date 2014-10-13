/*

    dsh-worm-plot-cytoscape3-app  Worm plot Cytoscape 3 app.
    Copyright (c) 2014 held jointly by the individual authors.

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
package org.dishevelled.wormplot.cytoscape3.internal;

import static com.google.common.base.Preconditions.checkNotNull;

import org.cytoscape.task.analyze.AnalyzeNetworkCollectionTaskFactory;

import org.cytoscape.view.layout.CyLayoutAlgorithmManager;

import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;

import org.cytoscape.work.TaskIterator;

/**
 * Worm plot task factory.
 *
 * @author  Michael Heuer
 */
final class WormPlotTaskFactory
{
    /** Analyze network collection task factory. */
    private final AnalyzeNetworkCollectionTaskFactory analyzeNetworkCollectionTaskFactory;

    /** Layout algorithm manager. */
    private final CyLayoutAlgorithmManager layoutAlgorithmManager;

    /** Visual mapping manager. */
    private final VisualMappingManager visualMappingManager;

    /** Continuous mapping factory. */
    private final VisualMappingFunctionFactory continuousMappingFactory;


    /**
     * Create a new worm plot task factory.
     *
     * @param analyzeNetworkCollectionTaskFactory analyze network collection task factory, must not be null
     * @param layoutAlgorithmManager layout algorithm manager, must not be null
     * @param visualMappingManager visual mapping manager, must not be null
     * @param continuousMappingFactory continuous mapping factory, must not be null
     */
    WormPlotTaskFactory(final AnalyzeNetworkCollectionTaskFactory analyzeNetworkCollectionTaskFactory,
                        final CyLayoutAlgorithmManager layoutAlgorithmManager,
                        final VisualMappingManager visualMappingManager,
                        final VisualMappingFunctionFactory continuousMappingFactory)
    {
        checkNotNull(analyzeNetworkCollectionTaskFactory);
        checkNotNull(layoutAlgorithmManager);
        checkNotNull(visualMappingManager);
        checkNotNull(continuousMappingFactory);
        this.analyzeNetworkCollectionTaskFactory = analyzeNetworkCollectionTaskFactory;
        this.layoutAlgorithmManager = layoutAlgorithmManager;
        this.visualMappingManager = visualMappingManager;
        this.continuousMappingFactory = continuousMappingFactory;
    }


    /**
     * Create and return a new task iterator for the specified model.
     *
     * @param model model, must not be null
     * @return a new task iterator for the specified model
     */
    public TaskIterator createTaskIterator(final WormPlotModel model)
    {
        checkNotNull(model);
        WormPlotTask wormPlotTask = new WormPlotTask(model);
        AnalyzeNetworkTask analyzeNetworkTask = new AnalyzeNetworkTask(model.getNetwork(), analyzeNetworkCollectionTaskFactory);
        LayoutNetworkTask layoutNetworkTask = new LayoutNetworkTask(model.getNetworkView(), layoutAlgorithmManager);
        VisualMappingTask visualMappingTask = new VisualMappingTask(model.getNetwork(), model.getNetworkView(), visualMappingManager, continuousMappingFactory);
        return new TaskIterator(wormPlotTask, analyzeNetworkTask, layoutNetworkTask, visualMappingTask);
    }
}
