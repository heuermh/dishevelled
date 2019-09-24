/*

    dsh-color-scheme-view  Views for color schemes.
    Copyright (c) 2019 held jointly by the individual authors.

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
package org.dishevelled.color.scheme.view;

import java.awt.Color;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JColorChooser;
import javax.swing.SwingUtilities;

import ca.odell.glazedlists.EventList;

import org.dishevelled.eventlist.view.ElementsList;

/**
 * Color list.
 *
 * @author  Michael Heuer
 */
public class ColorList extends ElementsList<Color>
{
    /** I18n. */
    private static final ResourceBundle I18N = ResourceBundle.getBundle("color-scheme-view", Locale.getDefault());


    /**
     * Create a new color list with the specified colors.
     *
     * @param colors colors, must not be null
     */
    public ColorList(final EventList<Color> colors)
    {
        super(I18N.getString("ColorList.colors") + ":", colors);
        getList().setCellRenderer(new ColorListCellRenderer());
    }


    @Override
    public void add()
    {
        Color color = JColorChooser.showDialog(this, I18N.getString("ColorList.selectAColor") + ":", null);
        if (color != null)
        {
            getModel().add(color);
        }
    }
}
