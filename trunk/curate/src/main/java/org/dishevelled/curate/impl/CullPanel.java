/*

    dsh-curate  Interfaces for curating collections quickly.
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
package org.dishevelled.curate.impl;

import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.Collection;
import java.util.Collections;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;

import ca.odell.glazedlists.event.ListEvent;
import ca.odell.glazedlists.event.ListEventListener;

import ca.odell.glazedlists.swing.EventListModel;
import ca.odell.glazedlists.swing.EventSelectionModel;

import org.dishevelled.curate.CullView;

import org.dishevelled.iconbundle.tango.TangoProject;

import org.dishevelled.identify.IdentifiableAction;
import org.dishevelled.identify.IdMenuItem;

import org.dishevelled.layout.LabelFieldPanel;

/**
 * Cull panel.
 *
 * @param <E> input and result collections element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class CullPanel<E>
    extends JPanel
    implements CullView<E>
{
    /** Default assist rate (in ms). */
    private static final int DEFAULT_ASSIST_RATE = 1000;

    /** Input collection. */
    private Collection<E> input;

    /** List of remaining elements. */
    private EventList<E> remaining;

    /** List of selected remaining elements. */
    private EventList<E> selectedRemaining;

    /** Swing list of remaining elements. */
    private final JList remainingList;

    /** Label for count of remaining elements. */
    private final JLabel remainingLabel;

    /** List event listener for updating remaining label. */
    private final ListEventListener<E> remainingLabelListener;

    /** List of removed elements. */
    private EventList<E> removed;

    /** Swing list of removed elements. */
    private final JList removedList;

    /** Label for number of removed elements. */
    private final JLabel removedLabel;

    /** List event listener for updating removed label. */
    private final ListEventListener<E> removedLabelListener;

    /** Assist. */
    private final Assist assist;

    /** Remove action. */
    private final IdentifiableAction removeAction;

    /** Remove all action. */
    private final Action removeAllAction;

    /** Start assist action. */
    private final IdentifiableAction startAssistAction;

    /** Stop assist action. */
    private final IdentifiableAction stopAssistAction;

    /** Toggle assist action. */
    private final Action toggleAssistAction;

    /** Context menu. */
    private final JPopupMenu contextMenu;


    /**
     * Create a new cull panel.
     */
    public CullPanel()
    {
        super();

        input = Collections.emptyList();
        remaining = GlazedLists.eventList(input);
        removed = GlazedLists.eventList(input);

        remainingLabel = new JLabel(" - ");
        remainingLabelListener = new ListEventListener<E>()
            {
                /** {@inheritDoc} */
                public void listChanged(final ListEvent<E> event)
                {
                    StringBuffer sb = new StringBuffer();
                    sb.append(remaining.size());
                    sb.append(" of ");
                    sb.append(input.size());
                    remainingLabel.setText(sb.toString());
                }
            };
        remainingList = new JList(new EventListModel<E>(remaining));

        EventSelectionModel<E> remainingSelectionModel = new EventSelectionModel<E>(remaining);
        remainingSelectionModel.setSelectionMode(EventSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        remainingList.setSelectionModel(remainingSelectionModel);
        selectedRemaining = remainingSelectionModel.getSelected();

        removedLabel = new JLabel(" - ");
        removedLabelListener = new ListEventListener<E>()
            {
                /** {@inheritDoc} */
                public void listChanged(final ListEvent<E> event)
                {
                    StringBuffer sb = new StringBuffer();
                    sb.append(removed.size());
                    sb.append(" of ");
                    sb.append(input.size());
                    removedLabel.setText(sb.toString());
                }
            };
        removedList = new JList(new EventListModel<E>(removed));

        removeAction = new IdentifiableAction("Remove", TangoProject.LIST_REMOVE)
            {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent event)
                {
                    EventSelectionModel<E> remainingSelectionModel = (EventSelectionModel<E>) remainingList.getSelectionModel();
                    int maxSelectionIndex = Math.min(remainingSelectionModel.getMaxSelectionIndex() + 1, remaining.size() - 1);
                    int selectionSize = selectedRemaining.size();
                    int newSelectionIndex = maxSelectionIndex - selectionSize;
                    removed.addAll(selectedRemaining);
                    remaining.removeAll(selectedRemaining);
                    remainingSelectionModel.setSelectionInterval(newSelectionIndex, newSelectionIndex);
                    remainingList.ensureIndexIsVisible(newSelectionIndex);
                    if (assist.isRunning())
                    {
                        assist.stop();
                        assist.start();
                    }
                }
            };

        removeAllAction = new AbstractAction("Remove all")
            {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent event)
                {
                    if (assist.isRunning())
                    {
                        assist.stop();
                    }
                    removed.addAll(remaining);
                    remaining.clear();
                }
            };

        assist = new Assist(DEFAULT_ASSIST_RATE);

        startAssistAction = new IdentifiableAction("Start assist", TangoProject.MEDIA_PLAYBACK_START)
            {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent event)
                {
                    if (!assist.isRunning())
                    {
                        assist.start();
                    }
                }
            };

        // ...or compound play+pause button?
        stopAssistAction = new IdentifiableAction("Stop assist", TangoProject.MEDIA_PLAYBACK_STOP)
            {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent event)
                {
                    if (assist.isRunning())
                    {
                        assist.stop();
                    }
                }
            };
        stopAssistAction.setEnabled(false);

        toggleAssistAction = new AbstractAction("Toggle assist")
            {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent event)
                {
                    if (assist.isRunning())
                    {
                        assist.stop();
                    }
                    else
                    {
                        assist.start();
                    }
                }
            };

        remainingList.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), "remove");
        remainingList.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "remove");
        remainingList.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_KP_RIGHT, 0), "remove");
        remainingList.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "remove");
        remainingList.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "toggleAssist");
        remainingList.getActionMap().put("remove", removeAction);
        remainingList.getActionMap().put("toggleAssist", toggleAssistAction);

        // up key should stop assist
        remainingList.addKeyListener(new KeyAdapter()
            {
                /** {@inheritDoc} */
                public void keyPressed(final KeyEvent event)
                {
                    if (assist.isRunning())
                    {
                        if (KeyEvent.VK_UP == event.getKeyCode())
                        {
                            assist.stop();
                        }
                    }
                }
            });

        // multiple value selections should stop assist
        remainingList.addListSelectionListener(new ListSelectionListener()
            {
                /** {@inheritDoc} */
                public void valueChanged(final ListSelectionEvent event)
                {
                    if (assist.isRunning())
                    {
                        if (remainingList.getSelectedIndices().length > 1)
                        {
                            assist.stop();
                        }
                    }
                }
            });

        // TODO:  down should restart assist?
        // TODO:  adapt assist rate to combined delete + down keystroke rate
        contextMenu = new JPopupMenu();
        contextMenu.add(new IdMenuItem(removeAction));
        contextMenu.add(removeAllAction);
        contextMenu.addSeparator();
        contextMenu.add(new IdMenuItem(startAssistAction));
        // stop action will never be enabled on popup trigger
        //contextMenu.add(new IdMenuItem(stopAssistAction));

        remainingList.addMouseListener(new MouseAdapter()
            {
                /** {@inheritDoc} */
                public void mousePressed(final MouseEvent event) {
                    if (assist.isRunning())
                    {
                        assist.stop();
                    }
                    if (event.isPopupTrigger())
                    {
                        showContextMenu(event);
                    }
                }

                /** {@inheritDoc} */
                public void mouseReleased(final MouseEvent event) {
                    if (event.isPopupTrigger())
                    {
                        showContextMenu(event);
                    }
                }

                /**
                 * Show context menu.
                 */
                private void showContextMenu(final MouseEvent event)
                {
                    contextMenu.show(event.getComponent(), event.getX(), event.getY());
                }
            });

        layoutComponents();
    }


    /**
     * Layout components.
     */
    private void layoutComponents()
    {
        LabelFieldPanel west = new LabelFieldPanel();
        west.addField("Remaining:", remainingLabel);
        west.addFinalField(new JScrollPane(remainingList));

        LabelFieldPanel east = new LabelFieldPanel();
        east.addField("Removed:", removedLabel);
        east.addFinalField(new JScrollPane(removedList));

        setLayout(new GridLayout(1, 2, 10, 0));
        add(west);
        add(east);
        // TODO:  add a user-definable "details..." panel center
    }

    /** {@inheritDoc} */
    public void setInput(final Collection<E> input)
    {
        if (input == null)
        {
            throw new IllegalArgumentException("input must not be null");
        }
        this.input = input;

        // see http://glazedlists.dev.java.net/issues/show_bug.cgi?id=419
        try
        {
            remaining.removeListEventListener(remainingLabelListener);
        }
        catch (IllegalArgumentException e)
        {
            // ignore
        }
        try
        {
            removed.removeListEventListener(removedLabelListener);
        }
        catch (IllegalArgumentException e)
        {
            // ignore
        }

        remaining = GlazedLists.eventList(input);
        removed = GlazedLists.eventList(Collections.<E>emptyList());

        remainingList.setModel(new EventListModel<E>(remaining));
        removedList.setModel(new EventListModel<E>(removed));

        EventSelectionModel<E> remainingSelectionModel = new EventSelectionModel<E>(remaining);
        remainingList.setSelectionModel(remainingSelectionModel);
        remainingSelectionModel.setSelectionMode(EventSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        selectedRemaining = remainingSelectionModel.getSelected();

        remaining.addListEventListener(remainingLabelListener);
        removed.addListEventListener(removedLabelListener);

        // mock change event to refresh labels
        remainingLabelListener.listChanged(null);
        removedLabelListener.listChanged(null);
    }

    /** {@inheritDoc} */
    public Collection<E> getRemaining()
    {
        return Collections.unmodifiableList(remaining);
    }

    /** {@inheritDoc} */
    public Collection<E> getRemoved()
    {
        return Collections.unmodifiableList(removed);
    }

    /**
     * Return the remove action for this cull panel.
     * The remove action will not be null.
     *
     * @return the remove action for this cull panel
     */
    IdentifiableAction getRemoveAction()
    {
        return removeAction;
    }

    /**
     * Return the remove all action for this cull panel.
     * The remove all action will not be null.
     *
     * @return the remove action for this cull panel
     */
    Action getRemoveAllAction()
    {
        return removeAllAction;
    }

    /**
     * Return the start assist action for this cull panel.
     * The start assist action will not be null.
     *
     * @return the start assist action for this cull panel
     */
    IdentifiableAction getStartAssistAction()
    {
        return startAssistAction;
    }

    /**
     * Return the stop assist action for this cull panel.
     * The stop assist action will not be null.
     *
     * @return the stop assist action for this cull panel
     */
    IdentifiableAction getStopAssistAction()
    {
        return stopAssistAction;
    }

    /**
     * Assist.
     */
    private class Assist
    {
        /** Rate (in ms) at which new selection change events are triggered. */
        private int rate;

        /** True if assist is running. */
        private boolean running;

        /** Timer. */
        private Timer timer;


        /**
         * Create a new assist with the specified rate.
         *
         * @param rate rate (in ms) at which new selection change events are triggered
         */
        Assist(final int rate)
        {
            this.rate = rate;
            this.running = false;
            timer = new Timer(0, new ActionListener()
                {
                    /** {@inheritDoc} */
                    public void actionPerformed(final ActionEvent event)
                    {
                        EventSelectionModel<E> remainingSelectionModel = (EventSelectionModel<E>) remainingList.getSelectionModel();
                        int maxSelectionIndex = Math.min(remaining.size() - 1, remainingSelectionModel.getMaxSelectionIndex() + 1);
                        remainingSelectionModel.setSelectionInterval(maxSelectionIndex, maxSelectionIndex);
                        remainingList.ensureIndexIsVisible(maxSelectionIndex);
                    }
                });
            timer.setRepeats(true);
        }


        /**
         * Set the rate (in ms) at which new selection change events are triggered to <code>rate</code>.
         *
         * @param rate rate (in ms) at which new seleciton change events are triggered
         */
        void setRate(final int rate)
        {
            this.rate = rate;
        }

        /**
         * Return true if this assist is running.
         *
         * @return true if this assist is running
         */
        boolean isRunning()
        {
            return running;
        }

        /**
         * Start generating new selection change events.
         */
        void start()
        {
            running = true;
            timer.setDelay(rate);
            timer.setInitialDelay(rate);
            startAssistAction.setEnabled(false);
            stopAssistAction.setEnabled(true);
            timer.start();
        }

        /**
         * Stop generating new selection change events.
         */
        void stop()
        {
            running = false;
            startAssistAction.setEnabled(true);
            stopAssistAction.setEnabled(false);
            timer.stop();
        }
    }
}