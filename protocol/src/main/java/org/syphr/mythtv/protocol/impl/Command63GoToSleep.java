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

import org.syphr.mythtv.commons.exception.CommandException;
import org.syphr.mythtv.commons.exception.ProtocolException;
import org.syphr.mythtv.commons.exception.ProtocolException.Direction;
import org.syphr.mythtv.commons.socket.SocketManager;
import org.syphr.mythtv.commons.translate.Translator;

/* default */class Command63GoToSleep extends AbstractProtocolCommand<Void>
{
    public Command63GoToSleep(Translator translator, Parser parser)
    {
        super(translator, parser);
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        return "GO_TO_SLEEP";
    }

    @Override
    public Void send(SocketManager socketManager) throws IOException, CommandException
    {
        String response = socketManager.sendAndWait(getMessage());

        if (response.startsWith("ERROR: "))
        {
            /*
             * Return the message after "ERROR: ".
             */
            throw new CommandException(response.substring(7));
        }

        if (!"OK".equals(response))
        {
            throw new ProtocolException(response, Direction.RECEIVE);
        }

        return null;
    }
}
