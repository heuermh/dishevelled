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

import static org.dishevelled.midi.cytoscape3.internal.MidiNetworksUtils.midiNameOf;
import static org.dishevelled.midi.cytoscape3.internal.MidiNetworksUtils.typeOf;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import rwmidi.Controller;
import rwmidi.MidiInput;
import rwmidi.MidiInputDevice;
import rwmidi.MidiOutput;
import rwmidi.MidiOutputDevice;
import rwmidi.Note;
import rwmidi.ProgramChange;
import rwmidi.SysexMessage;

/**
 * Record task.
 *
 * @author  Michael Heuer
 */
public final class RecordTask extends AbstractTask // needs to be public for reflection to work
{
    /** MIDI output. */
    private final MidiOutput output;

    /** Network. */
    private final CyNetwork network;

    /** Last timestamp. */
    private long last;

    /** Last node. */
    private CyNode lastNode;


    /**
     * Create a new record task with the specified MIDI input and output devices.
     *
     * @param inputDevice MIDI input device, must not be null
     * @param outputDevice MIDI output device, must not be null
     * @param network network, must not be null
     */
    RecordTask(final MidiInputDevice inputDevice, final MidiOutputDevice outputDevice, final CyNetwork network)
    {
        super();
        if (inputDevice == null)
        {
            throw new IllegalArgumentException("inputDevice must not be null");
        }
        if (outputDevice == null)
        {
            throw new IllegalArgumentException("outputDevice must not be null");
        }
        if (network == null)
        {
            throw new IllegalArgumentException("network must not be null");
        }
        MidiInput input = inputDevice.createInput(this);
        input.plug(this, 1); // pass channel in?
        this.output = outputDevice.createOutput();
        this.network = network;
    }


    @Override
    public void run(final TaskMonitor taskMonitor)
    {
        taskMonitor.setTitle("Recording");
        taskMonitor.setProgress(0.0d);

        while (!cancelled)
        {
            try
            {
                Thread.sleep(250L);
            }
            catch (InterruptedException e)
            {
                // ok
            }
        }
    }

    /**
     * Note on callback.
     *
     * @param note note
     */
    public void noteOnReceived(final Note note)
    {
        if (!cancelled)
        {
            CyNode currentNode = createNoteOn(note.getPitch(), note.getVelocity());
            long current = System.currentTimeMillis();
            if (((current - last) > 0) && (lastNode != null))
            {
                createWait(lastNode, currentNode, (current - last), 1.0d);
            }
            last = current;
            lastNode = currentNode;

            // forward note on midi message
            output.sendNoteOn(1, note.getPitch(), note.getVelocity());
        }
    }

    /**
     * Note off callback.
     *
     * @param note note
     */
    public void noteOffReceived(final Note note)
    {
        if (!cancelled)
        {
            CyNode currentNode = createNoteOff(note.getPitch(), note.getVelocity());
            long current = System.currentTimeMillis();
            if (((current - last) > 0) && (lastNode != null))
            {
                createWait(lastNode, currentNode, (current - last), 1.0d);
            }
            last = current;
            lastNode = currentNode;

            // forward note off midi message
            output.sendNoteOff(1, note.getPitch(), note.getVelocity());
        }
    }

    /**
     * Controller change callback.
     *
     * @param controller controller
     */
    public void controllerChangeReceived(final Controller controller)
    {
        // empty
    }

    /**
     * Program change callback.
     *
     * @param programChange program change
     */
    public void programChangeReceived(final ProgramChange programChange)
    {
        // empty
    }

    /**
     * Sysex message callback.
     *
     * @param sysexMessage sysex message
     */
    public void sysexReceived(final SysexMessage sysexMessage)
    {
        // empty
    }

    /**
     * Create and return a new note on node.
     *
     * @param note note
     * @param velocity velocity
     * @return the new note on node
     */
    private CyNode createNoteOn(final int note, final int velocity)
    {
        return createNode("noteOn", note, velocity);
    }

    /**
     * Create and return a new note off node.
     *
     * @param note note
     * @param velocity velocity
     * @return the new note off node
     */
    private CyNode createNoteOff(final int note, final int velocity)
    {
        return createNode("noteOff", note, velocity);
    }

    /**
     * Create and return a new node.
     *
     * @param type type
     * @param note note
     * @param velocity velocity
     * @return the new node
     */
    private CyNode createNode(final String type, final int note, final int velocity)
    {
        CyNode node = network.addNode();
        CyTable table = network.getDefaultNodeTable();
        CyRow row = table.getRow(node.getSUID());
        long timestamp = System.currentTimeMillis();

        if (table.getColumn(CyNetwork.NAME) == null)
        {
            table.createColumn(CyNetwork.NAME, String.class, false);
        }
        if (table.getColumn("type") == null)
        {
            table.createColumn("type", String.class, false);
        }
        if (table.getColumn("note") == null)
        {
            table.createColumn("note", Integer.class, false);
        }
        if (table.getColumn("velocity") == null)
        {
            table.createColumn("velocity", Integer.class, false);
        }
        if (table.getColumn("timestamp") == null)
        {
            table.createColumn("timestamp", Long.class, false);
        }
        row.set(CyNetwork.NAME, midiNameOf(note));
        row.set("type", type);
        row.set("note", note);
        row.set("velocity", velocity);
        row.set("timestamp", timestamp);

        return node;
    }

    /**
     * Create a new wait edge.
     *
     * @param source source node
     * @param target target node
     * @param duration duration
     * @param weight weight
     * @return the new wait edge
     */
    private CyEdge createWait(final CyNode source, final CyNode target, final long duration, final double weight)
    {
        CyEdge edge = network.addEdge(source, target, true);
        CyTable table = network.getDefaultEdgeTable();
        CyRow row = table.getRow(edge.getSUID());
        String type = edgeType(source, target);

        if (table.getColumn("type") == null)
        {
            table.createColumn("type", String.class, false);
        }
        if (table.getColumn("duration") == null)
        {
            table.createColumn("duration", Long.class, false);
        }
        if (table.getColumn("weight") == null)
        {
            table.createColumn("weight", Double.class, false);
        }

        row.set(CyEdge.INTERACTION, type);
        row.set("type", type);
        row.set("duration", duration);
        row.set("weight", weight);

        return edge;
    }

    /**
     * Return the edge type given the specified source and target nodes.
     *
     * @param source source node
     * @param target target node
     * @return the edge type given the specified source and target nodes
     */
    private String edgeType(final CyNode source, final CyNode target)
    {
        String sourceType = typeOf(source, network);
        String targetType = typeOf(target, network);

        if ("noteOn".equals(sourceType) && "noteOff".equals(targetType))
        {
            return "note";
        }
        else if ("noteOff".equals(sourceType) && "noteOn".equals(targetType))
        {
            return "rest";
        }
        return "wait";
    }
}