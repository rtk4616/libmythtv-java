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
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.syphr.mythtv.protocol.ProtocolException;
import org.syphr.mythtv.protocol.ProtocolException.Direction;
import org.syphr.mythtv.protocol.SocketManager;
import org.syphr.mythtv.protocol.data.FileInfo;

/* default */class Command63QueryFileExists extends AbstractCommand<FileInfo>
{
    private final URI filename;
    private final String storageGroup;

    public Command63QueryFileExists(URI filename, String storageGroup)
    {
        this.filename = filename;
        this.storageGroup = storageGroup;
    }

    public URI getFilename()
    {
        return filename;
    }

    public String getStorageGroup()
    {
        return storageGroup;
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        return Protocol63Utils.combineArguments("QUERY_FILE_EXISTS",
                                                getFilename().getPath(),
                                                getStorageGroup());
    }

    @Override
    public FileInfo send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(getMessage());
        if ("0".equals(response))
        {
            return null;
        }

        List<String> args = Protocol63Utils.splitArguments(response);
        if (args.size() < 2 || !"1".equals(args.get(0)))
        {
            throw new ProtocolException(response, Direction.RECEIVE);
        }

        if (args.size() == 2)
        {
            return new FileInfo(args.get(1));
        }

        if (args.size() == 15)
        {
            int i = 1;

            try
            {
                return new FileInfo(args.get(i++),
                                    Long.parseLong(args.get(i++)),
                                    Long.parseLong(args.get(i++)),
                                    Long.parseLong(args.get(i++)),
                                    Long.parseLong(args.get(i++)),
                                    Long.parseLong(args.get(i++)),
                                    Long.parseLong(args.get(i++)),
                                    Long.parseLong(args.get(i++)),
                                    Long.parseLong(args.get(i++)),
                                    Long.parseLong(args.get(i++)),
                                    Long.parseLong(args.get(i++)),
                                    new Date(TimeUnit.SECONDS.toMillis(Long.parseLong(args.get(i++)))),
                                    new Date(TimeUnit.SECONDS.toMillis(Long.parseLong(args.get(i++)))),
                                    new Date(TimeUnit.SECONDS.toMillis(Long.parseLong(args.get(i++)))));
            }
            catch (NumberFormatException e)
            {
                throw new ProtocolException(response, Direction.RECEIVE, e);
            }
        }

        throw new ProtocolException(response, Direction.RECEIVE);
    }
}