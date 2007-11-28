/*

    dsh-piccolo-identify-examples  Piccolo identifiable bean node examples.
    Copyright (c) 2007 held jointly by the individual authors.

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

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import edu.umd.cs.piccolo.PCanvas;

import edu.umd.cs.piccolo.util.PPaintContext;

import org.dishevelled.iconbundle.IconBundle;
import org.dishevelled.iconbundle.IconSize;

import org.dishevelled.iconbundle.tango.TangoProject;

import org.dishevelled.identify.Identifiable;

import org.dishevelled.piccolo.identify.GenericIdNode;

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
    private static final IconSize CUSTOM_128X128 = new IconSize(128, 128) {};


    /**
     * Create a new id node example.
     */
    public IdNodeExample()
    {
        super();

        PCanvas canvas = new PCanvas();
        canvas.setAnimatingRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.setDefaultRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.setInteractingRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);

        GenericIdNode idNode0 = new GenericIdNode(new Doc(), TangoProject.EXTRA_SMALL);
        idNode0.offset(75, 75);
        canvas.getLayer().addChild(idNode0);
        GenericIdNode idNode1 = new GenericIdNode(new Mic(), TangoProject.SMALL);
        idNode1.offset(150, 150);
        canvas.getLayer().addChild(idNode1);
        GenericIdNode idNode2 = new GenericIdNode(new Computer(), TangoProject.MEDIUM);
        idNode2.offset(225, 225);
        canvas.getLayer().addChild(idNode2);
        GenericIdNode idNode3 = new GenericIdNode(new Doc(), TangoProject.LARGE);
        idNode3.offset(300, 300);
        canvas.getLayer().addChild(idNode3);
        GenericIdNode idNode4 = new GenericIdNode(new Mic(), CUSTOM_128X128);
        idNode4.offset(375, 375);
        canvas.getLayer().addChild(idNode4);

        setLayout(new BorderLayout());
        add("Center", canvas);
    }


    /** {@inheritDoc} */
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
        /** {@inheritDoc} */
        public IconBundle getIconBundle()
        {
            return TangoProject.AUDIO_INPUT_MICROPHONE;
        }

        /** {@inheritDoc} */
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
        /** {@inheritDoc} */
        public IconBundle getIconBundle()
        {
            return TangoProject.TEXT_X_GENERIC;
        }

        /** {@inheritDoc} */
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
        /** {@inheritDoc} */
        public IconBundle getIconBundle()
        {
            return TangoProject.COMPUTER;
        }

        /** {@inheritDoc} */
        public String getName()
        {
            return "My Computer";
        }
    }

    /**
     * Main.
     *
     * @param args command line arguments, ignored
     */
    public static void main(final String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
            {
                /** {@inheritDoc} */
                public void run()
                {
                    new IdNodeExample().run();
                }
            });
    }
}
