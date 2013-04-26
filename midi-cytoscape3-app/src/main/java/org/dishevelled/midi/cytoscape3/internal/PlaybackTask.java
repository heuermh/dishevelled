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

import java.awt.Color;
import java.awt.Paint;
import java.util.Random;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyEdge;

import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.vizmap.VisualStyle;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import org.dishevelled.weighted.WeightedMap;
import org.dishevelled.weighted.WeightedMaps;

import rwmidi.MidiOutput;
import rwmidi.MidiOutputDevice;

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

    /** Network view. */
    private final CyNetworkView networkView;

    /** Visual style. */
    private final VisualStyle style;

    /** MIDI output. */
    private final MidiOutput output;

    /** Source of randomness. */
    private final Random random;

    /** Decorate paint. */
    private final Paint decorate;

    /** True if this playback task locked the current node view. */
    private boolean lockedNodeView = false;

    /** True if this playback task locked the current edge view. */
    private boolean lockedEdgeView = false;

    // channel, bpm, humanize, etc.


    /**
     * Create a new playback task with the specified MIDI output device.
     *
     * @param outputDevice MIDI output device, must not be null
     * @param random source of randomness, must not be null
     * @param start node to start from, must not be null
     * @param network network, must not be null
     * @param networkView network view, must not be null
     * @param style visual style, must not be null
     */
    PlaybackTask(final MidiOutputDevice outputDevice, final Random random, final CyNode start, final CyNetwork network, final CyNetworkView networkView, final VisualStyle style)
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
        this.output = outputDevice.createOutput();
        this.random = random;
        this.start = start;
        this.network = network;
        this.networkView = networkView;
        this.style = style;

        this.decorate = new Color(random.nextInt(200) + 55, random.nextInt(200) + 55, random.nextInt(200) + 55);
    }


    @Override
    public void run(final TaskMonitor taskMonitor)
    {
        // todo:  use type note velocity instead of name?
        taskMonitor.setTitle("Playback from " + nameOf(start, network));
        taskMonitor.setProgress(0.0d);

        CyNode current = start;
        while (!cancelled)
        {
            String nodeType = typeOf(current, network);
            int note = noteOf(current, network);
            int velocity = velocityOf(current, network);
            decorateNode(current);

            if ("noteOn".equals(nodeType))
            {
                // send note on midi
                output.sendNoteOn(1, note, velocity);
            }
            else if ("noteOff".equals(nodeType))
            {
                // send note off midi
                output.sendNoteOff(1, note, velocity);
            }

            WeightedMap<CyEdge> outEdges = WeightedMaps.createWeightedMap();
            for (CyEdge edge : outEdges(current, network))
            {
                outEdges.put(edge, weightOf(edge, network));
            }

            // break if no outgoing edges
            if (outEdges.isEmpty())
            {
                break;
            }

            // wait on sampled edge
            CyEdge edge = outEdges.sample();
            String edgeType = typeOf(edge, network);
            long duration = durationOf(edge, network);
            taskMonitor.setStatusMessage("Sending MIDI message " + nodeType + " " + note + " " + velocity + "; waiting for " + edgeType + " "  + duration + " ms");
            undecorateNode(current);
            decorateEdge(edge);

            try
            {
                Thread.sleep(duration);
            }
            catch (InterruptedException e)
            {
                // ok
            }
            current = edge.getTarget();
            undecorateEdge(edge);
        }
    }

    // todo:  doesn't work apparently

    private void decorateNode(final CyNode node)
    {
        View<CyNode> nodeView = networkView.getNodeView(node);
        if (!nodeView.isValueLocked(BasicVisualLexicon.NODE_FILL_COLOR))
        {
            lockedNodeView = true;
            nodeView.setLockedValue(BasicVisualLexicon.NODE_FILL_COLOR, decorate);
            style.apply(networkView);
            networkView.updateView();
        }
    }

    private void undecorateNode(final CyNode node)
    {
        View<CyNode> nodeView = networkView.getNodeView(node);
        if (lockedNodeView && nodeView.isValueLocked(BasicVisualLexicon.NODE_FILL_COLOR))
        {
            lockedNodeView = false;
            nodeView.clearValueLock(BasicVisualLexicon.NODE_FILL_COLOR);
            style.apply(networkView);
            networkView.updateView();
        }
    }

    private void decorateEdge(final CyEdge edge)
    {
        View<CyEdge> edgeView = networkView.getEdgeView(edge);
        if (!edgeView.isValueLocked(BasicVisualLexicon.EDGE_STROKE_UNSELECTED_PAINT))
        {
            lockedEdgeView = true;
            edgeView.setLockedValue(BasicVisualLexicon.EDGE_STROKE_UNSELECTED_PAINT, decorate);
            style.apply(networkView);
            networkView.updateView();
        }
    }

    private void undecorateEdge(final CyEdge edge)
    {
        View<CyEdge> edgeView = networkView.getEdgeView(edge);
        if (lockedEdgeView && edgeView.isValueLocked(BasicVisualLexicon.EDGE_STROKE_UNSELECTED_PAINT))
        {
            lockedEdgeView = false;
            edgeView.clearValueLock(BasicVisualLexicon.EDGE_STROKE_UNSELECTED_PAINT);
            style.apply(networkView);
            networkView.updateView();
        }
    }
}