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
package org.syphr.mythtv.types;

public enum TunerStatusCategory
{
    /**
     * @since 63
     * @deprecated 65 - use {@link #SCRIPT_STATUS} instead
     */
    @Deprecated
    CHANNEL_TUNED,

    SIGNAL_LOCK,
    SIGNAL_POWER,
    SEEN_PAT,
    MATCHING_PAT,
    SEEN_PMT,
    MATCHING_PMT,

    /**
     * @since 65
     */
    SCRIPT_STATUS
}