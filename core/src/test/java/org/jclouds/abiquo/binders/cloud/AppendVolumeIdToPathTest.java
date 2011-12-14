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

package org.jclouds.abiquo.binders.cloud;

import static org.testng.Assert.assertEquals;

import java.net.URI;

import org.jclouds.abiquo.functions.cloud.ParseVolumeId;
import org.jclouds.http.HttpRequest;
import org.testng.annotations.Test;

import com.abiquo.server.core.infrastructure.storage.VolumeManagementDto;

/**
 * Unit tests for the {@link AppendVolumeIdToPath} binder.
 * 
 * @author Ignasi Barrera
 */
@Test(groups = "unit")
public class AppendVolumeIdToPathTest
{
    @Test(expectedExceptions = NullPointerException.class)
    public void testGetValueWithNullInput()
    {
        AppendVolumeIdToPath binder = new AppendVolumeIdToPath(new ParseVolumeId());
        HttpRequest request =
            HttpRequest.builder().method("GET").endpoint(URI.create("http://localhost")).build();
        binder.getValue(request, null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testGetValueWithInvalidInput()
    {
        AppendVolumeIdToPath binder = new AppendVolumeIdToPath(new ParseVolumeId());
        HttpRequest request =
            HttpRequest.builder().method("GET").endpoint(URI.create("http://localhost")).build();
        binder.getValue(request, new Object());
    }

    public void testGetValue()
    {
        AppendVolumeIdToPath binder = new AppendVolumeIdToPath(new ParseVolumeId());
        HttpRequest request =
            HttpRequest.builder().method("GET").endpoint(URI.create("http://localhost")).build();

        VolumeManagementDto dto = new VolumeManagementDto();
        dto.setId(5);

        assertEquals(binder.getValue(request, dto), "5");
    }
}
