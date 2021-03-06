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

import static org.jclouds.Constants.PROPERTY_API_VERSION;
import static org.jclouds.Constants.PROPERTY_BUILD_VERSION;
import static org.jclouds.Constants.PROPERTY_MAX_REDIRECTS;
import static org.jclouds.abiquo.reference.AbiquoConstants.ASYNC_TASK_MONITOR_DELAY;
import static org.jclouds.abiquo.reference.AbiquoConstants.MAX_SCHEDULER_THREADS;

import java.util.Properties;

import org.jclouds.PropertiesBuilder;

/**
 * Builds properties used in Abiquo clients.
 * 
 * @author Ignasi Barrera
 */
public class AbiquoPropertiesBuilder extends PropertiesBuilder
{
    public AbiquoPropertiesBuilder(final Properties properties)
    {
        super(properties);
    }

    @Override
    protected Properties defaultProperties()
    {
        Properties properties = super.defaultProperties();
        // Supported Abiquo version
        properties.setProperty(PROPERTY_API_VERSION, AbiquoAsyncClient.API_VERSION);
        properties.setProperty(PROPERTY_BUILD_VERSION, AbiquoAsyncClient.BUILD_VERSION);
        // By default redirects will be handled in the domain objects
        properties.setProperty(PROPERTY_MAX_REDIRECTS, "0");
        // The default polling delay between AsyncTask monitor requests
        properties.setProperty(ASYNC_TASK_MONITOR_DELAY, "5000");
        // The default number of concurrent scheduler threads to be used
        properties.setProperty(MAX_SCHEDULER_THREADS, "10");
        return properties;
    }

}
