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
import org.syphr.mythtv.proto.data.MemStats;
import org.syphr.mythtv.proto.data.MemStats.MemStatCategory;

/* default */class Command63QueryMemStats implements Command<MemStats>
{
    @SuppressWarnings("unchecked")
    @Override
    public MemStats send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait("QUERY_MEMSTATS");
        List<String> args = Protocol63Utils.getArguments(response);

        if (args.size() != 4)
        {
            throw new ProtocolException(response);
        }

        try
        {
            return new MemStats(Pair.of(MemStatCategory.TOTAL_PHYSICAL,
                                        Integer.parseInt(args.get(0))),
                                Pair.of(MemStatCategory.FREE_PHYSICAL,
                                        Integer.parseInt(args.get(1))),
                                Pair.of(MemStatCategory.TOTAL_VIRTUAL,
                                        Integer.parseInt(args.get(2))),
                                Pair.of(MemStatCategory.FREE_VIRTUAL, Integer.parseInt(args.get(3))));
        }
        catch (NumberFormatException e)
        {
            throw new ProtocolException(response, e);
        }
    }
}
