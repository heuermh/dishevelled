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
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FileDialog;
import java.awt.Image;
import java.awt.Paint;

import java.awt.event.ActionEvent;

import java.awt.image.RenderedImage;

import java.io.File;
import java.io.IOException;

import java.util.Iterator;
import java.util.Set;

import javax.imageio.ImageIO;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import static javax.swing.SwingUtilities.windowForComponent;

import cytoscape.CyNode;
import cytoscape.CyNetwork;
import cytoscape.Cytoscape;

import cytoscape.logger.CyLogger;

import org.dishevelled.piccolo.venn.BinaryVennNode;
import org.dishevelled.piccolo.venn.TernaryVennNode;
import org.dishevelled.piccolo.venn.QuaternaryVennNode;

import org.piccolo2d.PCanvas;
import org.piccolo2d.PNode;

import org.piccolo2d.nodes.PText;

import org.piccolo2d.event.PBasicInputEventHandler;
import org.piccolo2d.event.PInputEvent;

import org.piccolo2d.util.PPaintContext;
import org.piccolo2d.util.PPickPath;

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

    /** Logger. */
    private static final CyLogger LOGGER = CyLogger.getLogger(DiagramView.class);

    /** Area color. */
    private static final Color AREA_COLOR = new Color(0, 0, 0, 0);

    /** Area pressed paint. */
    private static final Paint AREA_PRESSED_PAINT = new Color(20, 20, 20, 80);


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
        binaryVennNode.getFirstOnly().addInputEventListener(new ToolTipTextListener());
        binaryVennNode.getFirstOnly().addInputEventListener(new MousePressedListener());
        binaryVennNode.getSecondOnly().addInputEventListener(new ToolTipTextListener());
        binaryVennNode.getSecondOnly().addInputEventListener(new MousePressedListener());
        binaryVennNode.getIntersection().addInputEventListener(new ToolTipTextListener());
        binaryVennNode.getIntersection().addInputEventListener(new MousePressedListener());

        binaryVennNode.getFirstOnlyLabel().addInputEventListener(new ToolTipTextListener());
        binaryVennNode.getFirstOnlyLabel().addInputEventListener(new MousePressedListener());
        binaryVennNode.getSecondOnlyLabel().addInputEventListener(new ToolTipTextListener());
        binaryVennNode.getSecondOnlyLabel().addInputEventListener(new MousePressedListener());
        binaryVennNode.getIntersectionLabel().addInputEventListener(new ToolTipTextListener());
        binaryVennNode.getIntersectionLabel().addInputEventListener(new MousePressedListener());
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
        ternaryVennNode.getFirstOnly().addInputEventListener(new ToolTipTextListener());
        ternaryVennNode.getFirstOnly().addInputEventListener(new MousePressedListener());
        ternaryVennNode.getSecondOnly().addInputEventListener(new ToolTipTextListener());
        ternaryVennNode.getSecondOnly().addInputEventListener(new MousePressedListener());
        ternaryVennNode.getThirdOnly().addInputEventListener(new ToolTipTextListener());
        ternaryVennNode.getThirdOnly().addInputEventListener(new MousePressedListener());
        ternaryVennNode.getFirstSecond().addInputEventListener(new ToolTipTextListener());
        ternaryVennNode.getFirstSecond().addInputEventListener(new MousePressedListener());
        ternaryVennNode.getFirstThird().addInputEventListener(new ToolTipTextListener());
        ternaryVennNode.getFirstThird().addInputEventListener(new MousePressedListener());
        ternaryVennNode.getSecondThird().addInputEventListener(new ToolTipTextListener());
        ternaryVennNode.getSecondThird().addInputEventListener(new MousePressedListener());
        ternaryVennNode.getIntersection().addInputEventListener(new ToolTipTextListener());
        ternaryVennNode.getIntersection().addInputEventListener(new MousePressedListener());

        ternaryVennNode.getFirstOnlyLabel().addInputEventListener(new ToolTipTextListener());
        ternaryVennNode.getFirstOnlyLabel().addInputEventListener(new MousePressedListener());
        ternaryVennNode.getSecondOnlyLabel().addInputEventListener(new ToolTipTextListener());
        ternaryVennNode.getSecondOnlyLabel().addInputEventListener(new MousePressedListener());
        ternaryVennNode.getThirdOnlyLabel().addInputEventListener(new ToolTipTextListener());
        ternaryVennNode.getThirdOnlyLabel().addInputEventListener(new MousePressedListener());
        ternaryVennNode.getFirstSecondLabel().addInputEventListener(new ToolTipTextListener());
        ternaryVennNode.getFirstSecondLabel().addInputEventListener(new MousePressedListener());
        ternaryVennNode.getFirstThirdLabel().addInputEventListener(new ToolTipTextListener());
        ternaryVennNode.getFirstThirdLabel().addInputEventListener(new MousePressedListener());
        ternaryVennNode.getSecondThirdLabel().addInputEventListener(new ToolTipTextListener());
        ternaryVennNode.getSecondThirdLabel().addInputEventListener(new MousePressedListener());
        ternaryVennNode.getIntersectionLabel().addInputEventListener(new ToolTipTextListener());
        ternaryVennNode.getIntersectionLabel().addInputEventListener(new MousePressedListener());
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
        quaternaryVennNode.getFirstOnly().addInputEventListener(new ToolTipTextListener());
        quaternaryVennNode.getFirstOnly().addInputEventListener(new MousePressedListener());
        quaternaryVennNode.getSecondOnly().addInputEventListener(new ToolTipTextListener());
        quaternaryVennNode.getSecondOnly().addInputEventListener(new MousePressedListener());
        quaternaryVennNode.getThirdOnly().addInputEventListener(new ToolTipTextListener());
        quaternaryVennNode.getThirdOnly().addInputEventListener(new MousePressedListener());
        quaternaryVennNode.getFourthOnly().addInputEventListener(new ToolTipTextListener());
        quaternaryVennNode.getFourthOnly().addInputEventListener(new MousePressedListener());
        quaternaryVennNode.getFirstSecond().addInputEventListener(new ToolTipTextListener());
        quaternaryVennNode.getFirstSecond().addInputEventListener(new MousePressedListener());
        quaternaryVennNode.getFirstThird().addInputEventListener(new ToolTipTextListener());
        quaternaryVennNode.getFirstThird().addInputEventListener(new MousePressedListener());
        quaternaryVennNode.getSecondThird().addInputEventListener(new ToolTipTextListener());
        quaternaryVennNode.getSecondThird().addInputEventListener(new MousePressedListener());
        quaternaryVennNode.getFirstFourth().addInputEventListener(new ToolTipTextListener());
        quaternaryVennNode.getFirstFourth().addInputEventListener(new MousePressedListener());
        quaternaryVennNode.getSecondFourth().addInputEventListener(new ToolTipTextListener());
        quaternaryVennNode.getSecondFourth().addInputEventListener(new MousePressedListener());
        quaternaryVennNode.getThirdFourth().addInputEventListener(new ToolTipTextListener());
        quaternaryVennNode.getThirdFourth().addInputEventListener(new MousePressedListener());
        quaternaryVennNode.getFirstSecondThird().addInputEventListener(new ToolTipTextListener());
        quaternaryVennNode.getFirstSecondThird().addInputEventListener(new MousePressedListener());
        quaternaryVennNode.getFirstSecondFourth().addInputEventListener(new ToolTipTextListener());
        quaternaryVennNode.getFirstSecondFourth().addInputEventListener(new MousePressedListener());
        quaternaryVennNode.getFirstThirdFourth().addInputEventListener(new ToolTipTextListener());
        quaternaryVennNode.getFirstThirdFourth().addInputEventListener(new MousePressedListener());
        quaternaryVennNode.getSecondThirdFourth().addInputEventListener(new ToolTipTextListener());
        quaternaryVennNode.getSecondThirdFourth().addInputEventListener(new MousePressedListener());
        quaternaryVennNode.getIntersection().addInputEventListener(new ToolTipTextListener());
        quaternaryVennNode.getIntersection().addInputEventListener(new MousePressedListener());

        quaternaryVennNode.getFirstOnlyLabel().addInputEventListener(new ToolTipTextListener());
        quaternaryVennNode.getFirstOnlyLabel().addInputEventListener(new MousePressedListener());
        quaternaryVennNode.getSecondOnlyLabel().addInputEventListener(new ToolTipTextListener());
        quaternaryVennNode.getSecondOnlyLabel().addInputEventListener(new MousePressedListener());
        quaternaryVennNode.getThirdOnlyLabel().addInputEventListener(new ToolTipTextListener());
        quaternaryVennNode.getThirdOnlyLabel().addInputEventListener(new MousePressedListener());
        quaternaryVennNode.getFourthOnlyLabel().addInputEventListener(new ToolTipTextListener());
        quaternaryVennNode.getFourthOnlyLabel().addInputEventListener(new MousePressedListener());
        quaternaryVennNode.getFirstSecondLabel().addInputEventListener(new ToolTipTextListener());
        quaternaryVennNode.getFirstSecondLabel().addInputEventListener(new MousePressedListener());
        quaternaryVennNode.getFirstThirdLabel().addInputEventListener(new ToolTipTextListener());
        quaternaryVennNode.getFirstThirdLabel().addInputEventListener(new MousePressedListener());
        quaternaryVennNode.getSecondThirdLabel().addInputEventListener(new ToolTipTextListener());
        quaternaryVennNode.getSecondThirdLabel().addInputEventListener(new MousePressedListener());
        quaternaryVennNode.getFirstFourthLabel().addInputEventListener(new ToolTipTextListener());
        quaternaryVennNode.getFirstFourthLabel().addInputEventListener(new MousePressedListener());
        quaternaryVennNode.getSecondFourthLabel().addInputEventListener(new ToolTipTextListener());
        quaternaryVennNode.getSecondFourthLabel().addInputEventListener(new MousePressedListener());
        quaternaryVennNode.getThirdFourthLabel().addInputEventListener(new ToolTipTextListener());
        quaternaryVennNode.getThirdFourthLabel().addInputEventListener(new MousePressedListener());
        quaternaryVennNode.getFirstSecondThirdLabel().addInputEventListener(new ToolTipTextListener());
        quaternaryVennNode.getFirstSecondThirdLabel().addInputEventListener(new MousePressedListener());
        quaternaryVennNode.getFirstSecondFourthLabel().addInputEventListener(new ToolTipTextListener());
        quaternaryVennNode.getFirstSecondFourthLabel().addInputEventListener(new MousePressedListener());
        quaternaryVennNode.getFirstThirdFourthLabel().addInputEventListener(new ToolTipTextListener());
        quaternaryVennNode.getFirstThirdFourthLabel().addInputEventListener(new MousePressedListener());
        quaternaryVennNode.getSecondThirdFourthLabel().addInputEventListener(new ToolTipTextListener());
        quaternaryVennNode.getSecondThirdFourthLabel().addInputEventListener(new MousePressedListener());
        quaternaryVennNode.getIntersectionLabel().addInputEventListener(new ToolTipTextListener());
        quaternaryVennNode.getIntersectionLabel().addInputEventListener(new MousePressedListener());
        canvas.getLayer().addChild(quaternaryVennNode);
    }


    // @todo move this logic internal to venn node(s)
    private String getLabelTextForPickedNode(final PPickPath path)
    {
        PNode pickedNode = path.getPickedNode();
        if (pickedNode instanceof PText)
        {
            LOGGER.info("labelText pickedNode was " + pickedNode + ", next picked node is " + path.nextPickedNode());
            pickedNode = path.nextPickedNode();
        }
        for (Iterator i = path.getNodeStackReference().iterator(); i.hasNext(); )
        {
            PNode node = (PNode) i.next();
            if (node instanceof BinaryVennNode)
            {
                BinaryVennNode binaryVennNode = (BinaryVennNode) node;
                if (binaryVennNode.getFirstOnly().equals(pickedNode))
                {
                    return binaryVennNode.getFirstOnlyLabelText();
                }
                else if (binaryVennNode.getSecondOnly().equals(pickedNode))
                {
                    return binaryVennNode.getSecondOnlyLabelText();
                }
                else if (binaryVennNode.getIntersection().equals(pickedNode))
                {
                    return binaryVennNode.getIntersectionLabelText();
                }
            }
            else if (node instanceof TernaryVennNode)
            {
                TernaryVennNode ternaryVennNode = (TernaryVennNode) node;
                if (ternaryVennNode.getFirstOnly().equals(pickedNode))
                {
                    return ternaryVennNode.getFirstOnlyLabelText();
                }
                else if (ternaryVennNode.getSecondOnly().equals(pickedNode))
                {
                    return ternaryVennNode.getSecondOnlyLabelText();
                }
                else if (ternaryVennNode.getThirdOnly().equals(pickedNode))
                {
                    return ternaryVennNode.getThirdOnlyLabelText();
                }
                else if (ternaryVennNode.getFirstSecond().equals(pickedNode))
                {
                    return ternaryVennNode.getFirstSecondLabelText();
                }
                else if (ternaryVennNode.getSecondThird().equals(pickedNode))
                {
                    return ternaryVennNode.getSecondThirdLabelText();
                }
                else if (ternaryVennNode.getIntersection().equals(pickedNode))
                {
                    return ternaryVennNode.getIntersectionLabelText();
                }
            }
            else if (node instanceof QuaternaryVennNode)
            {
                QuaternaryVennNode quaternaryVennNode = (QuaternaryVennNode) node;
                if (quaternaryVennNode.getFirstOnly().equals(pickedNode))
                {
                    return quaternaryVennNode.getFirstOnlyLabelText();
                }
                else if (quaternaryVennNode.getSecondOnly().equals(pickedNode))
                {
                    return quaternaryVennNode.getSecondOnlyLabelText();
                }
                else if (quaternaryVennNode.getThirdOnly().equals(pickedNode))
                {
                    return quaternaryVennNode.getThirdOnlyLabelText();
                }
                else if (quaternaryVennNode.getFourthOnly().equals(pickedNode))
                {
                    return quaternaryVennNode.getFourthOnlyLabelText();
                }
                else if (quaternaryVennNode.getFirstSecond().equals(pickedNode))
                {
                    return quaternaryVennNode.getFirstSecondLabelText();
                }
                else if (quaternaryVennNode.getFirstThird().equals(pickedNode))
                {
                    return quaternaryVennNode.getFirstThirdLabelText();
                }
                else if (quaternaryVennNode.getSecondThird().equals(pickedNode))
                {
                    return quaternaryVennNode.getSecondThirdLabelText();
                }
                else if (quaternaryVennNode.getFirstFourth().equals(pickedNode))
                {
                    return quaternaryVennNode.getFirstFourthLabelText();
                }
                else if (quaternaryVennNode.getSecondFourth().equals(pickedNode))
                {
                    return quaternaryVennNode.getSecondFourthLabelText();
                }
                else if (quaternaryVennNode.getThirdFourth().equals(pickedNode))
                {
                    return quaternaryVennNode.getThirdFourthLabelText();
                }
                else if (quaternaryVennNode.getFirstSecondThird().equals(pickedNode))
                {
                    return quaternaryVennNode.getFirstSecondThirdLabelText();
                }
                else if (quaternaryVennNode.getFirstSecondFourth().equals(pickedNode))
                {
                    return quaternaryVennNode.getFirstSecondFourthLabelText();
                }
                else if (quaternaryVennNode.getFirstThirdFourth().equals(pickedNode))
                {
                    return quaternaryVennNode.getFirstThirdFourthLabelText();
                }
                else if (quaternaryVennNode.getSecondThirdFourth().equals(pickedNode))
                {
                    return quaternaryVennNode.getSecondThirdFourthLabelText();
                }
                else if (quaternaryVennNode.getIntersection().equals(pickedNode))
                {
                    return quaternaryVennNode.getIntersectionLabelText();
                }
            }
        }
        return null;
    }

    // @todo move this logic internal to venn node(s)
    private Set<CyNode> getViewForPickedNode(final PPickPath path)
    {
        PNode pickedNode = path.getPickedNode();
        if (pickedNode instanceof PText)
        {
            LOGGER.info("view pickedNode was " + pickedNode + ", next picked node is " + path.nextPickedNode());
            pickedNode = path.nextPickedNode();
        }
        for (Iterator i = path.getNodeStackReference().iterator(); i.hasNext(); )
        {
            PNode node = (PNode) i.next();
            if (node instanceof BinaryVennNode)
            {
                BinaryVennNode binaryVennNode = (BinaryVennNode) node;
                if (binaryVennNode.getFirstOnly().equals(pickedNode))
                {
                    return binaryVennNode.getModel().firstOnly();
                }
                else if (binaryVennNode.getSecondOnly().equals(pickedNode))
                {
                    return binaryVennNode.getModel().secondOnly();
                }
                else if (binaryVennNode.getIntersection().equals(pickedNode))
                {
                    return binaryVennNode.getModel().intersection();
                }
            }
            else if (node instanceof TernaryVennNode)
            {
                TernaryVennNode ternaryVennNode = (TernaryVennNode) node;
                if (ternaryVennNode.getFirstOnly().equals(pickedNode))
                {
                    return ternaryVennNode.getModel().firstOnly();
                }
                else if (ternaryVennNode.getSecondOnly().equals(pickedNode))
                {
                    return ternaryVennNode.getModel().secondOnly();
                }
                else if (ternaryVennNode.getThirdOnly().equals(pickedNode))
                {
                    return ternaryVennNode.getModel().thirdOnly();
                }
                else if (ternaryVennNode.getFirstSecond().equals(pickedNode))
                {
                    return ternaryVennNode.getModel().firstSecond();
                }
                else if (ternaryVennNode.getSecondThird().equals(pickedNode))
                {
                    return ternaryVennNode.getModel().secondThird();
                }
                else if (ternaryVennNode.getIntersection().equals(pickedNode))
                {
                    return ternaryVennNode.getModel().intersection();
                }
            }
            else if (node instanceof QuaternaryVennNode)
            {
                QuaternaryVennNode quaternaryVennNode = (QuaternaryVennNode) node;
                if (quaternaryVennNode.getFirstOnly().equals(pickedNode))
                {
                    return quaternaryVennNode.getModel().firstOnly();
                }
                else if (quaternaryVennNode.getSecondOnly().equals(pickedNode))
                {
                    return quaternaryVennNode.getModel().secondOnly();
                }
                else if (quaternaryVennNode.getThirdOnly().equals(pickedNode))
                {
                    return quaternaryVennNode.getModel().thirdOnly();
                }
                else if (quaternaryVennNode.getFourthOnly().equals(pickedNode))
                {
                    return quaternaryVennNode.getModel().fourthOnly();
                }
                else if (quaternaryVennNode.getFirstSecond().equals(pickedNode))
                {
                    return quaternaryVennNode.getModel().firstSecond();
                }
                else if (quaternaryVennNode.getFirstThird().equals(pickedNode))
                {
                    return quaternaryVennNode.getModel().firstThird();
                }
                else if (quaternaryVennNode.getSecondThird().equals(pickedNode))
                {
                    return quaternaryVennNode.getModel().secondThird();
                }
                else if (quaternaryVennNode.getFirstFourth().equals(pickedNode))
                {
                    return quaternaryVennNode.getModel().firstFourth();
                }
                else if (quaternaryVennNode.getSecondFourth().equals(pickedNode))
                {
                    return quaternaryVennNode.getModel().secondFourth();
                }
                else if (quaternaryVennNode.getThirdFourth().equals(pickedNode))
                {
                    return quaternaryVennNode.getModel().thirdFourth();
                }
                else if (quaternaryVennNode.getFirstSecondThird().equals(pickedNode))
                {
                    return quaternaryVennNode.getModel().firstSecondThird();
                }
                else if (quaternaryVennNode.getFirstSecondFourth().equals(pickedNode))
                {
                    return quaternaryVennNode.getModel().firstSecondFourth();
                }
                else if (quaternaryVennNode.getFirstThirdFourth().equals(pickedNode))
                {
                    return quaternaryVennNode.getModel().firstThirdFourth();
                }
                else if (quaternaryVennNode.getSecondThirdFourth().equals(pickedNode))
                {
                    return quaternaryVennNode.getModel().secondThirdFourth();
                }
                else if (quaternaryVennNode.getIntersection().equals(pickedNode))
                {
                    return quaternaryVennNode.getModel().intersection();
                }
           }
        }
        return null;
    }

    /**
     * Tool tip text listener.
     */
    private class ToolTipTextListener
        extends PBasicInputEventHandler
    {

        /** {@inheritDoc} */
        public void mouseEntered(final PInputEvent event)
        {
            PCanvas canvas = (PCanvas) event.getComponent();
            canvas.setToolTipText(getLabelTextForPickedNode(event.getPath()));
        }

        /** {@inheritDoc} */
        public void mouseExited(final PInputEvent event)
        {
            PCanvas canvas = (PCanvas) event.getComponent();
            canvas.setToolTipText(null);
        }
    }


    /**
     * Mouse pressed listener.
     */
    private class MousePressedListener
        extends PBasicInputEventHandler
    {
        /** Last color. */
        private Color lastColor;


        /** {@inheritDoc} */
        public void mousePressed(final PInputEvent event)
        {
            PNode pickedNode = event.getPickedNode();
            if (pickedNode instanceof PText)
            {
                LOGGER.info("color pressed pickedNode was " + pickedNode + ", next picked node is " + event.getPath().nextPickedNode());
                pickedNode = event.getPath().nextPickedNode();
            }
            lastColor = (Color) pickedNode.getPaint();
            pickedNode.setPaint(AREA_PRESSED_PAINT);

            Set<CyNode> selection = getViewForPickedNode(event.getPath());
            if (selection != null)
            {
                CyNetwork currentNetwork = Cytoscape.getCurrentNetwork();
                currentNetwork.unselectAllNodes();
                currentNetwork.setSelectedNodeState(selection, true);
                Cytoscape.getCurrentNetworkView().updateView();
            }
        }

        /** {@inheritDoc} */
        public void mouseReleased(final PInputEvent event)
        {
            PNode pickedNode = event.getPickedNode();
            if (pickedNode instanceof PText)
            {
                LOGGER.info("color released pickedNode was " + pickedNode + ", next picked node is " + event.getPath().nextPickedNode());
                pickedNode = event.getPath().nextPickedNode();
            }
            pickedNode.animateToColor(AREA_COLOR, 250L);
        }
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