/*

    dsh-codegen  Source code generation suite.
    Copyright (c) 2004-2007 held jointly by the individual authors.

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
 * A description of a collection interface and implementation.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class CollectionDescription
{
    /** The interface name for this collection description. */
    private final String interfaceName;

    /** The interface description for this collection description. */
    private final String interfaceDescription;

    /** The interface package name for this collection description. */
    private final String interfacePackageName;

    /** The implementation name for this collection description. */
    private final String implementationName;

    /** The implementation package name for this collection description. */
    private final String implementationPackageName;


    /**
     * Create a new collection description from the specified parameters.
     *
     * @param interfaceName interface name for this collection description
     * @param interfaceDescription interface description for this collection description
     * @param interfacePackageName interface package name for this collection description
     * @param implementationName implementation name for this collection description
     * @param implementationPackageName implementation package name for this collection description
     */
    public CollectionDescription(final String interfaceName,
                                 final String interfaceDescription,
                                 final String interfacePackageName,
                                 final String implementationName,
                                 final String implementationPackageName)
    {
        this.interfaceName = interfaceName;
        this.interfaceDescription = interfaceDescription;
        this.interfacePackageName = interfacePackageName;
        this.implementationName = implementationName;
        this.implementationPackageName = implementationPackageName;
    }


    /**
     * Return the interface name for this collection description.
     *
     * @return the interface name for this collection description
     */
    public String getInterfaceName()
    {
        return interfaceName;
    }

    /**
     * Return the interface description for this collection description.
     *
     * @return the interface description for this collection description
     */
    public String getInterfaceDescription()
    {
        return interfaceDescription;
    }

    /**
     * Return the interface package name for this collection description.
     *
     * @return the interface package name for this collection description
     */
    public String getInterfacePackageName()
    {
        return interfacePackageName;
    }

    /**
     * Return the implementation name for this collection description.
     *
     * @return the implementation name for this collection description
     */
    public String getImplementationName()
    {
        return implementationName;
    }

    /**
     * Return the implementation package name for this collection description.
     *
     * @return the implementation package name for this collection description
     */
    public String getImplementationPackageName()
    {
        return implementationPackageName;
    }


    /** Collection description. */
    public static final CollectionDescription COLLECTION =
        new CollectionDescription("Collection", "collection", "java.util", "ArrayList", "java.util");

    /** LinkedList description. */
    public static final CollectionDescription LINKED_LIST =
        new CollectionDescription("List", "list", "java.util", "LinkedList", "java.util");

    /** ArrayList description. */
    public static final CollectionDescription ARRAY_LIST =
        new CollectionDescription("List", "list", "java.util", "ArrayList", "java.util");

    /** HashSet description. */
    public static final CollectionDescription HASH_SET =
        new CollectionDescription("Set", "set", "java.util", "HashSet", "java.util");

    /** LinkedHashSet description. */
    public static final CollectionDescription LINKED_HASH_SET =
        new CollectionDescription("Set", "set", "java.util", "LinkedHashSet", "java.util");

    /** TreeSet description. */
    public static final CollectionDescription TREE_SET =
        new CollectionDescription("SortedSet", "sorted set", "java.util", "TreeSet", "java.util");


    /**
     * Choose a collection description that satisfies the specified parameters.
     *
     * @param indexed true if the collection should be indexed
     * @param unique true if the collection should not allow duplicate elements
     * @param ordered true if the collection should iterate over elements in <i>insertion-order</i>
     * @param sorted true if the collection should iterate over elements in ascending element order,
     *    sorted according to the <i>natural ordering</i> of its elements (see Comparable), or by a Comparator
     *    provided at creation time
     * @return a collection description that satisfies the specified parameters
     */
    public static CollectionDescription choose(final boolean indexed,
                                               final boolean unique,
                                               final boolean ordered,
                                               final boolean sorted)
    {
        if (indexed && !ordered)
        {
            return ARRAY_LIST;
        }
        if (indexed && ordered)
        {
            return LINKED_LIST;
        }
        if (unique && !ordered && !sorted)
        {
            return HASH_SET;
        }
        if (unique && ordered && !sorted)
        {
            return LINKED_HASH_SET;
        }
        if (unique && sorted)
        {
            return TREE_SET;
        }
        return COLLECTION;
    }
}
