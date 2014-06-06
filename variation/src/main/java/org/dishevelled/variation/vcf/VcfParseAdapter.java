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

import java.io.IOException;

import java.util.Map;

/**
 * VCF parse adapter.
 *
 * @author  Michael Heuer
 */
class VcfParseAdapter implements VcfParseListener
{

    @Override
    public void lineNumber(final long lineNumber) throws IOException
    {
        // empty
    }

    @Override
    public void meta(final String meta) throws IOException
    {
        // empty
    }

    @Override
    public void samples(final String... samples) throws IOException
    {
        // empty
    }

    @Override
    public void chrom(final String chrom) throws IOException
    {
        // empty
    }

    @Override
    public void pos(final int pos) throws IOException
    {
        // empty
    }

    @Override
    public void id(final String... id) throws IOException
    {
        // empty
    }

    @Override
    public void ref(final String ref) throws IOException
    {
        // empty
    }

    @Override
    public void alt(final String... alt) throws IOException
    {
        // empty
    }

    @Override
    public void qual(final double qual) throws IOException
    {
        // empty
    }

    @Override
    public void filter(final String filter) throws IOException
    {
        // empty
    }

    @Override
    public void info(final Map<String, String> info) throws IOException
    {
        // empty
    }

    @Override
    public void gt(final String sample, final String gt) throws IOException
    {
        // empty
    }

    @Override
    public boolean complete() throws IOException
    {
        return true;
    }
}
