/*

    dsh-venn  Lightweight components for venn diagrams.
    Copyright (c) 2009-2015 held jointly by the individual authors.

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

import java.awt.Shape;

import java.awt.geom.Point2D;

/**
 * Result of a quaternary venn diagram layout operation.
 *
 * @author  Michael Heuer
 */
public interface QuaternaryVennLayout extends VennLayout
{

    /**
     * Return the shape for the first set.
     *
     * @return the shape for the first set
     */
    Shape firstShape();

    /**
     * Return the shape for the second set.
     *
     * @return the shape for the second set
     */
    Shape secondShape();

    /**
     * Return the shape for the third set.
     *
     * @return the shape for the third set
     */
    Shape thirdShape();

    /**
     * Return the shape for the fourth set.
     *
     * @return the shape for the fourth set
     */
    Shape fourthShape();

    /**
     * Return the lune center of the first only area.
     *
     * @return the lune center of the first only area
     */
    Point2D firstOnlyLuneCenter();

    /**
     * Return the lune center of the second only area.
     *
     * @return the lune center of the second only area
     */
    Point2D secondOnlyLuneCenter();

    /**
     * Return the lune center of the third only area.
     *
     * @return the lune center of the third only area
     */
    Point2D thirdOnlyLuneCenter();

    /**
     * Return the lune center of the fourth only area.
     *
     * @return the lune center of the fourth only area
     */
    Point2D fourthOnlyLuneCenter();

    /**
     * Return the lune center of the first second area.
     *
     * @return the lune center of the first second area
     */
    Point2D firstSecondLuneCenter();

    /**
     * Return the lune center of the first third area.
     *
     * @return the lune center of the first third area
     */
    Point2D firstThirdLuneCenter();

    /**
     * Return the lune center of the second third area.
     *
     * @return the lune center of the second third area
     */
    Point2D secondThirdLuneCenter();

    /**
     * Return the lune center of the first fourth area.
     *
     * @return the lune center of the first fourth area
     */
    Point2D firstFourthLuneCenter();

    /**
     * Return the lune center of the second fourth area.
     *
     * @return the lune center of the second fourth area
     */
    Point2D secondFourthLuneCenter();

    /**
     * Return the lune center of the third fourth area.
     *
     * @return the lune center of the third fourth area
     */
    Point2D thirdFourthLuneCenter();

    /**
     * Return the lune center of the first second third area.
     *
     * @return the lune center of the first second third area
     */
    Point2D firstSecondThirdLuneCenter();

    /**
     * Return the lune center of the first second fourth area.
     *
     * @return the lune center of the first second fourth area
     */
    Point2D firstSecondFourthLuneCenter();

    /**
     * Return the lune center of the first third fourth area.
     *
     * @return the lune center of the first third fourth area
     */
    Point2D firstThirdFourthLuneCenter();

    /**
     * Return the lune center of the second third fourth area.
     *
     * @return the lune center of the second third fourth area
     */
    Point2D secondThirdFourthLuneCenter();

    /**
     * Return the lune center of the intersection area.
     *
     * @return the lune center of the intersection area
     */
    Point2D intersectionLuneCenter();
}
