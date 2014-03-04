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

import static org.junit.Assert.assertNotNull;

import com.google.inject.Guice;
import com.google.inject.Injector;

import org.dishevelled.variation.FeatureService;
import org.dishevelled.variation.VariationService;
import org.dishevelled.variation.VariationConsequenceService;
import org.dishevelled.variation.VariationConsequencePredictionService;

import org.junit.Test;

/**
 * Unit test for EnsemblRestClientServiceModule.
 */
public final class EnsemblRestClientServiceModuleTest
{

    @Test
    public void testEnsemblRestClientServiceModule()
    {
        Injector injector = Guice.createInjector(new com.github.heuermh.ensemblrestclient.EnsemblRestClientModule(),
                                                 new EnsemblRestClientServiceModule());

        FeatureService featureService = injector.getInstance(FeatureService.class);
        VariationService variationService = injector.getInstance(VariationService.class);
        VariationConsequenceService variationConsequenceService = injector.getInstance(VariationConsequenceService.class);
        VariationConsequencePredictionService variationConsequencePredictionService = injector.getInstance(VariationConsequencePredictionService.class);

        assertNotNull(featureService);
        assertNotNull(variationService);
        assertNotNull(variationConsequenceService);
        assertNotNull(variationConsequencePredictionService);
    }
}