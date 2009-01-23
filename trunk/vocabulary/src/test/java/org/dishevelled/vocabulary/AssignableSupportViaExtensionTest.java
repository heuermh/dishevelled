/*

    dsh-vocabulary  A description of concepts and their relations in a domain.
    Copyright (c) 2002-2009 held jointly by the individual authors.

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
package org.dishevelled.vocabulary;

/**
 * Unit test for AssignableSupport, used via extension.
 *
 * @author  Michael Heuer
 * @version $Revision: 1.2 $ $Date: 2003/12/02 03:41:15 $
 */
public class AssignableSupportViaExtensionTest
    extends AbstractAssignableTest
{

    /** {@inheritDoc} */
    protected Assignable createAssignable()
    {
        return new ViaExtension();
    }

    public void testNullAssignable()
    {
        try
        {
            Assignable a = new NullViaExtension();
            fail("setAssignable(null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    /** Mock assignable that improperly sets the assignable to null. */
    private class NullViaExtension
        extends AssignableSupport
        implements Assignable
    {

        /**
         * Create a new mock assignable via extension.
         */
        public NullViaExtension()
        {
            super();

            setAssignable(null);
        }
    }

    /** Mock assignable that uses AssignableSupport via extension. */
    private class ViaExtension
        extends AssignableSupport
        implements Assignable
    {

        /**
         * Create a new mock assignable via extension.
         */
        public ViaExtension()
        {
            super();

            setAssignable(this);
        }
    }
}
