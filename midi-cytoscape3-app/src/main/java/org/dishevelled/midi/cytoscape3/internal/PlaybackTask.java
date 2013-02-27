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

import static org.dishevelled.midi.cytoscape3.internal.MidiNetworksUtils.durationOf;
import static org.dishevelled.midi.cytoscape3.internal.MidiNetworksUtils.nameOf;
import static org.dishevelled.midi.cytoscape3.internal.MidiNetworksUtils.noteOf;
import static org.dishevelled.midi.cytoscape3.internal.MidiNetworksUtils.outEdges;
import static org.dishevelled.midi.cytoscape3.internal.MidiNetworksUtils.typeOf;
import static org.dishevelled.midi.cytoscape3.internal.MidiNetworksUtils.velocityOf;
import static org.dishevelled.midi.cytoscape3.internal.MidiNetworksUtils.weightOf;

import java.util.Random;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyEdge;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import org.dishevelled.weighted.WeightedMap;
import org.dishevelled.weighted.WeightedMaps;

import rwmidi.Controller;
import rwmidi.MidiOutput;
import rwmidi.MidiOutputDevice;
import rwmidi.Note;
import rwmidi.ProgramChange;
import rwmidi.SysexMessage;

/**
 * Playback task.
 *
 * @author  Michael Heuer
 */
final class PlaybackTask extends AbstractTask
{
    /** Node to start from. */
    private final CyNode start;

    /** Network. */
    private final CyNetwork network;

    /** MIDI output. */
    private final MidiOutput output;

    /** Source of randomness. */
    private final Random random;

    // channel, bpm, humanize, etc.


    /**
     * Create a new playback task with the specified MIDI output device.
     *
     * @param output MIDI output device, must not be null
     * @param random source of randomness, must not be null
     * @param start node to start from, must not be null
     * @param network network, must not be null
     */
    PlaybackTask(final MidiOutputDevice outputDevice, final Random random, final CyNode start, final CyNetwork network)
    {
        super();
        if (outputDevice == null)
        {
            throw new IllegalArgumentException("outputDevice must not be null");
        }
        if (random == null)
        {
            throw new IllegalArgumentException("random must not be null");
        }
        if (start == null)
        {
            throw new IllegalArgumentException("start must not be null");
        }
        if (network == null)
        {
            throw new IllegalArgumentException("network must not be null");
        }
        this.output = outputDevice.createOutput();
        this.random = random;
        this.start = start;
        this.network = network;
    }


    @Override
    public void run(final TaskMonitor taskMonitor)
    {
        taskMonitor.setTitle("Playback from " + nameOf(start, network));
        taskMonitor.setProgress(0.0d);

        CyNode current = start;
        while (!cancelled)
        {
            String nodeType = typeOf(current, network);
            int note = noteOf(current, network);
            int velocity = velocityOf(current, network);
            taskMonitor.setStatusMessage(nodeType + " " + note + " " + velocity);
            System.out.println(nodeType + " " + note + " " + velocity);

            if ("noteOn".equals(nodeType))
            {
                // send note on midi
            }
            else if ("noteOff".equals(nodeType))
            {
                // send note off midi
            }

            WeightedMap<CyEdge> outEdges = WeightedMaps.createWeightedMap();
            for (CyEdge edge : outEdges(current, network))
            {
                outEdges.put(edge, weightOf(edge, network));
            }

            // break if no outgoing edges
            if (outEdges.isEmpty())
            {
                System.out.println("outEdges is empty, breaking...");
                break;
            }
            else
            {
                System.out.println("outEdges size " + outEdges.size());
            }

            // wait on sampled edge
            CyEdge edge = outEdges.sample();
            String edgeType = typeOf(edge, network);
            long duration = durationOf(edge, network);
            taskMonitor.setStatusMessage(edgeType + " "  + duration + " ms");
            System.out.println(edgeType + " " + duration + " ms");

            try
            {
                Thread.currentThread().sleep(duration);
            }
            catch (InterruptedException e)
            {
                // ok
            }
            current = edge.getTarget();

            System.out.println("done sleeping");
        }
        System.out.println("break or cancelled");
    }
}