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

package org.jclouds.abiquo.domain;

import static org.jclouds.abiquo.domain.DomainUtils.link;

import com.abiquo.model.rest.RESTLink;
import com.abiquo.model.transport.LinksDto;
import com.abiquo.server.core.infrastructure.network.IpPoolManagementDto;
import com.abiquo.server.core.infrastructure.network.NicDto;
import com.abiquo.server.core.infrastructure.network.VLANNetworkDto;

/**
 * Network domain utilities.
 * 
 * @author Ignasi Barrera
 * @author Francesc Montserrat
 */
public class NetworkResources
{
    public static VLANNetworkDto vlanPost()
    {
        VLANNetworkDto vlan = new VLANNetworkDto();
        vlan.setAddress("192.168.1.0");
        vlan.setDefaultNetwork(true);
        vlan.setName("DefaultNetwork");
        vlan.setGateway("192.168.1.1");
        vlan.setMask(24);

        return vlan;
    }

    public static IpPoolManagementDto privateIpPut()
    {
        IpPoolManagementDto ip = new IpPoolManagementDto();
        ip.setId(1);
        ip.setName("private ip");
        ip.setMac("00:58:5A:c0:C3:01");
        return ip;
    }

    public static NicDto nicPut()
    {
        NicDto nic = new NicDto();
        nic.setId(1);
        nic.setIp("123.123.123.123");
        nic.setMac("00:58:5A:c0:C3:01");
        nic
            .addLink(new RESTLink("edit",
                "http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/virtualmachines/1/network/nics/1"));

        return nic;
    }

    public static VLANNetworkDto privateNetworkPut()
    {
        VLANNetworkDto vlan = new VLANNetworkDto();
        vlan.setAddress("192.168.1.0");
        vlan.setDefaultNetwork(true);
        vlan.setName("DefaultNetwork");
        vlan.setGateway("192.168.1.1");
        vlan.setMask(24);
        vlan.addLink(new RESTLink("edit",
            "http://localhost/api/cloud/virtualdatacenters/1/privatenetworks/1"));
        vlan.addLink(new RESTLink("ips",
            "http://localhost/api/cloud/virtualdatacenters/1/privatenetworks/1/ips"));

        return vlan;
    }

    public static VLANNetworkDto publicNetworkPut()
    {
        VLANNetworkDto vlan = new VLANNetworkDto();
        vlan.setAddress("192.168.1.0");
        vlan.setDefaultNetwork(true);
        vlan.setName("PublicNetwork");
        vlan.setGateway("192.168.1.1");
        vlan.setMask(24);
        vlan.addLink(new RESTLink("edit", "http://localhost/api/admin/datacenters/1/network/1"));
        vlan.addLink(new RESTLink("ips", "http://localhost/api/admin/datacenters/1/network/1/ips"));

        return vlan;
    }

    public static String vlanNetworkPostPayload()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<network>");
        buffer.append("<address>192.168.1.0</address>");
        buffer.append("<defaultNetwork>true</defaultNetwork>");
        buffer.append("<gateway>192.168.1.1</gateway>");
        buffer.append("<mask>24</mask>");
        buffer.append("<name>DefaultNetwork</name>");
        buffer.append("</network>");
        return buffer.toString();
    }

    public static String privateNetworkPutPayload()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<network>");
        buffer.append(link("/cloud/virtualdatacenters/1/privatenetworks/1", "edit"));
        buffer.append(link("/cloud/virtualdatacenters/1/privatenetworks/1/ips", "ips"));
        buffer.append("<address>192.168.1.0</address>");
        buffer.append("<defaultNetwork>true</defaultNetwork>");
        buffer.append("<gateway>192.168.1.1</gateway>");
        buffer.append("<mask>24</mask>");
        buffer.append("<name>DefaultNetwork</name>");
        buffer.append("</network>");
        return buffer.toString();
    }

    public static String publicNetworkPutPayload()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<network>");
        buffer.append(link("/admin/datacenters/1/network/1", "edit"));
        buffer.append(link("/admin/datacenters/1/network/1/ips", "ips"));
        buffer.append("<address>192.168.1.0</address>");
        buffer.append("<defaultNetwork>true</defaultNetwork>");
        buffer.append("<gateway>192.168.1.1</gateway>");
        buffer.append("<mask>24</mask>");
        buffer.append("<name>PublicNetwork</name>");
        buffer.append("</network>");
        return buffer.toString();
    }

    public static String privateIpPutPayload()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<ipPoolManagement>");
        buffer.append("<available>false</available>");
        buffer.append("<id>1</id>");
        buffer.append("<mac>00:58:5A:c0:C3:01</mac>");
        buffer.append("<name>private ip</name>");
        buffer.append("<quarantine>false</quarantine>");
        buffer.append("</ipPoolManagement>");
        return buffer.toString();
    }

    public static String linksDtoPayload(final LinksDto dto)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<links>");
        for (RESTLink link : dto.getLinks())
        {
            buffer.append(link(link));
        }
        buffer.append("</links>");
        return buffer.toString();
    }
}
