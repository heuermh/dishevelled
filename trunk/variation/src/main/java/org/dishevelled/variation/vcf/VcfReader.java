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

import java.net.URL;

import java.io.Closeable;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.List;

import com.google.common.base.Charsets;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import com.google.common.io.InputSupplier;
import com.google.common.io.Files;
import com.google.common.io.Resources;

public final class VcfReader
{

    private VcfReader()
    {
        // empty
    }


    public static <R extends Readable & Closeable> void parse(final InputSupplier<R> supplier,
                                                              final VcfParseListener listener)
        throws IOException
    {
        VcfParser.parse(supplier, listener);
    }

    public static <R extends Readable & Closeable> void stream(final InputSupplier<R> supplier,
                                                               final VcfStreamListener listener)
        throws IOException
    {
        StreamingVcfParser.stream(supplier, listener);
    }

    public static Iterable<VcfRecord> read(final File file) throws IOException
    {
        checkNotNull(file);
        Collect collect = new Collect();
        stream(Files.newReaderSupplier(file, Charsets.UTF_8), collect);
        return collect.getResult();
    }

    public static Iterable<VcfRecord> read(final URL url) throws IOException
    {
        checkNotNull(url);
        Collect collect = new Collect();
        stream(Resources.newReaderSupplier(url, Charsets.UTF_8), collect);
        return collect.getResult();
    }

    public static Iterable<VcfRecord> read(final InputStream inputStream) throws IOException
    {
        checkNotNull(inputStream);
        Collect collect = new Collect();
        stream(new InputSupplier<InputStreamReader>()
               {
                   @Override
                   public InputStreamReader getInput() throws IOException
                   {
                       return new InputStreamReader(inputStream);
                   }
               }, collect);
        return collect.getResult();
    }

    // do these all need to be here?

    static <R extends Readable & Closeable> Iterable<VcfSample> samples(final InputSupplier<R> supplier)
        throws IOException
    {
        return VcfSampleParser.samples(supplier);
    }

    public static Iterable<VcfSample> samples(final File file) throws IOException
    {
        checkNotNull(file);
        return samples(Files.newReaderSupplier(file, Charsets.UTF_8));
    }

    public static Iterable<VcfSample> samples(final URL url) throws IOException
    {
        checkNotNull(url);
        return samples(Resources.newReaderSupplier(url, Charsets.UTF_8));
    }

    public static Iterable<VcfSample> samples(final InputStream inputStream) throws IOException
    {
        checkNotNull(inputStream);
        return samples(new InputSupplier<InputStreamReader>()
               {
                   @Override
                   public InputStreamReader getInput() throws IOException
                   {
                       return new InputStreamReader(inputStream);
                   }
               });
    }

    private static final class Collect implements VcfStreamListener
    {
        private final List<VcfRecord> result = Lists.newLinkedList();

        @Override
        public void record(final VcfRecord record)
        {
            result.add(record);
        }

        private Iterable<VcfRecord> getResult()
        {
            return Iterables.unmodifiableIterable(result);
        }
    }
}