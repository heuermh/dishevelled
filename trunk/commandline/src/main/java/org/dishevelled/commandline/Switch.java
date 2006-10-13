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
package org.dishevelled.commandline;

/**
 * Switch.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class Switch
    implements Argument<Boolean>
{
    /** Short name. */
    private final String shortName;

    /** Long name. */
    private final String longName;

    /** Description. */
    private final String description;

    /** True if this switch was found. */
    private boolean found;


    /**
     * Create a new switch.
     *
     * @param shortName short switch name
     * @param longName long switch name
     * @param description switch description
     */
    public Switch(final String shortName,
                  final String longName,
                  final String description)
    {
        this.shortName = shortName;
        this.longName = longName;
        this.description = description;
        this.found = false;
    }


    /** @see Argument */
    public void visit(final String current, final CommandLine commandLine)
        throws Exception
    {
        if (("-" + getShortName()).equals(current) || ("--" + getLongName()).equals(current))
        {
            found = true;
        }
    }


    /** @see Argument */
    public String getShortName()
    {
        return shortName;
    }

    /** @see Argument */
    public String getLongName()
    {
        return longName;
    }

    /** @see Argument */
    public String getDescription()
    {
        return description;
    }

    /** @see Argument */
    public boolean isRequired()
    {
        return false;
    }

    /** @see Argument */
    public boolean wasFound()
    {
        return found;
    }

    /** @see Argument */
    public Boolean getValue()
    {
        return Boolean.valueOf(found);
    }
}
