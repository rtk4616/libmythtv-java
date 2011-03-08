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
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.syphr.mythtv.proto.ProtocolException;
import org.syphr.mythtv.proto.SocketManager;
import org.syphr.mythtv.proto.data.Channel;

/* default */class Command63QueryBookmark implements Command<Long>
{
    private final String message;

    public Command63QueryBookmark(Channel channel, Date recStartTs)
    {
        StringBuilder builder = new StringBuilder();
        builder.append("QUERY_BOOKMARK");
        builder.append(' ');
        builder.append(channel.getId());
        builder.append(' ');
        builder.append(TimeUnit.MILLISECONDS.toSeconds(recStartTs.getTime()));

        message = builder.toString();
    }

    @Override
    public Long send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(message);
        List<String> args = Protocol63Utils.getArguments(response);
        if (args.size() != 2)
        {
            throw new ProtocolException(response);
        }

        return ProtocolUtils.combineInts(Integer.parseInt(args.get(0)),
                                           Integer.parseInt(args.get(1)));
    }
}
