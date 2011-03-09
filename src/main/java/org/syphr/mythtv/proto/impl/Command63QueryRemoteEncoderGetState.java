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
package org.syphr.mythtv.proto.impl;

import java.io.IOException;

import org.syphr.mythtv.proto.ProtocolException;
import org.syphr.mythtv.proto.SocketManager;
import org.syphr.mythtv.proto.types.TvState;

/* default */class Command63QueryRemoteEncoderGetState extends AbstractCommand63QueryRemoteEncoder<TvState>
{
    public Command63QueryRemoteEncoderGetState(int recorderId)
    {
        super(recorderId);
    }

    @Override
    protected String getSubCommand() throws ProtocolException
    {
        return "GET_STATE";
    }

    @Override
    public TvState send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(getMessage());

        try
        {
            return Protocol63Utils.getTvState(Integer.parseInt(response));
        }
        catch (NumberFormatException e)
        {
            throw new ProtocolException(response, e);
        }
    }
}