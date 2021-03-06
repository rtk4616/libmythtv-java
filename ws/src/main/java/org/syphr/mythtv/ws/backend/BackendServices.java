/*
 * Copyright 2011-2012 Gregory P. Moyer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.syphr.mythtv.ws.backend;

import java.io.IOException;

import org.syphr.mythtv.ws.ServiceVersionException;

public interface BackendServices
{
    public void configure(String host) throws ServiceVersionException, IOException;

    public void configure(String host, int port) throws ServiceVersionException, IOException;

    public void removeConfiguration();

    public CaptureService getCaptureService();

    public ChannelService getChannelService();

    public ContentService getContentService();

    public DvrService getDvrService();

    public GuideService getGuideService();

    public MythService getMythService();

    public VideoService getVideoService();
}
