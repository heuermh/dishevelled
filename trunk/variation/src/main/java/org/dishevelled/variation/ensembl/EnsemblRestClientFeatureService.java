/*

    dsh-variation  Variation.
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
package org.dishevelled.variation.ensembl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import static org.dishevelled.variation.ensembl.EnsemblRestClientUtils.retryIfNecessary;
import static org.dishevelled.variation.ensembl.EnsemblRestClientUtils.throttle;

import com.github.heuermh.ensemblrestclient.EnsemblRestClientException;
import com.github.heuermh.ensemblrestclient.Lookup;
import com.github.heuermh.ensemblrestclient.LookupService;

import org.dishevelled.variation.Feature;
import org.dishevelled.variation.FeatureService;

import org.dishevelled.variation.ensembl.EnsemblRestClientUtils.Remote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ensembl REST client feature service.
 *
 * @author  Michael Heuer
 */
public final class EnsemblRestClientFeatureService
    implements FeatureService
{
    /** Species. */
    private final String species;

    /** Reference. */
    private final String reference;

    /** Lookup service. */
    private final LookupService lookupService;

    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(EnsemblRestClientFeatureService.class);


    /**
     * Create a new Ensembl REST client feature service.
     *
     * @param species species, must not be null
     * @param reference reference, must not be null
     * @param lookupService lookup service, must not be null
     */
    public EnsemblRestClientFeatureService(final String species,
                                           final String reference,
                                           final LookupService lookupService)
    {
        checkNotNull(species);
        checkNotNull(reference);
        checkNotNull(lookupService);

        this.species = species;
        this.reference = reference;
        this.lookupService = lookupService;
    }


    @Override
    public Feature feature(final String species, final String reference, final String identifier)
    {
        checkNotNull(species);
        checkNotNull(reference);
        checkNotNull(identifier);
        checkArgument(this.species.equals(species));
        checkArgument(this.reference.equals(reference));

        throttle();
        try
        {
            Lookup lookup = retryIfNecessary(new Remote<Lookup>()
                {
                    @Override
                    public Lookup remote() throws EnsemblRestClientException
                    {
                        return lookupService.lookup(species, identifier);
                    }
                });

            if (lookup == null)
            {
                if (logger.isWarnEnabled())
                {
                    logger.warn("unable to lookup identifier {} for species {}", identifier, this.species);
                }
                return null;
            }
            // convert from 1-based fully closed to 0-based closed open
            long start = lookup.getLocation().getStart() - 1L;
            long end = lookup.getLocation().getEnd();
            return new Feature(this.species,
                               reference,
                               identifier,
                               lookup.getLocation().getName(),
                               start,
                               end,
                               lookup.getLocation().getStrand());
        }
        catch (EnsemblRestClientException e)
        {
            if (logger.isWarnEnabled())
            {
                logger.warn("unable to lookup identifier {} for species {}, rec'd {} {}", identifier, this.species, e.getStatus(), e.getReason());
            }
        }
        return null;
    }

    @Override
    public Feature feature(final String species,
                           final String reference,
                           final String region,
                           final long start,
                           final long end,
                           final int strand)
    {
        checkNotNull(species);
        checkNotNull(reference);
        checkNotNull(region);
        checkArgument(this.species.equals(species));
        checkArgument(this.reference.equals(reference));

        return null;
    }

    @Override
    public String toString()
    {
        return "Ensembl REST client features (" + species + " " + reference + ")";
    }
}
