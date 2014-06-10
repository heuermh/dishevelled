/*

    dsh-variation-cytoscape3-app  Variation Cytoscape3 app.
    Copyright (c) 2013-2014 held jointly by the individual authors.

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

import static com.google.common.base.Preconditions.checkNotNull;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Range;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;

import org.dishevelled.variation.Feature;
import org.dishevelled.variation.FeatureService;
import org.dishevelled.variation.Variation;
import org.dishevelled.variation.VariationService;
import org.dishevelled.variation.VariationConsequence;
import org.dishevelled.variation.VariationConsequenceService;
import org.dishevelled.variation.VariationConsequencePredictionService;

import org.dishevelled.variation.range.tree.CenteredRangeTree;
import org.dishevelled.variation.range.tree.RangeTree;

/**
 * Variation model.
 *
 * @author  Michael Heuer
 */
final class VariationModel
{
    /** Species. */
    private String species;

    /** Reference. */
    private String reference;

    /** Ensembl gene id column. */
    private String ensemblGeneIdColumn;

    /** True if this variation model should use canonical transcripts only. */
    private boolean canonical;

    /** True if this variation model should include somatic variations. */
    private boolean somatic;

    /** Current network. */
    private CyNetwork network;

    /** Feature service. */
    private FeatureService featureService;

    /** Variation service. */
    private VariationService variationService;

    /** Variation consequence service. */
    private VariationConsequenceService variationConsequenceService;

    /** Variation consequence prediction service. */
    private VariationConsequencePredictionService variationConsequencePredictionService;

    /** List of nodes. */
    private final EventList<CyNode> nodes;

    /** List of features. */
    private final EventList<Feature> features;

    /** List of variations. */
    private final EventList<Variation> variations;

    /** List of variation consequences. */
    private final EventList<VariationConsequence> variationConsequences;

    /** Bidirectional mapping of nodes to features. */
    private final BiMap<CyNode, Feature> nodesToFeatures;

    /** Bidirectional mapping of features to ranges. */
    private final BiMap<Feature, Range<Long>> featuresToRanges;

    /** Map of range trees keyed by region name. */
    private final Map<String, RangeTree<Long>> rangeTrees;

    /** Map of lists of variation consequences by feature. */
    private final ListMultimap<Feature, VariationConsequence> featuresToConsequences;

    /** Property change support. */
    private final PropertyChangeSupport propertyChangeSupport;

    /** Default species, <code>human</code>. */
    private static final String DEFAULT_SPECIES = "human";

    /** Default reference, <code>GRCh37</code>. */
    private static final String DEFAULT_REFERENCE = "GRCh37";

    /** Default Ensembl gene id column, <code>ensembl</code>. */
    private static final String DEFAULT_ENSEMBL_GENE_ID_COLUMN = "ensembl";

    /** Use canonical transcripts only by default. */
    private static final boolean DEFAULT_CANONICAL = true;

    /** Do not include somatic variations by default. */
    private static final boolean DEFAULT_SOMATIC = false;


    /**
     * Create a new variation model.
     */
    VariationModel()
    {
        this.species = DEFAULT_SPECIES;
        this.reference = DEFAULT_REFERENCE;
        this.ensemblGeneIdColumn = DEFAULT_ENSEMBL_GENE_ID_COLUMN;
        this.canonical = DEFAULT_CANONICAL;
        this.somatic = DEFAULT_SOMATIC;
        nodes = GlazedLists.eventList(new ArrayList<CyNode>());
        features = GlazedLists.eventList(new ArrayList<Feature>());
        variations = GlazedLists.eventList(new ArrayList<Variation>());
        variationConsequences = GlazedLists.eventList(new ArrayList<VariationConsequence>());
        nodesToFeatures = HashBiMap.create();
        featuresToRanges = HashBiMap.create();
        rangeTrees = Maps.newHashMap();
        featuresToConsequences = ArrayListMultimap.create();

        propertyChangeSupport = new PropertyChangeSupport(this);
    }


    // bound properties

    /**
     * Return the species for this variation model.
     *
     * @return the species for this variation model
     */
    public String getSpecies()
    {
        return species;
    }

    /**
     * Set the species for this variation model to <code>species</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param species species for this variation model
     */
    public void setSpecies(final String species)
    {
        String oldSpecies = this.species;
        this.species = species;
        propertyChangeSupport.firePropertyChange("species", oldSpecies, this.species);
    }

    /**
     * Return the reference for this variation model.
     *
     * @return the reference for this variation model
     */
    public String getReference()
    {
        return reference;
    }

    /**
     * Set the reference for this variation model to <code>reference</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param reference reference for this variation model
     */
    public void setReference(final String reference)
    {
        String oldReference = this.reference;
        this.reference = reference;
        propertyChangeSupport.firePropertyChange("reference", oldReference, this.reference);
    }

    /**
     * Return the Ensembl gene id column for this variation model.
     *
     * @return the Ensembl gene id column for this variation model
     */
    public String getEnsemblGeneIdColumn()
    {
        return ensemblGeneIdColumn;
    }

    /**
     * Set the Ensembl gene id column for this variation model to <code>ensemblGeneIdColumn</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param ensemblGeneIdColumn Ensembl gene id column for this variation model
     */
    public void setEnsemblGeneIdColumn(final String ensemblGeneIdColumn)
    {
        String oldEnsemblGeneIdColumn = this.ensemblGeneIdColumn;
        this.ensemblGeneIdColumn = ensemblGeneIdColumn;
        propertyChangeSupport.firePropertyChange("ensemblGeneIdColumn", oldEnsemblGeneIdColumn, this.ensemblGeneIdColumn);
    }

    /**
     * Return true if this variation model should use canonical transcripts only.
     *
     * @return true if this variation model should use canonical transcripts only
     */
    public boolean isCanonical()
    {
        return canonical;
    }

    /**
     * Set to true if this variation model should use canonical transcripts only.
     *
     * <p>This is a bound property.</p>
     *
     * @param canonical true if this variation model should use canonical transcripts only
     */
    public void setCanonical(final boolean canonical)
    {
        boolean oldCanonical = this.canonical;
        this.canonical = canonical;
        propertyChangeSupport.firePropertyChange("canonical", oldCanonical, this.canonical);
    }

    /**
     * Return true if this variation model should include somatic variations.
     *
     * @return true if this variation model should include somatic variations
     */
    public boolean isSomatic()
    {
        return somatic;
    }

    /**
     * Set to true if this variation model should include somatic variations.
     *
     * <p>This is a bound property.</p>
     *
     * @param somatic true if this variation model should include somatic variations
     */
    public void setSomatic(final boolean somatic)
    {
        boolean oldSomatic = this.somatic;
        this.somatic = somatic;
        propertyChangeSupport.firePropertyChange("somatic", oldSomatic, this.somatic);
    }

    /**
     * Return the network for this variation model.
     *
     * @return the network for this variation model
     */
    public CyNetwork getNetwork()
    {
        return network;
    }

    /**
     * Set the network for this variation model to <code>network</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param network network for this variation model
     */
    public void setNetwork(final CyNetwork network)
    {
        CyNetwork oldNetwork = this.network;
        this.network = network;
        propertyChangeSupport.firePropertyChange("network", oldNetwork, this.network);

        // or move this to property change listener
        nodes.clear();
        if (this.network != null)
        {
            nodes.addAll(this.network.getNodeList());
        }
        // also need to remove/add network node change listener or similar
    }

    /**
     * Return the feature service for this variation model.
     *
     * @return the feature service for this variation model
     */
    public FeatureService getFeatureService()
    {
        return featureService;
    }

    /**
     * Set the feature service for this variation model to <code>featureService</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param featureService feature service for this variation model
     */
    public void setFeatureService(final FeatureService featureService)
    {
        FeatureService oldFeatureService = this.featureService;
        this.featureService = featureService;
        propertyChangeSupport.firePropertyChange("featureService", oldFeatureService, this.featureService);
    }

    /**
     * Return the variation service for this variation model.
     *
     * @return the variation service for this variation model
     */
    public VariationService getVariationService()
    {
        return variationService;
    }

    /**
     * Set the variation service for this variation model to <code>variationService</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param variationService variation service for this variation model
     */
    public void setVariationService(final VariationService variationService)
    {
        VariationService oldVariationService = this.variationService;
        this.variationService = variationService;
        propertyChangeSupport.firePropertyChange("variationService", oldVariationService, this.variationService);
    }

    /**
     * Return the variation consequence service for this variation model.
     *
     * @return the variation consequence service for this variation model
     */
    public VariationConsequenceService getVariationConsequenceService()
    {
        return variationConsequenceService;
    }

    /**
     * Set the variation consequence service for this variation model to <code>variationConsequenceService</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param variationConsequenceService variation consequence service for this variation model
     */
    public void setVariationConsequenceService(final VariationConsequenceService variationConsequenceService)
    {
        VariationConsequenceService oldVariationConsequenceService = this.variationConsequenceService;
        this.variationConsequenceService = variationConsequenceService;
        propertyChangeSupport.firePropertyChange("variationConsequenceService", oldVariationConsequenceService, this.variationConsequenceService);
    }

    /**
     * Return the variation consequence prediction service for this variation model.
     *
     * @return the variation consequence prediction service for this variation model
     */
    public VariationConsequencePredictionService getVariationConsequencePredictionService()
    {
        return variationConsequencePredictionService;
    }

    /**
     * Set the variation consequence prediction service for this variation model to <code>variationConsequencePredictionService</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param variationConsequencePredictionService variation consequence prediction service for this variation model
     */
    public void setVariationConsequencePredictionService(final VariationConsequencePredictionService variationConsequencePredictionService)
    {
        VariationConsequencePredictionService oldVariationConsequencePredictionService = this.variationConsequencePredictionService;
        this.variationConsequencePredictionService = variationConsequencePredictionService;
        propertyChangeSupport.firePropertyChange("variationConsequencePredictionService", oldVariationConsequencePredictionService, this.variationConsequencePredictionService);
    }

    // event lists

    /**
     * Return the list of nodes for this variation model.
     *
     * @return the list of nodes for this variation model
     */
    EventList<CyNode> nodes()
    {
        return nodes;
    }

    /**
     * Return the list of features for this variation model.
     *
     * @return the list of features for this variation model
     */
    EventList<Feature> features()
    {
        return features;
    }

    /**
     * Return the list of variations for this variation model.
     *
     * @return the list of variations for this variation model
     */
    EventList<Variation> variations()
    {
        return variations;
    }

    /**
     * Return the list of variation consequences for this variation model.
     *
     * @return the list of variation consequences for this variation model
     */
    EventList<VariationConsequence> variationConsequences()
    {
        return variationConsequences;
    }

    // indexes

    /**
     * Rebuild range trees.
     */
    void rebuildTrees()
    {
        rangeTrees.clear();
        ListMultimap<String, Feature> featuresByRegion = ArrayListMultimap.create();
        for (Feature feature : features)
        {
            featuresByRegion.put(feature.getRegion(), feature);
        }
        for (String region : featuresByRegion.keySet())
        {
            List<Feature> regionFeatures = featuresByRegion.get(region);
            List<Range<Long>> ranges = Lists.newArrayListWithCapacity(regionFeatures.size());
            for (Feature feature : regionFeatures)
            {
                // todo:  confirm that feature ranges in Ensembl are closed
                Range<Long> range = Range.closed(feature.getStart(), feature.getEnd());
                ranges.add(range);
                featuresToRanges.put(feature, range);
            }
            rangeTrees.put(region, CenteredRangeTree.create(ranges));
        }
    }

    /**
     * Associate the specified feature with the specified node.
     *
     * @param node node, must not be null
     * @param feature feature, must not be null
     */
    void add(final CyNode node, final Feature feature)
    {
        checkNotNull(node);
        checkNotNull(feature);

        // todo:  merge strategy
        if (!nodes.contains(node))
        {
            nodes.add(node);
        }
        if (!features.contains(feature))
        {
            features.add(feature);
        }
        nodesToFeatures.put(node, feature);
        // or create column in node table for feature, add node back-reference to Feature
    }

    /**
     * Associate the specified feature with the specified list of variation consequences.
     *
     * @param feature feature, must not be null
     * @param variationConsequences list of variation consequences, must not be null
     */
    void add(final Feature feature, final List<VariationConsequence> variationConsequences)
    {
        checkNotNull(feature);
        checkNotNull(variationConsequences);

        featuresToConsequences.putAll(feature, variationConsequences);
    }

    /**
     * Return zero or more features that overlap with the specified variation.
     *
     * @param variation variation, must not be null
     * @return zero or more features that overlap with the specified variation
     */
    Iterable<Feature> hit(final Variation variation)
    {
        checkNotNull(variation);
        List<Feature> hits = Lists.newArrayList();
        RangeTree<Long> rangeTree = rangeTrees.get(variation.getRegion());

        Range<Long> query;
        if (variation.getStart() == variation.getEnd())
        {
            query = Range.singleton(variation.getStart());
        }
        // todo: Ensembl REST client variation query returns end < start for deletions, e.g ref - alt [AAA]
        else if (variation.getEnd() < variation.getStart())
        {
            query = Range.closedOpen(variation.getEnd(), variation.getStart());
        }
        else
        {
            query = Range.closedOpen(variation.getStart(), variation.getEnd());
        }

        for (Range<Long> range : rangeTree.intersect(query))
        {
            hits.add(featuresToRanges.inverse().get(range));
        }
        return hits;
    }

    /**
     * Return the feature associated with the specified node, if any.
     *
     * @param node node, must not be null
     * @return the feature associated with the specified node, or <code>null</code> if no such feature exists
     */
    Feature featureFor(final CyNode node)
    {
        checkNotNull(node);
        return nodesToFeatures.get(node);
    }

    /**
     * Return the node associated with the specified feature, if any.
     *
     * @param feature feature, must not be null
     * @return the node associated with the specified feature, or <code>null</code> if no such node exists
     */
    CyNode nodeFor(final Feature feature)
    {
        checkNotNull(feature);
        return nodesToFeatures.inverse().get(feature);
    }

    /**
     * Return the list of variation consequences associated with the specified feature, if any.
     *
     * @param feature feature, must not be null
     * @return the list of variation consequences associated with the specified feature, or <code>null</code> if no such feature exists (or is it an empty list?)
     */
    List<VariationConsequence> consequencesFor(final Feature feature)
    {
        checkNotNull(feature);
        return featuresToConsequences.get(feature);
    }

    // property change support

    /**
     * Add the specified property change listener.
     *
     * @param propertyChangeListener property change listener to add
     */
    public void addPropertyChangeListener(final PropertyChangeListener propertyChangeListener)
    {
        propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
    }

    /**
     * Add the specified property change listener.
     *
     * @param propertyName property name
     * @param propertyChangeListener property change listener to add
     */
    public void addPropertyChangeListener(final String propertyName, final PropertyChangeListener propertyChangeListener)
    {
        propertyChangeSupport.addPropertyChangeListener(propertyName, propertyChangeListener);
    }

    /**
     * Remove the specified property change listener.
     *
     * @param propertyChangeListener property change listener to remove
     */
    public void removePropertyChangeListener(final PropertyChangeListener propertyChangeListener)
    {
        propertyChangeSupport.removePropertyChangeListener(propertyChangeListener);
    }

    /**
     * Remove the specified property change listener.
     *
     * @param propertyName property name
     * @param propertyChangeListener property change listener to remove
     */
    public void removePropertyChangeListener(final String propertyName, final PropertyChangeListener propertyChangeListener)
    {
        propertyChangeSupport.removePropertyChangeListener(propertyName, propertyChangeListener);
    }
}