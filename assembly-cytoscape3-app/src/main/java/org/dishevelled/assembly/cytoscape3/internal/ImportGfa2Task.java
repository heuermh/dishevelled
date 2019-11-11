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

import java.io.File;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.Tunable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Import Graphical Fragment Assembly (GFA) 2.0 task.
 *
 * @author  Michael Heuer
 */
final class ImportGfa2Task extends AbstractTask
{
    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /** Input file tunable. */
    @Tunable(description = "Graphical Fragment Assembly (GFA) 2.0 file",
             tooltip = "<html>Input file in Graphical Fragment Assembly (GFA) 2.0 format.<br/>Compressed files (.bgz, .bzip2, .gz) are supported.</html>",
             required = true,
             params = "input=true;fileCategory=unspecified")
    public File inputFile;


    @Override
    public void run(final TaskMonitor taskMonitor) throws Exception
    {
        logger.info("run, inputFile " + inputFile);
    }
}
