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

package org.jclouds.abiquo.strategy.config.internal;

import static com.google.common.collect.Iterables.size;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.jclouds.abiquo.domain.config.SystemProperty;
import org.jclouds.abiquo.domain.config.options.PropertyOptions;
import org.jclouds.abiquo.environment.CloudTestEnvironment;
import org.jclouds.abiquo.predicates.config.SystemPropertyPredicates;
import org.jclouds.abiquo.strategy.BaseAbiquoStrategyLiveTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.common.base.Predicates;

/**
 * Live tests for the {@link ListPrivilegesImpl} strategy.
 * 
 * @author Ignasi Barrera
 * @author Francesc Montserrat
 */
@Test(groups = "live")
public class ListPropertiesTest extends BaseAbiquoStrategyLiveTest<CloudTestEnvironment>
{
    private ListPropertiesImpl strategy;

    @Override
    @BeforeClass(groups = "live")
    protected void setupStrategy()
    {
        this.strategy = context.getUtils().getInjector().getInstance(ListPropertiesImpl.class);
    }

    public void testExecute()
    {
        Iterable<SystemProperty> properties = strategy.execute();
        assertNotNull(properties);
        assertTrue(size(properties) > 0);
    }

    public void testExecuteWithOptions()
    {
        PropertyOptions options = PropertyOptions.builder().component("client").build();

        Iterable<SystemProperty> properties = strategy.execute(options);
        assertNotNull(properties);
        assertTrue(size(properties) > 0);
    }

    public void testExecutePredicateWithoutResults()
    {
        Iterable<SystemProperty> properties =
            strategy.execute(SystemPropertyPredicates.name("Cloud color"));
        assertNotNull(properties);
        assertEquals(size(properties), 0);
    }

    public void testExecutePredicateWithResults()
    {
        Iterable<SystemProperty> properties =
            strategy.execute(SystemPropertyPredicates
                .name("client.applibrary.ovfpackagesDownloadingProgressUpdateInterval"));
        assertNotNull(properties);
        assertEquals(size(properties), 1);
    }

    public void testExecuteNotPredicateWithResults()
    {
        Iterable<SystemProperty> properties =
            strategy.execute(Predicates.not(SystemPropertyPredicates
                .name("client.applibrary.ovfpackagesDownloadingProgressUpdateInterval")));

        Iterable<SystemProperty> allProperties = strategy.execute();

        assertNotNull(properties);
        assertNotNull(allProperties);
        assertEquals(size(properties), size(allProperties) - 1);
    }
}
