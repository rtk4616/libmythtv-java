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

import org.syphr.mythtv.commons.socket.SocketManager;
import org.syphr.mythtv.commons.translate.Translator;
import org.syphr.mythtv.commons.unsupported.UnsupportedHandler;
import org.syphr.mythtv.types.FileTransferType;
import org.syphr.mythtv.types.SeekOrigin;

public class QueryFileTransfer70 extends QueryFileTransfer63
{
    public QueryFileTransfer70(Translator translator,
                               Parser parser,
                               int socketNumber,
                               long size,
                               FileTransferType transferType,
                               SocketManager socketManager,
                               UnsupportedHandler unsupported)
    {
        super(translator, parser, socketNumber, size, transferType, socketManager, unsupported);
    }

    @Override
    public long seek(long position, SeekOrigin origin, long curPosition) throws IOException
    {
        return new Command66QueryFileTransferSeek(getTranslator(),
                                                  getParser(),
                                                  getSocketNumber(),
                                                  position,
                                                  origin,
                                                  curPosition).send(getSocketManager());
    }

    @Override
    public boolean reOpen(String filename) throws IOException
    {
        return new Command70QueryFileTransferReOpen(getTranslator(),
                                                    getParser(),
                                                    getSocketNumber(),
                                                    filename).send(getSocketManager());
    }
}
