/*

    dsh-worm-plot-cytoscape3-app  Worm plot Cytoscape 3 app.
    Copyright (c) 2014 held jointly by the individual authors.

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
package org.dishevelled.wormplot.cytoscape3.internal;

import java.io.File;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.cytoscape.model.CyNetwork;

import org.cytoscape.view.model.CyNetworkView;

/**
 * Worm plot model.
 *
 * @author  Michael Heuer
 */
final class WormPlotModel
{
    /** Sequence file. */
    private File sequenceFile;

    /** Length. */
    private int length;

    /** Overlap. */
    private int overlap;

    /** Network. */
    private CyNetwork network;

    /** Network view. */
    private CyNetworkView networkView;

    /** Property change support. */
    private final PropertyChangeSupport propertyChangeSupport;

    /** Default length, <code>120</code> base pairs. */
    static final int DEFAULT_LENGTH = 120;

    /** Default overlap, <code>40</code> base pairs. */
    static final int DEFAULT_OVERLAP = 40;


    /**
     * Create a new worm plot model.
     */
    WormPlotModel()
    {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }


    // bound properties

    /**
     * Return the sequence file for this model.
     *
     * @return the sequence file for this model
     */
    File getSequenceFile()
    {
        return sequenceFile;
    }

    /**
     * Set the sequence file for this model to <code>sequenceFile</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param sequenceFile sequence file for this model
     */
    void setSequenceFile(final File sequenceFile)
    {
        File oldSequenceFile = this.sequenceFile;
        this.sequenceFile = sequenceFile;
        propertyChangeSupport.firePropertyChange("sequenceFile", oldSequenceFile, this.sequenceFile);
    }

    /**
     * Return the length for this model.  Defaults to <code>DEFAULT_LENGTH</code>.
     *
     * @return the length for this model
     */
    int getLength()
    {
        return length;
    }

    /**
     * Set the length for this model to <code>length</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param length length for this model, must be <code>&gt; 0</code>
     */
    void setLength(final int length)
    {
        if (length < 1)
        {
            throw new IllegalArgumentException("length must be at least 1");
        }
        int oldLength = this.length;
        this.length = length;
        propertyChangeSupport.firePropertyChange("length", oldLength, this.length);
    }

    /**
     * Return the overlap for this model.  Defaults to <code>DEFAULT_OVERLAP</code>.
     *
     * @return the overlap for this model
     */
    int getOverlap()
    {
        return overlap;
    }

    /**
     * Set the overlap for this model to <code>overlap</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param overlap overlap for this model, must be <code>&gt; 0</code> and must be <code>&lt; getLength()</code>
     */
    void setOverlap(final int overlap)
    {
        if (overlap < 1)
        {
            throw new IllegalArgumentException("overlap must be at least 1");
        }
        if (overlap >= length)
        {
            throw new IllegalArgumentException("overlap must be less than length");
        }
        int oldOverlap = this.overlap;
        this.overlap = overlap;
        propertyChangeSupport.firePropertyChange("overlap", oldOverlap, this.overlap);
    }

    /**
     * Return the network for this model.
     *
     * @return the network for this model
     */
    CyNetwork getNetwork()
    {
        return network;
    }

    /**
     * Set the network for this model to <code>network</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param network network for this model
     */
    void setNetwork(final CyNetwork network)
    {
        CyNetwork oldNetwork = this.network;
        this.network = network;
        propertyChangeSupport.firePropertyChange("network", oldNetwork, this.network);
    }

    /**
     * Return the network view for this model.
     *
     * @return the network view for this model
     */
    CyNetworkView getNetworkView()
    {
        return networkView;
    }

    /**
     * Set the network view for this model to <code>networkView</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param networkView network view for this model
     */
    void setNetworkView(final CyNetworkView networkView)
    {
        CyNetworkView oldNetworkView = this.networkView;
        this.networkView = networkView;
        propertyChangeSupport.firePropertyChange("networkView", oldNetworkView, this.networkView);
    }

    // property change support

    /**
     * Add the specified property change listener.
     *
     * @param propertyChangeListener property change listener to add
     */
    void addPropertyChangeListener(final PropertyChangeListener propertyChangeListener)
    {
        propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
    }

    /**
     * Add the specified property change listener.
     *
     * @param propertyName property name
     * @param propertyChangeListener property change listener to add
     */
    void addPropertyChangeListener(final String propertyName, final PropertyChangeListener propertyChangeListener)
    {
        propertyChangeSupport.addPropertyChangeListener(propertyName, propertyChangeListener);
    }

    /**
     * Remove the specified property change listener.
     *
     * @param propertyChangeListener property change listener to remove
     */
    void removePropertyChangeListener(final PropertyChangeListener propertyChangeListener)
    {
        propertyChangeSupport.removePropertyChangeListener(propertyChangeListener);
    }

    /**
     * Remove the specified property change listener.
     *
     * @param propertyName property name
     * @param propertyChangeListener property change listener to remove
     */
    void removePropertyChangeListener(final String propertyName, final PropertyChangeListener propertyChangeListener)
    {
        propertyChangeSupport.removePropertyChangeListener(propertyName, propertyChangeListener);
    }
}