/*

    dsh-codegen  Source code generation suite.
    Copyright (c) 2004-2009 held jointly by the individual authors.

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
package org.dishevelled.codegen;

/**
 * A role for an attribute or association.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class Role
{
    /** The lowercase name for this role. */
    private final String lower;

    /** The mixed-case name for this role. */
    private final String mixed;

    /** The uppercase name for this role. */
    private final String upper;

    /** The description for this role. */
    private final String description;


    /**
     * Create a new role with the specified name.
     *
     * @param name role name
     */
    public Role(final String name)
    {
        this.lower = CodegenUtils.makeLowercase(name);
        this.upper = CodegenUtils.makeUppercase(name);
        this.mixed = CodegenUtils.makeMixedCase(name);
        this.description = CodegenUtils.makeDescription(name);
    }

    /**
     * Create a new role from the specified parameters.
     *
     * @param lower lowercase name for this role
     * @param mixed mixed-case name for this role
     * @param upper uppercase name for this role
     * @param description description for this role
     */
    public Role(final String lower,
                final String mixed,
                final String upper,
                final String description)
    {
        this.lower = lower;
        this.mixed = mixed;
        this.upper = upper;
        this.description = description;
    }


    /**
     * Return the lowercase name for this role.
     *
     * @return the lowercase name for this role
     */
    public String getLower()
    {
        return lower;
    }

    /**
     * Return the mixed-case name for this role.
     *
     * @return the mixed-case name for this role
     */
    public String getMixed()
    {
        return mixed;
    }

    /**
     * Return the uppercase name for this role.
     *
     * @return the uppercase name for this role
     */
    public String getUpper()
    {
        return upper;
    }

    /**
     * Return the description for this role.
     *
     * @return the description for this role
     */
    public String getDescription()
    {
        return description;
    }
}
