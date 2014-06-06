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

import static com.google.common.collect.Maps.newHashMap;

import java.io.Closeable;
import java.io.IOException;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import com.google.common.io.CharStreams;
import com.google.common.io.InputSupplier;
import com.google.common.io.LineProcessor;

/**
 * Low-level VCF parser.
 *
 * @author  Michael Heuer
 */
final class VcfParser
{

    /**
     * Private no-arg constructor.
     */
    private VcfParser()
    {
        // empty
    }

    /**
     * Parse the specified input supplier.
     *
     * @param supplier input supplier, must not be null
     * @param listener low-level event based parser callback, must not be null
     * @throws IOException if an I/O error occurs
     */
    static <R extends Readable & Closeable> void parse(final InputSupplier<R> supplier, final VcfParseListener listener)
        throws IOException
    {
        checkNotNull(supplier);
        VcfLineProcessor lineProcessor = new VcfLineProcessor(listener);
        CharStreams.readLines(supplier, lineProcessor);
    }

    /**
     * VCF line processor.
     */
    private static final class VcfLineProcessor implements LineProcessor<Object>
    {
        /** Line number. */
        private long lineNumber = 0;

        /** VCF parse listener. */
        private final VcfParseListener listener;

        /** Map of sample names keyed by column. */
        private final Map<Integer, String> samples = newHashMap();


        /**
         * Create a new VCF line processor.
         *
         * @param listener VCF parse listener
         */
        private VcfLineProcessor(final VcfParseListener listener)
        {
            checkNotNull(listener);
            this.listener = listener;
        }


        @Override
        public Object getResult()
        {
            return null;
        }

        @Override
        public boolean processLine(final String line) throws IOException
        {
            lineNumber++;
            // consider using guava Splitter
            String[] tokens = line.split("\t");

            if (tokens[0].startsWith("##"))
            {
                // meta-information lines
                listener.meta(line);
            }
            else if (tokens[0].startsWith("#CHROM"))
            {
                // header line
                if (tokens.length > 8)
                {
                    for (int column = 9, columns = tokens.length; column < columns; column++)
                    {
                        samples.put(column, tokens[column]);
                    }
                }
                listener.samples(samples.values().toArray(new String[0]));
            }
            else
            {
                // data lines
                listener.lineNumber(lineNumber);
                listener.chrom(tokens[0]);
                listener.pos(Integer.parseInt(tokens[1]));
                listener.id(tokens[2].split(";"));
                listener.ref(tokens[3]);

                // todo: check for symbolic alleles
                listener.alt(tokens[4].split(","));

                listener.qual(Double.parseDouble(tokens[5]));
                listener.filter(tokens[6]);

                String[] infoTokens = tokens[7].split(";");
                ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
                for (String infoToken : infoTokens)
                {
                    String[] entryTokens = infoToken.split("=");
                    if (entryTokens.length == 2)
                    {
                        builder.put(entryTokens[0], entryTokens[1]);
                    }
                }
                listener.info(builder.build());

                if (tokens.length > 8)
                {
                    for (int column = 9, columns = tokens.length; column < columns; column++)
                    {
                        String[] sampleTokens = tokens[column].split(":");
                        // todo:  assumes GT exists and is first field
                        listener.gt(samples.get(column), sampleTokens[0]);
                    }
                }

                if (!listener.complete())
                {
                    return false;
                }
            }
            return true;
        }
    }
}
