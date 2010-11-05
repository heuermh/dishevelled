/*

    dsh-glazedlists-view  Views that use GlazedLists' EventList.
    Copyright (c) 2010 held jointly by the individual authors.

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
package org.dishevelled.glazedlists.view;

import ca.odell.glazedlists.EventList;

import java.util.HashMap;
import java.util.Map;

import org.dishevelled.functor.UnaryFunction;

import org.dishevelled.identify.IdLabel;

/**
 * Identifiable elements view.
 *
 * @param <E> model element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class IdElementsView<E>
    extends ElementsView<E>
{
    /** Map of labels keyed by element. */
    private final Map<E, IdLabel> labels = new HashMap<E, IdLabel>();

    /** Model to view mapping. */
    private final UnaryFunction<E, IdLabel> modelToView = new UnaryFunction<E, IdLabel>()
        {
            /** {@inheritDoc} */
            public IdLabel evaluate(final E element)
            {
                if (!labels.containsKey(element))
                {
                    IdLabel label = new IdLabel(element);
                    labels.put(element, label);
                }
                return labels.get(element);                
            }
        };

    /**
     * Create a new identifiable elements view with the specified model.
     *
     * @param model model, must not be null
     */
    public IdElementsView(final EventList<E> model)
    {
        super(model);
        setModelToView(modelToView);
    }

    // dispose?
}