/*

    dsh-variation-cytoscape3-app  Variation Cytoscape3 app.
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
package org.dishevelled.variation.cytoscape3.internal;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Merge strategy.
 *
 * @author  Michael Heuer
 */
enum MergeStrategy
{
    /** Retain only existing values. */
    RETAIN("Retain only existing values"),

    /** Replace all existing values with new values. */
    REPLACE("Replace all existing values with new values"),

    /** Merge new values with existing values. */
    MERGE("Merge new values with existing values");


    /** Description. */
    private final String description;


    /**
     * Create a new merge strategy with the specified description.
     *
     * @param description description, must not be null
     */
    private MergeStrategy(final String description)
    {
        checkNotNull(description);
        this.description = description;
    }


    /**
     * Return the description for this merge strategy.
     *
     * @return the description for this merge strategy
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Return true if this merge strategy is <code>RETAIN</code>.
     *
     * @return true if this merge strategy is <code>RETAIN</code>
     */
    public boolean isRetain()
    {
        return this.equals(RETAIN);
    }

    /**
     * Return true if this merge strategy is <code>REPLACE</code>.
     *
     * @return true if this merge strategy is <code>REPLACE</code>
     */
    public boolean isReplace()
    {
        return this.equals(REPLACE);
    }

    /**
     * Return true if this merge strategy is <code>MERGE</code>.
     *
     * @return true if this merge strategy is <code>MERGE</code>
     */
    public boolean isMerge()
    {
        return this.equals(MERGE);
    }
}
