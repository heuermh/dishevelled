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

import javax.swing.JPanel;

import rwmidi.Controller;
import rwmidi.MidiInput;
import rwmidi.Note;
import rwmidi.ProgramChange;
import rwmidi.SysexMessage;

/**
 * Record view.
 *
 * @author  Michael Heuer
 */
final class RecordView extends JPanel
{
    /** Last timestamp. */
    private long last;

    /** Last node id. */
    private String lastId;

    /** True if recording. */
    private boolean recording = false;


    /**
     * Create a new record view with the specified MIDI input.
     *
     * @param input MIDI input, must not be null
     */
    RecordView(final MidiInput input)
    {
        super();
        if (input == null)
        {
            throw new IllegalArgumentException("input must not be null");
        }
        input.plug(this, 1); // pass channel in?

        layoutComponents();
    }


    /**
     * Layout components.
     */
    private void layoutComponents()
    {
        // empty
    }

    /**
     * Start recording.
     */
    public void start()
    {
        recording = true;
    }

    /**
     * Stop recording.
     */
    public void stop()
    {
        recording = false;
    }

    /**
     * Toggle recording.
     */
    public void toggle()
    {
        recording = !recording;
    }

    /**
     * Note on callback.
     *
     * @param note note
     */
    public void noteOnReceived(final Note note)
    {
        if (recording)
        {
            String currentId = createNoteOn(note.getPitch(), note.getVelocity());
            long current = System.currentTimeMillis();
            if (((current - last) > 0) && (lastId != null))
            {
                createWait(lastId, currentId, (current - last), 1.0d);
            }
            last = current;
            lastId = currentId;
        }
    }

    /**
     * Note off callback.
     *
     * @param note note
     */
    public void noteOffReceived(final Note note)
    {
        if (recording)
        {
            String currentId = createNoteOff(note.getPitch(), note.getVelocity());
            long current = System.currentTimeMillis();
            if (((current - last) > 0) && (lastId != null))
            {
                createWait(lastId, currentId, (current - last), 1.0d);
            }
            last = current;
            lastId = currentId;
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
     * Create a new note on node.
     *
     * @param note note
     * @param velocity velocity
     * @return the new note on node id
     */
    private String createNoteOn(final int note, final int velocity)
    {
        return null;
    }

    /**
     * Create a new note off node.
     *
     * @param note note
     * @param velocity velocity
     * @return the new note off node id
     */
    private String createNoteOff(final int note, final int velocity)
    {
        return null;
    }

    /**
     * Create a new wait edge.
     *
     * @param sourceId source node id
     * @param targetId target node id
     * @param duration duration
     * @param weight weight
     * @return the new wait edge id
     */
    private String createWait(final String sourceId, final String targetId, final long duration, final double weight)
    {
        return null;
    }
}