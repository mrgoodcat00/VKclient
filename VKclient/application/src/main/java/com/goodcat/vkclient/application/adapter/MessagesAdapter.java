package com.goodcat.vkclient.application.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.goodcat.vkclient.application.R;
import com.goodcat.vkclient.application.model.messages.MessagesModel;

import java.util.List;

public class MessagesAdapter extends ArrayAdapter<MessagesModel>{

    public MessagesAdapter(Context context, List<MessagesModel> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View currentView = View.inflate(getContext(), R.layout.messages_list_item,null);


        return currentView;
    }
}
