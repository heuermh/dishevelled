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

import java.awt.Component;

import java.awt.event.ActionEvent;

import javax.swing.border.EmptyBorder;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.gui.TableFormat;

import org.cytoscape.model.CyNode;

import org.dishevelled.eventlist.view.CountLabel;
import org.dishevelled.eventlist.view.ElementsTable;

import org.dishevelled.iconbundle.tango.TangoProject;

import org.dishevelled.identify.IdentifiableAction;

import org.dishevelled.layout.LabelFieldPanel;

import org.dishevelled.variation.Feature;

/**
 * Feature view.
 *
 * @author  Michael Heuer
 */
final class FeatureView
    extends LabelFieldPanel
{
    /** Model. */
    private final VariationModel model;

    /** Feature table. */
    private final FeatureTable featureTable;

    /** Refresh action. */
    private final IdentifiableAction refresh = new IdentifiableAction("Refresh...", TangoProject.VIEW_REFRESH)
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                refresh();
            }
        };

    // todo:  need to add node column
    /** Table property names. */
    private static final String[] PROPERTY_NAMES = { "species", "reference", "identifier", "region", "start", "end", "strand" };

    /** Table column labels. */
    private static final String[] COLUMN_LABELS = { "Species", "Reference", "Identifier", "Region", "Start", "End", "Strand" };

    /** Table format. */
    private static final TableFormat<Feature> TABLE_FORMAT = GlazedLists.tableFormat(Feature.class, PROPERTY_NAMES, COLUMN_LABELS);


    /**
     * Create a new feature view with the specified model.
     *
     * @param model model, must not be null
     */
    FeatureView(final VariationModel model)
    {
        super();
        checkNotNull(model);
        this.model = model;
        featureTable = new FeatureTable(this.model.features());

        setOpaque(false);
        layoutComponents();
    }


    /**
     * Layout components.
     */
    private void layoutComponents()
    {
        addSpacing(12);
        addField(createLabelPanel());
        addSpacing(12);
        addFinalField(featureTable);
    }

    /**
     * Create and return a new label panel.
     *
     * @return a new label panel
     */
    private LabelFieldPanel createLabelPanel()
    {
        LabelFieldPanel panel = new LabelFieldPanel();
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(0, 12, 0, 0));
        panel.addField("Nodes:", new CountLabel<CyNode>(model.nodes()));
        panel.addField("Nodes with features:", new CountLabel<Feature>(model.features()));
        return panel;
    }

    /**
     * Refresh.
     */
    private void refresh()
    {
        // empty
    }

    /**
     * Feature table.
     */
    private class FeatureTable extends ElementsTable<Feature>
    {

        /**
         * Create a new feature table with the specified list of features.
         *
         * @param features list of features, must not be null
         */
        FeatureTable(final EventList<Feature> features)
        {
            super("Features:", features, TABLE_FORMAT);
            getAddAction().setEnabled(false);
            getPasteAction().setEnabled(false);

            Component refreshContextMenuComponent = getContextMenu().add(refresh);
            // place at index 8 before add action
            getContextMenu().remove(refreshContextMenuComponent);
            getContextMenu().add(refreshContextMenuComponent, 8);

            Component refreshToolBarComponent = getToolBar().add(refresh);
            // place at index 0 before add action
            getToolBar().remove(refreshToolBarComponent);
            getToolBar().add(refreshToolBarComponent, 0);

            getToolBar().displayIcons();
            getToolBar().setIconSize(TangoProject.EXTRA_SMALL);
        }
    }
}