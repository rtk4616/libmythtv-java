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
import java.util.Date;
import java.util.List;

import org.syphr.mythtv.types.JumpPoint;
import org.syphr.mythtv.types.Key;
import org.syphr.mythtv.types.Verbose;
import org.syphr.mythtv.util.exception.CommandException;
import org.syphr.mythtv.util.translate.Translator;

public class Control1 extends AbstractControl
{
    public Control1()
    {
        super(new ControlSocketManager());
    }

    @Override
    public void jump(JumpPoint jumpPoint) throws IOException
    {
        new Command1Jump(jumpPoint).send(getSocketManager());
    }

    @Override
    public void key(char c) throws IOException
    {
        new Command1Key(c).send(getSocketManager());
    }

    @Override
    public void key(Key key) throws IOException
    {
        new Command1Key(key).send(getSocketManager());
    }

    @Override
    public void playVolume(int percent) throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void playChannelUp() throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void playChannelDown() throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void playChannel(String channelNumber) throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void playChannelId(int channelId) throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void playFile(String filename) throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void playProgram(int channelId, Date recStartTs, boolean resume) throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void playSavePreview() throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void playSavePreview(String filename) throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void playSavePreview(String filename, int width, int height) throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void playSeek() throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void playSpeedPause() throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void playSpeed(float speed) throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void playStop() throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void queryLocation() throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void queryVolume() throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void queryRecordings() throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void queryRecording(int channelId, Date recStartTs) throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void queryLiveTv() throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void queryLiveTv(int channelId) throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void queryLoad() throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void queryMemStats() throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void queryTime() throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void queryUptime() throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void queryVerbose() throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void queryVersion() throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void queryChannels() throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void queryChannels(int start, int limit) throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void setVerbose(List<Verbose> options) throws IOException, CommandException
    {
        new Command1SetVerbose(options).send(getSocketManager());
    }

    @Override
    public void screenshot() throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void screenshot(String filename) throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void screenshot(String filename, int width, int height) throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void message(String text) throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void exit() throws IOException
    {
        new Command1Exit().send(getSocketManager());
        super.exit();
    }

    @Override
    protected Translator getTranslator()
    {
        return Control1Utils.getTranslator();
    }
}
