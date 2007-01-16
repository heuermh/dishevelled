/*

    dsh-commandline  Command line parser based on typed arguments.
    Copyright (c) 2004-2007 held jointly by the individual authors.

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

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

/**
 * A date argument.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class DateArgument
    extends AbstractArgument<Date>
{

    /**
     * Create a new date argument.
     *
     * @param shortName short argument name
     * @param longName long argument name
     * @param description argument description
     * @param required <code>true</code> if this argument is required
     */
    public DateArgument(final String shortName,
                        final String longName,
                        final String description,
                        final boolean required)
    {
        super(shortName, longName, description, required);
    }


    /** {@inheritDoc} */
    protected Date convert(final String s)
        throws Exception
    {
        DateFormat df = DateFormat.getDateInstance();
        ((SimpleDateFormat) df).applyPattern("yyyy-MM-dd");
        return df.parse(s);
    }
}
