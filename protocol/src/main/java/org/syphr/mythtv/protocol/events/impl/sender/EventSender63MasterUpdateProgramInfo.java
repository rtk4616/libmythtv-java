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

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.syphr.mythtv.commons.exception.ProtocolException;
import org.syphr.mythtv.commons.exception.ProtocolException.Direction;
import org.syphr.mythtv.commons.translate.DateUtils;
import org.syphr.mythtv.data.Channel;
import org.syphr.mythtv.protocol.events.BackendEventListener63;
import org.syphr.mythtv.protocol.events.impl.BackendMessage;
import org.syphr.mythtv.protocol.events.impl.EventSender;

public class EventSender63MasterUpdateProgramInfo implements EventSender<BackendEventListener63>
{
    private Channel channel;
    private Date startTime;

    @Override
    public void processMessage(BackendMessage message) throws ProtocolException
    {
        List<String> args = message.getArgs();
        channel = new Channel(Integer.parseInt(args.get(0)));

        try
        {
            startTime = DateUtils.getIsoDateFormat().parse(args.get(1));
        }
        catch (ParseException e)
        {
            throw new ProtocolException("Invalid start time format", Direction.RECEIVE, e);
        }
    }

    @Override
    public void sendEvent(BackendEventListener63 l)
    {
        l.masterUpdateProgInfo(channel, startTime);
    }
}
