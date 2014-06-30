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

import java.io.File;
import java.io.InputStreamReader;
import java.io.IOException;

import java.security.GeneralSecurityException;

import java.util.List;

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


    /**
     * Create a new Google Genomics API factory.
     */
    public GoogleGenomicsFactory()
    {
        jsonFactory = createJsonFactory();
        clientSecrets = createGoogleClientSecrets(jsonFactory, CLIENT_SECRETS);
        httpTransport = createNetHttpTransport();
        dataStoreFactory = createFileDataStoreFactory(DATA_STORE_DIR);
    }



    /**
     * Start a new Google Genomics API authorization code flow.
     *
     * @return a new Google Genomics API authorization code flow
     * @throws IOException if an I/O error occurs
     */
    public GoogleAuthorizationCodeFlow startFlow() throws IOException
    {
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
        return googleAuthorizationCodeFlow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI).build();
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
        TokenResponse tokenResponse = googleAuthorizationCodeFlow.newTokenRequest(authorizationCode).setRedirectUri(REDIRECT_URI).execute();
        final Credential credential = googleAuthorizationCodeFlow.createAndStoreCredential(tokenResponse, "user");
        return new Genomics.Builder(httpTransport, jsonFactory, credential)
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
            throw new RuntimeException("unable to create HTTP transport", e);
        }
        catch (IOException e)
        {
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
            throw new RuntimeException("unable to create data store factory", e);
        }
    }
}
