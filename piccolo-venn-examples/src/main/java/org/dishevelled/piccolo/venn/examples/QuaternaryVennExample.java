/*

    dsh-piccolo-venn-examples  Piccolo2D venn diagram node examples.
    Copyright (c) 2009-2010 held jointly by the individual authors.

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

import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.PLayer;

import edu.umd.cs.piccolo.util.PPaintContext;

import org.dishevelled.venn.QuaternaryVennModel;

import org.dishevelled.venn.model.QuaternaryVennModelImpl;

import org.dishevelled.piccolo.venn.QuaternaryVennNode;

/**
 * Quaternary venn node example.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class QuaternaryVennExample
    extends JPanel
    implements Runnable
{

    /**
     * Create a new quaternary venn example.
     */
    public QuaternaryVennExample()
    {
        super();
        PCanvas canvas = new PCanvas();
        canvas.setDefaultRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.setAnimatingRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.setInteractingRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);

        Set<String> set0 = new HashSet<String>();
        set0.add("foo");
        set0.add("bar");
        set0.add("baz");

        Set<String> set1 = new HashSet<String>();
        set1.add("bar");
        set1.add("baz");
        set1.add("qux");
        set1.add("garply");

        Set<String> set2 = new HashSet<String>();
        set2.add("bar");
        set2.add("baz");
        set2.add("garply");

        Set<String> set3 = new HashSet<String>();
        set3.add("bar");
        set3.add("baz");

        QuaternaryVennModel<String> model = new QuaternaryVennModelImpl<String>(set0, set1, set2, set3);
        QuaternaryVennNode<String> node = new QuaternaryVennNode<String>(model);
        node.offset(75.0d, 75.0d);
        canvas.getLayer().addChild(node);

        setLayout(new BorderLayout());
        add("Center", canvas);
    }


    /** {@inheritDoc} */
    public void run()
    {
        JFrame f = new JFrame("Quaternary Venn Example");
        f.setContentPane(this);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setBounds(100, 100, 600, 600);
        f.setVisible(true);
    }


    /**
     * Main.
     *
     * @param args command line arguments, ignored
     */
    public static void main(final String[] args)
    {
        SwingUtilities.invokeLater(new QuaternaryVennExample());
    }
}