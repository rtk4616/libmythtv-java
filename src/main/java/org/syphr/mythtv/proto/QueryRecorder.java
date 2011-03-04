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
package org.syphr.mythtv.proto;

import java.io.IOException;

import org.syphr.mythtv.proto.data.Channel;

/**
 * This interface is a sub-protocol to {@link Protocol} and represents the
 * combined recorder information API of all MythTV protocols that are supported.
 * However, any functionality that is not part of the protocol present in the
 * most current stable release of MythTV will be marked as deprecated.
 *
 * @author Gregory P. Moyer
 */
public interface QueryRecorder
{
    // TODO
    public void cancelNextRecording() throws IOException;

    // TODO
    public void changeBrightness() throws IOException;

    // TODO
    public void changeChannel() throws IOException;

    // TODO
    public void changeColour() throws IOException;

    // TODO
    public void changeContrast() throws IOException;

    // TODO
    public void changeHue() throws IOException;

    // TODO
    public void checkChannel() throws IOException;

    // TODO
    public void checkChannelPrefix() throws IOException;

    // TODO
    public void fillPositionMap() throws IOException;

    // TODO
    public void finishRecording() throws IOException;

    // TODO
    public void frontendReady() throws IOException;

    // TODO
    public void getBrightness() throws IOException;

    // TODO
    public void getChannelInfo() throws IOException;

    // TODO
    public void getColour() throws IOException;

    // TODO
    public void getContrast() throws IOException;

    // TODO
    public void getCurrentRecording() throws IOException;

    // TODO
    public void getFilePosition() throws IOException;

    // TODO
    public void getFramerate() throws IOException;

    // TODO
    public void getFramesWritten() throws IOException;

    // TODO
    public void getFreeInputs() throws IOException;

    // TODO
    public void getHue() throws IOException;

    // TODO
    public void getInput() throws IOException;

    // TODO
    public void getKeyframePos() throws IOException;

    // TODO
    public void getMaxBitrate() throws IOException;

    // TODO
    public void getNextProgramInfo() throws IOException;

    // TODO
    public void getRecording() throws IOException;

    // TODO
    public void getRecordingStatus() throws IOException;

    /**
     * Determine whether or not this recorder is currently recording.
     *
     * @return <code>true</code> if this recorder is actively recording,
     *         <code>false</code> otherwise
     * @throws IOException
     *             if there is a communication or protocol error
     *
     * @since 63
     */
    public boolean isRecording() throws IOException;

    // TODO
    public void pause() throws IOException;

    // TODO
    public void setChannel() throws IOException;

    // TODO
    public void setInput() throws IOException;

    // TODO
    public void setSignalMonitoringRate() throws IOException;

    // TODO
    public void shouldSwitchCard() throws IOException;

    /**
     * Request a new LiveTV chain to start recording.
     *
     * @param chainId
     *            the ID of the new chain (suggest live-[host]-[start date])
     * @param pip
     *            tell the backend whether or not this chain will be used for
     *            Picture-In-Picture
     * @param startChannel
     *            the channel to start recording
     * @return <code>true</code> if the request was successful; <code>false</code>
     *         otherwise
     * @throws IOException
     *             if there is a communication or protocol error
     *
     * @since 63
     */
    public boolean spawnLiveTv(String chainId, boolean pip, Channel startChannel) throws IOException;

    /**
     * Request that the recorder stop recording and cancel it's LiveTV chain.
     *
     * @return <code>true</code> if the request was successful; <code>false</code>
     *         otherwise
     * @throws IOException
     *             if there is a communication or protocol error
     *
     * @since 63
     */
    public boolean stopLiveTv() throws IOException;

    /**
     * Toggle the current channel as a member of the given channel group. The request only
     * works for recorders that are local to the connected backend. If the recorder is
     * remote, the request will be silently ignored.
     *
     * @param channelGroup
     *            the group in which to toggle the current channel
     * @throws IOException
     *             if there is a communication or protocol error
     *
     * @since 63
     */
    public void toggleChannelFavorite(String channelGroup) throws IOException;
}