/*

    dsh-venn-app  App for venn and euler diagrams.
    Copyright (c) 2013 held jointly by the individual authors.

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

import java.awt.Toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

/**
 * App for venn and euler diagrams.
 */
public final class VennApp
    implements Runnable
{
    /** Groups view. */
    private final GroupsView groupsView;

    /** Open action. */
    private final Action open = new AbstractAction("Open...")
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                open();
            }
        };


    /**
     * Create a new app for venn and euler diagrams.
     */
    public VennApp()
    {
        super();
        this.groupsView = new GroupsView();
    }


    private void open()
    {
        // groupsView.addGroup(readFile(fileDialog()));
    }

    private JMenuBar createMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        int menuKeyMask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
        JMenuItem openMenuItem = fileMenu.add(open);
        openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, menuKeyMask));

        // osx doesn't need an exit menu item, Quit VennApp [Q] is present in VennApp application menu
        fileMenu.addSeparator();
        JMenuItem exitMenuItem = new JMenuItem("Exit");

        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);

        return menuBar;
    }

    @Override
    public void run()
    {
        JFrame frame = new JFrame("Venn and Euler Diagrams");
        frame.setContentPane(groupsView);
        frame.setJMenuBar(createMenuBar());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(200, 200, 720, 540);
        frame.setVisible(true);
    }

    /**
     * Main.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args)
    {
        System.setProperty("apple.laf.useScreenMenuBar", "true");

        // todo:  open files from command line args
        SwingUtilities.invokeLater(new VennApp());
    }
}