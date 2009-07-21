/*

    dsh-piccolo-state-machine-sprite-examples  Piccolo2D state machine sprite node examples.
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
package org.dishevelled.piccolo.sprite.statemachine.examples;

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.SwingUtilities;

import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.PLayer;

import edu.umd.cs.piccolo.util.PPaintContext;

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
        PLayer layer = canvas.getLayer();

        Random random = new Random();
        walkingSprites = new CopyOnWriteArrayList<WalkingSprite>();
        for (int i = 0; i < 2; i++)
        {
            WalkingSprite walkingSprite = new WalkingSprite();
            walkingSprite.setWidth(18.0);
            walkingSprite.setHeight(28.0);
            walkingSprite.offset(random.nextDouble() * 400.0d, random.nextDouble() * 400.0d);
            layer.addChild(walkingSprite);
            walkingSprites.add(walkingSprite);
        }

        Timer timer = new Timer((int) (1000 / 3), new ActionListener()
            {
                /** {@inheritDoc} */
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

        Timer timer2 = new Timer(5000, new ActionListener()
            {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent e)
                {
                    for (WalkingSprite walkingSprite : walkingSprites)
                    {
                        walkingSprite.walk();
                    }
                }
            });
        timer2.setRepeats(true);
        timer2.start();

        Timer timer3 = new Timer(5000, new ActionListener()
            {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent e)
                {
                    for (WalkingSprite walkingSprite : walkingSprites)
                    {
                        walkingSprite.stop();
                    }
                }
            });
        timer3.setInitialDelay(2500);
        timer3.setRepeats(true);
        timer3.start();

        setLayout(new BorderLayout());
        add("Center", canvas);
    }


    /** {@inheritDoc} */
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
        SwingUtilities.invokeLater(new Runnable()
            {
                /** {@inheritDoc} */
                public void run()
                {
                    new StateMachineSpriteExample().run();
                }
            });
    }
}