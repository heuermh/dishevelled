/*

    dsh-vocabulary  A description of concepts and their relations in a domain.
    Copyright (c) 2002-2008 held jointly by the individual authors.

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
package org.dishevelled.vocabulary;

import java.util.List;
import java.util.Arrays;
import java.util.Collections;

import junit.framework.TestCase;

/**
 * Unit test for Evidence.
 *
 * @author  Michael Heuer
 * @version $Revision: 1.3 $ $Date: 2003/12/02 03:41:15 $
 */
public class EvidenceTest
    extends TestCase
{

    public void testConstructor()
    {
        Evidence e0 = new Evidence("evidence", 1.0d, 1.0d);
        Evidence e1 = new Evidence("", 1.0d, 1.0d);
        Evidence e2 = new Evidence(null, 1.0d, 1.0d);
        Evidence e3 = new Evidence("evidence", Double.MIN_VALUE, Double.MIN_VALUE);
        Evidence e4 = new Evidence("evidence", Double.MAX_VALUE, Double.MAX_VALUE);
        Evidence e5 = new Evidence("evidence", Double.NaN, Double.NaN);
    }

    public void testEvidence()
    {
        Evidence e = new Evidence("evidence name", 0.5d, 1.0d);
        assertNotNull("e not null", e);
        assertNotNull("name not null", e.getName());
        assertEquals("name equals evidence name", "evidence name", e.getName());
        assertTrue("score == 0.5d", 0.5d == e.getScore());
        assertTrue("confidence == 1.0d", 1.0d == e.getConfidence());

        Evidence e0 = new Evidence("evidence0", 0.5d, 0.75d);
        Evidence e1 = new Evidence("evidence1", 0.5d, 0.75d);
        Evidence e2 = new Evidence("evidence0", 0.75d, 0.75d);
        Evidence e3 = new Evidence("evidence0", 0.5d, 1.0d);
        Evidence e4 = new Evidence("evidence0", 0.5d, 0.75d);

        assertTrue("e0 not equals e1", !e0.equals(e1));
        assertTrue("e0 not equals e2", !e0.equals(e2));
        assertTrue("e0 not equals e3", !e0.equals(e3));
        assertTrue("e0 not equals new Object()", !e0.equals(new Object()));
        assertEquals("e0 equals e4", e0, e4);
        assertEquals("e0 hashCode equals e4 hashCode", e0.hashCode(), e4.hashCode());
        assertNotNull("e0 toString not null", e0.toString());
    }

    public void testComparable()
    {
        Evidence e0 = new Evidence("evidence0", 1.0d, 1.0d);
        Evidence e1 = new Evidence("evidence1", 1.0d, 1.0d);
        Evidence e2 = new Evidence(null, 1.0d, 1.0d);
        Evidence e3 = new Evidence("evidence0", 1.0d, 0.75d);
        Evidence e4 = new Evidence("evidence0", 0.75d, 1.0d);
        Evidence e5 = new Evidence("evidence0", 0.5d, 1.0d);
        Evidence e6 = new Evidence("evidence0", 0.5d, 0.5d);
        Evidence e7 = new Evidence("evidence0", 0.5d, 0.25d);
        Evidence e8 = new Evidence("evidence0", 0.25d, 1.0d);
        Evidence e9 = new Evidence("evidence0", 0.25d, 0.5d);

        List evidence0 = Arrays.asList(new Evidence[] { e9, e8, e7, e6, e5, e4, e3, e2, e1, e0 });

        Collections.sort(evidence0);

        assertEquals("evidence0 size equals 10", 10, evidence0.size());
        assertEquals("evidence0 get(0) equals e0", e0, evidence0.get(0));
        assertEquals("evidence0 get(1) equals e1", e1, evidence0.get(1));
        assertEquals("evidence0 get(2) equals e2", e2, evidence0.get(2));
        assertEquals("evidence0 get(3) equals e3", e3, evidence0.get(3));
        assertEquals("evidence0 get(4) equals e4", e4, evidence0.get(4));
        assertEquals("evidence0 get(5) equals e5", e5, evidence0.get(5));
        assertEquals("evidence0 get(6) equals e6", e6, evidence0.get(6));
        assertEquals("evidence0 get(7) equals e7", e7, evidence0.get(7));
        assertEquals("evidence0 get(8) equals e8", e8, evidence0.get(8));
        assertEquals("evidence0 get(9) equals e9", e9, evidence0.get(9));

        List evidence1 = Arrays.asList(new Evidence[] { e0, e1, e2, e3, e4, e5, e6, e7, e8, e9 });

        Collections.sort(evidence1);

        assertEquals("evidence1 size equals 10", 10, evidence1.size());
        assertEquals("evidence1 get(0) equals e0", e0, evidence1.get(0));
        assertEquals("evidence1 get(1) equals e1", e1, evidence1.get(1));
        assertEquals("evidence1 get(2) equals e2", e2, evidence1.get(2));
        assertEquals("evidence1 get(3) equals e3", e3, evidence1.get(3));
        assertEquals("evidence1 get(4) equals e4", e4, evidence1.get(4));
        assertEquals("evidence1 get(5) equals e5", e5, evidence1.get(5));
        assertEquals("evidence1 get(6) equals e6", e6, evidence1.get(6));
        assertEquals("evidence1 get(7) equals e7", e7, evidence1.get(7));
        assertEquals("evidence1 get(8) equals e8", e8, evidence1.get(8));
        assertEquals("evidence1 get(9) equals e9", e9, evidence1.get(9));

        List evidence2 = Arrays.asList(new Evidence[] { e2, e7, e5, e3, e6, e1, e4, e0, e9, e8 });

        Collections.sort(evidence2);

        assertEquals("evidence2 size equals 10", 10, evidence2.size());
        assertEquals("evidence2 get(0) equals e0", e0, evidence2.get(0));
        assertEquals("evidence2 get(1) equals e1", e1, evidence2.get(1));
        assertEquals("evidence2 get(2) equals e2", e2, evidence2.get(2));
        assertEquals("evidence2 get(3) equals e3", e3, evidence2.get(3));
        assertEquals("evidence2 get(4) equals e4", e4, evidence2.get(4));
        assertEquals("evidence2 get(5) equals e5", e5, evidence2.get(5));
        assertEquals("evidence2 get(6) equals e6", e6, evidence2.get(6));
        assertEquals("evidence2 get(7) equals e7", e7, evidence2.get(7));
        assertEquals("evidence2 get(8) equals e8", e8, evidence2.get(8));
        assertEquals("evidence2 get(9) equals e9", e9, evidence2.get(9));

        List evidence3 = Arrays.asList(new Evidence[] { e9, e8, e7, e6, e5, e4, e3, e2, e1, e0 });

        Collections.sort(evidence3, Collections.reverseOrder());

        assertEquals("evidence3 size equals 10", 10, evidence3.size());
        assertEquals("evidence3 get(0) equals e9", e9, evidence3.get(0));
        assertEquals("evidence3 get(1) equals e8", e8, evidence3.get(1));
        assertEquals("evidence3 get(2) equals e7", e7, evidence3.get(2));
        assertEquals("evidence3 get(3) equals e6", e6, evidence3.get(3));
        assertEquals("evidence3 get(4) equals e5", e5, evidence3.get(4));
        assertEquals("evidence3 get(5) equals e4", e4, evidence3.get(5));
        assertEquals("evidence3 get(6) equals e3", e3, evidence3.get(6));
        assertEquals("evidence3 get(7) equals e2", e2, evidence3.get(7));
        assertEquals("evidence3 get(8) equals e1", e1, evidence3.get(8));
        assertEquals("evidence3 get(9) equals e0", e0, evidence3.get(9));

        List evidence4 = Arrays.asList(new Evidence[] { e0, e1, e2, e3, e4, e5, e6, e7, e8, e9 });

        Collections.sort(evidence4, Collections.reverseOrder());

        assertEquals("evidence4 size equals 10", 10, evidence4.size());
        assertEquals("evidence4 get(0) equals e9", e9, evidence4.get(0));
        assertEquals("evidence4 get(1) equals e8", e8, evidence4.get(1));
        assertEquals("evidence4 get(2) equals e7", e7, evidence4.get(2));
        assertEquals("evidence4 get(3) equals e6", e6, evidence4.get(3));
        assertEquals("evidence4 get(4) equals e5", e5, evidence4.get(4));
        assertEquals("evidence4 get(5) equals e4", e4, evidence4.get(5));
        assertEquals("evidence4 get(6) equals e3", e3, evidence4.get(6));
        assertEquals("evidence4 get(7) equals e2", e2, evidence4.get(7));
        assertEquals("evidence4 get(8) equals e1", e1, evidence4.get(8));
        assertEquals("evidence4 get(9) equals e0", e0, evidence4.get(9));

        List evidence5 = Arrays.asList(new Evidence[] { e2, e7, e5, e3, e6, e1, e4, e0, e9, e8 });

        Collections.sort(evidence5, Collections.reverseOrder());

        assertEquals("evidence5 size equals 10", 10, evidence5.size());
        assertEquals("evidence5 get(0) equals e9", e9, evidence5.get(0));
        assertEquals("evidence5 get(1) equals e8", e8, evidence5.get(1));
        assertEquals("evidence5 get(2) equals e7", e7, evidence5.get(2));
        assertEquals("evidence5 get(3) equals e6", e6, evidence5.get(3));
        assertEquals("evidence5 get(4) equals e5", e5, evidence5.get(4));
        assertEquals("evidence5 get(5) equals e4", e4, evidence5.get(5));
        assertEquals("evidence5 get(6) equals e3", e3, evidence5.get(6));
        assertEquals("evidence5 get(7) equals e2", e2, evidence5.get(7));
        assertEquals("evidence5 get(8) equals e1", e1, evidence5.get(8));
        assertEquals("evidence5 get(9) equals e0", e0, evidence5.get(9));

        Evidence e10 = new Evidence(null, 1.0d, 1.0d);
        assertEquals(0, e10.compareTo(e2));
        assertEquals(0, e2.compareTo(e10));
    }
}
