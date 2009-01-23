/*

    dsh-curate-examples  Examples for the curate library.
    Copyright (c) 2007-2009 held jointly by the individual authors.

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
package org.dishevelled.curate.examples;

import java.awt.BorderLayout;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.dishevelled.curate.impl.CullDialog;
import org.dishevelled.curate.impl.CullPanel;

/**
 * Cull view example.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class CullViewExample
    extends JPanel
    implements Runnable
{
    /** List of strings. */
    private final List<String> strings;

    /** Cull dialog. */
    private CullDialog<String> cullDialog;

    /** Cull panel. */
    private CullPanel<String> cullPanel;


    /**
     * Cull view example.
     */
    public CullViewExample()
    {
        strings = new ArrayList<String>();
        for (int i = 0; i < 100; i++)
        {
            strings.add("string" + i);
        }

        cullDialog = new CullDialog<String>();
        cullDialog.setTitle("Cull view example dialog");
        cullPanel = new CullPanel<String>();

        cullDialog.setInput(strings);
        cullPanel.setInput(strings);

        setLayout(new BorderLayout());
        add("Center", cullPanel);
    }


    /** {@inheritDoc} */
    public void run()
    {
        JFrame f = new JFrame("Cull view example");
        f.setContentPane(this);
        f.setBounds(100, 100, 400, 400);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);

        cullDialog.setBounds(200, 200, 400, 400);
        cullDialog.setVisible(true);
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
                    new CullViewExample().run();
                }
            });
    }
}