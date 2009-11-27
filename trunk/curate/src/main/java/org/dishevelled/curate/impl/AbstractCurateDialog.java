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

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import org.dishevelled.iconbundle.tango.TangoProject;

import org.dishevelled.identify.IdButton;
import org.dishevelled.identify.IdentifiableAction;

import org.dishevelled.layout.ButtonPanel;

/**
 * Abstract curate dialog.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractCurateDialog
    extends JDialog
{
    /** Cancel action. */
    private final Action cancel;

    /** Help action. */
    private final IdentifiableAction help;

    /** OK action. */
    private final Action ok;


    /**
     * Create a new abstract curate dialog.
     */
    protected AbstractCurateDialog()
    {
        super();

        //cancel = new IdentifiableAction("Cancel", TangoProject.DIALOG_CANCEL)
        cancel = new AbstractAction("Cancel")
            {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent event)
                {
                    cancel();
                }
            };

        help = new IdentifiableAction("Help", TangoProject.HELP_BROWSER)
            {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent event)
                {
                    help();
                }
            };
        help.setEnabled(false);

        //ok = new IdentifiableAction("OK", TangoProject.DIALOG_OK)
        ok = new AbstractAction("OK")
            {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent event)
                {
                    ok();
                }
            };
    }

    /**
     * Create and return the button panel.
     *
     * @return the button panel
     */
    protected final JPanel createButtonPanel()
    {
        ButtonPanel buttonPanel = new ButtonPanel();
        JButton okButton = buttonPanel.add(ok);
        JButton cancelButton = buttonPanel.add(cancel);
        buttonPanel.add(new IdButton(help));

        // don't let ok button get too small
        okButton.setPreferredSize(cancelButton.getPreferredSize());
        getRootPane().setDefaultButton(okButton);

        return buttonPanel;
    }

    /**
     * Return the cancel action.  The cancel action calls the
     * abstract <code>cancel()</code> method of this class.
     *
     * @see #cancel
     * @return the cancel action
     */
    protected final Action getCancelAction()
    {
        return cancel;
    }

    /**
     * Return the help action.  The help action calls the
     * abstract <code>help()</code> method of this class.
     *
     * @see #help
     * @return the help action
     */
    protected final Action getHelpAction()
    {
        return help;
    }

    /**
     * Return the OK action.  The OK action calls the
     * abstract <code>ok()</code> method of this class.
     *
     * @see #ok
     * @return the OK action
     */
    protected final Action getOKAction()
    {
        return ok;
    }

    /**
     * Abstract method called by the cancel action.
     *
     * @see #getCancelAction
     */
    protected abstract void cancel();

    /**
     * Abstract method called by the help action.
     *
     * @see #getHelpAction
     */
    protected abstract void help();

    /**
     * Abstract method called by the OK action.
     *
     * @see #getOKAction
     */
    protected abstract void ok();
}