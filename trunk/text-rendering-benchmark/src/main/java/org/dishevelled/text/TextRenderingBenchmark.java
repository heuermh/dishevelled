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
package org.dishevelled.text;

import java.awt.Font;
import java.awt.Dimension;

import java.awt.image.BufferedImage;

import java.util.concurrent.Callable;

import javax.swing.JComponent;

/**
 * Text rendering benchmark.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface TextRenderingBenchmark
    extends Callable<BufferedImage>
{

    /**
     * Return the name of this text rendering benchmark.
     *
     * @return the name of this text rendering benchmark
     */
    String getName();

    /**
     * Set the font for this text rendering benchmark to <code>font</code>.  This method
     * will be called only once during the lifetime of an instance of an implementation of this interface.
     *
     * @param font font for this text rendering benchmark
     */
    void setFont(Font font);

    /**
     * Set the text for this text rendering benchmark to <code>font</code>.  This method
     * will be called only once during the lifetime of an instance of an implementation of this interface.
     *
     * @param text text for this text rendering benchmark
     */
    void setText(String text);

    /**
     * Set the bounds for this text rendering benchmark to <code>bounds</code>.  This method
     * will be called only once during the lifetime of an instance of an implementation this interface.
     *
     * @param bounds bounds for this text rendering benchmark
     */
    void setBounds(Dimension bounds);

    /**
     * Return the component for this text rendering benchmark.
     *
     * @return the component for this text rendering benchmark
     */
    JComponent getComponent();

    /**
     * {@inheritDoc}
     *
     * <p>
     * Render the output of this text rendering benchmark with the specified font to a
     * buffered image of the specified bounds.  See {@link #setFont(Font)}, {@link #setBounds(Dimension)}.
     * </p>
     */
    BufferedImage call() throws Exception;
}