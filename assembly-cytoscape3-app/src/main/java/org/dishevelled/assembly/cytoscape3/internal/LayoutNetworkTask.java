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

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.TaskMonitor;

import org.cytoscape.view.layout.CyLayoutAlgorithm;
import org.cytoscape.view.layout.CyLayoutAlgorithmManager;

/**
 * Layout network task.
 *
 * @author  Michael Heuer
 */
final class LayoutNetworkTask extends AbstractTask
{
    /** Application manager. */
    private final CyApplicationManager applicationManager;

    /** Layout algorithm manager. */
    private final CyLayoutAlgorithmManager layoutAlgorithmManager;

    /** Layout algorithm name, <code>force-directed</code>. */
    static final String LAYOUT_ALGORITHM_NAME = "force-directed";

    /** Layout algorithm parameters, <code>""</code>. */
    static final String LAYOUT_ALGORITHM_PARAMETERS = "";


    /**
     * Create a new layout network task.
     *
     * @param applicationManager application manager, must not be null
     * @param layoutAlgorithmManager layout algorithm manager, must not be null
     */
    LayoutNetworkTask(final CyApplicationManager applicationManager, final CyLayoutAlgorithmManager layoutAlgorithmManager)
    {
        checkNotNull(applicationManager);
        checkNotNull(layoutAlgorithmManager);
        this.applicationManager = applicationManager;
        this.layoutAlgorithmManager = layoutAlgorithmManager;
    }


    @Override
    public void run(final TaskMonitor taskMonitor)
    {
        taskMonitor.setProgress(0.0d);
        taskMonitor.setTitle("Applying layout...");
        taskMonitor.setStatusMessage("Applying layout...");

        CyLayoutAlgorithm layoutAlgorithm = layoutAlgorithmManager.getLayout(LAYOUT_ALGORITHM_NAME);
        TaskIterator taskIterator = layoutAlgorithm.createTaskIterator(applicationManager.getCurrentNetworkView(),
                                                                       layoutAlgorithm.getDefaultLayoutContext(),
                                                                       CyLayoutAlgorithm.ALL_NODE_VIEWS,
                                                                       LAYOUT_ALGORITHM_PARAMETERS);
        insertTasksAfterCurrentTask(taskIterator);
        taskMonitor.setProgress(1.0d);
    }
}
