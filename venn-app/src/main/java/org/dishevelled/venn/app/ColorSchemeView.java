/*

    dsh-venn-app  App for venn and euler diagrams.
    Copyright (c) 2013-2019 held jointly by the individual authors.

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
package org.dishevelled.venn.app;

import java.awt.BorderLayout;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextField;

import javax.swing.border.EmptyBorder;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.ListSelection;

import org.dishevelled.eventlist.view.IdElementsList;

import org.dishevelled.iconbundle.IconBundle;
import org.dishevelled.iconbundle.IconSize;

import org.dishevelled.iconbundle.impl.svg.SVGIconBundle;

import org.dishevelled.identify.Identifiable;

import org.dishevelled.layout.LabelFieldPanel;

/**
 * Color scheme view.
 *
 * @author  Michael Heuer
 */
final class ColorSchemeView extends JPanel
{
    /** Number of colors. */
    private final int n;

    /** Alpha. */
    private final JTextField alpha;

    /** Color scheme model. */
    private final EventList<IdColorScheme> colorSchemes;

    /** Color scheme list. */
    private final ColorSchemeList colorSchemeList;

    /** Default alpha, 0.20. */
    private static final float DEFAULT_ALPHA = 0.20f;

    /** Custom icon size, 128x32. */
    private static final IconSize ICON_SIZE = new IconSize(324, 24) {}; // 810 x 60 originally

    /** Identifiable color scheme. */
    private class IdColorScheme implements Identifiable
    {
        private final String id;
        private final String name;
        private final IconBundle iconBundle;

        IdColorScheme(final String id,
                      final String name)
        {
            this.id = id;
            this.name = name;
            this.iconBundle = new SVGIconBundle(getClass().getResource("/org/dishevelled/color/scheme/examples/discrete-" + id + "-" + n + ".svg"));
        }

        @Override
        public String getName()
        {
            return name + " with " + n + " colors";
        }

        @Override
        public IconBundle getIconBundle()
        {
            return iconBundle;
        }
    }

    ColorSchemeView(final int n)
    {
        super();

        this.n = n;

        alpha = new JTextField(String.valueOf(DEFAULT_ALPHA));
        colorSchemes = GlazedLists.eventList(new ArrayList<IdColorScheme>());
        colorSchemeList = new ColorSchemeList(colorSchemes);
        
        addColorSchemes();
        layoutComponents();
    }

    private void addColorSchemes()
    {
        colorSchemes.add(new IdColorScheme("aaas", "AAAS"));
        colorSchemes.add(new IdColorScheme("accent", "Accent"));
        colorSchemes.add(new IdColorScheme("category", "Category"));
        colorSchemes.add(new IdColorScheme("dark-2", "Dark 2"));
        colorSchemes.add(new IdColorScheme("gut", "Gut"));
        colorSchemes.add(new IdColorScheme("jama", "JAMA"));
        colorSchemes.add(new IdColorScheme("jco", "JCO"));
        colorSchemes.add(new IdColorScheme("lancet", "Lancet"));
        colorSchemes.add(new IdColorScheme("nejm", "NEJM"));
        colorSchemes.add(new IdColorScheme("npg", "NPG"));
        colorSchemes.add(new IdColorScheme("pastel-1", "Pastel 1"));
        colorSchemes.add(new IdColorScheme("pastel-2", "Pastel 2"));
        colorSchemes.add(new IdColorScheme("set-1", "Set 1"));
        colorSchemes.add(new IdColorScheme("set-2", "Set 2"));
        colorSchemes.add(new IdColorScheme("set-3", "Set 3"));
        colorSchemes.add(new IdColorScheme("spectral", "Spectral"));
        colorSchemes.add(new IdColorScheme("syn", "Syn"));
        colorSchemes.add(new IdColorScheme("tableau", "Tableau"));
        colorSchemes.add(new IdColorScheme("unambiguous", "Unambiguous"));
    }

    private void layoutComponents()
    {
        LabelFieldPanel mainPanel = new LabelFieldPanel();
        mainPanel.setBorder(new EmptyBorder(12, 12, 0, 12));

        mainPanel.addField("Number of colors: ", String.valueOf(n));
        mainPanel.addField("Alpha (in range [0.0, 1.0]):", alpha);
        mainPanel.addSpacing(20);
        mainPanel.addFinalField(colorSchemeList);

        setLayout(new BorderLayout());
        add("Center", mainPanel);
    }

    private class ColorSchemeList extends IdElementsList
    {
        private ColorSchemeList(final EventList<IdColorScheme> colorSchemes)
        {
            super("Color schemes", colorSchemes);

            setIconSize(ICON_SIZE);
            getSelectionModel().setSelectionMode(ListSelection.SINGLE_SELECTION);

            // remove Select all
            getContextMenu().remove(4);
            // remove Invert selection
            getContextMenu().remove(5);
        }
    }
}
