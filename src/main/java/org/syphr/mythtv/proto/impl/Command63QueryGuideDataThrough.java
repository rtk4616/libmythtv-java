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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.syphr.mythtv.proto.ProtocolException;
import org.syphr.mythtv.proto.SocketManager;

/* default */class Command63QueryGuideDataThrough implements Command<Date>
{
    private static final String BAD_RESPONSE = "0000-00-00 00:00";

    private final DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Override
    public Date send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait("QUERY_GUIDEDATATHROUGH");
        if (BAD_RESPONSE.equals(response))
        {
            throw new ProtocolException("Unable to determine guide data date");
        }

        try
        {
            return format.parse(response);
        }
        catch (ParseException e)
        {
            throw new ProtocolException(response, e);
        }
    }
}
