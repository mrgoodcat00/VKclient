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

        View view = null;
        if (convertView == null) {
            view = View.inflate(getContext(),R.layout.all_wall_posts_list_item,null);
        } else {
            view = convertView;
        }


        UserWallPostsModel singlePost = getItem(position);

        TextView user_name                = (TextView) view.findViewById(R.id.user_name);
        TextView user_posted_time         = (TextView) view.findViewById(R.id.user_posted_time);
        TextView repost_starter_user_name = (TextView) view.findViewById(R.id.repost_starter_user_name);
        TextView postTextFromWallOwner    = (TextView) view.findViewById(R.id.postTextFromWallOwner);
        TextView repost_start_post_time   = (TextView) view.findViewById(R.id.repost_start_post_time);
        TextView post_text                = (TextView) view.findViewById(R.id.post_text);
        TextView comments_cntr            = (TextView) view.findViewById(R.id.comments_cntr);
        TextView repost_cntr              = (TextView) view.findViewById(R.id.repost_cntr);
        TextView likes_cntr               = (TextView) view.findViewById(R.id.likes_cntr);
        LinearLayout authorLayout         = (LinearLayout) view.findViewById(R.id.authorSetLayout);
        LinearLayout ownerLayout          = (LinearLayout) view.findViewById(R.id.ownerSetLayout);
        LinearLayout postTextFromWallOwnerWrapper = (LinearLayout) view.findViewById(R.id.postTextFromWallOwnerWrapper);
        ImageView user_logo          = (ImageView) view.findViewById(R.id.user_logo);
        ImageView repost_user_logo   = (ImageView) view.findViewById(R.id.repost_user_logo);
        ImageView repost_icon        = (ImageView) view.findViewById(R.id.repost_icon);
        ImageView post_image         = (ImageView) view.findViewById(R.id.post_image);
        ImageView comments_icon      = (ImageView) view.findViewById(R.id.comments_icon);
        ImageView bottom_repost_icon = (ImageView) view.findViewById(R.id.bottom_repost_icon);
        ImageView likes_icon         = (ImageView) view.findViewById(R.id.likes_icon);


        List<UserModel> userAuthor = UserDataService.getUserByID(token, singlePost.getFrom_id().toString());
        user_name.setText(userAuthor.get(userAuthor.size()-1).getFirst_name() + " " + userAuthor.get(userAuthor.size()-1).getLast_name());


        List<UserResponseWallCopyHistoryModel> copyHistory = singlePost.getCopy_history();
        if(copyHistory != null) {
            Integer groupID = Math.abs(copyHistory.get(0).getOwner_id());

            List<GroupModel> groupModel = UserDataService.getGroupDataByID(token, groupID.toString());

            String postStarterName = groupModel.get(0).getName() + " " + groupModel.get(0).getScreen_name();

            repost_starter_user_name.setText("" + postStarterName);


            if(copyHistory.get(0).getText() != null){
                post_text.setText(copyHistory.get(0).getText());
            } else {
                post_text.setHeight(0);
                post_text.setTextSize(0);
            }

            Log.d("COPY_HITORY", "" + copyHistory.get(0).getOwner_id());
            Log.d("COPY_HITORY", "" + copyHistory.get(0).getFrom_id());
            Log.d("COPY_HITORY", "" + copyHistory.get(0).getDate());
            Log.d("COPY_HITORY", "" + copyHistory.get(0).getPost_type());
            Log.d("COPY_HITORY", "" + copyHistory.get(0).getId());
            Log.d("COPY_HITORY", "" + copyHistory.get(0).getText());
        } else {
            ownerLayout.removeAllViews();
            ownerLayout.setVisibility(View.INVISIBLE);
        }

        if(singlePost.getText() != null) {
            postTextFromWallOwner.setText(singlePost.getText());
        } else {
            postTextFromWallOwner.setHeight(0);
            postTextFromWallOwner.setTextSize(0);
            postTextFromWallOwnerWrapper.removeAllViews();
        }

        user_posted_time.setText(singlePost.getDate() / (24 * 60 * 60 * 1000)+" hours ago");


        return view;
    }
}
