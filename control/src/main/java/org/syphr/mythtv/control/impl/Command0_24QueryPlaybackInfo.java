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
package org.syphr.mythtv.control.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.syphr.mythtv.data.Channel;
import org.syphr.mythtv.data.PlaybackInfo;
import org.syphr.mythtv.types.PlaybackType;
import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.exception.ProtocolException.Direction;
import org.syphr.mythtv.util.socket.AbstractCommand;
import org.syphr.mythtv.util.socket.SocketManager;
import org.syphr.mythtv.util.translate.DateUtils;
import org.syphr.mythtv.util.translate.UriUtils;

/* default */class Command0_24QueryPlaybackInfo extends AbstractCommand<PlaybackInfo>
{
    private static final Pattern PLAYBACK_TYPE_PATTERN = Pattern.compile("Playback\\s+(.*?)\\s+(.*)");

    private static final Pattern CHANNEL_VIDEO_PATTERN = Pattern.compile("(\\d+):(\\d{2})\\s+of\\s+(\\d+):(\\d{2})\\s+(\\d+\\.?\\d*)x\\s+(\\d+)\\s+(" + DateUtils.getIsoDatePattern() + ")\\s+(\\d+)\\s+(.*)\\s+(\\d+\\.?\\d*)");
    private static final Pattern OTHER_VIDEO_PATTERN = Pattern.compile("(\\d+):(\\d{2})\\s+(\\d+\\.?\\d*)x\\s+(.*)\\s+(\\d+)\\s+(\\d+\\.?\\d*)");

    @Override
    protected String getMessage()
    {
        return "query location";
    }

    @Override
    public PlaybackInfo send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(getMessage());

        Matcher mainMatcher = PLAYBACK_TYPE_PATTERN.matcher(response);
        if (!mainMatcher.find())
        {
            return null;
        }

        PlaybackType type = Control0_24Utils.getTranslator().toEnum(mainMatcher.group(1), PlaybackType.class);

        try
        {
            switch (type)
            {
                case RECORDED:
                case LIVE_TV:
                {
                    Matcher infoMatcher = CHANNEL_VIDEO_PATTERN.matcher(mainMatcher.group(2));
                    if (!infoMatcher.find())
                    {
                        throw new ProtocolException(response, Direction.RECEIVE);
                    }

                    int currentPosition = Integer.parseInt(infoMatcher.group(1)) * 60 + Integer.parseInt(infoMatcher.group(2));
                    int totalLength = Integer.parseInt(infoMatcher.group(3)) * 60 + Integer.parseInt(infoMatcher.group(4));
                    float speed = Float.parseFloat(infoMatcher.group(5));
                    Channel channel = new Channel(Integer.parseInt(infoMatcher.group(6)));
                    Date start = DateUtils.getIsoDateFormat().parse(infoMatcher.group(7));
                    long currentFrame = Long.parseLong(infoMatcher.group(8));
                    URI location = UriUtils.toUri(infoMatcher.group(9));
                    float fps = Float.parseFloat(infoMatcher.group(10));

                    return new PlaybackInfo(type,
                                            currentPosition,
                                            totalLength,
                                            speed,
                                            channel,
                                            start,
                                            currentFrame,
                                            location,
                                            fps);
                }

                default:
                {
                    Matcher infoMatcher = OTHER_VIDEO_PATTERN.matcher(mainMatcher.group(2));
                    if (!infoMatcher.find())
                    {
                        throw new ProtocolException(response, Direction.RECEIVE);
                    }

                    int currentPosition = Integer.parseInt(infoMatcher.group(1)) * 60 + Integer.parseInt(infoMatcher.group(2));
                    float speed = Float.parseFloat(infoMatcher.group(3));
                    URI location = UriUtils.toUri(infoMatcher.group(4));
                    long currentFrame = Long.parseLong(infoMatcher.group(5));
                    float fps = Float.parseFloat(infoMatcher.group(6));

                    return new PlaybackInfo(type,
                                            currentPosition,
                                            speed,
                                            currentFrame,
                                            location,
                                            fps);
                }
            }
        }
        catch (NumberFormatException e)
        {
            throw new ProtocolException(response, Direction.RECEIVE, e);
        }
        catch (ParseException e)
        {
            throw new ProtocolException(response, Direction.RECEIVE, e);
        }
        catch (URISyntaxException e)
        {
            throw new ProtocolException(response, Direction.RECEIVE, e);
        }
    }
}
