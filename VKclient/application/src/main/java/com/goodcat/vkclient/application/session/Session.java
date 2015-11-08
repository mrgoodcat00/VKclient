package com.goodcat.vkclient.application.session;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

public class Session {

    private static final String KEY = "vk_session";

    public static SessionToken getSession(Context context){
        SessionToken sessionObject = new SessionToken();
        SharedPreferences pref = context.getSharedPreferences(KEY,Context.MODE_PRIVATE);
        sessionObject.setUserId(pref.getString("user_id",null));
        sessionObject.setToken(pref.getString("access_token",null));
        sessionObject.setTokenTTL(pref.getString("expires_in",null));
        return sessionObject;
    }

    public static Long getUserOwnerId(Context context){
        SharedPreferences pref = context.getSharedPreferences(KEY,Context.MODE_PRIVATE);
        return Long.parseLong(pref.getString("user_id",null));
    }

    public static boolean isUserOwner(Context context, Long userId){
        SharedPreferences pref = context.getSharedPreferences(KEY,Context.MODE_PRIVATE);
        if( (pref.getString("user_id",null) != null) && Long.parseLong(pref.getString("user_id",null)) == userId ){
            return true;
        } else {return false;}
    }

    public static void reLogin(Context context) {
        context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit().clear().commit();
    }

    public static void saveParams(String  url, Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE)
                .edit();

        if(url.indexOf('#')>0){
            String[] string = url.substring(url.indexOf('#')+1).split("&");
            for(String s:string){
                String[] keyVal = s.split("=");
                editor.putString(keyVal[0],keyVal[1]);
            }
            editor.commit();
        }
    }

    public static boolean isValidSession(Context context){
        return getSession(context).isTokenValid();
    }

    public static Bundle readError(String url) {
        Bundle params = new Bundle();
        int findIndex = url.indexOf("?");
        String errorStr = url.substring(findIndex + 1, url.length());
        String[] paramStrings = errorStr.split("&");
        for (String param : paramStrings) {
            String items[] = param.split("=");
            params.putString(items[0], items[1].replaceAll("[+]", " "));
        }
        return params;
    }

}
