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

import org.syphr.mythtv.commons.exception.ProtocolException;
import org.syphr.mythtv.commons.translate.Translator;

/* default */abstract class AbstractCommand63Message extends AbstractCommandOkResponse
{
    public AbstractCommand63Message(Translator translator, Parser parser)
    {
        super(translator, parser);
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        return getParser().combineArguments("MESSAGE", getSubCommand());
    }

    protected abstract String getSubCommand() throws ProtocolException;
}
