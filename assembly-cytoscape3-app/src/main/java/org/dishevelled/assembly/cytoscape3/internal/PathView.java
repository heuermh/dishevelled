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

import javax.swing.JLabel;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;

import ca.odell.glazedlists.gui.TableFormat;

import org.dishevelled.bio.assembly.gfa1.Path;

import org.dishevelled.eventlist.view.CountLabel;
import org.dishevelled.eventlist.view.ElementsTable;

import org.dishevelled.layout.LabelFieldPanel;

/**
 * Path view.
 *
 * @author  Michael Heuer
 */
final class PathView extends LabelFieldPanel
{
    /** Assembly model. */
    private final AssemblyModel model;

    /** Path table. */
    private final PathTable pathTable;

    /** Table property names. */
    private static final String[] PROPERTY_NAMES = { "name", "segments", "overlaps" };

    /** Table column labels. */
    private static final String[] COLUMN_LABELS = { "Name", "Segments", "Overlaps" };

    /** Table format. */
    private static final TableFormat<Path> TABLE_FORMAT = GlazedLists.tableFormat(Path.class, PROPERTY_NAMES, COLUMN_LABELS);


    /**
     * Create a new path view with the specified assembly model.
     *
     * @param model assembly model, must not be null
     */
    PathView(final AssemblyModel model)
    {
        checkNotNull(model);
        this.model = model;
        pathTable = new PathTable(this.model.paths());

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
        addFinalField(pathTable);
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
        // todo: update labels based on property change
        panel.addField("Input file:", new JLabel(model.getInputFileName()));
        panel.addField("Path:", new JLabel(model.getPath() == null ? "" : model.getPath().getName()));
        panel.addField("Paths:", new CountLabel<Path>(model.paths()));
        return panel;
    }

    /**
     * Path table.
     */
    private class PathTable extends ElementsTable<Path>
    {

        /**
         * Create a new path table with the specified list of paths.
         *
         * @param paths list of paths, must not be null
         */
        PathTable(final EventList<Path> paths)
        {
            super("Paths:", paths, TABLE_FORMAT);
            getAddAction().setEnabled(false);
            getPasteAction().setEnabled(false);
        }
    }
}
