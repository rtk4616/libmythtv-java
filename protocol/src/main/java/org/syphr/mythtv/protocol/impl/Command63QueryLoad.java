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
import java.util.List;

import org.apache.commons.lang3.Pair;
import org.syphr.mythtv.protocol.ProtocolException;
import org.syphr.mythtv.protocol.SocketManager;
import org.syphr.mythtv.protocol.ProtocolException.Direction;
import org.syphr.mythtv.protocol.data.Load;
import org.syphr.mythtv.protocol.types.LoadCategory;

/* default */class Command63QueryLoad extends AbstractCommand<Load>
{
    @Override
    protected String getMessage() throws ProtocolException
    {
        return "QUERY_LOAD";
    }

    @SuppressWarnings("unchecked")
    @Override
    public Load send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(getMessage());
        List<String> args = Protocol63Utils.splitArguments(response);

        if (args.size() != 3)
        {
            throw new ProtocolException(response, Direction.RECEIVE);
        }

        try
        {
            return new Load(Pair.of(LoadCategory.ONE_MINUTE, Double.parseDouble(args.get(0))),
                            Pair.of(LoadCategory.FIVE_MINUTES, Double.parseDouble(args.get(1))),
                            Pair.of(LoadCategory.FIFTEEN_MINUTES, Double.parseDouble(args.get(2))));
        }
        catch (NumberFormatException e)
        {
            throw new ProtocolException(response, Direction.RECEIVE, e);
        }
    }
}