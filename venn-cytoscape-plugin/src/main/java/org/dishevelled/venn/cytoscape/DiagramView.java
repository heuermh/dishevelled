/*

    dsh-venn-cytoscape-plugin  Cytoscape plugin for venn diagrams.
    Copyright (c) 2010 held jointly by the individual authors.

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
package org.dishevelled.venn.cytoscape;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FileDialog;
import java.awt.Image;

import java.awt.event.ActionEvent;

import java.awt.image.RenderedImage;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import static javax.swing.SwingUtilities.windowForComponent;

import cytoscape.CyNode;

import org.dishevelled.piccolo.venn.BinaryVennNode;
import org.dishevelled.piccolo.venn.TernaryVennNode;
import org.dishevelled.piccolo.venn.QuaternaryVennNode;

import org.piccolo2d.PCanvas;

import org.piccolo2d.util.PPaintContext;

/**
 * Diagram view.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
final class DiagramView
    extends JPanel
{
    /** Canvas. */
    private final PCanvas canvas;

    /** Export to PNG image action. */
    private final Action exportToPNG = new AbstractAction("Export to PNG...") // i18n
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                exportToPNG();
            }
        };


    /**
     * Create a new diagram view.
     */
    private DiagramView()
    {
        canvas = new PCanvas();
        canvas.setDefaultRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.setAnimatingRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.setInteractingRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.removeInputEventListener(canvas.getPanEventHandler());
        canvas.removeInputEventListener(canvas.getZoomEventHandler());

        JPopupMenu contextMenu = new JPopupMenu();
        contextMenu.add(exportToPNG);
        canvas.addMouseListener(new ContextMenuListener(contextMenu));

        setLayout(new BorderLayout());
        add("Center", canvas);
    }

    /**
     * Create a new diagram view with the specified binary venn node.
     *
     * @param binaryVennNode binary venn node
     */
    DiagramView(final BinaryVennNode<CyNode> binaryVennNode)
    {
        this();
        binaryVennNode.offset(92.0d, 124.0d);
        canvas.getLayer().addChild(binaryVennNode);
    }

    /**
     * Create a new diagram view with the specified ternary venn node.
     *
     * @param ternaryVennNode ternary venn node
     */
    DiagramView(final TernaryVennNode<CyNode> ternaryVennNode)
    {
        this();
        ternaryVennNode.offset(92.0d, 70.0d);
        canvas.getLayer().addChild(ternaryVennNode);
    }

    /**
     * Create a new diagram view with the specified quaternary venn node.
     *
     * @param quaternaryVennNode quaternary venn node
     */
    DiagramView(final QuaternaryVennNode<CyNode> quaternaryVennNode)
    {
        this();
        quaternaryVennNode.offset(40.0d, 235.0d);
        canvas.getLayer().addChild(quaternaryVennNode);
    }


    /**
     * Export to PNG.
     */
    private void exportToPNG()
    {
        Image image = canvas.getLayer().toImage();
        // unsafe cast, if this view isn't rooted in a dialog
        FileDialog fileDialog = new FileDialog((Dialog) windowForComponent(this), "Export to PNG...", FileDialog.SAVE);
        fileDialog.setVisible(true);

        String directory = fileDialog.getDirectory();
        String fileName = fileDialog.getFile();

        if (fileName != null && directory != null)
        {
            try
            {
                ImageIO.write((RenderedImage) image, "png", new File(directory, fileName));
            }
            catch (IOException e)
            {
                // ignore
            }
        }
    }
}