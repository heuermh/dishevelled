/*

    dsh-curate  Interfaces for curating collections quickly.
    Copyright (c) 2007-2009 held jointly by the individual authors.

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
package org.dishevelled.curate.impl;

import org.dishevelled.curate.AbstractCullViewTest;
import org.dishevelled.curate.CullView;

/**
 * Unit test for CullPanel.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class CullPanelTest
    extends AbstractCullViewTest
{

    /** {@inheritDoc} */
    protected <E> CullView<E> createCullView()
    {
        return new CullPanel<E>();
    }
}
