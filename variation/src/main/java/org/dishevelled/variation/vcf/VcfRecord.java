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

import javax.annotation.concurrent.Immutable;

/**
 * VCF record.
 *
 * @author  Michael Heuer
 */
@Immutable
final class VcfRecord
{
    /** Line number. */
    private long lineNumber;

    /** Chromosome. */
    private String chrom;

    /** Position. */
    private long pos;

    /** Array of ids. */
    private String[] id;

    /** Reference allele. */
    private String ref;

    /** Alternate alleles. */
    private String[] alt;

    /** QUAL score. */
    private double qual;

    /** Filter. */
    private String filter;

    /** INFO key-value pairs. */
    private Map<String, String> info;

    /** GT key-value pairs. */
    private Map<String, String> gt;


    /**
     * Create a new VCF record.
     *
     * @param lineNumber line number
     * @param chrom chromosome
     * @param pos position
     * @param id array of ids
     * @param ref reference allele
     * @param alt alternate alleles
     * @param qual QUAL score
     * @param filter filter
     * @param info INFO key-value pairs
     * @param gt GT key-value pairs
     */
    VcfRecord(final long lineNumber,
              final String chrom,
              final long pos,
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


    /**
     * Return the line number for this VCF record.
     *
     * @return the line number for this VCF record
     */
    long getLineNumber()
    {
        return lineNumber;
    }

    /**
     * Return the chromosome for this VCF record.
     *
     * @return the chromosome for this VCF record
     */
    String getChrom()
    {
        return chrom;
    }

    /**
     * Return the position for this VCF record.
     *
     * @return the position for this VCF record
     */
    long getPos()
    {
        return pos;
    }

    /**
     * Return the array of ids for this VCF record.
     *
     * @return the array of ids for this VCF record
     */
    String[] getId()
    {
        return id;
    }

    /**
     * Return the reference allele for this VCF record.
     *
     * @return the reference allele for this VCF record
     */
    String getRef()
    {
        return ref;
    }

    /**
     * Return the alternate alleles for this VCF record.
     *
     * @return the alternate alleles for this VCF record
     */
    String[] getAlt()
    {
        return alt;
    }

    /**
     * Return the QUAL score for this VCF record.
     *
     * @return the QUAL score for this VCF record
     */
    double getQual()
    {
        return qual;
    }

    /**
     * Return the filter for this VCF record.
     *
     * @return the filter for this VCF record
     */
    String getFilter()
    {
        return filter;
    }

    /**
     * Return the INFO key-value pairs for this VCF record.
     *
     * @return the INFO key-value pairs for this VCF record
     */
    Map<String, String> getInfo()
    {
        return info;
    }

    /**
     * Return the GT key-value pairs for this VCF record.
     *
     * @return the GT key-value pairs for this VCF record
     */
    Map<String, String> getGt()
    {
        return gt;
    }
}
