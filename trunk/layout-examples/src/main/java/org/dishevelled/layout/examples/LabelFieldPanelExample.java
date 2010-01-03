/*

    dsh-layout-examples  Examples for the layout library.
    Copyright (c) 2008-2010 held jointly by the individual authors.

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

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import javax.swing.border.EmptyBorder;

import org.dishevelled.layout.LabelFieldPanel;

/**
 * LabelFieldPanel example.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class LabelFieldPanelExample
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

        final JFrame finalField = new JFrame("Final field example");
        finalField.setContentPane(createFinalFieldPane());
        finalField.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        finalField.setBounds(200, 200, 380, 480);

        finalSpacing.setVisible(true);
        finalField.setVisible(true);
    }

    /**
     * Create and return a new final spacing content pane.
     *
     * @return a new final spacing content pane
     */
    private JPanel createFinalSpacingPane()
    {
        LabelFieldPanel contentPane = new LabelFieldPanel();
        contentPane.setBorder(new EmptyBorder(11, 11, 11, 11));
        contentPane.addField("Label label 0:", "Label field 0");
        contentPane.addField("Label label 1:", "Label field 1");
        contentPane.addField("Label label 2:", "Label field 2");
        contentPane.addField("Normal label 0:", new JTextField("Normal field 0"));
        contentPane.addField("Normal label 1:", new JTextField("Normal field 1"));
        contentPane.addSpacing(12);
        contentPane.addLabel("Wide label");
        contentPane.addFinalSpacing();
        return contentPane;
    }

    /**
     * Create and return a new final field content pane.
     *
     * @return a new final field content pane
     */
    private JPanel createFinalFieldPane()
    {
        LabelFieldPanel contentPane = new LabelFieldPanel();
        contentPane.setBorder(new EmptyBorder(11, 11, 11, 11));
        contentPane.addField("Label label 0:", "Label field 0");
        contentPane.addField("Label label 1:", "Label field 1");
        contentPane.addField("Label label 2:", "Label field 2");
        contentPane.addField("Normal label 0:", new JTextField("Normal field 0"));
        contentPane.addField("Normal label 1:", new JTextField("Normal field 1"));
        contentPane.addSpacing(12);
        contentPane.addLabel("Wide label");
        contentPane.addSpacing(12);
        contentPane.addLabel("Final field:");
        JList list = new JList(new Object[] { "Final field A",
                                              "Final field B",
                                              "Final field C" });
        contentPane.addFinalField(new JScrollPane(list));
        return contentPane;
    }


    /**
     * Main.
     *
     * @param args command line arguments, ignored
     */
    public static void main(final String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
            {
                /** {@inheritDoc} */
                public void run()
                {
                    Runnable r = new LabelFieldPanelExample();
                    r.run();
                }
            });
    }
}