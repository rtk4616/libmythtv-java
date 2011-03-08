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
import java.util.List;

import org.apache.commons.lang3.Pair;
import org.syphr.mythtv.proto.ProtocolException;
import org.syphr.mythtv.proto.SocketManager;
import org.syphr.mythtv.proto.types.SeekOrigin;

/* default */class Command63QueryFileTransferSeek implements Command<Long>
{
    private final String message;

    public Command63QueryFileTransferSeek(int socketNumber,
                                          long position,
                                          SeekOrigin origin,
                                          long curPosition) throws ProtocolException
    {
        Pair<Integer, Integer> splitPosition = ProtocolUtils.splitLong(position);
        Pair<Integer, Integer> splitCurPosition = ProtocolUtils.splitLong(curPosition);

        message = Protocol63Utils.getProtocolValue("QUERY_FILETRANSFER "
                                                           + socketNumber,
                                                   "SEEK",
                                                   String.valueOf(splitPosition.getLeftElement()),
                                                   String.valueOf(splitPosition.getRightElement()),
                                                   String.valueOf(Protocol63Utils.getSeekOrigin(origin)),
                                                   String.valueOf(splitCurPosition.getLeftElement()),
                                                   String.valueOf(splitCurPosition.getRightElement()));
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

        try
        {
            return ProtocolUtils.combineInts(Integer.parseInt(args.get(0)),
                                               Integer.parseInt(args.get(1)));
        }
        catch (NumberFormatException e)
        {
            throw new ProtocolException(response, e);
        }
    }
}
