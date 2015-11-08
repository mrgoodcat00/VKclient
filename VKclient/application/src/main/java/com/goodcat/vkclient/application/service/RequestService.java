package com.goodcat.vkclient.application.service;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import com.goodcat.vkclient.application.model.user.UserModel;
import com.goodcat.vkclient.application.model.user.UserWallPostsModel;
import com.goodcat.vkclient.application.session.Session;
import com.goodcat.vkclient.application.session.SessionToken;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RequestService extends Service{

    public class RequestWorker extends Binder{

        private final Executor executor = Executors.newSingleThreadExecutor();

        private final Handler handler = new Handler(Looper.getMainLooper());

        private final SessionToken session;

        public RequestWorker(SessionToken s) {
            session = s;
        }





        public void getUserData (final ResponseCallback<UserModel> userModel, final String userId){
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
        }



        public void getUserWithWallData (final ResponseHomeCallback<UserModel,UserWallPostsModel> userModel, final String userId){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    String token = session.getToken();
                    final List<UserModel> uM = UserDataService.getUserData(token,userId);
                    final List<UserWallPostsModel> uW = UserDataService.getUserWall(token,userId);
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




    @Override
    public IBinder onBind(Intent intent) {
        return new RequestWorker(Session.getSession(this));
    }
}
