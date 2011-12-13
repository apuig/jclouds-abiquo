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

package org.jclouds.abiquo.domain.cloud;

import static com.google.common.base.Preconditions.checkNotNull;

import org.jclouds.abiquo.AbiquoContext;
import org.jclouds.abiquo.domain.DomainWrapper;
import org.jclouds.abiquo.domain.infrastructure.Tier;
import org.jclouds.abiquo.reference.ValidationErrors;
import org.jclouds.abiquo.reference.annotations.EnterpriseEdition;
import org.jclouds.abiquo.reference.rest.ParentLinkName;

import com.abiquo.model.enumerator.VolumeState;
import com.abiquo.server.core.cloud.VirtualDatacenterDto;
import com.abiquo.server.core.infrastructure.storage.TierDto;
import com.abiquo.server.core.infrastructure.storage.VolumeManagementDto;

/**
 * Adds high level functionality to {@link VolumeManagementDto}.
 * 
 * @author Ignasi Barrera
 * @see <a href="http://community.abiquo.com/display/ABI20/Volume+Resource">
 *      http://community.abiquo.com/display/ABI20/Volume+Resource</a>
 */
@EnterpriseEdition
public class Volume extends DomainWrapper<VolumeManagementDto>
{
    /** The default state for folumes. */
    public static final VolumeState DEFAULT_STATE = VolumeState.DETACHED;

    /** The virtual datacenter where the volume belongs. */
    // Package protected to allow navigation from children
    VirtualDatacenter virtualDatacenter;

    /** The tier where the volume belongs. */
    // Package protected to allow navigation from children
    Tier tier;

    /**
     * Constructor to be used only by the builder.
     */
    protected Volume(final AbiquoContext context, final VolumeManagementDto target)
    {
        super(context, target);
    }

    // Domain operations

    public void delete()
    {
        context.getApi().getCloudClient().deleteVolume(target);
        target = null;
    }

    public void save()
    {
        target = context.getApi().getCloudClient().createVolume(virtualDatacenter.unwrap(), target);
    }

    public void update()
    {
        target = context.getApi().getCloudClient().updateVolume(target);
    }

    // Parent access

    /**
     * @see <a
     *      href="http://community.abiquo.com/display/ABI20/Virtual+Datacenter+Resource#VirtualDatacenterResource-RetrieveaVirtualDatacenter">
     *      http://community.abiquo.com/display/ABI20/Virtual+Datacenter+Resource#VirtualDatacenterResource-RetrieveaVirtualDatacenter</a>
     */
    public VirtualDatacenter getVirtualDatacenter()
    {
        Integer virtualDatacenterId = target.getIdFromLink(ParentLinkName.VIRTUAL_DATACENTER);
        VirtualDatacenterDto dto =
            context.getApi().getCloudClient().getVirtualDatacenter(virtualDatacenterId);
        virtualDatacenter = wrap(context, VirtualDatacenter.class, dto);
        return virtualDatacenter;
    }

    /**
     * TODO javadoc link
     */
    public Tier getTier()
    {
        Integer tierId = target.getIdFromLink(ParentLinkName.TIER);
        TierDto dto =
            context.getApi().getCloudClient().getStorageTier(virtualDatacenter.unwrap(), tierId);
        tier = wrap(context, Tier.class, dto);
        return tier;
    }

    // Builder

    public static Builder builder(final AbiquoContext context,
        final VirtualDatacenter virtualDatacenter, final Tier tier)
    {
        return new Builder(context, virtualDatacenter, tier);
    }

    public static class Builder
    {
        private AbiquoContext context;

        private String name;

        private String description;

        private Long sizeInMb;

        private VirtualDatacenter virtualDatacenter;

        private Tier tier;

        public Builder(final AbiquoContext context, final VirtualDatacenter virtualDatacenter,
            final Tier tier)
        {
            super();
            checkNotNull(virtualDatacenter, ValidationErrors.NULL_RESOURCE
                + VirtualDatacenter.class);
            checkNotNull(virtualDatacenter, ValidationErrors.NULL_RESOURCE + Tier.class);
            this.virtualDatacenter = virtualDatacenter;
            this.context = context;
        }

        public Builder name(final String name)
        {
            this.name = name;
            return this;
        }

        public Builder description(final String description)
        {
            this.description = description;
            return this;
        }

        public Builder sizeInMb(final long sizeInMb)
        {
            this.sizeInMb = sizeInMb;
            return this;
        }

        public Volume build()
        {
            VolumeManagementDto dto = new VolumeManagementDto();
            dto.setName(name);
            dto.setDescription(description);
            dto.setSizeInMB(sizeInMb);
            dto.setState(DEFAULT_STATE.name());

            Volume volume = new Volume(context, dto);
            volume.virtualDatacenter = virtualDatacenter;
            volume.tier = tier;

            return volume;
        }
    }

    // Delegate methods

    public Integer getId()
    {
        return target.getId();
    }

    public String getState()
    {
        return target.getState();
    }

    public String getName()
    {
        return target.getName();
    }

    public void setName(final String name)
    {
        target.setName(name);
    }

    public long getSizeInMB()
    {
        return target.getSizeInMB();
    }

    public void setSizeInMB(final long sizeInMB)
    {
        target.setSizeInMB(sizeInMB);
    }

    public String getDescription()
    {
        return target.getDescription();
    }

    public void setDescription(final String description)
    {
        target.setDescription(description);
    }

}