/**
 * Licensed to jclouds, Inc. (jclouds) under one or more
 * contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  jclouds licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.jclouds.abiquo.http.filters;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.jclouds.Constants.PROPERTY_CREDENTIAL;
import static org.jclouds.Constants.PROPERTY_IDENTITY;

import java.io.UnsupportedEncodingException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.core.HttpHeaders;

import org.jclouds.crypto.Crypto;
import org.jclouds.crypto.CryptoStreams;
import org.jclouds.http.HttpException;
import org.jclouds.http.HttpRequest;
import org.jclouds.http.HttpRequestFilter;
import org.jclouds.http.utils.ModifyRequest;

import com.google.common.annotations.VisibleForTesting;

/**
 * Authenticates using Basic Authentication or a generated token from previous API sessions.
 * 
 * @author Ignasi Barrera
 */
@Singleton
public class AbiquoAuthentication implements HttpRequestFilter
{
    /** The authentication string. */
    private final String header;

    /** Boolean indicating if basic authentication must be used. */
    private boolean isBasicAuth = false;

    @Inject
    public AbiquoAuthentication(@Named(PROPERTY_IDENTITY) String identityOrToken,
        @Named(PROPERTY_CREDENTIAL) String credential, Crypto crypto)
        throws UnsupportedEncodingException
    {
        isBasicAuth = (credential != null);
        this.header =
            isBasicAuth ? basicAuth(identityOrToken, credential) : tokenAuth(identityOrToken);
    }

    @Override
    public HttpRequest filter(HttpRequest request) throws HttpException
    {
        return ModifyRequest.replaceHeader(request, isBasicAuth ? HttpHeaders.AUTHORIZATION
            : HttpHeaders.COOKIE, header);
    }

    @VisibleForTesting
    String basicAuth(String user, String password) throws UnsupportedEncodingException
    {
        return "Basic "
            + CryptoStreams.base64(String.format("%s:%s", checkNotNull(user, "user"),
                checkNotNull(password, "password")).getBytes("UTF-8"));
    }

    @VisibleForTesting
    String tokenAuth(String token)
    {
        return "auth=" + checkNotNull(token, "token");
    }
}
