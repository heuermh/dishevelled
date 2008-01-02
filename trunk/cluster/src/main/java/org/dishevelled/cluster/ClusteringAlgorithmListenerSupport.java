/*

    dsh-cluster  Framework for clustering algorithms.
    Copyright (c) 2007-2008 held jointly by the individual authors.

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
package org.dishevelled.cluster;

import javax.swing.event.EventListenerList;

/**
 * A support class that can be used via delegation to provide
 * <code>ClusteringAlgorithmListener</code> management.
 *
 * @param <E> value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class ClusteringAlgorithmListenerSupport<E>
{
    /** Source of the events. */
    private ClusteringAlgorithm<E> source;

    /** Event listener list. */
    private final EventListenerList listenerList;


    /**
     * Create a new support class meant to be subclassed.
     */
    protected ClusteringAlgorithmListenerSupport()
    {
        listenerList = new EventListenerList();
    }

    /**
     * Create a new support class that fires clustering algorithm events with the
     * specified source as the source of the events.
     *
     * @param source source of the events
     */
    public ClusteringAlgorithmListenerSupport(final ClusteringAlgorithm<E> source)
    {
        listenerList = new EventListenerList();
        setSource(source);
    }


    /**
     * Set the source of clustering algorithm events
     * to <code>source</code>.  Subclasses should call this method before
     * any of the <code>fireX</code> methods.
     *
     * @param source source of the events
     */
    protected final void setSource(final ClusteringAlgorithm<E> source)
    {
        this.source = source;
    }

    /**
     * Return the <code>EventListenerList</code> backing this
     * clustering algorithm listener support class.
     *
     * @return the <code>EventListenerList</code> backing this
     *    clustering algorithm listener support class.
     */
    protected final EventListenerList getEventListenerList()
    {
        return listenerList;
    }

    /**
     * Add the specified clustering algorithm listener.
     *
     * @param listener clustering algorithm listener to add
     */
    public final void addClusteringAlgorithmListener(final ClusteringAlgorithmListener<E> listener)
    {
        listenerList.add(ClusteringAlgorithmListener.class, listener);
    }

    /**
     * Remove the specified clustering algorithm listener.
     *
     * @param listener clustering algorithm listener to remove
     */
    public final void removeClusteringAlgorithmListener(final ClusteringAlgorithmListener<E> listener)
    {
        listenerList.remove(ClusteringAlgorithmListener.class, listener);
    }

    /**
     * Fire an exit failed event to all registered <code>ClusteringAlgorithmListener</code>s.
     */
    public void fireExitFailed()
    {
        Object[] listeners = listenerList.getListenerList();
        ClusteringAlgorithmEvent<E> e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == ClusteringAlgorithmListener.class)
            {
                // lazily create the event
                if (e == null)
                {
                    e = new ClusteringAlgorithmEvent<E>(source);
                }
                ((ClusteringAlgorithmListener<E>) listeners[i + 1]).exitFailed(e);
            }
        }
    }

    /**
     * Fire the specified exit failed event to all registered <code>ClusteringAlgorithmListener</code>s.
     *
     * @param e exit failed event
     */
    public void fireExitFailed(final ClusteringAlgorithmEvent<E> e)
    {
        Object[] listeners = listenerList.getListenerList();

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == ClusteringAlgorithmListener.class)
            {
                ((ClusteringAlgorithmListener<E>) listeners[i + 1]).exitFailed(e);
            }
        }
    }

    /**
     * Fire an exit succeeded event to all registered <code>ClusteringAlgorithmListener</code>s.
     */
    public void fireExitSucceeded()
    {
        Object[] listeners = listenerList.getListenerList();
        ClusteringAlgorithmEvent<E> e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == ClusteringAlgorithmListener.class)
            {
                // lazily create the event
                if (e == null)
                {
                    e = new ClusteringAlgorithmEvent<E>(source);
                }
                ((ClusteringAlgorithmListener<E>) listeners[i + 1]).exitSucceeded(e);
            }
        }
    }

    /**
     * Fire the specified exit succeeded event to all registered <code>ClusteringAlgorithmListener</code>s.
     *
     * @param e exit succeeded event
     */
    public void fireExitSucceeded(final ClusteringAlgorithmEvent<E> e)
    {
        Object[] listeners = listenerList.getListenerList();

        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == ClusteringAlgorithmListener.class)
            {
                ((ClusteringAlgorithmListener<E>) listeners[i + 1]).exitSucceeded(e);
            }
        }
    }
}