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

import static com.google.common.base.Preconditions.checkNotNull;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

/**
 * Assembly app task.
 *
 * @author  Michael Heuer
 */
public final class AssemblyAppTask extends AbstractTask
{
    /** Assembly model. */
    private final AssemblyModel assemblyModel;

    /**
     * Create a new assembly app task.
     *
     * @param assemblyModel assembly model, must not be null
     */
    AssemblyAppTask(final AssemblyModel assemblyModel)
    {
        checkNotNull(assemblyModel);
        this.assemblyModel = assemblyModel;
    }


    @Override
    public void run(final TaskMonitor taskMonitor) throws Exception
    {
        //JFrame frame = (JFrame) windowForComponent((Component) event.getSource());
        JFrame frame = null;
        JDialog dialog = new JDialog(frame, "Assembly" + (assemblyModel.getInputFileName() == null ? "" : " " + assemblyModel.getInputFileName()));

        // todo: is an app already visible?
        AssemblyApp app = new AssemblyApp(assemblyModel);
        dialog.setContentPane(app);

        dialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        //installCloseKeyBinding(dialog);
        dialog.setSize(800, 600);
        dialog.setVisible(true);
    }
}
