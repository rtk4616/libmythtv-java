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

import org.syphr.mythtv.proto.CommandException;
import org.syphr.mythtv.proto.ProtocolException;
import org.syphr.mythtv.proto.ProtocolException.Direction;
import org.syphr.mythtv.proto.data.Channel;

/* default */class Command63QueryRecorderSpawnLiveTv extends AbstractCommand63QueryRecorder<Void>
{
    private final String chainId;
    private final boolean pip;
    private final Channel startChannel;

    public Command63QueryRecorderSpawnLiveTv(int recorderId,
                                             String chainId,
                                             boolean pip,
                                             Channel startChannel)
    {
        super(recorderId);

        this.chainId = chainId;
        this.pip = pip;
        this.startChannel = startChannel;
    }

    @Override
    protected String getSubCommand() throws ProtocolException
    {
        return Protocol63Utils.combineArguments("SPAWN_LIVETV",
                                                chainId,
                                                pip ? "1" : "0",
                                                startChannel.getNumber());
    }

    @Override
    protected Void parseResponse(String response) throws ProtocolException, CommandException
    {
        if (!"ok".equals(response))
        {
            throw new ProtocolException(response, Direction.RECEIVE);
        }

        return null;
    }
}
