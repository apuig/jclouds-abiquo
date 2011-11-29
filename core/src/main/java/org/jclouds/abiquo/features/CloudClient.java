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

package org.jclouds.abiquo.features;

import java.util.concurrent.TimeUnit;

import org.jclouds.abiquo.domain.cloud.options.VirtualDatacenterOptions;
import org.jclouds.concurrent.Timeout;

import com.abiquo.server.core.cloud.VirtualDatacenterDto;
import com.abiquo.server.core.cloud.VirtualDatacentersDto;

/**
 * Provides synchronous access to Abiquo Cloud API.
 * 
 * @see http://community.abiquo.com/display/ABI20/API+Reference
 * @see CloudAsyncClient
 * @author Ignasi Barrera
 * @author Francesc Montserrat
 */
@Timeout(duration = 30, timeUnit = TimeUnit.SECONDS)
public interface CloudClient
{
    /*********************** Virtual Datacenter ***********************/

    /**
     * List all virtual datacenters.
     * 
     * @param options Optional query params.
     * @return The list of Datacenters.
     */
    VirtualDatacentersDto listDatacenters(VirtualDatacenterOptions options);

    /**
     * Get the given virtual datacenter.
     * 
     * @param virtualDatacenterId The id of the virtual datacenter.
     * @return The virtual datacenter or <code>null</code> if it does not exist.
     */
    VirtualDatacenterDto getVirtualDatacenter(Integer virtualDatacenterId);

    /**
     * Create a new virtual datacenter.
     * 
     * @param virtualDatacenter The virtual datacenter to be created.
     * @param datacenterId Datacenter where the virtualdatacenter will be deployed.
     * @param datacenterId Enterprise of the virtual datacenter.
     * @return The created virtual datacenter.
     */
    VirtualDatacenterDto createVirtualDatacenter(VirtualDatacenterDto virtualDatacenter,
        Integer datacenterId, Integer enterpriseId);

    /**
     * Updates an existing virtual datacenter.
     * 
     * @param virtualDatacenter The new attributes for the virtual datacenter.
     * @return The updated virtual datacenter.
     */
    VirtualDatacenterDto updateVirtualDatacenter(VirtualDatacenterDto virtualDatacenter);

    /**
     * Deletes an existing virtual datacenter.
     * 
     * @param virtualDatacenter The virtual datacenter to delete.
     */
    void deleteVirtualDatacenter(VirtualDatacenterDto virtualDatacenter);
}
