/*

    dsh-variation-cytoscape3-app  Variation Cytoscape3 app.
    Copyright (c) 2013-2014 held jointly by the individual authors.

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
package org.dishevelled.variation.cytoscape3.internal;

import static org.junit.Assert.assertNotNull;

import org.cytoscape.application.CyApplicationManager;

import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;

import org.cytoscape.work.swing.DialogTaskManager;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Unit test for VariationAction.
 *
 * @author  Michael Heuer
 */
public final class VariationActionTest
{
    private VariationAction variationAction;

    @Mock
    private CyApplicationManager applicationManager;
    @Mock
    private DialogTaskManager dialogTaskManager;
    @Mock
    private VisualMappingManager visualMappingManager;
    @Mock
    private VisualMappingFunctionFactory continuousMappingFactory;
    @Mock
    private VisualMappingFunctionFactory discreteMappingFactory;
    @Mock
    private VisualMappingFunctionFactory passthroughMappingFactory;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        variationAction = new VariationAction(applicationManager,
                                              dialogTaskManager,
                                              visualMappingManager,
                                              continuousMappingFactory,
                                              discreteMappingFactory,
                                              passthroughMappingFactory);
    }

    @Test
    public void testConstructor()
    {
        assertNotNull(variationAction);
    }

    @Test(expected=NullPointerException.class)
    public void testActionPerformedNullActionEvent()
    {
        variationAction.actionPerformed(null);
    }
}