/*
 * Copyright 2011 Gregory P. Moyer
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
package org.syphr.mythtv.protocol;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.syphr.mythtv.protocol.CommandException;
import org.syphr.mythtv.protocol.Protocol;
import org.syphr.mythtv.protocol.SocketManager;
import org.syphr.mythtv.protocol.test.Utils;
import org.syphr.mythtv.protocol.types.EventLevel;
import org.syphr.mythtv.test.Settings;
import org.syphr.prom.PropertiesManager;

public class SocketManagerTest
{
    private static PropertiesManager<Settings> settings;

    private static SocketManager socketManager;
    private static Protocol proto;

    @BeforeClass
    public static void setUpBeforeClass() throws IOException, CommandException
    {
        settings = Settings.createSettings();
        socketManager = Utils.connect(settings);
        proto = Utils.announceMonitor(settings, socketManager, EventLevel.NONE);
    }

    @AfterClass
    public static void tearDownAfterClass() throws IOException
    {
        proto.done();
        socketManager.disconnect();
    }

    @Test
    public void testSendAndWaitTimeout() throws IOException
    {
        /*
         * The response to this message does not parse as a long. It should be discarded
         * and not returned to the second command.
         */
        Assert.assertEquals("", socketManager.sendAndWait("QUERY_LOAD", 1, TimeUnit.NANOSECONDS));

        String uptime = socketManager.sendAndWait("QUERY_UPTIME");
        Assert.assertNotNull(uptime);
        Long.parseLong(uptime);
    }
}