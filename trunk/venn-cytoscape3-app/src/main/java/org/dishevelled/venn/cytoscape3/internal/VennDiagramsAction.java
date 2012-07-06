/*

    dsh-venn-cytoscape3-app  Cytoscape3 app for venn diagrams.
    Copyright (c) 2012 held jointly by the individual authors.

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
package org.dishevelled.venn.cytoscape3.internal;

import static javax.swing.SwingUtilities.windowForComponent;
import static org.dishevelled.venn.cytoscape3.internal.VennDiagramsUtils.installCloseKeyBinding;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.group.CyGroupManager;
import org.cytoscape.service.util.CyServiceRegistrar;

/**s
 * Venn diagrams action.
 */
final class VennDiagramsAction extends AbstractCyAction
{
    /** Application manager. */
    private final CyApplicationManager applicationManager;

    /** Group manager. */
    private final CyGroupManager groupManager;

    /** Service registrar. */
    private final CyServiceRegistrar serviceRegistrar;


    /**
     * Create a new venn diagrams action.
     *
     * @param applicationManager application manager, must not be null
     * @param groupManager group manager, must not be null
     * @param serviceRegistrar service registrar, must not be null
     */
    VennDiagramsAction(final CyApplicationManager applicationManager, final CyGroupManager groupManager, final CyServiceRegistrar serviceRegistrar)
    {
        super("Venn/Euler Diagrams");
        if (applicationManager == null)
        {
            throw new IllegalArgumentException("applicationManager must not be null");
        }
        if (groupManager == null)
        {
            throw new IllegalArgumentException("groupManager must not be null");
        }
        if (serviceRegistrar == null)
        {
            throw new IllegalArgumentException("serviceRegistrar must not be null");
        }
        this.applicationManager = applicationManager;
        this.groupManager = groupManager;
        this.serviceRegistrar = serviceRegistrar;

        setPreferredMenu("Apps");
    }


    @Override
    public void actionPerformed(final ActionEvent event)
    {
        if (event == null)
        {
            throw new NullPointerException("event must not be null");
        }
        JFrame frame = (JFrame) windowForComponent((Component) event.getSource());
        JDialog dialog = new JDialog(frame, "Venn/Euler Diagrams"); // i18n
        dialog.setContentPane(new GroupsView(applicationManager, groupManager, serviceRegistrar));
        dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        installCloseKeyBinding(dialog);
        dialog.setBounds(200, 200, 400, 400);
        dialog.setVisible(true);
    }
}