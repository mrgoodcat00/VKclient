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
import com.goodcat.vkclient.application.model.group.GroupModel;
import com.goodcat.vkclient.application.model.user.UserModel;
import com.goodcat.vkclient.application.model.user.UserResponseWallCopyHistoryModel;
import com.goodcat.vkclient.application.model.user.UserWallPostsModel;
import com.goodcat.vkclient.application.service.UserDataService;

import java.util.List;

public class UserWallPostsAdapter extends ArrayAdapter<UserWallPostsModel>{

    private static String token;

    public UserWallPostsAdapter(Context context, List<UserWallPostsModel> objects, String token) {
        super(context, 0, objects);
        this.token = token;
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
            holder.ownerLayout          = (LinearLayout) convertView.findViewById(R.id.ownerSetLayout);
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
        }

        UserWallPostsModel singlePost = getItem(position);



        List<UserModel> userAuthor = UserDataService.getUserByID(token, singlePost.getFrom_id().toString());
        holder.user_name.setText(userAuthor.get(0).getFirst_name() + " " + userAuthor.get(0).getLast_name());


        List<UserResponseWallCopyHistoryModel> copyHistory = singlePost.getCopy_history();
        if(copyHistory != null) {
            Integer groupID = Math.abs(copyHistory.get(0).getOwner_id());

            List<GroupModel> groupModel = UserDataService.getGroupDataByID(token, groupID.toString());
            if(groupModel != null) {
                String postStarterName = groupModel.get(0).getName() + " " + groupModel.get(0).getScreen_name();

                holder.repost_starter_user_name.setText("" + postStarterName);
            }

            if(copyHistory.get(0).getText() != null){
                holder.post_text.setText(copyHistory.get(0).getText());
            } else {
                holder.post_text.setHeight(0);
                holder.post_text.setTextSize(0);
            }


            Log.d("COPY_HITORY", "" + copyHistory.get(0).getOwner_id());
            Log.d("COPY_HITORY", "" + copyHistory.get(0).getFrom_id());
            Log.d("COPY_HITORY", "" + copyHistory.get(0).getDate());
            Log.d("COPY_HITORY", "" + copyHistory.get(0).getPost_type());
            Log.d("COPY_HITORY", "" + copyHistory.get(0).getId());
            Log.d("COPY_HITORY", "" + copyHistory.get(0).getText());
        } else {
            holder.ownerLayout.removeAllViews();
            holder.ownerLayout.setVisibility(View.INVISIBLE);
        }

        if(singlePost.getText() != null) {
            holder.postTextFromWallOwner.setText(singlePost.getText());
        } else {
            holder.postTextFromWallOwner.setHeight(0);
            holder.postTextFromWallOwner.setTextSize(0);
            holder.postTextFromWallOwnerWrapper.removeAllViews();
        }

        holder.user_posted_time.setText(singlePost.getDate() / (24 * 60 * 60 * 1000)+" hours ago");


        return convertView;
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
        LinearLayout ownerLayout;
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
