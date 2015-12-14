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
import com.goodcat.vkclient.application.model.user.UserResponseWallCopyHistoryModel;
import com.goodcat.vkclient.application.model.user.UserWallGroupsModel;
import com.goodcat.vkclient.application.model.user.UserWallPostsModel;
import com.goodcat.vkclient.application.model.user.UserWallProfilesModel;
import com.goodcat.vkclient.application.model.user.attachments.PhotoAttachmentModel;
import com.goodcat.vkclient.application.model.user.attachments.UserWallAttachmentsModel;
import com.goodcat.vkclient.application.model.user.attachments.VideoAttachmentModel;
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
    private List<UserWallProfilesModel> wProfiles;
    private List<UserWallGroupsModel> wGroups;
    private List<UserWallPostsModel> wItems;
    private LayoutInflater inflater;

    public UserWallPostsAdapter(Context context, List<UserWallPostsModel> objects,List<UserWallProfilesModel> wProfiles,List<UserWallGroupsModel> wGroups , String token) {
        super(context, 0, objects);
        this.token     = token;
        this.wProfiles = wProfiles;
        this.wGroups   = wGroups;
        this.wItems    = objects;
        this.inflater  = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

/*        if(!Session.internetConnection(getContext())){
            convertView = inflater.inflate(R.layout.no_internet_connection,null);

            ImageButton button = (ImageButton) convertView.findViewById(R.id.noInternet);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(),MainActivity.class);
                    getContext().startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
            });
            return convertView;
        }*/

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
        List<UserResponseWallCopyHistoryModel> historyRepost = singlePost.getCopyHistory();

        /*-------------------------------- POST OWNER INFO ----------------------------------------*/
        if(singlePost.getOwnerId() == singlePost.getFromId()){
            UserWallProfilesModel postFromWallOwner = identifyUser(singlePost.getOwnerId());
            holder.user_name.setText(postFromWallOwner.getFirstName() + " " + postFromWallOwner.getLastName());
            DownloadImageService.fetchImage(postFromWallOwner.getPhoto50(), holder.user_logo);
        } else {
            UserWallProfilesModel postFromOtherUser = identifyUser(singlePost.getFromId());
            if(postFromOtherUser != null){
                holder.user_name.setText(postFromOtherUser.getFirstName() + " " + postFromOtherUser.getLastName());
                DownloadImageService.fetchImage(postFromOtherUser.getPhoto50(), holder.user_logo);
            } else {
                UserWallGroupsModel group = identifyGroup(singlePost.getFromId());
                holder.user_name.setText(group.getName());
                DownloadImageService.fetchImage(group.getPhoto50(), holder.user_logo);
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
                    UserWallProfilesModel postOwner = identifyUser(historyRepost.get(0).getFromId());
                    holder.repost_starter_user_name.setText(postOwner.getFirstName() + " " + postOwner.getLastName());
                    DownloadImageService.fetchImage(postOwner.getPhoto50(), holder.repost_user_logo);
                    setPostTime(historyRepost.get(0).getDate(),holder.repost_start_post_time);
                } else {
                    UserWallGroupsModel groupOwner = identifyGroup(historyRepost.get(0).getFromId());
                    holder.repost_starter_user_name.setText(groupOwner.getName());
                    DownloadImageService.fetchImage(groupOwner.getPhoto50(), holder.repost_user_logo);
                    setPostTime(historyRepost.get(0).getDate(),holder.repost_start_post_time);
                    holder.post_text.setText(historyRepost.get(0).getText());
                }
                holder.post_text.setVisibility(View.VISIBLE);
                holder.post_text.setText(historyRepost.get(0).getText());
                holder.post_image.setVisibility(View.GONE);
            } else {

                if (historyRepost.get(0).getFromId() > 0) {
                    UserWallProfilesModel postOwner = identifyUser(historyRepost.get(0).getFromId());
                    holder.repost_starter_user_name.setText(postOwner.getFirstName() + " " + postOwner.getLastName());
                    DownloadImageService.fetchImage(postOwner.getPhoto50(), holder.repost_user_logo);
                } else {
                    UserWallGroupsModel group = identifyGroup(historyRepost.get(0).getFromId());
                    holder.repost_starter_user_name.setText(group.getName());
                    DownloadImageService.fetchImage(group.getPhoto50(), holder.repost_user_logo);
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

    public void updateWallPosts(List<UserWallPostsModel> wPosts,List<UserWallProfilesModel> wProfiles,List<UserWallGroupsModel> wGroups){
        for(UserWallPostsModel uwm:wPosts){
            this.wItems.add(this.wItems.size(),uwm);
        }
        for(UserWallProfilesModel uwpm:wProfiles){
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

    private UserWallProfilesModel identifyUser(Long id) {
        UserWallProfilesModel user = null;
        for (UserWallProfilesModel m : wProfiles) {
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

    private void printVideoAttachment(List<UserWallAttachmentsModel> attachmentInsideRepost, List<UserResponseWallCopyHistoryModel> historyRepost, ViewWallItemHolder holder){
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
            DownloadImageService.fetchImage(video.getPhoto640(), holder.post_image);
        } else if (video.getPhoto320() != null) {
            DownloadImageService.fetchImage(video.getPhoto320(), holder.post_image);
        } else if (video.getPhoto130() != null) {
            DownloadImageService.fetchImage(video.getPhoto130(), holder.post_image);
        } else {
            holder.post_image.setVisibility(View.GONE);
        }

    }

    private void printPhotoAttachment(List<UserWallAttachmentsModel> attachmentInsideRepost,List<UserResponseWallCopyHistoryModel> historyRepost, ViewWallItemHolder holder) {
        PhotoAttachmentModel photo = attachmentInsideRepost.get(0).getPhoto();
        if(historyRepost != null){
            if (historyRepost.get(0).getText() != null ) {
                holder.post_text.setVisibility(View.VISIBLE);
                holder.post_text.setText("" + historyRepost.get(0).getText());
            } else { holder.post_text.setVisibility(View.GONE); }
        }

        if (photo.getPhoto604() != null) {
            DownloadImageService.fetchImage(photo.getPhoto604(), holder.post_image);
        } else if (photo.getPhoto807() != null) {
            DownloadImageService.fetchImage(photo.getPhoto807(), holder.post_image);
        } else if (photo.getPhoto130() != null) {
            DownloadImageService.fetchImage(photo.getPhoto130(), holder.post_image);
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
