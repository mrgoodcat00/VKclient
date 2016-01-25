package com.goodcat.vkclient.application.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import com.goodcat.vkclient.application.R;
import com.goodcat.vkclient.application.model.messages.MessagesModel;

import java.util.List;

public class DialogMessageAdapter extends ArrayAdapter<MessagesModel> {
    public DialogMessageAdapter(Context context,  List<MessagesModel> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View currentView = View.inflate(getContext(), R.layout.dialog_messages_list_item, null);

        MessagesModel message = getItem(position);

        ImageButton userImage = (ImageButton) currentView.findViewById(R.id.user_image_dialog_message);
        TextView userMessage = (TextView) currentView.findViewById(R.id.user_text_dialog_message);
        TextView messageDate = (TextView) currentView.findViewById(R.id.date_dialog_message);

        userMessage.setText(message.getBody()+"");
        messageDate.setText(message.getDateToString()+"");

        return currentView;




    }
}
