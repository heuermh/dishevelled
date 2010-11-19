/*

    dsh-iconbundle-tango-examples  Examples using the iconbundle-tango library.
    Copyright (c) 2005-2010 held jointly by the individual authors.

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
package org.dishevelled.iconbundle.tango.examples;

import java.awt.BorderLayout;
import java.awt.Image;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

import org.dishevelled.iconbundle.IconState;
import org.dishevelled.iconbundle.IconTextDirection;

import org.dishevelled.iconbundle.tango.TangoProject;

import org.dishevelled.identify.IdentifiableAction;
import org.dishevelled.identify.IdButton;
import org.dishevelled.identify.IdToolBar;

/**
 * Example that mimics the gedit tool bar.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class GeditToolBarExample
    extends JPanel
    implements Runnable
{
    /** Menu bar. */
    private final JMenuBar menuBar = new JMenuBar();

    /** Tool bar. */
    private final IdToolBar toolBar = new IdToolBar();

    /** Recent documents. */
    private final JPopupMenu recentDocuments = new JPopupMenu();

    /** Text pane. */
    private final JTextPane textPane = new JTextPane();

    /** Create action. */
    private final IdentifiableAction create = new IdentifiableAction("Create a new document", TangoProject.DOCUMENT_NEW)
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                // empty
            }
        };

    /** Open action. */
    private final IdentifiableAction open = new IdentifiableAction("Open", TangoProject.DOCUMENT_OPEN)
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                // empty
            }
        };

    /** Save action. */
    private final IdentifiableAction save = new IdentifiableAction("Save", TangoProject.DOCUMENT_SAVE)
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                // empty
            }
        };

    /** Print action. */
    private final IdentifiableAction print = new IdentifiableAction("Print", TangoProject.DOCUMENT_PRINT)
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                // empty
            }
        };

    /** Undo action. */
    private final IdentifiableAction undo = new IdentifiableAction("Undo", TangoProject.EDIT_UNDO)
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                // empty
            }
        };

    /** Redo action. */
    private final IdentifiableAction redo = new IdentifiableAction("Redo", TangoProject.EDIT_REDO)
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                // empty
            }
        };

    /** Cut action. */
    private final IdentifiableAction cut = new IdentifiableAction("Cut", TangoProject.EDIT_CUT)
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                // empty
            }
        };

    /** Copy action. */
    private final IdentifiableAction copy = new IdentifiableAction("Copy", TangoProject.EDIT_COPY)
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                // empty
            }
        };

    /** Paste action. */
    private final IdentifiableAction paste = new IdentifiableAction("Paste", TangoProject.EDIT_PASTE)
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                // empty
            }
        };

    /** Search action. */
    private final IdentifiableAction find = new IdentifiableAction("Search", TangoProject.EDIT_FIND)
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                // empty
            }
        };

    /** Search and replace action. */
    private final IdentifiableAction findReplace = new IdentifiableAction("Search and replace", TangoProject.EDIT_FIND_REPLACE)
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                // empty
            }
        };


    /**
     * Create a new gedit tool bar example.
     */
    public GeditToolBarExample()
    {
        super();
        initComponents();
        layoutComponents();
    }


    /**
     * Initialize components.
     */
    private void initComponents()
    {
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        JMenu view = new JMenu("View");
        JMenu search = new JMenu("Search");
        JMenu tools = new JMenu("Tools");
        JMenu documents = new JMenu("Documents");
        JMenu help = new JMenu("Help");

        file.setMnemonic(KeyEvent.VK_F);
        edit.setMnemonic(KeyEvent.VK_E);
        view.setMnemonic(KeyEvent.VK_V);
        search.setMnemonic(KeyEvent.VK_S);
        tools.setMnemonic(KeyEvent.VK_T);
        documents.setMnemonic(KeyEvent.VK_D);
        help.setMnemonic(KeyEvent.VK_H);

        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(view);
        menuBar.add(search);
        menuBar.add(tools);
        menuBar.add(documents);
        menuBar.add(help);

        JMenuItem noItemsFound = new JMenuItem("No items found");
        noItemsFound.setEnabled(false);
        recentDocuments.add(noItemsFound);

        undo.setEnabled(false);
        redo.setEnabled(false);
        cut.setEnabled(false);
        copy.setEnabled(false);
        paste.setEnabled(false);

        toolBar.add(create);
        IdButton openButton = toolBar.add(open);
        openButton.displayIconAndText();
        toolBar.add(recentDocuments);
        IdButton saveButton = toolBar.add(save);
        saveButton.displayIconAndText();
        toolBar.addSeparator();
        toolBar.add(print);
        toolBar.addSeparator();
        IdButton undoButton = toolBar.add(undo);
        undoButton.displayIconAndText();
        toolBar.add(redo);
        toolBar.addSeparator();
        toolBar.add(cut);
        toolBar.add(copy);
        toolBar.add(paste);
        toolBar.addSeparator();
        toolBar.add(find);
        toolBar.add(findReplace);
        toolBar.setIconSize(TangoProject.SMALL);
    }

    /**
     * Layout components.
     */
    private void layoutComponents()
    {
        setLayout(new BorderLayout());
        add("North", toolBar);
        add("Center", createTabbedPane());
    }

    /**
     * Create and return a new tabbed pane.
     *
     * @return a new tabbed pane
     */
    private JTabbedPane createTabbedPane()
    {
        JTabbedPane tabbedPane = new JTabbedPane();
        Image documentImage = TangoProject.TEXT_X_GENERIC.getImage(null, IconTextDirection.LEFT_TO_RIGHT, IconState.NORMAL, TangoProject.EXTRA_SMALL);
        Icon documentIcon = new ImageIcon(documentImage);
        tabbedPane.addTab("Unsaved Document 1", documentIcon, new JScrollPane(textPane));
        return tabbedPane;
    }

    /** {@inheritDoc} */
    public void run()
    {
        JFrame f = new JFrame("Unsaved Document 1 - gedit");
        f.setContentPane(this);
        f.setJMenuBar(menuBar);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setBounds(100, 100, 750, 650);
        f.setVisible(true);
    }


    /**
     * Main.
     *
     * @param args command line arguments, ignored
     */
    public static void main(final String[] args)
    {
        SwingUtilities.invokeLater(new GeditToolBarExample());
    }
}