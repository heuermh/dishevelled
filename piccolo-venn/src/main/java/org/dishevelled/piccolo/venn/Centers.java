/*

    dsh-piccolo-venn  Piccolo2D venn diagram nodes and supporting classes.
    Copyright (c) 2009-2010 held jointly by the individual authors.

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

import java.awt.Shape;

import java.awt.geom.Area;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.dishevelled.evolve.EvolutionaryAlgorithm;
import org.dishevelled.evolve.ExitStrategy;
import org.dishevelled.evolve.Fitness;
import org.dishevelled.evolve.Mutation;
import org.dishevelled.evolve.Recombination;
import org.dishevelled.evolve.Selection;

import org.dishevelled.evolve.exit.TimeLimitExitStrategy;

import org.dishevelled.evolve.impl.EvolutionaryAlgorithmImpl;

import org.dishevelled.evolve.recombine.SexualRecombination;

import org.dishevelled.evolve.select.FitnessProportionalSelection;

/**
 * Utility methods for finding the center of areas.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
final class Centers
{

    /**
     * Find and return the center of the specified shape using its bounds rectangle.
     *
     * @param shape shape, must not be null
     * @return the center of the specified shape using its bounds rectangle
     */
    static Point2D centerOf(final Shape shape)
    {
        return centerOf(shape, new Point2D.Double());
    }

    /**
     * Find and return the specified center of the specified shape using its bounds rectangle.
     *
     * @param shape shape, must not be null
     * @param center center, must not be null
     * @return the specified center of the specified shape using its bounds rectangle
     */
    static Point2D centerOf(final Shape shape, final Point2D center)
    {
        if (shape == null)
        {
            throw new IllegalArgumentException("shape must not be null");
        }
        return centerOf(shape.getBounds2D(), center);
    }

    /**
     * Find and return the center of the specified rectangle.
     *
     * @param rectangle rectangle, must not be null
     * @return the center of the specified rectangle
     */
    static Point2D centerOf(final Rectangle2D rectangle)
    {
        return centerOf(rectangle, new Point2D.Double());
    }

    /**
     * Find and return the specified center of the specified rectangle.
     *
     * @param rectangle rectangle, must not be null
     * @param center center, must not be null
     * @return the specified center of the specified rectangle
     */
    static Point2D centerOf(final Rectangle2D rectangle, final Point2D center)
    {
        if (rectangle == null)
        {
            throw new IllegalArgumentException("rectangle must not be null");
        }
        if (center == null)
        {
            throw new IllegalArgumentException("center must not be null");
        }
        center.setLocation(rectangle.getCenterX(), rectangle.getCenterY());
        return center;
    }

    /**
     * Find and return the center of the specified area using its bounds rectangle.
     *
     * @param area area, must not be null
     * @return the center of the specified area using its bounds rectangle
     */
    static Point2D centerOf(final Area area)
    {
        return centerOf(area, new Point2D.Double());
    }

    /**
     * Find and return the specified center of the specified area using its bounds rectangle.
     *
     * @param area area, must not be null
     * @param center center, must not be null
     * @return the specified center of the specified area using its bounds rectangle
     */
    static Point2D centerOf(final Area area, final Point2D center)
    {
        if (area == null)
        {
            throw new IllegalArgumentException("area must not be null");
        }
        return centerOf(area.getBounds2D(), center);
    }

    /**
     * Find and return an approximate centroid of the specified area using
     * an evolutionary algorithm.
     *
     * @param shape shape, must not be null
     * @return an approximate centroid of the specified area using an
     *    evolutionary algorithm
     */
    static Point2D centroidOf(final Area area)
    {
        return centroidOf(area, new Point2D.Double());
    }

    /**
     * Find and return the specified approximate centroid of the specified area using
     * an evolutionary algorithm.
     *
     * @param shape shape, must not be null
     * @param centroid centroid, must not be null
     * @return the specified approximate centroid of the specified area using an
     *    evolutionary algorithm
     */
    static Point2D centroidOf(final Area area, final Point2D centroid)
    {
        if (area == null)
        {
            throw new IllegalArgumentException("area must not be null");
        }
        if (centroid == null)
        {
            throw new IllegalArgumentException("centroid must not be null");
        }

        final Rectangle2D bounds = area.getBounds2D();
        final Random random = new Random();
        List<Point2D> individuals = new ArrayList<Point2D>(100);
        for (int i = 0; i < 100; i++)
        {
            double x = bounds.getX() + random.nextDouble() * bounds.getWidth();
            double y = bounds.getY() + random.nextDouble() * bounds.getHeight();
            individuals.add(new Point2D.Double(x, y));                                               
        }
        ExitStrategy<Point2D> exitStrategy = new TimeLimitExitStrategy<Point2D>(75);
        Selection<Point2D> selection = new FitnessProportionalSelection<Point2D>();

        Recombination<Point2D> recombination = new SexualRecombination<Point2D>()
            {
                private double x = 0.0d;
                private double y = 0.0d;

                /** {@inheritDoc} */
                public Point2D recombine(final Point2D individual0, final Point2D individual1)
                {
                    x = (individual0.getX() + individual1.getX()) / 2.0d;
                    y = (individual0.getY() + individual1.getY()) / 2.0d;
                    return new Point2D.Double(x, y);
                }
            };

        Mutation<Point2D> mutation = new Mutation<Point2D>()
            {
                private double x = 0.0d;
                private double y = 0.0d;

                /** {@inheritDoc} */
                public Collection<Point2D> mutate(final Collection<Point2D> recombined)
                {
                    for (Point2D point : recombined)
                    {
                        x = point.getX() + random.nextDouble() * 2.0d - random.nextDouble() * 2.0d;
                        y = point.getY() + random.nextDouble() * 2.0d - random.nextDouble() * 2.0d;
                        point.setLocation(x, y);
                    }
                    return recombined;
                }
            };

        Fitness<Point2D> fitness = new Fitness<Point2D>()
            {
                private double distance;
                private double leastXDistance;
                private double leastYDistance;
                private final Point2D query = new Point2D.Double();
                private Area vertical;
                private Area horizontal;
                private Rectangle2D horizontalBounds;
                private Rectangle2D verticalBounds;
                private final Rectangle2D v = new Rectangle2D.Double();
                private final Rectangle2D h = new Rectangle2D.Double();


                /** {@inheritDoc} */
                public Double score(final Point2D individual)
                {
                    if (!area.contains(individual))
                    {
                        return Double.valueOf(0.0d);
                    }
                    query.setLocation(individual.getX(), individual.getY());

                    v.setRect(query.getX(), bounds.getY(), 1.0d, bounds.getY() + bounds.getHeight());
                    vertical = new Area(v);
                    vertical.intersect(area);
                    verticalBounds = vertical.getBounds2D();
                    leastYDistance = Math.min(query.getY() - verticalBounds.getY(), verticalBounds.getY() + verticalBounds.getHeight() - query.getY());

                    h.setRect(bounds.getX(), query.getY(), bounds.getX() + bounds.getWidth(), 1.0d);
                    horizontal = new Area(h);
                    horizontal.intersect(area);
                    horizontalBounds = horizontal.getBounds2D();
                    leastXDistance = Math.min(query.getX() - horizontalBounds.getX(), horizontalBounds.getX() + horizontalBounds.getWidth() - query.getX());

                    return Double.valueOf(leastXDistance + leastYDistance);
                }
            };

        double maxFitness = 0.0d;
        Point2D max = null;
        EvolutionaryAlgorithm<Point2D> ea = new EvolutionaryAlgorithmImpl<Point2D>();
        for (Point2D point : ea.evolve(individuals, exitStrategy, recombination, mutation, fitness, selection))
        {
            double f = fitness.score(point);
            if (f > maxFitness)
            {
                maxFitness = f;
                max = point;
            }
        }
        centroid.setLocation(max.getX(), max.getY());
        return centroid;
    }
}