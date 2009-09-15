/*

    dsh-commandline  Command line parser based on typed arguments.
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
package org.dishevelled.commandline;

/**
 * Command line parser.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class CommandLineParser
{

    /**
     * Private no-arg constructor.
     */
    private CommandLineParser()
    {
        // empty
    }


    /**
     * Parse the specified command line according to the specified
     * list of arguments.
     *
     * @param commandLine command line, must not be null
     * @param arguments list of arguments, must not be null
     * @throws CommandLineParseException if an error occurs
     */
    public static void parse(final CommandLine commandLine, final ArgumentList arguments)
        throws CommandLineParseException
    {
        if (commandLine == null)
        {
            throw new IllegalArgumentException("commandLine must not be null");
        }
        if (arguments == null)
        {
            throw new IllegalArgumentException("arguments must not be null");
        }

        try
        {
            while (commandLine.hasNext())
            {
                String s = commandLine.next();

                for (Argument<?> a : arguments)
                {
                    a.visit(s, commandLine);
                }
            }
        }
        catch (Exception e)
        {
            throw new CommandLineParseException(e);
        }

        for (Argument<?> a : arguments)
        {
            if (a.isRequired() && !(a.wasFound()))
            {
                StringBuffer sb = new StringBuffer();
                sb.append("required argument -");
                sb.append(a.getShortName());
                sb.append(", --");
                sb.append(a.getLongName());
                sb.append(" not found");

                throw new CommandLineParseException(sb.toString());
            }
        }
    }
}
