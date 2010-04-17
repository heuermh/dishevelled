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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.HashSet;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import javax.swing.border.EmptyBorder;

import org.dishevelled.iconbundle.tango.TangoProject;

import org.dishevelled.identify.IdentifiableAction;
import org.dishevelled.identify.IdMenuItem;

import org.dishevelled.layout.LabelFieldPanel;

import org.dishevelled.venn.BinaryVennModel3;
import org.dishevelled.venn.TernaryVennModel3;

import org.dishevelled.venn.model.BinaryVennModelImpl3;
import org.dishevelled.venn.model.TernaryVennModelImpl3;

import org.dishevelled.venn.swing.BinaryVennList3;
import org.dishevelled.venn.swing.TernaryVennList3;

/**
 * Venn list example.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class VennListExample
    extends LabelFieldPanel
    implements Runnable
{
    /** Clear selection action. */
    private final Action clearSelection;

    /** Select all action. */
    private final IdentifiableAction selectAll;

    /** Exit action. */
    private final Action exit;

    /** Binary venn list. */
    private final BinaryVennList3 binaryList;


    /**
     * Create a new venn list example.
     */
    public VennListExample()
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
        binaryList = new BinaryVennList3(binaryModel);

        TernaryVennModel3<String> ternaryModel = new TernaryVennModelImpl3<String>(set0, set1, set2);
        TernaryVennList3 ternaryList = new TernaryVennList3(ternaryModel);

        addLabel("Binary:");
        addField(binaryList);
        addSpacing(12);
        //addLabel("Ternary:");
        //addField(ternaryList);
        addFinalSpacing();

        exit = new AbstractAction("Exit")
            {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent event)
                {
                    System.exit(0);
                }
            };
        clearSelection = new AbstractAction("Clear Selection")
            {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent e)
                {
                    binaryList.clearSelection();
                }
            };
        selectAll = new IdentifiableAction("Select All", TangoProject.EDIT_SELECT_ALL)
            {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent e)
                {
                    binaryList.selectAll();
                }
            };

        Timer t1 = new Timer(5000, new ActionListener()
            {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent e)
                {
                    binaryList.getModel().first().add("waldo" + binaryList.getModel().first().size());
                }
            });
        t1.setRepeats(true);
        t1.start();

        Timer t2 = new Timer(8000, new ActionListener()
            {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent e)
                {
                    binaryList.getModel().second().add("waldo" + binaryList.getModel().second().size());
                }
            });
        t2.setRepeats(true);
        t2.start();

        setBorder(new EmptyBorder(12, 12, 12, 12));
    }

    /**
     * Create and return a new menu bar.
     *
     * @return a new menu bar
     */
    private JMenuBar createMenuBar()
    {
        JMenu file = new JMenu("File");
        file.add(exit);

        JMenu edit = new JMenu("Edit");
        edit.add(clearSelection);
        edit.add(new IdMenuItem(selectAll, TangoProject.EXTRA_SMALL));

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(file);
        menuBar.add(edit);
        return menuBar;
    }


    /** {@inheritDoc} */
    public void run()
    {
        final JFrame f = new JFrame("VennList example");
        f.setContentPane(this);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setJMenuBar(createMenuBar());
        f.setBounds(120, 120, 800, 600);
        f.setVisible(true);
    }


    /**
     * Main.
     *
     * @param args command line arguments, ignored
     */
    public static void main(final String[] args)
    {
        SwingUtilities.invokeLater(new VennListExample());
    }
}
