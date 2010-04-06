/*

    dsh-venn  Lightweight components for venn diagrams.
    Copyright (c) 2009-2010 held jointly by the individual authors.

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
package org.dishevelled.venn.swing;

import java.awt.GridLayout;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.Set;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ca.odell.glazedlists.swing.EventListModel;

import org.dishevelled.layout.LabelFieldPanel;

import org.dishevelled.observable.event.SetChangeEvent;
import org.dishevelled.observable.event.SetChangeListener;

import org.dishevelled.venn.BinaryVennModel3;

/**
 * Binary venn diagram list 3.
 *
 * @param <E> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class BinaryVennList3<E>
    extends AbstractBinaryVennDiagram<E>
{
    /** Contents of the first set. */
    private final JList first = new JList();

    /** Contents of the second set. */
    private final JList second = new JList();

    /** Contents of the first only view. */
    private final JList firstOnly = new JList();

    /** Contents for the second only view. */
    private final JList secondOnly = new JList();

    /** Contents of the intersection view. */
    private final JList intersection = new JList();

    /** Contents of the union view. */
    private final JList union = new JList();

    /** Adapter for the first list model. */
    private ObservableSetEventListAdapter<E> firstAdapter;

    /** Adapter for the second list model. */
    private ObservableSetEventListAdapter<E> secondAdapter;

    /** Adapter for the first only list model. */
    private SetEventListAdapter<E> firstOnlyAdapter;

    /** Adapter for the second only list model. */
    private SetEventListAdapter<E> secondOnlyAdapter;

    /** Adapter for the intersection list model. */
    private SetEventListAdapter<E> intersectionAdapter;

    /** Adapter for the union list model. */
    private SetEventListAdapter<E> unionAdapter;

    /** Model change listener. */
    private final PropertyChangeListener modelChange = new PropertyChangeListener()
    {
        /** {@inheritDoc} */
        public void propertyChange(final PropertyChangeEvent event)
        {
            uninstallListModels((BinaryVennModel3<E>) event.getOldValue());
            installListModels();
        }
    };

    /** Update list models and list selection from model. */
    private final SetChangeListener<E> updateListModels = new SetChangeListener<E>()
    {
        /** {@inheritDoc} */
        public void setChanged(final SetChangeEvent<E> event)
        {
            updateListModels();
            updateSelection();
        }
    };


    /**
     * Create a new empty binary venn list.
     */
    public BinaryVennList3()
    {
        super();
        installListModels();
        installSelectionListeners();
        layoutComponents();
        addPropertyChangeListener("model", modelChange);
    }

    /**
     * Create a new binary venn list with the specified sets.
     *
     * @param firstLabelText label text for the first set
     * @param first first set, must not be null
     * @param secondLabelText label text for the second set
     * @param second second set, must not be null
     */
    public BinaryVennList3(final String firstLabelText, final Set<? extends E> first,
        final String secondLabelText, final Set<? extends E> second)
    {
        super(firstLabelText, first, secondLabelText, second);
        installListModels();
        installSelectionListeners();
        layoutComponents();
        addPropertyChangeListener("model", modelChange);
    }

    /**
     * Create a new binary venn list with the specified model.
     *
     * @param model model for this binary venn list, must not be null
     */
    public BinaryVennList3(final BinaryVennModel3<E> model)
    {
        super(model);
        installListModels();
        installSelectionListeners();
        layoutComponents();
        addPropertyChangeListener("model", modelChange);
    }


    /**
     * Install selection listeners.
     */
    private void installSelectionListeners()
    {
        first.getSelectionModel().addListSelectionListener(new ListSelectionListener()
            {
                /** {@inheritDoc} */
                public void valueChanged(final ListSelectionEvent event)
                {
                    //if (!event.getValueIsAdjusting())
                    //{
                        ListSelectionModel selectionModel = (ListSelectionModel) event.getSource();
                        for (int index = event.getFirstIndex(); index < event.getLastIndex(); index++)
                        {
                            E e = firstAdapter.get(index);
                            System.out.println("considering " + e);
                            if (selectionModel.isSelectedIndex(index))
                            {
                                System.out.println(e + " is selected index");
                                if (!getModel().selection().contains(e))
                                {
                                    System.out.println("adding " + e + " to selection view");
                                    getModel().selection().add(e);  // add later?
                                }
                            }
                            else
                            {
                                System.out.println(e + " is not selected index");
                                if (getModel().selection().contains(e))
                                {
                                    System.out.println("removing " + e + " from selection view");
                                    getModel().selection().remove(e);  // remove later?
                                }
                            }
                        }
                        //}
                }
            });
    }

    /**
     * Install list models.
     */
    private void installListModels()
    {
        firstAdapter = new ObservableSetEventListAdapter<E>(getModel().first());
        first.setModel(new EventListModel<E>(firstAdapter));
        secondAdapter = new ObservableSetEventListAdapter<E>(getModel().second());
        second.setModel(new EventListModel<E>(secondAdapter));

        firstOnlyAdapter = new SetEventListAdapter<E>(getModel().firstOnly());
        firstOnly.setModel(new EventListModel<E>(firstOnlyAdapter));
        secondOnlyAdapter = new SetEventListAdapter<E>(getModel().secondOnly());
        secondOnly.setModel(new EventListModel<E>(secondOnlyAdapter));
        intersectionAdapter = new SetEventListAdapter<E>(getModel().intersection());
        intersection.setModel(new EventListModel<E>(intersectionAdapter));
        unionAdapter = new SetEventListAdapter<E>(getModel().union());
        union.setModel(new EventListModel<E>(unionAdapter));

        getModel().first().addSetChangeListener(updateListModels);
        getModel().second().addSetChangeListener(updateListModels);
    }

    /**
     * Update list models.
     */
    private void updateListModels()
    {
        firstOnlyAdapter.updateEventList();
        secondOnlyAdapter.updateEventList();
        intersectionAdapter.updateEventList();
        unionAdapter.updateEventList();
    }

    /**
     * Uninstall list models.
     *
     * @param oldModel old model
     */
    private void uninstallListModels(final BinaryVennModel3<E> oldModel)
    {
        firstAdapter.dispose();
        secondAdapter.dispose();
        ((EventListModel<E>) first.getModel()).dispose();
        ((EventListModel<E>) second.getModel()).dispose();
        ((EventListModel<E>) firstOnly.getModel()).dispose();
        ((EventListModel<E>) secondOnly.getModel()).dispose();
        ((EventListModel<E>) intersection.getModel()).dispose();
        ((EventListModel<E>) union.getModel()).dispose();
        oldModel.first().removeSetChangeListener(updateListModels);
        oldModel.second().removeSetChangeListener(updateListModels);
    }

    /**
     * Update list selection from the selection view in the model.
     */
    private void updateSelection()
    {
        if (getModel().selection().isEmpty())
        {
            System.out.println("clearing list selection");
            first.clearSelection();
            second.clearSelection();
            intersection.clearSelection();
            union.clearSelection();
        }
        else
        {
            for (E e : getModel().selection())
            {
                if (getModel().first().contains(e))
                {
                    int index = firstAdapter.indexOf(e);
                    System.out.println("adding selection interval (" + index + ", " + (index + 1) + ") to first");
                    first.getSelectionModel().addSelectionInterval(index, index + 1);
                }
                if (getModel().second().contains(e))
                {
                    int index = secondAdapter.indexOf(e);
                    System.out.println("adding selection interval (" + index + ", " + (index + 1) + ") to second");
                    second.getSelectionModel().addSelectionInterval(index, index + 1);
                }
                if (getModel().intersection().contains(e))
                {
                    int index = intersectionAdapter.indexOf(e);
                    System.out.println("adding selection interval (" + index + ", " + (index + 1) + ") to intersection");
                    intersection.getSelectionModel().addSelectionInterval(index, index + 1);
                }
                if (getModel().union().contains(e))
                {
                    int index = unionAdapter.indexOf(e);
                    System.out.println("adding selection interval (" + index + ", " + (index + 1) + ") to union");
                    union.getSelectionModel().addSelectionInterval(index, index + 1);
                }
            }
            // remove selection intervals for those no longer in selection()
        }
    }

    /** {@inheritDoc} */
    protected void updateContents()
    {
        // empty
    }

    /**
     * Layout components.
     */
    private void layoutComponents()
    {
        addFinalField(createMainPanel());
    }

    /**
     * Create main panel.
     */
    private JPanel createMainPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 4, 12, 0));

        LabelFieldPanel f = new LabelFieldPanel();
        f.addLabel(getFirstLabel());
        f.addFinalField(new JScrollPane(first));
        panel.add(f);

        LabelFieldPanel s = new LabelFieldPanel();
        s.addLabel(getSecondLabel());
        s.addFinalField(new JScrollPane(second));
        panel.add(s);

        LabelFieldPanel fo = new LabelFieldPanel();
        fo.addLabel(getFirstOnlyLabel());
        fo.addFinalField(new JScrollPane(firstOnly));
        panel.add(fo);

        LabelFieldPanel so = new LabelFieldPanel();
        so.addLabel(getSecondOnlyLabel());
        so.addFinalField(new JScrollPane(secondOnly));
        panel.add(so);

        LabelFieldPanel n = new LabelFieldPanel();
        n.addLabel(getIntersectionLabel());
        n.addFinalField(new JScrollPane(intersection));
        panel.add(n);

        LabelFieldPanel u = new LabelFieldPanel();
        u.addLabel(getUnionLabel());
        u.addFinalField(new JScrollPane(union));
        panel.add(u);
        return panel;
    }

    // todo:  sync selection
}