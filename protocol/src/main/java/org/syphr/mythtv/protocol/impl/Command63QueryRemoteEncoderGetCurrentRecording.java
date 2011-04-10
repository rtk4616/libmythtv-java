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
package org.syphr.mythtv.protocol.impl;

import java.util.List;

import org.syphr.mythtv.data.ProgramInfo;
import org.syphr.mythtv.types.RecordingType;
import org.syphr.mythtv.util.exception.ProtocolException;

/* default */class Command63QueryRemoteEncoderGetCurrentRecording extends AbstractCommand63QueryRemoteEncoder<ProgramInfo>
{
    public Command63QueryRemoteEncoderGetCurrentRecording(int recorderId)
    {
        super(recorderId);
    }

    @Override
    protected String getSubCommand() throws ProtocolException
    {
        return "GET_CURRENT_RECORDING";
    }

    @Override
    protected ProgramInfo parseResponse(String response) throws ProtocolException
    {
        List<String> args = Protocol63Utils.splitArguments(response);

        ProgramInfo program = Protocol63Utils.parseProgramInfo(args);
        if (RecordingType.NOT_RECORDING.equals(program.getRecType()))
        {
            return null;
        }

        return program;
    }
}
