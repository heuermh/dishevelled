/*

    dsh-disclosure-triangle-examples  Examples for the disclosure-triangle library.
    Copyright (c) 2007-2013 held jointly by the individual authors.

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
package org.dishevelled.disclosuretriangle.examples;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import javax.swing.border.EmptyBorder;

import org.dishevelled.layout.LabelFieldLayout;

import org.dishevelled.disclosuretriangle.DisclosureTriangle;

/**
 * DisclosureTriangle example.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class DisclosureTriangleExample
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

        final JFrame f = new JFrame("JFrame example");
        f.setContentPane(createContentPane());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setBounds(120, 120, 400, 200);

        final JDialog d = new JDialog(f, "JDialog example");
        d.setContentPane(createContentPane());
        d.setBounds(140, 140, 400, 200);

        final JDialog desktop = new JDialog(f, "JInternalFrame example");
        JDesktopPane desktopPane = new JDesktopPane();
        JInternalFrame internalFrame = new JInternalFrame("JInternalFrame example");
        internalFrame.setContentPane(createContentPane());
        internalFrame.setBounds(50, 20, 400, 200);
        internalFrame.setVisible(true);
        desktopPane.add(internalFrame);
        desktop.setContentPane(desktopPane);
        desktop.setBounds(160, 160, 500, 300);

        f.setVisible(true);
        d.setVisible(true);
        desktop.setVisible(true);
    }

    /**
     * Create and return a new content pane.
     *
     * @return a new content pane
     */
    private JPanel createContentPane()
    {
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBorder(new EmptyBorder(11, 11, 11, 11));
        contentPane.add("North", new JLabel("North"));
        contentPane.add("Center", new JLabel("Center"));
        JPanel detailsPane = new JPanel();
        LabelFieldLayout l = new LabelFieldLayout();
        detailsPane.setLayout(l);
        detailsPane.setBorder(new EmptyBorder(11, 11, 0, 0));
        detailsPane.add(new JLabel("Foo:"), l.label());
        detailsPane.add(new JTextField(), l.field());
        l.nextLine();
        detailsPane.add(new JLabel("Bar:"), l.label());
        detailsPane.add(new JTextField(), l.field());
        l.nextLine();
        detailsPane.add(new JLabel("Baz:"), l.label());
        detailsPane.add(new JTextField(), l.field());
        l.nextLine();
        detailsPane.add(Box.createGlue(), l.finalSpacing());
        contentPane.add("South", new DisclosureTriangle(detailsPane));
        return contentPane;
    }


    /**
     * Main.
     *
     * @param args command line arguments, ignored
     */
    public static void main(final String[] args)
    {
        SwingUtilities.invokeLater(new DisclosureTriangleExample());
    }
}