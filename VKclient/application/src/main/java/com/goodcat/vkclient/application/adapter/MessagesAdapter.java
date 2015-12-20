package com.goodcat.vkclient.application.adapter;


import android.content.Context;
import android.graphics.Typeface;
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
        String url = users.get(message.getUser_id()+"").getPhoto50();
        if(url != null) {
            DownloadImageService.loadImage(holder.photoFrom,url);
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

    public static class ViewMessagesHolder {
        LinearLayout messageContainer;
        ImageView photoFrom;
        TextView shortMessage;
        TextView fromName;
        TextView date;
    }
}
