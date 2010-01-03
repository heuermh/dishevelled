/*

    dsh-cluster-examples  Examples for the cluster library.
    Copyright (c) 2008-2010 held jointly by the individual authors.

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
package org.dishevelled.cluster.examples;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dishevelled.commandline.ArgumentList;
import org.dishevelled.commandline.CommandLine;
import org.dishevelled.commandline.CommandLineParseException;
import org.dishevelled.commandline.CommandLineParser;
import org.dishevelled.commandline.Switch;
import org.dishevelled.commandline.Usage;

/**
 * Split the words from standard in and print them one per line to standard out.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class SplitWords
    implements Runnable
{
    /** Usage string. */
    private static final String USAGE = "java SplitWords [args]\n\n   Split the words from standard in and print them one per line to standard out.";

    /** Pattern to find words. */
    private static final Pattern PATTERN = Pattern.compile("(\\w+)\\W*");


    /** {@inheritDoc} */
    public void run()
    {
        BufferedReader reader = null;
        try
        {
            reader = new BufferedReader(new InputStreamReader(System.in));
            while (reader.ready())
            {
                String line = reader.readLine();
                Matcher matcher = PATTERN.matcher(line);
                while (matcher.find())
                {
                    System.out.println(matcher.group(1));
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (reader != null)
                {
                    reader.close();
                }
            }
            catch (Exception e)
            {
                //. ignore
            }
        }
    }


    /**
     * Main.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args)
    {
        CommandLine commandLine = null;
        ArgumentList arguments = null;
        try
        {
            Switch help = new Switch("h", "help", "display help message");

            arguments = new ArgumentList(help);
            commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            if (help.wasFound())
            {
                Usage.usage(USAGE, null, commandLine, arguments, System.out);
            }
            else
            {
                new SplitWords().run();
            }
        }
        catch (CommandLineParseException e)
        {
            Usage.usage(USAGE, e, commandLine, arguments, System.err);
        }
        catch (IllegalArgumentException e)
        {
            Usage.usage(USAGE, e, commandLine, arguments, System.err);
        }
    }
}