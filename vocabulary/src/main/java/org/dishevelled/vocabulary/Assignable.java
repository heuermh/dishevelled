/*

    dsh-vocabulary  A description of concepts and their relations in a domain.
    Copyright (c) 2002-2007 held jointly by the individual authors.

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
package org.dishevelled.vocabulary;

import java.util.Set;

/**
 * Marks an entity as being &quot;assignable,&quot; in
 * that it may be assigned to concepts in one or more domains for
 * annotation purposes.
 *
 * @author  Michael Heuer
 * @version $Revision: 1.3.2.1 $ $Date: 2004/02/05 22:39:51 $
 */
public interface Assignable
{

    /**
     * Return an unmodifiable set of assignments for this entity.
     * The set will not be null, but may be empty.
     *
     * @return the set of assignments
     */
    Set<Assignment> getAssignments();

    /**
     * Add the specified assigment.  <code>assignment</code> must
     * not be null and must have <code>this</code> as its assignable
     * entity.
     *
     * @param assignment assignment to add, must not be null and
     *    must have <code>this</code> as its assignable entity
     */
    void addAssignment(Assignment assignment);
}
