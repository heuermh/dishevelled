/*

    dsh-weighted  Weighted map interface and implementation.
    Copyright (c) 2005-2006 held jointly by the individual authors.

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
package org.dishevelled.weighted;

import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.Random;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.AbstractSet;
import java.util.AbstractCollection;

/**
 * Implementation of WeightedMap that delegates to a HashMap.
 *
 * @author  Michael Heuer
 * @author  Mark Schreiber
 * @version $Revision$ $Date$
 */
public class HashWeightedMap<E>
    implements WeightedMap<E>
{
    /** Map of elements to weights. */
    private final Map<E, Double> map;

    /** Total weight. */
    private Double totalWeight = 0.0d;

    /** Source of randomness. */
    private Random random = new Random();

    /** Map of elements to rank. */
    private transient Map<E, Integer> rank;

    /** Dirty flag for calculating rank. */
    private transient boolean dirty;

    /** Entry set view. */
    private transient EntrySet entrySet;

    /** Key set view. */
    private transient KeySet keySet;

    /** Values view. */
    private transient Values values;


    /**
     * Create a new weighted map with the default initial capacity
     * and load factor.
     */
    public HashWeightedMap()
    {
        map = new HashMap<E, Double>();
        dirty = true;
    }

    /**
     * Create a new weighted map with the specified initial capacity
     * and load factor.
     *
     * @param initialCapacity initial capacity
     * @param loadFactor load factor
     */
    public HashWeightedMap(final int initialCapacity, final float loadFactor)
    {
        map = new HashMap<E, Double>(initialCapacity, loadFactor);
        dirty = true;
    }

    /**
     * Create a new weighted map with the elements and weights
     * in the specified weighted map (copy constructor).
     *
     * @param t map to copy
     */
    public HashWeightedMap(final WeightedMap<? extends E> t)
    {
        map = new HashMap<E, Double>(Math.max(2 * t.size(), 16), 0.75f);
        putAll(t);
    }


    /**
     * Set the source of randomness for this weighted map to
     * <code>random</code>.
     *
     * @param random source of randomness, must not be null
     */
    public final void setRandom(final Random random)
    {
        if (random == null)
        {
            throw new IllegalArgumentException("random must not be null");
        }
        this.random = random;
    }

    /** @see WeightedMap */
    public void clear()
    {
        map.clear();
        dirty = true;
        totalWeight = 0.0d;
    }

    /** @see WeightedMap */
    public int size()
    {
        return map.size();
    }

    /** @see WeightedMap */
    public boolean isEmpty()
    {
        return map.isEmpty();
    }

    /** @see WeightedMap */
    public boolean containsKey(final Object o)
    {
        return map.containsKey(o);
    }

    /** @see WeightedMap */
    public boolean containsValue(final Object o)
    {
        return map.containsValue(o);
    }

    /** @see WeightedMap */
    public Double get(final Object o)
    {
        return map.get(o);
    }

    /** @see WeightedMap */
    public Double put(final E e, final Double w)
    {
        // TODO:  need to add this assertion to the API specification somehow
        if (w < 0.0d)
        {
            throw new IllegalArgumentException("w must be >= 0.0d");
        }

        Double oldWeight = map.put(e, w);
        if (oldWeight != null)
        {
            totalWeight -= oldWeight;
        }
        dirty = true;
        totalWeight += w;
        return oldWeight;
    }

    /** @see WeightedMap */
    public void putAll(final Map<? extends E,? extends Double> t)
    {
        for (Map.Entry<? extends E, ? extends Double> e : t.entrySet())
        {
            put(e.getKey(), e.getValue());
        }
    }

    /** @see WeightedMap */
    public Double remove(final Object o)
    {
        Double w = map.remove(o);
        if (w != null)
        {
            dirty = true;
            totalWeight -= w;
        }
        return w;
    }

    /** @see WeightedMap */
    public E sample()
    {
        Double r = random.nextDouble();

        for (E e : keySet())
        {
            r -= normalizedWeight(e);

            if (r <= 0.0d)
            {
                return e;
            }
        }
        return null;
    }

    /** @see WeightedMap */
    public Double weight(final E e)
    {
        return map.get(e);
    }

    /** @see WeightedMap */
    public Double normalizedWeight(final E e)
    {
        if (isEmpty())
        {
            return null;
        }
        if (totalWeight == 0.0d)
        {
            return 0.0d;
        }

        Double w = weight(e);

        if (w == null)
        {
            return null;
        }

        return (w / totalWeight);
    }

    /** @see WeightedMap */
    public Double totalWeight()
    {
        return totalWeight;
    }

    /** @see WeightedMap */
    public int rank(final E e)
    {
        if (dirty)
        {
            calculateRank();
            dirty = false;
        }
        return rank.containsKey(e) ? rank.get(e) : -1;
    }

    /** @see WeightedMap */
    public int maximumRank()
    {
        if (isEmpty())
        {
            return -1;
        }

        int maximumRank = 0;
        for (E e : keySet())
        {
            int rank = rank(e);

            if (rank > maximumRank)
            {
                maximumRank = rank;
            }
        }

        return maximumRank;
    }


    /**
     * Sort elements in descending order according to their
     * normalized weights.
     */
    private Comparator<E> BY_RANK_DESC = new Comparator<E>()
        {
            /** @see Comparator */
            public int compare(final E e1, final E e2)
            {
                Double w1 = normalizedWeight(e1);
                Double w2 = normalizedWeight(e2);

                return (w2.compareTo(w1));
            }
        };

    /**
     * Sort the elements in descending order according
     * to their normalized weights and calculate rank.
     */
    private void calculateRank()
    {
        rank = new HashMap<E,Integer>(size());

        int r = 0;
        List<E> l = new ArrayList<E>(keySet());
        Collections.sort(l, BY_RANK_DESC);

        Double lastWeight = Double.NaN;
        for (E e : l)
        {
            Double w = normalizedWeight(e);
            if (lastWeight.equals(w) == false)
            {
                r++;
            }
            rank.put(e, r);
            lastWeight = w;
        }
        l = null;
    }


    /** @see WeightedMap */
    public Set<E> keySet()
    {
        if (keySet == null)
        {
            keySet = new KeySet();
        }
        return keySet;
    }

    /** @see WeightedMap */
    public Collection<Double> values()
    {
        if (values == null)
        {
            values = new Values();
        }
        return values;
    }

    /** @see WeightedMap */
    public Set<Map.Entry<E, Double>> entrySet()
    {
        if (entrySet == null)
        {
            entrySet = new EntrySet();
        }
        return entrySet;
    }


    /**
     * Key set wrapper.
     */
    private class KeySet
        extends AbstractSet<E>
    {

        /** @see AbstractSet */
        public int size()
        {
            return map.keySet().size();
        }

        /** @see AbstractSet */
        public void clear()
        {
            HashWeightedMap.this.clear();
        }

        /** @see AbstractSet */
        public Iterator<E> iterator()
        {
            return new KeySetIterator();
        }
    }

    /**
     * Key set wrapper iterator.
     */
    private class KeySetIterator
        implements Iterator<E>
    {
        /** Wrapped key set iterator. */
        private Iterator<E> iterator;

        /** Last element. */
        private E e;


        /**
         * Create a new key set wrapper iterator.
         */
        public KeySetIterator()
        {
            iterator = map.keySet().iterator();
        }


        /** @see Iterator */
        public boolean hasNext()
        {
            return iterator.hasNext();
        }

        /** @see Iterator */
        public E next()
        {
            e = iterator.next();
            return e;
        }

        /** @see Iterator */
        public void remove()
        {
            Double w = weight(e);
            iterator.remove();
            dirty = true;
            totalWeight -= w;
        }
    }


    /**
     * Values wrapper.
     */
    private class Values
        extends AbstractCollection<Double>
    {

        /** @see AbstractCollection */
        public int size()
        {
            return map.values().size();
        }

        /** @see AbstractCollection */
        public void clear()
        {
            HashWeightedMap.this.clear();
        }

        /** @see AbstractCollection */
        public Iterator<Double> iterator()
        {
            return new ValuesIterator();
        }
    }

    /**
     * Values wrapper iterator.
     */
    private class ValuesIterator
        implements Iterator<Double>
    {
        /** Wrapped values iterator. */
        private Iterator<Double> iterator;

        /** Last weight. */
        private Double w;


        /**
         * Create a new values wrapper iterator.
         */
        public ValuesIterator()
        {
            iterator = map.values().iterator();
        }


        /** @see Iterator */
        public boolean hasNext()
        {
            return iterator.hasNext();
        }

        /** @see Iterator */
        public Double next()
        {
            w = iterator.next();
            return w;
        }

        /** @see Iterator */
        public void remove()
        {
            iterator.remove();
            dirty = true;
            totalWeight -= w;
        }
    }


    /**
     * Entry set wrapper.
     */
    private class EntrySet
        extends AbstractSet<Map.Entry<E,Double>>
    {

        /** @see AbstractSet */
        public int size()
        {
            return map.entrySet().size();
        }

        /** @see AbstractSet */
        public void clear()
        {
            HashWeightedMap.this.clear();
        }

        /** @see AbstractSet */
        public Iterator<Map.Entry<E, Double>> iterator()
        {
            return new EntrySetIterator();
        }
    }

    /**
     * Entry set wrapper iterator.
     */
    private class EntrySetIterator
        implements Iterator<Map.Entry<E, Double>>
    {
        /** Wrapped key set iterator. */
        private Iterator<Map.Entry<E, Double>> iterator;

        /** Last entry. */
        private Map.Entry<E,Double> e;


        /**
         * Create a new key set wrapper iterator.
         */
        public EntrySetIterator()
        {
            iterator = map.entrySet().iterator();
        }


        /** @see Iterator */
        public boolean hasNext()
        {
            return iterator.hasNext();
        }

        /** @see Iterator */
        public Map.Entry<E, Double> next()
        {
            e = iterator.next();
            return new MapEntry(e);
        }

        /** @see Iterator */
        public void remove()
        {
            iterator.remove();
            dirty = true;
            totalWeight -= e.getValue();
        }
    }

    /**
     * Map entry wrapper.
     */
    private class MapEntry
        implements Map.Entry<E, Double>
    {
        /** Wrapped map entry. */
        private Map.Entry<E, Double> e;


        /**
         * Create a new wrapper for the specified map entry.
         *
         * @param e map entry to wrap
         */
        public MapEntry(final Map.Entry<E, Double> e)
        {
            this.e = e;
        }


        /** @see Map.Entry */
        public boolean equals(final Object o)
        {
            return e.equals(o);
        }

        /** @see Map.Entry */
        public int hashCode()
        {
            return e.hashCode();
        }

        /** @see Map.Entry */
        public E getKey()
        {
            return e.getKey();
        }

        /** @see Map.Entry */
        public Double getValue()
        {
            return e.getValue();
        }

        /** @see Map.Entry */
        public Double setValue(final Double w)
        {
            // TODO:  need to add this assertion to the API specification somehow
            if (w < 0.0d)
            {
                throw new IllegalArgumentException("w must be >= 0.0d");
            }

            Double oldWeight = e.setValue(w);
            if (oldWeight != null)
            {
                totalWeight -= oldWeight;
            }
            dirty = true;
            totalWeight += w;
            return oldWeight;
        }
    }
}
