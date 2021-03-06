/*

    dsh-eventlist-view  Views for event lists.
    Copyright (c) 2010-2019 held jointly by the individual authors.

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
package org.dishevelled.eventlist.view;

import java.util.HashMap;
import java.util.Map;

import ca.odell.glazedlists.EventList;

import org.dishevelled.functor.UnaryFunction;

import org.dishevelled.iconbundle.tango.TangoProject;

import org.dishevelled.iconbundle.IconSize;

import org.dishevelled.identify.IdLabel;

/**
 * Identifiable elements summary.
 *
 * @param <E> model element type
 * @author  Michael Heuer
 */
public final class IdElementsSummary<E>
    extends ElementsSummary<E>
{
    /** Icon size. */
    private IconSize iconSize = DEFAULT_ICON_SIZE;

    /** Default icon size, {@link TangoProject#EXTRA_SMALL}. */
    public static final IconSize DEFAULT_ICON_SIZE = TangoProject.EXTRA_SMALL;

    /** Map of labels keyed by element. */
    private final Map<E, IdLabel> labels = new HashMap<E, IdLabel>();

    /** Model to view mapping. */
    private final UnaryFunction<E, IdLabel> modelToView = new UnaryFunction<E, IdLabel>()
        {
            @Override
            public IdLabel evaluate(final E element)
            {
                if (!labels.containsKey(element))
                {
                    IdLabel label = new IdLabel(element);
                    label.setIconSize(iconSize);
                    labels.put(element, label);
                }
                return labels.get(element);
            }
        };


    /**
     * Create a new identifiable elements summary with the specified model.
     *
     * @param model model, must not be null
     */
    public IdElementsSummary(final EventList<E> model)
    {
        super(model);
        setModelToView(modelToView);
    }


    /**
     * Return the icon size for this identifiable elements summary.
     *
     * @return the icon size for this identifiable elements summary
     */
    public IconSize getIconSize()
    {
        return iconSize;
    }

    /**
     * Set the icon size for this identifiable elements summary to <code>iconSize</code>.
     * Defaults to {@link #DEFAULT_ICON_SIZE}.
     *
     * <p>This is a bound property.</p>
     *
     * @param iconSize icon size for this identifiable elements summary, must not be null
     */
    public void setIconSize(final IconSize iconSize)
    {
        IconSize oldIconSize = this.iconSize;
        this.iconSize = iconSize;
        updateIconSize();
        firePropertyChange("iconSize", oldIconSize, this.iconSize);
    }

    /**
     * Update icon size.
     */
    private void updateIconSize()
    {
        for (IdLabel label : labels.values())
        {
            label.setIconSize(iconSize);
        }
        updateComponents();
    }
}
