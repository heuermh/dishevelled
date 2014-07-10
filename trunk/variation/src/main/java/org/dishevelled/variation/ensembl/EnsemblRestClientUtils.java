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
package org.dishevelled.variation.ensembl;

import static com.google.common.base.Preconditions.checkNotNull;

import com.github.heuermh.ensemblrestclient.EnsemblRestClientException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ensembl REST client utility methods.
 *
 * @author  Michael Heuer
 */
final class EnsemblRestClientUtils
{
    /** Throttle delay. */
    static final long THROTTLE_DELAY = 400L;

    /** Maximum number of retries, <code>10</code>. */
    static final int MAX_RETRIES = 10;

    /** Logger. */
    private static final Logger logger = LoggerFactory.getLogger(EnsemblRestClientUtils.class);


    /**
     * Private no-arg constructor.
     */
    private EnsemblRestClientUtils()
    {
        // empty
    }


    /**
     * Remote call.
     *
     * @param <T> return type
     */
    interface Remote<T>
    {
        /**
         * Remote call.
         *
         * @return value
         * @throws EnsemblRestClientException if an error occurs
         */
        T remote() throws EnsemblRestClientException;
    }


    /**
     * Throttle.
     */
    static void throttle()
    {
        try
        {
            Thread.sleep(THROTTLE_DELAY);
        }
        catch (InterruptedException e)
        {
            // ignore
        }
    }

    /**
     * Retry the specified remote call if a HTTP 429 error is received up until the maximum number of retries.
     *
     * @param <T> return type
     * @param remote remote call, must not be null
     * @throws EnsemblRestClientException if an error occurs
     */
    static <T> T retryIfNecessary(final Remote<T> remote) throws EnsemblRestClientException
    {
        checkNotNull(remote);

        int retries = 0;
        while (true)
        {
            try
            {
                return remote.remote();
            }
            catch (EnsemblRestClientException e)
            {
                if (!e.isRateLimitError())
                {
                    throw e;
                }
                retries++;
                if (retries > MAX_RETRIES)
                {
                    if (logger.isWarnEnabled())
                    {
                        logger.warn("unable to remote call, too many retries ({})", retries);
                    }
                    break;
                }
                if (logger.isWarnEnabled())
                {
                    logger.warn("unable to remote call, rec'd {} {}, will retry in {} sec", e.getStatus(), e.getReason(), e.getRateLimitReset());
                }
                try
                {
                    Thread.sleep(e.getRateLimitReset() * 1000L);
                }
                catch (InterruptedException ie)
                {
                    // ignore
                }
            }
        }
        return null;
    }
}