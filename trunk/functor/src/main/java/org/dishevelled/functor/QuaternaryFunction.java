/*

    dsh-functor  Typed functor interfaces.
    Copyright (c) 2004-2012 held jointly by the individual authors.

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
package org.dishevelled.functor;

/**
 * Typed functor that takes four arguments of types <code>E</code>,
 * <code>F</code>, <code>G</code>, and <code>H</code> and returns a
 * value of type <code>RV</code>.
 *
 * @param <E> first argument type
 * @param <F> second argument type
 * @param <G> third argument type
 * @param <H> fourth argument type
 * @param <RV> return value type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface QuaternaryFunction<E, F, G, H, RV>
{

    /**
     * Evaluate this function with the specified arguments.
     *
     * @param e first argument to this evaluation
     * @param f second argument to this evaluation
     * @param g third argument to this evaluation
     * @param h fourth argument to this evaluation
     * @return the result of this evaluation
     */
    RV evaluate(E e, F f, G g, H h);
}
