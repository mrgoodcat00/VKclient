package com.goodcat.vkclient.application.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.goodcat.vkclient.application.R;
import com.goodcat.vkclient.application.model.user.UserModel;
import com.goodcat.vkclient.application.model.user.attachments.PhotoAttachmentModel;
import com.goodcat.vkclient.application.model.user.attachments.UserWallAttachmentsModel;
import com.goodcat.vkclient.application.model.user.attachments.VideoAttachmentModel;
import com.goodcat.vkclient.application.model.user.wall_post.UserWallGroupsModel;
import com.goodcat.vkclient.application.model.user.wall_post.UserWallPostsModel;
import com.goodcat.vkclient.application.service.DownloadImageService;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class UserWallPostsAdapter extends ArrayAdapter<UserWallPostsModel>{

    private static String token;
    private List<UserModel> wProfiles;
    private List<UserWallGroupsModel> wGroups;
    private List<UserWallPostsModel> wItems;
    private LayoutInflater inflater;

    public UserWallPostsAdapter(Context context, List<UserWallPostsModel> objects,List<UserModel> wProfiles,List<UserWallGroupsModel> wGroups , String token) {
        super(context, 0, objects);
        this.token     = token;
        this.wProfiles = wProfiles;
        this.wGroups   = wGroups;
        this.wItems    = objects;
        this.inflater  = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewWallItemHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.all_wall_posts_list_item,null);
            holder = new ViewWallItemHolder();
            holder.user_name                = (TextView) convertView.findViewById(R.id.user_name);
            holder.user_posted_time         = (TextView) convertView.findViewById(R.id.user_posted_time);
            holder.repost_starter_user_name = (TextView) convertView.findViewById(R.id.repost_starter_user_name);
            holder.postTextFromWallOwner    = (TextView) convertView.findViewById(R.id.postTextFromWallOwner);
            holder.repost_start_post_time   = (TextView) convertView.findViewById(R.id.repost_start_post_time);
            holder.post_text                = (TextView) convertView.findViewById(R.id.post_text);
            holder.comments_cntr            = (TextView) convertView.findViewById(R.id.comments_cntr);
            holder.repost_cntr              = (TextView) convertView.findViewById(R.id.repost_cntr);
            holder.likes_cntr               = (TextView) convertView.findViewById(R.id.likes_cntr);
            holder.authorLayout             = (LinearLayout) convertView.findViewById(R.id.authorSetLayout);
            holder.ownerSetLayout           = (LinearLayout) convertView.findViewById(R.id.ownerSetLayout);
            holder.postTextFromWallOwnerWrapper = (LinearLayout) convertView.findViewById(R.id.postTextFromWallOwnerWrapper);
            holder.user_logo          = (ImageView) convertView.findViewById(R.id.user_logo);
            holder.repost_user_logo   = (ImageView) convertView.findViewById(R.id.repost_user_logo);
            holder.repost_icon        = (ImageView) convertView.findViewById(R.id.repost_icon);
            holder.post_image         = (ImageView) convertView.findViewById(R.id.post_image);
            holder.comments_icon      = (ImageView) convertView.findViewById(R.id.comments_icon);
            holder.bottom_repost_icon = (ImageView) convertView.findViewById(R.id.bottom_repost_icon);
            holder.likes_icon         = (ImageView) convertView.findViewById(R.id.likes_icon);
            convertView.setTag(holder);
        } else {

            holder = (ViewWallItemHolder) convertView.getTag();
            holder.user_name.setText("");
            holder.user_posted_time.setText("");
            holder.repost_starter_user_name.setText("");
            holder.postTextFromWallOwner.setText("");
            holder.repost_start_post_time.setText("");
            holder.post_text.setText("");
            holder.comments_cntr.setText("");
            holder.repost_cntr.setText("");
            holder.likes_cntr.setText("");
            holder.post_image.setImageResource(R.drawable.icon);
            holder.ownerSetLayout.setVisibility(View.VISIBLE);
            holder.postTextFromWallOwnerWrapper.setVisibility(View.VISIBLE);
            holder.post_image.setVisibility(View.VISIBLE);
        }
        UserWallPostsModel singlePost = wItems.get(position);
        List<UserWallAttachmentsModel> attachments = singlePost.getAttachments();
        List<UserWallPostsModel> historyRepost = singlePost.getCopyHistory();

        /*-------------------------------- POST OWNER INFO ----------------------------------------*/
        if(singlePost.getOwnerId() == singlePost.getFromId()){
            UserModel postFromWallOwner = identifyUser(singlePost.getOwnerId());
            holder.user_name.setText(postFromWallOwner.getFirstName() + " " + postFromWallOwner.getLastName());
            DownloadImageService.loadImage(holder.user_logo, postFromWallOwner.getPhoto50());
        } else {
            UserModel postFromOtherUser = identifyUser(singlePost.getFromId());
            if(postFromOtherUser != null){
                holder.user_name.setText(postFromOtherUser.getFirstName() + " " + postFromOtherUser.getLastName());
                DownloadImageService.loadImage( holder.user_logo, postFromOtherUser.getPhoto50());
            } else {
                UserWallGroupsModel group = identifyGroup(singlePost.getFromId());
                holder.user_name.setText(group.getName());
                DownloadImageService.loadImage(holder.user_logo,group.getPhoto50());
            }
        }
        setPostTime(singlePost.getDate(),holder.user_posted_time);

        /*-------------------------------- POST OWNER TEXT ----------------------------------------*/
        if(singlePost.getText().length()>0 && !singlePost.getText().startsWith("Original: http:")){
            holder.postTextFromWallOwner.setText(singlePost.getText());
        } else {
            holder.postTextFromWallOwnerWrapper.setVisibility(View.GONE);
        }
        /*-------------------------------- POST Likes and Reposts ----------------------------------*/
        holder.likes_cntr.setText(singlePost.getLikes().getCount()+"");
        holder.repost_cntr.setText(singlePost.getReposts().getCount()+"");
        holder.comments_cntr.setText(singlePost.getComments().getCount()+"");
        /*-------------------------------- Post by Post Owner -------------------------------------*/
       if(attachments != null && historyRepost == null) {
            if (attachments.get(0).getType().equals("video")) {
                printVideoAttachment(attachments,historyRepost,holder);
                /*Try to put custom video post with description and test them there*/
            } else if (attachments.get(0).getType().equals("photo")) {
                if(attachments.get(0).getPhoto().getText() != null && !attachments.get(0).getPhoto().getText().contains("Original: http:")) {
                    holder.postTextFromWallOwner.setText(attachments.get(0).getPhoto().getText());
                } else {holder.postTextFromWallOwner.setVisibility(View.GONE);
                }
                printPhotoAttachment(attachments,historyRepost,holder);
            }

            holder.ownerSetLayout.setVisibility(View.GONE);

        } else if(attachments != null && historyRepost != null) {

        /*-------------------------------- Repost by Wall Owner with custom attachment-------------------------------------*/
            holder.post_text.setText("In construction");
            holder.ownerSetLayout.setVisibility(View.GONE);
            holder.post_image.setVisibility(View.GONE);

            /*-------------------------------- Repost by Wall Owner without custom attachment-------------------------------------*/
        } else if(historyRepost != null && attachments == null){

            List<UserWallAttachmentsModel> attachmentInsideRepost = historyRepost.get(0).getAttachments();

            if(attachmentInsideRepost == null) {
                if(historyRepost.get(0).getFromId() > 0) {
                    UserModel postOwner = identifyUser(historyRepost.get(0).getFromId());
                    holder.repost_starter_user_name.setText(postOwner.getFirstName() + " " + postOwner.getLastName());
                    DownloadImageService.loadImage(holder.repost_user_logo,postOwner.getPhoto50());
                    setPostTime(historyRepost.get(0).getDate(),holder.repost_start_post_time);
                } else {
                    UserWallGroupsModel groupOwner = identifyGroup(historyRepost.get(0).getFromId());
                    holder.repost_starter_user_name.setText(groupOwner.getName());
                    DownloadImageService.loadImage(holder.repost_user_logo,groupOwner.getPhoto50());
                    setPostTime(historyRepost.get(0).getDate(),holder.repost_start_post_time);
                    holder.post_text.setText(historyRepost.get(0).getText());
                }
                holder.post_text.setVisibility(View.VISIBLE);
                holder.post_text.setText(historyRepost.get(0).getText());
                holder.post_image.setVisibility(View.GONE);
            } else {

                if (historyRepost.get(0).getFromId() > 0) {
                    UserModel postOwner = identifyUser(historyRepost.get(0).getFromId());
                    holder.repost_starter_user_name.setText(postOwner.getFirstName() + " " + postOwner.getLastName());
                    DownloadImageService.loadImage(holder.repost_user_logo,postOwner.getPhoto50());
                } else {
                    UserWallGroupsModel group = identifyGroup(historyRepost.get(0).getFromId());
                    holder.repost_starter_user_name.setText(group.getName());
                    DownloadImageService.loadImage(holder.repost_user_logo,group.getPhoto50());
                }

                if (attachmentInsideRepost.get(0).getType().equals("video")) {
                    printVideoAttachment(attachmentInsideRepost,historyRepost,holder);
                    setPostTime(historyRepost.get(0).getDate(),holder.repost_start_post_time);
                } else if(attachmentInsideRepost.get(0).getType().equals("photo")){
                    printPhotoAttachment(attachmentInsideRepost,historyRepost,holder);
                    setPostTime(historyRepost.get(0).getDate(),holder.repost_start_post_time);
                }
            }
        }

        return convertView;
    }

    public void updateWallPosts(List<UserWallPostsModel> wPosts,List<UserModel> wProfiles,List<UserWallGroupsModel> wGroups){
        for(UserWallPostsModel uwm:wPosts){
            this.wItems.add(uwm);
        }
        for(UserModel uwpm:wProfiles){
            if(!this.wProfiles.contains(uwpm)){
                this.wProfiles.add(uwpm);
            }
        }
        for(UserWallGroupsModel ugpm:wGroups){
            if(!this.wGroups.contains(ugpm)){
                this.wGroups.add(ugpm);
            }
        }
        this.notifyDataSetChanged();
    }

    private UserModel identifyUser(Long id) {
        UserModel user = null;
        for (UserModel m : wProfiles) {
            if (m.getId()==id) {
                user = m;
                break;
            }
        }
        return user;
    }

    private UserWallGroupsModel identifyGroup(Long id) {
        id *= -1;
        UserWallGroupsModel group = null;
        for (UserWallGroupsModel m : wGroups) {
            if (m.getId() == id) {
                group = m;
                break;
            }
        }
        return group;
    }

    private void setPostTime(Long postTime,TextView timeView){
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();
        Long time = postTime * 1000L;
        DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(tz);
        DateTimeFormatter fmt = DateTimeFormat.forPattern("dd MMM yyyy   HH:mm");
        DateTime repostDateTime = new DateTime(time,dateTimeZone);
        String str = fmt.print(repostDateTime);
        timeView.setText(str+"");
    }

    private void printVideoAttachment(List<UserWallAttachmentsModel> attachmentInsideRepost, List<UserWallPostsModel> historyRepost, ViewWallItemHolder holder){
        VideoAttachmentModel video = attachmentInsideRepost.get(0).getVideo();
        holder.post_image.setVisibility(View.VISIBLE);
        if(historyRepost != null) {
            if (historyRepost.get(0).getText() != null) {
                holder.post_text.setVisibility(View.VISIBLE);
                holder.post_text.setText("\n" + historyRepost.get(0).getText() + "\n" + video.getTitle() + "\n");
            } else {
                holder.post_text.setVisibility(View.VISIBLE);
                holder.post_text.setText(video.getTitle() + "\n");
            }
        }
        if (video.getPhoto640() != null) {
            DownloadImageService.loadImage(holder.post_image, video.getPhoto640());
        } else if (video.getPhoto320() != null) {
            DownloadImageService.loadImage(holder.post_image, video.getPhoto320());
        } else if (video.getPhoto130() != null) {
            DownloadImageService.loadImage(holder.post_image, video.getPhoto130());
        } else {
            holder.post_image.setVisibility(View.GONE);
        }

    }

    private void printPhotoAttachment(List<UserWallAttachmentsModel> attachmentInsideRepost,List<UserWallPostsModel> historyRepost, ViewWallItemHolder holder) {
        PhotoAttachmentModel photo = attachmentInsideRepost.get(0).getPhoto();
        if(historyRepost != null){
            if (historyRepost.get(0).getText() != null ) {
                holder.post_text.setVisibility(View.VISIBLE);
                holder.post_text.setText("" + historyRepost.get(0).getText());
            } else { holder.post_text.setVisibility(View.GONE); }
        }

        if (photo.getPhoto604() != null) {
            DownloadImageService.loadImage(holder.post_image, photo.getPhoto604());
        } else if (photo.getPhoto807() != null) {
            DownloadImageService.loadImage(holder.post_image, photo.getPhoto807());
        } else if (photo.getPhoto130() != null) {
            DownloadImageService.loadImage(holder.post_image, photo.getPhoto130());
        } else { holder.post_image.setVisibility(View.GONE);}
    }

    public static class ViewWallItemHolder {
        TextView user_name;
        TextView user_posted_time;
        TextView repost_starter_user_name;
        TextView postTextFromWallOwner;
        TextView repost_start_post_time;
        TextView post_text;
        TextView comments_cntr;
        TextView repost_cntr;
        TextView likes_cntr;
        LinearLayout authorLayout;
        LinearLayout ownerSetLayout;
        LinearLayout postTextFromWallOwnerWrapper;
        ImageView user_logo;
        ImageView repost_user_logo;
        ImageView repost_icon;
        ImageView post_image;
        ImageView comments_icon;
        ImageView bottom_repost_icon;
        ImageView likes_icon;
    }
}
