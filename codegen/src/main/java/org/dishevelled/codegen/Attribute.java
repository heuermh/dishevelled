/*

    dsh-codegen  Source code generation suite.
    Copyright (c) 2004-2012 held jointly by the individual authors.

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
 * An attribute on a class or interface description.  An attribute has a role,
 * a cardinality, and if the cardinality is <b>Cardinality.ZeroToMany</b> or
 * <b>Cardinality.OneToMany</b>, a collection description.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class Attribute
{
    /** The lowercase name for this attribute. */
    private final String lower;

    /** The mixed-case name for this attribute. */
    private final String mixed;

    /** The uppercase name for this attribute. */
    private final String upper;

    /** The description for this attribute. */
    private final String description;

    /** The role for this attribute. */
    private final Role role;

    /** The cardinality for this attribute. */
    private final Cardinality cardinality;

    /** The collection description for this attribute. */
    private final CollectionDescription collectionDescription;

    /** True if this is a "bound" attribute. */
    private final boolean bound;


    /**
     * Create a new attribute with the specified name, role name,
     * and cardinality.  The cardinality must be one of <b>Cardinality.ZeroToOne</b>
     * or <b>Cardinality.StrictlyOne</b>.
     *
     * @param name attribute name
     * @param roleName role name
     * @param cardinality cardinality, must not be null and must be one
     *    of <b>Cardinality.ZeroToOne</b> or <b>Cardinality.StrictlyOne</b>
     */
    public Attribute(final String name,
                     final String roleName,
                     final Cardinality cardinality)
    {
        if ((cardinality == Cardinality.ZeroToMany) || (cardinality == Cardinality.OneToMany))
        {
            throw new IllegalArgumentException("cardinality must be one of {ZeroToOne, StrictlyOne}");
        }

        this.lower = CodegenUtils.makeLowercase(name);
        this.mixed = CodegenUtils.makeMixedCase(name);
        this.upper = CodegenUtils.makeUppercase(name);
        this.description = CodegenUtils.makeDescription(name);

        this.role = new Role(roleName);

        this.cardinality = cardinality;
        this.collectionDescription = null;
        this.bound = false;
    }

    /**
     * Create a new attribute with the specified name, role name,
     * cardinality, and bound flag.  The cardinality must be one of <b>Cardinality.ZeroToOne</b>
     * or <b>Cardinality.StrictlyOne</b>.
     *
     * @param name attribute name
     * @param roleName role name
     * @param cardinality cardinality, must not be null and must be one
     *    of <b>Cardinality.ZeroToOne</b> or <b>Cardinality.StrictlyOne</b>
     * @param bound true if this is a "bound" attribute
     */
    public Attribute(final String name,
                     final String roleName,
                     final Cardinality cardinality,
                     final boolean bound)
    {
        if ((cardinality == Cardinality.ZeroToMany) || (cardinality == Cardinality.OneToMany))
        {
            throw new IllegalArgumentException("cardinality must be one of {ZeroToOne, StrictlyOne}");
        }

        this.lower = CodegenUtils.makeLowercase(name);
        this.mixed = CodegenUtils.makeMixedCase(name);
        this.upper = CodegenUtils.makeUppercase(name);
        this.description = CodegenUtils.makeDescription(name);

        this.role = new Role(roleName);

        this.cardinality = cardinality;
        this.collectionDescription = null;
        this.bound = bound;
    }

    /**
     * Create a new attribute with the specified name, role name, and
     * cardinality and choose a collection description that satisfies the
     * specified boolean parameters.
     *
     * @param name attribute name
     * @param roleName role name
     * @param cardinality cardinality, must not be null
     * @param indexed true if the collection should be indexed
     * @param unique true if the collection should not allow duplicate elements
     * @param ordered true if the collection should iterate over elements in <i>insertion-order</i>
     * @param sorted true if the collection should iterate over elements in ascending element order,
     *    sorted according to the <i>natural ordering</i> of its elements (see Comparable), or by a Comparator
     *    provided at creation time
     */
    public Attribute(final String name,
                     final String roleName,
                     final Cardinality cardinality,
                     final boolean indexed,
                     final boolean unique,
                     final boolean ordered,
                     final boolean sorted)
    {
        this.lower = CodegenUtils.makeLowercase(name);
        this.mixed = CodegenUtils.makeMixedCase(name);
        this.upper = CodegenUtils.makeUppercase(name);
        this.description = CodegenUtils.makeDescription(name);

        this.role = new Role(roleName);

        this.cardinality = cardinality;
        if ((cardinality == Cardinality.ZeroToMany) || (cardinality == Cardinality.OneToMany))
        {
            this.collectionDescription = CollectionDescription.choose(indexed, unique, ordered, sorted);
        }
        else
        {
            this.collectionDescription = null;
        }
        this.bound = false;
    }

    /**
     * Create a new attribute with the specified name, role name,
     * cardinality, and bound flag and choose a collection description that satisfies the
     * specified boolean parameters.
     *
     * @param name attribute name
     * @param roleName role name
     * @param cardinality cardinality, must not be null
     * @param bound true if this is a "bound" attribute
     * @param indexed true if the collection should be indexed
     * @param unique true if the collection should not allow duplicate elements
     * @param ordered true if the collection should iterate over elements in <i>insertion-order</i>
     * @param sorted true if the collection should iterate over elements in ascending element order,
     *    sorted according to the <i>natural ordering</i> of its elements (see Comparable), or by a Comparator
     *    provided at creation time
     */
    public Attribute(final String name,
                     final String roleName,
                     final Cardinality cardinality,
                     final boolean bound,
                     final boolean indexed,
                     final boolean unique,
                     final boolean ordered,
                     final boolean sorted)
    {
        this.lower = CodegenUtils.makeLowercase(name);
        this.mixed = CodegenUtils.makeMixedCase(name);
        this.upper = CodegenUtils.makeUppercase(name);
        this.description = CodegenUtils.makeDescription(name);

        this.role = new Role(roleName);

        this.cardinality = cardinality;
        if ((cardinality == Cardinality.ZeroToMany) || (cardinality == Cardinality.OneToMany))
        {
            this.collectionDescription = CollectionDescription.choose(indexed, unique, ordered, sorted);
        }
        else
        {
            this.collectionDescription = null;
        }
        this.bound = bound;
    }

    /**
     * Create a new attribute from the specified parameters.
     *
     * @param lower lowercase name for this attribute
     * @param mixed mixed-case name for this attribute
     * @param upper uppercase name for this attribute
     * @param description description for this attribute
     * @param role role for this attribute, must not be null
     * @param cardinality cardinality for this attribute, must not be null
     * @param collectionDescription collection description for this attribute
     */
    public Attribute(final String lower,
                     final String mixed,
                     final String upper,
                     final String description,
                     final Role role,
                     final Cardinality cardinality,
                     final CollectionDescription collectionDescription)
    {
        if (role == null)
        {
            throw new IllegalArgumentException("role must not be null");
        }
        if (cardinality == null)
        {
            throw new IllegalArgumentException("cardinality must not be null");
        }

        this.lower = lower;
        this.mixed = mixed;
        this.upper = upper;
        this.description = description;
        this.role = role;
        this.cardinality = cardinality;
        this.collectionDescription = collectionDescription;
        this.bound = false;
    }

    /**
     * Create a new attribute from the specified parameters.
     *
     * @param lower lowercase name for this attribute
     * @param mixed mixed-case name for this attribute
     * @param upper uppercase name for this attribute
     * @param description description for this attribute
     * @param role role for this attribute, must not be null
     * @param cardinality cardinality for this attribute, must not be null
     * @param collectionDescription collection description for this attribute
     * @param bound true if this is a "bound" attribute
     */
    public Attribute(final String lower,
                     final String mixed,
                     final String upper,
                     final String description,
                     final Role role,
                     final Cardinality cardinality,
                     final CollectionDescription collectionDescription,
                     final boolean bound)
    {
        if (role == null)
        {
            throw new IllegalArgumentException("role must not be null");
        }
        if (cardinality == null)
        {
            throw new IllegalArgumentException("cardinality must not be null");
        }

        this.lower = lower;
        this.mixed = mixed;
        this.upper = upper;
        this.description = description;
        this.role = role;
        this.cardinality = cardinality;
        this.collectionDescription = collectionDescription;
        this.bound = bound;
    }


    /**
     * Return the lowercase name for this attribute.
     *
     * @return the lowercase name for this attribute
     */
    public String getLower()
    {
        return lower;
    }

    /**
     * Return the mixed-case name for this attribute.
     *
     * @return the mixed-case name for this attribute
     */
    public String getMixed()
    {
        return mixed;
    }

    /**
     * Return the uppercase name for this attribute.
     *
     * @return the uppercase name for this attribute
     */
    public String getUpper()
    {
        return upper;
    }

    /**
     * Return the description for this attribute.
     *
     * @return the description for this attribute
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Return the role for this attribute.  The role will not be null.
     *
     * @return the role for this attribute
     */
    public Role getRole()
    {
        return role;
    }

    /**
     * Return the cardinality for this attribute.  The cardinality will not be null.
     *
     * @return the cardinality for this attribute
     */
    public Cardinality getCardinality()
    {
        return cardinality;
    }

    /**
     * Return the collection description for this attribute.
     * The collection description may be null.
     *
     * @return the collection description for this attribute
     */
    public CollectionDescription getCollectionDescription()
    {
        return collectionDescription;
    }

    /**
     * Return true if this is a "bound" attribute.
     *
     * @return true if this is a "bound" attribute
     */
    public boolean isBound()
    {
        return bound;
    }
}
