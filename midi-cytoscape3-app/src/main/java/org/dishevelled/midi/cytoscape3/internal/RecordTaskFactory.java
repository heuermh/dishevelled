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

import org.cytoscape.model.CyNetwork;

import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

import rwmidi.MidiInputDevice;
import rwmidi.MidiOutputDevice;

/**
 * Record task factory.
 *
 * @author  Michael Heuer
 */
final class RecordTaskFactory extends AbstractTaskFactory
{
    /** MIDI input device. */
    private final MidiInputDevice inputDevice;

    /** MIDI output device. */
    private final MidiOutputDevice outputDevice;

    /** Network. */
    private final CyNetwork network;


    /**
     * Create a new record task factory with the specified MIDI input and output devices.
     *
     * @param inputDevice MIDI input device, must not be null
     * @param outputDevice MIDI output device, must not be null
     * @param network network, must not be null
     */
    RecordTaskFactory(final MidiInputDevice inputDevice, final MidiOutputDevice outputDevice, final CyNetwork network)
    {
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
        this.inputDevice = inputDevice;
        this.outputDevice = outputDevice;
        this.network = network;
    }


    @Override
    public TaskIterator createTaskIterator()
    {
        RecordTask recordTask = new RecordTask(inputDevice, outputDevice, network);
        return new TaskIterator(recordTask);
    }
}