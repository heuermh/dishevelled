/*

    dsh-commandline  Simple command line parser based on typed arguments.
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
package org.dishevelled.commandline;

/**
 * Command line parse exception.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class CommandLineParseException
    extends Exception
{

    /**
     * Create a new command line parse exception with the
     * specified message.
     *
     * @param message message
     */
    public CommandLineParseException(final String message)
    {
        super(message);
    }

    /**
     * Create a new command line parse exception with the
     * specified cause.
     *
     * @param cause cause
     */
    public CommandLineParseException(final Throwable cause)
    {
        super(cause);
    }

    /**
     * Create a new command line parse exception with the
     * specified message and cause.
     *
     * @param message message
     * @param cause cause
     */
    public CommandLineParseException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
