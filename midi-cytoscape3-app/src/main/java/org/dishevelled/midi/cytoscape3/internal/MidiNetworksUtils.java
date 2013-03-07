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

import java.awt.Toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JRootPane;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.CyTableUtil;

/**
 * Utility methods.
 *
 * @author  Michael Heuer
 */
final class MidiNetworksUtils
{

    /**
     * Private no-arg constructor.
     */
    private MidiNetworksUtils()
    {
        // empty
    }


    /**
     * Install a close action binding to <code>Ctrl-C</code>/<code>Command-C</code> for the specified dialog.
     *
     * @param dialog dialog, must not be null
     */
    static void installCloseKeyBinding(final JDialog dialog)
    {
        Action close = new AbstractAction()
            {
                @Override
                public void actionPerformed(final ActionEvent event)
                {
                    dialog.dispatchEvent(new WindowEvent(dialog, WindowEvent.WINDOW_CLOSING));
                }
            };
        int menuKeyMask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
        KeyStroke closeStroke = KeyStroke.getKeyStroke(KeyEvent.VK_W, menuKeyMask);
        JRootPane rootPane = dialog.getRootPane();
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(closeStroke, "close");
        rootPane.getActionMap().put("close", close);
    }

    /**
     * Return the duration of the specified edge.
     *
     * @param edge edge
     * @param network network
     * @return the duration of the specified edge
     */
    static Long durationOf(final CyEdge edge, final CyNetwork network)
    {
        CyTable edgeTable = network.getDefaultEdgeTable();
        CyRow edgeRow = edgeTable.getRow(edge.getSUID());
        return edgeRow.get("duration", Long.class);
    }

    /**
     * Return the name of the specified node.
     *
     * @param node node
     * @param network network
     * @return the name of the specified node
     */
    static String nameOf(final CyNode node, final CyNetwork network)
    {
        CyTable nodeTable = network.getDefaultNodeTable();
        CyRow nodeRow = nodeTable.getRow(node.getSUID());
        String name = nodeRow.get(CyNetwork.NAME, String.class);
        return (name == null) ? node.toString() : name;
    }

    /**
     * Return the note of the specified node.
     *
     * @param node node
     * @param network network
     * @return the note of the specified node
     */
    static Integer noteOf(final CyNode node, final CyNetwork network)
    {
        CyTable nodeTable = network.getDefaultNodeTable();
        CyRow nodeRow = nodeTable.getRow(node.getSUID());
        return nodeRow.get("note", Integer.class);
    }

    /**
     * Return the type of the specified edge.
     *
     * @param edge edge
     * @param network network
     * @return the type of the specified edge
     */
    static String typeOf(final CyEdge edge, final CyNetwork network)
    {
        CyTable edgeTable = network.getDefaultEdgeTable();
        CyRow edgeRow = edgeTable.getRow(edge.getSUID());
        return edgeRow.get("type", String.class);
    }

    /**
     * Return the type of the specified node.
     *
     * @param node node
     * @param network network
     * @return the type of the specified node
     */
    static String typeOf(final CyNode node, final CyNetwork network)
    {
        CyTable nodeTable = network.getDefaultNodeTable();
        CyRow nodeRow = nodeTable.getRow(node.getSUID());
        return nodeRow.get("type", String.class);
    }

    /**
     * Return the velocity of the specified node.
     *
     * @param node node
     * @param network network
     * @return the velocity of the specified node
     */
    static Integer velocityOf(final CyNode node, final CyNetwork network)
    {
        CyTable nodeTable = network.getDefaultNodeTable();
        CyRow nodeRow = nodeTable.getRow(node.getSUID());
        return nodeRow.get("velocity", Integer.class);
    }

    /**
     * Return the weight of the specified edge.
     *
     * @param edge edge
     * @param network network
     * @return the weight of the specified edge
     */
    static Double weightOf(final CyEdge edge, final CyNetwork network)
    {
        CyTable edgeTable = network.getDefaultEdgeTable();
        CyRow edgeRow = edgeTable.getRow(edge.getSUID());
        return edgeRow.get("weight", Double.class);
    }

    /**
     * Return the out edges for the specified node in the specified network.
     *
     * @param node node
     * @param network network
     * @return the out edges for the specified node in the specified network
     */
    static Iterable<CyEdge> outEdges(final CyNode node, final CyNetwork network)
    {
        return network.getAdjacentEdgeIterable(node, CyEdge.Type.OUTGOING);
    }

    /**
     * Return the selected node in the specified network or null if none or more than one are selected.
     *
     * @param network network
     * @return the selected node in the specified network or null if none or more than one are selected
     */
    static CyNode selectedNode(final CyNetwork network)
    {
        if (network == null)
        {
            return null;
        }
        List<CyNode> selectedNodes = CyTableUtil.getNodesInState(network, "selected", true);
        return (selectedNodes.size() == 1) ? selectedNodes.get(0) : null;
    }

    /**
     * Write the default vizmap styles to a temporary file.
     *
     * @return the default vizmap styles written to a temporary file
     */
    static File writeVizmapToTempFile()
    {
        File tmp = null;
        BufferedReader reader = null;
        PrintWriter writer = null;
        try
        {
            tmp = File.createTempFile("midi-networks-vizmap", "xml");
            reader = new BufferedReader(new InputStreamReader(MidiNetworksUtils.class.getResourceAsStream("midi-networks-vizmap.xml")));
            writer = new PrintWriter(new FileWriter(tmp), true);

            while (reader.ready())
            {
                String line = reader.readLine();
                if (line == null)
                {
                    break;
                }
                writer.write(line);
            }
            return tmp;
        }
        catch (IOException e)
        {
            // ignore
        }
        finally
        {
            try
            {
                reader.close();
            }
            catch (Exception e)
            {
                // ignore
            }
            try
            {
                writer.close();
            }
            catch (Exception e)
            {
                // ignore
            }
        }
        return tmp;
    }
}