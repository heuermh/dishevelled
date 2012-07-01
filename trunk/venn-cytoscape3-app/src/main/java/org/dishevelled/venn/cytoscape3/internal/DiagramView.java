/*

    dsh-venn-cytoscape3-app  Cytoscape3 app for venn diagrams.
    Copyright (c) 2012 held jointly by the individual authors.

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
package org.dishevelled.venn.cytoscape3.internal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.FileDialog;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.awt.geom.Point2D;

import java.awt.image.RenderedImage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Iterator;
import java.util.Set;

import javax.imageio.ImageIO;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import static javax.swing.SwingUtilities.windowForComponent;

import org.apache.batik.dom.GenericDOMImplementation;

import org.apache.batik.svggen.SVGGraphics2D;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;

import org.dishevelled.identify.ContextMenuListener;

import org.dishevelled.piccolo.venn.AbstractVennNode;
import org.dishevelled.piccolo.venn.BinaryVennNode;
import org.dishevelled.piccolo.venn.TernaryVennNode;
import org.dishevelled.piccolo.venn.QuaternaryVennNode;
import org.dishevelled.piccolo.venn.VennNode;

import org.piccolo2d.PCamera;
import org.piccolo2d.PCanvas;
import org.piccolo2d.PNode;

import org.piccolo2d.nodes.PText;

import org.piccolo2d.event.PBasicInputEventHandler;
import org.piccolo2d.event.PInputEvent;
import org.piccolo2d.event.PInputEventFilter;
import org.piccolo2d.event.PMouseWheelZoomEventHandler;
import org.piccolo2d.event.PPanEventHandler;

import org.piccolo2d.util.PPaintContext;
import org.piccolo2d.util.PPickPath;

import org.w3c.dom.Document;
import org.w3c.dom.DOMImplementation;

/**
 * Diagram view.
 */
final class DiagramView
    extends JPanel
{
    /** SVG namespace. */
    private static final String SVG_NS = "http://www.w3.org/2000/svg";

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

    /** Export to SNG image action. */
    private final Action exportToSVG = new AbstractAction("Export to SVG...") // i18n
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                exportToSVG();
            }
        };

    /** Select all action. */
    private final Action selectAll = new AbstractAction("Select all") // i18n
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                selectAll();
            }
        };

    /** Clear selection action. */
    private final Action clearSelection = new AbstractAction("Clear selection") // i18n
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                clearSelection();
            }
        };

    /** Zoom in action. */
    private final Action zoomIn = new AbstractAction("Zoom in") // i18n
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                zoomIn();
            }
        };

    /** Zoom out action. */
    private final Action zoomOut = new AbstractAction("Zoom out") // i18n
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                zoomOut();
            }
        };

    /** Toggle set label should display sizes. */
    private final Action displaySizes = new AbstractAction("Display sizes in set labels") // i18n
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                displaySizes(((AbstractButton) event.getSource()).isSelected());
            }
        };

    /** Toggle display sizes for empty areas. */
    private final Action displaySizesForEmptyAreas = new AbstractAction("Display sizes for empty areas") // i18n
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                displaySizesForEmptyAreas(((AbstractButton) event.getSource()).isSelected());
            }
        };

    /** Area color. */
    private static final Color AREA_COLOR = new Color(0, 0, 0, 0);

    /** Area pressed paint. */
    private static final Paint AREA_PRESSED_PAINT = new Color(20, 20, 20, 80);

    /** Scale factor. */
    private static final double SCALE_FACTOR = 0.1d;

    /** Current mode. */
    private Mode mode = Mode.EDIT;

    /** Edit or pan modes, toggled by the space bar. */
    private enum Mode
    {
        /** Edit mode. */
        EDIT,

        /** Pan mode. */
        PAN
    };

    /** Application manager. */
    private final CyApplicationManager applicationManager;


    /**
     * Create a new diagram view.
     *
     * @param applicationManager application manager, must not be null
     */
    private DiagramView(final CyApplicationManager applicationManager)
    {
        if (applicationManager == null)
        {
            throw new IllegalArgumentException("applicationManager must not be null");
        }
        this.applicationManager = applicationManager;

        canvas = new PCanvas();
        canvas.setDefaultRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.setAnimatingRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.setInteractingRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.removeInputEventListener(canvas.getPanEventHandler());
        canvas.removeInputEventListener(canvas.getZoomEventHandler());
        canvas.addKeyListener(new ModeEventHandler());
        canvas.addInputEventListener(new PanEventHandler());
        PMouseWheelZoomEventHandler mouseWheelZoomEventHandler = new PMouseWheelZoomEventHandler();
        mouseWheelZoomEventHandler.zoomAboutViewCenter();
        canvas.addInputEventListener(mouseWheelZoomEventHandler);

        JPopupMenu contextMenu = new JPopupMenu();
        contextMenu.add(exportToPNG);
        contextMenu.add(exportToSVG);
        contextMenu.addSeparator();
        JCheckBoxMenuItem displaySizesMenuItem = new JCheckBoxMenuItem(displaySizes);
        displaySizesMenuItem.setSelected(true);
        contextMenu.add(displaySizesMenuItem);
        JCheckBoxMenuItem displaySizesForEmptyAreasMenuItem = new JCheckBoxMenuItem(displaySizesForEmptyAreas);
        displaySizesForEmptyAreasMenuItem.setSelected(true);
        contextMenu.add(displaySizesForEmptyAreasMenuItem);
        contextMenu.addSeparator();
        contextMenu.add(selectAll);
        contextMenu.add(clearSelection);
        contextMenu.addSeparator();
        contextMenu.add(zoomIn);
        contextMenu.add(zoomOut);
        canvas.addMouseListener(new ContextMenuListener(contextMenu));

        setLayout(new BorderLayout());
        add("Center", canvas);
    }

    /**
     * Create a new diagram view with the specified binary venn node.
     *
     * @param binaryVennNode binary venn node
     * @param applicationManager application manager, must not be null
     */
    DiagramView(final BinaryVennNode<CyNode> binaryVennNode, final CyApplicationManager applicationManager)
    {
        this(applicationManager);
        // todo:  use bounding rect provided by layout
        binaryVennNode.offset(92.0d, 124.0d);
        for (PNode node : binaryVennNode.nodes())
        {
            node.addInputEventListener(new ToolTipTextListener());
            node.addInputEventListener(new MousePressedListener());
        }
        canvas.getLayer().addChild(binaryVennNode);
    }

    /**
     * Create a new diagram view with the specified ternary venn node.
     *
     * @param ternaryVennNode ternary venn node
     * @param applicationManager application manager, must not be null
     */
    DiagramView(final TernaryVennNode<CyNode> ternaryVennNode, final CyApplicationManager applicationManager)
    {
        this(applicationManager);
        ternaryVennNode.offset(92.0d, 70.0d);
        for (PNode node : ternaryVennNode.nodes())
        {
            node.addInputEventListener(new ToolTipTextListener());
            node.addInputEventListener(new MousePressedListener());
        }
        canvas.getLayer().addChild(ternaryVennNode);
    }

    /**
     * Create a new diagram view with the specified quaternary venn node.
     *
     * @param quaternaryVennNode quaternary venn node
     * @param applicationManager application manager, must not be null
     */
    DiagramView(final QuaternaryVennNode<CyNode> quaternaryVennNode, final CyApplicationManager applicationManager)
    {
        this(applicationManager);
        quaternaryVennNode.offset(40.0d, 235.0d);
        for (PNode node : quaternaryVennNode.nodes())
        {
            node.addInputEventListener(new ToolTipTextListener());
            node.addInputEventListener(new MousePressedListener());
        }
        canvas.getLayer().addChild(quaternaryVennNode);
    }

    /**
     * Create a new diagram view with the specified venn node.
     *
     * @param vennNode venn node
     * @param applicationManager application manager, must not be null
     */
    DiagramView(final VennNode<CyNode> vennNode, final CyApplicationManager applicationManager)
    {
        this(applicationManager);
        vennNode.offset(100.0d, 100.0d);
        for (PNode node : vennNode.nodes())
        {
            node.addInputEventListener(new ToolTipTextListener());
            node.addInputEventListener(new MousePressedListener());
        }
        canvas.getLayer().addChild(vennNode);
    }


    /**
     * Return the label text for the picked node for the specified pick path, if any.
     *
     * @param path pick path
     * @return the label text for the picked node for the specified pick path, or <code>null</code>
     *    if no such label text exists
     */
    private String getLabelTextForPickedNode(final PPickPath path)
    {
        PNode pickedNode = path.getPickedNode();
        for (Iterator i = path.getNodeStackReference().iterator(); i.hasNext(); )
        {
            PNode node = (PNode) i.next();
            if (node instanceof AbstractVennNode)
            {
                AbstractVennNode abstractVennNode = (AbstractVennNode) node;
                String labelText = abstractVennNode.labelTextForNode(pickedNode);
                if (labelText != null)
                {
                    return labelText;
                }
            }
        }
        return null;
    }

    /**
     * Return the view for the picked node for the specified pick path, if any.
     *
     * @param path pick path
     * @return the view for the picked node for the specified pick path, or <code>null</code>
     *    if no such view exists
     */
    private Set<CyNode> getViewForPickedNode(final PPickPath path)
    {
        PNode pickedNode = path.getPickedNode();
        for (Iterator i = path.getNodeStackReference().iterator(); i.hasNext(); )
        {
            PNode node = (PNode) i.next();
            if (node instanceof AbstractVennNode)
            {
                AbstractVennNode<CyNode> abstractVennNode = (AbstractVennNode<CyNode>) node;
                Set<CyNode> view = abstractVennNode.viewForNode(pickedNode);
                if (view != null)
                {
                    return view;
                }
            }
        }
        return null;
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

    /**
     * Export to SVG.
     */
    private void exportToSVG()
    {
        // unsafe cast, if this view isn't rooted in a dialog
        FileDialog fileDialog = new FileDialog((Dialog) windowForComponent(this), "Export to SVG...", FileDialog.SAVE);
        fileDialog.setVisible(true);

        String directory = fileDialog.getDirectory();
        String fileName = fileDialog.getFile();

        if (fileName != null && directory != null)
        {
            FileWriter writer = null;
            try
            {
                writer = new FileWriter(new File(directory, fileName));
                DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation();
                Document document = domImpl.createDocument(SVG_NS, "svg", null);
                SVGGraphics2D svgGenerator = new SVGGraphics2D(document);
                canvas.paint(svgGenerator);
                svgGenerator.stream(writer, true);
            }
            catch (IOException e)
            {
                // ignore
            }
            finally
            {
                try
                {
                    writer.close();
                }
                catch (Exception e)
                {
                    // ignore
                }
            }
        }
    }

    /**
     * Display sizes.
     *
     * @param displaySizes true if labels should display sizes
     */
    private void displaySizes(final boolean displaySizes)
    {
        for (Iterator i = canvas.getLayer().getChildrenIterator(); i.hasNext(); )
        {
            PNode node = (PNode) i.next();
            if (node instanceof AbstractVennNode)
            {
                AbstractVennNode<CyNode> vennNode = (AbstractVennNode<CyNode>) node;
                vennNode.setDisplaySizes(displaySizes);
            }
        }
    }

    /**
     * Display sizes for empty areas.
     *
     * @param displaySizesForEmptyAreas true if labels should display sizes for empty areas
     */
    private void displaySizesForEmptyAreas(final boolean displaySizesForEmptyAreas)
    {
        for (Iterator i = canvas.getLayer().getChildrenIterator(); i.hasNext(); )
        {
            PNode node = (PNode) i.next();
            if (node instanceof AbstractVennNode)
            {
                AbstractVennNode<CyNode> vennNode = (AbstractVennNode<CyNode>) node;
                for (PText sizeLabel : vennNode.sizeLabels())
                {
                    if ("0".equals(sizeLabel.getText()))
                    {
                        // todo:  should also consider whether assocated areaNode is empty
                        //   maybe distinguish between empty areas and those that contain zero CyNodes
                        sizeLabel.setVisible(displaySizesForEmptyAreas);
                    }
                }
            }
        }
    }

    /**
     * Select all.
     */
    private void selectAll()
    {
        CyNetwork currentNetwork = applicationManager.getCurrentNetwork();
        for (Iterator i = canvas.getLayer().getChildrenIterator(); i.hasNext(); )
        {
            PNode node = (PNode) i.next();
            if (node instanceof BinaryVennNode)
            {
                BinaryVennNode<CyNode> binaryVennNode = (BinaryVennNode<CyNode>) node;                
                for (CyNode cyNode : currentNetwork.getNodeList())
                {
                    currentNetwork.getRow(cyNode).set(CyNetwork.SELECTED, binaryVennNode.getModel().union().contains(cyNode));
                }
            }
            else if (node instanceof TernaryVennNode)
            {
                TernaryVennNode<CyNode> ternaryVennNode = (TernaryVennNode<CyNode>) node;                
                for (CyNode cyNode : currentNetwork.getNodeList())
                {
                    currentNetwork.getRow(cyNode).set(CyNetwork.SELECTED, ternaryVennNode.getModel().union().contains(cyNode));
                }
            }
            else if (node instanceof QuaternaryVennNode)
            {
                QuaternaryVennNode<CyNode> quaternaryVennNode = (QuaternaryVennNode<CyNode>) node;                
                for (CyNode cyNode : currentNetwork.getNodeList())
                {
                    currentNetwork.getRow(cyNode).set(CyNetwork.SELECTED, quaternaryVennNode.getModel().union().contains(cyNode));
                }
            }
        }
        applicationManager.getCurrentNetworkView().updateView();
    }

    /**
     * Clear selection.
     */
    private void clearSelection()
    {
        CyNetwork currentNetwork = applicationManager.getCurrentNetwork();
        for (CyNode node : currentNetwork.getNodeList())
        {
            currentNetwork.getRow(node).set(CyNetwork.SELECTED, false);
        }
        applicationManager.getCurrentNetworkView().updateView();
    }

    /**
     * Zoom in.
     */
    private void zoomIn()
    {
        PCamera camera = canvas.getCamera();
        double scale = 1.0d + 4.0d * SCALE_FACTOR;
        Point2D center = camera.getBoundsReference().getCenter2D();
        camera.scaleViewAboutPoint(scale, center.getX(), center.getY());
    }

    /**
     * Zoom out.
     */
    private void zoomOut()
    {
        PCamera camera = canvas.getCamera();
        double scale = 1.0d - 2.0d * SCALE_FACTOR;
        Point2D center = camera.getBoundsReference().getCenter2D();
        camera.scaleViewAboutPoint(scale, center.getX(), center.getY());
    }

    /**
     * Tool tip text listener.
     */
    private class ToolTipTextListener
        extends PBasicInputEventHandler
    {

        /**
         * Create a new tool tip text listener.
         */
        ToolTipTextListener()
        {
            super();
            PInputEventFilter eventFilter = new PInputEventFilter();
            eventFilter.rejectAllEventTypes();
            eventFilter.setAcceptsMouseEntered(true);
            eventFilter.setAcceptsMouseExited(true);
            setEventFilter(eventFilter);
        }

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


        /**
         * Create a new mouse pressed listener.
         */
        MousePressedListener()
        {
            super();
            PInputEventFilter eventFilter = new PInputEventFilter();
            eventFilter.rejectAllEventTypes();
            eventFilter.setAcceptsMousePressed(true);
            eventFilter.setAcceptsMouseReleased(true);
            eventFilter.setAndMask(InputEvent.BUTTON1_MASK);
            setEventFilter(eventFilter);
        }


        /** {@inheritDoc} */
        public void mousePressed(final PInputEvent event)
        {
            if (Mode.PAN == mode)
            {
                return;
            }
            PNode pickedNode = event.getPickedNode();
            lastColor = (Color) pickedNode.getPaint();
            pickedNode.setPaint(AREA_PRESSED_PAINT);

            Set<CyNode> selection = getViewForPickedNode(event.getPath());
            if (selection != null)
            {
                CyNetwork currentNetwork = applicationManager.getCurrentNetwork();
                for (CyNode node : currentNetwork.getNodeList())
                {
                    currentNetwork.getRow(node).set(CyNetwork.SELECTED, selection.contains(node));
                }
                applicationManager.getCurrentNetworkView().updateView();
            }
        }

        /** {@inheritDoc} */
        public void mouseReleased(final PInputEvent event)
        {
            if (Mode.PAN == mode)
            {
                return;
            }
            PNode pickedNode = event.getPickedNode();
            pickedNode.animateToColor(AREA_COLOR, 250L);
        }
    }

    /**
     * Mode event listener.
     */
    private class ModeEventHandler
        extends KeyAdapter
    {

        /** {@inheritDoc} */
        public void keyPressed(final KeyEvent event)
        {
            if (KeyEvent.VK_SPACE == event.getKeyCode())
            {
                mode = Mode.PAN;
                ((PCanvas) event.getComponent()).setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
            }
        }

        /** {@inheritDoc} */
        public void keyReleased(final KeyEvent event)
        {
            if (KeyEvent.VK_SPACE == event.getKeyCode())
            {
                mode = Mode.EDIT;
                ((PCanvas) event.getComponent()).setCursor(Cursor.getDefaultCursor());
            }
        }
    }

    /**
     * Pan event handler.
     */
    private class PanEventHandler
        extends PPanEventHandler
    {

        /**
         * Create a new pan event handler.
         */
        PanEventHandler()
        {
            super();
            // @todo  cytoscape main network view uses middle-click + drag to pan
            setEventFilter(new PInputEventFilter(InputEvent.BUTTON1_MASK)
                {
                    /** {@inheritDoc} */
                    public boolean acceptsEvent(final PInputEvent event, final int type)
                    {
                        return super.acceptsEvent(event, type) && (Mode.PAN == mode);
                    }
                });
        }
    }
}