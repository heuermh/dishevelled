/*

    dsh-piccolo-eventlist-view  Piccolo2D views for event lists.
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
package org.dishevelled.piccolo.eventlist.view;

import ca.odell.glazedlists.EventList;

import org.dishevelled.functor.UnaryFunction;

import org.dishevelled.iconbundle.IconSize;

import org.dishevelled.iconbundle.tango.TangoProject;

import org.dishevelled.piccolo.identify.GenericIdNode;

/**
 * Identifiable elements node.
 *
 * @param <E> model element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class IdElementsNode<E>
    extends ElementsNode<E>
{
    /** Icon size. */
    private IconSize iconSize = DEFAULT_ICON_SIZE;

    /** Default icon size. */
    public static final IconSize DEFAULT_ICON_SIZE = TangoProject.LARGE;

    /** Model to view mapping. */
    private final UnaryFunction<E, GenericIdNode> modelToView = new UnaryFunction<E, GenericIdNode>()
        {
            /** {@inheritDoc} */
            public GenericIdNode evaluate(final E element)
            {
                return new GenericIdNode(element, iconSize);
            }
        };


    /**
     * Create a new identifiable elements summary with the specified model.
     *
     * @param model model, must not be null
     */
    public IdElementsNode(final EventList<E> model)
    {
        super(model);
        setModelToView(modelToView);
    }


    // allow other id node impls, setIconSize
}