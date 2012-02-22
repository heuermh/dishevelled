/*

    dsh-piccolo-venn  Piccolo2D venn diagram nodes and supporting classes.
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
package org.dishevelled.piccolo.venn;

import java.awt.geom.Rectangle2D;

import org.dishevelled.venn.BinaryVennModel;
import org.dishevelled.venn.TernaryVennModel;
import org.dishevelled.venn.QuaternaryVennModel;

/**
 * Venn layout.
 *
 * @author  Michael Heuer
 */
public interface VennLayout
{

    // let the algorithm choose the best answer

    // or Future<BinaryVennLayout> ?

    /**
     * Layout the specified binary venn diagram within the specified bounding rectangle.
     *
     * @param model binary venn model, must not be null
     * @param boundingRectangle bounding rectangle, must not be null
     * @param performanceHint performance hint, must not be null
     * @return the result of the layout operation
     */
    BinaryVennLayout layout(BinaryVennModel<?> model, Rectangle2D boundingRectangle, PerformanceHint performanceHint);

    /**
     * Layout the specified ternary venn diagram within the specified bounding rectangle.
     *
     * @param model ternary venn model, must not be null
     * @param boundingRectangle bounding rectangle, must not be null
     * @param performanceHint performance hint, must not be null
     * @return the result of the layout operation
     */
    TernaryVennLayout layout(TernaryVennModel<?> model, Rectangle2D boundingRectangle, PerformanceHint performanceHint);

    /**
     * Layout the specified quaternary venn diagram within the specified bounding rectangle.
     *
     * @param model quaternary venn model, must not be null
     * @param boundingRectangle bounding rectangle, must not be null
     * @param performanceHint performance hint, must not be null
     * @return the result of the layout operation
     */
    QuaternaryVennLayout layout(QuaternaryVennModel<?> model, Rectangle2D boundingRectangle, PerformanceHint performanceHint);


    // allow the caller to choose the best answer

    /*
      or Iterable<Future<BinaryVennLayout>> ?
      or SortedSet<BinaryVennLayout> sorted by score ?
      or WeightedMap<BinaryVennLayout> ?

    Iterable<BinaryVennLayout> layout(BinaryVennModel<?> model,
                                      Rectangle2D boundingRectangle,
                                      Function<BinaryVennLayout, Double> scoringFunction, // return a score for each layout
                                      Predicate<Double> evaluationPredicate, // return true if a score meets the cutoff
                                      PerformanceHint performanceHint);
    */

    enum PerformanceHint
    {
        OPTIMIZE_FOR_SPEED,
        OPTIMIZE_FOR_CORRECTNESS;
    }
}