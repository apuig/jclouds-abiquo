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

import org.jclouds.abiquo.internal.BaseAbiquoService;

import com.abiquo.server.core.infrastructure.DatacenterDto;
import com.google.common.base.Predicate;
import com.google.inject.ImplementedBy;

/**
 * Provides high level Abiquo operations.
 * 
 * @author Ignasi Barrera
 */
@ImplementedBy(BaseAbiquoService.class)
public interface AbiquoService
{
    /**
     * Get the Abiquo context.
     */
    AbiquoContext getContext();

    /**
     * Get the list of all datacenters.
     */
    Iterable<DatacenterDto> listDatacenters();

    /**
     * Get the list of datacenters matching the given filtger.
     */
    Iterable<DatacenterDto> listDatacenters(Predicate<DatacenterDto> filter);
}
