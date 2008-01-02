/*

    dsh-commandline  Command line parser based on typed arguments.
    Copyright (c) 2004-2008 held jointly by the individual authors.

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
package org.dishevelled.commandline;

/**
 * A typed argument.
 *
 * @param <E> argument type
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface Argument<E>
{

    /**
     * Visit the specified argument string from the specified command line.
     *
     * <p>Previous and next argument strings in the command line may be interrogated
     * by this argument, but the <i>cursor position</i> of the command line must
     * be reset to what it was at the beginning of this method invocation.</p>
     *
     * @param s current argument string
     * @param commandLine command line to visit
     * @throws Exception if any error occurs
     */
    void visit(String s, CommandLine commandLine)
        throws Exception;

    /**
     * Return the short name of this argument.
     *
     * @return the short name of this argument
     */
    String getShortName();

    /**
     * Return the long name of this argument.
     *
     * @return the long name of this argument
     */
    String getLongName();

    /**
     * Return the description of this argument.
     *
     * @return the description of this argument
     */
    String getDescription();

    /**
     * Return true if this argument is required.
     *
     * @return true if this argument is required
     */
    boolean isRequired();

    /**
     * Return true if this argument was found in the
     * command line, false if it was not found or if
     * this argument has not visited a command line
     * yet.
     *
     * @see #visit(String, CommandLine)
     * @return true if this argument was found in the
     *    command line
     */
    boolean wasFound();

    /**
     * Return the value of this argument.
     *
     * @return the value of this argument
     */
    E getValue();
}
