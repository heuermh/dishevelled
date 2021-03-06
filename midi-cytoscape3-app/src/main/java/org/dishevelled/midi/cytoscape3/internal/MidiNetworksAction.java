/*

    dsh-midi-cytoscape3-app  Cytoscape3 app for MIDI networks.
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
package org.dishevelled.midi.cytoscape3.internal;

import static javax.swing.SwingUtilities.windowForComponent;

import static org.dishevelled.midi.cytoscape3.internal.MidiNetworksUtils.installCloseKeyBinding;

import java.awt.Component;

import java.awt.event.ActionEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.cytoscape.application.CyApplicationManager;

import org.cytoscape.application.swing.AbstractCyAction;

import org.cytoscape.service.util.CyServiceRegistrar;

import org.cytoscape.task.read.LoadVizmapFileTaskFactory;

import org.cytoscape.view.vizmap.VisualMappingManager;

import org.cytoscape.work.swing.DialogTaskManager;

/**
 * Midi networks action.
 *
 * @author  Michael Heuer
 */
final class MidiNetworksAction extends AbstractCyAction
{
    /** Application manager. */
    private final CyApplicationManager applicationManager;

    /** Service registrar. */
    private final CyServiceRegistrar serviceRegistrar;

    /** Dialog task manager. */
    private final DialogTaskManager dialogTaskManager;

    /** Visual mapping manager. */
    private final VisualMappingManager visualMappingManager;

    /** Load vizmap file task factory. */
    private final LoadVizmapFileTaskFactory loadVizmapFileTaskFactory;


    /**
     * Create a new midi networks action.
     *
     * @param applicationManager application manager, must not be null
     * @param serviceRegistrar service registrar, must not be null
     * @param dialogTaskManager dialog task manager, must not be null
     * @param visualMappingManager visual mapping manager, must not be null
     * @param loadVizmapFileTaskFactory load vizmap file task factory, must not be null
     */
    MidiNetworksAction(final CyApplicationManager applicationManager,
                       final CyServiceRegistrar serviceRegistrar,
                       final DialogTaskManager dialogTaskManager,
                       final VisualMappingManager visualMappingManager,
                       final LoadVizmapFileTaskFactory loadVizmapFileTaskFactory)
    {
        super("Midi Networks");
        if (applicationManager == null)
        {
            throw new IllegalArgumentException("applicationManager must not be null");
        }
        if (serviceRegistrar == null)
        {
            throw new IllegalArgumentException("serviceRegistrar must not be null");
        }
        if (dialogTaskManager == null)
        {
            throw new IllegalArgumentException("dialogTaskManager must not be null");
        }
        if (visualMappingManager == null)
        {
            throw new IllegalArgumentException("visualMappingManager must not be null");
        }
        if (loadVizmapFileTaskFactory == null)
        {
            throw new IllegalArgumentException("loadVizmapFileTaskFactory must not be null");
        }
        this.applicationManager = applicationManager;
        this.serviceRegistrar = serviceRegistrar;
        this.dialogTaskManager = dialogTaskManager;
        this.visualMappingManager = visualMappingManager;
        this.loadVizmapFileTaskFactory = loadVizmapFileTaskFactory;

        setPreferredMenu("Apps");
    }


    @Override
    public void actionPerformed(final ActionEvent event)
    {
        if (event == null)
        {
            throw new NullPointerException("event must not be null");
        }
        JFrame frame = (JFrame) windowForComponent((Component) event.getSource());
        JDialog dialog = new JDialog(frame, "Midi Networks"); // i18n
        dialog.setContentPane(new DeviceView(applicationManager, serviceRegistrar, dialogTaskManager, visualMappingManager, loadVizmapFileTaskFactory));
        dialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        installCloseKeyBinding(dialog);
        dialog.setBounds(200, 200, 600, 400);
        dialog.setVisible(true);
    }
}