/*

    dsh-identify  Lightweight components for identifiable beans.
    Copyright (c) 2003-2013 held jointly by the individual authors.

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
package org.dishevelled.identify;

import java.beans.BeanInfo;
import java.beans.BeanDescriptor;
import java.beans.SimpleBeanInfo;

import org.dishevelled.iconbundle.IconBundle;

import org.dishevelled.iconbundle.tango.TangoProject;

/**
 * BeanInfo for the ExampleWithNameProperty class.
 *
 * @author  Michael Heuer
 */
public final class ExampleWithNamePropertyBeanInfo
    extends SimpleBeanInfo
{
    /** Index of the name property descriptor. */
    private static final int NAME_PROPERTY_INDEX = 1;

    /** Icon bundle for the ExampleWithNameProperty class. */
    private static final IconBundle ICON_BUNDLE = TangoProject.TEXT_X_GENERIC;

    /** Identifiable bean descriptor. */
    private final BeanDescriptor beanDescriptor;


    /**
     * Create a new BeanInfo for the ExampleWithNameProperty class.
     */
    public ExampleWithNamePropertyBeanInfo()
    {
        super();

        this.beanDescriptor = new IdentifiableBeanDescriptor(ExampleWithNameProperty.class)
            {
                @Override
                public int getNamePropertyIndex()
                {
                    return NAME_PROPERTY_INDEX;
                }

                @Override
                public IconBundle getIconBundle()
                {
                    return ICON_BUNDLE;
                }
            };
    }


    @Override
    public BeanDescriptor getBeanDescriptor()
    {
        return beanDescriptor;
    }
}