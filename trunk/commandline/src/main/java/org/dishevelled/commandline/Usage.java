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

import java.io.PrintWriter;
import java.io.OutputStream;

import java.util.List;
import java.util.Arrays;

import java.lang.reflect.ParameterizedType;

/**
 * Usage string.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class Usage
{

    /**
     * Private no-arg constructor.
     */
    private Usage()
    {
        // empty
    }


    /**
     * Generate an usage string to the specified output stream.
     *
     * @param r runnable
     * @param message message
     * @param cause cause
     * @param commandLine command line
     * @param arguments list of arguments
     * @param out output stream, must not be null
     */
    public static void usage(final Runnable r,
                             final String message,
                             final Throwable cause,
                             final CommandLine commandLine,
                             final List<Argument<?>> arguments,
                             final OutputStream out)
    {
        if (out == null)
        {
            throw new IllegalArgumentException("out must not be null");
        }

        PrintWriter pw = new PrintWriter(out, false);
        pw.println("usage:");

        if (r != null)
        {
            pw.println("java " + r.getClass().getName());
            pw.print("\n");
        }

        if (message != null)
        {
            pw.println(message);
            pw.print("\n");
        }

        if (cause != null)
        {
            cause.printStackTrace(pw);
            pw.print("\n");
        }

        if ((arguments != null) && (arguments.size() > 0))
        {
            pw.println("arguments:");

            for (Argument a : arguments)
            {
                StringBuffer sb = new StringBuffer();
                sb.append("   -");
                sb.append(a.getShortName());
                sb.append(", --");
                sb.append(a.getLongName());

                if (!(a instanceof Switch))
                {
                    sb.append(" ");
                    sb.append(Arrays.asList(((ParameterizedType) a.getClass().getGenericSuperclass()).getActualTypeArguments()));
                }

                sb.append("  ");
                sb.append(a.getDescription());

                if (a.isRequired())
                {
                    sb.append(" [required]");
                }
                else
                {
                    sb.append(" [optional]");
                }

                pw.println(sb.toString());
                pw.print("\n");
            }
        }

        pw.flush();
    }
}
