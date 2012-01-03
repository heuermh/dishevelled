/*

    dsh-piccolo-physics-examples  Piccolo2D particle system physics examples.
    Copyright (c) 2009-2012 held jointly by the individual authors.

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
import java.awt.event.KeyEvent;

import java.awt.geom.Point2D;

import java.util.Iterator;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import javax.swing.border.EmptyBorder;

import org.piccolo2d.PCanvas;
import org.piccolo2d.PLayer;
import org.piccolo2d.PNode;

import org.piccolo2d.activities.PActivity;
import org.piccolo2d.activities.PInterpolatingActivity;

import org.piccolo2d.event.PInputEvent;
import org.piccolo2d.event.PInputEventListener;
import org.piccolo2d.event.PBasicInputEventHandler;

import org.piccolo2d.nodes.PPath;
import org.piccolo2d.nodes.PText;

import org.piccolo2d.util.PPaintContext;

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

    /** Gravity action. */
    private final Action gravity = new AbstractAction("Gravity")
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                gravity();
            }
        };

    /** Anti-gravity action. */
    private final Action antiGravity = new AbstractAction("Anti-gravity")
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                antiGravity();
            }
        };

    /** Repulse action. */
    private final Action repulse = new AbstractAction("Repulse")
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                repulse();
            }
        };

    /** Attract action. */
    private final Action attract = new AbstractAction("Attract")
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                attract();
            }
        };

    /** Input action. */
    private final Action input = new AbstractAction("Input")
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                input();
            }
        };

    /** Randomize action. */
    private final Action randomize = new AbstractAction("Randomize")
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                randomize();
            }
        };


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

    /**
     * Create and return a new button panel.
     *
     * @return a new button panel
     */
    private JPanel createButtonPanel()
    {
        ButtonPanel buttonPanel = new ButtonPanel();
        buttonPanel.setBorder(new EmptyBorder(11, 11, 11, 11));
        buttonPanel.add(gravity);
        buttonPanel.add(antiGravity);
        buttonPanel.add(repulse);
        buttonPanel.add(attract);
        buttonPanel.add(input);
        buttonPanel.add(randomize);
        return buttonPanel;
    }

    /**
     * Enable actions.
     */
    private void enableActions()
    {
        gravity.setEnabled(true);
        antiGravity.setEnabled(true);
        attract.setEnabled(true);
        repulse.setEnabled(true);
        input.setEnabled(true);
        randomize.setEnabled(true);
    }

    /**
     * Disable actions.
     */
    private void disableActions()
    {
        gravity.setEnabled(false);
        antiGravity.setEnabled(false);
        attract.setEnabled(false);
        repulse.setEnabled(false);
        input.setEnabled(false);
        randomize.setEnabled(false);
    }

    /**
     * Gravity.
     */
    private void gravity()
    {
        disableActions();

        ParticleSystemActivity activity = new ParticleSystemActivity(5000L)
            {
                /** {@inheritDoc} */
                protected void activityFinished()
                {
                    super.activityFinished();
                    enableActions();
                }
            };
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
        disableActions();

        ParticleSystemActivity activity = new ParticleSystemActivity(5000L)
            {
                /** {@inheritDoc} */
                protected void activityFinished()
                {
                    super.activityFinished();
                    enableActions();
                }
            };
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
        disableActions();

        // create temporary node at center
        final PPath center = PPath.createEllipse(0.0f, 0.0f, 32.0f, 32.0f);
        center.setPaint(new Color(180, 0, 0));
        center.setStroke(null);
        center.setOffset(300.0d, 225.0d);
        canvas.getLayer().addChild(center);

        ParticleSystemActivity activity = new ParticleSystemActivity(5000L)
            {
                /** {@inheritDoc} */
                protected void activityFinished()
                {
                    super.activityFinished();
                    canvas.getLayer().removeChild(center);
                    enableActions();
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
        disableActions();

        // create temporary node at center
        final PPath center = PPath.createEllipse(0.0f, 0.0f, 32.0f, 32.0f);
        center.setPaint(new Color(0, 180, 0));
        center.setStroke(null);
        center.setOffset(300.0d, 225.0d);
        canvas.getLayer().addChild(center);

        ParticleSystemActivity activity = new ParticleSystemActivity(5000L)
            {
                /** {@inheritDoc} */
                protected void activityFinished()
                {
                    super.activityFinished();
                    canvas.getLayer().removeChild(center);
                    enableActions();
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
                activity.createAttraction(center, target, 400.0f, 30.0f);
            }
        }

        canvas.getRoot().addActivity(activity);
    }

    /**
     * Input.
     */
    private void input()
    {
        disableActions();

        // create temporary node that follows mouse input
        final PPath mouse = PPath.createEllipse(0.0f, 0.0f, 32.0f, 32.0f);
        mouse.setPaint(new Color(0, 180, 0));
        mouse.setStroke(null);
        mouse.setOffset(300.0d, 185.0d);
        canvas.getLayer().addChild(mouse);
        final PInputEventListener mouseListener = new PBasicInputEventHandler()
            {
                /** {@inheritDoc} */
                public void mouseMoved(final PInputEvent event)
                {
                    Point2D position = event.getPosition();
                    mouse.setOffset(position.getX() - (mouse.getWidth() / 2.0d),
                                    position.getY() - (mouse.getHeight() / 2.0d));
                }
            };
        canvas.addInputEventListener(mouseListener);

        // create temporary node that follows keyboard arrow key input
        final PPath keys = PPath.createEllipse(0.0f, 0.0f, 32.0f, 32.0f);
        keys.setPaint(new Color(180, 0, 0));
        keys.setStroke(null);
        keys.setOffset(300.0d, 265.0d);
        canvas.getLayer().addChild(keys);
        final PInputEventListener keysListener = new PBasicInputEventHandler()
            {
                /** {@inheritDoc} */
                public void keyPressed(final PInputEvent event)
                {
                    switch (event.getKeyCode())
                    {
                    case KeyEvent.VK_RIGHT:
                        keys.offset(20.0d, 0.0d);
                        break;
                    case KeyEvent.VK_LEFT:
                        keys.offset(-20.0d, 0.0d);
                        break;
                    case KeyEvent.VK_UP:
                        keys.offset(0.0d, -20.0d);
                        break;
                    case KeyEvent.VK_DOWN:
                        keys.offset(0.0d, 20.0d);
                        break;
                    default:
                        break;
                    }
                }
            };
        canvas.getRoot().getDefaultInputManager().setKeyboardFocus(keysListener);
        keys.addInputEventListener(keysListener);

        ParticleSystemActivity activity = new ParticleSystemActivity(10000L)
            {
                /** {@inheritDoc} */
                protected void activityFinished()
                {
                    super.activityFinished();
                    canvas.getLayer().removeChild(mouse);
                    canvas.getLayer().removeChild(keys);
                    canvas.removeInputEventListener(mouseListener);
                    keys.removeInputEventListener(keysListener);
                    enableActions();
                }
            };
        activity.setGravity(0.0f);
        for (Iterator i = canvas.getLayer().getChildrenIterator(); i.hasNext(); )
        {
            PNode node = (PNode) i.next();
            activity.createParticle(node, 1.0f);
        }

        // clamp mouse and keys; this allows their
        //    offsets to be translated into particle position
        activity.clamp(mouse);
        activity.clamp(keys);

        // create attraction between mouse and other nodes
        //    and repulsion between keys and other nodes
        for (Iterator i = canvas.getLayer().getChildrenIterator(); i.hasNext(); )
        {
            PNode target = (PNode) i.next();
            if (!mouse.equals(target) && !keys.equals(target))
            {
                activity.createAttraction(mouse, target, 200.0f, 30.0f);
                activity.createAttraction(keys, target, -200.0f, 30.0f);
            }
        }

        final PText instructions = new PText("Attract with mouse\nRepulse with arrow keys");
        instructions.setOffset(500.0d - (instructions.getWidth() / 2.0d), 380.0d);
        canvas.getLayer().addChild(instructions);

        PActivity fade = new PInterpolatingActivity(500L, 20L, System.currentTimeMillis() + 4000L, -1, -1)
            {
                /** {@inheritDoc} */
                public void setRelativeTargetValue(final float value) {
                    instructions.setTransparency(1.0f - value);
                }

                /** {@inheritDoc} */
                protected void activityFinished()
                {
                    super.activityFinished();
                    canvas.getLayer().removeChild(instructions);
                }
            };

        canvas.requestFocus();
        canvas.getRoot().addActivity(fade);
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
        SwingUtilities.invokeLater(new ParticleSystemActivityExample());
    }
}