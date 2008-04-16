/*

    dsh-iconbundle-tango-examples  Examples using the iconbundle-tango library.
    Copyright (c) 2005-2008 held jointly by the individual authors.

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
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import javax.swing.border.EmptyBorder;

import javax.swing.tree.TreeSelectionModel;

import org.dishevelled.iconbundle.IconSize;

import org.dishevelled.iconbundle.tango.TangoProject;

import org.dishevelled.identify.Identifiable;
import org.dishevelled.identify.IdentifiableAction;
import org.dishevelled.identify.IdLabel;
import org.dishevelled.identify.IdMenuItem;
import org.dishevelled.identify.IdToolBar;
import org.dishevelled.identify.IdTreeCellRenderer;

import org.dishevelled.layout.LabelFieldLayout;

/**
 * Tango example.
 */
public final class TangoExample
    extends JPanel
    implements Runnable
{
    /** Context tree. */
    private ContextTree contextTree;

    /** Selected Identifiable. */
    private Identifiable selection;

    /** Copy action. */
    private IdentifiableAction copy;

    /** Cut action. */
    private IdentifiableAction cut;

    /** Paste action. */
    private IdentifiableAction paste;

    /** Exit action. */
    private Action exit;

    /** Context menu. */
    private JPopupMenu contextMenu;


    /**
     * Create a new tango example.
     */
    public TangoExample()
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
        contextTree = new ContextTree();
        selection = (Identifiable) contextTree.getModel().getRoot();

        contextTree.addTreeSelectionListener(new TreeSelectionListener()
            {
                /** {@inheritDoc} */
                public void valueChanged(final TreeSelectionEvent e)
                {
                    if (e.isAddedPath())
                    {
                        setSelection((Identifiable) e.getPath().getLastPathComponent());
                    }
                    else
                    {
                        setSelection((Identifiable) ((ContextTree) e.getSource()).getModel().getRoot());
                    }
                }
            });

        copy = new IdentifiableAction("Copy", TangoProject.EDIT_COPY)
            {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent e)
                {
                    // empty
                }
            };

        cut = new IdentifiableAction("Cut", TangoProject.EDIT_CUT)
            {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent e)
                {
                    // empty
                }
            };

        paste = new IdentifiableAction("Paste", TangoProject.EDIT_PASTE)
            {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent e)
                {
                    // empty
                }
            };

        paste.setEnabled(false);

        exit = new AbstractAction("Exit")
            {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent event)
                {
                    System.exit(0);
                }
            };

        contextMenu = createContextMenu();

        contextTree.addMouseListener(new MouseAdapter()
            {
                /** {@inheritDoc} */
                public void mousePressed(final MouseEvent event)
                {
                    if (event.isPopupTrigger())
                    {
                        showContextMenu(event);
                    }
                }

                /** {@inheritDoc} */
                public void mouseReleased(final MouseEvent event)
                {
                    if (event.isPopupTrigger())
                    {
                        showContextMenu(event);
                    }
                }

                /**
                 * Show the context menu.
                 *
                 * @param event mouse event
                 */
                private void showContextMenu(final MouseEvent event)
                {
                    contextMenu.show(contextTree, event.getX(), event.getY());
                }
            });
    }

    /**
     * Layout components.
     */
    private void layoutComponents()
    {
        setLayout(new BorderLayout());
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, createContextTreePanel(), createDetailsPanel());
        add("North", createToolBar());
        add("Center", splitPane);
    }

    /**
     * Create and return a new context tree panel.
     *
     * @return a new context tree panel
     */
    private JComponent createContextTreePanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        JScrollPane scrollPane = new JScrollPane(contextTree);
        panel.add("Center", scrollPane);
        panel.setMinimumSize(new Dimension(300, 300));
        return panel;
    }

    /**
     * Create and return a new details panel.
     *
     * @return a new details panel
     */
    private JComponent createDetailsPanel()
    {
        JPanel panel = new JPanel();
        LabelFieldLayout l = new LabelFieldLayout();
        panel.setLayout(l);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        final IdLabel extraSmall = new IdLabel(selection);
        extraSmall.setIconSize(TangoProject.EXTRA_SMALL);

        addPropertyChangeListener("selection", new PropertyChangeListener()
            {
                /** {@inheritDoc} */
                public void propertyChange(final PropertyChangeEvent e)
                {
                    extraSmall.setValue(selection);
                    extraSmall.repaint();
                }
            });

        final IdLabel small = new IdLabel(selection);
        small.setIconSize(TangoProject.SMALL);

        addPropertyChangeListener("selection", new PropertyChangeListener()
            {
                /** {@inheritDoc} */
                public void propertyChange(final PropertyChangeEvent e)
                {
                    small.setValue(selection);
                    small.repaint();
                }
            });

        final IdLabel medium = new IdLabel(selection);
        medium.setIconSize(TangoProject.MEDIUM);

        addPropertyChangeListener("selection", new PropertyChangeListener()
            {
                /** {@inheritDoc} */
                public void propertyChange(final PropertyChangeEvent e)
                {
                    medium.setValue(selection);
                    medium.repaint();
                }
            });

        final IdLabel large = new IdLabel(selection);
        large.setIconSize(TangoProject.LARGE);
        large.setHorizontalTextPosition(IdLabel.CENTER);
        large.setVerticalTextPosition(IdLabel.BOTTOM);

        addPropertyChangeListener("selection", new PropertyChangeListener()
            {
                /** {@inheritDoc} */
                public void propertyChange(final PropertyChangeEvent e)
                {
                    large.setValue(selection);
                    large.repaint();
                }
            });

        final IdLabel huge = new IdLabel(selection);
        huge.setIconSize(IconSize.DEFAULT_128X128);
        huge.setHorizontalTextPosition(IdLabel.CENTER);
        huge.setVerticalTextPosition(IdLabel.BOTTOM);

        addPropertyChangeListener("selection", new PropertyChangeListener()
            {
                /** {@inheritDoc} */
                public void propertyChange(final PropertyChangeEvent e)
                {
                    huge.setValue(selection);
                    huge.repaint();
                }
            });

        panel.add(new JLabel("Tango Extra Small (16x16)"), l.wideField());
        l.nextLine();
        panel.add(extraSmall, l.wideField());
        l.nextLine();
        panel.add(Box.createVerticalStrut(20), l.spacing());
        l.nextLine();
        panel.add(new JLabel("Tango Small (22x22)"), l.wideField());
        l.nextLine();
        panel.add(small, l.wideField());
        l.nextLine();
        panel.add(Box.createVerticalStrut(20), l.spacing());
        l.nextLine();
        panel.add(new JLabel("Tango Medium (32x32)"), l.wideField());
        l.nextLine();
        panel.add(medium, l.wideField());
        l.nextLine();
        panel.add(Box.createVerticalStrut(20), l.spacing());
        l.nextLine();
        panel.add(new JLabel("Tango Large (48x48)"), l.wideField());
        l.nextLine();
        panel.add(large, l.wideField());
        l.nextLine();
        panel.add(Box.createVerticalStrut(20), l.spacing());
        l.nextLine();
        panel.add(new JLabel("Custom size (128x128)"), l.wideField());
        l.nextLine();
        panel.add(huge, l.wideField());
        l.nextLine();
        panel.add(Box.createGlue(), l.finalSpacing());

        return panel;
    }

    /**
     * Create a return a new toolbar component.
     *
     * @return a new toolbar component
     */
    private JComponent createToolBar()
    {
        final IdToolBar toolBar = new IdToolBar();
        toolBar.add(copy);
        toolBar.add(cut);
        toolBar.add(paste);

        toolBar.setIconSize(TangoProject.EXTRA_SMALL);
        toolBar.displayIconsAndText();

        final JPopupMenu toolBarContextMenu = new JPopupMenu();
        for (Iterator i = toolBar.getDisplayActions().iterator(); i.hasNext(); )
        {
            toolBarContextMenu.add((Action) i.next());
        }
        toolBarContextMenu.addSeparator();
        toolBarContextMenu.add(toolBar.createIconSizeAction(TangoProject.EXTRA_SMALL));
        toolBarContextMenu.add(toolBar.createIconSizeAction(TangoProject.SMALL));
        toolBarContextMenu.add(toolBar.createIconSizeAction(TangoProject.MEDIUM));
        toolBarContextMenu.add(toolBar.createIconSizeAction(TangoProject.LARGE));

        toolBar.addMouseListener(new MouseAdapter()
            {
                /** {@inheritDoc} */
                public void mousePressed(final MouseEvent event)
                {
                    if (event.isPopupTrigger())
                    {
                        showContextMenu(event);
                    }
                }

                /** {@inheritDoc} */
                public void mouseReleased(final MouseEvent event)
                {
                    if (event.isPopupTrigger())
                    {
                        showContextMenu(event);
                    }
                }

                /**
                 * Show the context menu.
                 *
                 * @param event mouse event
                 */
                private void showContextMenu(final MouseEvent event)
                {
                    toolBarContextMenu.show(toolBar, event.getX(), event.getY());
                }
            });

        return toolBar;
    }

    /**
     * Create a return a new menu bar component.
     *
     * @return a new menu bar component
     */
    private JMenuBar createMenuBar()
    {
        JMenu file = new JMenu("File");
        file.add(exit);

        JMenu edit = new JMenu("Edit");
        edit.add(new IdMenuItem(cut, TangoProject.EXTRA_SMALL));
        edit.add(new IdMenuItem(copy, TangoProject.EXTRA_SMALL));
        edit.add(new IdMenuItem(paste, TangoProject.EXTRA_SMALL));

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(file);
        menuBar.add(edit);
        return menuBar;
    }

    /**
     * Create and return a new context menu.
     *
     * @return a new context menu
     */
    private JPopupMenu createContextMenu()
    {
        JPopupMenu contextMenu = new JPopupMenu();
        contextMenu.add(new IdMenuItem(cut, TangoProject.EXTRA_SMALL));
        contextMenu.add(new IdMenuItem(copy, TangoProject.EXTRA_SMALL));
        contextMenu.add(new IdMenuItem(paste, TangoProject.EXTRA_SMALL));
        return contextMenu;
    }

    /**
     * Set the selected identifiable to <code>selection</code>.
     *
     * @param selection selected identifiable
     */
    private void setSelection(final Identifiable selection)
    {
        Identifiable oldSelection = this.selection;
        this.selection = selection;
        firePropertyChange("selection", oldSelection, this.selection);
    }


    /** {@inheritDoc} */
    public void run()
    {
        final JFrame f = new JFrame("Tango Project");
        f.setContentPane(this);
        f.setJMenuBar(createMenuBar());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setBounds(100, 100, 750, 650);

        SwingUtilities.invokeLater(new Runnable()
            {
                /** {@inheritDoc} */
                public void run()
                {
                    f.setVisible(true);
                }
            });
    }


    /**
     * Context tree.
     */
    private class ContextTree
        extends JTree
    {

        /**
         * Create a new context tree.
         */
        public ContextTree()
        {
            super(new TangoTreeModel());
            setCellRenderer(new IdTreeCellRenderer(TangoProject.EXTRA_SMALL));
            getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        }
    }


    /**
     * Main.
     *
     * @param args command line arguments, ignored
     */
    public static void main(final String[] args)
    {
        Runnable r = new TangoExample();
        r.run();
    }
}
