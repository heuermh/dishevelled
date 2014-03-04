/*

    dsh-variation-cytoscape3-app  Variation Cytoscape3 app.
    Copyright (c) 2013-2014 held jointly by the individual authors.

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

import static org.dishevelled.variation.cytoscape3.internal.VariationUtils.maxCount;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Paint;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import javax.swing.JPanel;

import javax.swing.border.EmptyBorder;

import org.cytoscape.view.presentation.property.BasicVisualLexicon;

import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;

import org.cytoscape.view.vizmap.mappings.BoundaryRangeValues;
import org.cytoscape.view.vizmap.mappings.ContinuousMapping;

import org.dishevelled.layout.LabelFieldPanel;

/**
 * Visual mapping view.
 *
 * @author  Michael Heuer
 */
final class VisualMappingView
    extends JPanel
{
    /** Variation model. */
    private final VariationModel model;

    /** Visual mapping manager. */
    private final VisualMappingManager visualMappingManager;

    /** Continuous mapping factory. */
    private final VisualMappingFunctionFactory continuousMappingFactory;

    /** Discrete mapping factory. */
    private final VisualMappingFunctionFactory discreteMappingFactory;

    /** Passthrough mapping factory. */
    private final VisualMappingFunctionFactory passthroughMappingFactory;

    /** Map variation_count to node size continuously. */
    private final JCheckBox variationCountToNodeSize;

    /** Map variation_count to node color continuously. */
    private final JCheckBox variationCountToNodeColor;

    /** Apply action. */
    private final AbstractAction apply = new AbstractAction("Apply")
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                apply();
            }
        };

    /** Check box action listener. */
    private final ActionListener checkBoxActionListener = new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                apply.setEnabled(true);
            }
        };


    /**
     * Create a new visual mapping view with the specified variation model.
     *
     * @param model variation model, must not be null
     * @param visualMappingManager visual mapping manager, must not be null
     * @param continuousMappingFactory continuous mapping factory, must not be null
     * @param discreteMappingFactory discrete mapping factory, must not be null
     * @param passthroughMappingFactory passthrough mapping factory, must not be null
     */
    VisualMappingView(final VariationModel model,
                      final VisualMappingManager visualMappingManager,
                      final VisualMappingFunctionFactory continuousMappingFactory,
                      final VisualMappingFunctionFactory discreteMappingFactory,
                      final VisualMappingFunctionFactory passthroughMappingFactory)
    {
        super();
        setOpaque(false);

        checkNotNull(model);
        checkNotNull(visualMappingManager);
        checkNotNull(continuousMappingFactory);
        checkNotNull(discreteMappingFactory);
        checkNotNull(passthroughMappingFactory);
        this.model = model;
        this.visualMappingManager = visualMappingManager;
        this.continuousMappingFactory = continuousMappingFactory;
        this.discreteMappingFactory = discreteMappingFactory;
        this.passthroughMappingFactory = passthroughMappingFactory;

        variationCountToNodeSize = new JCheckBox("Map variation_count to node size continuously");
        variationCountToNodeColor = new JCheckBox("Map variation_count to node color continuously");

        variationCountToNodeSize.setSelected(false);
        variationCountToNodeColor.setSelected(false);

        variationCountToNodeSize.addActionListener(checkBoxActionListener);
        variationCountToNodeColor.addActionListener(checkBoxActionListener);

        apply.setEnabled(false);

        layoutComponents();
    }


    /**
     * Layout components.
     */
    private void layoutComponents()
    {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(0, 12, 12, 48));
        add("Center", createMainPanel());
        add("South", createButtonPanel());
    }

    /**
     * Create and return a new main panel.
     *
     * @return a new main panel
     */
    private JPanel createMainPanel()
    {
        LabelFieldPanel panel = new LabelFieldPanel();
        panel.setOpaque(false);

        // panel.addLabel("Simple mappings:");
        panel.addField(variationCountToNodeSize);
        panel.addField(variationCountToNodeColor);
        // panel.addLabel("Compound mappings:");
        // ...
        panel.addFinalSpacing();

        return panel;
    }

    /**
     * Create and return a new button panel.
     *
     * @return a new button panel
     */
    private JPanel createButtonPanel()
    {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(Box.createHorizontalGlue());
        panel.add(Box.createHorizontalGlue());
        panel.add(Box.createHorizontalGlue());
        panel.add(new JButton(apply));
        return panel;
    }

    /**
     * Apply.
     */
    private void apply()
    {
        if (variationCountToNodeSize.isSelected())
        {
            mapVariationCountToNodeSize();
        }
        else
        {
            removeNodeSizeMapping();
        }

        if (variationCountToNodeColor.isSelected())
        {
            mapVariationCountToNodeColor();
        }
        else
        {
            removeNodeColorMapping();
        }

        apply.setEnabled(false);
    }

    /**
     * Map variation_count to node size continuously.
     */
    private void mapVariationCountToNodeSize()
    {
        VisualStyle visualStyle = visualMappingManager.getCurrentVisualStyle();

        // remove existing node size mapping
        visualStyle.removeVisualMappingFunction(BasicVisualLexicon.NODE_SIZE);

        // create new continuous node size mapping
        ContinuousMapping<Integer, Double> nodeSizeMapping = (ContinuousMapping<Integer, Double>) continuousMappingFactory.createVisualMappingFunction("variation_count", Integer.class, BasicVisualLexicon.NODE_SIZE);

        Double smallest = Double.valueOf(30.0d);
        Double largest = Double.valueOf(90.0d);
        int maxVariationCount = maxCount(model.getNetwork(), "variation_count");
        nodeSizeMapping.addPoint(0, new BoundaryRangeValues(smallest, smallest, smallest));
        nodeSizeMapping.addPoint(maxVariationCount, new BoundaryRangeValues(largest, largest, largest));

        // install new mapping
        visualStyle.addVisualMappingFunction(nodeSizeMapping);
    }

    /**
     * Remove node size mapping.
     */
    private void removeNodeSizeMapping()
    {
        VisualStyle visualStyle = visualMappingManager.getCurrentVisualStyle();
        visualStyle.removeVisualMappingFunction(BasicVisualLexicon.NODE_SIZE);
    }

    /**
     * Map variation_count to node color continuously.
     */
    private void mapVariationCountToNodeColor()
    {
        VisualStyle visualStyle = visualMappingManager.getCurrentVisualStyle();

        // remove existing node color mapping
        visualStyle.removeVisualMappingFunction(BasicVisualLexicon.NODE_FILL_COLOR);

        // create new continuous node color mapping
        ContinuousMapping<Integer, Paint> nodeColorMapping = (ContinuousMapping<Integer, Paint>) continuousMappingFactory.createVisualMappingFunction("variation_count", Integer.class, BasicVisualLexicon.NODE_FILL_COLOR);

        // blues-4
        Paint p0 = new Color(239, 243, 255);
        Paint p1 = new Color(189, 215, 231);
        Paint p2 = new Color(107, 174, 214);
        Paint p3 = new Color(33, 113, 181);
        int maxVariationCount = maxCount(model.getNetwork(), "variation_count");
        nodeColorMapping.addPoint(0, new BoundaryRangeValues(p0, p0, p0));
        nodeColorMapping.addPoint(maxVariationCount / 3, new BoundaryRangeValues(p1, p1, p1));
        nodeColorMapping.addPoint(2 * maxVariationCount / 3, new BoundaryRangeValues(p2, p2, p2));
        nodeColorMapping.addPoint(maxVariationCount, new BoundaryRangeValues(p3, p3, p3));

        // install new mapping
        visualStyle.addVisualMappingFunction(nodeColorMapping);
    }

    /**
     * Remove node color mapping.
     */
    private void removeNodeColorMapping()
    {
        VisualStyle visualStyle = visualMappingManager.getCurrentVisualStyle();
        visualStyle.removeVisualMappingFunction(BasicVisualLexicon.NODE_FILL_COLOR);
    }
}
