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

import java.util.Iterator;
import java.util.Set;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.dishevelled.layout.LabelFieldPanel;

import org.dishevelled.venn.BinaryVennModel3;

import org.dishevelled.venn.model.BinaryVennModelImpl3;

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
    //private final JList firstOnly = new JList();

    /** Contents for the second only view. */
    //private final JList secondOnly = new JList();

    /** Contents of the intersection view. */
    private final JList intersection = new JList();

    /** Contents of the union view. */
    private final JList union = new JList();


    /**
     * Create a new empty binary venn list.
     */
    public BinaryVennList3()
    {
        super();
        updateContents();
        layoutComponents();
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
        updateContents();
        layoutComponents();
    }

    /**
     * Create a new binary venn list with the specified model.
     *
     * @param model model for this binary venn list, must not be null
     */
    public BinaryVennList3(final BinaryVennModel3<E> model)
    {
        super(model);
        updateContents();
        layoutComponents();
    }


    /** {@inheritDoc} */
    protected void updateContents()
    {
        // todo:  expensive
        //    add hook for model change, create list models at that
        //    point, and fire model changed events here
        first.setListData(getModel().first().toArray());
        second.setListData(getModel().second().toArray());
        //firstOnly.setListData(getModel().firstOnly().toArray());
        //secondOnly.setListData(getModel().secondOnly().toArray());
        intersection.setListData(getModel().intersection().toArray());
        union.setListData(getModel().union().toArray());
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
        t.addLabel(getIntersectionLabel());
        t.addFinalField(new JScrollPane(intersection));
        panel.add(t);

        LabelFieldPanel r = new LabelFieldPanel();
        r.addLabel(getUnionLabel());
        r.addFinalField(new JScrollPane(union));
        panel.add(r);
        return panel;
    }

    // todo:  sync selection
}