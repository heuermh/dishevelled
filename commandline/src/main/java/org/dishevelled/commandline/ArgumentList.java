/*

    dsh-commandline  Command line parser based on typed arguments.
    Copyright (c) 2004-2014 held jointly by the individual authors.

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
package org.dishevelled.commandline;

import java.util.Set;
import java.util.List;
import java.util.Arrays;
import java.util.Iterator;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * An argument list.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class ArgumentList
    implements Iterable<Argument<?>>
{
    /** Set of argument short names. */
    private final Set<String> shortNames;

    /** Set of argument long names. */
    private final Set<String> longNames;

    /** List of arguments. */
    private final List<Argument<?>> arguments;


    /**
     * Create a new empty argument list.
     */
    public ArgumentList()
    {
        shortNames = new HashSet<String>();
        longNames = new HashSet<String>();
        arguments = new ArrayList<Argument<?>>();
    }

    /**
     * Create a new argument list with the specified arguments.  The arguments must
     * not contain any duplicate short or long names or an <code>IllegalArgumentException</code>
     * will be thrown.
     *
     * @param arguments variable number of arguments, must not be null
     */
    public ArgumentList(final Argument<?>... arguments)
    {
        this();
        if (arguments == null)
        {
            throw new IllegalArgumentException("arguments must not be null");
        }
        addAll(Arrays.asList(arguments));
    }

    /**
     * Create a new argument list with the arguments in the specified
     * collection of arguments.  The arguments will be copied defensively
     * into this class.  The arguments in <code>arguments</code> must
     * not contain any duplicate short or long names or an <code>IllegalArgumentException</code>
     * will be thrown.
     *
     * @param arguments collection of arguments to add, must not be null
     */
    public ArgumentList(final Collection<Argument<?>> arguments)
    {
        this();
        addAll(arguments);
    }


    /**
     * Add the specified argument to this argument list.  An argument with
     * the same short or long name as the specified argument must not
     * be contained in this argument list already or an <code>IllegalArgumentException</code>
     * will be thrown.
     *
     * @param argument argument to add, must not be null
     * @return true if this argument list changed as a result of the call
     */
    public boolean add(final Argument<?> argument)
    {
        if (argument == null)
        {
            throw new IllegalArgumentException("argument must not be null");
        }
        if (shortNames.contains(argument.getShortName()))
        {
            throw new IllegalArgumentException("this argument list already contains an argument with short name "
                                               + argument.getShortName());
        }
        if (longNames.contains(argument.getLongName()))
        {
            throw new IllegalArgumentException("this argument list already contains an argument with long name "
                                               + argument.getLongName());
        }
        if (arguments.add(argument))
        {
            shortNames.add(argument.getShortName());
            longNames.add(argument.getLongName());
            return true;
        }
        return false;
    }

    /**
     * Add all of the arguments in the specified collection of arguments to this
     * argument list.  An argument with the same short or long name as any
     * of the arguments in <code>arguments</code> must not be contained
     * in this argument list already or an <code>IllegalArgumentException</code>
     * will be thrown.
     *
     * @param arguments collection of arguments to add, must not be null
     * @return true if this argument list changed as a result of the call
     */
    public boolean addAll(final Collection<Argument<?>> arguments)
    {
        if (arguments == null)
        {
            throw new IllegalArgumentException("arguments must not be null");
        }

        boolean result = false;
        for (Argument<?> a : arguments)
        {
            result = add(a) || result;
        }
        return result;
    }

    /**
     * Return the number of arguments in this argument list.
     *
     * @return the number of arguments in this argument list
     */
    public int size()
    {
        return arguments.size();
    }

    /** {@inheritDoc} */
    public Iterator<Argument<?>> iterator()
    {
        return Collections.unmodifiableList(arguments).iterator();
    }
}
