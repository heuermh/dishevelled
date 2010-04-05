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

import ca.odell.glazedlists.swing.EventListModel;

import org.dishevelled.layout.LabelFieldPanel;

import org.dishevelled.observable.event.SetChangeEvent;
import org.dishevelled.observable.event.SetChangeListener;

import org.dishevelled.venn.TertiaryVennModel3;

/**
 * Tertiary venn diagram list 3.
 *
 * @param <E> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class TertiaryVennList3<E>
    extends AbstractTertiaryVennDiagram<E>
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
            uninstallListModels((TertiaryVennModel3<E>) event.getOldValue());
            installListModels();
        }
    };

    /** Update list models. */
    private final SetChangeListener<E> updateListModels = new SetChangeListener<E>()
    {
        /** {@inheritDoc} */
        public void setChanged(final SetChangeEvent<E> event)
        {
            updateListModels();
        }
    };


    /**
     * Create a new empty tertiary venn list.
     */
    public TertiaryVennList3()
    {
        super();
        installListModels();
        layoutComponents();
        addPropertyChangeListener("model", modelChange);
    }

    /**
     * Create a new tertiary venn list with the specified sets.
     *
     * @param firstLabelText label text for the first set
     * @param first first set, must not be null
     * @param secondLabelText label text for the second set
     * @param second second set, must not be null
     * @param thirdLabelText label text for the third set
     * @param third third set, must not be null
     */
    public TertiaryVennList3(final String firstLabelText, final Set<? extends E> first,
                             final String secondLabelText, final Set<? extends E> second,
                             final String thirdLabelText, final Set<? extends E> third)
    {
        super(firstLabelText, first, secondLabelText, second, thirdLabelText, third);
        installListModels();
        layoutComponents();
        addPropertyChangeListener("model", modelChange);
    }

    /**
     * Create a new tertiary venn list with the specified model.
     *
     * @param model model for this tertiary venn list, must not be null
     */
    public TertiaryVennList3(final TertiaryVennModel3<E> model)
    {
        super(model);
        installListModels();
        layoutComponents();
        addPropertyChangeListener("model", modelChange);
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
        secondThirdAdapter = new SetEventListAdapter<E>(getModel().secondThird());
        secondThird.setModel(new EventListModel<E>(secondThirdAdapter));
        intersectionAdapter = new SetEventListAdapter<E>(getModel().intersection());
        intersection.setModel(new EventListModel<E>(intersectionAdapter));
        unionAdapter = new SetEventListAdapter<E>(getModel().union());
        union.setModel(new EventListModel<E>(unionAdapter));

        getModel().first().addSetChangeListener(updateListModels);
        getModel().second().addSetChangeListener(updateListModels);
        getModel().third().addSetChangeListener(updateListModels);
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
        secondThirdAdapter.updateEventList();
        intersectionAdapter.updateEventList();
        unionAdapter.updateEventList();
    }

    /**
     * Uninstall list models.
     *
     * @param oldModel old model
     */
    private void uninstallListModels(final TertiaryVennModel3<E> oldModel)
    {
        firstAdapter.dispose();
        secondAdapter.dispose();
        ((EventListModel<E>) first.getModel()).dispose();
        ((EventListModel<E>) second.getModel()).dispose();
        ((EventListModel<E>) third.getModel()).dispose();
        ((EventListModel<E>) firstOnly.getModel()).dispose();
        ((EventListModel<E>) secondOnly.getModel()).dispose();
        ((EventListModel<E>) thirdOnly.getModel()).dispose();
        ((EventListModel<E>) firstSecond.getModel()).dispose();
        ((EventListModel<E>) secondThird.getModel()).dispose();
        ((EventListModel<E>) intersection.getModel()).dispose();
        ((EventListModel<E>) union.getModel()).dispose();
        oldModel.first().removeSetChangeListener(updateListModels);
        oldModel.second().removeSetChangeListener(updateListModels);
        oldModel.third().removeSetChangeListener(updateListModels);
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

        LabelFieldPanel t = new LabelFieldPanel();
        t.addLabel(getThirdLabel());
        t.addFinalField(new JScrollPane(third));
        panel.add(t);

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

        LabelFieldPanel fs = new LabelFieldPanel();
        fs.addLabel(getFirstSecondLabel());
        fs.addFinalField(new JScrollPane(firstSecond));
        panel.add(fs);

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

    // todo:  sync selection
}