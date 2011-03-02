/*

    dsh-text-rendering-benchmark  Text rendering benchmarks.
    Copyright (c) 2009-2011 held jointly by the individual authors.

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
package org.dishevelled.text.pswing;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import static java.awt.RenderingHints.*;

import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JLabel;

import org.piccolo2d.extras.pswing.PSwing;
import org.piccolo2d.extras.pswing.PSwingCanvas;

import org.dishevelled.text.TextRenderingBenchmark;

/**
 * Abstract PSwing text rendering benchmark.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractPSwingTextRenderingBenchmark
    implements TextRenderingBenchmark
{
    /** Label to render text. */
    private final JLabel label = new JLabel()
        {
            /** {@inheritDoc} */
            protected void paintComponent(final Graphics graphics)
            {
                Graphics2D g2 = (Graphics2D) graphics;
                g2.setRenderingHint(KEY_FRACTIONALMETRICS, VALUE_FRACTIONALMETRICS_ON);
                prepareGraphics(g2);
                super.paintComponent(g2);
            }
        };

    /** Swing canvas. */
    private final PSwingCanvas canvas;


    /**
     * Create a new abstract PSwing text rendering benchmark.
     */
    public AbstractPSwingTextRenderingBenchmark()
    {
        canvas = new PSwingCanvas();
        canvas.getLayer().addChild(new PSwing(label));
    }


    /**
     * Prepare the specified graphics for text rendering.
     *
     * @param graphics graphics to prepare
     */
    protected abstract void prepareGraphics(Graphics2D graphics);

    /** {@inheritDoc} */
    public void setFont(final Font font)
    {
        label.setFont(font);
    }

    /** {@inheritDoc} */
    public void setBounds(final Dimension bounds)
    {
        canvas.setSize(bounds);
        canvas.setMinimumSize(bounds);
        canvas.setPreferredSize(bounds);
    }

    /** {@inheritDoc} */
    public void setText(final String text)
    {
        label.setText(text);
    }

    /** {@inheritDoc} */
    public JComponent getComponent()
    {
        return canvas;
    }

    /** {@inheritDoc} */
    public BufferedImage call() throws Exception
    {
        int h = canvas.getHeight();
        int w = canvas.getWidth();
        BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setRenderingHint(KEY_FRACTIONALMETRICS, VALUE_FRACTIONALMETRICS_ON);
        prepareGraphics(graphics);
        canvas.paint(graphics);
        graphics.dispose();
        return bufferedImage;
    }
}