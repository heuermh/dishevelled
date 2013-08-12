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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.util.ArrayList;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;

import org.cytoscape.model.CyNetwork;

import org.dishevelled.variation.Feature;
import org.dishevelled.variation.Variation;
import org.dishevelled.variation.VariationConsequence;

/**
 * Variation model.
 */
final class VariationModel
{
    /** Species. */
    private String species;

    /** Reference. */
    private String reference;

    /** Ensembl gene id column. */
    private String ensemblGeneIdColumn;

    /** True if variations from only canonical transcripts should be included. */
    private boolean canonical;

    /** True if somatic variations should be included. */
    private boolean somatic;

    /** Current network. */
    private CyNetwork network;

    /** List of features. */
    private final EventList<Feature> features;

    /** List of variations. */
    private final EventList<Variation> variations;

    /** List of variation consequences. */
    private final EventList<VariationConsequence> variationConsequences;

    /** Property change support. */
    private final PropertyChangeSupport propertyChangeSupport;

    private static final String DEFAULT_SPECIES = "human";
    private static final String DEFAULT_REFERENCE = "GRCh37";
    private static final String DEFAULT_ENSEMBL_GENE_ID_COLUMN = "ensembl";
    private static final boolean DEFAULT_CANONICAL = true;
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
        this.network = null;
        features = GlazedLists.eventList(new ArrayList<Feature>());
        variations = GlazedLists.eventList(new ArrayList<Variation>());
        variationConsequences = GlazedLists.eventList(new ArrayList<VariationConsequence>());

        propertyChangeSupport = new PropertyChangeSupport(this);
    }


    // bound properties

    public String getSpecies()
    {
        return species;
    }

    public void setSpecies(final String species)
    {
        String oldSpecies = this.species;
        this.species = species;
        propertyChangeSupport.firePropertyChange("species", oldSpecies, this.species);
    }

    public String getReference()
    {
        return reference;
    }

    public void setReference(final String reference)
    {
        String oldReference = this.reference;
        this.reference = reference;
        propertyChangeSupport.firePropertyChange("reference", oldReference, this.reference);
    }

    public String getEnsemblGeneIdColumn()
    {
        return ensemblGeneIdColumn;
    }

    public void setEnsemblGeneIdColumn(final String ensemblGeneIdColumn)
    {
        String oldEnsemblGeneIdColumn = this.ensemblGeneIdColumn;
        this.ensemblGeneIdColumn = ensemblGeneIdColumn;
        propertyChangeSupport.firePropertyChange("ensemblGeneIdColumn", oldEnsemblGeneIdColumn, this.ensemblGeneIdColumn);
    }

    public boolean isCanonical()
    {
        return canonical;
    }

    public void setCanonical(final boolean canonical)
    {
        boolean oldCanonical = this.canonical;
        this.canonical = canonical;
        propertyChangeSupport.firePropertyChange("canonical", oldCanonical, this.canonical);
    }

    public boolean isSomatic()
    {
        return somatic;
    }

    public void setSomatic(final boolean somatic)
    {
        boolean oldSomatic = this.somatic;
        this.somatic = somatic;
        propertyChangeSupport.firePropertyChange("somatic", oldSomatic, this.somatic);
    }

    public CyNetwork getNetwork()
    {
        return network;
    }

    public void setNetwork(final CyNetwork network)
    {
        CyNetwork oldNetwork = this.network;
        this.network = network;
        propertyChangeSupport.firePropertyChange("network", oldNetwork, this.network);
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

    public void addPropertyChangeListener(final PropertyChangeListener propertyChangeListener)
    {
        propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
    }

    public void addPropertyChangeListener(final String propertyName, final PropertyChangeListener propertyChangeListener)
    {
        propertyChangeSupport.addPropertyChangeListener(propertyName, propertyChangeListener);
    }

    public void removePropertyChangeListener(final PropertyChangeListener propertyChangeListener)
    {
        propertyChangeSupport.removePropertyChangeListener(propertyChangeListener);
    }

    public void removePropertyChangeListener(final String propertyName, final PropertyChangeListener propertyChangeListener)
    {
        propertyChangeSupport.removePropertyChangeListener(propertyName, propertyChangeListener);
    }
}