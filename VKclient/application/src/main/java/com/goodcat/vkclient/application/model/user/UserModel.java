package com.goodcat.vkclient.application.model.user;


import com.google.gson.annotations.SerializedName;

public class UserModel {

    @SerializedName("id")
    private long id;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("last_seen")
    private UserLastSeenModel lastSeen;

    @SerializedName("home_town")
    private String homeTown;

    @SerializedName("counters")
    private UserCountersModel counters;

    @SerializedName("photo_200")
    private String photo200;

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public UserLastSeenModel getLastSeen() {
        return lastSeen;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public UserCountersModel getCounters() {
        return counters;
    }

    public String getPhoto200() {
        return photo200;
    }
}
