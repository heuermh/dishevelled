/*

    dsh-assembly-cytoscape3-app  Assembly Cytoscape3 app.
    Copyright (c) 2019-2021 held jointly by the individual authors.

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
package org.dishevelled.assembly.cytoscape3.internal;

import static com.google.common.base.Preconditions.checkNotNull;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import org.dishevelled.bio.annotation.Annotation;

import org.dishevelled.bio.assembly.gfa1.Path;
import org.dishevelled.bio.assembly.gfa1.Reference;
import org.dishevelled.bio.assembly.gfa1.Traversal;

/**
 * Assembly model.
 *
 * @author  Michael Heuer
 */
final class AssemblyModel
{
    /** Paths. */
    private final EventList<Path> paths;

    /** Path traversals, by selected path. */
    private final EventList<Traversal> traversals;

    /** Path traversals keyed by path. */
    private final ListMultimap<Path, Traversal> traversalsByPath;

    /** Input file name. */
    private String inputFileName;

    /** Selected path, if any. */
    private Path path;

    /** Property change support. */
    private final PropertyChangeSupport propertyChangeSupport;


    /**
     * Create a new empty assembly model.
     */
    AssemblyModel()
    {
        inputFileName = null;
        path = null;
        paths = GlazedLists.eventList(new ArrayList<Path>());
        traversals = GlazedLists.eventList(new ArrayList<Traversal>());
        traversalsByPath = ArrayListMultimap.create();
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    /**
     * Create a new assembly model with the specified GFA 1.0 paths.
     *
     * @param paths zero or more GFA 1.0 paths, must not be null
     */
    AssemblyModel(final Iterable<Path> paths)
    {
        this();
        setPaths(paths, ArrayListMultimap.<String, Traversal>create());
    }


    /**
     * Set the paths for this assembly model to the specified GFA 1.0 paths.
     *
     * @param paths zero or more GFA 1.0 paths, must not be null
     * @param traversalsByPathName traversals keyed by path name, must not be null
     */
    void setPaths(final Iterable<Path> paths, final ListMultimap<String, Traversal> traversalsByPathName)
    {
        checkNotNull(paths);
        checkNotNull(traversalsByPathName);

        // reset if necessary
        if (!this.paths.isEmpty())
        {
            setPath(null);
            this.paths.clear();
            traversals.clear();
            traversalsByPath.clear();
        }

        // create traversals from paths if necessary
        for (Path path : paths)
        {
            List<Traversal> traversals = traversalsByPathName.get(path.getName());
            traversalsByPath.putAll(path, traversals.isEmpty() ? traversalsFor(path) : traversals);
        }
        if (!traversalsByPath.isEmpty())
        {
            Set<Path> keys = traversalsByPath.keySet();
            paths().addAll(keys);
            setPath(keys.iterator().next());
        }
    }

    /**
     * Return the input file name for this assembly model, if any.
     *
     * @return the input file name for this assembly model, if any
     */
    String getInputFileName()
    {
        return inputFileName;
    }

    /**
     * Set the input file name for this assembly model to <code>inputFileName</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param inputFileName input file name for this assembly model, if any
     */
    void setInputFileName(final String inputFileName)
    {
        String oldInputFileName = this.inputFileName;
        this.inputFileName = inputFileName;
        propertyChangeSupport.firePropertyChange("inputFileName", oldInputFileName, this.inputFileName);
    }

    /**
     * Return the selected path for this assembly model, if any.
     *
     * @return the selected path for this assembly model, if any
     */
    Path getPath()
    {
        return path;
    }

    /**
     * Set the selected path for this assembly model to <code>path</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param path selected path for this assembly model, if any
     */
    void setPath(final Path path)
    {
        Path oldPath = this.path;
        this.path = path;

        traversals.clear();
        if (this.path != null && traversalsByPath.containsKey(path))
        {
            traversals.addAll(traversalsByPath.get(path));
        }
        propertyChangeSupport.firePropertyChange("path", oldPath, this.path);
    }

    /**
     * Return the paths for this assembly model.
     *
     * @return the paths for this assembly model
     */
    EventList<Path> paths()
    {
        return paths;
    }

    /**
     * Return the traversals for this assembly model.
     *
     * @return the traversals for this assembly model
     */
    EventList<Traversal> traversals()
    {
        return traversals;
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


    /**
     * Create and return a list of path traversals for the specified path.
     *
     * @param path path, must not be null
     * @return a list of path traversals for the specified path
     */
    static List<Traversal> traversalsFor(final Path path)
    {
        checkNotNull(path);

        int size = path.getSegments().size();
        List<Traversal> traversals = new ArrayList<Traversal>(size);

        Reference source = null;
        Reference target = null;
        String overlap = null;
        Map<String, Annotation> emptyAnnotations = Collections.emptyMap();
        for (int i = 0; i < size; i++)
        {
            target = path.getSegments().get(i);
            if (i > 0)
            {
                overlap = (path.getOverlaps() != null && path.getOverlaps().size() > i) ? path.getOverlaps().get(i - 1) : null;
            }
            if (source != null)
            {
                Traversal traversal = new Traversal(path.getName(), i - 1, source, target, overlap, emptyAnnotations);
                traversals.add(traversal);
            }
            source = target;
        }
        return traversals;
    }
}
