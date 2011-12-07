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

import javax.ws.rs.DELETE;

import org.jclouds.abiquo.binders.BindToPath;
import org.jclouds.abiquo.features.AdminAsyncClient;
import org.jclouds.abiquo.features.CloudAsyncClient;
import org.jclouds.abiquo.features.ConfigAsyncClient;
import org.jclouds.abiquo.features.EnterpriseAsyncClient;
import org.jclouds.abiquo.features.InfrastructureAsyncClient;
import org.jclouds.abiquo.features.VirtualMachineTemplateAsyncClient;
import org.jclouds.abiquo.rest.annotations.EndpointLink;
import org.jclouds.rest.annotations.BinderParam;
import org.jclouds.rest.annotations.Delegate;

import com.abiquo.model.transport.SingleResourceTransportDto;
import com.google.common.util.concurrent.ListenableFuture;

/**
 * Provides asynchronous access to Abiquo via their REST API.
 * 
 * @see http://community.abiquo.com/display/ABI20/API+Reference
 * @see InfrastructureAsyncClient
 * @author Ignasi Barrera
 */
public interface AbiquoAsyncClient
{
    public static final String API_VERSION = "2.0-SNAPSHOT";

    /**
     * Provides asynchronous access to Admin features.
     */
    @Delegate
    AdminAsyncClient getAdminClient();

    /**
     * Provides asynchronous access to Infrastructure features.
     */
    @Delegate
    InfrastructureAsyncClient getInfrastructureClient();

    /**
     * Provides asynchronous access to Cloud features.
     */
    @Delegate
    CloudAsyncClient getCloudClient();

    /**
     * Provides asynchronous access to Apps library features.
     */
    @Delegate
    VirtualMachineTemplateAsyncClient getVirtualMachineTemplateClient();

    /**
     * Provides asynchronous access to Enterprise features.
     */
    @Delegate
    EnterpriseAsyncClient getEnterpriseClient();

    /**
     * Provides asynchronous access to configuration features.
     */
    @Delegate
    ConfigAsyncClient getConfigClient();

    /**
     * @see AbiquoClient#getResource(SingleResourceTransportDto)
     */
    @DELETE
    ListenableFuture<Void> getResource(
        @EndpointLink("edit") @BinderParam(BindToPath.class) SingleResourceTransportDto resource);
}
