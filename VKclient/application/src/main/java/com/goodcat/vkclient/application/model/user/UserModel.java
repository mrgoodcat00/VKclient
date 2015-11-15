package com.goodcat.vkclient.application.model.user;


import com.google.gson.annotations.SerializedName;

public class UserModel {

    @SerializedName("id")
    private Long id;

    @SerializedName("first_name")
    private String first_name;

    @SerializedName("last_name")
    private String last_name;

    @SerializedName("last_seen")
    private UserLastSeenModel last_seen;

    @SerializedName("home_town")
    private String home_town;

    @SerializedName("counters")
    private UserCountersModel counters;

    @SerializedName("photo_200")
    private String photo_200;

    public String getPhoto_200() {return photo_200;}

    public UserCountersModel getCounters() {
        return counters;
    }

    public void setCounters(UserCountersModel counters) {
        this.counters = counters;
    }

    public UserLastSeenModel getLast_seen() {return last_seen;}

    public void setLast_seen(UserLastSeenModel last_seen) {
        this.last_seen = last_seen;
    }

    public String getHome_town() {
        return home_town;
    }

    public void setHome_town(String home_town) {
        this.home_town = home_town;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
}
