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

import static org.cytoscape.view.presentation.property.ArrowShapeVisualProperty.ARROW;
import static org.cytoscape.view.presentation.property.ArrowShapeVisualProperty.T;

import static org.cytoscape.view.presentation.property.BasicVisualLexicon.EDGE_LABEL;
import static org.cytoscape.view.presentation.property.BasicVisualLexicon.EDGE_LINE_TYPE;
import static org.cytoscape.view.presentation.property.BasicVisualLexicon.EDGE_TARGET_ARROW_SHAPE;
import static org.cytoscape.view.presentation.property.BasicVisualLexicon.EDGE_TRANSPARENCY;
import static org.cytoscape.view.presentation.property.BasicVisualLexicon.EDGE_TOOLTIP;
import static org.cytoscape.view.presentation.property.BasicVisualLexicon.EDGE_WIDTH;
import static org.cytoscape.view.presentation.property.BasicVisualLexicon.NODE_BORDER_PAINT;
import static org.cytoscape.view.presentation.property.BasicVisualLexicon.NODE_BORDER_TRANSPARENCY;
import static org.cytoscape.view.presentation.property.BasicVisualLexicon.NODE_BORDER_WIDTH;
import static org.cytoscape.view.presentation.property.BasicVisualLexicon.NODE_FILL_COLOR;
import static org.cytoscape.view.presentation.property.BasicVisualLexicon.NODE_LABEL;
import static org.cytoscape.view.presentation.property.BasicVisualLexicon.NODE_LABEL_COLOR;
import static org.cytoscape.view.presentation.property.BasicVisualLexicon.NODE_TOOLTIP;
import static org.cytoscape.view.presentation.property.BasicVisualLexicon.NODE_WIDTH;

import static org.cytoscape.view.presentation.property.LineTypeVisualProperty.SOLID;
import static org.cytoscape.view.presentation.property.LineTypeVisualProperty.EQUAL_DASH;

import java.awt.Color;
import java.awt.Paint;

import java.util.Optional;

import com.google.common.collect.Range;

import org.cytoscape.application.CyApplicationManager;

import org.cytoscape.model.CyColumn;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyTable;

import org.cytoscape.view.model.CyNetworkView;

import org.cytoscape.view.presentation.property.values.ArrowShape;
import org.cytoscape.view.presentation.property.values.LineType;

import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;

import org.cytoscape.view.vizmap.mappings.BoundaryRangeValues;
import org.cytoscape.view.vizmap.mappings.ContinuousMapping;
import org.cytoscape.view.vizmap.mappings.DiscreteMapping;
import org.cytoscape.view.vizmap.mappings.PassthroughMapping;

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
        visualStyle.setDefaultValue(NODE_BORDER_PAINT, new Color(85, 87, 83));
        visualStyle.setDefaultValue(NODE_BORDER_TRANSPARENCY, Integer.valueOf(120));
        visualStyle.setDefaultValue(NODE_BORDER_WIDTH, Double.valueOf(0.9d));
        visualStyle.setDefaultValue(NODE_LABEL_COLOR, new Color(46, 52, 54));

        CyNetwork network = applicationManager.getCurrentNetwork();
        CyTable nodeTable = network.getDefaultNodeTable();

        // passthrough mapping, displaySequence --> node label, or fall back to displayName --> node label
        if (nodeTable.getColumn("displaySequence") != null)
        {
            PassthroughMapping displaySequenceNodeLabelMapping = (PassthroughMapping) passthroughMappingFactory.createVisualMappingFunction("displaySequence", String.class, NODE_LABEL);
            visualStyle.addVisualMappingFunction(displaySequenceNodeLabelMapping);
        }
        else if (nodeTable.getColumn("displayName") != null)
        {
            PassthroughMapping displayNameNodeLabelMapping = (PassthroughMapping) passthroughMappingFactory.createVisualMappingFunction("displayName", String.class, NODE_LABEL);
            visualStyle.addVisualMappingFunction(displayNameNodeLabelMapping);
        }

        // passthrough mapping, displayLabel --> node tooltip
        PassthroughMapping nodeTooltipMapping = (PassthroughMapping) passthroughMappingFactory.createVisualMappingFunction("displayLabel", String.class, NODE_TOOLTIP);
        visualStyle.addVisualMappingFunction(nodeTooltipMapping);

        // continuous mapping, length or sequenceLength --> node fill color
        Optional<Range<Integer>> displayLengths = rangeOpt(nodeTable.getColumn("displayLength"));
        if (displayLengths.isPresent())
        {
            Range<Integer> r = displayLengths.get();

            Color white = new Color(218, 238, 255);
            Color blue = new Color(52, 101, 164);
            BoundaryRangeValues<Paint> minimumNodeFillColor = new BoundaryRangeValues<Paint>(white, white, white);
            BoundaryRangeValues<Paint> maximumNodeFillColor = new BoundaryRangeValues<Paint>(blue, blue, blue);

            ContinuousMapping<Integer, Paint> nodeFillColorMapping = (ContinuousMapping<Integer, Paint>) continuousMappingFactory.createVisualMappingFunction("displayLength", Integer.class, NODE_FILL_COLOR);
            nodeFillColorMapping.addPoint(r.lowerEndpoint(), minimumNodeFillColor);
            nodeFillColorMapping.addPoint(r.upperEndpoint(), maximumNodeFillColor);
            visualStyle.addVisualMappingFunction(nodeFillColorMapping);
        }

        // continuous mapping, displaySequenceLength --> node width
        Optional<Range<Integer>> displaySequenceLengths = rangeOpt(nodeTable.getColumn("displaySequenceLength"));
        if (displaySequenceLengths.isPresent())
        {
            Range<Integer> r = displaySequenceLengths.get();

            BoundaryRangeValues<Double> minimumNodeWidth = new BoundaryRangeValues<Double>(20.0d, 20.0d, 20.0d);
            BoundaryRangeValues<Double> maximumNodeWidth = new BoundaryRangeValues<Double>(320.0d, 320.0d, 320.0d);

            ContinuousMapping<Integer, Double> nodeWidthMapping = (ContinuousMapping<Integer, Double>) continuousMappingFactory.createVisualMappingFunction("displaySequenceLength", Integer.class, NODE_WIDTH);
            nodeWidthMapping.addPoint(r.lowerEndpoint(), minimumNodeWidth);
            nodeWidthMapping.addPoint(r.upperEndpoint(), maximumNodeWidth);
            visualStyle.addVisualMappingFunction(nodeWidthMapping);
        }

        // discrete mapping edge type --> edge stroke
        DiscreteMapping<String, LineType> edgeLineTypeMapping = (DiscreteMapping<String, LineType>) discreteMappingFactory.createVisualMappingFunction("type", String.class, EDGE_LINE_TYPE);
        edgeLineTypeMapping.putMapValue("edge", SOLID);
        edgeLineTypeMapping.putMapValue("gap", EQUAL_DASH);

        // todo: display columns for edges, to capture gfa2 gap attributes

        // passthrough mapping, id --> edge label
        PassthroughMapping edgeLabelMapping = (PassthroughMapping) passthroughMappingFactory.createVisualMappingFunction("id", String.class, EDGE_LABEL);
        visualStyle.addVisualMappingFunction(edgeLabelMapping);

        // overlap --> edge tooltip
        PassthroughMapping edgeTooltipMapping = (PassthroughMapping) passthroughMappingFactory.createVisualMappingFunction("overlap", String.class, EDGE_TOOLTIP);
        visualStyle.addVisualMappingFunction(edgeTooltipMapping);

        // discrete mapping, target orientation --> edge target arrow shape
        DiscreteMapping<String, ArrowShape> edgeTargetArrowMapping = (DiscreteMapping<String, ArrowShape>) discreteMappingFactory.createVisualMappingFunction("targetOrientation", String.class, EDGE_TARGET_ARROW_SHAPE);
        edgeTargetArrowMapping.putMapValue("+", ARROW);
        edgeTargetArrowMapping.putMapValue("-", T);
        visualStyle.addVisualMappingFunction(edgeTargetArrowMapping);


        taskMonitor.setProgress(0.66d);
        taskMonitor.setStatusMessage("Updating network view...");

        CyNetworkView networkView = applicationManager.getCurrentNetworkView();
        visualStyle.apply(networkView);
        networkView.updateView();

        taskMonitor.setProgress(1.0d);
    }


    /**
     * Return an optional range for the integer values in the specified column, if any.
     *
     * @param column column, if any
     * @return an optional range for the integer values in the specified column, if any
     */
    static Optional<Range<Integer>> rangeOpt(final CyColumn column)
    {
        if (column == null)
        {
            return Optional.empty();
        }
        int minimum = Integer.MAX_VALUE;
        int maximum = 0;
        boolean found = false;
        for (Integer value : column.getValues(Integer.class))
        {
            if (value != null)
            {
                found = true;
                if (value < minimum)
                {
                    minimum = value;
                }
                if (value > maximum)
                {
                    maximum = value;
                }
            }
        }
        return found ? Optional.of(Range.closed(minimum, maximum)) : Optional.empty();
    }
}
