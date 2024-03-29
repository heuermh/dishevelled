/*

    dsh-venn-cytoscape3-app  Cytoscape3 app for venn and euler diagrams.
    Copyright (c) 2012-2019 held jointly by the individual authors.

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

import static org.junit.Assert.assertNotNull;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.group.CyGroupManager;
import org.cytoscape.service.util.CyServiceRegistrar;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Unit test for VennDiagramsAction.
 *
 * @author  Michael Heuer
 */
public final class VennDiagramsActionTest
{
    private VennDiagramsAction vennDiagramsAction;
    @Mock
    private CyApplicationManager applicationManager;
    @Mock
    private CyGroupManager groupManager;
    @Mock
    private CyServiceRegistrar serviceRegistrar;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        vennDiagramsAction = new VennDiagramsAction(applicationManager, groupManager, serviceRegistrar);
    }

    @Test
    public void testConstructor()
    {
        assertNotNull(vennDiagramsAction);
    }

    @Test(expected=NullPointerException.class)
    public void testActionPerformedNullActionEvent()
    {
        vennDiagramsAction.actionPerformed(null);
    }
}
