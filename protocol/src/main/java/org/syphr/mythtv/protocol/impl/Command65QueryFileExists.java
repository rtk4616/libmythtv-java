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

import java.net.URI;

import org.syphr.mythtv.protocol.ProtocolException;

/* default */class Command65QueryFileExists extends Command63QueryFileExists
{
    public Command65QueryFileExists(URI filename, String storageGroup)
    {
        super(filename, storageGroup);
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        String message = Protocol63Utils.combineArguments("QUERY_FILE_EXISTS",
                                                          getFilename().getPath());

        String storageGroup = getStorageGroup();
        if (storageGroup != null)
        {
            message = Protocol63Utils.combineArguments(message, storageGroup);
        }

        return message;
    }
}