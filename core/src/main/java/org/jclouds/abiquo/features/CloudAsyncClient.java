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

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jclouds.abiquo.binders.AppendOptionsToPath;
import org.jclouds.abiquo.binders.AppendToPath;
import org.jclouds.abiquo.binders.BindToPath;
import org.jclouds.abiquo.binders.BindToXMLPayloadAndPath;
import org.jclouds.abiquo.binders.cloud.BindHardDiskRefsToPayload;
import org.jclouds.abiquo.binders.cloud.BindIpRefToPayload;
import org.jclouds.abiquo.binders.cloud.BindIpRefsToPayload;
import org.jclouds.abiquo.binders.cloud.BindMoveVolumeToPath;
import org.jclouds.abiquo.binders.cloud.BindNetworkConfigurationRefToPayload;
import org.jclouds.abiquo.binders.cloud.BindNetworkRefToPayload;
import org.jclouds.abiquo.binders.cloud.BindUnmanagedIpRefToPayload;
import org.jclouds.abiquo.binders.cloud.BindVirtualDatacenterRefToPayload;
import org.jclouds.abiquo.binders.cloud.BindVolumeRefsToPayload;
import org.jclouds.abiquo.domain.cloud.VirtualDatacenter;
import org.jclouds.abiquo.domain.cloud.options.VirtualApplianceOptions;
import org.jclouds.abiquo.domain.cloud.options.VirtualDatacenterOptions;
import org.jclouds.abiquo.domain.cloud.options.VolumeOptions;
import org.jclouds.abiquo.domain.enterprise.Enterprise;
import org.jclouds.abiquo.domain.infrastructure.Datacenter;
import org.jclouds.abiquo.domain.network.options.IpOptions;
import org.jclouds.abiquo.functions.ReturnTaskReferenceOrNull;
import org.jclouds.abiquo.functions.cloud.ReturnMovedVolume;
import org.jclouds.abiquo.functions.enterprise.ParseEnterpriseId;
import org.jclouds.abiquo.functions.infrastructure.ParseDatacenterId;
import org.jclouds.abiquo.http.filters.AbiquoAuthentication;
import org.jclouds.abiquo.reference.annotations.EnterpriseEdition;
import org.jclouds.abiquo.rest.annotations.EndpointLink;
import org.jclouds.rest.annotations.BinderParam;
import org.jclouds.rest.annotations.ExceptionParser;
import org.jclouds.rest.annotations.ParamParser;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.ResponseParser;
import org.jclouds.rest.binders.BindToXMLPayload;
import org.jclouds.rest.functions.ReturnNullOnNotFoundOr404;

import com.abiquo.model.transport.AcceptedRequestDto;
import com.abiquo.server.core.appslibrary.VirtualMachineTemplateDto;
import com.abiquo.server.core.cloud.VirtualApplianceDto;
import com.abiquo.server.core.cloud.VirtualApplianceStateDto;
import com.abiquo.server.core.cloud.VirtualAppliancesDto;
import com.abiquo.server.core.cloud.VirtualDatacenterDto;
import com.abiquo.server.core.cloud.VirtualDatacentersDto;
import com.abiquo.server.core.cloud.VirtualMachineDto;
import com.abiquo.server.core.cloud.VirtualMachineStateDto;
import com.abiquo.server.core.cloud.VirtualMachineTaskDto;
import com.abiquo.server.core.cloud.VirtualMachinesDto;
import com.abiquo.server.core.enterprise.EnterpriseDto;
import com.abiquo.server.core.infrastructure.DatacenterDto;
import com.abiquo.server.core.infrastructure.network.IpPoolManagementDto;
import com.abiquo.server.core.infrastructure.network.IpsPoolManagementDto;
import com.abiquo.server.core.infrastructure.network.NicDto;
import com.abiquo.server.core.infrastructure.network.NicsDto;
import com.abiquo.server.core.infrastructure.network.VLANNetworkDto;
import com.abiquo.server.core.infrastructure.network.VLANNetworksDto;
import com.abiquo.server.core.infrastructure.network.VMNetworkConfigurationDto;
import com.abiquo.server.core.infrastructure.network.VMNetworkConfigurationsDto;
import com.abiquo.server.core.infrastructure.storage.DiskManagementDto;
import com.abiquo.server.core.infrastructure.storage.DisksManagementDto;
import com.abiquo.server.core.infrastructure.storage.TiersDto;
import com.abiquo.server.core.infrastructure.storage.VolumeManagementDto;
import com.abiquo.server.core.infrastructure.storage.VolumesManagementDto;
import com.google.common.util.concurrent.ListenableFuture;

/**
 * Provides asynchronous access to Abiquo Cloud API.
 * 
 * @see http://community.abiquo.com/display/ABI20/API+Reference
 * @see CloudClient
 * @author Ignasi Barrera
 * @author Francesc Montserrat
 */
@RequestFilters(AbiquoAuthentication.class)
@Consumes(MediaType.APPLICATION_XML)
@Path("/cloud")
public interface CloudAsyncClient
{
    /*********************** Virtual Datacenter ***********************/

    /**
     * @see CloudClient#listVirtualDatacenters(VirtualDatacenterOptions)
     */
    @GET
    @Path("/virtualdatacenters")
    ListenableFuture<VirtualDatacentersDto> listVirtualDatacenters(
        @BinderParam(AppendOptionsToPath.class) VirtualDatacenterOptions options);

    /**
     * @see CloudClient#getVirtualDatacenter(Integer)
     */
    @GET
    @Path("/virtualdatacenters/{virtualdatacenter}")
    @ExceptionParser(ReturnNullOnNotFoundOr404.class)
    ListenableFuture<VirtualDatacenterDto> getVirtualDatacenter(
        @PathParam("virtualdatacenter") Integer virtualDatacenterId);

    /**
     * @see CloudClient#createVirtualDatacenter(VirtualDatacenterDto, Datacenter, Enterprise)
     */
    @POST
    @Path("/virtualdatacenters")
    ListenableFuture<VirtualDatacenterDto> createVirtualDatacenter(
        @BinderParam(BindToXMLPayload.class) final VirtualDatacenterDto virtualDatacenter,
        @QueryParam("datacenter") @ParamParser(ParseDatacenterId.class) final DatacenterDto datacenter,
        @QueryParam("enterprise") @ParamParser(ParseEnterpriseId.class) final EnterpriseDto enterprise);

    /**
     * @see CloudClient#updateVirtualDatacenter(VirtualDatacenterDto)
     */
    @PUT
    ListenableFuture<VirtualDatacenterDto> updateVirtualDatacenter(
        @EndpointLink("edit") @BinderParam(BindToXMLPayloadAndPath.class) VirtualDatacenterDto virtualDatacenter);

    /**
     * @see CloudClient#deleteVirtualDatacenter(VirtualDatacenterDto)
     */
    @DELETE
    ListenableFuture<Void> deleteVirtualDatacenter(
        @EndpointLink("edit") @BinderParam(BindToPath.class) VirtualDatacenterDto virtualDatacenter);

    /**
     * @see CloudClient#listStorageTiers(VirtualDatacenterDto)
     */
    @EnterpriseEdition
    @GET
    ListenableFuture<TiersDto> listStorageTiers(
        @EndpointLink("tiers") @BinderParam(BindToPath.class) VirtualDatacenterDto virtualDatacenter);

    /**
     * @see CloudClient#getStorageTier(VirtualDatacenterDto, Integer)
     */
    @EnterpriseEdition
    @GET
    @ExceptionParser(ReturnNullOnNotFoundOr404.class)
    ListenableFuture<TiersDto> getStorageTier(
        @EndpointLink("tiers") @BinderParam(BindToPath.class) VirtualDatacenterDto virtualDatacenter,
        @BinderParam(AppendToPath.class) Integer tierId);

    /*********************** Public IP ***********************/

    /**
     * @see CloudClient#listAvailablePublicIps(VirtualDatacenterDto, IpOptions)
     */
    @GET
    ListenableFuture<IpsPoolManagementDto> listAvailablePublicIps(
        @EndpointLink("topurchase") @BinderParam(BindToPath.class) VirtualDatacenterDto virtualDatacenter,
        @BinderParam(AppendOptionsToPath.class) IpOptions options);

    /**
     * @see CloudClient#listPurchasedPublicIps(VirtualDatacenterDto, IpOptions)
     */
    @GET
    ListenableFuture<IpsPoolManagementDto> listPurchasedPublicIps(
        @EndpointLink("purchased") @BinderParam(BindToPath.class) VirtualDatacenterDto virtualDatacenter,
        @BinderParam(AppendOptionsToPath.class) IpOptions options);

    /**
     * @see CloudClient#purchasePublicIp(IpsPoolManagementDto)
     */
    @PUT
    ListenableFuture<IpPoolManagementDto> purchasePublicIp(
        @EndpointLink("purchase") @BinderParam(BindToPath.class) IpPoolManagementDto publicIp);

    /**
     * @see CloudClient#releasePublicIp(IpsPoolManagementDto)
     */
    @PUT
    ListenableFuture<IpPoolManagementDto> releasePublicIp(
        @EndpointLink("release") @BinderParam(BindToPath.class) IpPoolManagementDto publicIp);

    /*********************** Private Network ***********************/

    /**
     * @see CloudClient#listPrivateNetworks(VirtualDatacenter)
     */
    @GET
    ListenableFuture<VLANNetworksDto> listPrivateNetworks(
        @EndpointLink("privatenetworks") @BinderParam(BindToPath.class) VirtualDatacenterDto virtualDatacenter);

    /**
     * @see CloudClient#getPrivateNetwork(VirtualDatacenterDto, Integer)
     */
    @GET
    @ExceptionParser(ReturnNullOnNotFoundOr404.class)
    ListenableFuture<VLANNetworkDto> getPrivateNetwork(
        @EndpointLink("privatenetworks") @BinderParam(BindToPath.class) VirtualDatacenterDto virtualDatacenter,
        @BinderParam(AppendToPath.class) Integer privateNetworkId);

    /**
     * @see CloudClient#createPrivateNetwork(VirtualDatacenterDto, VLANNetworkDto)
     */
    @POST
    ListenableFuture<VLANNetworkDto> createPrivateNetwork(
        @EndpointLink("privatenetworks") @BinderParam(BindToPath.class) VirtualDatacenterDto virtualDatacenter,
        @BinderParam(BindToXMLPayload.class) VLANNetworkDto privateNetwork);

    /**
     * @see CloudClient#updatePrivateNetwork(VLANNetworkDto)
     */
    @PUT
    ListenableFuture<VLANNetworkDto> updatePrivateNetwork(
        @EndpointLink("edit") @BinderParam(BindToXMLPayloadAndPath.class) VLANNetworkDto privateNetwork);

    /**
     * @see CloudClient#deletePrivateNetwork(VLANNetworkDto)
     */
    @DELETE
    ListenableFuture<Void> deletePrivateNetwork(
        @EndpointLink("edit") @BinderParam(BindToPath.class) VLANNetworkDto privateNetwork);

    /**
     * @see CloudClient#getDefaultNetwork(VirtualDatacenterDto)
     */
    @GET
    ListenableFuture<VLANNetworkDto> getDefaultNetwork(
        @EndpointLink("defaultnetwork") @BinderParam(BindToPath.class) VirtualDatacenterDto virtualDatacenter);

    /**
     * @see CloudClient#setDefaultNetwork(VirtualDatacenterDto, VLANNetworkDto)
     */
    @PUT
    ListenableFuture<Void> setDefaultNetwork(
        @EndpointLink("defaultvlan") @BinderParam(BindToPath.class) VirtualDatacenterDto virtualDatacenter,
        @BinderParam(BindNetworkRefToPayload.class) VLANNetworkDto network);

    /*********************** Private Network IPs ***********************/

    /**
     * @see CloudClient#listPrivateNetworkIps(VLANNetworkDto)
     */
    @GET
    ListenableFuture<IpsPoolManagementDto> listPrivateNetworkIps(
        @EndpointLink("ips") @BinderParam(BindToPath.class) VLANNetworkDto network);

    /**
     * @see CloudClient#listPrivateNetworkIps(VLANNetworkDto, IpOptions)
     */
    @GET
    ListenableFuture<IpsPoolManagementDto> listPrivateNetworkIps(
        @EndpointLink("ips") @BinderParam(BindToPath.class) VLANNetworkDto network,
        @BinderParam(AppendOptionsToPath.class) IpOptions options);

    /*********************** Attached Nic ***********************/

    /**
     * @see CloudClient#createNic(VirtualMachineDto, IpPoolManagementDto)
     */
    @POST
    @ResponseParser(ReturnTaskReferenceOrNull.class)
    ListenableFuture<AcceptedRequestDto<String>> createNic(
        @EndpointLink("nics") @BinderParam(BindToPath.class) VirtualMachineDto virtualMachine,
        @BinderParam(BindIpRefToPayload.class) IpPoolManagementDto ip);

    /**
     * @see CloudClient#createNic(VirtualMachineDto, VLANNetworkDto)
     */
    @POST
    @ResponseParser(ReturnTaskReferenceOrNull.class)
    ListenableFuture<AcceptedRequestDto<String>> createNic(
        @EndpointLink("nics") @BinderParam(BindToPath.class) VirtualMachineDto virtualMachine,
        @BinderParam(BindUnmanagedIpRefToPayload.class) VLANNetworkDto network);

    /**
     * @see CloudClient#deleteNic(NicDto)
     */
    @DELETE
    @ResponseParser(ReturnTaskReferenceOrNull.class)
    ListenableFuture<AcceptedRequestDto<String>> deleteNic(
        @EndpointLink("edit") @BinderParam(BindToPath.class) NicDto nic);

    /**
     * @see CloudClient#replaceVolumes(VirtualMachineDto, VolumeManagementDto...)
     */
    @PUT
    @ResponseParser(ReturnTaskReferenceOrNull.class)
    ListenableFuture<AcceptedRequestDto<String>> replaceNics(
        @EndpointLink("nics") @BinderParam(BindToPath.class) VirtualMachineDto virtualMachine,
        @BinderParam(BindIpRefsToPayload.class) IpPoolManagementDto... ips);

    /**
     * @see CloudClient#listAttachedNics(VirtualMachineDto)
     */
    @GET
    ListenableFuture<NicsDto> listAttachedNics(
        @EndpointLink("nics") @BinderParam(BindToPath.class) VirtualMachineDto virtualMachine);

    /*********************** Virtual Appliance ***********************/

    /**
     * @see CloudClient#listVirtualAppliances(VirtualDatacenterDto)
     */
    @GET
    ListenableFuture<VirtualAppliancesDto> listVirtualAppliances(
        @EndpointLink("virtualappliances") @BinderParam(BindToPath.class) VirtualDatacenterDto virtualDatacenter);

    /**
     * @see CloudClient#getVirtualAppliance(VirtualDatacenterDto, Integer)
     */
    @GET
    @ExceptionParser(ReturnNullOnNotFoundOr404.class)
    ListenableFuture<VirtualApplianceDto> getVirtualAppliance(
        @EndpointLink("virtualappliances") @BinderParam(BindToPath.class) VirtualDatacenterDto virtualDatacenter,
        @BinderParam(AppendToPath.class) Integer virtualApplianceId);

    /**
     * @see CloudClient#getVirtualApplianceState(VirtualApplianceDto)
     */
    @GET
    ListenableFuture<VirtualApplianceStateDto> getVirtualApplianceState(
        @EndpointLink("state") @BinderParam(BindToPath.class) VirtualApplianceDto virtualAppliance);

    /**
     * @see CloudClient#createVirtualAppliance(VirtualDatacenterDto, VirtualApplianceDto)
     */
    @POST
    ListenableFuture<VirtualApplianceDto> createVirtualAppliance(
        @EndpointLink("virtualappliances") @BinderParam(BindToPath.class) VirtualDatacenterDto virtualDatacenter,
        @BinderParam(BindToXMLPayload.class) VirtualApplianceDto virtualAppliance);

    /**
     * @see CloudClient#updateVirtualAppliance(VirtualApplianceDto)
     */
    @PUT
    ListenableFuture<VirtualApplianceDto> updateVirtualAppliance(
        @EndpointLink("edit") @BinderParam(BindToXMLPayloadAndPath.class) VirtualApplianceDto virtualAppliance);

    /**
     * @see CloudClient#deleteVirtualAppliance(VirtualApplianceDto)
     */
    @DELETE
    ListenableFuture<Void> deleteVirtualAppliance(
        @EndpointLink("edit") @BinderParam(BindToPath.class) VirtualApplianceDto virtualAppliance);

    /**
     * @see CloudClient#deleteVirtualAppliance(VirtualApplianceDto, VirtualApplianceOptions)
     */
    @DELETE
    ListenableFuture<Void> deleteVirtualAppliance(
        @EndpointLink("edit") @BinderParam(BindToPath.class) VirtualApplianceDto virtualAppliance,
        @BinderParam(AppendOptionsToPath.class) VirtualApplianceOptions options);

    /**
     * @see CloudClient#deployVirtualAppliance(VirtualApplianceDto, VirtualMachineTaskDto)
     */
    @POST
    ListenableFuture<AcceptedRequestDto<String>> deployVirtualAppliance(
        @EndpointLink("deploy") @BinderParam(BindToPath.class) VirtualApplianceDto virtualAppliance,
        @BinderParam(BindToXMLPayload.class) VirtualMachineTaskDto task);

    /**
     * @see CloudClient#undeployVirtualAppliance(VirtualApplianceDto, VirtualMachineTaskDto)
     */
    @POST
    ListenableFuture<AcceptedRequestDto<String>> undeployVirtualAppliance(
        @EndpointLink("undeploy") @BinderParam(BindToPath.class) VirtualApplianceDto virtualAppliance,
        @BinderParam(BindToXMLPayload.class) VirtualMachineTaskDto task);

    /*********************** Virtual Machine ***********************/

    /**
     * @see CloudClient#listVirtualMachines(VirtualApplianceDto)
     */
    @GET
    ListenableFuture<VirtualMachinesDto> listVirtualMachines(
        @EndpointLink("virtualmachines") @BinderParam(BindToPath.class) VirtualApplianceDto virtualAppliance);

    /**
     * @see CloudClient#getVirtualMachine(VirtualApplianceDto, Integer)
     */
    @GET
    @ExceptionParser(ReturnNullOnNotFoundOr404.class)
    ListenableFuture<VirtualMachineDto> getVirtualMachine(
        @EndpointLink("virtualmachines") @BinderParam(BindToPath.class) VirtualApplianceDto virtualAppliance,
        @BinderParam(AppendToPath.class) Integer virtualMachineId);

    /**
     * @see CloudClient#createVirtualMachine(VirtualApplianceDto, VirtualMachineDto)
     */
    @POST
    ListenableFuture<VirtualMachineDto> createVirtualMachine(
        @EndpointLink("virtualmachines") @BinderParam(BindToPath.class) VirtualApplianceDto virtualAppliance,
        @BinderParam(BindToXMLPayload.class) VirtualMachineDto virtualMachine);

    /**
     * @see CloudClient#deleteVirtualMachine(VirtualMachineDto)
     */
    @DELETE
    ListenableFuture<Void> deleteVirtualMachine(
        @EndpointLink("edit") @BinderParam(BindToPath.class) VirtualMachineDto virtualMachine);

    /**
     * @see CloudClient#updateVirtualMachine(VirtualMachineDto)
     */
    @PUT
    @ResponseParser(ReturnTaskReferenceOrNull.class)
    ListenableFuture<AcceptedRequestDto<String>> updateVirtualMachine(
        @EndpointLink("edit") @BinderParam(BindToXMLPayloadAndPath.class) VirtualMachineDto virtualMachine);

    /**
     * @see CloudClient#changeVirtualMachineState(VirtualMachineDto, VirtualMachineStateDto)
     */
    @PUT
    ListenableFuture<AcceptedRequestDto<String>> changeVirtualMachineState(
        @EndpointLink("state") @BinderParam(BindToPath.class) VirtualMachineDto virtualMachine,
        @BinderParam(BindToXMLPayload.class) VirtualMachineStateDto state);

    /**
     * @see CloudClient#getVirtualMachineState(VirtualMachineDto)
     */
    @GET
    ListenableFuture<VirtualMachineStateDto> getVirtualMachineState(
        @EndpointLink("state") @BinderParam(BindToPath.class) VirtualMachineDto virtualMachine);

    /**
     * @see CloudClient#listNetworkConfigurations(VirtualMachineDto)
     */
    @GET
    ListenableFuture<VMNetworkConfigurationsDto> listNetworkConfigurations(
        @EndpointLink("configurations") @BinderParam(BindToPath.class) VirtualMachineDto virtualMachine);

    /**
     * @see CloudClient#setGatewayNetwork(VirtualMachineDto, VMNetworkConfigurationDto)
     */
    @PUT
    ListenableFuture<Void> setGatewayNetwork(
        @EndpointLink("configurations") @BinderParam(BindToPath.class) VirtualMachineDto virtualMachine,
        @BinderParam(BindNetworkConfigurationRefToPayload.class) VLANNetworkDto network);

    /*********************** Virtual Machine Template ***********************/

    /**
     * @see CloudClient#getVirtualMachineTemplate(VirtualMachineTemplateDto)
     */
    @GET
    ListenableFuture<VirtualMachineTemplateDto> getVirtualMachineTemplate(
        @EndpointLink("virtualmachinetemplate") @BinderParam(BindToPath.class) VirtualMachineDto virtualMachine);

    /**
     * @see CloudClient#listAttachedVolumes(VirtualMachineDto)
     */
    @GET
    ListenableFuture<VolumesManagementDto> listAttachedVolumes(
        @EndpointLink("volumes") @BinderParam(BindToPath.class) VirtualMachineDto virtualMachine);

    /**
     * @see CloudClient#detachAllVolumes(VirtualMachineDto)
     */
    @DELETE
    @ResponseParser(ReturnTaskReferenceOrNull.class)
    ListenableFuture<AcceptedRequestDto<String>> detachAllVolumes(
        @EndpointLink("volumes") @BinderParam(BindToPath.class) VirtualMachineDto virtualMachine);

    /**
     * @see CloudClient#replaceVolumes(VirtualMachineDto, VolumeManagementDto...)
     */
    @PUT
    @ResponseParser(ReturnTaskReferenceOrNull.class)
    ListenableFuture<AcceptedRequestDto<String>> replaceVolumes(
        @EndpointLink("volumes") @BinderParam(BindToPath.class) VirtualMachineDto virtualMachine,
        @BinderParam(BindVolumeRefsToPayload.class) VolumeManagementDto... volumes);

    /**
     * @see CloudClient#listAttachedHardDisks(VirtualMachineDto)
     */
    @GET
    ListenableFuture<DisksManagementDto> listAttachedHardDisks(
        @EndpointLink("disks") @BinderParam(BindToPath.class) VirtualMachineDto virtualMachine);

    /**
     * @see CloudClient#detachAllHardDisks(VirtualMachineDto)
     */
    @DELETE
    @ResponseParser(ReturnTaskReferenceOrNull.class)
    ListenableFuture<AcceptedRequestDto<String>> detachAllHardDisks(
        @EndpointLink("disks") @BinderParam(BindToPath.class) VirtualMachineDto virtualMachine);

    /**
     * @see CloudClient#replaceHardDisks(VirtualMachineDto, DiskManagementDto...)
     */
    @PUT
    @ResponseParser(ReturnTaskReferenceOrNull.class)
    ListenableFuture<AcceptedRequestDto<String>> replaceHardDisks(
        @EndpointLink("disks") @BinderParam(BindToPath.class) VirtualMachineDto virtualMachine,
        @BinderParam(BindHardDiskRefsToPayload.class) DiskManagementDto... hardDisks);

    /**
     * @see CloudClient#deployVirtualMachine(VirtualMachineDto, VirtualMachineTaskDto)
     */
    @POST
    ListenableFuture<AcceptedRequestDto<String>> deployVirtualMachine(
        @EndpointLink("deploy") @BinderParam(BindToPath.class) VirtualMachineDto virtualMachine,
        @BinderParam(BindToXMLPayload.class) VirtualMachineTaskDto task);

    /**
     * @see CloudClient#undeployVirtualMachine(VirtualMachineDto, VirtualMachineTaskDto)
     */
    @POST
    ListenableFuture<AcceptedRequestDto<String>> undeployVirtualMachine(
        @EndpointLink("undeploy") @BinderParam(BindToPath.class) VirtualMachineDto virtualMachine,
        @BinderParam(BindToXMLPayload.class) VirtualMachineTaskDto task);

    /*********************** Hard disks ***********************/

    /**
     * @see CloudClient#listHardDisks(VirtualDatacenterDto)
     */
    @GET
    ListenableFuture<DisksManagementDto> listHardDisks(
        @EndpointLink("disks") @BinderParam(BindToPath.class) VirtualDatacenterDto virtualDatacenter);

    /**
     * @see CloudClient#getHardDisk(VirtualDatacenterDto, Integer)
     */
    @GET
    @ExceptionParser(ReturnNullOnNotFoundOr404.class)
    ListenableFuture<DiskManagementDto> getHardDisk(
        @EndpointLink("disks") @BinderParam(BindToPath.class) VirtualDatacenterDto virtualDatacenter,
        @BinderParam(AppendToPath.class) Integer diskId);

    /**
     * @see CloudClient#createHardDisk(VirtualDatacenterDto, DiskManagementDto)
     */
    @POST
    ListenableFuture<DiskManagementDto> createHardDisk(
        @EndpointLink("disks") @BinderParam(BindToPath.class) VirtualDatacenterDto virtualDatacenter,
        @BinderParam(BindToXMLPayload.class) DiskManagementDto hardDisk);

    /**
     * @see CloudClient#deleteHardDisk(DiskManagementDto)
     */
    @DELETE
    ListenableFuture<Void> deleteHardDisk(
        @EndpointLink("edit") @BinderParam(BindToPath.class) DiskManagementDto hardDisk);

    /*********************** Volumes ***********************/

    /**
     * @see CloudClient#listVolumes(VirtualDatacenterDto)
     */
    @EnterpriseEdition
    @GET
    ListenableFuture<VolumesManagementDto> listVolumes(
        @EndpointLink("volumes") @BinderParam(BindToPath.class) VirtualDatacenterDto virtualDatacenter);

    /**
     * @see CloudClient#listVolumes(VirtualDatacenterDto, VolumeOptions)
     */
    @EnterpriseEdition
    @GET
    ListenableFuture<VolumesManagementDto> listVolumes(
        @EndpointLink("volumes") @BinderParam(BindToPath.class) VirtualDatacenterDto virtualDatacenter,
        @BinderParam(AppendOptionsToPath.class) VolumeOptions options);

    /**
     * @see CloudClient#getVolume(VirtualDatacenterDto, Integer)
     */
    @EnterpriseEdition
    @GET
    @ExceptionParser(ReturnNullOnNotFoundOr404.class)
    ListenableFuture<VolumeManagementDto> getVolume(
        @EndpointLink("volumes") @BinderParam(BindToPath.class) VirtualDatacenterDto virtualDatacenter,
        @BinderParam(AppendToPath.class) Integer volumeId);

    /**
     * @see CloudClient#createVolume(VirtualDatacenterDto, VolumeManagementDto)
     */
    @EnterpriseEdition
    @POST
    ListenableFuture<VolumeManagementDto> createVolume(
        @EndpointLink("volumes") @BinderParam(BindToPath.class) VirtualDatacenterDto virtualDatacenter,
        @BinderParam(BindToXMLPayload.class) VolumeManagementDto volume);

    /**
     * @see CloudClient#updateVolume(VolumeManagementDto)
     */
    @EnterpriseEdition
    @PUT
    ListenableFuture<VolumeManagementDto> updateVolume(
        @EndpointLink("edit") @BinderParam(BindToXMLPayloadAndPath.class) VolumeManagementDto volume);

    /**
     * @see CloudClient#updateVolume(VolumeManagementDto)
     */
    @EnterpriseEdition
    @DELETE
    ListenableFuture<Void> deleteVolume(
        @EndpointLink("edit") @BinderParam(BindToPath.class) VolumeManagementDto volume);

    /**
     * @see CloudClient#moveVolume(VolumeManagementDto, VirtualDatacenterDto)
     */
    @EnterpriseEdition
    @POST
    @ExceptionParser(ReturnMovedVolume.class)
    ListenableFuture<VolumeManagementDto> moveVolume(
        @BinderParam(BindMoveVolumeToPath.class) VolumeManagementDto volume,
        @BinderParam(BindVirtualDatacenterRefToPayload.class) VirtualDatacenterDto newVirtualDatacenter);
}
