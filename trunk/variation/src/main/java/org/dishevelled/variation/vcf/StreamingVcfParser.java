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

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Closeable;
import java.io.IOException;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import com.google.common.io.InputSupplier;

/**
 * Streaming VCF parser.
 *
 * @author  Michael Heuer
 */
final class StreamingVcfParser
{

    /**
     * Private no-arg constructor.
     */
    private StreamingVcfParser()
    {
        // empty
    }

    /**
     * Stream the specified input supplier.
     *
     * @param supplier input supplier, must not be null
     * @param listener event based reader callback, must not be null
     * @throws IOException if an I/O error occurs
     */
    static <R extends Readable & Closeable> void stream(final InputSupplier<R> supplier, final VcfStreamListener listener)
        throws IOException
    {
        checkNotNull(supplier);
        checkNotNull(listener);

        VcfParser.parse(supplier, new VcfParseAdapter()
            {
                /** Line number. */
                private long lineNumber;

                /** Chromosome. */
                private String chrom;

                /** Position. */
                private int pos;

                /** Array of ids. */
                private String[] id;

                /** Reference alleles. */
                private String ref;

                /** Alternate alleles. */
                private String[] alt;

                /** QUAL score. */
                private double qual;

                /** Filter. */
                private String filter;

                /** INFO key-value pairs. */
                private Map<String, String> info;

                /** Map builder for GT key-value pairs. */
                private ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();


                @Override
                public void lineNumber(final long lineNumber) throws IOException
                {
                    this.lineNumber = lineNumber;
                }

                @Override
                public void chrom(final String chrom) throws IOException
                {
                    this.chrom = chrom;
                }

                @Override
                public void pos(final int pos) throws IOException
                {
                    this.pos = pos;
                }

                @Override
                public void id(final String... id) throws IOException
                {
                    this.id = id;
                }

                @Override
                public void ref(final String ref) throws IOException
                {
                    this.ref = ref;
                }

                @Override
                public void alt(final String... alt) throws IOException
                {
                    this.alt = alt;
                }

                @Override
                public void qual(final double qual) throws IOException
                {
                    this.qual = qual;
                }

                @Override
                public void filter(final String filter) throws IOException
                {
                    this.filter = filter;
                }

                @Override
                public void info(final Map<String, String> info) throws IOException
                {
                    this.info = info;
                }

                @Override
                public void gt(final String sample, final String gt) throws IOException
                {
                    builder.put(sample, gt);
                }

                @Override
                public boolean complete() throws IOException
                {
                    listener.record(new VcfRecord(lineNumber, chrom, pos, id, ref, alt, qual, filter, info, builder.build()));

                    chrom = null;
                    pos = -1;
                    id = null;
                    ref = null;
                    alt = null;
                    qual = -1.0d;
                    filter = null;
                    info = null;
                    builder = ImmutableMap.builder();

                    return true;
                }
            });
    }
}
