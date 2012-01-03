/*

    dsh-interpolate  Interpolation and easing functions.
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
package org.dishevelled.interpolate;

import junit.framework.TestCase;

/**
 * Abstract unit test for implementations of EasingFunction.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractEasingFunctionTest
    extends TestCase
{

    /**
     * Create and return a new instance of an implementation of EasingFunction to test.
     *
     * @return a new instance of an implementation of EasingFunction to test
     */
    protected abstract EasingFunction createEasingFunction();

    public void testCreateEasingFunction()
    {
        EasingFunction function = createEasingFunction();
        assertNotNull(function);
    }

    public void testEasingFunction()
    {
        EasingFunction function = createEasingFunction();
        assertNotNull(function);
        for (double v = 0.0d; v < 1.01d; v += 0.01d)
        {
            assertNotNull(function.evaluate(v));
        }
        try
        {
            function.evaluate(null);
            fail("evaluate(null) expected NullPointerException");
        }
        catch (NullPointerException e)
        {
            // expected
        }
    }
}