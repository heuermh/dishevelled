/*

    dsh-assembly-cytoscape3-app  Assembly Cytoscape3 app.
    Copyright (c) 2019 held jointly by the individual authors.

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

import org.cytoscape.application.CyApplicationManager;

import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Import Graphical Fragment Assembly (GFA) 1.0 task factory.
 *
 * @author  Michael Heuer
 */
final class ImportGfa1TaskFactory extends AbstractTaskFactory
{
    /** Application manager. */
    private final CyApplicationManager applicationManager;

    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * Create a new import Graphical Fragment Assembly (GFA) 1.0 task factory
     * with the specified application manager.
     *
     * @param applicationManager application manager, must not be null
     */
    ImportGfa1TaskFactory(final CyApplicationManager applicationManager)
    {
        checkNotNull(applicationManager);
        this.applicationManager = applicationManager;
    }


    @Override
    public boolean isReady()
    {
        return true;
    }

    @Override
    public TaskIterator createTaskIterator()
    {
        return new TaskIterator(new ImportGfa1Task(applicationManager));
    }
}
