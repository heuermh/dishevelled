/*

    dsh-color-scheme-cytoscape3-app  Color scheme Cytoscape3 app.
    Copyright (c) 2013 held jointly by the individual authors.

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
package org.dishevelled.color.scheme.cytoscape3.internal;

import static org.junit.Assert.assertNotNull;

import org.cytoscape.application.CyApplicationManager;

import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Unit test for ColorSchemeAction.
 *
 * @author  Michael Heuer
 */
public final class ColorSchemeActionTest
{
    private ColorSchemeAction colorSchemeAction;

    @Mock
    private CyApplicationManager applicationManager;
    @Mock
    private VisualMappingManager visualMappingManager;
    @Mock
    private VisualMappingFunctionFactory continuousMappingFactory;
    @Mock
    private VisualMappingFunctionFactory discreteMappingFactory;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        colorSchemeAction = new ColorSchemeAction(applicationManager,
                                                  visualMappingManager,
                                                  continuousMappingFactory,
                                                  discreteMappingFactory);
    }

    @Test
    public void testConstructor()
    {
        assertNotNull(colorSchemeAction);
    }

    @Test(expected=NullPointerException.class)
    public void testActionPerformedNullActionEvent()
    {
        colorSchemeAction.actionPerformed(null);
    }
}