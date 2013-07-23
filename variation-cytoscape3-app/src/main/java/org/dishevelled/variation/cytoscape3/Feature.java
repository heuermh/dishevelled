/*

    dsh-variation-cytoscape3-app  Variation Cytoscape3 app.
    Copyright (c) 2013 held jointly by the individual authors.

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
package org.dishevelled.variation.cytoscape3;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Feature.
 */
public final class Feature
{
    private String species;
    private String reference;
    private String identifier; // gene_id
    private String name; // seq_region_name
    private int start;
    private int end;
    private int strand;

    public Feature(final String species,
                   final String reference,
                   final String identifier,
                   final String name,
                   final int start,
                   final int end,
                   final int strand)
    {
        checkNotNull(species);
        checkNotNull(reference);
        checkNotNull(identifier);
        checkNotNull(name);

        this.species = species;
        this.reference = reference;
        this.identifier = identifier;
        this.name = name;
        this.start = start;
        this.end = end;
        this.strand = strand;
    }


    public String getSpecies()
    {
        return species;
    }

    public String getReference()
    {
        return reference;
    }

    public String getIdentifier()
    {
        return identifier;
    }

    public String getName()
    {
        return name;
    }

    public int getStart()
    {
        return start;
    }

    public int getEnd()
    {
        return end;
    }

    public int getStrand()
    {
        return strand;
    }
}