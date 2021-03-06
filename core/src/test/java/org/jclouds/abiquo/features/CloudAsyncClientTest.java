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

import org.jclouds.abiquo.domain.CloudResources;
import org.jclouds.abiquo.domain.EnterpriseResources;
import org.jclouds.abiquo.domain.InfrastructureResources;
import org.jclouds.abiquo.domain.NetworkResources;
import org.jclouds.abiquo.domain.cloud.options.VirtualDatacenterOptions;
import org.jclouds.abiquo.domain.cloud.options.VolumeOptions;
import org.jclouds.abiquo.domain.network.options.IpOptions;
import org.jclouds.abiquo.domain.options.search.reference.OrderBy;
import org.jclouds.abiquo.functions.ReturnTaskReferenceOrNull;
import org.jclouds.abiquo.functions.cloud.ReturnMovedVolume;
import org.jclouds.http.functions.ParseXMLWithJAXB;
import org.jclouds.http.functions.ReleasePayloadAndReturn;
import org.jclouds.rest.functions.ReturnNullOnNotFoundOr404;
import org.jclouds.rest.internal.GeneratedHttpRequest;
import org.jclouds.rest.internal.RestAnnotationProcessor;
import org.testng.annotations.Test;

import com.abiquo.model.rest.RESTLink;
import com.abiquo.server.core.cloud.VirtualApplianceDto;
import com.abiquo.server.core.cloud.VirtualDatacenterDto;
import com.abiquo.server.core.cloud.VirtualMachineDto;
import com.abiquo.server.core.cloud.VirtualMachineStateDto;
import com.abiquo.server.core.cloud.VirtualMachineTaskDto;
import com.abiquo.server.core.enterprise.EnterpriseDto;
import com.abiquo.server.core.infrastructure.DatacenterDto;
import com.abiquo.server.core.infrastructure.network.IpPoolManagementDto;
import com.abiquo.server.core.infrastructure.network.NicDto;
import com.abiquo.server.core.infrastructure.network.VLANNetworkDto;
import com.abiquo.server.core.infrastructure.storage.DiskManagementDto;
import com.abiquo.server.core.infrastructure.storage.VolumeManagementDto;
import com.google.inject.TypeLiteral;

/**
 * Tests annotation parsing of {@code CloudAsyncClient}
 * 
 * @author Ignasi Barrera
 * @author Francesc Montserrat
 */
@Test(groups = "unit")
public class CloudAsyncClientTest extends BaseAbiquoAsyncClientTest<CloudAsyncClient>
{
    /*********************** Virtual Datacenter ***********************/

    public void testListVirtualDatacentersParams() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("listVirtualDatacenters",
                VirtualDatacenterOptions.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, VirtualDatacenterOptions.builder().datacenterId(1)
                .enterpriseId(1).build());

        assertRequestLineEquals(request,
            "GET http://localhost/api/cloud/virtualdatacenters?datacenter=1&enterprise=1 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testListVirtualDatacentersNoParams() throws SecurityException,
        NoSuchMethodException, IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("listVirtualDatacenters",
                VirtualDatacenterOptions.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, VirtualDatacenterOptions.builder().build());

        assertRequestLineEquals(request,
            "GET http://localhost/api/cloud/virtualdatacenters HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testCreateVirtualDatacenter() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("createVirtualDatacenter", VirtualDatacenterDto.class,
                DatacenterDto.class, EnterpriseDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualDatacenterPost(),
                InfrastructureResources.datacenterPut(), EnterpriseResources.enterprisePut());

        assertRequestLineEquals(request,
            "POST http://localhost/api/cloud/virtualdatacenters?enterprise=1&datacenter=1 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, withHeader(CloudResources.virtualDatacenterPostPayload()),
            "application/xml", false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testGetVirtualDatacenter() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method = CloudAsyncClient.class.getMethod("getVirtualDatacenter", Integer.class);
        GeneratedHttpRequest<CloudAsyncClient> request = processor.createRequest(method, 1);

        assertRequestLineEquals(request,
            "GET http://localhost/api/cloud/virtualdatacenters/1 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, ReturnNullOnNotFoundOr404.class);

        checkFilters(request);
    }

    public void testUpdateVirtualDatacenter() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("updateVirtualDatacenter", VirtualDatacenterDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualDatacenterPut());

        assertRequestLineEquals(request,
            "PUT http://localhost/api/cloud/virtualdatacenters/1 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, withHeader(CloudResources.virtualDatacenterPutPayload()),
            "application/xml", false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testDeleteVirtualDatacenter() throws SecurityException, NoSuchMethodException
    {
        Method method =
            CloudAsyncClient.class.getMethod("deleteVirtualDatacenter", VirtualDatacenterDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualDatacenterPut());

        assertRequestLineEquals(request,
            "DELETE http://localhost/api/cloud/virtualdatacenters/1 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ReleasePayloadAndReturn.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    /*********************** Ips ***********************/

    public void testListAvailablePublicIpsWithOptions() throws SecurityException,
        NoSuchMethodException, IOException
    {
        IpOptions options = IpOptions.builder().limit(5).build();
        Method method =
            CloudAsyncClient.class.getMethod("listAvailablePublicIps", VirtualDatacenterDto.class,
                IpOptions.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualDatacenterPut(), options);

        assertRequestLineEquals(request,
            "GET http://localhost/api/cloud/virtualdatacenters/1/publicips/topurchase?limit=5 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testListPurchasedPublicIpsWithOptions() throws SecurityException,
        NoSuchMethodException, IOException
    {
        IpOptions options = IpOptions.builder().limit(5).build();
        Method method =
            CloudAsyncClient.class.getMethod("listPurchasedPublicIps", VirtualDatacenterDto.class,
                IpOptions.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualDatacenterPut(), options);

        assertRequestLineEquals(request,
            "GET http://localhost/api/cloud/virtualdatacenters/1/publicips/purchased?limit=5 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testPurchasePublicIp() throws SecurityException, NoSuchMethodException, IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("purchasePublicIp", IpPoolManagementDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, NetworkResources.publicIpToPurchase());

        assertRequestLineEquals(request,
            "PUT http://localhost/api/cloud/virtualdatacenters/5/publicips/purchased/1 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testReleasePublicIp() throws SecurityException, NoSuchMethodException, IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("releasePublicIp", IpPoolManagementDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, NetworkResources.publicIpToRelease());

        assertRequestLineEquals(request,
            "PUT http://localhost/api/cloud/virtualdatacenters/5/publicips/topurchase/1 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    /*********************** Storage Tiers ***********************/

    public void testListStorageTiers() throws SecurityException, NoSuchMethodException, IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("listStorageTiers", VirtualDatacenterDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualDatacenterPut());

        assertRequestLineEquals(request,
            "GET http://localhost/api/cloud/virtualdatacenters/1/tiers HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testGetStorageTier() throws SecurityException, NoSuchMethodException, IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("getStorageTier", VirtualDatacenterDto.class,
                Integer.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualDatacenterPut(), 1);

        assertRequestLineEquals(request,
            "GET http://localhost/api/cloud/virtualdatacenters/1/tiers/1 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, ReturnNullOnNotFoundOr404.class);

        checkFilters(request);
    }

    public void testGetDefaultNetwork() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("getDefaultNetwork", VirtualDatacenterDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualDatacenterPut());

        assertRequestLineEquals(request,
            "GET http://localhost/api/cloud/virtualdatacenters/1/privatenetworks/1 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testSetDefaultNetworkInternal() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("setDefaultNetwork", VirtualDatacenterDto.class,
                VLANNetworkDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualDatacenterPut(),
                NetworkResources.privateNetworkPut());

        RESTLink netLink = NetworkResources.privateNetworkPut().getEditLink();

        assertRequestLineEquals(request,
            "PUT http://localhost/api/cloud/virtualdatacenters/1/action/defaultvlan HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, withHeader("<links><link href=\"" + netLink.getHref()
            + "\" rel=\"internalnetwork\"/></links>"), "application/xml", false);

        assertResponseParserClassEquals(method, request, ReleasePayloadAndReturn.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testSetDefaultNetworkExternal() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("setDefaultNetwork", VirtualDatacenterDto.class,
                VLANNetworkDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualDatacenterPut(),
                NetworkResources.externalNetworkPut());

        RESTLink netLink = NetworkResources.externalNetworkPut().getEditLink();

        assertRequestLineEquals(request,
            "PUT http://localhost/api/cloud/virtualdatacenters/1/action/defaultvlan HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, withHeader("<links><link href=\"" + netLink.getHref()
            + "\" rel=\"externalnetwork\"/></links>"), "application/xml", false);

        assertResponseParserClassEquals(method, request, ReleasePayloadAndReturn.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    /*********************** Private Network ***********************/

    public void testListPrivateNetworks() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("listPrivateNetworks", VirtualDatacenterDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualDatacenterPut());

        assertRequestLineEquals(request,
            "GET http://localhost/api/cloud/virtualdatacenters/1/privatenetworks HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testGetPrivateNetwork() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("getPrivateNetwork", VirtualDatacenterDto.class,
                Integer.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualDatacenterPut(), 1);

        assertRequestLineEquals(request,
            "GET http://localhost/api/cloud/virtualdatacenters/1/privatenetworks/1 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, ReturnNullOnNotFoundOr404.class);

        checkFilters(request);
    }

    public void testCreatePrivateNetwork() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("createPrivateNetwork", VirtualDatacenterDto.class,
                VLANNetworkDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualDatacenterPut(),
                NetworkResources.vlanPost());

        assertRequestLineEquals(request,
            "POST http://localhost/api/cloud/virtualdatacenters/1/privatenetworks HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, withHeader(NetworkResources.vlanNetworkPostPayload()),
            "application/xml", false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testUpdatePrivateNetwork() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("updatePrivateNetwork", VLANNetworkDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, NetworkResources.privateNetworkPut());

        assertRequestLineEquals(request,
            "PUT http://localhost/api/cloud/virtualdatacenters/1/privatenetworks/1 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, withHeader(NetworkResources.privateNetworkPutPayload()),
            "application/xml", false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testDeletePrivateNetwork() throws SecurityException, NoSuchMethodException
    {
        Method method =
            CloudAsyncClient.class.getMethod("deletePrivateNetwork", VLANNetworkDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, NetworkResources.privateNetworkPut());

        assertRequestLineEquals(request,
            "DELETE http://localhost/api/cloud/virtualdatacenters/1/privatenetworks/1 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ReleasePayloadAndReturn.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    /*********************** Private Network IPs ***********************/

    public void testListPrivateNetworkIps() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("listPrivateNetworkIps", VLANNetworkDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, NetworkResources.privateNetworkPut());

        assertRequestLineEquals(request,
            "GET http://localhost/api/cloud/virtualdatacenters/1/privatenetworks/1/ips HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testListPrivateNetworkIpsWithOptions() throws SecurityException,
        NoSuchMethodException, IOException
    {
        IpOptions options = IpOptions.builder().startWith(10).build();
        Method method =
            CloudAsyncClient.class.getMethod("listPrivateNetworkIps", VLANNetworkDto.class,
                IpOptions.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, NetworkResources.privateNetworkPut(), options);

        assertRequestLineEquals(request,
            "GET http://localhost/api/cloud/virtualdatacenters/1/privatenetworks/1/ips?startwith=10 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    /*********************** Attached Nic ***********************/

    public void testCreateNicFromIp() throws SecurityException, NoSuchMethodException, IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("createNic", VirtualMachineDto.class,
                IpPoolManagementDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualMachinePut(),
                NetworkResources.privateIpPut());

        RESTLink ipLink = NetworkResources.privateIpPut().searchLink("self");

        assertRequestLineEquals(
            request,
            "POST http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/virtualmachines/1/network/nics HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, withHeader("<links><link href=\"" + ipLink.getHref()
            + "\" rel=\"" + ipLink.getTitle() + "\"/></links>"), "application/xml", false);

        assertResponseParserClassEquals(method, request, ReturnTaskReferenceOrNull.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testCreateNicFromUnmanagedNetwork() throws SecurityException,
        NoSuchMethodException, IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("createNic", VirtualMachineDto.class,
                VLANNetworkDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualMachinePut(),
                NetworkResources.unmanagedNetworkPut());

        RESTLink ipsLink = NetworkResources.unmanagedNetworkPut().searchLink("ips");

        assertRequestLineEquals(
            request,
            "POST http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/virtualmachines/1/network/nics HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, withHeader("<links><link href=\"" + ipsLink.getHref()
            + "\" rel=\"unmanagedip\"/></links>"), "application/xml", false);

        assertResponseParserClassEquals(method, request, ReturnTaskReferenceOrNull.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testListAttachedNics() throws SecurityException, NoSuchMethodException, IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("listAttachedNics", VirtualMachineDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualMachinePut());

        assertRequestLineEquals(
            request,
            "GET http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/virtualmachines/1/network/nics HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testDeleteNic() throws SecurityException, NoSuchMethodException
    {
        Method method = CloudAsyncClient.class.getMethod("deleteNic", NicDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, NetworkResources.nicPut());

        assertRequestLineEquals(
            request,
            "DELETE http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/virtualmachines/1/network/nics/1 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ReturnTaskReferenceOrNull.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testReplaceNics() throws SecurityException, NoSuchMethodException, IOException
    {
        IpPoolManagementDto first = NetworkResources.privateIpPut();
        IpPoolManagementDto second = NetworkResources.privateIpPut();
        second.searchLink("self").setHref(second.searchLink("self").getHref() + "second");

        Method method =
            CloudAsyncClient.class.getMethod("replaceNics", VirtualMachineDto.class,
                IpPoolManagementDto[].class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualMachinePut(),
                new IpPoolManagementDto[] {first, second});

        RESTLink selfLink = first.searchLink("self");
        assertRequestLineEquals(
            request,
            "PUT http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/virtualmachines/1/network/nics HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, withHeader("<links><link href=\"" + selfLink.getHref()
            + "\" rel=\"" + selfLink.getTitle() + "\"/><link href=\"" + selfLink.getHref()
            + "second\" rel=\"" + selfLink.getTitle() + "\"/></links>"), "application/xml", false);

        assertResponseParserClassEquals(method, request, ReturnTaskReferenceOrNull.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    /*********************** Virtual Appliance ***********************/

    public void testListVirtualAppliances() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("listVirtualAppliances", VirtualDatacenterDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualDatacenterPut());

        assertRequestLineEquals(request,
            "GET http://localhost/api/cloud/virtualdatacenters/1/virtualappliances HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testGetVirtualAppliance() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("getVirtualAppliance", VirtualDatacenterDto.class,
                Integer.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualDatacenterPut(), 1);

        assertRequestLineEquals(request,
            "GET http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, ReturnNullOnNotFoundOr404.class);

        checkFilters(request);
    }

    public void testGetVirtualApplianceState() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("getVirtualApplianceState", VirtualApplianceDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualAppliancePut());

        assertRequestLineEquals(request,
            "GET http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/state HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testCreateVirtualAppliance() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("createVirtualAppliance", VirtualDatacenterDto.class,
                VirtualApplianceDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualDatacenterPut(),
                CloudResources.virtualAppliancePost());

        assertRequestLineEquals(request,
            "POST http://localhost/api/cloud/virtualdatacenters/1/virtualappliances HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, withHeader(CloudResources.virtualAppliancePostPayload()),
            "application/xml", false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testUpdateVirtualAppliance() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("updateVirtualAppliance", VirtualApplianceDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualAppliancePut());

        assertRequestLineEquals(request,
            "PUT http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, withHeader(CloudResources.virtualAppliancePutPayload()),
            "application/xml", false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testDeleteVirtualAppliance() throws SecurityException, NoSuchMethodException
    {
        Method method =
            CloudAsyncClient.class.getMethod("deleteVirtualAppliance", VirtualApplianceDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualAppliancePut());

        assertRequestLineEquals(request,
            "DELETE http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ReleasePayloadAndReturn.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testDeployVirtualAppliance() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("deployVirtualAppliance", VirtualApplianceDto.class,
                VirtualMachineTaskDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualAppliancePut(),
                CloudResources.deployOptions());

        assertRequestLineEquals(request,
            "POST http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/action/deploy HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, withHeader(CloudResources.deployPayload()), "application/xml",
            false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testUndeployVirtualAppliance() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("undeployVirtualAppliance", VirtualApplianceDto.class,
                VirtualMachineTaskDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualAppliancePut(),
                CloudResources.undeployOptions());

        assertRequestLineEquals(request,
            "POST http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/action/undeploy HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, withHeader(CloudResources.undeployPayload()),
            "application/xml", false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    /*********************** Virtual Machine ***********************/

    public void testListVirtualMachines() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("listVirtualMachines", VirtualApplianceDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualAppliancePut());

        assertRequestLineEquals(request,
            "GET http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/virtualmachines HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testGetVirtualMachine() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("getVirtualMachine", VirtualApplianceDto.class,
                Integer.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualAppliancePut(), 1);

        assertRequestLineEquals(
            request,
            "GET http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/virtualmachines/1 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, ReturnNullOnNotFoundOr404.class);

        checkFilters(request);
    }

    public void testCreateVirtualMachine() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("createVirtualMachine", VirtualApplianceDto.class,
                VirtualMachineDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualAppliancePut(),
                CloudResources.virtualMachinePost());

        assertRequestLineEquals(request,
            "POST http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/virtualmachines HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, withHeader(CloudResources.virtualMachinePostPayload()),
            "application/xml", false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testUpdateVirtualMachine() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("updateVirtualMachine", VirtualMachineDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualMachinePut());

        assertRequestLineEquals(
            request,
            "PUT http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/virtualmachines/1 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, withHeader(CloudResources.virtualMachinePutPayload()),
            "application/xml", false);

        assertResponseParserClassEquals(method, request, ReturnTaskReferenceOrNull.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testChangeVirtualMachineState() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("changeVirtualMachineState", VirtualMachineDto.class,
                VirtualMachineStateDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualMachinePut(),
                CloudResources.virtualMachineState());

        assertRequestLineEquals(
            request,
            "PUT http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/virtualmachines/1/state HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, withHeader(CloudResources.virtualMachineStatePayload()),
            "application/xml", false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testDeleteVirtualMachine() throws SecurityException, NoSuchMethodException
    {
        Method method =
            CloudAsyncClient.class.getMethod("deleteVirtualMachine", VirtualMachineDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualMachinePut());

        assertRequestLineEquals(
            request,
            "DELETE http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/virtualmachines/1 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ReleasePayloadAndReturn.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testGetVirtualMachineState() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("getVirtualMachineState", VirtualMachineDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualMachinePut());

        assertRequestLineEquals(
            request,
            "GET http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/virtualmachines/1/state HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testDeployVirtualMachine() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("deployVirtualMachine", VirtualMachineDto.class,
                VirtualMachineTaskDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualMachinePut(),
                CloudResources.deployOptions());

        assertRequestLineEquals(
            request,
            "POST http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/virtualmachines/1/action/deploy HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, withHeader(CloudResources.deployPayload()), "application/xml",
            false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testUndeployVirtualMachine() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("undeployVirtualMachine", VirtualMachineDto.class,
                VirtualMachineTaskDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualMachinePut(),
                CloudResources.undeployOptions());

        assertRequestLineEquals(
            request,
            "POST http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/virtualmachines/1/action/undeploy HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, withHeader(CloudResources.undeployPayload()),
            "application/xml", false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testListNetworkConfigurations() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("listNetworkConfigurations", VirtualMachineDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualMachinePut());

        assertRequestLineEquals(
            request,
            "GET http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/virtualmachines/1/network/configurations HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testSetGatewayNetwork() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("setGatewayNetwork", VirtualMachineDto.class,
                VLANNetworkDto.class);

        VirtualMachineDto vm = CloudResources.virtualMachinePut();
        VLANNetworkDto network = NetworkResources.privateNetworkPut();

        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, vm, network);

        String configLink = vm.searchLink("configurations").getHref() + "/" + network.getId();

        assertRequestLineEquals(
            request,
            "PUT http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/virtualmachines/1/network/configurations HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, withHeader("<links><link href=\"" + configLink
            + "\" rel=\"network_configuration\"/></links>"), "application/xml", false);

        assertResponseParserClassEquals(method, request, ReleasePayloadAndReturn.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    /*********************** Virtual Machine Template ***********************/

    public void testGetVirtualMachineTemplate() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("getVirtualMachineTemplate", VirtualMachineDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualMachinePut());

        assertRequestLineEquals(
            request,
            "GET http://localhost/api/admin/enterprises/1/datacenterrepositories/1/virtualmachinetemplates/1 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testListAttachedVolumes() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("listAttachedVolumes", VirtualMachineDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualMachinePut());

        assertRequestLineEquals(
            request,
            "GET http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/virtualmachines/1/storage/volumes HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testDetachAllVolumes() throws SecurityException, NoSuchMethodException, IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("detachAllVolumes", VirtualMachineDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualMachinePut());

        assertRequestLineEquals(
            request,
            "DELETE http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/virtualmachines/1/storage/volumes HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ReturnTaskReferenceOrNull.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testReplaceVolumes() throws SecurityException, NoSuchMethodException, IOException
    {
        VolumeManagementDto first = CloudResources.volumePut();
        VolumeManagementDto second = CloudResources.volumePut();
        second.getEditLink().setHref(second.getEditLink().getHref() + "second");

        Method method =
            CloudAsyncClient.class.getMethod("replaceVolumes", VirtualMachineDto.class,
                VolumeManagementDto[].class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualMachinePut(),
                new VolumeManagementDto[] {first, second});

        String editLink = CloudResources.volumePut().getEditLink().getHref();
        assertRequestLineEquals(
            request,
            "PUT http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/virtualmachines/1/storage/volumes HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, withHeader("<links><link href=\"" + editLink
            + "\" rel=\"volume\"/><link href=\"" + editLink + "second\" rel=\"volume\"/></links>"),
            "application/xml", false);

        assertResponseParserClassEquals(method, request, ReturnTaskReferenceOrNull.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testListAttachedHardDisks() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("listAttachedHardDisks", VirtualMachineDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualMachinePut());

        assertRequestLineEquals(
            request,
            "GET http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/virtualmachines/1/storage/disks HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testDetachAllHardDisks() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("detachAllHardDisks", VirtualMachineDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualMachinePut());

        assertRequestLineEquals(
            request,
            "DELETE http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/virtualmachines/1/storage/disks HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ReturnTaskReferenceOrNull.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testReplaceHardDisks() throws SecurityException, NoSuchMethodException, IOException
    {
        DiskManagementDto first = CloudResources.hardDiskPut();
        DiskManagementDto second = CloudResources.hardDiskPut();
        second.getEditLink().setHref(second.getEditLink().getHref() + "second");

        Method method =
            CloudAsyncClient.class.getMethod("replaceHardDisks", VirtualMachineDto.class,
                DiskManagementDto[].class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualMachinePut(),
                new DiskManagementDto[] {first, second});

        String editLink = CloudResources.hardDiskPut().getEditLink().getHref();
        assertRequestLineEquals(
            request,
            "PUT http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/virtualmachines/1/storage/disks HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, withHeader("<links><link href=\"" + editLink
            + "\" rel=\"disk\"/><link href=\"" + editLink + "second\" rel=\"disk\"/></links>"),
            "application/xml", false);

        assertResponseParserClassEquals(method, request, ReturnTaskReferenceOrNull.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    /*********************** Hard disks ***********************/

    public void testListHardDisks() throws SecurityException, NoSuchMethodException, IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("listHardDisks", VirtualDatacenterDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualDatacenterPut());

        assertRequestLineEquals(request,
            "GET http://localhost/api/cloud/virtualdatacenters/1/disks HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testGetHardDisk() throws SecurityException, NoSuchMethodException, IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("getHardDisk", VirtualDatacenterDto.class,
                Integer.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualDatacenterPut(), 1);

        assertRequestLineEquals(request,
            "GET http://localhost/api/cloud/virtualdatacenters/1/disks/1 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, ReturnNullOnNotFoundOr404.class);

        checkFilters(request);
    }

    public void testCreateHardDisk() throws SecurityException, NoSuchMethodException, IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("createHardDisk", VirtualDatacenterDto.class,
                DiskManagementDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualDatacenterPut(),
                CloudResources.hardDiskPost());

        assertRequestLineEquals(request,
            "POST http://localhost/api/cloud/virtualdatacenters/1/disks HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, withHeader(CloudResources.hardDiskPostPayload()),
            "application/xml", false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testDeleteHardDisk() throws SecurityException, NoSuchMethodException, IOException
    {
        Method method = CloudAsyncClient.class.getMethod("deleteHardDisk", DiskManagementDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.hardDiskPut());

        assertRequestLineEquals(request,
            "DELETE http://localhost/api/cloud/virtualdatacenters/1/disks/1 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ReleasePayloadAndReturn.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    /*********************** Volumes ***********************/

    public void testListVolumes() throws SecurityException, NoSuchMethodException, IOException
    {
        Method method = CloudAsyncClient.class.getMethod("listVolumes", VirtualDatacenterDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualDatacenterPut());

        assertRequestLineEquals(request,
            "GET http://localhost/api/cloud/virtualdatacenters/1/volumes HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testListVolumesWithOptions() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("listVolumes", VirtualDatacenterDto.class,
                VolumeOptions.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualDatacenterPut(), VolumeOptions
                .builder().onlyAvailable(true).build());

        assertRequestLineEquals(request,
            "GET http://localhost/api/cloud/virtualdatacenters/1/volumes?available=true HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testListVolumesWithFilterOptions() throws SecurityException, NoSuchMethodException,
        IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("listVolumes", VirtualDatacenterDto.class,
                VolumeOptions.class);

        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualDatacenterPut(), VolumeOptions
                .builder().has("vol").orderBy(OrderBy.NAME).ascendant(true).build());

        assertRequestLineEquals(request,
            "GET http://localhost/api/cloud/virtualdatacenters/1/volumes?has=vol&by=name&asc=true HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testGetVolume() throws SecurityException, NoSuchMethodException, IOException
    {
        Method method =
            CloudAsyncClient.class
                .getMethod("getVolume", VirtualDatacenterDto.class, Integer.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualDatacenterPut(), 1);

        assertRequestLineEquals(request,
            "GET http://localhost/api/cloud/virtualdatacenters/1/volumes/1 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, ReturnNullOnNotFoundOr404.class);

        checkFilters(request);
    }

    public void testCreateVolume() throws SecurityException, NoSuchMethodException, IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("createVolume", VirtualDatacenterDto.class,
                VolumeManagementDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.virtualDatacenterPut(),
                CloudResources.volumePost());

        assertRequestLineEquals(request,
            "POST http://localhost/api/cloud/virtualdatacenters/1/volumes HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, withHeader(CloudResources.volumePostPayload()),
            "application/xml", false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testUpdateVolume() throws SecurityException, NoSuchMethodException, IOException
    {
        Method method = CloudAsyncClient.class.getMethod("updateVolume", VolumeManagementDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.volumePut());

        assertRequestLineEquals(request,
            "PUT http://localhost/api/cloud/virtualdatacenters/1/volumes/1 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, withHeader(CloudResources.volumePutPayload()),
            "application/xml", false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testDeleteVolume() throws SecurityException, NoSuchMethodException, IOException
    {
        Method method = CloudAsyncClient.class.getMethod("deleteVolume", VolumeManagementDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.volumePut());

        assertRequestLineEquals(request,
            "DELETE http://localhost/api/cloud/virtualdatacenters/1/volumes/1 HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, null, null, false);

        assertResponseParserClassEquals(method, request, ReleasePayloadAndReturn.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, null);

        checkFilters(request);
    }

    public void testMoveVolume() throws SecurityException, NoSuchMethodException, IOException
    {
        Method method =
            CloudAsyncClient.class.getMethod("moveVolume", VolumeManagementDto.class,
                VirtualDatacenterDto.class);
        GeneratedHttpRequest<CloudAsyncClient> request =
            processor.createRequest(method, CloudResources.volumePut(),
                CloudResources.virtualDatacenterPut());

        assertRequestLineEquals(request,
            "POST http://localhost/api/cloud/virtualdatacenters/1/volumes/1/action/move HTTP/1.1");
        assertNonPayloadHeadersEqual(request, "Accept: application/xml\n");
        assertPayloadEquals(request, withHeader(CloudResources.virtualDatacenterRefPayload()),
            "application/xml", false);

        assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
        assertSaxResponseParserClassEquals(method, null);
        assertExceptionParserClassEquals(method, ReturnMovedVolume.class);

        checkFilters(request);
    }

    @Override
    protected TypeLiteral<RestAnnotationProcessor<CloudAsyncClient>> createTypeLiteral()
    {
        return new TypeLiteral<RestAnnotationProcessor<CloudAsyncClient>>()
        {
        };
    }
}
