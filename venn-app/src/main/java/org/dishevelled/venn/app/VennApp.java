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

import java.awt.Toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JPopupMenu.Separator;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * App for venn and euler diagrams.
 *
 * @author  Michael Heuer
 */
public final class VennApp
    implements Runnable
{
    /** I18n. */
    private static final ResourceBundle I18N = ResourceBundle.getBundle("VennApp", Locale.getDefault());

    /** Groups view. */
    private final GroupsView groupsView;

    /** Exit action. */
    private final Action exit = new AbstractAction(I18N.getString("VennApp.exit"))
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                exit();
            }
        };

    /** Open action. */
    private final Action open = new AbstractAction(I18N.getString("VennApp.open") + "...")
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                open();
            }
        };


    /**
     * Create a new app for venn and euler diagrams.
     *
     * @param args command line arguments
     */
    public VennApp(final String[] args)
    {
        super();
        this.groupsView = new GroupsView();

        SwingUtilities.invokeLater(new Runnable()
            {
                @Override
                public void run()
                {
                    for (String arg : args)
                    {
                        File file = new File(arg);
                        Group group = readFile(file);
                        groupsView.addGroup(group);
                    }
                }
            });
    }


    /**
     * Exit.
     */
    private static void exit()
    {
        System.exit(0);
    }

    /**
     * Open zero or more text files and load them into the groups view.
     */
    private void open()
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        int returnVal = fileChooser.showOpenDialog(groupsView);

        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            for (File file: fileChooser.getSelectedFiles())
            {
                Group group = readFile(file);
                groupsView.addGroup(group);
            }
        }
    }

    /**
     * Read the specified file into a group.
     *
     * @param file file to read
     * @return the specified file read into a group
     */
    private static Group readFile(final File file)
    {
        BufferedReader reader = null;
        String name = file.toString();
        List<String> values = new ArrayList<String>();
        try
        {
            reader = new BufferedReader(new FileReader(file));
            while (reader.ready())
            {
                String line = reader.readLine();
                if (line == null)
                {
                    break;
                }
                values.add(line);
            }
        }
        catch (IOException e)
        {
            // ignore
        }
        finally
        {
            try
            {
                reader.close();
            }
            catch (Exception e)
            {
                // ignore
            }
        }
        return new Group(name, values);
    }

    /**
     * Create and return the menu bar.
     */
    private JMenuBar createMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu(I18N.getString("VennApp.file"));

        int menuKeyMask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
        JMenuItem openMenuItem = fileMenu.add(open);
        openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, menuKeyMask));

        // osx doesn't need an exit menu item, Quit VennApp [Q] is present in VennApp application menu
        fileMenu.addSeparator();
        fileMenu.add(exit);

        menuBar.add(fileMenu);
        return menuBar;
    }

    @Override
    public void run()
    {
        JFrame frame = new JFrame(I18N.getString("VennApp.frame"));
        frame.setContentPane(groupsView);
        frame.setJMenuBar(createMenuBar());

        JMenuItem openMenuItem = new JMenuItem(open);
        groupsView.getContextMenu().add(openMenuItem, 0);
        int menuKeyMask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
        openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, menuKeyMask));
        groupsView.getContextMenu().add(new JPopupMenu.Separator(), 1);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(666, 500);
        frame.setVisible(true);
    }

    /**
     * Main.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args)
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e)
        {
            // ignore
        }

        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.macos.useScreenMenuBar", "true");
        System.setProperty("com.apple.macos.use-file-dialog-packages", "true");
        System.setProperty("apple.awt.application.name", "VennApp");

        SwingUtilities.invokeLater(new VennApp(args));
    }
}
