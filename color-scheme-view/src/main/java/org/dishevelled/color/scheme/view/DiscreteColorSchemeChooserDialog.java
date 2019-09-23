/*

    dsh-color-scheme-view  Views for color schemes.
    Copyright (c) 2019 held jointly by the individual authors.

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
package org.dishevelled.color.scheme.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;

import java.awt.event.ActionEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import javax.swing.border.EmptyBorder;

import org.dishevelled.layout.ButtonPanel;

class DiscreteColorSchemeChooserDialog extends JDialog
{
    /** I18n. */
    private static final ResourceBundle I18N = ResourceBundle.getBundle("color-scheme-view", Locale.getDefault());

    /** Cancel action. */
    private final AbstractAction cancel = new AbstractAction(I18N.getString("DiscreteColorSchemeChooserDialog.cancel"))
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                cancel();
            }
        };

    /** OK action. */
    private final AbstractAction ok = new AbstractAction(I18N.getString("DiscreteColorSchemeChooserDialog.ok"))
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                ok();
            }
        };

    /** OK button. */
    private final JButton okButton;

    /** Was this chooser cancelled? */
    private boolean cancelled = true;

    /** Discrete color scheme chooser. */
    private final DiscreteColorSchemeChooser chooser;


    DiscreteColorSchemeChooserDialog(final Dialog owner, final String title, final boolean modal, final DiscreteColorSchemeChooser chooser)
    {
        super(owner, title, modal);

        ok.setEnabled(false);
        okButton = new JButton(ok);
        this.chooser = chooser;

        layoutComponents();
    }

    DiscreteColorSchemeChooserDialog(final Frame owner, final String title, final boolean modal, final DiscreteColorSchemeChooser chooser)
    {
        super(owner, title, modal);

        ok.setEnabled(false);
        okButton = new JButton(ok);
        this.chooser = chooser;

        layoutComponents();
    }

    private void layoutComponents()
    {
        ButtonPanel buttonPanel = new ButtonPanel();
        buttonPanel.setBorder(new EmptyBorder(0, 12, 12, 12));
        buttonPanel.add(okButton);
        buttonPanel.add(cancel);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(12, 12, 12, 12));
        mainPanel.add("Center", chooser);
        mainPanel.add("South", buttonPanel);

        setContentPane(mainPanel);
    }

    private void cancel()
    {
        cancelled = true;
        hide();
    }

    private void ok()
    {
        cancelled = false;
        hide();
    }

    boolean wasCancelled()
    {
        return cancelled;
    }
}
