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

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * Assembly app.
 *
 * @author  Michael Heuer
 */
final class AssemblyApp extends JPanel
{
    /** Assembly model. */
    private final AssemblyModel model;

    /** Path view. */
    private final PathView pathView;

    /** Traversal view. */
    private final TraversalView traversalView;


    /**
     * Create a new assembly app with the specified assembly model.
     *
     * @param model assembly model, must not be null
     */
    AssemblyApp(final AssemblyModel model)
    {
        super();

        checkNotNull(model);
        this.model = model;

        pathView = new PathView(this.model);
        traversalView = new TraversalView(this.model);

        layoutComponents();
    }


    /**
     * Layout components.
     */
    private void layoutComponents()
    {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Paths", pathView);
        tabbedPane.add("Traversals", traversalView);

        setLayout(new BorderLayout());
        add("Center", tabbedPane);
    }
}
