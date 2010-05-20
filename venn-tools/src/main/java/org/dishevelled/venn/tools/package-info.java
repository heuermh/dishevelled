/*

    dsh-venn-tools  Command line tools for venn diagrams.
    Copyright (c) 2010 held jointly by the individual authors.

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

/**
 * Command line tools for venn diagrams.
 *
 * <p>
 * Examples:
 * <pre>
 * $ venn2 --count first.txt second.txt
 * 1000\t2000\t500\t3000
 *
 * $ venn2 --count --header first.txt second.txt
 * first.txt only\tsecond.txt only\nintersection\tunion
 * 1000\t2000\t500\t3000
 *
 * $ venn2 --count --first-only first.txt second.txt
 * 1000
 *
 * $ venn2 --count --second-only first.txt second.txt
 * 2000
 *
 * $ venn2 --count --intersection first.txt second.txt
 * 500
 *
 * $ venn2 --count --union first.txt second.txt
 * 3000
 *
 * $ venn2 --count --first-only --header first.txt second.txt
 * first.txt only
 * 1000
 *
 * $ venn2 --count --second-only --header first.txt second.txt
 * second.txt only
 * 2000
 *
 * $ venn2 --count --intersection --header first.txt second.txt
 * intersection
 * 500
 *
 * $ venn2 --count --union --header first.txt second.txt
 * union
 * 3000
 *
 * $ venn2 --count --first-only --intersection first.txt second.txt
 * 1000\t500
 *
 * $ venn2 --count --first-only --intersection --header first.txt second.txt
 * first.txt only\tintersection
 * 1000\t500
 *
 * $ venn2 --count --first-only first-only.txt first.txt second.txt
 * 1000\n &gt; first-only.txt
 *
 * $ venn2 --count --first-only first-only.txt --header first.txt second.txt
 * first.txt only\n1000\n &gt; first-only.txt
 *
 * $ venn2 --count --first-only first-only.txt --intersection intersection.txt first.txt second.txt
 * 1000\n &gt; first-only.txt
 * 500\n... &gt; intersection.txt
 *
 * $ venn2 --count --first-only first-only.txt --intersection intersection.txt --header first.txt second.txt
 * first.txt only\n1000\n &gt; first-only.txt
 * intersection\n500\n... &gt; intersection.txt
 *
 * $ venn2 first.txt second.txt
 * first0\tsecond0\tintersection0\tunion0
 * first1\tsecond1\tintersection1\tunion1...
 *
 * $ venn2 --header first.txt second.txt
 * first.txt only\tsecond.txt only\nintersection\tunion
 * first0\tsecond0\tintersection0\tunion0
 * first1\tsecond1\tintersection1\tunion1...
 *
 * $ venn2 --first-only first.txt second.txt
 * first0
 * first1...
 *
 * $ venn2 --second-only first.txt second.txt
 * second0
 * second1...
 *
 * $ venn2 --intersection first.txt second.txt
 * intersection0
 * intersection1...
 *
 * $ venn2 --union first.txt second.txt
 * union0
 * union1...
 *
 * $ venn2 --first-only --header first.txt second.txt
 * first.txt only
 * first0
 * first1...
 *
 * $ venn2 --second-only --header first.txt second.txt
 * second.txt only
 * second0
 * second1...
 *
 * $ venn2 --intersection --header first.txt second.txt
 * intersection
 * intersection0
 * intersection1...
 *
 * $ venn2 --union --header first.txt second.txt
 * union
 * union0
 * union1...
 *
 * $ venn2 --first-only --intersection first.txt second.txt
 * first0\tintersection0
 * first1\tintersection1...
 *
 * $ venn2 --first-only --intersection --header first.txt second.txt
 * first.txt only\tintersection
 * first0\tintersection0
 * first1\tintersection1...
 *
 * $ venn2 --first-only first-only.txt first.txt second.txt
 * first0\nfirst1\n... &gt; first-only.txt
 *
 * $ venn2 --first-only first-only.txt --header first.txt second.txt
 * first.txt only\nfirst0\nfirst1\n... &gt; first-only.txt
 *
 * $ venn2 --first-only first-only.txt --intersection intersection.txt first.txt second.txt
 * first0\nfirst1\n... &gt; first-only.txt
 * intersection0\nintersection1\n... &gt; intersection.txt
 *
 * $ venn2 --first-only first-only.txt --intersection first.txt second.txt
 * intersection0
 * intersection1...
 * first0\nfirst1\n... &gt; first-only.txt
 *
 * $ venn2 --first-only first-only.txt --intersection --header first.txt second.txt
 * intersection
 * intersection0
 * intersection1...
 * first.txt only\nfirst0\nfirst1\n... &gt; first-only.txt
 *
 * $ venn2 --first-only first-only.txt --intersection --union first.txt second.txt
 * intersection0\tunion0
 * intersection1\tunion1...
 * first0\nfirst1\n... &gt; first-only.txt
 *
 * $ venn2 --first-only first-only.txt --intersection --header first.txt second.txt
 * intersection\tunion
 * intersection0\tunion0
 * intersection1\tunion1...
 * first.txt only\nfirst0\nfirst1\n... &gt; first-only.txt
 * </pre>
 * </p>
 */
package org.dishevelled.venn.tools;