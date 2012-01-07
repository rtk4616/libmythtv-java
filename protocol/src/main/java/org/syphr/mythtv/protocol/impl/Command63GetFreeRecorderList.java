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
package org.syphr.mythtv.protocol.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.exception.ProtocolException.Direction;
import org.syphr.mythtv.util.socket.SocketManager;
import org.syphr.mythtv.util.translate.Translator;

/* default */class Command63GetFreeRecorderList extends AbstractProtocolCommand<List<Integer>>
{
    public Command63GetFreeRecorderList(Translator translator, Parser parser)
    {
        super(translator, parser);
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        return "GET_FREE_RECORDER_LIST";
    }

    @Override
    public List<Integer> send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(getMessage());

        List<Integer> freeRecorders = new ArrayList<Integer>();

        if ("0".equals(response))
        {
            return freeRecorders;
        }

        List<String> args = getParser().splitArguments(response);
        try
        {
            for (String arg : args)
            {
                freeRecorders.add(Integer.valueOf(arg));
            }

            return freeRecorders;
        }
        catch (NumberFormatException e)
        {
            throw new ProtocolException(response, Direction.RECEIVE, e);
        }
    }
}
