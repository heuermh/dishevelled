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

import static org.junit.Assert.assertNotNull;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.group.CyGroupManager;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.service.util.internal.FakeBundleContext;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.osgi.framework.BundleContext;

/**
 * Unit test for CyActivator.
 *
 * @author  Michael Heuer
 */
public final class CyActivatorTest
{
    private CyActivator cyActivator;
    private BundleContext bundleContext;
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
        cyActivator = new CyActivator();
        bundleContext = new FakeBundleContext(CyApplicationManager.class, CyGroupManager.class, CyServiceRegistrar.class);
    }

    @Test
    public void testConstructor()
    {
        assertNotNull(cyActivator);
    }

    @Test(expected=NullPointerException.class)
    public void testStartNullBundleContext()
    {
        cyActivator.start(null);
    }

    @Test
    public void testStart()
    {
        cyActivator.start(bundleContext);
    }
}