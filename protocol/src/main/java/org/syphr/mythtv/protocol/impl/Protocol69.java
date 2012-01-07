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
import java.net.URI;

import org.syphr.mythtv.protocol.events.EventProtocol;
import org.syphr.mythtv.protocol.events.impl.EventProtocol69;
import org.syphr.mythtv.util.exception.CommandException;
import org.syphr.mythtv.util.socket.SocketManager;
import org.syphr.mythtv.util.translate.Translator;

public class Protocol69 extends Protocol68
{
    public Protocol69(SocketManager socketManager)
    {
        super(socketManager);
    }

    @Override
    public void mythProtoVersion() throws IOException, CommandException
    {
        new Command63MythProtoVersion(getTranslator(), getParser())
        {
            @Override
            protected String getVersion()
            {
                return "69";
            }

            @Override
            protected String getToken()
            {
                return "63835135";
            }
        }.send(getSocketManager());
    }

    @Override
    public String queryFileHash(URI filename, String storageGroup) throws IOException,
                                                                  CommandException
    {
        return queryFileHash(filename, storageGroup, null);
    }

    @Override
    public String queryFileHash(URI filename, String storageGroup, String host) throws IOException,
                                                                               CommandException
    {
        return new Command69QueryFileHash(getTranslator(),
                                          getParser(),
                                          filename,
                                          storageGroup,
                                          host).send(getSocketManager());
    }

    @Override
    protected EventProtocol createEventProtocol(Translator translator, Parser parser)
    {
        return new EventProtocol69(translator, parser);
    }

    @Override
    protected Translator createTranslator()
    {
        return new Translator69();
    }
}
