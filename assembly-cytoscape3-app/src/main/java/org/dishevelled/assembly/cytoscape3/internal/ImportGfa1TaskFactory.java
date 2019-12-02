/*

    dsh-assembly-cytoscape3-app  Assembly Cytoscape3 app.
    Copyright (c) 2019 held jointly by the individual authors.

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
package org.dishevelled.assembly.cytoscape3.internal;

import static com.google.common.base.Preconditions.checkNotNull;

import org.cytoscape.application.CyApplicationManager;

import org.cytoscape.view.layout.CyLayoutAlgorithmManager;

import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;

import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Import Graphical Fragment Assembly (GFA) 1.0 task factory.
 *
 * @author  Michael Heuer
 */
final class ImportGfa1TaskFactory extends AbstractTaskFactory
{
    /** Assembly model. */
    private final AssemblyModel assemblyModel;

    /** Application manager. */
    private final CyApplicationManager applicationManager;

    /** Layout algorithm manager. */
    private final CyLayoutAlgorithmManager layoutAlgorithmManager;

    /** Visual mapping manager. */
    private final VisualMappingManager visualMappingManager;

    /** Continuous mapping factory. */
    private final VisualMappingFunctionFactory continuousMappingFactory;

    /** Discrete mapping factory. */
    private final VisualMappingFunctionFactory discreteMappingFactory;

    /** Passthrough mapping factory. */
    private final VisualMappingFunctionFactory passthroughMappingFactory;

    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * Create a new import Graphical Fragment Assembly (GFA) 1.0 task factory
     * with the specified application manager.
     *
     * @param assemblyModel assembly model, must not be null
     * @param applicationManager application manager, must not be null
     * @param layoutAlgorithmManager layout algorithm manager, must not be null
     * @param visualMappingManager visual mapping manager, must not be null
     * @param continuousMappingFactory continuous mapping factory, must not be null
     * @param discreteMappingFactory discreteMappingFactory, must not be null
     * @param passthroughMappingFactory passthrough mapping factory, must not be null
     */
    ImportGfa1TaskFactory(final AssemblyModel assemblyModel,
                          final CyApplicationManager applicationManager,
                          final CyLayoutAlgorithmManager layoutAlgorithmManager,
                          final VisualMappingManager visualMappingManager,
                          final VisualMappingFunctionFactory continuousMappingFactory,
                          final VisualMappingFunctionFactory discreteMappingFactory,
                          final VisualMappingFunctionFactory passthroughMappingFactory)
    {
        checkNotNull(assemblyModel);
        checkNotNull(applicationManager);
        checkNotNull(layoutAlgorithmManager);
        checkNotNull(visualMappingManager);
        checkNotNull(continuousMappingFactory);
        checkNotNull(discreteMappingFactory);
        checkNotNull(passthroughMappingFactory);
        this.assemblyModel = assemblyModel;
        this.applicationManager = applicationManager;
        this.layoutAlgorithmManager = layoutAlgorithmManager;
        this.visualMappingManager = visualMappingManager;
        this.continuousMappingFactory = continuousMappingFactory;
        this.discreteMappingFactory = discreteMappingFactory;
        this.passthroughMappingFactory = passthroughMappingFactory;
    }


    @Override
    public boolean isReady()
    {
        return applicationManager.getCurrentNetwork() != null && applicationManager.getCurrentNetworkView() != null;
    }

    @Override
    public TaskIterator createTaskIterator()
    {
        ImportGfa1Task importTask = new ImportGfa1Task(assemblyModel, applicationManager);
        LayoutNetworkTask layoutNetworkTask = new LayoutNetworkTask(applicationManager, layoutAlgorithmManager);
        VisualMappingTask visualMappingTask = new VisualMappingTask(applicationManager, visualMappingManager, continuousMappingFactory, discreteMappingFactory, passthroughMappingFactory);
        AssemblyAppTask assemblyAppTask = new AssemblyAppTask(assemblyModel);
        return new TaskIterator(importTask, layoutNetworkTask, visualMappingTask, assemblyAppTask);
    }
}
