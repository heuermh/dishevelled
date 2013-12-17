/*

    dsh-variation-cytoscape3-app  Variation Cytoscape3 app.
    Copyright (c) 2013 held jointly by the individual authors.

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

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import javax.swing.JPanel;

import javax.swing.border.EmptyBorder;

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


    /**
     * Create a new visual mapping view with the specified variation model.
     *
     * @param model variation model, must not be null
     */
    VisualMappingView(final VariationModel model)
    {
        super();
        setOpaque(false);

        checkNotNull(model);
        this.model = model;

        variationCountToNodeSize = new JCheckBox("Map variation_count to node size continuously");
        variationCountToNodeColor = new JCheckBox("Map variation_count to node color continuously");

        variationCountToNodeSize.setEnabled(false);
        variationCountToNodeColor.setEnabled(false);

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
        if (variationCountToNodeSize.isEnabled())
        {
            mapVariationCountToNodeSize();
        }
        if (variationCountToNodeColor.isEnabled())
        {
            mapVariationCountToNodeColor();
        }
    }

    // might need to migrate these to tasks, e.g. ApplyVisualStyleTaskFactory

    /**
     * Map variation_count to node size continuously.
     */
    private void mapVariationCountToNodeSize()
    {
        // empty
    }

    /**
     * Map variation_count to node color continuously.
     */
    private void mapVariationCountToNodeColor()
    {
        // empty
        /*

        // remove existing node color mapping
        visualStyle.removeVisualMappingFunction(BasicVisualLexicon.NODE_FILL_COLOR);

        // create new continuous node color mapping
        int maxVariationCount = maxCount(model.getNetwork(), "variation_count");
        ContinuousMapping<String, Paint> nodeColorMapping = (ContinuousMapping<String, Paint>) continuousMappingFactory.createVisualMappingFunction("variation_count", Integer.class, BasicVisualLexicon.NODE_FILL_COLOR);
        nodeColorMapping.addPoint(new Color(255, 255, 255), new BoundaryRangeValues(0, 0, 0));
        nodeColorMapping.addPoint(new Color(80, 0, 0), new BoundaryRangeValues(maxVariationCount, maxVariationCount, maxVariationCount));

        // install new mapping
        visualStyle.addVisualMappingFunction(nodeColorMapping);

        // apply, update, etc.

        */
    }
}
