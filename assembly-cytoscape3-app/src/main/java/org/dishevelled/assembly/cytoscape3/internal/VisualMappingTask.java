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

import static org.cytoscape.view.presentation.property.ArrowShapeVisualProperty.ARROW;
import static org.cytoscape.view.presentation.property.BasicVisualLexicon.EDGE_PAINT;
import static org.cytoscape.view.presentation.property.BasicVisualLexicon.EDGE_TARGET_ARROW_SHAPE;
import static org.cytoscape.view.presentation.property.BasicVisualLexicon.EDGE_TRANSPARENCY;
import static org.cytoscape.view.presentation.property.BasicVisualLexicon.EDGE_WIDTH;
import static org.cytoscape.view.presentation.property.BasicVisualLexicon.NODE_BORDER_PAINT;
import static org.cytoscape.view.presentation.property.BasicVisualLexicon.NODE_BORDER_TRANSPARENCY;
import static org.cytoscape.view.presentation.property.BasicVisualLexicon.NODE_BORDER_WIDTH;
import static org.cytoscape.view.presentation.property.BasicVisualLexicon.NODE_FILL_COLOR;
import static org.cytoscape.view.presentation.property.BasicVisualLexicon.NODE_LABEL_COLOR;

import java.awt.Color;
import java.awt.Paint;

import org.cytoscape.application.CyApplicationManager;

import org.cytoscape.model.CyColumn;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyTable;

import org.cytoscape.view.model.CyNetworkView;

import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;

import org.cytoscape.view.vizmap.mappings.BoundaryRangeValues;
import org.cytoscape.view.vizmap.mappings.ContinuousMapping;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

/**
 * Visual mapping task.
 *
 * @author  Michael Heuer
 */
final class VisualMappingTask extends AbstractTask
{
    /** Application manager. */
    private final CyApplicationManager applicationManager;

    /** Visual mapping manager. */
    private final VisualMappingManager visualMappingManager;

    /** Continuous mapping factory. */
    private final VisualMappingFunctionFactory continuousMappingFactory;

    /** Discrete mapping factory. */
    private final VisualMappingFunctionFactory discreteMappingFactory;

    /** Passthrough mapping factory. */
    private final VisualMappingFunctionFactory passthroughMappingFactory;


    /**
     * Create a new visual mapping task.
     *
     * @param applicationManager application manager, must not be null
     * @param visualMappingManager visual mapping manager, must not be null
     * @param continuousMappingFactory continuous mapping factory, must not be null
     * @param discreteMappingFactory discrete mapping factory, must not be null
     * @param passthroughMappingFactory passthrough mapping factory, must not be null
     */
    VisualMappingTask(final CyApplicationManager applicationManager,
                      final VisualMappingManager visualMappingManager,
                      final VisualMappingFunctionFactory continuousMappingFactory,
                      final VisualMappingFunctionFactory discreteMappingFactory,
                      final VisualMappingFunctionFactory passthroughMappingFactory)
    {
        checkNotNull(applicationManager);
        checkNotNull(visualMappingManager);
        checkNotNull(continuousMappingFactory);
        checkNotNull(discreteMappingFactory);
        checkNotNull(passthroughMappingFactory);
        this.applicationManager = applicationManager;
        this.visualMappingManager = visualMappingManager;
        this.continuousMappingFactory = continuousMappingFactory;
        this.discreteMappingFactory = discreteMappingFactory;
        this.passthroughMappingFactory = passthroughMappingFactory;
    }


    @Override
    public void run(final TaskMonitor taskMonitor)
    {
        taskMonitor.setProgress(0.0d);
        taskMonitor.setTitle("Applying visual mapping...");
        taskMonitor.setStatusMessage("Applying visual mapping...");

        VisualStyle visualStyle = visualMappingManager.getCurrentVisualStyle();

        // overwrite some defaults
        visualStyle.setDefaultValue(EDGE_TRANSPARENCY, Integer.valueOf(80));
        visualStyle.setDefaultValue(EDGE_WIDTH, Double.valueOf(0.9d));
        visualStyle.setDefaultValue(EDGE_TARGET_ARROW_SHAPE, ARROW);
        visualStyle.setDefaultValue(NODE_BORDER_PAINT, new Color(85, 87, 83));
        visualStyle.setDefaultValue(NODE_BORDER_TRANSPARENCY, Integer.valueOf(120));
        visualStyle.setDefaultValue(NODE_BORDER_WIDTH, Double.valueOf(0.9d));
        visualStyle.setDefaultValue(NODE_LABEL_COLOR, new Color(46, 52, 54));

        CyNetwork network = applicationManager.getCurrentNetwork();

        // passthrough mapping, name --> node label
        // passthrough mapping, sequence --> node tooltip
        // continuous mapping, length --> node fill color
        // continuous mapping, length --> node width

        // passthrough mapping, id --> edge label
        // overlap --> edge tooltip
        // ask about edge label and edge width mapping, any of the counts or mapping quality?
        // discrete mapping, source orientation --> edge source arrow shape
        // discrete mapping, target orientation --> edge target arrow shape

        taskMonitor.setProgress(0.66d);
        taskMonitor.setStatusMessage("Updating network view...");

        CyNetworkView networkView = applicationManager.getCurrentNetworkView();
        visualStyle.apply(networkView);
        networkView.updateView();

        taskMonitor.setProgress(1.0d);
    }
}
