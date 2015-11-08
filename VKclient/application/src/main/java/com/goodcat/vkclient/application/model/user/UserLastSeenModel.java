package com.goodcat.vkclient.application.model.user;

import com.google.gson.annotations.SerializedName;
import org.joda.time.Hours;

public class UserLastSeenModel {

    @SerializedName("time")
    private Integer time;

    @SerializedName("platform")
    private Integer platform;

    public String getTime() {

        String i = Hours.hours(time).toString();



       /* Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time * 1000L);
        int hour = calendar.get(Calendar.HOUR);*/


       /* String convertedDate = hour+"";*/ //time / ( 60 * 60 * 1000)+" hours ago";
        return i;
    }

    public Integer getPlatform() {
        return platform;
    }

}
