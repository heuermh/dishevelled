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

import java.awt.Graphics2D;

import static java.awt.RenderingHints.*;

/**
 * Java2D with KEY_TEXT_ANTIALIASING set to VALUE_TEXT_ANTIALIAS_LCD_HBGR.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class Java2DAntialiasingLcdHbgr
    extends AbstractJava2DTextRenderingBenchmark
{

    /** {@inheritDoc} */
    public String getName()
    {
        return "java2d-lcd-hbgr";
    }

    /** {@inheritDoc} */
    protected void prepareGraphics(final Graphics2D graphics)
    {
        graphics.setRenderingHint(KEY_TEXT_ANTIALIASING, VALUE_TEXT_ANTIALIAS_LCD_HBGR);
    }
}