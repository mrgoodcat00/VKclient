package com.goodcat.vkclient.application.db.dao;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.goodcat.vkclient.application.db.DbBone;
import com.goodcat.vkclient.application.model.music.MusicModel;

import java.util.ArrayList;
import java.util.List;

public class UserAudioDao implements CommonDAO<MusicModel> {

    private final String LOG = this.getClass().getSimpleName();
    private final SQLiteDatabase db;
    
    public UserAudioDao(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public boolean insert(MusicModel item) {

        Cursor queryRes = db.query(DbBone.AUDIO_TABLE,
                null,
                DbBone.AUDIO_ID + "=?",
                new String[]{String.valueOf(item.getId())},
                null, null, null);


        if(queryRes.moveToFirst()) {
            Log.d(LOG,"item was not added, cause exist"+queryRes.getCount()+" "+queryRes.getInt(queryRes.getColumnIndex(DbBone.AUDIO_ID)));
            Log.d(LOG,"item name"+item.getTitle());
            queryRes.close();
            return false;
        } else {
            ContentValues cv = new ContentValues();
            cv.put(DbBone.AUDIO_ID, item.getId());
            cv.put(DbBone.AUDIO_OWNER_ID, item.getOwnerId());
            cv.put(DbBone.AUDIO_ARTIST, item.getArtist());
            cv.put(DbBone.AUDIO_TITLE, item.getTitle());
            cv.put(DbBone.AUDIO_DURATION, item.getDuration());
            cv.put(DbBone.AUDIO_URL, item.getUrl());
            cv.put(DbBone.AUDIO_LIRICS_ID, item.getLyricsId());
            cv.put(DbBone.AUDIO_ALBUM_ID, item.getAlbumId());
            cv.put(DbBone.AUDIO_GENRE_ID, item.getGenreId());
            cv.put(DbBone.AUDIO_DATE, item.getDate());
            long res = db.insert(DbBone.AUDIO_TABLE, null, cv);
            Log.d("DATABASE", "audio added with result :" + res);
            return true;
        }
    }

    @Override
    public boolean update(MusicModel item) {
        return false;
    }

    @Override
    public boolean delete(MusicModel item) {
        return false;
    }

    @Override
    public MusicModel findById(long id) {
        Cursor c = db.query(DbBone.AUDIO_TABLE,null,DbBone.AUDIO_OWNER_ID + "=?",new String[]{String.valueOf(id)},null,null,null);
        MusicModel m = new MusicModel();
        if(c.moveToFirst()){
            do{
                m.setId(c.getLong(c.getColumnIndex(DbBone.AUDIO_ID)));
                m.setOwnerId(c.getLong(c.getColumnIndex(DbBone.AUDIO_OWNER_ID)));
                m.setArtist(c.getString(c.getColumnIndex(DbBone.AUDIO_ARTIST)));
                m.setTitle(c.getString(c.getColumnIndex(DbBone.AUDIO_TITLE)));
                m.setDuration(c.getInt(c.getColumnIndex(DbBone.AUDIO_DURATION)));
                m.setUrl(c.getString(c.getColumnIndex(DbBone.AUDIO_URL)));
                m.setLyricsId(c.getLong(c.getColumnIndex(DbBone.AUDIO_LIRICS_ID)));
                m.setAlbumId(c.getLong(c.getColumnIndex(DbBone.AUDIO_ALBUM_ID)));
                m.setGenreId(c.getLong(c.getColumnIndex(DbBone.AUDIO_GENRE_ID)));
                m.setDate(c.getLong(c.getColumnIndex(DbBone.AUDIO_DATE)));
            }while(c.moveToNext());
        }
        return m;
    }

    public List<MusicModel> findById(long id,int limit) {
        Cursor c = db.query(DbBone.AUDIO_TABLE,null,DbBone.AUDIO_OWNER_ID + "=?",new String[]{String.valueOf(id)},null,null,null, String.valueOf(limit));
        List<MusicModel> mList = new ArrayList<MusicModel>();
        if(c.moveToFirst()){
            do{
                MusicModel m = new MusicModel();
                m.setId(c.getLong(c.getColumnIndex(DbBone.AUDIO_ID)));
                m.setOwnerId(c.getLong(c.getColumnIndex(DbBone.AUDIO_OWNER_ID)));
                m.setArtist(c.getString(c.getColumnIndex(DbBone.AUDIO_ARTIST)));
                m.setTitle(c.getString(c.getColumnIndex(DbBone.AUDIO_TITLE)));
                m.setDuration(c.getInt(c.getColumnIndex(DbBone.AUDIO_DURATION)));
                m.setUrl(c.getString(c.getColumnIndex(DbBone.AUDIO_URL)));
                m.setLyricsId(c.getLong(c.getColumnIndex(DbBone.AUDIO_LIRICS_ID)));
                m.setAlbumId(c.getLong(c.getColumnIndex(DbBone.AUDIO_ALBUM_ID)));
                m.setGenreId(c.getLong(c.getColumnIndex(DbBone.AUDIO_GENRE_ID)));
                m.setDate(c.getLong(c.getColumnIndex(DbBone.AUDIO_DATE)));
                mList.add(m);
            }while(c.moveToNext());
        }
        return mList;
    }


    @Override
    public List<MusicModel> loadAll() {
        return null;
    }
}
