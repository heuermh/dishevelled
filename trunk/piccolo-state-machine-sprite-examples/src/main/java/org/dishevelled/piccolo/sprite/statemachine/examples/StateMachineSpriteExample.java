/*

    dsh-piccolo-state-machine-sprite-examples  Piccolo2D state machine sprite node examples.
    Copyright (c) 2009-2013 held jointly by the individual authors.

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
package org.dishevelled.piccolo.sprite.statemachine.examples;

import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.SwingUtilities;

import org.piccolo2d.PCanvas;
import org.piccolo2d.PLayer;

import org.piccolo2d.util.PPaintContext;

/**
 * State machine sprite example.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class StateMachineSpriteExample
    extends JPanel
    implements Runnable
{
    /** Source of randomness. */
    private final Random random;

    /** List of walking sprites. */
    private final List<WalkingSprite> walkingSprites;


    /**
     * Create a new state machine sprite example.
     */
    public StateMachineSpriteExample()
    {
        super();

        PCanvas canvas = new PCanvas();
        canvas.setDefaultRenderQuality(PPaintContext.LOW_QUALITY_RENDERING);
        canvas.setAnimatingRenderQuality(PPaintContext.LOW_QUALITY_RENDERING);
        canvas.setInteractingRenderQuality(PPaintContext.LOW_QUALITY_RENDERING);
        canvas.setOpaque(true);
        canvas.setBackground(new Color(0, 64, 0));
        PLayer layer = canvas.getLayer();

        random = new Random();
        walkingSprites = new CopyOnWriteArrayList<WalkingSprite>();
        for (int i = 0; i < 50; i++)
        {
            WalkingSprite walkingSprite = new WalkingSprite();
            walkingSprite.setWidth(18.0);
            walkingSprite.setHeight(28.0);
            walkingSprite.offset(random.nextDouble() * 400.0d, random.nextDouble() * 400.0d);

            // randomly tweak the starting animation frame
            if (random.nextDouble() > 0.5d)
            {
                walkingSprite.advance();
            }
            if (random.nextDouble() > 0.5d)
            {
                walkingSprite.advance();
            }
            if (random.nextDouble() > 0.5d)
            {
                walkingSprite.advance();
            }

            layer.addChild(walkingSprite);
            walkingSprites.add(walkingSprite);
        }

        // put sprites in correct z-order based on y-location
        List<WalkingSprite> toSort = new ArrayList<WalkingSprite>(walkingSprites);
        Collections.sort(toSort, new Comparator<WalkingSprite>()
            {
                @Override
                public int compare(final WalkingSprite walkingSprite0, final WalkingSprite walkingSprite1)
                {
                    if (walkingSprite0 == walkingSprite1)
                    {
                        return 0;
                    }
                    return Double.compare(walkingSprite0.getFullBoundsReference().getY(), walkingSprite1.getFullBoundsReference().getY());
                }
            });
        for (WalkingSprite walkingSprite : toSort)
        {
            walkingSprite.raiseToTop();
        }

        // animation frame rate timer
        Timer timer = new Timer((int) (1000 / 3), new ActionListener()
            {
                @Override
                public void actionPerformed(final ActionEvent e)
                {
                    for (WalkingSprite walkingSprite : walkingSprites)
                    {
                        walkingSprite.advance();
                    }
                }
            });

        timer.setRepeats(true);
        timer.start();

        // set some sprites walking every five seconds
        Timer timer2 = new Timer(5000, new ActionListener()
            {
                @Override
                public void actionPerformed(final ActionEvent e)
                {
                    for (WalkingSprite walkingSprite : walkingSprites)
                    {
                        if (random.nextDouble() > 0.33d)
                        {
                            walkingSprite.walk();
                        }
                    }
                }
            });
        timer2.setRepeats(true);
        timer2.start();

        // stop some walking sprites every five seconds
        Timer timer3 = new Timer(5000, new ActionListener()
            {
                @Override
                public void actionPerformed(final ActionEvent e)
                {
                    for (WalkingSprite walkingSprite : walkingSprites)
                    {
                        if (random.nextDouble() > 0.33d)
                        {
                            walkingSprite.stop();
                        }
                    }
                }
            });
        // offset by two and a half seconds
        timer3.setInitialDelay(2500);
        timer3.setRepeats(true);
        timer3.start();

        setLayout(new BorderLayout());
        add("Center", canvas);
    }


    @Override
    public void run()
    {
        JFrame f = new JFrame("State Machine Sprite Example");
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
        SwingUtilities.invokeLater(new StateMachineSpriteExample());
    }
}