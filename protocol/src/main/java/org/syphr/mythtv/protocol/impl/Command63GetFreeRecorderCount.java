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
package org.syphr.mythtv.protocol.impl;

import java.io.IOException;

import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.exception.ProtocolException.Direction;
import org.syphr.mythtv.util.socket.AbstractCommand;
import org.syphr.mythtv.util.socket.SocketManager;

/* default */class Command63GetFreeRecorderCount extends AbstractCommand<Integer>
{
    @Override
    protected String getMessage() throws ProtocolException
    {
        return "GET_FREE_RECORDER_COUNT";
    }

    @Override
    public Integer send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(getMessage());

        try
        {
            int count = Integer.valueOf(response);

            if (count < 0)
            {
                throw new ProtocolException(response, Direction.RECEIVE);
            }

            return count;
        }
        catch (NumberFormatException e)
        {
            throw new ProtocolException(response, Direction.RECEIVE, e);
        }
    }
}
