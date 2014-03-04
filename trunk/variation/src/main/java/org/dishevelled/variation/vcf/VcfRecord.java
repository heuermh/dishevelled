/*

    dsh-variation  Variation.
    Copyright (c) 2013-2014 held jointly by the individual authors.

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

import java.util.Map;

/**
 * VCF record.
 */
public final class VcfRecord
{
    private long lineNumber;
    private String chrom;
    private int pos;
    private String[] id;
    private String ref;
    private String[] alt;
    private double qual;
    private String filter;
    private Map<String, String> info;
    private Map<String, String> gt;

    VcfRecord(final long lineNumber,
              final String chrom,
              final int pos,
              final String[] id,
              final String ref,
              final String[] alt,
              final double qual,
              final String filter,
              final Map<String, String> info,
              final Map<String, String> gt)
    {
        this.lineNumber = lineNumber;
        this.chrom = chrom;
        this.pos = pos;
        this.id = id;
        this.ref = ref;
        this.alt = alt;
        this.qual = qual;
        this.filter = filter;
        this.info = info;
        this.gt = gt;
    }


    public long getLineNumber()
    {
        return lineNumber;
    }

    public String getChrom()
    {
        return chrom;
    }

    public int getPos()
    {
        return pos;
    }

    public String[] getId()
    {
        return id;
    }

    public String getRef()
    {
        return ref;
    }

    public String[] getAlt()
    {
        return alt;
    }

    public double getQual()
    {
        return qual;
    }

    public String getFilter()
    {
        return filter;
    }

    public Map<String, String> getInfo()
    {
        return info;
    }

    public Map<String, String> getGt()
    {
        return gt;
    }
}