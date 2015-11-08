package com.goodcat.vkclient.application.session;

public class SessionToken {

    private String userId;
    private String token;
    private String tokenTTL;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenTTL() {
        return tokenTTL;
    }

    public void setTokenTTL(String tokenTTL) {
        this.tokenTTL = tokenTTL;
    }

    public boolean isTokenValid(){
        if(getToken() != null && getTokenTTL() != null && getUserId() != null) {
            return true;
        } else {return false;}
    }
}
