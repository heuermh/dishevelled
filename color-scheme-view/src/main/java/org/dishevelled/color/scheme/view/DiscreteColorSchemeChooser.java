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

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;

import java.util.Collections;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import javax.swing.border.EmptyBorder;

import ca.odell.glazedlists.GlazedLists;

import org.dishevelled.color.scheme.impl.DiscreteColorScheme;

import org.dishevelled.color.scheme.factory.CachingColorFactory;

import org.dishevelled.layout.LabelFieldPanel;

/**
 * Discrete color scheme chooser.
 *
 * @author  Michael Heuer
 */
public class DiscreteColorSchemeChooser extends LabelFieldPanel
{
    /** I18n. */
    private static final ResourceBundle I18N = ResourceBundle.getBundle("color-scheme-view", Locale.getDefault());

    /** Name. */
    private final JTextField name;

    /** Color list. */
    private final ColorList colorList;


    /**
     * Create a new discrete color scheme chooser.
     */
    public DiscreteColorSchemeChooser()
    {
        super();
        name = new JTextField();
        colorList = new ColorList(GlazedLists.eventList(Collections.emptyList()));

        layoutComponents();
    }


    /**
     * Layout components.
     */
    private void layoutComponents()
    {
        setBorder(new EmptyBorder(12, 12, 12, 12));

        addField(I18N.getString("DiscreteColorSchemeChooser.name") + ":", name);
        addSpacing(20);
        addFinalField(colorList);
    }

    /**
     * Return the name text field for this discrete color scheme chooser.
     *
     * @return the name text field for this discrete color scheme chooser
     */
    JTextField name()
    {
        return name;
    }

    /**
     * Return the color list for this discrete color scheme chooser.
     *
     * @return the color list for this discrete color scheme chooser
     */
    ColorList colorList()
    {
        return colorList;
    }

    /**
     * Return true if name and color list are valid.
     *
     * @return true if name and color list are valid
     */
    protected boolean ready()
    {
        return colorList.getModel().size() >= 2 && name.getText().length() > 0;
    }

    /**
     * Return the color scheme for this discrete color scheme chooser, if any.
     *
     * @return the color scheme for this discrete color scheme chooser, if any
     */
    DiscreteColorScheme getColorScheme()
    {
        if (ready())
        {
            return new DiscreteColorScheme(name.getText(), colorList.getModel(), 0.0d, 1.0d, new CachingColorFactory());
        }
        else
        {
            return null;
        }
    }

    /**
     * Show dialog.
     *
     * @param component component
     * @param title title
     * @return dialog
     */
    public static DiscreteColorScheme showDialog(final Component component, final String title)
    {
        DiscreteColorSchemeChooser chooserPane = new DiscreteColorSchemeChooser();
        DiscreteColorSchemeChooserDialog dialog = createDialog(component, title, true, chooserPane);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.show();
        return dialog.getColorScheme();
    }

    /**
     * Create dialog.
     *
     * @param component component
     * @param title title
     * @param modal modal
     * @param chooserPane chooser pane
     * @return dialog
     */
    public static DiscreteColorSchemeChooserDialog createDialog(final Component component, final String title, final boolean modal, final DiscreteColorSchemeChooser chooserPane)
    {
        Window window = SwingUtilities.windowForComponent(component);
        DiscreteColorSchemeChooserDialog dialog;
        if (window instanceof Frame)
        {
            dialog = new DiscreteColorSchemeChooserDialog((Frame) window, title, modal, chooserPane);
        }
        else //if (window instanceof Dialog)
        {
            dialog = new DiscreteColorSchemeChooserDialog((Dialog) window, title, modal, chooserPane);
        }
        dialog.getAccessibleContext().setAccessibleDescription(title);
        return dialog;       
    }
}
