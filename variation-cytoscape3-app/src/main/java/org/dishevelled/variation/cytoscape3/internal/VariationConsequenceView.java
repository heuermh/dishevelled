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

import java.awt.Color;
import java.awt.Component;

import java.awt.event.ActionEvent;

import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTable;

import javax.swing.border.EmptyBorder;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

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
import org.dishevelled.variation.Variation;
import org.dishevelled.variation.VariationConsequence;

/**
 * Variation consequence view.
 *
 * @author  Michael Heuer
 */
final class VariationConsequenceView
    extends LabelFieldPanel
{
    /** Variation model. */
    private final VariationModel model;

    /** Variation consequence table. */
    private final VariationConsequenceTable variationConsequenceTable;

    /** Refresh action. */
    private final IdentifiableAction refresh = new IdentifiableAction("Refresh...", TangoProject.VIEW_REFRESH)
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                refresh();
            }
        };

    // todo:  need to add node, feature, variation columns
    /** Table property names. */
    private static final String[] PROPERTY_NAMES = { "species", "reference", "region", "start", "end", "identifiers", "referenceAllele", "alternateAllele", "sequenceOntologyTerm" };

    /** Table column labels. */
    private static final String[] COLUMN_LABELS = { "Species", "Reference", "Region", "Start", "End", "Identifiers", "Ref", "Alt", "Consequence" };

    /** Table format. */
    private static final TableFormat<VariationConsequence> TABLE_FORMAT = GlazedLists.tableFormat(VariationConsequence.class, PROPERTY_NAMES, COLUMN_LABELS);


    /**
     * Create a new variation consequence view with the specified model.
     *
     * @param model model, must not be null
     */
    VariationConsequenceView(final VariationModel model)
    {
        super();
        setOpaque(false);
        this.model = model;
        variationConsequenceTable = new VariationConsequenceTable(this.model.variationConsequences());

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
        addFinalField(variationConsequenceTable);
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
        panel.addField("Variations associated with features:", new CountLabel<Variation>(model.variations()));
        panel.addField("Variation consequences:", new CountLabel<VariationConsequence>(model.variationConsequences()));
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
     * Variation consequence table.
     */
    private class VariationConsequenceTable extends ElementsTable<VariationConsequence>
    {

        /**
         * Create a new variation consequence table with the specified list of variation consequences.
         *
         * @param variationConsequences list of variation consequences, must not be null
         */
        VariationConsequenceTable(final EventList<VariationConsequence> variationConsequences)
        {
            super("Variation consequences:", variationConsequences, TABLE_FORMAT);

            final TableRowColorMapping tableRowColorMapping = new TableRowColorMapping();
            final TableCellRenderer tableCellRenderer = new DefaultTableCellRenderer()
                {
                    @Override
                    public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int column)
                    {
                        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                        TableModel tableModel = table.getModel();
                        int tableModelRow = table.convertRowIndexToModel(row);
                        String consequenceTerm = (String) tableModel.getValueAt(tableModelRow, 8); // hard coded column index
                        Color rowColor = tableRowColorMapping.getColor(consequenceTerm);
                        if (rowColor != null && !isSelected)
                        {
                            label.setBackground(rowColor);
                        }
                        return label;
                    }
                };
            getTable().setDefaultRenderer(Integer.class, tableCellRenderer);
            getTable().setDefaultRenderer(String.class, tableCellRenderer);
            getTable().setDefaultRenderer(List.class, tableCellRenderer);

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


        @Override
        public void add()
        {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showOpenDialog(this);
        }
    }
}