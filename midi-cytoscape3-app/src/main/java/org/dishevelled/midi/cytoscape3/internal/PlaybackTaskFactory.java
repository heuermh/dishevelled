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

import java.util.Random;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;

import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.vizmap.VisualStyle;

import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

import rwmidi.MidiOutputDevice;

/**
 * Playback task factory.
 *
 * @author  Michael Heuer
 */
final class PlaybackTaskFactory extends AbstractTaskFactory
{
    /** Node to start from. */
    private final CyNode start;

    /** Network. */
    private final CyNetwork network;

    /** Network view. */
    private final CyNetworkView networkView;

    /** Visual style. */
    private final VisualStyle style;

    /** MIDI output device. */
    private final MidiOutputDevice outputDevice;

    /** Source of randomness. */
    private final Random random;

    // channel, bpm, humanize, etc.


    /**
     * Create a new playback task factory with the specified MIDI output device.
     *
     * @param outputDevice MIDI output device, must not be null
     * @param random source of randomness, must not be null
     * @param start node to start from, must not be null
     * @param network network, must not be null
     * @param networkView network view, must not be null
     * @param style visual style, must not be null
     */
    PlaybackTaskFactory(final MidiOutputDevice outputDevice, final Random random, final CyNode start, final CyNetwork network, final CyNetworkView networkView, final VisualStyle style)
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
        if (networkView == null)
        {
            throw new IllegalArgumentException("networkView must not be null");
        }
        if (style == null)
        {
            throw new IllegalArgumentException("style must not be null");
        }
        this.outputDevice = outputDevice;
        this.random = random;
        this.start = start;
        this.network = network;
        this.networkView = networkView;
        this.style = style;
    }


    @Override
    public TaskIterator createTaskIterator()
    {
        PlaybackTask playbackTask = new PlaybackTask(outputDevice, random, start, network, networkView, style);
        return new TaskIterator(playbackTask);
    }
}