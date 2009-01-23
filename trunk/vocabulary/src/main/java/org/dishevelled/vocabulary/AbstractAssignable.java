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
 * Abstract class providing Assignable support.
 *
 * @author  Michael Heuer
 * @version $Revision: 1.2 $ $Date: 2003/11/19 07:19:58 $
 */
public abstract class AbstractAssignable
    extends AssignableSupport
    implements Assignable
{

    /**
     * Create a new abstract assignable entity.
     */
    protected AbstractAssignable()
    {
        super();
        setAssignable(this);
    }
}
