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
package org.syphr.mythtv.api;

import org.syphr.mythtv.control.ControlVersion;
import org.syphr.mythtv.db.SchemaVersion;
import org.syphr.mythtv.protocol.ProtocolVersion;

public enum MythVersion
{
    _0_24(ProtocolVersion._63, ControlVersion._0_24, SchemaVersion._1264, "0.24"),
    _0_25(ProtocolVersion._70, ControlVersion._0_25, SchemaVersion._1292, "0.25");

    private final ProtocolVersion protocol;
    private final ControlVersion control;
    private final SchemaVersion schema;

    private final String display;

    private MythVersion(ProtocolVersion protocol,
                        ControlVersion control,
                        SchemaVersion schema,
                        String display)
    {
        this.protocol = protocol;
        this.control = control;
        this.schema = schema;
        this.display = display;
    }

    public ProtocolVersion getProtocol()
    {
        return protocol;
    }

    public ControlVersion getControl()
    {
        return control;
    }

    public SchemaVersion getSchema()
    {
        return schema;
    }

    public String getDisplay()
    {
        return display;
    }

    @Override
    public String toString()
    {
        return getDisplay();
    }
}
