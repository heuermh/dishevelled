/*

    dsh-cluster  Framework for clustering algorithms.
    Copyright (c) 2007-2010 held jointly by the individual authors.

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
package org.dishevelled.cluster;

import junit.framework.TestCase;

/**
 * Unit test for ClusteringAlgorithmException.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class ClusteringAlgorithmExceptionTest
    extends TestCase
{

    public void testConstructor()
    {
        Throwable cause = new Exception("message");

        ClusteringAlgorithmException exception0 = new ClusteringAlgorithmException((String) null);
        ClusteringAlgorithmException exception1 = new ClusteringAlgorithmException("message");
        ClusteringAlgorithmException exception2 = new ClusteringAlgorithmException((Throwable) null);
        ClusteringAlgorithmException exception3 = new ClusteringAlgorithmException(cause);
        ClusteringAlgorithmException exception4 = new ClusteringAlgorithmException(null, cause);
        ClusteringAlgorithmException exception5 = new ClusteringAlgorithmException("message", null);
        ClusteringAlgorithmException exception6 = new ClusteringAlgorithmException("message", cause);
    }
}