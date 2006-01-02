/*

    dsh-commandline  Simple command line parser based on typed arguments.
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
package org.dishevelled.commandline.argument;

import java.util.Set;
import java.util.HashSet;
import java.util.StringTokenizer;

/**
 * A boolean set argument.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class BooleanSetArgument
    extends AbstractArgument<Set<Boolean>>
{

    /**
     * Create a new boolean set argument.
     *
     * @param shortName short argument name
     * @param longName long argument name
     * @param description argument description
     * @param required <code>true</code> if this argument is required
     */
    public BooleanSetArgument(final String shortName,
                              final String longName,
                              final String description,
                              final boolean required)
    {
        super(shortName, longName, description, required);
    }


    /** @see AbstractArgument */
    protected Set<Boolean> convert(final String s)
        throws Exception
    {
        Set<Boolean> set = new HashSet<Boolean>();
        StringTokenizer st = new StringTokenizer(s, ",");
        while (st.hasMoreTokens())
        {
            String token = st.nextToken();

            if ("true".equalsIgnoreCase(token))
            {
                set.add(Boolean.TRUE);
            }
            else if ("false".equalsIgnoreCase(token))
            {
                set.add(Boolean.FALSE);
            }
            else if ("t".equalsIgnoreCase(token))
            {
                set.add(Boolean.TRUE);
            }
            else if ("f".equalsIgnoreCase(token))
            {
                set.add(Boolean.FALSE);
            }
            else if ("1".equalsIgnoreCase(token))
            {
                set.add(Boolean.TRUE);
            }
            else if ("0".equalsIgnoreCase(token))
            {
                set.add(Boolean.FALSE);
            }
            else
            {
                throw new Exception("could not parse " + token + " into a Boolean");
            }
        }
        return set;
    }
}
