package com.goodcat.vkclient.application.service;


import java.util.LinkedHashMap;
import java.util.Map;

public class RequestBuilder {

    private static final String hostUrl = "https://api.vk.com/method/";
    private String requestMethod;
    private String version;
    private String token;
    private String userId;
    private String customHost;

    private StringBuilder sb = new StringBuilder();

    private Map<String, String> paramFields = new LinkedHashMap<String, String>();

    public RequestBuilder(String method, String t, String uId) {
        this.requestMethod = method;
        this.version = "v=5.40";
        this.token = "access_token="+t;
        if(uId != null){
            if(method.startsWith("user.get")){
                this.userId = "user_ids=" + uId;
            } else if(method.startsWith("audio.get")) {
                this.userId = "owner_id=" + uId;
            } else if(method.startsWith("photos.getAlbums")) {
                this.userId = "owner_id=" + uId;
            } else if(method.startsWith("photos.get")) {
                this.userId = "owner_id=" + uId;
            } else {
                this.userId = "user_id=" + uId;
            }
        }
    }

    public RequestBuilder(String t, String server) {
        this.version = "v=5.40";
        this.customHost = server;
        this.token = "access_token="+t;
    }

    public void setFields(String paramName, Object params){
        if(params != null) {
            paramFields.put(paramName, params.toString());
        } else {return;}
    }

    public String getUrl(){
        if(customHost != null){
            sb.append("https://").append(customHost);
        } else {
            sb.append(hostUrl);
        }
        if(requestMethod != null) {
            sb.append(requestMethod).append("?").append(version).append("&");
        } else {
            sb.append("?").append(version).append("&");
        }

        for(String methSet:paramFields.keySet()){
            sb.append(methSet).append("=").append(paramFields.get(methSet)).append("&");
        }

        if(userId != null) {sb.append(token).append("&").append(userId);}
        else {sb.append(token);}

        //if(userId != null && token != null) {sb.append(token).append("&").append(userId);}
        return sb.toString();
    }
}
