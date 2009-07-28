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

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.Collection;
import java.util.Iterator;

import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import javax.swing.border.EmptyBorder;

import org.dishevelled.curate.CullView;

import org.dishevelled.iconbundle.tango.TangoProject;

import org.dishevelled.identify.IdToolBar;

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

    /** Toolbar. */
    private final IdToolBar toolBar;


    /**
     * Create a new cull dialog.
     */
    public CullDialog()
    {
        cullPanel = new CullPanel<E>();
        toolBar = new IdToolBar();
        toolBar.add(cullPanel.getRemoveAction());
        toolBar.add(cullPanel.getRemoveAllAction());
        toolBar.addSeparator();
        toolBar.add(cullPanel.getStartAssistAction());
        toolBar.add(cullPanel.getStopAssistAction());
        toolBar.displayIcons();
        toolBar.setFloatable(false);
        toolBar.setIconSize(TangoProject.SMALL);
        // todo:  would be nice if actions displayed checkbox in menu
        final JPopupMenu toolBarContextMenu = new JPopupMenu();
        for (Iterator<?> displayActions = ((IdToolBar) toolBar).getDisplayActions().iterator(); displayActions.hasNext(); )
        {
            Action displayAction = (Action) displayActions.next();
            toolBarContextMenu.add(displayAction);
        }
        toolBarContextMenu.addSeparator();
        for (Iterator<?> iconSizeActions = ((IdToolBar) toolBar).getDefaultIconSizeActions().iterator(); iconSizeActions.hasNext(); )
        {
            Action iconSizeAction = (Action) iconSizeActions.next();
            toolBarContextMenu.add(iconSizeAction);
        }
        toolBar.addMouseListener(new MouseAdapter()
        {
            /** {@inheritDoc} */
            public void mousePressed(final MouseEvent event)
            {
                showContextMenu(event);
            }

            /** {@inheritDoc} */
            public void mouseReleased(final MouseEvent event)
            {
                showContextMenu(event);
            }

            /**
             * Show the context menu if the specified mouse event is a popup trigger.
             *
             * @param event event
             */
            private void showContextMenu(final MouseEvent event)
            {
                if (event.isPopupTrigger())
                {
                    toolBarContextMenu.show(event.getComponent(), event.getX(), event.getY());
                }
            }
        });

        JPanel contentPane = (JPanel) getContentPane();
        contentPane.setLayout(new BorderLayout());
        cullPanel.setBorder(new EmptyBorder(20, 10, 20, 10));
        JPanel buttonPanel = createButtonPanel();
        buttonPanel.setBorder(new EmptyBorder(0, 10, 10, 10));

        contentPane.add("North", toolBar);
        contentPane.add("Center", cullPanel);
        contentPane.add("South", buttonPanel);
        contentPane.requestFocus();
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