/*

    dsh-commandline  Command line parser based on typed arguments.
    Copyright (c) 2004-2013 held jointly by the individual authors.

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

import java.util.List;
import java.util.Arrays;
import java.util.ListIterator;

/**
 * Command line.
 *
 * @author  Michael Heuer
 */
public final class CommandLine
    implements ListIterator<String>
{
    /** Wrapped iterator. */
    private final ListIterator<String> iterator;


    /**
     * Create a new command line with the specified arguments.
     *
     * @param args command line arguments
     */
    public CommandLine(final String[] args)
    {
        if (args == null)
        {
            throw new IllegalArgumentException("args must not be null");
        }

        List<String> argumentList = Arrays.asList(args);
        iterator = argumentList.listIterator();
    }


    /** {@inheritDoc} */
    public void add(final String s)
    {
        throw new UnsupportedOperationException("add operation not supported by CommandLine");
    }

    /** {@inheritDoc} */
    public boolean hasNext()
    {
        return iterator.hasNext();
    }

    /** {@inheritDoc} */
    public boolean hasPrevious()
    {
        return iterator.hasPrevious();
    }

    /** {@inheritDoc} */
    public String next()
    {
        return iterator.next();
    }

    /** {@inheritDoc} */
    public int nextIndex()
    {
        return iterator.nextIndex();
    }

    /** {@inheritDoc} */
    public String previous()
    {
        return iterator.previous();
    }

    /** {@inheritDoc} */
    public int previousIndex()
    {
        return iterator.previousIndex();
    }

    /** {@inheritDoc} */
    public void remove()
    {
        throw new UnsupportedOperationException("remove operation not supported by CommandLine");
    }

    /** {@inheritDoc} */
    public void set(final String s)
    {
        throw new UnsupportedOperationException("set operation not supported by CommandLine");
    }
}
