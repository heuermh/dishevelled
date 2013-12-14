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
        // empty
    }
}
