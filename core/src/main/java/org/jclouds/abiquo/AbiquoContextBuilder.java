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

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Properties;

import org.jclouds.abiquo.config.AbiquoRestClientModule;
import org.jclouds.abiquo.config.ConfiguresEventBus;
import org.jclouds.abiquo.config.ConfiguresScheduler;
import org.jclouds.abiquo.config.EventBusModule;
import org.jclouds.abiquo.config.SchedulerModule;
import org.jclouds.rest.RestContextBuilder;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * Builds the Abiquo context.
 * 
 * @author Ignasi Barrera
 */
public class AbiquoContextBuilder extends RestContextBuilder<AbiquoClient, AbiquoAsyncClient>
{
    public AbiquoContextBuilder(final Properties props)
    {
        super(AbiquoClient.class, AbiquoAsyncClient.class, props);
    }

    @Override
    protected void addClientModule(final List<Module> modules)
    {
        modules.add(new AbiquoRestClientModule());
    }

    @Override
    public AbiquoContextBuilder withModules(final Iterable<Module> modules)
    {
        return (AbiquoContextBuilder) super.withModules(modules);
    }

    @Override
    public Injector buildInjector()
    {
        if (!isModulePresent(ConfiguresScheduler.class))
        {
            modules.add(new SchedulerModule());
        }
        if (!isModulePresent(ConfiguresEventBus.class))
        {
            modules.add(new EventBusModule());
        }

        return super.buildInjector();
    }

    @SuppressWarnings("unchecked")
    @Override
    public AbiquoContext buildContext()
    {
        Injector injector = buildInjector();
        return injector.getInstance(AbiquoContext.class);
    }

    private boolean isModulePresent(final Class< ? extends Annotation> annotatedWith)
    {
        return Iterables.any(modules, new Predicate<Module>()
        {
            @Override
            public boolean apply(final Module input)
            {
                return input.getClass().isAnnotationPresent(annotatedWith);
            }

        });
    }

}
