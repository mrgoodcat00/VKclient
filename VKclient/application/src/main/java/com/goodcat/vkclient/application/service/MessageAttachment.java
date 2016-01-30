package com.goodcat.vkclient.application.service;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.goodcat.vkclient.application.model.messages.MessagesModel;
import com.goodcat.vkclient.application.model.user.UserModel;
import com.goodcat.vkclient.application.model.user.attachments.UserWallAttachmentsModel;
import com.goodcat.vkclient.application.model.user.attachments.WallMessageAttachmentModel;

import java.util.List;

public class MessageAttachment {

    private LinearLayout wrapper;
    private List<UserWallAttachmentsModel> attacments;
    private List<UserModel> dialogUsers;
    private Context context;

    public MessageAttachment(Context context, Object cw, MessagesModel message, List<UserModel> dialogUsers) {
        this.wrapper = (LinearLayout) cw;
        this.attacments = message.getAttachments();
        this.dialogUsers = dialogUsers;
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

    public void printAttachment(List<UserWallAttachmentsModel> attachments){
        for(UserWallAttachmentsModel uwam:attachments){
            Log.d("!!!!",""+uwam.getType());
            if(uwam.getType().equals("video")){
                printVideoAttachment(uwam);
            } else if(uwam.getType().equals("photo")){
                printPhotoAttachment(uwam);
            } else if(uwam.getType().equals("audio")){
                printAudioAttachment(uwam);
            } else if(uwam.getType().equals("doc")){
                printDocAttachment(uwam);


            } else if(uwam.getType().equals("posted_photo")){
                printWallAttachmentFromAttachment(uwam);
            } else if(uwam.getType().equals("graffiti")){
                printWallReplyAttachment(uwam);
            } else if(uwam.getType().equals("link")){
                printStickerListAttachment(uwam);
            } else if(uwam.getType().equals("note")){
                printStickerListAttachment(uwam);
            } else if(uwam.getType().equals("app")){
                printStickerListAttachment(uwam);
            } else if(uwam.getType().equals("poll")){
                printStickerListAttachment(uwam);
            } else if(uwam.getType().equals("page")){
                printStickerListAttachment(uwam);
            } else if(uwam.getType().equals("album")){
                printStickerListAttachment(uwam);
            } else if(uwam.getType().equals("photos_list")){
                printStickerListAttachment(uwam);
            }
        }
    }

    public void printPhotoAttachment(UserWallAttachmentsModel uwam){
        ImageView image = new ImageView(this.context);
        DownloadImageService.loadImage(image,uwam.getPhoto().getPhoto130());
        addViewToAttachmentWrapper(image);
    }

    public void printVideoAttachment(UserWallAttachmentsModel uwam){

    }

    public void printAudioAttachment(UserWallAttachmentsModel uwam){

    }

    public void printDocAttachment(UserWallAttachmentsModel uwam){

    }

    public void printWallAttachment(UserWallAttachmentsModel uwam){
        WallMessageAttachmentModel wall = uwam.getWall();
        if(wall.getText() != null){
            TextView textFromUser = new TextView(this.context);
            textFromUser.setText(wall.getText());
            textFromUser.setPadding(0,0,0,5);
            addViewToAttachmentWrapper(textFromUser);
        }
        this.printAttachment(wall.getAttachments());
    }

    public void printWallAttachmentFromAttachment(UserWallAttachmentsModel uwam){
    }

    public void printWallReplyAttachment(UserWallAttachmentsModel uwam){

    }

    public void printStickerListAttachment(UserWallAttachmentsModel uwam){

    }

    private void addViewToAttachmentWrapper(View view){
        this.wrapper.addView(view,this.wrapper.getChildCount());
    }

}
