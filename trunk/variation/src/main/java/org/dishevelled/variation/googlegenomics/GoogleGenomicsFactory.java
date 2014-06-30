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
package org.dishevelled.variation.googlegenomics;

import static com.google.common.base.Preconditions;

import java.io.File;
import java.io.InputStreamReader;
import java.io.IOException;

import java.security.GeneralSecurityException;

import java.util.List;
import java.util.Objects;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import com.google.common.collect.ImmutableList;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;

import com.google.api.client.http.javanet.NetHttpTransport;

import com.google.api.client.json.JsonFactory;

import com.google.api.client.json.jackson2.JacksonFactory;

import com.google.api.client.util.store.FileDataStoreFactory;

import com.google.api.services.genomics.Genomics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory methods for the Google Genomics API.
 *
 * @author  Michael Heuer
 */
public final class GoogleGenomicsFactory
{
    /** Application name. */
    private static final String APPLICATION_NAME = "Variation/1.0-SNAPSHOT";

    /** Data store directory. */
    private static final File DATA_STORE_DIR = new File(System.getProperty("user.home"), ".variation/data-store");

    /** Client secrets resource name. */
    private static final String CLIENT_SECRETS = "client_secrets.json";

    /** Genomics scope. */
    private static final String GENOMICS_SCOPE = "https://www.googleapis.com/auth/genomics.readonly";

    /** List of scopes. */
    private static final List<String> SCOPES = ImmutableList.of(GENOMICS_SCOPE);

    /** Redirect URI. */
    private static final String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob"; // also com.google.api.client.googleapis.auth.oauth2.GoogleOAuthConstants.OOB_REDIRECT_URI

    /** JSON factory. */
    private final JsonFactory jsonFactory;

    /** Client secrets. */
    private final GoogleClientSecrets clientSecrets;

    /** HTTP transport. */
    private final NetHttpTransport httpTransport;

    /** Data store factory. */
    private final FileDataStoreFactory dataStoreFactory;

    /** Cache of genomics APIs keyed by root URL, authorization code, and authorization code flow. */
    private final LoadingCache<GenomicsKey, Genomics> cache = CacheBuilder.newBuilder()
        .expireAfterWrite(4000, TimeUnit.SECONDS)
        .build(new CacheLoader<GenomicsKey, Genomics>()
               {
                   @Override
                   public Genomics load(final GenomicsKey genomicsKey) throws IOException
                   {
                       return createGenomics(genomicsKey);
                   }
               });

    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(GoogleGenomicsFactory.class);


    /**
     * Create a new Google Genomics API factory.
     */
    public GoogleGenomicsFactory()
    {
        jsonFactory = createJsonFactory();
        clientSecrets = createGoogleClientSecrets(jsonFactory, CLIENT_SECRETS);
        httpTransport = createNetHttpTransport();
        dataStoreFactory = createFileDataStoreFactory(DATA_STORE_DIR);

        if (logger.isInfoEnabled())
        {
            logger.info("created google genomics factory with client id {}", clientSecrets.getDetails().getClientId());
        }
    }



    /**
     * Start a new Google Genomics API authorization code flow.
     *
     * @return a new Google Genomics API authorization code flow
     * @throws IOException if an I/O error occurs
     */
    public GoogleAuthorizationCodeFlow startFlow() throws IOException
    {
        if (logger.isInfoEnabled())
        {
            logger.info("starting new google genomics authorization code flow");
        }
        return new GoogleAuthorizationCodeFlow.Builder(httpTransport, jsonFactory, clientSecrets, SCOPES)
            .setDataStoreFactory(dataStoreFactory).build();
    }

    /**
     * Return the authorization URL for the specified Google Genomics API authorization code flow.
     *
     * @param googleAuthorizationCodeFlow Google Genomics API authorization code flow, must not be null
     * @return the authorization URL for the specified Google Genomics API authorization code flow
     */
    public String authorizationUrl(final GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow)
    {
        checkNotNull(googleAuthorizationCodeFlow);
        String authorizationUrl = googleAuthorizationCodeFlow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI).build();
        if (logger.isInfoEnabled())
        {
            logger.info("created new google genomics authorization url {}", authorizationUrl);
        }
        return authorizationUrl;
    }

    /**
     * Create and return a new Google Genomics API for the specified root URL, authorization code, and authorization code flow.
     *
     * @param rootUrl root URL, must not be null
     * @param authorizationCode authorization code, must not be null
     * @param googleAuthorizationCodeFlow Google Genomics API authorization code flow, must not be null
     * @throws IOException if an I/O error occurs
     */
    public Genomics genomics(final String rootUrl,
                             final String authorizationCode,
                             final GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow) throws IOException
    {
        checkNotNull(rootUrl);
        checkNotNull(authorizationCode);
        checkNotNull(googleAuthorizationCodeFlow);
        return cache.getUnchecked(new GenomicsKey(rootUrl, authorizationCode, googleAuthorizationCodeFlow));
    }

    Genomics createGenomics(final GenomicsKey genomicsKey) throws IOException
    {
        final String rootUrl = genomicsKey.rootUrl();
        final String authorizationCode = genomicsKey.authorizationCode();
        final GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow = genomicsKey.googleAuthorizationCodeFlow();
        if (logger.isInfoEnabled())
        {
            logger.info("creating new google genomics api for root url {} authorization code {}", rootUrl, authorizationCode.substring(0, 8) + "...");
        }
        TokenResponse tokenResponse = googleAuthorizationCodeFlow.newTokenRequest(authorizationCode).setRedirectUri(REDIRECT_URI).execute();
        if (logger.isInfoEnabled())
        {
            logger.info("received token response {}", tokenResponse.getAccessToken() == null ? "null" : tokenResponse.getAccessToken().substring(0, 8) + "...");
        }
        final Credential credential = googleAuthorizationCodeFlow.createAndStoreCredential(tokenResponse, "user");
        if (logger.isInfoEnabled())
        {
            logger.info("received credential {} expires in {} s", credential.getAccessToken() == null ? "null" : credential.getAccessToken().substring(0, 8) + "...", credential.getExpiresInSeconds());
        }
        Genomics genomics = new Genomics.Builder(httpTransport, jsonFactory, credential)
            .setApplicationName(APPLICATION_NAME)
            .setRootUrl(rootUrl)
            .setServicePath("/")
            .setHttpRequestInitializer(new HttpRequestInitializer()
                {
                    @Override
                    public void initialize(final HttpRequest httpRequest) throws IOException {
                        credential.initialize(httpRequest);
                        httpRequest.setReadTimeout(60000); // 60 seconds                                                                                            
                    }
                }).build();

        if (logger.isInfoEnabled())
        {
            logger.info("created new google genomics api for root URL {} authorization code {} application name {}", rootUrl, authorizationCode.substring(0, 8) + "...", genomics.getApplicationName() == null ? "null" : genomics.getApplicationName());
        }
        return genomics;
    }


    // could be guice providers I suppose . . .

    JsonFactory createJsonFactory()
    {
        return JacksonFactory.getDefaultInstance();
    }

    GoogleClientSecrets createGoogleClientSecrets(final JsonFactory jsonFactory, final String resourceName)
    {
        try
        {
            return GoogleClientSecrets.load(jsonFactory, new InputStreamReader(getClass().getResourceAsStream(resourceName)));
        }
        catch (IOException e)
        {
            logger.error("unable to load client secrets", e);
            throw new RuntimeException("unable to load client secrets", e);
        }
    }

    NetHttpTransport createNetHttpTransport()
    {
        try
        {
            return GoogleNetHttpTransport.newTrustedTransport();
        }
        catch (GeneralSecurityException e)
        {
            logger.error("unable to create HTTP transport", e);
            throw new RuntimeException("unable to create HTTP transport", e);
        }
        catch (IOException e)
        {
            logger.error("unable to create HTTP transport", e);
            throw new RuntimeException("unable to create HTTP transport", e);
        }
    }

    FileDataStoreFactory createFileDataStoreFactory(final File dataStore)
    {
        try
        {
            return new FileDataStoreFactory(dataStore);
        }
        catch (IOException e)
        {
            logger.error("unable to create data store factory", e);
            throw new RuntimeException("unable to create data store factory", e);
        }
    }

    /**
     * Compound key of root URL, authorization code, and authorization code flow.
     */
    private static class GenomicsKey
    {
        private final String rootUrl;
        private final String authorizationCode;
        private final GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow;
        private final int hashCode;

        private GenomicsKey(final String rootUrl,
                            final String authorizationCode,
                            final GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow)
        {
            this.rootUrl = rootUrl;
            this.authorizationCode = authorizationCode;
            this.googleAuthorizationCodeFlow = googleAuthorizationCodeFlow;
            hashCode = Objects.hash(rootUrl, authorizationCode, googleAuthorizationCodeFlow);
        }


        private String rootUrl()
        {
            return rootUrl;
        }

        private String authorizationCode()
        {
            return authorizationCode;
        }

        private GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow()
        {
            return googleAuthorizationCodeFlow;
        }

        @Override
        public boolean equals(final Object o)
        {
            if (o == this)
            {
                return true;
            }
            if (!(o instanceof GenomicsKey))
            {
                return false;
            }
            GenomicsKey genomicsKey = (GenomicsKey) o;
            return Objects.equals(rootUrl, genomicsKey.rootUrl)
                && Objects.equals(authorizationCode, genomicsKey.authorizationCode)
                && Objects.equals(googleAuthorizationCodeFlow, googleAuthorizationCodeFlow);
        }


        @Override
        public int hashCode()
        {
            return hashCode;
        }
    }
}
