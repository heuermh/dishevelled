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
package org.dishevelled.variation.ensembl;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import org.dishevelled.variation.FeatureService;
import org.dishevelled.variation.VariationConsequenceService;
import org.dishevelled.variation.VariationConsequencePredictionService;
import org.dishevelled.variation.VariationService;

/**
 * Ensembl REST client service module.
 *
 * @author  Michael Heuer
 */
public final class EnsemblRestClientServiceModule
    extends AbstractModule
{

    @Override
    protected void configure()
    {
        // empty
    }

    @Provides @Singleton
    static FeatureService createFeatureService(final com.github.heuermh.ensemblrestclient.LookupService lookupService)
    {
        return new EnsemblRestClientFeatureService("human", "GRCh37", lookupService);
    }

    @Provides @Singleton
    static VariationService createVariationService(final com.github.heuermh.ensemblrestclient.FeatureService featureService)
    {
        return new EnsemblRestClientVariationService("human", "GRCh37", featureService);
    }

    @Provides @Singleton
    static VariationConsequenceService createVariationConsequenceService(final com.github.heuermh.ensemblrestclient.VariationService variationService)
    {
        return new EnsemblRestClientVariationConsequenceService("human", "GRCh37", variationService);
    }

    @Provides @Singleton
    static VariationConsequencePredictionService createVariationConsequencePredictionService(final com.github.heuermh.ensemblrestclient.VariationService variationService)
    {
        return new EnsemblRestClientVariationConsequencePredictionService("human", "GRCh37", variationService);
    }
}
