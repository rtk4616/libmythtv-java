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
package org.syphr.mythtv.control.impl;

import java.util.regex.Pattern;

import org.syphr.mythtv.util.socket.DefaultPacket;

/**
 * This class represents the lowest level socket communication between the client and a
 * MythTV frontend instance.
 *
 * @author Gregory P. Moyer
 */
/* default */class ControlPacket extends DefaultPacket
{
    private static final String READ_MESSAGE_TERMINATOR = Pattern.quote("\r\n# ");
    private static final String WRITE_MESSAGE_TERMINATOR = "\r\n";

    @Override
    public String getMessageTerminator()
    {
        return READ_MESSAGE_TERMINATOR;
    }
    
    @Override
    protected String buildMessage(String data)
    {
        return data.concat(WRITE_MESSAGE_TERMINATOR);
    }
}
