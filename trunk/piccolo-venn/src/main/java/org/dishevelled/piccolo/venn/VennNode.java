/*

    dsh-piccolo-venn  Piccolo2D venn diagram nodes and supporting classes.
    Copyright (c) 2009-2012 held jointly by the individual authors.

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
package org.dishevelled.piccolo.venn;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Paint;
import java.awt.Stroke;

import java.awt.geom.Area;
import java.awt.geom.Point2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import org.dishevelled.bitset.MutableBitSet;
import org.dishevelled.bitset.ImmutableBitSet;

import org.dishevelled.venn.VennLayout;
import org.dishevelled.venn.VennModel;

import org.piccolo2d.PNode;

import org.piccolo2d.jdk16.nodes.PArea;
import org.piccolo2d.jdk16.nodes.PPath;

import org.piccolo2d.nodes.PText;

/**
 * Venn diagram node.
 *
 * @param <E> value type
 * @author  Michael Heuer
 */
public class VennNode<E>
    extends AbstractVennNode<E>
{
    /** Paints. */
    private static final Paint[] PAINTS = new Color[]
    {
        new Color(30, 30, 30, 50),
        new Color(5, 37, 255, 50),
        new Color(255, 100, 5, 50),
        new Color(11, 255, 5, 50),
        // next twelve colors copied from set-3-12 qualitative color scheme
        new Color(141, 211, 199, 50),
        new Color(255, 255, 179, 50),
        new Color(190, 186, 218, 50),
        new Color(251, 128, 114, 50),
        new Color(128, 177, 211, 50),
        new Color(253, 180, 98, 50),
        new Color(179, 222, 105, 50),
        new Color(252, 205, 229, 50),
        new Color(217, 217, 217, 50),
        new Color(188, 128, 189, 50),
        new Color(204, 235, 197, 50),
        new Color(255, 237, 111, 50),
    };

    /** Default label text. */
    private static final String[] DEFAULT_LABEL_TEXT = new String[]
    {
        "First set",
        "Second set",
        "Third set",
        "Fourth set",
        "Fifth set",
        "Sixth set",
        "Seventh set",
        "Eighth set",
        "Ninth set",
        "Tenth set",
        "Eleventh set",
        "Twelveth set",
        "Thirteenth set",
        "Fourteenth set",
        "Fifteenth set",
        "Sixteenth set"
    };

    /** Area paint. */
    private static final Paint AREA_PAINT = new Color(0, 0, 0, 0);

    /** Area stroke. */
    private static final Stroke AREA_STROKE = null;

    /** Stroke. */
    private static final Stroke STROKE = new BasicStroke(0.5f);

    /** Stroke paint. */
    private static final Paint STROKE_PAINT = new Color(20, 20, 20);

    /** Shape nodes. */
    private final List<PPath> shapeNodes;

    /** Labels. */
    private final List<PText> labels;

    /** Label text. */
    private final List<String> labelText;

    /** Area nodes. */
    private final Map<ImmutableBitSet, PArea> areaNodes;

    /** Area label text, for tooltips. */
    private final Map<ImmutableBitSet, String> areaLabelText;

    /** Size labels. */
    private final Map<ImmutableBitSet, PText> sizeLabels;

    /** Venn layout. */
    private final VennLayout layout;

    /** Venn model. */
    private final VennModel model;


    public VennNode(final VennModel model, final VennLayout layout) // need to pass in labels
    {
        super();

        if (model == null)
        {
            throw new IllegalArgumentException("model must not be null");
        }
        if (layout == null)
        {
            throw new IllegalArgumentException("layout must not be null");
        }
        this.model = model;
        this.layout = layout;

        shapeNodes = new ArrayList<PPath>(layout.size());
        labels = new ArrayList<PText>(layout.size());
        labelText = new ArrayList<String>(layout.size());
        areaNodes = new HashMap<ImmutableBitSet, PArea>();
        areaLabelText = new HashMap<ImmutableBitSet, String>();
        sizeLabels = new HashMap<ImmutableBitSet, PText>();

        createNodes();
        updateLabels();
    }

    /**
     * Create nodes.
     */
    private void createNodes()
    {
        for (int i = 0, size = layout.size(); i < size; i++)
        {
            PPath path = new PPath.Double(layout.get(i));
            path.setPaint(PAINTS[i]);
            path.setStroke(STROKE);
            path.setStrokePaint(STROKE_PAINT);
            shapeNodes.add(path);

            PText label = new PText();
            //label.setText(DEFAULT_LABEL_TEXT[i]);
            label.setHorizontalAlignment(Component.CENTER_ALIGNMENT);
            label.offset(path.getBounds().getWidth() / 2.0d - label.getWidth() / 2.0d,
                         path.getBounds().getHeight() / 2.0d - label.getHeight() - 12.0d);

            labels.add(label);
            labelText.add(DEFAULT_LABEL_TEXT[i]);
        }

        // calculate the power set (note n > 30 will overflow int)
        ImmutableSet<Integer> range = range(layout.size());
        Set<Set<Integer>> powerSet = Sets.powerSet(range);
        for (Set<Integer> set : powerSet)
        {
            if (!set.isEmpty())
            {
                int first = first(set);
                int[] additional = additional(set);

                PArea areaNode = new PArea(AREA_STROKE);
                areaNode.setPaint(AREA_PAINT);

                areaNode.add(new Area(layout.get(first)));
                for (Integer index : set)
                {
                    areaNode.intersect(new Area(shapeNodes.get(index.intValue()).getPathReference()));
                }
                for (Integer index : range)
                {
                    if (!set.contains(index))
                    {
                        areaNode.subtract(new Area(shapeNodes.get(index.intValue()).getPathReference()));
                    }
                }

                Set<?> view = model.exclusiveTo(first, additional);
                Point2D luneCenter = layout.luneCenter(first, additional);

                PText sizeLabel = new PText();
                //sizeLabel.setText(view.size());
                sizeLabel.setHorizontalAlignment(Component.CENTER_ALIGNMENT);
                sizeLabel.setOffset(luneCenter.getX() - sizeLabel.getWidth() / 2.0d,
                                    luneCenter.getY() - sizeLabel.getHeight() / 2.0d);

                ImmutableBitSet bitSet = toImmutableBitSet(set);
                areaNodes.put(bitSet, areaNode);
                areaLabelText.put(bitSet, "");
                sizeLabels.put(bitSet, sizeLabel);
            }
        }

        for (PPath shapeNode : shapeNodes)
        {
            addChild(shapeNode);
        }
        for (PText sizeLabel : sizeLabels.values())
        {
            addChild(sizeLabel);
        }
        for (PArea areaNode : areaNodes.values())
        {
            addChild(areaNode);
        }
        for (PText label : labels)
        {
            addChild(label);
        }
    }

    /**
     * Update labels.
     */
    private void updateLabels()
    {
        for (int i = 0; i < layout.size(); i++)
        {
            labels.get(i).setText(buildLabel(labelText.get(i), model.get(i).size()));
        }
        for (ImmutableBitSet key : areaNodes.keySet()) // assumes keys are identical for all of {areaNodes, areaLabels, areaLabelText}
        {
            int size = model.exclusiveTo(first(key), additional(key)).size();
            sizeLabels.get(key).setText(String.valueOf(size));
            //areaLabels.get(key).setText(buildLabel(areaLabelText.get(key), size));
        }
    }

    /** {@inheritDoc} */
    public Iterable<PText> labels()
    {
        return null;
    }

    /** {@inheritDoc} */
    public Iterable<PNode> nodes()
    {
        return null;
    }

    /** {@inheritDoc} */
    public PText labelForNode(final PNode node)
    {
        return null;
    }

    /** {@inheritDoc} */
    public String labelTextForNode(final PNode node)
    {
        return null;
    }

    /** {@inheritDoc} */
    public Set<E> viewForNode(final PNode node)
    {
        return null;
    }

    /**
     * Check the specified indices are valid.
     *
     * @param index index
     * @param additional variable number of additional indices
     * @throws IndexOutOfBoundsException if <code>index</code> or any of <code>additional</code>
     *    are out of bounds, or if too many indices are specified
     */
    /*
    static void checkIndices(final int index, final int... additional)
    {
        int maxIndex = layout.size() - 1;
        if (index < 0 || index > maxIndex)
        {
            throw new IndexOutOfBoundsException("index out of bounds");
        }
        if (additional != null && additional.length > 0)
        {
            if (additional.length > maxIndex)
            {
                throw new IndexOutOfBoundsException("too many indices provided");
            }
            for (int i = 0, j = additional.length; i < j; i++)
            {
                if (additional[i] < 0 || additional[i] > maxIndex)
                {
                    throw new IndexOutOfBoundsException("additional index [" + i + "] out of bounds");
                }
            }
        }
    }
    */

    /**
     * Return an immutable set of the integers between <code>0</code> and <code>n</code>, exclusive.
     *
     * @param n n
     * @return an immutable set of integers between <code>0</code> and <code>n</code>, exclusive
     */
    static ImmutableSet<Integer> range(final int n)
    {
        Set<Integer> range = Sets.newHashSet();
        for (int i = 0; i < n; i++)
        {
            range.add(Integer.valueOf(i));
        }
        return ImmutableSet.copyOf(range);
    }

    /**
     * Return the first index set to true in the specified bit set.
     *
     * @param bitSet bit set
     * @return the first index set to true in the specified bit set
     *    or <code>-1</code> if no bits in the specified bit set are set to true
     */
    static int first(final ImmutableBitSet bitSet)
    {
        return (int) bitSet.nextSetBit(0L);
    }

    /**
     * Return the additional indices set to true in the specified bit set.
     *
     * @param bitSet bit set
     * @return the additional indices set to true in the specified bit set
     *    or an empty <code>int[]</code> if only zero or one bits are set to true
     *    the specified bit set
     */
    static int[] additional(final ImmutableBitSet bitSet)
    {
        int[] additional = new int[Math.max(0, (int) bitSet.cardinality() - 1)];
        int index = 0;
        long first = bitSet.nextSetBit(0);
        for (long value = bitSet.nextSetBit(first + 1); value >= 0L; value = bitSet.nextSetBit(value + 1))
        {
            additional[index] = (int) value;
            index++;
        }
        return additional;
    }

    /**
     * Return the first int value in the specified set of values.
     *
     *
     */
    static int first(final Set<Integer> values)
    {
        if (values.isEmpty())
        {
            return -1;
        }
        return values.iterator().next().intValue();
    }

    static int[] additional(final Set<Integer> values)
    {
        int[] additional = new int[Math.max(0, values.size() - 1)];
        int index = -1;
        for (Integer value : values)
        {
            if (index >= 0)
            {
                additional[index] = value.intValue();
            }
            index++;
        }
        return additional;
    }

    // copied from VennLayoutUtils.java here to keep package-private visibility
    /**
     * Create and return a new immutable bit set with the specified bits set to true.
     *
     * @param index first index to set to true
     * @param additional variable number of additional indices to set to true, if any
     * @return a new immutable bit set with the specified bits set to true
     */
    static ImmutableBitSet toImmutableBitSet(final int index, final int... additional)
    {
        int size = 1 + ((additional == null) ? 0 : additional.length);
        MutableBitSet mutableBitSet = new MutableBitSet(size);
        mutableBitSet.set(index);
        if (additional != null)
        {
            for (int i = 0; i < additional.length; i++)
            {
                mutableBitSet.set(additional[i]);
            }
        }
        mutableBitSet.trimTrailingZeros();
        return mutableBitSet.immutableCopy();
    }

    /**
     * Create and return a new immutable bit set with the specified bits set to true.
     *
     * @param indices set of indicies to set to true, must not be null and must not be empty
     * @return a new immutable bit set with the specified bits set to true
     */
    static ImmutableBitSet toImmutableBitSet(final Set<Integer> indices)
    {
        if (indices == null)
        {
            throw new IllegalArgumentException("indices must not be null");
        }
        if (indices.isEmpty())
        {
            throw new IllegalArgumentException("indices must not be empty");
        }
        MutableBitSet mutableBitSet = new MutableBitSet(indices.size());
        for (Integer index : indices)
        {
            mutableBitSet.set(index);
        }
        mutableBitSet.trimTrailingZeros();
        return mutableBitSet.immutableCopy();
    }
}