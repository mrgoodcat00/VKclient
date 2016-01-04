package com.goodcat.vkclient.application.adapter;


import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.goodcat.vkclient.application.R;
import com.goodcat.vkclient.application.model.messages.DialogModel;
import com.goodcat.vkclient.application.model.messages.MessagesModel;
import com.goodcat.vkclient.application.model.user.UserModel;
import com.goodcat.vkclient.application.service.DownloadImageService;

import java.util.HashMap;
import java.util.List;

public class MessagesAdapter extends ArrayAdapter<DialogModel> {

    HashMap<String,UserModel> users;

    public MessagesAdapter(Context context, List<DialogModel> objects, HashMap<String,UserModel> usersInfo) {
        super(context, 0, objects);
        this.users = usersInfo;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View currentView;
        ViewMessagesHolder holder;
        if (convertView == null) {
            holder = new ViewMessagesHolder();
            currentView = View.inflate(getContext(), R.layout.messages_list_item, null);
            holder.messageContainer = (LinearLayout) currentView.findViewById(R.id.message_wrapper);
            holder.photoFrom = (ImageView) currentView.findViewById(R.id.messages_user_logo);
            holder.shortMessage = (TextView) currentView.findViewById(R.id.messages_excerpt_message);
            holder.fromName = (TextView) currentView.findViewById(R.id.messages_user_name);
            holder.date = (TextView) currentView.findViewById(R.id.messages_post_time);
            currentView.setTag(holder);
        } else {
            currentView = convertView;
            holder = (ViewMessagesHolder) currentView.getTag();
        }



        MessagesModel message = getItem(position).getMessage();


        android.support.v7.widget.GridLayout grid = (android.support.v7.widget.GridLayout) currentView.findViewById(R.id.grid_icons_wrapper);


        if(message.getUsers_count()>0){
            holder.photoFrom.setVisibility(View.GONE);
            grid.setVisibility(View.VISIBLE);
            Log.d("!!!!", "total count " + message.getUsers_count());

            int count = 0;
            for(long user:message.getChat_active()){
                count++;
                Log.d("!!!!", "counter " + count);
                String url = users.get(user+"").getPhoto50();
                setGridIcon(count,url,grid);
                Log.d("!!!!", "user photo " + url);
            }
        } else {
            holder.photoFrom.setVisibility(View.VISIBLE);
            grid.setVisibility(View.GONE);
            String url = users.get(message.getUser_id() + "").getPhoto100();
            if (url != null) {
                DownloadImageService.loadImage(holder.photoFrom, url);
            }
        }

        if(message.getRead_state() == 0 && message.getOut() == 0){
            holder.messageContainer.setBackgroundResource(R.color.colorUnreadedMessage);
        } else {holder.messageContainer.setBackgroundResource(R.color.colorMessage);}

        if(message.getAttachments() != null) {
            holder.shortMessage.setText(message.getAttachments().get(0).getType());
            holder.shortMessage.setTypeface(null,Typeface.BOLD);
        } else {
            holder.shortMessage.setText(message.getBody());
            holder.shortMessage.setTypeface(null,Typeface.NORMAL);
        }


        holder.fromName.setText(users.get(message.getUser_id()+"").getFirstName()+"");
        holder.date.setText(message.getDateToString());

        return currentView;
    }

    private void setGridIcon(int counter, String url, android.support.v7.widget.GridLayout grid) {
        switch (counter){
            case 0:
                ImageView image1 = (ImageView) grid.findViewById(R.id.u_logo1);
                image1.setVisibility(View.VISIBLE);
                DownloadImageService.loadImage(image1, url);
                break;
            case 1:
                ImageView image2 = (ImageView) grid.findViewById(R.id.u_logo2);
                image2.setVisibility(View.VISIBLE);
                DownloadImageService.loadImage(image2, url);
                break;
            case 2:
                ImageView image3 = (ImageView) grid.findViewById(R.id.u_logo3);
                image3.setVisibility(View.VISIBLE);
                DownloadImageService.loadImage(image3, url);
                break;
            case 3:
                ImageView image4 = (ImageView) grid.findViewById(R.id.u_logo4);
                image4.setVisibility(View.VISIBLE);
                DownloadImageService.loadImage(image4, url);
                break;

            default:
                grid.findViewById(R.id.u_logo1).setVisibility(View.GONE);
                grid.findViewById(R.id.u_logo2).setVisibility(View.GONE);
                grid.findViewById(R.id.u_logo3).setVisibility(View.GONE);
                grid.findViewById(R.id.u_logo4).setVisibility(View.GONE);
                break;
        }

    }

    public static class ViewMessagesHolder {
        LinearLayout messageContainer;
        ImageView photoFrom;
        TextView shortMessage;
        TextView fromName;
        TextView date;
    }
}
