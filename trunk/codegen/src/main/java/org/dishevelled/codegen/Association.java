/*

    dsh-codegen  Source code generation suite.
    Copyright (c) 2004-2008 held jointly by the individual authors.

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
 * An association between a class or interface description and
 * a target class or interface description.  An association has a role,
 * a cardinality, and if the cardinality is <b>Cardinality.ZeroToMany</b> or
 * <b>Cardinality.OneToMany</b>, a collection description.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class Association
{
    /** The lowercase name for this association. */
    private final String lower;

    /** The mixed-case name for this association. */
    private final String mixed;

    /** The uppercase name for this association. */
    private final String upper;

    /** The package name for this association. */
    private final String packageName;

    /** The description for this association. */
    private final String description;

    /** The role for this association. */
    private final Role role;

    /** The cardinality for this association. */
    private final Cardinality cardinality;

    /** The collection description for this association. */
    private final CollectionDescription collectionDescription;

    /** True if this is a "bound" association. */
    private final boolean bound;


    /**
     * Create a new association with the specified class description, role name,
     * and cardinality.  The cardinality must be one of <b>Cardinality.ZeroToOne</b>
     * or <b>Cardinality.StrictlyOne</b>.
     *
     * @param cd class description, must not be null
     * @param roleName role name
     * @param cardinality cardinality, must not be null and must be one
     *    of <b>Cardinality.ZeroToOne</b> or <b>Cardinality.StrictlyOne</b>
     */
    public Association(final ClassDescription cd,
                       final String roleName,
                       final Cardinality cardinality)
    {
        if ((cardinality == Cardinality.ZeroToMany) || (cardinality == Cardinality.OneToMany))
        {
            throw new IllegalArgumentException("cardinality must be one of {ZeroToOne, StrictlyOne}");
        }

        this.lower = cd.getLower();
        this.mixed = cd.getMixed();
        this.upper = cd.getUpper();
        this.description = cd.getDescription();
        this.packageName = cd.getPackageName();

        this.role = new Role(roleName);

        this.cardinality = cardinality;
        this.collectionDescription = null;
        this.bound = false;
    }

    /**
     * Create a new association with the specified class description, role name,
     * cardinality, and bound flag.  The cardinality must be one of <b>Cardinality.ZeroToOne</b>
     * or <b>Cardinality.StrictlyOne</b>.
     *
     * @param cd class description, must not be null
     * @param roleName role name
     * @param cardinality cardinality, must not be null and must be one
     *    of <b>Cardinality.ZeroToOne</b> or <b>Cardinality.StrictlyOne</b>
     * @param bound true if this is a "bound" association
     */
    public Association(final ClassDescription cd,
                       final String roleName,
                       final Cardinality cardinality,
                       final boolean bound)
    {
        if ((cardinality == Cardinality.ZeroToMany) || (cardinality == Cardinality.OneToMany))
        {
            throw new IllegalArgumentException("cardinality must be one of {ZeroToOne, StrictlyOne}");
        }

        this.lower = cd.getLower();
        this.mixed = cd.getMixed();
        this.upper = cd.getUpper();
        this.description = cd.getDescription();
        this.packageName = cd.getPackageName();

        this.role = new Role(roleName);

        this.cardinality = cardinality;
        this.collectionDescription = null;
        this.bound = bound;
    }

    /**
     * Create a new association with the specified class description, role name, and
     * cardinality and choose a collection description that satisfies the
     * specified boolean parameters.
     *
     * @param cd class description, must not be null
     * @param roleName role name
     * @param cardinality cardinality, must not be null
     * @param indexed true if the collection should be indexed
     * @param unique true if the collection should not allow duplicate elements
     * @param ordered true if the collection should iterate over elements in <i>insertion-order</i>
     * @param sorted true if the collection should iterate over elements in ascending element order,
     *    sorted according to the <i>natural ordering</i> of its elements (see Comparable), or by a Comparator
     *    provided at creation time
     */
    public Association(final ClassDescription cd,
                       final String roleName,
                       final Cardinality cardinality,
                       final boolean indexed,
                       final boolean unique,
                       final boolean ordered,
                       final boolean sorted)
    {
        this.lower = cd.getLower();
        this.mixed = cd.getMixed();
        this.upper = cd.getUpper();
        this.description = cd.getDescription();
        this.packageName = cd.getPackageName();

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
     * Create a new association with the specified class description, role name,
     * cardinality, and bound flag and choose a collection description that satisfies the
     * specified boolean parameters.
     *
     * @param cd class description, must not be null
     * @param roleName role name
     * @param cardinality cardinality, must not be null
     * @param bound true if this is a "bound" association
     * @param indexed true if the collection should be indexed
     * @param unique true if the collection should not allow duplicate elements
     * @param ordered true if the collection should iterate over elements in <i>insertion-order</i>
     * @param sorted true if the collection should iterate over elements in ascending element order,
     *    sorted according to the <i>natural ordering</i> of its elements (see Comparable), or by a Comparator
     *    provided at creation time
     */
    public Association(final ClassDescription cd,
                       final String roleName,
                       final Cardinality cardinality,
                       final boolean bound,
                       final boolean indexed,
                       final boolean unique,
                       final boolean ordered,
                       final boolean sorted)
    {
        this.lower = cd.getLower();
        this.mixed = cd.getMixed();
        this.upper = cd.getUpper();
        this.description = cd.getDescription();
        this.packageName = cd.getPackageName();

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
     * Create a new association with the specified interface description, role name,
     * and cardinality.  The cardinality must be one of <b>Cardinality.ZeroToOne</b>
     * or <b>Cardinality.StrictlyOne</b>.
     *
     * @param id interface description, must not be null
     * @param roleName role name
     * @param cardinality cardinality, must not be null and must be one
     *    of <b>Cardinality.ZeroToOne</b> or <b>Cardinality.StrictlyOne</b>
     */
    public Association(final InterfaceDescription id,
                       final String roleName,
                       final Cardinality cardinality)
    {
        if ((cardinality == Cardinality.ZeroToMany) || (cardinality == Cardinality.OneToMany))
        {
            throw new IllegalArgumentException("cardinality must be one of {ZeroToOne, StrictlyOne}");
        }

        this.lower = id.getLower();
        this.mixed = id.getMixed();
        this.upper = id.getUpper();
        this.description = id.getDescription();
        this.packageName = id.getPackageName();

        this.role = new Role(roleName);

        this.cardinality = cardinality;
        this.collectionDescription = null;
        this.bound = false;
    }

    /**
     * Create a new association with the specified interface description, role name,
     * and cardinality.  The cardinality must be one of <b>Cardinality.ZeroToOne</b>
     * or <b>Cardinality.StrictlyOne</b>.
     *
     * @param id interface description, must not be null
     * @param roleName role name
     * @param cardinality cardinality, must not be null and must be one
     *    of <b>Cardinality.ZeroToOne</b> or <b>Cardinality.StrictlyOne</b>
     * @param bound true if this is a "bound" association
     */
    public Association(final InterfaceDescription id,
                       final String roleName,
                       final Cardinality cardinality,
                       final boolean bound)
    {
        if ((cardinality == Cardinality.ZeroToMany) || (cardinality == Cardinality.OneToMany))
        {
            throw new IllegalArgumentException("cardinality must be one of {ZeroToOne, StrictlyOne}");
        }

        this.lower = id.getLower();
        this.mixed = id.getMixed();
        this.upper = id.getUpper();
        this.description = id.getDescription();
        this.packageName = id.getPackageName();

        this.role = new Role(roleName);

        this.cardinality = cardinality;
        this.collectionDescription = null;
        this.bound = bound;
    }

    /**
     * Create a new association with the specified interface description, role name, and
     * cardinality and choose a collection description that satisfies the
     * specified boolean parameters.
     *
     * @param id interface description, must not be null
     * @param roleName role name
     * @param cardinality cardinality, must not be null
     * @param indexed true if the collection should be indexed
     * @param unique true if the collection should not allow duplicate elements
     * @param ordered true if the collection should iterate over elements in <i>insertion-order</i>
     * @param sorted true if the collection should iterate over elements in ascending element order,
     *    sorted according to the <i>natural ordering</i> of its elements (see Comparable), or by a Comparator
     *    provided at creation time
     */
    public Association(final InterfaceDescription id,
                       final String roleName,
                       final Cardinality cardinality,
                       final boolean indexed,
                       final boolean unique,
                       final boolean ordered,
                       final boolean sorted)
    {
        this.lower = id.getLower();
        this.mixed = id.getMixed();
        this.upper = id.getUpper();
        this.description = id.getDescription();
        this.packageName = id.getPackageName();

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
     * Create a new association with the specified interface description, role name, and
     * cardinality and choose a collection description that satisfies the
     * specified boolean parameters.
     *
     * @param id interface description, must not be null
     * @param roleName role name
     * @param cardinality cardinality, must not be null
     * @param bound true if this is a "bound" association
     * @param indexed true if the collection should be indexed
     * @param unique true if the collection should not allow duplicate elements
     * @param ordered true if the collection should iterate over elements in <i>insertion-order</i>
     * @param sorted true if the collection should iterate over elements in ascending element order,
     *    sorted according to the <i>natural ordering</i> of its elements (see Comparable), or by a Comparator
     *    provided at creation time
     */
    public Association(final InterfaceDescription id,
                       final String roleName,
                       final Cardinality cardinality,
                       final boolean bound,
                       final boolean indexed,
                       final boolean unique,
                       final boolean ordered,
                       final boolean sorted)
    {
        this.lower = id.getLower();
        this.mixed = id.getMixed();
        this.upper = id.getUpper();
        this.description = id.getDescription();
        this.packageName = id.getPackageName();

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
     * Create a new association from the specified parameters.
     *
     * @param lower lowercase name for this association
     * @param mixed mixed-case name for this association
     * @param upper uppercase name for this association
     * @param description description for this association
     * @param role role for this association, must not be null
     * @param cardinality cardinality for this association, must not be null
     * @param collectionDescription collection description for this association
     */
    public Association(final String lower,
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
        this.packageName = "";
        this.role = role;
        this.cardinality = cardinality;
        this.collectionDescription = collectionDescription;
        this.bound = false;
    }

    /**
     * Create a new association from the specified parameters.
     *
     * @param lower lowercase name for this association
     * @param mixed mixed-case name for this association
     * @param upper uppercase name for this association
     * @param description description for this association
     * @param role role for this association, must not be null
     * @param cardinality cardinality for this association, must not be null
     * @param collectionDescription collection description for this association
     * @param bound true if this is a "bound" association
     */
    public Association(final String lower,
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
        this.packageName = "";
        this.role = role;
        this.cardinality = cardinality;
        this.collectionDescription = collectionDescription;
        this.bound = bound;
    }


    /**
     * Return the lowercase name for this association.
     *
     * @return the lowercase name for this association
     */
    public String getLower()
    {
        return lower;
    }

    /**
     * Return the mixed-case name for this association.
     *
     * @return the mixed-case name for this association
     */
    public String getMixed()
    {
        return mixed;
    }

    /**
     * Return the uppercase name for this association.
     *
     * @return the uppercase name for this association
     */
    public String getUpper()
    {
        return upper;
    }

    /**
     * Return the description for this association.
     *
     * @return the description for this association
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Return the package name for this association.
     *
     * @return the package name for this association
     */
    public String getPackageName()
    {
        return packageName;
    }

    /**
     * Return the role for this association.  The role will not be null.
     *
     * @return the role for this association
     */
    public Role getRole()
    {
        return role;
    }

    /**
     * Return the cardinality for this association.  The cardinality will not be null.
     *
     * @return the cardinality for this association
     */
    public Cardinality getCardinality()
    {
        return cardinality;
    }

    /**
     * Return the collection description for this association.
     * The collection description may be null.
     *
     * @return the collection description for this association
     */
    public CollectionDescription getCollectionDescription()
    {
        return collectionDescription;
    }

    /**
     * Return true if this is a "bound" association.
     *
     * @return true if this is a "bound" association
     */
    public boolean isBound()
    {
        return bound;
    }
}
