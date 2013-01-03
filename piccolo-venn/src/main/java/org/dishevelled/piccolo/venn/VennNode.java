/*

    dsh-piccolo-venn  Piccolo2D venn diagram nodes and supporting classes.
    Copyright (c) 2009-2013 held jointly by the individual authors.

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
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;

import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import org.dishevelled.bitset.MutableBitSet;
import org.dishevelled.bitset.ImmutableBitSet;

import org.dishevelled.venn.VennLayout;
import org.dishevelled.venn.VennModel;

import org.piccolo2d.PNode;

import org.piccolo2d.jdk16.nodes.PArea;
import org.piccolo2d.jdk16.nodes.PPath;

import org.piccolo2d.nodes.PText;

import org.piccolo2d.util.PBounds;

/**
 * Venn diagram node.
 *
 * @param <E> value type
 * @author  Michael Heuer
 */
public class VennNode<E>
    extends AbstractVennNode<E>
{
    /** Thread pool executor service. */
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(2);

    /** Animation length, in milliseconds, <code>2000L</code>. */
    private static final long MS = 2000L;

    /** Area paint. */
    private static final Paint AREA_PAINT = new Color(0, 0, 0, 0);

    /** Area stroke. */
    private static final Stroke AREA_STROKE = null;

    /** Area stroke paint. */
    private static final Paint AREA_STROKE_PAINT = null;

    /** Paints. */
    private static final Paint[] PAINTS = new Color[]
    {
        new Color(30, 30, 30, 50),
        new Color(5, 37, 255, 50),
        new Color(255, 100, 5, 50),
        new Color(11, 255, 5, 50),
        // next twelve colors copied from set-3-12 qualitative color scheme
        //    might need to increase alpha
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

    /** Stroke. */
    private static final Stroke STROKE = new BasicStroke(0.5f);

    /** Stroke paint. */
    private static final Paint STROKE_PAINT = new Color(20, 20, 20);

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

    /** Path nodes. */
    private final List<PPath> pathNodes;

    /** Labels. */
    private final List<PText> labels;

    /** Label text. */
    private final List<String> labelTexts;

    /** Area nodes. */
    private final Map<ImmutableBitSet, PArea> areaNodes;

    /** Area label text, for tooltips. */
    private final Map<ImmutableBitSet, String> areaLabelTexts;

    /** Size labels. */
    private final Map<ImmutableBitSet, PText> sizeLabels;

    /** Venn layout. */
    private VennLayout layout;

    /** Venn model. */
    private final VennModel<E> model;


    /**
     * Create a new venn node with the specified model.
     *
     * @param model model for this venn node, must not be null
     */
    public VennNode(final VennModel<E> model)
    {
        super();
        if (model == null)
        {
            throw new IllegalArgumentException("model must not be null");
        }
        this.model = model;
        this.layout = new InitialLayout();

        pathNodes = new ArrayList<PPath>(this.model.size());
        labels = new ArrayList<PText>(this.model.size());
        labelTexts = new ArrayList<String>(this.model.size());
        areaNodes = new HashMap<ImmutableBitSet, PArea>();
        areaLabelTexts = new HashMap<ImmutableBitSet, String>();
        sizeLabels = new HashMap<ImmutableBitSet, PText>();

        createNodes();
        updateLabels();
    }


    /**
     * Create nodes.
     */
    private void createNodes()
    {
        for (int i = 0, size = size(); i < size; i++)
        {
            PPath pathNode = new PPath.Double();
            pathNode.setPaint(PAINTS[i]);
            pathNode.setStroke(STROKE);
            pathNode.setStrokePaint(STROKE_PAINT);
            pathNodes.add(pathNode);

            labelTexts.add(DEFAULT_LABEL_TEXT[i]);

            PText label = new PText();
            labels.add(label);
        }

        // calculate the power set (note n > 30 will overflow int)
        Set<Set<Integer>> powerSet = Sets.powerSet(range(size()));
        for (Set<Integer> set : powerSet)
        {
            if (!set.isEmpty())
            {
                ImmutableBitSet key = toImmutableBitSet(set);

                PArea areaNode = new PArea();
                areaNode.setPaint(AREA_PAINT);
                areaNode.setStroke(AREA_STROKE);
                areaNode.setStrokePaint(AREA_STROKE_PAINT);
                areaNodes.put(key, areaNode);

                PText sizeLabel = new PText();
                sizeLabels.put(key, sizeLabel);
            }
        }

        for (PText label : labels)
        {
            addChild(label);
        }
        for (PPath pathNode : pathNodes)
        {
            addChild(pathNode);
        }
        for (PText sizeLabel : sizeLabels.values())
        {
            addChild(sizeLabel);
        }
        for (PArea areaNode : areaNodes.values())
        {
            addChild(areaNode);
        }
    }

    /**
     * Layout nodes.
     */
    private void layoutNodes()
    {
        for (int i = 0, size = size(); i < size; i++)
        {
            PPath pathNode = pathNodes.get(i);
            pathNode.reset();
            pathNode.append(layout.get(i), false);
            PBounds pathNodeBounds = pathNode.getBoundsReference();

            PText label = labels.get(i);
            PBounds labelBounds = label.getBoundsReference();
            // consider layout on bottom of path if (x, y) is in bottom half of boundingRectangle
            label.setOffset(pathNodeBounds.getX() + pathNodeBounds.getWidth() / 2.0d - labelBounds.getWidth() / 2.0d,
                            pathNodeBounds.getY() - labelBounds.getHeight() / 2.0d - 12.0d);
        }

        for (ImmutableBitSet key : areaNodes.keySet())
        {
            int first = first(key);
            int[] additional = additional(key);

            PArea areaNode = areaNodes.get(key);
            areaNode.reset();
            areaNode.add(new Area(layout.get(first)));
            // intersect with all indices in additional
            for (int i = 0, size = additional.length; i < size; i++)
            {
                areaNode.intersect(new Area(layout.get(additional[i])));
            }
            // subtract everything else
            for (int i = 0, size = size(); i < size; i++)
            {
                if (!key.getQuick(i))
                {
                    areaNode.subtract(new Area(layout.get(i)));
                }
            }

            Point2D luneCenter = layout.luneCenter(first, additional);
            PText sizeLabel = sizeLabels.get(key);
            PBounds sizeLabelBounds = sizeLabel.getBoundsReference();
            // offset to lune center now
            sizeLabel.setOffset(luneCenter.getX() - sizeLabelBounds.getWidth() / 2.0d,
                                luneCenter.getY() - sizeLabelBounds.getHeight() / 2.0d);
            // delay offset to area centroids
            EXECUTOR_SERVICE.submit(new LayoutWorker(areaNode.getAreaReference(), sizeLabel));
        }
    }

    /** {@inheritDoc} */
    protected void updateLabels()
    {
        for (int i = 0; i < size(); i++)
        {
            PText label = labels.get(i);
            label.setText(buildLabel(labelTexts.get(i), model.get(i).size()));
            label.setVisible(getDisplayLabels());
        }
        for (ImmutableBitSet key : areaNodes.keySet())
        {
            int first = first(key);
            int[] additional = additional(key);
            int size = model.exclusiveTo(first, additional).size();
            boolean isEmpty = (size == 0);

            PArea areaNode = areaNodes.get(key);
            boolean areaNodeIsEmpty = !(layout instanceof VennNode.InitialLayout) && areaNode.isEmpty();

            PText sizeLabel = sizeLabels.get(key);
            sizeLabel.setText(String.valueOf(size));
            sizeLabel.setVisible(getDisplaySizeLabels() && !areaNodeIsEmpty && (getDisplaySizesForEmptyAreas() || !isEmpty));

            areaLabelTexts.put(key, buildAreaLabel(first, additional));
        }
    }

    /**
     * Return the size of this venn node.
     *
     * @return the size of this venn node
     */
    public final int size()
    {
        return model.size();
    }

    /**
     * Return the layout for this venn node.  The layout will not be null.
     *
     * @return the layout for this venn node
     */
    public final VennLayout getLayout()
    {
        return layout;
    }

    /**
     * Set the layout for this venn node to <code>layout</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param layout layout for this venn node, must not be null
     */
    public final void setLayout(final VennLayout layout)
    {
        if (layout == null)
        {
            throw new IllegalArgumentException("layout must not be null");
        }
        VennLayout oldLayout = this.layout;
        this.layout = layout;
        firePropertyChange(-1, "layout", oldLayout, this.layout);

        SwingUtilities.invokeLater(new Runnable()
            {
                /** {@inheritDoc} */
                public void run()
                {
                    layoutNodes();
                    updateLabels();
                }
            });
    }

    /**
     * Return the model for this venn node.  The model will not be null.
     *
     * @return the model for this venn node
     */
    public final VennModel<E> getModel()
    {
        return model;
    }

    /**
     * Return the path node for the set at the specified index.
     *
     * @param index index
     * @return the path node for the set at the specified index
     * @throws IndexOutOfBoundsException if <code>index</code> is out of bounds
     */
    public final PPath getPath(final int index)
    {
        return pathNodes.get(index);
    }

    /**
     * Return the label text for the set at the specified index.  Defaults to {@link #DEFAULT_LABEL_TEXT}<code>.get(index)</code>.
     *
     * @param index index
     * @return the label text for the set at the specified index
     * @throws IndexOutOfBoundsException if <code>index</code> is out of bounds
     */
    public final String getLabelText(final int index)
    {
        return labelTexts.get(index);
    }

    /**
     * Set the label text for the set at the specified index to <code>labelText</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param index index
     * @param labelText label text for the set at the specified index
     * @throws IndexOutOfBoundsException if <code>index</code> is out of bounds
     */
    public final void setLabelText(final int index, final String labelText)
    {
        String oldLabelText = labelTexts.get(index);
        labelTexts.set(index, labelText);
        firePropertyChange(-1, "labelTexts", oldLabelText, labelTexts.get(index));
        updateLabels();
    }

    /**
     * Return the label for the set at the specified index.  The text for the returned PText
     * should not be changed, as the text is synchronized to the venn model backing this venn
     * diagram.  Use methods {@link #setLabelText(int, String)} and {@link #setDisplaySizes(boolean)}
     * to set the label text and whether to display sizes respectively.
     *
     * @return the label for the set at the specified index
     */
    public final PText getLabel(final int index)
    {
        return labels.get(index);
    }

    /**
     * Return the area node for the intersecting area defined by the specified indices.
     *
     * @param index first index
     * @param additional variable number of additional indices, if any
     * @return the area node for the intersecting area defined by the specified indices
     * @throws IndexOutOfBoundsException if <code>index</code> or any of <code>additional</code>
     *    are out of bounds, or if too many indices are specified
     */
    public final PArea getArea(final int index, final int... additional)
    {
        checkIndices(index, additional);
        return areaNodes.get(toImmutableBitSet(index, additional));
    }

    /**
     * Return the area label text for the intersecting area defined by the specified indices.
     *
     * @param index first index
     * @param additional variable number of additional indices, if any
     * @return the area label text for the intersecting area defined by the specified indices
     * @throws IndexOutOfBoundsException if <code>index</code> or any of <code>additional</code>
     *    are out of bounds, or if too many indices are specified
     */
    public final String getAreaLabelText(final int index, final int... additional)
    {
        checkIndices(index, additional);
        return areaLabelTexts.get(toImmutableBitSet(index, additional));
    }

    /**
     * Return the size label for the intersecting area defined by the specified indices.
     *
     * @param index first index
     * @param additional variable number of additional indices, if any
     * @return the size label for the intersecting area defined by the specified indices
     * @throws IndexOutOfBoundsException if <code>index</code> or any of <code>additional</code>
     *    are out of bounds, or if too many indices are specified
     */
    public final PText getSizeLabel(final int index, final int... additional)
    {
        checkIndices(index, additional);
        return sizeLabels.get(toImmutableBitSet(index, additional));
    }

    /** {@inheritDoc} */
    public Iterable<PText> labels()
    {
        return labels;
    }

    /** {@inheritDoc} */
    public Iterable<PNode> nodes()
    {
        List<PNode> nodes = new ArrayList<PNode>(pathNodes.size() + areaNodes.size());
        nodes.addAll(pathNodes);
        nodes.addAll(areaNodes.values());
        return nodes;
    }

    /** {@inheritDoc} */
    public PText labelForNode(final PNode node)
    {
        if (node instanceof PPath)
        {
            PPath pathNode = (PPath) node;
            int index = pathNodes.indexOf(pathNode);
            return labels.get(index);
        }
        return null;
    }

    /** {@inheritDoc} */
    public String labelTextForNode(final PNode node)
    {
        if (node instanceof PPath)
        {
            PPath pathNode = (PPath) node;
            int index = pathNodes.indexOf(pathNode);
            return labelTexts.get(index);
        }
        else if (node instanceof PArea)
        {
            PArea areaNode = (PArea) node;
            for (Map.Entry<ImmutableBitSet, PArea> entry : areaNodes.entrySet())
            {
                if (entry.getValue().equals(areaNode))
                {
                    return areaLabelTexts.get(entry.getKey());
                }
            }
        }
        return null;
    }

    /** {@inheritDoc} */
    public Iterable<PText> sizeLabels()
    {
        return sizeLabels.values();
    }

    /** {@inheritDoc} */
    public Set<E> viewForNode(final PNode node)
    {
        if (node instanceof PPath)
        {
            PPath pathNode = (PPath) node;
            int index = pathNodes.indexOf(pathNode);
            return model.get(index);
        }
        else if (node instanceof PArea)
        {
            PArea areaNode = (PArea) node;
            for (Map.Entry<ImmutableBitSet, PArea> entry : areaNodes.entrySet())
            {
                if (entry.getValue().equals(areaNode))
                {
                    ImmutableBitSet key = entry.getKey();
                    return model.exclusiveTo(first(key), additional(key));
                }
            }
        }
        return null;
    }

    /**
     * Build and return area label text.
     *
     * @param index index
     * @param additional variable number of additional indices
     * @throws IndexOutOfBoundsException if <code>index</code> or any of <code>additional</code>
     *    are out of bounds, or if too many indices are specified
     */
    protected final String buildAreaLabel(final int index, final int... additional)
    {
        checkIndices(index, additional);
        StringBuilder sb = new StringBuilder();
        sb.append(labelTexts.get(index));
        if (additional.length > 0) {
            for (int i = 0, size = additional.length - 1; i < size; i++)
            {
                sb.append(", ");
                sb.append(labelTexts.get(additional[i]));
            }
            sb.append(" and ");
            sb.append(labelTexts.get(additional[Math.max(0, additional.length - 1)]));
        }
        sb.append(" only");
        return sb.toString();
    }

    /**
     * Check the specified indices are valid.
     *
     * @param index index
     * @param additional variable number of additional indices
     * @throws IndexOutOfBoundsException if <code>index</code> or any of <code>additional</code>
     *    are out of bounds, or if too many indices are specified
     */
    private void checkIndices(final int index, final int... additional)
    {
        int maxIndex = size() - 1;
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
     * @param values set of values
     * @return the first int value int the specified set of values
     */
    static int first(final Set<Integer> values)
    {
        if (values.isEmpty())
        {
            return -1;
        }
        return values.iterator().next().intValue();
    }

    /**
     * Return the additional int values in the specified set of values.
     *
     * @param values set of values
     * @return the additional it values in the specified set of values
     */
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
     * @param indices set of indices to set to true, must not be null and must not be empty
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

    /**
     * Intial layout.
     */
    private final class InitialLayout implements VennLayout {
        /** Offscreen left. */
        private final Point2D offscreenLeft = new Point2D.Double(-10000.0d, 0.0d);

        /** Empty. */
        private final Rectangle2D empty = new Rectangle2D.Double(-10000.0d, 0.0d, 0.0d, 0.0d);


        /** {@inheritDoc} */
        public int size()
        {
            return VennNode.this.size();
        }

        /** {@inheritDoc} */
        public Shape get(final int index)
        {
            if (index < 0 || index >= size())
            {
                throw new IndexOutOfBoundsException("index " + index + " out of bounds");
            }
            return empty;
        }

        /** {@inheritDoc} */
        public Point2D luneCenter(final int index, final int... additional)
        {
            checkIndices(index, additional);
            return offscreenLeft;
        }

        /** {@inheritDoc} */
        public Rectangle2D boundingRectangle()
        {
            return empty;
        }
    }

    // copied from QuaternaryVennNode.java
    /**
     * Layout worker.
     */
    private final class LayoutWorker
        extends SwingWorker<Point2D, Object>
    {
        /** Area for this layout worker. */
        private Area area;

        /** Size label for this layout worker. */
        private PText size;


        /**
         * Create a new layout worker for the specified area and size label.
         *
         * @param area area
         * @param size size label
         */
        private LayoutWorker(final Area area, final PText size)
        {
            this.area = area;
            this.size = size;
        }


        /** {@inheritDoc} */
        public Point2D doInBackground()
        {
            return Centers.centroidOf(area);
        }

        /** {@inheritDoc} */
        protected void done()
        {
            try
            {
                Rectangle2D bounds = size.getFullBoundsReference();
                Point2D centroid = get();
                size.animateToPositionScaleRotation(centroid.getX() - (bounds.getWidth() / 2.0d),
                                                    centroid.getY() - (bounds.getHeight() / 2.0d), 1.0d, 0.0d, MS);
            }
            catch (Exception e)
            {
                // ignore
            }
        }
    }
}