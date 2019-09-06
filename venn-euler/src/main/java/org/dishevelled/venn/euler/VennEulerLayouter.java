/*

    dsh-venn-euler  Lightweight components for venn/euler diagrams.
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
package org.dishevelled.venn.euler;

import org.dishevelled.venn.VennModel;
import org.dishevelled.venn.VennLayout;
import org.dishevelled.venn.VennLayouter;

import edu.uic.ncdm.venn.VennData; 
import edu.uic.ncdm.venn.VennDiagram; 
import edu.uic.ncdm.venn.VennAnalytic; 

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D;
import java.awt.Shape;

/**
 * VennEuler layouter.
 */
public final class VennEulerLayouter<T> implements VennLayouter<T>
{	
    private final Map<Integer,List<T>> data = new HashMap<Integer,List<T>>();

    public VennLayout layout(final VennModel<T> model,
                             final Rectangle2D boundingBox,
                             final PerformanceHint performanceHint)
    {
        if (model == null)
        {
            throw new NullPointerException("model can't be null");
        }
        if (boundingBox == null)
        {
            throw new NullPointerException("bounding rectangle can't be null");
        }
        if (model.size() <= 0)
        {
            throw new IllegalArgumentException("model size can't be 0!");
        }
        data.clear();

        // populate data
        for (int i = 0; i < model.size(); i++)
        {
            for (T t : model.get(i))
            {
                addElement(i,t);
            }
        }

        return create(boundingBox);
    }

    private void addElement(final int setKey, final T setValue)
    {
        if (setValue == null)
        {
            throw new NullPointerException("set value cannot be null");
        }

        List<T> values = data.get(setKey);
        if (values == null)
        {
            values = new ArrayList<T>();
            data.put(setKey,values);
        }
        values.add(setValue);
    }

    private VennLayout create(final Rectangle2D boundingBox)
    {
        // convert map into VennData
        int numRows = 0;
        for (Integer key : data.keySet())
        {
            numRows += data.get(key).size();
        }

        String[][] stringData = new String[numRows][2];
        int i = 0;
        for (Integer key : data.keySet())
        {
            String keyString = key.toString();
            for (T value : data.get(key))
            {
                stringData[i][0] = value.toString();
                stringData[i][1] = keyString;
                i++;
            }
        }
        VennData vennData = new VennData(stringData, new double[0], false);

        // now create the diagram
        VennDiagram vennDiagram = new VennAnalytic().compute(vennData);

        return new VennEulerLayout(vennDiagram,boundingBox);
    }
}
