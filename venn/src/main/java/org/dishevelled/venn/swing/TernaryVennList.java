/*

    dsh-venn  Lightweight components for venn diagrams.
    Copyright (c) 2009-2019 held jointly by the individual authors.

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

import java.util.List;
import java.util.Set;

import javax.swing.Box;
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

import org.dishevelled.venn.TernaryVennModel;

/**
 * Tertiary venn diagram list.
 *
 * @param <E> value type
 * @author  Michael Heuer
 */
public final class TernaryVennList<E>
    extends AbstractTernaryVennDiagram<E>
{
    /** Contents of the first set. */
    private final JList first = new JList();

    /** Contents of the second set. */
    private final JList second = new JList();

    /** Contents of the third set. */
    private final JList third = new JList();

    /** Contents of the first only view. */
    private final JList firstOnly = new JList();

    /** Contents for the second only view. */
    private final JList secondOnly = new JList();

    /** Contents for the third only view. */
    private final JList thirdOnly = new JList();

    /** Contents for the first second view. */
    private final JList firstSecond = new JList();

    /** Contents for the first third view. */
    private final JList firstThird = new JList();

    /** Contents for the second third view. */
    private final JList secondThird = new JList();

    /** Contents of the intersection view. */
    private final JList intersection = new JList();

    /** Contents of the union view. */
    private final JList union = new JList();

    /** Adapter for the first list model. */
    private ObservableSetEventListAdapter<E> firstAdapter;

    /** Adapter for the second list model. */
    private ObservableSetEventListAdapter<E> secondAdapter;

    /** Adapter for the third list model. */
    private ObservableSetEventListAdapter<E> thirdAdapter;

    /** Adapter for the first only list model. */
    private SetEventListAdapter<E> firstOnlyAdapter;

    /** Adapter for the second only list model. */
    private SetEventListAdapter<E> secondOnlyAdapter;

    /** Adapter for the third only list model. */
    private SetEventListAdapter<E> thirdOnlyAdapter;

    /** Adapter for the first second list model. */
    private SetEventListAdapter<E> firstSecondAdapter;

    /** Adapter for the first third list model. */
    private SetEventListAdapter<E> firstThirdAdapter;

    /** Adapter for the second third list model. */
    private SetEventListAdapter<E> secondThirdAdapter;

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
            uninstallListModels((TernaryVennModel<E>) event.getOldValue());
            installListModels();
        }
    };

    /** Update list models from model. */
    private final SetChangeListener<E> updateListModels = new SetChangeListener<E>()
    {
        /** {@inheritDoc} */
        public void setChanged(final SetChangeEvent<E> event)
        {
            updateListModels();
        }
    };

    /** Update list selection from model. */
    private final SetChangeListener<E> updateSelection = new SetChangeListener<E>()
    {
        /** {@inheritDoc} */
        public void setChanged(final SetChangeEvent<E> event)
        {
            updateSelection();
        }
    };


    /**
     * Create a new empty ternary venn list.
     */
    public TernaryVennList()
    {
        super();
        installListModels();
        installSelectionListeners();
        layoutComponents();
        addPropertyChangeListener("model", modelChange);
    }

    /**
     * Create a new ternary venn list with the specified sets.
     *
     * @param firstLabelText label text for the first set
     * @param first first set, must not be null
     * @param secondLabelText label text for the second set
     * @param second second set, must not be null
     * @param thirdLabelText label text for the third set
     * @param third third set, must not be null
     */
    public TernaryVennList(final String firstLabelText, final Set<? extends E> first,
                             final String secondLabelText, final Set<? extends E> second,
                             final String thirdLabelText, final Set<? extends E> third)
    {
        super(firstLabelText, first, secondLabelText, second, thirdLabelText, third);
        installListModels();
        installSelectionListeners();
        layoutComponents();
        addPropertyChangeListener("model", modelChange);
    }

    /**
     * Create a new ternary venn list with the specified model.
     *
     * @param model model for this ternary venn list, must not be null
     */
    public TernaryVennList(final TernaryVennModel<E> model)
    {
        super(model);
        installListModels();
        installSelectionListeners();
        layoutComponents();
        addPropertyChangeListener("model", modelChange);
    }


    /**
     * Clear selection.
     */
    public void clearSelection()
    {
        union.requestFocusInWindow();
        getModel().selection().clear();
    }

    /**
     * Select all.
     */
    public void selectAll()
    {
        union.requestFocusInWindow();
        // todo:  dreadfully inefficient
        getModel().selection().addAll(getModel().union());
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
        thirdAdapter = new ObservableSetEventListAdapter<E>(getModel().third());
        third.setModel(new EventListModel<E>(thirdAdapter));

        firstOnlyAdapter = new SetEventListAdapter<E>(getModel().firstOnly());
        firstOnly.setModel(new EventListModel<E>(firstOnlyAdapter));
        secondOnlyAdapter = new SetEventListAdapter<E>(getModel().secondOnly());
        secondOnly.setModel(new EventListModel<E>(secondOnlyAdapter));
        thirdOnlyAdapter = new SetEventListAdapter<E>(getModel().thirdOnly());
        thirdOnly.setModel(new EventListModel<E>(thirdOnlyAdapter));
        firstSecondAdapter = new SetEventListAdapter<E>(getModel().firstSecond());
        firstSecond.setModel(new EventListModel<E>(firstSecondAdapter));
        firstThirdAdapter = new SetEventListAdapter<E>(getModel().firstThird());
        firstThird.setModel(new EventListModel<E>(firstThirdAdapter));
        secondThirdAdapter = new SetEventListAdapter<E>(getModel().secondThird());
        secondThird.setModel(new EventListModel<E>(secondThirdAdapter));
        intersectionAdapter = new SetEventListAdapter<E>(getModel().intersection());
        intersection.setModel(new EventListModel<E>(intersectionAdapter));
        unionAdapter = new SetEventListAdapter<E>(getModel().union());
        union.setModel(new EventListModel<E>(unionAdapter));

        getModel().first().addSetChangeListener(updateListModels);
        getModel().second().addSetChangeListener(updateListModels);
        getModel().third().addSetChangeListener(updateListModels);
        getModel().first().addSetChangeListener(updateSelection);
        getModel().second().addSetChangeListener(updateSelection);
        getModel().third().addSetChangeListener(updateSelection);
        getModel().selection().addSetChangeListener(updateSelection);
    }

    /**
     * Update list models.
     */
    private void updateListModels()
    {
        firstOnlyAdapter.updateEventList();
        secondOnlyAdapter.updateEventList();
        thirdOnlyAdapter.updateEventList();
        firstSecondAdapter.updateEventList();
        firstThirdAdapter.updateEventList();
        secondThirdAdapter.updateEventList();
        intersectionAdapter.updateEventList();
        unionAdapter.updateEventList();
    }

    /**
     * Uninstall list models.
     *
     * @param oldModel old model
     */
    private void uninstallListModels(final TernaryVennModel<E> oldModel)
    {
        firstAdapter.dispose();
        secondAdapter.dispose();
        thirdAdapter.dispose();
        ((EventListModel<E>) first.getModel()).dispose();
        ((EventListModel<E>) second.getModel()).dispose();
        ((EventListModel<E>) third.getModel()).dispose();
        ((EventListModel<E>) firstOnly.getModel()).dispose();
        ((EventListModel<E>) secondOnly.getModel()).dispose();
        ((EventListModel<E>) thirdOnly.getModel()).dispose();
        ((EventListModel<E>) firstSecond.getModel()).dispose();
        ((EventListModel<E>) firstThird.getModel()).dispose();
        ((EventListModel<E>) secondThird.getModel()).dispose();
        ((EventListModel<E>) intersection.getModel()).dispose();
        ((EventListModel<E>) union.getModel()).dispose();
        oldModel.first().removeSetChangeListener(updateListModels);
        oldModel.second().removeSetChangeListener(updateListModels);
        oldModel.third().removeSetChangeListener(updateListModels);
        oldModel.first().removeSetChangeListener(updateSelection);
        oldModel.second().removeSetChangeListener(updateSelection);
        oldModel.third().removeSetChangeListener(updateSelection);
        oldModel.selection().removeSetChangeListener(updateSelection);
    }

     /**
     * Install selection listeners.
     */
    private void installSelectionListeners()
    {
        first.addListSelectionListener(new UpdateSelectionView());
        second.addListSelectionListener(new UpdateSelectionView());
        third.addListSelectionListener(new UpdateSelectionView());
        firstOnly.addListSelectionListener(new UpdateSelectionView());
        secondOnly.addListSelectionListener(new UpdateSelectionView());
        thirdOnly.addListSelectionListener(new UpdateSelectionView());
        firstSecond.addListSelectionListener(new UpdateSelectionView());
        firstThird.addListSelectionListener(new UpdateSelectionView());
        secondThird.addListSelectionListener(new UpdateSelectionView());
        intersection.addListSelectionListener(new UpdateSelectionView());
        union.addListSelectionListener(new UpdateSelectionView());
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
     * Create and return the main panel.
     *
     * @return the main panel
     */
    private JPanel createMainPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 5, 12, 12));

        LabelFieldPanel f = new LabelFieldPanel();
        f.addLabel(getFirstLabel());
        f.addFinalField(new JScrollPane(first));
        panel.add(f);

        LabelFieldPanel s = new LabelFieldPanel();
        s.addLabel(getSecondLabel());
        s.addFinalField(new JScrollPane(second));
        panel.add(s);

        LabelFieldPanel t = new LabelFieldPanel();
        t.addLabel(getThirdLabel());
        t.addFinalField(new JScrollPane(third));
        panel.add(t);

        panel.add(Box.createGlue());
        panel.add(Box.createGlue());

        LabelFieldPanel fo = new LabelFieldPanel();
        fo.addLabel(getFirstOnlyLabel());
        fo.addFinalField(new JScrollPane(firstOnly));
        panel.add(fo);

        LabelFieldPanel so = new LabelFieldPanel();
        so.addLabel(getSecondOnlyLabel());
        so.addFinalField(new JScrollPane(secondOnly));
        panel.add(so);

        LabelFieldPanel to = new LabelFieldPanel();
        to.addLabel(getThirdOnlyLabel());
        to.addFinalField(new JScrollPane(thirdOnly));
        panel.add(to);

        panel.add(Box.createGlue());
        panel.add(Box.createGlue());

        LabelFieldPanel fs = new LabelFieldPanel();
        fs.addLabel(getFirstSecondLabel());
        fs.addFinalField(new JScrollPane(firstSecond));
        panel.add(fs);

        LabelFieldPanel ft = new LabelFieldPanel();
        ft.addLabel(getFirstThirdLabel());
        ft.addFinalField(new JScrollPane(firstThird));
        panel.add(ft);

        LabelFieldPanel st = new LabelFieldPanel();
        st.addLabel(getSecondThirdLabel());
        st.addFinalField(new JScrollPane(secondThird));
        panel.add(st);

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

    /**
     * Update list selection from the selection view in the model.
     */
    private void updateSelection()
    {
        if (getModel().selection().isEmpty())
        {
            clearSelection(first);
            clearSelection(second);
            clearSelection(third);
            clearSelection(firstOnly);
            clearSelection(secondOnly);
            clearSelection(thirdOnly);
            clearSelection(firstSecond);
            clearSelection(firstThird);
            clearSelection(secondThird);
            clearSelection(intersection);
            clearSelection(union);
        }
        else
        {
            // todo:  need element(s) that were added from set change event
            for (E e : getModel().selection())
            {
                addToSelection(getModel().first(), first, firstAdapter, e);
                addToSelection(getModel().second(), second, secondAdapter, e);
                addToSelection(getModel().third(), third, thirdAdapter, e);
                addToSelection(getModel().firstOnly(), firstOnly, firstOnlyAdapter, e);
                addToSelection(getModel().secondOnly(), secondOnly, secondOnlyAdapter, e);
                addToSelection(getModel().thirdOnly(), thirdOnly, thirdOnlyAdapter, e);
                addToSelection(getModel().firstSecond(), firstSecond, firstSecondAdapter, e);
                addToSelection(getModel().firstThird(), firstThird, firstThirdAdapter, e);
                addToSelection(getModel().secondThird(), secondThird, secondThirdAdapter, e);
                addToSelection(getModel().intersection(), intersection, intersectionAdapter, e);
                addToSelection(getModel().union(), union, unionAdapter, e);
            }

            removeFromSelection(getModel().first(), first, firstAdapter);
            removeFromSelection(getModel().second(), second, secondAdapter);
            removeFromSelection(getModel().third(), third, thirdAdapter);
            removeFromSelection(getModel().firstOnly(), firstOnly, firstOnlyAdapter);
            removeFromSelection(getModel().secondOnly(), secondOnly, secondOnlyAdapter);
            removeFromSelection(getModel().thirdOnly(), thirdOnly, thirdOnlyAdapter);
            removeFromSelection(getModel().firstSecond(), firstSecond, firstSecondAdapter);
            removeFromSelection(getModel().firstThird(), firstThird, firstThirdAdapter);
            removeFromSelection(getModel().secondThird(), secondThird, secondThirdAdapter);
            removeFromSelection(getModel().intersection(), intersection, intersectionAdapter);
            removeFromSelection(getModel().union(), union, unionAdapter);
        }
    }

    /**
     * Clear the selection for the specified list if it is not a focus owner.
     *
     * @param list list
     */
    private void clearSelection(final JList list)
    {
        if (!list.isFocusOwner())
        {
            list.clearSelection();
        }
    }

    /**
     * Add the specified element to the list selection if it is contained
     * in the specified model and the list is not a focus owner.
     *
     * @param model model
     * @param list list
     * @param adapter adapter
     * @param e element
     */
    private void addToSelection(final Set<E> model, final JList list, final List<E> adapter, final E e)
    {
        if (!list.isFocusOwner() && model.contains(e))
        {
            int index = adapter.indexOf(e);
            list.getSelectionModel().addSelectionInterval(index, index);
        }
    }

    /**
     * Remove elements from the list selection if they are not present in
     * the specified model and the list is not a focus owner.
     *
     * @param model model
     * @param list list
     * @param adapter adapter
     */
    private void removeFromSelection(final Set<E> model, final JList list, final List<E> adapter)
    {
        if (!list.isFocusOwner())
        {
            // todo:  need element(s) that were removed from set change event
            for (E e : model)
            {
                if (!getModel().selection().contains(e))
                {
                    int index = adapter.indexOf(e);
                    list.getSelectionModel().removeSelectionInterval(index, index);
                }
            }
        }
    }

    /**
     * Return the contents of the first set.  The model for the returned
     * JList should not be changed, as the current model implementation is
     * synchronized to the ternary venn model backing this venn diagram.
     *
     * @return the contents of the first set
     */
    public JList getFirst()
    {
        return first;
    }

    /**
     * Return the contents of the second set.  The model for the returned
     * JList should not be changed, as the current model implementation is
     * synchronized to the ternary venn model backing this venn diagram.
     *
     * @return the contents of the second set
     */
    public JList getSecond()
    {
        return second;
    }

    /**
     * Return the contents of the third set.  The model for the returned
     * JList should not be changed, as the current model implementation is
     * synchronized to the ternary venn model backing this venn diagram.
     *
     * @return the contents of the thid set
     */
    public JList getThird()
    {
        return third;
    }

    /**
     * Return the contents of the first only view.  The model for the returned
     * JList should not be changed, as the current model implementation is
     * synchronized to the ternary venn model backing this venn diagram.
     *
     * @return the contents of the first only view
     */
    public JList getFirstOnly()
    {
        return firstOnly;
    }

    /**
     * Return the contents of the second only view.  The model for the returned
     * JList should not be changed, as the current model implementation is
     * synchronized to the ternary venn model backing this venn diagram.
     *
     * @return the contents of the second only view
     */
    public JList getSecondOnly()
    {
        return secondOnly;
    }

    /**
     * Return the contents of the third only view.  The model for the returned
     * JList should not be changed, as the current model implementation is
     * synchronized to the ternary venn model backing this venn diagram.
     *
     * @return the contents of the third only view
     */
    public JList getThirdOnly()
    {
        return thirdOnly;
    }

    /**
     * Return the contents of the first second view.  The model for the returned
     * JList should not be changed, as the current model implementation is
     * synchronized to the ternary venn model backing this venn diagram.
     *
     * @return the contents of the first second view
     */
    public JList getFirstSecond()
    {
        return firstSecond;
    }

    /**
     * Return the contents of the first third view.  The model for the returned
     * JList should not be changed, as the current model implementation is
     * synchronized to the ternary venn model backing this venn diagram.
     *
     * @return the contents of the first third view
     */
    public JList getFirstThird()
    {
        return firstThird;
    }

    /**
     * Return the contents of the second third view.  The model for the returned
     * JList should not be changed, as the current model implementation is
     * synchronized to the ternary venn model backing this venn diagram.
     *
     * @return the contents of the second third view
     */
    public JList getSecondThird()
    {
        return secondThird;
    }

    /**
     * Return the contents of the intersection view.  The model for the returned
     * JList should not be changed, as the current model implementation is
     * synchronized to the ternary venn model backing this venn diagram.
     *
     * @return the contents of the intersection view
     */
    public JList getIntersection()
    {
        return intersection;
    }

    /**
     * Return the contents of the union view.  The model for the returned
     * JList should not be changed, as the current model implementation is
     * synchronized to the ternary venn model backing this venn diagram.
     *
     * @return the contents of the union view
     */
    public JList getUnion()
    {
        return union;
    }

    /**
     * Update selection view.
     */
    private class UpdateSelectionView implements ListSelectionListener
    {
        /** {@inheritDoc} */
        public void valueChanged(final ListSelectionEvent event)
        {
            JList list = (JList) event.getSource();
            if (list.isFocusOwner() && !event.getValueIsAdjusting())
            {
                ListSelectionModel selectionModel = list.getSelectionModel();
                for (int index = event.getFirstIndex(); index < (event.getLastIndex() + 1); index++)
                {
                    E e = (E) list.getModel().getElementAt(index);
                    if (selectionModel.isSelectedIndex(index))
                    {
                        if (!getModel().selection().contains(e))
                        {
                            getModel().selection().add(e);
                        }
                    }
                    else
                    {
                        if (getModel().selection().contains(e))
                        {
                            getModel().selection().remove(e);
                        }
                    }
                }
            }
            // todo:  may need to remove from selection view those
            //    elements not present in model for focused list
            //    e.g.  say "bar" is selected, select "foo" in First only
        }
    }
}
