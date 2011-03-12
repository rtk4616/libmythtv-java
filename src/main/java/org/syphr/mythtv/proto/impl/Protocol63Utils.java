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
package org.syphr.mythtv.proto.impl;

import java.io.File;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.syphr.mythtv.proto.ProtocolException;
import org.syphr.mythtv.proto.ProtocolException.Direction;
import org.syphr.mythtv.proto.data.Channel;
import org.syphr.mythtv.proto.data.DriveInfo;
import org.syphr.mythtv.proto.data.ProgramInfo;
import org.syphr.mythtv.proto.data.UpcomingRecordings;
import org.syphr.mythtv.proto.types.ConnectionType;
import org.syphr.mythtv.proto.types.EventLevel;
import org.syphr.mythtv.proto.types.FileTransferType;
import org.syphr.mythtv.proto.types.RecorderFlag;
import org.syphr.mythtv.proto.types.RecordingCategory;
import org.syphr.mythtv.proto.types.RecordingStatus;
import org.syphr.mythtv.proto.types.RecordingType;
import org.syphr.mythtv.proto.types.SeekOrigin;
import org.syphr.mythtv.proto.types.SleepStatus;
import org.syphr.mythtv.proto.types.TunerStatusCategory;
import org.syphr.mythtv.proto.types.TvState;
import org.syphr.mythtv.proto.types.VideoEditMark;

import com.google.common.collect.BiMap;
import com.google.common.collect.EnumHashBiMap;

public class Protocol63Utils
{
    private static final String DELIMITER = "[]:[]";

    private static final BiMap<RecordingStatus, Integer> REC_STATUS_MAP = EnumHashBiMap.create(RecordingStatus.class);
    static
    {
        REC_STATUS_MAP.put(RecordingStatus.TUNING, -10);
        REC_STATUS_MAP.put(RecordingStatus.FAILED, -9);
        REC_STATUS_MAP.put(RecordingStatus.TUNER_BUSY, -8);
        REC_STATUS_MAP.put(RecordingStatus.LOW_DISK_SPACE, -7);
        REC_STATUS_MAP.put(RecordingStatus.CANCELLED, -6);
        REC_STATUS_MAP.put(RecordingStatus.MISSED, -5);
        REC_STATUS_MAP.put(RecordingStatus.ABORTED, -4);
        REC_STATUS_MAP.put(RecordingStatus.RECORDED, -3);
        REC_STATUS_MAP.put(RecordingStatus.RECORDING, -2);
        REC_STATUS_MAP.put(RecordingStatus.WILL_RECORD, -1);
        REC_STATUS_MAP.put(RecordingStatus.UNKNOWN, 0);
        REC_STATUS_MAP.put(RecordingStatus.DONT_RECORD, 1);
        REC_STATUS_MAP.put(RecordingStatus.PREVIOUS_RECORDING, 2);
        REC_STATUS_MAP.put(RecordingStatus.CURRENT_RECORDING, 3);
        REC_STATUS_MAP.put(RecordingStatus.EARLIER_SHOWING, 4);
        REC_STATUS_MAP.put(RecordingStatus.TOO_MANY_RECORDINGS, 5);
        REC_STATUS_MAP.put(RecordingStatus.NOT_LISTED, 6);
        REC_STATUS_MAP.put(RecordingStatus.CONFLICT, 7);
        REC_STATUS_MAP.put(RecordingStatus.LATER_SHOWING, 8);
        REC_STATUS_MAP.put(RecordingStatus.REPEAT, 9);
        REC_STATUS_MAP.put(RecordingStatus.INACTIVE, 10);
        REC_STATUS_MAP.put(RecordingStatus.NEVER_RECORD, 11);
        REC_STATUS_MAP.put(RecordingStatus.OFFLINE, 12);
        REC_STATUS_MAP.put(RecordingStatus.OTHER_SHOWING, 13);
    }

    private static final BiMap<ConnectionType, String> CONN_TYPE_MAP = EnumHashBiMap.create(ConnectionType.class);
    static
    {
        CONN_TYPE_MAP.put(ConnectionType.MONITOR, "Monitor");
        CONN_TYPE_MAP.put(ConnectionType.PLAYBACK, "Playback");
    }

    private static final BiMap<EventLevel, Integer> EVENT_LEVEL_MAP = EnumHashBiMap.create(EventLevel.class);
    static
    {
        EVENT_LEVEL_MAP.put(EventLevel.NONE, 0);
        EVENT_LEVEL_MAP.put(EventLevel.ALL, 1);
        EVENT_LEVEL_MAP.put(EventLevel.NO_SYSTEM, 2);
        EVENT_LEVEL_MAP.put(EventLevel.ONLY_SYSTEM, 3);
    }

    private static final BiMap<RecordingCategory, String> REC_CATEGORY_MAP = EnumHashBiMap.create(RecordingCategory.class);
    static
    {
        REC_CATEGORY_MAP.put(RecordingCategory.RECORDING, "Recording");
        REC_CATEGORY_MAP.put(RecordingCategory.PLAY, "Play");
    }

    private static final BiMap<RecordingType, Integer> REC_TYPE_MAP = EnumHashBiMap.create(RecordingType.class);
    static
    {
        REC_TYPE_MAP.put(RecordingType.NOT_RECORDING, 0);
        REC_TYPE_MAP.put(RecordingType.SINGLE_RECORD, 1);
        REC_TYPE_MAP.put(RecordingType.TIMESLOT_RECORD, 2);
        REC_TYPE_MAP.put(RecordingType.CHANNEL_RECORD, 3);
        REC_TYPE_MAP.put(RecordingType.ALL_RECORD, 4);
        REC_TYPE_MAP.put(RecordingType.WEEKSLOT_RECORD, 5);
        REC_TYPE_MAP.put(RecordingType.FIND_ONE_RECORD, 6);
        REC_TYPE_MAP.put(RecordingType.OVERRIDE_RECORD, 7);
        REC_TYPE_MAP.put(RecordingType.DONT_RECORD, 8);
        REC_TYPE_MAP.put(RecordingType.FIND_DAILY_RECORD, 9);
        REC_TYPE_MAP.put(RecordingType.FIND_WEEKLY_RECORD, 10);
    }

    private static final BiMap<TunerStatusCategory, String> TUNER_STATUS_CATEGORY_MAP = EnumHashBiMap.create(TunerStatusCategory.class);
    static
    {
        TUNER_STATUS_CATEGORY_MAP.put(TunerStatusCategory.CHANNEL_TUNED, "Channel Tuned");
        TUNER_STATUS_CATEGORY_MAP.put(TunerStatusCategory.SIGNAL_LOCK, "Signal Lock");
        TUNER_STATUS_CATEGORY_MAP.put(TunerStatusCategory.SIGNAL_POWER, "Signal Power");
        TUNER_STATUS_CATEGORY_MAP.put(TunerStatusCategory.SEEN_PAT, "Seen PAT");
        TUNER_STATUS_CATEGORY_MAP.put(TunerStatusCategory.MATCHING_PAT, "Matching PAT");
        TUNER_STATUS_CATEGORY_MAP.put(TunerStatusCategory.SEEN_PMT, "Seen PMT");
        TUNER_STATUS_CATEGORY_MAP.put(TunerStatusCategory.MATCHING_PMT, "Matching PMT");
    }

    private static final BiMap<FileTransferType, Integer> FILE_TRANSFER_TYPE_MAP = EnumHashBiMap.create(FileTransferType.class);
    static
    {
        FILE_TRANSFER_TYPE_MAP.put(FileTransferType.READ, 0);
        FILE_TRANSFER_TYPE_MAP.put(FileTransferType.WRITE, 1);
    }

    private static final BiMap<VideoEditMark, Integer> VIDEO_EDIT_MARK_MAP = EnumHashBiMap.create(VideoEditMark.class);
    static
    {
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.ALL, -100);
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.UNSET, -10);
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.TMP_CUT_END, -5);
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.TMP_CUT_START, -4);
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.UPDATED_CUT, -3);
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.PLACEHOLDER, -2);
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.CUT_END, 0);
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.CUT_START, 1);
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.BOOKMARK, 2);
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.BLANK_FRAME, 3);
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.COMM_START, 4);
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.COMM_END, 5);
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.GOP_START, 6);
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.KEYFRAME, 7);
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.SCENE_CHANGE, 8);
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.GOP_BYFRAME, 9);
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.ASPECT_1_1, 10);
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.ASPECT_4_3, 11);
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.ASPECT_16_9, 12);
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.ASPECT_2_21_1, 13);
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.ASPECT_CUSTOM, 14);
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.VIDEO_WIDTH, 30);
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.VIDEO_HEIGHT, 31);
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.VIDEO_RATE, 32);
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.DURATION_MS, 33);
    }

    private static final BiMap<TvState, Integer> TV_STATE_MAP = EnumHashBiMap.create(TvState.class);
    static
    {
        TV_STATE_MAP.put(TvState.ERROR, -1);
        TV_STATE_MAP.put(TvState.NONE, 0);
        TV_STATE_MAP.put(TvState.WATCHING_LIVETV, 1);
        TV_STATE_MAP.put(TvState.WATCHING_PRERECORDED, 2);
        TV_STATE_MAP.put(TvState.WATCHING_VIDEO, 3);
        TV_STATE_MAP.put(TvState.WATCHING_DVD, 4);
        TV_STATE_MAP.put(TvState.WATCHING_BD, 5);
        TV_STATE_MAP.put(TvState.WATCHING_RECORDING, 6);
        TV_STATE_MAP.put(TvState.RECORDING_ONLY, 7);
        TV_STATE_MAP.put(TvState.CHANGING_STATE, 8);
    }

    private static final BiMap<SeekOrigin, Integer> SEEK_ORIGIN_MAP = EnumHashBiMap.create(SeekOrigin.class);
    static
    {
        SEEK_ORIGIN_MAP.put(SeekOrigin.BEGINNING, 0);
        SEEK_ORIGIN_MAP.put(SeekOrigin.CURRENT, 1);
        SEEK_ORIGIN_MAP.put(SeekOrigin.END, 2);
    }

    private static final BiMap<SleepStatus, Integer> SLEEP_STATUS_MAP = EnumHashBiMap.create(SleepStatus.class);
    static
    {
        SLEEP_STATUS_MAP.put(SleepStatus.AWAKE, 0);
        SLEEP_STATUS_MAP.put(SleepStatus.ASLEEP, 1);
        SLEEP_STATUS_MAP.put(SleepStatus.FALLING_ASLEEP, 3);
        SLEEP_STATUS_MAP.put(SleepStatus.WAKING, 5);
        SLEEP_STATUS_MAP.put(SleepStatus.UNDEFINED, 8);
    }

    private static final BiMap<RecorderFlag, Long> REC_FLAG_MAP = EnumHashBiMap.create(RecorderFlag.class);
    static
    {
        /*
         * In MythTV, these values are unsigned integers.
         */
        REC_FLAG_MAP.put(RecorderFlag.FRONTEND_READY, 0x00000001L);
        REC_FLAG_MAP.put(RecorderFlag.RUN_MAIN_LOOP, 0x00000002L);
        REC_FLAG_MAP.put(RecorderFlag.EXIT_PLAYER, 0x00000004L);
        REC_FLAG_MAP.put(RecorderFlag.FINISH_RECORDING, 0x00000008L);
        REC_FLAG_MAP.put(RecorderFlag.ERRORED, 0x00000010L);
        REC_FLAG_MAP.put(RecorderFlag.CANCEL_NEXT_RECORDING, 0x00000020L);
        REC_FLAG_MAP.put(RecorderFlag.LIVE_TV, 0x00000100L);
        REC_FLAG_MAP.put(RecorderFlag.RECORDING, 0x00000200L);
        REC_FLAG_MAP.put(RecorderFlag.ANTENNA_ADJUST, 0x00000400L);
        REC_FLAG_MAP.put(RecorderFlag.REC, 0x00000F00L);
        REC_FLAG_MAP.put(RecorderFlag.EIT_SCAN, 0x00001000L);
        REC_FLAG_MAP.put(RecorderFlag.CLOSE_REC, 0x00002000L);
        REC_FLAG_MAP.put(RecorderFlag.KILL_REC, 0x00004000L);
        REC_FLAG_MAP.put(RecorderFlag.NO_REC, 0x0000F000L);
        REC_FLAG_MAP.put(RecorderFlag.KILL_RING_BUFFER, 0x00010000L);
        REC_FLAG_MAP.put(RecorderFlag.WAITING_FOR_REC_PAUSE, 0x00100000L);
        REC_FLAG_MAP.put(RecorderFlag.WAITING_FOR_SIGNAL, 0x00200000L);
        REC_FLAG_MAP.put(RecorderFlag.NEED_TO_START_RECORDER, 0x00800000L);
        REC_FLAG_MAP.put(RecorderFlag.PENDING_ACTIONS, 0x00F00000L);
        REC_FLAG_MAP.put(RecorderFlag.SIGNAL_MONITOR_RUNNING, 0x01000000L);
        REC_FLAG_MAP.put(RecorderFlag.EIT_SCANNER_RUNNING, 0x04000000L);
        REC_FLAG_MAP.put(RecorderFlag.DUMMY_RECORDER_RUNNING, 0x10000000L);
        REC_FLAG_MAP.put(RecorderFlag.RECORDER_RUNNING, 0x20000000L);
        REC_FLAG_MAP.put(RecorderFlag.ANY_REC_RUNNING, 0x30000000L);
        REC_FLAG_MAP.put(RecorderFlag.ANY_RUNNING, 0x3F000000L);
        REC_FLAG_MAP.put(RecorderFlag.RING_BUFFER_READY, 0x40000000L);
        REC_FLAG_MAP.put(RecorderFlag.DETECT, 0x80000000L);
    }

    public static RecordingStatus getRecordingStatus(int recStatus) throws ProtocolException
    {
        return ProtocolUtils.translate(recStatus, REC_STATUS_MAP.inverse(), Direction.RECEIVE);
    }

    public static int getRecordingStatus(RecordingStatus recStatus) throws ProtocolException
    {
        return ProtocolUtils.translate(recStatus, REC_STATUS_MAP, Direction.SEND);
    }

    public static ConnectionType getConnectionType(String connType) throws ProtocolException
    {
        return ProtocolUtils.translate(connType, CONN_TYPE_MAP.inverse(), Direction.RECEIVE);
    }

    public static String getConnectionType(ConnectionType connType) throws ProtocolException
    {
        return ProtocolUtils.translate(connType, CONN_TYPE_MAP, Direction.SEND);
    }

    public static EventLevel getEventLevel(int eventLevel) throws ProtocolException
    {
        return ProtocolUtils.translate(eventLevel, EVENT_LEVEL_MAP.inverse(), Direction.RECEIVE);
    }

    public static int getEventLevel(EventLevel eventLevel) throws ProtocolException
    {
        return ProtocolUtils.translate(eventLevel, EVENT_LEVEL_MAP, Direction.SEND);
    }

    public static RecordingCategory getRecordingCategory(String recCategory) throws ProtocolException
    {
        return ProtocolUtils.translate(recCategory, REC_CATEGORY_MAP.inverse(), Direction.RECEIVE);
    }

    public static String getRecordingCategory(RecordingCategory recCategory) throws ProtocolException
    {
        return ProtocolUtils.translate(recCategory, REC_CATEGORY_MAP, Direction.SEND);
    }

    public static RecordingType getRecordingType(int recType) throws ProtocolException
    {
        return ProtocolUtils.translate(recType, REC_TYPE_MAP.inverse(), Direction.RECEIVE);
    }

    public static int getRecordingType(RecordingType recType) throws ProtocolException
    {
        return ProtocolUtils.translate(recType, REC_TYPE_MAP, Direction.SEND);
    }

    public static TunerStatusCategory getTunerStatusCategory(String tunerStatusCategory) throws ProtocolException
    {
        return ProtocolUtils.translate(tunerStatusCategory, TUNER_STATUS_CATEGORY_MAP.inverse(), Direction.RECEIVE);
    }

    public static String getTunerStatusCategory(TunerStatusCategory tunerStatusCategory) throws ProtocolException
    {
        return ProtocolUtils.translate(tunerStatusCategory, TUNER_STATUS_CATEGORY_MAP, Direction.SEND);
    }

    public static FileTransferType getFileTransferType(int fileTransferType) throws ProtocolException
    {
        return ProtocolUtils.translate(fileTransferType, FILE_TRANSFER_TYPE_MAP.inverse(), Direction.RECEIVE);
    }

    public static int getFileTransferType(FileTransferType fileTransferType) throws ProtocolException
    {
        return ProtocolUtils.translate(fileTransferType, FILE_TRANSFER_TYPE_MAP, Direction.SEND);
    }

    public static VideoEditMark getVideoEditMark(int videoEditMark) throws ProtocolException
    {
        return ProtocolUtils.translate(videoEditMark, VIDEO_EDIT_MARK_MAP.inverse(), Direction.RECEIVE);
    }

    public static int getVideoEditMark(VideoEditMark videoEditMark) throws ProtocolException
    {
        return ProtocolUtils.translate(videoEditMark, VIDEO_EDIT_MARK_MAP, Direction.SEND);
    }

    public static TvState getTvState(int tvState) throws ProtocolException
    {
        return ProtocolUtils.translate(tvState, TV_STATE_MAP.inverse(), Direction.RECEIVE);
    }

    public static int getTvState(TvState tvState) throws ProtocolException
    {
        return ProtocolUtils.translate(tvState, TV_STATE_MAP, Direction.SEND);
    }

    public static SeekOrigin getSeekOrigin(int seekOrigin) throws ProtocolException
    {
        return ProtocolUtils.translate(seekOrigin, SEEK_ORIGIN_MAP.inverse(), Direction.RECEIVE);
    }

    public static int getSeekOrigin(SeekOrigin seekOrigin) throws ProtocolException
    {
        return ProtocolUtils.translate(seekOrigin, SEEK_ORIGIN_MAP, Direction.SEND);
    }

    public static SleepStatus getSleepStatus(int sleepStatus) throws ProtocolException
    {
        return ProtocolUtils.translate(sleepStatus, SLEEP_STATUS_MAP.inverse(), Direction.RECEIVE);
    }

    public static int getSleepStatus(SleepStatus sleepStatus) throws ProtocolException
    {
        return ProtocolUtils.translate(sleepStatus, SLEEP_STATUS_MAP, Direction.SEND);
    }

    public static Set<RecorderFlag> getRecorderFlags(long recFlags) throws ProtocolException
    {
        return ProtocolUtils.translateMultiple(recFlags, REC_FLAG_MAP.inverse());
    }

    public static long getRecorderFlags(Collection<RecorderFlag> recFlags) throws ProtocolException
    {
        return ProtocolUtils.translateMultiple(recFlags, REC_FLAG_MAP);
    }

    public static List<String> splitArguments(String value)
    {
        return new ArrayList<String>(Arrays.asList(value.split(Pattern.quote(DELIMITER))));
    }

    public static String combineArguments(String... args)
    {
        return combineArguments(new ArrayList<String>(Arrays.asList(args)));
    }

    public static String combineArguments(List<String> args)
    {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < args.size(); i++)
        {
            String arg = args.get(i);

            if (arg != null)
            {
                builder.append(arg);
            }

            /*
             * Don't append a delimiter to the end of the string.
             */
            if (i < args.size() - 1)
            {
                builder.append(DELIMITER);
            }
        }

        return builder.toString();
    }

    public static List<DriveInfo> parseDriveInfo(String value) throws ProtocolException
    {
        List<DriveInfo> drives = new ArrayList<DriveInfo>();

        try
        {
            List<String> args = splitArguments(value);

            for (int i = 0; i < args.size();)
            {
                String hostname = args.get(i++);
                File driveRoot = new File(args.get(i++));
                boolean local = "1".equals(args.get(i++));
                long driveNumber = Long.parseLong(args.get(i++));
                long storageGroupId = Long.parseLong(args.get(i++));
                long blockSize = Long.parseLong(args.get(i++));
                long totalSpace = ProtocolUtils.combineInts(Integer.parseInt(args.get(i++)),
                                              Integer.parseInt(args.get(i++)));
                long usedSpace = ProtocolUtils.combineInts(Integer.parseInt(args.get(i++)),
                                             Integer.parseInt(args.get(i++)));

                drives.add(new DriveInfo(hostname,
                                         driveRoot,
                                         local,
                                         driveNumber,
                                         storageGroupId,
                                         blockSize,
                                         totalSpace,
                                         usedSpace));
            }
        }
        catch (RuntimeException e)
        {
            throw new ProtocolException(value, Direction.RECEIVE, e);
        }

        return drives;
    }

    public static UpcomingRecordings parseUpcomingRecordings(String value) throws ProtocolException
    {
        List<String> args = Protocol63Utils.splitArguments(value);

        try
        {
            boolean conflicted = "1".equals(args.get(0));

            /*
             * Remove the conflict value and the next, which is the number of
             * programs returned.
             */
            args.remove(0);
            args.remove(0);

            List<ProgramInfo> programs = Protocol63Utils.parseProgramInfos(args);

            return new UpcomingRecordings(conflicted, programs);
        }
        catch (RuntimeException e)
        {
            throw new ProtocolException(value, Direction.RECEIVE, e);
        }
    }

    public static List<ProgramInfo> parseProgramInfos(List<String> args) throws ProtocolException
    {
        List<ProgramInfo> programs = new ArrayList<ProgramInfo>();
        DateFormat airDateFormat = getProgramInfoAirDateFormat();

        int i = 0;
        while (i < args.size())
        {
            programs.add(parseProgramInfo(args.subList(i, i += 41), airDateFormat));
        }

        return programs;
    }

    public static ProgramInfo parseProgramInfo(List<String> args) throws ProtocolException
    {
        return parseProgramInfo(args, getProgramInfoAirDateFormat());
    }

    private static ProgramInfo parseProgramInfo(List<String> args,
                                                DateFormat airDateFormat) throws ProtocolException
    {
        try
        {
            int i = 0;

            String title = args.get(i++);
            String subtitle = args.get(i++);
            String description = args.get(i++);
            String category = args.get(i++);
            int chanId = Integer.parseInt(args.get(i++));
            String chanNum = args.get(i++);
            String callsign = args.get(i++);
            String chanName = args.get(i++);
            URI filename = URI.create(args.get(i++));
            long fileSize = Long.parseLong(args.get(i++));
            Date startTime = getDateTime(args.get(i++));
            Date endTime = getDateTime(args.get(i++));
            int findId = Integer.parseInt(args.get(i++));
            String hostname = args.get(i++);
            int sourceId = Integer.parseInt(args.get(i++));
            int cardId = Integer.parseInt(args.get(i++));
            int inputId = Integer.parseInt(args.get(i++));
            int recPriority = Integer.parseInt(args.get(i++));
            RecordingStatus recStatus = getRecordingStatus(Integer.parseInt(args.get(i++)));
            int recordId = Integer.parseInt(args.get(i++));
            RecordingType recType = getRecordingType(Integer.parseInt(args.get(i++)));
            int dupIn = Integer.parseInt(args.get(i++));
            int dupMethod = Integer.parseInt(args.get(i++));
            Date recStartTs = getDateTime(args.get(i++));
            Date recEndTs = getDateTime(args.get(i++));
            long programFlags = Long.parseLong(args.get(i++));
            String recGroup = args.get(i++);
            String outputFilters = args.get(i++);
            String seriesId = args.get(i++);
            String programId = args.get(i++);
            Date lastModified = getDateTime(args.get(i++));
            float stars = Float.parseFloat(args.get(i++));
            Date airDate;
            String arg = args.get(i++);
            if (arg.isEmpty())
            {
                airDate = null;
            }
            else
            {
                airDate = airDateFormat.parse(arg);
            }
            String playGroup = args.get(i++);
            int recPriority2 = Integer.parseInt(args.get(i++));
            int parentId = Integer.parseInt(args.get(i++));
            String storageGroup = args.get(i++);
            int audioProps = Integer.parseInt(args.get(i++));
            int videoProps = Integer.parseInt(args.get(i++));
            int subtitleType = Integer.parseInt(args.get(i++));
            int year = Integer.parseInt(args.get(i++));

            return new ProgramInfo(title,
                                   subtitle,
                                   description,
                                   category,
                                   new Channel(chanId, sourceId, chanNum, callsign, chanName),
                                   filename,
                                   fileSize,
                                   startTime,
                                   endTime,
                                   findId,
                                   hostname,
                                   cardId,
                                   inputId,
                                   recPriority,
                                   recStatus,
                                   recordId,
                                   recType,
                                   dupIn,
                                   dupMethod,
                                   recStartTs,
                                   recEndTs,
                                   programFlags,
                                   recGroup,
                                   outputFilters,
                                   seriesId,
                                   programId,
                                   lastModified,
                                   stars,
                                   airDate,
                                   playGroup,
                                   recPriority2,
                                   parentId,
                                   storageGroup,
                                   audioProps,
                                   videoProps,
                                   subtitleType,
                                   year);
        }
        catch (Exception e)
        {
            throw new ProtocolException(args.toString(), Direction.RECEIVE, e);
        }
    }

    public static List<String> extractProgramInfo(ProgramInfo program) throws ProtocolException
    {
        List<String> extracted = new ArrayList<String>();

        Channel channel = program.getChannel();

        extracted.add(program.getTitle());
        extracted.add(program.getSubtitle());
        extracted.add(program.getDescription());
        extracted.add(program.getCategory());
        extracted.add(String.valueOf(channel.getId()));
        extracted.add(String.valueOf(channel.getNumber()));
        extracted.add(String.valueOf(channel.getCallsign()));
        extracted.add(String.valueOf(channel.getName()));
        extracted.add(String.valueOf(program.getFilename()));
        extracted.add(String.valueOf(program.getFileSize()));
        extracted.add(getDateTime(program.getStartTime()));
        extracted.add(getDateTime(program.getEndTime()));
        extracted.add(String.valueOf(program.getFindId()));
        extracted.add(program.getHostname());
        extracted.add(String.valueOf(channel.getSourceId()));
        extracted.add(String.valueOf(program.getCardId()));
        extracted.add(String.valueOf(program.getInputId()));
        extracted.add(String.valueOf(program.getRecPriority()));
        extracted.add(String.valueOf(getRecordingStatus(program.getRecStatus())));
        extracted.add(String.valueOf(program.getRecordId()));
        extracted.add(String.valueOf(getRecordingType(program.getRecType())));
        extracted.add(String.valueOf(program.getDupIn()));
        extracted.add(String.valueOf(program.getDupMethod()));
        extracted.add(getDateTime(program.getRecStartTs()));
        extracted.add(getDateTime(program.getRecEndTs()));
        extracted.add(String.valueOf(program.getProgramFlags()));
        extracted.add(program.getRecGroup());
        extracted.add(program.getOutputFilters());
        extracted.add(program.getSeriesId());
        extracted.add(program.getProgramId());
        extracted.add(getDateTime(program.getLastModified()));
        extracted.add(String.valueOf(program.getStars()));
        extracted.add(program.getAirDate() == null
                ? ""
                : getProgramInfoAirDateFormat().format(program.getAirDate()));
        extracted.add(program.getPlayGroup());
        extracted.add(String.valueOf(program.getRecPriority2()));
        extracted.add(String.valueOf(program.getParentId()));
        extracted.add(program.getStorageGroup());
        extracted.add(String.valueOf(program.getAudioProps()));
        extracted.add(String.valueOf(program.getVideoProps()));
        extracted.add(String.valueOf(program.getSubtitleType()));
        extracted.add(String.valueOf(program.getYear()));

        return extracted;
    }

    public static List<String> extractChannel(Channel channel)
    {
        List<String> extracted = new ArrayList<String>();

        extracted.add(String.valueOf(channel.getId()));
        extracted.add(String.valueOf(channel.getSourceId()));
        extracted.add(channel.getNumber());
        extracted.add(channel.getCallsign());
        extracted.add(channel.getName());

        return extracted;
    }

    private static DateFormat getProgramInfoAirDateFormat()
    {
        return new SimpleDateFormat("yyyy-MM-dd");
    }

    private static Date getDateTime(String seconds)
    {
        return new Date(TimeUnit.SECONDS.toMillis(Long.parseLong(seconds)));
    }

    private static String getDateTime(Date date)
    {
        return String.valueOf(TimeUnit.MILLISECONDS.toSeconds(date.getTime()));
    }
}
