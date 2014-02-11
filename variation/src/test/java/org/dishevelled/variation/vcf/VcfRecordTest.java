/*

    dsh-variation  Variation.
    Copyright (c) 2013 held jointly by the individual authors.

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
package org.dishevelled.variation.vcf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import org.junit.Test;

/**
 * Unit test for VcfRecord.
 */
public final class VcfRecordTest
{

    @Test
    public void testConstructor()
    {
        long lineNumber = 42L;
        String chrom = "22";
        int pos = 16140370;
        String[] id = new String[] { "rs2096606" };
        String ref = "A";
        String[] alt = new String[] { "G" };
        double qual = 100.0d;
        String filter = "PASS";
        Map<String, String> info = ImmutableMap.<String, String>builder().build();
        Map<String, String> gt = ImmutableMap.<String, String>builder().put("NA19131", "1|1").put("NA19223", "1|1").build();

        VcfRecord record = new VcfRecord(lineNumber, chrom, pos, id, ref, alt, qual, filter, info, gt);
        assertNotNull(record);
        assertEquals(lineNumber, record.getLineNumber());
        assertEquals(chrom, record.getChrom());
        assertEquals(id, record.getId());
        assertEquals(ref, record.getRef());
        assertEquals(alt, record.getAlt());
        assertEquals(qual, record.getQual(), 0.1d);
        assertEquals(filter, record.getFilter());
        assertEquals(info, record.getInfo());
        assertEquals(gt, record.getGt());
    }
}