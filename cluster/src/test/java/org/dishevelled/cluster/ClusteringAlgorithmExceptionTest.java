/*

    dsh-cluster  Framework for cluster algorithms.
    Copyright (c) 2007 held jointly by the individual authors.

    This library is free software; you can redistribute it and/or modify it
    under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation; either version 2.1 of the License, or (at
    your option) any later version.

    This library is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; with out even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
    License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this library;  if not, write to the Free Software Foundation,
    Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA.

    > http://www.gnu.org/copyleft/lesser.html
    > http://www.opensource.org/licenses/lgpl-license.php

*/
package org.dishevelled.cluster;

import junit.framework.TestCase;

/**
 * Unit test for ClusterAlgorithmException.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class ClusterAlgorithmExceptionTest
    extends TestCase
{

    public void testConstructor()
    {
        Throwable cause = new Exception("message");

        ClusterAlgorithmException exception0 = new ClusterAlgorithmException((String) null);
        ClusterAlgorithmException exception1 = new ClusterAlgorithmException("message");
        ClusterAlgorithmException exception2 = new ClusterAlgorithmException((Throwable) null);
        ClusterAlgorithmException exception3 = new ClusterAlgorithmException(cause);
        ClusterAlgorithmException exception4 = new ClusterAlgorithmException(null, cause);
        ClusterAlgorithmException exception5 = new ClusterAlgorithmException("message", null);
        ClusterAlgorithmException exception6 = new ClusterAlgorithmException("message", cause);
    }
}