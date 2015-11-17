package com.goodcat.vkclient.application.model.user;

import com.google.gson.annotations.SerializedName;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Calendar;
import java.util.TimeZone;

public class UserLastSeenModel {

    @SerializedName("time")
    private int time;

    @SerializedName("platform")
    private int platform;

    public String getTime() {

        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();
        Long timeLong = time * 1000L;
        DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(tz);
        DateTimeFormatter fmt = DateTimeFormat.forPattern("dd MMM yyyy   HH:mm");
        DateTime repostDateTime = new DateTime(timeLong,dateTimeZone);
        return fmt.print(repostDateTime);
    }

    public Integer getPlatform() {
        return platform;
    }

}
