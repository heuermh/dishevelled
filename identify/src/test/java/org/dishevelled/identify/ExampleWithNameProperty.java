/*

    dsh-identify  Lightweight components for identifiable beans.
    Copyright (c) 2003-2009 held jointly by the individual authors.

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

/**
 * Example object with name property.
 *
 * @author  Michael
 * @version $Revision$ $Date$
 */
public final class ExampleWithNameProperty
{
    /** The name for this example object with name property. */
    private final String name;


    /**
     * Create a new example object with name property with the specified name.
     *
     * @param name name for this example object with name property, must not be null
     */
    public ExampleWithNameProperty(final String name)
    {
        if (name == null)
        {
            throw new IllegalArgumentException("name must not be null");
        }

        this.name = name;
    }


    /**
     * Return the name for this example object with name property.
     * The name will not be null.  The identifiable BeanInfo for
     * this class will identify this property as the name property.
     *
     * @return the name for this example object with name property
     */
    public String getName()
    {
        return name;
    }
}