/*

    dsh-iconbundle-tango-examples  Examples using the iconbundle-tango library.
    Copyright (c) 2005-2019 held jointly by the individual authors.

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
import java.awt.Font;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import javax.swing.border.EmptyBorder;

import org.dishevelled.iconbundle.IconBundle;

import org.dishevelled.iconbundle.tango.TangoProject;

import org.dishevelled.identify.Identifiable;
import org.dishevelled.identify.IdentifiableAction;
import org.dishevelled.identify.IdLabel;
import org.dishevelled.identify.IdListCellRenderer;
import org.dishevelled.identify.IdToolBar;

import org.dishevelled.layout.LabelFieldPanel;

/**
 * Example that mimics the OSX Disk Utility.
 *
 * @author  Michael Heuer
 */
public final class DiskUtilityExample
    extends JPanel
    implements Runnable
{
    /** Menu bar. */
    private final JMenuBar menuBar = new JMenuBar();

    /** Tool bar. */
    private final IdToolBar toolBar = new IdToolBar();

    /** Volume. */
    private final Identifiable volume = new Identifiable()
        {
            /** {@inheritDoc} */
            public String getName()
            {
                return "Macintosh HD";
            }

            /** {@inheritDoc} */
            public IconBundle getIconBundle()
            {
                return TangoProject.DRIVE_HARDDISK;
            }
        };

    /** Volume icon. */
    private final Identifiable volumeIcon = new Identifiable()
        {
            /** {@inheritDoc} */
            public String getName()
            {
                return "";
            }

            /** {@inheritDoc} */
            public IconBundle getIconBundle()
            {
                return TangoProject.DRIVE_HARDDISK;
            }
        };

    /** Verify action. */
    private final IdentifiableAction verify = new IdentifiableAction("Verify", TangoProject.DRIVE_HARDDISK)
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                // empty
            }
        };

    /** Info action. */
    private final IdentifiableAction info = new IdentifiableAction("Info", TangoProject.DIALOG_INFORMATION)
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                // empty
            }
        };

    /** Burn action. */
    private final IdentifiableAction burn = new IdentifiableAction("Burn", TangoProject.MEDIA_RECORD)
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                // empty
            }
        };

    /** Mount action. */
    private final IdentifiableAction mount = new IdentifiableAction("Mount", TangoProject.MEDIA_FLASH)
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                // empty
            }
        };

    /** Eject action. */
    private final IdentifiableAction eject = new IdentifiableAction("Eject", TangoProject.MEDIA_EJECT)
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                // empty
            }
        };

    /** Enable journaling action. */
    private final IdentifiableAction enableJournaling = new IdentifiableAction("Enable Journaling", TangoProject.MEDIA_SKIP_FORWARD)
        {

            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                // empty
            }
        };

    /** New image action. */
    private final IdentifiableAction newImage = new IdentifiableAction("New Image", TangoProject.DOCUMENT_NEW)
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                // empty
            }
        };

    /** Convert action. */
    private final IdentifiableAction convert = new IdentifiableAction("Convert", TangoProject.EDIT_COPY)
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                // empty
            }
        };

    /** Resize image action. */
    private final IdentifiableAction resizeImage = new IdentifiableAction("Resize Image", TangoProject.EDIT_PASTE)
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                // empty
            }
        };

    /** Log action. */
    private final IdentifiableAction log = new IdentifiableAction("Log", TangoProject.TEXT_X_GENERIC)
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                // empty
            }
        };

    /** Exit action. */
    private final AbstractAction exit = new AbstractAction("Exit")
            {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent event)
                {
                    System.exit(0);
                }
            };


    /**
     * Create a new disk utility example.
     */
    public DiskUtilityExample()
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
        JMenu images = new JMenu("Images");
        JMenu window = new JMenu("Window");
        JMenu help = new JMenu("Help");

        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(images);
        menuBar.add(window);
        menuBar.add(help);

        file.add(exit);

        mount.setEnabled(false);
        eject.setEnabled(false);
        convert.setEnabled(false);
        resizeImage.setEnabled(false);

        toolBar.add(verify);
        toolBar.add(info);
        toolBar.add(burn);
        toolBar.add(mount);
        toolBar.add(eject);
        toolBar.add(enableJournaling);
        toolBar.add(newImage);
        toolBar.add(convert);
        toolBar.add(resizeImage);
        toolBar.add(Box.createGlue());
        toolBar.add(Box.createGlue());
        toolBar.add(Box.createGlue());
        toolBar.add(log);
        toolBar.setIconSize(TangoProject.MEDIUM);
        toolBar.displayIconsAndText();
    }

    /**
     * Layout components.
     */
    private void layoutComponents()
    {
        setLayout(new BorderLayout());
        add("North", toolBar);
        add("Center", createMainPanel());
        add("South", createButtonPanel());
    }

    /**
     * Create and return the main panel.
     *
     * @return the main panel
     */
    private JPanel createMainPanel()
    {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 0, 20));
        mainPanel.add("Center", createVolumePanel());
        mainPanel.add("South", createDetailsPanel());
        return mainPanel;
    }

    /**
     * Create and return the volume panel.
     *
     * @return the volume panel
     */
    private JSplitPane createVolumePanel()
    {
        JList volumes = new JList(new Object[] { volume });
        volumes.setCellRenderer(new IdListCellRenderer());
        JScrollPane volumesScrollPane = new JScrollPane(volumes);
        volumesScrollPane.setMinimumSize(new Dimension(200, 400));
        volumesScrollPane.setPreferredSize(new Dimension(200, 400));

        // tabs are too tall, text is not vertically centered
        // todo:  bottom of the tab is cut off
        JTabbedPane volumeActions = new JTabbedPane();
        volumeActions.addTab("First Aid", createFirstAidPanel());
        volumeActions.addTab("Erase", null);
        volumeActions.addTab("RAID", null);
        volumeActions.addTab("Restore", null);
        volumeActions.setTabComponentAt(0, small("First Aid"));
        volumeActions.setTabComponentAt(1, small("Erase"));
        volumeActions.setTabComponentAt(2, small("RAID"));
        volumeActions.setTabComponentAt(3, small("Restore"));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, volumesScrollPane, volumeActions);
        splitPane.setBorder(new EmptyBorder(0, 0, 0, 0)); // gets rid of extra border
        return splitPane;
    }

    /**
     * Create and return the first aid panel.
     *
     * @return the first aid panel
     */
    private static JPanel createFirstAidPanel()
    {
        LabelFieldPanel firstAidPanel = new LabelFieldPanel();
        firstAidPanel.setBorder(new EmptyBorder(12, 12, 12, 12));
        firstAidPanel.setOpaque(false); // panels inside of tabbed panes mismatch the background color

        // text in <li> is indented too far, symbol is too big
        firstAidPanel.addLabel(small("<html>If you're having trouble with the selected disk:<ul><li>Click Repair Disk.  If the repair fails, back up and erase the disk.</li><li>If Repair Disk is unavailable, click Verify Disk.  If the disk needs repairs, start up from<br/>your Mac OS X installation disc, and then choose Utilities &gt; Disk Utility.</ul>If you have a permissions problem with a file installed by the Mac OS X installer, click<br/>Repair Disk Permissions.</html>"));
        firstAidPanel.addSpacing(20);

        // buttons are too tall
        JCheckBox showDetails = new JCheckBox("Show details");
        showDetails.setFont(showDetails.getFont().deriveFont(11.0f));
        showDetails.setSelected(true);
        JButton clearHistory = new JButton("Clear history");
        clearHistory.setFont(clearHistory.getFont().deriveFont(11.0f));
        clearHistory.setEnabled(false);

        JPanel showDetailsPanel = new JPanel();
        showDetailsPanel.setLayout(new BoxLayout(showDetailsPanel, BoxLayout.X_AXIS));
        showDetailsPanel.setOpaque(false);
        showDetailsPanel.add(showDetails);
        showDetailsPanel.add(Box.createGlue());
        showDetailsPanel.add(Box.createGlue());
        showDetailsPanel.add(Box.createGlue());
        showDetailsPanel.add(clearHistory);

        firstAidPanel.addField(showDetailsPanel);
        JList emptyList = new JList();
        emptyList.setVisibleRowCount(6);
        JScrollPane emptyListScrollPane = new JScrollPane(emptyList);
        emptyListScrollPane.setMinimumSize(new Dimension(200, 120));
        //emptyListScrollPane.setMinimuSize(200, 100);
        firstAidPanel.addField(emptyListScrollPane);

        JButton verifyDiskPermissions = new JButton("Verify Disk Permissions");
        verifyDiskPermissions.setFont(verifyDiskPermissions.getFont().deriveFont(11.0f));
        JButton verifyDisk = new JButton("Verify Disk");
        verifyDisk.setFont(verifyDisk.getFont().deriveFont(11.0f));

        JPanel verifyDiskPanel = new JPanel();
        verifyDiskPanel.setLayout(new BoxLayout(verifyDiskPanel, BoxLayout.X_AXIS));
        verifyDiskPanel.setOpaque(false);
        verifyDiskPanel.add(verifyDiskPermissions);
        verifyDiskPanel.add(Box.createGlue());
        verifyDiskPanel.add(Box.createGlue());
        verifyDiskPanel.add(Box.createGlue());
        verifyDiskPanel.add(verifyDisk);

        firstAidPanel.addField(verifyDiskPanel);

        JButton repairDiskPermissions = new JButton("Repair Disk Permissions");
        repairDiskPermissions.setFont(repairDiskPermissions.getFont().deriveFont(11.0f));
        JButton repairDisk = new JButton("Repair Disk");
        repairDisk.setFont(repairDisk.getFont().deriveFont(11.0f));
        repairDisk.setEnabled(false);

        JPanel repairDiskPanel = new JPanel();
        repairDiskPanel.setLayout(new BoxLayout(repairDiskPanel, BoxLayout.X_AXIS));
        repairDiskPanel.setOpaque(false);
        repairDiskPanel.add(repairDiskPermissions);
        repairDiskPanel.add(Box.createGlue());
        repairDiskPanel.add(Box.createGlue());
        repairDiskPanel.add(Box.createGlue());
        repairDiskPanel.add(repairDisk);

        firstAidPanel.addField(repairDiskPanel);
        firstAidPanel.addFinalSpacing();
        return firstAidPanel;
    }

    /**
     * Create and return the details panel.
     *
     * @return the details panel
     */
    private JPanel createDetailsPanel()
    {
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BorderLayout());
        detailsPanel.setBorder(new EmptyBorder(20, 0, 0, 0));
        IdLabel volumeIconLabel = new IdLabel(volumeIcon);
        volumeIconLabel.setIconSize(TangoProject.LARGE);
        detailsPanel.add("West", volumeIconLabel);
        detailsPanel.add("Center", createDetailsColumns());
        return detailsPanel;
    }

    private static JLabel small(final String text)
    {
        JLabel label = new JLabel(text);
        label.setFont(label.getFont().deriveFont(11.0f));
        return label;
    }

    private static JLabel bold(final String text)
    {
        JLabel label = new JLabel(text);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 11.0f));
        return label;
    }

    /**
     * Create and return the two-column details panel.
     *
     * @return the two-column details panel
     */
    private static JPanel createDetailsColumns()
    {
        LabelFieldPanel column1 = new LabelFieldPanel();
        column1.addField(bold("Mount Point :"), small("<html><a href=\"\">/</a></html>"));
        column1.addField(bold("Partition Type :"), small("Mac OS Extended (Journaled)"));
        column1.addField(bold("Owners Enabled :"), small("Yes"));
        column1.addField(bold("Number of Folders :"), small("201,639"));
        column1.addFinalSpacing();

        LabelFieldPanel column2 = new LabelFieldPanel();
        column2.addField(bold("Capacity :"), small("499.76 GB (499,763,888,128 Bytes)"));
        column2.addField(bold("Available :"), small("226.93 GB (226,930,135,040 Bytes)"));
        column2.addField(bold("Used :"), small("272.83 GB (272,833,753,088 Bytes)"));
        column2.addField(bold("Number of Files :"), small("729,342"));
        column2.addFinalSpacing();

        JPanel detailsColumns = new JPanel();
        detailsColumns.setLayout(new BoxLayout(detailsColumns, BoxLayout.X_AXIS));
        detailsColumns.setBorder(new EmptyBorder(0, 20, 0, 0));
        detailsColumns.add(column1);
        detailsColumns.add(Box.createHorizontalStrut(20));
        detailsColumns.add(column2);
        detailsColumns.add(Box.createGlue());
        return detailsColumns;
    }

    /**
     * Create and return the button panel.
     *
     * @return the button panel
     */
    private static JPanel createButtonPanel()
    {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBorder(new EmptyBorder(0, 20, 20, 20));
        // todo:  use osx built-in help
        buttonPanel.add(new JButton("Help"));
        buttonPanel.add(Box.createGlue());
        buttonPanel.add(Box.createGlue());
        buttonPanel.add(Box.createGlue());
        return buttonPanel;
    }

    /** {@inheritDoc} */
    public void run()
    {
        JFrame f = new JFrame("Disk Utility");
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
    // run with -D"com.apple.mrj.application.apple.menu.about.name=Disk Utility Example"
    public static void main(final String[] args)
    {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        SwingUtilities.invokeLater(new DiskUtilityExample());
    }
}
