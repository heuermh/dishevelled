/*

    dsh-curate  Interfaces for curating collections quickly.
    Copyright (c) 2007 held jointly by the individual authors.

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

import java.awt.BorderLayout;

import java.util.Collection;

import javax.swing.JPanel;

import javax.swing.border.EmptyBorder;

import org.dishevelled.curate.CullView;

/**
 * Cull dialog.
 *
 * @param <E> input and result collections element type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class CullDialog<E>
    extends AbstractCurateDialog
    implements CullView<E>
{
    /** Cull panel. */
    private final CullPanel<E> cullPanel;


    /**
     * Create a new cull dialog.
     */
    public CullDialog()
    {
        cullPanel = new CullPanel<E>();

        JPanel contentPane = (JPanel) getContentPane();
        contentPane.setLayout(new BorderLayout());
        cullPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
        contentPane.add("Center", cullPanel);
        contentPane.add("South", createButtonPanel());
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
    }


    /** {@inheritDoc} */
    public void setInput(final Collection<E> input)
    {
        cullPanel.setInput(input);
    }

    /** {@inheritDoc} */
    public Collection<E> getRemaining()
    {
        return cullPanel.getRemaining();
    }

    /** {@inheritDoc} */
    public Collection<E> getRemoved()
    {
        return cullPanel.getRemoved();
    }

    /** {@inheritDoc} */
    protected void cancel()
    {
        // remaining == input, removed == empty
        setVisible(false);
    }

    /** {@inheritDoc} */
    protected void help()
    {
        // empty
    }

    /** {@inheritDoc} */
    protected void ok()
    {
        // notify clients that remaining and removed are ready to be read
        setVisible(false);
    }
}