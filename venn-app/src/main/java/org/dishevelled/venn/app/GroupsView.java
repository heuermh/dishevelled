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

import static javax.swing.SwingUtilities.windowForComponent;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.swing.EventListModel;
import ca.odell.glazedlists.swing.EventSelectionModel;

import com.google.common.base.Joiner;

import org.dishevelled.iconbundle.IconBundle;
import org.dishevelled.iconbundle.IconSize;
import org.dishevelled.iconbundle.impl.CachingIconBundle;
import org.dishevelled.iconbundle.impl.PNGIconBundle;
import org.dishevelled.identify.ContextMenuListener;
import org.dishevelled.identify.IdentifiableAction;
import org.dishevelled.identify.IdButton;
import org.dishevelled.identify.IdMenuItem;
import org.dishevelled.identify.IdToolBar;
import org.dishevelled.layout.LabelFieldPanel;
import org.dishevelled.piccolo.venn.BinaryVennNode;
import org.dishevelled.piccolo.venn.TernaryVennNode;
import org.dishevelled.piccolo.venn.QuaternaryVennNode;
import org.dishevelled.piccolo.venn.VennNode;
import org.dishevelled.venn.VennModel;
import org.dishevelled.venn.VennLayout;
import org.dishevelled.venn.VennLayouter;
import org.dishevelled.venn.VennLayouter.PerformanceHint;
import org.dishevelled.venn.euler.VennEulerLayouter;
import org.dishevelled.venn.model.VennModels;
import org.dishevelled.venn.swing.BinaryVennList;
import org.dishevelled.venn.swing.TernaryVennList;
import org.dishevelled.venn.swing.QuaternaryVennList;

/**
 * Groups view.
 *
 * @author  Michael Heuer
 */
final class GroupsView
    extends JPanel
{
    /** List of groups. */
    private final EventList<Group> groups;

    /** List selection. */
    private final EventList<Group> selected;

    /** List of groups. */
    private final JList groupList;

    /** Context menu. */
    private final JPopupMenu contextMenu;

    /** Euler diagram action icon bundle. */
    private final IconBundle eulerDiagramIconBundle = new CachingIconBundle(new PNGIconBundle("/org/dishevelled/venn/app/eulerDiagram"));

    /** Euler diagram action. */
    private final IdentifiableAction eulerDiagram = new IdentifiableAction("Euler Diagram...", eulerDiagramIconBundle)
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                if (selected.size() > 1)
                {
                    eulerDiagram();
                }
            }
        };

    /** Venn diagram action icon bundle. */
    private final IconBundle vennDiagramIconBundle = new CachingIconBundle(new PNGIconBundle("/org/dishevelled/venn/app/vennDiagram"));

    /** Venn diagram action. */
    private final IdentifiableAction vennDiagram = new IdentifiableAction("Venn Diagram...", vennDiagramIconBundle)
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                switch (selected.size())
                {
                    case 2:
                        binaryDiagram();
                        break;
                    case 3:
                        ternaryDiagram();
                        break;
                    case 4:
                        quaternaryDiagram();
                        break;
                    default:
                        break;
                }
            }
        };

    /** Details action icon bundle. */
    private final IconBundle detailsIconBundle = new CachingIconBundle(new PNGIconBundle("/org/dishevelled/venn/app/details"));

    /** Details action. */
    private final IdentifiableAction details = new IdentifiableAction("Details...", detailsIconBundle)
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                switch (selected.size())
                {
                    case 2:
                        binaryDetails();
                        break;
                    case 3:
                        ternaryDetails();
                        break;
                    case 4:
                        quaternaryDetails();
                        break;
                    default:
                        break;
                }
            }
        };

    /** Rename group action. */
    private final Action renameGroup = new AbstractAction("Rename group...")
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                renameGroup();
            }
        };

    /** List selection listener. */
    private final ListSelectionListener listSelectionListener = new ListSelectionListener()
        {
            @Override
            public void valueChanged(final ListSelectionEvent event)
            {
                int size = selected.size();
                eulerDiagram.setEnabled(size > 1);
                vennDiagram.setEnabled(size > 1 && size < 5);
                details.setEnabled(size > 1 && size < 5);
                renameGroup.setEnabled(size == 1);
            }
        };

    /** Venn euler layouter. */
    private final VennLayouter<String> vennLayouter = new VennEulerLayouter<String>();


    /**
     * Create a new groups view.
     */
    GroupsView()
    {
        super();

        groups = GlazedLists.eventList(new ArrayList<Group>());
        EventListModel<Group> listModel = new EventListModel<Group>(groups);
        EventSelectionModel<Group> selectionModel = new EventSelectionModel<Group>(groups);
        selected = selectionModel.getSelected();
        selectionModel.addListSelectionListener(listSelectionListener);
        groupList = new JList(listModel);
        groupList.setSelectionModel(selectionModel);

        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        int menuKeyMask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
        KeyStroke ctrlE = KeyStroke.getKeyStroke(KeyEvent.VK_E, menuKeyMask);
        KeyStroke ctrlN = KeyStroke.getKeyStroke(KeyEvent.VK_N, menuKeyMask);
        KeyStroke ctrlD = KeyStroke.getKeyStroke(KeyEvent.VK_D, menuKeyMask);
        KeyStroke ctrlR = KeyStroke.getKeyStroke(KeyEvent.VK_R, menuKeyMask);
        inputMap.put(ctrlE, "eulerDiagram");
        inputMap.put(ctrlN, "vennDiagram");
        inputMap.put(ctrlD, "details");
        inputMap.put(ctrlR, "renameGroup");
        getActionMap().put("eulerDiagram", eulerDiagram);
        getActionMap().put("vennDiagram", vennDiagram);
        getActionMap().put("details", details);
        getActionMap().put("renameGroup", renameGroup);

        IdMenuItem eulerDiagramMenuItem = new IdMenuItem(eulerDiagram);
        eulerDiagramMenuItem.setAccelerator(ctrlE);
        IdMenuItem vennDiagramMenuItem = new IdMenuItem(vennDiagram);
        vennDiagramMenuItem.setAccelerator(ctrlN);
        IdMenuItem detailsMenuItem = new IdMenuItem(details);
        detailsMenuItem.setAccelerator(ctrlD);
        JMenuItem renameGroupMenuItem = new JMenuItem(renameGroup);
        renameGroupMenuItem.setAccelerator(ctrlR);

        contextMenu = new JPopupMenu();
        contextMenu.add(eulerDiagramMenuItem);
        contextMenu.add(vennDiagramMenuItem);
        contextMenu.add(detailsMenuItem);
        contextMenu.addSeparator();
        contextMenu.add(renameGroupMenuItem);
        groupList.addMouseListener(new ContextMenuListener(contextMenu));

        eulerDiagram.setEnabled(false);
        vennDiagram.setEnabled(false);
        details.setEnabled(false);
        renameGroup.setEnabled(false);

        layoutComponents();
    }


    /**
     * Add the specified group to this group view.
     *
     * @param group group to add
     */
    void addGroup(final Group group)
    {
        groups.add(group);
    }

    /**
     * Layout components.
     */
    private void layoutComponents()
    {
        LabelFieldPanel mainPanel = new LabelFieldPanel();
        mainPanel.setBorder(new EmptyBorder(12, 12, 0, 12));
        mainPanel.addLabel("Groups:");
        mainPanel.addFinalField(new JScrollPane(groupList));

        IdToolBar toolBar = new IdToolBar();
        IdButton eulerDiagramButton = toolBar.add(eulerDiagram);
        eulerDiagramButton.setBorderPainted(false);
        eulerDiagramButton.setFocusPainted(false);
        IdButton vennDiagramButton = toolBar.add(vennDiagram);
        vennDiagramButton.setBorderPainted(false);
        vennDiagramButton.setFocusPainted(false);
        IdButton detailsButton = toolBar.add(details);
        detailsButton.setBorderPainted(false);
        detailsButton.setFocusPainted(false);

        toolBar.displayIcons();
        toolBar.setIconSize(IconSize.DEFAULT_24X24);

        JPopupMenu toolBarContextMenu = new JPopupMenu();
        for (Object menuItem : toolBar.getDisplayMenuItems())
        {
            toolBarContextMenu.add((JCheckBoxMenuItem) menuItem);
        }
        toolBar.addMouseListener(new ContextMenuListener(toolBarContextMenu));

        setLayout(new BorderLayout());
        add("North", toolBar);
        add("Center", mainPanel);
    }

    /**
     * Show a euler diagram.
     */
    private void eulerDiagram()
    {
        List<String> labels = new ArrayList<String>(selected.size());
        List<Set<String>> sets = new ArrayList<Set<String>>(selected.size());
        for (Group selectedGroup : selected)
        {
            labels.add(selectedGroup.getName());
            sets.add(new HashSet<String>(selectedGroup.getValues()));
        }

        final VennModel<String> model = VennModels.createVennModel(sets);
        final VennNode<String> vennNode = new VennNode<String>(model);
        // add ctr that takes List<String> labels as parameter?
        for (int i = 0, size = labels.size(); i < size; i++)
        {
            vennNode.setLabelText(i, labels.get(i));
        }

        JDialog dialog = new JDialog(windowForComponent(this), Joiner.on(", ").join(labels) + " Euler Diagram");
        dialog.setContentPane(new DiagramView(vennNode));
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        installCloseKeyBinding(dialog);

        // todo: offset per parent frame
        dialog.setBounds(100, 100, 600, 600);
        if (model.size() > 4)
        {
            dialog.setBounds(100, 100, 800, 800);
        }
        dialog.setVisible(true);

        SwingUtilities.invokeLater(new Runnable()
            {
                @Override
                public void run()
                {
                    Rectangle2D.Double boundingRectangle = new Rectangle2D.Double(0.0d, 0.0d, 400.0d, 400.0d);
                    if (model.size() > 4)
                    {
                        boundingRectangle.setRect(0.0d, 0.0d, 600.0d, 600.0d);
                    }
                    VennLayout layout = vennLayouter.layout(model, boundingRectangle, PerformanceHint.OPTIMIZE_FOR_SPEED);
                    vennNode.setLayout(layout);
                }
            });
    }

    /**
     * Show a binary venn diagram.
     */
    private void binaryDiagram()
    {
        String firstLabel = selected.get(0).getName();
        String secondLabel = selected.get(1).getName();
        Set<String> first = new HashSet<String>(selected.get(0).getValues());
        Set<String> second = new HashSet<String>(selected.get(1).getValues());
        BinaryVennNode<String> binaryVennNode = new BinaryVennNode<String>(firstLabel, first, secondLabel, second);

        JDialog dialog = new JDialog(windowForComponent(this), firstLabel + ", " + secondLabel + " Venn Diagram");
        dialog.setContentPane(new DiagramView(binaryVennNode));
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        installCloseKeyBinding(dialog);

        // todo: offset per parent frame
        dialog.setBounds(100, 100, 400, 400);
        dialog.setVisible(true);
    }

    /**
     * Show a ternary venn diagram.
     */
    private void ternaryDiagram()
    {
        String firstLabel = selected.get(0).getName();
        String secondLabel = selected.get(1).getName();
        String thirdLabel = selected.get(2).getName();
        Set<String> first = new HashSet<String>(selected.get(0).getValues());
        Set<String> second = new HashSet<String>(selected.get(1).getValues());
        Set<String> third = new HashSet<String>(selected.get(2).getValues());
        TernaryVennNode<String> ternaryVennNode = new TernaryVennNode<String>(firstLabel, first, secondLabel, second, thirdLabel, third);

        JDialog dialog = new JDialog(windowForComponent(this), firstLabel + ", " + secondLabel + ", " + thirdLabel + " Venn Diagram");
        dialog.setContentPane(new DiagramView(ternaryVennNode));
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        installCloseKeyBinding(dialog);

        // todo: offset per parent frame
        dialog.setBounds(100, 100, 400, 400);
        dialog.setVisible(true);
    }

    /**
     * Show a quaternary venn diagram.
     */
    private void quaternaryDiagram()
    {
        String firstLabel = selected.get(0).getName();
        String secondLabel = selected.get(1).getName();
        String thirdLabel = selected.get(2).getName();
        String fourthLabel = selected.get(3).getName();
        Set<String> first = new HashSet<String>(selected.get(0).getValues());
        Set<String> second = new HashSet<String>(selected.get(1).getValues());
        Set<String> third = new HashSet<String>(selected.get(2).getValues());
        Set<String> fourth = new HashSet<String>(selected.get(3).getValues());
        QuaternaryVennNode<String> quaternaryVennNode = new QuaternaryVennNode<String>(firstLabel, first, secondLabel, second, thirdLabel, third, fourthLabel, fourth);

        JDialog dialog = new JDialog(windowForComponent(this), firstLabel + ", " + secondLabel + ", " + thirdLabel + ", " + fourthLabel + " Venn Diagram");
        dialog.setContentPane(new DiagramView(quaternaryVennNode));
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        installCloseKeyBinding(dialog);

        // todo: offset per parent frame
        dialog.setBounds(100, 100, 600, 600);
        dialog.setVisible(true);
    }

    /**
     * Show binary venn details.
     */
    private void binaryDetails()
    {
        String firstLabel = selected.get(0).getName();
        String secondLabel = selected.get(1).getName();
        Set<String> first = new HashSet<String>(selected.get(0).getValues());
        Set<String> second = new HashSet<String>(selected.get(1).getValues());
        final BinaryVennList<String> binaryVennList = new BinaryVennList<String>(firstLabel, first, secondLabel, second);

        JDialog dialog = new JDialog(windowForComponent(this), firstLabel + ", " + secondLabel + " Details");
        dialog.setContentPane(new DetailsView(binaryVennList));
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        installCloseKeyBinding(dialog);

        // todo: offset per parent frame
        dialog.setBounds(100, 100, 600, 450);
        dialog.setVisible(true);
    }

    /**
     * Show ternary venn details.
     */
    private void ternaryDetails()
    {
        String firstLabel = selected.get(0).getName();
        String secondLabel = selected.get(1).getName();
        String thirdLabel = selected.get(2).getName();
        Set<String> first = new HashSet<String>(selected.get(0).getValues());
        Set<String> second = new HashSet<String>(selected.get(1).getValues());
        Set<String> third = new HashSet<String>(selected.get(2).getValues());
        final TernaryVennList<String> ternaryVennList = new TernaryVennList<String>(firstLabel, first, secondLabel, second, thirdLabel, third);

        JDialog dialog = new JDialog(windowForComponent(this), firstLabel + ", " + secondLabel + ", " + thirdLabel + " Details");
        dialog.setContentPane(new DetailsView(ternaryVennList));
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        installCloseKeyBinding(dialog);

        // todo: offset per parent frame
        dialog.setBounds(100, 100, 747, 669);
        dialog.setVisible(true);
    }

    /**
     * Show quaternary venn details.
     */
    private void quaternaryDetails()
    {
        String firstLabel = selected.get(0).getName();
        String secondLabel = selected.get(1).getName();
        String thirdLabel = selected.get(2).getName();
        String fourthLabel = selected.get(3).getName();
        Set<String> first = new HashSet<String>(selected.get(0).getValues());
        Set<String> second = new HashSet<String>(selected.get(1).getValues());
        Set<String> third = new HashSet<String>(selected.get(2).getValues());
        Set<String> fourth = new HashSet<String>(selected.get(3).getValues());
        final QuaternaryVennList<String> quaternaryVennList = new QuaternaryVennList<String>(firstLabel, first, secondLabel, second, thirdLabel, third, fourthLabel, fourth);

        JDialog dialog = new JDialog(windowForComponent(this), firstLabel + ", " + secondLabel + ", " + thirdLabel + ", " + fourthLabel + " Details");
        dialog.setContentPane(new DetailsView(quaternaryVennList));
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        installCloseKeyBinding(dialog);

        // todo: offset per parent frame
        dialog.setBounds(100, 100, 894, 888);
        dialog.setVisible(true);
    }

    /**
     * Rename the selected group if any.
     */
    private void renameGroup()
    {
        if (selected.size() == 1)
        {
            Group group = selected.get(0);
            group.setName(JOptionPane.showInputDialog(windowForComponent(this), "Please enter a new name for this group:", group.getName()));
        }
    }

    /**
     * Install a close action binding to <code>Ctrl-C</code>/<code>Command-C</code> for the specified dialog.
     *
     * @param dialog dialog, must not be null
     */
    static void installCloseKeyBinding(final JDialog dialog)
    {
        Action close = new AbstractAction()
            {
                @Override
                public void actionPerformed(final ActionEvent event)
                {
                    dialog.dispatchEvent(new WindowEvent(dialog, WindowEvent.WINDOW_CLOSING));
                }
            };
        int menuKeyMask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
        KeyStroke closeStroke = KeyStroke.getKeyStroke(KeyEvent.VK_W, menuKeyMask);
        JRootPane rootPane = dialog.getRootPane();
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(closeStroke, "close");
        rootPane.getActionMap().put("close", close);
    }
}
