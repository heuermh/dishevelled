/*

    dsh-color-scheme  Color schemes.
    Copyright (c) 2009-2019 held jointly by the individual authors.

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
package org.dishevelled.color.scheme;

import java.awt.Color;

/**
 * Factory for creating Color instances.
 *
 * @author  Michael Heuer
 */
public interface ColorFactory
{

    /**
     * Create and return an instance of the color specified by <code>red</code>,
     * <code>green</code>, and <code>blue</code> color components in integer
     * format and <code>alpha</code> as a float.
     *
     * @param red red integer color component, must be <code>0 &lt;= red &lt;= 255</code>
     * @param green green integer color component, must be <code>0 &lt;= green &lt;= 255</code>
     * @param blue blue integer color component, must be <code>0 &lt;= blue &lt;= 255</code>
     * @param alpha alpha value, must be <code>0.0 &lt;= alpha &lt;= 1.0</code>
     * @return an instance of the colored specified by <code>red</code>,
     *    <code>green</code>, and <code>blue</code> color components in integer
     *    format and <code>alpha</code> as a float
     */
    Color createColor(final int red, final int green, final int blue, final float alpha);
}
