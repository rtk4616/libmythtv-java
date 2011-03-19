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
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.Pair;
import org.syphr.mythtv.protocol.data.InputInfo;
import org.syphr.mythtv.protocol.data.ProgramInfo;
import org.syphr.mythtv.protocol.types.RecorderFlag;
import org.syphr.mythtv.protocol.types.RecordingStatus;
import org.syphr.mythtv.protocol.types.SleepStatus;
import org.syphr.mythtv.protocol.types.TvState;

/**
 * This interface is a sub-protocol to {@link Protocol} and represents the
 * combined recorder information API of all MythTV protocols that are supported.
 * However, any functionality that is not part of the protocol present in the
 * most current stable release of MythTV will be marked as deprecated.
 *
 * @see QueryRecorder
 *
 * @author Gregory P. Moyer
 */
public interface QueryRemoteEncoder
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
     *             if the recorder is not local or it is not currently recording
     *
     * @since 63
     */
    public void cancelNextRecording(boolean cancel) throws IOException, CommandException;

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
     * Retrieve state information about various parts of the recorder.
     *
     * @return all set flags
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is not local or it is not currently recording
     *
     * @since 63
     */
    public Set<RecorderFlag> getFlags() throws IOException, CommandException;

    // TODO
    public List<InputInfo> getFreeInputs() throws IOException, CommandException;

    /**
     * Retrieve the maximum bits per second for this recorder.
     *
     * @return the max bitrate
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is not local or it is not currently recording
     *
     * @since 63
     */
    public long getMaxBitrate() throws IOException, CommandException;

    /**
     * Retrieve the current recording status of this recorder.
     *
     * @return the status
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is not local or it is not currently recording
     *
     * @since 63
     */
    public RecordingStatus getRecordingStatus() throws IOException, CommandException;

    /**
     * Retrieve the current sleep/awake state of this recorder.
     *
     * @return the status
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is not local or it is not currently recording
     *
     * @since 63
     */
    public SleepStatus getSleepStatus() throws IOException, CommandException;

    /**
     * Retrieve the current state of this recorder.
     *
     * @return the state
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is not local or it is not currently recording
     *
     * @since 63
     */
    public TvState getState() throws IOException, CommandException;

    /**
     * Determine whether or not this recorder is busy or will be within the given number
     * of seconds.
     *
     * @param withinSeconds
     *            the number of seconds ahead to check
     * @return information about the busy input or <code>null</code> if the recorder will
     *         not be busy
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is not local or it is not currently recording
     *
     * @since 63
     */
    public Pair<Boolean, InputInfo> isBusy(int withinSeconds) throws IOException, CommandException;

    /**
     * Determine whether or not the recorder is currently recording the given program.
     *
     * @param program
     *            the program to check
     * @return <code>true</code> if the recorder is recording the given program;
     *         <code>false</code> otherwise
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is not local or it is not currently recording
     *
     * @since 63
     */
    public boolean matchesRecording(ProgramInfo program) throws IOException, CommandException;

    /**
     * Inform the backend that the given program is scheduled for this recorder in the
     * given number of seconds.
     *
     * @param secondsLeft
     *            the number of seconds until the recording is sceduled to start
     * @param hasLater
     *            <code>true</code> indicates that there is a later showing of this
     *            program that is not conflicted
     * @param program
     *            the program that is scheduled to be recorded
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is not local or it is not currently recording
     *
     * @since 63
     */
    public void recordPending(int secondsLeft, boolean hasLater, ProgramInfo program) throws IOException, CommandException;

    /**
     * Request the given program start recording on this recorder.
     *
     * @param program
     *            the program to record
     * @return <code>true</code> if the program started recording successfully;
     *         <code>false</code> otherwise
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is not local or it is not currently recording
     *
     * @since 63
     */
    public boolean startRecording(ProgramInfo program) throws IOException, CommandException;

    /**
     * Request that this recorder stop recording, if it is currently recording.
     *
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is not local or it is not currently recording
     *
     * @since 63
     */
    public void stopRecording() throws IOException, CommandException;
}