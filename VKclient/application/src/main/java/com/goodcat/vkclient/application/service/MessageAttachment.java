package com.goodcat.vkclient.application.service;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.goodcat.vkclient.application.R;
import com.goodcat.vkclient.application.adapter.MusicAdapter;
import com.goodcat.vkclient.application.model.messages.MessagesModel;
import com.goodcat.vkclient.application.model.messages.attachments.MessageAttachmentModel;
import com.goodcat.vkclient.application.model.messages.attachments.WallTypeAttachmentModel;
import com.goodcat.vkclient.application.model.music.MusicModel;
import com.goodcat.vkclient.application.model.user.UserModel;
import com.goodcat.vkclient.application.model.user.attachments.UserWallAttachmentsModel;
import com.goodcat.vkclient.application.model.user.attachments.VideoAttachmentModel;
import com.goodcat.vkclient.application.model.user.wall_post.UserWallPostsModel;

import java.util.ArrayList;
import java.util.List;

public class MessageAttachment {

    private LinearLayout wrapper;
    private LinearLayout insiderWrapper;
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

            Log.d("!!!!Simple",""+uwam.getType());
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

        Log.d("!!!!Extended",""+attachments.size());

        for(UserWallAttachmentsModel uwam:attachments){
            Log.d("!!!!Extended ",""+uwam.getType());
            if(uwam.getType().equals("video")){
                printVideoAttachment(uwam);
            } else if(uwam.getType().equals("photo")){
                printPhotoAttachment(uwam);
            } else if(uwam.getType().equals("audio")){
                printAudioAttachment(uwam);
            } else if(uwam.getType().equals("posted_photo")){
                printWallAttachmentFromAttachment(uwam);
            }

        }
    }

    public void printAttachmentFromWall(List<UserWallPostsModel> wallPost){
        UserWallPostsModel thePost = wallPost.get(0);
        Log.d("!!!!Extended wall",""+thePost.getText());
        Log.d("!!!!Extended wall",""+thePost.getCopyHistory());

        if(thePost.getAttachments() != null){
            printAttachment(thePost.getAttachments());
        }

    }

    public void printPhotoAttachment(MessageAttachmentModel uwam){
        TextView divider = new TextView(this.context);
        divider.setHeight(10);
        addViewToAttachmentWrapper(divider);
        ImageView image = new ImageView(this.context);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageParams.gravity = Gravity.LEFT;
        image.setLayoutParams(imageParams);
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

        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageParams.gravity = Gravity.LEFT;
        previewImage.setLayoutParams(imageParams);

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
        this.insiderWrapper = new LinearLayout(this.context);
        this.insiderWrapper.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams insiderParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        insiderParams.setMargins(10,5,5,5);
        this.insiderWrapper.setLayoutParams(insiderParams);


        Log.d("!!!!!-------------- ","");
        Log.d("!!!!!ATTACH text ",""+wall.getText());

        TextView wallType = new TextView(this.context);
        wallType.setText("This post from wall of");
        wallType.setTextColor(this.context.getResources().getColor(R.color.material_blue_grey_900));
        addViewToInsiderWrapper(wallType);

        if(wall.getText() != null){
            TextView textFromUser = new TextView(this.context);
            textFromUser.setText(wall.getText());
            textFromUser.setPadding(0,0,0,5);
            addViewToInsiderWrapper(textFromUser);
        }

        if(wall.getAttachments() !=  null){
            this.printAttachment(wall.getAttachments());
        } else if(wall.getCopyHistory() != null){
            this.printAttachmentFromWall(wall.getCopyHistory());
        }

        Log.d("!!!!!ATTACHMENTS ",""+wall.getAttachments());
        Log.d("!!!!!AT HISTORY ",""+wall.getCopyHistory());
        Log.d("!!!!!-------------- ","");
        //this.printAttachment(wall.getAttachments());
        addViewToAttachmentWrapper(this.insiderWrapper);
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
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageParams.gravity = Gravity.LEFT;
        image.setLayoutParams(imageParams);
        addViewToInsiderWrapper(image);
        TextView divider = new TextView(this.context);
        divider.setHeight(10);
        addViewToInsiderWrapper(divider);
    }

    public void printVideoAttachment(UserWallAttachmentsModel uwam){

    }

    public void printAudioAttachment(UserWallAttachmentsModel uwam){
        MusicModel audio = uwam.getAudio();
        TextView divider = new TextView(this.context);
        divider.setHeight(10);
        addViewToInsiderWrapper(divider);
        List<MusicModel> audioList= new ArrayList<MusicModel>();
        List<MusicModel> audioListAdditional= new ArrayList<MusicModel>();
        audioList.add(audio);
        ListView listView = new ListView(context);
        listView.setFooterDividersEnabled(false);
        addViewToInsiderWrapper(listView);
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

    public void printWallAttachment(UserWallAttachmentsModel uwam){
        UserWallPostsModel wall = uwam.getWall();
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
    private void addViewToInsiderWrapper(View view){
        this.insiderWrapper.addView(view,this.insiderWrapper.getChildCount());
    }

}
