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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;

import org.cytoscape.model.CyNetwork;

/**
 * Node count label.
 */
final class NodeCountLabel
    extends JLabel
{
    // listen for model network property change events
    private final PropertyChangeListener propertyChangeListener = new PropertyChangeListener()
        {
            @Override
            public void propertyChange(final PropertyChangeEvent event)
            {
                if (event.getNewValue() instanceof CyNetwork)
                {
                    CyNetwork network = (CyNetwork) event.getNewValue();
                    setText(String.valueOf(network.getNodeCount()));
                }
                else
                {
                    setText("-");
                }
            }
        };

    // todo: also need to listen for node added/removed events fired by the current network

    NodeCountLabel(final VariationModel model)
    {
        super("-", 12);
        checkNotNull(model);
        model.addPropertyChangeListener("network", propertyChangeListener);
    }
}
