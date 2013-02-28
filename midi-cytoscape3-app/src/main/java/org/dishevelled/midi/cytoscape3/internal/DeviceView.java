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

import static org.dishevelled.midi.cytoscape3.internal.MidiNetworksUtils.selectedNode;
import static org.dishevelled.midi.cytoscape3.internal.MidiNetworksUtils.writeVizmapToTempFile;

import static javax.swing.SwingUtilities.windowForComponent;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;

import java.io.File;

import java.util.Arrays;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import javax.swing.border.EmptyBorder;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;

import ca.odell.glazedlists.swing.EventListModel;
import ca.odell.glazedlists.swing.EventSelectionModel;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.task.read.LoadVizmapFileTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.work.swing.DialogTaskManager;

import org.dishevelled.iconbundle.tango.TangoProject;

import org.dishevelled.identify.ContextMenuListener;
import org.dishevelled.identify.IdentifiableAction;
import org.dishevelled.identify.IdButton;
import org.dishevelled.identify.IdMenuItem;
import org.dishevelled.identify.IdToolBar;

import org.dishevelled.layout.ButtonPanel;
import org.dishevelled.layout.LabelFieldPanel;

import rwmidi.MidiInputDevice;
import rwmidi.MidiOutputDevice;
import rwmidi.RWMidi;

/**
 * Device view.
 *
 * @author  Michael Heuer
 */
final class DeviceView extends JPanel
{
    /** Application manager. */
    private final CyApplicationManager applicationManager;

    /** Dialog task manager. */
    private final DialogTaskManager dialogTaskManager;

    /** Load vizmap file task factory. */
    private final LoadVizmapFileTaskFactory loadVizmapFileTaskFactory;

    /** List of MIDI input devices. */
    private final EventList<String> inputDevices;

    /** List of MIDI output devices. */
    private final EventList<String> outputDevices;

    /** List of selected MIDI input devices. */
    private final EventList<String> selectedInputDevices;

    /** List of selected MIDI output devices. */
    private final EventList<String> selectedOutputDevices;

    /** Input device list. */
    private final JList inputDeviceList;

    /** Output device list. */
    private final JList outputDeviceList;

    /** Play action. */
    private final IdentifiableAction play = new IdentifiableAction("Play", TangoProject.MEDIA_PLAYBACK_START) // i18n
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                play();
            }
        };

    /** Record action. */
    private final IdentifiableAction record = new IdentifiableAction("Record", TangoProject.MEDIA_RECORD) // i18n
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                record();
            }
        };

    /** Load default vizmap styles. */
    private final Action loadDefaultVizmapStyles = new AbstractAction("Load Vizmap Styles") // i18n
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                loadDefaultVizmapStyles();
            }
        };

    /** Done action. */
    private final Action done = new AbstractAction("Done") // i18n
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                done();
            }
        };


    /**
     * Create a new device view.
     */
    DeviceView(final CyApplicationManager applicationManager,
               final DialogTaskManager dialogTaskManager,
               final LoadVizmapFileTaskFactory loadVizmapFileTaskFactory)
    {
        super();
        if (applicationManager == null)
        {
            throw new IllegalArgumentException("applicationManager must not be null");
        }
        if (dialogTaskManager == null)
        {
            throw new IllegalArgumentException("dialogTaskManager must not be null");
        }
        if (loadVizmapFileTaskFactory == null)
        {
            throw new IllegalArgumentException("loadVizmapFileTaskFactory must not be null");
        }
        this.applicationManager = applicationManager;
        this.dialogTaskManager = dialogTaskManager;
        this.loadVizmapFileTaskFactory = loadVizmapFileTaskFactory;

        inputDevices = GlazedLists.eventList(Arrays.asList(RWMidi.getInputDeviceNames()));
        EventListModel<String> inputDeviceModel = new EventListModel<String>(inputDevices);
        EventSelectionModel<String> inputDeviceSelectionModel = new EventSelectionModel<String>(inputDevices);
        selectedInputDevices = inputDeviceSelectionModel.getSelected();
        inputDeviceList = new JList(inputDeviceModel);
        inputDeviceList.setSelectionModel(inputDeviceSelectionModel);
        inputDeviceSelectionModel.setSelectionMode(EventSelectionModel.SINGLE_SELECTION);
        inputDeviceSelectionModel.addListSelectionListener(new ListSelectionListener()
            {
                @Override
                public void valueChanged(final ListSelectionEvent event)
                {
                    int size = selectedInputDevices.size();
                    record.setEnabled((size == 1) && (applicationManager.getCurrentNetwork() != null));
                }
            });
        // todo:  need to listen for changes to current network
        record.setEnabled(false);

        IdMenuItem recordMenuItem = new IdMenuItem(record);
        JPopupMenu inputDeviceContextMenu = new JPopupMenu();
        inputDeviceContextMenu.add(recordMenuItem);
        inputDeviceList.addMouseListener(new ContextMenuListener(inputDeviceContextMenu));

        outputDevices = GlazedLists.eventList(Arrays.asList(RWMidi.getOutputDeviceNames()));
        EventListModel<String> outputDeviceModel = new EventListModel<String>(outputDevices);
        EventSelectionModel<String> outputDeviceSelectionModel = new EventSelectionModel<String>(outputDevices);
        selectedOutputDevices = outputDeviceSelectionModel.getSelected();
        outputDeviceList = new JList(outputDeviceModel);
        outputDeviceList.setSelectionModel(outputDeviceSelectionModel);
        outputDeviceSelectionModel.setSelectionMode(EventSelectionModel.SINGLE_SELECTION);
        outputDeviceSelectionModel.addListSelectionListener(new ListSelectionListener()
            {
                @Override
                public void valueChanged(final ListSelectionEvent event)
                {
                    int size = selectedInputDevices.size();
                    play.setEnabled((size == 1) && (applicationManager.getCurrentNetwork() != null));
                }
            });
        // todo:  need to listen for changes in node selection
        play.setEnabled(false);

        IdMenuItem playMenuItem = new IdMenuItem(play);
        JPopupMenu outputDeviceContextMenu = new JPopupMenu();
        outputDeviceContextMenu.add(playMenuItem);
        outputDeviceList.addMouseListener(new ContextMenuListener(outputDeviceContextMenu));

        layoutComponents();
    }


    /**
     * Layout components.
     */
    private void layoutComponents()
    {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 2, 12, 12));
        mainPanel.setBorder(new EmptyBorder(12, 12, 0, 12));

        LabelFieldPanel inputDevicePanel = new LabelFieldPanel();
        inputDevicePanel.addLabel("MIDI input devices:"); // i18n
        inputDevicePanel.addFinalField(new JScrollPane(inputDeviceList));

        LabelFieldPanel outputDevicePanel = new LabelFieldPanel();
        outputDevicePanel.addLabel("MIDI output devices:"); // i18n
        outputDevicePanel.addFinalField(new JScrollPane(outputDeviceList));

        mainPanel.add(inputDevicePanel);
        mainPanel.add(outputDevicePanel);

        IdToolBar toolBar = new IdToolBar();
        IdButton recordButton = toolBar.add(record);
        recordButton.setBorderPainted(false);
        recordButton.setFocusPainted(false);
        IdButton playButton = toolBar.add(play);
        playButton.setBorderPainted(false);
        playButton.setFocusPainted(false);
        toolBar.add(loadDefaultVizmapStyles);

        JPopupMenu toolBarContextMenu = new JPopupMenu();
        for (Object menuItem : toolBar.getDisplayMenuItems())
        {
            toolBarContextMenu.add((JCheckBoxMenuItem) menuItem);
        }
        toolBarContextMenu.addSeparator();
        toolBarContextMenu.add(toolBar.createIconSizeMenuItem(TangoProject.EXTRA_SMALL));
        toolBarContextMenu.add(toolBar.createIconSizeMenuItem(TangoProject.SMALL));
        toolBarContextMenu.add(toolBar.createIconSizeMenuItem(TangoProject.MEDIUM));
        toolBarContextMenu.add(toolBar.createIconSizeMenuItem(TangoProject.LARGE));
        toolBar.addMouseListener(new ContextMenuListener(toolBarContextMenu));

        toolBar.displayIcons();
        toolBar.setIconSize(TangoProject.MEDIUM);

        ButtonPanel buttonPanel = new ButtonPanel();
        buttonPanel.setBorder(new EmptyBorder(24, 12, 12, 12));
        buttonPanel.add(done);

        setLayout(new BorderLayout());
        add("North", toolBar);
        add("Center", mainPanel);
        add("South", buttonPanel);
    }

    /**
     * Play.
     */
    private void play()
    {
        String outputDeviceName = selectedOutputDevices.get(0);
        MidiOutputDevice outputDevice = RWMidi.getOutputDevice(outputDeviceName);
        Random random = new Random();
        CyNetwork network = applicationManager.getCurrentNetwork();
        CyNode start = selectedNode(network);

        // start playback task
        PlaybackTaskFactory playbackTaskFactory = new PlaybackTaskFactory(outputDevice, random, start, network);
        dialogTaskManager.execute(playbackTaskFactory.createTaskIterator());
    }

    /**
     * Record.
     */
    private void record()
    {
        String inputDeviceName = selectedInputDevices.get(0);
        MidiInputDevice inputDevice = RWMidi.getInputDevice(inputDeviceName);
        CyNetwork network = applicationManager.getCurrentNetwork();

        // start record task
        RecordTaskFactory recordTaskFactory = new RecordTaskFactory(inputDevice, network);
        dialogTaskManager.execute(recordTaskFactory.createTaskIterator());
    }

    /**
     * Done.
     */
    private void done()
    {
        windowForComponent(this).setVisible(false);
    }

    /**
     * Load default vizmap styles.
     */
    private void loadDefaultVizmapStyles()
    {
        File tmp = writeVizmapToTempFile();
        CyNetworkView networkView = applicationManager.getCurrentNetworkView();
        for (VisualStyle visualStyle : loadVizmapFileTaskFactory.loadStyles(tmp))
        {
            visualStyle.apply(networkView);
        }
        networkView.updateView();
        loadDefaultVizmapStyles.setEnabled(false);
    }
}