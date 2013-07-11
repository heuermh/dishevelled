/*

    dsh-variation-cytoscape3-app  Variation Cytoscape3 app.
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
package org.dishevelled.variation.cytoscape3.internal;

import java.util.Properties;

import org.cytoscape.application.swing.CyAction;
import org.cytoscape.service.util.AbstractCyActivator;

import org.osgi.framework.BundleContext;

/**
 * Activator.
 *
 * @author  Michael Heuer
 */
public final class CyActivator extends AbstractCyActivator
{

    @Override
    public void start(final BundleContext bundleContext)
    {
        if (bundleContext == null)
        {
            throw new NullPointerException("bundleContext must not be null");
        }
        VariationAction variationAction = new VariationAction();
        Properties properties = new Properties();
        registerService(bundleContext, variationAction, CyAction.class, properties);
    }
}