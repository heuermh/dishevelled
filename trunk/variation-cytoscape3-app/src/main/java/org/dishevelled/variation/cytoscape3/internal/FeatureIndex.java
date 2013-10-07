/*

    dsh-variation-cytoscape3-app  Variation Cytoscape3 app.
    Copyright (c) 2013 held jointly by the individual authors.

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
package org.dishevelled.variation.cytoscape3.internal;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.cytoscape.model.CyNode;

import org.dishevelled.variation.Feature;
import org.dishevelled.variation.Variation;
import org.dishevelled.variation.VariationConsequence;

import org.dishevelled.variation.interval.Interval;

import org.dishevelled.variation.interval.tree.CenteredIntervalTree;

/*

  CenteredIntervalTree is immutable, might need to use mutable interval tree from SnpEff to keep up with changes

 */

/**
 * Feature index.
 *
 * @author  Michael Heuer
 */
final class FeatureIndex
{
    private final List<CyNode> nodes = Lists.newArrayList();
    private final List<Feature> features = Lists.newArrayList();
    private final BiMap<CyNode, Feature> nodesToFeatures = HashBiMap.create();
    private final BiMap<Feature, Interval> featuresToIntervals = HashBiMap.create();
    private final Map<String, CenteredIntervalTree> intervalTrees = Maps.newHashMap();
    private final ListMultimap<Feature, VariationConsequence> consequences = ArrayListMultimap.create();

    void buildTrees()
    {
        ListMultimap<String, Feature> featuresByName = ArrayListMultimap.create();
        for (Feature feature : features)
        {
            featuresByName.put(feature.getRegion(), feature);
        }
        for (String chr : featuresByName.keySet())
        {
            List<Interval> intervals = Lists.newArrayList(); // with expected size...
            for (Feature feature : featuresByName.get(chr))
            {
                // todo:  confirm that feature intervals in Ensembl are closed
                Interval interval = Interval.closed(feature.getStart(), feature.getEnd());
                intervals.add(interval);
                featuresToIntervals.put(feature, interval);
            }
            intervalTrees.put(chr, new CenteredIntervalTree(intervals));
        }
    }

    void add(final CyNode node, final Feature feature)
    {
        nodes.add(node);
        features.add(feature);
        nodesToFeatures.put(node, feature);
    }

    void add(final Feature feature, final List<VariationConsequence> variationConsequences)
    {
        consequences.putAll(feature, variationConsequences);
    }

    Iterable<Feature> hit(final Variation variation)
    {
        List<Feature> hits = Lists.newArrayList();
        CenteredIntervalTree intervalTree = intervalTrees.get(variation.getRegion());
        for (Interval interval : intervalTree.intersect(Interval.closedOpen(variation.getStart(), variation.getEnd())))
        {
            hits.add(featuresToIntervals.inverse().get(interval));
        }
        return hits;
    }

    int size()
    {
        return nodes.size();
    }

    CyNode nodeAt(final int index)
    {
        return nodes.get(index);
    }

    List<VariationConsequence> consequencesAt(final int index)
    {
        Feature feature = features.get(index);
        return consequences.get(feature);
    }

    void clear()
    {
        nodes.clear();
        features.clear();
        nodesToFeatures.clear();
        featuresToIntervals.clear();
        intervalTrees.clear();
        consequences.clear();
    }
}