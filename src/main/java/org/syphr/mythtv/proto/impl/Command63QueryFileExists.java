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

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.syphr.mythtv.proto.ProtocolException;
import org.syphr.mythtv.proto.SocketManager;

/* default */class Command63QueryFileExists implements Command<File>
{
    private final String message;

    public Command63QueryFileExists(String basename, String storageGroup)
    {
        message = Protocol63Utils.getProtocolValue("QUERY_FILE_EXISTS",
                                                   basename,
                                                   storageGroup);
    }

    @Override
    public File send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(message);
        if ("0".equals(response))
        {
            return null;
        }

        List<String> args = Protocol63Utils.getArguments(response);
        if (args.size() != 2 || !"1".equals(args.get(0)))
        {
            throw new ProtocolException(response);
        }

        return new File(args.get(1));
    }
}