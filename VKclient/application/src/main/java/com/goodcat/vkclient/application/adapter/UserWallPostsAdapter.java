package com.goodcat.vkclient.application.adapter;


import android.content.Context;
import android.util.Log;
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
import com.goodcat.vkclient.application.model.user.attachments.UserWallAttachmentsModel;
import com.goodcat.vkclient.application.service.DownloadImageService;
import org.joda.time.DateTime;

import java.util.List;

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
            holder.post_image.setImageResource(0);
        }


        UserWallPostsModel singlePost = wItems.get(position);

        if(singlePost.getOwner_id().equals(singlePost.getFrom_id())){

            UserWallProfilesModel postFromWallOwner = identifyUser(wProfiles,singlePost.getOwner_id());
            holder.user_name.setText(postFromWallOwner.getFirst_name() + " " + postFromWallOwner.getLast_name());
            DownloadImageService.fetchImage(postFromWallOwner.getPhoto_50(), holder.user_logo);

        } else {

            UserWallProfilesModel postFromOtherUser = identifyUser(wProfiles,singlePost.getFrom_id());
            holder.user_name.setText(postFromOtherUser.getFirst_name() + " " + postFromOtherUser.getLast_name());
            DownloadImageService.fetchImage(postFromOtherUser.getPhoto_50(), holder.user_logo);

        }






        UserWallAttachmentsModel attachmentsFormOwner = singlePost.getAttachments();
        if(attachmentsFormOwner != null) {

            if(attachmentsFormOwner.getType().equals("photo")){
                Log.d("GOODCAT", attachmentsFormOwner.getPhoto().getPhoto_130() + "");
                if( !attachmentsFormOwner.getPhoto().getText().contains("Original") && attachmentsFormOwner.getPhoto().getText() != null ) {
                    holder.post_text.setText(attachmentsFormOwner.getPhoto().getText());
                }
                if(attachmentsFormOwner.getPhoto().getPhoto_604().length()>0){
                    DownloadImageService.fetchImage(attachmentsFormOwner.getPhoto().getPhoto_604(),holder.post_image);
                }
                else if (attachmentsFormOwner.getPhoto().getPhoto_807().length()>0){
                    DownloadImageService.fetchImage(attachmentsFormOwner.getPhoto().getPhoto_807(),holder.post_image);
                }
                else if(attachmentsFormOwner.getPhoto().getPhoto_130().length()>0){
                    DownloadImageService.fetchImage(attachmentsFormOwner.getPhoto().getPhoto_130(),holder.post_image);
                }

            } else if(attachmentsFormOwner.getType().equals("video")){
                if( !attachmentsFormOwner.getPhoto().getText().contains("Original") && attachmentsFormOwner.getPhoto().getText() != null ) {
                    holder.post_text.setText(attachmentsFormOwner.getPhoto().getText());
                }
                if(attachmentsFormOwner.getPhoto().getPhoto_604().length()>0){
                    DownloadImageService.fetchImage(attachmentsFormOwner.getPhoto().getPhoto_604(),holder.post_image);
                }
                else if (attachmentsFormOwner.getPhoto().getPhoto_807().length()>0){
                    DownloadImageService.fetchImage(attachmentsFormOwner.getPhoto().getPhoto_807(),holder.post_image);
                }
                else if(attachmentsFormOwner.getPhoto().getPhoto_130().length()>0){
                    DownloadImageService.fetchImage(attachmentsFormOwner.getPhoto().getPhoto_130(),holder.post_image);
                }
            }
        }









        DateTime startDate = new DateTime(wItems.get(position).getDate() * 1000L);
        holder.user_posted_time.setText(startDate.getDayOfMonth() + "-" +
                startDate.getMonthOfYear() + "-" + startDate.getYear() + "  " +
                startDate.getHourOfDay() + ":" + startDate.getMinuteOfHour());

        if(singlePost.getText() != null) {
            holder.postTextFromWallOwner.setText(singlePost.getText());
        } else {
            holder.postTextFromWallOwnerWrapper.setVisibility(View.GONE);
        }




        final List<UserResponseWallCopyHistoryModel> copy_history = singlePost.getCopy_history();

        if (copy_history != null) {









            for (UserWallProfilesModel p : wProfiles) {
                if (p.getId().equals(singlePost.getFrom_id())&& p.getId().equals(singlePost.getOwner_id())) {
                    holder.ownerSetLayout.setVisibility(View.GONE);
                    holder.postTextFromWallOwnerWrapper.setVisibility(View.GONE);
                    break;
                } else if(p.getId().equals(singlePost.getFrom_id())){
                    holder.repost_starter_user_name.setText(p.getFirst_name() + " " + p.getLast_name());
                    DownloadImageService.fetchImage(p.getPhoto_50(), holder.repost_user_logo);

                    DateTime startPostTime = new DateTime(wItems.get(position).getDate() * 1000L);
                    holder.repost_start_post_time.setText(startPostTime.getDayOfMonth() + "-" +
                            startPostTime.getMonthOfYear() + "-" + startPostTime.getYear() + "  " +
                            startPostTime.getHourOfDay() + ":" + startPostTime.getMinuteOfHour());
                    break;
                }
            }


            if (copy_history != null) {
                if (copy_history.get(0).getText().toString().length()>0) {

                    holder.post_text.setText(copy_history.get(0).getText());

                    for (UserWallGroupsModel g : wGroups) {
                        String groupID = "-"+g.getId();
                        if (groupID.equals(copy_history.get(0).getFrom_id().toString())) {
                            holder.ownerSetLayout.setVisibility(View.VISIBLE);
                            holder.repost_starter_user_name.setText(g.getName());
                            DownloadImageService.fetchImage(g.getPhoto_50(), holder.repost_user_logo);
                            break;
                        }
                    }

                    DateTime start = new DateTime(copy_history.get(0).getDate().longValue() * 1000L);
                    holder.repost_start_post_time.setText(start.getDayOfMonth() + "-" +
                            start.getMonthOfYear() + "-" + start.getYear() + "  " +
                            start.getHourOfDay() + ":" + start.getMinuteOfHour());

                }
            }









        } else {
            holder.ownerSetLayout.setVisibility(View.GONE);
        }












        return convertView;
    }

    private UserWallProfilesModel identifyUser(List<UserWallProfilesModel> wProfiles,Long id){
        UserWallProfilesModel user = null;
        for(UserWallProfilesModel m : wProfiles){
            if(m.getId().equals(id)) {
               user = m;
                break;
            }
        }
        return user;
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
