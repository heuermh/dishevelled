/*

    dsh-text-rendering-benchmark  Text rendering benchmarks.
    Copyright (c) 2009 held jointly by the individual authors.

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
package org.dishevelled.text.runner;

import java.awt.Dimension;
import java.awt.Font;

import java.awt.image.BufferedImage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import org.dishevelled.text.TextRenderingBenchmark;

import org.dishevelled.text.java2d.*;
import org.dishevelled.text.phtmlview.*;
import org.dishevelled.text.pswing.*;
import org.dishevelled.text.ptext.*;
import org.dishevelled.text.swing.*;

/**
 * Text rendering benchmark runner.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class TextRenderingBenchmarkRunner
    implements Runnable
{

    /** {@inheritDoc} */
    public void run()
    {
        PrintWriter writer = null;
        try
        {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)), true);
            writer.println("<html><body>");
            Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
            Dimension bounds = new Dimension(622, 22);

            List<Class<?>> classes = Arrays.asList(new Class<?>[]
                                                   {
                                                   JLabelAntialiasingOff.class,
                                                   JLabelAntialiasingGasp.class,
                                                   JLabelAntialiasingLcdHrgb.class,
                                                   JLabelAntialiasingLcdHbgr.class,
                                                   JLabelAntialiasingLcdVrgb.class,
                                                   JLabelAntialiasingLcdVbgr.class,
                                                   PTextAntialiasingOff.class,
                                                   PTextAntialiasingGasp.class,
                                                   PTextAntialiasingLcdHrgb.class,
                                                   PTextAntialiasingLcdHbgr.class,
                                                   PTextAntialiasingLcdVrgb.class,
                                                   PTextAntialiasingLcdVbgr.class,
                                                   PSwingAntialiasingOff.class,
                                                   PSwingAntialiasingGasp.class,
                                                   PSwingAntialiasingLcdHrgb.class,
                                                   PSwingAntialiasingLcdHbgr.class,
                                                   PSwingAntialiasingLcdVrgb.class,
                                                   PSwingAntialiasingLcdVbgr.class,
                                                   JTextComponentAntialiasingOff.class,
                                                   JTextComponentAntialiasingGasp.class,
                                                   JTextComponentAntialiasingLcdHrgb.class,
                                                   JTextComponentAntialiasingLcdHbgr.class,
                                                   JTextComponentAntialiasingLcdVrgb.class,
                                                   JTextComponentAntialiasingLcdVbgr.class,
                                                   Java2DAntialiasingOff.class,
                                                   Java2DAntialiasingGasp.class,
                                                   Java2DAntialiasingLcdHrgb.class,
                                                   Java2DAntialiasingLcdHbgr.class,
                                                   Java2DAntialiasingLcdVrgb.class,
                                                   Java2DAntialiasingLcdVbgr.class,
                                                   PHtmlViewAntialiasingOff.class,
                                                   PHtmlViewAntialiasingGasp.class,
                                                   PHtmlViewAntialiasingLcdHrgb.class,
                                                   PHtmlViewAntialiasingLcdHbgr.class,
                                                   PHtmlViewAntialiasingLcdVrgb.class,
                                                   PHtmlViewAntialiasingLcdVbgr.class,
                                                   });

            for (Class<?> c : classes)
            {
                TextRenderingBenchmark benchmark = null;
                benchmark = (TextRenderingBenchmark) c.newInstance();
                benchmark.setFont(font);
                benchmark.setBounds(bounds);
                benchmark.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit posuere.");
                BufferedImage image = null;
                image = benchmark.call();
                ImageIO.write(image, "png", new File(benchmark.getName() + ".png"));
                writer.println("<p>" + benchmark.getName() + ":<br/><img src=\"" + benchmark.getName() + ".png\"/></p>");
            }
            writer.println("</body></html>");
            writer.flush();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
        finally
        {
            try
            {
                writer.close();
            }
            catch (Exception e)
            {
                // ignore
            }
        }
    }


    /**
     * Main.
     *
     * @param args command line arguments, ignored
     */
    public static void main(final String[] args)
    {
        new TextRenderingBenchmarkRunner().run();
    }
}