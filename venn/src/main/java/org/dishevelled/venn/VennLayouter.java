/*

    dsh-venn  Lightweight components for venn diagrams.
    Copyright (c) 2009-2012 held jointly by the individual authors.

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
package org.dishevelled.venn;

import java.awt.geom.Rectangle2D;

import java.util.Iterator;

/**
 * Venn layout algorithm.
 *
 * @author  Michael Heuer
 */
public interface VennLayouter
{

    // let the algorithm choose the best answer

    /**
     * Layout the specified venn diagram within the specified bounding rectangle.
     *
     * @param model venn model, must not be null
     * @param boundingRectangle bounding rectangle, must not be null
     * @param performanceHint performance hint, must not be null
     * @return the result of the layout operation
     */
    VennLayout layout(VennModel<?> model,
                      Rectangle2D boundingRectangle,
                      PerformanceHint performanceHint);

    // allow the caller to choose the best answer

    /**
     * Layout the specified venn diagram within the specified bounding rectangle,
     * returning zero or more possible layouts that satisfy the specified scoring
     * function and score predicate.
     *
     * @param model venn model, must not be null
     * @param boundingRectangle bounding rectangle, must not be null
     * @param scoringFunction scoring function, must not be null
     * @param scorePredicate score predicate, must not be null
     * @param performanceHint performance hint, must not be null
     * @return zero or more possible layouts that satisfy the specified scoring
     *    function and score predicate
     */
    Iterator<VennLayout> layout(VennModel<?> model,
                                Rectangle2D boundingRectangle,
                                ScoringFunction scoringFunction,
                                ScorePredicate scorePredicate,
                                PerformanceHint performanceHint);


    /**
     * Scoring function.
     */
    interface ScoringFunction
    {
        /**
         * Return a score for the specified venn layout.
         *
         * @param layout venn layout to score
         * @return a score for the specified venn layout
         */
        double score(VennLayout layout);
    }

    /**
     * Score predicate.
     */
    interface ScorePredicate
    {
        /**
         * Return true if the specified score satisfies this predicate.
         *
         * @param score score to evaluate
         * @return true if the specified score satisfies this predicate
         */
        boolean evaluate(double score);
    }

    /**
     * Performance hint.
     */
    enum PerformanceHint
    {
        /** Optimize for speed. */
        OPTIMIZE_FOR_SPEED,

        /** Optimize for correctness. */
        OPTIMIZE_FOR_CORRECTNESS;
    }
}