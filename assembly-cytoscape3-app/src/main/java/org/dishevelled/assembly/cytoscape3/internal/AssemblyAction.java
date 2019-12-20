/*

    dsh-assembly-cytoscape3-app  Assembly Cytoscape3 app.
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
package org.dishevelled.assembly.cytoscape3.internal;

import static javax.swing.SwingUtilities.windowForComponent;

import static com.google.common.base.Preconditions.checkNotNull;

import java.awt.Component;

import java.awt.event.ActionEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.cytoscape.application.swing.AbstractCyAction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Assembly action.
 *
 * @author  Michael Heuer
 */
final class AssemblyAction extends AbstractCyAction
{
    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /** Assembly model. */
    private final AssemblyModel assemblyModel;


    /**
     * Create a new assembly action.
     *
     * @param assemblyModel assembly model, must not be null
     */
    AssemblyAction(final AssemblyModel assemblyModel)
    {
        super("Open assembly view...");
        setPreferredMenu("Apps.Assembly");

        checkNotNull(assemblyModel);
        this.assemblyModel = assemblyModel;
    }


    @Override
    public void actionPerformed(final ActionEvent event)
    {
        checkNotNull(event);

        JFrame frame = (JFrame) windowForComponent((Component) event.getSource());
        JDialog dialog = new JDialog(frame, "Assembly" + (assemblyModel.getInputFileName() == null ? "" : " " + assemblyModel.getInputFileName()));

        AssemblyApp app = new AssemblyApp(assemblyModel);
        dialog.setContentPane(app);

        dialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        //installCloseKeyBinding(dialog);
        dialog.setSize(400, 300);
        dialog.setVisible(true);
    }
}
