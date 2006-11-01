/*

    dsh-codegen  Source code generation suite.
    Copyright (c) 2004-2006 held jointly by the individual authors.

    This library is free software; you can redistribute it and/or modify it
    under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation; either version 2.1 of the License, or (at
    your option) any later version.

    This library is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; with out even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
    License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this library;  if not, write to the Free Software Foundation,
    Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA.

    > http://www.gnu.org/copyleft/lesser.html
    > http://www.opensource.org/licenses/lgpl-license.php

*/
package org.dishevelled.codegen;

import java.util.Set;
import java.util.LinkedHashSet;
import java.util.Collections;

/**
 * A description of the attributes and associations for an interface.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class InterfaceDescription
{
    /** The package name for this interface description. */
    private String packageName;

    /** The lowercase name for this interface description. */
    private String lower;

    /** The mixed-case name for this interface description. */
    private String mixed;

    /** The uppercase name for this interface description. */
    private String upper;

    /** The author for this interface description. */
    private String author;

    /** The version for this interface description. */
    private String version;

    /** The description for this interface description. */
    private String description;

    /** Set of interfaces this interface description specializes. */
    private final Set<InterfaceDescription> specializes;

    /** Set of attributes for this interface description. */
    private final Set<Attribute> attributes;

    /** Set of associations for this interface description. */
    private final Set<Association> associations;


    /**
     * Create a new interface description with the specified package name
     * and name.
     *
     * @param packageName package name for this interface description
     * @param name name of this interface description
     */
    public InterfaceDescription(final String packageName, final String name)
    {
        this.packageName = packageName;
        this.lower = CodegenUtils.makeLowercase(name);
        this.mixed = CodegenUtils.makeMixedCase(name);
        this.upper = CodegenUtils.makeUppercase(name);
        this.description = CodegenUtils.makeDescription(name);
        this.author = "codegen";
        this.version = "$Revision$ $Date$";
        this.specializes = new LinkedHashSet<InterfaceDescription>();
        this.attributes = new LinkedHashSet<Attribute>();
        this.associations = new LinkedHashSet<Association>();
    }

    /**
     * Create a new interface description from the specified parameters.
     *
     * @param packageName package name for this interface description
     * @param name uppercase name for this interface description
     * @param author author for this interface description
     * @param version version for this interface description
     * @param description description for this interface description
     */
    public InterfaceDescription(final String packageName, final String name,
                                final String author, final String version, final String description)
    {
        this.packageName = packageName;
        this.lower = CodegenUtils.makeLowercase(name);
        this.mixed = CodegenUtils.makeMixedCase(name);
        this.upper = CodegenUtils.makeUppercase(name);
        this.author = author;
        this.version = version;
        this.description = description;
        this.specializes = new LinkedHashSet<InterfaceDescription>();
        this.attributes = new LinkedHashSet<Attribute>();
        this.associations = new LinkedHashSet<Association>();
    }

    /**
     * Create a new interface description from the specified parameters.
     *
     * @param packageName package name for this interface description
     * @param lower lowercase name for this interface description
     * @param mixed mixed-case name for this interface description
     * @param upper uppercase name for this interface description
     * @param author author for this interface description
     * @param version version for this interface description
     * @param description description for this interface description
     */
    public InterfaceDescription(final String packageName, final String lower, final String mixed, final String upper,
                                final String author, final String version, final String description)
    {
        this.packageName = packageName;
        this.lower = lower;
        this.mixed = mixed;
        this.upper = upper;
        this.author = author;
        this.version = version;
        this.description = description;
        this.specializes = new LinkedHashSet<InterfaceDescription>();
        this.attributes = new LinkedHashSet<Attribute>();
        this.associations = new LinkedHashSet<Association>();
    }

    /**
     * Create a new interface description from the specified parameters.
     *
     * <p>The interfaces this interface description specializes in <code>specializes</code> are copied defensively
     * into this class.</p>
     *
     * <p>The attributes in <code>attributes</code> are copied defensively
     * into this class.</p>
     *
     * <p>The associations in <code>associations</code> are copied defensively
     * into this class.</p>
     *
     * @param packageName package name for this interface description
     * @param lower lowercase name for this interface description
     * @param mixed mixed-case name for this interface description
     * @param upper uppercase name for this interface description
     * @param author author for this interface description
     * @param version version for this interface description
     * @param description description for this interface description
     * @param specializes set of interfaces this interface description specializes, must not be null
     * @param attributes set of attributes, must not be null
     * @param associations set of associations, must not be null
     */
    public InterfaceDescription(final String packageName,
                                final String lower,
                                final String mixed,
                                final String upper,
                                final String author,
                                final String version,
                                final String description,
                                final Set<InterfaceDescription> specializes,
                                final Set<Attribute> attributes,
                                final Set<Association> associations)
    {
        this.packageName = packageName;
        this.lower = lower;
        this.mixed = mixed;
        this.upper = upper;
        this.author = author;
        this.version = version;
        this.description = description;

        this.specializes = new LinkedHashSet<InterfaceDescription>(specializes.size());
        this.specializes.addAll(specializes);

        this.attributes = new LinkedHashSet<Attribute>(attributes.size());
        this.attributes.addAll(attributes);

        this.associations = new LinkedHashSet<Association>(associations.size());
        this.associations.addAll(associations);
    }


    /**
     * Return the package name for this interface description.
     *
     * @return the package name for this interface description
     */
    public String getPackageName()
    {
        return packageName;
    }

    /**
     * Return the lowercase name for this interface description.
     *
     * @return the lowercase name for this interface description
     */
    public String getLower()
    {
        return lower;
    }

    /**
     * Return the mixed-case name for this interface description.
     *
     * @return the mixed-case name for this interface description
     */
    public String getMixed()
    {
        return mixed;
    }

    /**
     * Return the uppercase name for this interface description.
     *
     * @return the uppercase name for this interface description
     */
    public String getUpper()
    {
        return upper;
    }

    /**
     * Return the author for this interface description.
     *
     * @return the author for this interface description
     */
    public String getAuthor()
    {
        return author;
    }

    /**
     * Return the version for this interface description.
     *
     * @return the version for this interface description
     */
    public String getVersion()
    {
        return version;
    }

    /**
     * Return the description for this interface description.
     *
     * @return the description for this interface description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Return an unmodifiable set of interfaces this interface description specializes.
     *
     * @return an unmodifiable set of interfaces this interface description specializes
     */
    public Set<InterfaceDescription> getSpecializes()
    {
        return Collections.unmodifiableSet(specializes);
    }

    /**
     * Add the specified interface description to the set of interfaces this interface
     * description specializes.  Return <code>true</code> if the set of interfaces this
     * interface description specializes changed as a result of this call.
     *
     * @param specializes interface description to add, must not be null
     * @return <code>true</code> if the set of interfaces this interface description specializes
     *    changed as a result of this call
     */
    public boolean addSpecializes(final InterfaceDescription specializes)
    {
        if (specializes == null)
        {
            throw new IllegalArgumentException("specializes must not be null");
        }
        if (this.specializes.contains(specializes))
        {
            return false;
        }
        boolean rv = this.specializes.add(specializes);

        if (rv)
        {
            for (Attribute a : specializes.getAttributes())
            {
                addAttribute(a);
            }
            for (Association a : specializes.getAssociations())
            {
                addAssociation(a);
            }
        }
        return rv;
    }

    /**
     * Add the specified interface description to the set of interfaces this interface
     * description specializes.  Return <code>true</code> if the set of interfaces this
     * interface description specializes changed as a result of this call.
     *
     * @param specializes interface description to add, must not be null
     * @return <code>true</code> if the set of interfaces this interface description specializes
     *    changed as a result of this call
     */
    public boolean specializes(final InterfaceDescription specializes)
    {
        return addSpecializes(specializes);
    }

    /**
     * Return an unmodifiable set of attributes for this interface description.
     *
     * @return an unmodifiable set of attributes for this interface description
     */
    public Set<Attribute> getAttributes()
    {
        return Collections.unmodifiableSet(attributes);
    }

    /**
     * Add the specified attribute to the set of attributes
     * for this interface description.  Return <code>true</code> if the set
     * of attributes changed as a result of this call.
     *
     * @param attribute attribute to add, must not be null
     * @return <code>true</code> if the set of attributes
     *    changed as a result of this call
     */
    public boolean addAttribute(final Attribute attribute)
    {
        if (attribute == null)
        {
            throw new IllegalArgumentException("attribute must not be null");
        }
        return attributes.add(attribute);
    }

    /**
     * Add the specified attribute to the set of attributes
     * for this interface description.  Return <code>true</code> if the set
     * of attributes changed as a result of this call.
     *
     * @param attribute attribute to add, must not be null
     * @return <code>true</code> if the set of attributes
     *    changed as a result of this call
     */
    public boolean attribute(final Attribute attribute)
    {
        return addAttribute(attribute);
    }

    /**
     * Add a new attribute to the set of attributes for this
     * interface description with the specified name, role name,
     * and cardinality.  The cardinality must be one of <b>Cardinality.ZeroToOne</b>
     * or <b>Cardinality.StrictlyOne</b>.  Return <code>true</code> if
     * the set of attributes changed as a result of this call.
     *
     * @param name attribute name
     * @param roleName role name
     * @param cardinality cardinality, must not be null and must be
     *    one of <b>Cardinality.ZeroToOne</b> or <b>Cardinality.StrictlyOne</b>
     * @return <code>true</code> if the set of attributes
     *    changed as a result of this call
     */
    public boolean attribute(final String name, final String roleName, final Cardinality cardinality)
    {
        Attribute a = new Attribute(name, roleName, cardinality);
        return addAttribute(a);
    }

    /**
     * Add a new attribute to the set of attributes for this
     * interface description with the specified parameters.
     * Return <code>true</code> if the set of attributes changed as
     * a result of this call.
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
     * @return <code>true</code> if the set of attributes
     *    changed as a result of this call
     */
    public boolean attribute(final String name, final String roleName, final Cardinality cardinality,
                             final boolean indexed, final boolean unique, final boolean ordered, final boolean sorted)
    {
        Attribute a = new Attribute(name, roleName, cardinality, indexed, unique, ordered, sorted);
        return addAttribute(a);
    }

    /**
     * Return an unmodifiable set of associations for this interface description.
     *
     * @return an unmodifiable set of associations for this interface description
     */
    public Set<Association> getAssociations()
    {
        return Collections.unmodifiableSet(associations);
    }

    /**
     * Add the specified association to the set of associations
     * for this interface description.  Return <code>true</code> if the set
     * of associations changed as a result of this call.
     *
     * @param association association to add, must not be null
     * @return <code>true</code> if the set of associations
     *    changed as a result of this call
     */
    public boolean addAssociation(final Association association)
    {
        if (association == null)
        {
            throw new IllegalArgumentException("association must not be null");
        }
        return associations.add(association);
    }

    /**
     * Add the specified association to the set of associations
     * for this interface description.  Return <code>true</code> if the set
     * of associations changed as a result of this call.
     *
     * @param association association to add, must not be null
     * @return <code>true</code> if the set of associations
     *    changed as a result of this call
     */
    public boolean associate(final Association association)
    {
        return addAssociation(association);
    }

    /**
     * Add a new association to the set of associations for this
     * interface description to the specified class with the
     * specified cardinality.  The assocation's role name will be
     * the same as the class' name.  The cardinality must be one of
     * <b>Cardinality.ZeroToOne</b> or <b>Cardinality.StrictlyOne</b>.
     * Return <code>true</code> if the set of associations changed as a result of
     * this call.
     *
     * @param cd class description, must not be null
     * @param cardinality cardinality, must not be null and must be
     *    one of <b>Cardinality.ZeroToOne</b> or <b>Cardinality.StrictlyOne</b>
     * @return <code>true</code> if the set of associations
     *    changed as a result of this call
     */
    public boolean associate(final ClassDescription cd, final Cardinality cardinality)
    {
        Association a = new Association(cd, cd.getUpper(), cardinality);
        return addAssociation(a);
    }

    /**
     * Add a new association to the set of attributes for this
     * interface description to the specified class with the
     * specified role name and cardinality.  The cardinality must be one of
     * <b>Cardinality.ZeroToOne</b> or <b>Cardinality.StrictlyOne</b>.
     * Return <code>true</code> if the set of associations changed as a result of
     * this call.
     *
     * @param cd class description, must not be null
     * @param roleName role name
     * @param cardinality cardinality, must not be null and must be
     *    one of <b>Cardinality.ZeroToOne</b> or <b>Cardinality.StrictlyOne</b>
     * @return <code>true</code> if the set of associations
     *    changed as a result of this call
     */
    public boolean associate(final ClassDescription cd, final String roleName, final Cardinality cardinality)
    {
        Association a = new Association(cd, roleName, cardinality);
        return addAssociation(a);
    }

    /**
     * Add a new association to the set of associations for this
     * interface description to the specified class with the specified
     * parameters.  The association's role name will be the same as
     * the class' name.
     *
     * @param cd class description, must not be null
     * @param cardinality cardinality, must not be null
     * @param indexed true if the collection should be indexed
     * @param unique true if the collection should not allow duplicate elements
     * @param ordered true if the collection should iterate over elements in <i>insertion-order</i>
     * @param sorted true if the collection should iterate over elements in ascending element order,
     *    sorted according to the <i>natural ordering</i> of its elements (see Comparable), or by a Comparator
     *    provided at creation time
     * @return <code>true</code> if the set of associations
     *    changed as a result of this call
     */
    public boolean associate(final ClassDescription cd, final Cardinality cardinality,
                             final boolean indexed, final boolean unique, final boolean ordered, final boolean sorted)
    {
        Association a = new Association(cd, cd.getUpper(), cardinality, indexed, unique, ordered, sorted);
        return addAssociation(a);
    }

    /**
     * Add a new association to the set of associations for this
     * interface description to the specified class with the specified
     * parameters.
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
     * @return <code>true</code> if the set of associations
     *    changed as a result of this call
     */
    public boolean associate(final ClassDescription cd, final String roleName, final Cardinality cardinality,
                             final boolean indexed, final boolean unique, final boolean ordered, final boolean sorted)
    {
        Association a = new Association(cd, roleName, cardinality, indexed, unique, ordered, sorted);
        return addAssociation(a);
    }

    /**
     * Add a new association to the set of associations for this
     * interface description to the specified interface with the
     * specified cardinality.  The assocation's role name will be
     * the same as the interface's name.  The cardinality must be one of
     * <b>Cardinality.ZeroToOne</b> or <b>Cardinality.StrictlyOne</b>.
     * Return <code>true</code> if the set of associations changed as a result of
     * this call.
     *
     * @param id interface description, must not be null
     * @param cardinality cardinality, must not be null and must be
     *    one of <b>Cardinality.ZeroToOne</b> or <b>Cardinality.StrictlyOne</b>
     * @return <code>true</code> if the set of associations
     *    changed as a result of this call
     */
    public boolean associate(final InterfaceDescription id, final Cardinality cardinality)
    {
        Association a = new Association(id, id.getUpper(), cardinality);
        return addAssociation(a);
    }


    /**
     * Add a new association to the set of attributes for this
     * interface description to the specified interface with the
     * specified role name and cardinality.  The cardinality must be one of
     * <b>Cardinality.ZeroToOne</b> or <b>Cardinality.StrictlyOne</b>.
     * Return <code>true</code> if the set of associations changed as a result of
     * this call.
     *
     * @param id interface description, must not be null
     * @param roleName role name
     * @param cardinality cardinality, must not be null and must be
     *    one of <b>Cardinality.ZeroToOne</b> or <b>Cardinality.StrictlyOne</b>
     * @return <code>true</code> if the set of associations
     *    changed as a result of this call
     */
    public boolean associate(final InterfaceDescription id, final String roleName, final Cardinality cardinality)
    {
        Association a = new Association(id, roleName, cardinality);
        return addAssociation(a);
    }

   /**
     * Add a new association to the set of associations for this
     * interface description to the specified interface with the specified
     * parameters.  The association's role name will be the same as
     * the interface's name.
     *
     * @param id interface description, must not be null
     * @param cardinality cardinality, must not be null
     * @param indexed true if the collection should be indexed
     * @param unique true if the collection should not allow duplicate elements
     * @param ordered true if the collection should iterate over elements in <i>insertion-order</i>
     * @param sorted true if the collection should iterate over elements in ascending element order,
     *    sorted according to the <i>natural ordering</i> of its elements (see Comparable), or by a Comparator
     *    provided at creation time
     * @return <code>true</code> if the set of associations
     *    changed as a result of this call
     */
    public boolean associate(final InterfaceDescription id, final Cardinality cardinality,
                             final boolean indexed, final boolean unique, final boolean ordered, final boolean sorted)
    {
        Association a = new Association(id, id.getUpper(), cardinality, indexed, unique, ordered, sorted);
        return addAssociation(a);
    }

    /**
     * Add a new association to the set of associations for this
     * interface description to the specified interface with the specified
     * parameters.
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
     * @return <code>true</code> if the set of associations
     *    changed as a result of this call
     */
    public boolean associate(final InterfaceDescription id, final String roleName, final Cardinality cardinality,
                             final boolean indexed, final boolean unique, final boolean ordered, final boolean sorted)
    {
        Association a = new Association(id, roleName, cardinality, indexed, unique, ordered, sorted);
        return addAssociation(a);
    }
}
