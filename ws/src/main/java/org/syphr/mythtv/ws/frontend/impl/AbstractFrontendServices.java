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
package org.syphr.mythtv.ws.frontend.impl;

import java.io.IOException;

import org.syphr.mythtv.ws.ServiceVersionException;
import org.syphr.mythtv.ws.frontend.FrontendService;
import org.syphr.mythtv.ws.frontend.FrontendServices;

public abstract class AbstractFrontendServices implements FrontendServices
{
    private static final int DEFAULT_PORT = 6547;

    private FrontendService frontendService;

    protected int getPort(int port)
    {
        if (port <= 0)
        {
            return DEFAULT_PORT;
        }

        return port;
    }

    @Override
    public void configure(String host) throws ServiceVersionException, IOException
    {
        configure(host, 0);
    }

    @Override
    public void removeConfiguration()
    {
        setFrontendService(null);
    }

    @Override
    public FrontendService getFrontendService()
    {
        return frontendService;
    }

    protected void setFrontendService(FrontendService frontendService)
    {
        this.frontendService = frontendService;
    }
}
