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
final class VisualMappingTask
    extends AbstractTask
{
    /** Network. */
    private final CyNetwork network;

    /** Network view. */
    private final CyNetworkView networkView;

    /** Visual mapping manager. */
    private final VisualMappingManager visualMappingManager;

    /** Continuous mapping factory. */
    private final VisualMappingFunctionFactory continuousMappingFactory;


    /**
     * Create a new visual mapping task.
     *
     * @param network network, must not be null
     * @param networkView network view, must not be null
     * @param visualMappingManager visual mapping manager, must not be null
     * @param continuousMappingFactory continuous mapping factory, must not be null
     */
    VisualMappingTask(final CyNetwork network,
                      final CyNetworkView networkView,
                      final VisualMappingManager visualMappingManager,
                      final VisualMappingFunctionFactory continuousMappingFactory)
    {
        checkNotNull(network);
        checkNotNull(networkView);
        checkNotNull(visualMappingManager);
        checkNotNull(continuousMappingFactory);
        this.network = network;
        this.networkView = networkView;
        this.visualMappingManager = visualMappingManager;
        this.continuousMappingFactory = continuousMappingFactory;
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

        // continuous mapping bitScore --> edge paint
        double minBitScore = Double.MAX_VALUE;
        double maxBitScore = -1.0d * Double.MAX_VALUE;
        CyTable edgeTable = network.getDefaultEdgeTable();
        CyColumn bitScoreColumn = edgeTable.getColumn("bitScore");
        for (Double bitScore : bitScoreColumn.getValues(Double.class))
        {
            if (bitScore != null)
            {
                if (bitScore < minBitScore)
                {
                    minBitScore = bitScore;
                }
                if (bitScore > maxBitScore)
                {
                    maxBitScore = bitScore;
                }
            }
        }

        Color grey = new Color(186, 189, 182);
        Color darkGrey = new Color(46, 52, 54);
        BoundaryRangeValues<Paint> minEdgePaint = new BoundaryRangeValues<Paint>(grey, grey, grey);
        BoundaryRangeValues<Paint> maxEdgePaint = new BoundaryRangeValues<Paint>(darkGrey, darkGrey, darkGrey);

        ContinuousMapping<Double, Paint> edgePaintMapping = (ContinuousMapping<Double, Paint>) continuousMappingFactory.createVisualMappingFunction("bitScore", Double.class, EDGE_PAINT);
        edgePaintMapping.addPoint(minBitScore, minEdgePaint);
        edgePaintMapping.addPoint(maxBitScore, maxEdgePaint);
        visualStyle.addVisualMappingFunction(edgePaintMapping);

        taskMonitor.setProgress(0.33d);

        // continuous mapping Degree --> node fill color
        int minDegree = Integer.MAX_VALUE;
        int maxDegree = 0;
        CyTable nodeTable = network.getDefaultNodeTable();
        CyColumn degreeColumn = nodeTable.getColumn("Degree");
        for (Integer degree : degreeColumn.getValues(Integer.class))
        {
            if (degree != null)
            {
                if (degree < minDegree)
                {
                    minDegree = degree;
                }
                if (degree > maxDegree)
                {
                    maxDegree = degree;
                }
            }
        }

        Color white = new Color(218, 238, 255);
        Color blue = new Color(52, 101, 164);
        BoundaryRangeValues<Paint> minNodeFillColor = new BoundaryRangeValues<Paint>(white, white, white);
        BoundaryRangeValues<Paint> maxNodeFillColor = new BoundaryRangeValues<Paint>(blue, blue, blue);

        ContinuousMapping<Integer, Paint> nodeFillColorMapping = (ContinuousMapping<Integer, Paint>) continuousMappingFactory.createVisualMappingFunction("Degree", Integer.class, NODE_FILL_COLOR);
        nodeFillColorMapping.addPoint(minDegree, minNodeFillColor);
        nodeFillColorMapping.addPoint(maxDegree, maxNodeFillColor);
        visualStyle.addVisualMappingFunction(nodeFillColorMapping);

        taskMonitor.setProgress(0.66d);
        taskMonitor.setStatusMessage("Updating network view...");
        visualStyle.apply(networkView);
        networkView.updateView();

        taskMonitor.setProgress(1.0d);
    }
}
