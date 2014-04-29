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

import static org.dishevelled.variation.cytoscape3.internal.MergeStrategy.REPLACE;
import static org.dishevelled.variation.cytoscape3.internal.MergeStrategy.RETAIN;

import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

/**
 * Annotate variation consequences task factory.
 *
 * @author  Michael Heuer
 */
final class AnnotateVariationConsequencesTaskFactory
    extends AbstractTaskFactory
{
    /** Variation model. */
    private final VariationModel model;


    /**
     * Create a new annotate variation consequences task factory.
     *
     * @param model model, must not be null
     */
    AnnotateVariationConsequencesTaskFactory(final VariationModel model)
    {
        checkNotNull(model);
        this.model = model;
    }


    @Override
    public TaskIterator createTaskIterator()
    {
        RetrieveFeaturesTask retrieveFeaturesTask = new RetrieveFeaturesTask(model, model.features().isEmpty() ? REPLACE : RETAIN);
        AddVariationsTask addVariationsTask = new AddVariationsTask(model, model.variations().isEmpty() ? REPLACE : RETAIN);
        AnnotateVariationConsequencesTask annotateVariationConsequencesTask = new AnnotateVariationConsequencesTask(model, model.variationConsequences().isEmpty() ? REPLACE : RETAIN);
        return new TaskIterator(retrieveFeaturesTask, addVariationsTask, annotateVariationConsequencesTask);
    }
}
