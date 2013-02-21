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

import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;

import java.util.Arrays;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.border.EmptyBorder;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;

import ca.odell.glazedlists.swing.EventListModel;
import ca.odell.glazedlists.swing.EventSelectionModel;

import org.dishevelled.layout.ButtonPanel;
import org.dishevelled.layout.LabelFieldPanel;

import rwmidi.RWMidi;

/**
 * Device view.
 *
 * @author  Michael Heuer
 */
final class DeviceView extends JPanel
{
    /** List of MIDI input devices. */
    private final EventList<String> inputDevices;

    /** List of MIDI output devices. */
    private final EventList<String> outputDevices;

    /** Input device list. */
    private final JList inputDeviceList;

    /** Output device list. */
    private final JList outputDeviceList;

    /** Done action. */
    private final Action done = new AbstractAction("Done") // i18n
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                done();
            }
        };


    /**
     * Create a new device view.
     */
    DeviceView()
    {
        super();

        inputDevices = GlazedLists.eventList(Arrays.asList(RWMidi.getInputDeviceNames()));
        EventListModel<String> inputDeviceModel = new EventListModel<String>(inputDevices);
        EventSelectionModel<String> inputDeviceSelectionModel = new EventSelectionModel<String>(inputDevices);
        inputDeviceList = new JList(inputDeviceModel);
        inputDeviceList.setSelectionModel(inputDeviceSelectionModel);

        outputDevices = GlazedLists.eventList(Arrays.asList(RWMidi.getOutputDeviceNames()));
        EventListModel<String> outputDeviceModel = new EventListModel<String>(outputDevices);
        EventSelectionModel<String> outputDeviceSelectionModel = new EventSelectionModel<String>(outputDevices);
        outputDeviceList = new JList(outputDeviceModel);
        outputDeviceList.setSelectionModel(outputDeviceSelectionModel);

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

        ButtonPanel buttonPanel = new ButtonPanel();
        buttonPanel.setBorder(new EmptyBorder(24, 12, 12, 12));
        buttonPanel.add(done);

        setLayout(new BorderLayout());
        add("Center", mainPanel);
        add("South", buttonPanel);
    }

    /**
     * Done.
     */
    private void done()
    {
        windowForComponent(this).setVisible(false);
    }
}