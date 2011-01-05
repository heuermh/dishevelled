/*

    dsh-layout-examples  Examples for the layout library.
    Copyright (c) 2008-2011 held jointly by the individual authors.

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
package org.dishevelled.layout.examples;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import javax.swing.border.EmptyBorder;

import org.dishevelled.layout.LabelFieldLayout;

/**
 * LabelFieldLayout example.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class LabelFieldLayoutExample
    implements Runnable
{

    /** {@inheritDoc} */
    public void run()
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e)
        {
            // ignore
        }

        final JFrame finalSpacing = new JFrame("Final spacing example");
        finalSpacing.setContentPane(createFinalSpacingPane());
        finalSpacing.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        finalSpacing.setBounds(120, 120, 380, 480);

        final JFrame finalWideField = new JFrame("Final wide field example");
        finalWideField.setContentPane(createFinalWideFieldPane());
        finalWideField.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        finalWideField.setBounds(200, 200, 380, 480);

        finalSpacing.setVisible(true);
        finalWideField.setVisible(true);
    }

    /**
     * Create and return a new final spacing content pane.
     *
     * @return a new final spacing content pane
     */
    private JPanel createFinalSpacingPane()
    {
        JPanel contentPane = new JPanel();
        LabelFieldLayout l = new LabelFieldLayout();
        contentPane.setLayout(l);
        contentPane.setBorder(new EmptyBorder(11, 11, 11, 11));

        contentPane.add(new JLabel("Label label 0:"), l.labelLabel());
        contentPane.add(new JLabel("Label field 0"), l.labelField());

        l.nextLine();
        contentPane.add(new JLabel("Label label 1:"), l.labelLabel());
        contentPane.add(new JLabel("Label field 1"), l.labelField());

        l.nextLine();
        contentPane.add(new JLabel("Label label 2:"), l.labelLabel());
        contentPane.add(new JLabel("Label field 2"), l.labelField());

        l.nextLine();
        contentPane.add(new JLabel("Normal label 0:"), l.normalLabel());
        contentPane.add(new JTextField("Normal field 0"), l.normalField());

        l.nextLine();
        contentPane.add(new JLabel("Normal label 1:"), l.normalLabel());
        contentPane.add(new JTextField("Normal field 1"), l.normalField());

        l.nextLine();
        contentPane.add(Box.createVerticalStrut(12), l.spacing());

        l.nextLine();
        contentPane.add(new JLabel("Wide label"), l.wideLabel());

        l.nextLine();
        contentPane.add(Box.createGlue(), l.finalSpacing());
        return contentPane;
    }

    /**
     * Create and return a new final wide field content pane.
     *
     * @return a new final wide field content pane
     */
    private JPanel createFinalWideFieldPane()
    {
        JPanel contentPane = new JPanel();
        LabelFieldLayout l = new LabelFieldLayout();
        contentPane.setLayout(l);
        contentPane.setBorder(new EmptyBorder(11, 11, 11, 11));

        contentPane.add(new JLabel("Label label 0:"), l.labelLabel());
        contentPane.add(new JLabel("Label field 0"), l.labelField());

        l.nextLine();
        contentPane.add(new JLabel("Label label 1:"), l.labelLabel());
        contentPane.add(new JLabel("Label field 1"), l.labelField());

        l.nextLine();
        contentPane.add(new JLabel("Label label 2:"), l.labelLabel());
        contentPane.add(new JLabel("Label field 2"), l.labelField());

        l.nextLine();
        contentPane.add(new JLabel("Normal label 0:"), l.normalLabel());
        contentPane.add(new JTextField("Normal field 0"), l.normalField());

        l.nextLine();
        contentPane.add(new JLabel("Normal label 1:"), l.normalLabel());
        contentPane.add(new JTextField("Normal field 1"), l.normalField());

        l.nextLine();
        contentPane.add(Box.createVerticalStrut(12), l.spacing());

        l.nextLine();
        contentPane.add(new JLabel("Wide label"), l.wideLabel());

        l.nextLine();
        contentPane.add(Box.createVerticalStrut(12), l.spacing());

        l.nextLine();
        contentPane.add(new JLabel("Final wide field:"), l.wideLabel());

        l.nextLine();
        JList list = new JList(new Object[] { "Final wide field A",
                                              "Final wide field B",
                                              "Final wide field C" });
        contentPane.add(new JScrollPane(list), l.finalWideField());
        return contentPane;
    }


    /**
     * Main.
     *
     * @param args command line arguments, ignored
     */
    public static void main(final String[] args)
    {
        SwingUtilities.invokeLater(new LabelFieldLayoutExample());
    }
}