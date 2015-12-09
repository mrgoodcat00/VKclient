package com.goodcat.vkclient.application.db.dao;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.goodcat.vkclient.application.db.DbBone;
import com.goodcat.vkclient.application.model.user.UserCountersModel;
import com.goodcat.vkclient.application.model.user.UserLastSeenModel;
import com.goodcat.vkclient.application.model.user.UserModel;

import java.util.ArrayList;
import java.util.List;

public class UserDataDao implements CommonDAO<UserModel> {

    private final SQLiteDatabase db;

    public UserDataDao(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public boolean insert(UserModel item) {
        ContentValues cv = new ContentValues();
        cv.put(DbBone.USER_ID,item.getId());
        cv.put(DbBone.USER_FIRST_NAME,item.getFirstName());
        cv.put(DbBone.USER_LAST_NAME,item.getLastName());
        cv.put(DbBone.USER_HOME_TOWN,item.getHomeTown());
        cv.put(DbBone.USER_LAST_SEEN,item.getLastSeen().getSimpleTime());
        cv.put(DbBone.USER_PHOTO_200,item.getPhoto200());
        cv.put(DbBone.USER_COUNTER_AUDIOS,item.getCounters().getAudios());
        cv.put(DbBone.USER_COUNTER_FOLLOWERS,item.getCounters().getFollowers());
        cv.put(DbBone.USER_COUNTER_FRIENDS,item.getCounters().getFriends());
        cv.put(DbBone.USER_COUNTER_GROUPS,item.getCounters().getGroups());
        cv.put(DbBone.USER_COUNTER_VIDEOS,item.getCounters().getVideos());
        cv.put(DbBone.USER_COUNTER_PHOTOS,item.getCounters().getPhotos());
        long res = db.insert(DbBone.USER_TABLE,null,cv);
        Log.d("DATABASE", "was inserted user :" + item.getLastName());
        Log.d("DATABASE","with result :"+item.getLastSeen().getSimpleTime());
        return true;
    }

    public boolean dropTheBase(int id){
        db.delete(DbBone.USER_TABLE,DbBone.USER_ID+ "=?", new String[]{String.valueOf(id)});
        return true;
    }

    @Override
    public boolean update(UserModel item) {
        return false;
    }

    @Override
    public boolean delete(UserModel item) {
        return false;
    }

    @Override
    public UserModel findById(long id) {
        Cursor c = db.query(DbBone.USER_TABLE,null,DbBone.USER_ID + "=?", new String[]{String.valueOf(id)},null,null,null);

        UserModel user = new UserModel();
        if (c.moveToFirst()) {
            do {
                UserLastSeenModel ls = new UserLastSeenModel();
                ls.setTime(c.getInt(c.getColumnIndex(DbBone.USER_LAST_SEEN)));
                ls.setPlatform(0);
                user.setLastSeen(ls);

                UserCountersModel uc = new UserCountersModel();
                uc.setAudios(c.getInt(c.getColumnIndex(DbBone.USER_COUNTER_AUDIOS)));
                uc.setFriends(c.getInt(c.getColumnIndex(DbBone.USER_COUNTER_FRIENDS)));
                uc.setPhotos(c.getInt(c.getColumnIndex(DbBone.USER_COUNTER_PHOTOS)));
                uc.setVideos(c.getInt(c.getColumnIndex(DbBone.USER_COUNTER_VIDEOS)));
                uc.setGroups(c.getInt(c.getColumnIndex(DbBone.USER_COUNTER_GROUPS)));
                uc.setFollowers(c.getInt(c.getColumnIndex(DbBone.USER_COUNTER_FOLLOWERS)));
                user.setCounters(uc);

                user.setId(c.getLong(c.getColumnIndex(DbBone.USER_ID)));
                user.setFirstName(c.getString(c.getColumnIndex(DbBone.USER_FIRST_NAME)));
                user.setLastName(c.getString(c.getColumnIndex(DbBone.USER_LAST_NAME)));
                user.setHomeTown(c.getString(c.getColumnIndex(DbBone.USER_HOME_TOWN)));
                user.setPhoto200(c.getString(c.getColumnIndex(DbBone.USER_PHOTO_200)));


            } while (c.moveToNext());
        }
        return user;
    }

    @Override
    public List<UserModel> loadAll() {
        Cursor c = db.query(DbBone.USER_TABLE, null, null, null, null, null, null);
        List<UserModel> user = new ArrayList<UserModel>();
        if(c.moveToFirst()){
            do{
                UserLastSeenModel ls = new UserLastSeenModel();
                ls.setTime(c.getInt(c.getColumnIndex(DbBone.USER_LAST_SEEN)));
                ls.setPlatform(0);

                UserCountersModel uc = new UserCountersModel();
                uc.setAudios(c.getInt(c.getColumnIndex(DbBone.USER_COUNTER_AUDIOS)));
                uc.setFriends(c.getInt(c.getColumnIndex(DbBone.USER_COUNTER_FRIENDS)));
                uc.setPhotos(c.getInt(c.getColumnIndex(DbBone.USER_COUNTER_PHOTOS)));
                uc.setVideos(c.getInt(c.getColumnIndex(DbBone.USER_COUNTER_VIDEOS)));
                uc.setFollowers(c.getInt(c.getColumnIndex(DbBone.USER_COUNTER_FOLLOWERS)));
                uc.setGroups(c.getInt(c.getColumnIndex(DbBone.USER_COUNTER_GROUPS)));

                UserModel um = new UserModel();
                um.setId(c.getLong(c.getColumnIndex(DbBone.USER_ID)));
                um.setFirstName(c.getString(c.getColumnIndex(DbBone.USER_FIRST_NAME)));
                um.setLastName(c.getString(c.getColumnIndex(DbBone.USER_LAST_NAME)));
                um.setHomeTown(c.getString(c.getColumnIndex(DbBone.USER_HOME_TOWN)));
                um.setLastSeen(ls);
                um.setPhoto200(c.getString(c.getColumnIndex(DbBone.USER_PHOTO_200)));
                um.setCounters(uc);

                user.add(um);
            }while(c.moveToNext());
        }
        return user;
    }
}
