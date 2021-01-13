/*

    dsh-assembly-cytoscape3-app  Assembly Cytoscape3 app.
    Copyright (c) 2019-2021 held jointly by the individual authors.

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

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNetworkFactory;
import org.cytoscape.model.CyNetworkManager;

import org.cytoscape.session.CyNetworkNaming;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.CyNetworkViewFactory;
import org.cytoscape.view.model.CyNetworkViewManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Create network task.
 *
 * @author  Michael Heuer
 */
public final class CreateNetworkTask extends AbstractTask
{
    /** Assembly model. */
    private final AssemblyModel assemblyModel;

    /** Network factory. */
    private final CyNetworkFactory networkFactory;

    /** Network naming. */
    private final CyNetworkNaming networkNaming;

    /** Network manager. */
    private final CyNetworkManager networkManager;

    /** Network view factory. */
    private final CyNetworkViewFactory networkViewFactory;

    /** Network view manager. */
    private final CyNetworkViewManager networkViewManager;

    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * Create a new create network task.
     *
     * @param assemblyModel assembly model, must not be null
     * @param networkFactory network factory, must not be null
     * @param networkNaming network naming, must not be null
     * @param networkManager network manager, must not be null
     * @param networkViewFactory network view factory, must not be null
     * @param networkViewManager network view manager, must not be null
     */
    CreateNetworkTask(final AssemblyModel assemblyModel,
                      final CyNetworkFactory networkFactory,
                      final CyNetworkNaming networkNaming,
                      final CyNetworkManager networkManager,
                      final CyNetworkViewFactory networkViewFactory,
                      final CyNetworkViewManager networkViewManager)
    {
        checkNotNull(assemblyModel);
        checkNotNull(networkFactory);
        checkNotNull(networkNaming);
        checkNotNull(networkManager);
        checkNotNull(networkViewFactory);
        checkNotNull(networkViewManager);
        this.assemblyModel = assemblyModel;
        this.networkFactory = networkFactory;
        this.networkNaming = networkNaming;
        this.networkManager = networkManager;
        this.networkViewFactory = networkViewFactory;
        this.networkViewManager = networkViewManager;
    }


    @Override
    public void run(final TaskMonitor taskMonitor) throws Exception
    {
        CyNetwork network = networkFactory.createNetwork();
        String networkName = networkNaming.getSuggestedNetworkTitle("Assembly");
        network.getDefaultNetworkTable().getRow(network.getSUID()).set(CyNetwork.NAME, networkName);
        networkManager.addNetwork(network);
        CyNetworkView networkView = networkViewFactory.createNetworkView(network);
        networkViewManager.addNetworkView(networkView);
    }
}
