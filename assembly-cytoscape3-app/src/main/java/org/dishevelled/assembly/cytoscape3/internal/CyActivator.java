/*

    dsh-assembly-cytoscape3-app  Assembly Cytoscape3 app.
    Copyright (c) 2019-2020 held jointly by the individual authors.

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

import static org.cytoscape.work.ServiceProperties.COMMAND;
import static org.cytoscape.work.ServiceProperties.COMMAND_DESCRIPTION;
import static org.cytoscape.work.ServiceProperties.COMMAND_NAMESPACE;
import static org.cytoscape.work.ServiceProperties.IN_CONTEXT_MENU;
import static org.cytoscape.work.ServiceProperties.IN_MENU_BAR;
import static org.cytoscape.work.ServiceProperties.INSERT_SEPARATOR_BEFORE;
import static org.cytoscape.work.ServiceProperties.MENU_GRAVITY;
import static org.cytoscape.work.ServiceProperties.PREFERRED_MENU;
import static org.cytoscape.work.ServiceProperties.TITLE;

import java.util.Properties;

import org.cytoscape.application.CyApplicationManager;

import org.cytoscape.model.CyNetworkFactory;
import org.cytoscape.model.CyNetworkManager;

import org.cytoscape.service.util.AbstractCyActivator;

import org.cytoscape.session.CyNetworkNaming;

import org.cytoscape.view.layout.CyLayoutAlgorithmManager;

import org.cytoscape.view.model.CyNetworkViewFactory;
import org.cytoscape.view.model.CyNetworkViewManager;

import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;

import org.cytoscape.work.TaskFactory;

import org.osgi.framework.BundleContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Activator.
 *
 * @author  Michael Heuer
 */
public final class CyActivator extends AbstractCyActivator
{
    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void start(final BundleContext bundleContext)
    {
        if (bundleContext == null)
        {
            throw new NullPointerException("bundleContext must not be null");
        }
        AssemblyModel assemblyModel = new AssemblyModel();
        CyApplicationManager applicationManager = getService(bundleContext, CyApplicationManager.class);
        CyLayoutAlgorithmManager layoutAlgorithmManager = getService(bundleContext, CyLayoutAlgorithmManager.class);

        CyNetworkFactory networkFactory = getService(bundleContext, CyNetworkFactory.class);
        CyNetworkNaming networkNaming = getService(bundleContext, CyNetworkNaming.class);
        CyNetworkManager networkManager = getService(bundleContext, CyNetworkManager.class);
        CyNetworkViewFactory networkViewFactory = getService(bundleContext, CyNetworkViewFactory.class);
        CyNetworkViewManager networkViewManager = getService(bundleContext, CyNetworkViewManager.class);

        VisualMappingManager visualMappingManager = getService(bundleContext, VisualMappingManager.class);
        VisualMappingFunctionFactory continuousMappingFactory = getService(bundleContext, VisualMappingFunctionFactory.class, "(mapping.type=continuous)");
        VisualMappingFunctionFactory discreteMappingFactory = getService(bundleContext, VisualMappingFunctionFactory.class, "(mapping.type=discrete)");
        VisualMappingFunctionFactory passthroughMappingFactory = getService(bundleContext, VisualMappingFunctionFactory.class, "(mapping.type=passthrough)");

        Properties importGfa1Properties = new Properties();
        importGfa1Properties.setProperty(COMMAND_NAMESPACE, "assembly");
        importGfa1Properties.setProperty(COMMAND, "import_gfa1");
        importGfa1Properties.setProperty(COMMAND_DESCRIPTION,  "Import a network in Graphical Fragment Assembly (GFA) 1.0 format.  Compressed files (.bgz, .bzip2, .gz) are supported.");
        importGfa1Properties.setProperty(PREFERRED_MENU, "File.Import");
        importGfa1Properties.setProperty(TITLE, "Network from Graphical Fragment Assembly (GFA) 1.0...");
        importGfa1Properties.setProperty(IN_MENU_BAR, "true");
        importGfa1Properties.setProperty(IN_CONTEXT_MENU, "false");
        importGfa1Properties.setProperty(INSERT_SEPARATOR_BEFORE, "false");
        importGfa1Properties.setProperty(MENU_GRAVITY, "4.0");

        TaskFactory importGfa1TaskFactory = new ImportGfa1TaskFactory(assemblyModel,
                                                                      applicationManager,
                                                                      layoutAlgorithmManager,
                                                                      networkFactory,
                                                                      networkNaming,
                                                                      networkManager,
                                                                      networkViewFactory,
                                                                      networkViewManager,
                                                                      visualMappingManager,
                                                                      continuousMappingFactory,
                                                                      discreteMappingFactory,
                                                                      passthroughMappingFactory);
        registerAllServices(bundleContext, importGfa1TaskFactory, importGfa1Properties);

        Properties importGfa2Properties = new Properties();
        importGfa2Properties.setProperty(COMMAND_NAMESPACE, "assembly");
        importGfa2Properties.setProperty(COMMAND, "import_gfa2");
        importGfa2Properties.setProperty(COMMAND_DESCRIPTION,  "Import a network in Graphical Fragment Assembly (GFA) 2.0 format.  Compressed files (.bgz, .bzip2, .gz) are supported.");
        importGfa2Properties.setProperty(PREFERRED_MENU, "File.Import");
        importGfa2Properties.setProperty(TITLE,  "Network from Graphical Fragment Assembly (GFA) 2.0...");
        importGfa2Properties.setProperty(IN_MENU_BAR, "true");
        importGfa2Properties.setProperty(IN_CONTEXT_MENU, "false");
        importGfa1Properties.setProperty(INSERT_SEPARATOR_BEFORE, "false");
        importGfa1Properties.setProperty(MENU_GRAVITY, "4.0");

        TaskFactory importGfa2TaskFactory = new ImportGfa2TaskFactory(applicationManager);
        //registerAllServices(bundleContext, importGfa2TaskFactory, importGfa2Properties);
    }
}
