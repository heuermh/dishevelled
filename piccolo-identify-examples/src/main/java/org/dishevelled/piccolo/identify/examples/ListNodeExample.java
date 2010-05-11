/*

    dsh-piccolo-identify-examples  Piccolo2D identifiable bean node examples.
    Copyright (c) 2007-2010 held jointly by the individual authors.

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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.piccolo2d.PCanvas;

import org.piccolo2d.util.PPaintContext;

import org.piccolo2d.extras.swing.PScrollPane;

import org.dishevelled.piccolo.identify.ListNode;

/**
 * List node example.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class ListNodeExample
    extends JPanel
    implements Runnable
{
    /** Canvas. */
    private final PCanvas canvas;

    /** List node. */
    private final ListNode<String> listNode;


    /**
     * Create a new list node example.
     */
    public ListNodeExample()
    {
        super();

        canvas = new PCanvas();
        canvas.setOpaque(true);
        canvas.setAnimatingRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.setDefaultRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.setInteractingRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);

        listNode = new ListNode<String>();
        listNode.getModel().add("foo");
        listNode.getModel().add("bar");
        listNode.getModel().add("baz");
        listNode.getModel().add("qux");
        canvas.getLayer().addChild(listNode);

        setLayout(new BorderLayout());
        add("Center", canvas);
        //add("Center", new PScrollPane(canvas));
    }


    /** {@inheritDoc} */
    public void run()
    {
        final JFrame f = new JFrame("ListNode Example");
        f.setContentPane(this);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setBounds(100, 100, 400, 400);
        f.setVisible(true);

        canvas.getCamera().animateViewToCenterBounds(listNode.getFullBounds(), true, 0);

        Timer t = new Timer(2000, new ActionListener()
            {
                private int i = 0;

                public void actionPerformed(final ActionEvent event)
                {
                    listNode.getModel().add("garply" + i);
                    canvas.getCamera().animateViewToCenterBounds(listNode.getFullBounds(), true, 400);
                    i++;
                }
            });

        t.setRepeats(true);
        t.start();
    }

    /**
     * Main.
     *
     * @param args command line arguments, ignored
     */
    public static void main(final String[] args)
    {
        SwingUtilities.invokeLater(new ListNodeExample());
    }
}