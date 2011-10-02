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

package org.jclouds.abiquo;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.jclouds.abiquo.functions.ParseDatacenters;
import org.jclouds.http.filters.BasicAuthentication;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.ResponseParser;

import com.abiquo.server.core.infrastructure.DatacentersDto;
import com.google.common.util.concurrent.ListenableFuture;

/**
 * Provides asynchronous access to Abiquo via their REST API.
 * <p/>
 * 
 * @see AbiquoClient
 * @author Ignasi Barrera
 */
@RequestFilters(BasicAuthentication.class)
@Consumes(MediaType.APPLICATION_XML)
public interface AbiquoAsyncClient
{
    public static final String API_VERSION = "2.0-SNAPSHOT";

    /**
     * @see AbiquoClient#listDatacenters()
     */
    @GET
    @Path("/admin/datacenters")
    @ResponseParser(ParseDatacenters.class)
    ListenableFuture<DatacentersDto> listDatacenters();

}
