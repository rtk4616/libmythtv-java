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
package org.syphr.mythtv.control;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syphr.mythtv.control.test.Utils;
import org.syphr.mythtv.control.types.JumpPoint;
import org.syphr.mythtv.test.Settings;
import org.syphr.prom.PropertiesManager;

public class ControlTest
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ControlTest.class);

    private static PropertiesManager<Settings> settings;
    private static Control control;

    @BeforeClass
    public static void setUpBeforeClass() throws IOException
    {
        settings = Settings.createSettings();
        control = Utils.connect(settings);
    }

    @AfterClass
    public static void tearDownAfterClass() throws IOException
    {
        control.exit();
    }

    @Test
    public void testJump() throws IOException
    {
        control.jump(JumpPoint.MYTH_VIDEO);
        control.jump(JumpPoint.MAIN_MENU);
    }
}