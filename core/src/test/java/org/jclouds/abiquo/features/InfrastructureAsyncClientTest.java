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

import static org.jclouds.abiquo.domain.DomainUtils.withHeader;

import java.io.IOException;
import java.lang.reflect.Method;

import org.jclouds.abiquo.domain.InfrastructureResources;
import org.jclouds.abiquo.domain.NetworkResources;
import org.jclouds.abiquo.domain.infrastructure.options.DatacenterOptions;
import org.jclouds.abiquo.domain.infrastructure.options.MachineOptions;
import org.jclouds.abiquo.domain.infrastructure.options.StoragePoolOptions;
import org.jclouds.abiquo.domain.network.options.IpOptions;
import org.jclouds.abiquo.domain.network.options.NetworkOptions;
import org.jclouds.abiquo.domain.options.search.FilterOptions;
import org.jclouds.abiquo.functions.ReturnAbiquoExceptionOnNotFoundOr4xx;
import org.jclouds.abiquo.functions.ReturnFalseIfNotAvailable;
import org.jclouds.http.functions.ParseXMLWithJAXB;
import org.jclouds.http.functions.ReleasePayloadAndReturn;
import org.jclouds.http.functions.ReturnStringIf2xx;
import org.jclouds.http.functions.ReturnTrueIf2xx;
import org.jclouds.rest.functions.MapHttp4xxCodesToExceptions;
import org.jclouds.rest.functions.ReturnNullOnNotFoundOr404;
import org.jclouds.rest.internal.GeneratedHttpRequest;
import org.jclouds.rest.internal.RestAnnotationProcessor;
import org.testng.annotations.Test;

import com.abiquo.model.enumerator.HypervisorType;
import com.abiquo.model.enumerator.NetworkType;
import com.abiquo.model.enumerator.RemoteServiceType;
import com.abiquo.server.core.infrastructure.DatacenterDto;
import com.abiquo.server.core.infrastructure.MachineDto;
import com.abiquo.server.core.infrastructure.RackDto;
import com.abiquo.server.core.infrastructure.RemoteServiceDto;
import com.abiquo.server.core.infrastructure.UcsRackDto;
import com.abiquo.server.core.infrastructure.network.VLANNetworkDto;
import com.abiquo.server.core.infrastructure.storage.StorageDeviceDto;
import com.abiquo.server.core.infrastructure.storage.StoragePoolDto;
import com.abiquo.server.core.infrastructure.storage.TierDto;
import com.google.inject.TypeLiteral;

/**
 * Tests annotation parsing of {@code InfrastructureAsyncClient}
 * 
 * @author Ignasi Barrera
 */
@Test(groups = "unit")
public class InfrastructureAsyncClientTest extends
    BaseAbiquoAsyncClientTest<InfrastructureAsyncClient>
{
    /*********************** Datacenter ***********************/

    public void testListDatacenters() throws SecurityException, NoSuchMethodException, IOException
    {
        Method method = InfrastructureAsyncClient.class.getMethod("listDatacenters");
        GeneratedHttpRequest<InfrastructureAsyncClient> request = processor.createRequest(method);

        assertRequestLineEquals(request, "GET http://localhost/api/admin/datacenters HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testCreateDatacenter() throws SecurityException, NoSuchMethodException, IOException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("createDatacenter", DatacenterDto.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.datacenterPost());

        assertRequestLineEquals(request, "POST http://localhost/api/admin/datacenters HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, withHeader(InfrastructureResources.datacenterPostPayload()),
            "application/xml", false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testGetDatacenter() throws SecurityException, NoSuchMethodException, IOException
    {
        Method method = InfrastructureAsyncClient.class.getMethod("getDatacenter", Integer.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, 1);

        assertRequestLineEquals(request, "GET http://localhost/api/admin/datacenters/1 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, ReturnNullOnNotFoundOr404.class);

        checkFilters(request);
    }

    public void testUpdateDatacenter() throws SecurityException, NoSuchMethodException, IOException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("updateDatacenter", DatacenterDto.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.datacenterPut());

        assertRequestLineEquals(request, "PUT http://localhost/api/admin/datacenters/1 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, withHeader(InfrastructureResources.datacenterPutPayload()),
            "application/xml", false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testDeleteDatacenter() throws SecurityException, NoSuchMethodException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("deleteDatacenter", DatacenterDto.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.datacenterPut());

        assertRequestLineEquals(request, "DELETE http://localhost/api/admin/datacenters/1 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ReleasePayloadAndReturn.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testListLimitsDatacenter() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("listLimits", DatacenterDto.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.datacenterPut());

        assertRequestLineEquals(request,
            "GET http://localhost/api/admin/datacenters/1/action/getLimits HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    /*********************** Hypervisor ***********************/

    public void testGetHypervisorTypeFromMachine() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("getHypervisorTypeFromMachine",
                DatacenterDto.class, DatacenterOptions.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.datacenterPut(),
                DatacenterOptions.builder().ip("10.60.1.120").build());

        assertRequestLineEquals(request,
            "GET http://localhost/api/admin/datacenters/1/action/hypervisor?ip=10.60.1.120 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ReturnStringIf2xx.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testGetHypervisorTypesFromDatacenter() throws SecurityException,
        NoSuchMethodException, IOException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("getHypervisorTypes", DatacenterDto.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.datacenterPut());

        assertRequestLineEquals(request,
            "GET http://localhost/api/admin/datacenters/1/hypervisors HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    /*********************** Managed Rack ***********************/

    public void testListRacks() throws SecurityException, NoSuchMethodException, IOException
    {
        Method method = InfrastructureAsyncClient.class.getMethod("listRacks", DatacenterDto.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.datacenterPut());

        assertRequestLineEquals(request,
            "GET http://localhost/api/admin/datacenters/1/racks HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/notmanagedrackdto+xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testCreateRack() throws SecurityException, NoSuchMethodException, IOException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("createRack", DatacenterDto.class,
                RackDto.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.datacenterPut(),
                InfrastructureResources.rackPost());

        assertRequestLineEquals(request,
            "POST http://localhost/api/admin/datacenters/1/racks HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, withHeader(InfrastructureResources.rackPostPayload()),
            "application/xml", false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testGetRack() throws SecurityException, NoSuchMethodException, IOException
    {
        Method method =
            InfrastructureAsyncClient.class
                .getMethod("getRack", DatacenterDto.class, Integer.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.datacenterPut(), 1);

        assertRequestLineEquals(request,
            "GET http://localhost/api/admin/datacenters/1/racks/1 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, ReturnNullOnNotFoundOr404.class);

        checkFilters(request);
    }

    public void testUpdateRack() throws SecurityException, NoSuchMethodException, IOException
    {
        Method method = InfrastructureAsyncClient.class.getMethod("updateRack", RackDto.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.rackPut());

        assertRequestLineEquals(request,
            "PUT http://localhost/api/admin/datacenters/1/racks/1 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, withHeader(InfrastructureResources.rackPutPayload()),
            "application/xml", false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testDeleteRack() throws SecurityException, NoSuchMethodException
    {
        Method method = InfrastructureAsyncClient.class.getMethod("deleteRack", RackDto.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.rackPut());

        assertRequestLineEquals(request,
            "DELETE http://localhost/api/admin/datacenters/1/racks/1 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ReleasePayloadAndReturn.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    /*********************** Managed Rack ***********************/

    public void testListManagedRacks() throws SecurityException, NoSuchMethodException, IOException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("listManagedRacks", DatacenterDto.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.datacenterPut());

        assertRequestLineEquals(request,
            "GET http://localhost/api/admin/datacenters/1/racks HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/managedrackdto+xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testCreateManagedRack() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("createManagedRack", DatacenterDto.class,
                UcsRackDto.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.datacenterPut(),
                InfrastructureResources.managedRackPost());

        assertRequestLineEquals(request,
            "POST http://localhost/api/admin/datacenters/1/racks HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/managedrackdto+xml\n");
        assertPayloadEquals(request, withHeader(InfrastructureResources.managedRackPostPayload()),
            "application/managedrackdto+xml", false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testGetManagedRack() throws SecurityException, NoSuchMethodException, IOException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("getManagedRack", DatacenterDto.class,
                Integer.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.datacenterPut(), 1);

        assertRequestLineEquals(request,
            "GET http://localhost/api/admin/datacenters/1/racks/1 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/managedrackdto+xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, ReturnNullOnNotFoundOr404.class);

        checkFilters(request);
    }

    public void testUpdateManagedRack() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("updateManagedRack", UcsRackDto.class);

        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.managedRackPut());

        assertRequestLineEquals(request,
            "PUT http://localhost/api/admin/datacenters/1/racks/1 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/managedrackdto+xml\n");
        assertPayloadEquals(request, withHeader(InfrastructureResources.managedRackPutPayload()),
            "application/managedrackdto+xml", false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testListServiceProfiles() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("listServiceProfiles", UcsRackDto.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.managedRackPut());

        assertRequestLineEquals(request,
            "GET http://localhost/api/admin/datacenters/1/racks/1/logicservers HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testListServiceProfilesWithOptions() throws SecurityException,
        NoSuchMethodException, IOException
    {
        FilterOptions options = FilterOptions.builder().startWith(1).limit(2).build();

        Method method =
            InfrastructureAsyncClient.class.getMethod("listServiceProfiles", UcsRackDto.class,
                FilterOptions.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.managedRackPut(), options);

        assertRequestLineEquals(request,
            "GET http://localhost/api/admin/datacenters/1/racks/1/logicservers?startwith=1&limit=2 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testListOrganizations() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("listOrganizations", UcsRackDto.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.managedRackPut());

        assertRequestLineEquals(request,
            "GET http://localhost/api/admin/datacenters/1/racks/1/organizations HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testListOrganizationsWithOptions() throws SecurityException, NoSuchMethodException,
        IOException
    {
        FilterOptions options = FilterOptions.builder().has("org").build();

        Method method =
            InfrastructureAsyncClient.class.getMethod("listOrganizations", UcsRackDto.class,
                FilterOptions.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.managedRackPut(), options);

        assertRequestLineEquals(request,
            "GET http://localhost/api/admin/datacenters/1/racks/1/organizations?has=org HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testListServiceProfileTemplates() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("listServiceProfileTemplates",
                UcsRackDto.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.managedRackPut());

        assertRequestLineEquals(request,
            "GET http://localhost/api/admin/datacenters/1/racks/1/lstemplates HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testListServiceProfileTemplatesWithOptions() throws SecurityException,
        NoSuchMethodException, IOException
    {
        FilterOptions options = FilterOptions.builder().ascendant(true).build();

        Method method =
            InfrastructureAsyncClient.class.getMethod("listServiceProfileTemplates",
                UcsRackDto.class, FilterOptions.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.managedRackPut(), options);

        assertRequestLineEquals(request,
            "GET http://localhost/api/admin/datacenters/1/racks/1/lstemplates?asc=true HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    /*********************** Remote Service **********************/

    public void testListRemoteServices() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("listRemoteServices", DatacenterDto.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.datacenterPut());

        assertRequestLineEquals(request,
            "GET http://localhost/api/admin/datacenters/1/remoteservices HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testCreateRemoteService() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("createRemoteService", DatacenterDto.class,
                RemoteServiceDto.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.datacenterPut(),
                InfrastructureResources.remoteServicePost());

        assertRequestLineEquals(request,
            "POST http://localhost/api/admin/datacenters/1/remoteservices HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request,
            withHeader(InfrastructureResources.remoteServicePostPayload()), "application/xml",
            false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testGetRemoteService() throws SecurityException, NoSuchMethodException, IOException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("getRemoteService", DatacenterDto.class,
                RemoteServiceType.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.datacenterPut(),
                RemoteServiceType.STORAGE_SYSTEM_MONITOR);

        assertRequestLineEquals(request,
            "GET http://localhost/api/admin/datacenters/1/remoteservices/storagesystemmonitor HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, ReturnNullOnNotFoundOr404.class);

        checkFilters(request);
    }

    public void testUpdateRemoteService() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            InfrastructureAsyncClient.class
                .getMethod("updateRemoteService", RemoteServiceDto.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.remoteServicePut());

        assertRequestLineEquals(request,
            "PUT http://localhost/api/admin/datacenters/1/remoteservices/nodecollector HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, withHeader(InfrastructureResources.remoteServicePutPayload()),
            "application/xml", false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testDeleteRemoteService() throws SecurityException, NoSuchMethodException
    {
        Method method =
            InfrastructureAsyncClient.class
                .getMethod("deleteRemoteService", RemoteServiceDto.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.remoteServicePut());

        assertRequestLineEquals(request,
            "DELETE http://localhost/api/admin/datacenters/1/remoteservices/nodecollector HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ReleasePayloadAndReturn.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testIsAvailableRemoteService() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("isAvailable", RemoteServiceDto.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.remoteServicePut());

        String checkUri = InfrastructureResources.remoteServicePut().searchLink("check").getHref();
        assertRequestLineEquals(request, String.format("GET %s HTTP/1.1", checkUri));
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ReturnTrueIf2xx.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, ReturnFalseIfNotAvailable.class);

        checkFilters(request);
    }

    /*********************** Machine ***********************/

    public void testDiscoverSingleMachineWithoutOptions() throws SecurityException,
        NoSuchMethodException, IOException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("discoverSingleMachine", DatacenterDto.class,
                String.class, HypervisorType.class, String.class, String.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.datacenterPut(), "10.60.1.222",
                HypervisorType.XENSERVER, "user", "pass");

        String baseUrl = "http://localhost/api/admin/datacenters/1/action/discoversingle";
        String query = "hypervisor=XENSERVER&ip=10.60.1.222&user=user&password=pass";
        String expectedRequest = String.format("GET %s?%s HTTP/1.1", baseUrl, query);

        assertRequestLineEquals(request, expectedRequest);
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, ReturnAbiquoExceptionOnNotFoundOr4xx.class);

        checkFilters(request);
    }

    public void testDiscoverSingleMachineAllParams() throws SecurityException,
        NoSuchMethodException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("discoverSingleMachine", DatacenterDto.class,
                String.class, HypervisorType.class, String.class, String.class,
                MachineOptions.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.datacenterPut(), "80.80.80.80",
                HypervisorType.KVM, "user", "pass", MachineOptions.builder().port(8889).build());

        String baseUrl = "http://localhost/api/admin/datacenters/1/action/discoversingle";
        String query = "hypervisor=KVM&ip=80.80.80.80&user=user&password=pass&port=8889";
        String expectedRequest = String.format("GET %s?%s HTTP/1.1", baseUrl, query);

        assertRequestLineEquals(request, expectedRequest);
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, ReturnAbiquoExceptionOnNotFoundOr4xx.class);

        checkFilters(request);
    }

    public void testDiscoverSingleMachineDefaultValues() throws SecurityException,
        NoSuchMethodException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("discoverSingleMachine", DatacenterDto.class,
                String.class, HypervisorType.class, String.class, String.class,
                MachineOptions.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.datacenterPut(), "80.80.80.80",
                HypervisorType.KVM, "user", "pass", MachineOptions.builder().build());

        String baseUrl = "http://localhost/api/admin/datacenters/1/action/discoversingle";
        String query = "hypervisor=KVM&ip=80.80.80.80&user=user&password=pass";
        String expectedRequest = String.format("GET %s?%s HTTP/1.1", baseUrl, query);

        assertRequestLineEquals(request, expectedRequest);
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, ReturnAbiquoExceptionOnNotFoundOr4xx.class);

        checkFilters(request);
    }

    public void testDiscoverMultipleMachinesWithoutOptions() throws SecurityException,
        NoSuchMethodException, IOException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("discoverMultipleMachines",
                DatacenterDto.class, String.class, String.class, HypervisorType.class,
                String.class, String.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.datacenterPut(), "10.60.1.222",
                "10.60.1.250", HypervisorType.XENSERVER, "user", "pass");

        String baseUrl = "http://localhost/api/admin/datacenters/1/action/discovermultiple";
        String query =
            "password=pass&ipTo=10.60.1.250&ipFrom=10.60.1.222&hypervisor=XENSERVER&user=user";
        String expectedRequest = String.format("GET %s?%s HTTP/1.1", baseUrl, query);

        assertRequestLineEquals(request, expectedRequest);
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, ReturnAbiquoExceptionOnNotFoundOr4xx.class);

        checkFilters(request);
    }

    public void testDiscoverMultipleMachinesAllParams() throws SecurityException,
        NoSuchMethodException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("discoverMultipleMachines",
                DatacenterDto.class, String.class, String.class, HypervisorType.class,
                String.class, String.class, MachineOptions.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.datacenterPut(), "80.80.80.80",
                "80.80.80.86", HypervisorType.KVM, "user", "pass",
                MachineOptions.builder().port(8889).build());

        String baseUrl = "http://localhost/api/admin/datacenters/1/action/discovermultiple";
        String query =
            "password=pass&ipTo=80.80.80.86&ipFrom=80.80.80.80&hypervisor=KVM&user=user&port=8889";
        String expectedRequest = String.format("GET %s?%s HTTP/1.1", baseUrl, query);

        assertRequestLineEquals(request, expectedRequest);
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, ReturnAbiquoExceptionOnNotFoundOr4xx.class);

        checkFilters(request);
    }

    public void testListMachines() throws SecurityException, NoSuchMethodException, IOException
    {
        Method method = InfrastructureAsyncClient.class.getMethod("listMachines", RackDto.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.rackPut());

        assertRequestLineEquals(request,
            "GET http://localhost/api/admin/datacenters/1/racks/1/machines HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testGetMachine() throws SecurityException, NoSuchMethodException, IOException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("getMachine", RackDto.class, Integer.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.rackPut(), 1);

        assertRequestLineEquals(request,
            "GET http://localhost/api/admin/datacenters/1/racks/1/machines/1 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, ReturnNullOnNotFoundOr404.class);

        checkFilters(request);
    }

    public void testCheckMachineState() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("checkMachineState", MachineDto.class,
                boolean.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.machinePut(), true);

        assertRequestLineEquals(
            request,
            "GET http://localhost/api/admin/datacenters/1/racks/1/machines/1/action/checkstate?sync=true HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testCreateMachine() throws SecurityException, NoSuchMethodException, IOException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("createMachine", RackDto.class,
                MachineDto.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.rackPut(),
                InfrastructureResources.machinePost());

        assertRequestLineEquals(request,
            "POST http://localhost/api/admin/datacenters/1/racks/1/machines HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, withHeader(InfrastructureResources.machinePostPayload()),
            "application/xml", false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testUpdateMachine() throws SecurityException, NoSuchMethodException, IOException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("updateMachine", MachineDto.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.machinePut());

        assertRequestLineEquals(request,
            "PUT http://localhost/api/admin/datacenters/1/racks/1/machines/1 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, withHeader(InfrastructureResources.machinePutPayload()),
            "application/xml", false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testDeleteMachine() throws SecurityException, NoSuchMethodException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("deleteMachine", MachineDto.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.machinePut());

        assertRequestLineEquals(request,
            "DELETE http://localhost/api/admin/datacenters/1/racks/1/machines/1 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ReleasePayloadAndReturn.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    /*********************** Storage Device ***********************/

    public void testListStorageDevices() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("listStorageDevices", DatacenterDto.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.datacenterPut());

        assertRequestLineEquals(request,
            "GET http://localhost/api/admin/datacenters/1/storage/devices HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testCreateStorageDevice() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("createStorageDevice", DatacenterDto.class,
                StorageDeviceDto.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.datacenterPut(),
                InfrastructureResources.storageDevicePost());

        assertRequestLineEquals(request,
            "POST http://localhost/api/admin/datacenters/1/storage/devices HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request,
            withHeader(InfrastructureResources.storageDevicePostPayload()), "application/xml",
            false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testDeleteStorageDevice() throws SecurityException, NoSuchMethodException
    {
        Method method =
            InfrastructureAsyncClient.class
                .getMethod("deleteStorageDevice", StorageDeviceDto.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.storageDevicePut());

        assertRequestLineEquals(request,
            "DELETE http://localhost/api/admin/datacenters/1/storage/devices/1 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ReleasePayloadAndReturn.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testUpdateStorageDevice() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            InfrastructureAsyncClient.class
                .getMethod("updateStorageDevice", StorageDeviceDto.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.storageDevicePut());

        assertRequestLineEquals(request,
            "PUT http://localhost/api/admin/datacenters/1/storage/devices/1 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, withHeader(InfrastructureResources.storageDevicePutPayload()),
            "application/xml", false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testGetStorageDevice() throws SecurityException, NoSuchMethodException, IOException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("getStorageDevice", DatacenterDto.class,
                Integer.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.datacenterPut(), 1);

        assertRequestLineEquals(request,
            "GET http://localhost/api/admin/datacenters/1/storage/devices/1 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, ReturnNullOnNotFoundOr404.class);

        checkFilters(request);
    }

    /*********************** Tier ***********************/

    public void testListTiers() throws SecurityException, NoSuchMethodException, IOException
    {
        Method method = InfrastructureAsyncClient.class.getMethod("listTiers", DatacenterDto.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.datacenterPut());

        assertRequestLineEquals(request,
            "GET http://localhost/api/admin/datacenters/1/storage/tiers HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testUpdateTier() throws SecurityException, NoSuchMethodException, IOException
    {
        Method method = InfrastructureAsyncClient.class.getMethod("updateTier", TierDto.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.tierPut());

        assertRequestLineEquals(request,
            "PUT http://localhost/api/admin/datacenters/1/storage/tiers/1 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, withHeader(InfrastructureResources.tierPutPayload()),
            "application/xml", false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testGetTier() throws SecurityException, NoSuchMethodException, IOException
    {
        Method method =
            InfrastructureAsyncClient.class
                .getMethod("getTier", DatacenterDto.class, Integer.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.datacenterPut(), 1);

        assertRequestLineEquals(request,
            "GET http://localhost/api/admin/datacenters/1/storage/tiers/1 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, ReturnNullOnNotFoundOr404.class);

        checkFilters(request);
    }

    /*********************** StoragePool ***********************/

    public void testListSyncStoragePools() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("listStoragePools", StorageDeviceDto.class,
                StoragePoolOptions.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.storageDevicePut(),
                StoragePoolOptions.builder().sync(true).build());

        assertRequestLineEquals(request,
            "GET http://localhost/api/admin/datacenters/1/storage/devices/1/pools?sync=true HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/storagepoolsdto+xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testListStoragePoolsFromTier() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("listStoragePools", TierDto.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.tierPut(), StoragePoolOptions
                .builder().sync(true).build());

        assertRequestLineEquals(request,
            "GET http://localhost/api/admin/datacenters/1/storage/tiers/1/pools HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/storagepoolsdto+xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testListStoragePoolsNoParams() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("listStoragePools", StorageDeviceDto.class,
                StoragePoolOptions.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.storageDevicePut(),
                StoragePoolOptions.builder().build());

        assertRequestLineEquals(request,
            "GET http://localhost/api/admin/datacenters/1/storage/devices/1/pools HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/storagepoolsdto+xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testCreateStoragePool() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("createStoragePool", StorageDeviceDto.class,
                StoragePoolDto.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.storageDevicePut(),
                InfrastructureResources.storagePoolPost());

        assertRequestLineEquals(request,
            "POST http://localhost/api/admin/datacenters/1/storage/devices/1/pools HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/storagepooldto+xml\n");
        assertPayloadEquals(request, withHeader(InfrastructureResources.storagePoolPostPayload()),
            "application/storagepooldto+xml", false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testUpdateStoragePool() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("updateStoragePool", StoragePoolDto.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.storagePoolPut());

        assertRequestLineEquals(request,
            "PUT http://localhost/api/admin/datacenters/1/storage/devices/1/pools/tururututu HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/storagepooldto+xml\n");
        assertPayloadEquals(request, withHeader(InfrastructureResources.storagePoolPutPayload()),
            "application/storagepooldto+xml", false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testDeleteStoragePool() throws SecurityException, NoSuchMethodException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("deleteStoragePool", StoragePoolDto.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.storagePoolPut());

        assertRequestLineEquals(request,
            "DELETE http://localhost/api/admin/datacenters/1/storage/devices/1/pools/tururututu HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ReleasePayloadAndReturn.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testGetStoragePool() throws SecurityException, NoSuchMethodException, IOException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("getStoragePool", StorageDeviceDto.class,
                String.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.storageDevicePut(),
                InfrastructureResources.storagePoolPut().getIdStorage());

        assertRequestLineEquals(request,
            "GET http://localhost/api/admin/datacenters/1/storage/devices/1/pools/tururututu HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, ReturnNullOnNotFoundOr404.class);

        checkFilters(request);
    }

    public void testRefreshStoragePool() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("refreshStoragePool", StoragePoolDto.class,
                StoragePoolOptions.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.storagePoolPut(),
                StoragePoolOptions.builder().sync(true).build());

        assertRequestLineEquals(
            request,
            "GET http://localhost/api/admin/datacenters/1/storage/devices/1/pools/tururututu?sync=true HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, MapHttp4xxCodesToExceptions.class);

        checkFilters(request);
    }

    /*********************** Network ***********************/

    public void testListNetworks() throws SecurityException, NoSuchMethodException, IOException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("listNetworks", DatacenterDto.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.datacenterPut());

        assertRequestLineEquals(request,
            "GET http://localhost/api/admin/datacenters/1/network HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testListNetworksWithOptions() throws SecurityException, NoSuchMethodException,
        IOException
    {
        NetworkOptions options = NetworkOptions.builder().type(NetworkType.PUBLIC).build();

        Method method =
            InfrastructureAsyncClient.class.getMethod("listNetworks", DatacenterDto.class,
                NetworkOptions.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.datacenterPut(), options);

        assertRequestLineEquals(request,
            "GET http://localhost/api/admin/datacenters/1/network?type=PUBLIC HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testGetNetworks() throws SecurityException, NoSuchMethodException, IOException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("getNetwork", DatacenterDto.class,
                Integer.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.datacenterPut(), 1);

        assertRequestLineEquals(request,
            "GET http://localhost/api/admin/datacenters/1/network/1 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, ReturnNullOnNotFoundOr404.class);

        checkFilters(request);
    }

    public void testCreateNetwork() throws SecurityException, NoSuchMethodException, IOException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("createNetwork", DatacenterDto.class,
                VLANNetworkDto.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.datacenterPut(),
                NetworkResources.vlanPost());

        assertRequestLineEquals(request,
            "POST http://localhost/api/admin/datacenters/1/network HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, withHeader(NetworkResources.vlanNetworkPostPayload()),
            "application/xml", false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testUpdateNetwork() throws SecurityException, NoSuchMethodException, IOException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("updateNetwork", VLANNetworkDto.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, NetworkResources.publicNetworkPut());

        assertRequestLineEquals(request,
            "PUT http://localhost/api/admin/datacenters/1/network/1 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, withHeader(NetworkResources.publicNetworkPutPayload()),
            "application/xml", false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testDeleteNetwork() throws SecurityException, NoSuchMethodException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("deleteNetwork", VLANNetworkDto.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, NetworkResources.publicNetworkPut());

        assertRequestLineEquals(request,
            "DELETE http://localhost/api/admin/datacenters/1/network/1 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ReleasePayloadAndReturn.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testCheckTagAvailability() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("checkTagAvailability", DatacenterDto.class,
                Integer.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, InfrastructureResources.datacenterPut(), 2);

        assertRequestLineEquals(request,
            "GET http://localhost/api/admin/datacenters/1/network/action/checkavailability?tag=2 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, MapHttp4xxCodesToExceptions.class);

        checkFilters(request);
    }

    /*********************** Network IPs ***********************/

    public void testListNetworkIps() throws SecurityException, NoSuchMethodException, IOException
    {
        Method method =
            InfrastructureAsyncClient.class.getMethod("listNetworkIps", VLANNetworkDto.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, NetworkResources.publicNetworkPut());

        assertRequestLineEquals(request,
            "GET http://localhost/api/admin/datacenters/1/network/1/ips HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testListNetworkIpsWithOptions() throws SecurityException, NoSuchMethodException,
        IOException
    {
        IpOptions options = IpOptions.builder().startWith(10).build();
        Method method =
            InfrastructureAsyncClient.class.getMethod("listNetworkIps", VLANNetworkDto.class,
                IpOptions.class);
        GeneratedHttpRequest<InfrastructureAsyncClient> request =
            processor.createRequest(method, NetworkResources.publicNetworkPut(), options);

        assertRequestLineEquals(request,
            "GET http://localhost/api/admin/datacenters/1/network/1/ips?startwith=10 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    @Override
    protected TypeLiteral<RestAnnotationProcessor<InfrastructureAsyncClient>> createTypeLiteral()
    {
        return new TypeLiteral<RestAnnotationProcessor<InfrastructureAsyncClient>>()
        {
        };
    }
}
