/*

    dsh-iconbundle  Bundles of variants for icon images.
    Copyright (c) 2003-2013 held jointly by the individual authors.

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
package org.dishevelled.iconbundle;

import java.awt.Image;
import java.awt.Component;

/**
 * A bundle of variants of a particular icon image, for
 * different text directions, sizes, and states.  IconBundles
 * are expected to dynamically create variants for which a
 * source image does not exist, by rescaling or transforming
 * existing images.
 *
 * @author  Michael Heuer
 */
public interface IconBundle
{

    /**
     * Return an icon image in the specified icon text direction,
     * size, and state suitable for the specified component.  The
     * specified AWT or Swing component may be null.
     *
     * @param component AWT or Swing component hint, if any
     * @param direction icon text direction, must not be null
     * @param state icon state, must not be null
     * @param size icon size, must not be null
     * @return an icon image suitable for the specified component
     */
    Image getImage(Component component,
                   IconTextDirection direction,
                   IconState state,
                   IconSize size);
}
