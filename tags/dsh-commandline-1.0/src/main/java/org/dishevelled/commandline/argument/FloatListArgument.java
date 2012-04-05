/*

    dsh-commandline  Command line parser based on typed arguments.
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
package org.dishevelled.commandline.argument;

import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

/**
 * A float list argument.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class FloatListArgument
    extends AbstractArgument<List<Float>>
{

    /**
     * Create a new float list argument.
     *
     * @param shortName short argument name
     * @param longName long argument name
     * @param description argument description
     * @param required <code>true</code> if this argument is required
     */
    public FloatListArgument(final String shortName,
                             final String longName,
                             final String description,
                             final boolean required)
    {
        super(shortName, longName, description, required);
    }


    /** {@inheritDoc} */
    protected List<Float> convert(final String s)
        throws Exception
    {
        List<Float> list = new ArrayList<Float>();
        StringTokenizer st = new StringTokenizer(s, ",");
        while (st.hasMoreTokens())
        {
            String token = StringUtils.stripToEmpty(st.nextToken());
            Float f = Float.valueOf(token);
            list.add(f);
        }
        return list;
    }
}
