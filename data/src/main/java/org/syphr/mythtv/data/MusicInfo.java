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
package org.syphr.mythtv.data;

public class MusicInfo
{
    private final String title;
    private final String artist;
    private final String album;

    public MusicInfo(String title, String artist, String album)
    {
        this.title = title;
        this.artist = artist;
        this.album = album;
    }

    public String getTitle()
    {
        return title;
    }

    public String getArtist()
    {
        return artist;
    }

    public String getAlbum()
    {
        return album;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("MusicInfo [title=");
        builder.append(title);
        builder.append(", artist=");
        builder.append(artist);
        builder.append(", album=");
        builder.append(album);
        builder.append("]");
        return builder.toString();
    }
}
