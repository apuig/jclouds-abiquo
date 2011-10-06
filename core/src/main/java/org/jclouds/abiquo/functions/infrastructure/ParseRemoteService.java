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

package org.jclouds.abiquo.functions.infrastructure;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jclouds.abiquo.functions.ParseXMLWithJAXB;
import org.jclouds.abiquo.xml.XMLParser;

import com.abiquo.server.core.infrastructure.RemoteServiceDto;
import com.google.inject.TypeLiteral;

/**
 * Parses a remote service object.
 * 
 * @author Francesc Montserrat
 */
@Singleton
public class ParseRemoteService extends ParseXMLWithJAXB<RemoteServiceDto>
{
    @Inject
    public ParseRemoteService(final XMLParser xml, final TypeLiteral<RemoteServiceDto> type)
    {
        super(xml, type);
    }

}
