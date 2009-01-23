/*

    dsh-codegen  Source code generation suite.
    Copyright (c) 2004-2009 held jointly by the individual authors.

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
package org.dishevelled.codegen;

/**
 * Source code generation style.
 *
 * <p>Feature summary:
 * <style type="text/css">.header th { text-decoration: underline; }</style>
 * <table width="90%">
 *   <tr class="header">
 *     <th>Consideration</th><th>Immutable</th><th>ImmutableWithCopyMutators</th><th>Mutable</th>
 *     <th>RichlyMutable</th><th>ExtendedRichlyMutable</th><th>Unsafe</th>
 *   </tr>
 *   <tr><td>final field</td>
 *       <td>X</td><td>X</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>
 *   <tr><td>final collection field</td>
 *       <td>X</td><td>X</td><td>X</td><td>X</td><td>X</td><td>&nbsp;</td></tr>
 *   <tr><td>final class</td>
 *       <td>X</td><td>X</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>
 *   <tr><td>defensive copy</td>
 *       <td>X</td><td>X</td><td>X</td><td>X</td><td>X</td><td>&nbsp;</td></tr>
 *   <tr><td>unmodifiable at creation</td>
 *       <td>X</td><td>X</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>
 *   <tr><td>unmodifiable at access</td>
 *       <td>&nbsp;</td><td>&nbsp;</td><td>X</td><td>X</td><td>X</td><td>&nbsp;</td></tr>
 *   <tr><td>get method</td>
 *       <td>X</td><td>X</td><td>X</td><td>X</td><td>X</td><td>X</td></tr>
 *   <tr><td>set method</td>
 *       <td>&nbsp;</td><td>&nbsp;</td><td>X</td><td>X</td><td>X</td><td>X</td></tr>
 *   <tr><td>set collection method</td>
 *       <td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>X</td></tr>
 *   <tr><td>with copy mutator</td>
 *       <td>&nbsp;</td><td>X</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>
 *   <tr><td>has method</td>
 *       <td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>X</td><td>&nbsp;</td></tr>
 *   <tr><td>add method</td>
 *       <td>&nbsp;</td><td>&nbsp;</td><td>X</td><td>X</td><td>X</td><td>&nbsp;</td></tr>
 *   <tr><td>add... method</td>
 *       <td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>X</td><td>&nbsp;</td></tr>
 *   <tr><td>addAll method</td>
 *       <td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>X</td><td>X</td><td>&nbsp;</td></tr>
 *   <tr><td>remove method</td>
 *       <td>&nbsp;</td><td>&nbsp;</td><td>X</td><td>X</td><td>X</td><td>&nbsp;</td></tr>
 *   <tr><td>remove... method</td>
 *       <td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>X</td><td>&nbsp;</td></tr>
 *   <tr><td>removeAll method</td>
 *       <td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>X</td><td>X</td><td>&nbsp;</td></tr>
 *   <tr><td>retainAll method</td>
 *       <td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>X</td><td>X</td><td>&nbsp;</td></tr>
 *   <tr><td>clear method</td>
 *       <td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>X</td><td>X</td><td>&nbsp;</td></tr>
 *   <tr><td>final methods</td>
 *       <td>&nbsp;</td><td>&nbsp;</td><td>X</td><td>X</td><td>X</td><td>&nbsp;</td></tr>
 *   <tr><td>default constructor</td>
 *       <td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>
 *   <tr><td>required argument only constructor</td>
 *       <td>&nbsp;</td><td>&nbsp;</td><td>X</td><td>X</td><td>X</td><td>X</td></tr>
 *   <tr><td>full argument constructor</td>
 *       <td>X</td><td>X</td><td>X</td><td>X</td><td>X</td><td>X</td></tr>
 *   <tr><td>override toString and hashCode</td>
 *       <td>&nbsp;</td><td>&nbsp;</td><td>X</td><td>X</td><td>X</td><td>X</td></tr>
 * </table></p>
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public enum Style
{

    /**
     * Immutable source code generation style.
     */
    Immutable,

    /**
     * Immutable with copy mutators source code generation style.
     */
    ImmutableWithCopyMutators,

    /**
     * Mutable source code generation style.
     */
    Mutable,

    /**
     * Mutable bean source code generation style, with property change support.
     */
    MutableBean,

    /**
     * Richly mutable source code generation style.
     */
    RichlyMutable,

    /**
     * Richly mutable source code generation style with a few additional
     * methods per attribute and association.
     *
     * <p><b>TODO</b>: come up with a more appropriate name for this value</p>
     */
    ExtendedRichlyMutable,

    /**
     * Unsafe source code generation style.
     */
    Unsafe,

    /**
     * Exception source code generation style.
     */
    Exception,

    /**
     * RuntimeException source code generation style.
     */
    RuntimeException
};
