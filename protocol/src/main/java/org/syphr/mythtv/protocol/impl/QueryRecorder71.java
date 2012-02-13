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
import java.util.List;
import java.util.Set;

import org.syphr.mythtv.data.InputInfo;
import org.syphr.mythtv.util.exception.CommandException;
import org.syphr.mythtv.util.socket.SocketManager;
import org.syphr.mythtv.util.translate.Translator;

public class QueryRecorder71 extends QueryRecorder66
{
    public QueryRecorder71(Translator translator,
                           Parser parser,
                           int recorderId,
                           SocketManager socketManager)
    {
        super(translator, parser, recorderId, socketManager);
    }

    @Override
    public List<InputInfo> getFreeInputs(Set<Integer> excludedCardIds) throws IOException,
                                                                      CommandException
    {
        return new Command71QueryRecorderRemoteEncoderGetFreeInputs(getTranslator(),
                                                                    getParser(),
                                                                    getRecorderId(),
                                                                    excludedCardIds).send(getSocketManager());
    }
}