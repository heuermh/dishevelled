/*

    dsh-text-rendering-benchmark  Text rendering benchmarks.
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
package org.dishevelled.text.java2d;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import static java.awt.RenderingHints.*;

import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.dishevelled.text.TextRenderingBenchmark;

/**
 * Abstract Java2D text rendering benchmark.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractJava2DTextRenderingBenchmark
    implements TextRenderingBenchmark
{
    /** Font. */
    private Font font;

    /** Text. */
    private String text;

    /** Component to render text. */
    private final JPanel component = new JPanel()
        {
            /** {@inheritDoc} */
            protected void paintComponent(final Graphics graphics)
            {
                Graphics2D g2 = (Graphics2D) graphics;
                g2.setFont(font);
                g2.setPaint(Color.BLACK);
                g2.setRenderingHint(KEY_FRACTIONALMETRICS, VALUE_FRACTIONALMETRICS_ON);
                prepareGraphics(g2);
                // TODO:  calculate height of text accurately
                g2.drawString(text, 0, component.getHeight() - 4);
            }
        };


    /**
     * Prepare the specified graphics for text rendering.
     *
     * @param graphics graphics to prepare
     */
    protected abstract void prepareGraphics(Graphics2D graphics);

    /** {@inheritDoc} */
    public void setFont(final Font font)
    {
        this.font = font;
    }

    /** {@inheritDoc} */
    public void setBounds(final Dimension bounds)
    {
        component.setSize(bounds);
        component.setMinimumSize(bounds);
        component.setPreferredSize(bounds);
    }

    /** {@inheritDoc} */
    public void setText(final String text)
    {
        this.text = text;
    }

    /** {@inheritDoc} */
    public JComponent getComponent()
    {
        return component;
    }

    /** {@inheritDoc} */
    public BufferedImage call() throws Exception
    {
        int h = component.getHeight();
        int w = component.getWidth();
        BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = bufferedImage.createGraphics();
        component.paint(graphics);
        graphics.dispose();
        return bufferedImage;
    }
}