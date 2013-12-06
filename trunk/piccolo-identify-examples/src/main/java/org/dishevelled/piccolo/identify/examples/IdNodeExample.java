/*

    dsh-piccolo-identify-examples  Piccolo2D identifiable bean node examples.
    Copyright (c) 2007-2013 held jointly by the individual authors.

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
package org.dishevelled.piccolo.identify.examples;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import java.awt.event.ActionEvent;

import java.net.URL;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import org.piccolo2d.PCanvas;
import org.piccolo2d.PNode;

import org.piccolo2d.event.PBasicInputEventHandler;
import org.piccolo2d.event.PInputEvent;

import org.piccolo2d.nodes.PImage;
import org.piccolo2d.nodes.PText;

import org.piccolo2d.util.PPaintContext;

import org.piccolo2d.extras.swing.PScrollPane;

import org.dishevelled.iconbundle.IconBundle;
import org.dishevelled.iconbundle.IconSize;

import org.dishevelled.iconbundle.tango.TangoProject;

import org.dishevelled.identify.Identifiable;

import org.dishevelled.piccolo.identify.AbstractIdNode;
import org.dishevelled.piccolo.identify.GenericIdNode;
import org.dishevelled.piccolo.identify.FinderIdNode;
import org.dishevelled.piccolo.identify.NautilusIdNode;
import org.dishevelled.piccolo.identify.ExplorerIdNode;

/**
 * IdNode example.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class IdNodeExample
    extends JPanel
    implements Runnable
{
    /** Custom 128x128 icon size. */
    private static final IconSize CUSTOM_128X128 = new IconSize(128, 128)
        {
            // empty
        };

    /** Dark background image url, <code>http://www.flickr.com/photos/pagedooley/7427165626/</code>. */
    private static final String DARK_BACKGROUND = "7427165626_3b463cf8dd_h.jpg";

    /** Light background image url, <code>http://www.flickr.com/photos/26782864@N00/5907569709/</code>. */
    private static final String LIGHT_BACKGROUND = "5907569709_b5a575d06c_b.jpg";

    /** Dark background color. */
    private static final Color DARK_BACKGROUND_COLOR = new Color(80, 80, 80);

    /** Light background color. */
    private static final Color LIGHT_BACKGROUND_COLOR = new Color(220, 220, 220);

    /** Canvas. */
    private final PCanvas canvas;

    /** True if the id nodes should display light text against a dark background. */
    private boolean reverseVideo;

    /** Dark background. */
    private final PImage darkBackground;

    /** Light background. */
    private final PImage lightBackground;


    /**
     * Create a new id node example.
     */
    public IdNodeExample()
    {
        super();

        canvas = new PCanvas();
        canvas.setOpaque(true);
        canvas.setAnimatingRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.setDefaultRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.setInteractingRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        reverseVideo = false;

        URL darkBackgroundURL = null;
        try
        {
            darkBackgroundURL = getClass().getResource(DARK_BACKGROUND);
        }
        catch (Exception e)
        {
            // ignore
        }
        URL lightBackgroundURL = null;
        try
        {
            lightBackgroundURL = getClass().getResource(LIGHT_BACKGROUND);
        }
        catch (Exception e)
        {
            // ignore
        }
        darkBackground = new PImage(darkBackgroundURL);
        lightBackground = new PImage(lightBackgroundURL);
        canvas.getLayer().addChild(darkBackground);
        canvas.getLayer().addChild(lightBackground);

        PText genericLabel = new PText("Generic");
        genericLabel.offset(50, 50);
        canvas.getLayer().addChild(genericLabel);
        Font defaultFont = PText.DEFAULT_FONT;
        GenericIdNode idNode0 = new GenericIdNode(new Doc(), TangoProject.EXTRA_SMALL);
        idNode0.setFont(defaultFont.deriveFont(10.0f));
        idNode0.offset(150 - idNode0.getWidth() / 2.0d, 100 + idNode0.getHeight() / 2.0d);
        canvas.getLayer().addChild(idNode0);
        GenericIdNode idNode1 = new GenericIdNode(new Mic(), TangoProject.SMALL);
        idNode1.setFont(defaultFont.deriveFont(11.0f));
        idNode1.offset(250 - idNode1.getWidth() / 2.0d, 100 + idNode1.getHeight() / 2.0d);
        canvas.getLayer().addChild(idNode1);
        GenericIdNode idNode2 = new GenericIdNode(new Computer(), TangoProject.MEDIUM);
        idNode2.offset(350 - idNode2.getWidth() / 2.0d, 100 + idNode2.getHeight() / 2.0d);
        canvas.getLayer().addChild(idNode2);
        GenericIdNode idNode3 = new GenericIdNode(new Doc(), TangoProject.LARGE);
        idNode3.offset(450 - idNode3.getWidth() / 2.0d, 100 + idNode3.getHeight() / 2.0d);
        canvas.getLayer().addChild(idNode3);
        GenericIdNode idNode4 = new GenericIdNode(new Mic(), CUSTOM_128X128);
        idNode4.setFont(defaultFont.deriveFont(14.0f));
        idNode4.offset(550 - idNode4.getWidth() / 2.0d, 100 + idNode4.getHeight() / 2.0d);
        canvas.getLayer().addChild(idNode4);

        PText finderLabel = new PText("Finder-style");
        finderLabel.offset(50, 200);
        canvas.getLayer().addChild(finderLabel);
        FinderIdNode idNode5 = new FinderIdNode(new Doc(), TangoProject.EXTRA_SMALL);
        idNode5.setFont(defaultFont.deriveFont(10.0f));
        idNode5.offset(150 - idNode5.getWidth() / 2.0d, 250 + idNode5.getHeight() / 2.0d);
        canvas.getLayer().addChild(idNode5);
        FinderIdNode idNode6 = new FinderIdNode(new Mic(), TangoProject.SMALL);
        idNode6.setFont(defaultFont.deriveFont(11.0f));
        idNode6.offset(250 - idNode6.getWidth() / 2.0d, 250 + idNode6.getHeight() / 2.0d);
        canvas.getLayer().addChild(idNode6);
        FinderIdNode idNode7 = new FinderIdNode(new Computer(), TangoProject.MEDIUM);
        idNode7.offset(350 - idNode7.getWidth() / 2.0d, 250 + idNode7.getHeight() / 2.0d);
        canvas.getLayer().addChild(idNode7);
        FinderIdNode idNode8 = new FinderIdNode(new Doc(), TangoProject.LARGE);
        idNode8.offset(450 - idNode8.getWidth() / 2.0d, 250 + idNode8.getHeight() / 2.0d);
        canvas.getLayer().addChild(idNode8);
        FinderIdNode idNode9 = new FinderIdNode(new Mic(), CUSTOM_128X128);
        idNode9.setFont(defaultFont.deriveFont(14.0f));
        idNode9.offset(550 - idNode9.getWidth() / 2.0d, 250 + idNode9.getHeight() / 2.0d);
        canvas.getLayer().addChild(idNode9);

        PText explorerLabel = new PText("Explorer-style");
        explorerLabel.offset(50, 400);
        canvas.getLayer().addChild(explorerLabel);
        Font tahoma = new Font("Tahoma", Font.PLAIN, 11);
        ExplorerIdNode idNode10 = new ExplorerIdNode(new Doc(), TangoProject.EXTRA_SMALL);
        idNode10.setFont(tahoma.deriveFont(10.0f));
        idNode10.offset(150 - idNode10.getWidth() / 2.0d, 450 + idNode10.getHeight() / 2.0d);
        canvas.getLayer().addChild(idNode10);
        ExplorerIdNode idNode11 = new ExplorerIdNode(new Mic(), TangoProject.SMALL);
        idNode11.setFont(tahoma);
        idNode11.offset(250 - idNode11.getWidth() / 2.0d, 450 + idNode11.getHeight() / 2.0d);
        canvas.getLayer().addChild(idNode11);
        ExplorerIdNode idNode12 = new ExplorerIdNode(new Computer(), TangoProject.MEDIUM);
        idNode12.setFont(tahoma);
        idNode12.offset(350 - idNode12.getWidth() / 2.0d, 450 + idNode12.getHeight() / 2.0d);
        canvas.getLayer().addChild(idNode12);
        ExplorerIdNode idNode13 = new ExplorerIdNode(new Doc(), TangoProject.LARGE);
        idNode13.setFont(tahoma);
        idNode13.offset(450 - idNode13.getWidth() / 2.0d, 450 + idNode13.getHeight() / 2.0d);
        canvas.getLayer().addChild(idNode13);
        ExplorerIdNode idNode14 = new ExplorerIdNode(new Mic(), CUSTOM_128X128);
        idNode14.setFont(tahoma.deriveFont(13.0f));
        idNode14.offset(550 - idNode14.getWidth() / 2.0d, 450 + idNode14.getHeight() / 2.0d);
        canvas.getLayer().addChild(idNode14);

        PText nautilusLabel = new PText("Nautilus-style");
        nautilusLabel.offset(50, 600);
        canvas.getLayer().addChild(nautilusLabel);
        NautilusIdNode idNode15 = new NautilusIdNode(new Doc(), TangoProject.EXTRA_SMALL);
        idNode15.setFont(defaultFont.deriveFont(10.0f));
        idNode15.offset(150 + idNode15.getWidth() / 2.0d, 650 + idNode15.getHeight() / 2.0d);
        canvas.getLayer().addChild(idNode15);
        NautilusIdNode idNode16 = new NautilusIdNode(new Mic(), TangoProject.SMALL);
        idNode16.setFont(defaultFont.deriveFont(11.0f));
        idNode16.offset(250 - idNode16.getWidth() / 2.0d, 650 + idNode16.getHeight() / 2.0d);
        canvas.getLayer().addChild(idNode16);
        NautilusIdNode idNode17 = new NautilusIdNode(new Computer(), TangoProject.MEDIUM);
        idNode17.offset(350 - idNode17.getWidth() / 2.0d, 650 + idNode17.getHeight() / 2.0d);
        canvas.getLayer().addChild(idNode17);
        NautilusIdNode idNode18 = new NautilusIdNode(new Doc(), TangoProject.LARGE);
        idNode18.offset(450 - idNode18.getWidth() / 2.0d, 650 + idNode18.getHeight() / 2.0d);
        canvas.getLayer().addChild(idNode18);
        NautilusIdNode idNode19 = new NautilusIdNode(new Mic(), CUSTOM_128X128);
        idNode19.setFont(defaultFont.deriveFont(14.0f));
        idNode19.offset(550 - idNode19.getWidth() / 2.0d, 650 + idNode19.getHeight() / 2.0d);
        canvas.getLayer().addChild(idNode19);

        // add actions to reverse video
        Action reverseVideo = new AbstractAction("Reverse video")
            {
                @Override
                public void actionPerformed(final ActionEvent event)
                {
                    reverseVideo();
                }
            };
        canvas.getInputMap().put(KeyStroke.getKeyStroke("ctrl R"), "reverseVideo");
        canvas.getActionMap().put("reverseVideo", reverseVideo);

        // simulate a selection model; handles shift & ctrl click, missing marquee and drag all selected
        final Set<AbstractIdNode> selection = new HashSet<AbstractIdNode>();
        for (Iterator i = canvas.getLayer().getChildrenIterator(); i.hasNext(); )
        {
            PNode node = (PNode) i.next();
            if (node instanceof AbstractIdNode)
            {
                AbstractIdNode idNode = (AbstractIdNode) node;
                idNode.addInputEventListener(new SelectionHandler(idNode, selection));
            }
        }
        canvas.getCamera().addInputEventListener(new PBasicInputEventHandler()
            {
                @Override
                public void mousePressed(final PInputEvent event)
                {
                    PNode pickedNode = event.getPickedNode();
                    if (event.getCamera().equals(pickedNode)
                        || lightBackground.equals(pickedNode)
                        || darkBackground.equals(pickedNode))
                    {
                        for (Iterator i = selection.iterator(); i.hasNext(); )
                        {
                            ((AbstractIdNode) i.next()).deselect();
                        }
                        selection.clear();
                    }
                }
            });

        // remove pan event handler, as it interferes with drag starts
        canvas.removeInputEventListener(canvas.getPanEventHandler());

        setLayout(new BorderLayout());
        add("Center", new PScrollPane(canvas));
    }

    /**
     * Reverse video.
     */
    private void reverseVideo()
    {
        if (reverseVideo)
        {
            lightenBackground();
        }
        else
        {
            darkenBackground();
        }
    }

    /**
     * Darken background.
     */
    private void darkenBackground()
    {
        canvas.setBackground(DARK_BACKGROUND_COLOR);
        darkBackground.lowerToBottom();
        lightBackground.lowerToBottom();

        for (Iterator iterator = canvas.getLayer().getChildrenIterator(); iterator.hasNext(); )
        {
            PNode node = (PNode) iterator.next();
            if (node instanceof AbstractIdNode)
            {
                AbstractIdNode idNode = (AbstractIdNode) node;
                idNode.reverseVideo();
            }
            if (node instanceof PText)
            {
                PText text = (PText) node;
                text.setTextPaint(Color.WHITE);
            }
        }
        reverseVideo = true;
    }

    /**
     * Lighten background.
     */
    private void lightenBackground()
    {
        canvas.setBackground(LIGHT_BACKGROUND_COLOR);
        lightBackground.lowerToBottom();
        darkBackground.lowerToBottom();

        for (Iterator iterator = canvas.getLayer().getChildrenIterator(); iterator.hasNext(); )
        {
            PNode node = (PNode) iterator.next();
            if (node instanceof AbstractIdNode)
            {
                AbstractIdNode idNode = (AbstractIdNode) node;
                idNode.reverseVideo();
            }
            if (node instanceof PText)
            {
                PText text = (PText) node;
                text.setTextPaint(Color.BLACK);
            }
        }
        reverseVideo = false;
    }

    @Override
    public void run()
    {
        final JFrame f = new JFrame("IdNode Example");
        f.setContentPane(this);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setBounds(100, 100, 400, 400);
        f.setVisible(true);
    }


    /**
     * Mic.
     */
    private static class Mic
        implements Identifiable
    {
        @Override
        public IconBundle getIconBundle()
        {
            return TangoProject.AUDIO_INPUT_MICROPHONE;
        }

        @Override
        public String getName()
        {
            return "Microphone";
        }
    }

    /**
     * Doc.
     */
    private static class Doc
        implements Identifiable
    {
        @Override
        public IconBundle getIconBundle()
        {
            return TangoProject.TEXT_X_GENERIC;
        }

        @Override
        public String getName()
        {
            return "Document";
        }
    }

    /**
     * Computer.
     */
    private static class Computer
        implements Identifiable
    {
        @Override
        public IconBundle getIconBundle()
        {
            return TangoProject.COMPUTER;
        }

        @Override
        public String getName()
        {
            return "My Computer";
        }
    }

    /**
     * Selection handler.
     */
    private static class SelectionHandler extends PBasicInputEventHandler
    {
        private final AbstractIdNode idNode;
        private final Set<AbstractIdNode> selection;

        private SelectionHandler(final AbstractIdNode idNode, final Set<AbstractIdNode> selection)
        {
            super();
            this.idNode = idNode;
            this.selection = selection;
        }

        @Override
        public void mousePressed(final PInputEvent event)
        {
            if (selection.contains(idNode))
            {
                if (!(event.isShiftDown()))
                {
                    idNode.deselect();
                    selection.remove(idNode);
                }
            }
            else
            {
                if (!(event.isShiftDown()) && !(event.isControlDown()))
                {
                    // deselect all others first
                    for (Iterator i = selection.iterator(); i.hasNext(); )
                    {
                        ((AbstractIdNode) i.next()).deselect();
                    }
                    selection.clear();
                }
                idNode.select();
                selection.add(idNode);
            }
        }
    }


    /**
     * Main.
     *
     * @param args command line arguments, ignored
     */
    public static void main(final String[] args)
    {
        SwingUtilities.invokeLater(new IdNodeExample());
    }
}
