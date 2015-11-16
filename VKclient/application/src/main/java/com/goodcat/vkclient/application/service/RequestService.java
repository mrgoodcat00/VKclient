package com.goodcat.vkclient.application.service;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
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

        /*public void getUserData (final ResponseCallback<UserModel> userModel, final String userId){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    String token = session.getToken();
                    final List<UserModel> uM = UserDataService.getUserData(token,userId);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            userModel.onResponse(uM);
                        }
                    });
                }
            });
        }*/

        public void getUserWithWallData (final ResponseHomeCallback<UserModel,UserWallPostsModel,UserWallProfilesModel,UserWallGroupsModel> userModel, final String userId){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    String token = session.getToken();
                    List<UserWallPostsModel> responseItems = new ArrayList<UserWallPostsModel>();
                    List<UserWallProfilesModel> responseProfiles = new ArrayList<UserWallProfilesModel>();
                    List<UserWallGroupsModel> responseGroups = new ArrayList<UserWallGroupsModel>();

                    try {
                        RequestBuilder reqBuilder = new RequestBuilder("wall.get", token, userId);
                        reqBuilder.setFields("count", "10");
                        reqBuilder.setFields("extended", "1");
                        reqBuilder.setFields("filter", "all");
                        reqBuilder.setFields("offset","0");
                        //reqBuilder.setFields("fields", "name,last_name,first_name,id,photo_50");

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
                            int counter = 0;
                            Gson gson = new Gson();
                            Type fooType = new TypeToken<List<UserWallPostsModel>>() {}.getType();
                            List<UserWallPostsModel> commonWallItemsModel = gson.fromJson(items.toString(), fooType);
                            responseItems = commonWallItemsModel;
                        }

                        if (groups.size() > 0) {
                            Gson gson = new Gson();
                            Type fooType = new TypeToken<List<UserWallGroupsModel>>() {
                            }.getType();
                            List<UserWallGroupsModel> commonWallGroupsModel = gson.fromJson(groups.toString(), fooType);
                            responseGroups = commonWallGroupsModel;
                        }

                        if (profiles.size() > 0) {
                            Gson gson = new Gson();
                            Type fooType = new TypeToken<List<UserWallProfilesModel>>() {
                            }.getType();
                            List<UserWallProfilesModel> commonWallProfilesModel = gson.fromJson(profiles.toString(), fooType);
                            responseProfiles = commonWallProfilesModel;
                        }

                        br.close();
                    } catch(IOException e) {
                        e.printStackTrace();
                    }

                    final List<UserModel> uM = UserDataService.getUserData(token, userId);
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
