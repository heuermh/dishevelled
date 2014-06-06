/*

    dsh-variation  Variation.
    Copyright (c) 2013-2014 held jointly by the individual authors.

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
package org.dishevelled.variation.synthetic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Synthetic genome.
 *
 * @author  Michael Heuer
 */
public final class SyntheticGenome
{
    /** Species. */
    private final String species;

    /** Reference. */
    private final String reference;

    /** List of contig names. */
    private final List<String> names;

    /** Map of lengths keyed by contig name. */
    private final Map<String, Integer> lengths;


    /**
     * Create a new synthetic genome.
     *
     * @param species species, must not be null
     * @param reference reference, must not be null
     * @param chromosomes number of chromosomes
     * @param bp base pairs
     */
    public SyntheticGenome(final String species, final String reference, final int chromosomes, final long bp)
    {
        this.species = species;
        this.reference = reference;

        names = new ArrayList<String>(chromosomes);
        lengths = new HashMap<String, Integer>(chromosomes);

        for (int i = 1, size = chromosomes + 1; i < size; i++)
        {
            names.add(String.valueOf(i));
        }
        long left = bp;
        for (int i = chromosomes - 1; i > 0; i--)
        { 
            int length = (int) (left / i);
            lengths.put(names.get(i), length);
            left -= length;
        }
    }


    /**
     * Return the species for this synthetic genome.
     *
     * @return the species for this synthetic genome
     */
    String getSpecies()
    {
        return species;
    }

    /**
     * Return the reference for this synthetic genome.
     *
     * @return the reference for this synthetic genome
     */
    String getReference()
    {
        return reference;
    }

    /**
     * Return the list of contig names for this synthetic genome.
     *
     * @return the list of contig names for this synthetic genome
     */
    List<String> getNames()
    {
        return names;
    }

    /**
     * Return the map of contig lengths keyed by name for this synthetic genome.
     *
     * @return the map of contig lengths keyed by name for this synthetic genome
     */
    Map<String, Integer> getLengths()
    {
        return lengths;
    }
}
