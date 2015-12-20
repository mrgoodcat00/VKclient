package com.goodcat.vkclient.application.model.messages;

import com.goodcat.vkclient.application.model.user.attachments.UserWallAttachmentsModel;
import com.google.gson.annotations.SerializedName;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class MessagesModel {

    @SerializedName("id")
    private long id;

    @SerializedName("user_id")
    private long user_id;

    @SerializedName("from_id")
    private long  from_id;

    @SerializedName("date")
    private long  date;

    @SerializedName("read_state")
    private int read_state;

    @SerializedName("out")
    private int out;

    @SerializedName("title")
    private String title;

    @SerializedName("body")
    private String body;

    @SerializedName("emoji")
    private int emoji;

    @SerializedName("important")
    private int  important;

    @SerializedName("deleted")
    private int deleted;

    @SerializedName("attachments")
    private List<UserWallAttachmentsModel> attachments;

/*------------------------- Additional fields from MultiDialog ------------------*/

    @SerializedName("chat_id")
    private long chat_id;

    @SerializedName("chat_active")
    private long[] chat_active;

    @SerializedName("push_settings")
    private String push_settings;

    @SerializedName("users_count")
    private int users_count;

    @SerializedName("admin_id")
    private long admin_id;

    @SerializedName("action_text")
    private String action_text;

    @SerializedName("photo_50")
    private String photo_50;

    @SerializedName("photo_100")
    private String photo_100;


    public long getId() {
        return id;
    }

    public long getUser_id() {
        return user_id;
    }

    public long getFrom_id() {
        return from_id;
    }

    public long getDate() {
        return date;
    }

    public List<UserWallAttachmentsModel> getAttachments() {
        return attachments;
    }

    public String getDateToString(){
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();
        Long timeLong = date * 1000L;
        DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(tz);
        DateTimeFormatter fmt = DateTimeFormat.forPattern("dd MMM HH:mm");
        DateTime repostDateTime = new DateTime(timeLong,dateTimeZone);
        return fmt.print(repostDateTime);
    }


    public int getRead_state() {
        return read_state;
    }

    public int getOut() {
        return out;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {

        if(emoji == 1){

        }
        else {

        }
        return body;
    }

    public int getEmoji() {
        return emoji;
    }

    public int getImportant() {
        return important;
    }

    public int getDeleted() {
        return deleted;
    }

    public long getChat_id() {
        return chat_id;
    }

    public long[] getChat_active() {
        return chat_active;
    }

    public String getPush_settings() {
        return push_settings;
    }

    public int getUsers_count() {
        return users_count;
    }

    public long getAdmin_id() {
        return admin_id;
    }

    public String getAction_text() {
        return action_text;
    }

    public String getPhoto_50() {
        return photo_50;
    }

    public String getPhoto_100() {
        return photo_100;
    }
}
