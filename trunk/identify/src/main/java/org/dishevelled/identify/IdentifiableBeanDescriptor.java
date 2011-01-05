/*

    dsh-identify  Lightweight components for identifiable beans.
    Copyright (c) 2003-2011 held jointly by the individual authors.

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

import java.beans.BeanDescriptor;

import org.dishevelled.iconbundle.IconBundle;

/**
 * Abstract BeanDescriptor which provides for discovery of the
 * name property and icon bundle for a bean through introspection.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class IdentifiableBeanDescriptor
    extends BeanDescriptor
{

    /**
     * Create a new abstract BeanDescriptor for an identifiable
     * bean with the specified class.
     *
     * @param beanClass class of the identifiable bean
     */
    protected IdentifiableBeanDescriptor(final Class beanClass)
    {
        super(beanClass);
    }

    /**
     * Create a new abstract BeanDescriptor for an identifiable
     * bean with the specified class and customizer class.
     *
     * @param beanClass class of the identifiable bean
     * @param customizerClass class of the customizer to use for the
     *    identifiable bean
     */
    protected IdentifiableBeanDescriptor(final Class beanClass,
                                         final Class customizerClass)
    {
        super(beanClass, customizerClass);
    }


    /**
     * Return the index of the name property descriptor, or
     * <code>-1</code> if one does not exist.
     *
     * @return the index of the name property descriptor, or
     *    <code>-1</code> if one does not exist
     */
    public abstract int getNamePropertyIndex();

    /**
     * Return an icon bundle to use for all instances of the
     * identifiable bean class this descriptor describes.
     *
     * @return an icon bundle to use for all instances of the
     *    identifiable bean class this descriptor describes
     */
    public abstract IconBundle getIconBundle();
}