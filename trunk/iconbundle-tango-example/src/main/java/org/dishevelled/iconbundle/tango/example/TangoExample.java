/*

    dsh-iconbundle-tango-example  Examples using the iconbundle-tango library.
    Copyright (c) 2005 held jointly by the individual authors.

    This library is free software; you can redistribute it and/or modify it
    under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation; either version 2.1 of the License, or (at
    your option) any later version.

    This library is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; with out even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
    License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this library;  if not, write to the Free Software Foundation,
    Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA.

    > http://www.gnu.org/copyleft/lesser.html
    > http://www.opensource.org/licenses/lgpl-license.php

*/
package org.dishevelled.iconbundle.tango.example;

import java.awt.Image;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.BorderLayout;

import java.awt.event.ActionEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Box;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.AbstractAction;
import javax.swing.JTree;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import javax.swing.border.EmptyBorder;

import javax.swing.tree.TreeSelectionModel;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.dishevelled.iconbundle.IconSize;
import org.dishevelled.iconbundle.IconState;
import org.dishevelled.iconbundle.IconTextDirection;
import org.dishevelled.iconbundle.IconBundle;

import org.dishevelled.iconbundle.tango.TangoProject;

import org.dishevelled.identify.IdLabel;
import org.dishevelled.identify.Identifiable;

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


    /**
     * Create a new tango example.
     */
    public TangoExample()
    {
        super();

        contextTree = new ContextTree();
        selection = (Identifiable) contextTree.getModel().getRoot();

        contextTree.addTreeSelectionListener(new TreeSelectionListener()
            {
                /** @see TreeSelectionListener */
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

        setLayout(new BorderLayout());

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, createContextTreePanel(), createDetailsPanel());
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
                /** @see PropertyChangeListener */
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
                /** @see PropertyChangeListener */
                public void propertyChange(final PropertyChangeEvent e)
                {
                    small.setValue(selection);
                    small.repaint();
                }
            });

        final IdLabel large = new IdLabel(selection);
        large.setIconSize(TangoProject.LARGE);
        large.setHorizontalTextPosition(IdLabel.CENTER);
        large.setVerticalTextPosition(IdLabel.BOTTOM);

        addPropertyChangeListener("selection", new PropertyChangeListener()
            {
                /** @see PropertyChangeListener */
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
                /** @see PropertyChangeListener */
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


    /** @see Runnable */
    public void run()
    {
        final JFrame f = new JFrame("Tango Project");
        f.setContentPane(this);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setBounds(100, 100, 750, 550);

        SwingUtilities.invokeLater(new Runnable()
            {
                /** @see Runnable */
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

            setCellRenderer(new DefaultTreeCellRenderer()
                {
                    /** @see DefaultTreeCellRenderer */
                    public Component getTreeCellRendererComponent(JTree tree,
                                                                  Object value,
                                                                  boolean isSelected,
                                                                  boolean isExpanded,
                                                                  boolean isLeaf,
                                                                  int row,
                                                                  boolean hasFocus)
                    {
                        JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value, isSelected, isExpanded, isLeaf, row, hasFocus);

                        Identifiable identifiable = (Identifiable) value;
                        label.setText(identifiable.getName());

                        IconBundle iconBundle = identifiable.getIconBundle();

                        IconState state = IconState.NORMAL;
                        if (isSelected)
                        {
                            state = IconState.SELECTED;
                        }
                        else
                        {
                            if (hasFocus)
                            {
                                state = IconState.ACTIVE;
                            }
                        }

                        Image image = iconBundle.getImage(label,
                                                          IconTextDirection.LEFT_TO_RIGHT,
                                                          state,
                                                          TangoProject.EXTRA_SMALL);

                        label.setIcon(new ImageIcon(image));
                        return label;
                    }
                });

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