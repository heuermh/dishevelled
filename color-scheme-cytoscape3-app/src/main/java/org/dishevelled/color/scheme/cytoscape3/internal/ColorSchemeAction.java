/*

    dsh-color-scheme-cytoscape3-app  Color scheme Cytoscape3 app.
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
package org.dishevelled.color.scheme.cytoscape3.internal;

import static javax.swing.SwingUtilities.windowForComponent;

import static com.google.common.base.Preconditions.checkNotNull;

import java.awt.BorderLayout;
import java.awt.Component;

import java.awt.event.ActionEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import javax.swing.border.EmptyBorder;

import org.cytoscape.application.CyApplicationManager;

import org.cytoscape.application.swing.AbstractCyAction;

import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;

/**
 * Color scheme action.
 *
 * @author  Michael Heuer
 */
final class ColorSchemeAction extends AbstractCyAction
{
    /** Application manager. */
    private final CyApplicationManager applicationManager;

    /** Visual mapping manager. */
    private final VisualMappingManager visualMappingManager;

    /** Continuous mapping factory. */
    private final VisualMappingFunctionFactory continuousMappingFactory;

    /** Discrete mapping factory. */
    private final VisualMappingFunctionFactory discreteMappingFactory;


    /**
     * Create a new color scheme action.
     *
     * @param applicationManager application manager, must not be null
     * @param visualMappingManager visual mapping manager, must not be null
     * @param continuousMappingFactory continuous mapping factory, must not be null
     * @param discreteMappingFactory discrete mapping factory, must not be null
     */
    ColorSchemeAction(final CyApplicationManager applicationManager,
                      final VisualMappingManager visualMappingManager,
                      final VisualMappingFunctionFactory continuousMappingFactory,
                      final VisualMappingFunctionFactory discreteMappingFactory)
    {
        super("Color Scheme");
        setPreferredMenu("Apps");

        checkNotNull(applicationManager);
        checkNotNull(visualMappingManager);
        checkNotNull(continuousMappingFactory);
        checkNotNull(discreteMappingFactory);
        this.applicationManager = applicationManager;
        this.visualMappingManager = visualMappingManager;
        this.continuousMappingFactory = continuousMappingFactory;
        this.discreteMappingFactory = discreteMappingFactory;
    }


    @Override
    public void actionPerformed(final ActionEvent event)
    {
        if (event == null)
        {
            throw new NullPointerException("event must not be null");
        }
        showDialog(event);
    }

    /**
     * Show dialog.
     *
     * @param event event
     */
    private void showDialog(final ActionEvent event)
    {
        JFrame frame = (JFrame) windowForComponent((Component) event.getSource());
        JDialog dialog = new JDialog(frame, "Color Scheme");

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(12, 12, 12, 12));
        contentPane.setLayout(new BorderLayout());

        // create app, set as content pane

        dialog.setContentPane(contentPane);

        dialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        //installCloseKeyBinding(dialog);
        dialog.setBounds(200, 200, 1016, 628);
        dialog.setVisible(true);
    }
}