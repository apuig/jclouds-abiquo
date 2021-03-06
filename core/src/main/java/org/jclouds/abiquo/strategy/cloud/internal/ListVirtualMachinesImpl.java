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

package org.jclouds.abiquo.strategy.cloud.internal;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Iterables.filter;
import static org.jclouds.abiquo.domain.DomainWrapper.wrap;
import static org.jclouds.concurrent.FutureIterables.transformParallel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.annotation.Resource;
import javax.inject.Named;
import javax.inject.Singleton;

import org.jclouds.Constants;
import org.jclouds.abiquo.AbiquoContext;
import org.jclouds.abiquo.domain.DomainWrapper;
import org.jclouds.abiquo.domain.cloud.VirtualAppliance;
import org.jclouds.abiquo.domain.cloud.VirtualMachine;
import org.jclouds.abiquo.strategy.cloud.ListVirtualMachines;
import org.jclouds.logging.Logger;

import com.abiquo.server.core.cloud.VirtualMachineDto;
import com.abiquo.server.core.cloud.VirtualMachinesDto;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.inject.Inject;

/**
 * List virtual machines in each virtual datacenter and each virtual appliance.
 * 
 * @author Ignasi Barrera
 */
@Singleton
public class ListVirtualMachinesImpl implements ListVirtualMachines
{
    protected AbiquoContext context;

    protected final ExecutorService userExecutor;

    @Resource
    protected Logger logger = Logger.NULL;

    @Inject(optional = true)
    @Named(Constants.PROPERTY_REQUEST_TIMEOUT)
    protected Long maxTime;

    @Inject
    ListVirtualMachinesImpl(final AbiquoContext context,
        @Named(Constants.PROPERTY_USER_THREADS) final ExecutorService userExecutor)
    {
        super();
        this.context = checkNotNull(context, "context");
        this.userExecutor = checkNotNull(userExecutor, "userExecutor");
    }

    @Override
    public Iterable<VirtualMachine> execute()
    {
        // Find virtual machines in concurrent requests
        Iterable<VirtualAppliance> vapps = context.getCloudService().listVirtualAppliances();
        Iterable<VirtualMachineDto> vms = listConcurrentVirtualMachines(vapps);

        return wrap(context, VirtualMachine.class, vms);
    }

    @Override
    public Iterable<VirtualMachine> execute(final Predicate<VirtualMachine> selector)
    {
        return filter(execute(), selector);
    }

    private Iterable<VirtualMachineDto> listConcurrentVirtualMachines(
        final Iterable<VirtualAppliance> vapps)
    {
        Iterable<VirtualMachinesDto> vms =
            transformParallel(vapps, new Function<VirtualAppliance, Future<VirtualMachinesDto>>()
            {
                @Override
                public Future<VirtualMachinesDto> apply(final VirtualAppliance input)
                {
                    return context.getAsyncApi().getCloudClient()
                        .listVirtualMachines(input.unwrap());
                }
            }, userExecutor, maxTime, logger, "getting virtual machines");

        return DomainWrapper.join(vms);
    }

}
