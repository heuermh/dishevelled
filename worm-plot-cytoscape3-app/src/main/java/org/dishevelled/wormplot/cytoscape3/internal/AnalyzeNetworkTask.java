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

import java.util.Collection;

import com.google.common.collect.ImmutableList;

import org.cytoscape.model.CyNetwork;

import org.cytoscape.task.analyze.AnalyzeNetworkCollectionTaskFactory;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.TaskMonitor;

/**
 * Analyze network task.
 *
 * @author  Michael Heuer
 */
final class AnalyzeNetworkTask
    extends AbstractTask
{
    /** Network. */
    private final CyNetwork network;

    /** Analyze network collection task factory. */
    private final AnalyzeNetworkCollectionTaskFactory taskFactory;


    /**
     * Create a new analyze network task.
     *
     * @param network network, must not be null
     * @param taskFactory analyze network collection task factory, must not be null
     */
    AnalyzeNetworkTask(final CyNetwork network, final AnalyzeNetworkCollectionTaskFactory taskFactory)
    {
        checkNotNull(network);
        checkNotNull(taskFactory);
        this.network = network;
        this.taskFactory = taskFactory;
    }


    @Override
    public void run(final TaskMonitor taskMonitor)
    {
        taskMonitor.setProgress(0.0d);
        taskMonitor.setStatusMessage("Analyzing network...");

        // todo:  will this raise the directed/undirected dialog?  need to know which of degree/outDegree to use
        Collection<CyNetwork> networks = ImmutableList.of(network);
        TaskIterator taskIterator = taskFactory.createTaskIterator(networks);

        insertTasksAfterCurrentTask(taskIterator);
        taskMonitor.setProgress(1.0d);
    }
}