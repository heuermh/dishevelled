/*

    dsh-piccolo-venn-examples  Piccolo2D venn diagram node examples.
    Copyright (c) 2009-2010 held jointly by the individual authors.

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
package org.dishevelled.piccolo.venn.examples;

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;

import javax.swing.border.EmptyBorder;

import org.piccolo2d.PCanvas;

import org.piccolo2d.event.PBasicInputEventHandler;
import org.piccolo2d.event.PInputEvent;

import org.piccolo2d.nodes.PText;

import org.piccolo2d.util.PPaintContext;

import org.dishevelled.piccolo.venn.QuaternaryVennNode;

import org.dishevelled.venn.swing.QuaternaryVennList;

/**
 * Quaternary venn node example.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class QuaternaryVennExample
    extends JPanel
    implements Runnable
{
    /** Canvas. */
    private final PCanvas canvas;

    /** Quaternary venn node. */
    private final QuaternaryVennNode<String> vennNode;

    /** Details label. */
    private final PText detailsLabel;

    /** Details dialog. */
    private JDialog details;


    /**
     * Create a new quaternary venn example.
     */
    public QuaternaryVennExample()
    {
        super();
        canvas = new PCanvas();
        canvas.setDefaultRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.setAnimatingRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.setInteractingRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);

        Set<String> set0 = read("the_pioneers.txt");
        Set<String> set1 = read("the_deerslayer.txt");
        Set<String> set2 = read("the_pathfinder.txt");
        Set<String> set3 = read("last_of_the_mohicans.txt");

        vennNode = new QuaternaryVennNode<String>("The Pioneers", set0,
                                                   "The Deerslayer", set1,
                                                   "The Pathfinder", set2,
                                                   "Last of the \nMohicans", set3);
        vennNode.offset(40.0d, 235.0d);

        detailsLabel = new PText("Refining label layout...");
        detailsLabel.offset(20.0d, 540.0d);

        canvas.getLayer().addChild(vennNode);
        canvas.getLayer().addChild(detailsLabel);

        Timer t = new Timer(35000, new ActionListener()
            {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent event)
                {
                    updateDetails();
                }
            });
        t.setRepeats(false);
        t.start();

        setLayout(new BorderLayout());
        add("Center", canvas);
    }

    /**
     * Update details label, install double-click listener.
     */
    private void updateDetails()
    {
        detailsLabel.setText("Left mouse click to pan, right mouse click to zoom, double-click for details");
        canvas.addInputEventListener(new PBasicInputEventHandler()
            {
                /** {@inheritDoc} */
                public void mousePressed(final PInputEvent event)
                {
                    if (event.getClickCount() == 2)
                    {
                        showDetails();
                    }
                }
            });
        Timer t = new Timer(25000, new ActionListener()
            {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent event)
                {
                    detailsLabel.animateToTransparency(0.0f, 2000);
                }
            });
        t.setRepeats(false);
        t.start();
    }

    /**
     * Show details.
     */
    private void showDetails()
    {
        if (details == null)
        {
            SwingUtilities.invokeLater(new Runnable()
                {
                    /** {@inheritDoc} */
                    public void run()
                    {
                        details = new JDialog((JFrame) SwingUtilities.getRoot(QuaternaryVennExample.this), "Details");
                        QuaternaryVennList<String> list = new QuaternaryVennList<String>(vennNode.getFirstLabelText(),
                                                                                           vennNode.getSecondLabelText(),
                                                                                           vennNode.getThirdLabelText(),
                                                                                           vennNode.getFourthLabelText(),
                                                                                           vennNode.getModel());
                        list.setBorder(new EmptyBorder(20, 20, 20, 20));
                        details.setContentPane(list);
                        details.setBounds(200, 200, 800, 800);
                        details.setVisible(true);
                    }
                });
        }
        else
        {
            details.setVisible(true);
            details.requestFocusInWindow();
        }
    }

    /** {@inheritDoc} */
    public void run()
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e)
        {
            // ignore
        }

        JFrame f = new JFrame("Quaternary Venn Example");
        f.setContentPane(this);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setBounds(100, 100, 600, 600);
        f.setVisible(true);
    }


    private static Set<String> read(final String name)
    {
        BufferedReader reader = null;
        Set<String> result = new HashSet<String>(12000);
        try
        {
            reader = new BufferedReader(new InputStreamReader(QuaternaryVennExample.class.getResourceAsStream(name)));
            while (reader.ready())
            {
                result.add(reader.readLine().trim());
            }
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
        }
        return result;
    }


    /**
     * Main.
     *
     * @param args command line arguments, ignored
     */
    public static void main(final String[] args)
    {
        SwingUtilities.invokeLater(new QuaternaryVennExample());
    }
}