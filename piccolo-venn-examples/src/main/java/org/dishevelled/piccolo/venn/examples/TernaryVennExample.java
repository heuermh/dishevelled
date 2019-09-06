/*

    dsh-piccolo-venn-examples  Piccolo2D venn diagram node examples.
    Copyright (c) 2009-2019 held jointly by the individual authors.

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
package org.dishevelled.piccolo.venn.examples;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.piccolo2d.PCanvas;
import org.piccolo2d.util.PPaintContext;
import org.dishevelled.piccolo.venn.TernaryVennNode;

/**
 * Ternary venn node example.
 *
 * @author  Michael Heuer
 */
public final class TernaryVennExample
    extends JPanel
    implements Runnable
{

    /**
     * Create a new ternary venn example.
     */
    public TernaryVennExample()
    {
        super();
        PCanvas canvas = new PCanvas();
        canvas.setDefaultRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.setAnimatingRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.setInteractingRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);

        Set<String> set0 = read("the_pioneers.txt");
        Set<String> set1 = read("the_deerslayer.txt");
        Set<String> set2 = read("the_pathfinder.txt");

        TernaryVennNode<String> node = new TernaryVennNode<String>("The Pioneers", set0,
                                                                       "The Deerslayer", set1, "The Pathfinder", set2);
        node.offset(75.0d, 75.0d);
        canvas.getLayer().addChild(node);

        setLayout(new BorderLayout());
        add("Center", canvas);
    }


    @Override
    public void run()
    {
        JFrame f = new JFrame("Ternary Venn Example");
        f.setContentPane(this);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setBounds(100, 100, 400, 400);
        f.setVisible(true);
    }


    private static Set<String> read(final String name)
    {
        BufferedReader reader = null;
        Set<String> result = new HashSet<String>(12000);
        try
        {
            reader = new BufferedReader(new InputStreamReader(TernaryVennExample.class.getResourceAsStream(name)));
            while (reader.ready())
            {
                result.add(reader.readLine().trim());
            }
        }
        catch (IOException e)
        {
            // ignore
        }
        finally
        {
            try
            {
                reader.close();
            }
            catch (Exception e)
            {
                // ignore
            }
        }
        return result;
    }


    /**
     * Main.
     *
     * @param args command line arguments, ignored
     */
    public static void main(final String[] args)
    {
        SwingUtilities.invokeLater(new TernaryVennExample());
    }
}
