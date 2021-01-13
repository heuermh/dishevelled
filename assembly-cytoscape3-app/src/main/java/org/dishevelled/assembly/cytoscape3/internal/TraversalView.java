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

import java.awt.Toolkit;

import java.awt.datatransfer.StringSelection;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.List;

import javax.swing.JLabel;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;

import ca.odell.glazedlists.gui.TableFormat;

import org.dishevelled.bio.assembly.gfa1.Orientation;
import org.dishevelled.bio.assembly.gfa1.Path;
import org.dishevelled.bio.assembly.gfa1.Traversal;

import org.dishevelled.identify.StripeTableCellRenderer;

import org.dishevelled.eventlist.view.CountLabel;
import org.dishevelled.eventlist.view.ElementsTable;

import org.dishevelled.layout.LabelFieldPanel;

/**
 * Traversal view.
 *
 * @author  Michael Heuer
 */
final class TraversalView extends LabelFieldPanel
{
    /** Assembly model. */
    private final AssemblyModel model;

    /** Input file label. */
    private final JLabel inputFile;

    /** Path label. */
    private final JLabel path;

    /** Path count label. */
    private final CountLabel<Path> pathCount;

    /** Traversal count label. */
    private final CountLabel<Traversal> traversalCount;

    /** Traversal table. */
    private final TraversalTable traversalTable;

    /** Table property names. */
    private static final String[] PROPERTY_NAMES = { "pathName", "ordinal", "source.id", "source.orientation", "target.id", "target.orientation", "overlap" };

    /** Table column labels. */
    private static final String[] COLUMN_LABELS = { "Path", "Ordinal", "Source id", "Source orientation", "Target id", "Target orientation", "Overlap" };

    /** Table format. */
    private static final TableFormat<Traversal> TABLE_FORMAT = GlazedLists.tableFormat(Traversal.class, PROPERTY_NAMES, COLUMN_LABELS);


    /**
     * Create a new traversal view with the specified assembly model.
     *
     * @param model assembly model, must not be null
     */
    TraversalView(final AssemblyModel model)
    {
        checkNotNull(model);
        this.model = model;

        inputFile = new JLabel(this.model.getInputFileName() == null ? "" : this.model.getInputFileName());
        pathCount = new CountLabel<Path>(this.model.paths());
        path = new JLabel(this.model.getPath() == null ? "" : this.model.getPath().getName());
        traversalCount = new CountLabel<Traversal>(this.model.traversals());
        traversalTable = new TraversalTable(this.model.traversals());

        this.model.addPropertyChangeListener(new PropertyChangeListener()
            {
                @Override
                public void propertyChange(final PropertyChangeEvent e)
                {
                    switch (e.getPropertyName())
                    {
                    case "inputFile":
                        inputFile.setText(TraversalView.this.model.getInputFileName() == null ? "" : TraversalView.this.model.getInputFileName());
                        break;
                    case "path":
                        path.setText(TraversalView.this.model.getPath() == null ? "" : TraversalView.this.model.getPath().getName());
                        break;
                    default:
                        break;
                    }
                }
            });

        layoutComponents();
    }


    /**
     * Layout components.
     */
    private void layoutComponents()
    {
        setBorder(12);
        setOpaque(false);
        addField(createLabelPanel());
        addFinalField(traversalTable);
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
        panel.addField("Input file:", inputFile);
        panel.addSpacing(6);
        panel.addField("Paths:", pathCount);
        panel.addField("Path:", path);
        panel.addSpacing(6);
        panel.addField("Traversals:", traversalCount);
        return panel;
    }

    /**
     * Traversal table.
     */
    private static class TraversalTable extends ElementsTable<Traversal>
    {

        /**
         * Create a new traversal table with the specified list of traversals.
         *
         * @param traversals list of traversals, must not be null
         */
        TraversalTable(final EventList<Traversal> traversals)
        {
            super("Traversals:", traversals, TABLE_FORMAT);
            getAddAction().setEnabled(false);
            getPasteAction().setEnabled(false);

            StripeTableCellRenderer renderer = new StripeTableCellRenderer();
            getTable().setDefaultRenderer(Orientation.class, renderer);
        }


        @Override
        protected final void copy(final List<Traversal> toCopy)
        {
            StringBuilder sb = new StringBuilder(toCopy.size() * 1024);
            for (Traversal traversal : toCopy)
            {
                sb.append(traversal.toString());
                sb.append("\n");
            }
            StringSelection selection = new StringSelection(sb.toString());
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);
        }
    }
}
