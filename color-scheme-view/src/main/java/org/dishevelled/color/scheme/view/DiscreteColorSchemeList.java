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

import java.util.Locale;
import java.util.ResourceBundle;

import ca.odell.glazedlists.EventList;

import org.dishevelled.color.scheme.impl.DiscreteColorScheme;

import org.dishevelled.eventlist.view.ElementsList;

/**
 * Discrete color scheme list.
 *
 * @author  Michael Heuer
 */
public class DiscreteColorSchemeList extends ElementsList<DiscreteColorScheme>
{
    /** I18n. */
    private static final ResourceBundle I18N = ResourceBundle.getBundle("dsh-color-scheme-view", Locale.getDefault());


    /**
     * Create a new discrete color scheme list with the specified discrete color schemes.
     *
     * @param colorSchemes discrete color schemes, must not be null
     */
    public DiscreteColorSchemeList(final EventList<DiscreteColorScheme> colorSchemes)
    {
        super(I18N.getString("DiscreteColorSchemeList.discreteColorSchemes") + ":", colorSchemes);
        getList().setCellRenderer(new DiscreteColorSchemeListCellRenderer());
    }


    @Override
    public void add()
    {
        DiscreteColorScheme colorScheme = DiscreteColorSchemeChooser.showDialog(this, I18N.getString("DiscreteColorSchemeList.createANewDiscreteColorScheme"));
        if (colorScheme != null)
        {
            getModel().add(colorScheme);
        }
    }
}
