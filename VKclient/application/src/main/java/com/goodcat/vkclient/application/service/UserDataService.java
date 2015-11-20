package com.goodcat.vkclient.application.service;


import android.util.Log;
import com.goodcat.vkclient.application.model.CommonListResponseModel;
import com.goodcat.vkclient.application.model.CommonParseCountVariableModel;
import com.goodcat.vkclient.application.model.group.GroupModel;
import com.goodcat.vkclient.application.model.user.UserModel;
import com.goodcat.vkclient.application.model.user.UserWallPostsModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UserDataService {

    public static String REQUEST_METHOD = "GET";

    public static List<UserModel> getUserData (String token,String userId){
        List<UserModel> response = new ArrayList<UserModel>();
        RequestBuilder reqBuilder = new RequestBuilder("users.get",token,userId);
        reqBuilder.setFields("fields","last_name,first_name,id,home_town,last_seen,counters,photo_200");
        String request = reqBuilder.getUrl();
        try {
            URL url = new URL(request);
            HttpsURLConnection httpConnection = (HttpsURLConnection) url.openConnection();
            httpConnection.setRequestMethod(REQUEST_METHOD);
            httpConnection.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            String line = null;
            StringBuilder st = new StringBuilder();
            while((line = br.readLine()) != null){
                st.append(line + "");
            }
            Log.d("getUserData", "" + st.toString());
            if(st.length()>0){
                Gson gson = new Gson();
                Type fooType =  new TypeToken<CommonListResponseModel<UserModel>>(){}.getType();
                CommonListResponseModel userListResponse = gson.fromJson(st.toString(), fooType);
                response = userListResponse.getResponse();
            }
            br.close();
            httpConnection.disconnect();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public static List<UserModel> getUserByID(String token, String userId){
        List<UserModel> response = new ArrayList<UserModel>();
        RequestBuilder reqBuilder = new RequestBuilder("users.get",token,userId);
        reqBuilder.setFields("count","last_name,first_name,id,last_seen");
        String request = reqBuilder.getUrl();
        try {
            URL url = new URL(request);
            HttpsURLConnection httpConnection = (HttpsURLConnection) url.openConnection();
            httpConnection.setRequestMethod(REQUEST_METHOD);
            httpConnection.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            String line = null;
            StringBuilder st = new StringBuilder();

            while((line = br.readLine()) != null){
                st.append(line + "");
            }

            Log.d("getUserByID",""+st.toString());
            if(st.length()>0){
                Gson gson = new Gson();
                Type fooType =  new TypeToken<CommonListResponseModel<UserModel>>(){}.getType();
                CommonListResponseModel userListResponse = gson.fromJson(st.toString(), fooType);
                response = userListResponse.getResponse();
            }
            br.close();
            httpConnection.disconnect();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public static List<UserWallPostsModel> getUserWall(String token,String userId){
        List<UserWallPostsModel>  response =  new ArrayList<UserWallPostsModel>();
        RequestBuilder reqBuilder = new RequestBuilder("wall.get",token,userId);
        reqBuilder.setFields("count","6");
        String request = reqBuilder.getUrl();
        try {
            URL url = new URL(request);
            HttpsURLConnection httpConnection = (HttpsURLConnection) url.openConnection();
            httpConnection.setRequestMethod(REQUEST_METHOD);
            httpConnection.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            String line = null;
            StringBuilder st = new StringBuilder();

            while((line = br.readLine()) != null){
                st.append(line + "");
            }

            if(st.length()>0){
                Gson gson = new Gson();
                Type fooType =  new TypeToken<CommonSingleResponseModel<CommonParseCountVariableModel<UserWallPostsModel>>>(){}.getType();
                CommonSingleResponseModel<CommonParseCountVariableModel<UserWallPostsModel>> commonModel = gson.fromJson(st.toString(), fooType);
                response = commonModel.getResponse().getItems();
            }
            br.close();
            httpConnection.disconnect();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public static List<GroupModel> getGroupDataByID(String token, String userId){
        List<GroupModel> response = null;
        RequestBuilder reqBuilder = new RequestBuilder("groups.getById",token,null);
        reqBuilder.setFields("fields","description&group_id="+userId);
        String request = reqBuilder.getUrl();
        try {
            URL url = new URL(request);
            HttpsURLConnection httpConnection = (HttpsURLConnection) url.openConnection();
            httpConnection.setRequestMethod(REQUEST_METHOD);
            httpConnection.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            String line = null;
            StringBuilder st = new StringBuilder();

            while((line = br.readLine()) != null){
                st.append(line + "");
            }

            Log.d("getGroupDataByID",""+st.toString());
            if(st.length()>0){
                Gson gson = new Gson();
                Type fooType =  new TypeToken<CommonListResponseModel<GroupModel>> (){}.getType();
                CommonListResponseModel commonModel = gson.fromJson(st.toString(), fooType);
                response = commonModel.getResponse();
            }
            br.close();
            httpConnection.disconnect();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
