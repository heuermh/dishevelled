/*

    dsh-functor  Typed functor interfaces.
    Copyright (c) 2004-2007 held jointly by the individual authors.

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
 * Typed functor that takes an arbitrary number of arguments
 * of the same type and returns a <code>boolean</code> value.
 *
 * @param <E> argument type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface NaryPredicate<E>
{

    /**
     * Evaluate this predicate for the specified arguments.
     * <p>The arguments may be passed as an array
     * <pre>  p.test(new E[] { e0, e1, e2, e3 })</pre>
     * or as a sequence of arguments:
     * <pre>  p.test(e1, e2, e3, e4)</pre></p>
     *
     * <p>For more information on varargs, see<br/>
     * &gt; <a href="http://java.sun.com/j2se/1.5.0/docs/guide/language/varargs.html">
     * http://java.sun.com/j2se/1.5.0/docs/guide/language/varargs.html</a></p>
     *
     * @param e arbitrary number of arguments to this execution
     * @return the result of this evaluation
     */
    boolean test(E... e);
}
