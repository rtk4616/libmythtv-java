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

import org.syphr.mythtv.types.PictureAdjustType;
import org.syphr.mythtv.util.exception.ProtocolException;

/* default */abstract class AbstractCommand63QueryRecorderChangePictureAttr extends AbstractCommand63QueryRecorderGetPictureAttr
{
    private final PictureAdjustType type;
    private final boolean increment;

    public AbstractCommand63QueryRecorderChangePictureAttr(int recorderId, PictureAdjustType type, boolean increment)
    {
        super(recorderId);

        this.type = type;
        this.increment = increment;
    }

    @Override
    protected String getSubCommand() throws ProtocolException
    {
        return Protocol63Utils.combineArguments(getSubCommandName(),
                                                Protocol63Utils.getTranslator().toString(type),
                                                increment ? "1" : "0");
    }

    protected abstract String getSubCommandName();
}
