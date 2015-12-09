package com.goodcat.vkclient.application.db;

public class DbBone {

    private final static String FIELD_ID = "_id";

    public final static String USER_TABLE = "USER_TABLE";
    public final static String AUDIO_TABLE = "AUDIO_TABLE";

    public final static String USER_ID                = "id";
    public final static String USER_FIRST_NAME        = "first_name";
    public final static String USER_LAST_NAME         = "last_name";
    public final static String USER_LAST_SEEN         = "last_seen";
    public final static String USER_HOME_TOWN         = "home_town";
    public final static String USER_PHOTO_200         = "photo_200";
    public final static String USER_COUNTER_PHOTOS    = "photos";
    public final static String USER_COUNTER_GROUPS    = "groups";
    public final static String USER_COUNTER_FRIENDS   = "friends";
    public final static String USER_COUNTER_VIDEOS    = "videos";
    public final static String USER_COUNTER_AUDIOS    = "audios";
    public final static String USER_COUNTER_FOLLOWERS = "followers";
    public final static String USER_WALL_TABLE_ID     = "user_wall_table_id";

    public final static String CREATE_USER_TABLE =
            "CREATE TABLE IF NOT EXISTS " +
            USER_TABLE + "("
            + FIELD_ID +               " integer primary key autoincrement, "
            + USER_ID +                " integer, "
            + USER_FIRST_NAME +        " integer, "
            + USER_LAST_NAME +         " text, "
            + USER_LAST_SEEN +         " integer, "
            + USER_HOME_TOWN +         " text, "
            + USER_PHOTO_200 +         " text,"
            + USER_COUNTER_PHOTOS +    " integer, "
            + USER_COUNTER_GROUPS +    " integer, "
            + USER_COUNTER_FRIENDS +   " integer, "
            + USER_COUNTER_VIDEOS +    " integer, "
            + USER_COUNTER_AUDIOS +    " integer, "
            + USER_COUNTER_FOLLOWERS + " integer, "
            + USER_WALL_TABLE_ID +     " integer);";




    public final static String AUDIO_ID        = "id";
    public final static String AUDIO_OWNER_ID  = "ownerId";
    public final static String AUDIO_ARTIST    = "artist";
    public final static String AUDIO_TITLE     = "title";
    public final static String AUDIO_DURATION  = "duration";
    public final static String AUDIO_URL       = "url";
    public final static String AUDIO_LIRICS_ID = "lyricsId";
    public final static String AUDIO_ALBUM_ID  = "albumId";
    public final static String AUDIO_GENRE_ID  = "genreId";
    public final static String AUDIO_DATE      = "date";

    public final static String CREATE_AUDIO_TABLE =
            "CREATE TABLE IF NOT EXISTS " +
            AUDIO_TABLE + "("
            + FIELD_ID        + " integer primary key autoincrement, "
            + AUDIO_ID        + " integer, "
            + AUDIO_OWNER_ID  + " integer, "
            + AUDIO_ARTIST    + " text, "
            + AUDIO_TITLE     + " text, "
            + AUDIO_DURATION  + " integer, "
            + AUDIO_URL       + " text, "
            + AUDIO_LIRICS_ID + " integer, "
            + AUDIO_ALBUM_ID  + " integer, "
            + AUDIO_GENRE_ID  + " integer, "
            + AUDIO_DATE      + " integer); ";


    public final static String CREATE_FIRENDS_DB = "";
    public final static String CREATE_WALL_DB = "";
}

