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
package org.syphr.mythtv.proto.events.impl;

import java.util.Date;

import org.syphr.mythtv.proto.data.Channel;

public interface BackendEvent63GeneratedPixmap
{
    public void generatedPixmap(Channel channel,
                                Date timestamp,
                                String comment,
                                Date timestamp2,
                                long num1,
                                long num2,
                                byte[] bytes);
}
