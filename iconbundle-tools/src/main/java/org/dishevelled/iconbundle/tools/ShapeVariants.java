/*

    dsh-iconbundle-tools  Command line icon bundle tools.
    Copyright (c) 2003-2011 held jointly by the individual authors.

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
package org.dishevelled.iconbundle.tools;

import java.awt.Color;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.BasicStroke;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import java.util.List;

import org.dishevelled.commandline.Usage;
import org.dishevelled.commandline.Switch;
import org.dishevelled.commandline.Argument;
import org.dishevelled.commandline.ArgumentList;
import org.dishevelled.commandline.CommandLine;
import org.dishevelled.commandline.CommandLineParser;
import org.dishevelled.commandline.CommandLineParseException;

import org.dishevelled.commandline.argument.FloatArgument;
import org.dishevelled.commandline.argument.StringArgument;
import org.dishevelled.commandline.argument.DoubleArgument;
import org.dishevelled.commandline.argument.IntegerListArgument;

import org.dishevelled.iconbundle.IconBundle;

import org.dishevelled.iconbundle.impl.ShapeIconBundle;

/**
 * Shape variants.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class ShapeVariants
{

    /**
     * Main.
     *
     * @param args command line arguments, ignored
     */
    public static void main(final String[] args)
    {
       CommandLine commandLine = null;
        ArgumentList arguments = null;
        try
        {
            commandLine = new CommandLine(args);

            Argument<Double> x = new DoubleArgument("x", "x", "x coordinate", true);
            Argument<Double> y = new DoubleArgument("y", "y", "y coordinate", true);
            Argument<Double> w = new DoubleArgument("w", "width", "width", true);
            Argument<Double> h = new DoubleArgument("h", "height", "height", true);
            Argument<String> s = new StringArgument("s", "shape", "shape, one of { ellipse, rectangle }", true);
            Argument<Float> t = new FloatArgument("t", "stroke", "stroke", true);
            Argument<List<Integer>> f = new IntegerListArgument("f", "fill-color", "fill color, in int RGBA format:  R,G,B,A", true);
            Argument<List<Integer>> k = new IntegerListArgument("k", "stroke-color", "stroke color, in int RGBA format:  R,G,B,A", true);
            Argument<String> o = new StringArgument("o", "output", "output file name", true);
            Argument<Boolean> b = new Switch("b", "draw-borders", "true to draw borders");

            arguments = new ArgumentList(new Argument[] { x, y, w, h, s, t, f, k, o, b });

            CommandLineParser.parse(commandLine, arguments);

            Shape shape = null;
            if ("ellipse".equals(s.getValue()))
            {
                shape = new Ellipse2D.Double(x.getValue(), y.getValue(), w.getValue(), h.getValue());
            }
            else if ("rectangle".equals(s.getValue()))
            {
                shape = new Rectangle2D.Double(x.getValue(), y.getValue(), w.getValue(), h.getValue());
            }
            else
            {
                throw new CommandLineParseException("shape " + s.getValue() + " not recognized");
            }

            Stroke stroke = new BasicStroke(t.getValue());
            Color fillColor = new Color(f.getValue().get(0), f.getValue().get(1), f.getValue().get(2), f.getValue().get(3));
            Color strokeColor = new Color(k.getValue().get(0), k.getValue().get(1), k.getValue().get(2), k.getValue().get(3));

            IconBundle iconBundle = new ShapeIconBundle(shape, stroke, fillColor, strokeColor);

            Runnable sav = new ShowAllVariants(iconBundle, o.getValue(), b.getValue());
            sav.run();
        }
        catch (CommandLineParseException e)
        {
            Usage.usage("java -jar shape-variants.jar [args]\n\nShape Variants", e, commandLine, arguments, System.err);
        }
    }
}