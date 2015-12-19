package com.goodcat.vkclient.application.service;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import com.goodcat.vkclient.application.db.DbHandler;
import com.goodcat.vkclient.application.db.dao.UserDataDao;
import com.goodcat.vkclient.application.model.CommonListResponseModel;
import com.goodcat.vkclient.application.model.music.MusicModel;
import com.goodcat.vkclient.application.model.photos.PhotoAlbumModel;
import com.goodcat.vkclient.application.model.photos.PhotoAlbumsModel;
import com.goodcat.vkclient.application.model.user.UserModel;
import com.goodcat.vkclient.application.model.user.UserWallGroupsModel;
import com.goodcat.vkclient.application.model.user.UserWallPostsModel;
import com.goodcat.vkclient.application.model.user.UserWallProfilesModel;
import com.goodcat.vkclient.application.service.callbacks.ResponseCallback;
import com.goodcat.vkclient.application.service.callbacks.ResponseHomeCallback;
import com.goodcat.vkclient.application.service.callbacks.ResponseLazyLoad;
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

public class RequestService extends Service {

    private static final String TAG = "RequestService";

    public class RequestWorker extends Binder {

        private final Executor executor = Executors.newSingleThreadExecutor();
        private final Handler handler = new Handler(Looper.getMainLooper());
        private final SessionToken session;
        public String LOG = this.getClass().getSimpleName();
        public RequestWorker(SessionToken s) {
            session = s;
        }

        public void getUserAudio(final ResponseCallback<MusicModel> userAudios, final String userId) {
            executor.execute(new Runnable() {
                @Override
                public void run() {

                    String token = session.getToken();
                    List<MusicModel> responseUserAudio = new ArrayList<MusicModel>();
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

                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

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


        public void getUserWallPart(final ResponseLazyLoad<UserModel, UserWallPostsModel, UserWallProfilesModel, UserWallGroupsModel> userModel, final int ofset, final String userId) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    String token = session.getToken();
                    List<UserModel> responseUser = new ArrayList<UserModel>();
                    List<UserWallPostsModel> responseItems = new ArrayList<UserWallPostsModel>();
                    List<UserWallProfilesModel> responseProfiles = new ArrayList<UserWallProfilesModel>();
                    List<UserWallGroupsModel> responseGroups = new ArrayList<UserWallGroupsModel>();
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
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    try {
                        RequestBuilder reqBuilder = new RequestBuilder("wall.get", token, userId);
                        reqBuilder.setFields("count", "10");
                        reqBuilder.setFields("extended", "1");
                        reqBuilder.setFields("filter", "all");
                        reqBuilder.setFields("offset", ofset);

                        BufferedReader br = executeHttpRequest(reqBuilder);
                        String line = null;
                        StringBuilder st = new StringBuilder();
                        while ((line = br.readLine()) != null) {
                            st.append(line + "");
                        }

                        JsonParser parser = new JsonParser();
                        JsonObject jObject = (JsonObject) parser.parse(st.toString()).getAsJsonObject().get("response");

                        JsonArray items = jObject.getAsJsonArray("items");
                        JsonArray profiles = jObject.getAsJsonArray("profiles");
                        JsonArray groups = jObject.getAsJsonArray("groups");

                        Log.d("WallResponse", st.toString());
                        Log.d("JSON-items", items.toString());
                        Log.d("JSON-profiles", profiles.toString());
                        Log.d("JSON-groups", groups.toString());

                        if (items.size() > 0) {
                            Gson gson = new Gson();
                            Type fooType = new TypeToken<List<UserWallPostsModel>>() {
                            }.getType();
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
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    final List<UserModel> uM = responseUser;
                    final List<UserWallPostsModel> uW = responseItems;
                    final List<UserWallProfilesModel> uP = responseProfiles;
                    final List<UserWallGroupsModel> uG = responseGroups;

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            userModel.onResponse(uM, uW, uP, uG);
                        }
                    });

                }
            });
        }


        public void getUserWithWallData(final ResponseHomeCallback<UserModel, UserWallPostsModel, UserWallProfilesModel, UserWallGroupsModel> userModel, final String userId) {
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
                        Log.d(LOG, "User Data was loaded from DB");
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
                        Log.d(LOG, "User Data was loaded from HTTP");
                    }


                    try {
                        RequestBuilder reqBuilder = new RequestBuilder("wall.get", token, userId);
                        reqBuilder.setFields("count", "10");
                        reqBuilder.setFields("extended", "1");
                        reqBuilder.setFields("filter", "all");
                        reqBuilder.setFields("offset", "0");

                        BufferedReader br = executeHttpRequest(reqBuilder);
                        String line = null;
                        StringBuilder st = new StringBuilder();
                        while ((line = br.readLine()) != null) {
                            st.append(line + "");
                        }

                        JsonParser parser = new JsonParser();
                        JsonObject jObject = (JsonObject) parser.parse(st.toString()).getAsJsonObject().get("response");

                        JsonArray items = jObject.getAsJsonArray("items");
                        JsonArray profiles = jObject.getAsJsonArray("profiles");
                        JsonArray groups = jObject.getAsJsonArray("groups");

                        Log.d("WallResponse", st.toString());
                        Log.d("JSON-items", items.toString());
                        Log.d("JSON-profiles", profiles.toString());
                        Log.d("JSON-groups", groups.toString());

                        if (items.size() > 0) {
                            Gson gson = new Gson();
                            Type fooType = new TypeToken<List<UserWallPostsModel>>() {
                            }.getType();
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
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    for (UserModel usMod : userDao.loadAll()) {
                        Log.d(LOG, "U DATA: time-" + usMod.getLastSeen().getTime() + " \n fn-" + usMod.getFirstName() + " \n ln-" + usMod.getLastName() + " \n foll-" + usMod.getCounters().getFollowers() + " \n " + usMod.getCounters().getAudios() + " \n groups" + usMod.getCounters().getGroups() + " \n photo-" + usMod.getPhoto200() + "\n");
                    }
                    dbHandler.closeDbConnection();


                    final List<UserModel> uM = responseUser;
                    final List<UserWallPostsModel> uW = responseItems;
                    final List<UserWallProfilesModel> uP = responseProfiles;
                    final List<UserWallGroupsModel> uG = responseGroups;

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            userModel.onResponse(uM, uW, uP, uG);
                        }
                    });
                }
            });
        }

        public void getPhotosFromAlbum(final ResponseCallback<PhotoAlbumModel> userAlbums, final String userId, final long albumId){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    String albumType = null;
                    String token = session.getToken();
                        switch ((int) albumId){
                            case -6:
                                albumType = "profile";
                                break;

                            case -7:
                                albumType = "wall";
                                break;

                            case -15:
                                albumType = "saved";
                                break;
                            default:
                                albumType = ""+albumId;
                        }
                    List<PhotoAlbumModel> photosFromAlbum = new ArrayList<PhotoAlbumModel>();
                    try {
                        RequestBuilder reqBuilder = new RequestBuilder("photos.get", token, userId);
                        reqBuilder.setFields("album_id",albumType);
                        reqBuilder.setFields("extended","1");
                        BufferedReader br = executeHttpRequest(reqBuilder);
                        String line = null;
                        StringBuilder st = new StringBuilder();
                        while ((line = br.readLine()) != null) {
                            st.append(line + "");
                        }
                        Log.d("JSON-photos-from-album", st.toString());
                        if (st.length() > 0) {
                            JsonParser parser = new JsonParser();
                            JsonObject jObject = (JsonObject) parser.parse(st.toString()).getAsJsonObject().get("response");
                            JsonArray items = jObject.getAsJsonArray("items");
                            Log.d("PhotosResponse", st.toString());
                            Log.d("JSON-photo-items", items.toString());

                            if (items.size() > 0) {
                                Gson gson = new Gson();
                                Type fooType = new TypeToken<List<PhotoAlbumModel>>() {
                                }.getType();
                                List<PhotoAlbumModel> commonItemsModel = gson.fromJson(items.toString(), fooType);
                                photosFromAlbum = commonItemsModel;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    final List<PhotoAlbumModel> responsephotosFromAlbum = photosFromAlbum;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            userAlbums.onResponse(responsephotosFromAlbum);
                        }
                    });
                }
            });
        }


        public void getPhotoAlbums(final ResponseCallback<PhotoAlbumsModel> userAlbums, final String userId){
            executor.execute(new Runnable() {
                @Override
                public void run() {

                    String token = session.getToken();
                    List<PhotoAlbumsModel> photoAlbums = new ArrayList<PhotoAlbumsModel>();
                    try {
                        RequestBuilder reqBuilder = new RequestBuilder("photos.getAlbums", token, null);
                        reqBuilder.setFields("need_system", "1");
                        reqBuilder.setFields("need_covers","1");
                        reqBuilder.setFields("photo_sizes","1");
                        BufferedReader br = executeHttpRequest(reqBuilder);
                        String line = null;
                        StringBuilder st = new StringBuilder();
                        while ((line = br.readLine()) != null) {
                            st.append(line + "");
                        }
                        Log.d("JSON-albums", st.toString());
                        if (st.length() > 0) {
                            JsonParser parser = new JsonParser();
                            JsonObject jObject = (JsonObject) parser.parse(st.toString()).getAsJsonObject().get("response");
                            JsonArray items = jObject.getAsJsonArray("items");
                            Log.d("AlbumsResponse", st.toString());
                            Log.d("JSON-album-items", items.toString());

                            if (items.size() > 0) {
                                Gson gson = new Gson();
                                Type fooType = new TypeToken<List<PhotoAlbumsModel>>() {
                                }.getType();
                                List<PhotoAlbumsModel> commonWallItemsModel = gson.fromJson(items.toString(), fooType);
                                photoAlbums = commonWallItemsModel;

                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    final List<PhotoAlbumsModel> responseAlbums = photoAlbums;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            userAlbums.onResponse(responseAlbums);
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
