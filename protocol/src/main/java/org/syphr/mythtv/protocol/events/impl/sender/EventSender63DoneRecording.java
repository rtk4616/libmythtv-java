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
package org.syphr.mythtv.protocol.events.impl.sender;

import java.util.List;

import org.syphr.mythtv.commons.exception.ProtocolException;
import org.syphr.mythtv.protocol.events.BackendEventListener63;
import org.syphr.mythtv.protocol.events.impl.BackendMessage;
import org.syphr.mythtv.protocol.events.impl.EventSender;

public class EventSender63DoneRecording implements EventSender<BackendEventListener63>
{
    private int recorder;
    private long seconds;
    private long frames;

    @Override
    public void processMessage(BackendMessage message) throws ProtocolException
    {
        List<String> args = message.getArgs();

        recorder = Integer.parseInt(args.get(0));
        seconds = Long.parseLong(args.get(1));
        frames = Long.parseLong(args.get(2));
    }

    @Override
    public void sendEvent(BackendEventListener63 l)
    {
        l.doneRecording(recorder, seconds, frames);
    }
}
