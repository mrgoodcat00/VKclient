package com.goodcat.vkclient.application.service;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import com.goodcat.vkclient.application.model.user.UserModel;
import com.goodcat.vkclient.application.model.user.UserWallPostsModel;
import com.goodcat.vkclient.application.session.Session;
import com.goodcat.vkclient.application.session.SessionToken;
import com.google.gson.Gson;
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

    private static final String TAG = RequestService.class.getName();

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

        public void getUserWithWallData (final ResponseHomeCallback<UserModel,UserWallPostsModel> userModel, final String userId){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    String token = session.getToken();
                    List<UserWallPostsModel> response = new ArrayList<UserWallPostsModel>();
                    try {
                        RequestBuilder reqBuilder = new RequestBuilder("wall.get", token, userId);
                        reqBuilder.setFields("count", "6");
                        BufferedReader br = executeHttpRequest(reqBuilder);
                        String line = null;
                        StringBuilder st = new StringBuilder();
                        while ((line = br.readLine()) != null) {
                            st.append(line + "");
                        }
                        if (st.length() > 0) {
                            Gson gson = new Gson();
                            Type fooType = new TypeToken<CommonSingleResponseModel<CommonParseCountVariableModel<UserWallPostsModel>>>() {
                            }.getType();
                            CommonSingleResponseModel<CommonParseCountVariableModel<UserWallPostsModel>> commonModel = gson.fromJson(st.toString(), fooType);
                            response = commonModel.getResponse().getItems();
                        }
                        br.close();
                    } catch(IOException e) {
                        e.printStackTrace();
                    }

                    final List<UserModel> uM = UserDataService.getUserData(token, userId);
                    final List<UserWallPostsModel> uW = response;

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            userModel.onResponse(uM,uW);
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
