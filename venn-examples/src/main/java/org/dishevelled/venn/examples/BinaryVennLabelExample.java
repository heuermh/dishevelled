/*

    dsh-venn-examples  Examples for the venn library.
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
package org.dishevelled.venn.examples;

import java.awt.BorderLayout;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import javax.swing.border.EmptyBorder;

import org.dishevelled.venn.BinaryVennModel;

import org.dishevelled.venn.model.BinaryVennModelImpl;

import org.dishevelled.venn.swing.BinaryVennLabel;

/**
 * Binary venn label example.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class BinaryVennLabelExample
    extends JPanel
    implements Runnable
{

    /**
     * Create a new binary venn label example.
     */
    public BinaryVennLabelExample()
    {
        super();
        setLayout(new BorderLayout());

        Set<String> set0 = new HashSet<String>();
        set0.add("foo");
        set0.add("bar");
        set0.add("baz");

        Set<String> set1 = new HashSet<String>();
        set1.add("bar");
        set1.add("baz");
        set1.add("qux");
        set1.add("garply");

        BinaryVennModel<String> model = new BinaryVennModelImpl<String>(set0, set1);
        BinaryVennLabel label = new BinaryVennLabel(model);
        add("Center", label);
        setBorder(new EmptyBorder(12, 12, 12, 12));
    }


    /** {@inheritDoc} */
    public void run()
    {
        final JFrame f = new JFrame("BinaryVennLabel example");
        f.setContentPane(this);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setBounds(120, 120, 400, 200);
        f.setVisible(true);
    }

    /**
     * Main.
     *
     * @param args command line arguments, ignored
     */
    public static void main(final String[] args)
    {
        SwingUtilities.invokeLater(new BinaryVennLabelExample());
    }
}
