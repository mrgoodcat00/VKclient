package com.goodcat.vkclient.application.service;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import com.goodcat.vkclient.application.db.DbHandler;
import com.goodcat.vkclient.application.db.dao.UserAudioDao;
import com.goodcat.vkclient.application.db.dao.UserDataDao;
import com.goodcat.vkclient.application.model.CommonListResponseModel;
import com.goodcat.vkclient.application.model.music.MusicModel;
import com.goodcat.vkclient.application.model.user.UserModel;
import com.goodcat.vkclient.application.model.user.UserWallGroupsModel;
import com.goodcat.vkclient.application.model.user.UserWallPostsModel;
import com.goodcat.vkclient.application.model.user.UserWallProfilesModel;
import com.goodcat.vkclient.application.session.Session;
import com.goodcat.vkclient.application.session.SessionToken;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RequestService extends Service{

    private static final String TAG = "RequestService";

    public class RequestWorker extends Binder{

        private final Executor executor = Executors.newSingleThreadExecutor();

        private final Handler handler = new Handler(Looper.getMainLooper());

        private final SessionToken session;

        public RequestWorker(SessionToken s) {
            session = s;
        }

        public String LOG = this.getClass().getSimpleName();

        public void getUserAudio (final ResponseCallback<MusicModel> userAudios, final String userId){
            executor.execute(new Runnable() {
                @Override
                public void run() {
        /*------------------------------------------------------ Get data from DATABASE ------------------------------- */
                    DbHandler dbHandler =  new DbHandler(getBaseContext());
                    UserAudioDao userAudioDao = dbHandler.getAudioDao();
                    List<MusicModel> userAudioFromDB = userAudioDao.findById(Long.parseLong(userId), 50);
                    //userDao.dropTheBase(Integer.parseInt(userId));
        /*------------------------------------------------------ Get data from WEB ------------------------------- */
                    String token = session.getToken();
                    List<MusicModel> responseUserAudio = new ArrayList<MusicModel>();
                    if (userAudioFromDB != null && !userAudioFromDB.isEmpty()) {
                        responseUserAudio = userAudioFromDB;
                        Log.d(LOG, "Audio getted from DB");
                    } else {
                        try {
                            RequestBuilder reqBuilder = new RequestBuilder("audio.get", token, userId);
                            reqBuilder.setFields("count", "50");
                            BufferedReader br = executeHttpRequest(reqBuilder);
                            String line = null;
                            StringBuilder st = new StringBuilder();
                            while ((line = br.readLine()) != null) {
                                st.append(line + "");
                            }
                            Log.d("JSON-audio", st.toString());
                            if (st.length() > 0) {
                                JsonParser parser = new JsonParser();
                                JsonObject jObject = (JsonObject) parser.parse(st.toString()).getAsJsonObject().get("response");
                                JsonArray items = jObject.getAsJsonArray("items");
                                Log.d("AudioResponse", st.toString());
                                Log.d("JSON-music-items", items.toString());

                                if (items.size() > 0) {
                                    Gson gson = new Gson();
                                    Type fooType = new TypeToken<List<MusicModel>>() {
                                    }.getType();
                                    List<MusicModel> commonWallItemsModel = gson.fromJson(items.toString(), fooType);
                                    responseUserAudio = commonWallItemsModel;
                                    for(MusicModel musMod:commonWallItemsModel) {
                                        userAudioDao.insert(musMod);
                                    }
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.d(LOG, "Audio getted from HTTP");
                    }

                    int counter = 0;
                    for(MusicModel musMod:userAudioFromDB){
                        Log.d(LOG, "U DATA "+counter+": \n url-"+musMod.getUrl()+" \n id-"+musMod.getId()+" \n dur-"+musMod.getDuration()+" \n title-"+musMod.getTitle()+" \n "+musMod.getArtist()+" \n date"+musMod.getDate()+" \n oId-"+musMod.getOwnerId()+"\n");
                        counter++;
                    }
                    dbHandler.closeDbConnection();


                    final List<MusicModel> responseAudio = responseUserAudio;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            userAudios.onResponse(responseAudio);
                        }
                    });
                }
            });



        }

        public void getUserWithWallData (final ResponseHomeCallback<UserModel,UserWallPostsModel,UserWallProfilesModel,UserWallGroupsModel> userModel, final String userId){
            executor.execute(new Runnable() {
                @Override
                public void run() {
        /*------------------------------------------------------ Get data from DATABASE ------------------------------- */
                    DbHandler dbHandler = new DbHandler(getBaseContext());
                    UserDataDao userDao = dbHandler.getUserDao();
                    UserModel userDataFromDB = userDao.findById(Long.parseLong(userId));
                    //userDao.dropTheBase(Integer.parseInt(userId));
        /*------------------------------------------------------ Get data from WEB ------------------------------- */
                    String token = session.getToken();
                    List<UserModel> responseUser = new ArrayList<UserModel>();
                    List<UserWallPostsModel> responseItems = new ArrayList<UserWallPostsModel>();
                    List<UserWallProfilesModel> responseProfiles = new ArrayList<UserWallProfilesModel>();
                    List<UserWallGroupsModel> responseGroups = new ArrayList<UserWallGroupsModel>();

                    if (userDataFromDB.getId() != 0) {
                        responseUser.add(userDataFromDB);
                        Log.d(LOG,"User Data was loaded from DB");
                    } else {
                        try {
                            RequestBuilder reqBuilder = new RequestBuilder("users.get", token, userId);
                            reqBuilder.setFields("fields", "sex,bdate,city,country,photo_50,photo_100,photo_200_orig,photo_200,photo_400_orig,photo_max,photo_max_orig,photo_id,online,online_mobile,domain,has_mobile,contacts,connections,site,education,universities,schools,can_post,can_see_all_posts,can_see_audio,can_write_private_message,status,last_seen,common_count,relation,relatives,counters,screen_name,maiden_name,timezone,occupation,activities,interests,music,movies,tv,books,games,about,quotes,personal,friend_status,military,career");
                            BufferedReader br = executeHttpRequest(reqBuilder);
                            String line = null;
                            StringBuilder st = new StringBuilder();
                            while ((line = br.readLine()) != null) {
                                st.append(line + "");
                            }
                            Log.d("JSON-user", st.toString());
                            if (st.length() > 0) {
                                Gson gson = new Gson();
                                Type fooType = new TypeToken<CommonListResponseModel<UserModel>>() {
                                }.getType();
                                CommonListResponseModel userListResponse = gson.fromJson(st.toString(), fooType);
                                responseUser = userListResponse.getResponse();
                                userDao.insert(responseUser.get(0));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.d(LOG,"User Data was loaded from HTTP");
                    }






                    try {
                        RequestBuilder reqBuilder = new RequestBuilder("wall.get", token, userId);
                        reqBuilder.setFields("count", "10");
                        reqBuilder.setFields("extended", "1");
                        reqBuilder.setFields("filter", "all");
                        reqBuilder.setFields("offset","0");

                        BufferedReader br = executeHttpRequest(reqBuilder);
                        String line = null;
                        StringBuilder st = new StringBuilder();
                        while ((line = br.readLine()) != null) {
                            st.append(line + "");
                        }

                        JsonParser  parser = new JsonParser();
                        JsonObject jObject =  (JsonObject) parser.parse( st.toString()).getAsJsonObject().get("response");

                        JsonArray items = jObject.getAsJsonArray("items");
                        JsonArray profiles = jObject.getAsJsonArray("profiles");
                        JsonArray groups = jObject.getAsJsonArray("groups");

                        Log.d("WallResponse",st.toString());
                        Log.d("JSON-items", items.toString());
                        Log.d("JSON-profiles", profiles.toString());
                        Log.d("JSON-groups", groups.toString());

                        if (items.size() > 0) {
                            Gson gson = new Gson();
                            Type fooType = new TypeToken<List<UserWallPostsModel>>() {}.getType();
                            List<UserWallPostsModel> commonWallItemsModel = gson.fromJson(items.toString(), fooType);
                            responseItems = commonWallItemsModel;
                        }

                        if (groups.size() > 0) {
                            Gson gson = new Gson();
                            Type fooType = new TypeToken<List<UserWallGroupsModel>>() {}.getType();
                            List<UserWallGroupsModel> commonWallGroupsModel = gson.fromJson(groups.toString(), fooType);
                            responseGroups = commonWallGroupsModel;
                        }

                        if (profiles.size() > 0) {
                            Gson gson = new Gson();
                            Type fooType = new TypeToken<List<UserWallProfilesModel>>() {}.getType();
                            List<UserWallProfilesModel> commonWallProfilesModel = gson.fromJson(profiles.toString(), fooType);
                            responseProfiles = commonWallProfilesModel;
                        }

                        br.close();
                    } catch(IOException e) {
                        e.printStackTrace();
                    }



                    for(UserModel usMod:userDao.loadAll()){
                        Log.d(LOG, "U DATA: time-"+usMod.getLastSeen().getTime()+" \n fn-"+usMod.getFirstName()+" \n ln-"+usMod.getLastName()+" \n foll-"+usMod.getCounters().getFollowers()+" \n "+usMod.getCounters().getAudios()+" \n groups"+usMod.getCounters().getGroups()+" \n photo-"+usMod.getPhoto200()+"\n");
                    }
                    dbHandler.closeDbConnection();



                    final List<UserModel> uM = responseUser;
                    final List<UserWallPostsModel> uW = responseItems;
                    final List<UserWallProfilesModel> uP = responseProfiles;
                    final List<UserWallGroupsModel> uG = responseGroups;

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            userModel.onResponse(uM,uW,uP,uG);
                        }
                    });


                }
            });
        }
    }

    private static BufferedReader executeHttpRequest(RequestBuilder request) throws IOException {
        String toUrl = request.getUrl();
        Log.d(TAG, "request " + toUrl);
        URL url = new URL(toUrl);
        HttpURLConnection connection =
                (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        Log.d(TAG, "response code " + connection.getResponseCode());
        InputStream inputStream = connection.getInputStream();
        return new BufferedReader(
                new InputStreamReader(inputStream)
        );
    }

    @Override
    public IBinder onBind(Intent intent) {

        return new RequestWorker(Session.getSession(this));
    }
}
