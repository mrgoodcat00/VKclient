package com.goodcat.vkclient.application.service;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.goodcat.vkclient.application.model.messages.MessagesModel;
import com.goodcat.vkclient.application.model.user.UserModel;
import com.goodcat.vkclient.application.model.user.UserWallPostsModel;
import com.goodcat.vkclient.application.model.user.attachments.UserWallAttachmentsModel;

import java.util.List;

public class ContentAttachment {

    private LinearLayout wrapper;
    private UserWallPostsModel wallPosts;
    private List<UserWallAttachmentsModel> attacments;
    private List<UserModel> dialogUsers;
    private boolean usedInWall = false;
    private boolean usedInMess = false;
    private Context context;

    public ContentAttachment(Object cw, UserWallPostsModel uwpm) {
        this.wrapper = (LinearLayout) cw;
        this.wallPosts = uwpm;
        this.usedInWall = true;
    }

    public ContentAttachment(Context context, Object cw, MessagesModel message,List<UserModel> dialogUsers) {
        this.wrapper = (LinearLayout) cw;
        this.attacments = message.getAttachments();
        this.dialogUsers = dialogUsers;
        this.usedInMess = true;
        this.context = context;
    }

    public void printAttachment(){
        for(UserWallAttachmentsModel uwam:this.attacments){
            Log.d("!!!!",""+uwam.getType());
            if(uwam.getType().equals("video")){
                printVideoAttachment(uwam);
            } else if(uwam.getType().equals("photo")){
                printPhotoAttachment(uwam);
            } else if(uwam.getType().equals("audio")){
                printAudioAttachment(uwam);
            } else if(uwam.getType().equals("doc")){
                printDocAttachment(uwam);
            } else if(uwam.getType().equals("wall")){
                printWallAttachment(uwam);
            } else if(uwam.getType().equals("wall_reply")){
                printWallReplyAttachment(uwam);
            } else if(uwam.getType().equals("sticker")){
                printStickerListAttachment(uwam);
            }
        }
    }

    public void printPhotoAttachment(UserWallAttachmentsModel uwam){

        ImageView image = new ImageView(this.context);
        DownloadImageService.loadImage(image,uwam.getPhoto().getPhoto130());
        this.wrapper.addView(image,this.wrapper.getChildCount());

        //uwam.getPhoto().getOwnerId();
        //uwam.getPhoto().getUserId();
        /*Log.d("attachment Line ", "-------------------------------------------------------");
        Log.d("attachment getText ", "" + uwam.getPhoto().getText());
        Log.d("attachment getOwnerId ",""+uwam.getPhoto().getOwnerId());
        Log.d("attachment getDate ",""+uwam.getPhoto().getDate());
        Log.d("attachment getUserId ",""+uwam.getPhoto().getUserId());
        Log.d("attachment getPhoto75 ",""+uwam.getPhoto().getPhoto75());
        Log.d("attachment getId ",""+uwam.getPhoto().getId());*/
    }

    public void printVideoAttachment(UserWallAttachmentsModel uwam){

    }

    public void printAudioAttachment(UserWallAttachmentsModel uwam){

    }

    public void printDocAttachment(UserWallAttachmentsModel uwam){

    }

    public void printWallAttachment(UserWallAttachmentsModel uwam){

    }

    public void printWallReplyAttachment(UserWallAttachmentsModel uwam){

    }

    public void printStickerListAttachment(UserWallAttachmentsModel uwam){

    }




}
