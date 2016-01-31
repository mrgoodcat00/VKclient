package com.goodcat.vkclient.application.service;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.goodcat.vkclient.application.adapter.MusicAdapter;
import com.goodcat.vkclient.application.model.messages.MessagesModel;
import com.goodcat.vkclient.application.model.messages.attachments.MessageAttachmentModel;
import com.goodcat.vkclient.application.model.messages.attachments.WallTypeAttachmentModel;
import com.goodcat.vkclient.application.model.music.MusicModel;
import com.goodcat.vkclient.application.model.user.UserModel;
import com.goodcat.vkclient.application.model.user.attachments.UserWallAttachmentsModel;
import com.goodcat.vkclient.application.model.user.attachments.VideoAttachmentModel;

import java.util.ArrayList;
import java.util.List;

public class MessageAttachment {

    private LinearLayout wrapper;
    private List<MessageAttachmentModel> attacments;
    private List<UserModel> dialogUsers;
    private Context context;
    private MusicService.MusicWorker musicBinder;


    public MessageAttachment(Context context, Object cw, MessagesModel message, List<UserModel> dialogUsers,MusicService.MusicWorker musicBinder) {
        this.wrapper = (LinearLayout) cw;
        this.attacments = message.getAttachments();
        this.dialogUsers = dialogUsers;
        this.context = context;
        this.musicBinder = musicBinder;
    }



    public void printAttachment(){
        for(MessageAttachmentModel uwam:this.attacments){

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
          /*  if(uwam.getType().equals("video")){
                printVideoAttachment(uwam);
            } else if(uwam.getType().equals("photo")){
                printPhotoAttachment(uwam);
            } else if(uwam.getType().equals("audio")){
                printAudioAttachment(uwam);
            } else if(uwam.getType().equals("posted_photo")){
                printWallAttachmentFromAttachment(uwam);
            }*/

            /*else if(uwam.getType().equals("graffiti")){
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
            } else if(uwam.getType().equals("doc")){
                printDocAttachment(uwam);
            }*/
        }
    }

    public void printPhotoAttachment(MessageAttachmentModel uwam){
        TextView divider = new TextView(this.context);
        divider.setHeight(10);
        addViewToAttachmentWrapper(divider);
        ImageView image = new ImageView(this.context);
        DownloadImageService.loadImage(image,uwam.getPhoto().getPhoto130());
        addViewToAttachmentWrapper(image);
    }
    public void printVideoAttachment(MessageAttachmentModel uwam){
        VideoAttachmentModel video = uwam.getVideo();

        TextView divider = new TextView(this.context);
        divider.setHeight(10);
        addViewToAttachmentWrapper(divider);

        ImageView previewImage = new ImageView(context);
        DownloadImageService.loadImage(previewImage,video.getPhoto130());
        addViewToAttachmentWrapper(previewImage);

        TextView videoTitle = new TextView(context);
        videoTitle.setText(video.getTitle());
        addViewToAttachmentWrapper(videoTitle);

        if(video.getDescription() != null) {
            TextView videoText = new TextView(context);
            videoText.setText(video.getDescription());
            addViewToAttachmentWrapper(videoText);
        }
    }
    public void printAudioAttachment(MessageAttachmentModel uwam){
        MusicModel audio = uwam.getAudio();
        TextView divider = new TextView(this.context);
        divider.setHeight(10);
        addViewToAttachmentWrapper(divider);
        List<MusicModel> audioList= new ArrayList<MusicModel>();
        List<MusicModel> audioListAdditional= new ArrayList<MusicModel>();
        audioList.add(audio);
        ListView listView = new ListView(context);
        listView.setFooterDividersEnabled(false);
        addViewToAttachmentWrapper(listView);
        if(musicBinder.getCurrentPlayingTrack() != null && !audioList.isEmpty() && musicBinder != null) {
            for (MusicModel m : audioList) {
                if(m.getId() == musicBinder.getCurrentPlayingTrack().getId()){
                    m.setIsPlaying(true);
                }
                audioListAdditional.add(m);
            }
        } else { audioListAdditional = audioList;}
        MusicAdapter mAdapter = new MusicAdapter(context,audioListAdditional,musicBinder);
        listView.setAdapter(mAdapter);
    }
    public void printWallAttachment (MessageAttachmentModel uwam){
        WallTypeAttachmentModel wall = uwam.getWall();
        if(wall.getText() != null){
            TextView textFromUser = new TextView(this.context);
            textFromUser.setText(wall.getText());
            textFromUser.setPadding(0,0,0,5);
            addViewToAttachmentWrapper(textFromUser);
        }
        //this.printAttachment(wall.getAttachments());
    }
    public void printWallReplyAttachment(MessageAttachmentModel uwam){

    }
    public void printDocAttachment(MessageAttachmentModel uwam){

    }
    public void printStickerListAttachment(MessageAttachmentModel uwam){

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

    public void printWallAttachment(UserWallAttachmentsModel uwam){
        WallTypeAttachmentModel wall = uwam.getWall();
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
    public void printDocAttachment(UserWallAttachmentsModel uwam){

    }
    public void printStickerListAttachment(UserWallAttachmentsModel uwam){

    }




    private void addViewToAttachmentWrapper(View view){
        this.wrapper.addView(view,this.wrapper.getChildCount());
    }

}
