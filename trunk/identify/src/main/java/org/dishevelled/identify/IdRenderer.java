/*

    dsh-identify  Lightweight components for identifiable beans.
    Copyright (c) 2003-2011 held jointly by the individual authors.

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
package org.dishevelled.identify;

import java.awt.Component;

import javax.swing.Renderer;

import org.dishevelled.iconbundle.IconState;

/**
 * Renderer that displays the name property value
 * and appropriate icon from an icon bundle for a given bean.
 * <p>
 * The AWT component returned by the <code>getComponent</code>
 * method is an instance of IdLabel, so the following cast is safe:
 * <pre>
 * IdRenderer renderer = new IdRenderer();
 * IdLabel rendererComponent = (IdLabel) renderer.getComponent();
 * // set properties on the IdLabel, e.g.
 * rendererComponent.setIconSize(IconSize.DEFAULT_16X16);
 * </pre>
 * <p>
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class IdRenderer
    implements Renderer
{
    /** IdLabel. */
    private final IdLabel delegate = new IdLabel();


    /** {@inheritDoc} */
    public Component getComponent()
    {
        return delegate;
    }

    /** {@inheritDoc} */
    public void setValue(final Object value, final boolean selected)
    {
        delegate.setValue(value);
        delegate.setIconState(selected ? IconState.SELECTED : IconState.NORMAL);
    }
}
