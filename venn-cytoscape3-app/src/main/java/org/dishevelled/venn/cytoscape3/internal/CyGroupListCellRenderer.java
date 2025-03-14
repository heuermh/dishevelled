/*

    dsh-venn-cytoscape3-app  Cytoscape3 app for venn and euler diagrams.
    Copyright (c) 2012-2019 held jointly by the individual authors.

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
package org.dishevelled.venn.cytoscape3.internal;

import static org.dishevelled.venn.cytoscape3.internal.VennDiagramsUtils.nameOf;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.group.CyGroup;
import org.cytoscape.model.CyNetwork;

/**
 * List cell renderer for <code>CyGroup</code>.
 *
 * @author  Michael Heuer
 */
final class CyGroupListCellRenderer extends DefaultListCellRenderer //IdListCellRenderer
{
    /** Application manager. */
    private final CyApplicationManager applicationManager;


    /**
     * Create a new CyGroup list cell renderer with the specified application manager.
     *
     * @param applicationManager application manager, must not be null
     */
    CyGroupListCellRenderer(final CyApplicationManager applicationManager)
    {
        super();
        if (applicationManager == null)
        {
            throw new IllegalArgumentException("applicationManager must not be null");
        }
        this.applicationManager = applicationManager;
    }


    @Override
    public Component getListCellRendererComponent(final JList list,
                                                  final Object value,
                                                  final int index,
                                                  final boolean isSelected,
                                                  final boolean hasFocus)
    {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, hasFocus);

        // alternatively, provide a BeanInfo implementation for CyNode, CyGroup that implements getName in this manner
        if (value instanceof CyGroup)
        {
            CyGroup group = (CyGroup) value;
            CyNetwork network = applicationManager.getCurrentNetwork();
            label.setText(nameOf(group, network));
        }
        return label;
    }
}
