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
package org.syphr.mythtv.protocol;

import java.io.IOException;
import java.util.Map;

import org.syphr.mythtv.protocol.data.Channel;
import org.syphr.mythtv.protocol.data.ProgramInfo;

/**
 * This interface is a sub-protocol to {@link Protocol} and represents the
 * combined recorder information API of all MythTV protocols that are supported.
 * However, any functionality that is not part of the protocol present in the
 * most current stable release of MythTV will be marked as deprecated.
 *
 * @see QueryRemoteEncoder
 *
 * @author Gregory P. Moyer
 */
public interface QueryRecorder
{
    /**
     * Inform this encoder to cancel or continue recording the next program scheduled to
     * be recorded. This is useful when the encoder is recording live TV and there is a
     * scheduled recording that needs this encoder.
     *
     * @param cancel
     *            if <code>true</code>, the next recording will be cancelled; otherwise
     *            the recording will continue as scheduled
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is unknown
     *
     * @since 63
     */
    public void cancelNextRecording(boolean cancel) throws IOException, CommandException;

    // TODO
    public void changeBrightness() throws IOException, CommandException;

    // TODO
    public void changeChannel() throws IOException, CommandException;

    // TODO
    public void changeColour() throws IOException, CommandException;

    // TODO
    public void changeContrast() throws IOException, CommandException;

    // TODO
    public void changeHue() throws IOException, CommandException;

    // TODO
    public void checkChannel() throws IOException, CommandException;

    // TODO
    public void checkChannelPrefix() throws IOException, CommandException;

    /**
     * Get a map of keyframe positions to file byte offsets for all of the
     * keyframes within the given range.
     *
     * @param start
     *            the start of the desired range
     * @param end
     *            the end of the desired range or a negative number to indicate
     *            no end to the range
     * @return the relevant keyframe positions and file offsets
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is not local or it is not currently recording
     *
     * @since 63
     */
    public Map<Long, Long> fillPositionMap(long start, long end) throws IOException, CommandException;

    /**
     * Request that this recorder stop recording as soon as possible.
     *
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is not local or it is not currently recording
     *
     * @since 63
     */
    public void finishRecording() throws IOException, CommandException;

    /**
     * Inform the backend that this client is ready to receive messages.
     *
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is unknown
     *
     * @since 63
     */
    public void frontendReady() throws IOException, CommandException;

    /**
     * Get the current brightness value for this recorder. This command is
     * likely to only be useful for framegrabbing recorders.
     *
     * @return the current value [0, 100] or <code>-1</code> if the recorder is
     *         not local to this backend or the value cannot be determined
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is unknown
     *
     * @since 63
     */
    public int getBrightness() throws IOException, CommandException;

    // TODO
    public void getChannelInfo() throws IOException, CommandException;

    /**
     * Get the current color value for this recorder. This command is likely to
     * only be useful for framegrabbing recorders.
     *
     * @return the current value [0, 100] or <code>-1</code> if the recorder is
     *         not local to this backend or the value cannot be determined
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is unknown
     *
     * @since 63
     */
    public int getColour() throws IOException, CommandException;

    /**
     * Get the current contrast value for this recorder. This command is likely
     * to only be useful for framegrabbing recorders.
     *
     * @return the current value [0, 100] or <code>-1</code> if the recorder is
     *         not local to this backend or the value cannot be determined
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is unknown
     *
     * @since 63
     */
    public int getContrast() throws IOException, CommandException;

    /**
     * Retrieve the currently recording program.
     *
     * @return the currently recording program or <code>null</code> if nothing is
     *         recording
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is not local or it is not currently recording
     *
     * @since 63
     */
    public ProgramInfo getCurrentRecording() throws IOException, CommandException;

    /**
     * Get the number of bytes written to disk for the current recording.<br>
     * <br>
     * Note, this command will throw an exception if the recorder is not local
     * to the connected backend.
     *
     * @return the number of bytes written
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is not local or it is not currently recording
     *
     * @since 63
     */
    public long getFilePosition() throws IOException, CommandException;

    /**
     * Get the frame rate of the current recording.<br>
     * <br>
     * Note, this command will throw an exception if the recorder is not local
     * to the connected backend.
     *
     * @return the frame rate
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is not local or it is not currently recording
     *
     * @since 63
     */
    public float getFrameRate() throws IOException, CommandException;

    /**
     * Get the number of frames written to disk for the current recording.<br>
     * <br>
     * Note, this command will throw an exception if the recorder is not local
     * to the connected backend.
     *
     * @return the number of frames written
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is not local or it is not currently recording
     *
     * @since 63
     */
    public long getFramesWritten() throws IOException, CommandException;

    // TODO
    public void getFreeInputs() throws IOException, CommandException;

    /**
     * Get the current hue value for this recorder. This command is likely to
     * only be useful for framegrabbing recorders.
     *
     * @return the current value [0, 100] or <code>-1</code> if the recorder is
     *         not local to this backend or the value cannot be determined
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is unknown
     *
     * @since 63
     */
    public int getHue() throws IOException, CommandException;

    /**
     * Get the input on the card to which this recorder is connected.<br>
     * <br>
     * Note, this command will throw an exception if the recorder is not local
     * to the connected backend.
     *
     * @return the name of the input
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is not local or it is not currently recording
     *
     * @since 63
     */
    public String getInput() throws IOException, CommandException;

    /**
     * Get the closest keyframe position to the desired position.
     *
     * @param desiredPosition
     *            the desired frame position
     * @return the keyframe position closest to the desired location
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is not local or it is not currently recording
     *
     * @since 63
     */
    public long getKeyframePos(long desiredPosition) throws IOException, CommandException;

    /**
     * Retrieve the maximum bits per second for this recorder.
     *
     * @return the max bitrate
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if this recorder is unknown
     *
     * @since 63
     */
    public long getMaxBitrate() throws IOException, CommandException;

    // TODO
    public void getNextProgramInfo() throws IOException, CommandException;

    // TODO
    public void getRecordingStatus() throws IOException, CommandException;

    /**
     * Determine whether or not this recorder is currently recording.
     *
     * @return <code>true</code> if this recorder is actively recording,
     *         <code>false</code> otherwise
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if this recorder is unknown
     *
     * @since 63
     */
    public boolean isRecording() throws IOException, CommandException;

    /**
     * Request that the backend pause this recorder.<br>
     * <br>
     * Note, this command will do nothing if the recorder is not local to the
     * connected backend.
     *
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if this recorder is unknown
     *
     * @since 63
     */
    public void pause() throws IOException, CommandException;

    // TODO
    public void setChannel() throws IOException, CommandException;

    // TODO
    public void setInput() throws IOException, CommandException;

    /**
     * Toggle the current state of the current recording between a actual
     * "recording" that will be preserved and a live show.<br>
     * <br>
     * Note, this command will do nothing if the recorder is not local to the
     * connected backend.
     *
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if this recorder is unknown
     *
     * @since 63
     */
    public void setLiveRecording() throws IOException, CommandException;

    // TODO
    public void setSignalMonitoringRate() throws IOException, CommandException;

    // TODO
    public void shouldSwitchCard() throws IOException, CommandException;

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
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if this recorder is unknown
     *
     * @since 63
     */
    public void spawnLiveTv(String chainId, boolean pip, Channel startChannel) throws IOException, CommandException;

    /**
     * Request that the recorder stop recording and cancel it's live TV chain.
     *
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if this recorder is unknown
     *
     * @since 63
     */
    public void stopLiveTv() throws IOException, CommandException;

    /**
     * Toggle the current channel as a member of the given channel group. The request only
     * works for recorders that are local to the connected backend. If the recorder is
     * remote, the request will be silently ignored.
     *
     * @param channelGroup
     *            the group in which to toggle the current channel
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if this recorder is unknown
     *
     * @since 63
     */
    public void toggleChannelFavorite(String channelGroup) throws IOException, CommandException;
}