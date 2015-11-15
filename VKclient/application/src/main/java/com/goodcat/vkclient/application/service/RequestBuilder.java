package com.goodcat.vkclient.application.service;


import java.util.LinkedHashMap;
import java.util.Map;

public class RequestBuilder {

    private static final String hostUrl = "https://api.vk.com/method/";
    private String requestMethod;
    private String version;
    private String token;
    private String userId;
    private Map<String, String> paramFields = new LinkedHashMap<String, String>();

    public RequestBuilder(String method, String t, String uId) {
        this.requestMethod = method;
        this.version = "v=5.40";
        this.token = "access_token="+t;
        if(uId != null){
            this.userId = "user_id="+uId;
        }

    }

    public void setFields(String paramName, Object params){
        if(params != null) {
            paramFields.put(paramName, params.toString());
        } else {return;}
    }

    public String getUrl(){
        StringBuilder sb = new StringBuilder(hostUrl);
        sb.append(requestMethod).append("?").append(version).append("&");
        for(String methSet:paramFields.keySet()){
            sb.append(methSet).append("=").append(paramFields.get(methSet)).append("&");
        }
        sb.append(token).append("&").append(userId);

        return sb.toString();
    }
}
