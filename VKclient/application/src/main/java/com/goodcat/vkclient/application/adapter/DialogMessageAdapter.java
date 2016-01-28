package com.goodcat.vkclient.application.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.goodcat.vkclient.application.R;
import com.goodcat.vkclient.application.model.messages.MessagesModel;
import com.goodcat.vkclient.application.model.user.UserModel;
import com.goodcat.vkclient.application.service.ContentAttachment;
import com.goodcat.vkclient.application.service.DownloadImageService;

import java.util.List;

public class DialogMessageAdapter extends ArrayAdapter<MessagesModel> {

    List<UserModel> dialogUsers;
    List<MessagesModel> messages;

    public DialogMessageAdapter(Context context,  List<MessagesModel> objects, List<UserModel> userModels) {
        super(context, 0, objects);
        this.dialogUsers = userModels;
        this.messages = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View currentView = View.inflate(getContext(), R.layout.dialog_messages_list_item, null);
        MessagesModel message = getItem(position);

        ImageButton userImage = (ImageButton) currentView.findViewById(R.id.user_image_dialog_message);
        TextView userMessage = (TextView) currentView.findViewById(R.id.user_text_dialog_message);
        TextView messageDate = (TextView) currentView.findViewById(R.id.date_dialog_message);
        LinearLayout contentWrapper = (LinearLayout) currentView.findViewById(R.id.dialogContentWrapper);

        for(UserModel um:this.dialogUsers){
            if(message.getFrom_id() == um.getId()) {
                DownloadImageService.loadImage(userImage, um.getPhoto50());
            } else if(message.getUser_id() == um.getId()){
                DownloadImageService.loadImage(userImage, um.getPhoto50());
            }
        }





        if(message.getAttachments() !=  null) {
            //Log.d("Mes attachment size", "" + message.getAttachments().size());
            ContentAttachment attachment = new ContentAttachment(getContext(),contentWrapper,message,dialogUsers);
            //Log.d("attachment BODY",""+message.getBody());
            attachment.printAttachment();

        }
        userMessage.setText(message.getBody()+"");
        messageDate.setText(message.getDateToString()+"");

        return currentView;




    }
}
