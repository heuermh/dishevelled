/*

    dsh-piccolo-physics-examples  Piccolo2D particle system physics examples.
    Copyright (c) 2009 held jointly by the individual authors.

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
package org.dishevelled.piccolo.physics.examples;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Stroke;

import java.awt.event.ActionEvent;

import java.util.Iterator;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import javax.swing.border.EmptyBorder;

import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.PNode;

import edu.umd.cs.piccolo.nodes.PPath;

import edu.umd.cs.piccolo.util.PPaintContext;

import org.dishevelled.layout.ButtonPanel;

import org.dishevelled.piccolo.physics.ParticleSystemActivity;

/**
 * Particle system activity example.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class ParticleSystemActivityExample
    extends JPanel
    implements Runnable
{
    /** Canvas. */
    private final PCanvas canvas;

    /** Source of randomness. */
    private final Random random;


    /**
     * Create a new particle system activity example.
     */
    public ParticleSystemActivityExample()
    {
        super();
        canvas = new PCanvas();
        random = new Random();

        canvas.setDefaultRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.setAnimatingRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.setInteractingRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        PLayer layer = canvas.getLayer();

        Paint paint = new Color(120, 120, 120, 120);
        Stroke stroke = new BasicStroke(0.5f);
        Paint strokePaint = new Color(80, 80, 80, 120);
        for (int i = 0; i < 25; i++)
        {
            PPath path = PPath.createEllipse(0.0f, 0.0f, 32.0f, 32.0f);
            path.setPaint(paint);
            path.setStroke(stroke);
            path.setStrokePaint(strokePaint);
            layer.addChild(path);
        }
        randomize();

        setLayout(new BorderLayout());
        add("Center", canvas);
        add("South", createButtonPanel());
    }


    // todo:  border at top of button panel
    // todo:  pull out actions, disable the activity ones while any other one is running

    /**
     * Create and return a new button panel.
     *
     * @return a new button panel
     */
    private JPanel createButtonPanel()
    {
        ButtonPanel buttonPanel = new ButtonPanel();
        buttonPanel.setBorder(new EmptyBorder(11, 11, 11, 11));
        buttonPanel.add(new AbstractAction("Gravity")
            {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent event)
                {
                    gravity();
                }
            });
        buttonPanel.add(new AbstractAction("Anti-gravity")
            {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent event)
                {
                    antiGravity();
                }
            });
        buttonPanel.add(new AbstractAction("Repulse")
            {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent event)
                {
                    repulse();
                }
            });
        buttonPanel.add(new AbstractAction("Attract")
            {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent event)
                {
                    attract();
                }
            });
        buttonPanel.add(new AbstractAction("Randomize")
            {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent event)
                {
                    randomize();
                }
            });
        return buttonPanel;
    }

    /**
     * Gravity.
     */
    private void gravity()
    {
        ParticleSystemActivity activity = new ParticleSystemActivity(5000L);
        activity.setGravity(0.04f);
        for (Iterator i = canvas.getLayer().getChildrenIterator(); i.hasNext(); )
        {
            PNode node = (PNode) i.next();
            activity.createParticle(node, 1.0f);
        }
        canvas.getRoot().addActivity(activity);
    }

    /**
     * Anti-gravity.
     */
    private void antiGravity()
    {
        ParticleSystemActivity activity = new ParticleSystemActivity(5000L);
        activity.setGravity(-0.04f);
        for (Iterator i = canvas.getLayer().getChildrenIterator(); i.hasNext(); )
        {
            PNode node = (PNode) i.next();
            activity.createParticle(node, 1.0f);
        }
        canvas.getRoot().addActivity(activity);
    }

    /**
     * Repulse.
     */
    private void repulse()
    {
        // create invisible node at center
        final PPath center = PPath.createEllipse(0.0f, 0.0f, 32.0f, 32.0f);
        center.setPaint(Color.RED);
        center.setOffset(300.0d, 225.0d);
        canvas.getLayer().addChild(center);

        ParticleSystemActivity activity = new ParticleSystemActivity(5000L)
            {
                /** {@inheritDoc} */
                protected void activityFinished()
                {
                    super.activityFinished();
                    canvas.getLayer().removeChild(center);
                }
            };
        activity.setGravity(0.0f);
        for (Iterator i = canvas.getLayer().getChildrenIterator(); i.hasNext(); )
        {
            PNode node = (PNode) i.next();
            activity.createParticle(node, 1.0f);
        }

        // clamp center
        activity.clamp(center);

        // create repulsion between center and other nodes
        for (Iterator i = canvas.getLayer().getChildrenIterator(); i.hasNext(); )
        {
            PNode target = (PNode) i.next();
            if (!center.equals(target))
            {
                activity.createAttraction(center, target, -200.0f, 30.0f);
            }
        }

        canvas.getRoot().addActivity(activity);
    }

    /**
     * Attract.
     */
    private void attract()
    {
        // create invisible node at center
        final PPath center = PPath.createEllipse(0.0f, 0.0f, 32.0f, 32.0f);
        center.setPaint(Color.RED);
        center.setOffset(300.0d, 225.0d);
        canvas.getLayer().addChild(center);

        ParticleSystemActivity activity = new ParticleSystemActivity(5000L)
            {
                /** {@inheritDoc} */
                protected void activityFinished()
                {
                    super.activityFinished();
                    canvas.getLayer().removeChild(center);
                }
            };
        activity.setGravity(0.0f);
        for (Iterator i = canvas.getLayer().getChildrenIterator(); i.hasNext(); )
        {
            PNode node = (PNode) i.next();
            activity.createParticle(node, 1.0f);
        }

        // clamp center
        activity.clamp(center);

        // create attraction between center and other nodes
        for (Iterator i = canvas.getLayer().getChildrenIterator(); i.hasNext(); )
        {
            PNode target = (PNode) i.next();
            if (!center.equals(target))
            {
                activity.createAttraction(center, target, 300.0f, 30.0f);
            }
        }

        canvas.getRoot().addActivity(activity);
    }

    /**
     * Randomize.
     */
    private void randomize()
    {
        for (Iterator i = canvas.getLayer().getChildrenIterator(); i.hasNext(); )
        {
            PNode node = (PNode) i.next();
            node.setOffset(500.0d * random.nextDouble(), 500.0d * random.nextDouble());
        }
    }

    /** {@inheritDoc} */
    public void run()
    {
        JFrame f = new JFrame("Particle System Activity Example");
        f.setContentPane(this);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setBounds(100, 100, 600, 500);
        f.setVisible(true);
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
                    new ParticleSystemActivityExample().run();
                }
            });
    }
}