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

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

/**
 * Self similarity task.
 *
 * @author  Michael Heuer
 */
final class SelfSimilarityTask
    extends AbstractTask
{

    @Override
    public void run(final TaskMonitor taskMonitor)
    {
        taskMonitor.setTitle("Self similarity");
        taskMonitor.setProgress(0.0d);

        // call out to blast, generate edges file

        taskMonitor.setProgress(1.0d);
    }
}