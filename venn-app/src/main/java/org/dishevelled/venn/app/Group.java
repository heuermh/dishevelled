/*

    dsh-venn-app  App for venn and euler diagrams.
    Copyright (c) 2013-2015 held jointly by the individual authors.

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
package org.dishevelled.venn.app;

import java.util.List;

/**
 * Group.
 *
 * @author  Michael Heuer
 */
final class Group
{
    private String name;
    private final List<String> values;

    Group(final String name, final List<String> values)
    {
        this.name = name;
        this.values = values;
    }

    String getName()
    {
        return name;
    }

    void setName(final String name)
    {
        this.name = name;
    }

    List<String> getValues()
    {
        return values;
    }

    @Override
    public String toString()
    {
        return getName() + " (" + getValues().size() + ")";
    }
}