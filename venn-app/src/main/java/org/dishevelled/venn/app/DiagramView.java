/*

    dsh-venn-app  App for venn and euler diagrams.
    Copyright (c) 2013-2019 held jointly by the individual authors.

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
package org.dishevelled.venn.app;

import static org.apache.commons.lang3.SystemUtils.IS_OS_MAC;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Toolkit;

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
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javax.imageio.ImageIO;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import static javax.swing.SwingUtilities.windowForComponent;

import org.apache.batik.dom.GenericDOMImplementation;

import org.apache.batik.svggen.SVGGraphics2D;

import org.dishevelled.color.scheme.ColorScheme;

import org.dishevelled.piccolo.venn.AbstractVennNode;
import org.dishevelled.piccolo.venn.BinaryVennNode;
import org.dishevelled.piccolo.venn.TernaryVennNode;
import org.dishevelled.piccolo.venn.QuaternaryVennNode;
import org.dishevelled.piccolo.venn.VennNode;

import org.dishevelled.identify.ContextMenuListener;

import org.drjekyll.fontchooser.FontDialog;

import org.piccolo2d.PCamera;
import org.piccolo2d.PCanvas;
import org.piccolo2d.PNode;

import org.piccolo2d.event.PBasicInputEventHandler;
import org.piccolo2d.event.PInputEvent;
import org.piccolo2d.event.PInputEventFilter;
import org.piccolo2d.event.PMouseWheelZoomEventHandler;
import org.piccolo2d.event.PPanEventHandler;

import org.piccolo2d.nodes.PArea;
import org.piccolo2d.nodes.PText;
import org.piccolo2d.nodes.PPath;

import org.piccolo2d.util.PBounds;
import org.piccolo2d.util.PPaintContext;
import org.piccolo2d.util.PPickPath;

import org.w3c.dom.Document;
import org.w3c.dom.DOMImplementation;

/**
 * Diagram view.
 *
 * @author  Michael Heuer
 */
final class DiagramView
    extends JPanel
{
    /** SVG namespace. */
    private static final String SVG_NS = "http://www.w3.org/2000/svg";

    /** I18n. */
    private static final ResourceBundle I18N = ResourceBundle.getBundle("VennApp", Locale.getDefault());

    /** Canvas. */
    private final PCanvas canvas;

    /** Choose color scheme. */
    private final Action chooseColorScheme = new AbstractAction(I18N.getString("DiagramView.chooseColorScheme") + "...")
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                chooseColorScheme();
            }
        };

    /** Choose label font. */
    private final Action chooseLabelFont = new AbstractAction(I18N.getString("DiagramView.chooseLabelFont") + "...")
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                chooseLabelFont();
            }
        };

    /** Choose size label font. */
    private final Action chooseSizeLabelFont = new AbstractAction(I18N.getString("DiagramView.chooseSizeLabelFont") + "...")
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                chooseSizeLabelFont();
            }
        };

    /** Export to PNG image action. */
    private final Action exportToPNG = new AbstractAction(I18N.getString("DiagramView.exportToPNG") + "...")
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                exportToPNG();
            }
        };

    /** Export to SNG image action. */
    private final Action exportToSVG = new AbstractAction(I18N.getString("DiagramView.exportToSVG") + "...")
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                exportToSVG();
            }
        };

    /** Select all action. */
    private final Action selectAll = new AbstractAction(I18N.getString("DiagramView.selectAll"))
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                selectAll();
            }
        };

    /** Clear selection action. */
    private final Action clearSelection = new AbstractAction(I18N.getString("DiagramView.clearSelection"))
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                clearSelection();
            }
        };

    /** Zoom in action. */
    private final Action zoomIn = new AbstractAction(I18N.getString("DiagramView.zoomIn"))
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                zoomIn();
            }
        };

    /** Zoom out action. */
    private final Action zoomOut = new AbstractAction(I18N.getString("DiagramView.zoomOut"))
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                zoomOut();
            }
        };

    /** Toggle display labels. */
    private final Action displayLabels = new AbstractAction(I18N.getString("DiagramView.displayLabels"))
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                displayLabels(((AbstractButton) event.getSource()).isSelected());
            }
        };

    /** Toggle display size labels. */
    private final Action displaySizeLabels = new AbstractAction(I18N.getString("DiagramView.displaySizeLabels"))
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                displaySizeLabels(((AbstractButton) event.getSource()).isSelected());
            }
        };

    /** Toggle display sizes in set labels. */
    private final Action displaySizes = new AbstractAction(I18N.getString("DiagramView.displaySizes"))
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                displaySizes(((AbstractButton) event.getSource()).isSelected());
            }
        };

    /** Toggle display sizes for empty areas. */
    private final Action displaySizesForEmptyAreas = new AbstractAction(I18N.getString("DiagramView.displaySizesForEmptyAreas"))
        {
            @Override
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
        canvas.addKeyListener(new ModeEventHandler());
        canvas.addInputEventListener(new PanEventHandler());
        PMouseWheelZoomEventHandler mouseWheelZoomEventHandler = new PMouseWheelZoomEventHandler();
        mouseWheelZoomEventHandler.zoomAboutCanvasCenter();
        mouseWheelZoomEventHandler.setScaleFactor(1.0E-02d);
        canvas.addInputEventListener(mouseWheelZoomEventHandler);

        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        int menuKeyMask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
        KeyStroke ctrlShiftP = KeyStroke.getKeyStroke(KeyEvent.VK_P, menuKeyMask | InputEvent.SHIFT_DOWN_MASK);
        KeyStroke ctrlShiftS = KeyStroke.getKeyStroke(KeyEvent.VK_S, menuKeyMask | InputEvent.SHIFT_DOWN_MASK);
        KeyStroke ctrlShiftA = KeyStroke.getKeyStroke(KeyEvent.VK_A, menuKeyMask | InputEvent.SHIFT_DOWN_MASK);
        KeyStroke ctrlShiftB = KeyStroke.getKeyStroke(KeyEvent.VK_A, menuKeyMask | InputEvent.SHIFT_DOWN_MASK);
        KeyStroke ctrlShiftC = KeyStroke.getKeyStroke(KeyEvent.VK_C, menuKeyMask | InputEvent.SHIFT_DOWN_MASK);
        KeyStroke ctrlShiftF = KeyStroke.getKeyStroke(KeyEvent.VK_F, menuKeyMask | InputEvent.SHIFT_DOWN_MASK);
        KeyStroke ctrlShiftR = KeyStroke.getKeyStroke(KeyEvent.VK_R, menuKeyMask | InputEvent.SHIFT_DOWN_MASK);
        KeyStroke ctrlShiftPeriod = KeyStroke.getKeyStroke(KeyEvent.VK_COMMA, menuKeyMask | InputEvent.SHIFT_DOWN_MASK);
        KeyStroke ctrlShiftComma = KeyStroke.getKeyStroke(KeyEvent.VK_PERIOD, menuKeyMask | InputEvent.SHIFT_DOWN_MASK);
        inputMap.put(ctrlShiftR, "chooseColorScheme");
        inputMap.put(ctrlShiftF, "chooseLabelFont");
        inputMap.put(ctrlShiftB, "chooseSizeLabelFont");
        inputMap.put(ctrlShiftP, "exportToPNG");
        inputMap.put(ctrlShiftS, "exportToSVG");
        inputMap.put(ctrlShiftA, "selectAll");
        inputMap.put(ctrlShiftC, "clearSelection");
        inputMap.put(ctrlShiftComma, "zoomIn");
        inputMap.put(ctrlShiftPeriod, "zoomOut");
        getActionMap().put("chooseColorScheme", chooseColorScheme);
        getActionMap().put("chooseLabelFont", chooseLabelFont);
        getActionMap().put("chooseSizeLabelFont", chooseSizeLabelFont);
        getActionMap().put("exportToPNG", exportToPNG);
        getActionMap().put("exportToSVG", exportToSVG);
        getActionMap().put("selectAll", selectAll);
        getActionMap().put("clearSelection", clearSelection);
        getActionMap().put("zoomIn", zoomIn);
        getActionMap().put("zoomOut", zoomOut);

        JMenuItem chooseColorSchemeMenuItem = new JMenuItem(chooseColorScheme);
        chooseColorSchemeMenuItem.setAccelerator(ctrlShiftR);
        JMenuItem chooseLabelFontMenuItem = new JMenuItem(chooseLabelFont);
        chooseLabelFontMenuItem.setAccelerator(ctrlShiftF);
        JMenuItem chooseSizeLabelFontMenuItem = new JMenuItem(chooseSizeLabelFont);
        chooseSizeLabelFontMenuItem.setAccelerator(ctrlShiftB);

        JMenuItem exportToPNGMenuItem = new JMenuItem(exportToPNG);
        exportToPNGMenuItem.setAccelerator(ctrlShiftP);
        JMenuItem exportToSVGMenuItem = new JMenuItem(exportToSVG);
        exportToSVGMenuItem.setAccelerator(ctrlShiftS);

        JCheckBoxMenuItem displayLabelsMenuItem = new JCheckBoxMenuItem(displayLabels);
        displayLabelsMenuItem.setSelected(true);
        JCheckBoxMenuItem displaySizesMenuItem = new JCheckBoxMenuItem(displaySizes);
        displaySizesMenuItem.setSelected(true);
        JCheckBoxMenuItem displaySizeLabelsMenuItem = new JCheckBoxMenuItem(displaySizeLabels);
        displaySizeLabelsMenuItem.setSelected(true);
        JCheckBoxMenuItem displaySizesForEmptyAreasMenuItem = new JCheckBoxMenuItem(displaySizesForEmptyAreas);
        displaySizesForEmptyAreasMenuItem.setSelected(true);

        JMenuItem selectAllMenuItem = new JMenuItem(selectAll);
        selectAllMenuItem.setAccelerator(ctrlShiftA);
        JMenuItem clearSelectionMenuItem = new JMenuItem(clearSelection);
        clearSelectionMenuItem.setAccelerator(ctrlShiftC);
        JMenuItem zoomInMenuItem = new JMenuItem(zoomIn);
        zoomInMenuItem.setAccelerator(ctrlShiftPeriod);
        JMenuItem zoomOutMenuItem = new JMenuItem(zoomOut);
        zoomOutMenuItem.setAccelerator(ctrlShiftComma);

        JPopupMenu contextMenu = new JPopupMenu();
        contextMenu.add(chooseColorSchemeMenuItem);
        contextMenu.add(chooseLabelFontMenuItem);
        contextMenu.add(chooseSizeLabelFontMenuItem);
        contextMenu.addSeparator();
        contextMenu.add(exportToPNGMenuItem);
        contextMenu.add(exportToSVGMenuItem);
        contextMenu.addSeparator();
        contextMenu.add(displayLabelsMenuItem);
        contextMenu.add(displaySizesMenuItem);
        contextMenu.add(displaySizeLabelsMenuItem);
        contextMenu.add(displaySizesForEmptyAreasMenuItem);
        contextMenu.addSeparator();
        contextMenu.add(selectAllMenuItem);
        contextMenu.add(clearSelectionMenuItem);
        contextMenu.addSeparator();
        contextMenu.add(zoomInMenuItem);
        contextMenu.add(zoomOutMenuItem);
        canvas.addMouseListener(new ContextMenuListener(contextMenu));

        setLayout(new BorderLayout());
        add("Center", canvas);
    }

    /**
     * Create a new diagram view with the specified binary venn node.
     *
     * @param binaryVennNode binary venn node
     */
    DiagramView(final BinaryVennNode<String> binaryVennNode)
    {
        this();

        for (PNode node : binaryVennNode.nodes())
        {
            node.addInputEventListener(new ToolTipTextListener());
            node.addInputEventListener(new MousePressedListener());
        }
        canvas.getLayer().addChild(binaryVennNode);
        centerView(binaryVennNode);
    }

    /**
     * Create a new diagram view with the specified ternary venn node.
     *
     * @param ternaryVennNode ternary venn node
     */
    DiagramView(final TernaryVennNode<String> ternaryVennNode)
    {
        this();

        for (PNode node : ternaryVennNode.nodes())
        {
            node.addInputEventListener(new ToolTipTextListener());
            node.addInputEventListener(new MousePressedListener());
        }
        canvas.getLayer().addChild(ternaryVennNode);
        centerView(ternaryVennNode);
    }

    /**
     * Create a new diagram view with the specified quaternary venn node.
     *
     * @param quaternaryVennNode quaternary venn node
     */
    DiagramView(final QuaternaryVennNode<String> quaternaryVennNode)
    {
        this();

        for (PNode node : quaternaryVennNode.nodes())
        {
            node.addInputEventListener(new ToolTipTextListener());
            node.addInputEventListener(new MousePressedListener());
        }
        canvas.getLayer().addChild(quaternaryVennNode);
        centerView(quaternaryVennNode);
    }

    /**
     * Create a new diagram view with the specified venn node.
     *
     * @param vennNode venn node
     */
    DiagramView(final VennNode<String> vennNode)
    {
        this();

        for (PNode node : vennNode.nodes())
        {
            node.addInputEventListener(new ToolTipTextListener());
            node.addInputEventListener(new MousePressedListener());
        }
        canvas.getLayer().addChild(vennNode);
        centerView(vennNode);
    }


    /**
     * Center the view about the specified node.  Happens later
     * on the event dispatch thread.
     *
     * @param node node
     */
    void centerView(final PNode node)
    {
        SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run()
                {
                    Point2D nodeCenter = node.getFullBoundsReference().getCenter2D();
                    Point2D viewCenter = canvas.getCamera().getBoundsReference().getCenter2D();
                    canvas.getCamera().setViewOffset(viewCenter.getX() - nodeCenter.getX(),
                                                     viewCenter.getY() - nodeCenter.getY());
                }
            });
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
                AbstractVennNode<String> abstractVennNode = (AbstractVennNode<String>) node;
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
    private Set<String> getViewForPickedNode(final PPickPath path)
    {
        PNode pickedNode = path.getPickedNode();
        for (Iterator i = path.getNodeStackReference().iterator(); i.hasNext(); )
        {
            PNode node = (PNode) i.next();
            if (node instanceof AbstractVennNode)
            {
                AbstractVennNode<String> abstractVennNode = (AbstractVennNode<String>) node;
                Set<String> view = abstractVennNode.viewForNode(pickedNode);
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
        FileDialog fileDialog = new FileDialog((Dialog) windowForComponent(this), I18N.getString("DiagramView.exportToPNG") + "...", FileDialog.SAVE);
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
        FileDialog fileDialog = new FileDialog((Dialog) windowForComponent(this), I18N.getString("DiagramView.exportToSVG") + "...", FileDialog.SAVE);
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
     * Choose label font.
     */
    private void chooseLabelFont()
    {
        // unsafe cast, if this view isn't rooted in a dialog
        FontDialog fontDialog = new FontDialog((Dialog) windowForComponent(this), I18N.getString("DiagramView.chooseLabelFont") + "...", true);
        fontDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        fontDialog.setVisible(true);

        if (!fontDialog.isCancelSelected())
        {
            setLabelFont(fontDialog.getSelectedFont());
        }
    }

    /**
     * Choose size label font.
     */
    private void chooseSizeLabelFont()
    {
        // unsafe cast, if this view isn't rooted in a dialog
        FontDialog fontDialog = new FontDialog((Dialog) windowForComponent(this), I18N.getString("DiagramView.chooseSizeLabelFont") + "...", true);
        fontDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        fontDialog.setVisible(true);

        if (!fontDialog.isCancelSelected())
        {
            setSizeLabelFont(fontDialog.getSelectedFont());
        }
    }

    /**
     * Set the label font for all label nodes to the specified font.
     *
     * @param font font
     */
    private void setLabelFont(final Font font)
    {
        for (Iterator i = canvas.getLayer().getChildrenIterator(); i.hasNext(); )
        {
            PNode node = (PNode) i.next();
            if (node instanceof AbstractVennNode)
            {
                AbstractVennNode<String> vennNode = (AbstractVennNode<String>) node;
                for (PText label : vennNode.labels())
                {
                    label.setFont(font);
                }
            }
        }
    }

    private void setSizeLabelFont(final Font font)
    {
        for (Iterator i = canvas.getLayer().getChildrenIterator(); i.hasNext(); )
        {
            PNode node = (PNode) i.next();
            if (node instanceof AbstractVennNode)
            {
                AbstractVennNode<String> vennNode = (AbstractVennNode<String>) node;
                for (PText sizeLabel : vennNode.sizeLabels())
                {
                    sizeLabel.setFont(font);
                }
            }
        }
    }

    private int n()
    {
        for (Iterator i = canvas.getLayer().getChildrenIterator(); i.hasNext(); )
        {
            PNode node = (PNode) i.next();
            if (node instanceof BinaryVennNode)
            {
                return 2;
            }
            else if (node instanceof TernaryVennNode)
            {
                return 3;
            }
            else if (node instanceof QuaternaryVennNode)
            {
                return 4;
            }
            else if (node instanceof VennNode)
            {
                return ((VennNode) node).size();
            }
        }
        return -1;
    }

    private void chooseColorScheme()
    {
        JDialog colorSchemeDialog = new JDialog((Dialog) windowForComponent(this), I18N.getString("DiagramView.chooseColorScheme") + "...", true);
        colorSchemeDialog.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
        colorSchemeDialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        int n = n();
        ColorSchemeView colorSchemeView = new ColorSchemeView(n);
        colorSchemeDialog.setContentPane(colorSchemeView);
        colorSchemeDialog.getRootPane().setDefaultButton(colorSchemeView.okButton());
        colorSchemeDialog.setSize(600, 706);
        colorSchemeDialog.setVisible(true);

        if (!colorSchemeView.wasCancelled())
        {
            setColorScheme(colorSchemeView.getSelectedColorScheme(), n);
        }
    }

    private void setColorScheme(final ColorScheme colorScheme, final int n)
    {
        for (Iterator i = canvas.getLayer().getChildrenIterator(); i.hasNext(); )
        {
            PNode node = (PNode) i.next();
            if (node instanceof BinaryVennNode)
            {
                BinaryVennNode binaryVennNode = (BinaryVennNode) node;
                binaryVennNode.getFirst().setPaint(colorScheme.getColor(0.0f));
                binaryVennNode.getSecond().setPaint(colorScheme.getColor(1.0f));
            }
            else if (node instanceof TernaryVennNode)
            {
                TernaryVennNode ternaryVennNode = (TernaryVennNode) node;
                ternaryVennNode.getFirst().setPaint(colorScheme.getColor(0.0f));
                ternaryVennNode.getSecond().setPaint(colorScheme.getColor(0.5f));
                ternaryVennNode.getThird().setPaint(colorScheme.getColor(1.0f));
            }
            else if (node instanceof QuaternaryVennNode)
            {
                QuaternaryVennNode quaternaryVennNode = (QuaternaryVennNode) node;
                quaternaryVennNode.getFirst().setPaint(colorScheme.getColor(0.0f));
                quaternaryVennNode.getSecond().setPaint(colorScheme.getColor(0.33f));
                quaternaryVennNode.getThird().setPaint(colorScheme.getColor(0.66f));
                quaternaryVennNode.getFourth().setPaint(colorScheme.getColor(1.0f));
            }
            else if (node instanceof VennNode)
            {
                VennNode vennNode = (VennNode) node;

                float d = 1.0f/(n * 1.0f);
                float f = 0.0f + d/2.0f;
                for (int j = 0; j < n; j++)
                {
                    PPath path = vennNode.getPath(j);
                    path.setPaint(colorScheme.getColor(f));
                    f += d;
                }
            }
        }
    }

    /**
     * Display set labels.
     *
     * @param displayLabels true if labels should display set labels
     */
    private void displayLabels(final boolean displayLabels)
    {
        for (Iterator i = canvas.getLayer().getChildrenIterator(); i.hasNext(); )
        {
            PNode node = (PNode) i.next();
            if (node instanceof AbstractVennNode)
            {
                AbstractVennNode<String> vennNode = (AbstractVennNode<String>) node;
                vennNode.setDisplayLabels(displayLabels);
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
                AbstractVennNode<String> vennNode = (AbstractVennNode<String>) node;
                vennNode.setDisplaySizes(displaySizes);
            }
        }
    }

    /**
     * Display size labels.
     *
     * @param displaySizeLabels true if labels should display size labels
     */
    private void displaySizeLabels(final boolean displaySizeLabels)
    {
        for (Iterator i = canvas.getLayer().getChildrenIterator(); i.hasNext(); )
        {
            PNode node = (PNode) i.next();
            if (node instanceof AbstractVennNode)
            {
                AbstractVennNode<String> vennNode = (AbstractVennNode<String>) node;
                vennNode.setDisplaySizeLabels(displaySizeLabels);
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
                AbstractVennNode<String> vennNode = (AbstractVennNode<String>) node;
                vennNode.setDisplaySizesForEmptyAreas(displaySizesForEmptyAreas);
            }
        }
    }

    /**
     * Select all.
     */
    private void selectAll()
    {
        // todo
    }

    /**
     * Clear selection.
     */
    private void clearSelection()
    {
        // todo
    }

    /**
     * Zoom in.
     */
    private void zoomIn()
    {
        PCamera camera = canvas.getCamera();
        double scale = 1.0d + 4.0d * SCALE_FACTOR;
        // todo: should limit scale to some reasonable maximum
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
        // todo: should limit scale to some reasonable minimum
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

        @Override
        public void mouseEntered(final PInputEvent event)
        {
            PCanvas canvas = (PCanvas) event.getComponent();
            canvas.setToolTipText(getLabelTextForPickedNode(event.getPath()));
        }

        @Override
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


        @Override
        public void mousePressed(final PInputEvent event)
        {
            if (Mode.PAN == mode)
            {
                return;
            }
            PNode pickedNode = event.getPickedNode();
            pickedNode.setPaint(AREA_PRESSED_PAINT);
        }

        @Override
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
        /** Pan cursor. */
        private final Cursor panCursor;

        /**
         * Create a new mode event listener.
         */
        ModeEventHandler()
        {
            panCursor = IS_OS_MAC ? createMoveCursor() : Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
        }

        @Override
        public void keyPressed(final KeyEvent event)
        {
            if (KeyEvent.VK_SPACE == event.getKeyCode())
            {
                mode = Mode.PAN;
                ((PCanvas) event.getComponent()).setCursor(panCursor);
            }
        }

        @Override
        public void keyReleased(final KeyEvent event)
        {
            if (KeyEvent.VK_SPACE == event.getKeyCode())
            {
                mode = Mode.EDIT;
                ((PCanvas) event.getComponent()).setCursor(Cursor.getDefaultCursor());
            }
        }

        /**
         * Create a custom move cursor.
         *
         * @return a custom move cursor
         */
        private Cursor createMoveCursor()
        {
            try
            {
                Image moveCursor = ImageIO.read(getClass().getResource("moveCursor16x16.png"));
                return Toolkit.getDefaultToolkit().createCustomCursor(moveCursor, new Point(8, 8), "moveCursor");
            }
            catch (Exception e)
            {
                // ignore
                return null;
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
            // todo: cytoscape main network view uses middle-click + drag to pan
            //    adding BUTTON2_MASK didn't seem to help, may need to fully subclass PPanEventHandler
            setEventFilter(new PInputEventFilter(InputEvent.BUTTON1_MASK)
                {
                    @Override
                    public boolean acceptsEvent(final PInputEvent event, final int type)
                    {
                        return super.acceptsEvent(event, type) && (Mode.PAN == mode);
                    }
                });
        }
    }
}
