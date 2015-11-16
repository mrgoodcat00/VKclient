package com.goodcat.vkclient.application.adapter;


import android.content.Context;
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

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class UserWallPostsAdapter extends ArrayAdapter<UserWallPostsModel>{

    private static String token;
    private List<UserWallProfilesModel> wProfiles;
    private List<UserWallGroupsModel> wGroups;
    private List<UserWallPostsModel> wItems;

    public UserWallPostsAdapter(Context context, List<UserWallPostsModel> objects,List<UserWallProfilesModel> wProfiles,List<UserWallGroupsModel> wGroups , String token) {
        super(context, 0, objects);
        this.token     = token;
        this.wProfiles = wProfiles;
        this.wGroups   = wGroups;
        this.wItems    = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewWallItemHolder holder;
        if (convertView == null) {
            convertView = View.inflate(getContext(),R.layout.all_wall_posts_list_item,null);
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
            holder.authorLayout         = (LinearLayout) convertView.findViewById(R.id.authorSetLayout);
            holder.ownerSetLayout          = (LinearLayout) convertView.findViewById(R.id.ownerSetLayout);
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
            holder.ownerSetLayout.setVisibility(View.VISIBLE);
            holder.postTextFromWallOwnerWrapper.setVisibility(View.VISIBLE);
            holder.post_image.setVisibility(View.VISIBLE);
        }
        UserWallPostsModel singlePost = wItems.get(position);
        List<UserWallAttachmentsModel> attachments = singlePost.getAttachments();
        List<UserResponseWallCopyHistoryModel> historyRepost = singlePost.getCopy_history();
        /*-------------------------------- POST OWNER INFO ----------------------------------------*/
        if(singlePost.getOwner_id().equals(singlePost.getFrom_id())){
            UserWallProfilesModel postFromWallOwner = identifyUser(singlePost.getOwner_id());
            holder.user_name.setText(postFromWallOwner.getFirst_name() + " " + postFromWallOwner.getLast_name());
            DownloadImageService.fetchImage(postFromWallOwner.getPhoto_50(), holder.user_logo);
        } else {
            UserWallProfilesModel postFromOtherUser = identifyUser(singlePost.getFrom_id());
            if(postFromOtherUser != null){
                holder.user_name.setText(postFromOtherUser.getFirst_name() + " " + postFromOtherUser.getLast_name());
                DownloadImageService.fetchImage(postFromOtherUser.getPhoto_50(), holder.user_logo);
            } else {
                UserWallGroupsModel group = identifyGroup(singlePost.getFrom_id());
                holder.user_name.setText(group.getName());
                DownloadImageService.fetchImage(group.getPhoto_50(), holder.user_logo);
            }
        }
        setPostTime(singlePost.getDate(),holder.user_posted_time);

        /*-------------------------------- POST OWNER TEXT ----------------------------------------*/
        if(singlePost.getText().length()>0 && !singlePost.getText().startsWith("Original: http:")){
            holder.postTextFromWallOwner.setText(singlePost.getText());
        } else {
            holder.postTextFromWallOwnerWrapper.setVisibility(View.GONE);
        }

        /*-------------------------------- Post by Post Owner -------------------------------------*/
        if(attachments != null && historyRepost == null) {
            if (attachments.get(0).getType().equals("video")) {
                VideoAttachmentModel video = attachments.get(0).getVideo();
                holder.post_text.setText("" + video.getTitle() + "\n" + video.getDescription() + "\n");
                if(video.getPhoto_640() != null) {
                    DownloadImageService.fetchImage(video.getPhoto_640(), holder.post_image);
                } else if(video.getPhoto_320()!= null) {
                    DownloadImageService.fetchImage(video.getPhoto_320(), holder.post_image);
                } else if(video.getPhoto_130()!= null) {
                    DownloadImageService.fetchImage(video.getPhoto_130(), holder.post_image);
                } else {holder.post_image.setVisibility(View.GONE);}
            } else if (attachments.get(0).getType().equals("photo")) {
                PhotoAttachmentModel photo = attachments.get(0).getPhoto();
                if(photo.getText().length()>0 && !photo.getText().startsWith("Original: http:")) {
                    holder.post_text.setText("" + photo.getText());
                } else {holder.post_text.setVisibility(View.GONE);}
                if(photo.getPhoto_604()!= null) {
                    DownloadImageService.fetchImage(photo.getPhoto_604(), holder.post_image);
                } else if(photo.getPhoto_807()!= null) {
                    DownloadImageService.fetchImage(photo.getPhoto_807(), holder.post_image);
                } else if(photo.getPhoto_130()!= null) {
                    DownloadImageService.fetchImage(photo.getPhoto_130(), holder.post_image);
                } else {holder.post_image.setVisibility(View.GONE);}
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
                if(historyRepost.get(0).getFrom_id() > 0) {
                    UserWallProfilesModel postOwner = identifyUser(historyRepost.get(0).getFrom_id());
                    holder.repost_starter_user_name.setText(postOwner.getFirst_name() + " " + postOwner.getLast_name());
                    DownloadImageService.fetchImage(postOwner.getPhoto_50(), holder.repost_user_logo);
                    setPostTime(historyRepost.get(0).getDate(),holder.repost_start_post_time);
                } else {
                    UserWallGroupsModel groupOwner = identifyGroup(historyRepost.get(0).getFrom_id());
                    holder.repost_starter_user_name.setText(groupOwner.getName());
                    DownloadImageService.fetchImage(groupOwner.getPhoto_50(), holder.repost_user_logo);
                    setPostTime(historyRepost.get(0).getDate(),holder.repost_start_post_time);
                }
                holder.post_text.setText(historyRepost.get(0).getText());
                holder.post_image.setVisibility(View.GONE);
            } else {







               /* UserWallProfilesModel postOwner = identifyUser(historyRepost.get(0).getFrom_id());
                if (postOwner != null) {
                    holder.repost_starter_user_name.setText(postOwner.getFirst_name() + " " + postOwner.getLast_name());
                    DownloadImageService.fetchImage(postOwner.getPhoto_50(), holder.repost_user_logo);
                } else {
                    UserWallGroupsModel group = identifyGroup(historyRepost.get(0).getFrom_id());
                    holder.repost_starter_user_name.setText(group.getName());
                    DownloadImageService.fetchImage(group.getPhoto_50(), holder.repost_user_logo);

                    if (attachments != null) {
                        if (attachments.get(0).getType().equals("video")) {
                            VideoAttachmentModel video = attachments.get(0).getVideo();
                            holder.post_text.setText("" + video.getTitle() + "\n" + video.getDescription() + "\n");
                            if (video.getPhoto_640() != null) {
                                if (video.getId().toString().equals("171565754")) {
                                    Log.d("!!!!!!", video.getPhoto_640() + "");
                                }
                                DownloadImageService.fetchImage(video.getPhoto_640(), holder.post_image);
                            } else if (video.getPhoto_320() != null) {
                                DownloadImageService.fetchImage(video.getPhoto_320(), holder.post_image);
                            } else if (video.getPhoto_130() != null) {
                                DownloadImageService.fetchImage(video.getPhoto_130(), holder.post_image);
                            } else {
                                DownloadImageService.fetchImage("default", holder.post_image);
                            }

                        } else if (attachments.get(0).getType().equals("photo")) {
                            PhotoAttachmentModel photo = attachments.get(0).getPhoto();
                            if (photo.getText().length() > 0 && !photo.getText().startsWith("Original: http:")) {
                                holder.post_text.setText("" + photo.getText());
                            } else { *//* --------------- Think about it------- *//*}
                            if (photo.getPhoto_604() != null) {
                                DownloadImageService.fetchImage(photo.getPhoto_604(), holder.post_image);
                            } else if (photo.getPhoto_807() != null) {
                                DownloadImageService.fetchImage(photo.getPhoto_807(), holder.post_image);
                            } else if (photo.getPhoto_130() != null) {
                                DownloadImageService.fetchImage(photo.getPhoto_130(), holder.post_image);
                            } else {
                                DownloadImageService.fetchImage("default", holder.post_image);
                            }
                        }
                    } else {
                        holder.post_text.setText(historyRepost.get(0).getText());
                        holder.post_image.setVisibility(View.GONE);
                    }
                }*/


            }









        }






        return convertView;
    }

    private UserWallProfilesModel identifyUser(Long id) {
        UserWallProfilesModel user = null;
        for (UserWallProfilesModel m : wProfiles) {
            if (m.getId().equals(id)) {
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
            if (m.getId().equals(id)) {
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
        DateTime repostDateTime = new DateTime(time,dateTimeZone);
        timeView.setText(repostDateTime.getDayOfMonth() + "-" +
                repostDateTime.getMonthOfYear() + "-" + repostDateTime.getYear() + "  " +
                repostDateTime.getHourOfDay() + ":" + repostDateTime.getMinuteOfHour());
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
