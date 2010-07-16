/*

    dsh-venn-cytoscape-plugin  Cytoscape plugin for venn diagrams.
    Copyright (c) 2010 held jointly by the individual authors.

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
package org.dishevelled.venn.cytoscape;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import cytoscape.CyNode;

import org.dishevelled.piccolo.venn.BinaryVennNode;
import org.dishevelled.piccolo.venn.TernaryVennNode;
import org.dishevelled.piccolo.venn.QuaternaryVennNode;

import org.piccolo2d.PCanvas;

import org.piccolo2d.util.PPaintContext;

/**
 * Diagram view.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
final class DiagramView
    extends JPanel
{
    /** Canvas. */
    private final PCanvas canvas;


    private DiagramView()
    {
        canvas = new PCanvas();
        canvas.setDefaultRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.setAnimatingRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);
        canvas.setInteractingRenderQuality(PPaintContext.HIGH_QUALITY_RENDERING);

        // override pan & zoom handlers
        // add context menu

        setLayout(new BorderLayout());
        add("Center", canvas);
    }

    DiagramView(final BinaryVennNode<CyNode> binaryVennNode)
    {
        this();
        binaryVennNode.offset(150.0d, 150.0d); // fix
        canvas.getLayer().addChild(binaryVennNode);
    }

    DiagramView(final TernaryVennNode<CyNode> ternaryVennNode)
    {
        this();
        ternaryVennNode.offset(75.0d, 75.0d);
        canvas.getLayer().addChild(ternaryVennNode);
    }

    DiagramView(final QuaternaryVennNode<CyNode> quaternaryVennNode)
    {
        this();
        quaternaryVennNode.offset(40.0d, 235.0d);
        canvas.getLayer().addChild(quaternaryVennNode);
    }
}