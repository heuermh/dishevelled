/*

    dsh-affinity  Clustering by affinity propagation.
    Copyright (c) 2007-2013 held jointly by the individual authors.

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
package org.dishevelled.affinity;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.dishevelled.cluster.Cluster;
import org.dishevelled.cluster.ClusteringAlgorithm;
import org.dishevelled.cluster.ClusteringAlgorithmException;
import org.dishevelled.cluster.ClusteringAlgorithmListener;
import org.dishevelled.cluster.ExitStrategy;
import org.dishevelled.cluster.Similarity;

/**
 * Clustering by affinity propagation.
 *
 * @param <E> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class AffinityPropagation<E>
// extends AbstractClusteringAlgorithm<E>
    implements ClusteringAlgorithm<E>
{
    /** Preference function. */
    private Preference<? super E> preference;


    /**
     * Create a new affinity propagation clustering algorithm with
     * the specified preference function.
     *
     * @param preference preference function, must not be null
     */
    public AffinityPropagation(final Preference<? super E> preference)
    {
        if (preference == null)
        {
            throw new IllegalArgumentException("preference must not be null");
        }
        this.preference = preference;
    }


    /** {@inheritDoc} */
    public Set<Cluster<E>> cluster(final List<? extends E> values,
                                   final Similarity<E> similarity,
                                   final ExitStrategy<E> exitStrategy)
        throws ClusteringAlgorithmException
    {
        if (values == null)
        {
            throw new IllegalArgumentException("values must not be null");
        }
        if (values.isEmpty())
        {
            throw new IllegalArgumentException("values must contain at least one value");
        }
        if (values.indexOf(null) != -1)
        {
            throw new IllegalArgumentException("values must not contain null values");
        }
        if (similarity == null)
        {
            throw new IllegalArgumentException("similarity must not be null");
        }
        if (exitStrategy == null)
        {
            throw new IllegalArgumentException("exitStrategy must not be null");
        }

        return Collections.<Cluster<E>>emptySet();
    }

    /** {@inheritDoc} */
    public void addClusteringAlgorithmListener(final ClusteringAlgorithmListener<E> listener)
    {
        // empty
    }

    /** {@inheritDoc} */
    public void removeClusteringAlgorithmListener(final ClusteringAlgorithmListener<E> listener)
    {
        // empty
    }

    /**
     * Set the preference function for this affinity propagation clustering
     * algorithm to <code>preference</code>.
     *
     * @param preference preference function, must not be null
     */
    public void setPreference(final Preference<? super E> preference)
    {
        if (preference == null)
        {
            throw new IllegalArgumentException("preference must not be null");
        }
        this.preference = preference;
    }

    /**
     * Return the preference function for this affinity propagation clustering
     * algorithm.  The preference function will not be null.
     *
     * @return the preference function for this affinity propagation
     *    cluster algorithm
     */
    public Preference<? super E> getPreference()
    {
        return preference;
    }
}
