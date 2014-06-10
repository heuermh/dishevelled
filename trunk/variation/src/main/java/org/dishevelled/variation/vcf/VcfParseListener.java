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
 * VCF parse listener.
 *
 * @author  Michael Heuer
 */
interface VcfParseListener
{

    /**
     * Notify this parse listener of the line number.
     *
     * @param lineNumber line number
     * @throws IOException if an I/O error occurs
     */
    void lineNumber(long lineNumber) throws IOException;

    /**
     * Notify this parse listener of a meta string.
     *
     * @param meta meta string
     * @throws IOException if an I/O error occurs
     */
    void meta(String meta) throws IOException;

    /**
     * Notify this parse listener of sample strings.
     *
     * @param samples sample strings
     * @throws IOException if an I/O error occurs
     */
    void samples(String... samples) throws IOException;

    /**
     * Notify this parse listener of a chrom string.
     *
     * @param chrom chrom string
     * @throws IOException if an I/O error occurs
     */
    void chrom(String chrom) throws IOException;

    /**
     * Notify this parse listener of a position.
     *
     * @param pos position
     * @throws IOException if an I/O error occurs
     */
    void pos(long pos) throws IOException;

    /**
     * Notify this parse listener of id strings.
     *
     * @param id id strings
     * @throws IOException if an I/O error occurs
     */
    void id(String... id) throws IOException;

    /**
     * Notify this parse listener of a ref string.
     *
     * @param ref ref string
     * @throws IOException if an I/O error occurs
     */
    void ref(String ref) throws IOException;

    /**
     * Notify this parse listener of alt strings.
     *
     * @param alt alt strings
     * @throws IOException if an I/O error occurs
     */
    void alt(String... alt) throws IOException;

    /**
     * Notify this parse listener of a qual string.
     *
     * @param qual qual string
     * @throws IOException if an I/O error occurs
     */
    void qual(double qual) throws IOException;

    /**
     * Notify this parse listener of a filter string.
     *
     * @param filter filter string
     * @throws IOException if an I/O error occurs
     */
    void filter(String filter) throws IOException;

    /**
     * Notify this parse listener of a info key-value pairs.
     *
     * @param info info key-value pairs
     * @throws IOException if an I/O error occurs
     */
    void info(Map<String, String> info) throws IOException;

    /**
     * Notify this parse listener of a sample gt pair.
     *
     * @param sample sample string
     * @param gt gt string
     * @throws IOException if an I/O error occurs
     */
    void gt(String sample, String gt) throws IOException;

    /**
     * Notify this parse listener a record is complete.
     *
     * @return true to continue parsing
     * @throws IOException if an I/O error occurs
     */
    boolean complete() throws IOException;
}
