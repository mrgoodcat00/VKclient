package com.goodcat.vkclient.application.model.user;

import com.google.gson.annotations.SerializedName;
import org.joda.time.DateTime;

public class UserLastSeenModel {

    @SerializedName("time")
    private Integer time;

    @SerializedName("platform")
    private Integer platform;

    public String getTime() {
        DateTime startDate = new DateTime(time * 1000L);
        return "Last seen " + startDate.getDayOfMonth() + "-" +
                startDate.getMonthOfYear() + "-" + startDate.getYear() + "  " +
                startDate.getHourOfDay() + ":" + startDate.getMinuteOfHour();
    }

    public Integer getPlatform() {
        return platform;
    }

}
