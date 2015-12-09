package com.goodcat.vkclient.application.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.goodcat.vkclient.application.db.dao.UserAudioDao;
import com.goodcat.vkclient.application.db.dao.UserDataDao;

public class DbHandler {

    private UserDataDao uDao;
    private UserAudioDao uAudioDao;
    private SQLiteDatabase db;
    public DbHandler(Context context) {
        DbHelper helper = new DbHelper(context);
        db = helper.getWritableDatabase();
        uDao = new UserDataDao(db);
        uAudioDao = new UserAudioDao(db);
    }

    public UserDataDao getUserDao() {
        return uDao;
    }

    public UserAudioDao getAudioDao() {
        return uAudioDao;
    }

    public void closeDbConnection(){
        db.close();
    }
}
