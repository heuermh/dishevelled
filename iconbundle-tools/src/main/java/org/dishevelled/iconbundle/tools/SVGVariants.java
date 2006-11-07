/*

    dsh-iconbundle-tools  Command line icon bundle tools.
    Copyright (c) 2003-2006 held jointly by the individual authors.

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
package org.dishevelled.iconbundle.tools;

import java.io.File;

import java.util.List;
import java.util.Arrays;

import org.dishevelled.commandline.Usage;
import org.dishevelled.commandline.Switch;
import org.dishevelled.commandline.Argument;
import org.dishevelled.commandline.ArgumentList;
import org.dishevelled.commandline.CommandLine;
import org.dishevelled.commandline.CommandLineParser;
import org.dishevelled.commandline.CommandLineParseException;

import org.dishevelled.commandline.argument.FileArgument;
import org.dishevelled.commandline.argument.StringArgument;

import org.dishevelled.iconbundle.IconBundle;

import org.dishevelled.iconbundle.impl.SVGIconBundle;

/**
 * SVG variants.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class SVGVariants
{

    /**
     * Main.
     *
     * @param args command line arguments
     */
    public static final void main(final String[] args)
    {
        CommandLine commandLine = null;
        ArgumentList arguments = null;
        try
        {
            commandLine = new CommandLine(args);

            Argument<File> i = new FileArgument("i", "input", "input file", true);
            Argument<String> o = new StringArgument("o", "output", "output file name", true);
            Argument<Boolean> b = new Switch("b", "draw-borders", "true to draw borders");

            arguments = new ArgumentList(new Argument[] { i, o, b });

            CommandLineParser.parse(commandLine, arguments);

            IconBundle iconBundle = new SVGIconBundle(i.getValue());

            Runnable sav = new ShowAllVariants(iconBundle, o.getValue(), b.getValue());
            sav.run();
        }
        catch (CommandLineParseException e)
        {
            Usage.usage("java -jar svg-variants.jar [args]\n\nSVG Variants", e, commandLine, arguments, System.err);
        }
        catch (IllegalArgumentException e)
        {
            Usage.usage("java -jar svg-variants.jar [args]\n\nSVG Variants", e, commandLine, arguments, System.err);
        }
    }
}