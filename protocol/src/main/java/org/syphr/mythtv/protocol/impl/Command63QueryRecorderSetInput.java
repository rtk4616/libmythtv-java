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

import org.syphr.mythtv.commons.exception.CommandException;
import org.syphr.mythtv.commons.exception.ProtocolException;
import org.syphr.mythtv.commons.translate.Translator;

/* default */class Command63QueryRecorderSetInput extends AbstractCommand63QueryRecorder<String>
{
    private final String input;

    public Command63QueryRecorderSetInput(Translator translator, Parser parser, int recorderId, String input)
    {
        super(translator, parser, recorderId);

        this.input = input;
    }

    @Override
    protected String getSubCommand() throws ProtocolException
    {
        return getParser().combineArguments("SET_INPUT", input);
    }

    @Override
    public String parseResponse(String response) throws ProtocolException, CommandException
    {
        if ("UNKNOWN".equals(response))
        {
            throw new CommandException("Unable to set this recorder's input");
        }

        return response;
    }
}
