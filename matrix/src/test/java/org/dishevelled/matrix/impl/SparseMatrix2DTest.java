/*

    dsh-matrix  long-addressable bit and typed object matrix implementations.
    Copyright (c) 2004-2013 held jointly by the individual authors.

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
package org.dishevelled.matrix.impl;

import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.dishevelled.functor.TernaryProcedure;

import org.dishevelled.matrix.Matrix2D;
import org.dishevelled.matrix.AbstractMatrix2DTest;

/**
 * Unit test for SparseMatrix2D.
 *
 * @author  Michael Heuer
 */
public class SparseMatrix2DTest
    extends AbstractMatrix2DTest
{

    /** @see AbstractMatrix2D */
    protected <T> Matrix2D<T> createMatrix2D(final long rows, final long columns)
    {
        Matrix2D<T> m = new SparseMatrix2D<T>(rows, columns);
        return m;
    }


    //
    // serialization tests

    public void testEmptySerialization()
        throws Exception
    {
        final SparseMatrix2D<String> m0 = new SparseMatrix2D<String>(10, 10);
        assertNotNull("m0 not null", m0);
        assertEquals("m0 size == 100", 100, m0.size());
        assertEquals("m0 cardinality == 0", 0, m0.cardinality());
        assertTrue("m0 instanceof Serializable", (m0 instanceof Serializable));

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(buffer);
        out.writeObject(m0);
        out.close();

        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(buffer.toByteArray()));
        Object dest = in.readObject();
        in.close();

        assertNotNull("dest not null", dest);
        assertTrue("dest instanceof SparseMatrix2D", (dest instanceof SparseMatrix2D));

        final SparseMatrix2D<String> m1 = (SparseMatrix2D<String>) dest;

        assertEquals("m1 size == m0 size", m0.size(), m1.size());
        assertEquals("m1 cardinality == m0 cardinality", m0.cardinality(), m1.cardinality());

        m1.forEach(new TernaryProcedure<Long, Long, String>()
             {
                public void run(final Long row, final Long column, final String ignore)
                {
                    assertEquals("m1 get(" + row + ", " + column + ") == m0 get(" + row + ", " + column + ")",
                                 m0.get(row, column), m1.get(row, column));
                }
            });
    }

    public void testFullSerialization()
        throws Exception
    {
        final SparseMatrix2D<String> m0 = new SparseMatrix2D<String>(10, 10);
        assertNotNull("m0 not null", m0);

        m0.forEach(new TernaryProcedure<Long, Long, String>()
            {
                public void run(final Long row, final Long column, final String ignore)
                {
                    m0.set(row, column, row + "x" + column);
                }
            });

        assertEquals("m0 size == 100", 100, m0.size());
        assertEquals("m0 cardinality == 100", 100, m0.cardinality());
        assertTrue("m0 instanceof Serializable", (m0 instanceof Serializable));

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(buffer);
        out.writeObject(m0);
        out.close();

        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(buffer.toByteArray()));
        Object dest = in.readObject();
        in.close();

        assertNotNull("dest not null", dest);
        assertTrue("dest instanceof SparseMatrix2D", (dest instanceof SparseMatrix2D));

        final SparseMatrix2D<String> m1 = (SparseMatrix2D<String>) dest;

        assertEquals("m1 size == m0 size", m0.size(), m1.size());
        assertEquals("m1 cardinality == m0 cardinality", m0.cardinality(), m1.size());

        m1.forEach(new TernaryProcedure<Long, Long, String>()
             {
                public void run(final Long row, final Long column, final String ignore)
                {
                    assertEquals("m1 get(" + row + ", " + column + ") == m0 get(" + row + ", " + column + ")",
                                 m0.get(row, column), m1.get(row, column));
                }
            });
    }

    /*
    public void testEmptySerializationCompatibility()
        throws Exception
    {
        final SparseMatrix2D<String> m0 = new SparseMatrix2D<String>(10, 10);
        assertNotNull("m0 not null", m0);
        assertEquals("m0 size == 100", 100, m0.size());
        assertEquals("m0 cardinality == 0", 0, m0.cardinality());
        assertTrue("m0 instanceof Serializable", (m0 instanceof Serializable));

        String name = "SparseMatrix2D<String>.empty.ser";
        java.net.URL url = SparseMatrix2DTest.class.getResource(name);
        assertNotNull("url not null", url);

        ObjectInputStream in = new ObjectInputStream(SparseMatrix2DTest.class.getResourceAsStream(name));
        Object dest = in.readObject();
        in.close();

        assertNotNull("dest not null", dest);
        assertTrue("dest instanceof SparseMatrix2D", (dest instanceof SparseMatrix2D));

        final SparseMatrix2D<String> m1 = (SparseMatrix2D<String>) dest;

        assertEquals("m1 size == m0 size", m0.size(), m1.size());
        assertEquals("m1 cardinality == m0 cardinality", m0.cardinality(), m1.cardinality());

        m1.forEach(new TernaryProcedure<Long, Long, String>()
             {
                public void run(final Long row, final Long column, final String ignore)
                {
                    assertEquals("m1 get(" + row + ", " + column + ") == m0 get(" + row + ", " + column + ")",
                                 m0.get(row, column), m1.get(row, column));
                }
            });        
    }

    public void testFullSerializationCompatibility()
        throws Exception
    {
        final SparseMatrix2D<String> m0 = new SparseMatrix2D<String>(10, 10);
        assertNotNull("m0 not null", m0);

        m0.forEach(new BinaryProcedure<Long, String>()
            {
                public void run(final Long index, final String ignore)
                {
                    m0.set(index, index.toString());
                }
            });

        assertEquals("m0 size == 100", 100, m0.size());
        assertEquals("m0 cardinality == 100", 100, m0.cardinality());
        assertTrue("m0 instanceof Serializable", (m0 instanceof Serializable));

        System.out.println("m0 before=" + m0);

        String name = "SparseMatrix2D<String>.full.ser";
        ObjectInputStream in = new ObjectInputStream(SparseMatrix2DTest.class.getResourceAsStream(name));
        Object dest = in.readObject();
        in.close();

        assertNotNull("dest not null", dest);
        assertTrue("dest instanceof SparseMatrix2D", (dest instanceof SparseMatrix2D));

        final SparseMatrix2D<String> m1 = (SparseMatrix2D<String>) dest;

        System.out.println("m0 after=" + m0);
        System.out.println("m1=" + m1);

        assertEquals("m1 size == m0 size", m0.size(), m1.size());
        assertEquals("m1 cardinality == m0 cardinality", m0.cardinality(), m1.cardinality());

        m1.forEach(new TernaryProcedure<Long, Long, String>()
             {
                public void run(final Long row, final Long column, final String ignore)
                {
                    assertEquals("m1 get(" + row + ", " + column + ") == m0 get(" + row + ", " + column + ")",
                                 m0.get(row, column), m1.get(row, column));
                }
            });
    }
    */
}
