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
import org.jclouds.abiquo.domain.enterprise.options.EnterpriseOptions;
import org.jclouds.abiquo.functions.infrastructure.ParseDatacenterId;
import org.jclouds.abiquo.http.filters.AbiquoAuthentication;
import org.jclouds.abiquo.reference.annotations.EnterpriseEdition;
import org.jclouds.abiquo.rest.annotations.EndpointLink;
import org.jclouds.rest.annotations.BinderParam;
import org.jclouds.rest.annotations.ExceptionParser;
import org.jclouds.rest.annotations.ParamParser;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.binders.BindToXMLPayload;
import org.jclouds.rest.functions.ReturnNullOnNotFoundOr404;

import com.abiquo.server.core.appslibrary.TemplateDefinitionListDto;
import com.abiquo.server.core.appslibrary.TemplateDefinitionListsDto;
import com.abiquo.server.core.cloud.VirtualDatacentersDto;
import com.abiquo.server.core.cloud.VirtualMachinesDto;
import com.abiquo.server.core.enterprise.DatacenterLimitsDto;
import com.abiquo.server.core.enterprise.DatacentersLimitsDto;
import com.abiquo.server.core.enterprise.EnterpriseDto;
import com.abiquo.server.core.enterprise.EnterprisePropertiesDto;
import com.abiquo.server.core.enterprise.EnterprisesDto;
import com.abiquo.server.core.enterprise.UserDto;
import com.abiquo.server.core.enterprise.UsersDto;
import com.abiquo.server.core.infrastructure.DatacenterDto;
import com.abiquo.server.core.infrastructure.DatacentersDto;
import com.abiquo.server.core.infrastructure.MachinesDto;
import com.abiquo.server.core.infrastructure.network.VLANNetworksDto;
import com.google.common.util.concurrent.ListenableFuture;

/**
 * Provides asynchronous access to Abiquo Enterprise API.
 * 
 * @see http://community.abiquo.com/display/ABI20/API+Reference
 * @see EnterpriseClient
 * @author Ignasi Barrera
 * @author Francesc Montserrat
 */
@RequestFilters(AbiquoAuthentication.class)
@Consumes(MediaType.APPLICATION_XML)
@Path("/admin")
public interface EnterpriseAsyncClient
{
    /*********************** Enterprise ***********************/

    /**
     * @see EnterpriseClient#listEnterprises()
     */
    @GET
    @Path("/enterprises")
    ListenableFuture<EnterprisesDto> listEnterprises();

    /**
     * @see EnterpriseClient#listEnterprises(EnterpriseOptions)
     */
    @GET
    @Path("/enterprises")
    ListenableFuture<EnterprisesDto> listEnterprises(
        @BinderParam(AppendOptionsToPath.class) EnterpriseOptions options);

    /**
     * @see EnterpriseClient#listEnterprises(DatacenterDto, EnterpriseOptions)
     */
    @GET
    ListenableFuture<EnterprisesDto> listEnterprises(
        @EndpointLink("enterprises") @BinderParam(BindToPath.class) DatacenterDto datacenter,
        @BinderParam(AppendOptionsToPath.class) EnterpriseOptions options);

    /**
     * @see EnterpriseClient#createEnterprise(EnterpriseDto)
     */
    @POST
    @Path("/enterprises")
    ListenableFuture<EnterpriseDto> createEnterprise(
        @BinderParam(BindToXMLPayload.class) EnterpriseDto enterprise);

    /**
     * @see EnterpriseClient#getEnterprise(Integer)
     */
    @GET
    @Path("/enterprises/{enterprise}")
    @ExceptionParser(ReturnNullOnNotFoundOr404.class)
    ListenableFuture<EnterpriseDto> getEnterprise(@PathParam("enterprise") Integer enterpriseId);

    /**
     * @see EnterpriseClient#updateEnterprise(EnterpriseDto)
     */
    @PUT
    ListenableFuture<EnterpriseDto> updateEnterprise(
        @EndpointLink("edit") @BinderParam(BindToXMLPayloadAndPath.class) EnterpriseDto enterprise);

    /**
     * @see EnterpriseClient#deleteEnterprise(EnterpriseDto)
     */
    @DELETE
    ListenableFuture<Void> deleteEnterprise(
        @EndpointLink("edit") @BinderParam(BindToPath.class) EnterpriseDto enterprise);

    /**
     * @see EnterpriseClient#listAllowedDatacenters(Integer)
     */
    @GET
    @Path("/datacenters")
    ListenableFuture<DatacentersDto> listAllowedDatacenters(
        @QueryParam("idEnterprise") Integer enterpriseId);

    /**
     * @see EnterpriseClient#listVirtualDatacenters(EnterpriseDto)
     */
    @GET
    ListenableFuture<VirtualDatacentersDto> listVirtualDatacenters(
        @EndpointLink("cloud/virtualdatacenters") @BinderParam(BindToPath.class) EnterpriseDto enterprise);

    /*********************** Enterprise Properties ***********************/

    /**
     * @see EnterpriseClient#getEnterpriseProperties(EnterpriseDto)
     */
    @GET
    @Path("/enterprises/{enterprise}/properties")
    @EnterpriseEdition
    ListenableFuture<EnterprisePropertiesDto> getEnterpriseProperties(
        @PathParam("enterprise") Integer enterpriseId);

    /**
     * @see EnterpriseClient#updateEnterpriseProperties(EnterprisePropertiesDto)
     */
    @PUT
    @EnterpriseEdition
    ListenableFuture<EnterprisePropertiesDto> updateEnterpriseProperties(
        @EndpointLink("edit") @BinderParam(BindToXMLPayloadAndPath.class) EnterprisePropertiesDto properties);

    /*********************** Enterprise Limits ***********************/

    /**
     * @see EnterpriseClient#createLimits(EnterpriseDto, DatacenterDto, DatacenterLimitsDto)
     */
    @POST
    ListenableFuture<DatacenterLimitsDto> createLimits(
        @EndpointLink("limits") @BinderParam(BindToPath.class) final EnterpriseDto enterprise,
        @QueryParam("datacenter") @ParamParser(ParseDatacenterId.class) final DatacenterDto datacenter,
        @BinderParam(BindToXMLPayload.class) DatacenterLimitsDto limits);

    /**
     * @see EnterpriseClient#getLimits(EnterpriseDto, DatacenterDto)
     */
    @GET
    @ExceptionParser(ReturnNullOnNotFoundOr404.class)
    ListenableFuture<DatacentersLimitsDto> getLimits(
        @EndpointLink("limits") @BinderParam(BindToPath.class) final EnterpriseDto enterprise,
        @QueryParam("datacenter") @ParamParser(ParseDatacenterId.class) final DatacenterDto datacenter);

    /**
     * @see EnterpriseClient#updateLimits(DatacenterLimitsDto)
     */
    @PUT
    ListenableFuture<DatacenterLimitsDto> updateLimits(
        @EndpointLink("edit") @BinderParam(BindToXMLPayloadAndPath.class) DatacenterLimitsDto limits);

    /**
     * @see EnterpriseClient#deleteLimits(DatacenterLimitsDto)
     */
    @DELETE
    ListenableFuture<Void> deleteLimits(
        @EndpointLink("edit") @BinderParam(BindToPath.class) DatacenterLimitsDto limits);

    /**
     * @see EnterpriseClient#listLimits(EnterpriseDto)
     */
    @GET
    ListenableFuture<DatacentersLimitsDto> listLimits(
        @EndpointLink("limits") @BinderParam(BindToPath.class) EnterpriseDto enterprise);

    /*********************** User ***********************/

    /**
     * @see EnterpriseClient#listUsers(EnterpriseDto)
     */
    @GET
    ListenableFuture<UsersDto> listUsers(
        @EndpointLink("users") @BinderParam(BindToPath.class) EnterpriseDto enterprise);

    /**
     * @see EnterpriseClient#getUser(EnterpriseDto, Integer)
     */
    @GET
    @ExceptionParser(ReturnNullOnNotFoundOr404.class)
    ListenableFuture<UserDto> getUser(
        @EndpointLink("users") @BinderParam(BindToPath.class) EnterpriseDto enterprise,
        @BinderParam(AppendToPath.class) Integer userId);

    /**
     * @see EnterpriseClient#createUser(EnterpriseDto)
     */
    @POST
    ListenableFuture<UserDto> createUser(
        @EndpointLink("users") @BinderParam(BindToPath.class) EnterpriseDto enterprise,
        @BinderParam(BindToXMLPayload.class) UserDto user);

    /**
     * @see EnterpriseClient#updateUser(UserDto)
     */
    @PUT
    ListenableFuture<UserDto> updateUser(
        @EndpointLink("edit") @BinderParam(BindToXMLPayloadAndPath.class) UserDto user);

    /**
     * @see EnterpriseClient#deleteUser(UserDto)
     */
    @DELETE
    ListenableFuture<Void> deleteUser(
        @EndpointLink("edit") @BinderParam(BindToPath.class) UserDto user);

    @GET
    ListenableFuture<VirtualMachinesDto> listVirtualMachines(
        @EndpointLink("virtualmachines") @BinderParam(BindToPath.class) final UserDto user);

    /*********************** Datacenter Repository ***********************/

    /**
     * @see EnterpriseClient#refreshTemplateRepository(Integer, Integer)
     */
    @PUT
    @Path("/enterprises/{enterprise}/datacenterrepositories/{datacenterrepository}/actions/refresh")
    ListenableFuture<Void> refreshTemplateRepository(@PathParam("enterprise") Integer enterpriseId,
        @PathParam("datacenterrepository") Integer datacenterRepositoryId);

    /*********************** External Network ***********************/

    /**
     * @see EnterpriseClient#listExternalNetworks(EnterpriseDto)
     */
    @EnterpriseEdition
    @GET
    ListenableFuture<VLANNetworksDto> listExternalNetworks(
        @EndpointLink("externalnetworks") @BinderParam(BindToPath.class) EnterpriseDto enterprise);

    /*********************** Virtual Machine ***********************/

    /**
     * @see EnterpriseClient#listVirtualMachines(EnterpriseDto)
     */
    @GET
    ListenableFuture<VirtualMachinesDto> listVirtualMachines(
        @EndpointLink("virtualmachines") @BinderParam(BindToPath.class) EnterpriseDto enterprise);

    /*********************** Machine ***********************/

    /**
     * @see EnterpriseClient#listVirtualMachines(EnterpriseDto)
     */
    @GET
    ListenableFuture<MachinesDto> listReservedMachines(
        @EndpointLink("reservedmachines") @BinderParam(BindToPath.class) EnterpriseDto enterprise);

    /*********************** Template definition list ***********************/

    /**
     * @see EnterpriseClient#listTemplateDefinitionLists(EnterpriseDto)
     */
    @GET
    ListenableFuture<TemplateDefinitionListsDto> listTemplateDefinitionLists(
        @EndpointLink("appslib/templateDefinitionLists") @BinderParam(BindToPath.class) EnterpriseDto enterprise);

    /**
     * @see EnterpriseClient#createTemplateDefinitionList(EnterpriseDto)
     */
    @POST
    ListenableFuture<TemplateDefinitionListDto> createTemplateDefinitionList(
        @EndpointLink("appslib/templateDefinitionLists") @BinderParam(BindToPath.class) EnterpriseDto enterprise,
        @BinderParam(BindToXMLPayload.class) TemplateDefinitionListDto templateList);

    /**
     * @see EnterpriseClient#deleteTemplateDefinitionList(EnterpriseDto)
     */
    @DELETE
    ListenableFuture<Void> deleteTemplateDefinitionList(
        @EndpointLink("edit") @BinderParam(BindToPath.class) TemplateDefinitionListDto templateList);

    /**
     * @see EnterpriseClient#getTemplateDefinitionList(EnterpriseDto, Integer)
     */
    @GET
    @ExceptionParser(ReturnNullOnNotFoundOr404.class)
    ListenableFuture<UserDto> getTemplateDefinitionList(
        @EndpointLink("appslib/templateDefinitionLists") @BinderParam(BindToPath.class) EnterpriseDto enterprise,
        @BinderParam(AppendToPath.class) Integer templateListId);
}
