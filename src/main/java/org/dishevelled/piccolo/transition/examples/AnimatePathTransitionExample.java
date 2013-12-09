/*

    dsh-piccolo-transition-examples  Examples for the piccolo-transition library.
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
package org.dishevelled.piccolo.transition.examples;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.geom.Path2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.piccolo2d.PCanvas;
import org.piccolo2d.PLayer;

import org.piccolo2d.nodes.PPath;

import org.piccolo2d.util.PPaintContext;

import org.dishevelled.piccolo.transition.AnimatePathTransition;

/**
 * Animate path transition example.
 *
 * @author  Michael Heuer
 */
public final class AnimatePathTransitionExample
    extends JPanel
    implements Runnable
{

    /**
     * Create a new animate path transition example.
     */
    public AnimatePathTransitionExample()
    {
        super();
        PCanvas canvas = new PCanvas();
        canvas.setDefaultRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.setAnimatingRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.setInteractingRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        PLayer layer = canvas.getLayer();

        PPath rect = PPath.createRectangle(0.0f, 0.0f, 25.0f, 25.0f);
        rect.setPaint(new Color(80, 80, 80, 80));
        rect.setStroke(new BasicStroke(1.0f));
        rect.setStrokePaint(new Color(20, 20, 20, 80));
        rect.offset(50.0d, 50.0d);

        Path2D path = new Path2D.Float();
        path.moveTo(200.0f, 100.0f);
        path.lineTo(300.0f, 200.0f);
        path.lineTo(200.0f, 300.0f);
        path.lineTo(100.0f, 200.0f);
        path.lineTo(200.0f, 100.0f);

        PPath pathNode = new PPath.Float(path);
        pathNode.setPaint(null);
        pathNode.setStroke(new BasicStroke(2.0f));
        pathNode.setStrokePaint(new Color(20, 20, 80, 80));

        canvas.getRoot().addActivity(new AnimatePathTransition(rect, path, 20000L));

        layer.addChild(rect);
        layer.addChild(pathNode);

        setLayout(new BorderLayout());
        add("Center", canvas);
    }


    @Override
    public void run()
    {
        JFrame f = new JFrame("Animate Path Transition Example");
        f.setContentPane(this);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setBounds(100, 100, 400, 400);
        f.setVisible(true);
    }


    /**
     * Main.
     *
     * @param args command line arguments, ignored
     */
    public static void main(final String[] args)
    {
        SwingUtilities.invokeLater(new AnimatePathTransitionExample());
    }
}
