/*

    dsh-piccolo-ease  Piccolo2D easing activities examples.
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
package org.dishevelled.piccolo.ease.examples;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.PLayer;

import edu.umd.cs.piccolo.nodes.PPath;
import edu.umd.cs.piccolo.nodes.PText;

import edu.umd.cs.piccolo.util.PPaintContext;

import org.dishevelled.interpolate.EasingFunction;
import org.dishevelled.interpolate.EasingFunctions;

import org.dishevelled.piccolo.ease.EasingActivity;

/**
 * Easing activity example.
 *
 * @author  Michael Heuer
 * @version $Version$ $Date$
 */
public final class EasingActivityExample
    extends JPanel
    implements Runnable
{
    /** Ellipse. */
    private final PPath ellipse;

    /** Current easing function. */
    private final PText currentEasingFunction;


    /**
     * Create a new easing activity example.
     */
    public EasingActivityExample()
    {
        super();
        PCanvas canvas = new PCanvas();
        canvas.setDefaultRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.setAnimatingRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.setInteractingRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        PLayer layer = canvas.getLayer();

        PPath rect = PPath.createRectangle(0.0f, 0.0f, 200.0f, 200.0f);
        rect.setPaint(null);
        rect.setStroke(new BasicStroke(1.0f));
        rect.setStrokePaint(new Color(20, 20, 20, 80));
        rect.offset(50.0d, 50.0d);

        ellipse = PPath.createEllipse(0.0f, 0.0f, 32.0f, 32.0f);
        ellipse.setPaint(new Color(0, 0, 128));
        ellipse.setStroke(null);
        ellipse.setStrokePaint(null);
        ellipse.offset(34.0d, 234.0d);

        PText label = new PText("Easing function:");
        label.offset(50.0d, 300.0d);

        currentEasingFunction = new PText();
        currentEasingFunction.offset(label.getFullBoundsReference().getX() + label.getFullBoundsReference().getWidth() + 20.0d, 300.0d);

        layer.addChild(rect);
        layer.addChild(ellipse);
        layer.addChild(label);
        layer.addChild(currentEasingFunction);

        Timer t = new Timer(6000, new ActionListener()
            {
                /** Easing function index. */
                private int index = 0;


                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent event)
                {
                    EasingFunction easingFunction = EasingFunctions.VALUES.get(index);
                    currentEasingFunction.setText(easingFunction.toString());
                    OffsetEasingActivity offsetEasingActivity = new OffsetEasingActivity(easingFunction);
                    ellipse.addActivity(offsetEasingActivity);
                    index++;
                    if (index >= EasingFunctions.VALUES.size())
                    {
                        index = 0;
                    }
                }
            });
        t.setInitialDelay(2500);
        t.setRepeats(true);
        t.start();

        setLayout(new BorderLayout());
        add("Center", canvas);
    }

    /** {@inheritDoc} */
    public void run()
    {
        JFrame f = new JFrame("Easing Activity Example");
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
                    new EasingActivityExample().run();
                }
            });
    }


    /**
     * Offset easing activity.
     */
    private class OffsetEasingActivity
        extends EasingActivity
    {
        /** Initial offset x. */
        private static final double INITIAL_OFFSET_X = 34.0d;

        /** Initial offset y. */
        private static final double INITIAL_OFFSET_Y = 234.0d;

        /** Offset x. */
        private static final double OFFSET_X = 200.0d;

        /** Offset y. */
        private static final double OFFSET_Y = -200.0d;


        /**
         * Create a new offset easing activity with the specified easing function.
         *
         * @param easingFunction easing function
         */
        OffsetEasingActivity(final EasingFunction easingFunction)
        {
            super(easingFunction, 5000L);
        }


        /** {@inheritDoc} */
        protected void activityStarted()
        {
            super.activityStarted();
            ellipse.setOffset(INITIAL_OFFSET_X, INITIAL_OFFSET_Y);
        }

        /** {@inheritDoc} */
        public void setRelativeTargetValue(final float value)
        {
            ellipse.setOffset(INITIAL_OFFSET_X + (value * OFFSET_X), INITIAL_OFFSET_Y + (value * OFFSET_Y));
        }
    }
}