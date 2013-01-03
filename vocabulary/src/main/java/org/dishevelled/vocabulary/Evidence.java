/*

    dsh-vocabulary  A description of concepts and their relations in a domain.
    Copyright (c) 2002-2013 held jointly by the individual authors.

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
package org.dishevelled.vocabulary;

/**
 * Evidence supports an assignment by reflecting the strength
 * of knowledge used to create that assignment.
 *
 * @author  Michael Heuer
 * @version $Revision: 1.4 $ $Date: 2003/12/02 03:41:15 $
 */
public final class Evidence
    implements Comparable<Evidence>
{
    /** Evidence name. */
    private final String name;

    /** Evidence quantitative score. */
    private final double score;

    /** Evidence confidence. */
    private final double confidence;

    /** Hash code prime. */
    private static final int HASH_CODE_PRIME = 17;

    /** Hash code prime factor. */
    private static final int HASH_CODE_PRIME_FACTOR = 37;

    /** Mask. */
    private static final int MASK = 32;


    /**
     * Create a new evidence with the specified name,
     * quantitative score, and confidence.
     *
     * @param name evidence name
     * @param score quantitative score
     * @param confidence confidence
     */
    public Evidence(final String name,
                    final double score,
                    final double confidence)
    {
        this.name = name;
        this.score = score;
        this.confidence = confidence;
    }


    /**
     * Return the name of this evidence.
     *
     * @return the name of this evidence
     */
    public String getName()
    {
        return name;
    }

    /**
     * Return a quantitative score reflecting the strength of this evidence.
     *
     * @return a quantitative score reflecting the strength of this evidence
     */
    public double getScore()
    {
        return score;
    }

    /**
     * Return a measure of confidence for the quantitative score, a p-value for instance.
     *
     * @return a measure of confidence for the quantitative score
     */
    public double getConfidence()
    {
        return confidence;
    }

    /** {@inheritDoc} */
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof Evidence))
        {
            return false;
        }

        final Evidence e = (Evidence) o;

        return (name == null ? e.getName() == null : name.equals(e.getName()))
            && (Double.doubleToLongBits(score) == Double.doubleToLongBits(e.getScore()))
            && (Double.doubleToLongBits(confidence) == Double.doubleToLongBits(e.getConfidence()));
    }

    /** {@inheritDoc} */
    public int hashCode()
    {
        int result = HASH_CODE_PRIME;
        result = HASH_CODE_PRIME_FACTOR * result + ((name == null) ? 0 : name.hashCode());
        long scoreAsLong = Double.doubleToLongBits(score);
        result = HASH_CODE_PRIME_FACTOR * result + (int) (scoreAsLong ^ (scoreAsLong >>> MASK));
        long confidenceAsLong = Double.doubleToLongBits(confidence);
        result = HASH_CODE_PRIME_FACTOR * result + (int) (confidenceAsLong ^ (confidenceAsLong >>> MASK));
        return result;
    }

    /** {@inheritDoc} */
    public int compareTo(final Evidence evidence)
    {
        // compare scores
        if (score < evidence.getScore())
        {
            return 1;
        }
        if (score > evidence.getScore())
        {
            return -1;
        }

        // scores are equal, compare confidence
        if (confidence < evidence.getConfidence())
        {
            return  1;
        }
        if (confidence > evidence.getConfidence())
        {
            return -1;
        }

        // scores and confidence are equal, compare name
        if ((name == null) && (evidence.getName() == null))
        {
            return 0;
        }
        if ((name == null) && (evidence.getName() != null))
        {
            return 1;
        }
        if ((name != null) && (evidence.getName() == null))
        {
            return -1;
        }

        return (name.compareTo(evidence.getName()));
    }

    /** {@inheritDoc} */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("evidence (name=");
        sb.append(name);
        sb.append(" score=");
        sb.append(score);
        sb.append(" confidence=");
        sb.append(confidence);
        sb.append(")\n");
        return sb.toString();
    }
}
