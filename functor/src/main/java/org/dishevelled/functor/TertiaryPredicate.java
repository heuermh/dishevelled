/*

    dsh-functor  Typed functor interfaces.
    Copyright (c) 2004-2005 held jointly by the individual authors.

    This library is free software; you can redistribute it and/or modify it
    under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation; either version 2.1 of the License, or (at
    your option) any later version.

    This library is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; with out even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
    License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this library;  if not, write to the Free Software Foundation,
    Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA.

    > http://www.gnu.org/copyleft/lesser.html
    > http://www.opensource.org/licenses/lgpl-license.php

*/
package org.dishevelled.functor;

/**
 * Typed functor that takes three arguments and returns a <code>boolean</code>
 * value.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface TertiaryPredicate<E, F, G>
{

    /**
     * Evaluate this predicate for the specified arguments.
     *
     * @param e first argument to this evaluation
     * @param f second argument to this evaluation
     * @param g third argument to this evaluation
     * @return the result of this evaluation
     */
    boolean test(E e, F f, G g);
}
