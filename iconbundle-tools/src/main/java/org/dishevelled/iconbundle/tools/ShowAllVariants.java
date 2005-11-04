/*

    dsh-iconbundle-tools  Command line icon bundle tools.
    Copyright (c) 2003-2005 held jointly by the individual authors.

    This library is free software; you can redistribute it and/or modify it
    under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation; either version 2.1 of the License, or (at
    your option) any later version.

    This library is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; with out even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
    License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this library;  if not, write to the Free Software Foundation,
    Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA.

    > http://www.gnu.org/copyleft/lesser.html
    > http://www.opensource.org/licenses/lgpl-license.php

*/
package org.dishevelled.iconbundle.tools;

import java.io.File;

import java.awt.Color;
import java.awt.Graphics2D;

import java.awt.image.BufferedImage;

import java.util.Iterator;

import javax.imageio.ImageIO;

import org.dishevelled.iconbundle.IconSize;
import org.dishevelled.iconbundle.IconState;
import org.dishevelled.iconbundle.IconBundle;
import org.dishevelled.iconbundle.IconTextDirection;

/**
 * Show all variants runnable.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class ShowAllVariants
    implements Runnable
{
    /** File name. */
    private String fileName;

    /** True to draw borders. */
    private boolean drawBorders;

    /** Icon bundle. */
    private IconBundle iconBundle;


    /**
     * Create a new show all variants runnable with the
     * specified icon bundle.
     *
     * @param iconBundle icon bundle
     * @param fileName file name
     * @param drawBorders true to draw borders
     */
    public ShowAllVariants(final IconBundle iconBundle, final String fileName, final boolean drawBorders)
    {
        this.iconBundle = iconBundle;
        this.fileName = fileName;
        this.drawBorders = drawBorders;
    }


    /** @see Runnable */
    public void run()
    {
        int w = 0;
        int h = 0;

        int maxHeight = 0;
        int horizontalGap = 4;
        int verticalGap = 4;

        for (Iterator i = IconSize.VALUES.iterator(); i.hasNext(); )
        {
            IconSize size = (IconSize) i.next();
            w += size.getWidth();
            w += horizontalGap;

            if (size.getHeight() > maxHeight)
            {
                maxHeight = size.getHeight();
            }
        }

        w *= IconTextDirection.VALUES.size();
        h = IconState.VALUES.size() * (maxHeight + verticalGap);

        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

        int x = 0;
        int y = 0;
        maxHeight = 0;
        for (Iterator i = IconState.VALUES.iterator(); i.hasNext(); )
        {
            IconState state = (IconState) i.next();
            for (Iterator j = IconSize.VALUES.iterator(); j.hasNext(); )
            {
                IconSize size = (IconSize) j.next();
                for (Iterator k = IconTextDirection.VALUES.iterator(); k.hasNext(); )
                {
                    IconTextDirection direction = (IconTextDirection) k.next();

                    g.drawImage(iconBundle.getImage(null, direction, state, size),
                                x, y, Color.WHITE, null);

                    if (drawBorders)
                    {
                        g.setPaint(Color.BLACK);
                        g.drawRect(x, y, size.getWidth(), size.getHeight());
                    }

                    x += size.getWidth();
                    x += horizontalGap;

                    if (size.getHeight() > maxHeight)
                    {
                        maxHeight = size.getHeight();
                    }
                }
            }
            x = 0;
            y += maxHeight;
            y += verticalGap;
            maxHeight = 0;
        }

        try
        {
            ImageIO.write(image, "png", new File(fileName));
        }
        catch (Exception e)
        {
            // ignore
        }
    }
}
