/*

    dsh-venn-examples  Examples for the venn library.
    Copyright (c) 2009-2010 held jointly by the individual authors.

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
package org.dishevelled.venn.examples;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import javax.swing.border.EmptyBorder;

import org.dishevelled.layout.LabelFieldPanel;

import org.dishevelled.venn.BinaryVennModel3;
import org.dishevelled.venn.TertiaryVennModel3;

import org.dishevelled.venn.model.BinaryVennModelImpl3;
import org.dishevelled.venn.model.TertiaryVennModelImpl3;

import org.dishevelled.venn.swing.BinaryVennLabel3;
import org.dishevelled.venn.swing.TertiaryVennLabel3;

/**
 * Venn label example.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class VennLabelExample
    extends LabelFieldPanel
    implements Runnable
{

    /**
     * Create a new venn label example.
     */
    public VennLabelExample()
    {
        super();

        Set<String> set0 = new HashSet<String>();
        set0.add("foo");
        set0.add("bar");
        set0.add("baz");

        Set<String> set1 = new HashSet<String>();
        set1.add("bar");
        set1.add("baz");
        set1.add("qux");
        set1.add("garply");

        Set<String> set2 = new HashSet<String>();
        set2.add("bar");
        set2.add("baz");
        set2.add("garply");

        BinaryVennModel3<String> binaryModel = new BinaryVennModelImpl3<String>(set0, set1);
        BinaryVennLabel3<String> binaryLabel = new BinaryVennLabel3<String>(binaryModel);

        TertiaryVennModel3<String> tertiaryModel = new TertiaryVennModelImpl3<String>(set0, set1, set2);
        TertiaryVennLabel3<String> tertiaryLabel = new TertiaryVennLabel3<String>(tertiaryModel);

        addLabel("Binary:");
        addField(binaryLabel);
        addSpacing(12);
        addLabel("Tertiary:");
        addField(tertiaryLabel);
        addFinalSpacing();

        setBorder(new EmptyBorder(12, 12, 12, 12));
    }


    /** {@inheritDoc} */
    public void run()
    {
        final JFrame f = new JFrame("VennLabel example");
        f.setContentPane(this);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setBounds(120, 120, 500, 600);
        f.setVisible(true);
    }


    /**
     * Main.
     *
     * @param args command line arguments, ignored
     */
    public static void main(final String[] args)
    {
        SwingUtilities.invokeLater(new VennLabelExample());
    }
}
