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
import java.util.ArrayList;
import java.util.List;

import org.syphr.mythtv.data.ProgramInfo;
import org.syphr.mythtv.data.RecorderLocation;
import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.exception.ProtocolException.Direction;
import org.syphr.mythtv.util.socket.AbstractCommand;
import org.syphr.mythtv.util.socket.SocketManager;

/* default */class Command63GetRecorderNum extends AbstractCommand<RecorderLocation>
{
    private final ProgramInfo program;

    public Command63GetRecorderNum(ProgramInfo program)
    {
        this.program = program;
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        List<String> args = new ArrayList<String>();
        args.add("GET_RECORDER_NUM");
        args.addAll(Protocol63Utils.extractProgramInfo(program));

        return Protocol63Utils.combineArguments(args);
    }

    @Override
    public RecorderLocation send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(getMessage());

        List<String> args = Protocol63Utils.splitArguments(response);
        if (args.size() != 3)
        {
            throw new ProtocolException(response, Direction.RECEIVE);
        }

        try
        {
            int recorderId = Integer.parseInt(args.get(0));
            if (recorderId == -1)
            {
                return null;
            }

            return new RecorderLocation(recorderId,
                                        args.get(1),
                                        Integer.parseInt(args.get(2)));
        }
        catch (NumberFormatException e)
        {
            throw new ProtocolException(response, Direction.RECEIVE, e);
        }
    }
}
