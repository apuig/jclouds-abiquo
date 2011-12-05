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

import org.jclouds.abiquo.domain.cloud.options.VirtualApplianceOptions;
import org.jclouds.abiquo.domain.cloud.options.VirtualDatacenterOptions;
import org.jclouds.concurrent.Timeout;

import com.abiquo.model.rest.RESTLink;
import com.abiquo.model.transport.AcceptedRequestDto;
import com.abiquo.server.core.cloud.VirtualApplianceDto;
import com.abiquo.server.core.cloud.VirtualAppliancesDto;
import com.abiquo.server.core.cloud.VirtualDatacenterDto;
import com.abiquo.server.core.cloud.VirtualDatacentersDto;
import com.abiquo.server.core.cloud.VirtualMachineDto;
import com.abiquo.server.core.cloud.VirtualMachineStateDto;
import com.abiquo.server.core.cloud.VirtualMachinesDto;

/**
 * Provides synchronous access to Abiquo Cloud API.
 * 
 * @see http://community.abiquo.com/display/ABI20/API+Reference
 * @see CloudAsyncClient
 * @author Ignasi Barrera
 * @author Francesc Montserrat
 */
/**
 * @author Francesc Montserrat
 *
 */
/**
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
    VirtualDatacentersDto listVirtualDatacenters(VirtualDatacenterOptions options);

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

    /*********************** Virtual Appliance ***********************/

    /**
     * List all virtual appliance for a virtual datacenter.
     * 
     * @param virtualDatacenter The virtual datacenter.
     * @return The list of virtual appliances for the virtual datacenter.
     */
    VirtualAppliancesDto listVirtualAppliances(VirtualDatacenterDto virtualDatacenter);

    /**
     * Get the given virtual appliance from the given virtual datacenter.
     * 
     * @param virtualDatacenter The virtual datacenter.
     * @param virtualApplianceId The id of the virtual appliance.
     * @return The virtual appliance or <code>null</code> if it does not exist.
     */
    VirtualApplianceDto getVirtualAppliance(VirtualDatacenterDto virtualDatacenter,
        Integer virtualApplianceId);

    /**
     * Create a new virtual appliance in a virtual datacenter.
     * 
     * @param virtualDatacenter The virtual datacenter.
     * @param virtualAppliance The virtual appliance to be created.
     * @return The created virtual appliance.
     */
    VirtualApplianceDto createVirtualAppliance(VirtualDatacenterDto virtualDatacenter,
        VirtualApplianceDto virtualAppliance);

    /**
     * Updates an existing virtual appliance from the given virtual datacenter.
     * 
     * @param virtualAppliance The new attributes for the virtual appliance.
     * @return The updated virtual appliance.
     */
    VirtualApplianceDto updateVirtualAppliance(VirtualApplianceDto virtualAppliance);

    /**
     * Deletes an existing virtual appliance.
     * 
     * @param virtualAppliance The virtual appliance to delete.
     */
    void deleteVirtualAppliance(VirtualApplianceDto virtualAppliance);

    /**
     * Deletes an existing virtual appliance.
     * 
     * @param virtualAppliance The virtual appliance to delete.
     * @param options The options to customize the delete operation (e.g. Force delete).
     */
    void deleteVirtualAppliance(VirtualApplianceDto virtualAppliance,
        VirtualApplianceOptions options);

    /**
     * Deploy/Undeploy a virtual appliance.
     * 
     * @param link The link of the deploy/undeploy action.
     * @return Response message to the deploy request.
     */
    AcceptedRequestDto<String> actionVirtualAppliance(final RESTLink link);

    /**
     * Get the given virtual appliance from the virtual appliance link.
     * 
     * @param link Link to the virtual appliance.
     * @return The virtual appliance or <code>null</code> if it does not exist.
     */
    VirtualApplianceDto getVirtualAppliance(RESTLink link);

    /*********************** Virtual Machine ***********************/

    /**
     * List all virtual machines for a virtual appliance.
     * 
     * @param virtualAppliance The virtual appliance.
     * @return The list of virtual machines for the virtual appliance.
     */
    VirtualMachinesDto listVirtualMachines(VirtualApplianceDto virtualAppliance);

    /**
     * Get the given virtual machine from the given virtual machine.
     * 
     * @param virtualAppliance The virtual appliance.
     * @param virtualMachineId The id of the virtual machine.
     * @return The virtual machine or <code>null</code> if it does not exist.
     */
    VirtualMachineDto getVirtualMachine(VirtualApplianceDto virtualAppliance,
        Integer virtualMachineId);

    /**
     * Create a new virtual machine in a virtual appliance.
     * 
     * @param virtualAppliance The virtual appliance.
     * @param virtualMachine The virtual machine to be created.
     * @return The created virtual machine.
     */
    VirtualMachineDto createVirtualMachine(VirtualApplianceDto virtualAppliance,
        VirtualMachineDto virtualMachine);

    /**
     * Deletes an existing virtual machine.
     * 
     * @param virtualMachine The virtual machine to delete.
     */
    void deleteVirtualMachine(VirtualMachineDto virtualMachine);

    /**
     * Updates an existing virtual machine from the given virtual appliance.
     * 
     * @param virtualMachine The new attributes for the virtual machine.
     * @return The updated virtual machine.
     */
    VirtualMachineDto updateVirtualMachine(VirtualMachineDto virtualMachine);

    /**
     * Changes the state an existing virtual machine.
     * 
     * @param virtualMachine The given virtual machine.
     * @param state The new state.
     * @return Response message to the state change request.
     */
    AcceptedRequestDto changeVirtualMachineState(VirtualMachineDto virtualMachine,
        VirtualMachineStateDto state);

    /**
     * Get the state of the given virtual machine.
     * 
     * @param virtualMachine The given virtual machine.
     * @return The state of the given virtual machine.
     */
    VirtualMachineStateDto getVirtualMachineState(VirtualMachineDto virtualMachine);
}
