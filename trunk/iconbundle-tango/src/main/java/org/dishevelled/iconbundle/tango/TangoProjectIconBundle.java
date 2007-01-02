/*

    dsh-iconbundle-tango  Icon bundles for the Tango Project icon theme.
    Copyright (c) 2005-2007 held jointly by the individual authors.

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
package org.dishevelled.iconbundle.tango;

import java.awt.Image;
import java.awt.Color;
import java.awt.Component;

import java.awt.image.BufferedImage;

import java.net.URL;

import javax.imageio.ImageIO;

import javax.swing.UIManager;

import org.dishevelled.iconbundle.IconSize;
import org.dishevelled.iconbundle.IconState;
import org.dishevelled.iconbundle.IconBundle;
import org.dishevelled.iconbundle.IconTextDirection;

import org.dishevelled.iconbundle.impl.IconBundleUtils;

/**
 * Tango project icon bundle.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
final class TangoProjectIconBundle
    implements IconBundle
{
    /** Base url. */
    private static final String BASE_URL = "/org/tango-project/tango-icon-theme/";

    /** Tango project standard icon context. */
    private final String context;

    /** Tango project standard icon name. */
    private final String name;


    /**
     * Create a new tango project icon bundle for
     * the specified standard icon name.
     *
     * @param context tango project standard icon context
     * @param name tango project standard icon name
     */
    TangoProjectIconBundle(final String context,
                           final String name)
    {
        this.context = context;
        this.name = name;
    }


    /** @see IconBundle */
    public Image getImage(final Component component,
                          final IconTextDirection direction,
                          final IconState state,
                          final IconSize size)
    {
        if (direction == null)
        {
            throw new IllegalArgumentException("direction must not be null");
        }
        if (state == null)
        {
            throw new IllegalArgumentException("state must not be null");
        }
        if (size == null)
        {
            throw new IllegalArgumentException("size must not be null");
        }

        URL url = null;
        BufferedImage image = null;

        if (TangoProject.EXTRA_SMALL.equals(size) || TangoProject.SMALL.equals(size))
        {
            try
            {
                url = this.getClass().getResource(BASE_URL + size.toString() + "/" + context + "/" + name + ".png");
                image = ImageIO.read(url);
            }
            catch (Exception e)
            {
                System.err.println("couldn't find image for url=" + url);
                System.err.println("   or=" + BASE_URL + size.toString() + "/" + context + "/" + name + ".png");
                e.printStackTrace();
                // ignore
            }
        }
        else
        {
            int width = size.getWidth();
            int height = size.getHeight();

            url = this.getClass().getResource(BASE_URL + "scalable/" + context + "/" + name + ".svg");
            image = IconBundleUtils.readSVG(url, width, height);
        }

        if (IconState.ACTIVE == state)
        {
            return IconBundleUtils.makeActive(image);
        }
        else if (IconState.MOUSEOVER == state)
        {
            return IconBundleUtils.makeMouseover(image);
        }
        else if (IconState.SELECTED == state)
        {
            Color selectionColor = UIManager.getColor("List.selectionBackground");
            return IconBundleUtils.makeSelected(image, selectionColor);
        }
        else if (IconState.DRAGGING == state)
        {
            return IconBundleUtils.makeDragging(image);
        }
        else if (IconState.DISABLED == state)
        {
            return IconBundleUtils.makeDisabled(image);
        }
        else
        {
            return image;
        }
    }
}