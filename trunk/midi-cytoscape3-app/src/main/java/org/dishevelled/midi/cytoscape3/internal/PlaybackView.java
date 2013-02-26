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

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;

import java.util.Random;

import javax.swing.JPanel;

import javax.swing.border.EmptyBorder;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyEdge;

import org.dishevelled.iconbundle.tango.TangoProject;

import org.dishevelled.identify.IdentifiableAction;

import org.dishevelled.layout.ButtonPanel;

import rwmidi.Controller;
import rwmidi.MidiOutput;
import rwmidi.Note;
import rwmidi.ProgramChange;
import rwmidi.SysexMessage;

/**
 * Playback view.
 *
 * @author  Michael Heuer
 */
final class PlaybackView extends JPanel
{
    /** MIDI output. */
    private final MidiOutput output;

    /** Source of randomness. */
    private final Random random;

    /** Network. */
    private final CyNetwork network;

    /** Play action. */  // enable only if a single node is selected
    private final IdentifiableAction play = new IdentifiableAction("Play", TangoProject.MEDIA_PLAYBACK_START)
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                play();
            }
        };

    // channel, bpm, humanize, etc.


    /**
     * Create a new playback view with the specified MIDI output.
     *
     * @param output MIDI output, must not be null
     * @param random source of randomness, must not be null
     * @param network network, must not be null
     */
    PlaybackView(final MidiOutput output, final Random random, final CyNetwork network)
    {
        super();
        if (output == null)
        {
            throw new IllegalArgumentException("output must not be null");
        }
        if (random == null)
        {
            throw new IllegalArgumentException("random must not be null");
        }
        if (network == null)
        {
            throw new IllegalArgumentException("network must not be null");
        }
        this.output = output;
        this.random = random;
        this.network = network;

        layoutComponents();
    }


    /**
     * Layout components.
     */
    private void layoutComponents()
    {
        JPanel mainPanel = new JPanel();        

        ButtonPanel buttonPanel = new ButtonPanel();
        buttonPanel.setBorder(new EmptyBorder(24, 12, 12, 12));
        buttonPanel.add(play);

        setLayout(new BorderLayout());
        add("Center", mainPanel);
        add("South", buttonPanel);
    }

    /**
     * Play.
     */
    private void play()
    {
        // capture selected node
        // start playback task
    }
}