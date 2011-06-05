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

import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.exception.ProtocolException.Direction;

/* default */class Command0_24PlaySpeed extends AbstractCommand0_24Play
{
    private final float speed;

    public Command0_24PlaySpeed(float speed) throws ProtocolException
    {
        if (speed < 0 && Math.floor(speed) != speed)
        {
            throw new ProtocolException("Rewind (negative) speeds must be integer values",
                                        Direction.SEND);
        }

        this.speed = speed;
    }

    @Override
    protected String getMessage()
    {
        /*
         * Make sure that a speed of 0.0 is sent as "0" since the former is
         * misinterpreted by the frontend.
         */
        return "play speed " + (speed == 0 ? "0" : speed) + "x";
    }
}
