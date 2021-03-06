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
package org.dishevelled.variation.synthetic;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import org.dishevelled.variation.FeatureService;
import org.dishevelled.variation.VariationConsequenceService;
import org.dishevelled.variation.VariationConsequencePredictionService;
import org.dishevelled.variation.VariationService;

/**
 * Synthetic module.
 *
 * @author  Michael Heuer
 */
public final class SyntheticModule
    extends AbstractModule
{

    @Override
    protected void configure()
    {
        bind(FeatureService.class).to(SyntheticFeatureService.class).in(Singleton.class);
        bind(VariationService.class).to(SyntheticVariationService.class).in(Singleton.class);
        bind(VariationConsequenceService.class).to(SyntheticVariationConsequenceService.class).in(Singleton.class);
        bind(VariationConsequencePredictionService.class).to(SyntheticVariationConsequencePredictionService.class).in(Singleton.class);
    }

    @Provides @Singleton
    static SyntheticGenome createSyntheticGenome()
    {
        return new SyntheticGenome("human", "GRCh38", 22, 3000000000L);
    }
}
